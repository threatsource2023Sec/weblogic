package com.sun.java.xml.ns.j2Ee;

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

public interface WsdlMessageMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WsdlMessageMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("wsdlmessagemappingtype5772type");

   WsdlMessageType getWsdlMessage();

   void setWsdlMessage(WsdlMessageType var1);

   WsdlMessageType addNewWsdlMessage();

   WsdlMessagePartNameType getWsdlMessagePartName();

   void setWsdlMessagePartName(WsdlMessagePartNameType var1);

   WsdlMessagePartNameType addNewWsdlMessagePartName();

   ParameterModeType getParameterMode();

   void setParameterMode(ParameterModeType var1);

   ParameterModeType addNewParameterMode();

   EmptyType getSoapHeader();

   boolean isSetSoapHeader();

   void setSoapHeader(EmptyType var1);

   EmptyType addNewSoapHeader();

   void unsetSoapHeader();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WsdlMessageMappingType newInstance() {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().newInstance(WsdlMessageMappingType.type, (XmlOptions)null);
      }

      public static WsdlMessageMappingType newInstance(XmlOptions options) {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().newInstance(WsdlMessageMappingType.type, options);
      }

      public static WsdlMessageMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      public static WsdlMessageMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsdlMessageMappingType.type, options);
      }

      public static WsdlMessageMappingType parse(File file) throws XmlException, IOException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(file, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      public static WsdlMessageMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(file, WsdlMessageMappingType.type, options);
      }

      public static WsdlMessageMappingType parse(URL u) throws XmlException, IOException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(u, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      public static WsdlMessageMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(u, WsdlMessageMappingType.type, options);
      }

      public static WsdlMessageMappingType parse(InputStream is) throws XmlException, IOException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(is, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      public static WsdlMessageMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(is, WsdlMessageMappingType.type, options);
      }

      public static WsdlMessageMappingType parse(Reader r) throws XmlException, IOException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(r, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      public static WsdlMessageMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(r, WsdlMessageMappingType.type, options);
      }

      public static WsdlMessageMappingType parse(XMLStreamReader sr) throws XmlException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(sr, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      public static WsdlMessageMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(sr, WsdlMessageMappingType.type, options);
      }

      public static WsdlMessageMappingType parse(Node node) throws XmlException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(node, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      public static WsdlMessageMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(node, WsdlMessageMappingType.type, options);
      }

      /** @deprecated */
      public static WsdlMessageMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(xis, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WsdlMessageMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WsdlMessageMappingType)XmlBeans.getContextTypeLoader().parse(xis, WsdlMessageMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlMessageMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsdlMessageMappingType.type, options);
      }

      private Factory() {
      }
   }
}
