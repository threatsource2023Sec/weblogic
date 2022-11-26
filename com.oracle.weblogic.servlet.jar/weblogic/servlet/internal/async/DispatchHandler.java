package weblogic.servlet.internal.async;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.servlet.internal.WebAppServletContext;

class DispatchHandler implements Runnable, ComponentRequest {
   private final AsyncContextImpl async;
   private final AsyncRequestDispatcher dispatcher;
   private final ServletRequest req;
   private final ServletResponse resp;

   DispatchHandler(AsyncContextImpl asyncCtx, AsyncRequestDispatcher dispatcher, ServletRequest request, ServletResponse response) {
      this.async = asyncCtx;
      this.dispatcher = dispatcher;
      this.req = request;
      this.resp = response;
   }

   public void run() {
      ClassLoader classLoader = this.pushOldContextEnvironment();

      try {
         this.dispatcher.dispatch(this.req, this.resp);
         this.async.returnToContainer();
      } catch (Throwable var6) {
         this.async.handleError(var6);
      } finally {
         this.popToOldContextEnvironment(classLoader);
      }

   }

   private ClassLoader pushOldContextEnvironment() {
      return this.async.getServletContext().pushEnvironment(Thread.currentThread());
   }

   private void popToOldContextEnvironment(ClassLoader oldClassLoader) {
      if (oldClassLoader != null) {
         WebAppServletContext.popEnvironment(Thread.currentThread(), oldClassLoader);
      }
   }

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.async.getServletContext().getComponentInvocationContext();
   }
}
