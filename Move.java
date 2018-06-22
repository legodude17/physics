import greenfoot.*;

public class Move {
  private HurtBox[] boxes;
  private int damage;
  private int knock;
  private Chara parent;
  private Frames frames;
  private MoveState state;
  private int currentFrame;
  public Move(HurtBox[] bxs, int dmage, int nock, Frames frms, Chara parnt) {
    boxes = bxs;
    damage = dmage;
    knock = nock;
    frames = frms;
    parent = parnt;
    for (int i = 0; i < bxs.length; i++) {
      bxs[i].setItems(damage, knock, parent);
    }
    state = MoveState.READY;
  }
  public void start() {
    if (state == MoveState.READY) {
      state = MoveState.STARTING;
      currentFrame = 0;
    }
  }
  public void onWorld(GameWorld world) {
    for (int i = 0; i < boxes.length; i++) {
      world.add(boxes[i]);
    }
  }
  public void act() {
    if (state == MoveState.RUNNING) {
      currentFrame++;
      show();
      doFrame();
      if (currentFrame == frames.length) {
        hide();
        state = MoveState.COOLDOWN;
        currentFrame = 0;
      }
    } else if (state == MoveState.COOLDOWN) {
      currentFrame++;
      if (currentFrame == frames.cooldown) {
        state = MoveState.READY;
        currentFrame = 0;
      }
    } else if (state == MoveState.STARTING) {
      currentFrame++;
      if (currentFrame == frames.start) {
        state = MoveState.RUNNING;
        currentFrame = 0;
      }
    }
    for (int i = 0; i < boxes.length; i++) {
      boxes[i].act();
    }
  }
  private void doFrame() {
    for (int i = 0; i < boxes.length; i++) {
      boxes[i].setPercent((float) currentFrame / frames.length);
    }
  }
  private void show() {
    for (int i = 0; i < boxes.length; i++) {
      boxes[i].show();
    }
  }
  private void hide() {
    for (int i = 0; i < boxes.length; i++) {
      boxes[i].hide();
    }
  }
}

enum MoveState {
  READY,
  COOLDOWN,
  STARTING,
  RUNNING
}
