package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
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

public interface CookieConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CookieConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("cookieconfigtype2586type");

   CookieNameType getName();

   boolean isSetName();

   void setName(CookieNameType var1);

   CookieNameType addNewName();

   void unsetName();

   CookieDomainType getDomain();

   boolean isSetDomain();

   void setDomain(CookieDomainType var1);

   CookieDomainType addNewDomain();

   void unsetDomain();

   CookiePathType getPath();

   boolean isSetPath();

   void setPath(CookiePathType var1);

   CookiePathType addNewPath();

   void unsetPath();

   CookieCommentType getComment();

   boolean isSetComment();

   void setComment(CookieCommentType var1);

   CookieCommentType addNewComment();

   void unsetComment();

   TrueFalseType getHttpOnly();

   boolean isSetHttpOnly();

   void setHttpOnly(TrueFalseType var1);

   TrueFalseType addNewHttpOnly();

   void unsetHttpOnly();

   TrueFalseType getSecure();

   boolean isSetSecure();

   void setSecure(TrueFalseType var1);

   TrueFalseType addNewSecure();

   void unsetSecure();

   XsdIntegerType getMaxAge();

   boolean isSetMaxAge();

   void setMaxAge(XsdIntegerType var1);

   XsdIntegerType addNewMaxAge();

   void unsetMaxAge();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CookieConfigType newInstance() {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().newInstance(CookieConfigType.type, (XmlOptions)null);
      }

      public static CookieConfigType newInstance(XmlOptions options) {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().newInstance(CookieConfigType.type, options);
      }

      public static CookieConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookieConfigType.type, (XmlOptions)null);
      }

      public static CookieConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CookieConfigType.type, options);
      }

      public static CookieConfigType parse(File file) throws XmlException, IOException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(file, CookieConfigType.type, (XmlOptions)null);
      }

      public static CookieConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(file, CookieConfigType.type, options);
      }

      public static CookieConfigType parse(URL u) throws XmlException, IOException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(u, CookieConfigType.type, (XmlOptions)null);
      }

      public static CookieConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(u, CookieConfigType.type, options);
      }

      public static CookieConfigType parse(InputStream is) throws XmlException, IOException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(is, CookieConfigType.type, (XmlOptions)null);
      }

      public static CookieConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(is, CookieConfigType.type, options);
      }

      public static CookieConfigType parse(Reader r) throws XmlException, IOException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(r, CookieConfigType.type, (XmlOptions)null);
      }

      public static CookieConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(r, CookieConfigType.type, options);
      }

      public static CookieConfigType parse(XMLStreamReader sr) throws XmlException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(sr, CookieConfigType.type, (XmlOptions)null);
      }

      public static CookieConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(sr, CookieConfigType.type, options);
      }

      public static CookieConfigType parse(Node node) throws XmlException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(node, CookieConfigType.type, (XmlOptions)null);
      }

      public static CookieConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(node, CookieConfigType.type, options);
      }

      /** @deprecated */
      public static CookieConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(xis, CookieConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CookieConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CookieConfigType)XmlBeans.getContextTypeLoader().parse(xis, CookieConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookieConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CookieConfigType.type, options);
      }

      private Factory() {
      }
   }
}
