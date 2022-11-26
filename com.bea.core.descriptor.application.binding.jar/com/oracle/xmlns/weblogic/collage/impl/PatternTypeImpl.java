package com.oracle.xmlns.weblogic.collage.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.collage.PatternType;
import javax.xml.namespace.QName;

public class PatternTypeImpl extends XmlComplexContentImpl implements PatternType {
   private static final long serialVersionUID = 1L;
   private static final QName REFID$0 = new QName("", "refid");

   public PatternTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getRefid() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REFID$0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRefid() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(REFID$0);
         return target;
      }
   }

   public void setRefid(String refid) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REFID$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(REFID$0);
         }

         target.setStringValue(refid);
      }
   }

   public void xsetRefid(XmlString refid) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(REFID$0);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(REFID$0);
         }

         target.set(refid);
      }
   }
}
