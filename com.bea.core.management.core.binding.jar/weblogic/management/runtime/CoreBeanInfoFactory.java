package weblogic.management.runtime;

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

public class CoreBeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(10);
   private static final ArrayList roleInfoList;
   private static final CoreBeanInfoFactory SINGLETON;

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
         InputStream is = this.getClass().getResourceAsStream("CoreBeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("weblogic.management.runtime.AuthenticatorRuntimeMBean", "weblogic.security.AuthenticatorRuntimeMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.runtime.LdapAuthenticatorRuntimeMBean", "weblogic.security.LdapAuthenticatorRuntimeBeanInfo");
      interfaceMap.put("weblogic.management.runtime.PersistenceUnitRuntimeMBean", "weblogic.persistence.PersistenceUnitRuntimeMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.runtime.ProviderRuntimeMBean", "weblogic.security.ProviderRuntimeMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.runtime.RealmRuntimeMBean", "weblogic.security.RealmRuntimeBeanInfo");
      interfaceMap.put("weblogic.management.runtime.RuntimeMBean", "weblogic.management.runtime.RuntimeMBeanDelegateBeanInfo");
      interfaceMap.put("weblogic.management.runtime.ServerSecurityRuntimeMBean", "weblogic.security.SecurityRuntimeBeanInfo");
      interfaceMap.put("weblogic.management.runtime.SingleSignOnServicesRuntimeMBean", "weblogic.security.SingleSignOnServicesRuntimeBeanInfo");
      interfaceMap.put("weblogic.management.runtime.TaskRuntimeMBean", "weblogic.management.runtime.TaskRuntimeMBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.runtime.UserLockoutManagerRuntimeMBean", "weblogic.security.UserLockoutManagerRuntimeBeanInfo");
      roleInfoList = new ArrayList(3);
      roleInfoList.add("weblogic.management.runtime.RealmRuntimeMBean");
      roleInfoList.add("weblogic.management.runtime.ServerSecurityRuntimeMBean");
      roleInfoList.add("weblogic.management.runtime.UserLockoutManagerRuntimeMBean");
      SINGLETON = new CoreBeanInfoFactory();
   }
}
