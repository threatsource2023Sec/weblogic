package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicJms.QueueType;
import javax.xml.namespace.QName;

public class QueueTypeImpl extends DestinationTypeImpl implements QueueType {
   private static final long serialVersionUID = 1L;
   private static final QName FORWARDDELAY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "forward-delay");
   private static final QName RESETDELIVERYCOUNTONFORWARD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "reset-delivery-count-on-forward");

   public QueueTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getForwardDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORWARDDELAY$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetForwardDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FORWARDDELAY$0, 0);
         return target;
      }
   }

   public boolean isSetForwardDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FORWARDDELAY$0) != 0;
      }
   }

   public void setForwardDelay(int forwardDelay) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORWARDDELAY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FORWARDDELAY$0);
         }

         target.setIntValue(forwardDelay);
      }
   }

   public void xsetForwardDelay(XmlInt forwardDelay) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FORWARDDELAY$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(FORWARDDELAY$0);
         }

         target.set(forwardDelay);
      }
   }

   public void unsetForwardDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FORWARDDELAY$0, 0);
      }
   }

   public boolean getResetDeliveryCountOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESETDELIVERYCOUNTONFORWARD$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetResetDeliveryCountOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RESETDELIVERYCOUNTONFORWARD$2, 0);
         return target;
      }
   }

   public boolean isSetResetDeliveryCountOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESETDELIVERYCOUNTONFORWARD$2) != 0;
      }
   }

   public void setResetDeliveryCountOnForward(boolean resetDeliveryCountOnForward) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESETDELIVERYCOUNTONFORWARD$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESETDELIVERYCOUNTONFORWARD$2);
         }

         target.setBooleanValue(resetDeliveryCountOnForward);
      }
   }

   public void xsetResetDeliveryCountOnForward(XmlBoolean resetDeliveryCountOnForward) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RESETDELIVERYCOUNTONFORWARD$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(RESETDELIVERYCOUNTONFORWARD$2);
         }

         target.set(resetDeliveryCountOnForward);
      }
   }

   public void unsetResetDeliveryCountOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESETDELIVERYCOUNTONFORWARD$2, 0);
      }
   }
}
