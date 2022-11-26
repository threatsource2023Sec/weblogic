package javax.realtime;

public class MemoryArea {
   static final MemoryArea DEFAULT = new MemoryArea();

   private MemoryArea() {
   }

   public static MemoryArea getMemoryArea(Object var0) {
      return DEFAULT;
   }

   public void executeInArea(Runnable var1) {
      var1.run();
   }

   public Object newInstance(Class var1) throws InstantiationException, IllegalAccessException {
      return var1.newInstance();
   }
}
