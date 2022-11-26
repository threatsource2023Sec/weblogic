package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PortComponentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PortComponentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("portcomponenttype59detype");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   DisplayNameType getDisplayName();

   boolean isSetDisplayName();

   void setDisplayName(DisplayNameType var1);

   DisplayNameType addNewDisplayName();

   void unsetDisplayName();

   IconType getIcon();

   boolean isSetIcon();

   void setIcon(IconType var1);

   IconType addNewIcon();

   void unsetIcon();

   String getPortComponentName();

   void setPortComponentName(String var1);

   String addNewPortComponentName();

   XsdQNameType getWsdlService();

   boolean isSetWsdlService();

   void setWsdlService(XsdQNameType var1);

   XsdQNameType addNewWsdlService();

   void unsetWsdlService();

   XsdQNameType getWsdlPort();

   boolean isSetWsdlPort();

   void setWsdlPort(XsdQNameType var1);

   XsdQNameType addNewWsdlPort();

   void unsetWsdlPort();

   TrueFalseType getEnableMtom();

   boolean isSetEnableMtom();

   void setEnableMtom(TrueFalseType var1);

   TrueFalseType addNewEnableMtom();

   void unsetEnableMtom();

   XsdNonNegativeIntegerType getMtomThreshold();

   boolean isSetMtomThreshold();

   void setMtomThreshold(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewMtomThreshold();

   void unsetMtomThreshold();

   AddressingType getAddressing();

   boolean isSetAddressing();

   void setAddressing(AddressingType var1);

   AddressingType addNewAddressing();

   void unsetAddressing();

   RespectBindingType getRespectBinding();

   boolean isSetRespectBinding();

   void setRespectBinding(RespectBindingType var1);

   RespectBindingType addNewRespectBinding();

   void unsetRespectBinding();

   java.lang.String getProtocolBinding();

   ProtocolBindingType xgetProtocolBinding();

   boolean isSetProtocolBinding();

   void setProtocolBinding(java.lang.String var1);

   void xsetProtocolBinding(ProtocolBindingType var1);

   void unsetProtocolBinding();

   FullyQualifiedClassType getServiceEndpointInterface();

   boolean isSetServiceEndpointInterface();

   void setServiceEndpointInterface(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewServiceEndpointInterface();

   void unsetServiceEndpointInterface();

   ServiceImplBeanType getServiceImplBean();

   void setServiceImplBean(ServiceImplBeanType var1);

   ServiceImplBeanType addNewServiceImplBean();

   HandlerType[] getHandlerArray();

   HandlerType getHandlerArray(int var1);

   int sizeOfHandlerArray();

   void setHandlerArray(HandlerType[] var1);

   void setHandlerArray(int var1, HandlerType var2);

   HandlerType insertNewHandler(int var1);

   HandlerType addNewHandler();

   void removeHandler(int var1);

   HandlerChainsType getHandlerChains();

   boolean isSetHandlerChains();

   void setHandlerChains(HandlerChainsType var1);

   HandlerChainsType addNewHandlerChains();

   void unsetHandlerChains();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PortComponentType newInstance() {
         return (PortComponentType)XmlBeans.getContextTypeLoader().newInstance(PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType newInstance(XmlOptions options) {
         return (PortComponentType)XmlBeans.getContextTypeLoader().newInstance(PortComponentType.type, options);
      }

      public static PortComponentType parse(java.lang.String xmlAsString) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PortComponentType.type, options);
      }

      public static PortComponentType parse(File file) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(file, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(file, PortComponentType.type, options);
      }

      public static PortComponentType parse(URL u) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(u, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(u, PortComponentType.type, options);
      }

      public static PortComponentType parse(InputStream is) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(is, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(is, PortComponentType.type, options);
      }

      public static PortComponentType parse(Reader r) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(r, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(r, PortComponentType.type, options);
      }

      public static PortComponentType parse(XMLStreamReader sr) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(sr, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(sr, PortComponentType.type, options);
      }

      public static PortComponentType parse(Node node) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(node, PortComponentType.type, (XmlOptions)null);
      }

      public static PortComponentType parse(Node node, XmlOptions options) throws XmlException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(node, PortComponentType.type, options);
      }

      /** @deprecated */
      public static PortComponentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(xis, PortComponentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PortComponentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PortComponentType)XmlBeans.getContextTypeLoader().parse(xis, PortComponentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortComponentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PortComponentType.type, options);
      }

      private Factory() {
      }
   }
}
