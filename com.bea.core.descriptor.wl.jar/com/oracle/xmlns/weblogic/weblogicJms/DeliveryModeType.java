package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface DeliveryModeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeliveryModeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deliverymodetype6c4ctype");
   Enum PERSISTENT = DeliveryModeType.Enum.forString("Persistent");
   Enum NON_PERSISTENT = DeliveryModeType.Enum.forString("Non-Persistent");
   Enum NO_DELIVERY = DeliveryModeType.Enum.forString("No-Delivery");
   int INT_PERSISTENT = 1;
   int INT_NON_PERSISTENT = 2;
   int INT_NO_DELIVERY = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static DeliveryModeType newValue(Object obj) {
         return (DeliveryModeType)DeliveryModeType.type.newValue(obj);
      }

      public static DeliveryModeType newInstance() {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().newInstance(DeliveryModeType.type, (XmlOptions)null);
      }

      public static DeliveryModeType newInstance(XmlOptions options) {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().newInstance(DeliveryModeType.type, options);
      }

      public static DeliveryModeType parse(String xmlAsString) throws XmlException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeliveryModeType.type, (XmlOptions)null);
      }

      public static DeliveryModeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeliveryModeType.type, options);
      }

      public static DeliveryModeType parse(File file) throws XmlException, IOException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(file, DeliveryModeType.type, (XmlOptions)null);
      }

      public static DeliveryModeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(file, DeliveryModeType.type, options);
      }

      public static DeliveryModeType parse(URL u) throws XmlException, IOException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(u, DeliveryModeType.type, (XmlOptions)null);
      }

      public static DeliveryModeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(u, DeliveryModeType.type, options);
      }

      public static DeliveryModeType parse(InputStream is) throws XmlException, IOException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(is, DeliveryModeType.type, (XmlOptions)null);
      }

      public static DeliveryModeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(is, DeliveryModeType.type, options);
      }

      public static DeliveryModeType parse(Reader r) throws XmlException, IOException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(r, DeliveryModeType.type, (XmlOptions)null);
      }

      public static DeliveryModeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(r, DeliveryModeType.type, options);
      }

      public static DeliveryModeType parse(XMLStreamReader sr) throws XmlException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(sr, DeliveryModeType.type, (XmlOptions)null);
      }

      public static DeliveryModeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(sr, DeliveryModeType.type, options);
      }

      public static DeliveryModeType parse(Node node) throws XmlException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(node, DeliveryModeType.type, (XmlOptions)null);
      }

      public static DeliveryModeType parse(Node node, XmlOptions options) throws XmlException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(node, DeliveryModeType.type, options);
      }

      /** @deprecated */
      public static DeliveryModeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(xis, DeliveryModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeliveryModeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeliveryModeType)XmlBeans.getContextTypeLoader().parse(xis, DeliveryModeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeliveryModeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeliveryModeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_PERSISTENT = 1;
      static final int INT_NON_PERSISTENT = 2;
      static final int INT_NO_DELIVERY = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Persistent", 1), new Enum("Non-Persistent", 2), new Enum("No-Delivery", 3)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
