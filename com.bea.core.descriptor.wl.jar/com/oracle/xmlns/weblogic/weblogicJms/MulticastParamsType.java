package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
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

public interface MulticastParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MulticastParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("multicastparamstype9b5btype");

   String getMulticastAddress();

   XmlString xgetMulticastAddress();

   boolean isNilMulticastAddress();

   boolean isSetMulticastAddress();

   void setMulticastAddress(String var1);

   void xsetMulticastAddress(XmlString var1);

   void setNilMulticastAddress();

   void unsetMulticastAddress();

   int getMulticastPort();

   XmlInt xgetMulticastPort();

   boolean isSetMulticastPort();

   void setMulticastPort(int var1);

   void xsetMulticastPort(XmlInt var1);

   void unsetMulticastPort();

   int getMulticastTimeToLive();

   XmlInt xgetMulticastTimeToLive();

   boolean isSetMulticastTimeToLive();

   void setMulticastTimeToLive(int var1);

   void xsetMulticastTimeToLive(XmlInt var1);

   void unsetMulticastTimeToLive();

   public static final class Factory {
      public static MulticastParamsType newInstance() {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().newInstance(MulticastParamsType.type, (XmlOptions)null);
      }

      public static MulticastParamsType newInstance(XmlOptions options) {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().newInstance(MulticastParamsType.type, options);
      }

      public static MulticastParamsType parse(String xmlAsString) throws XmlException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MulticastParamsType.type, (XmlOptions)null);
      }

      public static MulticastParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MulticastParamsType.type, options);
      }

      public static MulticastParamsType parse(File file) throws XmlException, IOException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(file, MulticastParamsType.type, (XmlOptions)null);
      }

      public static MulticastParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(file, MulticastParamsType.type, options);
      }

      public static MulticastParamsType parse(URL u) throws XmlException, IOException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(u, MulticastParamsType.type, (XmlOptions)null);
      }

      public static MulticastParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(u, MulticastParamsType.type, options);
      }

      public static MulticastParamsType parse(InputStream is) throws XmlException, IOException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(is, MulticastParamsType.type, (XmlOptions)null);
      }

      public static MulticastParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(is, MulticastParamsType.type, options);
      }

      public static MulticastParamsType parse(Reader r) throws XmlException, IOException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(r, MulticastParamsType.type, (XmlOptions)null);
      }

      public static MulticastParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(r, MulticastParamsType.type, options);
      }

      public static MulticastParamsType parse(XMLStreamReader sr) throws XmlException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(sr, MulticastParamsType.type, (XmlOptions)null);
      }

      public static MulticastParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(sr, MulticastParamsType.type, options);
      }

      public static MulticastParamsType parse(Node node) throws XmlException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(node, MulticastParamsType.type, (XmlOptions)null);
      }

      public static MulticastParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(node, MulticastParamsType.type, options);
      }

      /** @deprecated */
      public static MulticastParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(xis, MulticastParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MulticastParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MulticastParamsType)XmlBeans.getContextTypeLoader().parse(xis, MulticastParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MulticastParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MulticastParamsType.type, options);
      }

      private Factory() {
      }
   }
}
