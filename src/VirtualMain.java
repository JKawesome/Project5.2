import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import processing.core.*;

public class VirtualMain extends PApplet
{

   //GAMEMODES
   private boolean game = false;

   //time position
   private final int TIME_POSX = 545;
   private final int TIME_POSY = 17;

   //IMAGES
   private long next_time;
   private PImage background;
   private PImage obstacle;
   private PImage goal;
   private PImage blackScreen;
   private PImage whiteScreen;
   private PImage menuScreen;

   private PImage bombing;

   private PImage camBreaker;
   private PImage invisibleMan;
   private PImage runner;
   private List<PImage> playerImgs;

   //TIME
   private final int SECOND = 1000;
   private int currentSec;


   //Grid portion
   private static final int TILE_SIZE = 32;
   private ArrayList<Object> wallColors;


   public static enum GridValues { BACKGROUND, OBSTACLE, GOAL, BLACKSCREEN, WALL, FLOOR, DOOR, CAMSCREEN, WALLCOLOR, BOMBING, WHITESCREEN};
   private GridValues[][] grid;
   private static final int ROWS = 15;
   private static final int COLS = 20;


   //creating levels
   private static Level level;

   //for rooms
   private PImage wall, floor, door, camScreen;


   //for now creating player
   private Player p1;


   //for explosion
   private List<PImage> explosionImages;
   private int currMouseX;
   private int currMouseY;
   private boolean explosion = false;
   private int explosionIndex = 0;


   //for ENERGYBAR
   private List<PImage> energyBar;
   private final int EXPLOSION_ENERGY = 5;
   private final int CAMERA_ENERGY = 1;

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


      bombing = loadImage("images/BulletRedsmall.png");


      background = loadImage("images/grass.bmp");
      obstacle = loadImage("images/vein.bmp");
      goal = loadImage("images/water.bmp");
      blackScreen = loadImage("images/blackScreen.bmp");
      whiteScreen = loadImage("images/whiteScreen.bmp");
      menuScreen = loadImage("images/menuScreen.png");


      //for each room
      wall = loadImage("images/wall_tile.png");//Room.getFilenameOfType(Room.WALL));
      floor = loadImage("images/skull_floor_tiles.png");
      door = loadImage("images/castledoors.png");
      camScreen = loadImage("images/camera_screen_icon.png");

      //all the explosions
      explosionImages = new ArrayList<>();
      explosionImages.add(loadImage("images/e1.png"));
      explosionImages.add(loadImage("images/e2.png"));
      explosionImages.add(loadImage("images/e3.png"));
      explosionImages.add(loadImage("images/e4.png"));
      explosionImages.add(loadImage("images/e5.png"));
      explosionImages.add(loadImage("images/e6.png"));
      explosionImages.add(loadImage("images/e7.png"));
      explosionImages.add(loadImage("images/e8.png"));
      explosionImages.add(loadImage("images/e9.png"));
      explosionImages.add(loadImage("images/e10.png"));

      wallColors = new ArrayList<>();
      wallColors.add(loadImage("images/c1.png"));
      wallColors.add(loadImage("images/c2.png"));
      wallColors.add(loadImage("images/c3.png"));
      wallColors.add(loadImage("images/c4.png"));
      wallColors.add(loadImage("images/c5.png"));
      wallColors.add(loadImage("images/c6.png"));
      wallColors.add(loadImage("images/c7.png"));
      wallColors.add(loadImage("images/c8.png"));

      //ENERGY BAR
      energyBar = new ArrayList<>();
      energyBar.add(loadImage("images/Energy1.png"));
      energyBar.add(loadImage("images/Energy2.png"));
      energyBar.add(loadImage("images/Energy3.png"));
      energyBar.add(loadImage("images/Energy4.png"));
      energyBar.add(loadImage("images/Energy5.png"));


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
               case Room.WALLCOLOR:
                  grid[row][col] = GridValues.WALLCOLOR;
                  break;
               case Room.BOMBING:
                  grid[row][col] = GridValues.BOMBING;
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
         text("Time: " + currentSec, TIME_POSX, TIME_POSY);


         image(energyBar.get(level.getEnergyIndex()),
                 level.getEnergyLocation().getX(), level.getEnergyLocation().getY());

         if(explosion)
         {
            image(explosionImages.get(explosionIndex), currMouseX, currMouseY);
            explosionIndex += 1;
            if(explosionIndex >= explosionImages.size())
            {
               explosion = false;
               explosionIndex = 0;
            }
         }

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

            //if in entity room, decrease cam timer
            if(!level.getCurrentRoom().equals(level.getRoom(0)))
            {
               if(level.decreaseEnergy(CAMERA_ENERGY))
               {
                  game = false;
                  setup();
                  System.out.println("You died to lack of energy");
               }
            }

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
         if(!level.getCurrentRoom().isBlackScreen() && !level.getCurrentRoom().equals(level.getRoom(0)))
         {
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
         case BOMBING:
            image(bombing, col  * TILE_SIZE, row * TILE_SIZE);
            break;
            //FOR EXPLOSION
         case WHITESCREEN:
            image(whiteScreen, col * TILE_SIZE, row * TILE_SIZE);
            break;
         case WALLCOLOR:
            image((PImage) wallColors.get(((RoomHome)level.getRoom(0)).getColor(row,col)-1),col * TILE_SIZE, row * TILE_SIZE);

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
         if(key == ' ' && ((RoomHome)p1.getRoom()).getCameraButton().equals(p1.getPos()))
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


         if(level.getCurrentRoom().equals(level.getRoom(0)))
         {
            if(((RoomHome)p1.getRoom()).getDoorButton().equals(p1.getPos()))
            {
               //creates explosion
               explosion = true;
               currMouseX = mouseX- 2*TILE_SIZE + TILE_SIZE/2;
               currMouseY = mouseY- 2*TILE_SIZE + TILE_SIZE/2;

               if(level.decreaseEnergy(EXPLOSION_ENERGY))
               {
                  game = false;
                  setup();
                  System.out.println("You died to lack of energy");
               }

               //suppose to create white spots and despawns entity
               for(Entity entity : level.getEntities())
               {
                  if(entity.getRoom().equals(level.getCurrentRoom()))
                  {
                     if(entity.getPos().equals(pressed))
                     {
                        drawWhiteSpot(entity.getPos());
                        Random rand = new Random();
                        int randRoom = rand.nextInt(2) + 1;
                        entity.setRoom(level.getRoom(randRoom));
                        entity.setMoving(true);
                        entity.makeBoxed();
                        System.out.println(entity + " got kicked out");



                        Point[] colorLocs = ((RoomHome) level.getRoom(0)).explosionOfColors(pressed);

                        for(Point  p:colorLocs){
                           level.getRoom(0).setBackground(p.getX(),p.getY(), Room.WALLCOLOR);
                        }

                        ((RoomHome)level.getCurrentRoom()).leftEnd();
                     }
                  }
               }
            }
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


   private void drawWhiteSpot(Point p)
   {
      for (int row = Math.max(0, p.getX() - 1); row < Math.min(grid.length, p.getX()); row++)
      {
         for (int col = Math.max(0, p.getY()); col < Math.min(grid[row].length, p.getY()); col++)
         {
            //FIX THIS
            level.getCurrentRoom().setBackground(row, col, Room.WHITESCREEN);
         }
      }
   }
}
