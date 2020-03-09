import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import processing.core.*;

public class VirtualMain extends PApplet
{

   //GAMEMODES
   private boolean game = false;

   //IMAGES
   private long next_time;
   private PImage background;
   private PImage obstacle;
   private PImage goal;
   private PImage blackScreen;
   private PImage menuScreen;

   private PImage camBreaker;
   private PImage invisibleMan;
   private PImage runner;
   private List<PImage> playerImgs;

   //TIME
   private final int SECOND = 1000;
   private int currentSec;


   //Grid portion
   private static final int TILE_SIZE = 32;
   public static enum GridValues { BACKGROUND, OBSTACLE, GOAL, BLACKSCREEN, WALL, FLOOR, DOOR, CAMSCREEN};
   private GridValues[][] grid;
   private static final int ROWS = 15;
   private static final int COLS = 20;


   //creating levels
   private static Level level;

   //for rooms
   private PImage wall, floor, door, camScreen;


   //for now creating player
   Player p1;


   private Point goalPos;

   public void settings()
   {
      size(640, 480);
   }

   public void setup()
   {
      next_time = 0;
      currentSec = 0;
      goalPos = new Point(14, 13);


      playerImgs = new ArrayList<>();
      playerImgs.add(loadImage("images/player2.png"));
      playerImgs.add(loadImage("images/player1.png"));
      playerImgs.add(loadImage("images/player3.png"));
      playerImgs.add(loadImage("images/player4.png"));

      camBreaker = loadImage("images/CamBreaker1.png");

      invisibleMan = loadImage("images/InvisibleMan1.png");

      runner = loadImage("images/Runner1.png");



      background = loadImage("images/grass.bmp");
      obstacle = loadImage("images/vein.bmp");
      goal = loadImage("images/water.bmp");
      blackScreen = loadImage("images/blackScreen.bmp");
      menuScreen = loadImage("images/menuScreen.png");


      //for each room
      wall = obstacle;//loadImage("images/wall_tile.png");//Room.getFilenameOfType(Room.WALL));
      floor = goal; //loadImage("images/skull_floor_tile.png");
      door = blackScreen;//loadImage("images/camera_screen_icon.gif");
      camScreen = background;//loadImage("images/camera_screen_icon.gif");

      level = new Level(camBreaker, invisibleMan, runner);
      grid = new GridValues[ROWS][COLS];
      initialize_level_grid(grid);



      //creating entities

      p1 = new Player("Bob", new Point(2, 2), level.getRoom(0));
   }

   public void setGrid(GridValues[][] grid)
   {
      this.grid = grid;
   }


   private static void initialize_level_grid(GridValues[][] grid) {
      for (int row = 0; row < grid.length; row++) {
         for (int col = 0; col < grid[row].length; col++) {
            switch (level.getCurrentRoom().getType(col, row)) {
               case Room.WALL:
                  grid[row][col] = GridValues.WALL;
                  break;
               case Room.DOOR:
                  grid[row][col] = GridValues.DOOR;
                  break;
               case Room.CAMSCREEN:
                  grid[row][col] = GridValues.CAMSCREEN;
                  break;
               case Room.FLOOR:
                  grid[row][col] = GridValues.FLOOR;
                  break;
               default:
                  grid[row][col] = GridValues.BLACKSCREEN;
                  break;
            }
         }
      }
   }

