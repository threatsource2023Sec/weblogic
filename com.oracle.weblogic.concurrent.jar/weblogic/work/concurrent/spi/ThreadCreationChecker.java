package weblogic.work.concurrent.spi;

public interface ThreadCreationChecker {
   void acquire() throws RejectException;

   void undo();
}
