package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.XsdPositiveIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface WeblogicQueryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicQueryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicquerytypedbb4type");

   DescriptionType getDescription();

   boolean isSetDescription();

   void setDescription(DescriptionType var1);

   DescriptionType addNewDescription();

   void unsetDescription();

   QueryMethodType getQueryMethod();

   void setQueryMethod(QueryMethodType var1);

   QueryMethodType addNewQueryMethod();

   EjbQlQueryType getEjbQlQuery();

   boolean isSetEjbQlQuery();

   void setEjbQlQuery(EjbQlQueryType var1);

   EjbQlQueryType addNewEjbQlQuery();

   void unsetEjbQlQuery();

   SqlQueryType getSqlQuery();

   boolean isSetSqlQuery();

   void setSqlQuery(SqlQueryType var1);

   SqlQueryType addNewSqlQuery();

   void unsetSqlQuery();

   XsdPositiveIntegerType getMaxElements();

   boolean isSetMaxElements();

   void setMaxElements(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewMaxElements();

   void unsetMaxElements();

   TrueFalseType getIncludeUpdates();

   boolean isSetIncludeUpdates();

   void setIncludeUpdates(TrueFalseType var1);

   TrueFalseType addNewIncludeUpdates();

   void unsetIncludeUpdates();

   TrueFalseType getSqlSelectDistinct();

   boolean isSetSqlSelectDistinct();

   void setSqlSelectDistinct(TrueFalseType var1);

   TrueFalseType addNewSqlSelectDistinct();

   void unsetSqlSelectDistinct();

   TrueFalseType getEnableQueryCaching();

   boolean isSetEnableQueryCaching();

   void setEnableQueryCaching(TrueFalseType var1);

   TrueFalseType addNewEnableQueryCaching();

   void unsetEnableQueryCaching();

   TrueFalseType getEnableEagerRefresh();

   boolean isSetEnableEagerRefresh();

   void setEnableEagerRefresh(TrueFalseType var1);

   TrueFalseType addNewEnableEagerRefresh();

   void unsetEnableEagerRefresh();

   TrueFalseType getIncludeResultCacheHint();

   boolean isSetIncludeResultCacheHint();

   void setIncludeResultCacheHint(TrueFalseType var1);

   TrueFalseType addNewIncludeResultCacheHint();

   void unsetIncludeResultCacheHint();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicQueryType newInstance() {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().newInstance(WeblogicQueryType.type, (XmlOptions)null);
      }

      public static WeblogicQueryType newInstance(XmlOptions options) {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().newInstance(WeblogicQueryType.type, options);
      }

      public static WeblogicQueryType parse(String xmlAsString) throws XmlException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicQueryType.type, (XmlOptions)null);
      }

      public static WeblogicQueryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicQueryType.type, options);
      }

      public static WeblogicQueryType parse(File file) throws XmlException, IOException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(file, WeblogicQueryType.type, (XmlOptions)null);
      }

      public static WeblogicQueryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(file, WeblogicQueryType.type, options);
      }

      public static WeblogicQueryType parse(URL u) throws XmlException, IOException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(u, WeblogicQueryType.type, (XmlOptions)null);
      }

      public static WeblogicQueryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(u, WeblogicQueryType.type, options);
      }

      public static WeblogicQueryType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(is, WeblogicQueryType.type, (XmlOptions)null);
      }

      public static WeblogicQueryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(is, WeblogicQueryType.type, options);
      }

      public static WeblogicQueryType parse(Reader r) throws XmlException, IOException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(r, WeblogicQueryType.type, (XmlOptions)null);
      }

      public static WeblogicQueryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(r, WeblogicQueryType.type, options);
      }

      public static WeblogicQueryType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicQueryType.type, (XmlOptions)null);
      }

      public static WeblogicQueryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicQueryType.type, options);
      }

      public static WeblogicQueryType parse(Node node) throws XmlException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(node, WeblogicQueryType.type, (XmlOptions)null);
      }

      public static WeblogicQueryType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(node, WeblogicQueryType.type, options);
      }

      /** @deprecated */
      public static WeblogicQueryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicQueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicQueryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicQueryType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicQueryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicQueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicQueryType.type, options);
      }

      private Factory() {
      }
   }
}
