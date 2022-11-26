package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicJms.DistributedDestinationMemberType;
import com.oracle.xmlns.weblogic.weblogicJms.DistributedQueueType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DistributedQueueTypeImpl extends DistributedDestinationTypeImpl implements DistributedQueueType {
   private static final long serialVersionUID = 1L;
   private static final QName DISTRIBUTEDQUEUEMEMBER$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "distributed-queue-member");
   private static final QName FORWARDDELAY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "forward-delay");
   private static final QName RESETDELIVERYCOUNTONFORWARD$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "reset-delivery-count-on-forward");

   public DistributedQueueTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DistributedDestinationMemberType[] getDistributedQueueMemberArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISTRIBUTEDQUEUEMEMBER$0, targetList);
         DistributedDestinationMemberType[] result = new DistributedDestinationMemberType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DistributedDestinationMemberType getDistributedQueueMemberArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationMemberType target = null;
         target = (DistributedDestinationMemberType)this.get_store().find_element_user(DISTRIBUTEDQUEUEMEMBER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDistributedQueueMemberArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISTRIBUTEDQUEUEMEMBER$0);
      }
   }

   public void setDistributedQueueMemberArray(DistributedDestinationMemberType[] distributedQueueMemberArray) {
      this.check_orphaned();
      this.arraySetterHelper(distributedQueueMemberArray, DISTRIBUTEDQUEUEMEMBER$0);
   }

   public void setDistributedQueueMemberArray(int i, DistributedDestinationMemberType distributedQueueMember) {
      this.generatedSetterHelperImpl(distributedQueueMember, DISTRIBUTEDQUEUEMEMBER$0, i, (short)2);
   }

   public DistributedDestinationMemberType insertNewDistributedQueueMember(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationMemberType target = null;
         target = (DistributedDestinationMemberType)this.get_store().insert_element_user(DISTRIBUTEDQUEUEMEMBER$0, i);
         return target;
      }
   }

   public DistributedDestinationMemberType addNewDistributedQueueMember() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationMemberType target = null;
         target = (DistributedDestinationMemberType)this.get_store().add_element_user(DISTRIBUTEDQUEUEMEMBER$0);
         return target;
      }
   }

   public void removeDistributedQueueMember(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTRIBUTEDQUEUEMEMBER$0, i);
      }
   }

   public int getForwardDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORWARDDELAY$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetForwardDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FORWARDDELAY$2, 0);
         return target;
      }
   }

   public boolean isSetForwardDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FORWARDDELAY$2) != 0;
      }
   }

   public void setForwardDelay(int forwardDelay) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORWARDDELAY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FORWARDDELAY$2);
         }

         target.setIntValue(forwardDelay);
      }
   }

   public void xsetForwardDelay(XmlInt forwardDelay) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FORWARDDELAY$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(FORWARDDELAY$2);
         }

         target.set(forwardDelay);
      }
   }

   public void unsetForwardDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FORWARDDELAY$2, 0);
      }
   }

   public boolean getResetDeliveryCountOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESETDELIVERYCOUNTONFORWARD$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetResetDeliveryCountOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RESETDELIVERYCOUNTONFORWARD$4, 0);
         return target;
      }
   }

   public boolean isSetResetDeliveryCountOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESETDELIVERYCOUNTONFORWARD$4) != 0;
      }
   }

   public void setResetDeliveryCountOnForward(boolean resetDeliveryCountOnForward) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESETDELIVERYCOUNTONFORWARD$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESETDELIVERYCOUNTONFORWARD$4);
         }

         target.setBooleanValue(resetDeliveryCountOnForward);
      }
   }

   public void xsetResetDeliveryCountOnForward(XmlBoolean resetDeliveryCountOnForward) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RESETDELIVERYCOUNTONFORWARD$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(RESETDELIVERYCOUNTONFORWARD$4);
         }

         target.set(resetDeliveryCountOnForward);
      }
   }

   public void unsetResetDeliveryCountOnForward() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESETDELIVERYCOUNTONFORWARD$4, 0);
      }
   }
}
