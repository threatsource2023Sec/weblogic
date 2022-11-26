package weblogic.management.security.authentication;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo;

public class GroupMembershipHierarchyCacheMBeanImplBeanInfo extends AbstractCommoConfigurationBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = GroupMembershipHierarchyCacheMBean.class;

   public GroupMembershipHierarchyCacheMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public GroupMembershipHierarchyCacheMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authentication.GroupMembershipHierarchyCacheMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.management.security.authentication");
      String description = (new String("Defines methods used to get/set the configuration attributes that are required to support the Group Membership Hierarchy Cache. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authentication.GroupMembershipHierarchyCacheMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("EnableGroupMembershipLookupHierarchyCaching")) {
         getterName = "getEnableGroupMembershipLookupHierarchyCaching";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableGroupMembershipLookupHierarchyCaching";
         }

         currentResult = new PropertyDescriptor("EnableGroupMembershipLookupHierarchyCaching", GroupMembershipHierarchyCacheMBean.class, getterName, setterName);
         descriptors.put("EnableGroupMembershipLookupHierarchyCaching", currentResult);
         currentResult.setValue("description", "Returns whether group membership hierarchies found during recursive membership lookup will be cached. If true, each subtree found will be cached. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GroupHierarchyCacheTTL")) {
         getterName = "getGroupHierarchyCacheTTL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGroupHierarchyCacheTTL";
         }

         currentResult = new PropertyDescriptor("GroupHierarchyCacheTTL", GroupMembershipHierarchyCacheMBean.class, getterName, setterName);
         descriptors.put("GroupHierarchyCacheTTL", currentResult);
         currentResult.setValue("description", "Returns the maximum number of seconds a group membership hierarchy entry is valid in the LRU cache. ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxGroupHierarchiesInCache")) {
         getterName = "getMaxGroupHierarchiesInCache";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxGroupHierarchiesInCache";
         }

         currentResult = new PropertyDescriptor("MaxGroupHierarchiesInCache", GroupMembershipHierarchyCacheMBean.class, getterName, setterName);
         descriptors.put("MaxGroupHierarchiesInCache", currentResult);
         currentResult.setValue("description", "Returns the maximum size of the LRU cache for holding group membership hierarchies if caching is enabled. ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
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
