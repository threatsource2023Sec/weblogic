package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.JspFileType;
import com.sun.java.xml.ns.j2Ee.ParamValueType;
import com.sun.java.xml.ns.j2Ee.RunAsType;
import com.sun.java.xml.ns.j2Ee.SecurityRoleRefType;
import com.sun.java.xml.ns.j2Ee.ServletNameType;
import com.sun.java.xml.ns.j2Ee.ServletType;
import com.sun.java.xml.ns.j2Ee.XsdIntegerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ServletTypeImpl extends XmlComplexContentImpl implements ServletType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName SERVLETNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "servlet-name");
   private static final QName SERVLETCLASS$8 = new QName("http://java.sun.com/xml/ns/j2ee", "servlet-class");
   private static final QName JSPFILE$10 = new QName("http://java.sun.com/xml/ns/j2ee", "jsp-file");
   private static final QName INITPARAM$12 = new QName("http://java.sun.com/xml/ns/j2ee", "init-param");
   private static final QName LOADONSTARTUP$14 = new QName("http://java.sun.com/xml/ns/j2ee", "load-on-startup");
   private static final QName RUNAS$16 = new QName("http://java.sun.com/xml/ns/j2ee", "run-as");
   private static final QName SECURITYROLEREF$18 = new QName("http://java.sun.com/xml/ns/j2ee", "security-role-ref");
   private static final QName ID$20 = new QName("", "id");

   public ServletTypeImpl(SchemaType sType) {
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

   public ServletNameType getServletName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletNameType target = null;
         target = (ServletNameType)this.get_store().find_element_user(SERVLETNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setServletName(ServletNameType servletName) {
      this.generatedSetterHelperImpl(servletName, SERVLETNAME$6, 0, (short)1);
   }

   public ServletNameType addNewServletName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletNameType target = null;
         target = (ServletNameType)this.get_store().add_element_user(SERVLETNAME$6);
         return target;
      }
   }

   public FullyQualifiedClassType getServletClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVLETCLASS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServletClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVLETCLASS$8) != 0;
      }
   }

   public void setServletClass(FullyQualifiedClassType servletClass) {
      this.generatedSetterHelperImpl(servletClass, SERVLETCLASS$8, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServletClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVLETCLASS$8);
         return target;
      }
   }

   public void unsetServletClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLETCLASS$8, 0);
      }
   }

   public JspFileType getJspFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspFileType target = null;
         target = (JspFileType)this.get_store().find_element_user(JSPFILE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJspFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JSPFILE$10) != 0;
      }
   }

   public void setJspFile(JspFileType jspFile) {
      this.generatedSetterHelperImpl(jspFile, JSPFILE$10, 0, (short)1);
   }

   public JspFileType addNewJspFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JspFileType target = null;
         target = (JspFileType)this.get_store().add_element_user(JSPFILE$10);
         return target;
      }
   }

   public void unsetJspFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JSPFILE$10, 0);
      }
   }

   public ParamValueType[] getInitParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INITPARAM$12, targetList);
         ParamValueType[] result = new ParamValueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ParamValueType getInitParamArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().find_element_user(INITPARAM$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInitParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITPARAM$12);
      }
   }

   public void setInitParamArray(ParamValueType[] initParamArray) {
      this.check_orphaned();
      this.arraySetterHelper(initParamArray, INITPARAM$12);
   }

   public void setInitParamArray(int i, ParamValueType initParam) {
      this.generatedSetterHelperImpl(initParam, INITPARAM$12, i, (short)2);
   }

   public ParamValueType insertNewInitParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().insert_element_user(INITPARAM$12, i);
         return target;
      }
   }

   public ParamValueType addNewInitParam() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().add_element_user(INITPARAM$12);
         return target;
      }
   }

   public void removeInitParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITPARAM$12, i);
      }
   }

   public XsdIntegerType getLoadOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().find_element_user(LOADONSTARTUP$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLoadOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOADONSTARTUP$14) != 0;
      }
   }

   public void setLoadOnStartup(XsdIntegerType loadOnStartup) {
      this.generatedSetterHelperImpl(loadOnStartup, LOADONSTARTUP$14, 0, (short)1);
   }

   public XsdIntegerType addNewLoadOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdIntegerType target = null;
         target = (XsdIntegerType)this.get_store().add_element_user(LOADONSTARTUP$14);
         return target;
      }
   }

   public void unsetLoadOnStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOADONSTARTUP$14, 0);
      }
   }

   public RunAsType getRunAs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsType target = null;
         target = (RunAsType)this.get_store().find_element_user(RUNAS$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRunAs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNAS$16) != 0;
      }
   }

   public void setRunAs(RunAsType runAs) {
      this.generatedSetterHelperImpl(runAs, RUNAS$16, 0, (short)1);
   }

   public RunAsType addNewRunAs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsType target = null;
         target = (RunAsType)this.get_store().add_element_user(RUNAS$16);
         return target;
      }
   }

   public void unsetRunAs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNAS$16, 0);
      }
   }

   public SecurityRoleRefType[] getSecurityRoleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SECURITYROLEREF$18, targetList);
         SecurityRoleRefType[] result = new SecurityRoleRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SecurityRoleRefType getSecurityRoleRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().find_element_user(SECURITYROLEREF$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSecurityRoleRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYROLEREF$18);
      }
   }

   public void setSecurityRoleRefArray(SecurityRoleRefType[] securityRoleRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(securityRoleRefArray, SECURITYROLEREF$18);
   }

   public void setSecurityRoleRefArray(int i, SecurityRoleRefType securityRoleRef) {
      this.generatedSetterHelperImpl(securityRoleRef, SECURITYROLEREF$18, i, (short)2);
   }

   public SecurityRoleRefType insertNewSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().insert_element_user(SECURITYROLEREF$18, i);
         return target;
      }
   }

   public SecurityRoleRefType addNewSecurityRoleRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityRoleRefType target = null;
         target = (SecurityRoleRefType)this.get_store().add_element_user(SECURITYROLEREF$18);
         return target;
      }
   }

   public void removeSecurityRoleRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYROLEREF$18, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$20);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$20);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$20) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$20);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$20);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$20);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$20);
      }
   }
}
