import java.util.ArrayList;
import java.util.List;

public class LobbyList {
    private List<Lobby> lobbyList;
    private static LobbyList lobbyListInstance = new LobbyList();

    public static LobbyList getLobbyListInstance() {
        return lobbyListInstance;
    }

    LobbyList(){
        lobbyList = new ArrayList<>();
    }

    public static void setLobbyListInstance(LobbyList lobbyListInstance) {
        LobbyList.lobbyListInstance = lobbyListInstance;
    }

    public void addLobbyToDB(Lobby newLobby){
        lobbyList.add(newLobby);
    }
}
