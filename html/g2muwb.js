var PlayerId = -1;
var GameId = -1;
class UserEvent {  
    Button = -1;
    PlayerId = 0;
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
var i = 0;
var j = 0;
var player_card_count = 0;
var dealer_card_count = 0;
const UserTypeEventMap = new Map();
UserTypeEventMap.set(-1, "DEAL");           //these need to be updated in the GameState switch
UserTypeEventMap.set(-2, "STAND");
UserTypeEventMap.set(-3, "HIT");
UserTypeEventMap.set(-4, "SPLIT");
UserTypeEventMap.set(-5, "DOUBLE");
UserTypeEventMap.set(-6, "SURRENDER");



const valid_choices = new Map();
valid_choices.set(0, "Not Appropriate");
valid_choices.set(1, "Valid Option");


var TimeLeft = 60;
//This is for the countdown which should probably be for the turns
var Timer = setInterval(function(){
 if (TimeLeft <= 0){
     clearInterval(Timer);
     //window.alert("AND your outta Time! Thanks for Playing & Buh Bye");
     //connection.close(); This is annoying to deal w/ after a while
 }
 else{
     document.getElementById("countdown").innerHTML = TimeLeft + " seconds left in this turn"
 }
 TimeLeft = TimeLeft - 1;
}, 1000);

cards_generated = 0;

connection.onmessage = function (evt) {             //message reciever
    var msg;
    msg = evt.data;
    //active = false;
    console.log("Message received: " + msg);
    const obj = JSON.parse(msg);                //this makes obj the parsed json string object

    if(!('Turn_Cycle' in obj)){                  //this means the obj was a ServerEvent 
        if (obj.PlayerId == "0") {
            PlayerId = 0;
        }
        else {
            PlayerId = obj.PlayerId;
        }

        gameid = obj.GameId;
        console.log("A ServerEvent was recieved & you are now Player: " + PlayerId + " in a game\n")
    }
    else if ('CurrentTurn' in obj) {                //this is for when the sent msg is a Game class object
        // only pay attention to this game
        if (gameid == obj.GameId) {
            var k = 0;
            TimeLeft = 60;  
            console.log("A GameState was recieved: " + obj + "\n")

            // button states can be lit up or shaded to display current player turn options
            for (button of obj.Button){    
                if (button == 1){
                    var Button_ID = "BUTTON_" + k;
                    var Btn = document.getElementById(Button_ID);
                    Btn.style.backgroundColor = 'lightgreen';
                    k++;
                }
                else{
                    var Button_ID = "BUTTON_" + k;
                    var Btn = document.getElementById(Button_ID);
                    Btn.style.backgroundColor = "#841311" ;
                    k++;
                }
            }

            // process the game state
            for (const player of obj.participants) {
               
                if (player.PlayerId != 0) {                //shows the cards for all players

                    if (player.PlayerId == PlayerId){               //shows cards for our Player            
                        i = 0;
                        cards_generated = 0;
                        //for (const hand of player.hand) {  //NOTE: needed for vector hand implementation
                            for(const card of player.hand.deck) {       //cycles through the hand for our Player
                                cardcount = card;
                                
                                if (cardcount > 0 && cards_generated < player.hand.num_of_cards){

                                        while(cardcount > 0)
                                        {
                                        var filename = i + ".svg";
                                        var element = "card" + (i + 1);
                                        var PlayerMapId = "P" + PlayerId + "_Map"; 

                                        var img = document.createElement("img");            //This if & else branch are responsible for drawing game state cards
                                        img.setAttribute("src", filename);
                                        img.setAttribute("class", "PlayersCards");
                                        const MainParent = document.getElementById("PlayersCards_Generated_Here");
                                        MainParent.appendChild(img);

                                        var img_for_map = document.createElement("img");            //This if & else branch are responsible for drawing game state cards
                                        img_for_map.setAttribute("src", filename);
                                        img_for_map.setAttribute("class", "Game_Play_Map_Cards");

                                        const MapParent = document.getElementById(PlayerMapId);
                                        MapParent.appendChild(img_for_map);
                                        
                                        cardcount--;
                                        cards_generated++;
                                        }
                                        i++;
                                }
                                i++;
                            }  // each card         
                        // } NEEDED for vector hand implementation
                            TimeLeft = 60;
                            Timer = setInterval(function(){
                                if (TimeLeft <= 0){
                                    clearInterval(Timer);
                                    //window.alert("AND your outta Time! Thanks for Playing & Buh Bye");
                                    //connection.close(); This is annoying to deal w/ after a while
                                }
                                else{
                                    document.getElementById("countdown").innerHTML = TimeLeft + " seconds left in this turn"
                                }
                                TimeLeft = TimeLeft - 1;
                            }, 1000);

                            document.getElementById("topMessage").innerHTML = obj.Msg[PlayerId]; // the message line. This returns a message to the current player after the turn. Goes w/ GameID check
                            var winnings_info = document.querySelector("#winning");
                            winnings_info.innerHTML = player.winnings;
                    }
                    else if (player.PlayerId != PlayerId){      
                        i = 0;
                        cards_generated = 0;
                        //for (const hand of player.hand) {  //NOTE: needed for vector hand implementation
                        for(const card of player.hand.deck) {
                            cardcount = card;
                                
                                    if (cardcount > 0 && cards_generated < player.hand.num_of_cards){
                                        while(cardcount > 0){
                                            var filename = i + ".svg";
                                            var element = "card" + (i + 1);
                                            var PlayerMapId = "P" + PlayerId + "_Map"; 

                                            var img = document.createElement("img");            //This if & else branch are responsible for drawing game state cards
                                            img.setAttribute("src", filename);
                                            img.setAttribute("class", "Game_Play_Map_Cards");

                                            const MapParenti = document.getElementById(PlayerMapId);
                                            MapParenti.appendChild(img);

                                            cardcount--;
                                            cards_generated++;
                                        }
                                        i++;
                                    }
                                    i++;
                                }
                        
                            }
                }       
                else {
                    j = 0;
                    cards_generated = 0;
                    //for (const hand of player.hand // NOTE: needed for vector hand implementation
                    for(const card of player.hand.deck) {
                        cardcount = card;
                        if (cardcount > 0 && cards_generated < player.hand.num_of_cards){
                           while(cardcount > 0){
                            var filename = j + ".svg";
                            var element = "card" + (j + 1);

                            var img = document.createElement("img");
                            img.setAttribute("src", filename);
                            img.setAttribute("class", "DealersCards");
                            const parent = document.getElementById("DealersCards_Generated_Here");
                            parent.appendChild(img);
            
                            var img_for_map = document.createElement("img");            //This if & else branch are responsible for drawing game state cards
                            img_for_map.setAttribute("src", filename);
                            img_for_map.setAttribute("class", "Game_Play_Map_Cards");

                            const MapParent = document.getElementById("DealerMap");
                            MapParent.appendChild(img_for_map);
                            
                            cardcount--;
                            cards_generated++;
                            }
                            j++;
                        }
                        j++;
                    }
                    document.getElementById("topMessage").innerHTML = obj.Msg[PlayerId];
                    var winnings_info = document.querySelector("#winning");
                    winnings_info.innerHTML = player.winnings;
                } 
            } // each player
        } // this is game state
        Timer_Stop(Timer);
    }
}


function buttonclick(i) {
    Timer_Stop(Timer);
    TimeLeft = 240;
    U = new UserEvent();            //makes an event to represent the input of a player action
    U.PlayerId = PlayerId;
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
function Timer_Fun(TimeLeft){
    if (TimeLeft <= 0){
        clearInterval(Timer);
        //window.alert("AND your outta Time! Thanks for Playing & Buh Bye");
        //connection.close(); This is annoying to deal w/ after a while
    }
    else{
        document.getElementById("countdown").innerHTML = TimeLeft + " seconds left in this turn"
    }
    TimeLeft = TimeLeft - 1;
}

function Timer_Stop(Timer){
    clearInterval(Timer);
}