## TinderRoulette backend

This is the backend part of the TinderRoulette web application. 
This application exposes the Partitionner, Matchmaking and Switchgroup features through its REST api and allow students and teachers alike to benefit from these group organizing functionnalities. 

### Features

* Partitionner
This features is targeted for the teachers. This tools allows the user to create multiple kind of groups such as exercise class or laboratories. 

GET:
Get existing indexes: /existingGroup/index/{idClass}/{idGroupType}/
Get existing groups with index: /existingGroup/{idClass}/{idGroupType}/{index}/
Get existing groups disregarding index: /existingGroup/{idClass}/{idGroupType}/

PUT: 
Update an existing group: /saveGroup/{idClass}/{idGroupType}/

POST: 
Create a group: /createGroup/
Save group: /saveGroup/{idClass}/{idGroupType}/

* Partitionning sets
* Requesting teamwork colleague
* Handling user setting

### Contributors

We were a team of six people in Computer Engineering. Click [here](https://github.com/orgs/S6infoTinderRoulette/people) to view the list.
