import greenfoot.*;
import java.util.List;

public class Projectile extends Chara {
  private int dirX, dirY;
  private HurtBox box;
  private Player parent;
  public Projectile(int width, int height, int drX, int drY, int dam, int nock, Player prnt) {
    dirX = drX;
    dirY = drY;
    parent = prnt;
    box = new HurtBox(1, 1, width, height, false, false, false);
    box.setItems(dam, nock, parent);
    box.dontCare();
    setImage(new GreenfootImage(1, 1));
  }
  public void fire(int x, int y) {
    if (box.isActive()) return;
    setLocation(x, y);
    box.show();
  }
  public void act() {
    box.act();
    if (box.isActive()) {
      setLocation(getX() + dirX, getY() + dirY);
    }
    List<HitBox> objs = getIntersectingObjects(HitBox.class);
    if (objs.size() != 0 && !objs.get(0).equals(parent.getBox())) {
      box.hide();
    }
    if (intersects(getStage()) || isAtEdge()) {
      box.hide();
    }
  }
  public void setLocation(int x, int y) {
    box.setCoords(x, y);
    super.setLocation(x, y);
  }
  public void onWorld(GameWorld world) {
    world.add(box);
    world.add(this);
  }
}
