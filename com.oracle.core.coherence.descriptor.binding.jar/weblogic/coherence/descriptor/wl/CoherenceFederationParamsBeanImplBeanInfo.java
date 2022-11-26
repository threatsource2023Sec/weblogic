package weblogic.coherence.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class CoherenceFederationParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CoherenceFederationParamsBean.class;

   public CoherenceFederationParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CoherenceFederationParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.coherence.descriptor.wl.CoherenceFederationParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("since", "12.2.1.1.0");
      beanDescriptor.setValue("package", "weblogic.coherence.descriptor.wl");
      String description = (new String("Bean to configure Federation configuration to be used. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.coherence.descriptor.wl.CoherenceFederationParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FederationTopology")) {
         getterName = "getFederationTopology";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFederationTopology";
         }

         currentResult = new PropertyDescriptor("FederationTopology", CoherenceFederationParamsBean.class, getterName, setterName);
         descriptors.put("FederationTopology", currentResult);
         currentResult.setValue("description", "<p>The federation topology. </p> ");
         setPropertyDescriptorDefault(currentResult, "none");
         currentResult.setValue("legalValues", new Object[]{"none", "active-active", "active-passive", "passive-active"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteCoherenceClusterListenPort")) {
         getterName = "getRemoteCoherenceClusterListenPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteCoherenceClusterListenPort";
         }

         currentResult = new PropertyDescriptor("RemoteCoherenceClusterListenPort", CoherenceFederationParamsBean.class, getterName, setterName);
         descriptors.put("RemoteCoherenceClusterListenPort", currentResult);
         currentResult.setValue("description", "<p>The Coherence Cluster Listen Port of the remote participant. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(7574));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteCoherenceClusterName")) {
         getterName = "getRemoteCoherenceClusterName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteCoherenceClusterName";
         }

         currentResult = new PropertyDescriptor("RemoteCoherenceClusterName", CoherenceFederationParamsBean.class, getterName, setterName);
         descriptors.put("RemoteCoherenceClusterName", currentResult);
         currentResult.setValue("description", "<p>The Coherence Cluster Name of the remote participant cluster. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteParticipantHosts")) {
         getterName = "getRemoteParticipantHosts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteParticipantHosts";
         }

         currentResult = new PropertyDescriptor("RemoteParticipantHosts", CoherenceFederationParamsBean.class, getterName, setterName);
         descriptors.put("RemoteParticipantHosts", currentResult);
         currentResult.setValue("description", "<p>The list of remote participant hosts, who will be added as participants in the federation topology. </p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CoherenceFederationParamsBean.class.getMethod("addRemoteParticipantHost", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("remoteParticipantHost", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds a remote participant hosts in the federation configuration. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "RemoteParticipantHosts");
      }

      mth = CoherenceFederationParamsBean.class.getMethod("removeRemoteParticipantHost", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("remoteParticipantHost", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Removes a remote participant host from the federation configuration. ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "RemoteParticipantHosts");
      }

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
