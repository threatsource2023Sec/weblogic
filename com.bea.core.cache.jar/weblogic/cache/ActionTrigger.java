package weblogic.cache;

public interface ActionTrigger {
   Object run();

   void close();
}
