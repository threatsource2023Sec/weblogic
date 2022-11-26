package weblogic.socket;

import java.io.IOException;
import java.net.Socket;
import weblogic.kernel.Kernel;
import weblogic.platform.VM;

class NativeSocketInfo extends SocketInfo {
   private static VM vm;
   protected final int fd = this.initFD();

   public final int getFD() {
      return this.fd;
   }

   NativeSocketInfo(MuxableSocket ms) throws IOException {
      super(ms);
      if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
         SocketLogger.logDebug("Obtained fd=" + this.fd + " for: sock=" + ms.getSocket());
      }

   }

   protected String fieldsToString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append(super.fieldsToString()).append(", ").append("fd = ").append(this.fd);
      return sb.toString();
   }

   private static synchronized void initVM() {
      if (vm == null) {
         vm = VM.getVM();
      }
   }

   protected int initFD() throws IOException {
      try {
         Socket sock = this.ms.getSocket();
         if (sock instanceof WeblogicSocket) {
            sock = ((WeblogicSocket)sock).getSocket();
         }

         if (vm == null) {
            initVM();
         }

         return vm.getFD(sock);
      } catch (IOException var2) {
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
            SocketLogger.logDebug(var2.getMessage());
         }

         throw var2;
      }
   }
}
