package weblogic.servlet.jsp;

import java.net.URI;
import weblogic.version;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.jsp.compiler.jsp.IWebAppProjectFeature;
import weblogic.jsp.wlw.filesystem.IFile;
import weblogic.jsp.wlw.util.filesystem.FS;
import weblogic.utils.classloaders.GenericClassLoader;

public class JavelinxTagFileStaleChecker implements StaleChecker {
   private static final boolean debug = false;
   private final IWebAppProjectFeature webapp;
   static final long serialVersionUID = -2152613620596402821L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.jsp.JavelinxTagFileStaleChecker");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public JavelinxTagFileStaleChecker(IWebAppProjectFeature webappProject) {
      this.webapp = webappProject;
   }

   public boolean isResourceStale(String resourcePath, long sinceWhen, String serverVersion, String var5) {
      LocalHolder var8;
      if ((var8 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var8.argsCapture) {
            var8.args = new Object[5];
            Object[] var10000 = var8.args;
            var10000[0] = this;
            var10000[1] = resourcePath;
            var10000[2] = InstrumentationSupport.convertToObject(sinceWhen);
            var10000[3] = serverVersion;
            var10000[4] = var5;
         }

         InstrumentationSupport.createDynamicJoinPoint(var8);
         InstrumentationSupport.preProcess(var8);
         var8.resetPostBegin();
      }

      boolean var11;
      label91: {
         try {
            if (!version.getReleaseBuildVersion().equals(serverVersion)) {
               var11 = true;
               break label91;
            }

            URI u = this.webapp.findResource(resourcePath);
            IFile f = FS.getIFile(u);
            var11 = sinceWhen < f.lastModified();
         } catch (Throwable var10) {
            if (var8 != null) {
               var8.th = var10;
               var8.ret = InstrumentationSupport.convertToObject(false);
               InstrumentationSupport.createDynamicJoinPoint(var8);
               InstrumentationSupport.postProcess(var8);
            }

            throw var10;
         }

         if (var8 != null) {
            var8.ret = InstrumentationSupport.convertToObject(var11);
            InstrumentationSupport.createDynamicJoinPoint(var8);
            InstrumentationSupport.postProcess(var8);
         }

         return var11;
      }

      if (var8 != null) {
         var8.ret = InstrumentationSupport.convertToObject(var11);
         InstrumentationSupport.createDynamicJoinPoint(var8);
         InstrumentationSupport.postProcess(var8);
      }

      return var11;
   }

   public static boolean isClassStale(String className, ClassLoader cl, IWebAppProjectFeature webAppProject) {
      if (!(cl instanceof GenericClassLoader)) {
         return true;
      } else {
         boolean isStale = false;
         JspClassLoader jspCL = null;

         try {
            jspCL = new JspClassLoader(((GenericClassLoader)cl).getClassFinder(), cl, className);
            Class clazz = jspCL.loadClass(className);
            isStale = JspStub.isJSPClassStale(clazz, new JavelinxTagFileStaleChecker(webAppProject));
         } catch (Throwable var9) {
            isStale = true;
         } finally {
            jspCL = null;
         }

         if (isStale && cl instanceof TagFileClassLoader) {
            TagFileClassLoader tagFileCL = (TagFileClassLoader)cl;
            tagFileCL.forceToBounce();
         }

         return isStale;
      }
   }

   static {
      _WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Stale_Resource_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "JavelinxTagFileStaleChecker.java", "weblogic.servlet.jsp.JavelinxTagFileStaleChecker", "isResourceStale", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;)Z", 26, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Stale_Resource_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("res", (String)null, false, true), null, null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Stale_Resource_Around_Medium};
   }
}
