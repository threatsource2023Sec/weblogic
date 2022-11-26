package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface OrderingOthersType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OrderingOthersType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("orderingotherstypeb3c1type");

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static OrderingOthersType newInstance() {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().newInstance(OrderingOthersType.type, (XmlOptions)null);
      }

      public static OrderingOthersType newInstance(XmlOptions options) {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().newInstance(OrderingOthersType.type, options);
      }

      public static OrderingOthersType parse(java.lang.String xmlAsString) throws XmlException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OrderingOthersType.type, (XmlOptions)null);
      }

      public static OrderingOthersType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OrderingOthersType.type, options);
      }

      public static OrderingOthersType parse(File file) throws XmlException, IOException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(file, OrderingOthersType.type, (XmlOptions)null);
      }

      public static OrderingOthersType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(file, OrderingOthersType.type, options);
      }

      public static OrderingOthersType parse(URL u) throws XmlException, IOException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(u, OrderingOthersType.type, (XmlOptions)null);
      }

      public static OrderingOthersType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(u, OrderingOthersType.type, options);
      }

      public static OrderingOthersType parse(InputStream is) throws XmlException, IOException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(is, OrderingOthersType.type, (XmlOptions)null);
      }

      public static OrderingOthersType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(is, OrderingOthersType.type, options);
      }

      public static OrderingOthersType parse(Reader r) throws XmlException, IOException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(r, OrderingOthersType.type, (XmlOptions)null);
      }

      public static OrderingOthersType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(r, OrderingOthersType.type, options);
      }

      public static OrderingOthersType parse(XMLStreamReader sr) throws XmlException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(sr, OrderingOthersType.type, (XmlOptions)null);
      }

      public static OrderingOthersType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(sr, OrderingOthersType.type, options);
      }

      public static OrderingOthersType parse(Node node) throws XmlException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(node, OrderingOthersType.type, (XmlOptions)null);
      }

      public static OrderingOthersType parse(Node node, XmlOptions options) throws XmlException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(node, OrderingOthersType.type, options);
      }

      /** @deprecated */
      public static OrderingOthersType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(xis, OrderingOthersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OrderingOthersType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OrderingOthersType)XmlBeans.getContextTypeLoader().parse(xis, OrderingOthersType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OrderingOthersType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OrderingOthersType.type, options);
      }

      private Factory() {
      }
   }
}
