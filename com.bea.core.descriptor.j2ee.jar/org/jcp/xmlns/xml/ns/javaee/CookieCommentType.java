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

public interface CookieCommentType extends NonEmptyStringType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CookieCommentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cookiecommenttype5b95type");

   public static final class Factory {
      public static CookieCommentType newInstance() {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().newInstance(CookieCommentType.type, (XmlOptions)null);
      }

      public static CookieCommentType newInstance(XmlOptions options) {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().newInstance(CookieCommentType.type, options);
      }

      public static CookieCommentType parse(java.lang.String xmlAsString) throws XmlException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookieCommentType.type, (XmlOptions)null);
      }

      public static CookieCommentType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookieCommentType.type, options);
      }

      public static CookieCommentType parse(File file) throws XmlException, IOException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(file, CookieCommentType.type, (XmlOptions)null);
      }

      public static CookieCommentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(file, CookieCommentType.type, options);
      }

      public static CookieCommentType parse(URL u) throws XmlException, IOException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(u, CookieCommentType.type, (XmlOptions)null);
      }

      public static CookieCommentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(u, CookieCommentType.type, options);
      }

      public static CookieCommentType parse(InputStream is) throws XmlException, IOException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(is, CookieCommentType.type, (XmlOptions)null);
      }

      public static CookieCommentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(is, CookieCommentType.type, options);
      }

      public static CookieCommentType parse(Reader r) throws XmlException, IOException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(r, CookieCommentType.type, (XmlOptions)null);
      }

      public static CookieCommentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(r, CookieCommentType.type, options);
      }

      public static CookieCommentType parse(XMLStreamReader sr) throws XmlException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(sr, CookieCommentType.type, (XmlOptions)null);
      }

      public static CookieCommentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(sr, CookieCommentType.type, options);
      }

      public static CookieCommentType parse(Node node) throws XmlException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(node, CookieCommentType.type, (XmlOptions)null);
      }

      public static CookieCommentType parse(Node node, XmlOptions options) throws XmlException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(node, CookieCommentType.type, options);
      }

      /** @deprecated */
      public static CookieCommentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(xis, CookieCommentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CookieCommentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CookieCommentType)XmlBeans.getContextTypeLoader().parse(xis, CookieCommentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookieCommentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookieCommentType.type, options);
      }

      private Factory() {
      }
   }
}
