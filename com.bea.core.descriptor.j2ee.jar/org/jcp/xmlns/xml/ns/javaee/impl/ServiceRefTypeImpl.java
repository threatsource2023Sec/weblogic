package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.DisplayNameType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.HandlerChainsType;
import org.jcp.xmlns.xml.ns.javaee.HandlerType;
import org.jcp.xmlns.xml.ns.javaee.IconType;
import org.jcp.xmlns.xml.ns.javaee.InjectionTargetType;
import org.jcp.xmlns.xml.ns.javaee.JndiNameType;
import org.jcp.xmlns.xml.ns.javaee.PathType;
import org.jcp.xmlns.xml.ns.javaee.PortComponentRefType;
import org.jcp.xmlns.xml.ns.javaee.ServiceRefType;
import org.jcp.xmlns.xml.ns.javaee.XsdAnyURIType;
import org.jcp.xmlns.xml.ns.javaee.XsdQNameType;
import org.jcp.xmlns.xml.ns.javaee.XsdStringType;

public class ServiceRefTypeImpl extends XmlComplexContentImpl implements ServiceRefType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "display-name");
   private static final QName ICON$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "icon");
   private static final QName SERVICEREFNAME$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-ref-name");
   private static final QName SERVICEINTERFACE$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-interface");
   private static final QName SERVICEREFTYPE$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-ref-type");
   private static final QName WSDLFILE$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "wsdl-file");
   private static final QName JAXRPCMAPPINGFILE$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "jaxrpc-mapping-file");
   private static final QName SERVICEQNAME$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-qname");
   private static final QName PORTCOMPONENTREF$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "port-component-ref");
   private static final QName HANDLER$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "handler");
   private static final QName HANDLERCHAINS$22 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "handler-chains");
   private static final QName MAPPEDNAME$24 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mapped-name");
   private static final QName INJECTIONTARGET$26 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "injection-target");
   private static final QName LOOKUPNAME$28 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "lookup-name");
   private static final QName ID$30 = new QName("", "id");

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

   public FullyQualifiedClassType getServiceRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVICEREFTYPE$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServiceRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEREFTYPE$10) != 0;
      }
   }

   public void setServiceRefType(FullyQualifiedClassType serviceRefType) {
      this.generatedSetterHelperImpl(serviceRefType, SERVICEREFTYPE$10, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServiceRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVICEREFTYPE$10);
         return target;
      }
   }

   public void unsetServiceRefType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEREFTYPE$10, 0);
      }
   }

   public XsdAnyURIType getWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdAnyURIType target = null;
         target = (XsdAnyURIType)this.get_store().find_element_user(WSDLFILE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLFILE$12) != 0;
      }
   }

   public void setWsdlFile(XsdAnyURIType wsdlFile) {
      this.generatedSetterHelperImpl(wsdlFile, WSDLFILE$12, 0, (short)1);
   }

   public XsdAnyURIType addNewWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdAnyURIType target = null;
         target = (XsdAnyURIType)this.get_store().add_element_user(WSDLFILE$12);
         return target;
      }
   }

   public void unsetWsdlFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLFILE$12, 0);
      }
   }

   public PathType getJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(JAXRPCMAPPINGFILE$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JAXRPCMAPPINGFILE$14) != 0;
      }
   }

   public void setJaxrpcMappingFile(PathType jaxrpcMappingFile) {
      this.generatedSetterHelperImpl(jaxrpcMappingFile, JAXRPCMAPPINGFILE$14, 0, (short)1);
   }

   public PathType addNewJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(JAXRPCMAPPINGFILE$14);
         return target;
      }
   }

   public void unsetJaxrpcMappingFile() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JAXRPCMAPPINGFILE$14, 0);
      }
   }

   public XsdQNameType getServiceQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(SERVICEQNAME$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServiceQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEQNAME$16) != 0;
      }
   }

   public void setServiceQname(XsdQNameType serviceQname) {
      this.generatedSetterHelperImpl(serviceQname, SERVICEQNAME$16, 0, (short)1);
   }

   public XsdQNameType addNewServiceQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(SERVICEQNAME$16);
         return target;
      }
   }

   public void unsetServiceQname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEQNAME$16, 0);
      }
   }

   public PortComponentRefType[] getPortComponentRefArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PORTCOMPONENTREF$18, targetList);
         PortComponentRefType[] result = new PortComponentRefType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PortComponentRefType getPortComponentRefArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentRefType target = null;
         target = (PortComponentRefType)this.get_store().find_element_user(PORTCOMPONENTREF$18, i);
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
         return this.get_store().count_elements(PORTCOMPONENTREF$18);
      }
   }

   public void setPortComponentRefArray(PortComponentRefType[] portComponentRefArray) {
      this.check_orphaned();
      this.arraySetterHelper(portComponentRefArray, PORTCOMPONENTREF$18);
   }

   public void setPortComponentRefArray(int i, PortComponentRefType portComponentRef) {
      this.generatedSetterHelperImpl(portComponentRef, PORTCOMPONENTREF$18, i, (short)2);
   }

   public PortComponentRefType insertNewPortComponentRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentRefType target = null;
         target = (PortComponentRefType)this.get_store().insert_element_user(PORTCOMPONENTREF$18, i);
         return target;
      }
   }

   public PortComponentRefType addNewPortComponentRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PortComponentRefType target = null;
         target = (PortComponentRefType)this.get_store().add_element_user(PORTCOMPONENTREF$18);
         return target;
      }
   }

   public void removePortComponentRef(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORTCOMPONENTREF$18, i);
      }
   }

   public HandlerType[] getHandlerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HANDLER$20, targetList);
         HandlerType[] result = new HandlerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public HandlerType getHandlerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().find_element_user(HANDLER$20, i);
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
         return this.get_store().count_elements(HANDLER$20);
      }
   }

   public void setHandlerArray(HandlerType[] handlerArray) {
      this.check_orphaned();
      this.arraySetterHelper(handlerArray, HANDLER$20);
   }

   public void setHandlerArray(int i, HandlerType handler) {
      this.generatedSetterHelperImpl(handler, HANDLER$20, i, (short)2);
   }

   public HandlerType insertNewHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().insert_element_user(HANDLER$20, i);
         return target;
      }
   }

   public HandlerType addNewHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().add_element_user(HANDLER$20);
         return target;
      }
   }

   public void removeHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLER$20, i);
      }
   }

   public HandlerChainsType getHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainsType target = null;
         target = (HandlerChainsType)this.get_store().find_element_user(HANDLERCHAINS$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HANDLERCHAINS$22) != 0;
      }
   }

   public void setHandlerChains(HandlerChainsType handlerChains) {
      this.generatedSetterHelperImpl(handlerChains, HANDLERCHAINS$22, 0, (short)1);
   }

   public HandlerChainsType addNewHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainsType target = null;
         target = (HandlerChainsType)this.get_store().add_element_user(HANDLERCHAINS$22);
         return target;
      }
   }

   public void unsetHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLERCHAINS$22, 0);
      }
   }

   public XsdStringType getMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(MAPPEDNAME$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPEDNAME$24) != 0;
      }
   }

   public void setMappedName(XsdStringType mappedName) {
      this.generatedSetterHelperImpl(mappedName, MAPPEDNAME$24, 0, (short)1);
   }

   public XsdStringType addNewMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(MAPPEDNAME$24);
         return target;
      }
   }

   public void unsetMappedName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPEDNAME$24, 0);
      }
   }

   public InjectionTargetType[] getInjectionTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INJECTIONTARGET$26, targetList);
         InjectionTargetType[] result = new InjectionTargetType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InjectionTargetType getInjectionTargetArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().find_element_user(INJECTIONTARGET$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInjectionTargetArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INJECTIONTARGET$26);
      }
   }

   public void setInjectionTargetArray(InjectionTargetType[] injectionTargetArray) {
      this.check_orphaned();
      this.arraySetterHelper(injectionTargetArray, INJECTIONTARGET$26);
   }

   public void setInjectionTargetArray(int i, InjectionTargetType injectionTarget) {
      this.generatedSetterHelperImpl(injectionTarget, INJECTIONTARGET$26, i, (short)2);
   }

   public InjectionTargetType insertNewInjectionTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().insert_element_user(INJECTIONTARGET$26, i);
         return target;
      }
   }

   public InjectionTargetType addNewInjectionTarget() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InjectionTargetType target = null;
         target = (InjectionTargetType)this.get_store().add_element_user(INJECTIONTARGET$26);
         return target;
      }
   }

   public void removeInjectionTarget(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INJECTIONTARGET$26, i);
      }
   }

   public XsdStringType getLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(LOOKUPNAME$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOOKUPNAME$28) != 0;
      }
   }

   public void setLookupName(XsdStringType lookupName) {
      this.generatedSetterHelperImpl(lookupName, LOOKUPNAME$28, 0, (short)1);
   }

   public XsdStringType addNewLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(LOOKUPNAME$28);
         return target;
      }
   }

   public void unsetLookupName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOOKUPNAME$28, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$30);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$30);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$30) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$30);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$30);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$30);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$30);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$30);
      }
   }
}
