package com.bea.ns.weblogic.x60;

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

public interface FinderType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FinderType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("findertype75c5type");

   FinderNameType getFinderName();

   void setFinderName(FinderNameType var1);

   FinderNameType addNewFinderName();

   FinderParamType[] getFinderParamArray();

   FinderParamType getFinderParamArray(int var1);

   int sizeOfFinderParamArray();

   void setFinderParamArray(FinderParamType[] var1);

   void setFinderParamArray(int var1, FinderParamType var2);

   FinderParamType insertNewFinderParam(int var1);

   FinderParamType addNewFinderParam();

   void removeFinderParam(int var1);

   FinderQueryType getFinderQuery();

   boolean isSetFinderQuery();

   void setFinderQuery(FinderQueryType var1);

   FinderQueryType addNewFinderQuery();

   void unsetFinderQuery();

   FinderSqlType getFinderSql();

   boolean isSetFinderSql();

   void setFinderSql(FinderSqlType var1);

   FinderSqlType addNewFinderSql();

   void unsetFinderSql();

   TrueFalseType getFindForUpdate();

   boolean isSetFindForUpdate();

   void setFindForUpdate(TrueFalseType var1);

   TrueFalseType addNewFindForUpdate();

   void unsetFindForUpdate();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FinderType newInstance() {
         return (FinderType)XmlBeans.getContextTypeLoader().newInstance(FinderType.type, (XmlOptions)null);
      }

      public static FinderType newInstance(XmlOptions options) {
         return (FinderType)XmlBeans.getContextTypeLoader().newInstance(FinderType.type, options);
      }

      public static FinderType parse(String xmlAsString) throws XmlException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderType.type, (XmlOptions)null);
      }

      public static FinderType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderType.type, options);
      }

      public static FinderType parse(File file) throws XmlException, IOException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(file, FinderType.type, (XmlOptions)null);
      }

      public static FinderType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(file, FinderType.type, options);
      }

      public static FinderType parse(URL u) throws XmlException, IOException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(u, FinderType.type, (XmlOptions)null);
      }

      public static FinderType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(u, FinderType.type, options);
      }

      public static FinderType parse(InputStream is) throws XmlException, IOException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(is, FinderType.type, (XmlOptions)null);
      }

      public static FinderType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(is, FinderType.type, options);
      }

      public static FinderType parse(Reader r) throws XmlException, IOException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(r, FinderType.type, (XmlOptions)null);
      }

      public static FinderType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(r, FinderType.type, options);
      }

      public static FinderType parse(XMLStreamReader sr) throws XmlException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(sr, FinderType.type, (XmlOptions)null);
      }

      public static FinderType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(sr, FinderType.type, options);
      }

      public static FinderType parse(Node node) throws XmlException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(node, FinderType.type, (XmlOptions)null);
      }

      public static FinderType parse(Node node, XmlOptions options) throws XmlException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(node, FinderType.type, options);
      }

      /** @deprecated */
      public static FinderType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(xis, FinderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FinderType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FinderType)XmlBeans.getContextTypeLoader().parse(xis, FinderType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderType.type, options);
      }

      private Factory() {
      }
   }
}
