package weblogic.deploy.api.tools.remote;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.management.ManagementException;
import weblogic.management.internal.AppInfo;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SessionHelperManagerRuntimeMBean;
import weblogic.management.runtime.SessionHelperRuntimeMBean;

public class SessionHelperManagerRuntime extends RuntimeMBeanDelegate implements SessionHelperManagerRuntimeMBean {
   private Set sessionHelpers = Collections.synchronizedSet(new HashSet());

   public SessionHelperManagerRuntime(String name) throws ManagementException {
      super(name, false);
   }

   public SessionHelperRuntimeMBean getSessionHelper(AppInfo appInfo, WLSModelMBeanContext context) {
      String appName = appInfo.getName();
      Iterator var4 = this.sessionHelpers.iterator();

      SessionHelperRuntimeMBean helper;
      do {
         if (!var4.hasNext()) {
            this.initializeSessionHelper(appInfo, context);
            var4 = this.sessionHelpers.iterator();

            do {
               if (!var4.hasNext()) {
                  return null;
               }

               helper = (SessionHelperRuntimeMBean)var4.next();
            } while(!helper.getName().equals(appName));

            return helper;
         }

         helper = (SessionHelperRuntimeMBean)var4.next();
      } while(!helper.getName().equals(appName));

      return helper;
   }

   private void initializeSessionHelper(AppInfo appInfo, WLSModelMBeanContext context) {
      try {
         SessionHelperRuntimeMBean helper = new SessionHelperRuntime(appInfo, context);
         this.sessionHelpers.add(helper);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   public void releaseSessionHelper(String appName) {
      if (appName != null) {
         Iterator var2 = this.sessionHelpers.iterator();

         while(var2.hasNext()) {
            SessionHelperRuntimeMBean helper = (SessionHelperRuntimeMBean)var2.next();
            if (helper.getName().equals(appName)) {
               this.sessionHelpers.remove(helper);

               try {
                  ((RuntimeMBeanDelegate)helper).unregister();
                  return;
               } catch (ManagementException var5) {
                  throw new RuntimeException(var5);
               }
            }
         }
      }

   }
}
