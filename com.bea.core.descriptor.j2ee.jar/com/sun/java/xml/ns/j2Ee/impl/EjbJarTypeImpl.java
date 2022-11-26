package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.AssemblyDescriptorType;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DeweyVersionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.EjbJarType;
import com.sun.java.xml.ns.j2Ee.EnterpriseBeansType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.PathType;
import com.sun.java.xml.ns.j2Ee.RelationshipsType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class EjbJarTypeImpl extends XmlComplexContentImpl implements EjbJarType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName ENTERPRISEBEANS$6 = new QName("http://java.sun.com/xml/ns/j2ee", "enterprise-beans");
   private static final QName RELATIONSHIPS$8 = new QName("http://java.sun.com/xml/ns/j2ee", "relationships");
   private static final QName ASSEMBLYDESCRIPTOR$10 = new QName("http://java.sun.com/xml/ns/j2ee", "assembly-descriptor");
   private static final QName EJBCLIENTJAR$12 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-client-jar");
   private static final QName VERSION$14 = new QName("", "version");
   private static final QName ID$16 = new QName("", "id");

   public EjbJarTypeImpl(SchemaType sType) {
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

   public EnterpriseBeansType getEnterpriseBeans() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnterpriseBeansType target = null;
         target = (EnterpriseBeansType)this.get_store().find_element_user(ENTERPRISEBEANS$6, 0);
         return target == null ? null : target;
      }
   }

   public void setEnterpriseBeans(EnterpriseBeansType enterpriseBeans) {
      this.generatedSetterHelperImpl(enterpriseBeans, ENTERPRISEBEANS$6, 0, (short)1);
   }

   public EnterpriseBeansType addNewEnterpriseBeans() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnterpriseBeansType target = null;
         target = (EnterpriseBeansType)this.get_store().add_element_user(ENTERPRISEBEANS$6);
         return target;
      }
   }

   public RelationshipsType getRelationships() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipsType target = null;
         target = (RelationshipsType)this.get_store().find_element_user(RELATIONSHIPS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRelationships() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RELATIONSHIPS$8) != 0;
      }
   }

   public void setRelationships(RelationshipsType relationships) {
      this.generatedSetterHelperImpl(relationships, RELATIONSHIPS$8, 0, (short)1);
   }

   public RelationshipsType addNewRelationships() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RelationshipsType target = null;
         target = (RelationshipsType)this.get_store().add_element_user(RELATIONSHIPS$8);
         return target;
      }
   }

   public void unsetRelationships() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RELATIONSHIPS$8, 0);
      }
   }

   public AssemblyDescriptorType getAssemblyDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AssemblyDescriptorType target = null;
         target = (AssemblyDescriptorType)this.get_store().find_element_user(ASSEMBLYDESCRIPTOR$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAssemblyDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ASSEMBLYDESCRIPTOR$10) != 0;
      }
   }

   public void setAssemblyDescriptor(AssemblyDescriptorType assemblyDescriptor) {
      this.generatedSetterHelperImpl(assemblyDescriptor, ASSEMBLYDESCRIPTOR$10, 0, (short)1);
   }

   public AssemblyDescriptorType addNewAssemblyDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AssemblyDescriptorType target = null;
         target = (AssemblyDescriptorType)this.get_store().add_element_user(ASSEMBLYDESCRIPTOR$10);
         return target;
      }
   }

   public void unsetAssemblyDescriptor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ASSEMBLYDESCRIPTOR$10, 0);
      }
   }

   public PathType getEjbClientJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(EJBCLIENTJAR$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbClientJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBCLIENTJAR$12) != 0;
      }
   }

   public void setEjbClientJar(PathType ejbClientJar) {
      this.generatedSetterHelperImpl(ejbClientJar, EJBCLIENTJAR$12, 0, (short)1);
   }

   public PathType addNewEjbClientJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(EJBCLIENTJAR$12);
         return target;
      }
   }

   public void unsetEjbClientJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBCLIENTJAR$12, 0);
      }
   }

   public BigDecimal getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$14);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$14);
         }

         return target == null ? null : target.getBigDecimalValue();
      }
   }

   public DeweyVersionType xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$14);
         if (target == null) {
            target = (DeweyVersionType)this.get_default_attribute_value(VERSION$14);
         }

         return target;
      }
   }

   public void setVersion(BigDecimal version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$14);
         }

         target.setBigDecimalValue(version);
      }
   }

   public void xsetVersion(DeweyVersionType version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeweyVersionType target = null;
         target = (DeweyVersionType)this.get_store().find_attribute_user(VERSION$14);
         if (target == null) {
            target = (DeweyVersionType)this.get_store().add_attribute_user(VERSION$14);
         }

         target.set(version);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$16) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$16);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$16);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$16);
      }
   }
}
