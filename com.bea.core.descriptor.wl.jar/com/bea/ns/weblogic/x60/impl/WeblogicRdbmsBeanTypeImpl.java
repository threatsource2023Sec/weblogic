package com.bea.ns.weblogic.x60.impl;

import com.bea.ns.weblogic.x60.DataSourceJndiNameType;
import com.bea.ns.weblogic.x60.EjbNameType;
import com.bea.ns.weblogic.x60.FieldMapType;
import com.bea.ns.weblogic.x60.FinderType;
import com.bea.ns.weblogic.x60.PoolNameType;
import com.bea.ns.weblogic.x60.TableNameType;
import com.bea.ns.weblogic.x60.TrueFalseType;
import com.bea.ns.weblogic.x60.WeblogicRdbmsBeanType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class WeblogicRdbmsBeanTypeImpl extends XmlComplexContentImpl implements WeblogicRdbmsBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName EJBNAME$0 = new QName("http://www.bea.com/ns/weblogic/60", "ejb-name");
   private static final QName POOLNAME$2 = new QName("http://www.bea.com/ns/weblogic/60", "pool-name");
   private static final QName DATASOURCEJNDINAME$4 = new QName("http://www.bea.com/ns/weblogic/60", "data-source-jndi-name");
   private static final QName TABLENAME$6 = new QName("http://www.bea.com/ns/weblogic/60", "table-name");
   private static final QName FIELDMAP$8 = new QName("http://www.bea.com/ns/weblogic/60", "field-map");
   private static final QName FINDER$10 = new QName("http://www.bea.com/ns/weblogic/60", "finder");
   private static final QName ENABLETUNEDUPDATES$12 = new QName("http://www.bea.com/ns/weblogic/60", "enable-tuned-updates");
   private static final QName ID$14 = new QName("", "id");

   public WeblogicRdbmsBeanTypeImpl(SchemaType sType) {
      super(sType);
   }

   public EjbNameType getEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().find_element_user(EJBNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbName(EjbNameType ejbName) {
      this.generatedSetterHelperImpl(ejbName, EJBNAME$0, 0, (short)1);
   }

   public EjbNameType addNewEjbName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbNameType target = null;
         target = (EjbNameType)this.get_store().add_element_user(EJBNAME$0);
         return target;
      }
   }

   public PoolNameType getPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolNameType target = null;
         target = (PoolNameType)this.get_store().find_element_user(POOLNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POOLNAME$2) != 0;
      }
   }

   public void setPoolName(PoolNameType poolName) {
      this.generatedSetterHelperImpl(poolName, POOLNAME$2, 0, (short)1);
   }

   public PoolNameType addNewPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PoolNameType target = null;
         target = (PoolNameType)this.get_store().add_element_user(POOLNAME$2);
         return target;
      }
   }

   public void unsetPoolName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POOLNAME$2, 0);
      }
   }

   public DataSourceJndiNameType getDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceJndiNameType target = null;
         target = (DataSourceJndiNameType)this.get_store().find_element_user(DATASOURCEJNDINAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATASOURCEJNDINAME$4) != 0;
      }
   }

   public void setDataSourceJndiName(DataSourceJndiNameType dataSourceJndiName) {
      this.generatedSetterHelperImpl(dataSourceJndiName, DATASOURCEJNDINAME$4, 0, (short)1);
   }

   public DataSourceJndiNameType addNewDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataSourceJndiNameType target = null;
         target = (DataSourceJndiNameType)this.get_store().add_element_user(DATASOURCEJNDINAME$4);
         return target;
      }
   }

   public void unsetDataSourceJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATASOURCEJNDINAME$4, 0);
      }
   }

   public TableNameType getTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableNameType target = null;
         target = (TableNameType)this.get_store().find_element_user(TABLENAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setTableName(TableNameType tableName) {
      this.generatedSetterHelperImpl(tableName, TABLENAME$6, 0, (short)1);
   }

   public TableNameType addNewTableName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableNameType target = null;
         target = (TableNameType)this.get_store().add_element_user(TABLENAME$6);
         return target;
      }
   }

   public FieldMapType[] getFieldMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FIELDMAP$8, targetList);
         FieldMapType[] result = new FieldMapType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FieldMapType getFieldMapArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldMapType target = null;
         target = (FieldMapType)this.get_store().find_element_user(FIELDMAP$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFieldMapArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FIELDMAP$8);
      }
   }

   public void setFieldMapArray(FieldMapType[] fieldMapArray) {
      this.check_orphaned();
      this.arraySetterHelper(fieldMapArray, FIELDMAP$8);
   }

   public void setFieldMapArray(int i, FieldMapType fieldMap) {
      this.generatedSetterHelperImpl(fieldMap, FIELDMAP$8, i, (short)2);
   }

   public FieldMapType insertNewFieldMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldMapType target = null;
         target = (FieldMapType)this.get_store().insert_element_user(FIELDMAP$8, i);
         return target;
      }
   }

   public FieldMapType addNewFieldMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FieldMapType target = null;
         target = (FieldMapType)this.get_store().add_element_user(FIELDMAP$8);
         return target;
      }
   }

   public void removeFieldMap(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FIELDMAP$8, i);
      }
   }

   public FinderType[] getFinderArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FINDER$10, targetList);
         FinderType[] result = new FinderType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FinderType getFinderArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderType target = null;
         target = (FinderType)this.get_store().find_element_user(FINDER$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFinderArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FINDER$10);
      }
   }

   public void setFinderArray(FinderType[] finderArray) {
      this.check_orphaned();
      this.arraySetterHelper(finderArray, FINDER$10);
   }

   public void setFinderArray(int i, FinderType finder) {
      this.generatedSetterHelperImpl(finder, FINDER$10, i, (short)2);
   }

   public FinderType insertNewFinder(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderType target = null;
         target = (FinderType)this.get_store().insert_element_user(FINDER$10, i);
         return target;
      }
   }

   public FinderType addNewFinder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderType target = null;
         target = (FinderType)this.get_store().add_element_user(FINDER$10);
         return target;
      }
   }

   public void removeFinder(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FINDER$10, i);
      }
   }

   public TrueFalseType getEnableTunedUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLETUNEDUPDATES$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableTunedUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLETUNEDUPDATES$12) != 0;
      }
   }

   public void setEnableTunedUpdates(TrueFalseType enableTunedUpdates) {
      this.generatedSetterHelperImpl(enableTunedUpdates, ENABLETUNEDUPDATES$12, 0, (short)1);
   }

   public TrueFalseType addNewEnableTunedUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLETUNEDUPDATES$12);
         return target;
      }
   }

   public void unsetEnableTunedUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLETUNEDUPDATES$12, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
