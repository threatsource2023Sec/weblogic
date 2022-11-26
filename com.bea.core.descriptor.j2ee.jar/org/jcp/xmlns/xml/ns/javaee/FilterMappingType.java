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

public interface FilterMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FilterMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("filtermappingtype26b2type");

   FilterNameType getFilterName();

   void setFilterName(FilterNameType var1);

   FilterNameType addNewFilterName();

   UrlPatternType[] getUrlPatternArray();

   UrlPatternType getUrlPatternArray(int var1);

   int sizeOfUrlPatternArray();

   void setUrlPatternArray(UrlPatternType[] var1);

   void setUrlPatternArray(int var1, UrlPatternType var2);

   UrlPatternType insertNewUrlPattern(int var1);

   UrlPatternType addNewUrlPattern();

   void removeUrlPattern(int var1);

   ServletNameType[] getServletNameArray();

   ServletNameType getServletNameArray(int var1);

   int sizeOfServletNameArray();

   void setServletNameArray(ServletNameType[] var1);

   void setServletNameArray(int var1, ServletNameType var2);

   ServletNameType insertNewServletName(int var1);

   ServletNameType addNewServletName();

   void removeServletName(int var1);

   DispatcherType[] getDispatcherArray();

   DispatcherType getDispatcherArray(int var1);

   int sizeOfDispatcherArray();

   void setDispatcherArray(DispatcherType[] var1);

   void setDispatcherArray(int var1, DispatcherType var2);

   DispatcherType insertNewDispatcher(int var1);

   DispatcherType addNewDispatcher();

   void removeDispatcher(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FilterMappingType newInstance() {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().newInstance(FilterMappingType.type, (XmlOptions)null);
      }

      public static FilterMappingType newInstance(XmlOptions options) {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().newInstance(FilterMappingType.type, options);
      }

      public static FilterMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterMappingType.type, (XmlOptions)null);
      }

      public static FilterMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FilterMappingType.type, options);
      }

      public static FilterMappingType parse(File file) throws XmlException, IOException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(file, FilterMappingType.type, (XmlOptions)null);
      }

      public static FilterMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(file, FilterMappingType.type, options);
      }

      public static FilterMappingType parse(URL u) throws XmlException, IOException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(u, FilterMappingType.type, (XmlOptions)null);
      }

      public static FilterMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(u, FilterMappingType.type, options);
      }

      public static FilterMappingType parse(InputStream is) throws XmlException, IOException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(is, FilterMappingType.type, (XmlOptions)null);
      }

      public static FilterMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(is, FilterMappingType.type, options);
      }

      public static FilterMappingType parse(Reader r) throws XmlException, IOException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(r, FilterMappingType.type, (XmlOptions)null);
      }

      public static FilterMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(r, FilterMappingType.type, options);
      }

      public static FilterMappingType parse(XMLStreamReader sr) throws XmlException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(sr, FilterMappingType.type, (XmlOptions)null);
      }

      public static FilterMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(sr, FilterMappingType.type, options);
      }

      public static FilterMappingType parse(Node node) throws XmlException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(node, FilterMappingType.type, (XmlOptions)null);
      }

      public static FilterMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(node, FilterMappingType.type, options);
      }

      /** @deprecated */
      public static FilterMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(xis, FilterMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FilterMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FilterMappingType)XmlBeans.getContextTypeLoader().parse(xis, FilterMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FilterMappingType.type, options);
      }

      private Factory() {
      }
   }
}
