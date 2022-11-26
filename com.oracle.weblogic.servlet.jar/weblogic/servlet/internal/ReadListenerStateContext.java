package weblogic.servlet.internal;

import javax.servlet.ReadListener;

public class ReadListenerStateContext extends AbstractNIOListenerContext {
   private ReadListener readListener = null;
   private HttpSocket httpSocket = null;
   private ServletInputStreamImpl inputStream = null;

   public ReadListenerStateContext(ReadListener readListener, ServletInputStreamImpl inputStream, WebAppServletContext context) {
      super(context);
      this.readListener = readListener;
      this.inputStream = inputStream;
      this.currentState = ReadListenerStates.READ_READY;
   }

   protected ReadListener getReadListener() {
      return this.readListener;
   }

   protected HttpSocket getHttpSocket() {
      return this.httpSocket;
   }

   public boolean isReadReady() {
      return this.currentState == ReadListenerStates.READ_READY;
   }

   protected boolean isReadWait() {
      return this.currentState == ReadListenerStates.READ_WAIT;
   }

   protected boolean isReadComplete() {
      return this.currentState == ReadListenerStates.READ_COMPLETE;
   }

   protected boolean isFinished() {
      return this.currentState == ReadListenerStates.FINISHED;
   }

   public void setReadReadyState() {
      this.setState(ReadListenerStates.READ_READY);
   }

   public void setReadCompleteState() {
      this.setState(ReadListenerStates.READ_COMPLETE);
   }

   public void setReadWaitState() {
      this.setState(ReadListenerStates.READ_WAIT);
   }

   public void setErrorState(Throwable t) {
      this.setErrorInfo(t);
      this.setState(ReadListenerStates.ERROR);
   }

   protected void setFinishedState() {
      this.setState(ReadListenerStates.FINISHED);
   }

   protected void setHttpSocket(HttpSocket httpSocket) {
      this.httpSocket = httpSocket;
   }

   protected void closeWebConnection() {
      if (this.inputStream != null && this.inputStream.isUpgrade() && this.inputStream.getWebConnection() != null) {
         try {
            this.setFinishedState();
            ((WebConnectionImpl)this.inputStream.getWebConnection()).close();
         } catch (Exception var2) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Error happened. Invoke ReadListener's onError(). when close webconnection: " + var2.getMessage(), var2);
            }

            var2.printStackTrace();
         }
      }

   }

   public String toString() {
      StringBuilder buf = new StringBuilder(512);
      buf.append("ReadListener = " + this.readListener);
      buf.append(", currentState = " + this.currentState);
      return buf.toString();
   }
}
