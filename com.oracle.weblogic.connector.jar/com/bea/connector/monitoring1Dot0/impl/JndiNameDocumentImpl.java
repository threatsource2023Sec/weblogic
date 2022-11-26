package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.JndiNameDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class JndiNameDocumentImpl extends XmlComplexContentImpl implements JndiNameDocument {
   private static final long serialVersionUID = 1L;
   private static final QName JNDINAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "jndi-name");

   public JndiNameDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         return target;
      }
   }

   public void setJndiName(String jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JNDINAME$0);
         }

         target.setStringValue(jndiName);
      }
   }

   public void xsetJndiName(XmlString jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$0);
         }

         target.set(jndiName);
      }
   }
}
