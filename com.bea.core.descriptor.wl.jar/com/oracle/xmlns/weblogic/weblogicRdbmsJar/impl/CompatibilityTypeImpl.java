package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.CompatibilityType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TrueFalseType;
import javax.xml.namespace.QName;

public class CompatibilityTypeImpl extends XmlComplexContentImpl implements CompatibilityType {
   private static final long serialVersionUID = 1L;
   private static final QName SERIALIZEBYTEARRAYTOORACLEBLOB$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "serialize-byte-array-to-oracle-blob");
   private static final QName SERIALIZECHARARRAYTOBYTES$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "serialize-char-array-to-bytes");
   private static final QName ALLOWREADONLYCREATEANDREMOVE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "allow-readonly-create-and-remove");
   private static final QName DISABLESTRINGTRIMMING$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "disable-string-trimming");
   private static final QName FINDERSRETURNNULLS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "finders-return-nulls");
   private static final QName LOADRELATEDBEANSFROMDBINPOSTCREATE$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "load-related-beans-from-db-in-post-create");
   private static final QName ID$12 = new QName("", "id");

   public CompatibilityTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getSerializeByteArrayToOracleBlob() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SERIALIZEBYTEARRAYTOORACLEBLOB$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSerializeByteArrayToOracleBlob() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERIALIZEBYTEARRAYTOORACLEBLOB$0) != 0;
      }
   }

   public void setSerializeByteArrayToOracleBlob(TrueFalseType serializeByteArrayToOracleBlob) {
      this.generatedSetterHelperImpl(serializeByteArrayToOracleBlob, SERIALIZEBYTEARRAYTOORACLEBLOB$0, 0, (short)1);
   }

   public TrueFalseType addNewSerializeByteArrayToOracleBlob() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SERIALIZEBYTEARRAYTOORACLEBLOB$0);
         return target;
      }
   }

   public void unsetSerializeByteArrayToOracleBlob() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERIALIZEBYTEARRAYTOORACLEBLOB$0, 0);
      }
   }

   public TrueFalseType getSerializeCharArrayToBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SERIALIZECHARARRAYTOBYTES$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSerializeCharArrayToBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERIALIZECHARARRAYTOBYTES$2) != 0;
      }
   }

   public void setSerializeCharArrayToBytes(TrueFalseType serializeCharArrayToBytes) {
      this.generatedSetterHelperImpl(serializeCharArrayToBytes, SERIALIZECHARARRAYTOBYTES$2, 0, (short)1);
   }

   public TrueFalseType addNewSerializeCharArrayToBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SERIALIZECHARARRAYTOBYTES$2);
         return target;
      }
   }

   public void unsetSerializeCharArrayToBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERIALIZECHARARRAYTOBYTES$2, 0);
      }
   }

   public TrueFalseType getAllowReadonlyCreateAndRemove() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ALLOWREADONLYCREATEANDREMOVE$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAllowReadonlyCreateAndRemove() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLOWREADONLYCREATEANDREMOVE$4) != 0;
      }
   }

   public void setAllowReadonlyCreateAndRemove(TrueFalseType allowReadonlyCreateAndRemove) {
      this.generatedSetterHelperImpl(allowReadonlyCreateAndRemove, ALLOWREADONLYCREATEANDREMOVE$4, 0, (short)1);
   }

   public TrueFalseType addNewAllowReadonlyCreateAndRemove() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ALLOWREADONLYCREATEANDREMOVE$4);
         return target;
      }
   }

   public void unsetAllowReadonlyCreateAndRemove() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLOWREADONLYCREATEANDREMOVE$4, 0);
      }
   }

   public TrueFalseType getDisableStringTrimming() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(DISABLESTRINGTRIMMING$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDisableStringTrimming() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISABLESTRINGTRIMMING$6) != 0;
      }
   }

   public void setDisableStringTrimming(TrueFalseType disableStringTrimming) {
      this.generatedSetterHelperImpl(disableStringTrimming, DISABLESTRINGTRIMMING$6, 0, (short)1);
   }

   public TrueFalseType addNewDisableStringTrimming() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(DISABLESTRINGTRIMMING$6);
         return target;
      }
   }

   public void unsetDisableStringTrimming() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISABLESTRINGTRIMMING$6, 0);
      }
   }

   public TrueFalseType getFindersReturnNulls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(FINDERSRETURNNULLS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFindersReturnNulls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FINDERSRETURNNULLS$8) != 0;
      }
   }

   public void setFindersReturnNulls(TrueFalseType findersReturnNulls) {
      this.generatedSetterHelperImpl(findersReturnNulls, FINDERSRETURNNULLS$8, 0, (short)1);
   }

   public TrueFalseType addNewFindersReturnNulls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(FINDERSRETURNNULLS$8);
         return target;
      }
   }

   public void unsetFindersReturnNulls() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FINDERSRETURNNULLS$8, 0);
      }
   }

   public TrueFalseType getLoadRelatedBeansFromDbInPostCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(LOADRELATEDBEANSFROMDBINPOSTCREATE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLoadRelatedBeansFromDbInPostCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOADRELATEDBEANSFROMDBINPOSTCREATE$10) != 0;
      }
   }

   public void setLoadRelatedBeansFromDbInPostCreate(TrueFalseType loadRelatedBeansFromDbInPostCreate) {
      this.generatedSetterHelperImpl(loadRelatedBeansFromDbInPostCreate, LOADRELATEDBEANSFROMDBINPOSTCREATE$10, 0, (short)1);
   }

   public TrueFalseType addNewLoadRelatedBeansFromDbInPostCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(LOADRELATEDBEANSFROMDBINPOSTCREATE$10);
         return target;
      }
   }

   public void unsetLoadRelatedBeansFromDbInPostCreate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOADRELATEDBEANSFROMDBINPOSTCREATE$10, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$12) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$12);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$12);
      }
   }
}
