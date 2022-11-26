package com.bea.ns.weblogic.x60.impl;

import com.bea.ns.weblogic.x60.FinderNameType;
import com.bea.ns.weblogic.x60.FinderParamType;
import com.bea.ns.weblogic.x60.FinderQueryType;
import com.bea.ns.weblogic.x60.FinderSqlType;
import com.bea.ns.weblogic.x60.FinderType;
import com.bea.ns.weblogic.x60.TrueFalseType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class FinderTypeImpl extends XmlComplexContentImpl implements FinderType {
   private static final long serialVersionUID = 1L;
   private static final QName FINDERNAME$0 = new QName("http://www.bea.com/ns/weblogic/60", "finder-name");
   private static final QName FINDERPARAM$2 = new QName("http://www.bea.com/ns/weblogic/60", "finder-param");
   private static final QName FINDERQUERY$4 = new QName("http://www.bea.com/ns/weblogic/60", "finder-query");
   private static final QName FINDERSQL$6 = new QName("http://www.bea.com/ns/weblogic/60", "finder-sql");
   private static final QName FINDFORUPDATE$8 = new QName("http://www.bea.com/ns/weblogic/60", "find-for-update");
   private static final QName ID$10 = new QName("", "id");

   public FinderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FinderNameType getFinderName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderNameType target = null;
         target = (FinderNameType)this.get_store().find_element_user(FINDERNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setFinderName(FinderNameType finderName) {
      this.generatedSetterHelperImpl(finderName, FINDERNAME$0, 0, (short)1);
   }

   public FinderNameType addNewFinderName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderNameType target = null;
         target = (FinderNameType)this.get_store().add_element_user(FINDERNAME$0);
         return target;
      }
   }

   public FinderParamType[] getFinderParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FINDERPARAM$2, targetList);
         FinderParamType[] result = new FinderParamType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FinderParamType getFinderParamArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderParamType target = null;
         target = (FinderParamType)this.get_store().find_element_user(FINDERPARAM$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFinderParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FINDERPARAM$2);
      }
   }

   public void setFinderParamArray(FinderParamType[] finderParamArray) {
      this.check_orphaned();
      this.arraySetterHelper(finderParamArray, FINDERPARAM$2);
   }

   public void setFinderParamArray(int i, FinderParamType finderParam) {
      this.generatedSetterHelperImpl(finderParam, FINDERPARAM$2, i, (short)2);
   }

   public FinderParamType insertNewFinderParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderParamType target = null;
         target = (FinderParamType)this.get_store().insert_element_user(FINDERPARAM$2, i);
         return target;
      }
   }

   public FinderParamType addNewFinderParam() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderParamType target = null;
         target = (FinderParamType)this.get_store().add_element_user(FINDERPARAM$2);
         return target;
      }
   }

   public void removeFinderParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FINDERPARAM$2, i);
      }
   }

   public FinderQueryType getFinderQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderQueryType target = null;
         target = (FinderQueryType)this.get_store().find_element_user(FINDERQUERY$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFinderQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FINDERQUERY$4) != 0;
      }
   }

   public void setFinderQuery(FinderQueryType finderQuery) {
      this.generatedSetterHelperImpl(finderQuery, FINDERQUERY$4, 0, (short)1);
   }

   public FinderQueryType addNewFinderQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderQueryType target = null;
         target = (FinderQueryType)this.get_store().add_element_user(FINDERQUERY$4);
         return target;
      }
   }

   public void unsetFinderQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FINDERQUERY$4, 0);
      }
   }

   public FinderSqlType getFinderSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderSqlType target = null;
         target = (FinderSqlType)this.get_store().find_element_user(FINDERSQL$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFinderSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FINDERSQL$6) != 0;
      }
   }

   public void setFinderSql(FinderSqlType finderSql) {
      this.generatedSetterHelperImpl(finderSql, FINDERSQL$6, 0, (short)1);
   }

   public FinderSqlType addNewFinderSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FinderSqlType target = null;
         target = (FinderSqlType)this.get_store().add_element_user(FINDERSQL$6);
         return target;
      }
   }

   public void unsetFinderSql() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FINDERSQL$6, 0);
      }
   }

   public TrueFalseType getFindForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(FINDFORUPDATE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFindForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FINDFORUPDATE$8) != 0;
      }
   }

   public void setFindForUpdate(TrueFalseType findForUpdate) {
      this.generatedSetterHelperImpl(findForUpdate, FINDFORUPDATE$8, 0, (short)1);
   }

   public TrueFalseType addNewFindForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(FINDFORUPDATE$8);
         return target;
      }
   }

   public void unsetFindForUpdate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FINDFORUPDATE$8, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
