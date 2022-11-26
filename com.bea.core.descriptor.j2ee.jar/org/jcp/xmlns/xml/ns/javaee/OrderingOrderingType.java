package org.jcp.xmlns.xml.ns.javaee;

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

public interface OrderingOrderingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OrderingOrderingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("orderingorderingtype6830type");

   JavaIdentifierType[] getNameArray();

   JavaIdentifierType getNameArray(int var1);

   int sizeOfNameArray();

   void setNameArray(JavaIdentifierType[] var1);

   void setNameArray(int var1, JavaIdentifierType var2);

   JavaIdentifierType insertNewName(int var1);

   JavaIdentifierType addNewName();

   void removeName(int var1);

   OrderingOthersType getOthers();

   boolean isSetOthers();

   void setOthers(OrderingOthersType var1);

   OrderingOthersType addNewOthers();

   void unsetOthers();

   public static final class Factory {
      public static OrderingOrderingType newInstance() {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().newInstance(OrderingOrderingType.type, (XmlOptions)null);
      }

      public static OrderingOrderingType newInstance(XmlOptions options) {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().newInstance(OrderingOrderingType.type, options);
      }

      public static OrderingOrderingType parse(java.lang.String xmlAsString) throws XmlException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OrderingOrderingType.type, (XmlOptions)null);
      }

      public static OrderingOrderingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OrderingOrderingType.type, options);
      }

      public static OrderingOrderingType parse(File file) throws XmlException, IOException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(file, OrderingOrderingType.type, (XmlOptions)null);
      }

      public static OrderingOrderingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(file, OrderingOrderingType.type, options);
      }

      public static OrderingOrderingType parse(URL u) throws XmlException, IOException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(u, OrderingOrderingType.type, (XmlOptions)null);
      }

      public static OrderingOrderingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(u, OrderingOrderingType.type, options);
      }

      public static OrderingOrderingType parse(InputStream is) throws XmlException, IOException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(is, OrderingOrderingType.type, (XmlOptions)null);
      }

      public static OrderingOrderingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(is, OrderingOrderingType.type, options);
      }

      public static OrderingOrderingType parse(Reader r) throws XmlException, IOException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(r, OrderingOrderingType.type, (XmlOptions)null);
      }

      public static OrderingOrderingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(r, OrderingOrderingType.type, options);
      }

      public static OrderingOrderingType parse(XMLStreamReader sr) throws XmlException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(sr, OrderingOrderingType.type, (XmlOptions)null);
      }

      public static OrderingOrderingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(sr, OrderingOrderingType.type, options);
      }

      public static OrderingOrderingType parse(Node node) throws XmlException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(node, OrderingOrderingType.type, (XmlOptions)null);
      }

      public static OrderingOrderingType parse(Node node, XmlOptions options) throws XmlException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(node, OrderingOrderingType.type, options);
      }

      /** @deprecated */
      public static OrderingOrderingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(xis, OrderingOrderingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OrderingOrderingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OrderingOrderingType)XmlBeans.getContextTypeLoader().parse(xis, OrderingOrderingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OrderingOrderingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OrderingOrderingType.type, options);
      }

      private Factory() {
      }
   }
}
