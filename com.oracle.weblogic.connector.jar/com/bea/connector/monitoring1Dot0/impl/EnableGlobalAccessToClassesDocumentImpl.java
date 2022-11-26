package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.EnableGlobalAccessToClassesDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import javax.xml.namespace.QName;

public class EnableGlobalAccessToClassesDocumentImpl extends XmlComplexContentImpl implements EnableGlobalAccessToClassesDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLEGLOBALACCESSTOCLASSES$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "enable-global-access-to-classes");

   public EnableGlobalAccessToClassesDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getEnableGlobalAccessToClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetEnableGlobalAccessToClasses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$0, 0);
         return target;
      }
   }

   public void setEnableGlobalAccessToClasses(boolean enableGlobalAccessToClasses) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENABLEGLOBALACCESSTOCLASSES$0);
         }

         target.setBooleanValue(enableGlobalAccessToClasses);
      }
   }

   public void xsetEnableGlobalAccessToClasses(XmlBoolean enableGlobalAccessToClasses) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLEGLOBALACCESSTOCLASSES$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ENABLEGLOBALACCESSTOCLASSES$0);
         }

         target.set(enableGlobalAccessToClasses);
      }
   }
}
