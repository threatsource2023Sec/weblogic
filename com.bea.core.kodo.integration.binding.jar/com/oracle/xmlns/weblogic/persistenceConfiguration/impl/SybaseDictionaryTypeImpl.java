package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SybaseDictionaryType;
import javax.xml.namespace.QName;

public class SybaseDictionaryTypeImpl extends BuiltInDbdictionaryTypeImpl implements SybaseDictionaryType {
   private static final long serialVersionUID = 1L;
   private static final QName CREATEIDENTITYCOLUMN$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "create-identity-column");
   private static final QName IDENTITYCOLUMNNAME$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "identity-column-name");

   public SybaseDictionaryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getCreateIdentityColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CREATEIDENTITYCOLUMN$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetCreateIdentityColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CREATEIDENTITYCOLUMN$0, 0);
         return target;
      }
   }

   public boolean isSetCreateIdentityColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CREATEIDENTITYCOLUMN$0) != 0;
      }
   }

   public void setCreateIdentityColumn(boolean createIdentityColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CREATEIDENTITYCOLUMN$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CREATEIDENTITYCOLUMN$0);
         }

         target.setBooleanValue(createIdentityColumn);
      }
   }

   public void xsetCreateIdentityColumn(XmlBoolean createIdentityColumn) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CREATEIDENTITYCOLUMN$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CREATEIDENTITYCOLUMN$0);
         }

         target.set(createIdentityColumn);
      }
   }

   public void unsetCreateIdentityColumn() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CREATEIDENTITYCOLUMN$0, 0);
      }
   }

   public String getIdentityColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IDENTITYCOLUMNNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetIdentityColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(IDENTITYCOLUMNNAME$2, 0);
         return target;
      }
   }

   public boolean isSetIdentityColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IDENTITYCOLUMNNAME$2) != 0;
      }
   }

   public void setIdentityColumnName(String identityColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IDENTITYCOLUMNNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IDENTITYCOLUMNNAME$2);
         }

         target.setStringValue(identityColumnName);
      }
   }

   public void xsetIdentityColumnName(XmlString identityColumnName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(IDENTITYCOLUMNNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(IDENTITYCOLUMNNAME$2);
         }

         target.set(identityColumnName);
      }
   }

   public void unsetIdentityColumnName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IDENTITYCOLUMNNAME$2, 0);
      }
   }
}
