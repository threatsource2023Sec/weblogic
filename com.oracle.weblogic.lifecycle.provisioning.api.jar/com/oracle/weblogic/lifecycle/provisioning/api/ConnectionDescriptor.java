package com.oracle.weblogic.lifecycle.provisioning.api;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.sql.DataSource;
import org.jvnet.hk2.annotations.Contract;

@Contract
public abstract class ConnectionDescriptor extends Properties implements DataSource, Serializable {
   public static final String DATABASE_NAME = "databaseName";
   public static final String DATASOURCE_NAME = "dataSourceName";
   public static final String DESCRIPTION = "description";
   public static final String NETWORK_PROTOCOL = "networkProtocol";
   public static final String PASSWORD = "password";
   public static final String PORT_NUMBER = "portNumber";
   public static final String ROLE_NAME = "roleName";
   public static final String SERVER_NAME = "serverName";
   public static final String USER = "user";
   public static final String DRIVER_CLASS_NAME = "driverClassName";
   public static final String URL = "URL";
   private static final long serialVersionUID = 1L;
   private static final Set SENSITIVE_PROPERTY_KEYS = new HashSet();

   protected ConnectionDescriptor() {
   }

   protected ConnectionDescriptor(Properties defaults) {
      super(defaults);
   }

   public final String getDatabaseName() {
      return this.getProperty("databaseName");
   }

   public final void setDatabaseName(String databaseName) {
      if (databaseName == null) {
         this.remove("databaseName");
      } else {
         this.setProperty("databaseName", databaseName);
      }

   }

   public final String getDataSourceName() {
      return this.getProperty("dataSourceName");
   }

   public final void setDataSourceName(String dataSourceName) {
      if (dataSourceName == null) {
         this.remove("dataSourceName");
      } else {
         this.setProperty("dataSourceName", dataSourceName);
      }

   }

   public final String getDescription() {
      return this.getProperty("description");
   }

   public final void setDescription(String description) {
      if (description == null) {
         this.remove("description");
      } else {
         this.setProperty("description", description);
      }

   }

   public final String getNetworkProtocol() {
      return this.getProperty("networkProtocol");
   }

   public final void setNetworkProtocol(String networkProtocol) {
      if (networkProtocol == null) {
         this.remove("networkProtocol");
      } else {
         this.setProperty("networkProtocol", networkProtocol);
      }

   }

   public final char[] getPassword() {
      String password = this.getProperty("password");
      char[] returnValue;
      if (password == null) {
         returnValue = null;
      } else {
         returnValue = password.toCharArray();
      }

      return returnValue;
   }

   public final void setPassword(char[] password) {
      if (password == null) {
         this.remove("password");
      } else {
         this.setProperty("password", new String(password));
      }

   }

   public final int getPortNumber() {
      int returnValue = -1;
      String portString = this.getProperty("portNumber");
      if (portString != null) {
         try {
            returnValue = Integer.parseInt(portString);
         } catch (NumberFormatException var4) {
            returnValue = -1;
         }
      }

      return returnValue;
   }

   public final void setPortNumber(int portNumber) {
      this.setProperty("portNumber", String.valueOf(portNumber));
   }

   public final String getRoleName() {
      return this.getProperty("roleName");
   }

   public final void setRoleName(String roleName) {
      if (roleName == null) {
         this.remove("roleName");
      } else {
         this.setProperty("roleName", roleName);
      }

   }

   public final String getServerName() {
      return this.getProperty("serverName");
   }

   public final void setServerName(String serverName) {
      if (serverName == null) {
         this.remove("serverName");
      } else {
         this.setProperty("serverName", serverName);
      }

   }

   public final String getUser() {
      return this.getProperty("user");
   }

   public final void setUser(String user) {
      if (user == null) {
         this.remove("user");
      } else {
         this.setProperty("user", user);
      }

   }

   public final String getDriverClassName() {
      return this.getProperty("driverClassName");
   }

   public final void setDriverClassName(String driverClassName) {
      if (driverClassName == null) {
         this.remove("driverClassName");
      } else {
         this.setProperty("driverClassName", driverClassName);
      }

   }

   public final String getURL() {
      return this.getProperty("URL");
   }

   public final void setURL(String url) {
      if (url == null) {
         this.remove("URL");
      } else {
         this.setProperty("URL", url);
      }

   }

   public String toString() {
      StringBuilder sb = new StringBuilder(this.getClass().getName());
      sb.append("(");
      Enumeration keys = this.propertyNames();
      if (keys != null) {
         while(true) {
            if (!keys.hasMoreElements()) {
               if (sb.toString().endsWith(", ")) {
                  sb.setLength(sb.length() - 2);
               }
               break;
            }

            Object key = keys.nextElement();
            if (key != null) {
               String propertyName = key.toString();
               sb.append(propertyName).append("=");
               String value = this.getProperty(propertyName);
               if (value != null && mask(propertyName)) {
                  sb.append("******");
               } else {
                  sb.append(String.valueOf(value));
               }

               sb.append(", ");
            }
         }
      }

      sb.append(")");
      return sb.toString();
   }

   private static final boolean mask(String key) {
      return key != null && SENSITIVE_PROPERTY_KEYS.contains(key);
   }

   static {
      SENSITIVE_PROPERTY_KEYS.add("password");
   }
}
