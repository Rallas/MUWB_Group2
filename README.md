# MUWB_Group2
A small, multi-user web-based blackjack (21) game played in the browser

Compile Status: Compiles
                mvn clean
                mvn compile
                mvn package
                mvn exec:java -Dexec.mainClass=com.my.company.app.App        

Class declarations:
    UserEvent:  int GameId;
                Person PlayerIdx;
                int Button;                 these were inherited

    ServerEvent:    Person YouAre;
                    int GameId;
    
    HttpServer:  See File for details - responsible for starting the Server

    App extends WebSocketServer: Handles Server communication & Game logistics
        Notes: - Maintains a vector of game states
                - Updates the gamestate (onMessage) & transmits it via JsonString
                - Starts the HTTP Server & Websocket (App) server
                - Constructor's pass parameter (port or address or port & draft? to WebSocketServer constructor)

        Functions:  main (String[] args)     - Starts the HTTP Server & Websocket (App) server
                    onStart()   
                    onError
                    onMessage(WebSocket conn, String message)   - gets webpage info to update gamestate & transfer it with that string
                    onMessage(WebSocket conn, ByteBuffer message)
                    onClose(WebSocket conn, int code, String reason, boolean remote)
                    onOpen(WebSocket conn, ClientHandshake handshake)

    Card: public Suite suite;
            public Value value;

        Notes: - has enums for both Suite & Value
                - has a random card generator in the constructor (lower limit needs to be removed from both uses or else it will go out of range)

        CardBank: Generates decks per a given hardcoded value. May be merged with Card later. - JM

    Person extends Actions:
            PlayerType type;    -Currently can be: DEALER, PLAYER, SPECTATOR
            Card hand[];      - Hardcoded to an array of 5 atm
            Card splitHand[];
            int wager;
            
            functions: Constructors, set & get wager & a custom gethand() function (returns a String)
    
    Actions: int Deal (Card deck[], Card bank[])
                int Hit (Card deck[], Card bank[])
                int Split (Card deck[], Card splitDeck[])
                int Stand ()
                int Double ()
                int Bet(int wager)
    
    GameState:  Person particpants[];
                Card bank[];
                int GameId;
                public Person CurrentTurn;
                public String[] Msg; 

                int setHand()
                int count(Card deck[])
                int packageAndPrint(Person participants[])              //prints Player & Dealer cards atm
                int StartGame(Person participants[])                   
                //int Update (UserEvent U) needs to be refactored

        PlayerType: an enum of [DEALER, PLAYER, SPECTATOR]; It is likely needed to get a working prototype for now

    

                    


