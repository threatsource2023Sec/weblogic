package weblogic.servlet.utils;

import com.bea.wls.redef.ClassRedefinitionRuntime;
import com.bea.wls.redef.RedefiningClassLoader;
import com.bea.wls.redef.RedefinitionTask;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
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
import weblogic.management.DeploymentException;
import weblogic.servlet.internal.WebAppServletContext;

public class FastSwapFilter implements Filter {
   private FilterConfig config;
   private long refreshInterval;
   private long lastRefreshTime;
   static final long serialVersionUID = 642171625125621178L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.utils.FastSwapFilter");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Filter_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public void init(FilterConfig config) throws ServletException {
      this.config = config;
      this.initRefreshInterval(config);
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[4];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = request;
            var10000[2] = response;
            var10000[3] = chain;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      label90: {
         try {
            if (!(request instanceof HttpServletRequest) && !(response instanceof HttpServletResponse)) {
               chain.doFilter(request, response);
               break label90;
            }

            this.redefineClasses();
            chain.doFilter(request, response);
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

         return;
      }

      if (var4 != null) {
         InstrumentationSupport.postProcess(var4);
      }

   }

   private void redefineClasses() throws ServletException {
      long now = System.currentTimeMillis();
      if (now - this.lastRefreshTime >= this.refreshInterval) {
         try {
            WebAppServletContext ctx = (WebAppServletContext)this.config.getServletContext();
            ClassLoader gcl = ctx.getApplicationContext().getAppClassLoader();
            if (!(gcl instanceof RedefiningClassLoader)) {
               gcl = ctx.getServletClassLoader();
            }

            if (gcl instanceof RedefiningClassLoader) {
               RedefiningClassLoader rcl = (RedefiningClassLoader)gcl;
               ClassRedefinitionRuntime runtime = rcl.getRedefinitionRuntime();
               RedefinitionTask task = new RedefinitionTask(runtime, (String)null, (String[])null);
               runtime.redefineClasses((String)null, (Set)null, task);
            }
         } catch (Exception var8) {
            throw new ServletException(var8.getMessage(), var8);
         }

         this.lastRefreshTime = now;
      }
   }

   public void destroy() {
   }

   private void initRefreshInterval(FilterConfig config) {
      WebAppServletContext context = (WebAppServletContext)config.getServletContext();
      this.refreshInterval = 1000L * (long)context.getConfigManager().getFastSwapRefreshInterval();
   }

   public static void registerFastSwapFilter(WebAppServletContext context) throws DeploymentException {
      Map initParams = Collections.emptyMap();
      context.registerFilter("FastSwapFilter", FastSwapFilter.class.getName(), new String[]{"/*"}, (String[])null, initParams, (String[])null);
   }

   static {
      _WLDF$INST_FLD_Servlet_Filter_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Filter_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "FastSwapFilter.java", "weblogic.servlet.utils.FastSwapFilter", "doFilter", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;Ljavax/servlet/FilterChain;)V", 54, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Filter_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Filter_Around_Medium};
   }
}
