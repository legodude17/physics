import greenfoot.*;
import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;
import java.awt.Toolkit;
import java.awt.Dimension;

public class GameWorld extends World {
  private int gravity = 1;
  private Stage stage;
  private Player playerTest;
  private Player dummy;
  public static Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
  public GameWorld() {
    super((int) GameWorld.ss.getWidth(), (int) GameWorld.ss.getHeight(), 1);
    // make a world
    stage = new Stage(500, 600, 800, 100);
    // make a stage
    add(stage);
    // add the stage
    playerTest = new Player(500, 50, true, 1);
    // place the plair into the GameWorld
    add(playerTest);
    dummy = new Player(700, 50, false, 2);
    add(dummy);
    setPaintOrder(Stage.class, Box.class, Player.class);
    Greenfoot.start();
    // begin the "game"
  }
  public Stage getStage() {
    return stage;
  }
  public void add(Chara obj) {
    addObject(obj, 0, 0);
    obj.render();
    obj.doit(this);
  }
  public void open(String url) {
    try {
      Desktop.getDesktop().browse(new URI(url));
    } catch (URISyntaxException e) {} catch (IOException e) {}
  }
}
