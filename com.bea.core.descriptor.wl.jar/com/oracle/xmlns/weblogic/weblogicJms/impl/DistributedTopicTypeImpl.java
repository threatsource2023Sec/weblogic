package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicJms.DistributedDestinationMemberType;
import com.oracle.xmlns.weblogic.weblogicJms.DistributedTopicType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DistributedTopicTypeImpl extends DistributedDestinationTypeImpl implements DistributedTopicType {
   private static final long serialVersionUID = 1L;
   private static final QName DISTRIBUTEDTOPICMEMBER$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "distributed-topic-member");

   public DistributedTopicTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DistributedDestinationMemberType[] getDistributedTopicMemberArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISTRIBUTEDTOPICMEMBER$0, targetList);
         DistributedDestinationMemberType[] result = new DistributedDestinationMemberType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DistributedDestinationMemberType getDistributedTopicMemberArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationMemberType target = null;
         target = (DistributedDestinationMemberType)this.get_store().find_element_user(DISTRIBUTEDTOPICMEMBER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDistributedTopicMemberArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISTRIBUTEDTOPICMEMBER$0);
      }
   }

   public void setDistributedTopicMemberArray(DistributedDestinationMemberType[] distributedTopicMemberArray) {
      this.check_orphaned();
      this.arraySetterHelper(distributedTopicMemberArray, DISTRIBUTEDTOPICMEMBER$0);
   }

   public void setDistributedTopicMemberArray(int i, DistributedDestinationMemberType distributedTopicMember) {
      this.generatedSetterHelperImpl(distributedTopicMember, DISTRIBUTEDTOPICMEMBER$0, i, (short)2);
   }

   public DistributedDestinationMemberType insertNewDistributedTopicMember(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationMemberType target = null;
         target = (DistributedDestinationMemberType)this.get_store().insert_element_user(DISTRIBUTEDTOPICMEMBER$0, i);
         return target;
      }
   }

   public DistributedDestinationMemberType addNewDistributedTopicMember() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationMemberType target = null;
         target = (DistributedDestinationMemberType)this.get_store().add_element_user(DISTRIBUTEDTOPICMEMBER$0);
         return target;
      }
   }

   public void removeDistributedTopicMember(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISTRIBUTEDTOPICMEMBER$0, i);
      }
   }
}
