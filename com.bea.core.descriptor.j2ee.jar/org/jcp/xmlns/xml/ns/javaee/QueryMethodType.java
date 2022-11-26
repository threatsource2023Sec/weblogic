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

public interface QueryMethodType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QueryMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("querymethodtypea1c5type");

   MethodNameType getMethodName();

   void setMethodName(MethodNameType var1);

   MethodNameType addNewMethodName();

   MethodParamsType getMethodParams();

   void setMethodParams(MethodParamsType var1);

   MethodParamsType addNewMethodParams();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static QueryMethodType newInstance() {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().newInstance(QueryMethodType.type, (XmlOptions)null);
      }

      public static QueryMethodType newInstance(XmlOptions options) {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().newInstance(QueryMethodType.type, options);
      }

      public static QueryMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueryMethodType.type, (XmlOptions)null);
      }

      public static QueryMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueryMethodType.type, options);
      }

      public static QueryMethodType parse(File file) throws XmlException, IOException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(file, QueryMethodType.type, (XmlOptions)null);
      }

      public static QueryMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(file, QueryMethodType.type, options);
      }

      public static QueryMethodType parse(URL u) throws XmlException, IOException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(u, QueryMethodType.type, (XmlOptions)null);
      }

      public static QueryMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(u, QueryMethodType.type, options);
      }

      public static QueryMethodType parse(InputStream is) throws XmlException, IOException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(is, QueryMethodType.type, (XmlOptions)null);
      }

      public static QueryMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(is, QueryMethodType.type, options);
      }

      public static QueryMethodType parse(Reader r) throws XmlException, IOException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(r, QueryMethodType.type, (XmlOptions)null);
      }

      public static QueryMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(r, QueryMethodType.type, options);
      }

      public static QueryMethodType parse(XMLStreamReader sr) throws XmlException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(sr, QueryMethodType.type, (XmlOptions)null);
      }

      public static QueryMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(sr, QueryMethodType.type, options);
      }

      public static QueryMethodType parse(Node node) throws XmlException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(node, QueryMethodType.type, (XmlOptions)null);
      }

      public static QueryMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(node, QueryMethodType.type, options);
      }

      /** @deprecated */
      public static QueryMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(xis, QueryMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QueryMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QueryMethodType)XmlBeans.getContextTypeLoader().parse(xis, QueryMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueryMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueryMethodType.type, options);
      }

      private Factory() {
      }
   }
}
