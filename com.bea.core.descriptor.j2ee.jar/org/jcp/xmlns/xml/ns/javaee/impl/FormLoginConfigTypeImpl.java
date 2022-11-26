package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.FormLoginConfigType;
import org.jcp.xmlns.xml.ns.javaee.WarPathType;

public class FormLoginConfigTypeImpl extends XmlComplexContentImpl implements FormLoginConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName FORMLOGINPAGE$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "form-login-page");
   private static final QName FORMERRORPAGE$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "form-error-page");
   private static final QName ID$4 = new QName("", "id");

   public FormLoginConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public WarPathType getFormLoginPage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WarPathType target = null;
         target = (WarPathType)this.get_store().find_element_user(FORMLOGINPAGE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setFormLoginPage(WarPathType formLoginPage) {
      this.generatedSetterHelperImpl(formLoginPage, FORMLOGINPAGE$0, 0, (short)1);
   }

   public WarPathType addNewFormLoginPage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WarPathType target = null;
         target = (WarPathType)this.get_store().add_element_user(FORMLOGINPAGE$0);
         return target;
      }
   }

   public WarPathType getFormErrorPage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WarPathType target = null;
         target = (WarPathType)this.get_store().find_element_user(FORMERRORPAGE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setFormErrorPage(WarPathType formErrorPage) {
      this.generatedSetterHelperImpl(formErrorPage, FORMERRORPAGE$2, 0, (short)1);
   }

   public WarPathType addNewFormErrorPage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WarPathType target = null;
         target = (WarPathType)this.get_store().add_element_user(FORMERRORPAGE$2);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
