package weblogic.diagnostics.lifecycle;

import java.lang.annotation.Annotation;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.image.ImageRuntimeMBeanImpl;
import weblogic.management.ManagementException;
import weblogic.management.runtime.TaskRuntimeMBean;
import weblogic.server.GlobalServiceLocator;
import weblogic.t3.srvr.ServerRuntime;
import weblogic.utils.PropertyHelper;

public class DiagnosticImageLifecycleImpl implements DiagnosticComponentLifecycle {
   private static DiagnosticImageLifecycleImpl singleton = new DiagnosticImageLifecycleImpl();

   public static final DiagnosticComponentLifecycle getInstance() {
      return singleton;
   }

   public int getStatus() {
      return 4;
   }

   public void initialize() throws DiagnosticComponentLifecycleException {
      try {
         ServerRuntime.theOne().getWLDFRuntime().setWLDFImageRuntime(ImageRuntimeMBeanImpl.getInstance());
         GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
      } catch (ManagementException var2) {
         throw new DiagnosticComponentLifecycleException(var2);
      }
   }

   public void enable() throws DiagnosticComponentLifecycleException {
   }

   public void disable() throws DiagnosticComponentLifecycleException {
      if (PropertyHelper.getBoolean("weblogic.diagnostics.image.CaptureOnShutdown")) {
         try {
            TaskRuntimeMBean task = ((ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0])).captureImage((String)null, (String)null);

            try {
               Thread.sleep(2000L);
            } catch (InterruptedException var4) {
            }

            while(task.isRunning()) {
               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException var3) {
               }
            }
         } catch (Exception var5) {
         }
      }

   }
}
