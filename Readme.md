Создаем запись в кеше через запрос в приложение из Postman 

(Для CentOS 7 удобно загрузить и распаковать архив с официального сайта:
https://www.postman.com/downloads/
установить библиотеку yum install libXScrnSaver
запустить Postman, при первом запуске выбрать Sign in / Sign up through email instead внизу страницы, чтобы не логиниться с google-аккаунтом)

Создание записи:

POST http://localhost:8080/person/create
{
      "name": "bubu"
}

ответ 200


Проверка наличия записи:
GET http://localhost:8080/person/get/all