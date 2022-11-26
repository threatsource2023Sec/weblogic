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

public interface EjbLinkType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbLinkType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("ejblinktype52e1type");

   public static final class Factory {
      public static EjbLinkType newInstance() {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().newInstance(EjbLinkType.type, (XmlOptions)null);
      }

      public static EjbLinkType newInstance(XmlOptions options) {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().newInstance(EjbLinkType.type, options);
      }

      public static EjbLinkType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbLinkType.type, (XmlOptions)null);
      }

      public static EjbLinkType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbLinkType.type, options);
      }

      public static EjbLinkType parse(File file) throws XmlException, IOException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(file, EjbLinkType.type, (XmlOptions)null);
      }

      public static EjbLinkType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(file, EjbLinkType.type, options);
      }

      public static EjbLinkType parse(URL u) throws XmlException, IOException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(u, EjbLinkType.type, (XmlOptions)null);
      }

      public static EjbLinkType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(u, EjbLinkType.type, options);
      }

      public static EjbLinkType parse(InputStream is) throws XmlException, IOException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(is, EjbLinkType.type, (XmlOptions)null);
      }

      public static EjbLinkType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(is, EjbLinkType.type, options);
      }

      public static EjbLinkType parse(Reader r) throws XmlException, IOException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(r, EjbLinkType.type, (XmlOptions)null);
      }

      public static EjbLinkType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(r, EjbLinkType.type, options);
      }

      public static EjbLinkType parse(XMLStreamReader sr) throws XmlException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(sr, EjbLinkType.type, (XmlOptions)null);
      }

      public static EjbLinkType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(sr, EjbLinkType.type, options);
      }

      public static EjbLinkType parse(Node node) throws XmlException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(node, EjbLinkType.type, (XmlOptions)null);
      }

      public static EjbLinkType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(node, EjbLinkType.type, options);
      }

      /** @deprecated */
      public static EjbLinkType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(xis, EjbLinkType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbLinkType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbLinkType)XmlBeans.getContextTypeLoader().parse(xis, EjbLinkType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbLinkType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbLinkType.type, options);
      }

      private Factory() {
      }
   }
}
