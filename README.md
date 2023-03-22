
## API

### GET /search/blog

#### Request
**parameter**

| 이름    | 타임     | 필수 | 설명                                                                   |
|:------|--------|----|----------------------------------------------------------------------|
| query | String | O  | 검색을 원하는 질의어                                                          |
| sort  | String | X  | 결과 문서 정렬 방식<br/>(ACCURACY(정확도순) 또는 RECENCY(최신순), default = accuracy) |
| page  | Int    | X  | 결과 페이지 번호 <br/>(min = 1, max = 50, default = 1)   |
| size  | Int    | X  | 한 페이지에 보여질 문서 수<br/>(min = 1, max = 50, default = 10)                |

#### Response
**Status**

 코드  | 구분     | 설명          |
|:----|--------|-------------|
| 200 | 정상     |  |
| 400 | 요청자 오류 | 필수값 누락      |
| 4xx | 요청자 오류 |             |
| 5xx | 시스템 오류 |             |

**Body**

| 이름             | 타입            | 필수  | 설명                                                          |
|:---------------|---------------|-----|-------------------------------------------------------------|
| meta           | Object        | O   | 메타 정보                                                       |
| - totalCount   | Int           | O   | 검색된 문서 수                                                    |
| - pageableCount| Int           | O   | total_count 중 노출 가능 문서 수                                    |
| - isEnd        | Boolean       | O   | 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음 |
| documents      | List\<Object> | O   | 검색된 문서<br/>(검색된 문서가 없으면 null 대신 empty List 존재)                   |
| - title        | String        | x   | 블로그 글 제목                                                    |
| - contents     | String        | x   | 블로그 글 요약                                                    |
| - url          | String        | x   | 블로그 글 URL                                                   |
| - blogName     | String        | x   | 블로그의 이름                                                     |
| - thumbnail    | String        | x   | 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음        |
| - createdAT    | LocalDateTime | x   | 문서 생성일시<br/>([YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000)           |

**Sample Body**

http://localhost:8080/search/blog?query=스즈메의문단속&size=2
```json
{
  "meta": {
    "totalCount": 15614,
    "pageableCount": 799,
    "isEnd": false
  },
  "documents": [
    {
      "title": "<b>스즈메의</b> <b>문단속</b> 감상후기",
      "contents": "<b>스즈메의</b> <b>문단속</b>을 두 번 보고 왔습니다(8일은 CGV, 10일은 메가박스). 어제(10일) 메가박스 대구신세계에서 <b>스즈메의</b> <b>문단속</b>을 보고 왔습니다. 작품 &#39;언어의 정원&#39;, &#39;초속 5센티미터&#39;, &#39;너의 이름은&#39;으로 잘 알려진 신카이 마코토 감독의 최신작입니다. 일본에서는 더 퍼스트 슬램덩크(2022년 12월)보다 한 달 먼저...",
      "url": "http://gicde.tistory.com/19",
      "blogName": null,
      "thumbnail": "https://search2.kakaocdn.net/argon/130x130_85_c/uhSVLUyebv",
      "createdAt": "2023-03-12T09:34:38"
    },
    {
      "title": "<b>스즈메의</b> <b>문단속</b> 리뷰",
      "contents": "일본 역사상 5위를 차지한 영화 이후 자신만의 스타일을 고수하며 세계관과 캐릭터 구성, 스타일이 비슷한 &#39;웨딩 차일드&#39;를 내놨다. 신작 &#39;<b>스즈메의</b> <b>문단속</b>&#39;도 일종의 재난 판타지 3부작으로 엮어질 만큼 반복되는 이야기와 캐릭터를 보여준다. 그럼에도 불구하고 &lt;<b>스즈메의</b> <b>문단속</b>&gt; 역시 이전의 신카이 월드가 보여주지...",
      "url": "http://victoryfutureismine.com/47",
      "blogName": null,
      "thumbnail": "https://search3.kakaocdn.net/argon/130x130_85_c/6L3jUB6GAlM",
      "createdAt": "2023-03-08T23:12:55"
    }
  ]
}
```

### GET /popular-keywords

#### Response
**Status**

 코드  | 구분     | 설명          |
|:----|--------|-------------|
| 200 | 정상     |  |
| 5xx | 시스템 오류 |             |

**Body(List)**

| 이름              | 타입     | 필수  | 설명     |
|:----------------|--------|-----|--------|
| keyword         | String | O   | 검색 키워드 |
| count           | Int    | O   | 검색된 횟수 |

***empty list 응답 가능***

**Sample Body**

http://localhost:8080/popular-keywords
```json
[
  {
    "keyword": "스즈메의문단속",
    "count": 6
  },
  {
    "keyword": "소울메이트",
    "count": 3
  }
]
```
empty List (검색된 키워드가 하나도 없을때)
```json
[
  
]
```


### 프로젝트 설명

- 
- 카카오 API 장애시 네이버 API 사용
  - PathVariable도 존재하지 않기 때문에 404도 일시적인 도메인 문제로 판단되어 5xx와 같이 네이버 검색을 이용합니다. 

## 외부 라이브러리 및 오픈소스

- [Spring Cloud OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html): 외부 API 서비스 클라이언트 구현을 위해 사용
- [Lombok](https://projectlombok.org): getter, settter, ... 자동 생성을 위해 사용
- mockito-junit-jupiter: mock test를 위해 사용
- spring-boot-starter-validation: Request에 대한 validation 적용을 위해 사용 
- spring-boot-starter-cache: 장기적으로 캐시 사용이 늘어난다는 가정하에 캐시를 위한 기본적인 종속성 추가를 위해 사용
