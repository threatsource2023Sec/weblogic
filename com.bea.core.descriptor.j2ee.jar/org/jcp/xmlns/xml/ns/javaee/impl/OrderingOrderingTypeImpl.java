package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.JavaIdentifierType;
import org.jcp.xmlns.xml.ns.javaee.OrderingOrderingType;
import org.jcp.xmlns.xml.ns.javaee.OrderingOthersType;

public class OrderingOrderingTypeImpl extends XmlComplexContentImpl implements OrderingOrderingType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "name");
   private static final QName OTHERS$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "others");

   public OrderingOrderingTypeImpl(SchemaType sType) {
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

   public OrderingOthersType getOthers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOthersType target = null;
         target = (OrderingOthersType)this.get_store().find_element_user(OTHERS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetOthers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OTHERS$2) != 0;
      }
   }

   public void setOthers(OrderingOthersType others) {
      this.generatedSetterHelperImpl(others, OTHERS$2, 0, (short)1);
   }

   public OrderingOthersType addNewOthers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrderingOthersType target = null;
         target = (OrderingOthersType)this.get_store().add_element_user(OTHERS$2);
         return target;
      }
   }

   public void unsetOthers() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OTHERS$2, 0);
      }
   }
}
