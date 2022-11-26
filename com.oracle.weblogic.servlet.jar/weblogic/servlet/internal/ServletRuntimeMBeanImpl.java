package weblogic.servlet.internal;

import java.io.PrintStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServletRuntimeMBean;
import weblogic.protocol.ServerURL;
import weblogic.protocol.configuration.ChannelHelperService;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.utils.URLMapping;
import weblogic.utils.concurrent.atomic.AtomicFactory;
import weblogic.utils.concurrent.atomic.AtomicInteger;
import weblogic.utils.concurrent.atomic.AtomicLong;

public final class ServletRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ServletRuntimeMBean {
   private static final long serialVersionUID = -7688216863798470821L;
   private int reloadCount;
   private final AtomicInteger invokeCount;
   private final AtomicLong totalExecutionTime;
   private long highExecutionTime;
   private long lowExecutionTime;
   private ServletStubImpl stub;

   private ServletRuntimeMBeanImpl(String name, ServletStubImpl ssi) throws ManagementException {
      super(name, ssi.getContext().getRuntimeMBean(), true, "Servlets");
      this.invokeCount = AtomicFactory.createAtomicInteger();
      this.totalExecutionTime = AtomicFactory.createAtomicLong();
      this.lowExecutionTime = -1L;
      this.stub = ssi;
   }

