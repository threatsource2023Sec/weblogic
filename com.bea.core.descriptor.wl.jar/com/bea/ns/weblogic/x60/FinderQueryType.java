package com.bea.ns.weblogic.x60;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface FinderQueryType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FinderQueryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("finderquerytype5700type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FinderQueryType newInstance() {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().newInstance(FinderQueryType.type, (XmlOptions)null);
      }

      public static FinderQueryType newInstance(XmlOptions options) {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().newInstance(FinderQueryType.type, options);
      }

      public static FinderQueryType parse(String xmlAsString) throws XmlException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderQueryType.type, (XmlOptions)null);
      }

      public static FinderQueryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderQueryType.type, options);
      }

      public static FinderQueryType parse(File file) throws XmlException, IOException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(file, FinderQueryType.type, (XmlOptions)null);
      }

      public static FinderQueryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(file, FinderQueryType.type, options);
      }

      public static FinderQueryType parse(URL u) throws XmlException, IOException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(u, FinderQueryType.type, (XmlOptions)null);
      }

      public static FinderQueryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(u, FinderQueryType.type, options);
      }

      public static FinderQueryType parse(InputStream is) throws XmlException, IOException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(is, FinderQueryType.type, (XmlOptions)null);
      }

      public static FinderQueryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(is, FinderQueryType.type, options);
      }

      public static FinderQueryType parse(Reader r) throws XmlException, IOException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(r, FinderQueryType.type, (XmlOptions)null);
      }

      public static FinderQueryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(r, FinderQueryType.type, options);
      }

      public static FinderQueryType parse(XMLStreamReader sr) throws XmlException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(sr, FinderQueryType.type, (XmlOptions)null);
      }

      public static FinderQueryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(sr, FinderQueryType.type, options);
      }

      public static FinderQueryType parse(Node node) throws XmlException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(node, FinderQueryType.type, (XmlOptions)null);
      }

      public static FinderQueryType parse(Node node, XmlOptions options) throws XmlException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(node, FinderQueryType.type, options);
      }

      /** @deprecated */
      public static FinderQueryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(xis, FinderQueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FinderQueryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FinderQueryType)XmlBeans.getContextTypeLoader().parse(xis, FinderQueryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderQueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderQueryType.type, options);
      }

      private Factory() {
      }
   }
}
