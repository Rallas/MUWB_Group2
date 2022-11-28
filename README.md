# MUWB_Group2
A small, multi-user web-based blackjack (21) game played in the browser

To deploy locally:
                mvn clean
                mvn compile
                mvn package
                mvn exec:java -Dexec.mainClass=com.mycompany.app.App        
  
                Go to localhost:9082/   in a browser. Note you may need to clear your cache upon repeated loads
                
To deploy to Webpoker.info/:
    
              1) Change the terminal to the project folder if necessary
              
              2) Enter: bash scripts/deploy.bash group2
              
              3) Group 2 Username: fa2022_group2; Enter the Group 2 password: (https://uta.instructure.com/courses/123495/pages/deployment-information?module_item_id=5368807)
                    
                 Note: this should take a few minutes to complete & may seem to time-out at times


