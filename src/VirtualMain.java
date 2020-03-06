import java.util.List;
import java.util.ArrayList;

import processing.core.*;

public class VirtualMain extends PApplet
{
   //IMAGES
   private int current_image;
   private long next_time;
   private PImage background;
   private PImage obstacle;
   private PImage goal;
   private PImage blackScreen;
   private PImage camBreaker;
   private List<PImage> playerImgs;

   //private boolean blackScreenTime = false;

   //TIME
   private final int SECOND = 1000;
   private int currentSec = 0;


   //CURRENT VIEWLEVEL


   //Grid portion
   private static final int TILE_SIZE = 32;
   private static enum GridValues { BACKGROUND, OBSTACLE, GOAL, BLACKSCREEN};
   private GridValues[][] grid;
   private static final int ROWS = 15;
   private static final int COLS = 20;


   //creating worlds/levles
   private World1 world1 = new World1(ROWS, COLS);
   private World2 world2 = new World2(ROWS, COLS);
   private Worlds currentWorld;


//   private Point wPos;
//for now creating player
   Player p1;

   //for now creating CamBreaker
   CamBreaker cB;


   private Point goalPos;
   private boolean foundPath = false;

   public void settings()
   {
      size(640, 480);
   }

   public void setup()
   {
      currentWorld = world1;
      //creating entities
      p1 = new Player("Bob", new Point(2, 2));
      cB = new CamBreaker("CamBreaker", new Point(4, 4), world1);


      goalPos = new Point(14, 13);


      playerImgs = new ArrayList<>();
      playerImgs.add(loadImage("images/player2.png"));
      playerImgs.add(loadImage("images/player1.png"));
      playerImgs.add(loadImage("images/player3.png"));
      playerImgs.add(loadImage("images/player4.png"));

      camBreaker = loadImage("images/CamBreaker1.png");



      background = loadImage("images/grass.bmp");
      obstacle = loadImage("images/vein.bmp");
      goal = loadImage("images/water.bmp");
      blackScreen = loadImage("images/blackScreen.bmp");

      grid = new GridValues[ROWS][COLS];
      initialize_grid(grid);

      current_image = 1;
      next_time = 0;
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

//   private void initialize_black_screen(GridValues[][] grid)
//   {
//      this.blackScreenTime = true;
//      for (int row = 0; row < grid.length; row++)
//      {
//         for (int col = 0; col < grid[row].length; col++)
//         {
//            grid[row][col] = GridValues.BLACKSCREEN;
//         }
//      }
//   }


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
      long time = System.currentTimeMillis();
      
      draw_grid();

      //EACH SECOND PASSED
      if(next_time < time)
      {
         next_time = time + SECOND;
         System.out.println(currentSec);
         System.out.println(p1.getPos());


         cB.randomMovementTimer();
         currentSec += 1;
      }

      //printing player
      image(playerImgs.get(p1.getCurrent_image()), p1.getPos().getX() * TILE_SIZE, p1.getPos().getY() * TILE_SIZE);

      if(cB.getWorld().equals(currentWorld))
      {
         image(camBreaker, cB.getPos().getX() * TILE_SIZE, cB.getPos().getY() * TILE_SIZE);
      }

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
      }
   }

   public static void main(String args[])
   {
      PApplet.main("VirtualMain");
   }

   public void keyPressed()
   {
      if(key == 'w' || key == 'a' ||
              key == 's' || key == 'd')
      {
         p1.move(key, currentWorld);
         if(currentWorld.equals(world1) && p1.getPos().equals(new Point(13, 14)))
         {
            currentWorld = world2;
            p1.setPos(new Point(1, 1));
            initialize_grid2(grid);
         }
         else if(currentWorld.equals(world2) && p1.getPos().equals(new Point(14, 14)))
         {
            currentWorld = world1;
            p1.setPos(new Point(1, 1));
            initialize_grid(grid);
         }
      }
//      else if(key == 'o')
//      {
//         grid = new GridValues[ROWS][COLS];
//         initialize_black_screen(grid);
//      }
   }




   public void mousePressed()
   {
      Point pressed = mouseToPoint(mouseX, mouseY);

     if (grid[pressed.getY()][pressed.getX()] == GridValues.OBSTACLE)
        grid[pressed.getY()][pressed.getX()] = GridValues.BACKGROUND;
     else if (grid[pressed.getY()][pressed.getX()] == GridValues.BACKGROUND)
        grid[pressed.getY()][pressed.getX()] = GridValues.OBSTACLE;

     redraw();
      
   }

   private Point mouseToPoint(int x, int y)
   {
      return new Point(mouseX/TILE_SIZE, mouseY/TILE_SIZE);
   }

//   private boolean findGoal(Point pos, Point goal, GridValues[][] grid, List<Point> path)
//   {
//      List<Point> points;
//
////      while (!neighbors(pos, goal))
////      {
//         points = strategy.computePath(pos, goalPos,
//                              p ->  withinBounds(p, grid) && grid[p.y][p.x] != GridValues.OBSTACLE,
//                              (p1, p2) -> neighbors(p1,p2),
//                              PathingStrategy.CARDINAL_NEIGHBORS);
////                              DIAGONAL_NEIGHBORS)
////                              DIAGONAL_CARDINAL_NEIGHBORS);
//
//         if (points.size() == 0)
//         {
//            System.out.println("No path found");
//            return false;
//         }
//
//         //pos = points.get(0);
//         //path.add(pos);
//         path.addAll(points);
////      }
//
//      return true;
//   }
//
//
//
//   private static boolean neighbors(Point p1, Point p2)
//   {
//      return p1.x+1 == p2.x && p1.y == p2.y ||
//             p1.x-1 == p2.x && p1.y == p2.y ||
//             p1.x == p2.x && p1.y+1 == p2.y ||
//             p1.x == p2.x && p1.y-1 == p2.y;
//   }

//   private static final Function<Point, Stream<Point>> DIAGONAL_NEIGHBORS =
//      point ->
//         Stream.<Point>builder()
//            .add(new Point(point.x - 1, point.y - 1))
//            .add(new Point(point.x + 1, point.y + 1))
//            .add(new Point(point.x - 1, point.y + 1))
//            .add(new Point(point.x + 1, point.y - 1))
//            .build();
//
//
//
//   private static final Function<Point, Stream<Point>> DIAGONAL_CARDINAL_NEIGHBORS =
//      point ->
//         Stream.<Point>builder()
//            .add(new Point(point.x - 1, point.y - 1))
//            .add(new Point(point.x + 1, point.y + 1))
//            .add(new Point(point.x - 1, point.y + 1))
//            .add(new Point(point.x + 1, point.y - 1))
//            .add(new Point(point.x, point.y - 1))
//            .add(new Point(point.x, point.y + 1))
//            .add(new Point(point.x - 1, point.y))
//            .add(new Point(point.x + 1, point.y))
//            .build();
}
