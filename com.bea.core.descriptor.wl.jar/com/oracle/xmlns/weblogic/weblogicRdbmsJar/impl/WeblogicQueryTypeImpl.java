package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.EjbQlQueryType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.QueryMethodType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.SqlQueryType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.TrueFalseType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.WeblogicQueryType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.XsdPositiveIntegerType;
import javax.xml.namespace.QName;

public class WeblogicQueryTypeImpl extends XmlComplexContentImpl implements WeblogicQueryType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "description");
   private static final QName QUERYMETHOD$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "query-method");
   private static final QName EJBQLQUERY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "ejb-ql-query");
   private static final QName SQLQUERY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "sql-query");
   private static final QName MAXELEMENTS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "max-elements");
   private static final QName INCLUDEUPDATES$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "include-updates");
   private static final QName SQLSELECTDISTINCT$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "sql-select-distinct");
   private static final QName ENABLEQUERYCACHING$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "enable-query-caching");
   private static final QName ENABLEEAGERREFRESH$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "enable-eager-refresh");
   private static final QName INCLUDERESULTCACHEHINT$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "include-result-cache-hint");
   private static final QName ID$20 = new QName("", "id");

   public WeblogicQueryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, 0, (short)1);
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public QueryMethodType getQueryMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryMethodType target = null;
         target = (QueryMethodType)this.get_store().find_element_user(QUERYMETHOD$2, 0);
         return target == null ? null : target;
      }
   }

   public void setQueryMethod(QueryMethodType queryMethod) {
      this.generatedSetterHelperImpl(queryMethod, QUERYMETHOD$2, 0, (short)1);
   }

   public QueryMethodType addNewQueryMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryMethodType target = null;
         target = (QueryMethodType)this.get_store().add_element_user(QUERYMETHOD$2);
         return target;
      }
   }

   public EjbQlQueryType getEjbQlQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbQlQueryType target = null;
         target = (EjbQlQueryType)this.get_store().find_element_user(EJBQLQUERY$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbQlQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBQLQUERY$4) != 0;
      }
   }

   public void setEjbQlQuery(EjbQlQueryType ejbQlQuery) {
      this.generatedSetterHelperImpl(ejbQlQuery, EJBQLQUERY$4, 0, (short)1);
   }

   public EjbQlQueryType addNewEjbQlQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbQlQueryType target = null;
         target = (EjbQlQueryType)this.get_store().add_element_user(EJBQLQUERY$4);
         return target;
      }
   }

   public void unsetEjbQlQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBQLQUERY$4, 0);
      }
   }

   public SqlQueryType getSqlQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlQueryType target = null;
         target = (SqlQueryType)this.get_store().find_element_user(SQLQUERY$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSqlQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SQLQUERY$6) != 0;
      }
   }

   public void setSqlQuery(SqlQueryType sqlQuery) {
      this.generatedSetterHelperImpl(sqlQuery, SQLQUERY$6, 0, (short)1);
   }

   public SqlQueryType addNewSqlQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlQueryType target = null;
         target = (SqlQueryType)this.get_store().add_element_user(SQLQUERY$6);
         return target;
      }
   }

   public void unsetSqlQuery() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SQLQUERY$6, 0);
      }
   }

   public XsdPositiveIntegerType getMaxElements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().find_element_user(MAXELEMENTS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMaxElements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXELEMENTS$8) != 0;
      }
   }

   public void setMaxElements(XsdPositiveIntegerType maxElements) {
      this.generatedSetterHelperImpl(maxElements, MAXELEMENTS$8, 0, (short)1);
   }

   public XsdPositiveIntegerType addNewMaxElements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdPositiveIntegerType target = null;
         target = (XsdPositiveIntegerType)this.get_store().add_element_user(MAXELEMENTS$8);
         return target;
      }
   }

   public void unsetMaxElements() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXELEMENTS$8, 0);
      }
   }

   public TrueFalseType getIncludeUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(INCLUDEUPDATES$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIncludeUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCLUDEUPDATES$10) != 0;
      }
   }

   public void setIncludeUpdates(TrueFalseType includeUpdates) {
      this.generatedSetterHelperImpl(includeUpdates, INCLUDEUPDATES$10, 0, (short)1);
   }

   public TrueFalseType addNewIncludeUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(INCLUDEUPDATES$10);
         return target;
      }
   }

   public void unsetIncludeUpdates() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCLUDEUPDATES$10, 0);
      }
   }

   public TrueFalseType getSqlSelectDistinct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SQLSELECTDISTINCT$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSqlSelectDistinct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SQLSELECTDISTINCT$12) != 0;
      }
   }

   public void setSqlSelectDistinct(TrueFalseType sqlSelectDistinct) {
      this.generatedSetterHelperImpl(sqlSelectDistinct, SQLSELECTDISTINCT$12, 0, (short)1);
   }

   public TrueFalseType addNewSqlSelectDistinct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SQLSELECTDISTINCT$12);
         return target;
      }
   }

   public void unsetSqlSelectDistinct() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SQLSELECTDISTINCT$12, 0);
      }
   }

   public TrueFalseType getEnableQueryCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEQUERYCACHING$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableQueryCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEQUERYCACHING$14) != 0;
      }
   }

   public void setEnableQueryCaching(TrueFalseType enableQueryCaching) {
      this.generatedSetterHelperImpl(enableQueryCaching, ENABLEQUERYCACHING$14, 0, (short)1);
   }

   public TrueFalseType addNewEnableQueryCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEQUERYCACHING$14);
         return target;
      }
   }

   public void unsetEnableQueryCaching() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEQUERYCACHING$14, 0);
      }
   }

   public TrueFalseType getEnableEagerRefresh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEEAGERREFRESH$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableEagerRefresh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEEAGERREFRESH$16) != 0;
      }
   }

   public void setEnableEagerRefresh(TrueFalseType enableEagerRefresh) {
      this.generatedSetterHelperImpl(enableEagerRefresh, ENABLEEAGERREFRESH$16, 0, (short)1);
   }

   public TrueFalseType addNewEnableEagerRefresh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEEAGERREFRESH$16);
         return target;
      }
   }

   public void unsetEnableEagerRefresh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEEAGERREFRESH$16, 0);
      }
   }

   public TrueFalseType getIncludeResultCacheHint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(INCLUDERESULTCACHEHINT$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIncludeResultCacheHint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCLUDERESULTCACHEHINT$18) != 0;
      }
   }

   public void setIncludeResultCacheHint(TrueFalseType includeResultCacheHint) {
      this.generatedSetterHelperImpl(includeResultCacheHint, INCLUDERESULTCACHEHINT$18, 0, (short)1);
   }

   public TrueFalseType addNewIncludeResultCacheHint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(INCLUDERESULTCACHEHINT$18);
         return target;
      }
   }

   public void unsetIncludeResultCacheHint() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCLUDERESULTCACHEHINT$18, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$20);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$20);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$20) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$20);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$20);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$20);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$20);
      }
   }
}
