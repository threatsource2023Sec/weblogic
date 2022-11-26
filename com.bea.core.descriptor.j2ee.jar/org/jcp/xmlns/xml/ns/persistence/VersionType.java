package org.jcp.xmlns.xml.ns.persistence;

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

public interface VersionType extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(VersionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("versiontype14dctype");

   public static final class Factory {
      public static VersionType newValue(Object obj) {
         return (VersionType)VersionType.type.newValue(obj);
      }

      public static VersionType newInstance() {
         return (VersionType)XmlBeans.getContextTypeLoader().newInstance(VersionType.type, (XmlOptions)null);
      }

      public static VersionType newInstance(XmlOptions options) {
         return (VersionType)XmlBeans.getContextTypeLoader().newInstance(VersionType.type, options);
      }

      public static VersionType parse(String xmlAsString) throws XmlException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VersionType.type, (XmlOptions)null);
      }

      public static VersionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, VersionType.type, options);
      }

      public static VersionType parse(File file) throws XmlException, IOException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(file, VersionType.type, (XmlOptions)null);
      }

      public static VersionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(file, VersionType.type, options);
      }

      public static VersionType parse(URL u) throws XmlException, IOException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(u, VersionType.type, (XmlOptions)null);
      }

      public static VersionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(u, VersionType.type, options);
      }

      public static VersionType parse(InputStream is) throws XmlException, IOException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(is, VersionType.type, (XmlOptions)null);
      }

      public static VersionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(is, VersionType.type, options);
      }

      public static VersionType parse(Reader r) throws XmlException, IOException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(r, VersionType.type, (XmlOptions)null);
      }

      public static VersionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(r, VersionType.type, options);
      }

      public static VersionType parse(XMLStreamReader sr) throws XmlException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(sr, VersionType.type, (XmlOptions)null);
      }

      public static VersionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(sr, VersionType.type, options);
      }

      public static VersionType parse(Node node) throws XmlException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(node, VersionType.type, (XmlOptions)null);
      }

      public static VersionType parse(Node node, XmlOptions options) throws XmlException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(node, VersionType.type, options);
      }

      /** @deprecated */
      public static VersionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(xis, VersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static VersionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (VersionType)XmlBeans.getContextTypeLoader().parse(xis, VersionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, VersionType.type, options);
      }

      private Factory() {
      }
   }
}
