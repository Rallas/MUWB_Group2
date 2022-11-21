var PlayerId = -1;
var gameid = -1;
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
var hand_index_count = 0;
var cards_generated = 0;
var player_card_count = 0;
var dealer_card_count = 0;

const UserTypeEventMap = new Map();
UserTypeEventMap.set(-1, "DEAL");           //Only needed if we have time to switch to User Events w/ an ENUM setup for the events
UserTypeEventMap.set(0, "STAND");
UserTypeEventMap.set(1, "HIT");
UserTypeEventMap.set(2, "SPLIT");
UserTypeEventMap.set(3, "DOUBLE");
UserTypeEventMap.set(-6, "SURRENDER");

connection.onmessage = function (evt) {             //message reciever
    var msg;
    msg = evt.data;
    console.log("Message received: " + msg);
    const obj = JSON.parse(msg);                //this makes obj the parsed json string object

    if('PlayerId' in obj){                  //this means the obj was a Server Event 
        
        PlayerId = obj.PlayerId;
        gameid = obj.GameId;
        console.log("A ServerEvent was recieved & you are now Player: " + PlayerId + " in a game\n")
    }
    else if ('CurrentTurn' in obj)                 //this is for when the sent msg is a Game class object
    {    
        if (gameid == obj.GameId)                     // only pay attention to this game
        {   console.log("A GameState was recieved: " + obj + "\n")     

           /* for (button of obj.Button){ //           button states can be lit up or shaded to display current player turn options (Low level Priority atm)     
                if (button == 1){                           //May be scrapped due to time constraints
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
            }*/

            for (const player of obj.participantsDupe) {             // process the game state
               
                if (player.PlayerId != 0)       //shows cards for our player & maps the other players cards to the side view 
                { 
                    if (player.PlayerId == PlayerId)
                    {
                        for (var hand of player.handDupe){
                            i = 0;
                            player_card_count = 0;

                            for(const card in player.hand.deck) 
                            {   hand_index_count = card;

                                if (hand_index_count > 0)              //NOTE: HARDCODED for testing purposes
                                {
                                    while(hand_index_count > 0){
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

                                        player_card_count++;
                                        hand_index_count--;
                                    }
                                    i++;            //i is used to cycle through the deck array
                                }                   
                                i++;
                            }                   //this ends the loop for generating player cards
                        }         
                        document.getElementById("topMessage").innerHTML = obj.Msg[PlayerId]; // the message line. This returns a message to the current player after the turn. Goes w/ GameID check
                        var winnings_info = document.querySelector("#winning");
                        winnings_info.innerHTML = player.winnings;
                    }
                    else if (player.PlayerId != PlayerId)           //draws images for other players on our players side Map to show their hands
                    {
                        for (var hand of player.handDupe)
                        {
                            i = 0;
                            player_card_count = 0;

                            for(const card in player.hand.deck) 
                            {   hand_index_count = card;

                                if (hand_index_count > 0)          //NOTE: HARDCODED for testing purposes
                                {
                                    while(hand_index_count > 0)
                                    {
                                        var filename = i + ".svg";
                                        var element = "card" + (i + 1);
                                        var PlayerMapId = "P" + PlayerId + "_Map"; 

                                        var img = document.createElement("img");            //This if & else branch are responsible for drawing game state cards
                                        img.setAttribute("src", filename);
                                        img.setAttribute("class", "Game_Play_Map_Cards");

                                        const MapParenti = document.getElementById(PlayerMapId);
                                        MapParenti.appendChild(img);

                                        player_card_count++;
                                        hand_index_count--;
                                    }
                                    i++;
                                }
                                i++;
                            }
                        }
                    }
                }              //This } indicates the end of the player card generation sequence       
                else 
                {   j = 0;
                    dealer_card_count = 0;                  //Dealer card image generation sequence
                    
                    for(const card in player.hand.deck)
                    {    hand_index_count = card;
                       
                        if (hand_index_count > 0)     //NOTE: HARCODED FOR TESTING PURPOSES
                        {    
                            while(hand_index_count > 0)
                            {
                                var filename = j + ".svg";
                                var element = "card" + (j + 1);

                                var img = document.createElement("img");
                                img.setAttribute("src", filename);
                                img.setAttribute("class", "DealersCards");
                                const parent = document.getElementById("DealersCards_Generated_Here");
                                parent.appendChild(img);
                
                                var img_for_map = document.createElement("img");            //This else subroutine draws the dealers cards
                                img_for_map.setAttribute("src", filename);
                                img_for_map.setAttribute("class", "Game_Play_Map_Cards");

                                const MapParent = document.getElementById("DealerMap");
                                MapParent.appendChild(img_for_map);

                                dealer_card_count++;
                                hand_index_count--;
                            }
                            j++;
                        }
                        j++;
                    }
                    document.getElementById("topMessage").innerHTML = obj.Msg[PlayerId];
                    var winnings_info = document.querySelector("#winning");
                    winnings_info.innerHTML = obj.piggybank;                                                //to represent the dealers worth
                }                       //} marks the end of the dealer card drawing routine
            }                    //} is for the end of cyclying through the vector of players in our game
        }                  //} is for the end of the subroutine for this specific game (it ignores the other games)
    }              //} is for the end of the game process sub routine
}           //} is for the end of the onconnection function


function buttonclick(i) {
    U = new UserEvent();            //makes an event to represent the input of a player action
    U.PlayerId = PlayerId;
    U.GameId = gameid;
    if (i > 0){
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

function showBet(){
    var betInfo = document.getElementById("sendBet").value;
    var betIndicator = document.querySelector("#Bet");
    betIndicator.innerHTML = betInfo;
    buttonclick(betInfo);
}