package weblogic.management.configuration;

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

public class VBeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(3);
   private static final ArrayList roleInfoList;
   private static final VBeanInfoFactory SINGLETON;

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
         InputStream is = this.getClass().getResourceAsStream("VBeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("weblogic.management.configuration.DeterminerCandidateResourceInfoVBean", "weblogic.management.mbeans.custom.DeterminerCandidateResourceInfoVBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.configuration.PropertyValueVBean", "weblogic.management.mbeans.custom.PropertyValueVBeanImplBeanInfo");
      interfaceMap.put("weblogic.management.configuration.SimplePropertyValueVBean", "weblogic.management.mbeans.custom.SimplePropertyValueVBeanImplBeanInfo");
      roleInfoList = new ArrayList(0);
      SINGLETON = new VBeanInfoFactory();
   }
}
