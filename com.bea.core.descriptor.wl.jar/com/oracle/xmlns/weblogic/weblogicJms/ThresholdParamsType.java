package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
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

public interface ThresholdParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ThresholdParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("thresholdparamstype6e0etype");

   long getBytesHigh();

   XmlLong xgetBytesHigh();

   boolean isSetBytesHigh();

   void setBytesHigh(long var1);

   void xsetBytesHigh(XmlLong var1);

   void unsetBytesHigh();

   long getBytesLow();

   XmlLong xgetBytesLow();

   boolean isSetBytesLow();

   void setBytesLow(long var1);

   void xsetBytesLow(XmlLong var1);

   void unsetBytesLow();

   long getMessagesHigh();

   XmlLong xgetMessagesHigh();

   boolean isSetMessagesHigh();

   void setMessagesHigh(long var1);

   void xsetMessagesHigh(XmlLong var1);

   void unsetMessagesHigh();

   long getMessagesLow();

   XmlLong xgetMessagesLow();

   boolean isSetMessagesLow();

   void setMessagesLow(long var1);

   void xsetMessagesLow(XmlLong var1);

   void unsetMessagesLow();

   public static final class Factory {
      public static ThresholdParamsType newInstance() {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().newInstance(ThresholdParamsType.type, (XmlOptions)null);
      }

      public static ThresholdParamsType newInstance(XmlOptions options) {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().newInstance(ThresholdParamsType.type, options);
      }

      public static ThresholdParamsType parse(String xmlAsString) throws XmlException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ThresholdParamsType.type, (XmlOptions)null);
      }

      public static ThresholdParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ThresholdParamsType.type, options);
      }

      public static ThresholdParamsType parse(File file) throws XmlException, IOException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(file, ThresholdParamsType.type, (XmlOptions)null);
      }

      public static ThresholdParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(file, ThresholdParamsType.type, options);
      }

      public static ThresholdParamsType parse(URL u) throws XmlException, IOException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(u, ThresholdParamsType.type, (XmlOptions)null);
      }

      public static ThresholdParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(u, ThresholdParamsType.type, options);
      }

      public static ThresholdParamsType parse(InputStream is) throws XmlException, IOException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(is, ThresholdParamsType.type, (XmlOptions)null);
      }

      public static ThresholdParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(is, ThresholdParamsType.type, options);
      }

      public static ThresholdParamsType parse(Reader r) throws XmlException, IOException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(r, ThresholdParamsType.type, (XmlOptions)null);
      }

      public static ThresholdParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(r, ThresholdParamsType.type, options);
      }

      public static ThresholdParamsType parse(XMLStreamReader sr) throws XmlException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(sr, ThresholdParamsType.type, (XmlOptions)null);
      }

      public static ThresholdParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(sr, ThresholdParamsType.type, options);
      }

      public static ThresholdParamsType parse(Node node) throws XmlException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(node, ThresholdParamsType.type, (XmlOptions)null);
      }

      public static ThresholdParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(node, ThresholdParamsType.type, options);
      }

      /** @deprecated */
      public static ThresholdParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(xis, ThresholdParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ThresholdParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ThresholdParamsType)XmlBeans.getContextTypeLoader().parse(xis, ThresholdParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ThresholdParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ThresholdParamsType.type, options);
      }

      private Factory() {
      }
   }
}
