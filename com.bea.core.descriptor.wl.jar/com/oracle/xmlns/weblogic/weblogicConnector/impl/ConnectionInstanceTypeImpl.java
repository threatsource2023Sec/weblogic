package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionDefinitionPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionInstanceType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import javax.xml.namespace.QName;

public class ConnectionInstanceTypeImpl extends XmlComplexContentImpl implements ConnectionInstanceType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "description");
   private static final QName JNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "jndi-name");
   private static final QName CONNECTIONPROPERTIES$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "connection-properties");

   public ConnectionInstanceTypeImpl(SchemaType sType) {
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

   public JndiNameType getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(JNDINAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setJndiName(JndiNameType jndiName) {
      this.generatedSetterHelperImpl(jndiName, JNDINAME$2, 0, (short)1);
   }

   public JndiNameType addNewJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(JNDINAME$2);
         return target;
      }
   }

   public ConnectionDefinitionPropertiesType getConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().find_element_user(CONNECTIONPROPERTIES$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONPROPERTIES$4) != 0;
      }
   }

   public void setConnectionProperties(ConnectionDefinitionPropertiesType connectionProperties) {
      this.generatedSetterHelperImpl(connectionProperties, CONNECTIONPROPERTIES$4, 0, (short)1);
   }

   public ConnectionDefinitionPropertiesType addNewConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().add_element_user(CONNECTIONPROPERTIES$4);
         return target;
      }
   }

   public void unsetConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONPROPERTIES$4, 0);
      }
   }
}
