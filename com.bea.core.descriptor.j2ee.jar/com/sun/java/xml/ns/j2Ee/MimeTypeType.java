package com.sun.java.xml.ns.j2Ee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface MimeTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MimeTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("mimetypetype29d4type");

   public static final class Factory {
      public static MimeTypeType newInstance() {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().newInstance(MimeTypeType.type, (XmlOptions)null);
      }

      public static MimeTypeType newInstance(XmlOptions options) {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().newInstance(MimeTypeType.type, options);
      }

      public static MimeTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MimeTypeType.type, (XmlOptions)null);
      }

      public static MimeTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MimeTypeType.type, options);
      }

      public static MimeTypeType parse(File file) throws XmlException, IOException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(file, MimeTypeType.type, (XmlOptions)null);
      }

      public static MimeTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(file, MimeTypeType.type, options);
      }

      public static MimeTypeType parse(URL u) throws XmlException, IOException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(u, MimeTypeType.type, (XmlOptions)null);
      }

      public static MimeTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(u, MimeTypeType.type, options);
      }

      public static MimeTypeType parse(InputStream is) throws XmlException, IOException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(is, MimeTypeType.type, (XmlOptions)null);
      }

      public static MimeTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(is, MimeTypeType.type, options);
      }

      public static MimeTypeType parse(Reader r) throws XmlException, IOException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(r, MimeTypeType.type, (XmlOptions)null);
      }

      public static MimeTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(r, MimeTypeType.type, options);
      }

      public static MimeTypeType parse(XMLStreamReader sr) throws XmlException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(sr, MimeTypeType.type, (XmlOptions)null);
      }

      public static MimeTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(sr, MimeTypeType.type, options);
      }

      public static MimeTypeType parse(Node node) throws XmlException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(node, MimeTypeType.type, (XmlOptions)null);
      }

      public static MimeTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(node, MimeTypeType.type, options);
      }

      /** @deprecated */
      public static MimeTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(xis, MimeTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MimeTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MimeTypeType)XmlBeans.getContextTypeLoader().parse(xis, MimeTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MimeTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MimeTypeType.type, options);
      }

      private Factory() {
      }
   }
}
