import greenfoot.*;
import java.util.List;

public class Player extends Chara {
  private Move[] moves;
  private HitBox box;
  private int xleft, xright, ytop, ybottom, halfTheWidth, halfTheHeight, x, y;
  private float movX, movY;
  private float maxSpeed = 5;
  private boolean domove;
  private boolean hasAirJump = true;
  private int damage = 0;
  private boolean invisible = false;
  private int orix = 0;
  private int oriy = 0;
  private int persentX = 40;
  private int persentY = 40;
  public Player(int x, int y, boolean move) {
    super();
    // "extends Chara"
    halfTheWidth = 10;
    halfTheHeight = 10;
    // the settings for the player
    box = new HitBox(x, y, halfTheWidth * 2, halfTheHeight * 2);
    this.x = orix = x;
    // set the original x and y
    this.y = oriy = y;
    // set the original x and y
    // the location, even though it's only used half the time
    domove = move;
    // if it is player controlled because yeah
    render();
  }
  public void doit(GameWorld world) {
    // basically init but joshua named it weird
    world.add(box);
    // add the hitbox to the world
    Stage stage = world.getStage();
    // get the staig
    int halfWidth = stage.getWidth() / 2;
    // the halfWidth of the stage (since getX returnd the middle of the stage)
    int halfHeight = stage.getHeight() / 2;
    // the halfHeight
    xleft = stage.getX() - halfWidth - halfTheWidth;
    // the x coord of the left of the stage
    xright = stage.getX() + halfWidth + halfTheWidth;
    // the x coord of the right of the stage
    ytop = stage.getY() - halfHeight - halfTheHeight;
    // the top of the stage
    ybottom = stage.getY() + halfHeight + halfTheHeight;
    // the bottom of the stage
  }
  public void render() {
    box.render();
    setImage(box.getImage());
  }
  public Boolean key(String k) {
    return Greenfoot.isKeyDown(k);
  }
  public void act() {
    anyPercent();
    physicMovement();
    if (domove) doMove();
    // System.out.println(x);
    // System.out.println(y);
    setLocation(x, y);
    box.setCoords(x, y);
    render();
    checkHit();
    if (isAtEdge()) {
      die();
    }
  }
  private void doMove() {
    if (key("d") && movX < maxSpeed) {
      movX += 0.3f;
      // increse right velocity
    }
    if (key("a") && movX > -maxSpeed) {
      movX -= 0.3f;
      // decrese right velocity
    }
    if (key("space")) {
      if (standingOnSmth()) {
        movY = -15;
      } else if (hasAirJump) {
        movY = -15;
        hasAirJump = false;
      }
      // set the veritcal velocity to -10
    }
    if (standingOnSmth() && !hasAirJump) {
      hasAirJump = true;
    }
    if (key("u")) {
      getGameWorld().add(new HurtBox(0, -halfTheHeight - 2, 50, 4, 10, 4, 5, this));
    }
    if (key("j")) {
      getGameWorld().add(new HurtBox(halfTheWidth + 25, 0, 50, 5, 10, 4, 5, this));
      getGameWorld().add(new HurtBox(-halfTheWidth - 25, 0, 50, 5, 10, 4, 5, this));
    }
  }
  private boolean standingOnSmth() {
    return getY() == ytop && getX() > xleft && getX() < xright;
    // on y level of stage && within stage left && right
  }
  private void physicMovement() {
    int gotoX = 0, gotoY = 0;
    movX = movX * 0.99f;
    movY = movY * 0.99f;
    doGravity();
    boolean ynear = x > xleft && x < xright;
    boolean xnear = y < ybottom && y > ytop;
    if (movY >= (ytop - getY()) && movY > 0 && getY() < getStage().getY() && ynear) {
      // System.out.println("top");
      movY = 0;
      gotoY = ytop;
    } else if (movY <= (getY() - ybottom) && movY < 0 && getY() > getStage().getY() && ynear) {
      // System.out.println("bottom");
      movY = 0;
      gotoY = ybottom;
    } else {
      gotoY = getY() + (int) movY;
    }

    if (movX >= (getX() - xleft) && movX > 0 && getX() < getStage().getX() && xnear) {
      // System.out.println("left");
      movX = 0;
      gotoX = xleft;
    } else if (movX <= (xright - getX()) && movX < 0 && getX() > getStage().getY() && xnear) {
      // System.out.println("right");
      movX = 0;
      gotoX = xright;
    } else {
      gotoX = getX() + (int) movX;
    }
    go(gotoX, gotoY);
  }
  public int getX() {
    return x;
  }
  public int getY() {
    return y;
  }
  public void go(int gotoX, int gotoY) {
    // System.out.println("nvidia gtx" + x);
    // System.out.println("gty" + y);
    x = gotoX;
    y = gotoY;
  }
  private void checkHit() {
    if (invisible) {
      return;
    }
    int _damage = 0;
    int _knock = 0;
    List<HurtBox> hurtBoxes = getIntersectingObjects(HurtBox.class);
    if (hurtBoxes.size() != 0) {
      for (int i = 0; i < hurtBoxes.size(); i++) {
        if (!hurtBoxes.get(i).wontHit(this)) {
          _damage += hurtBoxes.get(i).getDamage();
          _knock += hurtBoxes.get(i).getKnock();
        }
      }
      applyDamage(_damage);
      applyKnock(_knock);
    }
  }
  private void applyDamage(int dam) {
    damage += dam;
  }
  private void applyKnock(int nock) {
    movX += (nock * damage) / 600;
    movY -= (nock * damage) / 600;
  }
  public void doGravity() {
    movY += 1;
    // move down, even though it's + 1
  }
  public void die() {
    x = orix;
    y = oriy;
    movX = 0;
    movY = 0;
    damage = 0;
    // getGameWorld().open("https://www.youtube.com/watch?v=f77SKdyn-1Y");
  }
  public void anyPercent() {
    getWorld().showText(Integer.toString(damage) + '%', persentX, persentY);
  }
}
