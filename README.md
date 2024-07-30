Technologies used:
PostgreSQL 16
Spring boot 3
java 22
lombok
mapstruct
Junit
Mockito
assetj


APIS:

1) Create Campaigns
curl --location 'http://localhost:8080/api/campaigns' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "goku",
    "subject" : "we will protest tomorrow",
    "emailBody" : "at noon 1.pm. The rich will see the potential of poor dogs.",
    "status" : "NONE",
    "subscribers": ["test.1@gmail.com", "test.2@gmail.com", "test.3@gmail.com", "ab@gmail.com"],
    "client_id" : 3
}'

Response : 
{"id":18,"name":"goku","subject":"we will protest tomorrow","emailBody":"at noon 1.pm. The rich will see the potential of poor dogs.","status":"NONE","subscribers":["test.1@gmail.com","test.2@gmail.com","test.3@gmail.com","ab@gmail.com"],"client_eMail":null,"client_id":3}


2) list Campaigns
curl --location 'http://localhost:8080/api/campaigns/client/3' \
--data ''
Response : 
[{"id":1,"name":"goku","subject":"we will protest tomorrow","emailBody":"at noon 1.pm. The rich will see the potential of poor dogs.","status":"SENT","subscribers":["a@gmail.com","b@gmail.com","c@gmail.com","a@gmail.com"],"client_eMail":"dbz@gmail.com","client_id":3},{"id":2,"name":"goku","subject":"we will protest tomorrow","emailBody":"at noon 1.pm. The rich will see the potential of poor dogs.","status":"NONE","subscribers":["test.1@gmail.com","test.2@gmail.com","test.3@gmail.com","ab@gmail.com"],"client_eMail":"dbz@gmail.com","client_id":3},{"id":15,"name":"goku","subject":"we will protest tomorrow","emailBody":"at noon 1.pm. The rich will see the potential of poor dogs.","status":"SENT","subscribers":["test.1@gmail.com","test.2@gmail.com","test.3@gmail.com","ab@gmail.com"],"client_eMail":"dbz@gmail.com","client_id":3},{"id":16,"name":"goku","subject":"we will protest tomorrow","emailBody":"at noon 1.pm. The rich will see the potential of poor dogs.","status":"NONE","subscribers":["test.1@gmail.com","test.2@gmail.com","test.3@gmail.com","ab@gmail.com"],"client_eMail":"dbz@gmail.com","client_id":3},{"id":17,"name":"goku","subject":"we will protest tomorrow","emailBody":"at noon 1.pm. The rich will see the potential of poor dogs.","status":"NONE","subscribers":["test.1@gmail.com","test.2@gmail.com","test.3@gmail.com","ab@gmail.com"],"client_eMail":"dbz@gmail.com","client_id":3}]

3) Send Mail
curl --location --request POST 'http://localhost:8080/api/campaigns/send/15'

Response:
Email sent

4) get Distinct clients 
curl --location 'http://localhost:8080/api/clients/client' \
   --data ''
Response :
   [
   {
   "id": null,
   "name": "Vegeta",
   "email": "dbz@gmail.com",
   "campaigns": null
   },
   {
   "id": null,
   "name": "goku",
   "email": "szya@asd.com",
   "campaigns": null
   }
   ]




