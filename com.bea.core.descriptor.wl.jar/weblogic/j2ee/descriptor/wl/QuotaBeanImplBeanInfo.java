package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class QuotaBeanImplBeanInfo extends NamedEntityBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = QuotaBean.class;

   public QuotaBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public QuotaBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.QuotaBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Quota beans control the allotment of system resources available to destinations. For example, the number of bytes a destination is allowed to store can be configured with a QuotaBean.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.QuotaBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BytesMaximum")) {
         getterName = "getBytesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBytesMaximum";
         }

         currentResult = new PropertyDescriptor("BytesMaximum", QuotaBean.class, getterName, setterName);
         descriptors.put("BytesMaximum", currentResult);
         currentResult.setValue("description", "<p>The total number of bytes that can be stored in a destination that uses this quota.</p>  <p>A value of zero means that no messages can be placed on a destination without exceeding the quota. A value of -1 prevents WebLogic Server from imposing a limit.</p>  <p>Because excessive bytes volume can cause memory saturation, Oracle recommends that the maximum corresponds to the amount of system memory that is available after accounting for the rest of your application load.</p>  <p>No consideration is given to messages that are pending; that is, messages that are in-flight, delayed, or otherwise inhibited from delivery still count against the message and/or bytes quota.</p>.  <p>This attribute is dynamic and can be changed at any time. If the quota is lowered and the quota object is now over quota, then subsequent requests for quota will be denied until quota is available. If the quota is raised, then this may allow the quota object to satisfy existing requests for quota.</p>  <p><b>Note:</b> If a JMS template is used for distributed destination members, then this maximum applies only to those specific members and not the distributed destination set as a whole.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessagesMaximum")) {
         getterName = "getMessagesMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessagesMaximum";
         }

         currentResult = new PropertyDescriptor("MessagesMaximum", QuotaBean.class, getterName, setterName);
         descriptors.put("MessagesMaximum", currentResult);
         currentResult.setValue("description", "<p>The total number of messages that can be stored in a destination that uses this quota.</p>  <p>A value of zero means that no messages can be placed on a destination without exceeding the quota. A value of -1 prevents WebLogic Server from imposing a limit.</p>  <p>Because excessive bytes volume can cause memory saturation, Oracle recommends that the maximum corresponds to the amount of system memory that is available after accounting for the rest of your application load.</p>  <p>No consideration is given to messages that are pending; that is, messages that are in-flight, delayed, or otherwise inhibited from delivery still count against the message and/or bytes quota.</p>.  <p>This attribute is dynamic and can be changed at any time. If the quota is lowered and the quota object is now over quota, then subsequent requests for quota will be denied until quota is available. If the quota is raised, then this may allow the quota object to satisfy existing requests for quota.</p>  <p><b>Note:</b> If a JMS template is used for distributed destination members, then this maximum applies only to those specific members and not the distributed destination set as a whole.</p> ");
         setPropertyDescriptorDefault(currentResult, new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMax", new Long(Long.MAX_VALUE));
         currentResult.setValue("legalMin", new Long(-1L));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Policy")) {
         getterName = "getPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPolicy";
         }

         currentResult = new PropertyDescriptor("Policy", QuotaBean.class, getterName, setterName);
         descriptors.put("Policy", currentResult);
         currentResult.setValue("description", "<p>For destinations that use this quota, this policy determines whether to deliver smaller messages before larger ones when a destination has exceeded its message quota.</p>  <p><code>FIFO</code> (first-in, first-out) indicates that requests for quota are submitted in the order they are received. If a given request for quota cannot be satisfied, and the client requesting the quota is willing to wait, then that request will block all other requests for quota until the request is satisfied or times out. This prevents smaller messages from being delivered when larger requests are already waiting for space.</p>  <p><code>Preemptive</code> indicates that subsequent requests can preempt previously unsatisfied requests. That is, if there is sufficient quota for the current request, then that space is used even if there are other requests waiting for quota. When quota is limited, the Preemptive policy can result in the starvation of larger requests.</p> ");
         setPropertyDescriptorDefault(currentResult, "FIFO");
         currentResult.setValue("legalValues", new Object[]{"FIFO", "Preemptive"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Shared")) {
         getterName = "isShared";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShared";
         }

         currentResult = new PropertyDescriptor("Shared", QuotaBean.class, getterName, setterName);
         descriptors.put("Shared", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this quota is shared by multiple destinations that refer to it.</p>  <p>If <code>Shared</code> is enabled (the default), all destinations referring to the quota object compete for  resources the resources defined by that object. If one destination consumes a large number of messages or bytes, then this will prevent other destinations from obtaining quota.</p>  <p>If <code>Shared</code> is disabled, the quota object behaves as a template. Each destination referring to the quota object gets its own internal instance of the object for tracking quota.</p>  <p>Destinations within the same JMS module may share quotas in any way they want. However, quota sharing only takes place for destinations within the same JMS server. In other words, if destinations <i>X</i> and <i>Y</i> both share the same quota <i>Q</i>, and the quota <i>Q</i> has <code>Shared</code> enabled, then <i>X</i> and <i>Y</i> will only share quota if both are deployed to the same JMS server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
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
