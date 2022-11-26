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

public interface InboundDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InboundDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("inboundfc27doctype");

   Inbound getInbound();

   void setInbound(Inbound var1);

   Inbound addNewInbound();

   public static final class Factory {
      public static InboundDocument newInstance() {
         return (InboundDocument)XmlBeans.getContextTypeLoader().newInstance(InboundDocument.type, (XmlOptions)null);
      }

      public static InboundDocument newInstance(XmlOptions options) {
         return (InboundDocument)XmlBeans.getContextTypeLoader().newInstance(InboundDocument.type, options);
      }

      public static InboundDocument parse(String xmlAsString) throws XmlException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundDocument.type, (XmlOptions)null);
      }

      public static InboundDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundDocument.type, options);
      }

      public static InboundDocument parse(File file) throws XmlException, IOException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(file, InboundDocument.type, (XmlOptions)null);
      }

      public static InboundDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(file, InboundDocument.type, options);
      }

      public static InboundDocument parse(URL u) throws XmlException, IOException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(u, InboundDocument.type, (XmlOptions)null);
      }

      public static InboundDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(u, InboundDocument.type, options);
      }

      public static InboundDocument parse(InputStream is) throws XmlException, IOException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(is, InboundDocument.type, (XmlOptions)null);
      }

      public static InboundDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(is, InboundDocument.type, options);
      }

      public static InboundDocument parse(Reader r) throws XmlException, IOException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(r, InboundDocument.type, (XmlOptions)null);
      }

      public static InboundDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(r, InboundDocument.type, options);
      }

      public static InboundDocument parse(XMLStreamReader sr) throws XmlException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(sr, InboundDocument.type, (XmlOptions)null);
      }

      public static InboundDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(sr, InboundDocument.type, options);
      }

      public static InboundDocument parse(Node node) throws XmlException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(node, InboundDocument.type, (XmlOptions)null);
      }

      public static InboundDocument parse(Node node, XmlOptions options) throws XmlException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(node, InboundDocument.type, options);
      }

      /** @deprecated */
      public static InboundDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(xis, InboundDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InboundDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InboundDocument)XmlBeans.getContextTypeLoader().parse(xis, InboundDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Inbound extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Inbound.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("inbound06a2elemtype");

      MessagelistenerDocument.Messagelistener[] getMessagelistenerArray();

      MessagelistenerDocument.Messagelistener getMessagelistenerArray(int var1);

      int sizeOfMessagelistenerArray();

      void setMessagelistenerArray(MessagelistenerDocument.Messagelistener[] var1);

      void setMessagelistenerArray(int var1, MessagelistenerDocument.Messagelistener var2);

      MessagelistenerDocument.Messagelistener insertNewMessagelistener(int var1);

      MessagelistenerDocument.Messagelistener addNewMessagelistener();

      void removeMessagelistener(int var1);

      public static final class Factory {
         public static Inbound newInstance() {
            return (Inbound)XmlBeans.getContextTypeLoader().newInstance(InboundDocument.Inbound.type, (XmlOptions)null);
         }

         public static Inbound newInstance(XmlOptions options) {
            return (Inbound)XmlBeans.getContextTypeLoader().newInstance(InboundDocument.Inbound.type, options);
         }

         private Factory() {
         }
      }
   }
}
