package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceAddressProviderType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceAddressProvidersType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class CoherenceAddressProvidersTypeImpl extends XmlComplexContentImpl implements CoherenceAddressProvidersType {
   private static final long serialVersionUID = 1L;
   private static final QName COHERENCEADDRESSPROVIDER$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-address-provider");

   public CoherenceAddressProvidersTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CoherenceAddressProviderType[] getCoherenceAddressProviderArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(COHERENCEADDRESSPROVIDER$0, targetList);
         CoherenceAddressProviderType[] result = new CoherenceAddressProviderType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CoherenceAddressProviderType getCoherenceAddressProviderArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceAddressProviderType target = null;
         target = (CoherenceAddressProviderType)this.get_store().find_element_user(COHERENCEADDRESSPROVIDER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCoherenceAddressProviderArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEADDRESSPROVIDER$0);
      }
   }

   public void setCoherenceAddressProviderArray(CoherenceAddressProviderType[] coherenceAddressProviderArray) {
      this.check_orphaned();
      this.arraySetterHelper(coherenceAddressProviderArray, COHERENCEADDRESSPROVIDER$0);
   }

   public void setCoherenceAddressProviderArray(int i, CoherenceAddressProviderType coherenceAddressProvider) {
      this.generatedSetterHelperImpl(coherenceAddressProvider, COHERENCEADDRESSPROVIDER$0, i, (short)2);
   }

   public CoherenceAddressProviderType insertNewCoherenceAddressProvider(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceAddressProviderType target = null;
         target = (CoherenceAddressProviderType)this.get_store().insert_element_user(COHERENCEADDRESSPROVIDER$0, i);
         return target;
      }
   }

   public CoherenceAddressProviderType addNewCoherenceAddressProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceAddressProviderType target = null;
         target = (CoherenceAddressProviderType)this.get_store().add_element_user(COHERENCEADDRESSPROVIDER$0);
         return target;
      }
   }

   public void removeCoherenceAddressProvider(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEADDRESSPROVIDER$0, i);
      }
   }
}
