package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface WsatConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WsatConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("wsatconfigtype0229type");

   boolean getEnabled();

   XmlBoolean xgetEnabled();

   boolean isSetEnabled();

   void setEnabled(boolean var1);

   void xsetEnabled(XmlBoolean var1);

   void unsetEnabled();

   TransactionFlowType.Enum getFlowType();

   TransactionFlowType xgetFlowType();

   boolean isSetFlowType();

   void setFlowType(TransactionFlowType.Enum var1);

   void xsetFlowType(TransactionFlowType var1);

   void unsetFlowType();

   TransactionVersion.Enum getVersion();

   TransactionVersion xgetVersion();

   boolean isSetVersion();

   void setVersion(TransactionVersion.Enum var1);

   void xsetVersion(TransactionVersion var1);

   void unsetVersion();

   public static final class Factory {
      public static WsatConfigType newInstance() {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().newInstance(WsatConfigType.type, (XmlOptions)null);
      }

      public static WsatConfigType newInstance(XmlOptions options) {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().newInstance(WsatConfigType.type, options);
      }

      public static WsatConfigType parse(String xmlAsString) throws XmlException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsatConfigType.type, (XmlOptions)null);
      }

      public static WsatConfigType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WsatConfigType.type, options);
      }

      public static WsatConfigType parse(File file) throws XmlException, IOException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(file, WsatConfigType.type, (XmlOptions)null);
      }

      public static WsatConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(file, WsatConfigType.type, options);
      }

      public static WsatConfigType parse(URL u) throws XmlException, IOException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(u, WsatConfigType.type, (XmlOptions)null);
      }

      public static WsatConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(u, WsatConfigType.type, options);
      }

      public static WsatConfigType parse(InputStream is) throws XmlException, IOException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(is, WsatConfigType.type, (XmlOptions)null);
      }

      public static WsatConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(is, WsatConfigType.type, options);
      }

      public static WsatConfigType parse(Reader r) throws XmlException, IOException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(r, WsatConfigType.type, (XmlOptions)null);
      }

      public static WsatConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(r, WsatConfigType.type, options);
      }

      public static WsatConfigType parse(XMLStreamReader sr) throws XmlException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(sr, WsatConfigType.type, (XmlOptions)null);
      }

      public static WsatConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(sr, WsatConfigType.type, options);
      }

      public static WsatConfigType parse(Node node) throws XmlException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(node, WsatConfigType.type, (XmlOptions)null);
      }

      public static WsatConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(node, WsatConfigType.type, options);
      }

      /** @deprecated */
      public static WsatConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(xis, WsatConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WsatConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WsatConfigType)XmlBeans.getContextTypeLoader().parse(xis, WsatConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsatConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WsatConfigType.type, options);
      }

      private Factory() {
      }
   }
}
