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

public interface EjbRefNameType extends JndiNameType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbRefNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("ejbrefnametypec976type");

   public static final class Factory {
      public static EjbRefNameType newInstance() {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().newInstance(EjbRefNameType.type, (XmlOptions)null);
      }

      public static EjbRefNameType newInstance(XmlOptions options) {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().newInstance(EjbRefNameType.type, options);
      }

      public static EjbRefNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRefNameType.type, (XmlOptions)null);
      }

      public static EjbRefNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbRefNameType.type, options);
      }

      public static EjbRefNameType parse(File file) throws XmlException, IOException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(file, EjbRefNameType.type, (XmlOptions)null);
      }

      public static EjbRefNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(file, EjbRefNameType.type, options);
      }

      public static EjbRefNameType parse(URL u) throws XmlException, IOException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(u, EjbRefNameType.type, (XmlOptions)null);
      }

      public static EjbRefNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(u, EjbRefNameType.type, options);
      }

      public static EjbRefNameType parse(InputStream is) throws XmlException, IOException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(is, EjbRefNameType.type, (XmlOptions)null);
      }

      public static EjbRefNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(is, EjbRefNameType.type, options);
      }

      public static EjbRefNameType parse(Reader r) throws XmlException, IOException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(r, EjbRefNameType.type, (XmlOptions)null);
      }

      public static EjbRefNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(r, EjbRefNameType.type, options);
      }

      public static EjbRefNameType parse(XMLStreamReader sr) throws XmlException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(sr, EjbRefNameType.type, (XmlOptions)null);
      }

      public static EjbRefNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(sr, EjbRefNameType.type, options);
      }

      public static EjbRefNameType parse(Node node) throws XmlException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(node, EjbRefNameType.type, (XmlOptions)null);
      }

      public static EjbRefNameType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(node, EjbRefNameType.type, options);
      }

      /** @deprecated */
      public static EjbRefNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(xis, EjbRefNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbRefNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbRefNameType)XmlBeans.getContextTypeLoader().parse(xis, EjbRefNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRefNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbRefNameType.type, options);
      }

      private Factory() {
      }
   }
}
