package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LogFactoryImplType;
import javax.xml.namespace.QName;

public class LogFactoryImplTypeImpl extends LogTypeImpl implements LogFactoryImplType {
   private static final long serialVersionUID = 1L;
   private static final QName DIAGNOSTICCONTEXT$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "diagnostic-context");
   private static final QName DEFAULTLEVEL$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-level");
   private static final QName FILE$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "file");

   public LogFactoryImplTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getDiagnosticContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DIAGNOSTICCONTEXT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDiagnosticContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DIAGNOSTICCONTEXT$0, 0);
         return target;
      }
   }

   public boolean isNilDiagnosticContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DIAGNOSTICCONTEXT$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDiagnosticContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DIAGNOSTICCONTEXT$0) != 0;
      }
   }

   public void setDiagnosticContext(String diagnosticContext) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DIAGNOSTICCONTEXT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DIAGNOSTICCONTEXT$0);
         }

         target.setStringValue(diagnosticContext);
      }
   }

   public void xsetDiagnosticContext(XmlString diagnosticContext) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DIAGNOSTICCONTEXT$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DIAGNOSTICCONTEXT$0);
         }

         target.set(diagnosticContext);
      }
   }

   public void setNilDiagnosticContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DIAGNOSTICCONTEXT$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DIAGNOSTICCONTEXT$0);
         }

         target.setNil();
      }
   }

   public void unsetDiagnosticContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DIAGNOSTICCONTEXT$0, 0);
      }
   }

   public String getDefaultLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTLEVEL$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefaultLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTLEVEL$2, 0);
         return target;
      }
   }

   public boolean isNilDefaultLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTLEVEL$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTLEVEL$2) != 0;
      }
   }

   public void setDefaultLevel(String defaultLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTLEVEL$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTLEVEL$2);
         }

         target.setStringValue(defaultLevel);
      }
   }

   public void xsetDefaultLevel(XmlString defaultLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTLEVEL$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTLEVEL$2);
         }

         target.set(defaultLevel);
      }
   }

   public void setNilDefaultLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTLEVEL$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTLEVEL$2);
         }

         target.setNil();
      }
   }

   public void unsetDefaultLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTLEVEL$2, 0);
      }
   }

   public String getFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILE$4, 0);
         return target;
      }
   }

   public boolean isNilFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILE$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILE$4) != 0;
      }
   }

   public void setFile(String file) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FILE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILE$4);
         }

         target.setStringValue(file);
      }
   }

   public void xsetFile(XmlString file) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FILE$4);
         }

         target.set(file);
      }
   }

   public void setNilFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FILE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FILE$4);
         }

         target.setNil();
      }
   }

   public void unsetFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILE$4, 0);
      }
   }
}
