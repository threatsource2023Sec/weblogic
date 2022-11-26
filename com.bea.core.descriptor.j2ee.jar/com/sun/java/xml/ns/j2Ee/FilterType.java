package com.sun.java.xml.ns.j2Ee;

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

public interface FilterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FilterType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("filtertype948btype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   IconType[] getIconArray();

   IconType getIconArray(int var1);

   int sizeOfIconArray();

   void setIconArray(IconType[] var1);

   void setIconArray(int var1, IconType var2);

   IconType insertNewIcon(int var1);

   IconType addNewIcon();

   void removeIcon(int var1);

   FilterNameType getFilterName();

   void setFilterName(FilterNameType var1);

   FilterNameType addNewFilterName();

   FullyQualifiedClassType getFilterClass();

   void setFilterClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewFilterClass();

   ParamValueType[] getInitParamArray();

   ParamValueType getInitParamArray(int var1);

   int sizeOfInitParamArray();

   void setInitParamArray(ParamValueType[] var1);

   void setInitParamArray(int var1, ParamValueType var2);

   ParamValueType insertNewInitParam(int var1);

   ParamValueType addNewInitParam();

   void removeInitParam(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FilterType newInstance() {
         return (FilterType)XmlBeans.getContextTypeLoader().newInstance(FilterType.type, (XmlOptions)null);
      }

      public static FilterType newInstance(XmlOptions options) {
         return (FilterType)XmlBeans.getContextTypeLoader().newInstance(FilterType.type, options);
      }

      public static FilterType parse(java.lang.String xmlAsString) throws XmlException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterType.type, (XmlOptions)null);
      }

      public static FilterType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterType.type, options);
      }

      public static FilterType parse(File file) throws XmlException, IOException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(file, FilterType.type, (XmlOptions)null);
      }

      public static FilterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(file, FilterType.type, options);
      }

      public static FilterType parse(URL u) throws XmlException, IOException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(u, FilterType.type, (XmlOptions)null);
      }

      public static FilterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(u, FilterType.type, options);
      }

      public static FilterType parse(InputStream is) throws XmlException, IOException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(is, FilterType.type, (XmlOptions)null);
      }

      public static FilterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(is, FilterType.type, options);
      }

      public static FilterType parse(Reader r) throws XmlException, IOException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(r, FilterType.type, (XmlOptions)null);
      }

      public static FilterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(r, FilterType.type, options);
      }

      public static FilterType parse(XMLStreamReader sr) throws XmlException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(sr, FilterType.type, (XmlOptions)null);
      }

      public static FilterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(sr, FilterType.type, options);
      }

      public static FilterType parse(Node node) throws XmlException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(node, FilterType.type, (XmlOptions)null);
      }

      public static FilterType parse(Node node, XmlOptions options) throws XmlException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(node, FilterType.type, options);
      }

      /** @deprecated */
      public static FilterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(xis, FilterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FilterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FilterType)XmlBeans.getContextTypeLoader().parse(xis, FilterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterType.type, options);
      }

      private Factory() {
      }
   }
}
