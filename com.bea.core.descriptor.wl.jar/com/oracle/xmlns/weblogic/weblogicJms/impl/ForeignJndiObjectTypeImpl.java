package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.ForeignJndiObjectType;
import javax.xml.namespace.QName;

public class ForeignJndiObjectTypeImpl extends NamedEntityTypeImpl implements ForeignJndiObjectType {
   private static final long serialVersionUID = 1L;
   private static final QName LOCALJNDINAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "local-jndi-name");
   private static final QName REMOTEJNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "remote-jndi-name");

   public ForeignJndiObjectTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$0, 0);
         return target;
      }
   }

   public void setLocalJndiName(String localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCALJNDINAME$0);
         }

         target.setStringValue(localJndiName);
      }
   }

   public void xsetLocalJndiName(XmlString localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$0);
         }

         target.set(localJndiName);
      }
   }

   public String getRemoteJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REMOTEJNDINAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRemoteJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTEJNDINAME$2, 0);
         return target;
      }
   }

   public void setRemoteJndiName(String remoteJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REMOTEJNDINAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REMOTEJNDINAME$2);
         }

         target.setStringValue(remoteJndiName);
      }
   }

   public void xsetRemoteJndiName(XmlString remoteJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTEJNDINAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REMOTEJNDINAME$2);
         }

         target.set(remoteJndiName);
      }
   }
}
