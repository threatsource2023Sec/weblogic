package weblogic.socket;

import weblogic.kernel.ExecuteRequest;
import weblogic.kernel.ExecuteThread;
import weblogic.work.WorkAdapter;

final class SocketReaderRequest extends WorkAdapter implements ExecuteRequest {
   public void run() {
      try {
         SocketMuxer.getMuxer().processSockets();
      } catch (ThreadDeath var2) {
         throw var2;
      } catch (Throwable var3) {
         SocketLogger.logMuxerError(var3.getMessage(), var3);
      }

   }

   public String toString() {
      return "Socket Reader Request";
   }

   public void execute(ExecuteThread thd) throws Exception {
      this.run();
   }
}
