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

public interface EjbClassType extends FullyQualifiedClassType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbClassType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("ejbclasstype4675type");

   public static final class Factory {
      public static EjbClassType newInstance() {
         return (EjbClassType)XmlBeans.getContextTypeLoader().newInstance(EjbClassType.type, (XmlOptions)null);
      }

      public static EjbClassType newInstance(XmlOptions options) {
         return (EjbClassType)XmlBeans.getContextTypeLoader().newInstance(EjbClassType.type, options);
      }

      public static EjbClassType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbClassType.type, (XmlOptions)null);
      }

      public static EjbClassType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbClassType.type, options);
      }

      public static EjbClassType parse(File file) throws XmlException, IOException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(file, EjbClassType.type, (XmlOptions)null);
      }

      public static EjbClassType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(file, EjbClassType.type, options);
      }

      public static EjbClassType parse(URL u) throws XmlException, IOException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(u, EjbClassType.type, (XmlOptions)null);
      }

      public static EjbClassType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(u, EjbClassType.type, options);
      }

      public static EjbClassType parse(InputStream is) throws XmlException, IOException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(is, EjbClassType.type, (XmlOptions)null);
      }

      public static EjbClassType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(is, EjbClassType.type, options);
      }

      public static EjbClassType parse(Reader r) throws XmlException, IOException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(r, EjbClassType.type, (XmlOptions)null);
      }

      public static EjbClassType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(r, EjbClassType.type, options);
      }

      public static EjbClassType parse(XMLStreamReader sr) throws XmlException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(sr, EjbClassType.type, (XmlOptions)null);
      }

      public static EjbClassType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(sr, EjbClassType.type, options);
      }

      public static EjbClassType parse(Node node) throws XmlException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(node, EjbClassType.type, (XmlOptions)null);
      }

      public static EjbClassType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(node, EjbClassType.type, options);
      }

      /** @deprecated */
      public static EjbClassType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(xis, EjbClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbClassType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbClassType)XmlBeans.getContextTypeLoader().parse(xis, EjbClassType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbClassType.type, options);
      }

      private Factory() {
      }
   }
}
