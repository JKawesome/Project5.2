import java.util.List;
import java.util.ArrayList;

import processing.core.*;

public class VirtualMain extends PApplet
{
   //GAMEMODES
   private boolean game = false;

   //IMAGES
   private long next_time = 0;
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
   private int currentSec = 0;


   //Grid portion
   private static final int TILE_SIZE = 32;
   public static enum GridValues { BACKGROUND, OBSTACLE, GOAL, BLACKSCREEN, WALL, FLOOR, DOOR, CAMSCREEN};
   private GridValues[][] grid;
   private static final int ROWS = 15;
   private static final int COLS = 20;


   //creating worlds/levels
//   private World1 world1 = new World1(ROWS, COLS);
//   private World2 world2 = new World2(ROWS, COLS);
//   private Room currentRoom;

   //creating levels
   private static Level level;

   //for rooms
   private PImage wall, floor, door, camScreen;


//for now creating player
   Player p1;

   //for now creating CamBreaker
   Entity cB;

   //creating invisman
   Entity iM;

   //creating runner
   Entity run;


   private Point goalPos;

   public void settings()
   {
      size(640, 480);
   }

   public void setup()
   {
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
      initialize_grid(grid);



      //creating entities

      p1 = new Player("Bob", new Point(2, 2), level.getRoom(0));
   }

   public void setGrid(GridValues[][] grid)
   {
      this.grid = grid;
   }

   private static void initialize_grid(GridValues[][] grid)
   {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            grid[row][col] = GridValues.BACKGROUND;
         }
      }

      for (int row = 2; row < 8; row++)
      {
         grid[row][row + 5] = GridValues.OBSTACLE;
      }

      for (int row = 8; row < 12; row++)
      {
         grid[row][19 - row] = GridValues.OBSTACLE;
      }

      for (int col = 1; col < 8; col++)
      {
         grid[11][col] = GridValues.OBSTACLE;
      }

      grid[13][14] = GridValues.GOAL;
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


   private static void initialize_grid2(GridValues[][] grid)
   {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            grid[row][col] = GridValues.BACKGROUND;
         }
      }

      for (int row = 2; row < 8; row++)
      {
         grid[row][row + 5] = GridValues.OBSTACLE;
      }

      for (int row = 8; row < 12; row++)
      {
         grid[row][19 - row] = GridValues.OBSTACLE;
      }

      for (int col = 1; col < 8; col++)
      {
         grid[11][col] = GridValues.OBSTACLE;
      }

      grid[12][12] = GridValues.GOAL;
      grid[13][15] = GridValues.GOAL;
   }

   public void draw()
   {
      if(game) {
         long time = System.currentTimeMillis();

         draw_grid();

         //NEED TO CHANGE THIS TO DO WITH THE LIST
//         if (((Runner) run).isRunning()) {
//            ((Runner) run).running();
//            if (changeWorlds(run)) {
//               ((Runner) run).setRunning();
//            }
//         }

         //EACH SECOND PASSED
         if (next_time < time) {
            next_time = time + SECOND;
            System.out.println(currentSec);
            System.out.println(p1.getPos());


            for (Entity entity : level.getEntities()) {
               if (entity.getClass().equals(Runner.class)) {
                  if (!((Runner) entity).isRunning()) {
                     entity.randomMovementTimer();
                     if (entity.getRoom().equals(this.level.getCurrentRoom())) {
                        entity.stareDecrease();
                     }
                  }
               } else {
                  entity.randomMovementTimer();
                  if (entity.getRoom().equals(this.level.getCurrentRoom())) {
                     entity.stareDecrease();
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



         for (Entity entity : level.getEntities()) {
            if (entity.getRoom().equals(this.level.getCurrentRoom())) {
               if (entity.getClass().equals(InvisibleMan.class)) {
                  if (!((InvisibleMan) entity).isInvis()) {
                     image(entity.getImage(), entity.getPos().getX() * TILE_SIZE, entity.getPos().getY() * TILE_SIZE);
                  }
               } else {
                  image(entity.getImage(), entity.getPos().getX() * TILE_SIZE, entity.getPos().getY() * TILE_SIZE);
               }
            }
            changeWorlds(entity);
         }
      }
      else if(game == false)
      {
         //MAIN MENU
         image(menuScreen, 0, 0);
      }
   }

   public boolean changeWorlds(Entity entity)
   {
      //CHANGE THIS LATER YEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
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
      if(key == 'u')
      {
         level.setRoom(0);
      }
      if(key == 'i')
      {
         level.setRoom(1);
      }
      if(key == 'o')
      {
         level.setRoom(2);
      }
      if(key == 'p')
      {
         level.setRoom(3);
      }

   }




   public void mousePressed()
   {
      if(game)
      {
         Point pressed = mouseToPoint(mouseX, mouseY);

         if (grid[pressed.getY()][pressed.getX()] == GridValues.OBSTACLE)
            grid[pressed.getY()][pressed.getX()] = GridValues.BACKGROUND;
         else if (grid[pressed.getY()][pressed.getX()] == GridValues.BACKGROUND)
            grid[pressed.getY()][pressed.getX()] = GridValues.OBSTACLE;
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
