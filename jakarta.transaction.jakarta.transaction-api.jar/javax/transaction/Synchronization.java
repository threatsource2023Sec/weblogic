package javax.transaction;

public interface Synchronization {
   void beforeCompletion();

   void afterCompletion(int var1);
}
