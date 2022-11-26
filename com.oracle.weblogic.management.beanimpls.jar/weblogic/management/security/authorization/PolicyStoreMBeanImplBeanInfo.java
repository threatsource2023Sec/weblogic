package weblogic.management.security.authorization;

import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.utils.PropertiesListerMBeanImplBeanInfo;

public class PolicyStoreMBeanImplBeanInfo extends PropertiesListerMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PolicyStoreMBean.class;

   public PolicyStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PolicyStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.security.authorization.PolicyStoreMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("abstract", Boolean.TRUE);
      beanDescriptor.setValue("since", "9.2.0.0");
      beanDescriptor.setValue("VisibleToPartitions", "ALWAYS");
      beanDescriptor.setValue("owner", "RealmAdministrator");
      beanDescriptor.setValue("package", "weblogic.management.security.authorization");
      String description = (new String("<p>Provides a set of methods for managing policies in a policy store. An Authorization-provider or Role Mapping-provider MBean can optionally implement this MBean interface.  Policies are expressed as XACML 2.0 Policy or PolicySet documents.</p>  <p>Authorization-providers should expect standard <code>Policy</code> or <code>PolicySet</code> documents as described in the XACML 2.0 Core Specification.</p>  <p>Role Mapping-providers should expect <code>Policy</code> or <code>PolicySet</code> documents consistent with role assignment policies described by the Role Based Access Control Profile, specifically the <code>Target</code> must contain:</p> <ul> <li>An ActionAttributeDesignator with the id, urn:oasis:names:tc:xacml:1.0:action:action-id, and the value, urn:oasis:names:tc:xacml:2.0:actions:enableRole, according to anyURI-equal</li> <li>A ResourceAttributeDesignator with the id, urn:oasis:names:tc:xacml:2.0:subject:role, and a value naming the role being assigned, according to string-equal</li> </ul> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.security.authorization.PolicyStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PolicyStoreMBean.class.getMethod("listAllPolicies");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if no policies are found")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns cursor listing all policies ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("listAllPoliciesAsString");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if no policies are found")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns cursor listing all policies.  Policies are returned as java.lang.String. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("listAllPolicySets");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if no policy sets are found")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns cursor listing all policy sets ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("listAllPolicySetsAsString");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if no policy sets are found")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns cursor listing all policy sets.  Policy sets are returned as java.lang.String. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("readPolicy", String.class, String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy identifier "), createParameterDescriptor("version", "Policy version ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy is not found")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Reads policy with specified identifier and version ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
      }

      mth = PolicyStoreMBean.class.getMethod("readPolicyAsString", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy identifier "), createParameterDescriptor("version", "Policy version ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy is not found")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Reads policy with specified identifier and version ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("readPolicySet", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy set identifier "), createParameterDescriptor("version", "Policy set version ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy set is not found")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Reads policy set with specified identifier and version ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
      }

      mth = PolicyStoreMBean.class.getMethod("readPolicySetAsString", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy set identifier "), createParameterDescriptor("version", "Policy set version ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy set is not found")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Reads policy set with specified identifier and version ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("addPolicy", Policy.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", "Policy document ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("AlreadyExistsException Thrown if matching policy id and version already present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds policy.  Policy is set to ACTIVE status. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
      }

      mth = PolicyStoreMBean.class.getMethod("addPolicy", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", "Policy document ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("AlreadyExistsException Thrown if matching policy id and version already present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds policy.  Policy is set to ACTIVE status. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("addPolicy", Policy.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", "Policy document "), createParameterDescriptor("status", "Policy status ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("AlreadyExistsException Thrown if matching policy id and version already present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds policy ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
      }

      mth = PolicyStoreMBean.class.getMethod("addPolicy", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", "Policy document "), createParameterDescriptor("status", "Policy status ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("AlreadyExistsException Thrown if matching policy id and version already present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds policy ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("addPolicySet", PolicySet.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("set", "Policy set document ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy set is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("AlreadyExistsException Thrown if matching policy set id and version already present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds policy set.  Policy set is set to ACTIVE status. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
      }

      mth = PolicyStoreMBean.class.getMethod("addPolicySet", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("set", "Policy set document ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy set is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("AlreadyExistsException Thrown if matching policy set id and version already present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds policy set.  Policy set is set to ACTIVE status. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("addPolicySet", PolicySet.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("set", "Policy set document "), createParameterDescriptor("status", "Policy status enumeration ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy set is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("AlreadyExistsException Thrown if matching policy set id and version already present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds policy set. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
      }

      mth = PolicyStoreMBean.class.getMethod("addPolicySet", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("set", "Policy set document "), createParameterDescriptor("status", "Policy status enumeration ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy set is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("AlreadyExistsException Thrown if matching policy set id and version already present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Adds policy set. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicy", Policy.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", "Policy document ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates policy.  This operation does not change the status of policy. already present in the store. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicy", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", "Policy document ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates policy.  This operation does not change the status of policy. already present in the store. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicy", Policy.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", "Policy document "), createParameterDescriptor("status", "Policy status enumeration ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates policy and status. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for Policy");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicy", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", "Policy document "), createParameterDescriptor("status", "Policy status enumeration ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates policy and status. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicySet", PolicySet.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("set", "Policy set document ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy set is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy set id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates policy set.  This operation does not change the status of policy set. already present in the store. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicySet", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("set", "Policy set document ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy set is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy set id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates policy set.  This operation does not change the status of policy set. already present in the store. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicySet", PolicySet.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("set", "Policy set document "), createParameterDescriptor("status", "Policy status enumeration ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy set is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy set id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates policy set and status. ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for PolicySet");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicySet", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("set", "Policy set document "), createParameterDescriptor("status", "Policy status enumeration ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if policy set is invalid or store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy set id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Updates policy set and status. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicyStatus", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy identifier "), createParameterDescriptor("version", "Policy version "), createParameterDescriptor("status", "Policy status enumeration ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Sets status for policy identified by identifier and version ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("getPolicyStatus", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy identifier "), createParameterDescriptor("version", "Policy version ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundExeption Thrown if matching policy id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns status for policy identified by identifier and version ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("modifyPolicySetStatus", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy set identifier "), createParameterDescriptor("version", "Policy set version "), createParameterDescriptor("status", "Policy status enumeration ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("CreateException Thrown if store cannot be updated"), BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy set id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Sets status for policy set identified by identifier and version ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("getPolicySetStatus", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy set identifier "), createParameterDescriptor("version", "Policy set version ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundExeption Thrown if matching policy set id and version not present in store")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns status for policy set identified by identifier and version ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("deletePolicy", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy identifier "), createParameterDescriptor("version", "Policy version ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy id and version not present in store"), BeanInfoHelper.encodeEntities("RemoveException Thrown if store cannot be updated")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Deletes policy with given identifier and version. ");
         currentResult.setValue("role", "operation");
      }

      mth = PolicyStoreMBean.class.getMethod("deletePolicySet", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("identifier", "Policy set identifier "), createParameterDescriptor("version", "Policy set version ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("NotFoundException Thrown if matching policy set id and version not present in store"), BeanInfoHelper.encodeEntities("RemoveException Thrown if store cannot be updated")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Deletes policy set with given identifier and version. ");
         currentResult.setValue("role", "operation");
      }

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
