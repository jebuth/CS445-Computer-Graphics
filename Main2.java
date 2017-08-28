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
         
         in = new Scanner(file);
         
         lines = new String[numberOfLines];
         while(in.hasNext()){
             lines[index] = in.nextLine();
             index++;
         }
         in.close();

         createWindow();
         initGL();

         int [] coordinates;
         
         for (int i = 0; i < numberOfLines; i++){
            coordinates = getCoordinates(lines[i]);
              switch (lines[i].charAt(0)) {
                  case 'l': {

                      renderLine(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
                      break;
                  }
                  case 'c': {
    
                      renderCircle(coordinates[0], coordinates[1], coordinates[2]);
                      break;
                  }
                  case 'e': {
  
                      renderEllipse(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
                      break;
                  }
              }

          }
          
      } catch (FileNotFoundException e){
          System.out.println("Unable to open file.");
      } catch (IOException e){
          System.out.println("Unable to read from file.");
      } catch(Exception e){
          e.printStackTrace();
      }

    }
    
    private int[] getCoordinates(String line) {
        int space1 = line.indexOf(' ');
        int space2 = line.lastIndexOf(' ');
        String point1 = line.substring(space1 + 1, space2);
        String point2 = line.substring(space2 + 1, line.length());
        int[] values = new int[4];

        values[0] = Integer.parseInt(point1.substring(0, point1.indexOf(',')));
        values[1] = Integer.parseInt(point1.substring(point1.indexOf(',') + 1, point1.length()));
        if (point2.contains(",")) {
            values[2] = Integer.parseInt(point2.substring(0, point2.indexOf(',')));
            values[3] = Integer.parseInt(point2.substring(point2.indexOf(',') + 1, point2.length()));
        } else {
            values[2] = Integer.parseInt(point2);
        }
        return values;
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
  
    private void renderLine(float tempX1, float tempY1, float tempX2, float tempY2) {
        float x1 = tempX1;
        float y1 = tempY1;
        float x2 = tempX2;
        float y2 = tempY2;
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
  
    private void renderCircle(float tempX, float tempY, float radius) {
        while (!Display.isCloseRequested()) {
            try {
                //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                glColor3f(1.0f, 1.0f, 0.0f);

                glBegin(GL_POINTS);

                float x = tempX;
                float y = tempY;
                float r = radius;

                float dummyX = 0.0f;
                float dummyY = 0.0f;

                for (float theta = 0; theta <= 2 * Math.PI; theta += .01) {
                    dummyX = (float) (r * Math.cos(theta));
                    dummyY = (float) (r * Math.sin(theta));

                    glVertex2f((x + dummyX), (y + dummyY));
                }

                Display.update();
                Display.sync(60);

                glEnd();
            } catch (Exception e) {
                Display.destroy();
            }
        }
    }
    
    private void renderEllipse(float tempX, float tempY, float tempRX, float tempRY) {
        while (!Display.isCloseRequested()) {
            try {
                //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();

                glColor3f(1.0f, 1.0f, 0.0f);

                glBegin(GL_POINTS);

                float x = tempX;
                float y = tempY;
                float rx = tempRX;
                float ry = tempRY;

                float dummyX = 0.0f;
                float dummyY = 0.0f;

                for (float theta = 0; theta <= 2 * Math.PI; theta += .01) {
                    dummyX = (float) (rx * Math.cos(theta));
                    dummyY = (float) (ry * Math.sin(theta));

                    glVertex2f((x + dummyX), (y + dummyY));
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

