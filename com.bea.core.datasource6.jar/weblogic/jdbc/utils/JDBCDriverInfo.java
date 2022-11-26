package weblogic.jdbc.utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.jdbc.common.internal.AddressList;

public class JDBCDriverInfo implements Serializable, Comparable {
   private static final long serialVersionUID = 6919106869180583924L;
   private static boolean debug = false;
   private boolean triedToLoadDriver = false;
   private boolean driverLoaded = false;
   private Exception loadDriverException;
   private MetaJDBCDriverInfo metaInfo = null;
   private Map myJDBCDriverAtributes = null;
   private Map unknownDriverAttributes = null;
   private transient AddressList hostPorts = new AddressList();
   private boolean fillRequired = false;
   public static final String DB_HOST = "DbmsHost";
   public static final String DB_PORT = "DbmsPort";
   public static final String DB_SERVERNAME = "DbmsName";
   public static final String DB_USER = "DbmsUsername";
   public static final String DB_PASS = "DbmsPassword";
   public static final String[] WELL_KNOWN_KEYS = new String[]{"DbmsHost", "DbmsPort", "DbmsName", "DbmsUsername", "DbmsPassword"};

   JDBCDriverInfo(MetaJDBCDriverInfo metaInfo) {
      this.metaInfo = metaInfo;
   }

   public String getDriverPK() {
      return this.metaInfo.toString();
   }

   public List getDbmsVersionList() {
      return this.metaInfo.getDbmsVersionList();
   }

   public String getDbmsVersion() {
      return this.metaInfo.getDbmsVersion();
   }

   public String getDbmsVendor() {
      return this.metaInfo.getDbmsVendor();
   }

   public String getDriverVendor() {
      return this.metaInfo.getDriverVendor();
   }

   public String getDriverClassName() {
      return this.metaInfo.getDriverClassName();
   }

   public String getURLHelperClassName() {
      return this.metaInfo.getURLHelperClassName();
   }

   public String getType() {
      return this.metaInfo.getType();
   }

   public String getTestSQL() {
      return this.metaInfo.getTestSQL();
   }

   public String getInstallURL() {
      return this.metaInfo.getInstallURL();
   }

   public String getDescription() {
      return this.metaInfo.getDescription();
   }

   public boolean isForXA() {
      return this.metaInfo.isForXA();
   }

   public boolean isCert() {
      return this.metaInfo.isCert();
   }

   public boolean isFillRequired() {
      return this.fillRequired;
   }

   public void setFillRequired(boolean fillRequired) {
      this.fillRequired = fillRequired;
   }

   public void setDbmsName(String dbmsName) {
      this.setWellKnownAttribute("DbmsName", dbmsName);
   }

   public String getDbmsName() {
      return this.getWellKnownAttribute("DbmsName");
   }

   public String getDbmsNameDefault() {
      return this.getDefaultFor("DbmsName");
   }

   public void setDbmsHost(String host) {
      this.setWellKnownAttribute("DbmsHost", host);
   }

   public String getDbmsHost() {
      return this.getWellKnownAttribute("DbmsHost");
   }

   public String getDbmsHostDefault() {
      return this.getDefaultFor("DbmsHost");
   }

   public void setDbmsPort(String port) {
      this.setWellKnownAttribute("DbmsPort", port);
   }

   public String getDbmsPort() {
      return this.getWellKnownAttribute("DbmsPort");
   }

   public String getDbmsPortDefault() {
      return this.getDefaultFor("DbmsPort");
   }

   public void setPassword(String pass) {
      this.setWellKnownAttribute("DbmsPassword", pass);
   }

   public String getPassword() {
      return this.getWellKnownAttribute("DbmsPassword");
   }

   public void setUserName(String user) {
      this.setWellKnownAttribute("DbmsUsername", user);
   }

   public String getUserName() {
      return this.getWellKnownAttribute("DbmsUsername");
   }

