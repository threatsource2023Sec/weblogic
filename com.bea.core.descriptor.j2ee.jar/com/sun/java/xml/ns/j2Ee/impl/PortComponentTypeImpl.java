package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.PortComponentHandlerType;
import com.sun.java.xml.ns.j2Ee.PortComponentType;
import com.sun.java.xml.ns.j2Ee.ServiceImplBeanType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.XsdQNameType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PortComponentTypeImpl extends XmlComplexContentImpl implements PortComponentType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName PORTCOMPONENTNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "port-component-name");
   private static final QName WSDLPORT$8 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-port");
   private static final QName SERVICEENDPOINTINTERFACE$10 = new QName("http://java.sun.com/xml/ns/j2ee", "service-endpoint-interface");
   private static final QName SERVICEIMPLBEAN$12 = new QName("http://java.sun.com/xml/ns/j2ee", "service-impl-bean");
   private static final QName HANDLER$14 = new QName("http://java.sun.com/xml/ns/j2ee", "handler");
   private static final QName ID$16 = new QName("", "id");

   public PortComponentTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, 0, (short)1);
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public DisplayNameType getDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPLAYNAME$2) != 0;
      }
   }

   public void setDisplayName(DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$2, 0, (short)1);
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$2);
         return target;
      }
   }

   public void unsetDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$2, 0);
      }
   }

   public IconType getIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ICON$4) != 0;
      }
   }

   public void setIcon(IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$4, 0, (short)1);
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$4);
         return target;
      }
   }

   public void unsetIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$4, 0);
      }
   }

   public String getPortComponentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PORTCOMPONENTNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setPortComponentName(String portComponentName) {
      this.generatedSetterHelperImpl(portComponentName, PORTCOMPONENTNAME$6, 0, (short)1);
   }

   public String addNewPortComponentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PORTCOMPONENTNAME$6);
         return target;
      }
   }

   public XsdQNameType getWsdlPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(WSDLPORT$8, 0);
         return target == null ? null : target;
      }
   }

   public void setWsdlPort(XsdQNameType wsdlPort) {
      this.generatedSetterHelperImpl(wsdlPort, WSDLPORT$8, 0, (short)1);
   }

   public XsdQNameType addNewWsdlPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(WSDLPORT$8);
         return target;
      }
   }

   public FullyQualifiedClassType getServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVICEENDPOINTINTERFACE$10, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceEndpointInterface(FullyQualifiedClassType serviceEndpointInterface) {
      this.generatedSetterHelperImpl(serviceEndpointInterface, SERVICEENDPOINTINTERFACE$10, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVICEENDPOINTINTERFACE$10);
         return target;
      }
   }

   public ServiceImplBeanType getServiceImplBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceImplBeanType target = null;
         target = (ServiceImplBeanType)this.get_store().find_element_user(SERVICEIMPLBEAN$12, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceImplBean(ServiceImplBeanType serviceImplBean) {
      this.generatedSetterHelperImpl(serviceImplBean, SERVICEIMPLBEAN$12, 0, (short)1);
   }

   public ServiceImplBeanType addNewServiceImplBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceImplBeanType target = null;
         target = (ServiceImplBeanType)this.get_store().add_element_user(SERVICEIMPLBEAN$12);
         return target;
      }
   }

   public PortComponentHandlerType[] getHandlerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HANDLER$14, targetList);
         PortComponentHandlerType[] result = new PortComponentHandlerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PortComponentHandlerType getHandlerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentHandlerType target = null;
         target = (PortComponentHandlerType)this.get_store().find_element_user(HANDLER$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfHandlerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HANDLER$14);
      }
   }

   public void setHandlerArray(PortComponentHandlerType[] handlerArray) {
      this.check_orphaned();
      this.arraySetterHelper(handlerArray, HANDLER$14);
   }

   public void setHandlerArray(int i, PortComponentHandlerType handler) {
      this.generatedSetterHelperImpl(handler, HANDLER$14, i, (short)2);
   }

   public PortComponentHandlerType insertNewHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentHandlerType target = null;
         target = (PortComponentHandlerType)this.get_store().insert_element_user(HANDLER$14, i);
         return target;
      }
   }

   public PortComponentHandlerType addNewHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentHandlerType target = null;
         target = (PortComponentHandlerType)this.get_store().add_element_user(HANDLER$14);
         return target;
      }
   }

   public void removeHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLER$14, i);
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
