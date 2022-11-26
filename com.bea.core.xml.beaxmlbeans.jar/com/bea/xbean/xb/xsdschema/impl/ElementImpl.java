package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.AllNNI;
import com.bea.xbean.xb.xsdschema.BlockSet;
import com.bea.xbean.xb.xsdschema.DerivationSet;
import com.bea.xbean.xb.xsdschema.Element;
import com.bea.xbean.xb.xsdschema.FormChoice;
import com.bea.xbean.xb.xsdschema.Keybase;
import com.bea.xbean.xb.xsdschema.KeyrefDocument;
import com.bea.xbean.xb.xsdschema.LocalComplexType;
import com.bea.xbean.xb.xsdschema.LocalSimpleType;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlNCName;
import com.bea.xml.XmlNonNegativeInteger;
import com.bea.xml.XmlQName;
import com.bea.xml.XmlString;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ElementImpl extends AnnotatedImpl implements Element {
   private static final QName SIMPLETYPE$0 = new QName("http://www.w3.org/2001/XMLSchema", "simpleType");
   private static final QName COMPLEXTYPE$2 = new QName("http://www.w3.org/2001/XMLSchema", "complexType");
   private static final QName UNIQUE$4 = new QName("http://www.w3.org/2001/XMLSchema", "unique");
   private static final QName KEY$6 = new QName("http://www.w3.org/2001/XMLSchema", "key");
   private static final QName KEYREF$8 = new QName("http://www.w3.org/2001/XMLSchema", "keyref");
   private static final QName NAME$10 = new QName("", "name");
   private static final QName REF$12 = new QName("", "ref");
   private static final QName TYPE$14 = new QName("", "type");
   private static final QName SUBSTITUTIONGROUP$16 = new QName("", "substitutionGroup");
   private static final QName MINOCCURS$18 = new QName("", "minOccurs");
   private static final QName MAXOCCURS$20 = new QName("", "maxOccurs");
   private static final QName DEFAULT$22 = new QName("", "default");
   private static final QName FIXED$24 = new QName("", "fixed");
   private static final QName NILLABLE$26 = new QName("", "nillable");
   private static final QName ABSTRACT$28 = new QName("", "abstract");
   private static final QName FINAL$30 = new QName("", "final");
   private static final QName BLOCK$32 = new QName("", "block");
   private static final QName FORM$34 = new QName("", "form");

   public ElementImpl(SchemaType sType) {
      super(sType);
   }

   public LocalSimpleType getSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SIMPLETYPE$0) != 0;
      }
   }

   public void setSimpleType(LocalSimpleType simpleType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().find_element_user((QName)SIMPLETYPE$0, 0);
         if (target == null) {
            target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
         }

         target.set(simpleType);
      }
   }

   public LocalSimpleType addNewSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalSimpleType target = null;
         target = (LocalSimpleType)this.get_store().add_element_user(SIMPLETYPE$0);
         return target;
      }
   }

   public void unsetSimpleType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)SIMPLETYPE$0, 0);
      }
   }

   public LocalComplexType getComplexType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalComplexType target = null;
         target = (LocalComplexType)this.get_store().find_element_user((QName)COMPLEXTYPE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetComplexType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPLEXTYPE$2) != 0;
      }
   }

   public void setComplexType(LocalComplexType complexType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalComplexType target = null;
         target = (LocalComplexType)this.get_store().find_element_user((QName)COMPLEXTYPE$2, 0);
         if (target == null) {
            target = (LocalComplexType)this.get_store().add_element_user(COMPLEXTYPE$2);
         }

         target.set(complexType);
      }
   }

   public LocalComplexType addNewComplexType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalComplexType target = null;
         target = (LocalComplexType)this.get_store().add_element_user(COMPLEXTYPE$2);
         return target;
      }
   }

   public void unsetComplexType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)COMPLEXTYPE$2, 0);
      }
   }

   public Keybase[] getUniqueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)UNIQUE$4, targetList);
         Keybase[] result = new Keybase[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Keybase getUniqueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().find_element_user(UNIQUE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfUniqueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNIQUE$4);
      }
   }

   public void setUniqueArray(Keybase[] uniqueArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(uniqueArray, UNIQUE$4);
      }
   }

   public void setUniqueArray(int i, Keybase unique) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().find_element_user(UNIQUE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(unique);
         }
      }
   }

   public Keybase insertNewUnique(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().insert_element_user(UNIQUE$4, i);
         return target;
      }
   }

   public Keybase addNewUnique() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().add_element_user(UNIQUE$4);
         return target;
      }
   }

   public void removeUnique(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNIQUE$4, i);
      }
   }

   public Keybase[] getKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)KEY$6, targetList);
         Keybase[] result = new Keybase[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Keybase getKeyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().find_element_user(KEY$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KEY$6);
      }
   }

   public void setKeyArray(Keybase[] keyArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(keyArray, KEY$6);
      }
   }

   public void setKeyArray(int i, Keybase key) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().find_element_user(KEY$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(key);
         }
      }
   }

   public Keybase insertNewKey(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().insert_element_user(KEY$6, i);
         return target;
      }
   }

   public Keybase addNewKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Keybase target = null;
         target = (Keybase)this.get_store().add_element_user(KEY$6);
         return target;
      }
   }

   public void removeKey(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KEY$6, i);
      }
   }

   public KeyrefDocument.Keyref[] getKeyrefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)KEYREF$8, targetList);
         KeyrefDocument.Keyref[] result = new KeyrefDocument.Keyref[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public KeyrefDocument.Keyref getKeyrefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyrefDocument.Keyref target = null;
         target = (KeyrefDocument.Keyref)this.get_store().find_element_user(KEYREF$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfKeyrefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KEYREF$8);
      }
   }

   public void setKeyrefArray(KeyrefDocument.Keyref[] keyrefArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(keyrefArray, KEYREF$8);
      }
   }

   public void setKeyrefArray(int i, KeyrefDocument.Keyref keyref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyrefDocument.Keyref target = null;
         target = (KeyrefDocument.Keyref)this.get_store().find_element_user(KEYREF$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(keyref);
         }
      }
   }

   public KeyrefDocument.Keyref insertNewKeyref(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyrefDocument.Keyref target = null;
         target = (KeyrefDocument.Keyref)this.get_store().insert_element_user(KEYREF$8, i);
         return target;
      }
   }

   public KeyrefDocument.Keyref addNewKeyref() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeyrefDocument.Keyref target = null;
         target = (KeyrefDocument.Keyref)this.get_store().add_element_user(KEYREF$8);
         return target;
      }
   }

   public void removeKeyref(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KEYREF$8, i);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlNCName xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$10);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$10) != null;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$10);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlNCName name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNCName target = null;
         target = (XmlNCName)this.get_store().find_attribute_user(NAME$10);
         if (target == null) {
            target = (XmlNCName)this.get_store().add_attribute_user(NAME$10);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$10);
      }
   }

   public QName getRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$12);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$12);
         return target;
      }
   }

   public boolean isSetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(REF$12) != null;
      }
   }

   public void setRef(QName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(REF$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(REF$12);
         }

         target.setQNameValue(ref);
      }
   }

   public void xsetRef(XmlQName ref) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(REF$12);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(REF$12);
         }

         target.set(ref);
      }
   }

   public void unsetRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(REF$12);
      }
   }

   public QName getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPE$14);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(TYPE$14);
         return target;
      }
   }

   public boolean isSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(TYPE$14) != null;
      }
   }

   public void setType(QName type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TYPE$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TYPE$14);
         }

         target.setQNameValue(type);
      }
   }

   public void xsetType(XmlQName type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(TYPE$14);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(TYPE$14);
         }

         target.set(type);
      }
   }

   public void unsetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(TYPE$14);
      }
   }

   public QName getSubstitutionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SUBSTITUTIONGROUP$16);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetSubstitutionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(SUBSTITUTIONGROUP$16);
         return target;
      }
   }

   public boolean isSetSubstitutionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(SUBSTITUTIONGROUP$16) != null;
      }
   }

   public void setSubstitutionGroup(QName substitutionGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SUBSTITUTIONGROUP$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(SUBSTITUTIONGROUP$16);
         }

         target.setQNameValue(substitutionGroup);
      }
   }

   public void xsetSubstitutionGroup(XmlQName substitutionGroup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_attribute_user(SUBSTITUTIONGROUP$16);
         if (target == null) {
            target = (XmlQName)this.get_store().add_attribute_user(SUBSTITUTIONGROUP$16);
         }

         target.set(substitutionGroup);
      }
   }

   public void unsetSubstitutionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(SUBSTITUTIONGROUP$16);
      }
   }

   public BigInteger getMinOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MINOCCURS$18);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(MINOCCURS$18);
         }

         return target == null ? null : target.getBigIntegerValue();
      }
   }

   public XmlNonNegativeInteger xgetMinOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNonNegativeInteger target = null;
         target = (XmlNonNegativeInteger)this.get_store().find_attribute_user(MINOCCURS$18);
         if (target == null) {
            target = (XmlNonNegativeInteger)this.get_default_attribute_value(MINOCCURS$18);
         }

         return target;
      }
   }

   public boolean isSetMinOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(MINOCCURS$18) != null;
      }
   }

   public void setMinOccurs(BigInteger minOccurs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MINOCCURS$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(MINOCCURS$18);
         }

         target.setBigIntegerValue(minOccurs);
      }
   }

   public void xsetMinOccurs(XmlNonNegativeInteger minOccurs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNonNegativeInteger target = null;
         target = (XmlNonNegativeInteger)this.get_store().find_attribute_user(MINOCCURS$18);
         if (target == null) {
            target = (XmlNonNegativeInteger)this.get_store().add_attribute_user(MINOCCURS$18);
         }

         target.set(minOccurs);
      }
   }

   public void unsetMinOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(MINOCCURS$18);
      }
   }

   public Object getMaxOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MAXOCCURS$20);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(MAXOCCURS$20);
         }

         return target == null ? null : target.getObjectValue();
      }
   }

   public AllNNI xgetMaxOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AllNNI target = null;
         target = (AllNNI)this.get_store().find_attribute_user(MAXOCCURS$20);
         if (target == null) {
            target = (AllNNI)this.get_default_attribute_value(MAXOCCURS$20);
         }

         return target;
      }
   }

   public boolean isSetMaxOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(MAXOCCURS$20) != null;
      }
   }

   public void setMaxOccurs(Object maxOccurs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MAXOCCURS$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(MAXOCCURS$20);
         }

         target.setObjectValue(maxOccurs);
      }
   }

   public void xsetMaxOccurs(AllNNI maxOccurs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AllNNI target = null;
         target = (AllNNI)this.get_store().find_attribute_user(MAXOCCURS$20);
         if (target == null) {
            target = (AllNNI)this.get_store().add_attribute_user(MAXOCCURS$20);
         }

         target.set(maxOccurs);
      }
   }

   public void unsetMaxOccurs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(MAXOCCURS$20);
      }
   }

   public String getDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(DEFAULT$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(DEFAULT$22);
         return target;
      }
   }

   public boolean isSetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(DEFAULT$22) != null;
      }
   }

   public void setDefault(String xdefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(DEFAULT$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(DEFAULT$22);
         }

         target.setStringValue(xdefault);
      }
   }

   public void xsetDefault(XmlString xdefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(DEFAULT$22);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(DEFAULT$22);
         }

         target.set(xdefault);
      }
   }

   public void unsetDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(DEFAULT$22);
      }
   }

   public String getFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FIXED$24);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(FIXED$24);
         return target;
      }
   }

   public boolean isSetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FIXED$24) != null;
      }
   }

   public void setFixed(String fixed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FIXED$24);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FIXED$24);
         }

         target.setStringValue(fixed);
      }
   }

   public void xsetFixed(XmlString fixed) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(FIXED$24);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(FIXED$24);
         }

         target.set(fixed);
      }
   }

   public void unsetFixed() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FIXED$24);
      }
   }

   public boolean getNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NILLABLE$26);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(NILLABLE$26);
         }

         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(NILLABLE$26);
         if (target == null) {
            target = (XmlBoolean)this.get_default_attribute_value(NILLABLE$26);
         }

         return target;
      }
   }

   public boolean isSetNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NILLABLE$26) != null;
      }
   }

   public void setNillable(boolean nillable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NILLABLE$26);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NILLABLE$26);
         }

         target.setBooleanValue(nillable);
      }
   }

   public void xsetNillable(XmlBoolean nillable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(NILLABLE$26);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(NILLABLE$26);
         }

         target.set(nillable);
      }
   }

   public void unsetNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NILLABLE$26);
      }
   }

   public boolean getAbstract() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ABSTRACT$28);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(ABSTRACT$28);
         }

         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAbstract() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(ABSTRACT$28);
         if (target == null) {
            target = (XmlBoolean)this.get_default_attribute_value(ABSTRACT$28);
         }

         return target;
      }
   }

   public boolean isSetAbstract() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ABSTRACT$28) != null;
      }
   }

   public void setAbstract(boolean xabstract) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ABSTRACT$28);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ABSTRACT$28);
         }

         target.setBooleanValue(xabstract);
      }
   }

   public void xsetAbstract(XmlBoolean xabstract) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(ABSTRACT$28);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(ABSTRACT$28);
         }

         target.set(xabstract);
      }
   }

   public void unsetAbstract() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ABSTRACT$28);
      }
   }

   public Object getFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FINAL$30);
         return target == null ? null : target.getObjectValue();
      }
   }

   public DerivationSet xgetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerivationSet target = null;
         target = (DerivationSet)this.get_store().find_attribute_user(FINAL$30);
         return target;
      }
   }

   public boolean isSetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FINAL$30) != null;
      }
   }

   public void setFinal(Object xfinal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FINAL$30);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FINAL$30);
         }

         target.setObjectValue(xfinal);
      }
   }

   public void xsetFinal(DerivationSet xfinal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerivationSet target = null;
         target = (DerivationSet)this.get_store().find_attribute_user(FINAL$30);
         if (target == null) {
            target = (DerivationSet)this.get_store().add_attribute_user(FINAL$30);
         }

         target.set(xfinal);
      }
   }

   public void unsetFinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FINAL$30);
      }
   }

   public Object getBlock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BLOCK$32);
         return target == null ? null : target.getObjectValue();
      }
   }

   public BlockSet xgetBlock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BlockSet target = null;
         target = (BlockSet)this.get_store().find_attribute_user(BLOCK$32);
         return target;
      }
   }

   public boolean isSetBlock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(BLOCK$32) != null;
      }
   }

   public void setBlock(Object block) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(BLOCK$32);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(BLOCK$32);
         }

         target.setObjectValue(block);
      }
   }

   public void xsetBlock(BlockSet block) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BlockSet target = null;
         target = (BlockSet)this.get_store().find_attribute_user(BLOCK$32);
         if (target == null) {
            target = (BlockSet)this.get_store().add_attribute_user(BLOCK$32);
         }

         target.set(block);
      }
   }

   public void unsetBlock() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(BLOCK$32);
      }
   }

   public FormChoice.Enum getForm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FORM$34);
         return target == null ? null : (FormChoice.Enum)target.getEnumValue();
      }
   }

   public FormChoice xgetForm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FormChoice target = null;
         target = (FormChoice)this.get_store().find_attribute_user(FORM$34);
         return target;
      }
   }

   public boolean isSetForm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FORM$34) != null;
      }
   }

   public void setForm(FormChoice.Enum form) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FORM$34);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FORM$34);
         }

         target.setEnumValue(form);
      }
   }

   public void xsetForm(FormChoice form) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FormChoice target = null;
         target = (FormChoice)this.get_store().find_attribute_user(FORM$34);
         if (target == null) {
            target = (FormChoice)this.get_store().add_attribute_user(FORM$34);
         }

         target.set(form);
      }
   }

   public void unsetForm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FORM$34);
      }
   }
}