   public void setUknownAttribute(String attributeName, String value) {
      this.setUnknownAttribute(attributeName, value);
   }

   public boolean isServerNameRequired() {
      return this.isAttributeRequired("DbmsName");
   }

   public boolean isPortRequired() {
      return this.isAttributeRequired("DbmsPort");
   }

   public boolean isHostNameRequired() {
      return this.isAttributeRequired("DbmsHost");
   }

   public boolean isUserNameRequired() {
      return this.isAttributeRequired("DbmsUsername");
   }

   public boolean isPassWordRequired() {
      return this.isAttributeRequired("DbmsPassword");
   }

   public void addHostPort(String host, int port) {
      this.hostPorts.add(host, port);
   }

   public void setHostPortAddressList(AddressList hostPortList) {
      this.hostPorts = hostPortList;
   }

   public boolean removeHostPort(String host, int port) {
      return this.hostPorts.remove(host, port);
   }

   public AddressList getHostPorts() {
      return this.hostPorts;
   }

   public Map getUnknownDriverAttributes() {
      if (this.unknownDriverAttributes == null) {
         this.unknownDriverAttributes = new LinkedHashMap(this.getUnknownDriverAttributesKeys().size());
         Set keys = this.getUnknownDriverAttributesKeys();
         Iterator i = keys.iterator();
         Map driverAttributes = this.getDriverAttributes();

         while(i.hasNext()) {
            Object key = i.next();
            this.unknownDriverAttributes.put(key, driverAttributes.get(key));
         }
      }

      return this.unknownDriverAttributes;
   }

   public Map getDriverAttributes() {
      if (this.myJDBCDriverAtributes == null) {
         this.myJDBCDriverAtributes = this.metaInfo.getDriverAttributes();
      }

      return this.myJDBCDriverAtributes;
   }

   public Set getUnknownDriverAttributesKeys() {
      return this.metaInfo.getUnknownDriverAttributesKeys();
   }

   public boolean isDriverInClasspath() {
      if (!this.triedToLoadDriver) {
         try {
            Class.forName(this.getDriverClassName()).newInstance();
            this.driverLoaded = true;
         } catch (Exception var2) {
            this.loadDriverException = var2;
         }
      }

      return this.driverLoaded;
   }

   public Exception exceptionEncounteredLoadingDriver() {
      return this.loadDriverException;
   }

   public String displayString() {
      StringBuffer buff = new StringBuffer();
      if (this.isCert()) {
         buff.append("*");
      }

      buff.append(this.getDriverVendor());
      buff.append("'s ");
      if (!this.getDriverVendor().equals(this.getDbmsVendor())) {
         buff.append(this.getDbmsVendor() + " ");
      }

      buff.append("Type ");
      buff.append(this.getType());
      buff.append(" Driver ");
      if (this.isForXA()) {
         buff.append("for Distributed Transactions (XA) ");
      }

      buff.append(" - Versions : " + this.getDbmsVersion());
      return buff.toString();
   }

   public String toString() {
      return this.metaInfo.toString();
   }

