package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class XMLEntitySpecRegistryEntryMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = XMLEntitySpecRegistryEntryMBean.class;

   public XMLEntitySpecRegistryEntryMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public XMLEntitySpecRegistryEntryMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.XMLEntitySpecRegistryEntryMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>An Entity Spec Entry specifies how to resolve an external entity based its public and system IDs.</p>  <p>When WebLogic Server is parsing an XML document and it encounters the specified external entity, WebLogic Server resolves the entity according to the values entered for this entry.  You can specify that the external entity resolve to a particular resource whose location is either a pathname reachable by the Administration Server or a URI to a local repository.</p>  <p>An Entity Spec entry is part of an XML Registry.</p>  <p>For this type of registry entry the document type is identified by either or both of:</p>  <ul> <li>a public ID (e.g, \"-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN\"</li> <li>a system ID (e.g, \"http://java.sun.com/j2ee/dtds/ejb-jar_2_0.dtd\")</li> </ul>  <p>This configuration information is used by the WebLogic JAXP implementation to set up SAX EntityResolvers.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.XMLEntitySpecRegistryEntryMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CacheTimeoutInterval")) {
         getterName = "getCacheTimeoutInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCacheTimeoutInterval";
         }

         currentResult = new PropertyDescriptor("CacheTimeoutInterval", XMLEntitySpecRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("CacheTimeoutInterval", currentResult);
         currentResult.setValue("description", "<p>Specifies the default timeout interval (in seconds) of the external entity cache.</p>  <p>A value of <code>-1</code> means that the entity cache timeout interval defers to the one specified for the XML registry of which this Entity Spec entry is a part.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityURI")) {
         getterName = "getEntityURI";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEntityURI";
         }

         currentResult = new PropertyDescriptor("EntityURI", XMLEntitySpecRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("EntityURI", currentResult);
         currentResult.setValue("description", "<p>The location of the external entity, either a pathname or URI.</p>  <p>The location of the external entity can be either a pathname relative to the XML Registry directories, reachable by the Administration Server, or a URI of the entity location in some local repository.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HandleEntityInvalidation")) {
         getterName = "getHandleEntityInvalidation";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHandleEntityInvalidation";
         }

         currentResult = new PropertyDescriptor("HandleEntityInvalidation", XMLEntitySpecRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("HandleEntityInvalidation", currentResult);
         currentResult.setValue("description", "<p>Whether cached DTD/schema is invalidated when parsing error is encountered.</p> ");
         setPropertyDescriptorDefault(currentResult, "defer-to-registry-setting");
         currentResult.setValue("legalValues", new Object[]{"true", "false", "defer-to-registry-setting"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PublicId")) {
         getterName = "getPublicId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPublicId";
         }

         currentResult = new PropertyDescriptor("PublicId", XMLEntitySpecRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("PublicId", currentResult);
         currentResult.setValue("description", "<p>The public ID of the external entity.</p>  <p>When WebLogic Server is parsing an XML document and it encounters an external entity with this public ID, WebLogic Server resolves the entity (to either a local file or a URL resource) according to the values set for this Entity spec entry.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SystemId")) {
         getterName = "getSystemId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSystemId";
         }

         currentResult = new PropertyDescriptor("SystemId", XMLEntitySpecRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("SystemId", currentResult);
         currentResult.setValue("description", "<p>The system ID of the external entity.</p>  <p>When WebLogic Server is parsing an XML document and it encounters an external entity with this system ID, WebLogic Server resolves the entity (to either a local file or a URL resource) according to the values set for this Entity spec entry.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WhenToCache")) {
         getterName = "getWhenToCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWhenToCache";
         }

         currentResult = new PropertyDescriptor("WhenToCache", XMLEntitySpecRegistryEntryMBean.class, getterName, setterName);
         descriptors.put("WhenToCache", currentResult);
         currentResult.setValue("description", "<p>Specifies when WebLogic Server should cache the external entities it retrieves from the Web.</p>  <p>WebLogic Server can cache the entities when they are referenced, as soon as possible (that is, on initialization) or never. Additionally, WebLogic Server can defer to the XML registry's cache setting.</p> ");
         setPropertyDescriptorDefault(currentResult, "defer-to-registry-setting");
         currentResult.setValue("legalValues", new Object[]{"cache-on-reference", "cache-at-initialization", "cache-never", "defer-to-registry-setting"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
