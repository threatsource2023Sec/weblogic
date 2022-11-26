package javolution.realtime;

import javolution.lang.Text;

public interface Realtime {
   boolean move(ObjectSpace var1);

   Text toText();

   public static class ObjectSpace {
      public static final ObjectSpace HEAP = new ObjectSpace();
      public static final ObjectSpace STACK = new ObjectSpace();
      public static final ObjectSpace OUTER = new ObjectSpace();
      public static final ObjectSpace HOLD = new ObjectSpace();
   }
}
