package weblogic.diagnostics.accessor;

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
      interfaceMap.put("weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean", "weblogic.diagnostics.accessor.AccessRuntimeBeanInfo");
      interfaceMap.put("weblogic.diagnostics.accessor.runtime.ArchiveRuntimeMBean", "weblogic.diagnostics.archive.DiagnosticArchiveRuntimeBeanInfo");
      interfaceMap.put("weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean", "weblogic.diagnostics.accessor.DataAccessRuntimeBeanInfo");
      interfaceMap.put("weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean", "weblogic.diagnostics.archive.DataRetirementTaskImplBeanInfo");
      interfaceMap.put("weblogic.diagnostics.accessor.runtime.DbstoreArchiveRuntimeMBean", "weblogic.diagnostics.archive.DiagnosticDbstoreArchiveRuntimeBeanInfo");
      interfaceMap.put("weblogic.diagnostics.accessor.runtime.EditableArchiveRuntimeMBean", "weblogic.diagnostics.archive.DiagnosticEditableArchiveRuntimeBeanInfo");
      interfaceMap.put("weblogic.diagnostics.accessor.runtime.FileArchiveRuntimeMBean", "weblogic.diagnostics.archive.DiagnosticFileArchiveRuntimeBeanInfo");
      interfaceMap.put("weblogic.diagnostics.accessor.runtime.WlstoreArchiveRuntimeMBean", "weblogic.diagnostics.archive.DiagnosticWlstoreArchiveRuntimeBeanInfo");
      roleInfoList = new ArrayList(2);
      roleInfoList.add("weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean");
      roleInfoList.add("weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean");
      SINGLETON = new BeanInfoFactory();
   }
}
