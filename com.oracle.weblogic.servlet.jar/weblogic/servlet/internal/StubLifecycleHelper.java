package weblogic.servlet.internal;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.UnavailableException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.http.FutureResponseModel;
import weblogic.utils.collections.StackPool;

public final class StubLifecycleHelper {
   private final ServletStubImpl stub;
   private final Class clazz;
   private final boolean isSingleThreadModel;
   private final boolean isFutureResponseServlet;
   private final ClassLoader contextLoader;
   private Servlet theServlet;
   private StackPool servletPool;
   private UnavailableException unavailException;
   private long unavailTime;

   StubLifecycleHelper(ServletStubImpl s, Class c, ClassLoader cl) throws ServletException {
      this(s, c, (Servlet)null, cl);
   }

   StubLifecycleHelper(ServletStubImpl s, Servlet servlet, ClassLoader cl) throws ServletException {
      this(s, (Class)null, servlet, cl);
   }

   private StubLifecycleHelper(ServletStubImpl s, Class c, Servlet servlet, ClassLoader cl) throws ServletException {
      this.stub = s;
      this.clazz = servlet == null ? c : servlet.getClass();
      this.contextLoader = cl;
      this.isSingleThreadModel = SingleThreadModel.class.isAssignableFrom(this.clazz) && servlet == null;
      this.isFutureResponseServlet = FutureResponseModel.class.isAssignableFrom(this.clazz);
      if (this.isSingleThreadModel) {
         WebAppConfigManager configManager = this.stub.getContext().getConfigManager();
         int size = configManager.getSingleThreadedServletPoolSize();
         this.servletPool = new StackPool(size);

         for(int i = 0; i < size; ++i) {
            Servlet result = this.createOneInstance(this.clazz, (Servlet)null);
            this.servletPool.add(result);
         }
      } else {
         this.theServlet = this.createOneInstance(this.clazz, servlet);
      }

   }

   private synchronized Servlet createOneInstance(Class clazz, Servlet servlet) throws ServletException {
      Thread thread = Thread.currentThread();
      ClassLoader cl = null;
      if (thread.getContextClassLoader() != this.contextLoader) {
         cl = this.stub.getContext().pushEnvironment(thread);
      }

      Servlet var5;
      try {
         if (servlet == null) {
            var5 = this.stub.getSecurityHelper().createServlet(clazz);
            return var5;
         }

         var5 = this.stub.getSecurityHelper().createServlet(servlet);
      } catch (UnavailableException var10) {
         this.unavailException = var10;
         int unavailEstimate = this.unavailException.getUnavailableSeconds();
         if (unavailEstimate > 0) {
            this.unavailTime = System.currentTimeMillis() + (long)(unavailEstimate * 1000);
         } else {
            this.unavailTime = System.currentTimeMillis() + 60000L;
         }

         throw var10;
      } finally {
         if (cl != null) {
            WebAppServletContext.popEnvironment(thread, cl);
         }

      }

      return var5;
   }

   public synchronized void makeUnavailable(UnavailableException ue) {
      this.unavailException = ue;
      int unavailEstimate = this.unavailException.getUnavailableSeconds();
      if (unavailEstimate > 0) {
         this.unavailTime = System.currentTimeMillis() + (long)(unavailEstimate * 1000);
      } else {
         this.unavailTime = System.currentTimeMillis() + 60000L;
      }

   }

   public int getPoolCapacity() {
      return this.isSingleThreadModel ? this.servletPool.capacity() : 0;
   }

   public Servlet getServlet() throws ServletException {
      this.checkIfUnavailable();
      if (this.isSingleThreadModel) {
         Servlet s = (Servlet)this.servletPool.remove();
         return s != null ? s : this.createOneInstance(this.clazz, (Servlet)null);
      } else {
         return this.theServlet;
      }
   }

   private void checkIfUnavailable() throws UnavailableException {
      if (this.unavailException != null) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug(this.stub.getContext().getLogContext() + ": servlet " + this.stub.getServletName() + " is unavaialable", this.unavailException);
         }

         if (this.unavailTime > System.currentTimeMillis()) {
            HTTPLogger.logTimeUnavailable(this.stub.getContext().getLogContext(), this.stub.getServletName(), this.unavailTime - System.currentTimeMillis() / 1000L);
            throw this.unavailException;
         }

         this.unavailException = null;
      }

   }

   public final void returnServlet(Servlet s) {
      if (this.isSingleThreadModel) {
         if (this.clazz == s.getClass() && this.servletPool.add(s)) {
            return;
         }

         this.stub.getSecurityHelper().destroyServlet(s);
      }

   }

   public synchronized void destroy() {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("destroyServlet: Destroying all servlets named: '" + this.stub.getServletName() + "'");
      }

      if (this.isSingleThreadModel) {
         while(!this.servletPool.isEmpty()) {
            this.destroyOneInstance((Servlet)this.servletPool.remove());
         }
      } else {
         this.destroyOneInstance(this.theServlet);
      }

   }

   private void destroyOneInstance(Servlet s) {
      Thread thread = Thread.currentThread();
      ClassLoader cl = thread.getContextClassLoader();
      thread.setContextClassLoader(this.contextLoader);

      try {
         this.stub.getSecurityHelper().destroyServlet(s);
      } catch (NullPointerException var8) {
         HTTPLogger.logNPEDuringServletDestroy(this.stub.getContext().getLogContext(), this.clazz.getName(), var8);
      } finally {
         thread.setContextClassLoader(cl);
      }

   }

   public boolean isSingleThreadModel() {
      return this.isSingleThreadModel;
   }

   public boolean isFutureResponseServlet() {
      return this.isFutureResponseServlet;
   }

   public ClassLoader getContextLoader() {
      return this.contextLoader;
   }
}
