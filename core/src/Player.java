import com.mygdx.game.MainClass;
import com.mygdx.game.SeaField;

public class Player {

    private int playerInstance;
    protected String playerType;
    protected boolean isMine;
    protected SeaField gf;

    public Player() {
        playerInstance = 1;
        playerType = "Human";
        isMine = true;
        gf = new SeaField(MainClass.LEFT_INDENT, MainClass.BOTTOM_INDENT);
    }
}
