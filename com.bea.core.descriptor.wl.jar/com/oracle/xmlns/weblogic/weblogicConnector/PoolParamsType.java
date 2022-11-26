package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface PoolParamsType extends ConnectionPoolParamsType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PoolParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("poolparamstype1bfdtype");

   TrueFalseType getMatchConnectionsSupported();

   boolean isSetMatchConnectionsSupported();

   void setMatchConnectionsSupported(TrueFalseType var1);

   TrueFalseType addNewMatchConnectionsSupported();

   void unsetMatchConnectionsSupported();

   TrueFalseType getUseFirstAvailable();

   boolean isSetUseFirstAvailable();

   void setUseFirstAvailable(TrueFalseType var1);

   TrueFalseType addNewUseFirstAvailable();

   void unsetUseFirstAvailable();

   public static final class Factory {
      public static PoolParamsType newInstance() {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().newInstance(PoolParamsType.type, (XmlOptions)null);
      }

      public static PoolParamsType newInstance(XmlOptions options) {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().newInstance(PoolParamsType.type, options);
      }

      public static PoolParamsType parse(String xmlAsString) throws XmlException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PoolParamsType.type, (XmlOptions)null);
      }

      public static PoolParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PoolParamsType.type, options);
      }

      public static PoolParamsType parse(File file) throws XmlException, IOException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(file, PoolParamsType.type, (XmlOptions)null);
      }

      public static PoolParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(file, PoolParamsType.type, options);
      }

      public static PoolParamsType parse(URL u) throws XmlException, IOException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(u, PoolParamsType.type, (XmlOptions)null);
      }

      public static PoolParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(u, PoolParamsType.type, options);
      }

      public static PoolParamsType parse(InputStream is) throws XmlException, IOException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(is, PoolParamsType.type, (XmlOptions)null);
      }

      public static PoolParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(is, PoolParamsType.type, options);
      }

      public static PoolParamsType parse(Reader r) throws XmlException, IOException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(r, PoolParamsType.type, (XmlOptions)null);
      }

      public static PoolParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(r, PoolParamsType.type, options);
      }

      public static PoolParamsType parse(XMLStreamReader sr) throws XmlException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(sr, PoolParamsType.type, (XmlOptions)null);
      }

      public static PoolParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(sr, PoolParamsType.type, options);
      }

      public static PoolParamsType parse(Node node) throws XmlException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(node, PoolParamsType.type, (XmlOptions)null);
      }

      public static PoolParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(node, PoolParamsType.type, options);
      }

      /** @deprecated */
      public static PoolParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(xis, PoolParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PoolParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PoolParamsType)XmlBeans.getContextTypeLoader().parse(xis, PoolParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PoolParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PoolParamsType.type, options);
      }

      private Factory() {
      }
   }
}
