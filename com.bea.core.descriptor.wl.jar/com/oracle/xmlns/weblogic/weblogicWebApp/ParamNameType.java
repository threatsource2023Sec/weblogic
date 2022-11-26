package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface ParamNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ParamNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("paramnametypefbfetype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ParamNameType newInstance() {
         return (ParamNameType)XmlBeans.getContextTypeLoader().newInstance(ParamNameType.type, (XmlOptions)null);
      }

      public static ParamNameType newInstance(XmlOptions options) {
         return (ParamNameType)XmlBeans.getContextTypeLoader().newInstance(ParamNameType.type, options);
      }

      public static ParamNameType parse(String xmlAsString) throws XmlException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParamNameType.type, (XmlOptions)null);
      }

      public static ParamNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParamNameType.type, options);
      }

      public static ParamNameType parse(File file) throws XmlException, IOException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(file, ParamNameType.type, (XmlOptions)null);
      }

      public static ParamNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(file, ParamNameType.type, options);
      }

      public static ParamNameType parse(URL u) throws XmlException, IOException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(u, ParamNameType.type, (XmlOptions)null);
      }

      public static ParamNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(u, ParamNameType.type, options);
      }

      public static ParamNameType parse(InputStream is) throws XmlException, IOException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(is, ParamNameType.type, (XmlOptions)null);
      }

      public static ParamNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(is, ParamNameType.type, options);
      }

      public static ParamNameType parse(Reader r) throws XmlException, IOException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(r, ParamNameType.type, (XmlOptions)null);
      }

      public static ParamNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(r, ParamNameType.type, options);
      }

      public static ParamNameType parse(XMLStreamReader sr) throws XmlException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(sr, ParamNameType.type, (XmlOptions)null);
      }

      public static ParamNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(sr, ParamNameType.type, options);
      }

      public static ParamNameType parse(Node node) throws XmlException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(node, ParamNameType.type, (XmlOptions)null);
      }

      public static ParamNameType parse(Node node, XmlOptions options) throws XmlException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(node, ParamNameType.type, options);
      }

      /** @deprecated */
      public static ParamNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(xis, ParamNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ParamNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ParamNameType)XmlBeans.getContextTypeLoader().parse(xis, ParamNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParamNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParamNameType.type, options);
      }

      private Factory() {
      }
   }
}
