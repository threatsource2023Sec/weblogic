package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface DestinationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DestinationType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("destinationtypea651type");

   Destination getDestination();

   void setDestination(Destination var1);

   Destination addNewDestination();

   public static final class Factory {
      public static DestinationType newInstance() {
         return (DestinationType)XmlBeans.getContextTypeLoader().newInstance(DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType newInstance(XmlOptions options) {
         return (DestinationType)XmlBeans.getContextTypeLoader().newInstance(DestinationType.type, options);
      }

      public static DestinationType parse(String xmlAsString) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestinationType.type, options);
      }

      public static DestinationType parse(File file) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(file, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(file, DestinationType.type, options);
      }

      public static DestinationType parse(URL u) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(u, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(u, DestinationType.type, options);
      }

      public static DestinationType parse(InputStream is) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(is, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(is, DestinationType.type, options);
      }

      public static DestinationType parse(Reader r) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(r, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(r, DestinationType.type, options);
      }

      public static DestinationType parse(XMLStreamReader sr) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(sr, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(sr, DestinationType.type, options);
      }

      public static DestinationType parse(Node node) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(node, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(Node node, XmlOptions options) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(node, DestinationType.type, options);
      }

      /** @deprecated */
      public static DestinationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(xis, DestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DestinationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(xis, DestinationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestinationType.type, options);
      }

      private Factory() {
      }
   }

   public interface Destination extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Destination.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("destination9637elemtype");

      String getName();

      XmlString xgetName();

      void setName(String var1);

      void xsetName(XmlString var1);

      String getServerName();

      XmlString xgetServerName();

      void setServerName(String var1);

      void xsetServerName(XmlString var1);

      String getServerURL();

      XmlString xgetServerURL();

      boolean isSetServerURL();

      void setServerURL(String var1);

      void xsetServerURL(XmlString var1);

      void unsetServerURL();

      String getApplicationName();

      XmlString xgetApplicationName();

      boolean isSetApplicationName();

      void setApplicationName(String var1);

      void xsetApplicationName(XmlString var1);

      void unsetApplicationName();

      String getModuleName();

      XmlString xgetModuleName();

      boolean isSetModuleName();

      void setModuleName(String var1);

      void xsetModuleName(XmlString var1);

      void unsetModuleName();

      public static final class Factory {
         public static Destination newInstance() {
            return (Destination)XmlBeans.getContextTypeLoader().newInstance(DestinationType.Destination.type, (XmlOptions)null);
         }

         public static Destination newInstance(XmlOptions options) {
            return (Destination)XmlBeans.getContextTypeLoader().newInstance(DestinationType.Destination.type, options);
         }

         private Factory() {
         }
      }
   }
}
