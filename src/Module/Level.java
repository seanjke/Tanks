package Module;

import java.io.*;

/**
 * Created by Администратор on 30.12.2014.
 */
public class Level {
    //private String path = "/lvl/Level1.txt";
    private String path = "/Module/lvl/Level1.txt";
    private File fileLevel = new File("src/Module/LVLs/Level1.txt").getAbsoluteFile();
    private int lvlSize = 13;
    public int[] getLevel() throws IOException {
        int[] level = new int[lvlSize*lvlSize];
        char[] ch;
        if(fileLevel.exists()) {
            String s;
            System.out.println(fileLevel.getName());
            BufferedReader in = new BufferedReader(new FileReader( fileLevel.getAbsoluteFile()));
            while ((s = in.readLine()) != null) {
                ch =  s.toCharArray();
                for (int i = 0; i < ch.length; i++) {
                    level[i] = Integer.parseInt(String.valueOf(ch[i]));
                }
            }
            in.close();
        }
        return level;
    }

    public int getLvlSize() {
        return lvlSize;
    }

}
