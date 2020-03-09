import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import processing.core.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class VirtualMain extends PApplet
{



   private static Level level; // todo - probably we make this not static
   private PImage wyvern;
   private int current_image;
   private long next_time;
   private PImage background;
   private PImage obstacle;
   private PImage goal;
   private List<Point> path;
   private PImage blackScreen;

   private PImage wall, floor, door, camScreen;

   private boolean blackScreenTime = false;
   private final int BLACKSTIME = 1000;

   //private PathingStrategy strategy = new SingleStepPathingStrategy();
   private PathingStrategy strategy = new AStarPathingStrategy();


   //BUTTHEAD
   private List<PImage> imgs;
   private List<PImage> playerImgs;
   private static final int ANIMATION_TIME = 100;

   private static final int TILE_SIZE = 32;

   private GridValues[][] grid;
   private static final int ROWS = 15;
   private static final int COLS = 20;


   private static enum GridValues { BACKGROUND, OBSTACLE, GOAL, BLACKSCREEN, WALL, FLOOR, DOOR, CAMSCREEN};

   private Point wPos;
   private Point goalPos;
   private boolean foundPath = false;

   public void settings()
   {
      size(640, 480);
   }

   public void setup()
   {
      path = new LinkedList<>();
      wPos = new Point(2, 2);
      goalPos = new Point(14, 13);

      level = new Level();

//      imgs = new ArrayList<>();
//      imgs.add(loadImage("images/wyvern1.bmp"));
//      imgs.add(loadImage("images/wyvern2.bmp"));
//      imgs.add(loadImage("images/wyvern3.bmp"));
//      imgs.add(loadImage("images/player1.png"));

      playerImgs = new ArrayList<>();
      playerImgs.add(loadImage("images/player2.png"));
      playerImgs.add(loadImage("images/player1.png"));
      playerImgs.add(loadImage("images/player3.png"));
      playerImgs.add(loadImage("images/player4.png"));



      background = loadImage("images/grass.bmp");
      obstacle = loadImage("images/vein.bmp");
      goal = loadImage("images/water.bmp");
      blackScreen = loadImage("images/blackScreen.bmp");

      wall = obstacle;//loadImage("images/wall_tile.png");//Room.getFilenameOfType(Room.WALL));
      floor = goal; //loadImage("images/skull_floor_tile.png");
      door = blackScreen;//loadImage("images/camera_screen_icon.gif");
      camScreen = background;//loadImage("images/camera_screen_icon.gif");



      grid = new GridValues[ROWS][COLS];
      initialize_grid(grid);

      current_image = 1;
      next_time = System.currentTimeMillis() + ANIMATION_TIME;
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

   private void initialize_black_screen(GridValues[][] grid)
   {
      this.blackScreenTime = true;
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

      grid[13][14] = GridValues.GOAL;
      grid[13][15] = GridValues.GOAL;
   }

   private static void initialize_level_grid(GridValues[][] grid)
   {
      for (int row = 0; row < grid.length; row++)
      {
         for (int col = 0; col < grid[row].length; col++)
         {
            switch(level.getRoom(2).getType(col,row)){
               case Room.WALL:
                  grid[row][col] =GridValues.WALL;
                  break;
               case Room.DOOR:
                  grid[row][col] =GridValues.DOOR;
                  break;
               case Room.CAMSCREEN:
                  grid[row][col] =GridValues.CAMSCREEN;
                  break;
               case Room.FLOOR:
                  grid[row][col] =GridValues.FLOOR;
                  break;
               default:
                  grid[row][col] = GridValues.BLACKSCREEN;
                  break;

            }

         }
      }
//
//      for (int row = 2; row < 8; row++)
//      {
//         grid[row][row + 5] = GridValues.OBSTACLE;
//      }
//
//      for (int row = 8; row < 12; row++)
//      {
//         grid[row][19 - row] = GridValues.OBSTACLE;
//      }
//
//      for (int col = 1; col < 8; col++)
//      {
//         grid[11][col] = GridValues.OBSTACLE;
//      }
//
//      grid[13][14] = GridValues.GOAL;
//      grid[13][15] = GridValues.GOAL;
   }

   public void draw()
   {
      // A simplified action scheduling handler
      long time = System.currentTimeMillis();
//      if (time >= next_time)
//      {
//         next_image();
//         next_time = time + ANIMATION_TIME;
//      }

      draw_grid();
      draw_path(foundPath);

      //image(wyvern, wPos.x * TILE_SIZE, wPos.y * TILE_SIZE);
      if(blackScreenTime)
      {
         next_time = time + BLACKSTIME;
         blackScreenTime = false;
      }

      if(next_time < time)
      {
         image(playerImgs.get(current_image), wPos.x * TILE_SIZE, wPos.y * TILE_SIZE);
      }
   }


//   private void next_image()
//   {
//      current_image = (current_image + 1) % imgs.size();
//   }

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

   private void draw_path(boolean foundPath)
   {
      for (Point p : path)
      {
         if (foundPath)
            fill(128, 0, 0);
         else 
            fill(0);

         rect(p.x * TILE_SIZE + TILE_SIZE * 3 / 8,
              p.y * TILE_SIZE + TILE_SIZE * 3 / 8,
              TILE_SIZE / 4, TILE_SIZE / 4);
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
      if (key == ' ')
      {
         path.clear();
         foundPath = findGoal(wPos, goalPos, grid, path);
         redraw();
      }
      else if(key == 'w' || key == 'a' ||
              key == 's' || key == 'd')
      {
         move(key);
      }
      else if(key == 'o')
      {
         grid = new GridValues[ROWS][COLS];
         initialize_black_screen(grid);
      }
      else if(key == 'n'){
         grid = new GridValues[level.getRoom(2).getNumRows()][level.getRoom(2).getNumCols()];
         initialize_level_grid(grid);
      }
   }


   private void move(char dir)
   {
      List<Character> letterList = Arrays.asList('w', 's', 'a', 'd');
      Point newArea;
      if(dir == 'w')
      {
         newArea = new Point(wPos.x, wPos.y - 1);
      }
      else if(dir == 's')
      {
         newArea = new Point(wPos.x, wPos.y + 1);
      }
      else if(dir == 'a')
      {
         newArea = new Point(wPos.x - 1, wPos.y);
      }
      else
      {
         newArea = new Point(wPos.x + 1, wPos.y);
      }

      if (withinBounds(newArea, grid)  &&
              grid[newArea.y][newArea.x] != GridValues.OBSTACLE)
      {
         wPos = newArea;
         current_image = letterList.indexOf(dir);
      }
   }

   public void mousePressed()
   {
      Point pressed = mouseToPoint(mouseX, mouseY);

     if (grid[pressed.y][pressed.x] == GridValues.OBSTACLE)
        grid[pressed.y][pressed.x] = GridValues.BACKGROUND;
     else if (grid[pressed.y][pressed.x] == GridValues.BACKGROUND)
        grid[pressed.y][pressed.x] = GridValues.OBSTACLE;

     redraw();
      
   }

   private Point mouseToPoint(int x, int y)
   {
      return new Point(mouseX/TILE_SIZE, mouseY/TILE_SIZE);
   }

   private boolean findGoal(Point pos, Point goal, GridValues[][] grid, List<Point> path)
   {
      List<Point> points;

//      while (!neighbors(pos, goal))
//      {
         points = strategy.computePath(pos, goalPos,
                              p ->  withinBounds(p, grid) && grid[p.y][p.x] != GridValues.OBSTACLE,
                              (p1, p2) -> neighbors(p1,p2),
                              PathingStrategy.CARDINAL_NEIGHBORS);
//                              DIAGONAL_NEIGHBORS)
//                              DIAGONAL_CARDINAL_NEIGHBORS);

         if (points.size() == 0)
         {
            System.out.println("No path found");
            return false;
         }

         //pos = points.get(0);
         //path.add(pos);
         path.addAll(points);
//      }

      return true;
   }

   private static boolean withinBounds(Point p, GridValues[][] grid)
   {
      return p.y >= 0 && p.y < grid.length &&
         p.x >= 0 && p.x < grid[0].length;
   }

   private static boolean neighbors(Point p1, Point p2)
   {
      return p1.x+1 == p2.x && p1.y == p2.y ||
             p1.x-1 == p2.x && p1.y == p2.y ||
             p1.x == p2.x && p1.y+1 == p2.y ||
             p1.x == p2.x && p1.y-1 == p2.y;
   }

   private static final Function<Point, Stream<Point>> DIAGONAL_NEIGHBORS =
      point ->
         Stream.<Point>builder()
            .add(new Point(point.x - 1, point.y - 1))
            .add(new Point(point.x + 1, point.y + 1))
            .add(new Point(point.x - 1, point.y + 1))
            .add(new Point(point.x + 1, point.y - 1))
            .build();



   private static final Function<Point, Stream<Point>> DIAGONAL_CARDINAL_NEIGHBORS =
      point ->
         Stream.<Point>builder()
            .add(new Point(point.x - 1, point.y - 1))
            .add(new Point(point.x + 1, point.y + 1))
            .add(new Point(point.x - 1, point.y + 1))
            .add(new Point(point.x + 1, point.y - 1))
            .add(new Point(point.x, point.y - 1))
            .add(new Point(point.x, point.y + 1))
            .add(new Point(point.x - 1, point.y))
            .add(new Point(point.x + 1, point.y))
            .build();
}
