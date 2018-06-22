import greenfoot.*;
import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

public class GameWorld extends World {
  private int gravity = 1;
  private Stage stage;
  private Player playerTest;
  private Player dummy;
  public GameWorld() {
    super(1000, 700, 1);
    // make a world
    stage = new Stage(500, 600, 800, 100);
    // make a stage
    add(stage);
    // add the stage
    playerTest = new Player(500, 50, true, 1);
    // place the player into the GameWorld
    add(playerTest);
    dummy = new Player(700, 50, false, 2);
    add(dummy);
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
