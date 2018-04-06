import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    private static UserList userList;
    private static LobbyList lobbyList;
    private static User currentUser;
    private static boolean loggedIn;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        System.out.println("------TEST CODE FOR MusicRT------");
        System.out.println("All users have been pre-loaded and there have been some lobbies contructed.");
        System.out.println("To see all info type in 'info' and return.");
        System.out.println("To see a single user's/lobby's info type 'info' followed by the name of the user or lobby");

        initAllData();
        while(true) { // Log in sequence original menue
            System.out.println("1) Login");
            int choice = Integer.parseInt(userInput.nextLine()); //Use nextLine instead of next in to remove the '/n' char at the end.

            if (choice == 1) {
                System.out.println("Username");
                String userName = userInput.nextLine();
                System.out.println("Password");
                String password = userInput.nextLine();
                loggedIn = loggIn(userName, password);
                if (!loggedIn) {
                    System.out.println("Invalid login.");
                } else if (loggedIn) {
                    break;
                }
            }
        }

        while (loggedIn){
            System.out.println("--------Logged in as " + currentUser.getUsername() + "--------");
            System.out.println("1) View all info");
            System.out.println("2) Start a lobby");
            System.out.println("3) Join lobby");
            System.out.println("4) Create Lobby");
            System.out.println("5) Search Users");
            System.out.println("6) Log Out");
            int choice = Integer.parseInt(userInput.nextLine());

            if(choice == 1){
                System.out.println("------------------------------");
                System.out.println("Currently Logged In Info:");
                System.out.println("Username: " + currentUser.getUsername() + "||Password: " + currentUser.getPassword() +
                "||Platnum: " + currentUser.isPlatinumUser());


                System.out.println("------------------------------");
                System.out.println(currentUser.getUsername() + "'s Hosted Lobbies");
                int i = 1;
                for(Lobby temp : currentUser.getHostedLobbies()){
                    System.out.println(i + ") Lobby Name: " + temp.getName() + ". Password: " + temp.getPassword()
                            + ". Public lobby: " + temp.isPublicBool());
                    System.out.println("    People in lobby");
                    for(User tempUser : temp.getPeopleInLobby().getUserList()){
                        System.out.println("        " + tempUser.getUsername());
                    }
                    i++;
                }

                System.out.println("------------------------------");
                System.out.println(currentUser.getUsername() + "'s Favorite Lobbies");
                i = 1;
                for(Lobby temp : currentUser.getFavoriteLobbies()){
                    System.out.println(i + ") " + temp.getName() + ", Hosted by: " + temp.getHost());
                    i++;
                }

                System.out.println("------------------------------");
                System.out.println(currentUser.getUsername() + "'s Friends");
                i = 1;
                for(User temp : currentUser.getFriendsList()){
                    System.out.println(i + ") " + temp.getUsername());
                }
            }
            else if(choice == 1){
                System.out.println("------------------------------");
                System.out.println("Select a lobby to start");
                int i = 1;
                for(Lobby temp : currentUser.getHostedLobbies()){
                    System.out.println(i + ") " + temp.getName());
                }
                int lobbyChoice = Integer.parseInt(userInput.nextLine());
                Lobby lobbyToStart = currentUser.getHostedLobbies().get(lobbyChoice-1);
                if(lobbyToStart.isActive()){
                    System.out.println("This lobby is already active");
                } else{
                    lobbyToStart.setActive(true);
                }
            }
        }
    }

    private static void initAllData() {
        User alex = new User("Alex", "1");
        User joe = new User("Joe", "2");
        User tanay = new User("Tanay", "3");
        User daniel = new User("Daniel", "4");

        boolean privateLobby = false; //These are here make the lobby contructor easy to read
        boolean publicLobby = true;

        Lobby lobby1 = new Lobby("alexPrivLobby", "11", alex, privateLobby);  //An issue with the lobby is that when public
        Lobby lobby2 = new Lobby("alexPubLobby", "", alex, publicLobby);      //it requires no password, but still need to have
        Lobby lobby3 = new Lobby("joePubLobby", "", joe, publicLobby);        //something in the constructor for the password position.

        userList = UserList.getUserListInstance(); //Singlet implamentation. Don't worry about this or ask me (alex) will be replaced by SQL
        userList.addUserToDB(alex); //the addUserToDB is a function that will be replaced by SQL work
        userList.addUserToDB(joe);
        userList.addUserToDB(tanay);
        userList.addUserToDB(daniel);

        lobbyList = LobbyList.getLobbyListInstance();
        lobbyList.addLobbyToDB(lobby1);
        lobbyList.addLobbyToDB(lobby2);
        lobbyList.addLobbyToDB(lobby3);
    }

    private static boolean loggIn(String username, String password){
        for(User temp : userList.getUserList()){
            if(temp.getUsername().equals(username)){
                if(temp.getPassword().equals(password)){
                    currentUser = temp;
                    System.out.println("User " + temp.getUsername() + " was logged in.");
                    return true;
                }
            }
        }
        return false;
    }
}

