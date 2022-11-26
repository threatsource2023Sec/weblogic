package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MappingDefaultsImplType;
import javax.xml.namespace.QName;

public class MappingDefaultsImplTypeImpl extends MappingDefaultsTypeImpl implements MappingDefaultsImplType {
   private static final long serialVersionUID = 1L;
   private static final QName USECLASSCRITERIA$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "use-class-criteria");
   private static final QName BASECLASSSTRATEGY$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "base-class-strategy");
   private static final QName VERSIONSTRATEGY$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "version-strategy");
   private static final QName DISCRIMINATORCOLUMNNAME$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "discriminator-column-name");
   private static final QName SUBCLASSSTRATEGY$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "subclass-strategy");
   private static final QName INDEXVERSION$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "index-version");
   private static final QName DEFAULTMISSINGINFO$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-missing-info");
   private static final QName INDEXLOGICALFOREIGNKEYS$14 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "index-logical-foreign-keys");
   private static final QName NULLINDICATORCOLUMNNAME$16 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "null-indicator-column-name");
   private static final QName FOREIGNKEYDELETEACTION$18 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "foreign-key-delete-action");
   private static final QName JOINFOREIGNKEYDELETEACTION$20 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "join-foreign-key-delete-action");
   private static final QName DISCRIMINATORSTRATEGY$22 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "discriminator-strategy");
   private static final QName DEFERCONSTRAINTS$24 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "defer-constraints");
   private static final QName FIELDSTRATEGIES$26 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "field-strategies");
   private static final QName VERSIONCOLUMNNAME$28 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "version-column-name");
   private static final QName DATASTOREIDCOLUMNNAME$30 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "data-store-id-column-name");
   private static final QName INDEXDISCRIMINATOR$32 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "index-discriminator");
   private static final QName STOREENUMORDINAL$34 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "store-enum-ordinal");
   private static final QName ORDERLISTS$36 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "order-lists");
   private static final QName ORDERCOLUMNNAME$38 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "order-column-name");
   private static final QName ADDNULLINDICATOR$40 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "add-null-indicator");
   private static final QName STOREUNMAPPEDOBJECTIDSTRING$42 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "store-unmapped-object-id-string");

   public MappingDefaultsImplTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getUseClassCriteria() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USECLASSCRITERIA$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseClassCriteria() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USECLASSCRITERIA$0, 0);
         return target;
      }
   }

   public boolean isSetUseClassCriteria() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USECLASSCRITERIA$0) != 0;
      }
   }

   public void setUseClassCriteria(boolean useClassCriteria) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USECLASSCRITERIA$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USECLASSCRITERIA$0);
         }

         target.setBooleanValue(useClassCriteria);
      }
   }

   public void xsetUseClassCriteria(XmlBoolean useClassCriteria) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USECLASSCRITERIA$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USECLASSCRITERIA$0);
         }

         target.set(useClassCriteria);
      }
   }

   public void unsetUseClassCriteria() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USECLASSCRITERIA$0, 0);
      }
   }

   public String getBaseClassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASECLASSSTRATEGY$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBaseClassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASECLASSSTRATEGY$2, 0);
         return target;
      }
   }

   public boolean isNilBaseClassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASECLASSSTRATEGY$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetBaseClassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BASECLASSSTRATEGY$2) != 0;
      }
   }

   public void setBaseClassStrategy(String baseClassStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASECLASSSTRATEGY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASECLASSSTRATEGY$2);
         }

         target.setStringValue(baseClassStrategy);
      }
   }

   public void xsetBaseClassStrategy(XmlString baseClassStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASECLASSSTRATEGY$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BASECLASSSTRATEGY$2);
         }

         target.set(baseClassStrategy);
      }
   }

   public void setNilBaseClassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASECLASSSTRATEGY$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BASECLASSSTRATEGY$2);
         }

         target.setNil();
      }
   }

   public void unsetBaseClassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BASECLASSSTRATEGY$2, 0);
      }
   }

   public String getVersionStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSIONSTRATEGY$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersionStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSIONSTRATEGY$4, 0);
         return target;
      }
   }

   public boolean isNilVersionStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSIONSTRATEGY$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetVersionStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSIONSTRATEGY$4) != 0;
      }
   }

   public void setVersionStrategy(String versionStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSIONSTRATEGY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSIONSTRATEGY$4);
         }

         target.setStringValue(versionStrategy);
      }
   }

   public void xsetVersionStrategy(XmlString versionStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSIONSTRATEGY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VERSIONSTRATEGY$4);
         }

         target.set(versionStrategy);
      }
   }

   public void setNilVersionStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSIONSTRATEGY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VERSIONSTRATEGY$4);
         }

         target.setNil();
      }
   }

   public void unsetVersionStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSIONSTRATEGY$4, 0);
      }
   }

   public String getDiscriminatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DISCRIMINATORCOLUMNNAME$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDiscriminatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISCRIMINATORCOLUMNNAME$6, 0);
         return target;
      }
   }

   public boolean isNilDiscriminatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISCRIMINATORCOLUMNNAME$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDiscriminatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISCRIMINATORCOLUMNNAME$6) != 0;
      }
   }

   public void setDiscriminatorColumnName(String discriminatorColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DISCRIMINATORCOLUMNNAME$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DISCRIMINATORCOLUMNNAME$6);
         }

         target.setStringValue(discriminatorColumnName);
      }
   }

   public void xsetDiscriminatorColumnName(XmlString discriminatorColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISCRIMINATORCOLUMNNAME$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DISCRIMINATORCOLUMNNAME$6);
         }

         target.set(discriminatorColumnName);
      }
   }

   public void setNilDiscriminatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISCRIMINATORCOLUMNNAME$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DISCRIMINATORCOLUMNNAME$6);
         }

         target.setNil();
      }
   }

   public void unsetDiscriminatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISCRIMINATORCOLUMNNAME$6, 0);
      }
   }

   public String getSubclassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBCLASSSTRATEGY$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSubclassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBCLASSSTRATEGY$8, 0);
         return target;
      }
   }

   public boolean isNilSubclassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBCLASSSTRATEGY$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSubclassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUBCLASSSTRATEGY$8) != 0;
      }
   }

   public void setSubclassStrategy(String subclassStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBCLASSSTRATEGY$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUBCLASSSTRATEGY$8);
         }

         target.setStringValue(subclassStrategy);
      }
   }

   public void xsetSubclassStrategy(XmlString subclassStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBCLASSSTRATEGY$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SUBCLASSSTRATEGY$8);
         }

         target.set(subclassStrategy);
      }
   }

   public void setNilSubclassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBCLASSSTRATEGY$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SUBCLASSSTRATEGY$8);
         }

         target.setNil();
      }
   }

   public void unsetSubclassStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUBCLASSSTRATEGY$8, 0);
      }
   }

   public boolean getIndexVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INDEXVERSION$10, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIndexVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INDEXVERSION$10, 0);
         return target;
      }
   }

   public boolean isSetIndexVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INDEXVERSION$10) != 0;
      }
   }

   public void setIndexVersion(boolean indexVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INDEXVERSION$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INDEXVERSION$10);
         }

         target.setBooleanValue(indexVersion);
      }
   }

   public void xsetIndexVersion(XmlBoolean indexVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INDEXVERSION$10, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(INDEXVERSION$10);
         }

         target.set(indexVersion);
      }
   }

   public void unsetIndexVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INDEXVERSION$10, 0);
      }
   }

   public boolean getDefaultMissingInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTMISSINGINFO$12, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDefaultMissingInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFAULTMISSINGINFO$12, 0);
         return target;
      }
   }

   public boolean isSetDefaultMissingInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTMISSINGINFO$12) != 0;
      }
   }

   public void setDefaultMissingInfo(boolean defaultMissingInfo) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTMISSINGINFO$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTMISSINGINFO$12);
         }

         target.setBooleanValue(defaultMissingInfo);
      }
   }

   public void xsetDefaultMissingInfo(XmlBoolean defaultMissingInfo) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFAULTMISSINGINFO$12, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DEFAULTMISSINGINFO$12);
         }

         target.set(defaultMissingInfo);
      }
   }

   public void unsetDefaultMissingInfo() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTMISSINGINFO$12, 0);
      }
   }

   public boolean getIndexLogicalForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INDEXLOGICALFOREIGNKEYS$14, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIndexLogicalForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INDEXLOGICALFOREIGNKEYS$14, 0);
         return target;
      }
   }

   public boolean isSetIndexLogicalForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INDEXLOGICALFOREIGNKEYS$14) != 0;
      }
   }

   public void setIndexLogicalForeignKeys(boolean indexLogicalForeignKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INDEXLOGICALFOREIGNKEYS$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INDEXLOGICALFOREIGNKEYS$14);
         }

         target.setBooleanValue(indexLogicalForeignKeys);
      }
   }

   public void xsetIndexLogicalForeignKeys(XmlBoolean indexLogicalForeignKeys) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INDEXLOGICALFOREIGNKEYS$14, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(INDEXLOGICALFOREIGNKEYS$14);
         }

         target.set(indexLogicalForeignKeys);
      }
   }

   public void unsetIndexLogicalForeignKeys() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INDEXLOGICALFOREIGNKEYS$14, 0);
      }
   }

   public String getNullIndicatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NULLINDICATORCOLUMNNAME$16, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNullIndicatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NULLINDICATORCOLUMNNAME$16, 0);
         return target;
      }
   }

   public boolean isNilNullIndicatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NULLINDICATORCOLUMNNAME$16, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNullIndicatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NULLINDICATORCOLUMNNAME$16) != 0;
      }
   }

   public void setNullIndicatorColumnName(String nullIndicatorColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NULLINDICATORCOLUMNNAME$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NULLINDICATORCOLUMNNAME$16);
         }

         target.setStringValue(nullIndicatorColumnName);
      }
   }

   public void xsetNullIndicatorColumnName(XmlString nullIndicatorColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NULLINDICATORCOLUMNNAME$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NULLINDICATORCOLUMNNAME$16);
         }

         target.set(nullIndicatorColumnName);
      }
   }

   public void setNilNullIndicatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NULLINDICATORCOLUMNNAME$16, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NULLINDICATORCOLUMNNAME$16);
         }

         target.setNil();
      }
   }

   public void unsetNullIndicatorColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NULLINDICATORCOLUMNNAME$16, 0);
      }
   }

   public int getForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FOREIGNKEYDELETEACTION$18, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FOREIGNKEYDELETEACTION$18, 0);
         return target;
      }
   }

   public boolean isSetForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FOREIGNKEYDELETEACTION$18) != 0;
      }
   }

   public void setForeignKeyDeleteAction(int foreignKeyDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FOREIGNKEYDELETEACTION$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FOREIGNKEYDELETEACTION$18);
         }

         target.setIntValue(foreignKeyDeleteAction);
      }
   }

   public void xsetForeignKeyDeleteAction(XmlInt foreignKeyDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FOREIGNKEYDELETEACTION$18, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(FOREIGNKEYDELETEACTION$18);
         }

         target.set(foreignKeyDeleteAction);
      }
   }

   public void unsetForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FOREIGNKEYDELETEACTION$18, 0);
      }
   }

   public String getJoinForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JOINFOREIGNKEYDELETEACTION$20, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJoinForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JOINFOREIGNKEYDELETEACTION$20, 0);
         return target;
      }
   }

   public boolean isNilJoinForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JOINFOREIGNKEYDELETEACTION$20, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJoinForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JOINFOREIGNKEYDELETEACTION$20) != 0;
      }
   }

   public void setJoinForeignKeyDeleteAction(String joinForeignKeyDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JOINFOREIGNKEYDELETEACTION$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JOINFOREIGNKEYDELETEACTION$20);
         }

         target.setStringValue(joinForeignKeyDeleteAction);
      }
   }

   public void xsetJoinForeignKeyDeleteAction(XmlString joinForeignKeyDeleteAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JOINFOREIGNKEYDELETEACTION$20, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JOINFOREIGNKEYDELETEACTION$20);
         }

         target.set(joinForeignKeyDeleteAction);
      }
   }

   public void setNilJoinForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JOINFOREIGNKEYDELETEACTION$20, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JOINFOREIGNKEYDELETEACTION$20);
         }

         target.setNil();
      }
   }

   public void unsetJoinForeignKeyDeleteAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JOINFOREIGNKEYDELETEACTION$20, 0);
      }
   }

   public String getDiscriminatorStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DISCRIMINATORSTRATEGY$22, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDiscriminatorStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISCRIMINATORSTRATEGY$22, 0);
         return target;
      }
   }

   public boolean isNilDiscriminatorStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISCRIMINATORSTRATEGY$22, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDiscriminatorStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISCRIMINATORSTRATEGY$22) != 0;
      }
   }

   public void setDiscriminatorStrategy(String discriminatorStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DISCRIMINATORSTRATEGY$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DISCRIMINATORSTRATEGY$22);
         }

         target.setStringValue(discriminatorStrategy);
      }
   }

   public void xsetDiscriminatorStrategy(XmlString discriminatorStrategy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISCRIMINATORSTRATEGY$22, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DISCRIMINATORSTRATEGY$22);
         }

         target.set(discriminatorStrategy);
      }
   }

   public void setNilDiscriminatorStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DISCRIMINATORSTRATEGY$22, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DISCRIMINATORSTRATEGY$22);
         }

         target.setNil();
      }
   }

   public void unsetDiscriminatorStrategy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISCRIMINATORSTRATEGY$22, 0);
      }
   }

   public boolean getDeferConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFERCONSTRAINTS$24, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDeferConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFERCONSTRAINTS$24, 0);
         return target;
      }
   }

   public boolean isSetDeferConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFERCONSTRAINTS$24) != 0;
      }
   }

   public void setDeferConstraints(boolean deferConstraints) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFERCONSTRAINTS$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFERCONSTRAINTS$24);
         }

         target.setBooleanValue(deferConstraints);
      }
   }

   public void xsetDeferConstraints(XmlBoolean deferConstraints) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFERCONSTRAINTS$24, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DEFERCONSTRAINTS$24);
         }

         target.set(deferConstraints);
      }
   }

   public void unsetDeferConstraints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFERCONSTRAINTS$24, 0);
      }
   }

   public String getFieldStrategies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIELDSTRATEGIES$26, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFieldStrategies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIELDSTRATEGIES$26, 0);
         return target;
      }
   }

   public boolean isNilFieldStrategies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIELDSTRATEGIES$26, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFieldStrategies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIELDSTRATEGIES$26) != 0;
      }
   }

   public void setFieldStrategies(String fieldStrategies) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FIELDSTRATEGIES$26, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FIELDSTRATEGIES$26);
         }

         target.setStringValue(fieldStrategies);
      }
   }

   public void xsetFieldStrategies(XmlString fieldStrategies) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIELDSTRATEGIES$26, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FIELDSTRATEGIES$26);
         }

         target.set(fieldStrategies);
      }
   }

   public void setNilFieldStrategies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FIELDSTRATEGIES$26, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FIELDSTRATEGIES$26);
         }

         target.setNil();
      }
   }

   public void unsetFieldStrategies() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIELDSTRATEGIES$26, 0);
      }
   }

   public String getVersionColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSIONCOLUMNNAME$28, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersionColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSIONCOLUMNNAME$28, 0);
         return target;
      }
   }

   public boolean isNilVersionColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSIONCOLUMNNAME$28, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetVersionColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSIONCOLUMNNAME$28) != 0;
      }
   }

   public void setVersionColumnName(String versionColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VERSIONCOLUMNNAME$28, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VERSIONCOLUMNNAME$28);
         }

         target.setStringValue(versionColumnName);
      }
   }

   public void xsetVersionColumnName(XmlString versionColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSIONCOLUMNNAME$28, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VERSIONCOLUMNNAME$28);
         }

         target.set(versionColumnName);
      }
   }

   public void setNilVersionColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VERSIONCOLUMNNAME$28, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VERSIONCOLUMNNAME$28);
         }

         target.setNil();
      }
   }

   public void unsetVersionColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSIONCOLUMNNAME$28, 0);
      }
   }

   public String getDataStoreIdColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATASTOREIDCOLUMNNAME$30, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDataStoreIdColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATASTOREIDCOLUMNNAME$30, 0);
         return target;
      }
   }

   public boolean isNilDataStoreIdColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATASTOREIDCOLUMNNAME$30, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDataStoreIdColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATASTOREIDCOLUMNNAME$30) != 0;
      }
   }

   public void setDataStoreIdColumnName(String dataStoreIdColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATASTOREIDCOLUMNNAME$30, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DATASTOREIDCOLUMNNAME$30);
         }

         target.setStringValue(dataStoreIdColumnName);
      }
   }

   public void xsetDataStoreIdColumnName(XmlString dataStoreIdColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATASTOREIDCOLUMNNAME$30, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DATASTOREIDCOLUMNNAME$30);
         }

         target.set(dataStoreIdColumnName);
      }
   }

   public void setNilDataStoreIdColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DATASTOREIDCOLUMNNAME$30, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DATASTOREIDCOLUMNNAME$30);
         }

         target.setNil();
      }
   }

   public void unsetDataStoreIdColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATASTOREIDCOLUMNNAME$30, 0);
      }
   }

   public boolean getIndexDiscriminator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INDEXDISCRIMINATOR$32, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIndexDiscriminator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INDEXDISCRIMINATOR$32, 0);
         return target;
      }
   }

   public boolean isSetIndexDiscriminator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INDEXDISCRIMINATOR$32) != 0;
      }
   }

   public void setIndexDiscriminator(boolean indexDiscriminator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INDEXDISCRIMINATOR$32, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INDEXDISCRIMINATOR$32);
         }

         target.setBooleanValue(indexDiscriminator);
      }
   }

   public void xsetIndexDiscriminator(XmlBoolean indexDiscriminator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INDEXDISCRIMINATOR$32, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(INDEXDISCRIMINATOR$32);
         }

         target.set(indexDiscriminator);
      }
   }

   public void unsetIndexDiscriminator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INDEXDISCRIMINATOR$32, 0);
      }
   }

   public boolean getStoreEnumOrdinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STOREENUMORDINAL$34, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStoreEnumOrdinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STOREENUMORDINAL$34, 0);
         return target;
      }
   }

   public boolean isSetStoreEnumOrdinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STOREENUMORDINAL$34) != 0;
      }
   }

   public void setStoreEnumOrdinal(boolean storeEnumOrdinal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STOREENUMORDINAL$34, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STOREENUMORDINAL$34);
         }

         target.setBooleanValue(storeEnumOrdinal);
      }
   }

   public void xsetStoreEnumOrdinal(XmlBoolean storeEnumOrdinal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STOREENUMORDINAL$34, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STOREENUMORDINAL$34);
         }

         target.set(storeEnumOrdinal);
      }
   }

   public void unsetStoreEnumOrdinal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOREENUMORDINAL$34, 0);
      }
   }

   public boolean getOrderLists() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ORDERLISTS$36, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetOrderLists() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ORDERLISTS$36, 0);
         return target;
      }
   }

   public boolean isSetOrderLists() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORDERLISTS$36) != 0;
      }
   }

   public void setOrderLists(boolean orderLists) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ORDERLISTS$36, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ORDERLISTS$36);
         }

         target.setBooleanValue(orderLists);
      }
   }

   public void xsetOrderLists(XmlBoolean orderLists) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ORDERLISTS$36, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ORDERLISTS$36);
         }

         target.set(orderLists);
      }
   }

   public void unsetOrderLists() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORDERLISTS$36, 0);
      }
   }

   public String getOrderColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ORDERCOLUMNNAME$38, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetOrderColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ORDERCOLUMNNAME$38, 0);
         return target;
      }
   }

   public boolean isNilOrderColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ORDERCOLUMNNAME$38, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetOrderColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORDERCOLUMNNAME$38) != 0;
      }
   }

   public void setOrderColumnName(String orderColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ORDERCOLUMNNAME$38, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ORDERCOLUMNNAME$38);
         }

         target.setStringValue(orderColumnName);
      }
   }

   public void xsetOrderColumnName(XmlString orderColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ORDERCOLUMNNAME$38, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ORDERCOLUMNNAME$38);
         }

         target.set(orderColumnName);
      }
   }

   public void setNilOrderColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ORDERCOLUMNNAME$38, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ORDERCOLUMNNAME$38);
         }

         target.setNil();
      }
   }

   public void unsetOrderColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORDERCOLUMNNAME$38, 0);
      }
   }

   public boolean getAddNullIndicator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADDNULLINDICATOR$40, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAddNullIndicator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ADDNULLINDICATOR$40, 0);
         return target;
      }
   }

   public boolean isSetAddNullIndicator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADDNULLINDICATOR$40) != 0;
      }
   }

   public void setAddNullIndicator(boolean addNullIndicator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADDNULLINDICATOR$40, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ADDNULLINDICATOR$40);
         }

         target.setBooleanValue(addNullIndicator);
      }
   }

   public void xsetAddNullIndicator(XmlBoolean addNullIndicator) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ADDNULLINDICATOR$40, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ADDNULLINDICATOR$40);
         }

         target.set(addNullIndicator);
      }
   }

   public void unsetAddNullIndicator() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADDNULLINDICATOR$40, 0);
      }
   }

   public boolean getStoreUnmappedObjectIdString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STOREUNMAPPEDOBJECTIDSTRING$42, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStoreUnmappedObjectIdString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STOREUNMAPPEDOBJECTIDSTRING$42, 0);
         return target;
      }
   }

   public boolean isSetStoreUnmappedObjectIdString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STOREUNMAPPEDOBJECTIDSTRING$42) != 0;
      }
   }

   public void setStoreUnmappedObjectIdString(boolean storeUnmappedObjectIdString) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STOREUNMAPPEDOBJECTIDSTRING$42, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STOREUNMAPPEDOBJECTIDSTRING$42);
         }

         target.setBooleanValue(storeUnmappedObjectIdString);
      }
   }

   public void xsetStoreUnmappedObjectIdString(XmlBoolean storeUnmappedObjectIdString) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STOREUNMAPPEDOBJECTIDSTRING$42, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STOREUNMAPPEDOBJECTIDSTRING$42);
         }

         target.set(storeUnmappedObjectIdString);
      }
   }

   public void unsetStoreUnmappedObjectIdString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOREUNMAPPEDOBJECTIDSTRING$42, 0);
      }
   }
}
