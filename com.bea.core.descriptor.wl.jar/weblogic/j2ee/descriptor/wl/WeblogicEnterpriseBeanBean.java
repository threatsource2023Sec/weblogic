package weblogic.j2ee.descriptor.wl;

public interface WeblogicEnterpriseBeanBean extends WeblogicEnvironmentBean {
   String getEjbName();

   void setEjbName(String var1);

   EntityDescriptorBean getEntityDescriptor();

   boolean isEntityDescriptorSet();

   StatelessSessionDescriptorBean getStatelessSessionDescriptor();

   boolean isStatelessSessionDescriptorSet();

   StatefulSessionDescriptorBean getStatefulSessionDescriptor();

   boolean isStatefulSessionDescriptorSet();

   SingletonSessionDescriptorBean getSingletonSessionDescriptor();

   boolean isSingletonSessionDescriptorSet();

   MessageDrivenDescriptorBean getMessageDrivenDescriptor();

   boolean isMessageDrivenDescriptorSet();

   TransactionDescriptorBean getTransactionDescriptor();

   boolean isTransactionDescriptorSet();

   IiopSecurityDescriptorBean getIiopSecurityDescriptor();

   boolean isIiopSecurityDescriptorSet();

   ResourceDescriptionBean[] getResourceDescriptions();

   ResourceDescriptionBean createResourceDescription();

   void destroyResourceDescription(ResourceDescriptionBean var1);

   ResourceEnvDescriptionBean[] getResourceEnvDescriptions();

   ResourceEnvDescriptionBean createResourceEnvDescription();

   void destroyResourceEnvDescription(ResourceEnvDescriptionBean var1);

   EjbReferenceDescriptionBean[] getEjbReferenceDescriptions();

   EjbReferenceDescriptionBean createEjbReferenceDescription();

   void destroyEjbReferenceDescription(EjbReferenceDescriptionBean var1);

   ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions();

   ServiceReferenceDescriptionBean createServiceReferenceDescription();

   void destroyServiceReferenceDescription(ServiceReferenceDescriptionBean var1);

   boolean isEnableCallByReference();

   void setEnableCallByReference(boolean var1);

   String getNetworkAccessPoint();

   void setNetworkAccessPoint(String var1);

   boolean isClientsOnSameServer();

   void setClientsOnSameServer(boolean var1);

   String getRunAsPrincipalName();

   void setRunAsPrincipalName(String var1);

   String getCreateAsPrincipalName();

   void setCreateAsPrincipalName(String var1);

   String getRemoveAsPrincipalName();

   void setRemoveAsPrincipalName(String var1);

   String getPassivateAsPrincipalName();

   void setPassivateAsPrincipalName(String var1);

   /** @deprecated */
   @Deprecated
   String getJNDIName();

   void setJNDIName(String var1);

   /** @deprecated */
   @Deprecated
   String getLocalJNDIName();

   void setLocalJNDIName(String var1);

   String getDispatchPolicy();

   void setDispatchPolicy(String var1);

   int getRemoteClientTimeout();

   void setRemoteClientTimeout(int var1);

   boolean isStickToFirstServer();

   void setStickToFirstServer(boolean var1);

   JndiBindingBean[] getJndiBinding();

   JndiBindingBean createJndiBinding();

   void destroyJndiBinding(JndiBindingBean var1);

   JndiBindingBean lookupJndiBinding(String var1);

   String getId();

   void setId(String var1);
}
