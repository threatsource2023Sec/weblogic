package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.LibraryRefType;
import javax.xml.namespace.QName;

public class LibraryRefTypeImpl extends XmlComplexContentImpl implements LibraryRefType {
   private static final long serialVersionUID = 1L;
   private static final QName LIBRARYNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "library-name");
   private static final QName SPECIFICATIONVERSION$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "specification-version");
   private static final QName IMPLEMENTATIONVERSION$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "implementation-version");
   private static final QName EXACTMATCH$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "exact-match");
   private static final QName CONTEXTROOT$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "context-root");

   public LibraryRefTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLibraryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LIBRARYNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLibraryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LIBRARYNAME$0, 0);
         return target;
      }
   }

   public void setLibraryName(String libraryName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LIBRARYNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LIBRARYNAME$0);
         }

         target.setStringValue(libraryName);
      }
   }

   public void xsetLibraryName(XmlString libraryName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LIBRARYNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LIBRARYNAME$0);
         }

         target.set(libraryName);
      }
   }

   public String getSpecificationVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SPECIFICATIONVERSION$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSpecificationVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SPECIFICATIONVERSION$2, 0);
         return target;
      }
   }

   public boolean isSetSpecificationVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SPECIFICATIONVERSION$2) != 0;
      }
   }

   public void setSpecificationVersion(String specificationVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SPECIFICATIONVERSION$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SPECIFICATIONVERSION$2);
         }

         target.setStringValue(specificationVersion);
      }
   }

   public void xsetSpecificationVersion(XmlString specificationVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SPECIFICATIONVERSION$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SPECIFICATIONVERSION$2);
         }

         target.set(specificationVersion);
      }
   }

   public void unsetSpecificationVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SPECIFICATIONVERSION$2, 0);
      }
   }

   public String getImplementationVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IMPLEMENTATIONVERSION$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetImplementationVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(IMPLEMENTATIONVERSION$4, 0);
         return target;
      }
   }

   public boolean isSetImplementationVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IMPLEMENTATIONVERSION$4) != 0;
      }
   }

   public void setImplementationVersion(String implementationVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IMPLEMENTATIONVERSION$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IMPLEMENTATIONVERSION$4);
         }

         target.setStringValue(implementationVersion);
      }
   }

   public void xsetImplementationVersion(XmlString implementationVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(IMPLEMENTATIONVERSION$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(IMPLEMENTATIONVERSION$4);
         }

         target.set(implementationVersion);
      }
   }

   public void unsetImplementationVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IMPLEMENTATIONVERSION$4, 0);
      }
   }

   public boolean getExactMatch() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXACTMATCH$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetExactMatch() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(EXACTMATCH$6, 0);
         return target;
      }
   }

   public boolean isSetExactMatch() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXACTMATCH$6) != 0;
      }
   }

   public void setExactMatch(boolean exactMatch) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXACTMATCH$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EXACTMATCH$6);
         }

         target.setBooleanValue(exactMatch);
      }
   }

   public void xsetExactMatch(XmlBoolean exactMatch) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(EXACTMATCH$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(EXACTMATCH$6);
         }

         target.set(exactMatch);
      }
   }

   public void unsetExactMatch() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXACTMATCH$6, 0);
      }
   }

   public String getContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONTEXTROOT$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONTEXTROOT$8, 0);
         return target;
      }
   }

   public boolean isSetContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTEXTROOT$8) != 0;
      }
   }

   public void setContextRoot(String contextRoot) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONTEXTROOT$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONTEXTROOT$8);
         }

         target.setStringValue(contextRoot);
      }
   }

   public void xsetContextRoot(XmlString contextRoot) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONTEXTROOT$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONTEXTROOT$8);
         }

         target.set(contextRoot);
      }
   }

   public void unsetContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTEXTROOT$8, 0);
      }
   }
}
