package org.jcp.xmlns.xml.ns.javaee;

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

public interface ErrorPageType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ErrorPageType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("errorpagetype8617type");

   ErrorCodeType getErrorCode();

   boolean isSetErrorCode();

   void setErrorCode(ErrorCodeType var1);

   ErrorCodeType addNewErrorCode();

   void unsetErrorCode();

   FullyQualifiedClassType getExceptionType();

   boolean isSetExceptionType();

   void setExceptionType(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewExceptionType();

   void unsetExceptionType();

   WarPathType getLocation();

   void setLocation(WarPathType var1);

   WarPathType addNewLocation();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ErrorPageType newInstance() {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().newInstance(ErrorPageType.type, (XmlOptions)null);
      }

      public static ErrorPageType newInstance(XmlOptions options) {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().newInstance(ErrorPageType.type, options);
      }

      public static ErrorPageType parse(java.lang.String xmlAsString) throws XmlException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ErrorPageType.type, (XmlOptions)null);
      }

      public static ErrorPageType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ErrorPageType.type, options);
      }

      public static ErrorPageType parse(File file) throws XmlException, IOException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(file, ErrorPageType.type, (XmlOptions)null);
      }

      public static ErrorPageType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(file, ErrorPageType.type, options);
      }

      public static ErrorPageType parse(URL u) throws XmlException, IOException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(u, ErrorPageType.type, (XmlOptions)null);
      }

      public static ErrorPageType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(u, ErrorPageType.type, options);
      }

      public static ErrorPageType parse(InputStream is) throws XmlException, IOException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(is, ErrorPageType.type, (XmlOptions)null);
      }

      public static ErrorPageType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(is, ErrorPageType.type, options);
      }

      public static ErrorPageType parse(Reader r) throws XmlException, IOException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(r, ErrorPageType.type, (XmlOptions)null);
      }

      public static ErrorPageType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(r, ErrorPageType.type, options);
      }

      public static ErrorPageType parse(XMLStreamReader sr) throws XmlException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(sr, ErrorPageType.type, (XmlOptions)null);
      }

      public static ErrorPageType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(sr, ErrorPageType.type, options);
      }

      public static ErrorPageType parse(Node node) throws XmlException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(node, ErrorPageType.type, (XmlOptions)null);
      }

      public static ErrorPageType parse(Node node, XmlOptions options) throws XmlException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(node, ErrorPageType.type, options);
      }

      /** @deprecated */
      public static ErrorPageType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(xis, ErrorPageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ErrorPageType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ErrorPageType)XmlBeans.getContextTypeLoader().parse(xis, ErrorPageType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ErrorPageType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ErrorPageType.type, options);
      }

      private Factory() {
      }
   }
}
