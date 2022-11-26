package com.bea.xbean.xb.xsdownload.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdownload.DownloadedSchemaEntry;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlAnyURI;
import com.bea.xml.XmlToken;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DownloadedSchemaEntryImpl extends XmlComplexContentImpl implements DownloadedSchemaEntry {
   private static final QName FILENAME$0 = new QName("http://www.bea.com/2003/01/xmlbean/xsdownload", "filename");
   private static final QName SHA1$2 = new QName("http://www.bea.com/2003/01/xmlbean/xsdownload", "sha1");
   private static final QName SCHEMALOCATION$4 = new QName("http://www.bea.com/2003/01/xmlbean/xsdownload", "schemaLocation");
   private static final QName NAMESPACE$6 = new QName("http://www.bea.com/2003/01/xmlbean/xsdownload", "namespace");

   public DownloadedSchemaEntryImpl(SchemaType sType) {
      super(sType);
   }

   public String getFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)FILENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlToken xgetFilename() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_element_user((QName)FILENAME$0, 0);
         return target;
      }
   }

   public void setFilename(String filename) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)FILENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FILENAME$0);
         }

         target.setStringValue(filename);
      }
   }

   public void xsetFilename(XmlToken filename) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_element_user((QName)FILENAME$0, 0);
         if (target == null) {
            target = (XmlToken)this.get_store().add_element_user(FILENAME$0);
         }

         target.set(filename);
      }
   }

   public String getSha1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)SHA1$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlToken xgetSha1() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_element_user((QName)SHA1$2, 0);
         return target;
      }
   }

   public void setSha1(String sha1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)SHA1$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHA1$2);
         }

         target.setStringValue(sha1);
      }
   }

   public void xsetSha1(XmlToken sha1) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_element_user((QName)SHA1$2, 0);
         if (target == null) {
            target = (XmlToken)this.get_store().add_element_user(SHA1$2);
         }

         target.set(sha1);
      }
   }

   public String[] getSchemaLocationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)SCHEMALOCATION$4, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getSchemaLocationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMALOCATION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlAnyURI[] xgetSchemaLocationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users((QName)SCHEMALOCATION$4, targetList);
         XmlAnyURI[] result = new XmlAnyURI[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlAnyURI xgetSchemaLocationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_element_user(SCHEMALOCATION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSchemaLocationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCHEMALOCATION$4);
      }
   }

   public void setSchemaLocationArray(String[] schemaLocationArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(schemaLocationArray, SCHEMALOCATION$4);
      }
   }

   public void setSchemaLocationArray(int i, String schemaLocation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMALOCATION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(schemaLocation);
         }
      }
   }

   public void xsetSchemaLocationArray(XmlAnyURI[] schemaLocationArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(schemaLocationArray, SCHEMALOCATION$4);
      }
   }

   public void xsetSchemaLocationArray(int i, XmlAnyURI schemaLocation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_element_user(SCHEMALOCATION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(schemaLocation);
         }
      }
   }

   public void insertSchemaLocation(int i, String schemaLocation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(SCHEMALOCATION$4, i);
         target.setStringValue(schemaLocation);
      }
   }

   public void addSchemaLocation(String schemaLocation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(SCHEMALOCATION$4);
         target.setStringValue(schemaLocation);
      }
   }

   public XmlAnyURI insertNewSchemaLocation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().insert_element_user(SCHEMALOCATION$4, i);
         return target;
      }
   }

   public XmlAnyURI addNewSchemaLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().add_element_user(SCHEMALOCATION$4);
         return target;
      }
   }

   public void removeSchemaLocation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCHEMALOCATION$4, i);
      }
   }

   public String getNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)NAMESPACE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlAnyURI xgetNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_element_user((QName)NAMESPACE$6, 0);
         return target;
      }
   }

   public boolean isSetNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NAMESPACE$6) != 0;
      }
   }

   public void setNamespace(String namespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)NAMESPACE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAMESPACE$6);
         }

         target.setStringValue(namespace);
      }
   }

   public void xsetNamespace(XmlAnyURI namespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlAnyURI target = null;
         target = (XmlAnyURI)this.get_store().find_element_user((QName)NAMESPACE$6, 0);
         if (target == null) {
            target = (XmlAnyURI)this.get_store().add_element_user(NAMESPACE$6);
         }

         target.set(namespace);
      }
   }

   public void unsetNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)NAMESPACE$6, 0);
      }
   }
}
