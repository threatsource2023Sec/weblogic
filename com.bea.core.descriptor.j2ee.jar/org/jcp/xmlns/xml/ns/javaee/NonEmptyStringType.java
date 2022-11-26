package org.jcp.xmlns.xml.ns.javaee;

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

public interface NonEmptyStringType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NonEmptyStringType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("nonemptystringtypeb87atype");

   public static final class Factory {
      public static NonEmptyStringType newInstance() {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().newInstance(NonEmptyStringType.type, (XmlOptions)null);
      }

      public static NonEmptyStringType newInstance(XmlOptions options) {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().newInstance(NonEmptyStringType.type, options);
      }

      public static NonEmptyStringType parse(java.lang.String xmlAsString) throws XmlException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NonEmptyStringType.type, (XmlOptions)null);
      }

      public static NonEmptyStringType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, NonEmptyStringType.type, options);
      }

      public static NonEmptyStringType parse(File file) throws XmlException, IOException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(file, NonEmptyStringType.type, (XmlOptions)null);
      }

      public static NonEmptyStringType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(file, NonEmptyStringType.type, options);
      }

      public static NonEmptyStringType parse(URL u) throws XmlException, IOException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(u, NonEmptyStringType.type, (XmlOptions)null);
      }

      public static NonEmptyStringType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(u, NonEmptyStringType.type, options);
      }

      public static NonEmptyStringType parse(InputStream is) throws XmlException, IOException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(is, NonEmptyStringType.type, (XmlOptions)null);
      }

      public static NonEmptyStringType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(is, NonEmptyStringType.type, options);
      }

      public static NonEmptyStringType parse(Reader r) throws XmlException, IOException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(r, NonEmptyStringType.type, (XmlOptions)null);
      }

      public static NonEmptyStringType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(r, NonEmptyStringType.type, options);
      }

      public static NonEmptyStringType parse(XMLStreamReader sr) throws XmlException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(sr, NonEmptyStringType.type, (XmlOptions)null);
      }

      public static NonEmptyStringType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(sr, NonEmptyStringType.type, options);
      }

      public static NonEmptyStringType parse(Node node) throws XmlException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(node, NonEmptyStringType.type, (XmlOptions)null);
      }

      public static NonEmptyStringType parse(Node node, XmlOptions options) throws XmlException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(node, NonEmptyStringType.type, options);
      }

      /** @deprecated */
      public static NonEmptyStringType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(xis, NonEmptyStringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NonEmptyStringType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NonEmptyStringType)XmlBeans.getContextTypeLoader().parse(xis, NonEmptyStringType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NonEmptyStringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NonEmptyStringType.type, options);
      }

      private Factory() {
      }
   }
}
