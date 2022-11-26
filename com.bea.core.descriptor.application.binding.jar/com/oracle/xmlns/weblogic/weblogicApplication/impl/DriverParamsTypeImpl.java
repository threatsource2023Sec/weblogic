package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.weblogicApplication.DriverParamsType;
import com.oracle.xmlns.weblogic.weblogicApplication.PreparedStatementType;
import com.oracle.xmlns.weblogic.weblogicApplication.StatementType;
import com.oracle.xmlns.weblogic.weblogicApplication.TrueFalseType;
import javax.xml.namespace.QName;

public class DriverParamsTypeImpl extends XmlComplexContentImpl implements DriverParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName STATEMENT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "statement");
   private static final QName PREPAREDSTATEMENT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "prepared-statement");
   private static final QName ROWPREFETCHENABLED$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "row-prefetch-enabled");
   private static final QName ROWPREFETCHSIZE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "row-prefetch-size");
   private static final QName STREAMCHUNKSIZE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "stream-chunk-size");

   public DriverParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public StatementType getStatement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatementType target = null;
         target = (StatementType)this.get_store().find_element_user(STATEMENT$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATEMENT$0) != 0;
      }
   }

   public void setStatement(StatementType statement) {
      this.generatedSetterHelperImpl(statement, STATEMENT$0, 0, (short)1);
   }

   public StatementType addNewStatement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatementType target = null;
         target = (StatementType)this.get_store().add_element_user(STATEMENT$0);
         return target;
      }
   }

   public void unsetStatement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATEMENT$0, 0);
      }
   }

   public PreparedStatementType getPreparedStatement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreparedStatementType target = null;
         target = (PreparedStatementType)this.get_store().find_element_user(PREPAREDSTATEMENT$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPreparedStatement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PREPAREDSTATEMENT$2) != 0;
      }
   }

   public void setPreparedStatement(PreparedStatementType preparedStatement) {
      this.generatedSetterHelperImpl(preparedStatement, PREPAREDSTATEMENT$2, 0, (short)1);
   }

   public PreparedStatementType addNewPreparedStatement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PreparedStatementType target = null;
         target = (PreparedStatementType)this.get_store().add_element_user(PREPAREDSTATEMENT$2);
         return target;
      }
   }

   public void unsetPreparedStatement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PREPAREDSTATEMENT$2, 0);
      }
   }

   public TrueFalseType getRowPrefetchEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ROWPREFETCHENABLED$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRowPrefetchEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROWPREFETCHENABLED$4) != 0;
      }
   }

   public void setRowPrefetchEnabled(TrueFalseType rowPrefetchEnabled) {
      this.generatedSetterHelperImpl(rowPrefetchEnabled, ROWPREFETCHENABLED$4, 0, (short)1);
   }

   public TrueFalseType addNewRowPrefetchEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ROWPREFETCHENABLED$4);
         return target;
      }
   }

   public void unsetRowPrefetchEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROWPREFETCHENABLED$4, 0);
      }
   }

   public int getRowPrefetchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROWPREFETCHSIZE$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetRowPrefetchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(ROWPREFETCHSIZE$6, 0);
         return target;
      }
   }

   public boolean isSetRowPrefetchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROWPREFETCHSIZE$6) != 0;
      }
   }

   public void setRowPrefetchSize(int rowPrefetchSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROWPREFETCHSIZE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ROWPREFETCHSIZE$6);
         }

         target.setIntValue(rowPrefetchSize);
      }
   }

   public void xsetRowPrefetchSize(XmlInt rowPrefetchSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(ROWPREFETCHSIZE$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(ROWPREFETCHSIZE$6);
         }

         target.set(rowPrefetchSize);
      }
   }

   public void unsetRowPrefetchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROWPREFETCHSIZE$6, 0);
      }
   }

   public int getStreamChunkSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STREAMCHUNKSIZE$8, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetStreamChunkSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(STREAMCHUNKSIZE$8, 0);
         return target;
      }
   }

   public boolean isSetStreamChunkSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STREAMCHUNKSIZE$8) != 0;
      }
   }

   public void setStreamChunkSize(int streamChunkSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STREAMCHUNKSIZE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STREAMCHUNKSIZE$8);
         }

         target.setIntValue(streamChunkSize);
      }
   }

   public void xsetStreamChunkSize(XmlInt streamChunkSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(STREAMCHUNKSIZE$8, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(STREAMCHUNKSIZE$8);
         }

         target.set(streamChunkSize);
      }
   }

   public void unsetStreamChunkSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STREAMCHUNKSIZE$8, 0);
      }
   }
}
