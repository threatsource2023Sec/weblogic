package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.QueryMethodType;
import com.sun.java.xml.ns.j2Ee.QueryType;
import com.sun.java.xml.ns.j2Ee.ResultTypeMappingType;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import javax.xml.namespace.QName;

public class QueryTypeImpl extends XmlComplexContentImpl implements QueryType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName QUERYMETHOD$2 = new QName("http://java.sun.com/xml/ns/j2ee", "query-method");
   private static final QName RESULTTYPEMAPPING$4 = new QName("http://java.sun.com/xml/ns/j2ee", "result-type-mapping");
   private static final QName EJBQL$6 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-ql");
   private static final QName ID$8 = new QName("", "id");

   public QueryTypeImpl(SchemaType sType) {
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

   public ResultTypeMappingType getResultTypeMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResultTypeMappingType target = null;
         target = (ResultTypeMappingType)this.get_store().find_element_user(RESULTTYPEMAPPING$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResultTypeMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESULTTYPEMAPPING$4) != 0;
      }
   }

   public void setResultTypeMapping(ResultTypeMappingType resultTypeMapping) {
      this.generatedSetterHelperImpl(resultTypeMapping, RESULTTYPEMAPPING$4, 0, (short)1);
   }

   public ResultTypeMappingType addNewResultTypeMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResultTypeMappingType target = null;
         target = (ResultTypeMappingType)this.get_store().add_element_user(RESULTTYPEMAPPING$4);
         return target;
      }
   }

   public void unsetResultTypeMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESULTTYPEMAPPING$4, 0);
      }
   }

   public XsdStringType getEjbQl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(EJBQL$6, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbQl(XsdStringType ejbQl) {
      this.generatedSetterHelperImpl(ejbQl, EJBQL$6, 0, (short)1);
   }

   public XsdStringType addNewEjbQl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(EJBQL$6);
         return target;
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
