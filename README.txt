
To Run:

mvn spring-boot:run


the following apis exist.   Enrollees are updated with 0-n dependents

http://localhost:8081/golden/home - health check

http://localhost:8081/golden/enrollment      - GET     all enrollees with dependents
http://localhost:8081/golden/enrollment/{id} - GET     all enrollees by id 
http://localhost:8081/golden/enrollment      - POST    create enrollee json with 0 to n dependents (see below)
http://localhost:8081/golden/enrollment/{id} - DELETE  delete enrollee
http://localhost:8081/golden/enrollment/{id} - PUT     update enrolllee json with 0 to n dependents (see below)

Enrollee example without dependents



{
	"id":1000,
	"firstName": "Mary",
	"middleName":"Jane",
	"lastName":  "Doe",
	"birthYear": 2020,
	"birthMonth": 11,
	"birthDayOfMonth": 8,
	"dbVersion":1,
	"phone":     "919-555-1212",
	"active":true,
	"dependents":null
}

Enrollee with dependents

{
	"id":1000,
	"firstName":"Mary",
	"middleName":"Jane",
	"lastName":"Doe",
	"birthYear": 2020,
	"birthMonth": 11,
	"birthDayOfMonth": 8,
	"dbVersion":1,
	"phone":"919-555-1212",
	"active":true,
	"dependents":[
		{
			"id":1001,
			"firstName":"Baby",
			"middleName":"Jane",
			"lastName":"Doe",
			"birthYear": 2020,
			"birthMonth": 11,
			"birthDayOfMonth": 8,
		    "dbVersion":1}
    ]
}



There is no CORS, CSRF etc - See SecurityConfig to update
There is no SSO authentication
There is on Authorization

