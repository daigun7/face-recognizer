Face Recognizer
===============

단말기의 카메라 사진촬영 또는 앨범 이미지 데이터를 사용하여 CFR API를 통해 얼굴 정보와 닮은 연예인을 조회하실 수 있습니다.





What's CFR API?
---------------
Clova Face Recognition API(이하 CFR API)는 이미지 데이터를 입력받은 후 얼굴 인식 결과를 JSON 형태로 반환합니다. CFR API는 이미지에 있는 얼굴 인식하여 분석 정보를 제공하는 얼굴 감지 API와 닮은 연예인을 알려주는 유명인 얼굴 인식 API를 제공합니다. CFR API는 HTTP 기반의 REST API이며, 사용자 인증(로그인)이 필요하지 않은 비로그인 Open API입니다.

더 많은 정보는 [네이버 개발자][1] 참조 바랍니다.

API Key Setting
---------------
```
//Constants.kt
const val CLIENT_ID = "client id here"
const val CLIENT_SECRET = "client secret here"
const val CFR_API_HOST = "https://openapi.naver.com"
```


[1]: https://developers.naver.com/docs/clova/api/