   public String toVerboseString() {
      StringBuffer buff = new StringBuffer();
      buff.append("DBMS Vendor      :  " + (this.getDbmsVendor() == null ? "null" : this.getDbmsVendor()) + "\n");
      buff.append("Driver Vendor    :  " + (this.getDriverVendor() == null ? "null" : this.getDriverVendor()) + "\n");
      buff.append("Driver Type      :  " + (this.getType() == null ? "null" : this.getType()) + "\n");
      buff.append("Driver Class     :  " + (this.getDriverClassName() == null ? "null" : this.getDriverClassName()) + "\n");
      buff.append("XA ?             :  " + Boolean.valueOf(this.isForXA()).toString() + "\n");
      buff.append("URLHelperClass   :  " + (this.getURLHelperClassName() == null ? "null" : this.getURLHelperClassName()) + "\n");
      buff.append("DBMS Version     :  " + (this.getDbmsVersion() == null ? "null" : this.getDbmsVersion()) + "\n");
      buff.append("DBMS Name        :  " + (this.getDbmsName() == null ? "null" : this.getDbmsName()) + "\n");
      buff.append("DBMS Host        :  " + (this.getDbmsHost() == null ? "null" : this.getDbmsHost()) + "\n");
      buff.append("isHostRequired   :  " + Boolean.valueOf(this.isHostNameRequired()).toString() + "\n");
      buff.append("DBMS Port        :  " + (this.getDbmsPort() == null ? "null" : this.getDbmsPort()) + "\n");
      buff.append("isPortRequired   :  " + Boolean.valueOf(this.isPortRequired()).toString() + "\n");
      buff.append("DBMS password    :  " + (this.getPassword() == null ? "null" : this.getPassword()) + "\n");
      buff.append("DBMS user        :  " + (this.getUserName() == null ? "null" : this.getUserName()) + "\n");
      buff.append("DBMS test sql    :  " + (this.getTestSQL() == null ? "null" : this.getTestSQL()) + "\n");
      buff.append("Description      :  " + (this.getDescription() == null ? "null" : this.getDescription()) + "\n");
      return buff.toString();
   }

   private void setWellKnownAttribute(String attributeName, String attributeValue) {
      JDBCDriverAttribute prop = null;
      if (this.getDriverAttributes().containsKey(attributeName)) {
         prop = (JDBCDriverAttribute)this.getDriverAttributes().get(attributeName);
      } else {
         prop = new JDBCDriverAttribute(this.metaInfo);
         prop.setName(attributeValue);
      }

      prop.setValue(attributeValue);
      this.getDriverAttributes().put(attributeName, prop);
   }

   private void setUnknownAttribute(String attributeName, String attributeValue) {
      if (!this.getDriverAttributes().containsKey(attributeName)) {
         throw new AssertionError("Trying to set a value '" + attributeValue + "' on an unknown attribute '" + attributeName + "'");
      } else {
         JDBCDriverAttribute prop = (JDBCDriverAttribute)this.getDriverAttributes().get(attributeName);
         prop.setValue(attributeValue);
         this.getDriverAttributes().put(attributeName, prop);
      }
   }

   private String getWellKnownAttribute(String attributeName) {
      return this.getWellKnownAttribute(attributeName, false);
   }

   private String getWellKnownAttribute(String attributeName, boolean returnDefault) {
      if (this.getDriverAttributes().containsKey(attributeName)) {
         JDBCDriverAttribute prop = (JDBCDriverAttribute)this.getDriverAttributes().get(attributeName);
         if (prop.getValue() != null) {
            if (this.fillRequired && prop.getValue().equals("")) {
               return attributeName;
            }

            return prop.getValue();
         }

         if (returnDefault && prop.getDefaultValue() != null) {
            return prop.getDefaultValue();
         }

         if (this.fillRequired) {
            return attributeName;
         }
      }

      return null;
   }

   private String getDefaultFor(String attributeName) {
      return this.getAttribute(attributeName) != null ? this.getAttribute(attributeName).getDefaultValue() : null;
   }

   private JDBCDriverAttribute getAttribute(String attributeName) {
      return this.getDriverAttributes().containsKey(attributeName) ? (JDBCDriverAttribute)this.getDriverAttributes().get(attributeName) : null;
   }

   private boolean isAttributeRequired(String attributeName) {
      if (this.getDriverAttributes().containsKey(attributeName)) {
         JDBCDriverAttribute prop = (JDBCDriverAttribute)this.getDriverAttributes().get(attributeName);
         return prop.isRequired();
      } else {
         return false;
      }
   }

   public int compareTo(Object o) throws ClassCastException {
      JDBCDriverInfo info1 = (JDBCDriverInfo)o;
      return this.getDriverPK().compareTo(info1.getDriverPK());
   }
}
