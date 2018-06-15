import greenfoot.*;

class Player extends Chara {
  public Player() {
    super();
  }
  public Boolean key(String k) {
    return Greenfoot.isKeyDown(k);
  }

}
