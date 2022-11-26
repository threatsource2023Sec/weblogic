package weblogic.jms.module;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class JMSResourceDefinitionManager {
   private static final JMSResourceDefinitionManager INSTANCE = new JMSResourceDefinitionManager();
   private final Map validJMSModules = new HashMap();
   private final Map applicationClientNodes = new HashMap();
   private Map connectionFactoryBeanGetTypeMethods = new HashMap();
   private Map connectionFactoryBeanSetMethods = new HashMap();
   private Map udQueueBeanGetTypeMethods = new HashMap();
   private Map udQueueBeanSetMethods = new HashMap();
   private Map udTopicBeanGetTypeMethods = new HashMap();
   private Map udTopicBeanSetMethods = new HashMap();

   private JMSResourceDefinitionManager() {
   }

   public static JMSResourceDefinitionManager getInstance() {
      return INSTANCE;
   }

   void addJMSModule(String moduleName, JMSModule jmsModule) {
      this.validJMSModules.put(moduleName, jmsModule);
   }

   Map getAllJMSModules() {
      return this.validJMSModules;
   }

   Map getConnectionFactoryBeanSetMethods() {
      return this.connectionFactoryBeanSetMethods;
   }

   void addConnectionFactoryBeanSetMethod(String beanProperty, Method beanMethod) {
      this.connectionFactoryBeanSetMethods.put(beanProperty, beanMethod);
   }

   Map getConnectionFactoryBeanGetTypeMethods() {
      return this.connectionFactoryBeanGetTypeMethods;
   }

   void addConnectionFactoryBeanGetTypeMethod(String beanProperty, Class complexType) {
      this.connectionFactoryBeanGetTypeMethods.put(beanProperty, complexType);
   }

   Map getUDQueueBeanSetMethods() {
      return this.udQueueBeanSetMethods;
   }

   void addUDQueueBeanSetMethod(String beanProperty, Method beanMethod) {
      this.udQueueBeanSetMethods.put(beanProperty, beanMethod);
   }

   Map getUDQueueBeanGetTypeMethods() {
      return this.udQueueBeanGetTypeMethods;
   }

   void addUDQueueBeanGetTypeMethod(String beanProperty, Class complexType) {
      this.udQueueBeanGetTypeMethods.put(beanProperty, complexType);
   }

   Map getUDTopicBeanSetMethods() {
      return this.udTopicBeanSetMethods;
   }

   void addUDTopicBeanSetMethod(String beanProperty, Method beanMethod) {
      this.udTopicBeanSetMethods.put(beanProperty, beanMethod);
   }

   Map getUDTopicBeanGetTypeMethodss() {
      return this.udTopicBeanGetTypeMethods;
   }

   void addUDTopicBeanGetTypeMethods(String beanProperty, Class complexType) {
      this.udTopicBeanGetTypeMethods.put(beanProperty, complexType);
   }

   void addApplicationClientNode(String moduleName, String moduleId) {
      this.applicationClientNodes.put(moduleName, moduleId);
   }

   Map getAllApplicationClientNodes() {
      return this.applicationClientNodes;
   }
}
