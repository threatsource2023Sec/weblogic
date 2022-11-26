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

public interface ParamValueType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ParamValueType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("paramvaluetype4832type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ParamValueType newInstance() {
         return (ParamValueType)XmlBeans.getContextTypeLoader().newInstance(ParamValueType.type, (XmlOptions)null);
      }

      public static ParamValueType newInstance(XmlOptions options) {
         return (ParamValueType)XmlBeans.getContextTypeLoader().newInstance(ParamValueType.type, options);
      }

      public static ParamValueType parse(String xmlAsString) throws XmlException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParamValueType.type, (XmlOptions)null);
      }

      public static ParamValueType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ParamValueType.type, options);
      }

      public static ParamValueType parse(File file) throws XmlException, IOException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(file, ParamValueType.type, (XmlOptions)null);
      }

      public static ParamValueType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(file, ParamValueType.type, options);
      }

      public static ParamValueType parse(URL u) throws XmlException, IOException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(u, ParamValueType.type, (XmlOptions)null);
      }

      public static ParamValueType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(u, ParamValueType.type, options);
      }

      public static ParamValueType parse(InputStream is) throws XmlException, IOException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(is, ParamValueType.type, (XmlOptions)null);
      }

      public static ParamValueType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(is, ParamValueType.type, options);
      }

      public static ParamValueType parse(Reader r) throws XmlException, IOException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(r, ParamValueType.type, (XmlOptions)null);
      }

      public static ParamValueType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(r, ParamValueType.type, options);
      }

      public static ParamValueType parse(XMLStreamReader sr) throws XmlException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(sr, ParamValueType.type, (XmlOptions)null);
      }

      public static ParamValueType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(sr, ParamValueType.type, options);
      }

      public static ParamValueType parse(Node node) throws XmlException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(node, ParamValueType.type, (XmlOptions)null);
      }

      public static ParamValueType parse(Node node, XmlOptions options) throws XmlException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(node, ParamValueType.type, options);
      }

      /** @deprecated */
      public static ParamValueType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(xis, ParamValueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ParamValueType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ParamValueType)XmlBeans.getContextTypeLoader().parse(xis, ParamValueType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParamValueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ParamValueType.type, options);
      }

      private Factory() {
      }
   }
}
