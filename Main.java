/*
 * Justin Buth
 */
package source;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


/**
 *
 * @author Justin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
  public static final int DISPLAY_HEIGHT = 480;
  public static final int DISPLAY_WIDTH = 640;
  
  public void start(){
      
      // read from file
      
      try{
          createWindow();
          initGL();
          render();
      } catch(Exception e){
          e.printStackTrace();
      }
  }
    
  private void createWindow() throws Exception{
      Display.setFullscreen(false);
      
      Display.setDisplayMode(new DisplayMode(640, 480));
      Display.setTitle("Title");
      Display.create();
  }
  
  private void initGL(){
      glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
      
      glMatrixMode(GL_PROJECTION);
      glLoadIdentity();
      
      glOrtho(0,DISPLAY_WIDTH, 0, DISPLAY_HEIGHT, 1, -1);
      
      glMatrixMode(GL_MODELVIEW);
      glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
  }
  
  private void render(){
      
      float x1 = 10.0f;
      float y1 = 380.0f;
      float x2 = 380.0f;
      float y2 = 10.0f;
      float dx = x2 - x1;
      float dy = y2 - y1;
      float incrementUpRight = 2*dy-dx;
      float incrementRight = 2*dy;
      float d = 2*dy-dx;
      
      while(!Display.isCloseRequested()){
          try{
              //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
              glLoadIdentity();
              
              glColor3f(1.0f, 1.0f, 0.0f);
              glPointSize(5);
              
              glBegin(GL_POINTS);

              float yCurrent = y1;
              
              if (y2 > y1){
                  for (float xCurrent = x1; xCurrent <= x2; xCurrent++) {
                      glVertex2f(xCurrent, yCurrent);
                      if (d > 0) {
                          yCurrent++;
                          d += incrementUpRight;
                      } else {
                          d += incrementRight;
                      }
                      Display.update();
                      Display.sync(60);
                  }
              } else if (y2 < y1){
                  for (float xCurrent = x1; xCurrent <= x2; xCurrent++) {
                      glVertex2f(xCurrent, yCurrent);
                      if (d > 0) {
                          yCurrent--;
                          d -= incrementUpRight;
                      } else {
                          yCurrent--;
                          d += incrementRight;
                      }
                      Display.update();
                      Display.sync(60);
                  }
              }
              glEnd();
          } catch (Exception e){
              Display.destroy();
          }
        }
    }
  
    public static void main(String[] args) {
      Main main = new Main();
      main.start();
  }
 }

