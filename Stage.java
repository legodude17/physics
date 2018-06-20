import greenfoot.*;

public class Stage extends CollisionBox {
  public StageTop top;
  public Stage(int x, int y, int width, int height) {
    super(x, y, width, height);
    top = new StageTop(x, y, width, height);
  }
}
