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

public interface DeliveryParamsOverridesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeliveryParamsOverridesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deliveryparamsoverridestyped42ftype");

   DeliveryModeType.Enum getDeliveryMode();

   DeliveryModeType xgetDeliveryMode();

   boolean isSetDeliveryMode();

   void setDeliveryMode(DeliveryModeType.Enum var1);

   void xsetDeliveryMode(DeliveryModeType var1);

   void unsetDeliveryMode();

   String getTimeToDeliver();

   XmlString xgetTimeToDeliver();

   boolean isNilTimeToDeliver();

   boolean isSetTimeToDeliver();

   void setTimeToDeliver(String var1);

   void xsetTimeToDeliver(XmlString var1);

   void setNilTimeToDeliver();

   void unsetTimeToDeliver();

   long getTimeToLive();

   XmlLong xgetTimeToLive();

   boolean isSetTimeToLive();

   void setTimeToLive(long var1);

   void xsetTimeToLive(XmlLong var1);

   void unsetTimeToLive();

   int getPriority();

   XmlInt xgetPriority();

   boolean isSetPriority();

   void setPriority(int var1);

   void xsetPriority(XmlInt var1);

   void unsetPriority();

   long getRedeliveryDelay();

   XmlLong xgetRedeliveryDelay();

   boolean isSetRedeliveryDelay();

   void setRedeliveryDelay(long var1);

   void xsetRedeliveryDelay(XmlLong var1);

   void unsetRedeliveryDelay();

   public static final class Factory {
      public static DeliveryParamsOverridesType newInstance() {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().newInstance(DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      public static DeliveryParamsOverridesType newInstance(XmlOptions options) {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().newInstance(DeliveryParamsOverridesType.type, options);
      }

      public static DeliveryParamsOverridesType parse(String xmlAsString) throws XmlException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      public static DeliveryParamsOverridesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeliveryParamsOverridesType.type, options);
      }

      public static DeliveryParamsOverridesType parse(File file) throws XmlException, IOException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(file, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      public static DeliveryParamsOverridesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(file, DeliveryParamsOverridesType.type, options);
      }

      public static DeliveryParamsOverridesType parse(URL u) throws XmlException, IOException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(u, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      public static DeliveryParamsOverridesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(u, DeliveryParamsOverridesType.type, options);
      }

      public static DeliveryParamsOverridesType parse(InputStream is) throws XmlException, IOException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(is, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      public static DeliveryParamsOverridesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(is, DeliveryParamsOverridesType.type, options);
      }

      public static DeliveryParamsOverridesType parse(Reader r) throws XmlException, IOException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(r, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      public static DeliveryParamsOverridesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(r, DeliveryParamsOverridesType.type, options);
      }

      public static DeliveryParamsOverridesType parse(XMLStreamReader sr) throws XmlException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(sr, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      public static DeliveryParamsOverridesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(sr, DeliveryParamsOverridesType.type, options);
      }

      public static DeliveryParamsOverridesType parse(Node node) throws XmlException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(node, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      public static DeliveryParamsOverridesType parse(Node node, XmlOptions options) throws XmlException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(node, DeliveryParamsOverridesType.type, options);
      }

      /** @deprecated */
      public static DeliveryParamsOverridesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(xis, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeliveryParamsOverridesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeliveryParamsOverridesType)XmlBeans.getContextTypeLoader().parse(xis, DeliveryParamsOverridesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeliveryParamsOverridesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeliveryParamsOverridesType.type, options);
      }

      private Factory() {
      }
   }
}
