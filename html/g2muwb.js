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
var dealer_cards_generated = 0;

connection.onmessage = function (evt) {             //message reciever
    var msg;
    msg = evt.data;
    console.log("Message received: " + msg);
    const obj = JSON.parse(msg);                //this makes obj the parsed json string object

    if(!('CurrentTurn' in obj)){                  //this means the obj was a Server Event 
        
        if (PlayerId != 5){
            PlayerId = obj.PlayerId;
        }      
        gameid = obj.GameId;
        console.log("A ServerEvent was recieved && you are now Player: " + PlayerId + " in a game\n")
    }
    else if ('CurrentTurn' in obj)                 //this is for when the sent msg is a Game class object
    {   console.log("A GameState was recieved: " + obj + "\n")
 
        clearPrevCards();

        if (gameid == obj.GameId)                     // only pay attention to this game
        {      
            for (const player of obj.participants)             // process the game state
            {                   
                if (player.PlayerId == 5)           //Dealer card image generation sequence
                {  
                    console.log("\nEntered DEALER CARD GEN ROUTINE\n");
                    dealer_cards_generated = 0;

                    for(const hand of player.hand)
                    {   j = 0;

                        for(const card in hand.deck)             //See Person Card generation sequence below for detailed explanation
                        {                      
                            hand_index_count = hand.deck[card];

                            if (hand_index_count > 0)     //NOTE: HARCODED FOR TESTING PURPOSES
                            {    

                                while(hand_index_count > 0)
                                {
                                    var filename = j + ".svg";

                                    if (dealer_cards_generated == 1 && obj.CurrentTurn == 1)            //Makes Dealers second card face down but only on first turn
                                    {
                                        filename = "BLACK_BACK_CARD.svg";
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
                                    }
                                    else{
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
                                    }
                                    hand_index_count--;
                                    dealer_cards_generated++;
                                }
                                j++;
                            }
                            else{
                                j++;
                            }
                        }
                    }
                    document.getElementById("topMessage").innerHTML = obj.Msg[player.PlayerId]; // the message line. This returns a message to the current player
                    var winnings_info = document.querySelector("#winning");
                    winnings_info.innerHTML = obj.piggybank;                                               //to represent the DEALERS worth
                }                       //<- marks the end of the dealer card drawing routine
                else if (player.PlayerId != 5)       //shows cards for our player & maps the other players cards to the side view 
                { 
                    if (player.PlayerId == PlayerId)   //What follows is the card generation sequence for OUR PLAYER
                    {
                        TimerUpdate(player);                    //draws the current time left for OUR PLAYER
<<<<<<< Updated upstream
                        for (const hand of player.hand)
                        {                            //for going through each hand
                            ClearMainCards();
                            
                            console.log("The hand is: " + hand.deck);
                            for(let z = player.currentDepth; z <= player.splitDepth; z++)   //Adds dividers when necessary (Split Hands)
                            {   
                                i = 0;
                                for(const card in hand.deck)    //goes through each card per deck in each hand
                                {          
                                    hand_index_count = hand.deck[card]    //copies array index count value to HIC so it can be decremented in the case of duplicates
                                    console.log("FOR CARD IN HAND.DECK: The hand_i_count is: " + hand_index_count + " for i value: " + i);
=======

                        for (const hand of player.hand)
                        {                             //for going through each hand
                            console.log("The hand is: " + hand.deck);
                            ClearOurPlayersCard();
                            for(let z = 0; z <= player.splitDepth; z++)   //Adds dividers when necessary (Split Hands)
                            {   i = 0;
                                ClearOurPlayersCard();


                                for(const card in hand.deck)    //goes through each card per deck in each hand
                                {          
                                    hand_index_count = hand.deck[card]    //copies array index count value to HIC so it can be decremented in the case of duplicates
                                    console.log("FOR CARD IN HAND.DECK: The hand_i_count is: " + hand_index_count + " for i value: " + i + " & Z is: " + z);
>>>>>>> Stashed changes

                                    if (hand_index_count > 0)  //if the index count is < 0 we have a card of this type & need to print it
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
<<<<<<< Updated upstream
                                            console.log("FOR CARD IN HAND.DECK, WHILE: The hand_i_count is: " + hand_index_count + " for i value: " + i);
=======
                                            console.log("FOR CARD IN HAND.DECK, WHILE: The hand_i_count is: " + hand_index_count + " for i value: " + i + " AND Z is: " + z);
>>>>>>> Stashed changes
                                            hand_index_count--; //lowers the index by 1 each time a card is printed
                                        }
                                        i++;            //the cycle var should be incremented every time 1 type of card is printed
                                    }
                                    else{                   
                                        i++;                //for when i isn't greater than 0
                                    }
                                }         //this ends the loop for generating player cards

<<<<<<< Updated upstream
                                var Our_Player_Hash_info = document.querySelector("#PlayersHashInfo");
                                Our_Player_Hash_info.innerHTML = 5; //player.hand.hand_total; 
=======
                               /* if (z > 0){
                                    var filename = "Hand_Bar.svg";                                  //Graphic Generation sequcnce for hand dividers
                                    var PlayerMapId = "P" + PlayerId + "_Map";                 

                                    var Hand_Divider = document.createElement("img");           //draws our cards for the main display
                                    Hand_Divider.setAttribute("src", filename); 
                                    Hand_Divider.setAttribute("class", "PlayersCards_divider");         
                                    const MainParent_4_Divider = document.getElementById("PlayersCards_Generated_Here");
                                    MainParent_4_Divider.appendChild(Hand_Divider);

                                    var Map_4_Player_Divider = document.createElement("img");            //This handles drawing the divider to the side map
                                    Map_4_Player_Divider.setAttribute("src", filename);
                                    Map_4_Player_Divider.setAttribute("class", "Game_Play_Map_Cards_divider");

                                    const MapParent_4_Our_Player = document.getElementById(PlayerMapId);        
                                    MapParent_4_Our_Player.appendChild(Map_4_Player_Divider);
                                }*/
>>>>>>> Stashed changes
                                document.getElementById("Bet").innerHTML = player.wagers[z]; // shows the player their hands worth
                            }
                        }         
                        document.getElementById("topMessage").innerHTML = obj.Msg[PlayerId]; // the message line. This returns a message to the current player after the turn. Goes w/ GameID check
                        var winnings_info = document.querySelector("#winning");
                        winnings_info.innerHTML = player.winnings;
                    }
                    else if (player.PlayerId != 5 && player.PlayerId != PlayerId)   //draws images for other players on our players side Map to show their hands
                    {
                        for (const hand of player.hand)
                        {
                            k = 0;

<<<<<<< Updated upstream
                            for(let z = 0; z <= player.splitDepth; z++)           //Adds dividers when necessary (Split Hands)
=======
                            for(let z = player.currentDepth; z <= player.splitDepth; z++)           //Adds dividers when necessary (Split Hands)
>>>>>>> Stashed changes
                            {
                                for(const card in hand.deck) 
                                {   
                                    hand_index_count = hand.deck[card];

                                    if (hand_index_count > 0)          
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
                                        }
                                        k++;
                                    }
                                    else{
                                        k++;
                                    }
                                }
                                /*if (z > 0){
                                    var filename = "Hand_Bar.svg";                                  //Graphic Generation sequcnce for hand dividers
                                    var PlayerMapId = "P" + PlayerId + "_Map";                 

                                    var Map_4_Player_Divider_others = document.createElement("img");            //This handles drawing the divider to the side map
                                    Map_4_Player_Divider_others.setAttribute("src", filename);
                                    Map_4_Player_Divider_others.setAttribute("class", "Game_Play_Map_Cards_divider");

                                    const MapParent_4_Others = document.getElementById(PlayerMapId);
                                    MapParent_4_Others.appendChild(Map_4_Player_Divider_others);
                                }*/
                            }   
                        }       
                    }               // <- for the end of the else branch to generate a players side cards on our PLAYERS display
                }              // <- indicates the end of the player card generation sequence       
            }               // <- is for the end of cycling through the vector of players in our game
        }                  // <- is for the end of the subroutine for this specific game (it ignores the other games)
    }              // <- is for the end of the game process sub routine  
}           // <- is for the end of the onconnection function


