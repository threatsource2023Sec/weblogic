package weblogic.servlet.internal;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;

public final class TailFilter implements Filter {
   private final ServletStub servletStub;
   static final long serialVersionUID = 2388376886674549741L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.TailFilter");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Filter_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public TailFilter(ServletStub stub) {
      this.servletStub = stub;
   }

   public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws ServletException, IOException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[4];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = req;
            var10000[2] = rsp;
            var10000[3] = chain;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      try {
         this.servletStub.execute(req, rsp, (FilterChainImpl)chain);
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            InstrumentationSupport.postProcess(var4);
         }

         throw var6;
      }

      if (var4 != null) {
         InstrumentationSupport.postProcess(var4);
      }

   }

   public void init(FilterConfig fc) throws ServletException {
   }

   public void destroy() {
   }

   static {
      _WLDF$INST_FLD_Servlet_Filter_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Filter_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "TailFilter.java", "weblogic.servlet.internal.TailFilter", "doFilter", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;Ljavax/servlet/FilterChain;)V", 25, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Filter_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Filter_Around_Medium};
   }
}
