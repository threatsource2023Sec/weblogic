package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ConnectionFactoryResourceType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.JndiNameType;
import org.jcp.xmlns.xml.ns.javaee.PropertyType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.TransactionSupportType;
import org.jcp.xmlns.xml.ns.javaee.XsdIntegerType;

public class ConnectionFactoryResourceTypeImpl extends XmlComplexContentImpl implements ConnectionFactoryResourceType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName NAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
   private static final QName INTERFACENAME$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "interface-name");
   private static final QName RESOURCEADAPTER$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-adapter");
   private static final QName MAXPOOLSIZE$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "max-pool-size");
   private static final QName MINPOOLSIZE$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "min-pool-size");
   private static final QName TRANSACTIONSUPPORT$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "transaction-support");
   private static final QName PROPERTY$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "property");
   private static final QName ID$16 = new QName("", "id");

   public ConnectionFactoryResourceTypeImpl(SchemaType sType) {
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

   public String getResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RESOURCEADAPTER$6, 0);
         return target == null ? null : target;
      }
   }

   public void setResourceAdapter(String resourceAdapter) {
      this.generatedSetterHelperImpl(resourceAdapter, RESOURCEADAPTER$6, 0, (short)1);
   }

   public String addNewResourceAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(RESOURCEADAPTER$6);
         return target;
      }
   }

   public XsdIntegerType getMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MAXPOOLSIZE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXPOOLSIZE$8) != 0;
      }
   }

   public void setMaxPoolSize(XsdIntegerType maxPoolSize) {
      this.generatedSetterHelperImpl(maxPoolSize, MAXPOOLSIZE$8, 0, (short)1);
   }

   public XsdIntegerType addNewMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MAXPOOLSIZE$8);
         return target;
      }
   }

   public void unsetMaxPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXPOOLSIZE$8, 0);
      }
   }

   public XsdIntegerType getMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(MINPOOLSIZE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MINPOOLSIZE$10) != 0;
      }
   }

   public void setMinPoolSize(XsdIntegerType minPoolSize) {
      this.generatedSetterHelperImpl(minPoolSize, MINPOOLSIZE$10, 0, (short)1);
   }

   public XsdIntegerType addNewMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(MINPOOLSIZE$10);
         return target;
      }
   }

   public void unsetMinPoolSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MINPOOLSIZE$10, 0);
      }
   }

   public TransactionSupportType getTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionSupportType target = null;
         target = (TransactionSupportType)this.get_store().find_element_user(TRANSACTIONSUPPORT$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONSUPPORT$12) != 0;
      }
   }

   public void setTransactionSupport(TransactionSupportType transactionSupport) {
      this.generatedSetterHelperImpl(transactionSupport, TRANSACTIONSUPPORT$12, 0, (short)1);
   }

   public TransactionSupportType addNewTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionSupportType target = null;
         target = (TransactionSupportType)this.get_store().add_element_user(TRANSACTIONSUPPORT$12);
         return target;
      }
   }

   public void unsetTransactionSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONSUPPORT$12, 0);
      }
   }

   public PropertyType[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$14, targetList);
         PropertyType[] result = new PropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PROPERTY$14, i);
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
         return this.get_store().count_elements(PROPERTY$14);
      }
   }

   public void setPropertyArray(PropertyType[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$14);
   }

   public void setPropertyArray(int i, PropertyType property) {
      this.generatedSetterHelperImpl(property, PROPERTY$14, i, (short)2);
   }

   public PropertyType insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().insert_element_user(PROPERTY$14, i);
         return target;
      }
   }

   public PropertyType addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().add_element_user(PROPERTY$14);
         return target;
      }
   }

   public void removeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$14, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$16) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$16);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$16);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$16);
      }
   }
}
