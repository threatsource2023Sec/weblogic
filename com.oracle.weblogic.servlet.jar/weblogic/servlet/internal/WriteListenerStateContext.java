package weblogic.servlet.internal;

import javax.servlet.WriteListener;
import weblogic.socket.WriteHandler;

public class WriteListenerStateContext extends AbstractNIOListenerContext implements WriteHandler {
   private WriteListener writeListener = null;
   private ServletOutputStreamImpl output = null;
   private ChunkOutputWrapper chunkOutput = null;

   public WriteListenerStateContext(WriteListener writeListener, ChunkOutputWrapper chunkOutput, ServletOutputStreamImpl output, WebAppServletContext context, AbstractHttpConnectionHandler connHandler) {
      super(context);
      this.writeListener = writeListener;
      this.output = output;
      this.chunkOutput = output.getOutput();
      this.currentState = WriteListenerStates.WRITE_READY;
   }

   protected ServletOutputStreamImpl getOutput() {
      return this.output;
   }

   protected ChunkOutputWrapper getChunkOutput() {
      return this.chunkOutput;
   }

   protected void setErrorState(Throwable t) {
      this.setErrorInfo(t);
      this.setState(WriteListenerStates.ERROR);
   }

   protected WriteListener getWriteListener() {
      return this.writeListener;
   }

   protected boolean isWriteWait() {
      return this.currentState == WriteListenerStates.WRITE_WAIT;
   }

   public boolean isWriteReady() {
      return this.currentState == WriteListenerStates.WRITE_READY;
   }

   protected void setWriteReadyState() {
      this.setState(WriteListenerStates.WRITE_READY);
   }

   protected void setWriteWaitState() {
      this.setState(WriteListenerStates.WRITE_WAIT);
   }

   protected void setFinishedState() {
      this.setState(WriteListenerStates.FINISHED);
   }

   protected void closeWebConnection() {
      if (this.output != null && this.output.isUpgrade() && this.output.getWebConnection() != null) {
         try {
            this.setFinishedState();
            ((WebConnectionImpl)this.output.getWebConnection()).close();
         } catch (Exception var2) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Error happened in WriteListener. invoke close webconnection: " + var2.getMessage(), var2);
            }

            var2.printStackTrace();
         }
      }

   }

   public String toString() {
      StringBuilder buf = new StringBuilder(512);
      buf.append("writeListener = " + this.writeListener);
      buf.append(", output = " + this.output);
      buf.append(", chunkOutput = " + this.chunkOutput);
      buf.append(", currentState = " + this.currentState);
      return buf.toString();
   }

   public void onWritable() throws Exception {
      this.scheduleProcess();
   }

   public void onError(Throwable t) {
      this.setErrorState(t);
      this.process();
   }
}
