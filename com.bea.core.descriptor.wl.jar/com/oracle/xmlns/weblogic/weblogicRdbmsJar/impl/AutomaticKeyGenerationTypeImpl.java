package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.AutomaticKeyGenerationType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.GeneratorNameType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.GeneratorTypeType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TrueFalseType;
import com.sun.java.xml.ns.j2Ee.XsdPositiveIntegerType;
import javax.xml.namespace.QName;

public class AutomaticKeyGenerationTypeImpl extends XmlComplexContentImpl implements AutomaticKeyGenerationType {
   private static final long serialVersionUID = 1L;
   private static final QName GENERATORTYPE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "generator-type");
   private static final QName GENERATORNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "generator-name");
   private static final QName KEYCACHESIZE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "key-cache-size");
   private static final QName SELECTFIRSTSEQUENCEKEYBEFOREUPDATE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "select-first-sequence-key-before-update");
   private static final QName ID$8 = new QName("", "id");

   public AutomaticKeyGenerationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public GeneratorTypeType getGeneratorType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GeneratorTypeType target = null;
         target = (GeneratorTypeType)this.get_store().find_element_user(GENERATORTYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setGeneratorType(GeneratorTypeType generatorType) {
      this.generatedSetterHelperImpl(generatorType, GENERATORTYPE$0, 0, (short)1);
   }

   public GeneratorTypeType addNewGeneratorType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GeneratorTypeType target = null;
         target = (GeneratorTypeType)this.get_store().add_element_user(GENERATORTYPE$0);
         return target;
      }
   }

   public GeneratorNameType getGeneratorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GeneratorNameType target = null;
         target = (GeneratorNameType)this.get_store().find_element_user(GENERATORNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetGeneratorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GENERATORNAME$2) != 0;
      }
   }

   public void setGeneratorName(GeneratorNameType generatorName) {
      this.generatedSetterHelperImpl(generatorName, GENERATORNAME$2, 0, (short)1);
   }

   public GeneratorNameType addNewGeneratorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GeneratorNameType target = null;
         target = (GeneratorNameType)this.get_store().add_element_user(GENERATORNAME$2);
         return target;
      }
   }

   public void unsetGeneratorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GENERATORNAME$2, 0);
      }
   }

   public XsdPositiveIntegerType getKeyCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(KEYCACHESIZE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetKeyCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KEYCACHESIZE$4) != 0;
      }
   }

   public void setKeyCacheSize(XsdPositiveIntegerType keyCacheSize) {
      this.generatedSetterHelperImpl(keyCacheSize, KEYCACHESIZE$4, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewKeyCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(KEYCACHESIZE$4);
         return target;
      }
   }

   public void unsetKeyCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KEYCACHESIZE$4, 0);
      }
   }

   public TrueFalseType getSelectFirstSequenceKeyBeforeUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SELECTFIRSTSEQUENCEKEYBEFOREUPDATE$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSelectFirstSequenceKeyBeforeUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SELECTFIRSTSEQUENCEKEYBEFOREUPDATE$6) != 0;
      }
   }

   public void setSelectFirstSequenceKeyBeforeUpdate(TrueFalseType selectFirstSequenceKeyBeforeUpdate) {
      this.generatedSetterHelperImpl(selectFirstSequenceKeyBeforeUpdate, SELECTFIRSTSEQUENCEKEYBEFOREUPDATE$6, 0, (short)1);
   }

   public TrueFalseType addNewSelectFirstSequenceKeyBeforeUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SELECTFIRSTSEQUENCEKEYBEFOREUPDATE$6);
         return target;
      }
   }

   public void unsetSelectFirstSequenceKeyBeforeUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SELECTFIRSTSEQUENCEKEYBEFOREUPDATE$6, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
