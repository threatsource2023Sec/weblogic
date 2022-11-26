package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.SafErrorHandlingType;
import javax.xml.namespace.QName;

public class SafErrorHandlingTypeImpl extends NamedEntityTypeImpl implements SafErrorHandlingType {
   private static final long serialVersionUID = 1L;
   private static final QName POLICY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "policy");
   private static final QName LOGFORMAT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "log-format");
   private static final QName SAFERRORDESTINATION$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-error-destination");

   public SafErrorHandlingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(POLICY$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(POLICY$0, 0);
         return target;
      }
   }

   public boolean isNilPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(POLICY$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POLICY$0) != 0;
      }
   }

   public void setPolicy(String policy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(POLICY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(POLICY$0);
         }

         target.setStringValue(policy);
      }
   }

   public void xsetPolicy(XmlString policy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(POLICY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(POLICY$0);
         }

         target.set(policy);
      }
   }

   public void setNilPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(POLICY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(POLICY$0);
         }

         target.setNil();
      }
   }

   public void unsetPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POLICY$0, 0);
      }
   }

   public String getLogFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGFORMAT$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLogFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGFORMAT$2, 0);
         return target;
      }
   }

   public boolean isNilLogFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGFORMAT$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLogFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGFORMAT$2) != 0;
      }
   }

   public void setLogFormat(String logFormat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGFORMAT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGFORMAT$2);
         }

         target.setStringValue(logFormat);
      }
   }

   public void xsetLogFormat(XmlString logFormat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGFORMAT$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOGFORMAT$2);
         }

         target.set(logFormat);
      }
   }

   public void setNilLogFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGFORMAT$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOGFORMAT$2);
         }

         target.setNil();
      }
   }

   public void unsetLogFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGFORMAT$2, 0);
      }
   }

   public String getSafErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFERRORDESTINATION$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSafErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORDESTINATION$4, 0);
         return target;
      }
   }

   public boolean isNilSafErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORDESTINATION$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSafErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFERRORDESTINATION$4) != 0;
      }
   }

   public void setSafErrorDestination(String safErrorDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFERRORDESTINATION$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SAFERRORDESTINATION$4);
         }

         target.setStringValue(safErrorDestination);
      }
   }

   public void xsetSafErrorDestination(XmlString safErrorDestination) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORDESTINATION$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAFERRORDESTINATION$4);
         }

         target.set(safErrorDestination);
      }
   }

   public void setNilSafErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORDESTINATION$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAFERRORDESTINATION$4);
         }

         target.setNil();
      }
   }

   public void unsetSafErrorDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFERRORDESTINATION$4, 0);
      }
   }
}
