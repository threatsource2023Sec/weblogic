package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.GenericXmlProperty;
import com.bea.ns.staxb.bindingConfig.x90.QnameProperty;
import com.bea.ns.staxb.bindingConfig.x90.SimpleContentBean;
import com.bea.ns.staxb.bindingConfig.x90.SimpleContentProperty;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SimpleContentBeanImpl extends BindingTypeImpl implements SimpleContentBean {
   private static final long serialVersionUID = 1L;
   private static final QName ANYATTRIBUTEPROPERTY$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "anyAttribute-property");
   private static final QName SIMPLECONTENTPROPERTY$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "simple-content-property");
   private static final QName ATTRIBUTEPROPERTY$4 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "attribute-property");

   public SimpleContentBeanImpl(SchemaType sType) {
      super(sType);
   }

   public GenericXmlProperty getAnyAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GenericXmlProperty target = null;
         target = (GenericXmlProperty)this.get_store().find_element_user(ANYATTRIBUTEPROPERTY$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAnyAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ANYATTRIBUTEPROPERTY$0) != 0;
      }
   }

   public void setAnyAttributeProperty(GenericXmlProperty anyAttributeProperty) {
      this.generatedSetterHelperImpl(anyAttributeProperty, ANYATTRIBUTEPROPERTY$0, 0, (short)1);
   }

   public GenericXmlProperty addNewAnyAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GenericXmlProperty target = null;
         target = (GenericXmlProperty)this.get_store().add_element_user(ANYATTRIBUTEPROPERTY$0);
         return target;
      }
   }

   public void unsetAnyAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ANYATTRIBUTEPROPERTY$0, 0);
      }
   }

   public SimpleContentProperty getSimpleContentProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleContentProperty target = null;
         target = (SimpleContentProperty)this.get_store().find_element_user(SIMPLECONTENTPROPERTY$2, 0);
         return target == null ? null : target;
      }
   }

   public void setSimpleContentProperty(SimpleContentProperty simpleContentProperty) {
      this.generatedSetterHelperImpl(simpleContentProperty, SIMPLECONTENTPROPERTY$2, 0, (short)1);
   }

   public SimpleContentProperty addNewSimpleContentProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleContentProperty target = null;
         target = (SimpleContentProperty)this.get_store().add_element_user(SIMPLECONTENTPROPERTY$2);
         return target;
      }
   }

   public QnameProperty[] getAttributePropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ATTRIBUTEPROPERTY$4, targetList);
         QnameProperty[] result = new QnameProperty[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public QnameProperty getAttributePropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnameProperty target = null;
         target = (QnameProperty)this.get_store().find_element_user(ATTRIBUTEPROPERTY$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAttributePropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTRIBUTEPROPERTY$4);
      }
   }

   public void setAttributePropertyArray(QnameProperty[] attributePropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(attributePropertyArray, ATTRIBUTEPROPERTY$4);
   }

   public void setAttributePropertyArray(int i, QnameProperty attributeProperty) {
      this.generatedSetterHelperImpl(attributeProperty, ATTRIBUTEPROPERTY$4, i, (short)2);
   }

   public QnameProperty insertNewAttributeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnameProperty target = null;
         target = (QnameProperty)this.get_store().insert_element_user(ATTRIBUTEPROPERTY$4, i);
         return target;
      }
   }

   public QnameProperty addNewAttributeProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QnameProperty target = null;
         target = (QnameProperty)this.get_store().add_element_user(ATTRIBUTEPROPERTY$4);
         return target;
      }
   }

   public void removeAttributeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTRIBUTEPROPERTY$4, i);
      }
   }
}