   public static void initialize_black_screen(GridValues[][] grid)
   {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            grid[row][col] = GridValues.BLACKSCREEN;
         }
      }
   }


   public void draw()
   {
      if(game) {
         long time = System.currentTimeMillis();


         draw_grid();


         if(level.getCurrentRoom().isBlackScreen())
         {
            initialize_black_screen(grid);
         }
         else
         {
            initialize_level_grid(grid);
         }


         //NEED TO CHANGE THIS TO DO WITH THE LIST
         if (((Runner) level.getEntities()[2]).isRunning()) {
            ((Runner) level.getEntities()[2]).running();
            if (changeWorlds(((Runner) level.getEntities()[2]))) {
               ((Runner) level.getEntities()[2]).setRunning();
            }
         }


         //EACH SECOND PASSED
         if (next_time < time) {
            next_time = time + SECOND;
            System.out.println(currentSec);

            for (Entity entity : level.getEntities()) {
               if(!entity.getRoom().equals(level.getRoom(0)))
               {
                  //IN ENTITY ROOMS
                  if (entity.getClass().equals(Runner.class)) {
                     if (!((Runner) entity).isRunning()) {
                        entity.randomMovementTimer();
                        if (entity.getRoom().equals(this.level.getCurrentRoom())) {
                           entity.stareDecrease();
                        }
                     }
                  } else if(entity.getClass().equals(CamBreaker.class) &&
                          entity.getRoom().isBlackScreen())
                  {
                     ((CamBreaker)entity).blackScreenTimer();
                  }
                  else
                  {
                     entity.randomMovementTimer();
                     if (entity.getRoom().equals(this.level.getCurrentRoom())) {
                        entity.stareDecrease();
                     }
                  }
               }
               else
               {
                  //INSIDE END ROOM
                  if(((RoomHome)entity.getRoom()).ending())
                  {
                     game = false;
                     setup();
                     System.out.println("You died to " + entity.getClass());
                  }
               }
            }

            currentSec += 1;
         }

         //printing player
         if(level.getCurrentRoom().equals(p1.getRoom()))
         {
            image(playerImgs.get(p1.getCurrent_image()), p1.getPos().getX() * TILE_SIZE, p1.getPos().getY() * TILE_SIZE);
         }


         //PRINTING ENTITY IN CURRENT VIEWED ROOM
         if(!level.getCurrentRoom().isBlackScreen())
         {
            for (Entity entity : level.getEntities()) {
               if (entity.getRoom().equals(this.level.getCurrentRoom())) {
                  if(!entity.getRoom().equals(level.getRoom(0)))
                  {
                     if (entity.getClass().equals(InvisibleMan.class)) {
                        if (!((InvisibleMan) entity).isInvis()) {
                           image(entity.getImage(), entity.getPos().getX() * TILE_SIZE, entity.getPos().getY() * TILE_SIZE);
                        }
                     } else {
                        image(entity.getImage(), entity.getPos().getX() * TILE_SIZE, entity.getPos().getY() * TILE_SIZE);
                     }
                  }
               }
               changeWorlds(entity);
            }
         }
      }
      else if(!game)
      {
         //MAIN MENU
         image(menuScreen, 0, 0);
      }
   }

   public boolean changeWorlds(Entity entity)
   {
      if(entity.getRoom().isDoor(entity.getPos()))
      {
         level.nextRoom(entity);
         return true;
      }
      return false;
   }
   

   private void draw_grid()
   {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            draw_tile(row, col);
         }
      }
   }



   

   private void draw_tile(int row, int col)
   {
      switch (grid[row][col])
      {
         case BACKGROUND:
            image(background, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case OBSTACLE:
            image(obstacle, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case GOAL:
            image(goal, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case BLACKSCREEN:
            image(blackScreen, col * TILE_SIZE, row * TILE_SIZE);
            break;
            //FOR ROOMS
         case WALL:
            image(wall, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case FLOOR:
            image(floor, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case DOOR:
            image(door, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case CAMSCREEN:
            image(camScreen, col * TILE_SIZE, row * TILE_SIZE);
            break;
      }
   }

   public static void main(String args[])
   {
      PApplet.main("VirtualMain");
   }

   public void keyPressed()
   {
      if(((key == 'w' || key == 'a' ||
              key == 's' || key == 'd')) && level.getCurrentRoom().equals(p1.getRoom()))
      {
         p1.move(key);
      }
      if(level.getCurrentRoom().equals(level.getRoom(0)))
      {
         if(key == ' ' && ((RoomHome)p1.getRoom()).getDoorButton().equals(p1.getPos()))
         {
            ((RoomHome)level.getCurrentRoom()).leftEnd();
            for(Entity entity : level.getEntities())
            {
               if(entity.getRoom().equals(level.getCurrentRoom()))
               {
                  Random rand = new Random();
                  int randRoom = rand.nextInt(2) + 1;
                  entity.setRoom(level.getRoom(randRoom));
                  System.out.println(entity + "got kicked out");
               }
            }
         }
         else if(key == ' ' && ((RoomHome)p1.getRoom()).getCameraButton().equals(p1.getPos()))
         {
            level.setRoom(1);
         }
      }
      else
      {
         if(key == ' ')
         {
            level.setRoom(0);
         }
      }
   }




   public void mousePressed()
   {
      if(game)
      {
         Point pressed = mouseToPoint(mouseX, mouseY);
         if(pressed.equals(level.getCurrentRoom().getDoor(0)) ||
                 pressed.equals(level.getCurrentRoom().getDoor(1)))
         {
            level.setRoom(level.getClickedRoom(pressed));
         }
      }
      else
      {
         //System.out.println(mouseX + ", " + mouseY);
         if(mouseX >= 37 && mouseX <= 600 &&
            mouseY >= 37 && mouseY <= 180)
         {
            game = true;
         }
         else if(mouseX >= 37 && mouseX <= 600 &&
                 mouseY >= 275 && mouseY <= 415)
         {
            System.out.println("Exit");
         }
      }

     redraw();
      
   }

   private Point mouseToPoint(int x, int y)
   {
      return new Point(mouseX/TILE_SIZE, mouseY/TILE_SIZE);
   }
}
