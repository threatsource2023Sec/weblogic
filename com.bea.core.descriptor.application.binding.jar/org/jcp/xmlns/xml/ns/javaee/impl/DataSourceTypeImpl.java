package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DataSourceType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.IsolationLevelType;
import org.jcp.xmlns.xml.ns.javaee.JdbcUrlType;
import org.jcp.xmlns.xml.ns.javaee.JndiNameType;
import org.jcp.xmlns.xml.ns.javaee.PropertyType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.XsdBooleanType;
import org.jcp.xmlns.xml.ns.javaee.XsdIntegerType;

public class DataSourceTypeImpl extends XmlComplexContentImpl implements DataSourceType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName NAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
   private static final QName CLASSNAME$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "class-name");
   private static final QName SERVERNAME$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "server-name");
   private static final QName PORTNUMBER$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "port-number");
   private static final QName DATABASENAME$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "database-name");
   private static final QName URL$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "url");
   private static final QName USER$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "user");
   private static final QName PASSWORD$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "password");
   private static final QName PROPERTY$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "property");
   private static final QName LOGINTIMEOUT$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "login-timeout");
   private static final QName TRANSACTIONAL$22 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "transactional");
   private static final QName ISOLATIONLEVEL$24 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "isolation-level");
   private static final QName INITIALPOOLSIZE$26 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "initial-pool-size");
   private static final QName MAXPOOLSIZE$28 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "max-pool-size");
   private static final QName MINPOOLSIZE$30 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "min-pool-size");
   private static final QName MAXIDLETIME$32 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "max-idle-time");
   private static final QName MAXSTATEMENTS$34 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "max-statements");
   private static final QName ID$36 = new QName("", "id");

   public DataSourceTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, 0, (short)1);
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public JndiNameType getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(NAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setName(JndiNameType name) {
      this.generatedSetterHelperImpl(name, NAME$2, 0, (short)1);
   }

   public JndiNameType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(NAME$2);
         return target;
      }
   }

   public FullyQualifiedClassType getClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CLASSNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSNAME$4) != 0;
      }
   }

   public void setClassName(FullyQualifiedClassType className) {
      this.generatedSetterHelperImpl(className, CLASSNAME$4, 0, (short)1);
   }

   public FullyQualifiedClassType addNewClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CLASSNAME$4);
         return target;
      }
   }

   public void unsetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSNAME$4, 0);
      }
   }

   public String getServerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(SERVERNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVERNAME$6) != 0;
      }
   }

   public void setServerName(String serverName) {
      this.generatedSetterHelperImpl(serverName, SERVERNAME$6, 0, (short)1);
   }

   public String addNewServerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(SERVERNAME$6);
         return target;
      }
   }

   public void unsetServerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVERNAME$6, 0);
      }
   }

   public XsdIntegerType getPortNumber() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(PORTNUMBER$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPortNumber() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTNUMBER$8) != 0;
      }
   }

   public void setPortNumber(XsdIntegerType portNumber) {
      this.generatedSetterHelperImpl(portNumber, PORTNUMBER$8, 0, (short)1);
   }

   public XsdIntegerType addNewPortNumber() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(PORTNUMBER$8);
         return target;
      }
   }

   public void unsetPortNumber() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTNUMBER$8, 0);
      }
   }

   public String getDatabaseName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DATABASENAME$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDatabaseName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATABASENAME$10) != 0;
      }
   }

   public void setDatabaseName(String databaseName) {
      this.generatedSetterHelperImpl(databaseName, DATABASENAME$10, 0, (short)1);
   }

   public String addNewDatabaseName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DATABASENAME$10);
         return target;
      }
   }

   public void unsetDatabaseName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATABASENAME$10, 0);
      }
   }

   public JdbcUrlType getUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcUrlType target = null;
         target = (JdbcUrlType)this.get_store().find_element_user(URL$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(URL$12) != 0;
      }
   }

   public void setUrl(JdbcUrlType url) {
      this.generatedSetterHelperImpl(url, URL$12, 0, (short)1);
   }

   public JdbcUrlType addNewUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcUrlType target = null;
         target = (JdbcUrlType)this.get_store().add_element_user(URL$12);
         return target;
      }
   }

   public void unsetUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URL$12, 0);
      }
   }

   public String getUser() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(USER$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUser() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USER$14) != 0;
      }
   }

   public void setUser(String user) {
      this.generatedSetterHelperImpl(user, USER$14, 0, (short)1);
   }

   public String addNewUser() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(USER$14);
         return target;
      }
   }

   public void unsetUser() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USER$14, 0);
      }
   }

   public String getPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PASSWORD$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PASSWORD$16) != 0;
      }
   }

   public void setPassword(String password) {
      this.generatedSetterHelperImpl(password, PASSWORD$16, 0, (short)1);
   }

   public String addNewPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PASSWORD$16);
         return target;
      }
   }

   public void unsetPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PASSWORD$16, 0);
      }
   }

   public PropertyType[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$18, targetList);
         PropertyType[] result = new PropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PROPERTY$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTY$18);
      }
   }

   public void setPropertyArray(PropertyType[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$18);
   }

   public void setPropertyArray(int i, PropertyType property) {
      this.generatedSetterHelperImpl(property, PROPERTY$18, i, (short)2);
   }

   public PropertyType insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().insert_element_user(PROPERTY$18, i);
         return target;
      }
   }

   public PropertyType addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().add_element_user(PROPERTY$18);
         return target;
      }
   }

   public void removeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$18, i);
      }
   }

   public XsdIntegerType getLoginTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(LOGINTIMEOUT$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLoginTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGINTIMEOUT$20) != 0;
      }
   }

   public void setLoginTimeout(XsdIntegerType loginTimeout) {
      this.generatedSetterHelperImpl(loginTimeout, LOGINTIMEOUT$20, 0, (short)1);
   }

   public XsdIntegerType addNewLoginTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(LOGINTIMEOUT$20);
         return target;
      }
   }

   public void unsetLoginTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGINTIMEOUT$20, 0);
      }
   }

   public XsdBooleanType getTransactional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().find_element_user(TRANSACTIONAL$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONAL$22) != 0;
      }
   }

   public void setTransactional(XsdBooleanType transactional) {
      this.generatedSetterHelperImpl(transactional, TRANSACTIONAL$22, 0, (short)1);
   }

   public XsdBooleanType addNewTransactional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().add_element_user(TRANSACTIONAL$22);
         return target;
      }
   }

   public void unsetTransactional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONAL$22, 0);
      }
   }

   public IsolationLevelType.Enum getIsolationLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ISOLATIONLEVEL$24, 0);
         return target == null ? null : (IsolationLevelType.Enum)target.getEnumValue();
      }
   }

   public IsolationLevelType xgetIsolationLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IsolationLevelType target = null;
         target = (IsolationLevelType)this.get_store().find_element_user(ISOLATIONLEVEL$24, 0);
         return target;
      }
   }

   public boolean isSetIsolationLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ISOLATIONLEVEL$24) != 0;
      }
   }

   public void setIsolationLevel(IsolationLevelType.Enum isolationLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ISOLATIONLEVEL$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ISOLATIONLEVEL$24);
         }

         target.setEnumValue(isolationLevel);
      }
   }

   public void xsetIsolationLevel(IsolationLevelType isolationLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IsolationLevelType target = null;
         target = (IsolationLevelType)this.get_store().find_element_user(ISOLATIONLEVEL$24, 0);
         if (target == null) {
            target = (IsolationLevelType)this.get_store().add_element_user(ISOLATIONLEVEL$24);
         }

         target.set(isolationLevel);
      }
   }

   public void unsetIsolationLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ISOLATIONLEVEL$24, 0);
      }
   }

   public XsdIntegerType getInitialPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(INITIALPOOLSIZE$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInitialPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALPOOLSIZE$26) != 0;
      }
   }

   public void setInitialPoolSize(XsdIntegerType initialPoolSize) {
      this.generatedSetterHelperImpl(initialPoolSize, INITIALPOOLSIZE$26, 0, (short)1);
   }

   public XsdIntegerType addNewInitialPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(INITIALPOOLSIZE$26);
         return target;
      }
   }

   public void unsetInitialPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALPOOLSIZE$26, 0);
      }
   }

   public XsdIntegerType getMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXPOOLSIZE$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXPOOLSIZE$28) != 0;
      }
   }

   public void setMaxPoolSize(XsdIntegerType maxPoolSize) {
      this.generatedSetterHelperImpl(maxPoolSize, MAXPOOLSIZE$28, 0, (short)1);
   }

   public XsdIntegerType addNewMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXPOOLSIZE$28);
         return target;
      }
   }

   public void unsetMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXPOOLSIZE$28, 0);
      }
   }

   public XsdIntegerType getMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MINPOOLSIZE$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINPOOLSIZE$30) != 0;
      }
   }

   public void setMinPoolSize(XsdIntegerType minPoolSize) {
      this.generatedSetterHelperImpl(minPoolSize, MINPOOLSIZE$30, 0, (short)1);
   }

   public XsdIntegerType addNewMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MINPOOLSIZE$30);
         return target;
      }
   }

   public void unsetMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINPOOLSIZE$30, 0);
      }
   }

   public XsdIntegerType getMaxIdleTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXIDLETIME$32, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxIdleTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXIDLETIME$32) != 0;
      }
   }

   public void setMaxIdleTime(XsdIntegerType maxIdleTime) {
      this.generatedSetterHelperImpl(maxIdleTime, MAXIDLETIME$32, 0, (short)1);
   }

   public XsdIntegerType addNewMaxIdleTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXIDLETIME$32);
         return target;
      }
   }

   public void unsetMaxIdleTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXIDLETIME$32, 0);
      }
   }

   public XsdIntegerType getMaxStatements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXSTATEMENTS$34, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxStatements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXSTATEMENTS$34) != 0;
      }
   }

   public void setMaxStatements(XsdIntegerType maxStatements) {
      this.generatedSetterHelperImpl(maxStatements, MAXSTATEMENTS$34, 0, (short)1);
   }

   public XsdIntegerType addNewMaxStatements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXSTATEMENTS$34);
         return target;
      }
   }

   public void unsetMaxStatements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXSTATEMENTS$34, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$36);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$36);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$36) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$36);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$36);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$36);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$36);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$36);
      }
   }
}
