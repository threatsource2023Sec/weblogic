package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplication.ConnectionParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.ParameterType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectionParamsTypeImpl extends XmlComplexContentImpl implements ConnectionParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName PARAMETER$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "parameter");

   public ConnectionParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ParameterType[] getParameterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PARAMETER$0, targetList);
         ParameterType[] result = new ParameterType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ParameterType getParameterArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParameterType target = null;
         target = (ParameterType)this.get_store().find_element_user(PARAMETER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfParameterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARAMETER$0);
      }
   }

   public void setParameterArray(ParameterType[] parameterArray) {
      this.check_orphaned();
      this.arraySetterHelper(parameterArray, PARAMETER$0);
   }

   public void setParameterArray(int i, ParameterType parameter) {
      this.generatedSetterHelperImpl(parameter, PARAMETER$0, i, (short)2);
   }

   public ParameterType insertNewParameter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParameterType target = null;
         target = (ParameterType)this.get_store().insert_element_user(PARAMETER$0, i);
         return target;
      }
   }

   public ParameterType addNewParameter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParameterType target = null;
         target = (ParameterType)this.get_store().add_element_user(PARAMETER$0);
         return target;
      }
   }

   public void removeParameter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARAMETER$0, i);
      }
   }
}
