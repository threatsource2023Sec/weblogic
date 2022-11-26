package weblogic.j2ee.descriptor.wl;

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

public class ApplicationBeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(36);
   private static final ArrayList roleInfoList;
   private static final ApplicationBeanInfoFactory SINGLETON;

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
         InputStream is = this.getClass().getResourceAsStream("ApplicationBeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("weblogic.j2ee.descriptor.ApplicationBean", "weblogic.j2ee.descriptor.ApplicationBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.ModuleBean", "weblogic.j2ee.descriptor.ModuleBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.WebBean", "weblogic.j2ee.descriptor.WebBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ApplicationEntityCacheBean", "weblogic.j2ee.descriptor.wl.ApplicationEntityCacheBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ApplicationParamBean", "weblogic.j2ee.descriptor.wl.ApplicationParamBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBean", "weblogic.j2ee.descriptor.wl.ApplicationPoolParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ApplicationSecurityRoleAssignmentBean", "weblogic.j2ee.descriptor.wl.ApplicationSecurityRoleAssignmentBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ClassloaderStructureBean", "weblogic.j2ee.descriptor.wl.ClassloaderStructureBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ConfigurationSupportBean", "weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBean", "weblogic.j2ee.descriptor.wl.ConnectionCheckParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ConnectionFactoryBean", "weblogic.j2ee.descriptor.wl.ConnectionFactoryBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ConnectionParamsBean", "weblogic.j2ee.descriptor.wl.ConnectionParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ConnectionPropertiesBean", "weblogic.j2ee.descriptor.wl.ConnectionPropertiesBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.CustomModuleBean", "weblogic.j2ee.descriptor.wl.CustomModuleBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.DriverParamsBean", "weblogic.j2ee.descriptor.wl.DriverParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.EjbBean", "weblogic.j2ee.descriptor.wl.EjbBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.EntityMappingBean", "weblogic.j2ee.descriptor.wl.EntityMappingBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolBean", "weblogic.j2ee.descriptor.wl.JDBCConnectionPoolBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.LibraryContextRootOverrideBean", "weblogic.j2ee.descriptor.wl.LibraryContextRootOverrideBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ListenerBean", "weblogic.j2ee.descriptor.wl.ListenerBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.MaxCacheSizeBean", "weblogic.j2ee.descriptor.wl.MaxCacheSizeBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ModuleProviderBean", "weblogic.j2ee.descriptor.wl.ModuleProviderBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ModuleRefBean", "weblogic.j2ee.descriptor.wl.ModuleRefBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ParameterBean", "weblogic.j2ee.descriptor.wl.ParameterBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ParserFactoryBean", "weblogic.j2ee.descriptor.wl.ParserFactoryBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.PreparedStatementBean", "weblogic.j2ee.descriptor.wl.PreparedStatementBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.SecurityBean", "weblogic.j2ee.descriptor.wl.SecurityBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.ShutdownBean", "weblogic.j2ee.descriptor.wl.ShutdownBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.SingletonServiceBean", "weblogic.j2ee.descriptor.wl.SingletonServiceBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.SizeParamsBean", "weblogic.j2ee.descriptor.wl.SizeParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.StartupBean", "weblogic.j2ee.descriptor.wl.StartupBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.StatementBean", "weblogic.j2ee.descriptor.wl.StatementBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.WeblogicApplicationBean", "weblogic.j2ee.descriptor.wl.WeblogicApplicationBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.WeblogicExtensionBean", "weblogic.j2ee.descriptor.wl.WeblogicExtensionBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.WeblogicModuleBean", "weblogic.j2ee.descriptor.wl.WeblogicModuleBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.XmlBean", "weblogic.j2ee.descriptor.wl.XmlBeanImplBeanInfo");
      roleInfoList = new ArrayList(0);
      SINGLETON = new ApplicationBeanInfoFactory();
   }
}
