package weblogic.rmi.internal;

import java.io.IOException;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.utils.NestedException;
import weblogic.utils.StackTraceUtilsClient;

public class BasicFutureResponse implements FutureResponse, OutboundResponse {
   private final OutboundResponse response;
   private final InboundRequest request;
   private boolean txSet;
   private Throwable sendTxTrace = null;

   public BasicFutureResponse(InboundRequest req, OutboundResponse res) {
      this.request = req;
      this.response = res;
   }

   public MsgOutput getMsgOutput() throws RemoteException {
      this.associateResponseData();
      return this.response.getMsgOutput();
   }

   public void send() throws RemoteException {
      this.associateResponseData();
      this.response.send();

      try {
         this.request.close();
      } catch (IOException var2) {
         throw new MarshalException("failed to close inbound request", var2);
      }
   }

   public void sendThrowable(Throwable problem) {
      Throwable t = problem;
      if (!RMIEnvironment.getEnvironment().printExceptionStackTrace()) {
         StackTraceUtilsClient.scrubExceptionStackTrace(problem);
      }

      try {
         this.associateResponseData();
      } catch (Throwable var4) {
         t = new NestedException(problem);
      }

      BasicServerRef.handleThrowable((Throwable)t, this.response);
   }

   public void close() throws IOException {
   }

   public void setTxContext(Object txContext) throws RemoteException {
      if (this.txSet) {
         throw new IllegalStateException("Attempt to send tx context twice");
      } else {
         this.response.setTxContext(txContext);
         this.txSet = true;
      }
   }

   public void transferThreadLocalContext(InboundRequest request) throws IOException {
   }

   public void setContext(int id, Object data) throws IOException {
   }

   private final void associateResponseData() throws RemoteException {
      if (!this.txSet) {
         if (this.response != null) {
            try {
               this.response.transferThreadLocalContext(this.request);
            } catch (IOException var2) {
               throw new RemoteException(var2.getMessage(), var2);
            }
         }

         this.txSet = true;
      }

   }

   public void setPiggybackResponse(PiggybackResponse piggybackResponse) throws IOException {
      this.response.setPiggybackResponse(piggybackResponse);
   }
}
