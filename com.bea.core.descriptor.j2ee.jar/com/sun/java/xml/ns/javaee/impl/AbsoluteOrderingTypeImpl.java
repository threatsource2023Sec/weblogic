package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.AbsoluteOrderingType;
import com.sun.java.xml.ns.javaee.JavaIdentifierType;
import com.sun.java.xml.ns.javaee.OrderingOthersType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AbsoluteOrderingTypeImpl extends XmlComplexContentImpl implements AbsoluteOrderingType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://java.sun.com/xml/ns/javaee", "name");
   private static final QName OTHERS$2 = new QName("http://java.sun.com/xml/ns/javaee", "others");

   public AbsoluteOrderingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public JavaIdentifierType[] getNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NAME$0, targetList);
         JavaIdentifierType[] result = new JavaIdentifierType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public JavaIdentifierType getNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().find_element_user(NAME$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAME$0);
      }
   }

   public void setNameArray(JavaIdentifierType[] nameArray) {
      this.check_orphaned();
      this.arraySetterHelper(nameArray, NAME$0);
   }

   public void setNameArray(int i, JavaIdentifierType name) {
      this.generatedSetterHelperImpl(name, NAME$0, i, (short)2);
   }

   public JavaIdentifierType insertNewName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().insert_element_user(NAME$0, i);
         return target;
      }
   }

   public JavaIdentifierType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaIdentifierType target = null;
         target = (JavaIdentifierType)this.get_store().add_element_user(NAME$0);
         return target;
      }
   }

   public void removeName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NAME$0, i);
      }
   }

   public OrderingOthersType[] getOthersArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OTHERS$2, targetList);
         OrderingOthersType[] result = new OrderingOthersType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public OrderingOthersType getOthersArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOthersType target = null;
         target = (OrderingOthersType)this.get_store().find_element_user(OTHERS$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOthersArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OTHERS$2);
      }
   }

   public void setOthersArray(OrderingOthersType[] othersArray) {
      this.check_orphaned();
      this.arraySetterHelper(othersArray, OTHERS$2);
   }

   public void setOthersArray(int i, OrderingOthersType others) {
      this.generatedSetterHelperImpl(others, OTHERS$2, i, (short)2);
   }

   public OrderingOthersType insertNewOthers(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOthersType target = null;
         target = (OrderingOthersType)this.get_store().insert_element_user(OTHERS$2, i);
         return target;
      }
   }

   public OrderingOthersType addNewOthers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOthersType target = null;
         target = (OrderingOthersType)this.get_store().add_element_user(OTHERS$2);
         return target;
      }
   }

   public void removeOthers(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OTHERS$2, i);
      }
   }
}
