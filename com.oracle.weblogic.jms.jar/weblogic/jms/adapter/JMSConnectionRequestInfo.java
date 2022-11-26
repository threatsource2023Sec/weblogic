package weblogic.jms.adapter;

import javax.resource.spi.ConnectionRequestInfo;

public class JMSConnectionRequestInfo implements ConnectionRequestInfo {
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
   private int type;
   private boolean preserveMsgProperty;

   public JMSConnectionRequestInfo(String user, String password, int type) {
      this.type = -1;
      this.preserveMsgProperty = false;
      this.user = user;
      this.password = password;
      this.type = type;
   }

   public JMSConnectionRequestInfo(String user, String password, int type, String url, String icFactory, String selector, String factoryJndi, String destJndi, String destType, String classPath) {
      this(user, password, type, url, icFactory, selector, factoryJndi, destJndi, destType, classPath, false);
   }

   public JMSConnectionRequestInfo(String user, String password, int type, String url, String icFactory, String selector, String factoryJndi, String destJndi, String destType, String classPath, boolean preserveMsgProperty) {
      this(user, password, type);
      this.url = url;
      this.icFactory = icFactory;
      this.selector = selector;
      this.factoryJndi = factoryJndi;
      this.destJndi = destJndi;
      this.destType = destType;
      this.classPath = classPath;
      this.preserveMsgProperty = preserveMsgProperty;
   }

   public JMSConnectionRequestInfo(String user, String password, int type, String url, String icFactory, String selector, String factoryJndi, String destJndi, String destType, String name, String fullName, boolean durable, String classPath) {
      this(user, password, type, url, icFactory, selector, factoryJndi, destJndi, destType, classPath);
      this.name = name;
      this.fullName = fullName;
      this.durable = durable;
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

   public int getType() {
      return this.type;
   }

   public String getUrl() {
      return this.url;
   }

   public String getInitialContextFactory() {
      return this.icFactory;
   }

   public String getSelector() {
      return this.selector;
   }

   public String getFactoryJndi() {
      return this.factoryJndi;
   }

   public String getDestJndi() {
      return this.destJndi;
   }

   public String getDestType() {
      return this.destType;
   }

   public boolean getDurability() {
      return this.durable;
   }

   public String getClasspath() {
      return this.classPath;
   }

   public boolean getPreserveMsgProperty() {
      return this.preserveMsgProperty;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof JMSConnectionRequestInfo)) {
         return false;
      } else {
         JMSConnectionRequestInfo other = (JMSConnectionRequestInfo)obj;
         return this.isEqual(this.user, other.getUser()) && this.isEqual(this.password, other.getPassword()) && this.isEqual(this.url, other.getUrl()) && this.isEqual(this.icFactory, other.getInitialContextFactory()) && this.isEqual(this.selector, other.getSelector()) && this.isEqual(this.factoryJndi, other.getFactoryJndi()) && this.isEqual(this.destJndi, other.getDestJndi()) && this.isEqual(this.destType, other.getDestType()) && this.isEqual(this.name, other.name) && this.isEqual(this.fullName, other.fullName) && this.isEqual(this.classPath, other.classPath) && this.preserveMsgProperty == other.preserveMsgProperty && (this.durable && other.durable || !this.durable && !other.durable) && this.type == other.type;
      }
   }

   public int hashCode() {
      String result = "";
      if (this.name != null) {
         result = result + this.name;
      }

      if (this.user != null) {
         result = result + this.user;
      }

      if (this.password != null) {
         result = result + this.password;
      }

      result = result + this.type;
      if (this.url != null) {
         result = result + this.url;
      }

      if (this.icFactory != null) {
         result = result + this.icFactory;
      }

      if (this.selector != null) {
         result = result + this.selector;
      }

      if (this.factoryJndi != null) {
         result = result + this.factoryJndi;
      }

      if (this.destJndi != null) {
         result = result + this.destJndi;
      }

      if (this.destType != null) {
         result = result + this.destType;
      }

      if (this.durable) {
         result = result + "true";
      } else {
         result = result + "false";
      }

      if (this.classPath != null) {
         result = result + this.classPath;
      }

      return result.hashCode();
   }

   private boolean isEqual(Object o1, Object o2) {
      if (o1 == null) {
         return o2 == null;
      } else {
         return o1.equals(o2);
      }
   }
}
