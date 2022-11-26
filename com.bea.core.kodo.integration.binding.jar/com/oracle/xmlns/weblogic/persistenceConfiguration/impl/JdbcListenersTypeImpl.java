package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomJdbcListenerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JdbcListenersType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class JdbcListenersTypeImpl extends XmlComplexContentImpl implements JdbcListenersType {
   private static final long serialVersionUID = 1L;
   private static final QName CUSTOMJDBCLISTENER$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-jdbc-listener");

   public JdbcListenersTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CustomJdbcListenerType[] getCustomJdbcListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CUSTOMJDBCLISTENER$0, targetList);
         CustomJdbcListenerType[] result = new CustomJdbcListenerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CustomJdbcListenerType getCustomJdbcListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomJdbcListenerType target = null;
         target = (CustomJdbcListenerType)this.get_store().find_element_user(CUSTOMJDBCLISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilCustomJdbcListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomJdbcListenerType target = null;
         target = (CustomJdbcListenerType)this.get_store().find_element_user(CUSTOMJDBCLISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfCustomJdbcListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMJDBCLISTENER$0);
      }
   }

   public void setCustomJdbcListenerArray(CustomJdbcListenerType[] customJdbcListenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(customJdbcListenerArray, CUSTOMJDBCLISTENER$0);
   }

   public void setCustomJdbcListenerArray(int i, CustomJdbcListenerType customJdbcListener) {
      this.generatedSetterHelperImpl(customJdbcListener, CUSTOMJDBCLISTENER$0, i, (short)2);
   }

   public void setNilCustomJdbcListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomJdbcListenerType target = null;
         target = (CustomJdbcListenerType)this.get_store().find_element_user(CUSTOMJDBCLISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public CustomJdbcListenerType insertNewCustomJdbcListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomJdbcListenerType target = null;
         target = (CustomJdbcListenerType)this.get_store().insert_element_user(CUSTOMJDBCLISTENER$0, i);
         return target;
      }
   }

   public CustomJdbcListenerType addNewCustomJdbcListener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomJdbcListenerType target = null;
         target = (CustomJdbcListenerType)this.get_store().add_element_user(CUSTOMJDBCLISTENER$0);
         return target;
      }
   }

   public void removeCustomJdbcListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMJDBCLISTENER$0, i);
      }
   }
}
