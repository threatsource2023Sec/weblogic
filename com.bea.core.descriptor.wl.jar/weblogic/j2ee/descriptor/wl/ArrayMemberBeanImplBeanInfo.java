package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ArrayMemberBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ArrayMemberBean.class;

   public ArrayMemberBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ArrayMemberBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ArrayMemberBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ArrayMemberBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CleartextOverrideValues")) {
         getterName = "getCleartextOverrideValues";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCleartextOverrideValues";
         }

         currentResult = new PropertyDescriptor("CleartextOverrideValues", ArrayMemberBean.class, getterName, setterName);
         descriptors.put("CleartextOverrideValues", currentResult);
         currentResult.setValue("description", "Not to be called by clients. A workaround to support a persisted override value that may or may not be encrypted. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MemberName")) {
         getterName = "getMemberName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemberName";
         }

         currentResult = new PropertyDescriptor("MemberName", ArrayMemberBean.class, getterName, setterName);
         descriptors.put("MemberName", currentResult);
         currentResult.setValue("description", "According to DConfigBean generation, key must be of string type ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MemberValues")) {
         getterName = "getMemberValues";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemberValues";
         }

         currentResult = new PropertyDescriptor("MemberValues", ArrayMemberBean.class, getterName, setterName);
         descriptors.put("MemberValues", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OverrideValues")) {
         getterName = "getOverrideValues";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOverrideValues";
         }

         currentResult = new PropertyDescriptor("OverrideValues", ArrayMemberBean.class, getterName, setterName);
         descriptors.put("OverrideValues", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RequiresEncryption")) {
         getterName = "getRequiresEncryption";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRequiresEncryption";
         }

         currentResult = new PropertyDescriptor("RequiresEncryption", ArrayMemberBean.class, getterName, setterName);
         descriptors.put("RequiresEncryption", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecuredOverrideValue")) {
         getterName = "getSecuredOverrideValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecuredOverrideValue";
         }

         currentResult = new PropertyDescriptor("SecuredOverrideValue", ArrayMemberBean.class, getterName, setterName);
         descriptors.put("SecuredOverrideValue", currentResult);
         currentResult.setValue("description", "Not to be called by clients. A workaround to support encryption. secured-override-value Note: Encrypted arrays values are concatinated into one string. ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.j2ee.customizers.ArrayMemberBeanCustomizer")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecuredOverrideValueEncrypted")) {
         getterName = "getSecuredOverrideValueEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecuredOverrideValueEncrypted";
         }

         currentResult = new PropertyDescriptor("SecuredOverrideValueEncrypted", ArrayMemberBean.class, getterName, setterName);
         descriptors.put("SecuredOverrideValueEncrypted", currentResult);
         currentResult.setValue("description", "This should not be called by anyone.  It's required when using the encrypted tag ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShortDescription")) {
         getterName = "getShortDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("ShortDescription", ArrayMemberBean.class, getterName, setterName);
         descriptors.put("ShortDescription", currentResult);
         currentResult.setValue("description", "Localized description ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
