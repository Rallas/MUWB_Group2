
// This is example code provided to CSE3310 Fall 2022
// You are free to use as is, or changed, any of the code provided

// Please comply with the licensing requirements for the
// open source packages being used.

// This code is based upon, and derived from the this repository
//            https:/thub.com/TooTallNate/Java-WebSocket/tree/master/src/main/example

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

/*
 * Copyright (c) 2010-2020 Nathan Rajlich
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package com.mycompany.app;

//COMMENTS ON IMPORTS SIGNIFY THAT THE ORIGINAL PROGRAM INCLUDED THIS LIBRARY BUT RECONCILIATION RENDERED IT
//UNUSED. COMMENTS HAVE BEEN USED TO SUPRESS THE WARNINGS, AS THESE LIBRARIES MAY BE USED WHEN FULL FUNCTIONALITY
//REIMPLEMENTED

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.InetSocketAddress;
//import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Collections;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class App extends WebSocketServer {
  // All games currently underway on this server are stored in the vector ActiveGames
  Vector <GameState> ActiveGames = new Vector<GameState>();
  
  int startWager = 500; 
  int GameId = 1;

  public App(int port) {
    super(new InetSocketAddress(port));
  }
  public App(InetSocketAddress address) {
    super(address);
  }
  public App(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
  }

  

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {

    System.out.println(conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    // search for a game needing a player
    GameState G = null;

    for (GameState i : ActiveGames) 
    {
      if (i.participants.size() >= 1 && i.participants.size() < 5) 
      {
        G = i;
        System.out.println("found a match");
      }
    }

    // No matches ? Create a new Game.
    int targetID;
    if (G == null) 
    {
      G = new GameState();
      G.GameId = GameId;
      GameId++;
      // Add the first player
      G.participants.add(new Person(6,startWager,1));   
      targetID = 1; 
      ActiveGames.add(G);
      System.out.println(" creating a new Game");
      G.StartGame(G.participants);
    } 
    else 
    {
      //find first unoccupied ID
      int firstID = G.findFirstUnoccupied(G);
      if(firstID == -1)
      {
        G.participants.add(new Person(6,startWager,G.participants.size()));
        targetID = G.participants.size();
        System.out.println(" not a new game, first empty ID at " + G.participants.size());
      }
      else
      {
        // join an existing game
        
        G.participants.add(new Person(6,startWager,firstID));
        targetID = firstID;
        System.out.println(" not a new game, first empty ID at " + firstID);
      }
      
    }
    System.out.println("G.participants are " + G.participants);
    // create an event to go to only the new player
    ServerEvent E = new ServerEvent();
    E.PlayerId = G.participants.get(targetID).PlayerId;
    E.GameId = G.GameId;            
    System.out.println("sending id " + E.PlayerId);
    // allows the websocket to give us the Game when a message arrives
    conn.setAttachment(G);

    Gson gson = new Gson();
    // Note only send to the single connection
    conn.send(gson.toJson(E));
    System.out.println(gson.toJson(E));

    // The state of the game has changed, so lets send it to everyone
    packageAndBroadcast(G);
  }

  public void startTimers()
  {
    Timer timer = new Timer();
    
    
    timer.scheduleAtFixedRate(new TimerTask() 
    {
      @Override
      public void run()
      {
        for(GameState G : ActiveGames)
        {
          for(Person P: G.participants)
          {
            if(P.type == PlayerType.DEALER || P.type == PlayerType.BOTCHEAT || P.type == PlayerType.BOTHIGH || P.type == PlayerType.BOTLOW || P.type == PlayerType.BOTMID)
            {
              P.TakeTurn(G);
              
            }
            if((G.CurrentTurn == P.PlayerId) && P.type == PlayerType.PLAYER)
            {
              P.timeOut++;
            }
            if(P.timeOut > 4)
            {
              P.type = PlayerType.BOTHIGH;
              P.agression = 17;
            }
          }
          packageAndBroadcast(G);
        }
        
      }
    }
    ,5*1000, 5*1000);
    
  }
    
  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    System.out.println(conn + " has closed");
    // Retrieve the game tied to the websocket connection
    GameState G = conn.getAttachment();
    G = null;
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out.println(conn + ": " + message);

    // Bring in the data from the webpage
    // A UserEvent is all that is allowed at this point
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    UserEvent U = gson.fromJson(message, UserEvent.class);
    System.out.println(U.Button);

    // Get our Game Object
    GameState G = conn.getAttachment();
    G.Update(U);

    // send out the game state every time
    // to everyone
    packageAndBroadcast(G);
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
    if (conn != null) {
      // some errors like port binding failed may not be assignable to a specific
      // websocket
    }
  }

  @Override
  public void onStart() {
    System.out.println("Server started!");
    setConnectionLostTimeout(0);
  }

  public void packageAndBroadcast(GameState G)
  {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    String jsonString;
    jsonString = gson.toJson(G);

    System.out.println(jsonString);
    broadcast(jsonString);
  }

  public static void main(String[] args) {

    // Set up the http server
    int port = 9082;                                    //updated to the Group 2 ports
    HttpServer H = new HttpServer(port, "./html");
    H.start();
    System.out.println("http Server started on port:" + port);

    // create and start the websocket server

    port = 9882;                                        //updated to the Group 2 ports
    App A = new App(port);
    A.start();
    System.out.println("websocket Server started on port: " + port);

    A.startTimers();
  }
}