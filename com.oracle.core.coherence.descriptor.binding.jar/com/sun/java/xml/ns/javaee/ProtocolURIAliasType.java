package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlToken;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ProtocolURIAliasType extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProtocolURIAliasType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("protocolurialiastype80b4type");

   public static final class Factory {
      public static ProtocolURIAliasType newValue(Object obj) {
         return (ProtocolURIAliasType)ProtocolURIAliasType.type.newValue(obj);
      }

      public static ProtocolURIAliasType newInstance() {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().newInstance(ProtocolURIAliasType.type, (XmlOptions)null);
      }

      public static ProtocolURIAliasType newInstance(XmlOptions options) {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().newInstance(ProtocolURIAliasType.type, options);
      }

      public static ProtocolURIAliasType parse(java.lang.String xmlAsString) throws XmlException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      public static ProtocolURIAliasType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProtocolURIAliasType.type, options);
      }

      public static ProtocolURIAliasType parse(File file) throws XmlException, IOException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(file, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      public static ProtocolURIAliasType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(file, ProtocolURIAliasType.type, options);
      }

      public static ProtocolURIAliasType parse(URL u) throws XmlException, IOException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(u, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      public static ProtocolURIAliasType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(u, ProtocolURIAliasType.type, options);
      }

      public static ProtocolURIAliasType parse(InputStream is) throws XmlException, IOException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(is, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      public static ProtocolURIAliasType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(is, ProtocolURIAliasType.type, options);
      }

      public static ProtocolURIAliasType parse(Reader r) throws XmlException, IOException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(r, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      public static ProtocolURIAliasType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(r, ProtocolURIAliasType.type, options);
      }

      public static ProtocolURIAliasType parse(XMLStreamReader sr) throws XmlException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(sr, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      public static ProtocolURIAliasType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(sr, ProtocolURIAliasType.type, options);
      }

      public static ProtocolURIAliasType parse(Node node) throws XmlException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(node, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      public static ProtocolURIAliasType parse(Node node, XmlOptions options) throws XmlException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(node, ProtocolURIAliasType.type, options);
      }

      /** @deprecated */
      public static ProtocolURIAliasType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(xis, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProtocolURIAliasType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProtocolURIAliasType)XmlBeans.getContextTypeLoader().parse(xis, ProtocolURIAliasType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProtocolURIAliasType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProtocolURIAliasType.type, options);
      }

      private Factory() {
      }
   }
}
