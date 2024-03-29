# 구현 범위
## 구현 1) - 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
GET /v1/products/lowest-by-category

일반적인 관계형 데이터베이스를 생각해보면 카테고리 특성상 cardinality가 크지 않아 별도의 인덱스를 생성하여도 데이터 조회 시 이점이 없습니다.  
카테고리마다 다수의 상품이 존재할 경우, API 요청할 때마다 카테고리별 최저가 상품을 구하는 작업은 리스크가 있습니다.  
따라서 별도의 aggregation 배치 작업을 통해 카테고리별 최저가 상품을 구하는 작업이 필요하고 이를 주기적으로 업데이트 하는 것이 좋습니다.

배치 작업을 통해 최저가를 구하기 때문에 상품 가격을 실시간으로 핸들링하짐 못하는 단점이 있습니다.  
다만, 가격 변동 이벤트가 발생했을 때 변동한 상품이 해당 카테고리에서 최저가 여부를 확인하고 업데이트 할 수 있습니다.  
이를 가격 변동을 담당하는 서버가 직접적으로 처리하여도 되지만 이벤트 기반의 메세지 처리하여 처리할수도 있습니다.  

상품 재고에 따른 프로세스도 위와 같이 구성할 수 있지만, 최저가 상품이 품절되었을 때 또 다른 최저가 상품을 구해야하기 때문에  
Aggregation 작업이 추가적으로 필요합니다.  

또는 상품 데이터를 Elasticsearch에 인덱싱하여 aggregation 요청을 처리할수도 있습니다.

추가적으로 최저가 데이터가 실시간으로 변하지 않는다면 별도의 글로벌하게 캐시 레이어를 두어 처리하는 것도 좋아보입니다.

## 구현 2) - 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
GET /v1/products/lowest-by-brand

최저가 브랜드를 찾는 요청도 위와 같이 aggregation 작업을 별도로 두는 것이 좋습니다.  
카테고리의 cardinality는 일정하지만 브랜드는 크게 증가할 수 있기 때문에 API 요청이 있을 때마다 브랜드의 모든 상품의 합을 구하고 최저가를 구하는 프로세스는 적절하지 않습니다.  

## 구현 3) - 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
GET /v1/products/lowest-and-highest?category={카테고리}
카테고리
- TOP: 상위
- OUTER: 아우터
- BOTTOM: 바지
- SNEAKERS: 스니커즈
- BAG: 가방
- HAT: 모자
- SOCKS: 양말
- ACCESSORY: 액세서리

이 API 처리도 구현 1와 같이 생각할 수 있습니다.

## 구현 4) 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API

CREATE
POST /v1/products

UPDATE
PUT /v1/products/{brand}

DELETE
DELETE /v1/products/{brand}

별도의 ID 값을 두지 않아 brand가 unique하고 한 브랜드의 모든 카테고리 상품이 반드시 하나씩 존재한다고 가정했습니다.
이미 있는 브랜드 상품이 있다면 생성하지 못하게 했고, 존재하지 않는 브랜드의 상품을 업데이트하거나 삭제하지 못하도록 했습니다.

나머지 가격, 브랜드 이름, 카테고리에 대한 제약사항(constraint)를 별도로 체크하지 않고 있으나 필요하다면 validation 작업이 필요합니다.  
또 dto와 entity 사이의 전환을 매뉴얼로 진행했으나 추후 외부 라이브러리(map-struct)를 통해 간단히 진행할 수 있습니다.   

## Entities
Product
- price: 소숫점 제외의 정수형 숫자(0 ~ N)
- brand: 문자열
- category: 문자열(상의부터 ~ 액세서리까지의 8개)

보통 primary key를 위해 auto-generated 값을 활용하지만,  
이 문제 범위에서는 `브랜드 - 카테고리` 별 단 하나의 상품만 존재하기 때문에 이 두 컬럼의 값을 pk로 잡아서 진행했습니다.

## Database
로컬 DB(H2)를 사용하지 않고 메모리에 데이터를 저장하도록 구현했습니다.
애플리케이션을 실행하면 예제 데이터는 초기화하여 저장하고 있습니다. 

# 빌드, 테스트 실행 방법

```shell
# cd [project root directory]
./gradlew clean
./gradlew build
java -jar ./build/libs/untitle01-0.0.1-SNAPSHOT.jar
```

프로젝트에 있는 example.http 파일에 있는 예제 요청으로 테스트 가능합니다.