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
import org.jcp.xmlns.xml.ns.javaee.JndiNameType;
import org.jcp.xmlns.xml.ns.javaee.MailSessionType;
import org.jcp.xmlns.xml.ns.javaee.PropertyType;
import org.jcp.xmlns.xml.ns.javaee.String;

public class MailSessionTypeImpl extends XmlComplexContentImpl implements MailSessionType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName NAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
   private static final QName STOREPROTOCOL$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "store-protocol");
   private static final QName STOREPROTOCOLCLASS$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "store-protocol-class");
   private static final QName TRANSPORTPROTOCOL$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "transport-protocol");
   private static final QName TRANSPORTPROTOCOLCLASS$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "transport-protocol-class");
   private static final QName HOST$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "host");
   private static final QName USER$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "user");
   private static final QName PASSWORD$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "password");
   private static final QName FROM$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "from");
   private static final QName PROPERTY$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "property");
   private static final QName ID$22 = new QName("", "id");

   public MailSessionTypeImpl(SchemaType sType) {
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

   public String getStoreProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(STOREPROTOCOL$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStoreProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STOREPROTOCOL$4) != 0;
      }
   }

   public void setStoreProtocol(String storeProtocol) {
      this.generatedSetterHelperImpl(storeProtocol, STOREPROTOCOL$4, 0, (short)1);
   }

   public String addNewStoreProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(STOREPROTOCOL$4);
         return target;
      }
   }

   public void unsetStoreProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOREPROTOCOL$4, 0);
      }
   }

   public FullyQualifiedClassType getStoreProtocolClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(STOREPROTOCOLCLASS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStoreProtocolClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STOREPROTOCOLCLASS$6) != 0;
      }
   }

   public void setStoreProtocolClass(FullyQualifiedClassType storeProtocolClass) {
      this.generatedSetterHelperImpl(storeProtocolClass, STOREPROTOCOLCLASS$6, 0, (short)1);
   }

   public FullyQualifiedClassType addNewStoreProtocolClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(STOREPROTOCOLCLASS$6);
         return target;
      }
   }

   public void unsetStoreProtocolClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOREPROTOCOLCLASS$6, 0);
      }
   }

   public String getTransportProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(TRANSPORTPROTOCOL$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransportProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSPORTPROTOCOL$8) != 0;
      }
   }

   public void setTransportProtocol(String transportProtocol) {
      this.generatedSetterHelperImpl(transportProtocol, TRANSPORTPROTOCOL$8, 0, (short)1);
   }

   public String addNewTransportProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(TRANSPORTPROTOCOL$8);
         return target;
      }
   }

   public void unsetTransportProtocol() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSPORTPROTOCOL$8, 0);
      }
   }

   public FullyQualifiedClassType getTransportProtocolClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(TRANSPORTPROTOCOLCLASS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransportProtocolClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSPORTPROTOCOLCLASS$10) != 0;
      }
   }

   public void setTransportProtocolClass(FullyQualifiedClassType transportProtocolClass) {
      this.generatedSetterHelperImpl(transportProtocolClass, TRANSPORTPROTOCOLCLASS$10, 0, (short)1);
   }

   public FullyQualifiedClassType addNewTransportProtocolClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(TRANSPORTPROTOCOLCLASS$10);
         return target;
      }
   }

   public void unsetTransportProtocolClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSPORTPROTOCOLCLASS$10, 0);
      }
   }

   public String getHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(HOST$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOST$12) != 0;
      }
   }

   public void setHost(String host) {
      this.generatedSetterHelperImpl(host, HOST$12, 0, (short)1);
   }

   public String addNewHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(HOST$12);
         return target;
      }
   }

   public void unsetHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOST$12, 0);
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

   public String getFrom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(FROM$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFrom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FROM$18) != 0;
      }
   }

   public void setFrom(String from) {
      this.generatedSetterHelperImpl(from, FROM$18, 0, (short)1);
   }

   public String addNewFrom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(FROM$18);
         return target;
      }
   }

   public void unsetFrom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FROM$18, 0);
      }
   }

   public PropertyType[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$20, targetList);
         PropertyType[] result = new PropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(PROPERTY$20, i);
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
         return this.get_store().count_elements(PROPERTY$20);
      }
   }

   public void setPropertyArray(PropertyType[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$20);
   }

   public void setPropertyArray(int i, PropertyType property) {
      this.generatedSetterHelperImpl(property, PROPERTY$20, i, (short)2);
   }

   public PropertyType insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().insert_element_user(PROPERTY$20, i);
         return target;
      }
   }

   public PropertyType addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().add_element_user(PROPERTY$20);
         return target;
      }
   }

   public void removeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$20, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$22);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$22) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$22);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$22);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$22);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$22);
      }
   }
}
