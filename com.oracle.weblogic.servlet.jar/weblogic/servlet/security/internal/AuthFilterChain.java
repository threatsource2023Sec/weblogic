package weblogic.servlet.security.internal;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;

public final class AuthFilterChain implements FilterChain {
   private final ServletSecurityContext context;
   private final Filter[] filters;
   private final int size;
   private int index = 0;

   public AuthFilterChain(Filter[] f, ServletSecurityContext ctx) {
      this.filters = f;
      this.context = ctx;
      this.size = f.length;
   }

   public void doFilter(ServletRequest req, ServletResponse rsp) throws IOException, ServletException {
      Object f;
      if (this.index >= this.size) {
         f = new LastFilter();
      } else {
         f = this.filters[this.index];
      }

      ++this.index;
      ((Filter)f).doFilter(req, rsp, this);
   }

   private class LastFilter implements Filter {
      static final long serialVersionUID = 2473293255198863912L;
      static final String _WLDF$INST_VERSION = "9.0.0";
      // $FF: synthetic field
      static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.security.internal.AuthFilterChain$LastFilter");
      static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Filter_Around_Medium;
      static final JoinPoint _WLDF$INST_JPFLD_0;
      static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

      private LastFilter() {
      }

      public void doFilter(ServletRequest req, ServletResponse res, FilterChain var3) throws IOException, ServletException {
         LocalHolder var5;
         if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
            if (var5.argsCapture) {
               var5.args = new Object[4];
               Object[] var10000 = var5.args;
               var10000[0] = this;
               var10000[1] = req;
               var10000[2] = res;
               var10000[3] = var3;
            }

            InstrumentationSupport.createDynamicJoinPoint(var5);
            InstrumentationSupport.preProcess(var5);
            var5.resetPostBegin();
         }

         try {
            try {
               AuthFilterChain.this.context.securedExecute((HttpServletRequest)req, (HttpServletResponse)res);
            } catch (IOException var11) {
               throw var11;
            } catch (ServletException var12) {
               throw var12;
            } catch (RuntimeException var13) {
               throw var13;
            } catch (Throwable var14) {
               throw new ServletException(var14);
            }
         } catch (Throwable var15) {
            if (var5 != null) {
               var5.th = var15;
               InstrumentationSupport.postProcess(var5);
            }

            throw var15;
         }

         if (var5 != null) {
            InstrumentationSupport.postProcess(var5);
         }

      }

      public void init(FilterConfig filterConfig) throws ServletException {
      }

      public void destroy() {
      }

      // $FF: synthetic method
      LastFilter(Object x1) {
         this();
      }

      static {
         _WLDF$INST_FLD_Servlet_Filter_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Filter_Around_Medium");
         _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "AuthFilterChain.java", "weblogic.servlet.security.internal.AuthFilterChain$LastFilter", "doFilter", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;Ljavax/servlet/FilterChain;)V", 43, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Filter_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null, null})}), (boolean)0);
         _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Filter_Around_Medium};
      }
   }
}
