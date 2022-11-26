package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface WeblogicEnterpriseBeanMBean extends XMLElementMBean {
   String getEJBName();

   void setEJBName(String var1);

   EntityDescriptorMBean getEntityDescriptor();

   void setEntityDescriptor(EntityDescriptorMBean var1);

   StatelessSessionDescriptorMBean getStatelessSessionDescriptor();

   void setStatelessSessionDescriptor(StatelessSessionDescriptorMBean var1);

   StatefulSessionDescriptorMBean getStatefulSessionDescriptor();

   void setStatefulSessionDescriptor(StatefulSessionDescriptorMBean var1);

   MessageDrivenDescriptorMBean getMessageDrivenDescriptor();

   void setMessageDrivenDescriptor(MessageDrivenDescriptorMBean var1);

   TransactionDescriptorMBean getTransactionDescriptor();

   void setTransactionDescriptor(TransactionDescriptorMBean var1);

   IIOPSecurityDescriptorMBean getIIOPSecurityDescriptor();

   void setIIOPSecurityDescriptor(IIOPSecurityDescriptorMBean var1);

   ReferenceDescriptorMBean getReferenceDescriptor();

   void setReferenceDescriptor(ReferenceDescriptorMBean var1);

   boolean getEnableCallByReference();

   void setEnableCallByReference(boolean var1);

   boolean getClientsOnSameServer();

   void setClientsOnSameServer(boolean var1);

   String getRunAsIdentityPrincipal();

   void setRunAsIdentityPrincipal(String var1);

   String getCreateAsPrincipalName();

   void setCreateAsPrincipalName(String var1);

   String getRemoveAsPrincipalName();

   void setRemoveAsPrincipalName(String var1);

   String getPassivateAsPrincipalName();

   void setPassivateAsPrincipalName(String var1);

   String getJNDIName();

   void setJNDIName(String var1);

   String getLocalJNDIName();

   void setLocalJNDIName(String var1);

   String getDispatchPolicy();

   void setDispatchPolicy(String var1);

   int getRemoteClientTimeout();

   void setRemoteClientTimeout(int var1);

   boolean getStickToFirstServer();

   void setStickToFirstServer(boolean var1);
}
