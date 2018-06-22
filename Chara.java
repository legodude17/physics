import greenfoot.*;

public class Chara extends Actor {
  public Chara() {
    super();
  }
  public GameWorld getGameWorld() {
    return (GameWorld) getWorld();
  }
  public Stage getStage() {
    return getGameWorld().getStage();
  }
  public void render() {
    // stub
  }
  public void doit(GameWorld world) {
    // stub
  }
}
