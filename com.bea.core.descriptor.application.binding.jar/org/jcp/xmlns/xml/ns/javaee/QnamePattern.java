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

public interface QnamePattern extends XmlToken {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QnamePattern.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("qnamepattern6f78type");

   public static final class Factory {
      public static QnamePattern newValue(Object obj) {
         return (QnamePattern)QnamePattern.type.newValue(obj);
      }

      public static QnamePattern newInstance() {
         return (QnamePattern)XmlBeans.getContextTypeLoader().newInstance(QnamePattern.type, (XmlOptions)null);
      }

      public static QnamePattern newInstance(XmlOptions options) {
         return (QnamePattern)XmlBeans.getContextTypeLoader().newInstance(QnamePattern.type, options);
      }

      public static QnamePattern parse(java.lang.String xmlAsString) throws XmlException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(xmlAsString, QnamePattern.type, (XmlOptions)null);
      }

      public static QnamePattern parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(xmlAsString, QnamePattern.type, options);
      }

      public static QnamePattern parse(File file) throws XmlException, IOException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(file, QnamePattern.type, (XmlOptions)null);
      }

      public static QnamePattern parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(file, QnamePattern.type, options);
      }

      public static QnamePattern parse(URL u) throws XmlException, IOException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(u, QnamePattern.type, (XmlOptions)null);
      }

      public static QnamePattern parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(u, QnamePattern.type, options);
      }

      public static QnamePattern parse(InputStream is) throws XmlException, IOException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(is, QnamePattern.type, (XmlOptions)null);
      }

      public static QnamePattern parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(is, QnamePattern.type, options);
      }

      public static QnamePattern parse(Reader r) throws XmlException, IOException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(r, QnamePattern.type, (XmlOptions)null);
      }

      public static QnamePattern parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(r, QnamePattern.type, options);
      }

      public static QnamePattern parse(XMLStreamReader sr) throws XmlException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(sr, QnamePattern.type, (XmlOptions)null);
      }

      public static QnamePattern parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(sr, QnamePattern.type, options);
      }

      public static QnamePattern parse(Node node) throws XmlException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(node, QnamePattern.type, (XmlOptions)null);
      }

      public static QnamePattern parse(Node node, XmlOptions options) throws XmlException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(node, QnamePattern.type, options);
      }

      /** @deprecated */
      public static QnamePattern parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(xis, QnamePattern.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QnamePattern parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QnamePattern)XmlBeans.getContextTypeLoader().parse(xis, QnamePattern.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QnamePattern.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QnamePattern.type, options);
      }

      private Factory() {
      }
   }
}
