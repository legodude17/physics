import greenfoot.*;

public class Player extends PhysicsEngine {
  private Move[] moves;
  private HitBox box;
  private int gotoX, gotoY;
  public Player(int x, int y) {
    super();
    halfTheWidth = 10;
    halfTheHeight = 10;
    box = new HitBox(x, y, 20, 20);
    setMaxSpeed(10);
    gotoX = x;
    gotoY = y;
    doRender();
  }
  public void addedToWorld(GameWorld world) {
    world.add(box);
    doRender();
    setLocation(gotoX, gotoY);
    doRender();
  }
  public void setLocation(int x, int y) {
    box.setCoords(x, y);
    doRender();
    super.setLocation(x, y);
  }
  private void doRender() {
    box.render();
    setImage(box.getImage());
  }
  public Boolean key(String k) {
    return Greenfoot.isKeyDown(k);
  }
  public void act() {
    doMove();
    checkForCollision();
    physicMovement();
  }
  private void doMove() {
    if (key("d")) {
      accelerateRight();
    }
    if (key("a")) {
      accelerateLeft();
    }
    if (key("space")) {
      jump();
    }
  }
  private boolean standingOnSmth(){
    for(int i = -halfTheWidth + 1; i < halfTheWidth - 1; i++){
      Actor touchingActor = getOneObjectAtOffset(i, halfTheHeight + 15, null);
      if((touchingActor != null) && touchingActor.getClass() == Stage.class){
        return true;
      }
    }
    return false;
  }
}