function buttonclick(i) {
    U = new UserEvent();            //makes an event to represent the input of a player action
    U.PlayerId = PlayerId;
    U.GameId = gameid;
    if (i >= 0){
        U.Event = "BET";
        U.Button = document.getElementById("sendBet").value;
    }
    else{
        U.Button = i;   
    }
    connection.send(JSON.stringify(U));             //Sends Dealer/Player Input to App?
    console.log(JSON.stringify(U));
}

function showBet(){
    var betInfo = document.getElementById("sendBet").value;
    buttonclick(betInfo);
}

function clearPrevCards(obj){

    var clear1 = document.getElementById("P1_Map");         //Clears OUR PLAYER CARD IMAGES  
    var clear2 = document.getElementById("P2_Map");       
    var clear3 = document.getElementById("P3_Map");       
    var clear4 = document.getElementById("P0_Map");       
    var clear5 = document.getElementById("DealerMap");       

    if (clear1 != null && clear2 != null && clear3 != null && clear4 != null && clear5 != null)
    {
    clear1.innerHTML = "";
    clear2.innerHTML = "";
    clear3.innerHTML = "";
    clear4.innerHTML = "";
    clear5.innerHTML = "";
    }

    var clear6 = document.getElementById("DealersCards_Generated_Here");         //Clears DEALER CARD MAIN DISPLAY IMAGES
    var clear7 = document.getElementById("PlayersCards_Generated_Here");         //Clears OUR PLAYER CARD MAIN DISPLAY IMAGES    

    if (clear6 != null && clear7 != null)
    {
    clear6.innerHTML = "";
    clear7.innerHTML = "";
    }

    var clear8 = document.getElementById("countdown");          //Clears the previous timer indication
    if (clear8 != null){
        clear8.innerHTML = "";
    }
}

