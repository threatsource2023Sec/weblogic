package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoCompatibilityType;
import javax.xml.namespace.QName;

public class KodoCompatibilityTypeImpl extends XmlComplexContentImpl implements KodoCompatibilityType {
   private static final long serialVersionUID = 1L;
   private static final QName COPYOBJECTIDS$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "copy-object-ids");
   private static final QName CLOSEONMANAGEDCOMMIT$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "close-on-managed-commit");
   private static final QName VALIDATETRUECHECKSSTORE$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "validate-true-checks-store");
   private static final QName VALIDATEFALSERETURNSHOLLOW$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "validate-false-returns-hollow");
   private static final QName STRICTIDENTITYVALUES$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "strict-identity-values");
   private static final QName QUOTEDNUMBERSINQUERIES$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "quoted-numbers-in-queries");

   public KodoCompatibilityTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getCopyObjectIds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COPYOBJECTIDS$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCopyObjectIds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(COPYOBJECTIDS$0, 0);
         return target;
      }
   }

   public boolean isSetCopyObjectIds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COPYOBJECTIDS$0) != 0;
      }
   }

   public void setCopyObjectIds(boolean copyObjectIds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COPYOBJECTIDS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COPYOBJECTIDS$0);
         }

         target.setBooleanValue(copyObjectIds);
      }
   }

   public void xsetCopyObjectIds(XmlBoolean copyObjectIds) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(COPYOBJECTIDS$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(COPYOBJECTIDS$0);
         }

         target.set(copyObjectIds);
      }
   }

   public void unsetCopyObjectIds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COPYOBJECTIDS$0, 0);
      }
   }

   public boolean getCloseOnManagedCommit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLOSEONMANAGEDCOMMIT$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCloseOnManagedCommit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CLOSEONMANAGEDCOMMIT$2, 0);
         return target;
      }
   }

   public boolean isSetCloseOnManagedCommit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLOSEONMANAGEDCOMMIT$2) != 0;
      }
   }

   public void setCloseOnManagedCommit(boolean closeOnManagedCommit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLOSEONMANAGEDCOMMIT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLOSEONMANAGEDCOMMIT$2);
         }

         target.setBooleanValue(closeOnManagedCommit);
      }
   }

   public void xsetCloseOnManagedCommit(XmlBoolean closeOnManagedCommit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CLOSEONMANAGEDCOMMIT$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CLOSEONMANAGEDCOMMIT$2);
         }

         target.set(closeOnManagedCommit);
      }
   }

   public void unsetCloseOnManagedCommit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLOSEONMANAGEDCOMMIT$2, 0);
      }
   }

   public boolean getValidateTrueChecksStore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATETRUECHECKSSTORE$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetValidateTrueChecksStore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VALIDATETRUECHECKSSTORE$4, 0);
         return target;
      }
   }

   public boolean isSetValidateTrueChecksStore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALIDATETRUECHECKSSTORE$4) != 0;
      }
   }

   public void setValidateTrueChecksStore(boolean validateTrueChecksStore) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATETRUECHECKSSTORE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALIDATETRUECHECKSSTORE$4);
         }

         target.setBooleanValue(validateTrueChecksStore);
      }
   }

   public void xsetValidateTrueChecksStore(XmlBoolean validateTrueChecksStore) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VALIDATETRUECHECKSSTORE$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(VALIDATETRUECHECKSSTORE$4);
         }

         target.set(validateTrueChecksStore);
      }
   }

   public void unsetValidateTrueChecksStore() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALIDATETRUECHECKSSTORE$4, 0);
      }
   }

   public boolean getValidateFalseReturnsHollow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATEFALSERETURNSHOLLOW$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetValidateFalseReturnsHollow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VALIDATEFALSERETURNSHOLLOW$6, 0);
         return target;
      }
   }

   public boolean isSetValidateFalseReturnsHollow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALIDATEFALSERETURNSHOLLOW$6) != 0;
      }
   }

   public void setValidateFalseReturnsHollow(boolean validateFalseReturnsHollow) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VALIDATEFALSERETURNSHOLLOW$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VALIDATEFALSERETURNSHOLLOW$6);
         }

         target.setBooleanValue(validateFalseReturnsHollow);
      }
   }

   public void xsetValidateFalseReturnsHollow(XmlBoolean validateFalseReturnsHollow) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(VALIDATEFALSERETURNSHOLLOW$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(VALIDATEFALSERETURNSHOLLOW$6);
         }

         target.set(validateFalseReturnsHollow);
      }
   }

   public void unsetValidateFalseReturnsHollow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALIDATEFALSERETURNSHOLLOW$6, 0);
      }
   }

   public boolean getStrictIdentityValues() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRICTIDENTITYVALUES$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetStrictIdentityValues() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STRICTIDENTITYVALUES$8, 0);
         return target;
      }
   }

   public boolean isSetStrictIdentityValues() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STRICTIDENTITYVALUES$8) != 0;
      }
   }

   public void setStrictIdentityValues(boolean strictIdentityValues) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRICTIDENTITYVALUES$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STRICTIDENTITYVALUES$8);
         }

         target.setBooleanValue(strictIdentityValues);
      }
   }

   public void xsetStrictIdentityValues(XmlBoolean strictIdentityValues) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(STRICTIDENTITYVALUES$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(STRICTIDENTITYVALUES$8);
         }

         target.set(strictIdentityValues);
      }
   }

   public void unsetStrictIdentityValues() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STRICTIDENTITYVALUES$8, 0);
      }
   }

   public boolean getQuotedNumbersInQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(QUOTEDNUMBERSINQUERIES$10, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetQuotedNumbersInQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(QUOTEDNUMBERSINQUERIES$10, 0);
         return target;
      }
   }

   public boolean isSetQuotedNumbersInQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QUOTEDNUMBERSINQUERIES$10) != 0;
      }
   }

   public void setQuotedNumbersInQueries(boolean quotedNumbersInQueries) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(QUOTEDNUMBERSINQUERIES$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(QUOTEDNUMBERSINQUERIES$10);
         }

         target.setBooleanValue(quotedNumbersInQueries);
      }
   }

   public void xsetQuotedNumbersInQueries(XmlBoolean quotedNumbersInQueries) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(QUOTEDNUMBERSINQUERIES$10, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(QUOTEDNUMBERSINQUERIES$10);
         }

         target.set(quotedNumbersInQueries);
      }
   }

   public void unsetQuotedNumbersInQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUOTEDNUMBERSINQUERIES$10, 0);
      }
   }
}
