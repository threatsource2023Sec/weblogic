package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AdminObjectGroupDocument;
import com.bea.connector.monitoring1Dot0.AdminObjectsDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AdminObjectsDocumentImpl extends XmlComplexContentImpl implements AdminObjectsDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ADMINOBJECTS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-objects");

   public AdminObjectsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public AdminObjectsDocument.AdminObjects getAdminObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectsDocument.AdminObjects target = null;
         target = (AdminObjectsDocument.AdminObjects)this.get_store().find_element_user(ADMINOBJECTS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAdminObjects(AdminObjectsDocument.AdminObjects adminObjects) {
      this.generatedSetterHelperImpl(adminObjects, ADMINOBJECTS$0, 0, (short)1);
   }

   public AdminObjectsDocument.AdminObjects addNewAdminObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectsDocument.AdminObjects target = null;
         target = (AdminObjectsDocument.AdminObjects)this.get_store().add_element_user(ADMINOBJECTS$0);
         return target;
      }
   }

   public static class AdminObjectsImpl extends XmlComplexContentImpl implements AdminObjectsDocument.AdminObjects {
      private static final long serialVersionUID = 1L;
      private static final QName ADMINOBJECTGROUP$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-object-group");

      public AdminObjectsImpl(SchemaType sType) {
         super(sType);
      }

      public AdminObjectGroupDocument.AdminObjectGroup[] getAdminObjectGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(ADMINOBJECTGROUP$0, targetList);
            AdminObjectGroupDocument.AdminObjectGroup[] result = new AdminObjectGroupDocument.AdminObjectGroup[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public AdminObjectGroupDocument.AdminObjectGroup getAdminObjectGroupArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AdminObjectGroupDocument.AdminObjectGroup target = null;
            target = (AdminObjectGroupDocument.AdminObjectGroup)this.get_store().find_element_user(ADMINOBJECTGROUP$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfAdminObjectGroupArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ADMINOBJECTGROUP$0);
         }
      }

      public void setAdminObjectGroupArray(AdminObjectGroupDocument.AdminObjectGroup[] adminObjectGroupArray) {
         this.check_orphaned();
         this.arraySetterHelper(adminObjectGroupArray, ADMINOBJECTGROUP$0);
      }

      public void setAdminObjectGroupArray(int i, AdminObjectGroupDocument.AdminObjectGroup adminObjectGroup) {
         this.generatedSetterHelperImpl(adminObjectGroup, ADMINOBJECTGROUP$0, i, (short)2);
      }

      public AdminObjectGroupDocument.AdminObjectGroup insertNewAdminObjectGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AdminObjectGroupDocument.AdminObjectGroup target = null;
            target = (AdminObjectGroupDocument.AdminObjectGroup)this.get_store().insert_element_user(ADMINOBJECTGROUP$0, i);
            return target;
         }
      }

      public AdminObjectGroupDocument.AdminObjectGroup addNewAdminObjectGroup() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AdminObjectGroupDocument.AdminObjectGroup target = null;
            target = (AdminObjectGroupDocument.AdminObjectGroup)this.get_store().add_element_user(ADMINOBJECTGROUP$0);
            return target;
         }
      }

      public void removeAdminObjectGroup(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ADMINOBJECTGROUP$0, i);
         }
      }
   }
}
