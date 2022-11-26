package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface OrderingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OrderingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("orderingtypec581type");

   OrderingOrderingType getAfter();

   boolean isSetAfter();

   void setAfter(OrderingOrderingType var1);

   OrderingOrderingType addNewAfter();

   void unsetAfter();

   OrderingOrderingType getBefore();

   boolean isSetBefore();

   void setBefore(OrderingOrderingType var1);

   OrderingOrderingType addNewBefore();

   void unsetBefore();

   public static final class Factory {
      public static OrderingType newInstance() {
         return (OrderingType)XmlBeans.getContextTypeLoader().newInstance(OrderingType.type, (XmlOptions)null);
      }

      public static OrderingType newInstance(XmlOptions options) {
         return (OrderingType)XmlBeans.getContextTypeLoader().newInstance(OrderingType.type, options);
      }

      public static OrderingType parse(java.lang.String xmlAsString) throws XmlException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OrderingType.type, (XmlOptions)null);
      }

      public static OrderingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OrderingType.type, options);
      }

      public static OrderingType parse(File file) throws XmlException, IOException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(file, OrderingType.type, (XmlOptions)null);
      }

      public static OrderingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(file, OrderingType.type, options);
      }

      public static OrderingType parse(URL u) throws XmlException, IOException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(u, OrderingType.type, (XmlOptions)null);
      }

      public static OrderingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(u, OrderingType.type, options);
      }

      public static OrderingType parse(InputStream is) throws XmlException, IOException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(is, OrderingType.type, (XmlOptions)null);
      }

      public static OrderingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(is, OrderingType.type, options);
      }

      public static OrderingType parse(Reader r) throws XmlException, IOException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(r, OrderingType.type, (XmlOptions)null);
      }

      public static OrderingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(r, OrderingType.type, options);
      }

      public static OrderingType parse(XMLStreamReader sr) throws XmlException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(sr, OrderingType.type, (XmlOptions)null);
      }

      public static OrderingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(sr, OrderingType.type, options);
      }

      public static OrderingType parse(Node node) throws XmlException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(node, OrderingType.type, (XmlOptions)null);
      }

      public static OrderingType parse(Node node, XmlOptions options) throws XmlException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(node, OrderingType.type, options);
      }

      /** @deprecated */
      public static OrderingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(xis, OrderingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OrderingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OrderingType)XmlBeans.getContextTypeLoader().parse(xis, OrderingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OrderingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OrderingType.type, options);
      }

      private Factory() {
      }
   }
}
