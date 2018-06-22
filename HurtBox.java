import greenfoot.*;
import java.util.Arrays;

public class HurtBox extends Box {
  // so uh, hurtboxes are the things that hurt hitboxes because
  private int damage;
  private int knock;
  private Chara parent;
  private int offsetX, offsetY;
  private Chara[] wonthit;
  private boolean _show = false;
  private int maxWidth, maxHeight, origX, origY;
  private boolean vertical;
  private boolean right, top;
  // "wonthit" the character that the hutbox wont hit
  public HurtBox(int x, int y, int width, int height, boolean rigt, boolean tp, boolean vert) {
    super(x, y, width, height, Color.RED);
    maxWidth = width;
    maxHeight = height;
    origX = x;
    origY = y;
    right = rigt;
    top = tp;
    vertical = vert;
  }
  public void setItems(int dmage, int nock, Chara parnt) {
    damage = dmage;
    knock = nock;
    parent = parnt;
    wonthit = new Chara[]{parent};
    setLocation(parent.getX() + offsetX, parent.getY() + offsetY);
  }
  public int getDamage() {
    return damage;
  }
  public int getKnock() {
    return knock;
  }
  public void act() {
    setCoords(parent.getX() + offsetX, parent.getY() + offsetY);
    if (_show) {
      render();
    } else {
      setImage(new GreenfootImage(1, 1));
    }
  }
  public boolean wontHit(Chara act) {
    // so it won't hit the thing that spawned it
    return Arrays.asList(wonthit).contains(act);
  }
  public void show() {
    _show = true;
  }
  public void hide() {
    _show = false;
    act();
  }
  public void setPercent(float perc) {
    int w, h;
    if (!vertical) {
      w = (int) (maxWidth * perc);
      h = maxHeight;
    } else {
      h = (int) (maxHeight * perc);
      w = maxWidth;
    }
    if (w == 0 || h == 0) {
      hide();
      return;
    }
    show();
    if (top) {
      offsetY = origY + h / 2;
    } else {
      offsetY = origY - h / 2;
    }
    if (right) {
      offsetX = origX - w / 2;
    } else {
      offsetX = origX + w / 2;
    }
    setDims(w, h);
  }
  public boolean isActive() {
    return _show;
  }
  public Chara getParent() {
    return parent;
  }
}
