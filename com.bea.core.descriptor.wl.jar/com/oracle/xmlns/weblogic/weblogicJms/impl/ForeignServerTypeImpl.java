package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.ForeignConnectionFactoryType;
import com.oracle.xmlns.weblogic.weblogicJms.ForeignDestinationType;
import com.oracle.xmlns.weblogic.weblogicJms.ForeignServerType;
import com.oracle.xmlns.weblogic.weblogicJms.PropertyType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ForeignServerTypeImpl extends TargetableTypeImpl implements ForeignServerType {
   private static final long serialVersionUID = 1L;
   private static final QName FOREIGNDESTINATION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "foreign-destination");
   private static final QName FOREIGNCONNECTIONFACTORY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "foreign-connection-factory");
   private static final QName INITIALCONTEXTFACTORY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "initial-context-factory");
   private static final QName CONNECTIONURL$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "connection-url");
   private static final QName JNDIPROPERTIESCREDENTIALENCRYPTED$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "jndi-properties-credential-encrypted");
   private static final QName JNDIPROPERTY$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "jndi-property");

   public ForeignServerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ForeignDestinationType[] getForeignDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FOREIGNDESTINATION$0, targetList);
         ForeignDestinationType[] result = new ForeignDestinationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ForeignDestinationType getForeignDestinationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignDestinationType target = null;
         target = (ForeignDestinationType)this.get_store().find_element_user(FOREIGNDESTINATION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfForeignDestinationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FOREIGNDESTINATION$0);
      }
   }

   public void setForeignDestinationArray(ForeignDestinationType[] foreignDestinationArray) {
      this.check_orphaned();
      this.arraySetterHelper(foreignDestinationArray, FOREIGNDESTINATION$0);
   }

   public void setForeignDestinationArray(int i, ForeignDestinationType foreignDestination) {
      this.generatedSetterHelperImpl(foreignDestination, FOREIGNDESTINATION$0, i, (short)2);
   }

   public ForeignDestinationType insertNewForeignDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignDestinationType target = null;
         target = (ForeignDestinationType)this.get_store().insert_element_user(FOREIGNDESTINATION$0, i);
         return target;
      }
   }

   public ForeignDestinationType addNewForeignDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignDestinationType target = null;
         target = (ForeignDestinationType)this.get_store().add_element_user(FOREIGNDESTINATION$0);
         return target;
      }
   }

   public void removeForeignDestination(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FOREIGNDESTINATION$0, i);
      }
   }

   public ForeignConnectionFactoryType[] getForeignConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FOREIGNCONNECTIONFACTORY$2, targetList);
         ForeignConnectionFactoryType[] result = new ForeignConnectionFactoryType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ForeignConnectionFactoryType getForeignConnectionFactoryArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignConnectionFactoryType target = null;
         target = (ForeignConnectionFactoryType)this.get_store().find_element_user(FOREIGNCONNECTIONFACTORY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfForeignConnectionFactoryArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FOREIGNCONNECTIONFACTORY$2);
      }
   }

   public void setForeignConnectionFactoryArray(ForeignConnectionFactoryType[] foreignConnectionFactoryArray) {
      this.check_orphaned();
      this.arraySetterHelper(foreignConnectionFactoryArray, FOREIGNCONNECTIONFACTORY$2);
   }

   public void setForeignConnectionFactoryArray(int i, ForeignConnectionFactoryType foreignConnectionFactory) {
      this.generatedSetterHelperImpl(foreignConnectionFactory, FOREIGNCONNECTIONFACTORY$2, i, (short)2);
   }

   public ForeignConnectionFactoryType insertNewForeignConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignConnectionFactoryType target = null;
         target = (ForeignConnectionFactoryType)this.get_store().insert_element_user(FOREIGNCONNECTIONFACTORY$2, i);
         return target;
      }
   }

   public ForeignConnectionFactoryType addNewForeignConnectionFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForeignConnectionFactoryType target = null;
         target = (ForeignConnectionFactoryType)this.get_store().add_element_user(FOREIGNCONNECTIONFACTORY$2);
         return target;
      }
   }

   public void removeForeignConnectionFactory(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FOREIGNCONNECTIONFACTORY$2, i);
      }
   }

   public String getInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALCONTEXTFACTORY$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITIALCONTEXTFACTORY$4, 0);
         return target;
      }
   }

   public boolean isNilInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITIALCONTEXTFACTORY$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITIALCONTEXTFACTORY$4) != 0;
      }
   }

   public void setInitialContextFactory(String initialContextFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INITIALCONTEXTFACTORY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INITIALCONTEXTFACTORY$4);
         }

         target.setStringValue(initialContextFactory);
      }
   }

   public void xsetInitialContextFactory(XmlString initialContextFactory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITIALCONTEXTFACTORY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INITIALCONTEXTFACTORY$4);
         }

         target.set(initialContextFactory);
      }
   }

   public void setNilInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(INITIALCONTEXTFACTORY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(INITIALCONTEXTFACTORY$4);
         }

         target.setNil();
      }
   }

   public void unsetInitialContextFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITIALCONTEXTFACTORY$4, 0);
      }
   }

   public String getConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         return target;
      }
   }

   public boolean isNilConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONURL$6) != 0;
      }
   }

   public void setConnectionUrl(String connectionUrl) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONURL$6);
         }

         target.setStringValue(connectionUrl);
      }
   }

   public void xsetConnectionUrl(XmlString connectionUrl) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONURL$6);
         }

         target.set(connectionUrl);
      }
   }

   public void setNilConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONURL$6);
         }

         target.setNil();
      }
   }

   public void unsetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONURL$6, 0);
      }
   }

   public String getJndiPropertiesCredentialEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJndiPropertiesCredentialEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8, 0);
         return target;
      }
   }

   public boolean isNilJndiPropertiesCredentialEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJndiPropertiesCredentialEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDIPROPERTIESCREDENTIALENCRYPTED$8) != 0;
      }
   }

   public void setJndiPropertiesCredentialEncrypted(String jndiPropertiesCredentialEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8);
         }

         target.setStringValue(jndiPropertiesCredentialEncrypted);
      }
   }

   public void xsetJndiPropertiesCredentialEncrypted(XmlString jndiPropertiesCredentialEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8);
         }

         target.set(jndiPropertiesCredentialEncrypted);
      }
   }

   public void setNilJndiPropertiesCredentialEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDIPROPERTIESCREDENTIALENCRYPTED$8);
         }

         target.setNil();
      }
   }

   public void unsetJndiPropertiesCredentialEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDIPROPERTIESCREDENTIALENCRYPTED$8, 0);
      }
   }

   public PropertyType[] getJndiPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(JNDIPROPERTY$10, targetList);
         PropertyType[] result = new PropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType getJndiPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().find_element_user(JNDIPROPERTY$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfJndiPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDIPROPERTY$10);
      }
   }

   public void setJndiPropertyArray(PropertyType[] jndiPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(jndiPropertyArray, JNDIPROPERTY$10);
   }

   public void setJndiPropertyArray(int i, PropertyType jndiProperty) {
      this.generatedSetterHelperImpl(jndiProperty, JNDIPROPERTY$10, i, (short)2);
   }

   public PropertyType insertNewJndiProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().insert_element_user(JNDIPROPERTY$10, i);
         return target;
      }
   }

   public PropertyType addNewJndiProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType target = null;
         target = (PropertyType)this.get_store().add_element_user(JNDIPROPERTY$10);
         return target;
      }
   }

   public void removeJndiProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDIPROPERTY$10, i);
      }
   }
}
