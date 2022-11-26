package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.InitMethodType;
import org.jcp.xmlns.xml.ns.javaee.NamedMethodType;

public class InitMethodTypeImpl extends XmlComplexContentImpl implements InitMethodType {
   private static final long serialVersionUID = 1L;
   private static final QName CREATEMETHOD$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "create-method");
   private static final QName BEANMETHOD$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "bean-method");
   private static final QName ID$4 = new QName("", "id");

   public InitMethodTypeImpl(SchemaType sType) {
      super(sType);
   }

   public NamedMethodType getCreateMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(CREATEMETHOD$0, 0);
         return target == null ? null : target;
      }
   }

   public void setCreateMethod(NamedMethodType createMethod) {
      this.generatedSetterHelperImpl(createMethod, CREATEMETHOD$0, 0, (short)1);
   }

   public NamedMethodType addNewCreateMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(CREATEMETHOD$0);
         return target;
      }
   }

   public NamedMethodType getBeanMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().find_element_user(BEANMETHOD$2, 0);
         return target == null ? null : target;
      }
   }

   public void setBeanMethod(NamedMethodType beanMethod) {
      this.generatedSetterHelperImpl(beanMethod, BEANMETHOD$2, 0, (short)1);
   }

   public NamedMethodType addNewBeanMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamedMethodType target = null;
         target = (NamedMethodType)this.get_store().add_element_user(BEANMETHOD$2);
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
