package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.ConnectorType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DeweyVersionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.LicenseType;
import com.sun.java.xml.ns.j2Ee.ResourceadapterType;
import com.sun.java.xml.ns.j2Ee.XsdStringType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectorTypeImpl extends XmlComplexContentImpl implements ConnectorType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName VENDORNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "vendor-name");
   private static final QName EISTYPE$8 = new QName("http://java.sun.com/xml/ns/j2ee", "eis-type");
   private static final QName RESOURCEADAPTERVERSION$10 = new QName("http://java.sun.com/xml/ns/j2ee", "resourceadapter-version");
   private static final QName LICENSE$12 = new QName("http://java.sun.com/xml/ns/j2ee", "license");
   private static final QName RESOURCEADAPTER$14 = new QName("http://java.sun.com/xml/ns/j2ee", "resourceadapter");
   private static final QName VERSION$16 = new QName("", "version");
   private static final QName ID$18 = new QName("", "id");

   public ConnectorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$2, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPLAYNAME$2);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$2);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$2, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$2, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$2);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$2, i);
      }
   }

   public IconType[] getIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ICON$4, targetList);
         IconType[] result = new IconType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IconType getIconArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ICON$4);
      }
   }

   public void setIconArray(IconType[] iconArray) {
      this.check_orphaned();
      this.arraySetterHelper(iconArray, ICON$4);
   }

   public void setIconArray(int i, IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$4, i, (short)2);
   }

   public IconType insertNewIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().insert_element_user(ICON$4, i);
         return target;
      }
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$4);
         return target;
      }
   }

   public void removeIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$4, i);
      }
   }

   public XsdStringType getVendorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(VENDORNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setVendorName(XsdStringType vendorName) {
      this.generatedSetterHelperImpl(vendorName, VENDORNAME$6, 0, (short)1);
   }

   public XsdStringType addNewVendorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(VENDORNAME$6);
         return target;
      }
   }

   public XsdStringType getEisType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(EISTYPE$8, 0);
         return target == null ? null : target;
      }
   }

   public void setEisType(XsdStringType eisType) {
      this.generatedSetterHelperImpl(eisType, EISTYPE$8, 0, (short)1);
   }

   public XsdStringType addNewEisType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(EISTYPE$8);
         return target;
      }
   }

   public XsdStringType getResourceadapterVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(RESOURCEADAPTERVERSION$10, 0);
         return target == null ? null : target;
      }
   }

   public void setResourceadapterVersion(XsdStringType resourceadapterVersion) {
      this.generatedSetterHelperImpl(resourceadapterVersion, RESOURCEADAPTERVERSION$10, 0, (short)1);
   }

   public XsdStringType addNewResourceadapterVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(RESOURCEADAPTERVERSION$10);
         return target;
      }
   }

   public LicenseType getLicense() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LicenseType target = null;
         target = (LicenseType)this.get_store().find_element_user(LICENSE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLicense() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LICENSE$12) != 0;
      }
   }

   public void setLicense(LicenseType license) {
      this.generatedSetterHelperImpl(license, LICENSE$12, 0, (short)1);
   }

   public LicenseType addNewLicense() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LicenseType target = null;
         target = (LicenseType)this.get_store().add_element_user(LICENSE$12);
         return target;
      }
   }

   public void unsetLicense() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LICENSE$12, 0);
      }
   }

   public ResourceadapterType getResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceadapterType target = null;
         target = (ResourceadapterType)this.get_store().find_element_user(RESOURCEADAPTER$14, 0);
         return target == null ? null : target;
      }
   }

   public void setResourceadapter(ResourceadapterType resourceadapter) {
      this.generatedSetterHelperImpl(resourceadapter, RESOURCEADAPTER$14, 0, (short)1);
   }

   public ResourceadapterType addNewResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceadapterType target = null;
         target = (ResourceadapterType)this.get_store().add_element_user(RESOURCEADAPTER$14);
         return target;
      }
   }

   public BigDecimal getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$16);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$16);
         }

         return target == null ? null : target.getBigDecimalValue();
      }
   }

   public DeweyVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$16);
         if (target == null) {
            target = (DeweyVersionType)this.get_default_attribute_value(VERSION$16);
         }

         return target;
      }
   }

   public void setVersion(BigDecimal version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$16);
         }

         target.setBigDecimalValue(version);
      }
   }

   public void xsetVersion(DeweyVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$16);
         if (target == null) {
            target = (DeweyVersionType)this.get_store().add_attribute_user(VERSION$16);
         }

         target.set(version);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$18);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$18);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$18) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$18);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$18);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$18);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$18);
      }
   }
}
