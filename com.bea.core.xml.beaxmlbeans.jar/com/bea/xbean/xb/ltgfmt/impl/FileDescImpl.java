package com.bea.xbean.xb.ltgfmt.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.ltgfmt.Code;
import com.bea.xbean.xb.ltgfmt.FileDesc;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlToken;
import javax.xml.namespace.QName;

public class FileDescImpl extends XmlComplexContentImpl implements FileDesc {
   private static final QName CODE$0 = new QName("http://www.bea.com/2003/05/xmlbean/ltgfmt", "code");
   private static final QName TSDIR$2 = new QName("", "tsDir");
   private static final QName FOLDER$4 = new QName("", "folder");
   private static final QName FILENAME$6 = new QName("", "fileName");
   private static final QName ROLE$8 = new QName("", "role");
   private static final QName VALIDITY$10 = new QName("", "validity");

   public FileDescImpl(SchemaType sType) {
      super(sType);
   }

   public Code getCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Code target = null;
         target = (Code)this.get_store().find_element_user((QName)CODE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CODE$0) != 0;
      }
   }

   public void setCode(Code code) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Code target = null;
         target = (Code)this.get_store().find_element_user((QName)CODE$0, 0);
         if (target == null) {
            target = (Code)this.get_store().add_element_user(CODE$0);
         }

         target.set(code);
      }
   }

   public Code addNewCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Code target = null;
         target = (Code)this.get_store().add_element_user(CODE$0);
         return target;
      }
   }

   public void unsetCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element((QName)CODE$0, 0);
      }
   }

   public String getTsDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TSDIR$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlToken xgetTsDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(TSDIR$2);
         return target;
      }
   }

   public boolean isSetTsDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(TSDIR$2) != null;
      }
   }

   public void setTsDir(String tsDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(TSDIR$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(TSDIR$2);
         }

         target.setStringValue(tsDir);
      }
   }

   public void xsetTsDir(XmlToken tsDir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(TSDIR$2);
         if (target == null) {
            target = (XmlToken)this.get_store().add_attribute_user(TSDIR$2);
         }

         target.set(tsDir);
      }
   }

   public void unsetTsDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(TSDIR$2);
      }
   }

   public String getFolder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FOLDER$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlToken xgetFolder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(FOLDER$4);
         return target;
      }
   }

   public boolean isSetFolder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FOLDER$4) != null;
      }
   }

   public void setFolder(String folder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FOLDER$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FOLDER$4);
         }

         target.setStringValue(folder);
      }
   }

   public void xsetFolder(XmlToken folder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(FOLDER$4);
         if (target == null) {
            target = (XmlToken)this.get_store().add_attribute_user(FOLDER$4);
         }

         target.set(folder);
      }
   }

   public void unsetFolder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FOLDER$4);
      }
   }

   public String getFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FILENAME$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlToken xgetFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(FILENAME$6);
         return target;
      }
   }

   public boolean isSetFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(FILENAME$6) != null;
      }
   }

   public void setFileName(String fileName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(FILENAME$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(FILENAME$6);
         }

         target.setStringValue(fileName);
      }
   }

   public void xsetFileName(XmlToken fileName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlToken target = null;
         target = (XmlToken)this.get_store().find_attribute_user(FILENAME$6);
         if (target == null) {
            target = (XmlToken)this.get_store().add_attribute_user(FILENAME$6);
         }

         target.set(fileName);
      }
   }

   public void unsetFileName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(FILENAME$6);
      }
   }

   public FileDesc.Role.Enum getRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ROLE$8);
         return target == null ? null : (FileDesc.Role.Enum)target.getEnumValue();
      }
   }

   public FileDesc.Role xgetRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FileDesc.Role target = null;
         target = (FileDesc.Role)this.get_store().find_attribute_user(ROLE$8);
         return target;
      }
   }

   public boolean isSetRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ROLE$8) != null;
      }
   }

   public void setRole(FileDesc.Role.Enum role) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ROLE$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ROLE$8);
         }

         target.setEnumValue(role);
      }
   }

   public void xsetRole(FileDesc.Role role) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FileDesc.Role target = null;
         target = (FileDesc.Role)this.get_store().find_attribute_user(ROLE$8);
         if (target == null) {
            target = (FileDesc.Role)this.get_store().add_attribute_user(ROLE$8);
         }

         target.set(role);
      }
   }

   public void unsetRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ROLE$8);
      }
   }

   public boolean getValidity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VALIDITY$10);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetValidity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(VALIDITY$10);
         return target;
      }
   }

   public boolean isSetValidity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VALIDITY$10) != null;
      }
   }

   public void setValidity(boolean validity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VALIDITY$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VALIDITY$10);
         }

         target.setBooleanValue(validity);
      }
   }

   public void xsetValidity(XmlBoolean validity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(VALIDITY$10);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(VALIDITY$10);
         }

         target.set(validity);
      }
   }

   public void unsetValidity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VALIDITY$10);
      }
   }

   public static class RoleImpl extends JavaStringEnumerationHolderEx implements FileDesc.Role {
      public RoleImpl(SchemaType sType) {
         super(sType, false);
      }

      protected RoleImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
