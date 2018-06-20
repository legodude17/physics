import greenfoot.*;

public class Chara extends Actor {
  private Animation animate;
  public Chara() {
    super();
  }
  public void act() {
    setImage(animate.frame());
  }
  public GameWorld getGameWorld() {
    return (GameWorld) getWorld();
  }
  public Stage getStage() {
    return getGameWorld().getStage();
  }
  public void render() {}
  public void setLoacation(int x, int y) {
    super.setLocation(x, y);
  }
}
