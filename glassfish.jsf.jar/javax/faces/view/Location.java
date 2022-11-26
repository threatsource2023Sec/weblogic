package javax.faces.view;

import java.io.Serializable;

public class Location implements Serializable {
   private static final long serialVersionUID = -1962991571371912405L;
   private final String path;
   private final int line;
   private final int column;

   public Location(String path, int line, int column) {
      this.path = path;
      this.line = line;
      this.column = column;
   }

   public int getColumn() {
      return this.column;
   }

   public int getLine() {
      return this.line;
   }

   public String getPath() {
      return this.path;
   }

   public String toString() {
      return this.path + " @" + this.line + "," + this.column;
   }
}
