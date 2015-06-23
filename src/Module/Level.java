package Module;


import java.io.File;
import java.net.URL;

/**
 * Created by Администратор on 30.12.2014.
 */
public class Level {
    private String path = "/Module/Levels/Level1.txt";
    private int lvlSize = 13;

    public int getLvlSize() {
        return lvlSize;
    }

    public int[] getLevel() {
        int[] level  = new int[lvlSize * lvlSize];
        URL url = this.getClass().getResource(path);
        String file = OpenFile.loadAsString(url);
        char[] ch = file.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] != '\n') {
                level[i] = Integer.parseInt(String.valueOf(ch[i]));
            }
        }
        return  level;
    }

}
