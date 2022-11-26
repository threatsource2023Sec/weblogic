package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicEnterpriseBeanMBeanImpl extends XMLElementMBeanDelegate implements WeblogicEnterpriseBeanMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_transactionDescriptor = false;
   private TransactionDescriptorMBean transactionDescriptor;
   private boolean isSet_createAsPrincipalName = false;
   private String createAsPrincipalName;
   private boolean isSet_passivateAsPrincipalName = false;
   private String passivateAsPrincipalName;
   private boolean isSet_jndiName = false;
   private String jndiName;
   private boolean isSet_messageDrivenDescriptor = false;
   private MessageDrivenDescriptorMBean messageDrivenDescriptor;
   private boolean isSet_stickToFirstServer = false;
   private boolean stickToFirstServer = false;
   private boolean isSet_statefulSessionDescriptor = false;
   private StatefulSessionDescriptorMBean statefulSessionDescriptor;
   private boolean isSet_statelessSessionDescriptor = false;
   private StatelessSessionDescriptorMBean statelessSessionDescriptor;
   private boolean isSet_dispatchPolicy = false;
   private String dispatchPolicy;
   private boolean isSet_removeAsPrincipalName = false;
   private String removeAsPrincipalName;
   private boolean isSet_remoteClientTimeout = false;
   private int remoteClientTimeout = 0;
   private boolean isSet_runAsIdentityPrincipal = false;
   private String runAsIdentityPrincipal;
   private boolean isSet_iiopSecurityDescriptor = false;
   private IIOPSecurityDescriptorMBean iiopSecurityDescriptor;
   private boolean isSet_entityDescriptor = false;
   private EntityDescriptorMBean entityDescriptor;
   private boolean isSet_ejbName = false;
   private String ejbName;
   private boolean isSet_enableCallByReference = false;
   private boolean enableCallByReference = false;
   private boolean isSet_referenceDescriptor = false;
   private ReferenceDescriptorMBean referenceDescriptor;
   private boolean isSet_clientsOnSameServer = false;
   private boolean clientsOnSameServer = false;
   private boolean isSet_localJNDIName = false;
   private String localJNDIName;

   public TransactionDescriptorMBean getTransactionDescriptor() {
      return this.transactionDescriptor;
   }

   public void setTransactionDescriptor(TransactionDescriptorMBean value) {
      TransactionDescriptorMBean old = this.transactionDescriptor;
      this.transactionDescriptor = value;
      this.isSet_transactionDescriptor = value != null;
      this.checkChange("transactionDescriptor", old, this.transactionDescriptor);
   }

   public String getCreateAsPrincipalName() {
      return this.createAsPrincipalName;
   }

   public void setCreateAsPrincipalName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.createAsPrincipalName;
      this.createAsPrincipalName = value;
      this.isSet_createAsPrincipalName = value != null;
      this.checkChange("createAsPrincipalName", old, this.createAsPrincipalName);
   }

   public String getPassivateAsPrincipalName() {
      return this.passivateAsPrincipalName;
   }

   public void setPassivateAsPrincipalName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.passivateAsPrincipalName;
      this.passivateAsPrincipalName = value;
      this.isSet_passivateAsPrincipalName = value != null;
      this.checkChange("passivateAsPrincipalName", old, this.passivateAsPrincipalName);
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.jndiName;
      this.jndiName = value;
      this.isSet_jndiName = value != null;
      this.checkChange("jndiName", old, this.jndiName);
   }

   public MessageDrivenDescriptorMBean getMessageDrivenDescriptor() {
      return this.messageDrivenDescriptor;
   }

   public void setMessageDrivenDescriptor(MessageDrivenDescriptorMBean value) {
      MessageDrivenDescriptorMBean old = this.messageDrivenDescriptor;
      this.messageDrivenDescriptor = value;
      this.isSet_messageDrivenDescriptor = value != null;
      this.checkChange("messageDrivenDescriptor", old, this.messageDrivenDescriptor);
   }

   public boolean getStickToFirstServer() {
      return this.stickToFirstServer;
   }

   public void setStickToFirstServer(boolean value) {
      boolean old = this.stickToFirstServer;
      this.stickToFirstServer = value;
      this.isSet_stickToFirstServer = true;
      this.checkChange("stickToFirstServer", old, this.stickToFirstServer);
   }

   public StatefulSessionDescriptorMBean getStatefulSessionDescriptor() {
      return this.statefulSessionDescriptor;
   }

   public void setStatefulSessionDescriptor(StatefulSessionDescriptorMBean value) {
      StatefulSessionDescriptorMBean old = this.statefulSessionDescriptor;
      this.statefulSessionDescriptor = value;
      this.isSet_statefulSessionDescriptor = value != null;
      this.checkChange("statefulSessionDescriptor", old, this.statefulSessionDescriptor);
   }

   public StatelessSessionDescriptorMBean getStatelessSessionDescriptor() {
      return this.statelessSessionDescriptor;
   }

   public void setStatelessSessionDescriptor(StatelessSessionDescriptorMBean value) {
      StatelessSessionDescriptorMBean old = this.statelessSessionDescriptor;
      this.statelessSessionDescriptor = value;
      this.isSet_statelessSessionDescriptor = value != null;
      this.checkChange("statelessSessionDescriptor", old, this.statelessSessionDescriptor);
   }

   public String getDispatchPolicy() {
      return this.dispatchPolicy;
   }

   public void setDispatchPolicy(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.dispatchPolicy;
      this.dispatchPolicy = value;
      this.isSet_dispatchPolicy = value != null;
      this.checkChange("dispatchPolicy", old, this.dispatchPolicy);
   }

   public String getRemoveAsPrincipalName() {
      return this.removeAsPrincipalName;
   }

   public void setRemoveAsPrincipalName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.removeAsPrincipalName;
      this.removeAsPrincipalName = value;
      this.isSet_removeAsPrincipalName = value != null;
      this.checkChange("removeAsPrincipalName", old, this.removeAsPrincipalName);
   }

   public int getRemoteClientTimeout() {
      return this.remoteClientTimeout;
   }

   public void setRemoteClientTimeout(int value) {
      int old = this.remoteClientTimeout;
      this.remoteClientTimeout = value;
      this.isSet_remoteClientTimeout = value != -1;
      this.checkChange("remoteClientTimeout", old, this.remoteClientTimeout);
   }

   public String getRunAsIdentityPrincipal() {
      return this.runAsIdentityPrincipal;
   }

   public void setRunAsIdentityPrincipal(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.runAsIdentityPrincipal;
      this.runAsIdentityPrincipal = value;
      this.isSet_runAsIdentityPrincipal = value != null;
      this.checkChange("runAsIdentityPrincipal", old, this.runAsIdentityPrincipal);
   }

   public IIOPSecurityDescriptorMBean getIIOPSecurityDescriptor() {
      return this.iiopSecurityDescriptor;
   }

   public void setIIOPSecurityDescriptor(IIOPSecurityDescriptorMBean value) {
      IIOPSecurityDescriptorMBean old = this.iiopSecurityDescriptor;
      this.iiopSecurityDescriptor = value;
      this.isSet_iiopSecurityDescriptor = value != null;
      this.checkChange("iiopSecurityDescriptor", old, this.iiopSecurityDescriptor);
   }

   public EntityDescriptorMBean getEntityDescriptor() {
      return this.entityDescriptor;
   }

   public void setEntityDescriptor(EntityDescriptorMBean value) {
      EntityDescriptorMBean old = this.entityDescriptor;
      this.entityDescriptor = value;
      this.isSet_entityDescriptor = value != null;
      this.checkChange("entityDescriptor", old, this.entityDescriptor);
   }

   public String getEJBName() {
      return this.ejbName;
   }

   public void setEJBName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbName;
      this.ejbName = value;
      this.isSet_ejbName = value != null;
      this.checkChange("ejbName", old, this.ejbName);
   }

   public boolean getEnableCallByReference() {
      return this.enableCallByReference;
   }

   public void setEnableCallByReference(boolean value) {
      boolean old = this.enableCallByReference;
      this.enableCallByReference = value;
      this.isSet_enableCallByReference = true;
      this.checkChange("enableCallByReference", old, this.enableCallByReference);
   }

   public ReferenceDescriptorMBean getReferenceDescriptor() {
      return this.referenceDescriptor;
   }

   public void setReferenceDescriptor(ReferenceDescriptorMBean value) {
      ReferenceDescriptorMBean old = this.referenceDescriptor;
      this.referenceDescriptor = value;
      this.isSet_referenceDescriptor = value != null;
      this.checkChange("referenceDescriptor", old, this.referenceDescriptor);
   }

   public boolean getClientsOnSameServer() {
      return this.clientsOnSameServer;
   }

   public void setClientsOnSameServer(boolean value) {
      boolean old = this.clientsOnSameServer;
      this.clientsOnSameServer = value;
      this.isSet_clientsOnSameServer = true;
      this.checkChange("clientsOnSameServer", old, this.clientsOnSameServer);
   }

   public String getLocalJNDIName() {
      return this.localJNDIName;
   }

   public void setLocalJNDIName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.localJNDIName;
      this.localJNDIName = value;
      this.isSet_localJNDIName = value != null;
      this.checkChange("localJNDIName", old, this.localJNDIName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-enterprise-bean");
      result.append(">\n");
      if (null != this.getEJBName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-name>").append(this.getEJBName()).append("</ejb-name>\n");
      }

      if (null != this.getStatefulSessionDescriptor()) {
         result.append(this.getStatefulSessionDescriptor().toXML(indentLevel + 2)).append("\n");
      } else if (null != this.getStatelessSessionDescriptor()) {
         result.append(this.getStatelessSessionDescriptor().toXML(indentLevel + 2)).append("\n");
      } else if (null != this.getMessageDrivenDescriptor()) {
         result.append(this.getMessageDrivenDescriptor().toXML(indentLevel + 2)).append("\n");
      } else if (null != this.getEntityDescriptor()) {
         result.append(this.getEntityDescriptor().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getTransactionDescriptor()) {
         result.append(this.getTransactionDescriptor().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getIIOPSecurityDescriptor()) {
         result.append(this.getIIOPSecurityDescriptor().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getReferenceDescriptor()) {
         result.append(this.getReferenceDescriptor().toXML(indentLevel + 2)).append("\n");
      }

      if (this.isSet_enableCallByReference || this.getEnableCallByReference()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<enable-call-by-reference>").append(ToXML.capitalize(Boolean.valueOf(this.getEnableCallByReference()).toString())).append("</enable-call-by-reference>\n");
      }

      if (this.isSet_clientsOnSameServer || this.getClientsOnSameServer()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<clients-on-same-server>").append(ToXML.capitalize(Boolean.valueOf(this.getClientsOnSameServer()).toString())).append("</clients-on-same-server>\n");
      }

      if (null != this.getRunAsIdentityPrincipal()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<run-as-identity-principal>").append(this.getRunAsIdentityPrincipal()).append("</run-as-identity-principal>\n");
      }

      if (null != this.getCreateAsPrincipalName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<create-as-principal-name>").append(this.getCreateAsPrincipalName()).append("</create-as-principal-name>\n");
      }

      if (null != this.getRemoveAsPrincipalName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<remove-as-principal-name>").append(this.getRemoveAsPrincipalName()).append("</remove-as-principal-name>\n");
      }

      if (null != this.getPassivateAsPrincipalName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<passivate-as-principal-name>").append(this.getPassivateAsPrincipalName()).append("</passivate-as-principal-name>\n");
      }

      if (null != this.getJNDIName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<jndi-name>").append(this.getJNDIName()).append("</jndi-name>\n");
      }

      if (null != this.getLocalJNDIName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<local-jndi-name>").append(this.getLocalJNDIName()).append("</local-jndi-name>\n");
      }

      if (null != this.getDispatchPolicy()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<dispatch-policy>").append(this.getDispatchPolicy()).append("</dispatch-policy>\n");
      }

      if (this.isSet_remoteClientTimeout || 0 != this.getRemoteClientTimeout()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<remote-client-timeout>").append(this.getRemoteClientTimeout()).append("</remote-client-timeout>\n");
      }

      if (this.isSet_stickToFirstServer || this.getStickToFirstServer()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<stick-to-first-server>").append(ToXML.capitalize(Boolean.valueOf(this.getStickToFirstServer()).toString())).append("</stick-to-first-server>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</weblogic-enterprise-bean>\n");
      return result.toString();
   }
}
