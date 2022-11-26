package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface InboundCallerPrincipalMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InboundCallerPrincipalMappingType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("inboundcallerprincipalmappingtypefa83type");

   String getEisCallerPrincipal();

   XmlString xgetEisCallerPrincipal();

   void setEisCallerPrincipal(String var1);

   void xsetEisCallerPrincipal(XmlString var1);

   AnonPrincipalType getMappedCallerPrincipal();

   void setMappedCallerPrincipal(AnonPrincipalType var1);

   AnonPrincipalType addNewMappedCallerPrincipal();

   public static final class Factory {
      public static InboundCallerPrincipalMappingType newInstance() {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().newInstance(InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundCallerPrincipalMappingType newInstance(XmlOptions options) {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().newInstance(InboundCallerPrincipalMappingType.type, options);
      }

      public static InboundCallerPrincipalMappingType parse(String xmlAsString) throws XmlException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundCallerPrincipalMappingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundCallerPrincipalMappingType.type, options);
      }

      public static InboundCallerPrincipalMappingType parse(File file) throws XmlException, IOException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(file, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundCallerPrincipalMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(file, InboundCallerPrincipalMappingType.type, options);
      }

      public static InboundCallerPrincipalMappingType parse(URL u) throws XmlException, IOException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(u, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundCallerPrincipalMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(u, InboundCallerPrincipalMappingType.type, options);
      }

      public static InboundCallerPrincipalMappingType parse(InputStream is) throws XmlException, IOException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(is, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundCallerPrincipalMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(is, InboundCallerPrincipalMappingType.type, options);
      }

      public static InboundCallerPrincipalMappingType parse(Reader r) throws XmlException, IOException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(r, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundCallerPrincipalMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(r, InboundCallerPrincipalMappingType.type, options);
      }

      public static InboundCallerPrincipalMappingType parse(XMLStreamReader sr) throws XmlException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(sr, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundCallerPrincipalMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(sr, InboundCallerPrincipalMappingType.type, options);
      }

      public static InboundCallerPrincipalMappingType parse(Node node) throws XmlException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(node, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      public static InboundCallerPrincipalMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(node, InboundCallerPrincipalMappingType.type, options);
      }

      /** @deprecated */
      public static InboundCallerPrincipalMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(xis, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InboundCallerPrincipalMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InboundCallerPrincipalMappingType)XmlBeans.getContextTypeLoader().parse(xis, InboundCallerPrincipalMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundCallerPrincipalMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundCallerPrincipalMappingType.type, options);
      }

      private Factory() {
      }
   }
}
