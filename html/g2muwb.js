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
var k = 0;
var hand_index_count = 0;
var cards_generated = 0;
var player_card_count = 0;
var dealer_card_count = 0;
var other_players_card_count = 0;

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

    if(!('CurrentTurn' in obj)){                  //this means the obj was a Server Event 
        
        if (PlayerId != 0){
            PlayerId = obj.PlayerId;
        }
        PlayerId = obj.PlayerId;        
        gameid = obj.GameId;
        console.log("A ServerEvent was recieved && you are now Player: " + PlayerId + " in a game\n")
    }
    else if ('CurrentTurn' in obj)                 //this is for when the sent msg is a Game class object
    {     console.log("A GameState was recieved: " + obj + "\n") 
        if (gameid == obj.GameId)                     // only pay attention to this game
        {      
            for (const player of obj.participants)             // process the game state
            {   
                console.log("A Player id is: " + player.PlayerId + " and is of type: " + player.type);
                
                if (player.PlayerId == 0)
                {  
                    //dealer_card_count = 0;                  //Dealer card image generation sequence
                    console.log("\nEntered DEALER CARD GEN ROUTINE\n");

                    for(const hand of player.hand)
                    {   j = 0;

                        for(const card in hand.deck)             //See Person Card generation sequence below for detailed explanation
                        {                      
                            hand_index_count = hand.deck[card];

                            if (hand_index_count > 0 && dealer_card_count < 9)     //NOTE: HARCODED FOR TESTING PURPOSES
                            {    

                                while(hand_index_count > 0)
                                {
                                    var filename = j + ".svg";

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

                                    hand_index_count--;
                                    dealer_card_count++;
                                }
                                j++;
                            }
                            else{
                                j++;
                            }
                        }
                    }
                        document.getElementById("topMessage").innerHTML = obj.Msg[player.PlayerId];
                        var winnings_info = document.querySelector("#winning");
                        winnings_info.innerHTML = obj.piggybank;                                               //to represent the DEALERS worth
                }                       //<- marks the end of the dealer card drawing routine
                else if (player.PlayerId != 0)       //shows cards for our player & maps the other players cards to the side view 
                { 
                    if (player.PlayerId == PlayerId && player_card_count < 9)   //worked w/ == 1. What follows is the card generation sequence for OUR PLAYER
                    {
                        //player_card_count = 0;        //for keeping track of the total number of player cards (needs to be included or else will take on junk value (2))

                        for (const hand of player.hand)
                        {    i = 0;                         //for going through each hand
                         
                            for(const card in hand.deck)    //goes through each card per deck in each hand
                            {          
                                hand_index_count = hand.deck[card]    //copies array index count value to HIC so it can be decremented in the case of duplicates

                                if (hand_index_count > 0 && player_card_count < 9)  //if the index count is < 0 we have a card of this type & need to print it. NOTE: Player_card_count is TEMP HARDCODED til we get a var for the card count per deck
                                {   
                                    
                                    while(hand_index_count > 0)//repeats to deal w/ multiples of 1 type of card
                                    {   
                                        var filename = i + ".svg";                                  //Card graphics are numbered from 0 - 51
                                        var PlayerMapId = "P" + PlayerId + "_Map";                 

                                        var img_4_player = document.createElement("img");            //creates a new graphic for each card
                                        img_4_player.setAttribute("src", filename); 
                                        img_4_player.setAttribute("class", "PlayersCards");         
                                        const MainParent = document.getElementById("PlayersCards_Generated_Here");
                                        MainParent.appendChild(img_4_player);

                                        var img_for_map_4_player = document.createElement("img");            //This handles drawing each card to the side map
                                        img_for_map_4_player.setAttribute("src", filename);
                                        img_for_map_4_player.setAttribute("class", "Game_Play_Map_Cards");

                                        const MapParent_4_player = document.getElementById(PlayerMapId);
                                        MapParent_4_player.appendChild(img_for_map_4_player);

                                        hand_index_count--; //lowers the index by 1 each time a card is printed
                                        player_card_count++;    //increased to reflect that a card has been added to the players hand graphically
                                    }
                                    i++;            //the cycle var should be incremented every time 1 type of card is printed
                                }
                                else{                   
                                    i++;                //for when i isn't greater than 0
                                }
                            }         //this ends the loop for generating player cards
                        }         
                        document.getElementById("topMessage").innerHTML = obj.Msg[PlayerId]; // the message line. This returns a message to the current player after the turn. Goes w/ GameID check
                        var winnings_info = document.querySelector("#winning");
                        winnings_info.innerHTML = player.winnings;
                    }
                    else if ((player.PlayerId != 0 || player.PlayerId == PlayerId) && other_players_card_count < 9)    //draws images for other players on our players side Map to show their hands
                    {
                        other_players_card_count = 0;
                        for (const hand of player.hand)
                        {
                            k = 0;

                            for(const card in hand.deck) 
                            {   hand_index_count = hand.deck[card];

                                if (hand_index_count > 0 && other_players_card_count < 9)          //NOTE: OPTC < 5 HARDCODED til a Var for hand size is implemented in Java
                                {

                                    while(hand_index_count > 0)
                                    {
                                        var filename_side_map = k + ".svg";
                                        var PlayerMapId = "P" + player.PlayerId + "_Map"; 

                                        var img_wrt_others = document.createElement("img");            //This if & else branch are responsible for drawing game state cards
                                        img_wrt_others.setAttribute("src", filename_side_map);
                                        img_wrt_others.setAttribute("class", "Game_Play_Map_Cards");

                                        const MapParenti = document.getElementById(PlayerMapId);
                                        MapParenti.appendChild(img_wrt_others);

                                        hand_index_count--;
                                        other_players_card_count++;
                                    }
                                    k++;
                                }
                                else{
                                    k++;
                                }
                            }   
                        }       
                    }               // <- for the end of the else branch to generate a players side cards on our PLAYERS display
                }              // <- indicates the end of the player card generation sequence       
            }               // <- is for the end of cycling through the vector of players in our game
        }                  // <- is for the end of the subroutine for this specific game (it ignores the other games)
    }              // <- is for the end of the game process sub routine  
}           // <- is for the end of the onconnection function


function buttonclick(i) {
    clearPrevCards();
    U = new UserEvent();            //makes an event to represent the input of a player action
    U.PlayerId = PlayerId;
    U.GameId = gameid;
    if (i > 0){
        U.Event = "BET";
        U.Button = document.getElementById("sendBet").value;
    }
    else if (i == -6) {
        console.log("Cards should be cleared");
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

function clearPrevCards(){
    var clear5 = document.getElementById("P2_Map");         //Clears OUR PLAYER CARD IMAGES    
    clear5.innerHTML = "";
    var clear6 = document.getElementById("P3_Map");       
    clear6.innerHTML = "";
    var clear7 = document.getElementById("P4_Map");       
    clear7.innerHTML = "";
    var clear8 = document.getElementById("P1_Map");       
    clear8.innerHTML = "";

    var clear = document.getElementById("DealersCards_Generated_Here");         //Clears DEALER CARD IMAGES
    clear.innerHTML = "";
    var clear2 = document.getElementById("DealerMap");       
    clear2.innerHTML = "";

    var clear3 = document.getElementById("PlayersCards_Generated_Here");         //Clears OUR PLAYER CARD IMAGES    
    clear3.innerHTML = "";
    var clear4 = document.getElementById("Game_Play_Map_Cards");       
    clear4.innerHTML = ""
}