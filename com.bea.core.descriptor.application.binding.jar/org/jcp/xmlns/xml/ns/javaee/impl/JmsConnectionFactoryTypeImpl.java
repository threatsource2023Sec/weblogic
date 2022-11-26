package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.JmsConnectionFactoryType;
import org.jcp.xmlns.xml.ns.javaee.JndiNameType;
import org.jcp.xmlns.xml.ns.javaee.PropertyType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.XsdBooleanType;
import org.jcp.xmlns.xml.ns.javaee.XsdIntegerType;

public class JmsConnectionFactoryTypeImpl extends XmlComplexContentImpl implements JmsConnectionFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName NAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
   private static final QName INTERFACENAME$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "interface-name");
   private static final QName CLASSNAME$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "class-name");
   private static final QName RESOURCEADAPTER$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-adapter");
   private static final QName USER$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "user");
   private static final QName PASSWORD$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "password");
   private static final QName CLIENTID$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "client-id");
   private static final QName PROPERTY$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "property");
   private static final QName TRANSACTIONAL$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "transactional");
   private static final QName MAXPOOLSIZE$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "max-pool-size");
   private static final QName MINPOOLSIZE$22 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "min-pool-size");
   private static final QName ID$24 = new QName("", "id");

   public JmsConnectionFactoryTypeImpl(SchemaType sType) {
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

   public FullyQualifiedClassType getInterfaceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(INTERFACENAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInterfaceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTERFACENAME$4) != 0;
      }
   }

   public void setInterfaceName(FullyQualifiedClassType interfaceName) {
      this.generatedSetterHelperImpl(interfaceName, INTERFACENAME$4, 0, (short)1);
   }

   public FullyQualifiedClassType addNewInterfaceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(INTERFACENAME$4);
         return target;
      }
   }

   public void unsetInterfaceName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTERFACENAME$4, 0);
      }
   }

   public FullyQualifiedClassType getClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CLASSNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSNAME$6) != 0;
      }
   }

   public void setClassName(FullyQualifiedClassType className) {
      this.generatedSetterHelperImpl(className, CLASSNAME$6, 0, (short)1);
   }

   public FullyQualifiedClassType addNewClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CLASSNAME$6);
         return target;
      }
   }

   public void unsetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSNAME$6, 0);
      }
   }

   public String getResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RESOURCEADAPTER$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEADAPTER$8) != 0;
      }
   }

   public void setResourceAdapter(String resourceAdapter) {
      this.generatedSetterHelperImpl(resourceAdapter, RESOURCEADAPTER$8, 0, (short)1);
   }

   public String addNewResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(RESOURCEADAPTER$8);
         return target;
      }
   }

   public void unsetResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEADAPTER$8, 0);
      }
   }

   public String getUser() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(USER$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUser() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USER$10) != 0;
      }
   }

   public void setUser(String user) {
      this.generatedSetterHelperImpl(user, USER$10, 0, (short)1);
   }

   public String addNewUser() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(USER$10);
         return target;
      }
   }

   public void unsetUser() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USER$10, 0);
      }
   }

   public String getPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PASSWORD$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PASSWORD$12) != 0;
      }
   }

   public void setPassword(String password) {
      this.generatedSetterHelperImpl(password, PASSWORD$12, 0, (short)1);
   }

   public String addNewPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PASSWORD$12);
         return target;
      }
   }

   public void unsetPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PASSWORD$12, 0);
      }
   }

   public String getClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(CLIENTID$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTID$14) != 0;
      }
   }

   public void setClientId(String clientId) {
      this.generatedSetterHelperImpl(clientId, CLIENTID$14, 0, (short)1);
   }

   public String addNewClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(CLIENTID$14);
         return target;
      }
   }

   public void unsetClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTID$14, 0);
      }
   }

   public PropertyType[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$16, targetList);
         PropertyType[] result = new PropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PROPERTY$16, i);
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
         return this.get_store().count_elements(PROPERTY$16);
      }
   }

   public void setPropertyArray(PropertyType[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$16);
   }

   public void setPropertyArray(int i, PropertyType property) {
      this.generatedSetterHelperImpl(property, PROPERTY$16, i, (short)2);
   }

   public PropertyType insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().insert_element_user(PROPERTY$16, i);
         return target;
      }
   }

   public PropertyType addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().add_element_user(PROPERTY$16);
         return target;
      }
   }

   public void removeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$16, i);
      }
   }

   public XsdBooleanType getTransactional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().find_element_user(TRANSACTIONAL$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONAL$18) != 0;
      }
   }

   public void setTransactional(XsdBooleanType transactional) {
      this.generatedSetterHelperImpl(transactional, TRANSACTIONAL$18, 0, (short)1);
   }

   public XsdBooleanType addNewTransactional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().add_element_user(TRANSACTIONAL$18);
         return target;
      }
   }

   public void unsetTransactional() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONAL$18, 0);
      }
   }

   public XsdIntegerType getMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXPOOLSIZE$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXPOOLSIZE$20) != 0;
      }
   }

   public void setMaxPoolSize(XsdIntegerType maxPoolSize) {
      this.generatedSetterHelperImpl(maxPoolSize, MAXPOOLSIZE$20, 0, (short)1);
   }

   public XsdIntegerType addNewMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXPOOLSIZE$20);
         return target;
      }
   }

   public void unsetMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXPOOLSIZE$20, 0);
      }
   }

   public XsdIntegerType getMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MINPOOLSIZE$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINPOOLSIZE$22) != 0;
      }
   }

   public void setMinPoolSize(XsdIntegerType minPoolSize) {
      this.generatedSetterHelperImpl(minPoolSize, MINPOOLSIZE$22, 0, (short)1);
   }

   public XsdIntegerType addNewMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MINPOOLSIZE$22);
         return target;
      }
   }

   public void unsetMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINPOOLSIZE$22, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$24);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$24);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$24) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$24);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$24);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$24);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$24);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$24);
      }
   }
}
