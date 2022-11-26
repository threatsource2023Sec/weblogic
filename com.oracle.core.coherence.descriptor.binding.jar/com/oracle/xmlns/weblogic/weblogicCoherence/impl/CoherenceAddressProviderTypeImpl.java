package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceAddressProviderType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceSocketAddressType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class CoherenceAddressProviderTypeImpl extends XmlComplexContentImpl implements CoherenceAddressProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "name");
   private static final QName COHERENCESOCKETADDRESS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-socket-address");

   public CoherenceAddressProviderTypeImpl(SchemaType sType) {
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

   public CoherenceSocketAddressType[] getCoherenceSocketAddressArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(COHERENCESOCKETADDRESS$2, targetList);
         CoherenceSocketAddressType[] result = new CoherenceSocketAddressType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CoherenceSocketAddressType getCoherenceSocketAddressArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceSocketAddressType target = null;
         target = (CoherenceSocketAddressType)this.get_store().find_element_user(COHERENCESOCKETADDRESS$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCoherenceSocketAddressArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCESOCKETADDRESS$2);
      }
   }

   public void setCoherenceSocketAddressArray(CoherenceSocketAddressType[] coherenceSocketAddressArray) {
      this.check_orphaned();
      this.arraySetterHelper(coherenceSocketAddressArray, COHERENCESOCKETADDRESS$2);
   }

   public void setCoherenceSocketAddressArray(int i, CoherenceSocketAddressType coherenceSocketAddress) {
      this.generatedSetterHelperImpl(coherenceSocketAddress, COHERENCESOCKETADDRESS$2, i, (short)2);
   }

   public CoherenceSocketAddressType insertNewCoherenceSocketAddress(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceSocketAddressType target = null;
         target = (CoherenceSocketAddressType)this.get_store().insert_element_user(COHERENCESOCKETADDRESS$2, i);
         return target;
      }
   }

   public CoherenceSocketAddressType addNewCoherenceSocketAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceSocketAddressType target = null;
         target = (CoherenceSocketAddressType)this.get_store().add_element_user(COHERENCESOCKETADDRESS$2);
         return target;
      }
   }

   public void removeCoherenceSocketAddress(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCESOCKETADDRESS$2, i);
      }
   }
}
