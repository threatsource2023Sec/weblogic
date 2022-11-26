package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ParserFactoryBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ParserFactoryBean.class;

   public ParserFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ParserFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ParserFactoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ParserFactoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("DocumentBuilderFactory")) {
         getterName = "getDocumentBuilderFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDocumentBuilderFactory";
         }

         currentResult = new PropertyDescriptor("DocumentBuilderFactory", ParserFactoryBean.class, getterName, setterName);
         descriptors.put("DocumentBuilderFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SaxparserFactory")) {
         getterName = "getSaxparserFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSaxparserFactory";
         }

         currentResult = new PropertyDescriptor("SaxparserFactory", ParserFactoryBean.class, getterName, setterName);
         descriptors.put("SaxparserFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SchemaFactory")) {
         getterName = "getSchemaFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSchemaFactory";
         }

         currentResult = new PropertyDescriptor("SchemaFactory", ParserFactoryBean.class, getterName, setterName);
         descriptors.put("SchemaFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransformerFactory")) {
         getterName = "getTransformerFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransformerFactory";
         }

         currentResult = new PropertyDescriptor("TransformerFactory", ParserFactoryBean.class, getterName, setterName);
         descriptors.put("TransformerFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLEventFactory")) {
         getterName = "getXMLEventFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXMLEventFactory";
         }

         currentResult = new PropertyDescriptor("XMLEventFactory", ParserFactoryBean.class, getterName, setterName);
         descriptors.put("XMLEventFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLInputFactory")) {
         getterName = "getXMLInputFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXMLInputFactory";
         }

         currentResult = new PropertyDescriptor("XMLInputFactory", ParserFactoryBean.class, getterName, setterName);
         descriptors.put("XMLInputFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLOutputFactory")) {
         getterName = "getXMLOutputFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXMLOutputFactory";
         }

         currentResult = new PropertyDescriptor("XMLOutputFactory", ParserFactoryBean.class, getterName, setterName);
         descriptors.put("XMLOutputFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XpathFactory")) {
         getterName = "getXpathFactory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setXpathFactory";
         }

         currentResult = new PropertyDescriptor("XpathFactory", ParserFactoryBean.class, getterName, setterName);
         descriptors.put("XpathFactory", currentResult);
         currentResult.setValue("description", " ");
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
