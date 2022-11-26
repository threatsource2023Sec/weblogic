package weblogic.jdbc.module;

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
   private static final Map interfaceMap = new HashMap(8);
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
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBean", "weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCDataSourceBean", "weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean", "weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean", "weblogic.j2ee.descriptor.wl.JDBCDriverParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBean", "weblogic.j2ee.descriptor.wl.JDBCOracleParamsBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCPropertiesBean", "weblogic.j2ee.descriptor.wl.JDBCPropertiesBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCPropertyBean", "weblogic.j2ee.descriptor.wl.JDBCPropertyBeanImplBeanInfo");
      interfaceMap.put("weblogic.j2ee.descriptor.wl.JDBCXAParamsBean", "weblogic.j2ee.descriptor.wl.JDBCXAParamsBeanImplBeanInfo");
      roleInfoList = new ArrayList(8);
      roleInfoList.add("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBean");
      roleInfoList.add("weblogic.j2ee.descriptor.wl.JDBCDataSourceBean");
      roleInfoList.add("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean");
      roleInfoList.add("weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean");
      roleInfoList.add("weblogic.j2ee.descriptor.wl.JDBCOracleParamsBean");
      roleInfoList.add("weblogic.j2ee.descriptor.wl.JDBCPropertiesBean");
      roleInfoList.add("weblogic.j2ee.descriptor.wl.JDBCPropertyBean");
      roleInfoList.add("weblogic.j2ee.descriptor.wl.JDBCXAParamsBean");
      SINGLETON = new BeanInfoFactory();
   }
}