   public static ServletRuntimeMBeanImpl newInstance(final ServletStubImpl stub) throws ManagementException {
      try {
         return (ServletRuntimeMBeanImpl)WebAppSecurity.getProvider().getKernelSubject().run(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return new ServletRuntimeMBeanImpl(stub.getServletName(), stub);
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception e = var3.getException();
         if (e instanceof ManagementException) {
            throw (ManagementException)e;
         } else {
            return null;
         }
      }
   }

   public void destroy() {
      try {
         WebAppSecurity.getProvider().getKernelSubject().run(new PrivilegedExceptionAction() {
            public Object run() throws ManagementException {
               ServletRuntimeMBeanImpl.this.unregister();
               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception e = var3.getException();
         if (e instanceof ManagementException) {
            HTTPLogger.logErrorUnregisteringServletRuntime(this.getObjectName(), e);
         }
      }

   }

   public String getServletName() {
      return this.stub.getServletName();
   }

   public String getServletClassName() {
      return this.stub.getClassName();
   }

   void incReloadCount() {
      ++this.reloadCount;
   }

   public int getReloadTotalCount() {
      return this.reloadCount;
   }

   void incInvocationCount() {
      this.invokeCount.incrementAndGet();
   }

   public int getInvocationTotalCount() {
      return this.invokeCount.get();
   }

   public int getPoolMaxCapacity() {
      StubLifecycleHelper h = this.stub.getLifecycleHelper();
      return h == null ? 0 : h.getPoolCapacity();
   }

   void addExecutionTimeTotal(long l) {
      this.totalExecutionTime.addAndGet(l);
   }

   public long getExecutionTimeTotal() {
      return this.totalExecutionTime.get();
   }

   void setExecutionTimeHighLow(long l) {
      if (l > this.highExecutionTime) {
         this.highExecutionTime = l;
      }

      if (l < this.lowExecutionTime || this.lowExecutionTime < 0L) {
         this.lowExecutionTime = l;
      }

   }

   public int getExecutionTimeHigh() {
      return (int)this.highExecutionTime;
   }

   public int getExecutionTimeLow() {
      return (int)(this.lowExecutionTime < 0L ? 0L : this.lowExecutionTime);
   }

   public int getExecutionTimeAverage() {
      int count = this.invokeCount.get();
      return count <= 0 ? 0 : (int)(this.getExecutionTimeTotal() / (long)count);
   }

   public String getServletPath() {
      URLMapping mapping = this.getServletContext().getServletMapping();
      Object[] values = mapping.values();
      if (values == null) {
         return null;
      } else {
         for(int i = 0; i < values.length; ++i) {
            URLMatchHelper umh = (URLMatchHelper)values[i];
            if (umh.getServletStub() == this.stub) {
               return umh.getPattern();
            }
         }

         URLMatchHelper defaultMapping = this.getServletContext().getDefaultMapping();
         if (defaultMapping != null && defaultMapping.getServletStub() == this.stub && "/*".equals(defaultMapping.getPattern())) {
            return "";
         } else {
            return null;
         }
      }
   }

   public String getContextPath() {
      return this.getServletContext().getContextPath();
   }

   private ChannelHelperService getChannelHelperService() {
      return (ChannelHelperService)GlobalServiceLocator.getServiceLocator().getService(ChannelHelperService.class, new Annotation[0]);
   }

   public String getURL() {
      String path = this.getServletPath();
      if (path == null) {
         return null;
      } else {
         HttpServer s = this.getServletContext().getServer();
         String[] hostnames = s.getVirtualHostNames();
         String serverName = hostnames != null && hostnames.length != 0 ? hostnames[0] : null;
         if (serverName == null) {
            serverName = s.getListenAddress();
         }

         String cp = this.getContextPath();
         if (cp.equals("/")) {
            cp = "";
         }

         String serverString = this.getChannelHelperService().getURL(ProtocolHandlerHTTP.PROTOCOL_HTTP);
         if (serverString == null) {
            return null;
         } else {
            ServerURL httpServerUrl;
            try {
               httpServerUrl = new ServerURL(serverString);
            } catch (MalformedURLException var9) {
               return null;
            }

            int port = httpServerUrl.getPort();
            if (httpServerUrl.getProtocol().equalsIgnoreCase("http") && port == 80) {
               port = -1;
            }

            return httpServerUrl.getProtocol() + "://" + serverName + (port == -1 ? "" : ":" + port) + cp + path;
         }
      }
   }

   public String[] getURLPatterns() {
      URLMapping mapping = this.getServletContext().getServletMapping();
      ArrayList l = new ArrayList();
      Object[] values = mapping.values();
      if (values != null) {
         for(int i = 0; i < values.length; ++i) {
            URLMatchHelper umh = (URLMatchHelper)values[i];
            if (umh != null && umh.getServletStub() == this.stub) {
               l.add(umh.getPattern());
            }
         }
      }

      return (String[])((String[])l.toArray(new String[l.size()]));
   }

   public boolean isInternalServlet() {
      return this.stub.isInternalServlet();
   }

   private WebAppServletContext getServletContext() {
      return (WebAppServletContext)this.stub.getServletContext();
   }

   public static void dumpServlet(PrintStream p, ServletRuntimeMBean mb) {
      println(p, "        SERVLET NAME: " + mb.getName());
      println(p, "        ReloadTotalCount: " + mb.getReloadTotalCount());
      println(p, "        InvocationTotalCount: " + mb.getInvocationTotalCount());
      println(p, "        PoolMaxCapacity: " + mb.getPoolMaxCapacity());
      println(p, "        ExecutionTimeTotal: " + mb.getExecutionTimeTotal());
      println(p, "        ExecutionTimeHigh: " + mb.getExecutionTimeHigh());
      println(p, "        ExecutionTimeLow: " + mb.getExecutionTimeLow());
      println(p, "        ExecutionTimeAverage: " + mb.getExecutionTimeAverage());
   }

   private static void println(PrintStream p, String s) {
      p.println(s + "<br>");
   }

   public Serializable[] invokeAnnotatedMethods(Class annotationToMatch, Serializable... arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      Object[] objs = this.stub.invokeAnnotatedMethods(annotationToMatch, (Object[])arguments);
      Serializable[] result = new Serializable[objs.length];

      for(int i = 0; i < objs.length; ++i) {
         result[i] = (Serializable)objs[i];
      }

      return result;
   }

   // $FF: synthetic method
   ServletRuntimeMBeanImpl(String x0, ServletStubImpl x1, Object x2) throws ManagementException {
      this(x0, x1);
   }
}
