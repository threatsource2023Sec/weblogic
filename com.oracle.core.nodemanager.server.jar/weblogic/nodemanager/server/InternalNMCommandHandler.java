package weblogic.nodemanager.server;

import java.io.InputStream;
import java.io.OutputStream;
import weblogic.nodemanager.common.NMInputOutput;

public class InternalNMCommandHandler {
   private Handler handler;
   private NMInputOutput ioHandler;

   public InternalNMCommandHandler(NMServer nmServer, OutputStream grizzlyOut, InputStream grizzlyIn) {
      this(nmServer, new NMInputOutput(grizzlyIn, grizzlyOut));
   }

   public InternalNMCommandHandler(NMServer nmServer, NMInputOutput sNm) {
      this.handler = new Handler(nmServer, sNm, true);
      this.ioHandler = sNm;
   }

   public void processRequests() {
      this.handler.run();
   }

   public void runCommand() {
      this.handler.runCommand();
   }

   public NMInputOutput getIoHandler() {
      return this.ioHandler;
   }
}
