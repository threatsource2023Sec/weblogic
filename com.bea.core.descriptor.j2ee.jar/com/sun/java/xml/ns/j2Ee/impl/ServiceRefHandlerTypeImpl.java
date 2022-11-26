package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.ParamValueType;
import com.sun.java.xml.ns.j2Ee.ServiceRefHandlerType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.XsdQNameType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ServiceRefHandlerTypeImpl extends XmlComplexContentImpl implements ServiceRefHandlerType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName HANDLERNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "handler-name");
   private static final QName HANDLERCLASS$8 = new QName("http://java.sun.com/xml/ns/j2ee", "handler-class");
   private static final QName INITPARAM$10 = new QName("http://java.sun.com/xml/ns/j2ee", "init-param");
   private static final QName SOAPHEADER$12 = new QName("http://java.sun.com/xml/ns/j2ee", "soap-header");
   private static final QName SOAPROLE$14 = new QName("http://java.sun.com/xml/ns/j2ee", "soap-role");
   private static final QName PORTNAME$16 = new QName("http://java.sun.com/xml/ns/j2ee", "port-name");
   private static final QName ID$18 = new QName("", "id");

   public ServiceRefHandlerTypeImpl(SchemaType sType) {
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

   public String getHandlerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(HANDLERNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setHandlerName(String handlerName) {
      this.generatedSetterHelperImpl(handlerName, HANDLERNAME$6, 0, (short)1);
   }

   public String addNewHandlerName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(HANDLERNAME$6);
         return target;
      }
   }

   public FullyQualifiedClassType getHandlerClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(HANDLERCLASS$8, 0);
         return target == null ? null : target;
      }
   }

   public void setHandlerClass(FullyQualifiedClassType handlerClass) {
      this.generatedSetterHelperImpl(handlerClass, HANDLERCLASS$8, 0, (short)1);
   }

   public FullyQualifiedClassType addNewHandlerClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(HANDLERCLASS$8);
         return target;
      }
   }

   public ParamValueType[] getInitParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INITPARAM$10, targetList);
         ParamValueType[] result = new ParamValueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ParamValueType getInitParamArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().find_element_user(INITPARAM$10, i);
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
         return this.get_store().count_elements(INITPARAM$10);
      }
   }

   public void setInitParamArray(ParamValueType[] initParamArray) {
      this.check_orphaned();
      this.arraySetterHelper(initParamArray, INITPARAM$10);
   }

   public void setInitParamArray(int i, ParamValueType initParam) {
      this.generatedSetterHelperImpl(initParam, INITPARAM$10, i, (short)2);
   }

   public ParamValueType insertNewInitParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().insert_element_user(INITPARAM$10, i);
         return target;
      }
   }

   public ParamValueType addNewInitParam() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ParamValueType target = null;
         target = (ParamValueType)this.get_store().add_element_user(INITPARAM$10);
         return target;
      }
   }

   public void removeInitParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITPARAM$10, i);
      }
   }

   public XsdQNameType[] getSoapHeaderArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SOAPHEADER$12, targetList);
         XsdQNameType[] result = new XsdQNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XsdQNameType getSoapHeaderArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(SOAPHEADER$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSoapHeaderArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SOAPHEADER$12);
      }
   }

   public void setSoapHeaderArray(XsdQNameType[] soapHeaderArray) {
      this.check_orphaned();
      this.arraySetterHelper(soapHeaderArray, SOAPHEADER$12);
   }

   public void setSoapHeaderArray(int i, XsdQNameType soapHeader) {
      this.generatedSetterHelperImpl(soapHeader, SOAPHEADER$12, i, (short)2);
   }

   public XsdQNameType insertNewSoapHeader(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().insert_element_user(SOAPHEADER$12, i);
         return target;
      }
   }

   public XsdQNameType addNewSoapHeader() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(SOAPHEADER$12);
         return target;
      }
   }

   public void removeSoapHeader(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SOAPHEADER$12, i);
      }
   }

   public String[] getSoapRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SOAPROLE$14, targetList);
         String[] result = new String[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public String getSoapRoleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(SOAPROLE$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSoapRoleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SOAPROLE$14);
      }
   }

   public void setSoapRoleArray(String[] soapRoleArray) {
      this.check_orphaned();
      this.arraySetterHelper(soapRoleArray, SOAPROLE$14);
   }

   public void setSoapRoleArray(int i, String soapRole) {
      this.generatedSetterHelperImpl(soapRole, SOAPROLE$14, i, (short)2);
   }

   public String insertNewSoapRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().insert_element_user(SOAPROLE$14, i);
         return target;
      }
   }

   public String addNewSoapRole() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(SOAPROLE$14);
         return target;
      }
   }

   public void removeSoapRole(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SOAPROLE$14, i);
      }
   }

   public String[] getPortNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PORTNAME$16, targetList);
         String[] result = new String[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public String getPortNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PORTNAME$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPortNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTNAME$16);
      }
   }

   public void setPortNameArray(String[] portNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(portNameArray, PORTNAME$16);
   }

   public void setPortNameArray(int i, String portName) {
      this.generatedSetterHelperImpl(portName, PORTNAME$16, i, (short)2);
   }

   public String insertNewPortName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().insert_element_user(PORTNAME$16, i);
         return target;
      }
   }

   public String addNewPortName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PORTNAME$16);
         return target;
      }
   }

   public void removePortName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTNAME$16, i);
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
