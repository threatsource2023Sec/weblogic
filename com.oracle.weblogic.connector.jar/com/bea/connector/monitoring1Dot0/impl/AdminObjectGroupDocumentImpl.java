package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AdminObjectGroupDocument;
import com.bea.connector.monitoring1Dot0.AdminObjectInstanceDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AdminObjectGroupDocumentImpl extends XmlComplexContentImpl implements AdminObjectGroupDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ADMINOBJECTGROUP$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-object-group");

   public AdminObjectGroupDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public AdminObjectGroupDocument.AdminObjectGroup getAdminObjectGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectGroupDocument.AdminObjectGroup target = null;
         target = (AdminObjectGroupDocument.AdminObjectGroup)this.get_store().find_element_user(ADMINOBJECTGROUP$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAdminObjectGroup(AdminObjectGroupDocument.AdminObjectGroup adminObjectGroup) {
      this.generatedSetterHelperImpl(adminObjectGroup, ADMINOBJECTGROUP$0, 0, (short)1);
   }

   public AdminObjectGroupDocument.AdminObjectGroup addNewAdminObjectGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectGroupDocument.AdminObjectGroup target = null;
         target = (AdminObjectGroupDocument.AdminObjectGroup)this.get_store().add_element_user(ADMINOBJECTGROUP$0);
         return target;
      }
   }

   public static class AdminObjectGroupImpl extends XmlComplexContentImpl implements AdminObjectGroupDocument.AdminObjectGroup {
      private static final long serialVersionUID = 1L;
      private static final QName ADMINOBJECTINTERFACE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-object-interface");
      private static final QName ADMINOBJECTCLASS$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-object-class");
      private static final QName ADMINOBJECTINSTANCE$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-object-instance");

      public AdminObjectGroupImpl(SchemaType sType) {
         super(sType);
      }

      public String getAdminObjectInterface() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetAdminObjectInterface() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
            return target;
         }
      }

      public void setAdminObjectInterface(String adminObjectInterface) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(ADMINOBJECTINTERFACE$0);
            }

            target.setStringValue(adminObjectInterface);
         }
      }

      public void xsetAdminObjectInterface(XmlString adminObjectInterface) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(ADMINOBJECTINTERFACE$0);
            }

            target.set(adminObjectInterface);
         }
      }

      public String getAdminObjectClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ADMINOBJECTCLASS$2, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetAdminObjectClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(ADMINOBJECTCLASS$2, 0);
            return target;
         }
      }

      public void setAdminObjectClass(String adminObjectClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ADMINOBJECTCLASS$2, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(ADMINOBJECTCLASS$2);
            }

            target.setStringValue(adminObjectClass);
         }
      }

      public void xsetAdminObjectClass(XmlString adminObjectClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(ADMINOBJECTCLASS$2, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(ADMINOBJECTCLASS$2);
            }

            target.set(adminObjectClass);
         }
      }

      public AdminObjectInstanceDocument.AdminObjectInstance[] getAdminObjectInstanceArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(ADMINOBJECTINSTANCE$4, targetList);
            AdminObjectInstanceDocument.AdminObjectInstance[] result = new AdminObjectInstanceDocument.AdminObjectInstance[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public AdminObjectInstanceDocument.AdminObjectInstance getAdminObjectInstanceArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AdminObjectInstanceDocument.AdminObjectInstance target = null;
            target = (AdminObjectInstanceDocument.AdminObjectInstance)this.get_store().find_element_user(ADMINOBJECTINSTANCE$4, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfAdminObjectInstanceArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ADMINOBJECTINSTANCE$4);
         }
      }

      public void setAdminObjectInstanceArray(AdminObjectInstanceDocument.AdminObjectInstance[] adminObjectInstanceArray) {
         this.check_orphaned();
         this.arraySetterHelper(adminObjectInstanceArray, ADMINOBJECTINSTANCE$4);
      }

      public void setAdminObjectInstanceArray(int i, AdminObjectInstanceDocument.AdminObjectInstance adminObjectInstance) {
         this.generatedSetterHelperImpl(adminObjectInstance, ADMINOBJECTINSTANCE$4, i, (short)2);
      }

      public AdminObjectInstanceDocument.AdminObjectInstance insertNewAdminObjectInstance(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AdminObjectInstanceDocument.AdminObjectInstance target = null;
            target = (AdminObjectInstanceDocument.AdminObjectInstance)this.get_store().insert_element_user(ADMINOBJECTINSTANCE$4, i);
            return target;
         }
      }

      public AdminObjectInstanceDocument.AdminObjectInstance addNewAdminObjectInstance() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            AdminObjectInstanceDocument.AdminObjectInstance target = null;
            target = (AdminObjectInstanceDocument.AdminObjectInstance)this.get_store().add_element_user(ADMINOBJECTINSTANCE$4);
            return target;
         }
      }

      public void removeAdminObjectInstance(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ADMINOBJECTINSTANCE$4, i);
         }
      }
   }
}
