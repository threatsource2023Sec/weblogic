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

public interface FinderNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FinderNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("findernametypeb03ftype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FinderNameType newInstance() {
         return (FinderNameType)XmlBeans.getContextTypeLoader().newInstance(FinderNameType.type, (XmlOptions)null);
      }

      public static FinderNameType newInstance(XmlOptions options) {
         return (FinderNameType)XmlBeans.getContextTypeLoader().newInstance(FinderNameType.type, options);
      }

      public static FinderNameType parse(String xmlAsString) throws XmlException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderNameType.type, (XmlOptions)null);
      }

      public static FinderNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderNameType.type, options);
      }

      public static FinderNameType parse(File file) throws XmlException, IOException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(file, FinderNameType.type, (XmlOptions)null);
      }

      public static FinderNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(file, FinderNameType.type, options);
      }

      public static FinderNameType parse(URL u) throws XmlException, IOException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(u, FinderNameType.type, (XmlOptions)null);
      }

      public static FinderNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(u, FinderNameType.type, options);
      }

      public static FinderNameType parse(InputStream is) throws XmlException, IOException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(is, FinderNameType.type, (XmlOptions)null);
      }

      public static FinderNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(is, FinderNameType.type, options);
      }

      public static FinderNameType parse(Reader r) throws XmlException, IOException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(r, FinderNameType.type, (XmlOptions)null);
      }

      public static FinderNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(r, FinderNameType.type, options);
      }

      public static FinderNameType parse(XMLStreamReader sr) throws XmlException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(sr, FinderNameType.type, (XmlOptions)null);
      }

      public static FinderNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(sr, FinderNameType.type, options);
      }

      public static FinderNameType parse(Node node) throws XmlException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(node, FinderNameType.type, (XmlOptions)null);
      }

      public static FinderNameType parse(Node node, XmlOptions options) throws XmlException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(node, FinderNameType.type, options);
      }

      /** @deprecated */
      public static FinderNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(xis, FinderNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FinderNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FinderNameType)XmlBeans.getContextTypeLoader().parse(xis, FinderNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderNameType.type, options);
      }

      private Factory() {
      }
   }
}
