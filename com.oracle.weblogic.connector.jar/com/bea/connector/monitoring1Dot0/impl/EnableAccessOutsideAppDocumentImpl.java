package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.EnableAccessOutsideAppDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import javax.xml.namespace.QName;

public class EnableAccessOutsideAppDocumentImpl extends XmlComplexContentImpl implements EnableAccessOutsideAppDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLEACCESSOUTSIDEAPP$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "enable-access-outside-app");

   public EnableAccessOutsideAppDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getEnableAccessOutsideApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetEnableAccessOutsideApp() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$0, 0);
         return target;
      }
   }

   public void setEnableAccessOutsideApp(boolean enableAccessOutsideApp) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ENABLEACCESSOUTSIDEAPP$0);
         }

         target.setBooleanValue(enableAccessOutsideApp);
      }
   }

   public void xsetEnableAccessOutsideApp(XmlBoolean enableAccessOutsideApp) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ENABLEACCESSOUTSIDEAPP$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ENABLEACCESSOUTSIDEAPP$0);
         }

         target.set(enableAccessOutsideApp);
      }
   }
}
