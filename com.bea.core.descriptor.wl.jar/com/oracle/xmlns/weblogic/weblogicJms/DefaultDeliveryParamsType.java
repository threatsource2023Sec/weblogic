package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
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

public interface DefaultDeliveryParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultDeliveryParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("defaultdeliveryparamstype9195type");

   DeliveryModeType.Enum getDefaultDeliveryMode();

   DeliveryModeType xgetDefaultDeliveryMode();

   boolean isSetDefaultDeliveryMode();

   void setDefaultDeliveryMode(DeliveryModeType.Enum var1);

   void xsetDefaultDeliveryMode(DeliveryModeType var1);

   void unsetDefaultDeliveryMode();

   String getDefaultTimeToDeliver();

   XmlString xgetDefaultTimeToDeliver();

   boolean isSetDefaultTimeToDeliver();

   void setDefaultTimeToDeliver(String var1);

   void xsetDefaultTimeToDeliver(XmlString var1);

   void unsetDefaultTimeToDeliver();

   long getDefaultTimeToLive();

   XmlLong xgetDefaultTimeToLive();

   boolean isSetDefaultTimeToLive();

   void setDefaultTimeToLive(long var1);

   void xsetDefaultTimeToLive(XmlLong var1);

   void unsetDefaultTimeToLive();

   int getDefaultPriority();

   XmlInt xgetDefaultPriority();

   boolean isSetDefaultPriority();

   void setDefaultPriority(int var1);

   void xsetDefaultPriority(XmlInt var1);

   void unsetDefaultPriority();

   long getDefaultRedeliveryDelay();

   XmlLong xgetDefaultRedeliveryDelay();

   boolean isSetDefaultRedeliveryDelay();

   void setDefaultRedeliveryDelay(long var1);

   void xsetDefaultRedeliveryDelay(XmlLong var1);

   void unsetDefaultRedeliveryDelay();

   long getSendTimeout();

   XmlLong xgetSendTimeout();

   boolean isSetSendTimeout();

   void setSendTimeout(long var1);

   void xsetSendTimeout(XmlLong var1);

   void unsetSendTimeout();

   int getDefaultCompressionThreshold();

   XmlInt xgetDefaultCompressionThreshold();

   boolean isSetDefaultCompressionThreshold();

   void setDefaultCompressionThreshold(int var1);

   void xsetDefaultCompressionThreshold(XmlInt var1);

   void unsetDefaultCompressionThreshold();

   String getDefaultUnitOfOrder();

   XmlString xgetDefaultUnitOfOrder();

   boolean isNilDefaultUnitOfOrder();

   boolean isSetDefaultUnitOfOrder();

   void setDefaultUnitOfOrder(String var1);

   void xsetDefaultUnitOfOrder(XmlString var1);

   void setNilDefaultUnitOfOrder();

   void unsetDefaultUnitOfOrder();

   public static final class Factory {
      public static DefaultDeliveryParamsType newInstance() {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().newInstance(DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      public static DefaultDeliveryParamsType newInstance(XmlOptions options) {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().newInstance(DefaultDeliveryParamsType.type, options);
      }

      public static DefaultDeliveryParamsType parse(String xmlAsString) throws XmlException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      public static DefaultDeliveryParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultDeliveryParamsType.type, options);
      }

      public static DefaultDeliveryParamsType parse(File file) throws XmlException, IOException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(file, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      public static DefaultDeliveryParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(file, DefaultDeliveryParamsType.type, options);
      }

      public static DefaultDeliveryParamsType parse(URL u) throws XmlException, IOException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(u, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      public static DefaultDeliveryParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(u, DefaultDeliveryParamsType.type, options);
      }

      public static DefaultDeliveryParamsType parse(InputStream is) throws XmlException, IOException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(is, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      public static DefaultDeliveryParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(is, DefaultDeliveryParamsType.type, options);
      }

      public static DefaultDeliveryParamsType parse(Reader r) throws XmlException, IOException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(r, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      public static DefaultDeliveryParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(r, DefaultDeliveryParamsType.type, options);
      }

      public static DefaultDeliveryParamsType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      public static DefaultDeliveryParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(sr, DefaultDeliveryParamsType.type, options);
      }

      public static DefaultDeliveryParamsType parse(Node node) throws XmlException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(node, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      public static DefaultDeliveryParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(node, DefaultDeliveryParamsType.type, options);
      }

      /** @deprecated */
      public static DefaultDeliveryParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultDeliveryParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultDeliveryParamsType)XmlBeans.getContextTypeLoader().parse(xis, DefaultDeliveryParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDeliveryParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultDeliveryParamsType.type, options);
      }

      private Factory() {
      }
   }
}
