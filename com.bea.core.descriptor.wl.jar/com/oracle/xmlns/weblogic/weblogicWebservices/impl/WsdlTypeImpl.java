package com.oracle.xmlns.weblogic.weblogicWebservices.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.weblogicWebservices.WsdlType;
import javax.xml.namespace.QName;

public class WsdlTypeImpl extends XmlComplexContentImpl implements WsdlType {
   private static final long serialVersionUID = 1L;
   private static final QName EXPOSED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-webservices", "exposed");

   public WsdlTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getExposed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXPOSED$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetExposed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(EXPOSED$0, 0);
         return target;
      }
   }

   public void setExposed(boolean exposed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXPOSED$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EXPOSED$0);
         }

         target.setBooleanValue(exposed);
      }
   }

   public void xsetExposed(XmlBoolean exposed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(EXPOSED$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(EXPOSED$0);
         }

         target.set(exposed);
      }
   }
}
