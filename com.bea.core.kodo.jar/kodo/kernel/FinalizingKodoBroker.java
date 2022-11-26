package kodo.kernel;

public class FinalizingKodoBroker extends KodoBroker {
   protected void finalize() throws Throwable {
      super.finalize();
      if (!this.isClosed()) {
         this.free();
      }

   }
}
