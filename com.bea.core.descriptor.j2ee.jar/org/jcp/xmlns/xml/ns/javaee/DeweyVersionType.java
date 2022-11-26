package org.jcp.xmlns.xml.ns.javaee;

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

public interface DeweyVersionType extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeweyVersionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("deweyversiontype9008type");

   public static final class Factory {
      public static DeweyVersionType newValue(Object obj) {
         return (DeweyVersionType)DeweyVersionType.type.newValue(obj);
      }

      public static DeweyVersionType newInstance() {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().newInstance(DeweyVersionType.type, (XmlOptions)null);
      }

      public static DeweyVersionType newInstance(XmlOptions options) {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().newInstance(DeweyVersionType.type, options);
      }

      public static DeweyVersionType parse(java.lang.String xmlAsString) throws XmlException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeweyVersionType.type, (XmlOptions)null);
      }

      public static DeweyVersionType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeweyVersionType.type, options);
      }

      public static DeweyVersionType parse(File file) throws XmlException, IOException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(file, DeweyVersionType.type, (XmlOptions)null);
      }

      public static DeweyVersionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(file, DeweyVersionType.type, options);
      }

      public static DeweyVersionType parse(URL u) throws XmlException, IOException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(u, DeweyVersionType.type, (XmlOptions)null);
      }

      public static DeweyVersionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(u, DeweyVersionType.type, options);
      }

      public static DeweyVersionType parse(InputStream is) throws XmlException, IOException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(is, DeweyVersionType.type, (XmlOptions)null);
      }

      public static DeweyVersionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(is, DeweyVersionType.type, options);
      }

      public static DeweyVersionType parse(Reader r) throws XmlException, IOException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(r, DeweyVersionType.type, (XmlOptions)null);
      }

      public static DeweyVersionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(r, DeweyVersionType.type, options);
      }

      public static DeweyVersionType parse(XMLStreamReader sr) throws XmlException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(sr, DeweyVersionType.type, (XmlOptions)null);
      }

      public static DeweyVersionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(sr, DeweyVersionType.type, options);
      }

      public static DeweyVersionType parse(Node node) throws XmlException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(node, DeweyVersionType.type, (XmlOptions)null);
      }

      public static DeweyVersionType parse(Node node, XmlOptions options) throws XmlException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(node, DeweyVersionType.type, options);
      }

      /** @deprecated */
      public static DeweyVersionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(xis, DeweyVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeweyVersionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeweyVersionType)XmlBeans.getContextTypeLoader().parse(xis, DeweyVersionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeweyVersionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeweyVersionType.type, options);
      }

      private Factory() {
      }
   }
}
