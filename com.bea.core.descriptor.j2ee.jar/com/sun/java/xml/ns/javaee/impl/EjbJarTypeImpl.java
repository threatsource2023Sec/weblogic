package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.AssemblyDescriptorType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.DeweyVersionType;
import com.sun.java.xml.ns.javaee.DisplayNameType;
import com.sun.java.xml.ns.javaee.EjbJarType;
import com.sun.java.xml.ns.javaee.EnterpriseBeansType;
import com.sun.java.xml.ns.javaee.IconType;
import com.sun.java.xml.ns.javaee.InterceptorsType;
import com.sun.java.xml.ns.javaee.PathType;
import com.sun.java.xml.ns.javaee.RelationshipsType;
import com.sun.java.xml.ns.javaee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EjbJarTypeImpl extends XmlComplexContentImpl implements EjbJarType {
   private static final long serialVersionUID = 1L;
   private static final QName MODULENAME$0 = new QName("http://java.sun.com/xml/ns/javaee", "module-name");
   private static final QName DESCRIPTION$2 = new QName("http://java.sun.com/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$4 = new QName("http://java.sun.com/xml/ns/javaee", "display-name");
   private static final QName ICON$6 = new QName("http://java.sun.com/xml/ns/javaee", "icon");
   private static final QName ENTERPRISEBEANS$8 = new QName("http://java.sun.com/xml/ns/javaee", "enterprise-beans");
   private static final QName INTERCEPTORS$10 = new QName("http://java.sun.com/xml/ns/javaee", "interceptors");
   private static final QName RELATIONSHIPS$12 = new QName("http://java.sun.com/xml/ns/javaee", "relationships");
   private static final QName ASSEMBLYDESCRIPTOR$14 = new QName("http://java.sun.com/xml/ns/javaee", "assembly-descriptor");
   private static final QName EJBCLIENTJAR$16 = new QName("http://java.sun.com/xml/ns/javaee", "ejb-client-jar");
   private static final QName VERSION$18 = new QName("", "version");
   private static final QName METADATACOMPLETE$20 = new QName("", "metadata-complete");
   private static final QName ID$22 = new QName("", "id");

   public EjbJarTypeImpl(SchemaType sType) {
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

   public EnterpriseBeansType getEnterpriseBeans() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnterpriseBeansType target = null;
         target = (EnterpriseBeansType)this.get_store().find_element_user(ENTERPRISEBEANS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnterpriseBeans() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENTERPRISEBEANS$8) != 0;
      }
   }

   public void setEnterpriseBeans(EnterpriseBeansType enterpriseBeans) {
      this.generatedSetterHelperImpl(enterpriseBeans, ENTERPRISEBEANS$8, 0, (short)1);
   }

   public EnterpriseBeansType addNewEnterpriseBeans() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnterpriseBeansType target = null;
         target = (EnterpriseBeansType)this.get_store().add_element_user(ENTERPRISEBEANS$8);
         return target;
      }
   }

   public void unsetEnterpriseBeans() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENTERPRISEBEANS$8, 0);
      }
   }

   public InterceptorsType getInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InterceptorsType target = null;
         target = (InterceptorsType)this.get_store().find_element_user(INTERCEPTORS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTERCEPTORS$10) != 0;
      }
   }

   public void setInterceptors(InterceptorsType interceptors) {
      this.generatedSetterHelperImpl(interceptors, INTERCEPTORS$10, 0, (short)1);
   }

   public InterceptorsType addNewInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InterceptorsType target = null;
         target = (InterceptorsType)this.get_store().add_element_user(INTERCEPTORS$10);
         return target;
      }
   }

   public void unsetInterceptors() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTERCEPTORS$10, 0);
      }
   }

   public RelationshipsType getRelationships() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipsType target = null;
         target = (RelationshipsType)this.get_store().find_element_user(RELATIONSHIPS$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRelationships() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RELATIONSHIPS$12) != 0;
      }
   }

   public void setRelationships(RelationshipsType relationships) {
      this.generatedSetterHelperImpl(relationships, RELATIONSHIPS$12, 0, (short)1);
   }

   public RelationshipsType addNewRelationships() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipsType target = null;
         target = (RelationshipsType)this.get_store().add_element_user(RELATIONSHIPS$12);
         return target;
      }
   }

   public void unsetRelationships() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RELATIONSHIPS$12, 0);
      }
   }

   public AssemblyDescriptorType getAssemblyDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AssemblyDescriptorType target = null;
         target = (AssemblyDescriptorType)this.get_store().find_element_user(ASSEMBLYDESCRIPTOR$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAssemblyDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ASSEMBLYDESCRIPTOR$14) != 0;
      }
   }

   public void setAssemblyDescriptor(AssemblyDescriptorType assemblyDescriptor) {
      this.generatedSetterHelperImpl(assemblyDescriptor, ASSEMBLYDESCRIPTOR$14, 0, (short)1);
   }

   public AssemblyDescriptorType addNewAssemblyDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AssemblyDescriptorType target = null;
         target = (AssemblyDescriptorType)this.get_store().add_element_user(ASSEMBLYDESCRIPTOR$14);
         return target;
      }
   }

   public void unsetAssemblyDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ASSEMBLYDESCRIPTOR$14, 0);
      }
   }

   public PathType getEjbClientJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(EJBCLIENTJAR$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbClientJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBCLIENTJAR$16) != 0;
      }
   }

   public void setEjbClientJar(PathType ejbClientJar) {
      this.generatedSetterHelperImpl(ejbClientJar, EJBCLIENTJAR$16, 0, (short)1);
   }

   public PathType addNewEjbClientJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(EJBCLIENTJAR$16);
         return target;
      }
   }

   public void unsetEjbClientJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBCLIENTJAR$16, 0);
      }
   }

   public java.lang.String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$18);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$18);
         }

         return target == null ? null : target.getStringValue();
      }
   }

   public DeweyVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$18);
         if (target == null) {
            target = (DeweyVersionType)this.get_default_attribute_value(VERSION$18);
         }

         return target;
      }
   }

   public void setVersion(java.lang.String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$18);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(DeweyVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$18);
         if (target == null) {
            target = (DeweyVersionType)this.get_store().add_attribute_user(VERSION$18);
         }

         target.set(version);
      }
   }

   public boolean getMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(METADATACOMPLETE$20);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(METADATACOMPLETE$20);
         return target;
      }
   }

   public boolean isSetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(METADATACOMPLETE$20) != null;
      }
   }

   public void setMetadataComplete(boolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(METADATACOMPLETE$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(METADATACOMPLETE$20);
         }

         target.setBooleanValue(metadataComplete);
      }
   }

   public void xsetMetadataComplete(XmlBoolean metadataComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(METADATACOMPLETE$20);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(METADATACOMPLETE$20);
         }

         target.set(metadataComplete);
      }
   }

   public void unsetMetadataComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(METADATACOMPLETE$20);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$22);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$22) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$22);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$22);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$22);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$22);
      }
   }
}
