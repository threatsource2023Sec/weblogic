package weblogic.jms.adapter;

import java.util.Enumeration;
import java.util.Properties;
import weblogic.jms.bridge.ConnectionSpec;

public class JMSConnectionSpec implements ConnectionSpec {
   private String name;
   private String fullName;
   private String user;
   private String password;
   private String url;
   private String icFactory;
   private String selector;
   private String factoryJndi;
   private String destJndi;
   private String destType;
   private boolean durable;
   private String classPath;
   private boolean preserveMsgProperty = false;

   public JMSConnectionSpec(String user, String password) {
      this.user = user;
      this.password = password;
   }

   public JMSConnectionSpec(String user, String password, String url, String icFactory, String selector, String cfJndi, String destJndi, String destType, String name, String fullName, boolean durable, String classPath, boolean preserveMsgProperty) {
      this.user = user;
      this.password = password;
      this.url = url;
      this.icFactory = icFactory;
      this.selector = selector;
      this.factoryJndi = cfJndi;
      this.destJndi = destJndi;
      this.destType = destType;
      this.name = name;
      this.fullName = fullName;
      this.durable = durable;
      this.classPath = classPath;
      this.preserveMsgProperty = preserveMsgProperty;
   }

   public JMSConnectionSpec(Properties props) {
      Enumeration enum_ = props.propertyNames();

      while(enum_.hasMoreElements()) {
         String key = (String)enum_.nextElement();
         String value = props.getProperty(key);
         if (key.equalsIgnoreCase("ConnectionFactoryJNDI")) {
            this.factoryJndi = value;
         } else if (key.equalsIgnoreCase("ConnectionFactoryJNDIName")) {
            this.factoryJndi = value;
         } else if (key.equalsIgnoreCase("ConnectionURL")) {
            this.url = value;
         } else if (key.equalsIgnoreCase("InitialContextFactory")) {
            this.icFactory = value;
         } else if (key.equalsIgnoreCase("DestinationJNDI")) {
            this.destJndi = value;
         } else if (key.equalsIgnoreCase("DestinationJNDIName")) {
            this.destJndi = value;
         } else if (key.equalsIgnoreCase("DestinationType")) {
            this.destType = value;
         } else if (key.equalsIgnoreCase("name")) {
            this.name = value;
         } else if (key.equalsIgnoreCase("fullName")) {
            this.fullName = value;
         } else if (key.equalsIgnoreCase("username")) {
            this.user = value;
         } else if (key.equalsIgnoreCase("selector")) {
            this.selector = value;
         } else if (key.equalsIgnoreCase("password")) {
            this.password = value;
         } else if (key.equalsIgnoreCase("classpath")) {
            this.classPath = value;
         } else if (key.equalsIgnoreCase("preserveMsgProperty")) {
            if (value.equals("true")) {
               this.preserveMsgProperty = true;
            } else {
               this.preserveMsgProperty = false;
            }
         } else if (key.equalsIgnoreCase("durability")) {
            if (value.equals("true")) {
               this.durable = true;
            } else {
               this.durable = false;
            }
         }
      }

   }

   public String getSelector() {
      return this.selector;
   }

   public String getUrl() {
      return this.url;
   }

   public String getInitialContextFactory() {
      return this.icFactory;
   }

   public String getName() {
      return this.name;
   }

   public String getFullName() {
      return this.fullName;
   }

   public String getUser() {
      return this.user;
   }

   public String getPassword() {
      return this.password;
   }

   public String getFactoryJndi() {
      return this.factoryJndi;
   }

   public boolean getDurability() {
      return this.durable;
   }

   public boolean getPreserveMsgProperty() {
      return this.preserveMsgProperty;
   }

   public String getDestJndi() {
      return this.destJndi;
   }

   public String getDestType() {
      return this.destType;
   }

   public String getClasspath() {
      return this.classPath;
   }
}
