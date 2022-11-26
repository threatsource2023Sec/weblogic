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

public interface JavaIdentifierType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JavaIdentifierType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("javaidentifiertype8109type");

   public static final class Factory {
      public static JavaIdentifierType newInstance() {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().newInstance(JavaIdentifierType.type, (XmlOptions)null);
      }

      public static JavaIdentifierType newInstance(XmlOptions options) {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().newInstance(JavaIdentifierType.type, options);
      }

      public static JavaIdentifierType parse(java.lang.String xmlAsString) throws XmlException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaIdentifierType.type, (XmlOptions)null);
      }

      public static JavaIdentifierType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JavaIdentifierType.type, options);
      }

      public static JavaIdentifierType parse(File file) throws XmlException, IOException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(file, JavaIdentifierType.type, (XmlOptions)null);
      }

      public static JavaIdentifierType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(file, JavaIdentifierType.type, options);
      }

      public static JavaIdentifierType parse(URL u) throws XmlException, IOException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(u, JavaIdentifierType.type, (XmlOptions)null);
      }

      public static JavaIdentifierType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(u, JavaIdentifierType.type, options);
      }

      public static JavaIdentifierType parse(InputStream is) throws XmlException, IOException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(is, JavaIdentifierType.type, (XmlOptions)null);
      }

      public static JavaIdentifierType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(is, JavaIdentifierType.type, options);
      }

      public static JavaIdentifierType parse(Reader r) throws XmlException, IOException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(r, JavaIdentifierType.type, (XmlOptions)null);
      }

      public static JavaIdentifierType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(r, JavaIdentifierType.type, options);
      }

      public static JavaIdentifierType parse(XMLStreamReader sr) throws XmlException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(sr, JavaIdentifierType.type, (XmlOptions)null);
      }

      public static JavaIdentifierType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(sr, JavaIdentifierType.type, options);
      }

      public static JavaIdentifierType parse(Node node) throws XmlException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(node, JavaIdentifierType.type, (XmlOptions)null);
      }

      public static JavaIdentifierType parse(Node node, XmlOptions options) throws XmlException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(node, JavaIdentifierType.type, options);
      }

      /** @deprecated */
      public static JavaIdentifierType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(xis, JavaIdentifierType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JavaIdentifierType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JavaIdentifierType)XmlBeans.getContextTypeLoader().parse(xis, JavaIdentifierType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaIdentifierType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JavaIdentifierType.type, options);
      }

      private Factory() {
      }
   }
}
