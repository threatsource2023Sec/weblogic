package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ResponseTimeRequestClassType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInteger;
import com.bea.xml.XmlString;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class ResponseTimeRequestClassTypeImpl extends XmlComplexContentImpl implements ResponseTimeRequestClassType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "name");
   private static final QName GOALMS$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "goal-ms");

   public ResponseTimeRequestClassTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
      }
   }

   public BigInteger getGoalMs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(GOALMS$2, 0);
         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlInteger xgetGoalMs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(GOALMS$2, 0);
         return target;
      }
   }

   public void setGoalMs(BigInteger goalMs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(GOALMS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(GOALMS$2);
         }

         target.setBigIntegerValue(goalMs);
      }
   }

   public void xsetGoalMs(XmlInteger goalMs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInteger target = null;
         target = (XmlInteger)this.get_store().find_element_user(GOALMS$2, 0);
         if (target == null) {
            target = (XmlInteger)this.get_store().add_element_user(GOALMS$2);
         }

         target.set(goalMs);
      }
   }
}
