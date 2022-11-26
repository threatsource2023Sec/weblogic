package weblogic.t3.srvr;

import java.util.Hashtable;

public class ExecutionContext extends Hashtable {
   private static final String name_separator = "|";
   private static final String primordial_name = "#";
   private static long counter = 1L;
   private static ExecutionContext primordial = new ExecutionContext();
   private String ID;

   public ExecutionContext(String name, ExecutionContext parent) {
      this.ID = null;
      this.ID = parent.getID() + "|" + name;
   }

   public ExecutionContext(String name) {
      this(name, primordial);
   }

   private ExecutionContext() {
      this.ID = null;
      this.ID = "#";
   }

   public static boolean isWSID(String s) {
      return s == null ? false : s.startsWith("#");
   }

   public String getID() {
      return this.ID;
   }

   public String private_putUnique(Object value) {
      long c;
      synchronized(this.getClass()) {
         c = (long)(counter++);
      }

      String key = value.getClass().getName() + "-" + c;
      this.put(key, value);
      return key;
   }

   public String toString() {
      return "[ExecutionContext: " + this.getID() + "]";
   }
}
