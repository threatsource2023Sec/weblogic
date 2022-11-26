package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class JMSReceiveTopicMBeanImpl extends XMLElementMBeanDelegate implements JMSReceiveTopicMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_providerUrl = false;
   private String providerUrl;
   private boolean isSet_connectionFactory = false;
   private String connectionFactory;
   private boolean isSet_componentName = false;
   private String componentName;
   private boolean isSet_initialContextFactory = false;
   private String initialContextFactory;
   private boolean isSet_jndiName = false;
   private JNDINameMBean jndiName;

   public String getProviderUrl() {
      return this.providerUrl;
   }

   public void setProviderUrl(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.providerUrl;
      this.providerUrl = value;
      this.isSet_providerUrl = value != null;
      this.checkChange("providerUrl", old, this.providerUrl);
   }

   public String getConnectionFactory() {
      return this.connectionFactory;
   }

   public void setConnectionFactory(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.connectionFactory;
      this.connectionFactory = value;
      this.isSet_connectionFactory = value != null;
      this.checkChange("connectionFactory", old, this.connectionFactory);
   }

   public String getComponentName() {
      return this.componentName;
   }

   public void setComponentName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.componentName;
      this.componentName = value;
      this.isSet_componentName = value != null;
      this.checkChange("componentName", old, this.componentName);
   }

   public String getInitialContextFactory() {
      return this.initialContextFactory;
   }

   public void setInitialContextFactory(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.initialContextFactory;
      this.initialContextFactory = value;
      this.isSet_initialContextFactory = value != null;
      this.checkChange("initialContextFactory", old, this.initialContextFactory);
   }

   public JNDINameMBean getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(JNDINameMBean value) {
      JNDINameMBean old = this.jndiName;
      this.jndiName = value;
      this.isSet_jndiName = value != null;
      this.checkChange("jndiName", old, this.jndiName);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<jms-receive-topic");
      if (this.isSet_componentName) {
         result.append(" name=\"").append(String.valueOf(this.getComponentName())).append("\"");
      }

      if (this.isSet_providerUrl) {
         result.append(" provider-url=\"").append(String.valueOf(this.getProviderUrl())).append("\"");
      }

      if (this.isSet_initialContextFactory) {
         result.append(" initial-context-factory=\"").append(String.valueOf(this.getInitialContextFactory())).append("\"");
      }

      if (this.isSet_connectionFactory) {
         result.append(" connection-factory=\"").append(String.valueOf(this.getConnectionFactory())).append("\"");
      }

      result.append(">\n");
      if (null != this.getJNDIName()) {
         result.append(this.getJNDIName().toXML(indentLevel + 2)).append("\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</jms-receive-topic>\n");
      return result.toString();
   }
}
