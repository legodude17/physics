import greenfoot.*;
import java.util.*;
public class PhysicsEngine extends Chara
{
    private float movX = 5, movY = 0;
    private int formerXPos, formerYPos, maxSpeed = 15;
    protected int halfTheWidth = getImage().getWidth() / 2, halfTheHeight = getImage().getHeight() / 2;
    @Override
    public void addedToWorld(World world){
        formerXPos = getX();
        formerYPos = getY();
    }

    @Override
    public void setLocation(int x, int y){
        super.setLocation(x, y);
        formerXPos = getX();
        formerYPos = getY();
    }

    protected void physicMovement() {
        movX = Math.signum(movX) * ((float) (Math.abs(movX) - 0.5));
        movY += 1;
        movY = Math.signum(movY) * ((float) (Math.abs(movY) - 0.2));
        formerYPos += movY;
        formerXPos += movX;
        setLocation((int) formerXPos, (int) formerYPos);
    }

    protected void accelerateRight(){
        if(movX < maxSpeed){
            movX++;
        }
    }

    protected void accelerateLeft(){
        if(movX > -maxSpeed){
            movX--;
        }
    }

    protected void jump(){
        movY = -15;
    }

    private void rescueIfNeeded(){
        if(getX() == 0 && (movX > -0.5 && movX < 0.5 )){
            movX = -movX + 3;
        }
    }

    protected void checkForCollision(){
        rescueIfNeeded();
        checkObjectCollision();
        checkWorldBoundCollision();
    }

    private void checkWorldBoundCollision(){
        if(getX() == getWorld().getWidth()-1 || getX() == 0){
            movX = -movX;
        }
    }

    private void checkObjectCollision(){
        Set<Direction> directionsOfCollidingObjects = getDirectionsOfCollidingObjects();
        boolean rightCollision = directionsOfCollidingObjects.contains(Direction.Right);
        boolean leftCollision = directionsOfCollidingObjects.contains(Direction.Left);
        boolean upCollision = directionsOfCollidingObjects.contains(Direction.Up);
        boolean downCollision = directionsOfCollidingObjects.contains(Direction.Down);

        if ((leftCollision || rightCollision) && !(upCollision || downCollision)){
            movX = 0;
        }
        else if (!(leftCollision || rightCollision) && (upCollision || downCollision)){
            movY = 0;
        }
        else if ((leftCollision || rightCollision) || (upCollision || downCollision)){
            movX = 0;
            movY = 0;
        }

    }

    public Set<Direction> getDirectionsOfCollidingObjects(){
        Actor touchingActor = null;
        Set<Direction> retVal = new HashSet<Direction>();
        if(movY >= 0){ //Downcheck
            for(int i = -halfTheWidth;i <halfTheWidth;i++){
                touchingActor = getOneObjectAtOffset(i,halfTheHeight,null);
                if(touchingActor != null){
                    retVal.add(Direction.Down);
                }
            }
        }
        else if(movY <= 0){ //Upcheck
            for(int i = -halfTheWidth;i <halfTheWidth;i++){
                touchingActor = getOneObjectAtOffset(i,-halfTheHeight,null);
                if(touchingActor != null){
                    retVal.add(Direction.Up);
                }
            }
        }
        if(movX <= 0){ //Leftcheck
            for(int i = -halfTheHeight;i <halfTheHeight;i++){
                touchingActor = getOneObjectAtOffset(i,-halfTheWidth,null);
                if(touchingActor != null){
                    retVal.add(Direction.Left);
                }
            }
        }
        else if(movX >= 0){ //Rightcheck
            for(int i = -halfTheHeight;i <halfTheHeight;i++){
                touchingActor = getOneObjectAtOffset(i,halfTheWidth,null);
                if(touchingActor != null){
                    retVal.add(Direction.Right);
                }
            }
        }
        return retVal;
    }

    public float getMovX(){
        return movX;
    }

    public float getMovY(){
        return movY;
    }

    protected void setMaxSpeed(int maxSpeed){
        this.maxSpeed = maxSpeed;
    }

}
