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

public interface QueryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QueryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("querytype94c3type");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   QueryMethodType getQueryMethod();

   void setQueryMethod(QueryMethodType var1);

   QueryMethodType addNewQueryMethod();

   ResultTypeMappingType getResultTypeMapping();

   boolean isSetResultTypeMapping();

   void setResultTypeMapping(ResultTypeMappingType var1);

   ResultTypeMappingType addNewResultTypeMapping();

   void unsetResultTypeMapping();

   XsdStringType getEjbQl();

   void setEjbQl(XsdStringType var1);

   XsdStringType addNewEjbQl();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static QueryType newInstance() {
         return (QueryType)XmlBeans.getContextTypeLoader().newInstance(QueryType.type, (XmlOptions)null);
      }

      public static QueryType newInstance(XmlOptions options) {
         return (QueryType)XmlBeans.getContextTypeLoader().newInstance(QueryType.type, options);
      }

      public static QueryType parse(java.lang.String xmlAsString) throws XmlException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueryType.type, (XmlOptions)null);
      }

      public static QueryType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QueryType.type, options);
      }

      public static QueryType parse(File file) throws XmlException, IOException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(file, QueryType.type, (XmlOptions)null);
      }

      public static QueryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(file, QueryType.type, options);
      }

      public static QueryType parse(URL u) throws XmlException, IOException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(u, QueryType.type, (XmlOptions)null);
      }

      public static QueryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(u, QueryType.type, options);
      }

      public static QueryType parse(InputStream is) throws XmlException, IOException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(is, QueryType.type, (XmlOptions)null);
      }

      public static QueryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(is, QueryType.type, options);
      }

      public static QueryType parse(Reader r) throws XmlException, IOException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(r, QueryType.type, (XmlOptions)null);
      }

      public static QueryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(r, QueryType.type, options);
      }

      public static QueryType parse(XMLStreamReader sr) throws XmlException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(sr, QueryType.type, (XmlOptions)null);
      }

      public static QueryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(sr, QueryType.type, options);
      }

      public static QueryType parse(Node node) throws XmlException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(node, QueryType.type, (XmlOptions)null);
      }

      public static QueryType parse(Node node, XmlOptions options) throws XmlException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(node, QueryType.type, options);
      }

      /** @deprecated */
      public static QueryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(xis, QueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QueryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QueryType)XmlBeans.getContextTypeLoader().parse(xis, QueryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QueryType.type, options);
      }

      private Factory() {
      }
   }
}
