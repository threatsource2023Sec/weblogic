package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface ConnectionDecoratorsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionDecoratorsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("connectiondecoratorstypee2e5type");

   CustomConnectionDecoratorType[] getCustomConnectionDecoratorArray();

   CustomConnectionDecoratorType getCustomConnectionDecoratorArray(int var1);

   boolean isNilCustomConnectionDecoratorArray(int var1);

   int sizeOfCustomConnectionDecoratorArray();

   void setCustomConnectionDecoratorArray(CustomConnectionDecoratorType[] var1);

   void setCustomConnectionDecoratorArray(int var1, CustomConnectionDecoratorType var2);

   void setNilCustomConnectionDecoratorArray(int var1);

   CustomConnectionDecoratorType insertNewCustomConnectionDecorator(int var1);

   CustomConnectionDecoratorType addNewCustomConnectionDecorator();

   void removeCustomConnectionDecorator(int var1);

   public static final class Factory {
      public static ConnectionDecoratorsType newInstance() {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().newInstance(ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorsType newInstance(XmlOptions options) {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().newInstance(ConnectionDecoratorsType.type, options);
      }

      public static ConnectionDecoratorsType parse(String xmlAsString) throws XmlException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConnectionDecoratorsType.type, options);
      }

      public static ConnectionDecoratorsType parse(File file) throws XmlException, IOException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(file, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(file, ConnectionDecoratorsType.type, options);
      }

      public static ConnectionDecoratorsType parse(URL u) throws XmlException, IOException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(u, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(u, ConnectionDecoratorsType.type, options);
      }

      public static ConnectionDecoratorsType parse(InputStream is) throws XmlException, IOException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(is, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(is, ConnectionDecoratorsType.type, options);
      }

      public static ConnectionDecoratorsType parse(Reader r) throws XmlException, IOException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(r, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(r, ConnectionDecoratorsType.type, options);
      }

      public static ConnectionDecoratorsType parse(XMLStreamReader sr) throws XmlException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(sr, ConnectionDecoratorsType.type, options);
      }

      public static ConnectionDecoratorsType parse(Node node) throws XmlException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(node, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      public static ConnectionDecoratorsType parse(Node node, XmlOptions options) throws XmlException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(node, ConnectionDecoratorsType.type, options);
      }

      /** @deprecated */
      public static ConnectionDecoratorsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConnectionDecoratorsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConnectionDecoratorsType)XmlBeans.getContextTypeLoader().parse(xis, ConnectionDecoratorsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionDecoratorsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConnectionDecoratorsType.type, options);
      }

      private Factory() {
      }
   }
}
