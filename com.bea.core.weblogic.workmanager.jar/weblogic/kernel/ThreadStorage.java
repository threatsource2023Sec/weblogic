package weblogic.kernel;

public interface ThreadStorage {
   Object UNINITIALIZED = new Object();

   Object get(int var1);

   void reset();

   void set(int var1, Object var2);
}
