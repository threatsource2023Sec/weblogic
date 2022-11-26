package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface OperationComponentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OperationComponentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("operationcomponenttypeb822type");

   String getName();

   void setName(String var1);

   String addNewName();

   WsatConfigType getWsatConfig();

   boolean isSetWsatConfig();

   void setWsatConfig(WsatConfigType var1);

   WsatConfigType addNewWsatConfig();

   void unsetWsatConfig();

   public static final class Factory {
      public static OperationComponentType newInstance() {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().newInstance(OperationComponentType.type, (XmlOptions)null);
      }

      public static OperationComponentType newInstance(XmlOptions options) {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().newInstance(OperationComponentType.type, options);
      }

      public static OperationComponentType parse(java.lang.String xmlAsString) throws XmlException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OperationComponentType.type, (XmlOptions)null);
      }

      public static OperationComponentType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OperationComponentType.type, options);
      }

      public static OperationComponentType parse(File file) throws XmlException, IOException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(file, OperationComponentType.type, (XmlOptions)null);
      }

      public static OperationComponentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(file, OperationComponentType.type, options);
      }

      public static OperationComponentType parse(URL u) throws XmlException, IOException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(u, OperationComponentType.type, (XmlOptions)null);
      }

      public static OperationComponentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(u, OperationComponentType.type, options);
      }

      public static OperationComponentType parse(InputStream is) throws XmlException, IOException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(is, OperationComponentType.type, (XmlOptions)null);
      }

      public static OperationComponentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(is, OperationComponentType.type, options);
      }

      public static OperationComponentType parse(Reader r) throws XmlException, IOException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(r, OperationComponentType.type, (XmlOptions)null);
      }

      public static OperationComponentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(r, OperationComponentType.type, options);
      }

      public static OperationComponentType parse(XMLStreamReader sr) throws XmlException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(sr, OperationComponentType.type, (XmlOptions)null);
      }

      public static OperationComponentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(sr, OperationComponentType.type, options);
      }

      public static OperationComponentType parse(Node node) throws XmlException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(node, OperationComponentType.type, (XmlOptions)null);
      }

      public static OperationComponentType parse(Node node, XmlOptions options) throws XmlException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(node, OperationComponentType.type, options);
      }

      /** @deprecated */
      public static OperationComponentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(xis, OperationComponentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OperationComponentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OperationComponentType)XmlBeans.getContextTypeLoader().parse(xis, OperationComponentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OperationComponentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OperationComponentType.type, options);
      }

      private Factory() {
      }
   }
}
