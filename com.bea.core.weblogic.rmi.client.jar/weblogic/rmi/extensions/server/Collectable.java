package weblogic.rmi.extensions.server;

public interface Collectable {
   void sweep(long var1);

   void incrementRefCount();

   void decrementRefCount();

   void renewLease();
}
