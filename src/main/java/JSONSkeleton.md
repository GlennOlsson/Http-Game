#Files
### Bots.json
```json
{
"bots" : 
[{
  "token" : "BOT_TOKEN (id)",
  "nickname" : "NICKNAME",
  "email" : "EMAIL",
  "creation" : "TIME_OF_CREATION (dd-mm-yyyy hh:mm)",
  "loginCount" : "NUMBER_OF_TIMES_LOGGED_IN (as number)"
}]
}
```

### Board.json
```json
{
"board" : 
{
 "bots" : 
 [{
   "token" : "BOT_TOKEN (id)",
   "nickname" : "NICKNAME",
   "x" : "X_COORDINATE (as number)",
   "y" : "Y_COORDINATE (as number)",
   "point" : "POINTS (as number)",
   "nextMove" : "NEXT_TIME_MOVEMENT_MILLIS (as number)"
 }],
 "points" : 
 [{
  "x" : "X_COORDINATE (as number)",
  "y" : "Y_COORDINATE (as number)"
 }]
  
  
  
}
}
```
# HTTP
## /bot
```json
{
  "name" : "NAME_FOR_BOT",
  "email" : "YOUR_EMAIL"
}
```



















