package weblogic.servlet.internal;

import java.security.PrivilegedAction;
import javax.servlet.AsyncContext;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.servlet.internal.async.AsyncContextImpl;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.work.WorkManagerFactory;

public abstract class AbstractNIOListenerContext {
   protected volatile NIOListenerState currentState = null;
   protected WebAppServletContext context = null;
   protected Throwable errorInfo = null;
   private AsyncContextImpl asyncContext = null;
   private NIOEventProcessor processor = null;

   AbstractNIOListenerContext(WebAppServletContext context) {
      this.context = context;
      this.processor = new NIOEventProcessor(this);
   }

   protected AsyncContextImpl getAsyncContext() {
      return this.asyncContext;
   }

   protected void setAsyncContext(AsyncContext asyncContext) {
      this.asyncContext = (AsyncContextImpl)asyncContext;
   }

   protected WebAppServletContext getContext() {
      return this.context;
   }

   public NIOListenerState getCurrentState() {
      return this.currentState;
   }

   protected Throwable getErrorInfo() {
      return this.errorInfo;
   }

   protected void setErrorInfo(Throwable errorInfo) {
      this.errorInfo = errorInfo;
   }

   protected void setState(NIOListenerState newState) {
      this.currentState = newState;
   }

   public void scheduleProcess() {
      WorkManagerFactory.getInstance().getDefault().schedule(this.processor);
   }

   public void process() {
      this.processor.run();
   }

   class NIOEventProcessor implements Runnable, ComponentRequest {
      private AbstractNIOListenerContext listenerContext = null;

      public NIOEventProcessor(AbstractNIOListenerContext listenerContext) {
         this.listenerContext = listenerContext;
      }

      public void run() {
         PrivilegedAction action = new PrivilegedAction() {
            public Object run() {
               Thread thread = Thread.currentThread();
               ClassLoader oldClassLoader = AbstractNIOListenerContext.this.context.pushEnvironment(thread);

               Throwable var4;
               try {
                  AbstractNIOListenerContext.this.currentState.handleEvent(NIOEventProcessor.this.listenerContext);
                  return null;
               } catch (IllegalStateException var9) {
                  throw var9;
               } catch (Throwable var10) {
                  AbstractNIOListenerContext.this.currentState.handleError(NIOEventProcessor.this.listenerContext, var10);
                  var4 = var10;
               } finally {
                  WebAppServletContext.popEnvironment(thread, oldClassLoader);
               }

               return var4;
            }
         };
         WebAppSecurity.getProvider().getAnonymousSubject().run(action);
      }

      public ComponentInvocationContext getComponentInvocationContext() {
         return AbstractNIOListenerContext.this.getContext().getComponentInvocationContext();
      }
   }
}
