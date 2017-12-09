### Bots.json
```json
{
"bots" : 
[{
  "id" : "BOT_ID",
  "nickname" : "NICKNAME",
  "email" : "EMAIL",
  "creation" : "TIME_OF_CREATION (dd-mm-yyyy)"
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
   "id" : "BOT_ID",
   "nickname" : "NICKNAME",
   "x" : "X_COORDINATE",
   "y" : "Y_COORDINATE",
   "point" : "POINTS",
   "nextMove" : "NEXT_TIME_MOVEMENT_MILLIS"
 }],
 "points" : 
 [{
  "x" : "X_COORDINATE",
  "y" : "Y_COORDINATE"
 }]
  
  
  
}
}
```