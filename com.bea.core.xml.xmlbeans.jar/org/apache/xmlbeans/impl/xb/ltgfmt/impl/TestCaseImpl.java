package org.apache.xmlbeans.impl.xb.ltgfmt.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlBoolean;
import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.ltgfmt.FileDesc;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase;

public class TestCaseImpl extends XmlComplexContentImpl implements TestCase {
   private static final QName DESCRIPTION$0 = new QName("http://www.bea.com/2003/05/xmlbean/ltgfmt", "description");
   private static final QName FILES$2 = new QName("http://www.bea.com/2003/05/xmlbean/ltgfmt", "files");
   private static final QName ID$4 = new QName("", "id");
   private static final QName ORIGIN$6 = new QName("", "origin");
   private static final QName MODIFIED$8 = new QName("", "modified");

   public TestCaseImpl(SchemaType sType) {
      super(sType);
   }

   public String getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)DESCRIPTION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)DESCRIPTION$0, 0);
         return target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user((QName)DESCRIPTION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.setStringValue(description);
      }
   }

   public void xsetDescription(XmlString description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user((QName)DESCRIPTION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.set(description);
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)DESCRIPTION$0, 0);
      }
   }

   public TestCase.Files getFiles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TestCase.Files target = null;
         target = (TestCase.Files)this.get_store().find_element_user((QName)FILES$2, 0);
         return target == null ? null : target;
      }
   }

   public void setFiles(TestCase.Files files) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TestCase.Files target = null;
         target = (TestCase.Files)this.get_store().find_element_user((QName)FILES$2, 0);
         if (target == null) {
            target = (TestCase.Files)this.get_store().add_element_user(FILES$2);
         }

         target.set(files);
      }
   }

   public TestCase.Files addNewFiles() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TestCase.Files target = null;
         target = (TestCase.Files)this.get_store().add_element_user(FILES$2);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }

   public String getOrigin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ORIGIN$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlToken xgetOrigin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(ORIGIN$6);
         return target;
      }
   }

   public boolean isSetOrigin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ORIGIN$6) != null;
      }
   }

   public void setOrigin(String origin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ORIGIN$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ORIGIN$6);
         }

         target.setStringValue(origin);
      }
   }

   public void xsetOrigin(XmlToken origin) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(ORIGIN$6);
         if (target == null) {
            target = (XmlToken)this.get_store().add_attribute_user(ORIGIN$6);
         }

         target.set(origin);
      }
   }

   public void unsetOrigin() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ORIGIN$6);
      }
   }

   public boolean getModified() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MODIFIED$8);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetModified() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(MODIFIED$8);
         return target;
      }
   }

   public boolean isSetModified() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(MODIFIED$8) != null;
      }
   }

   public void setModified(boolean modified) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(MODIFIED$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(MODIFIED$8);
         }

         target.setBooleanValue(modified);
      }
   }

   public void xsetModified(XmlBoolean modified) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(MODIFIED$8);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(MODIFIED$8);
         }

         target.set(modified);
      }
   }

   public void unsetModified() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(MODIFIED$8);
      }
   }

   public static class FilesImpl extends XmlComplexContentImpl implements TestCase.Files {
      private static final QName FILE$0 = new QName("http://www.bea.com/2003/05/xmlbean/ltgfmt", "file");

      public FilesImpl(SchemaType sType) {
         super(sType);
      }

      public FileDesc[] getFileArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)FILE$0, targetList);
            FileDesc[] result = new FileDesc[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public FileDesc getFileArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FileDesc target = null;
            target = (FileDesc)this.get_store().find_element_user(FILE$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfFileArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(FILE$0);
         }
      }

      public void setFileArray(FileDesc[] fileArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(fileArray, FILE$0);
         }
      }

      public void setFileArray(int i, FileDesc file) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FileDesc target = null;
            target = (FileDesc)this.get_store().find_element_user(FILE$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(file);
            }
         }
      }

      public FileDesc insertNewFile(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FileDesc target = null;
            target = (FileDesc)this.get_store().insert_element_user(FILE$0, i);
            return target;
         }
      }

      public FileDesc addNewFile() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FileDesc target = null;
            target = (FileDesc)this.get_store().add_element_user(FILE$0);
            return target;
         }
      }

      public void removeFile(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(FILE$0, i);
         }
      }
   }
}
