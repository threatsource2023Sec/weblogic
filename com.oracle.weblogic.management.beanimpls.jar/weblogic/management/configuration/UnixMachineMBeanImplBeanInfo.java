package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class UnixMachineMBeanImplBeanInfo extends MachineMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = UnixMachineMBean.class;

   public UnixMachineMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public UnixMachineMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.UnixMachineMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean represents a machine that is running the UNIX operating system. It extends MachineMBean with properties specific to the UNIX platform.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.UnixMachineMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("PostBindGID")) {
         getterName = "getPostBindGID";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPostBindGID";
         }

         currentResult = new PropertyDescriptor("PostBindGID", UnixMachineMBean.class, getterName, setterName);
         descriptors.put("PostBindGID", currentResult);
         currentResult.setValue("description", "<p>The UNIX group ID (GID) that a server running on this machine will run under after it has carried out all privileged startup actions. Otherwise, the server will continue to run under the group under which it was started.(Requires that you enable Post-Bind GID.)</p> ");
         setPropertyDescriptorDefault(currentResult, "nobody");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostBindUID")) {
         getterName = "getPostBindUID";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPostBindUID";
         }

         currentResult = new PropertyDescriptor("PostBindUID", UnixMachineMBean.class, getterName, setterName);
         descriptors.put("PostBindUID", currentResult);
         currentResult.setValue("description", "<p>The UNIX user ID (UID) that a server running on this machine will run under after it has carried out all privileged startup actions. Otherwise, the server will continue to run under the account under which it was started.(Requires that you enable Post-Bind UID.)</p> ");
         setPropertyDescriptorDefault(currentResult, "nobody");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostBindGIDEnabled")) {
         getterName = "isPostBindGIDEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPostBindGIDEnabled";
         }

         currentResult = new PropertyDescriptor("PostBindGIDEnabled", UnixMachineMBean.class, getterName, setterName);
         descriptors.put("PostBindGIDEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a server running on this machine binds to a UNIX Group ID (GID) after it has carried out all privileged startup actions.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostBindUIDEnabled")) {
         getterName = "isPostBindUIDEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPostBindUIDEnabled";
         }

         currentResult = new PropertyDescriptor("PostBindUIDEnabled", UnixMachineMBean.class, getterName, setterName);
         descriptors.put("PostBindUIDEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a server running on this machine binds to a UNIX User ID (UID) after it has carried out all privileged startup actions.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
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
