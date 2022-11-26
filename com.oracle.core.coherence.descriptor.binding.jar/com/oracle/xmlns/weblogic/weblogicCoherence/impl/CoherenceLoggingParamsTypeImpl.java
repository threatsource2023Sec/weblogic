package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceLoggingParamsType;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import javax.xml.namespace.QName;

public class CoherenceLoggingParamsTypeImpl extends XmlComplexContentImpl implements CoherenceLoggingParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "enabled");
   private static final QName LOGGERNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "logger-name");
   private static final QName MESSAGEFORMAT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "message-format");

   public CoherenceLoggingParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLED$0) != 0;
      }
   }

   public void setEnabled(TrueFalseType enabled) {
      this.generatedSetterHelperImpl(enabled, ENABLED$0, 0, (short)1);
   }

   public TrueFalseType addNewEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLED$0);
         return target;
      }
   }

   public void unsetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLED$0, 0);
      }
   }

   public String getLoggerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGGERNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLoggerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGGERNAME$2, 0);
         return target;
      }
   }

   public boolean isNilLoggerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGGERNAME$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLoggerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGGERNAME$2) != 0;
      }
   }

   public void setLoggerName(String loggerName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGGERNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGGERNAME$2);
         }

         target.setStringValue(loggerName);
      }
   }

   public void xsetLoggerName(XmlString loggerName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGGERNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOGGERNAME$2);
         }

         target.set(loggerName);
      }
   }

   public void setNilLoggerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGGERNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOGGERNAME$2);
         }

         target.setNil();
      }
   }

   public void unsetLoggerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGGERNAME$2, 0);
      }
   }

   public String getMessageFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGEFORMAT$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMessageFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGEFORMAT$4, 0);
         return target;
      }
   }

   public boolean isNilMessageFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGEFORMAT$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMessageFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGEFORMAT$4) != 0;
      }
   }

   public void setMessageFormat(String messageFormat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGEFORMAT$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGEFORMAT$4);
         }

         target.setStringValue(messageFormat);
      }
   }

   public void xsetMessageFormat(XmlString messageFormat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGEFORMAT$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MESSAGEFORMAT$4);
         }

         target.set(messageFormat);
      }
   }

   public void setNilMessageFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGEFORMAT$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MESSAGEFORMAT$4);
         }

         target.setNil();
      }
   }

   public void unsetMessageFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGEFORMAT$4, 0);
      }
   }
}
