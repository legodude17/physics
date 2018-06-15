import greenfoot.*;

public class GameWorld extends World {
  private int gravity = 1;
  private Stage stage;
  public GameWorld() {
    super(1000, 700, 1);
    stage = new Stage(500, 600, 900, 100);
    addObject(stage, 0, 0);
    stage.render();
    Greenfoot.start();
  }
  public Stage getStage() {
    return stage;
  }
  public void add(Renderable obj) {
    addObject(obj, 0, 0);
    obj.render();
  }
}
