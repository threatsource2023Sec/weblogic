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

public interface FinderParamType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FinderParamType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("finderparamtype5b05type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FinderParamType newInstance() {
         return (FinderParamType)XmlBeans.getContextTypeLoader().newInstance(FinderParamType.type, (XmlOptions)null);
      }

      public static FinderParamType newInstance(XmlOptions options) {
         return (FinderParamType)XmlBeans.getContextTypeLoader().newInstance(FinderParamType.type, options);
      }

      public static FinderParamType parse(String xmlAsString) throws XmlException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderParamType.type, (XmlOptions)null);
      }

      public static FinderParamType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FinderParamType.type, options);
      }

      public static FinderParamType parse(File file) throws XmlException, IOException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(file, FinderParamType.type, (XmlOptions)null);
      }

      public static FinderParamType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(file, FinderParamType.type, options);
      }

      public static FinderParamType parse(URL u) throws XmlException, IOException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(u, FinderParamType.type, (XmlOptions)null);
      }

      public static FinderParamType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(u, FinderParamType.type, options);
      }

      public static FinderParamType parse(InputStream is) throws XmlException, IOException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(is, FinderParamType.type, (XmlOptions)null);
      }

      public static FinderParamType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(is, FinderParamType.type, options);
      }

      public static FinderParamType parse(Reader r) throws XmlException, IOException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(r, FinderParamType.type, (XmlOptions)null);
      }

      public static FinderParamType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(r, FinderParamType.type, options);
      }

      public static FinderParamType parse(XMLStreamReader sr) throws XmlException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(sr, FinderParamType.type, (XmlOptions)null);
      }

      public static FinderParamType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(sr, FinderParamType.type, options);
      }

      public static FinderParamType parse(Node node) throws XmlException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(node, FinderParamType.type, (XmlOptions)null);
      }

      public static FinderParamType parse(Node node, XmlOptions options) throws XmlException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(node, FinderParamType.type, options);
      }

      /** @deprecated */
      public static FinderParamType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(xis, FinderParamType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FinderParamType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FinderParamType)XmlBeans.getContextTypeLoader().parse(xis, FinderParamType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderParamType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FinderParamType.type, options);
      }

      private Factory() {
      }
   }
}
