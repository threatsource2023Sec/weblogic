package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceClusterWellKnownAddressType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceClusterWellKnownAddressesType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class CoherenceClusterWellKnownAddressesTypeImpl extends XmlComplexContentImpl implements CoherenceClusterWellKnownAddressesType {
   private static final long serialVersionUID = 1L;
   private static final QName COHERENCECLUSTERWELLKNOWNADDRESS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-cluster-well-known-address");

   public CoherenceClusterWellKnownAddressesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CoherenceClusterWellKnownAddressType[] getCoherenceClusterWellKnownAddressArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(COHERENCECLUSTERWELLKNOWNADDRESS$0, targetList);
         CoherenceClusterWellKnownAddressType[] result = new CoherenceClusterWellKnownAddressType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CoherenceClusterWellKnownAddressType getCoherenceClusterWellKnownAddressArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterWellKnownAddressType target = null;
         target = (CoherenceClusterWellKnownAddressType)this.get_store().find_element_user(COHERENCECLUSTERWELLKNOWNADDRESS$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCoherenceClusterWellKnownAddressArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCECLUSTERWELLKNOWNADDRESS$0);
      }
   }

   public void setCoherenceClusterWellKnownAddressArray(CoherenceClusterWellKnownAddressType[] coherenceClusterWellKnownAddressArray) {
      this.check_orphaned();
      this.arraySetterHelper(coherenceClusterWellKnownAddressArray, COHERENCECLUSTERWELLKNOWNADDRESS$0);
   }

   public void setCoherenceClusterWellKnownAddressArray(int i, CoherenceClusterWellKnownAddressType coherenceClusterWellKnownAddress) {
      this.generatedSetterHelperImpl(coherenceClusterWellKnownAddress, COHERENCECLUSTERWELLKNOWNADDRESS$0, i, (short)2);
   }

   public CoherenceClusterWellKnownAddressType insertNewCoherenceClusterWellKnownAddress(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterWellKnownAddressType target = null;
         target = (CoherenceClusterWellKnownAddressType)this.get_store().insert_element_user(COHERENCECLUSTERWELLKNOWNADDRESS$0, i);
         return target;
      }
   }

   public CoherenceClusterWellKnownAddressType addNewCoherenceClusterWellKnownAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterWellKnownAddressType target = null;
         target = (CoherenceClusterWellKnownAddressType)this.get_store().add_element_user(COHERENCECLUSTERWELLKNOWNADDRESS$0);
         return target;
      }
   }

   public void removeCoherenceClusterWellKnownAddress(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCECLUSTERWELLKNOWNADDRESS$0, i);
      }
   }
}
