package weblogic.rmi.internal;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.spi.AsyncCallback;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.spi.OutboundResponse;

public class AsyncResultImpl implements AsyncResult, AsyncCallback {
   protected boolean objectRetrieved;
   protected InboundResponse inboundResponse;
   private InboundRequest request;
   private OutboundResponse response;
   protected Throwable throwable;
   protected Object result;
   private long timeOut = -1L;

   public AsyncResultImpl() {
   }

   public AsyncResultImpl(InboundRequest ir, OutboundResponse or) {
      this.request = ir;
      this.response = or;
   }

   protected synchronized Object getResults() throws Throwable {
      if (!this.hasResults()) {
         if (this.timeOut == -1L) {
            this.wait();
         } else {
            this.wait(this.timeOut);
         }

         if (!this.hasResults()) {
            throw new RequestTimeoutException();
         }
      }

      if (this.throwable != null) {
         throw this.throwable;
      } else {
         return this.inboundResponse.unmarshalReturn();
      }
   }

   public synchronized boolean hasResults() {
      return this.inboundResponse != null || this.throwable != null;
   }

   public final void setTimeOut(long msecs) {
      this.timeOut = msecs;
   }

   public final void setResult(Object o) throws RemoteException {
      this.result = o;
      this.objectRetrieved = true;
      if (this.response != null) {
         try {
            this.response.transferThreadLocalContext(this.request);
            MsgOutput sos = this.response.getMsgOutput();
            if (sos != null) {
               sos.writeObject(o, o == null ? null : o.getClass());
            }
         } catch (IOException var3) {
            throw new MarshalException("error marshalling return", var3);
         }
      }
   }

   public final Object getObjectNoWait() throws Throwable {
      return this.result;
   }

   public Object getObject() throws Throwable {
      if (this.objectRetrieved) {
         return this.result;
      } else {
         this.objectRetrieved = true;

         try {
            this.result = this.getResults();
         } finally {
            this.closeResponse();
         }

         return this.result;
      }
   }

   public synchronized void setThrowable(Throwable throwable) {
      this.throwable = throwable;
      this.notify();
   }

   protected synchronized void closeResponse() throws IOException {
      if (this.inboundResponse != null) {
         this.inboundResponse.close();
      }

   }

   public synchronized void setInboundResponse(InboundResponse inboundResponse) {
      this.inboundResponse = inboundResponse;
      this.notify();
   }

   protected Object throwUserDefinedException(Throwable throwable, Class exceptionClass, Constructor constructor) {
      Object exception;
      if (throwable instanceof RequestTimeoutException) {
         exception = new TimeoutException();
      } else {
         exception = new RuntimeException();
      }

      ((Exception)exception).initCause(throwable);
      if (exceptionClass != null && constructor != null) {
         try {
            return constructor.newInstance(exception);
         } catch (InstantiationException var6) {
         } catch (IllegalAccessException var7) {
         } catch (Throwable var8) {
         }
      }

      return exception;
   }
}
