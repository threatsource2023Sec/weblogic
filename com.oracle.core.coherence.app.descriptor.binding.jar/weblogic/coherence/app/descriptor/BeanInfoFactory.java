package weblogic.coherence.app.descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import weblogic.utils.codegen.ImplementationFactory;
import weblogic.utils.codegen.RoleInfoImplementationFactory;

public class BeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(6);
   private static final ArrayList roleInfoList;
   private static final BeanInfoFactory SINGLETON;

   public static final ImplementationFactory getInstance() {
      return SINGLETON;
   }

   public String getImplementationClassName(String interfaceName) {
      return (String)interfaceMap.get(interfaceName);
   }

   public String[] getInterfaces() {
      Set keySet = interfaceMap.keySet();
      return (String[])((String[])keySet.toArray(new String[keySet.size()]));
   }

   public String[] getInterfacesWithRoleInfo() {
      return (String[])((String[])roleInfoList.toArray(new String[roleInfoList.size()]));
   }

   public String getRoleInfoImplementationFactoryTimestamp() {
      try {
         InputStream is = this.getClass().getResourceAsStream("BeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("weblogic.coherence.app.descriptor.wl.ApplicationLifecycleListenerBean", "weblogic.coherence.app.descriptor.wl.ApplicationLifecycleListenerBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.app.descriptor.wl.CoherenceApplicationBean", "weblogic.coherence.app.descriptor.wl.CoherenceApplicationBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.app.descriptor.wl.ConfigurableCacheFactoryConfigBean", "weblogic.coherence.app.descriptor.wl.ConfigurableCacheFactoryConfigBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.app.descriptor.wl.InitParamBean", "weblogic.coherence.app.descriptor.wl.InitParamBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.app.descriptor.wl.InitParamsBean", "weblogic.coherence.app.descriptor.wl.InitParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.app.descriptor.wl.WeblogicCohAppBean", "weblogic.coherence.app.descriptor.wl.WeblogicCohAppBeanImplBeanInfo");
      roleInfoList = new ArrayList(6);
      roleInfoList.add("weblogic.coherence.app.descriptor.wl.ApplicationLifecycleListenerBean");
      roleInfoList.add("weblogic.coherence.app.descriptor.wl.CoherenceApplicationBean");
      roleInfoList.add("weblogic.coherence.app.descriptor.wl.ConfigurableCacheFactoryConfigBean");
      roleInfoList.add("weblogic.coherence.app.descriptor.wl.InitParamBean");
      roleInfoList.add("weblogic.coherence.app.descriptor.wl.InitParamsBean");
      roleInfoList.add("weblogic.coherence.app.descriptor.wl.WeblogicCohAppBean");
      SINGLETON = new BeanInfoFactory();
   }
}
