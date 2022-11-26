package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.AddressingType;
import org.jcp.xmlns.xml.ns.javaee.DescriptionType;
import org.jcp.xmlns.xml.ns.javaee.DisplayNameType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.HandlerChainsType;
import org.jcp.xmlns.xml.ns.javaee.HandlerType;
import org.jcp.xmlns.xml.ns.javaee.IconType;
import org.jcp.xmlns.xml.ns.javaee.PortComponentType;
import org.jcp.xmlns.xml.ns.javaee.ProtocolBindingType;
import org.jcp.xmlns.xml.ns.javaee.RespectBindingType;
import org.jcp.xmlns.xml.ns.javaee.ServiceImplBeanType;
import org.jcp.xmlns.xml.ns.javaee.String;
import org.jcp.xmlns.xml.ns.javaee.TrueFalseType;
import org.jcp.xmlns.xml.ns.javaee.XsdNonNegativeIntegerType;
import org.jcp.xmlns.xml.ns.javaee.XsdQNameType;

public class PortComponentTypeImpl extends XmlComplexContentImpl implements PortComponentType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "display-name");
   private static final QName ICON$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "icon");
   private static final QName PORTCOMPONENTNAME$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "port-component-name");
   private static final QName WSDLSERVICE$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "wsdl-service");
   private static final QName WSDLPORT$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "wsdl-port");
   private static final QName ENABLEMTOM$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "enable-mtom");
   private static final QName MTOMTHRESHOLD$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "mtom-threshold");
   private static final QName ADDRESSING$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "addressing");
   private static final QName RESPECTBINDING$18 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "respect-binding");
   private static final QName PROTOCOLBINDING$20 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "protocol-binding");
   private static final QName SERVICEENDPOINTINTERFACE$22 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-endpoint-interface");
   private static final QName SERVICEIMPLBEAN$24 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "service-impl-bean");
   private static final QName HANDLER$26 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "handler");
   private static final QName HANDLERCHAINS$28 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "handler-chains");
   private static final QName ID$30 = new QName("", "id");

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

   public XsdQNameType getWsdlService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(WSDLSERVICE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLSERVICE$8) != 0;
      }
   }

   public void setWsdlService(XsdQNameType wsdlService) {
      this.generatedSetterHelperImpl(wsdlService, WSDLSERVICE$8, 0, (short)1);
   }

   public XsdQNameType addNewWsdlService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(WSDLSERVICE$8);
         return target;
      }
   }

   public void unsetWsdlService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLSERVICE$8, 0);
      }
   }

   public XsdQNameType getWsdlPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().find_element_user(WSDLPORT$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWsdlPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WSDLPORT$10) != 0;
      }
   }

   public void setWsdlPort(XsdQNameType wsdlPort) {
      this.generatedSetterHelperImpl(wsdlPort, WSDLPORT$10, 0, (short)1);
   }

   public XsdQNameType addNewWsdlPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdQNameType target = null;
         target = (XsdQNameType)this.get_store().add_element_user(WSDLPORT$10);
         return target;
      }
   }

   public void unsetWsdlPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WSDLPORT$10, 0);
      }
   }

   public TrueFalseType getEnableMtom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLEMTOM$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnableMtom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLEMTOM$12) != 0;
      }
   }

   public void setEnableMtom(TrueFalseType enableMtom) {
      this.generatedSetterHelperImpl(enableMtom, ENABLEMTOM$12, 0, (short)1);
   }

   public TrueFalseType addNewEnableMtom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLEMTOM$12);
         return target;
      }
   }

   public void unsetEnableMtom() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLEMTOM$12, 0);
      }
   }

   public XsdNonNegativeIntegerType getMtomThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MTOMTHRESHOLD$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMtomThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MTOMTHRESHOLD$14) != 0;
      }
   }

   public void setMtomThreshold(XsdNonNegativeIntegerType mtomThreshold) {
      this.generatedSetterHelperImpl(mtomThreshold, MTOMTHRESHOLD$14, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMtomThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MTOMTHRESHOLD$14);
         return target;
      }
   }

   public void unsetMtomThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MTOMTHRESHOLD$14, 0);
      }
   }

   public AddressingType getAddressing() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AddressingType target = null;
         target = (AddressingType)this.get_store().find_element_user(ADDRESSING$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAddressing() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADDRESSING$16) != 0;
      }
   }

   public void setAddressing(AddressingType addressing) {
      this.generatedSetterHelperImpl(addressing, ADDRESSING$16, 0, (short)1);
   }

   public AddressingType addNewAddressing() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AddressingType target = null;
         target = (AddressingType)this.get_store().add_element_user(ADDRESSING$16);
         return target;
      }
   }

   public void unsetAddressing() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADDRESSING$16, 0);
      }
   }

   public RespectBindingType getRespectBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RespectBindingType target = null;
         target = (RespectBindingType)this.get_store().find_element_user(RESPECTBINDING$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRespectBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESPECTBINDING$18) != 0;
      }
   }

   public void setRespectBinding(RespectBindingType respectBinding) {
      this.generatedSetterHelperImpl(respectBinding, RESPECTBINDING$18, 0, (short)1);
   }

   public RespectBindingType addNewRespectBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RespectBindingType target = null;
         target = (RespectBindingType)this.get_store().add_element_user(RESPECTBINDING$18);
         return target;
      }
   }

   public void unsetRespectBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESPECTBINDING$18, 0);
      }
   }

   public java.lang.String getProtocolBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROTOCOLBINDING$20, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public ProtocolBindingType xgetProtocolBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProtocolBindingType target = null;
         target = (ProtocolBindingType)this.get_store().find_element_user(PROTOCOLBINDING$20, 0);
         return target;
      }
   }

   public boolean isSetProtocolBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROTOCOLBINDING$20) != 0;
      }
   }

   public void setProtocolBinding(java.lang.String protocolBinding) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROTOCOLBINDING$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PROTOCOLBINDING$20);
         }

         target.setStringValue(protocolBinding);
      }
   }

   public void xsetProtocolBinding(ProtocolBindingType protocolBinding) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProtocolBindingType target = null;
         target = (ProtocolBindingType)this.get_store().find_element_user(PROTOCOLBINDING$20, 0);
         if (target == null) {
            target = (ProtocolBindingType)this.get_store().add_element_user(PROTOCOLBINDING$20);
         }

         target.set(protocolBinding);
      }
   }

   public void unsetProtocolBinding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROTOCOLBINDING$20, 0);
      }
   }

   public FullyQualifiedClassType getServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(SERVICEENDPOINTINTERFACE$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVICEENDPOINTINTERFACE$22) != 0;
      }
   }

   public void setServiceEndpointInterface(FullyQualifiedClassType serviceEndpointInterface) {
      this.generatedSetterHelperImpl(serviceEndpointInterface, SERVICEENDPOINTINTERFACE$22, 0, (short)1);
   }

   public FullyQualifiedClassType addNewServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(SERVICEENDPOINTINTERFACE$22);
         return target;
      }
   }

   public void unsetServiceEndpointInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVICEENDPOINTINTERFACE$22, 0);
      }
   }

   public ServiceImplBeanType getServiceImplBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceImplBeanType target = null;
         target = (ServiceImplBeanType)this.get_store().find_element_user(SERVICEIMPLBEAN$24, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceImplBean(ServiceImplBeanType serviceImplBean) {
      this.generatedSetterHelperImpl(serviceImplBean, SERVICEIMPLBEAN$24, 0, (short)1);
   }

   public ServiceImplBeanType addNewServiceImplBean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceImplBeanType target = null;
         target = (ServiceImplBeanType)this.get_store().add_element_user(SERVICEIMPLBEAN$24);
         return target;
      }
   }

   public HandlerType[] getHandlerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(HANDLER$26, targetList);
         HandlerType[] result = new HandlerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public HandlerType getHandlerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().find_element_user(HANDLER$26, i);
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
         return this.get_store().count_elements(HANDLER$26);
      }
   }

   public void setHandlerArray(HandlerType[] handlerArray) {
      this.check_orphaned();
      this.arraySetterHelper(handlerArray, HANDLER$26);
   }

   public void setHandlerArray(int i, HandlerType handler) {
      this.generatedSetterHelperImpl(handler, HANDLER$26, i, (short)2);
   }

   public HandlerType insertNewHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().insert_element_user(HANDLER$26, i);
         return target;
      }
   }

   public HandlerType addNewHandler() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerType target = null;
         target = (HandlerType)this.get_store().add_element_user(HANDLER$26);
         return target;
      }
   }

   public void removeHandler(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLER$26, i);
      }
   }

   public HandlerChainsType getHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainsType target = null;
         target = (HandlerChainsType)this.get_store().find_element_user(HANDLERCHAINS$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HANDLERCHAINS$28) != 0;
      }
   }

   public void setHandlerChains(HandlerChainsType handlerChains) {
      this.generatedSetterHelperImpl(handlerChains, HANDLERCHAINS$28, 0, (short)1);
   }

   public HandlerChainsType addNewHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HandlerChainsType target = null;
         target = (HandlerChainsType)this.get_store().add_element_user(HANDLERCHAINS$28);
         return target;
      }
   }

   public void unsetHandlerChains() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HANDLERCHAINS$28, 0);
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
