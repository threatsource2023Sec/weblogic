package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.BindingProperty;
import com.bea.ns.staxb.bindingConfig.x90.JavaClassName;
import com.bea.ns.staxb.bindingConfig.x90.JavaFieldName;
import com.bea.ns.staxb.bindingConfig.x90.JavaInstanceFactory;
import com.bea.ns.staxb.bindingConfig.x90.JavaMethodName;
import com.bea.ns.staxb.bindingConfig.x90.XmlSignature;
import com.bea.xbean.values.JavaIntHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import javax.xml.namespace.QName;

public class BindingPropertyImpl extends XmlComplexContentImpl implements BindingProperty {
   private static final long serialVersionUID = 1L;
   private static final QName XMLCOMPONENT$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "xmlcomponent");
   private static final QName JAVATYPE$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "javatype");
   private static final QName GETTER$4 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "getter");
   private static final QName SETTER$6 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "setter");
   private static final QName ISSETTER$8 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "issetter");
   private static final QName FIELD$10 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "field");
   private static final QName STATIC$12 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "static");
   private static final QName COLLECTION$14 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "collection");
   private static final QName FACTORY$16 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "factory");
   private static final QName CONSTRUCTORPARAMETERINDEX$18 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "constructor-parameter-index");

   public BindingPropertyImpl(SchemaType sType) {
      super(sType);
   }

   public String getXmlcomponent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLCOMPONENT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlSignature xgetXmlcomponent() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlSignature target = null;
         target = (XmlSignature)this.get_store().find_element_user(XMLCOMPONENT$0, 0);
         return target;
      }
   }

   public void setXmlcomponent(String xmlcomponent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(XMLCOMPONENT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(XMLCOMPONENT$0);
         }

         target.setStringValue(xmlcomponent);
      }
   }

   public void xsetXmlcomponent(XmlSignature xmlcomponent) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlSignature target = null;
         target = (XmlSignature)this.get_store().find_element_user(XMLCOMPONENT$0, 0);
         if (target == null) {
            target = (XmlSignature)this.get_store().add_element_user(XMLCOMPONENT$0);
         }

         target.set(xmlcomponent);
      }
   }

   public String getJavatype() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JAVATYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public JavaClassName xgetJavatype() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(JAVATYPE$2, 0);
         return target;
      }
   }

   public void setJavatype(String javatype) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JAVATYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JAVATYPE$2);
         }

         target.setStringValue(javatype);
      }
   }

   public void xsetJavatype(JavaClassName javatype) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(JAVATYPE$2, 0);
         if (target == null) {
            target = (JavaClassName)this.get_store().add_element_user(JAVATYPE$2);
         }

         target.set(javatype);
      }
   }

   public JavaMethodName getGetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().find_element_user(GETTER$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GETTER$4) != 0;
      }
   }

   public void setGetter(JavaMethodName getter) {
      this.generatedSetterHelperImpl(getter, GETTER$4, 0, (short)1);
   }

   public JavaMethodName addNewGetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().add_element_user(GETTER$4);
         return target;
      }
   }

   public void unsetGetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GETTER$4, 0);
      }
   }

   public JavaMethodName getSetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().find_element_user(SETTER$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SETTER$6) != 0;
      }
   }

   public void setSetter(JavaMethodName setter) {
      this.generatedSetterHelperImpl(setter, SETTER$6, 0, (short)1);
   }

   public JavaMethodName addNewSetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().add_element_user(SETTER$6);
         return target;
      }
   }

   public void unsetSetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SETTER$6, 0);
      }
   }

   public JavaMethodName getIssetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().find_element_user(ISSETTER$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIssetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ISSETTER$8) != 0;
      }
   }

   public void setIssetter(JavaMethodName issetter) {
      this.generatedSetterHelperImpl(issetter, ISSETTER$8, 0, (short)1);
   }

   public JavaMethodName addNewIssetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaMethodName target = null;
         target = (JavaMethodName)this.get_store().add_element_user(ISSETTER$8);
         return target;
      }
   }

   public void unsetIssetter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ISSETTER$8, 0);
      }
   }

   public String getField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIELD$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public JavaFieldName xgetField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaFieldName target = null;
         target = (JavaFieldName)this.get_store().find_element_user(FIELD$10, 0);
         return target;
      }
   }

   public boolean isSetField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIELD$10) != 0;
      }
   }

   public void setField(String field) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIELD$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FIELD$10);
         }

         target.setStringValue(field);
      }
   }

   public void xsetField(JavaFieldName field) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaFieldName target = null;
         target = (JavaFieldName)this.get_store().find_element_user(FIELD$10, 0);
         if (target == null) {
            target = (JavaFieldName)this.get_store().add_element_user(FIELD$10);
         }

         target.set(field);
      }
   }

   public void unsetField() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIELD$10, 0);
      }
   }

   public String getStatic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATIC$12, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public JavaFieldName xgetStatic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaFieldName target = null;
         target = (JavaFieldName)this.get_store().find_element_user(STATIC$12, 0);
         return target;
      }
   }

   public boolean isSetStatic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATIC$12) != 0;
      }
   }

   public void setStatic(String xstatic) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATIC$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATIC$12);
         }

         target.setStringValue(xstatic);
      }
   }

   public void xsetStatic(JavaFieldName xstatic) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaFieldName target = null;
         target = (JavaFieldName)this.get_store().find_element_user(STATIC$12, 0);
         if (target == null) {
            target = (JavaFieldName)this.get_store().add_element_user(STATIC$12);
         }

         target.set(xstatic);
      }
   }

   public void unsetStatic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATIC$12, 0);
      }
   }

   public String getCollection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COLLECTION$14, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public JavaClassName xgetCollection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(COLLECTION$14, 0);
         return target;
      }
   }

   public boolean isSetCollection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COLLECTION$14) != 0;
      }
   }

   public void setCollection(String collection) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COLLECTION$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COLLECTION$14);
         }

         target.setStringValue(collection);
      }
   }

   public void xsetCollection(JavaClassName collection) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaClassName target = null;
         target = (JavaClassName)this.get_store().find_element_user(COLLECTION$14, 0);
         if (target == null) {
            target = (JavaClassName)this.get_store().add_element_user(COLLECTION$14);
         }

         target.set(collection);
      }
   }

   public void unsetCollection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COLLECTION$14, 0);
      }
   }

   public JavaInstanceFactory getFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaInstanceFactory target = null;
         target = (JavaInstanceFactory)this.get_store().find_element_user(FACTORY$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FACTORY$16) != 0;
      }
   }

   public void setFactory(JavaInstanceFactory factory) {
      this.generatedSetterHelperImpl(factory, FACTORY$16, 0, (short)1);
   }

   public JavaInstanceFactory addNewFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JavaInstanceFactory target = null;
         target = (JavaInstanceFactory)this.get_store().add_element_user(FACTORY$16);
         return target;
      }
   }

   public void unsetFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FACTORY$16, 0);
      }
   }

   public int getConstructorParameterIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSTRUCTORPARAMETERINDEX$18, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public BindingProperty.ConstructorParameterIndex xgetConstructorParameterIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BindingProperty.ConstructorParameterIndex target = null;
         target = (BindingProperty.ConstructorParameterIndex)this.get_store().find_element_user(CONSTRUCTORPARAMETERINDEX$18, 0);
         return target;
      }
   }

   public boolean isSetConstructorParameterIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONSTRUCTORPARAMETERINDEX$18) != 0;
      }
   }

   public void setConstructorParameterIndex(int constructorParameterIndex) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSTRUCTORPARAMETERINDEX$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONSTRUCTORPARAMETERINDEX$18);
         }

         target.setIntValue(constructorParameterIndex);
      }
   }

   public void xsetConstructorParameterIndex(BindingProperty.ConstructorParameterIndex constructorParameterIndex) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BindingProperty.ConstructorParameterIndex target = null;
         target = (BindingProperty.ConstructorParameterIndex)this.get_store().find_element_user(CONSTRUCTORPARAMETERINDEX$18, 0);
         if (target == null) {
            target = (BindingProperty.ConstructorParameterIndex)this.get_store().add_element_user(CONSTRUCTORPARAMETERINDEX$18);
         }

         target.set(constructorParameterIndex);
      }
   }

   public void unsetConstructorParameterIndex() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONSTRUCTORPARAMETERINDEX$18, 0);
      }
   }

   public static class ConstructorParameterIndexImpl extends JavaIntHolderEx implements BindingProperty.ConstructorParameterIndex {
      private static final long serialVersionUID = 1L;

      public ConstructorParameterIndexImpl(SchemaType sType) {
         super(sType, false);
      }

      protected ConstructorParameterIndexImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
