package com.bea.connector.monitoring1Dot0;

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

public interface OutboundDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OutboundDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("outboundc53edoctype");

   Outbound getOutbound();

   void setOutbound(Outbound var1);

   Outbound addNewOutbound();

   public static final class Factory {
      public static OutboundDocument newInstance() {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().newInstance(OutboundDocument.type, (XmlOptions)null);
      }

      public static OutboundDocument newInstance(XmlOptions options) {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().newInstance(OutboundDocument.type, options);
      }

      public static OutboundDocument parse(String xmlAsString) throws XmlException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundDocument.type, (XmlOptions)null);
      }

      public static OutboundDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundDocument.type, options);
      }

      public static OutboundDocument parse(File file) throws XmlException, IOException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(file, OutboundDocument.type, (XmlOptions)null);
      }

      public static OutboundDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(file, OutboundDocument.type, options);
      }

      public static OutboundDocument parse(URL u) throws XmlException, IOException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(u, OutboundDocument.type, (XmlOptions)null);
      }

      public static OutboundDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(u, OutboundDocument.type, options);
      }

      public static OutboundDocument parse(InputStream is) throws XmlException, IOException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(is, OutboundDocument.type, (XmlOptions)null);
      }

      public static OutboundDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(is, OutboundDocument.type, options);
      }

      public static OutboundDocument parse(Reader r) throws XmlException, IOException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(r, OutboundDocument.type, (XmlOptions)null);
      }

      public static OutboundDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(r, OutboundDocument.type, options);
      }

      public static OutboundDocument parse(XMLStreamReader sr) throws XmlException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(sr, OutboundDocument.type, (XmlOptions)null);
      }

      public static OutboundDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(sr, OutboundDocument.type, options);
      }

      public static OutboundDocument parse(Node node) throws XmlException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(node, OutboundDocument.type, (XmlOptions)null);
      }

      public static OutboundDocument parse(Node node, XmlOptions options) throws XmlException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(node, OutboundDocument.type, options);
      }

      /** @deprecated */
      public static OutboundDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(xis, OutboundDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OutboundDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OutboundDocument)XmlBeans.getContextTypeLoader().parse(xis, OutboundDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Outbound extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Outbound.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("outbound8ccaelemtype");

      OutboundGroupDocument.OutboundGroup[] getOutboundGroupArray();

      OutboundGroupDocument.OutboundGroup getOutboundGroupArray(int var1);

      int sizeOfOutboundGroupArray();

      void setOutboundGroupArray(OutboundGroupDocument.OutboundGroup[] var1);

      void setOutboundGroupArray(int var1, OutboundGroupDocument.OutboundGroup var2);

      OutboundGroupDocument.OutboundGroup insertNewOutboundGroup(int var1);

      OutboundGroupDocument.OutboundGroup addNewOutboundGroup();

      void removeOutboundGroup(int var1);

      public static final class Factory {
         public static Outbound newInstance() {
            return (Outbound)XmlBeans.getContextTypeLoader().newInstance(OutboundDocument.Outbound.type, (XmlOptions)null);
         }

         public static Outbound newInstance(XmlOptions options) {
            return (Outbound)XmlBeans.getContextTypeLoader().newInstance(OutboundDocument.Outbound.type, options);
         }

         private Factory() {
         }
      }
   }
}
