package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ConnectionPropertiesMBeanImpl extends XMLElementMBeanDelegate implements ConnectionPropertiesMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_userName = false;
   private String userName;
   private boolean isSet_connectionParams = false;
   private ConnectionParamsMBean connectionParams;
   private boolean isSet_driverClassName = false;
   private String driverClassName;
   private boolean isSet_url = false;
   private String url;
   private boolean isSet_password = false;
   private String password;

   public String getUserName() {
      return this.userName;
   }

   public void setUserName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.userName;
      this.userName = value;
      this.isSet_userName = value != null;
      this.checkChange("userName", old, this.userName);
   }

   public ConnectionParamsMBean getConnectionParams() {
      return this.connectionParams;
   }

   public void setConnectionParams(ConnectionParamsMBean value) {
      ConnectionParamsMBean old = this.connectionParams;
      this.connectionParams = value;
      this.isSet_connectionParams = value != null;
      this.checkChange("connectionParams", old, this.connectionParams);
   }

   public String getDriverClassName() {
      return this.driverClassName;
   }

   public void setDriverClassName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.driverClassName;
      this.driverClassName = value;
      this.isSet_driverClassName = value != null;
      this.checkChange("driverClassName", old, this.driverClassName);
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.url;
      this.url = value;
      this.isSet_url = value != null;
      this.checkChange("url", old, this.url);
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.password;
      this.password = value;
      this.isSet_password = value != null;
      this.checkChange("password", old, this.password);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<connection-properties");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</connection-properties>\n");
      return result.toString();
   }
}
