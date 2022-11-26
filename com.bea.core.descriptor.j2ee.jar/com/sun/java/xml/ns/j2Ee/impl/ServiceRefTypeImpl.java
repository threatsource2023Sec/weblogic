package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.JndiNameType;
import com.sun.java.xml.ns.j2Ee.PathType;
import com.sun.java.xml.ns.j2Ee.PortComponentRefType;
import com.sun.java.xml.ns.j2Ee.ServiceRefHandlerType;
import com.sun.java.xml.ns.j2Ee.ServiceRefType;
import com.sun.java.xml.ns.j2Ee.XsdAnyURIType;
import com.sun.java.xml.ns.j2Ee.XsdQNameType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ServiceRefTypeImpl extends XmlComplexContentImpl implements ServiceRefType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName SERVICEREFNAME$6 = new QName("http://java.sun.com/xml/ns/j2ee", "service-ref-name");
   private static final QName SERVICEINTERFACE$8 = new QName("http://java.sun.com/xml/ns/j2ee", "service-interface");
   private static final QName WSDLFILE$10 = new QName("http://java.sun.com/xml/ns/j2ee", "wsdl-file");
   private static final QName JAXRPCMAPPINGFILE$12 = new QName("http://java.sun.com/xml/ns/j2ee", "jaxrpc-mapping-file");
   private static final QName SERVICEQNAME$14 = new QName("http://java.sun.com/xml/ns/j2ee", "service-qname");
   private static final QName PORTCOMPONENTREF$16 = new QName("http://java.sun.com/xml/ns/j2ee", "port-component-ref");
   private static final QName HANDLER$18 = new QName("http://java.sun.com/xml/ns/j2ee", "handler");
   private static final QName ID$20 = new QName("", "id");

   public ServiceRefTypeImpl(SchemaType sType) {
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

   public JndiNameType getServiceRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(SERVICEREFNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceRefName(JndiNameType serviceRefName) {
      this.generatedSetterHelperImpl(serviceRefName, SERVICEREFNAME$6, 0, (short)1);
   }

   public JndiNameType addNewServiceRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(SERVICEREFNAME$6);
         return target;
      }
   }

   public FullyQualifiedClassType getServiceInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVICEINTERFACE$8, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceInterface(FullyQualifiedClassType serviceInterface) {
      this.generatedSetterHelperImpl(serviceInterface, SERVICEINTERFACE$8, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServiceInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVICEINTERFACE$8);
         return target;
      }
   }

   public XsdAnyURIType getWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdAnyURIType target = null;
         target = (XsdAnyURIType)this.get_store().find_element_user(WSDLFILE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLFILE$10) != 0;
      }
   }

   public void setWsdlFile(XsdAnyURIType wsdlFile) {
      this.generatedSetterHelperImpl(wsdlFile, WSDLFILE$10, 0, (short)1);
   }

   public XsdAnyURIType addNewWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdAnyURIType target = null;
         target = (XsdAnyURIType)this.get_store().add_element_user(WSDLFILE$10);
         return target;
      }
   }

   public void unsetWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLFILE$10, 0);
      }
   }

   public PathType getJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(JAXRPCMAPPINGFILE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JAXRPCMAPPINGFILE$12) != 0;
      }
   }

   public void setJaxrpcMappingFile(PathType jaxrpcMappingFile) {
      this.generatedSetterHelperImpl(jaxrpcMappingFile, JAXRPCMAPPINGFILE$12, 0, (short)1);
   }

   public PathType addNewJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(JAXRPCMAPPINGFILE$12);
         return target;
      }
   }

   public void unsetJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JAXRPCMAPPINGFILE$12, 0);
      }
   }

   public XsdQNameType getServiceQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(SERVICEQNAME$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServiceQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEQNAME$14) != 0;
      }
   }

   public void setServiceQname(XsdQNameType serviceQname) {
      this.generatedSetterHelperImpl(serviceQname, SERVICEQNAME$14, 0, (short)1);
   }

   public XsdQNameType addNewServiceQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(SERVICEQNAME$14);
         return target;
      }
   }

   public void unsetServiceQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEQNAME$14, 0);
      }
   }

   public PortComponentRefType[] getPortComponentRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PORTCOMPONENTREF$16, targetList);
         PortComponentRefType[] result = new PortComponentRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PortComponentRefType getPortComponentRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentRefType target = null;
         target = (PortComponentRefType)this.get_store().find_element_user(PORTCOMPONENTREF$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPortComponentRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORTCOMPONENTREF$16);
      }
   }

   public void setPortComponentRefArray(PortComponentRefType[] portComponentRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(portComponentRefArray, PORTCOMPONENTREF$16);
   }

   public void setPortComponentRefArray(int i, PortComponentRefType portComponentRef) {
      this.generatedSetterHelperImpl(portComponentRef, PORTCOMPONENTREF$16, i, (short)2);
   }

   public PortComponentRefType insertNewPortComponentRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentRefType target = null;
         target = (PortComponentRefType)this.get_store().insert_element_user(PORTCOMPONENTREF$16, i);
         return target;
      }
   }

   public PortComponentRefType addNewPortComponentRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentRefType target = null;
         target = (PortComponentRefType)this.get_store().add_element_user(PORTCOMPONENTREF$16);
         return target;
      }
   }

   public void removePortComponentRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTCOMPONENTREF$16, i);
      }
   }

   public ServiceRefHandlerType[] getHandlerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HANDLER$18, targetList);
         ServiceRefHandlerType[] result = new ServiceRefHandlerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ServiceRefHandlerType getHandlerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefHandlerType target = null;
         target = (ServiceRefHandlerType)this.get_store().find_element_user(HANDLER$18, i);
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
         return this.get_store().count_elements(HANDLER$18);
      }
   }

   public void setHandlerArray(ServiceRefHandlerType[] handlerArray) {
      this.check_orphaned();
      this.arraySetterHelper(handlerArray, HANDLER$18);
   }

   public void setHandlerArray(int i, ServiceRefHandlerType handler) {
      this.generatedSetterHelperImpl(handler, HANDLER$18, i, (short)2);
   }

   public ServiceRefHandlerType insertNewHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefHandlerType target = null;
         target = (ServiceRefHandlerType)this.get_store().insert_element_user(HANDLER$18, i);
         return target;
      }
   }

   public ServiceRefHandlerType addNewHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefHandlerType target = null;
         target = (ServiceRefHandlerType)this.get_store().add_element_user(HANDLER$18);
         return target;
      }
   }

   public void removeHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLER$18, i);
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
