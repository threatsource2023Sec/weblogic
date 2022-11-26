package com.oracle.xmlns.weblogic.jdbcDataSource.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.jdbcDataSource.OsgiFrameworkReferenceType;
import javax.xml.namespace.QName;

public class OsgiFrameworkReferenceTypeImpl extends XmlComplexContentImpl implements OsgiFrameworkReferenceType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "name");
   private static final QName BUNDLESDIRECTORY$2 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "bundles-directory");
   private static final QName APPLICATIONBUNDLESYMBOLICNAME$4 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "application-bundle-symbolic-name");
   private static final QName APPLICATIONBUNDLEVERSION$6 = new QName("http://xmlns.oracle.com/weblogic/jdbc-data-source", "application-bundle-version");

   public OsgiFrameworkReferenceTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
      }
   }

   public String getBundlesDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BUNDLESDIRECTORY$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBundlesDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BUNDLESDIRECTORY$2, 0);
         return target;
      }
   }

   public boolean isSetBundlesDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BUNDLESDIRECTORY$2) != 0;
      }
   }

   public void setBundlesDirectory(String bundlesDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BUNDLESDIRECTORY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BUNDLESDIRECTORY$2);
         }

         target.setStringValue(bundlesDirectory);
      }
   }

   public void xsetBundlesDirectory(XmlString bundlesDirectory) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BUNDLESDIRECTORY$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BUNDLESDIRECTORY$2);
         }

         target.set(bundlesDirectory);
      }
   }

   public void unsetBundlesDirectory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BUNDLESDIRECTORY$2, 0);
      }
   }

   public String getApplicationBundleSymbolicName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(APPLICATIONBUNDLESYMBOLICNAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetApplicationBundleSymbolicName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(APPLICATIONBUNDLESYMBOLICNAME$4, 0);
         return target;
      }
   }

   public boolean isSetApplicationBundleSymbolicName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(APPLICATIONBUNDLESYMBOLICNAME$4) != 0;
      }
   }

   public void setApplicationBundleSymbolicName(String applicationBundleSymbolicName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(APPLICATIONBUNDLESYMBOLICNAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(APPLICATIONBUNDLESYMBOLICNAME$4);
         }

         target.setStringValue(applicationBundleSymbolicName);
      }
   }

   public void xsetApplicationBundleSymbolicName(XmlString applicationBundleSymbolicName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(APPLICATIONBUNDLESYMBOLICNAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(APPLICATIONBUNDLESYMBOLICNAME$4);
         }

         target.set(applicationBundleSymbolicName);
      }
   }

   public void unsetApplicationBundleSymbolicName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(APPLICATIONBUNDLESYMBOLICNAME$4, 0);
      }
   }

   public String getApplicationBundleVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(APPLICATIONBUNDLEVERSION$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetApplicationBundleVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(APPLICATIONBUNDLEVERSION$6, 0);
         return target;
      }
   }

   public boolean isSetApplicationBundleVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(APPLICATIONBUNDLEVERSION$6) != 0;
      }
   }

   public void setApplicationBundleVersion(String applicationBundleVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(APPLICATIONBUNDLEVERSION$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(APPLICATIONBUNDLEVERSION$6);
         }

         target.setStringValue(applicationBundleVersion);
      }
   }

   public void xsetApplicationBundleVersion(XmlString applicationBundleVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(APPLICATIONBUNDLEVERSION$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(APPLICATIONBUNDLEVERSION$6);
         }

         target.set(applicationBundleVersion);
      }
   }

   public void unsetApplicationBundleVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(APPLICATIONBUNDLEVERSION$6, 0);
      }
   }
}
