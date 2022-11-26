package weblogic.coherence.descriptor;

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
   private static final Map interfaceMap = new HashMap(15);
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
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceAddressProviderBean", "weblogic.coherence.descriptor.wl.CoherenceAddressProviderBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceAddressProvidersBean", "weblogic.coherence.descriptor.wl.CoherenceAddressProvidersBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceCacheBean", "weblogic.coherence.descriptor.wl.CoherenceCacheBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceClusterParamsBean", "weblogic.coherence.descriptor.wl.CoherenceClusterParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressBean", "weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressesBean", "weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressesBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceFederationParamsBean", "weblogic.coherence.descriptor.wl.CoherenceFederationParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceIdentityAsserterBean", "weblogic.coherence.descriptor.wl.CoherenceIdentityAsserterBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceInitParamBean", "weblogic.coherence.descriptor.wl.CoherenceInitParamBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceKeystoreParamsBean", "weblogic.coherence.descriptor.wl.CoherenceKeystoreParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceLoggingParamsBean", "weblogic.coherence.descriptor.wl.CoherenceLoggingParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherencePersistenceParamsBean", "weblogic.coherence.descriptor.wl.CoherencePersistenceParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceServiceBean", "weblogic.coherence.descriptor.wl.CoherenceServiceBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.CoherenceSocketAddressBean", "weblogic.coherence.descriptor.wl.CoherenceSocketAddressBeanImplBeanInfo");
      interfaceMap.put("weblogic.coherence.descriptor.wl.WeblogicCoherenceBean", "weblogic.coherence.descriptor.wl.WeblogicCoherenceBeanImplBeanInfo");
      roleInfoList = new ArrayList(15);
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceAddressProviderBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceAddressProvidersBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceCacheBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceClusterParamsBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressesBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceFederationParamsBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceIdentityAsserterBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceInitParamBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceKeystoreParamsBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceLoggingParamsBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherencePersistenceParamsBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceServiceBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.CoherenceSocketAddressBean");
      roleInfoList.add("weblogic.coherence.descriptor.wl.WeblogicCoherenceBean");
      SINGLETON = new BeanInfoFactory();
   }
}
