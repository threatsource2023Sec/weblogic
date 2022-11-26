package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.AdapterType;
import com.bea.connector.diagnostic.ConnectorDiagnosticImageType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectorDiagnosticImageTypeImpl extends XmlComplexContentImpl implements ConnectorDiagnosticImageType {
   private static final long serialVersionUID = 1L;
   private static final QName ADAPTER$0 = new QName("http://www.bea.com/connector/diagnostic", "adapter");

   public ConnectorDiagnosticImageTypeImpl(SchemaType sType) {
      super(sType);
   }

   public AdapterType[] getAdapterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ADAPTER$0, targetList);
         AdapterType[] result = new AdapterType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AdapterType getAdapterArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdapterType target = null;
         target = (AdapterType)this.get_store().find_element_user(ADAPTER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAdapterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADAPTER$0);
      }
   }

   public void setAdapterArray(AdapterType[] adapterArray) {
      this.check_orphaned();
      this.arraySetterHelper(adapterArray, ADAPTER$0);
   }

   public void setAdapterArray(int i, AdapterType adapter) {
      this.generatedSetterHelperImpl(adapter, ADAPTER$0, i, (short)2);
   }

   public AdapterType insertNewAdapter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdapterType target = null;
         target = (AdapterType)this.get_store().insert_element_user(ADAPTER$0, i);
         return target;
      }
   }

   public AdapterType addNewAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdapterType target = null;
         target = (AdapterType)this.get_store().add_element_user(ADAPTER$0);
         return target;
      }
   }

   public void removeAdapter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADAPTER$0, i);
      }
   }
}
