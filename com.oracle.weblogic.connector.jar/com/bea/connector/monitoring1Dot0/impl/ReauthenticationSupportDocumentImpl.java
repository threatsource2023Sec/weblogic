package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ReauthenticationSupportDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import javax.xml.namespace.QName;

public class ReauthenticationSupportDocumentImpl extends XmlComplexContentImpl implements ReauthenticationSupportDocument {
   private static final long serialVersionUID = 1L;
   private static final QName REAUTHENTICATIONSUPPORT$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "reauthentication-support");

   public ReauthenticationSupportDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetReauthenticationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$0, 0);
         return target;
      }
   }

   public void setReauthenticationSupport(boolean reauthenticationSupport) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REAUTHENTICATIONSUPPORT$0);
         }

         target.setBooleanValue(reauthenticationSupport);
      }
   }

   public void xsetReauthenticationSupport(XmlBoolean reauthenticationSupport) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(REAUTHENTICATIONSUPPORT$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(REAUTHENTICATIONSUPPORT$0);
         }

         target.set(reauthenticationSupport);
      }
   }
}
