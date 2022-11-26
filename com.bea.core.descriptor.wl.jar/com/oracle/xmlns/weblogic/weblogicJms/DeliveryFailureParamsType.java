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

public interface DeliveryFailureParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DeliveryFailureParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("deliveryfailureparamstype5d06type");

   String getErrorDestination();

   XmlString xgetErrorDestination();

   boolean isNilErrorDestination();

   boolean isSetErrorDestination();

   void setErrorDestination(String var1);

   void xsetErrorDestination(XmlString var1);

   void setNilErrorDestination();

   void unsetErrorDestination();

   int getRedeliveryLimit();

   XmlInt xgetRedeliveryLimit();

   boolean isSetRedeliveryLimit();

   void setRedeliveryLimit(int var1);

   void xsetRedeliveryLimit(XmlInt var1);

   void unsetRedeliveryLimit();

   ExpirationPolicyType.Enum getExpirationPolicy();

   ExpirationPolicyType xgetExpirationPolicy();

   boolean isSetExpirationPolicy();

   void setExpirationPolicy(ExpirationPolicyType.Enum var1);

   void xsetExpirationPolicy(ExpirationPolicyType var1);

   void unsetExpirationPolicy();

   String getExpirationLoggingPolicy();

   XmlString xgetExpirationLoggingPolicy();

   boolean isNilExpirationLoggingPolicy();

   boolean isSetExpirationLoggingPolicy();

   void setExpirationLoggingPolicy(String var1);

   void xsetExpirationLoggingPolicy(XmlString var1);

   void setNilExpirationLoggingPolicy();

   void unsetExpirationLoggingPolicy();

   public static final class Factory {
      public static DeliveryFailureParamsType newInstance() {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().newInstance(DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      public static DeliveryFailureParamsType newInstance(XmlOptions options) {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().newInstance(DeliveryFailureParamsType.type, options);
      }

      public static DeliveryFailureParamsType parse(String xmlAsString) throws XmlException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      public static DeliveryFailureParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DeliveryFailureParamsType.type, options);
      }

      public static DeliveryFailureParamsType parse(File file) throws XmlException, IOException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(file, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      public static DeliveryFailureParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(file, DeliveryFailureParamsType.type, options);
      }

      public static DeliveryFailureParamsType parse(URL u) throws XmlException, IOException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(u, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      public static DeliveryFailureParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(u, DeliveryFailureParamsType.type, options);
      }

      public static DeliveryFailureParamsType parse(InputStream is) throws XmlException, IOException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(is, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      public static DeliveryFailureParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(is, DeliveryFailureParamsType.type, options);
      }

      public static DeliveryFailureParamsType parse(Reader r) throws XmlException, IOException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(r, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      public static DeliveryFailureParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(r, DeliveryFailureParamsType.type, options);
      }

      public static DeliveryFailureParamsType parse(XMLStreamReader sr) throws XmlException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(sr, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      public static DeliveryFailureParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(sr, DeliveryFailureParamsType.type, options);
      }

      public static DeliveryFailureParamsType parse(Node node) throws XmlException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(node, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      public static DeliveryFailureParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(node, DeliveryFailureParamsType.type, options);
      }

      /** @deprecated */
      public static DeliveryFailureParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(xis, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DeliveryFailureParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DeliveryFailureParamsType)XmlBeans.getContextTypeLoader().parse(xis, DeliveryFailureParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeliveryFailureParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DeliveryFailureParamsType.type, options);
      }

      private Factory() {
      }
   }
}
