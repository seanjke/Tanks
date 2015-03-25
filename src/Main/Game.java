package Main;

import Control.GUIcontrol;
import Control.MoveControl;
import Module.Board;
import View.Screen;
import View.SpriteSheet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

/**
 * Created by Hank on 26.11.14.
 */
public class Game extends Canvas implements Runnable{
    public static final String NAME = "Tanks";
    public static final int HEIGHT = 480;
    public static final int WIDTH = 640;
    public Screen screen;
    private static final int SCALE = 1;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    public InputHandler input = new InputHandler(this);
    private boolean running = false;
    public Graphics g;
    public GUIcontrol guiControl = new GUIcontrol();
    public Board board = new Board();
    public Thread thread = new Thread(this);
    public int[] colors = new int[256];
    private boolean eagle = true;
    private int result;
    private static State state = State.MAIN_MENU;
    private static enum State {
        INTRO, MAIN_MENU, GAME, END_GAME
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        JFrame frame = new JFrame(Game.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setAutoRequestFocus(true);
        frame.setResizable(false);
        frame.setVisible(true);
        game.start();
    }

    public void start() {
        running = true;
        thread.start();
    }

    public void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frame = 0;
        int updates = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                updates();
                updates++;
                delta--;
            }
            render();
            frame++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("updates " + updates + " fps " + frame);
                frame = 0;
                updates = 0;
            }
        }
        stop();
    }

    private void init() throws IOException {
        int pp = 0;
        for (int r = 0; r < 6; r++) {
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int rr = (r * 255 / 5);
                    int gg = (g * 255 / 5);
                    int bb = (b * 255 / 5);
                    int mid = (rr * 30 + gg * 59 + bb * 11) / 100;

                    int r1 = ((rr + mid * 1) / 2) * 230 / 255 + 10;
                    int g1 = ((gg + mid * 1) / 2) * 230 / 255 + 10;
                    int b1 = ((bb + mid * 1) / 2) * 230 / 255 + 10;
                    colors[pp++] = r1 << 16 | g1 << 8 | b1;
                }
            }
        }
        screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/View/Images/icons.png"))));
        guiControl.setColors(colors);
        guiControl.setScreen(screen);
        guiControl.setPixels(this.pixels);
        setUpGame();
    }

    private void updates() {
        checkInput();
        switch (state) {
            case INTRO:

                break;
            case GAME:
                keyListener();
                boardEvents();
                redrawGraphics();
                break;
            case MAIN_MENU:
                guiControl.drawMainMenu();
                break;
            case END_GAME:
                guiControl.drawDeadMenu();
                break;
        }
    }

    private  void checkInput() {
        switch (state) {
            case INTRO:

                break;
            case GAME:

                break;
            case MAIN_MENU:
                if (input.menu.down) {
                    state = State.GAME;
                }
                break;
            case END_GAME:
                if(input.menu.down) {
                    state = State.GAME;
                    board.clearField();
                    restartGame();
                }
                break;
        }
    }
    private void setUpGame() {
        try {
            board.loadLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        board.addPlayer();
        board.addTanks();
        guiControl.drawTanks(board);
        guiControl.drawObjects(board);
    }

    private void redrawGraphics() {
        guiControl.drawTanks(board);
        guiControl.drawBullets(board);
        guiControl.drawObjects(board);
        guiControl.drawStatistics(board);
    }

    private void boardEvents(){
        board.shot();
        board.moveBullets();
        board.moveEnemy();
        board.checkNumOfTanks();
        if(!board.isEagleAlive()){
          state = State.END_GAME;
        }
        if(board.getKills().size() > 19) {
          state = State.END_GAME;
        }
    }

    private void restartGame() {
        try {
            board.loadLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        board.addPlayer();
        board.addTanks();
    }

    private void keyListener() {
        if(eagle) {
            input.tick();
            if (input.up.down) {
                board.movePlayer(MoveControl.Move.FORWARD);
            } else if (input.down.down) {
                board.movePlayer(MoveControl.Move.BACK);
            } else if (input.left.down) {
                board.movePlayer(MoveControl.Move.LEFT);
            } else if (input.right.down) {
                board.movePlayer(MoveControl.Move.RIGHT);
            }
            if (input.attack.clicked) {
                board.playerShot();
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }
}
