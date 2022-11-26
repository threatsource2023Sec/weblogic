package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ConnectorType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.DeweyVersionType;
import org.jcp.xmlns.xml.ns.javaee.DisplayNameType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.IconType;
import org.jcp.xmlns.xml.ns.javaee.LicenseType;
import org.jcp.xmlns.xml.ns.javaee.ResourceadapterType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.XsdStringType;

public class ConnectorTypeImpl extends XmlComplexContentImpl implements ConnectorType {
   private static final long serialVersionUID = 1L;
   private static final QName MODULENAME$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "module-name");
   private static final QName DESCRIPTION$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "display-name");
   private static final QName ICON$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "icon");
   private static final QName VENDORNAME$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "vendor-name");
   private static final QName EISTYPE$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "eis-type");
   private static final QName RESOURCEADAPTERVERSION$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resourceadapter-version");
   private static final QName LICENSE$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "license");
   private static final QName RESOURCEADAPTER$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "resourceadapter");
   private static final QName REQUIREDWORKCONTEXT$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "required-work-context");
   private static final QName VERSION$20 = new QName("", "version");
   private static final QName METADATACOMPLETE$22 = new QName("", "metadata-complete");
   private static final QName ID$24 = new QName("", "id");

   public ConnectorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MODULENAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MODULENAME$0) != 0;
      }
   }

   public void setModuleName(String moduleName) {
      this.generatedSetterHelperImpl(moduleName, MODULENAME$0, 0, (short)1);
   }

   public String addNewModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MODULENAME$0);
         return target;
      }
   }

   public void unsetModuleName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MODULENAME$0, 0);
      }
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$2, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$2, i);
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
         return this.get_store().count_elements(DESCRIPTION$2);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$2);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$2, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$2, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$2);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$2, i);
      }
   }

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$4, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$4, i);
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
         return this.get_store().count_elements(DISPLAYNAME$4);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$4);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$4, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$4, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$4);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$4, i);
      }
   }

   public IconType[] getIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ICON$6, targetList);
         IconType[] result = new IconType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IconType getIconArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$6, i);
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
         return this.get_store().count_elements(ICON$6);
      }
   }

   public void setIconArray(IconType[] iconArray) {
      this.check_orphaned();
      this.arraySetterHelper(iconArray, ICON$6);
   }

   public void setIconArray(int i, IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$6, i, (short)2);
   }

   public IconType insertNewIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().insert_element_user(ICON$6, i);
         return target;
      }
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$6);
         return target;
      }
   }

   public void removeIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$6, i);
      }
   }

   public XsdStringType getVendorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(VENDORNAME$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetVendorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VENDORNAME$8) != 0;
      }
   }

   public void setVendorName(XsdStringType vendorName) {
      this.generatedSetterHelperImpl(vendorName, VENDORNAME$8, 0, (short)1);
   }

   public XsdStringType addNewVendorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(VENDORNAME$8);
         return target;
      }
   }

   public void unsetVendorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VENDORNAME$8, 0);
      }
   }

   public XsdStringType getEisType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(EISTYPE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEisType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EISTYPE$10) != 0;
      }
   }

   public void setEisType(XsdStringType eisType) {
      this.generatedSetterHelperImpl(eisType, EISTYPE$10, 0, (short)1);
   }

   public XsdStringType addNewEisType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(EISTYPE$10);
         return target;
      }
   }

   public void unsetEisType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EISTYPE$10, 0);
      }
   }

   public XsdStringType getResourceadapterVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(RESOURCEADAPTERVERSION$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResourceadapterVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCEADAPTERVERSION$12) != 0;
      }
   }

   public void setResourceadapterVersion(XsdStringType resourceadapterVersion) {
      this.generatedSetterHelperImpl(resourceadapterVersion, RESOURCEADAPTERVERSION$12, 0, (short)1);
   }

   public XsdStringType addNewResourceadapterVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(RESOURCEADAPTERVERSION$12);
         return target;
      }
   }

   public void unsetResourceadapterVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCEADAPTERVERSION$12, 0);
      }
   }

   public LicenseType getLicense() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LicenseType target = null;
         target = (LicenseType)this.get_store().find_element_user(LICENSE$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLicense() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LICENSE$14) != 0;
      }
   }

   public void setLicense(LicenseType license) {
      this.generatedSetterHelperImpl(license, LICENSE$14, 0, (short)1);
   }

   public LicenseType addNewLicense() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LicenseType target = null;
         target = (LicenseType)this.get_store().add_element_user(LICENSE$14);
         return target;
      }
   }

   public void unsetLicense() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LICENSE$14, 0);
      }
   }

   public ResourceadapterType getResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceadapterType target = null;
         target = (ResourceadapterType)this.get_store().find_element_user(RESOURCEADAPTER$16, 0);
         return target == null ? null : target;
      }
   }

   public void setResourceadapter(ResourceadapterType resourceadapter) {
      this.generatedSetterHelperImpl(resourceadapter, RESOURCEADAPTER$16, 0, (short)1);
   }

   public ResourceadapterType addNewResourceadapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ResourceadapterType target = null;
         target = (ResourceadapterType)this.get_store().add_element_user(RESOURCEADAPTER$16);
         return target;
      }
   }

   public FullyQualifiedClassType[] getRequiredWorkContextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(REQUIREDWORKCONTEXT$18, targetList);
         FullyQualifiedClassType[] result = new FullyQualifiedClassType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FullyQualifiedClassType getRequiredWorkContextArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(REQUIREDWORKCONTEXT$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfRequiredWorkContextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REQUIREDWORKCONTEXT$18);
      }
   }

   public void setRequiredWorkContextArray(FullyQualifiedClassType[] requiredWorkContextArray) {
      this.check_orphaned();
      this.arraySetterHelper(requiredWorkContextArray, REQUIREDWORKCONTEXT$18);
   }

   public void setRequiredWorkContextArray(int i, FullyQualifiedClassType requiredWorkContext) {
      this.generatedSetterHelperImpl(requiredWorkContext, REQUIREDWORKCONTEXT$18, i, (short)2);
   }

   public FullyQualifiedClassType insertNewRequiredWorkContext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().insert_element_user(REQUIREDWORKCONTEXT$18, i);
         return target;
      }
   }

   public FullyQualifiedClassType addNewRequiredWorkContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(REQUIREDWORKCONTEXT$18);
         return target;
      }
   }

   public void removeRequiredWorkContext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIREDWORKCONTEXT$18, i);
      }
   }

   public java.lang.String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$20);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$20);
         }

         return target == null ? null : target.getStringValue();
      }
   }

   public DeweyVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$20);
         if (target == null) {
            target = (DeweyVersionType)this.get_default_attribute_value(VERSION$20);
         }

         return target;
      }
   }

   public void setVersion(java.lang.String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$20);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(DeweyVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$20);
         if (target == null) {
            target = (DeweyVersionType)this.get_store().add_attribute_user(VERSION$20);
         }

         target.set(version);
      }
   }

   public boolean getMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(METADATACOMPLETE$22);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(METADATACOMPLETE$22);
         return target;
      }
   }

   public boolean isSetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(METADATACOMPLETE$22) != null;
      }
   }

   public void setMetadataComplete(boolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(METADATACOMPLETE$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(METADATACOMPLETE$22);
         }

         target.setBooleanValue(metadataComplete);
      }
   }

   public void xsetMetadataComplete(XmlBoolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(METADATACOMPLETE$22);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(METADATACOMPLETE$22);
         }

         target.set(metadataComplete);
      }
   }

   public void unsetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(METADATACOMPLETE$22);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$24);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$24);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$24) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$24);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$24);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$24);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$24);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$24);
      }
   }
}
