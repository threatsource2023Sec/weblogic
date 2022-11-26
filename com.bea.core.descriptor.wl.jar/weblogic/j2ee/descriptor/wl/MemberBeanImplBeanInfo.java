package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class MemberBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = MemberBean.class;

   public MemberBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MemberBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.MemberBeanImpl");
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
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.MemberBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CleartextOverrideValue")) {
         getterName = "getCleartextOverrideValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCleartextOverrideValue";
         }

         currentResult = new PropertyDescriptor("CleartextOverrideValue", MemberBean.class, getterName, setterName);
         descriptors.put("CleartextOverrideValue", currentResult);
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

         currentResult = new PropertyDescriptor("MemberName", MemberBean.class, getterName, setterName);
         descriptors.put("MemberName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MemberValue")) {
         getterName = "getMemberValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemberValue";
         }

         currentResult = new PropertyDescriptor("MemberValue", MemberBean.class, getterName, setterName);
         descriptors.put("MemberValue", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OverrideValue")) {
         getterName = "getOverrideValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOverrideValue";
         }

         currentResult = new PropertyDescriptor("OverrideValue", MemberBean.class, getterName, setterName);
         descriptors.put("OverrideValue", currentResult);
         currentResult.setValue("description", "This is the client API for getting/setting the override value. The value is not initialized from the annotation manifest. This property is implemented by the customizer. ");
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

         currentResult = new PropertyDescriptor("RequiresEncryption", MemberBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("SecuredOverrideValue", MemberBean.class, getterName, setterName);
         descriptors.put("SecuredOverrideValue", currentResult);
         currentResult.setValue("description", "Not to be called by clients. A workaround to support a persisted override value that may or may not be encrypted. secured-override-value ");
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

         currentResult = new PropertyDescriptor("SecuredOverrideValueEncrypted", MemberBean.class, getterName, setterName);
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
         currentResult = new PropertyDescriptor("ShortDescription", MemberBean.class, getterName, setterName);
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
