## TinderRoulette backend

This is the backend part of the TinderRoulette web application. 
This application exposes the Partitioner, Matchmaking and Switchgroup features through its REST API and allows students and teachers alike to benefit from these group organizing functionalities. 

### Features

#### Partitioner
This feature is targeted for the teachers. This tool allows the user to create multiple kinds of groups such as exercise classes or laboratories. 

GET:   
Get existing indexes: /existingGroup/index/{idClass}/{idGroupType}/    
Get existing groups with index: /existingGroup/{idClass}/{idGroupType}/{index}/    
Get existing groups disregarding index: /existingGroup/{idClass}/{idGroupType}/    

PUT:    
Update an existing group: /saveGroup/{idClass}/{idGroupType}/     

POST:   
Create a group: /createGroup/    
Save group: /saveGroup/{idClass}/{idGroupType}/    

#### Matchmaking  
This feature is tailored for the students as a way to facilitate group formation.    

GET:      
Get existing groups based on activity: /matchmaking/{idActivity}/        
Check if current user is in a full team: /matchmaking/userteamfull/{idActivity}/    
Get current user team: /matchmaking/userteam/{idActivity}/     
Get all free users: /matchmaking/members/{idActivity}/    
Get all groups that are not complete: /matchmaking/groups/{idActivity}/{getOpen}/    
Get current user invitations: /request/requested/    
Get current user sent invitations: /request/seeking/    
Get current user sent invitations for a specific activity: /request/seeking/{idActivity}/    
Get request where both users have invited each other: /request/duplicate/    
Get activities associated to a specific class: /activities/associatedTo/{idClass}/  
Get numbers of partners per groups in a specific activity: /activities/{idActivity}/numberOfPartners/    

PUT:   
Get state of formed teams and allows teacher to lock team modification for said activity: /matchmaking/{idActivity}/{isFinal}/    

POST:    
Merge two or more members into a team: /matchmaking/{idActivity}/     
Add invitation for group forming: /matchmaking/request/     

DELETE:    
Leave group for current user: /matchmaking/{idActivity}/     

#### Switchgroup
This feature allows student part of a tutoring group to exchange there class with each other.   

GET:     
Get switchgroup request available for current user: /switchgroup/request/    
Add request for a group change: /switchgroup/request/{idClass}/{tutorat}/    
Accept request for a group change: /switchgroup/accept/{idClass}/{cipRequested}/

#### Maintenance
These are generics functions for maintenance purposes. Only teachers and system administrator have access to these.
    
GET, PUT, POST: /nameOfClass/    
DELETE: /nameOfClass/{nameOfClass}/     

### Contributors   

We were a team of six people in Computer Engineering. Click [here](https://github.com/orgs/S6infoTinderRoulette/people) to view the list.
