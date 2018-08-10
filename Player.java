import greenfoot.*;
import java.util.List;

public class Player extends Chara {
  private Move[] moves;
  private Projectile[] projs;
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
  private boolean spaceDown = false;
  private int spaceCount = 0;
  private boolean inRecovery = false;
  private int recoveryCount = 0;
  private boolean specialFall = false;
  private int dogespedX = 8;
  private int dogespedY = 8;
  private Color color;
  public Player(int x, int y, boolean move, int num) {
    super();
    // "extends Chara"
    persentX = 40 * num * 2;
    halfTheWidth = 10;
    halfTheHeight = 10;
    // the settings for the playa
    box = new HitBox(x, y, halfTheWidth * 2, halfTheHeight * 2);
    box.setColor(new Color(0, 0, 0, 0));
    this.x = orix = x;
    // set the original x and y
    this.y = oriy = y;
    // set the original x and y
    // the location, even though it's only used half the time
    domove = move;
    // if it is player controlled because yeah
    moves = new Move[]{
      new Move(
        new HurtBox[]{
          new HurtBox(halfTheWidth, 2, 50, 4, false, false, false)
        },
        2,
        1,
        new Frames(5, 10, 60),
        this
      ),
      new Move(
        new HurtBox[]{
          new HurtBox(2, -halfTheHeight, 4, 50, true, false, true)
        },
        2,
        1,
        new Frames(10, 10, 60),
        this
      ),
      new Move(
        new HurtBox[]{
          new HurtBox(2, halfTheHeight, 4, 50, true, true, true)
        },
        2,
        1,
        new Frames(5, 10, 60),
        this
      ),
      new Move(
        new HurtBox[]{
          new HurtBox(-halfTheWidth, 2, 50, 4, true, false, false)
        },
        2,
        1,
        new Frames(5, 10, 60),
        this
      ),
      new Move(
        new HurtBox[]{
          new HurtBox(-halfTheWidth, 2, 10, 4, true, false, false),
          new HurtBox(halfTheWidth, 2, 10, 4, false, false, false)
        },
        1,
        1,
        new Frames(2, 5, 10),
        this
      ),
      new Move(
        new HurtBox[]{
          new HurtBox(-2, 2, 4, 20, false, false, true),
          new HurtBox(2, 2, 20, 4, true, false, false),
          new HurtBox(-2, -2, 4, 20, false, true, true),
          new HurtBox(2, 2, 20, 4, false, false, false)
        },
        5,
        0,
        new Frames(1, 3, 5),
        this
      )
    };
    projs = new Projectile[]{
      new Projectile(30, 10, 1, 0, 10, 5, this),
      new Projectile(30, 10, -1, 0, 10, 5, this)
    };
    if (move) {
      color = Color.GREEN;
    } else {
      color = Color.BLUE;
    }
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
    for (int i = 0; i < moves.length; i++) {
      moves[i].onWorld(world);
    }
    for (int i = 0; i < projs.length; i++) {
      projs[i].onWorld(world);
    }
  }
  public void render() {
    box.render();
    GreenfootImage image = new GreenfootImage(halfTheWidth * 2, halfTheHeight * 2);
    image.setColor(color);
    image.fill();
    setImage(image);
  }
  public Boolean key(String k) {
    return Greenfoot.isKeyDown(k);
  }
  public void act() {
    anyPercent();
    physicMovement();
    doMove();
    // System.out.println(x);
    // System.out.println(y);
    setLocation(x, y);
    box.setCoords(x, y);
    render();
    checkHit();
    for (int i = 0; i < moves.length; i++) {
      moves[i].act();
    }
    for (int i = 0; i < projs.length; i++) {
      projs[i].act();
    }
    if (isAtEdge()) {
      die();
    }

    if (inRecovery) {
      recoveryCount++;
      if (recoveryCount >= 15) {
        inRecovery = false;
        recoveryCount = 0;
        specialFall = true;
        color = getImage().getColor().darker();
        render();
      }
    }
    if (standingOnSmth() && specialFall) {
      specialFall = false;
      color = getImage().getColor().brighter();
      render();
    }
  }
  private void doMove() {
    if (domove) {
      int dirX = 0, dirY = 0;
      if (key("d") && movX < maxSpeed) {
        movX += 0.3f;
        dirX = 1;
        // increse right velocity
      }
      if (key("a") && movX > -maxSpeed) {
        movX -= 0.3f;
        dirX = -1;
        // decrese right velocity
      }
      if (key("w")) {
        dirY = -1;
      }
      if (key("s")) {
        dirY = 1;
      }
      if (specialFall) return;
      if (key("space")) {
        if (!spaceDown) {
          spaceDown = true;
          if (standingOnSmth()) {
            movY = -7;
          } else if (hasAirJump) {
            movY = -7;
            hasAirJump = false;
          }
        }
        // set the veritcal velocity to -10
      } else {
        spaceDown = false;
      }
      if (standingOnSmth() && !hasAirJump) {
        hasAirJump = true;
      }
      if (key("l")) {
        if (standingOnSmth()) {
          if (key("s")) {
            waveDash(dirX);
          }
        } else {
          airDoge(dirX,dirY);
        }
      }
      if (key("j")) {
        if (dirX == 1) {
          moves[0].start();
        } else if (dirX == -1) {
          moves[3].start();
        } else if (dirY == 1) {
          moves[2].start();
        } else if (dirY == -1) {
          moves[1].start();
        } else {
          moves[4].start();
        }
      } else {
        for (int i = 0; i <= 4; i++) {
          moves[i].up();
        }
      }
      if (key("k")) {
        if (dirY == -1) {
          inRecovery = true;
        } else if (dirY == 1) {
          moves[5].start();
        } else if (dirX == 1) {
          projs[0].fire(getX(), getY());
        } else if (dirX == -1) {
          projs[1].fire(getX(), getY());
        }
      } else {
        moves[5].up();
      }
    } else {
      if (key("up")) {
        moves[1].start();
      }
      if (key("down")) {
        moves[2].start();
      }
      if (key("left")) {
        moves[3].start();
      }
      if (key("right")) {
        moves[0].start();
      }
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
        if (!hurtBoxes.get(i).wontHit(this) && hurtBoxes.get(i).isActive()) {
          _damage += hurtBoxes.get(i).getDamage();
          _knock += hurtBoxes.get(i).getKnock();
        }
      }
      applyDamage(_damage);
      applyKnock(_knock, hurtBoxes.get(0).getParent());
    }
  }
  private void applyDamage(int dam) {
    damage += dam;
  }
  private void applyKnock(int nock, Chara parent) {
    boolean isRight = parent.getX() < getX();
    boolean isBottom = parent.getY() > getY();
    movX += (nock * (damage / 100)) * (isRight ? 1 : -1);
    movY -= 1 * (nock * (damage / 100)) * (isBottom ? 1 : -1);
  }
  public void doGravity() {
    if (inRecovery) {
      movY -= 1;
    } else {
      movY += 0.5;
    }
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
  public HitBox getBox() {
    return box;
  }
  public void airDoge(int dirX, int dirY) {
    movX=dirX*dogespedX;
    movY=dirY*dogespedY;
    specialFall=true;
    color = getImage().getColor().darker();
    render();
  }
  public void waveDash(int dirX){
    movX=dirX*dogespedX;
  }
}
