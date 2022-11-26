package com.bea.httppubsub.descriptor;

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
   private static final Map interfaceMap = new HashMap(12);
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
      interfaceMap.put("com.bea.httppubsub.descriptor.AuthConstraintBean", "com.bea.httppubsub.descriptor.AuthConstraintBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.ChannelBean", "com.bea.httppubsub.descriptor.ChannelBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.ChannelConstraintBean", "com.bea.httppubsub.descriptor.ChannelConstraintBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.ChannelPersistenceBean", "com.bea.httppubsub.descriptor.ChannelPersistenceBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.ChannelResourceCollectionBean", "com.bea.httppubsub.descriptor.ChannelResourceCollectionBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.JmsHandlerBean", "com.bea.httppubsub.descriptor.JmsHandlerBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.JmsHandlerMappingBean", "com.bea.httppubsub.descriptor.JmsHandlerMappingBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.MessageFilterBean", "com.bea.httppubsub.descriptor.MessageFilterBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.ServerConfigBean", "com.bea.httppubsub.descriptor.ServerConfigBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.ServiceBean", "com.bea.httppubsub.descriptor.ServiceBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.SupportedTransportBean", "com.bea.httppubsub.descriptor.SupportedTransportBeanImplBeanInfo");
      interfaceMap.put("com.bea.httppubsub.descriptor.WeblogicPubsubBean", "com.bea.httppubsub.descriptor.WeblogicPubsubBeanImplBeanInfo");
      roleInfoList = new ArrayList(0);
      SINGLETON = new BeanInfoFactory();
   }
}
