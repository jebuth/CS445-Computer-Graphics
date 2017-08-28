/*
 * Justin Buth
 */
package source;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import java.io.*;
import java.util.Scanner;

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
         File file = new File("coordinates.txt");
         Scanner in = new Scanner(file);
         String [] lines;
         String temp = null;
         int numberOfLines = 0;
         int index = 0;
         
         while(in.hasNext()){
             temp = in.nextLine();
             numberOfLines++;
         }
         in.close();
         
         lines = new String[numberOfLines];
         while(in.hasNext()){
             lines[index] = in.nextLine();
             index++;
         }
         in.close();
          
      } catch (FileNotFoundException e){
          System.out.println("Unable to open file.");
      } catch (IOException e){
          System.out.println("Unable to read from file.");
      }

      try{
          createWindow();
          initGL();

          renderLine();
          renderCircle();
          renderEllipse();
      } 
       catch(Exception e){
          e.printStackTrace();
      }
    }
    
    private void createWindow() throws Exception {
        Display.setFullscreen(false);

        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Title");
        Display.create();
    }
  
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(0, DISPLAY_WIDTH, 0, DISPLAY_HEIGHT, 1, -1);

        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

        glPointSize(2);
    }
  
    private void renderLine() {

        float x1 = 10.0f;
        float y1 = 380.0f;
        float x2 = 380.0f;
        float y2 = 10.0f;
        float dx = x2 - x1;
        float dy = y2 - y1;
        float incrementUpRight = 2 * dy - dx;
        float incrementRight = 2 * dy;
        float d = 2 * dy - dx;

        while (!Display.isCloseRequested()) {
            try {
                //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                glColor3f(1.0f, 1.0f, 0.0f);

                glBegin(GL_POINTS);

                float yCurrent = y1;

                if (y2 > y1) {
                    for (float xCurrent = x1; xCurrent <= x2; xCurrent++) {
                        glVertex2f(xCurrent, yCurrent);
                        if (d > 0) {
                            yCurrent++;
                            d += incrementUpRight;
                        } else {
                            d += incrementRight;
                        }

                    }
                } else if (y2 < y1) {
                    for (float xCurrent = x1; xCurrent <= x2; xCurrent++) {
                        glVertex2f(xCurrent, yCurrent);
                        if (d > 0) {
                            yCurrent--;
                            d -= incrementUpRight;
                        } else {
                            yCurrent--;
                            d += incrementRight;
                        }

                    }
                }
                Display.update();
                Display.sync(60);
                glEnd();
            } catch (Exception e) {
                Display.destroy();
            }
        }
    }
  
    private void renderCircle() {
        while (!Display.isCloseRequested()) {
            try {
                //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                glColor3f(1.0f, 1.0f, 0.0f);

                glBegin(GL_POINTS);

                float x = 50.0f;
                float y = 50.0f;
                float r = 100.0f;

                float tempX = 0.0f;
                float tempY = 0.0f;

                for (float theta = 0; theta <= 2 * Math.PI; theta += .01) {
                    tempX = (float) (r * Math.cos(theta));
                    tempY = (float) (r * Math.sin(theta));

                    glVertex2f((x + tempX), (y + tempY));
                }

                Display.update();
                Display.sync(60);

                glEnd();
            } catch (Exception e) {
                Display.destroy();
            }
        }
    }
    
    private void renderEllipse() {
        while (!Display.isCloseRequested()) {
            try {
                //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                glColor3f(1.0f, 1.0f, 0.0f);

                glBegin(GL_POINTS);

                float x = 450.0f;
                float y = 250.0f;
                float rx = 75.0f;
                float ry = 35.0f;

                float tempX = 0.0f;
                float tempY = 0.0f;

                for (float theta = 0; theta <= 2 * Math.PI; theta += .01) {
                    tempX = (float) (rx * Math.cos(theta));
                    tempY = (float) (ry * Math.sin(theta));

                    glVertex2f((x + tempX), (y + tempY));
                }

                Display.update();
                Display.sync(60);

                glEnd();
            } catch (Exception e) {
                Display.destroy();
            }
        }
    }
  
    public static void main(String[] args) {
      Main main = new Main();
      main.start();
  }
 }

