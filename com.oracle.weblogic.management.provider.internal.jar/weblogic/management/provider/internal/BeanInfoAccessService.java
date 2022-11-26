package weblogic.management.provider.internal;

import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(
   value = 5,
   mode = 0
)
public class BeanInfoAccessService extends AbstractServerService {
   @Inject
   private IterableProvider beanInfoEnablers;

   public void start() throws ServiceFailureException {
      BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
      if (beanInfoAccess == null) {
         beanInfoAccess = BeanInfoAccessSingleton.getInstance();
         beanInfoAccess.disableProperty(DomainMBean.class.getName(), new String[]{"WebserviceTestpage", "WebserviceSecurities"});
         beanInfoAccess.disableProperty(ServerMBean.class.getName(), new String[]{"WebService"});
         beanInfoAccess.disableProperty(ServerRuntimeMBean.class.getName(), new String[]{"WseeClusterFrontEndRuntime", "WseeWsrmRuntime"});
         if (this.beanInfoEnablers != null) {
            Iterator var2 = this.beanInfoEnablers.iterator();

            label31:
            while(true) {
               Map enables;
               do {
                  if (!var2.hasNext()) {
                     break label31;
                  }

                  BeanInfoEnabler bi = (BeanInfoEnabler)var2.next();
                  enables = bi.getEnabledProperties();
               } while(enables == null);

               Iterator var5 = enables.keySet().iterator();

               while(var5.hasNext()) {
                  String mbeanName = (String)var5.next();
                  String[] properties = (String[])enables.get(mbeanName);
                  if (properties != null) {
                     beanInfoAccess.enableProperty(mbeanName, properties);
                  }
               }
            }
         }

         ManagementService.initializeBeanInfo(beanInfoAccess);
      }
   }
}
