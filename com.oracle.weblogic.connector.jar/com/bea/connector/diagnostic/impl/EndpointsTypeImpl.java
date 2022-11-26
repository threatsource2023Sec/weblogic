package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.EndpointType;
import com.bea.connector.diagnostic.EndpointsType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EndpointsTypeImpl extends XmlComplexContentImpl implements EndpointsType {
   private static final long serialVersionUID = 1L;
   private static final QName ENDPOINT$0 = new QName("http://www.bea.com/connector/diagnostic", "endpoint");

   public EndpointsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public EndpointType[] getEndpointArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ENDPOINT$0, targetList);
         EndpointType[] result = new EndpointType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public EndpointType getEndpointArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EndpointType target = null;
         target = (EndpointType)this.get_store().find_element_user(ENDPOINT$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEndpointArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENDPOINT$0);
      }
   }

   public void setEndpointArray(EndpointType[] endpointArray) {
      this.check_orphaned();
      this.arraySetterHelper(endpointArray, ENDPOINT$0);
   }

   public void setEndpointArray(int i, EndpointType endpoint) {
      this.generatedSetterHelperImpl(endpoint, ENDPOINT$0, i, (short)2);
   }

   public EndpointType insertNewEndpoint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EndpointType target = null;
         target = (EndpointType)this.get_store().insert_element_user(ENDPOINT$0, i);
         return target;
      }
   }

   public EndpointType addNewEndpoint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EndpointType target = null;
         target = (EndpointType)this.get_store().add_element_user(ENDPOINT$0);
         return target;
      }
   }

   public void removeEndpoint(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENDPOINT$0, i);
      }
   }
}
