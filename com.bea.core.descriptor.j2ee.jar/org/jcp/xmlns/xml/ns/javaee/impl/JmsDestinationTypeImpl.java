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
import org.jcp.xmlns.xml.ns.javaee.JmsDestinationType;
import org.jcp.xmlns.xml.ns.javaee.JndiNameType;
import org.jcp.xmlns.xml.ns.javaee.PropertyType;
import org.jcp.xmlns.xml.ns.javaee.String;

public class JmsDestinationTypeImpl extends XmlComplexContentImpl implements JmsDestinationType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName NAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
   private static final QName INTERFACENAME$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "interface-name");
   private static final QName CLASSNAME$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "class-name");
   private static final QName RESOURCEADAPTER$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resource-adapter");
   private static final QName DESTINATIONNAME$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "destination-name");
   private static final QName PROPERTY$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "property");
   private static final QName ID$14 = new QName("", "id");

   public JmsDestinationTypeImpl(SchemaType sType) {
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

   public String getDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DESTINATIONNAME$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONNAME$10) != 0;
      }
   }

   public void setDestinationName(String destinationName) {
      this.generatedSetterHelperImpl(destinationName, DESTINATIONNAME$10, 0, (short)1);
   }

   public String addNewDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DESTINATIONNAME$10);
         return target;
      }
   }

   public void unsetDestinationName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONNAME$10, 0);
      }
   }

   public PropertyType[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$12, targetList);
         PropertyType[] result = new PropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PROPERTY$12, i);
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
         return this.get_store().count_elements(PROPERTY$12);
      }
   }

   public void setPropertyArray(PropertyType[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$12);
   }

   public void setPropertyArray(int i, PropertyType property) {
      this.generatedSetterHelperImpl(property, PROPERTY$12, i, (short)2);
   }

   public PropertyType insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().insert_element_user(PROPERTY$12, i);
         return target;
      }
   }

   public PropertyType addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().add_element_user(PROPERTY$12);
         return target;
      }
   }

   public void removeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$12, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
