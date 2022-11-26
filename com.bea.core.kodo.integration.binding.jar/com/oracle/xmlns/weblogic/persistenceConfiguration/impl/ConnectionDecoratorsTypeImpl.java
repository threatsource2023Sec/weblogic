package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ConnectionDecoratorsType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomConnectionDecoratorType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectionDecoratorsTypeImpl extends XmlComplexContentImpl implements ConnectionDecoratorsType {
   private static final long serialVersionUID = 1L;
   private static final QName CUSTOMCONNECTIONDECORATOR$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-connection-decorator");

   public ConnectionDecoratorsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CustomConnectionDecoratorType[] getCustomConnectionDecoratorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CUSTOMCONNECTIONDECORATOR$0, targetList);
         CustomConnectionDecoratorType[] result = new CustomConnectionDecoratorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CustomConnectionDecoratorType getCustomConnectionDecoratorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomConnectionDecoratorType target = null;
         target = (CustomConnectionDecoratorType)this.get_store().find_element_user(CUSTOMCONNECTIONDECORATOR$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilCustomConnectionDecoratorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomConnectionDecoratorType target = null;
         target = (CustomConnectionDecoratorType)this.get_store().find_element_user(CUSTOMCONNECTIONDECORATOR$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfCustomConnectionDecoratorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMCONNECTIONDECORATOR$0);
      }
   }

   public void setCustomConnectionDecoratorArray(CustomConnectionDecoratorType[] customConnectionDecoratorArray) {
      this.check_orphaned();
      this.arraySetterHelper(customConnectionDecoratorArray, CUSTOMCONNECTIONDECORATOR$0);
   }

   public void setCustomConnectionDecoratorArray(int i, CustomConnectionDecoratorType customConnectionDecorator) {
      this.generatedSetterHelperImpl(customConnectionDecorator, CUSTOMCONNECTIONDECORATOR$0, i, (short)2);
   }

   public void setNilCustomConnectionDecoratorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomConnectionDecoratorType target = null;
         target = (CustomConnectionDecoratorType)this.get_store().find_element_user(CUSTOMCONNECTIONDECORATOR$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public CustomConnectionDecoratorType insertNewCustomConnectionDecorator(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomConnectionDecoratorType target = null;
         target = (CustomConnectionDecoratorType)this.get_store().insert_element_user(CUSTOMCONNECTIONDECORATOR$0, i);
         return target;
      }
   }

   public CustomConnectionDecoratorType addNewCustomConnectionDecorator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomConnectionDecoratorType target = null;
         target = (CustomConnectionDecoratorType)this.get_store().add_element_user(CUSTOMCONNECTIONDECORATOR$0);
         return target;
      }
   }

   public void removeCustomConnectionDecorator(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMCONNECTIONDECORATOR$0, i);
      }
   }
}
