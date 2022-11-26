package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DestinationKeyType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DestinationKeyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("destinationkeytype6f48type");

   String getProperty();

   XmlString xgetProperty();

   boolean isNilProperty();

   boolean isSetProperty();

   void setProperty(String var1);

   void xsetProperty(XmlString var1);

   void setNilProperty();

   void unsetProperty();

   KeyTypeType.Enum getKeyType();

   KeyTypeType xgetKeyType();

   boolean isSetKeyType();

   void setKeyType(KeyTypeType.Enum var1);

   void xsetKeyType(KeyTypeType var1);

   void unsetKeyType();

   SortOrderType.Enum getSortOrder();

   SortOrderType xgetSortOrder();

   boolean isSetSortOrder();

   void setSortOrder(SortOrderType.Enum var1);

   void xsetSortOrder(SortOrderType var1);

   void unsetSortOrder();

   public static final class Factory {
      public static DestinationKeyType newInstance() {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().newInstance(DestinationKeyType.type, (XmlOptions)null);
      }

      public static DestinationKeyType newInstance(XmlOptions options) {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().newInstance(DestinationKeyType.type, options);
      }

      public static DestinationKeyType parse(String xmlAsString) throws XmlException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestinationKeyType.type, (XmlOptions)null);
      }

      public static DestinationKeyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestinationKeyType.type, options);
      }

      public static DestinationKeyType parse(File file) throws XmlException, IOException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(file, DestinationKeyType.type, (XmlOptions)null);
      }

      public static DestinationKeyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(file, DestinationKeyType.type, options);
      }

      public static DestinationKeyType parse(URL u) throws XmlException, IOException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(u, DestinationKeyType.type, (XmlOptions)null);
      }

      public static DestinationKeyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(u, DestinationKeyType.type, options);
      }

      public static DestinationKeyType parse(InputStream is) throws XmlException, IOException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(is, DestinationKeyType.type, (XmlOptions)null);
      }

      public static DestinationKeyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(is, DestinationKeyType.type, options);
      }

      public static DestinationKeyType parse(Reader r) throws XmlException, IOException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(r, DestinationKeyType.type, (XmlOptions)null);
      }

      public static DestinationKeyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(r, DestinationKeyType.type, options);
      }

      public static DestinationKeyType parse(XMLStreamReader sr) throws XmlException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(sr, DestinationKeyType.type, (XmlOptions)null);
      }

      public static DestinationKeyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(sr, DestinationKeyType.type, options);
      }

      public static DestinationKeyType parse(Node node) throws XmlException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(node, DestinationKeyType.type, (XmlOptions)null);
      }

      public static DestinationKeyType parse(Node node, XmlOptions options) throws XmlException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(node, DestinationKeyType.type, options);
      }

      /** @deprecated */
      public static DestinationKeyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(xis, DestinationKeyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DestinationKeyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DestinationKeyType)XmlBeans.getContextTypeLoader().parse(xis, DestinationKeyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestinationKeyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestinationKeyType.type, options);
      }

      private Factory() {
      }
   }
}
