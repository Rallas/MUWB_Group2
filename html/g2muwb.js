var idx = -1;
var gameid = -1;
class UserEvent {
    Button = -1;
    PlayerIdx = 0;
    GameId = 0;
}
var connection = null;

var serverUrl;
serverUrl = "ws://" + window.location.hostname + ":9882";               //Updated to the Group2 port
// Create the connection with the server
connection = new WebSocket(serverUrl);

connection.onopen = function (evt) {
    console.log("open");
}
connection.onclose = function (evt) {
    console.log("close");
    document.getElementById("topMessage").innerHTML = "Server Offline"
}
const ButtonStateToDisplay = new Map();         //makes a hashmap with the 1st value being the key
ButtonStateToDisplay.set("DEALER", "X");
ButtonStateToDisplay.set("PLAYER", "O");
ButtonStateToDisplay.set("SPECTATOR", "-");
connection.onmessage = function (evt) {
    var msg;
    msg = evt.data;

    console.log("Message received: " + msg);
    const obj = JSON.parse(msg);                //this makes obj the parsed json string object

    if ('YouAre' in obj) {                  //this means the obj was a ServerEvent 
        if (obj.YouAre == "DEALER") {
            idx = 0;
        }
        else {
            idx = 1;
        }
        gameid = obj.GameId;
        console.log("Made it into YouAre: a ServerEvent was recieved\n")
        console.log(obj)
    }
    else if ('CurrentTurn' in obj) {                //this is for when the sent msg is a Game class object
        // only pay attention to this game
        if (gameid == obj.GameId) {

            console.log("Made it into CurrentTurn: a GameState was recieved\n")
            console.log(obj)

            // button states can be lit up or shaded to display current player turn options

            //document.getElementById("STANDB").value = ButtonStateToDisplay.get(obj.Button[0]);              //This needs to update the display accordingly
            //document.getElementById("HITB").value = ButtonStateToDisplay.get(obj.Button[1]);
            //document.getElementById("SPLITPAIRSB").value = ButtonStateToDisplay.get(obj.Button[2]);
            //document.getElementById("DOUBLEDOWNB").value = ButtonStateToDisplay.get(obj.Button[3]);
            //document.getElementById("SURRENDERB").value = ButtonStateToDisplay.get(obj.Button[4]);

            // the message line. This returns a message to the current player after the turn
            document.getElementById("topMessage").innerHTML = obj.Msg[idx];
        }
    }   
}



function buttonclick(i) {
    U = new UserEvent();
    U.Button = i;               //makes an event to represent the input of a player action
    if (idx == 0)
        U.PlayerIdx = "DEALER";
    else
        U.PlayerIdx = "PLAYER";
    U.GameId = gameid;
    connection.send(JSON.stringify(U));             //Sends Dealer/Player Input to App?
    console.log(JSON.stringify(U))
}
    window.addEventListener("DOMContentLoaded", function()          //This is needed for drawing cards
{
    var image  = document.getElementById("html5");
    var canvas = document.createElement("canvas");
    document.body.appendChild(canvas);

    canvas.width  = image.width;
    canvas.height = image.height;

    var context = canvas.getContext("2d");

    context.drawImage(image, 0, 0);
});