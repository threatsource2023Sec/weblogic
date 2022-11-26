package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.ByNameBean;
import com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty;
import com.bea.ns.staxb.bindingConfig.x90.QnameProperty;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ByNameBeanImpl extends BindingTypeImpl implements ByNameBean {
   private static final long serialVersionUID = 1L;
   private static final QName ANYPROPERTY$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "any-property");
   private static final QName ANYATTRIBUTEPROPERTY$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "anyAttribute-property");
   private static final QName QNAMEPROPERTY$4 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "qname-property");

   public ByNameBeanImpl(SchemaType sType) {
      super(sType);
   }

   public GenericXmlProperty getAnyProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GenericXmlProperty target = null;
         target = (GenericXmlProperty)this.get_store().find_element_user(ANYPROPERTY$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnyProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANYPROPERTY$0) != 0;
      }
   }

   public void setAnyProperty(GenericXmlProperty anyProperty) {
      this.generatedSetterHelperImpl(anyProperty, ANYPROPERTY$0, 0, (short)1);
   }

   public GenericXmlProperty addNewAnyProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GenericXmlProperty target = null;
         target = (GenericXmlProperty)this.get_store().add_element_user(ANYPROPERTY$0);
         return target;
      }
   }

   public void unsetAnyProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANYPROPERTY$0, 0);
      }
   }

   public GenericXmlProperty getAnyAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GenericXmlProperty target = null;
         target = (GenericXmlProperty)this.get_store().find_element_user(ANYATTRIBUTEPROPERTY$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnyAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANYATTRIBUTEPROPERTY$2) != 0;
      }
   }

   public void setAnyAttributeProperty(GenericXmlProperty anyAttributeProperty) {
      this.generatedSetterHelperImpl(anyAttributeProperty, ANYATTRIBUTEPROPERTY$2, 0, (short)1);
   }

   public GenericXmlProperty addNewAnyAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GenericXmlProperty target = null;
         target = (GenericXmlProperty)this.get_store().add_element_user(ANYATTRIBUTEPROPERTY$2);
         return target;
      }
   }

   public void unsetAnyAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANYATTRIBUTEPROPERTY$2, 0);
      }
   }

   public QnameProperty[] getQnamePropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(QNAMEPROPERTY$4, targetList);
         QnameProperty[] result = new QnameProperty[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public QnameProperty getQnamePropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnameProperty target = null;
         target = (QnameProperty)this.get_store().find_element_user(QNAMEPROPERTY$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfQnamePropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QNAMEPROPERTY$4);
      }
   }

   public void setQnamePropertyArray(QnameProperty[] qnamePropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(qnamePropertyArray, QNAMEPROPERTY$4);
   }

   public void setQnamePropertyArray(int i, QnameProperty qnameProperty) {
      this.generatedSetterHelperImpl(qnameProperty, QNAMEPROPERTY$4, i, (short)2);
   }

   public QnameProperty insertNewQnameProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnameProperty target = null;
         target = (QnameProperty)this.get_store().insert_element_user(QNAMEPROPERTY$4, i);
         return target;
      }
   }

   public QnameProperty addNewQnameProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnameProperty target = null;
         target = (QnameProperty)this.get_store().add_element_user(QNAMEPROPERTY$4);
         return target;
      }
   }

   public void removeQnameProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QNAMEPROPERTY$4, i);
      }
   }
}
