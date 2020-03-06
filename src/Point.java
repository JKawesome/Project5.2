final class Point
{
   private final int x;
   private final int y;

   public Point(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public String toString()
   {
      return "(" + x + "," + y + ")";
   }

   public int getX()
   {
      return x;
   }

   public int getY()
   {
      return y;
   }

   public int hashCode()
   {
      int result = 17;
      result = result * 31 + x;
      result = result * 31 + y;
      return result;
   }

   public boolean adjacent(Point p)
   {
      return (x == p.x && Math.abs(y - p.y) == 1) ||
              (y == p.y && Math.abs(x - p.x) == 1);
   }

   public boolean equals(Object other)
   {
      if(other == null)
      {
         return false;
      }
      if(!other.getClass().equals(this.getClass()))
      {
         return false;
      }
      Point o = (Point)other;

      if(this.x == o.x && this.y == o.y)
      {
         return true;
      }
      return false;
   }
}
