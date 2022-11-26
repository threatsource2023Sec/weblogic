package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class MessageDrivenDescriptorMBeanImpl extends XMLElementMBeanDelegate implements MessageDrivenDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_jmsClientID = false;
   private String jmsClientID;
   private boolean isSet_providerURL = false;
   private String providerURL;
   private boolean isSet_jmsPollingIntervalSeconds = false;
   private int jmsPollingIntervalSeconds = 10;
   private boolean isSet_destinationJNDIName = false;
   private String destinationJNDIName;
   private boolean isSet_pool = false;
   private PoolMBean pool;
   private boolean isSet_connectionFactoryJNDIName = false;
   private String connectionFactoryJNDIName = "weblogic.jms.MessageDrivenBeanConnectionFactory";
   private boolean isSet_initialContextFactory = false;
   private String initialContextFactory = "weblogic.jndi.WLInitialContextFactory";

   public String getJMSClientID() {
      return this.jmsClientID;
   }

   public void setJMSClientID(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.jmsClientID;
      this.jmsClientID = value;
      this.isSet_jmsClientID = value != null;
      this.checkChange("jmsClientID", old, this.jmsClientID);
   }

   public String getProviderURL() {
      return this.providerURL;
   }

   public void setProviderURL(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.providerURL;
      this.providerURL = value;
      this.isSet_providerURL = value != null;
      this.checkChange("providerURL", old, this.providerURL);
   }

   public int getJMSPollingIntervalSeconds() {
      return this.jmsPollingIntervalSeconds;
   }

   public void setJMSPollingIntervalSeconds(int value) {
      int old = this.jmsPollingIntervalSeconds;
      this.jmsPollingIntervalSeconds = value;
      this.isSet_jmsPollingIntervalSeconds = value != -1;
      this.checkChange("jmsPollingIntervalSeconds", old, this.jmsPollingIntervalSeconds);
   }

   public String getDestinationJNDIName() {
      return this.destinationJNDIName;
   }

   public void setDestinationJNDIName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.destinationJNDIName;
      this.destinationJNDIName = value;
      this.isSet_destinationJNDIName = value != null;
      this.checkChange("destinationJNDIName", old, this.destinationJNDIName);
   }

   public PoolMBean getPool() {
      return this.pool;
   }

   public void setPool(PoolMBean value) {
      PoolMBean old = this.pool;
      this.pool = value;
      this.isSet_pool = value != null;
      this.checkChange("pool", old, this.pool);
   }

   public String getConnectionFactoryJNDIName() {
      return this.connectionFactoryJNDIName;
   }

   public void setConnectionFactoryJNDIName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.connectionFactoryJNDIName;
      this.connectionFactoryJNDIName = value;
      this.isSet_connectionFactoryJNDIName = value != null;
      this.checkChange("connectionFactoryJNDIName", old, this.connectionFactoryJNDIName);
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

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<message-driven-descriptor");
      result.append(">\n");
      if (null != this.getPool()) {
         result.append(this.getPool().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getDestinationJNDIName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<destination-jndi-name>").append(this.getDestinationJNDIName()).append("</destination-jndi-name>\n");
      }

      if ((this.isSet_initialContextFactory || !"weblogic.jndi.WLInitialContextFactory".equals(this.getInitialContextFactory())) && null != this.getInitialContextFactory()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<initial-context-factory>").append(this.getInitialContextFactory()).append("</initial-context-factory>\n");
      }

      if (null != this.getProviderURL()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<provider-url>").append(this.getProviderURL()).append("</provider-url>\n");
      }

      if ((this.isSet_connectionFactoryJNDIName || !"weblogic.jms.MessageDrivenBeanConnectionFactory".equals(this.getConnectionFactoryJNDIName())) && null != this.getConnectionFactoryJNDIName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<connection-factory-jndi-name>").append(this.getConnectionFactoryJNDIName()).append("</connection-factory-jndi-name>\n");
      }

      if (this.isSet_jmsPollingIntervalSeconds || 10 != this.getJMSPollingIntervalSeconds()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<jms-polling-interval-seconds>").append(this.getJMSPollingIntervalSeconds()).append("</jms-polling-interval-seconds>\n");
      }

      if (null != this.getJMSClientID()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<jms-client-id>").append(this.getJMSClientID()).append("</jms-client-id>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</message-driven-descriptor>\n");
      return result.toString();
   }
}
