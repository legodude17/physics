import greenfoot.*;

public class GameWorld extends World {
  private int gravity = 1;
  private Stage stage;
  private Player playerTest;
  public GameWorld() {
    super(1000, 700, 1);
    stage = new Stage(500, 600, 800, 100);
    add(stage);
    stage.top.setColor(new Color(0,0,0,0));
    add(stage.top);
    playerTest = new Player(500, 500);
    add(playerTest);
    Greenfoot.start();
  }
  public Stage getStage() {
    return stage;
  }
  public void add(Chara obj) {
    addObject(obj, 0, 0);
    obj.render();
  }
}
