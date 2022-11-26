package weblogic.servlet.internal;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.management.DeploymentException;

public final class RequestEventsFilter implements Filter {
   public static final String REQUEST_INIT_EVENT_NOTIFIED = "requestInitEventNotified";
   public static final String REQUEST_DESTROY_EVENT_NOTIFIED = "requestDestroyEventNotified";
   private EventsManager eventsManager;
   static final long serialVersionUID = 6023203043917115371L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.RequestEventsFilter");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Filter_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws ServletException, IOException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[4];
            Object[] var10000 = var5.args;
            var10000[0] = this;
            var10000[1] = req;
            var10000[2] = rsp;
            var10000[3] = chain;
         }

         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.preProcess(var5);
         var5.resetPostBegin();
      }

      try {
         if (req.getAttribute("requestInitEventNotified") == null) {
            this.eventsManager.notifyRequestLifetimeEvent(req, true);
            req.setAttribute("requestInitEventNotified", Boolean.TRUE.toString());
         }

         try {
            chain.doFilter(new EventsRequestWrapper((HttpServletRequest)req, this.eventsManager), rsp);
         } finally {
            if (req.getAttribute("requestDestroyEventNotified") == null) {
               this.eventsManager.notifyRequestLifetimeEvent(req, false);
               req.setAttribute("requestDestroyEventNotified", Boolean.TRUE.toString());
            }

         }
      } catch (Throwable var11) {
         if (var5 != null) {
            var5.th = var11;
            InstrumentationSupport.postProcess(var5);
         }

         throw var11;
      }

      if (var5 != null) {
         InstrumentationSupport.postProcess(var5);
      }

   }

   public void init(FilterConfig fc) throws ServletException {
      ServletContext ctx = fc.getServletContext();
      if (!(ctx instanceof ServletWorkContext)) {
         throw new ServletException(new DeploymentException("Incorrect ServletContext runtime type in FilterConfig. Expecting 'WebAppServletContext', but got '" + ctx.getClass().getName() + "'."));
      } else {
         this.eventsManager = ((WebAppServletContext)ctx).getEventsManager();
      }
   }

   public void destroy() {
      this.eventsManager = null;
   }

   static {
      _WLDF$INST_FLD_Servlet_Filter_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Filter_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "RequestEventsFilter.java", "weblogic.servlet.internal.RequestEventsFilter", "doFilter", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;Ljavax/servlet/FilterChain;)V", 27, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Filter_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Filter_Around_Medium};
   }

   public static class EventsRequestWrapper extends HttpServletRequestWrapper {
      private final EventsManager eventsManager;

      private EventsRequestWrapper(HttpServletRequest req, EventsManager eventsManager) {
         super(req);
         this.eventsManager = eventsManager;
      }

      public void setAttribute(String name, Object o) {
         Object prev = this.getAttribute(name);
         super.setAttribute(name, o);
         this.eventsManager.notifyRequestAttributeEvent(this, name, prev, o);
      }

      public void removeAttribute(String name) {
         Object prev = this.getAttribute(name);
         super.removeAttribute(name);
         this.eventsManager.notifyRequestAttributeEvent(this, name, prev, (Object)null);
      }

      // $FF: synthetic method
      EventsRequestWrapper(HttpServletRequest x0, EventsManager x1, Object x2) {
         this(x0, x1);
      }
   }
}
