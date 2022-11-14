var idx = -1;
var gameid = -1;
class UserEvent {  
    Button = -1;
    PlayerIdx = 0;
    GameId = 0;
    Event = "N/A"
}
var connection = null;

var serverUrl;
serverUrl = "ws://" + window.location.hostname + ":9882";               //Updated to the Group2 port
connection = new WebSocket(serverUrl);                  // Create the connection with the server

connection.onopen = function (evt) {
    console.log("open");
}
connection.onclose = function (evt) {
    console.log("close");
    document.getElementById("topMessage").innerHTML = "Server Offline"
}

const cards = new Map();
cards.set(0, "0");         //these are needed to generate cards
cards.set(1, "1");
cards.set(2, "2");
cards.set(3, "3");
cards.set(4, "4");
cards.set(5, "5");
cards.set(6, "6");
cards.set(7, "7");
cards.set(8, "8");
cards.set(9, "9");
cards.set(10, "10");
cards.set(11, "11");
cards.set(12, "12");
cards.set(13, "13");
cards.set(14, "14");
cards.set(15, "15");
cards.set(16, "16");
cards.set(17, "17");
cards.set(18, "18");
cards.set(19, "19");
cards.set(20, "20");
cards.set(21, "21");
cards.set(22, "22");
cards.set(23, "23");
cards.set(24, "24");
cards.set(25, "25");
cards.set(26, "26");
cards.set(27, "27");
cards.set(28, "28");
cards.set(29, "29");
cards.set(30, "30");
cards.set(31, "31");
cards.set(32, "32");
cards.set(33, "33");
cards.set(34, "34");
cards.set(35, "35");
cards.set(36, "36");
cards.set(37, "37");
cards.set(38, "38");
cards.set(39, "39");
cards.set(40, "40");
cards.set(41, "41");
cards.set(42, "42");
cards.set(43, "43");
cards.set(44, "44");
cards.set(45, "45");
cards.set(46, "46");
cards.set(47, "47");
cards.set(48, "48");
cards.set(49, "49");
cards.set(50, "50");
cards.set(51, "51");

const UserTypeEventMap = new Map();
UserTypeEventMap.set(-1, "DEAL");
UserTypeEventMap.set(0, "STAND");
UserTypeEventMap.set(1, "HIT");
UserTypeEventMap.set(2, "SPLIT");
UserTypeEventMap.set(3, "DOUBLE");
UserTypeEventMap.set(4, "SURRENDER");



const valid_choices = new Map();
valid_choices.set(0, "Not Appropriate");
valid_choices.set(1, "Valid Option");

connection.onmessage = function (evt) {             //message reciever
    var msg;
    msg = evt.data;

    console.log("Message received: " + msg);
    const obj = JSON.parse(msg);                //this makes obj the parsed json string object

    if('YouAre' in obj) {                  //this means the obj was a ServerEvent 
        if (obj.YouAre == "DEALER") {
            idx = 0;
        }
        else {
            idx = 1;
        }

        gameid = obj.GameId;
        console.log("Made it into YouAre: a ServerEvent was recieved & the player is now in a game\n")
    }
    else if ('CurrentTurn' in obj) {                //this is for when the sent msg is a Game class object
        // only pay attention to this game
        if (gameid == obj.GameId) {

            console.log("Made it into CurrentTurn: a GameState was recieved\n")

            // button states can be lit up or shaded to display current player turn options
            document.getElementById("STAND").value = valid_choices.get(obj.Button[0]);              //This needs to update the display accordingly
            document.getElementById("HIT").value = valid_choices.get(obj.Button[1]);
            document.getElementById("SPLITPAIRS").value = valid_choices.get(obj.Button[2]);
            document.getElementById("DOUBLEDOWN").value = valid_choices.get(obj.Button[3]);
            document.getElementById("SURRENDER").value = valid_choices.get(obj.Button[4]);

            // process the game state
            for (const player of obj.participants) {
                var i = 0;

                // only show the cards for this player
                if (player.type != "DEALER") { //was orig player.id

                    

                    for(const card of player.hand) {
                        //console.log("Card =" + card.suite + "  " + card.value);
                        var filename = cards.get(card) + ".svg";
                        var element = "card" + (i + 1);

                        var img = document.createElement("img");            //This if & else branch are responsible for drawing game state cards
                        img.setAttribute("src", filename);
                        img.setAttribute("class", card);
                        const parent = document.getElementById("PlayersCards");

                        parent.appendChild(img);
                        i++;
                    }  // each card
                        
                        document.getElementById("topMessage").innerHTML = obj.Msg[idx]; // the message line. This returns a message to the current player after the turn. Goes w/ GameID check
                        var winnings_info = document.querySelector("#winning");
                        winnings_info.innerHTML = player.winnings;
                    }       
                else {
                    for(const card of player.hand) {
                        var filename = cards.get(card) + ".svg";
                        var element = "card" + (i + 1);

                        var img = document.createElement("img");
                        img.setAttribute("src", filename);
                        img.setAttribute("class", card);
                        const parent = document.getElementById("DealersCards");

                        parent.appendChild(img);
                        i++;
                    }  
                        
                        document.getElementById("topMessage").innerHTML = obj.Msg[idx];
                        var winnings_info = document.querySelector("#winning");
                        winnings_info.innerHTML = player.winnings;
                    } 
            } // each player
        } // this is game state
    }
}


function buttonclick(i) {
    U = new UserEvent();            //makes an event to represent the input of a player action
    if (idx == 0)
        U.PlayerIdx = "DEALER";
    else
        U.PlayerIdx = "PLAYER";
    U.GameId = gameid;
    if (i > 6){
        U.Event = "BET";
        U.Button = document.getElementById("sendBet").value;
    }
    else{
        U.Event = UserTypeEventMap.get(i);
        U.Button = i;   
    }
    connection.send(JSON.stringify(U));             //Sends Dealer/Player Input to App?
    console.log(JSON.stringify(U));

    var x = document.getElementById("makeBet");                 //hides the send bet interface after send is pressed
    if (x.style.diplay === "none"){
        x.style.display = "block";
    }else{
        x.style.display = "none";
    }
}

var coll = document.getElementsByClassName("collapse");
var i;

for (i = 0; i < coll.length; i++){
    coll[i].addEventListener("click", function(){
        this.classList.toggle("active");
        var content = this.nextElementSibling;
        if (content.style.display === "block"){
            content.style.display = "none";
        }else{
            content.style.display = "block";
        }
    });
}

function showBet(){
    var betInfo = document.getElementById("sendBet").value;
    var betIndicator = document.querySelector("#Bet");
    betIndicator.innerHTML = betInfo;
    buttonclick(betInfo);
}