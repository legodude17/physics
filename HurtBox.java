import greenfoot.*;

public class HurtBox extends Box {
  private int damage;
  private int knock;
  private int life;
  private Actor parent;
  private int offsetX, offsetY;
  private Chara[] wonthit;
  // "wonthit"
  public HurtBox(int x, int y, int width, int height, int damage, int knock, int life, Actor parent) {
    super(parent.getX() + x, parent.getY() + y, width, height, Color.RED);
    this.damage = damage;
    this.knock = knock;
    this.life = life;
    this.parent = parent;
    wonthit = new Actor[]{parent};
    offsetX = x;
    offsetY = y;
  }
  public int getDamage() {
    return damage;
  }
  public int getKnock() {
    return knock;
  }
  public void act() {
    life--;
    setLocation(parent.getX() + offsetX, parent.getY() + offsetY);
    System.out.println(getX());
    System.out.println(getY());
    render();
    if (life <= 0) {
      getWorld().removeObject(this);
    }
  }
  public boolean wontHit(Actor act) {
    // so it won't hit the thing that spawned it
    return Arrays.asList(wonthit).contains(act);
  }
}
