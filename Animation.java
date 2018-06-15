import greenfoot.*;

public class Animation {
  private GreenfootImage[] images;
  private int frame;
  private int fps;
  private int counter = 0;
  private GreenfootImage curImage;
  public Animation(String[] frames, int framesps) {
    fps = framesps;
    images = new GreenfootImage[frames.length];
    for (int i = 0; i < frames.length; i++) {
      images[i] = new GreenfootImage(frames[i].concat(".png"));
    }
  }
  public Animation(String image) {
    curImage = new GreenfootImage(image);
    images = new GreenfootImage[]{curImage};
  }
  public GreenfootImage frame() {
    counter++;
    if (counter == fps && images.length > 1) {
      counter = 0;
      frame++;
      if (frame == images.length) {
        frame = 0;
      }
      curImage = images[frame];
    }
    return curImage;
  }
}
