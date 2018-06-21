import greenfoot.*;

public class Box extends Chara {
  private int width;
  private int height;
  private int x;
  private int y;
  private Color color;
  public Box(int x, int y, int width, int height, Color color) {
    super();
    // box is a character
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    // set stuff
    setColor(color);
    // also set stuff
  }
  public void setColor(Color color) {
    this.color = color;
    // set color I mean really come on
  }
  public int getWidth() {
    return width;
  }
  public int getHeight() {
    return height;
  }
  public void render() {
    setLocation(x, y);
    GreenfootImage image = new GreenfootImage(width, height);
    image.setColor(color);
    image.fill();
    setImage(image);
  }
  public void setX(int x) {
    this.x = x;
  }
  public void setY(int y) {
    this.y = y;
  }
  public void setCoords(int x, int y) {
    setX(x);
    setY(y);
  }
  public void act() {
    // stub
  }
}