<<<<<<< Updated upstream
function ClearMainCards(){
    var clear8 = document.getElementById("PlayersCards_Generated_Here");         //Clears OUR PLAYER CARD MAIN DISPLAY IMAGES    
    var clear9 = document.getElementById("P0_Map");       

    if (clear8 != null && clear9 != null)
    {
        clear8.innerHTML = "";
        clear9.innerHTML = "";
=======
function ClearOurPlayersCard(){
    var clear9 = document.getElementById("PlayersCards_Generated_Here");         //Clears OUR PLAYER CARD MAIN DISPLAY IMAGES    
    var clear10 = document.getElementById("P0_Map");
    if (clear9 != null && clear10 != null)
    {
    clear9.innerHTML = "";
    clear10.innerHTML = "";
>>>>>>> Stashed changes
    }
}

function TimerUpdate(player){
    var Timer_Info = document.createElement("p");            //This handles drawing the divider to the side map
    Timer_Info.setAttribute("id", "Timer");

    var c = player.timeOut;

    if (c <= 10){
        Timer_Info.innerHTML = "You have " + (20 - player.timeOut * 2) + " seconds remaining";          //20 is a hardcoded value based on Game Logic
    }
    else{
        Timer_Info.innerHTML = "And you're a bot now. Nice";         
    }

    const Timer_Parent = document.getElementById("countdown");
    Timer_Parent.appendChild(Timer_Info);
}


                                /*if (z > 0){
                                    var filename = "Hand_Bar.svg";                                  //Graphic Generation sequcnce for hand dividers
                                    var PlayerMapId = "P" + PlayerId + "_Map";                 

                                    var Hand_Divider = document.createElement("img");           //draws our cards for the main display
                                    Hand_Divider.setAttribute("src", filename); 
                                    Hand_Divider.setAttribute("class", "PlayersCards_divider");         
                                    const MainParent_4_Divider = document.getElementById("PlayersCards_Generated_Here");
                                    MainParent_4_Divider.appendChild(Hand_Divider);

                                    var Map_4_Player_Divider = document.createElement("img");            //This handles drawing the divider to the side map
                                    Map_4_Player_Divider.setAttribute("src", filename);
                                    Map_4_Player_Divider.setAttribute("class", "Game_Play_Map_Cards_divider");

                                    const MapParent_4_Our_Player = document.getElementById(PlayerMapId);        
                                    MapParent_4_Our_Player.appendChild(Map_4_Player_Divider);
                                }*/