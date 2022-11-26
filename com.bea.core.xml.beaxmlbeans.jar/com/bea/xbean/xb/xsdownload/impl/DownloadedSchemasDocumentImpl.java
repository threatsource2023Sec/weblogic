package com.bea.xbean.xb.xsdownload.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdownload.DownloadedSchemaEntry;
import com.bea.xbean.xb.xsdownload.DownloadedSchemasDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlToken;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DownloadedSchemasDocumentImpl extends XmlComplexContentImpl implements DownloadedSchemasDocument {
   private static final QName DOWNLOADEDSCHEMAS$0 = new QName("http://www.bea.com/2003/01/xmlbean/xsdownload", "downloaded-schemas");

   public DownloadedSchemasDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public DownloadedSchemasDocument.DownloadedSchemas getDownloadedSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DownloadedSchemasDocument.DownloadedSchemas target = null;
         target = (DownloadedSchemasDocument.DownloadedSchemas)this.get_store().find_element_user((QName)DOWNLOADEDSCHEMAS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDownloadedSchemas(DownloadedSchemasDocument.DownloadedSchemas downloadedSchemas) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DownloadedSchemasDocument.DownloadedSchemas target = null;
         target = (DownloadedSchemasDocument.DownloadedSchemas)this.get_store().find_element_user((QName)DOWNLOADEDSCHEMAS$0, 0);
         if (target == null) {
            target = (DownloadedSchemasDocument.DownloadedSchemas)this.get_store().add_element_user(DOWNLOADEDSCHEMAS$0);
         }

         target.set(downloadedSchemas);
      }
   }

   public DownloadedSchemasDocument.DownloadedSchemas addNewDownloadedSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DownloadedSchemasDocument.DownloadedSchemas target = null;
         target = (DownloadedSchemasDocument.DownloadedSchemas)this.get_store().add_element_user(DOWNLOADEDSCHEMAS$0);
         return target;
      }
   }

   public static class DownloadedSchemasImpl extends XmlComplexContentImpl implements DownloadedSchemasDocument.DownloadedSchemas {
      private static final QName ENTRY$0 = new QName("http://www.bea.com/2003/01/xmlbean/xsdownload", "entry");
      private static final QName DEFAULTDIRECTORY$2 = new QName("", "defaultDirectory");

      public DownloadedSchemasImpl(SchemaType sType) {
         super(sType);
      }

      public DownloadedSchemaEntry[] getEntryArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)ENTRY$0, targetList);
            DownloadedSchemaEntry[] result = new DownloadedSchemaEntry[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public DownloadedSchemaEntry getEntryArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DownloadedSchemaEntry target = null;
            target = (DownloadedSchemaEntry)this.get_store().find_element_user(ENTRY$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfEntryArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(ENTRY$0);
         }
      }

      public void setEntryArray(DownloadedSchemaEntry[] entryArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(entryArray, ENTRY$0);
         }
      }

      public void setEntryArray(int i, DownloadedSchemaEntry entry) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DownloadedSchemaEntry target = null;
            target = (DownloadedSchemaEntry)this.get_store().find_element_user(ENTRY$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(entry);
            }
         }
      }

      public DownloadedSchemaEntry insertNewEntry(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DownloadedSchemaEntry target = null;
            target = (DownloadedSchemaEntry)this.get_store().insert_element_user(ENTRY$0, i);
            return target;
         }
      }

      public DownloadedSchemaEntry addNewEntry() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DownloadedSchemaEntry target = null;
            target = (DownloadedSchemaEntry)this.get_store().add_element_user(ENTRY$0);
            return target;
         }
      }

      public void removeEntry(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(ENTRY$0, i);
         }
      }

      public String getDefaultDirectory() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(DEFAULTDIRECTORY$2);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlToken xgetDefaultDirectory() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlToken target = null;
            target = (XmlToken)this.get_store().find_attribute_user(DEFAULTDIRECTORY$2);
            return target;
         }
      }

      public boolean isSetDefaultDirectory() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(DEFAULTDIRECTORY$2) != null;
         }
      }

      public void setDefaultDirectory(String defaultDirectory) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(DEFAULTDIRECTORY$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(DEFAULTDIRECTORY$2);
            }

            target.setStringValue(defaultDirectory);
         }
      }

      public void xsetDefaultDirectory(XmlToken defaultDirectory) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlToken target = null;
            target = (XmlToken)this.get_store().find_attribute_user(DEFAULTDIRECTORY$2);
            if (target == null) {
               target = (XmlToken)this.get_store().add_attribute_user(DEFAULTDIRECTORY$2);
            }

            target.set(defaultDirectory);
         }
      }

      public void unsetDefaultDirectory() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(DEFAULTDIRECTORY$2);
         }
      }
   }
}
