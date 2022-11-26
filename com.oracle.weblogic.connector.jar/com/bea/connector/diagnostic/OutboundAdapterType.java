package com.bea.connector.diagnostic;

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

public interface OutboundAdapterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OutboundAdapterType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("outboundadaptertypeedc0type");

   String getJndiName();

   XmlString xgetJndiName();

   void setJndiName(String var1);

   void xsetJndiName(XmlString var1);

   String getState();

   XmlString xgetState();

   void setState(String var1);

   void xsetState(XmlString var1);

   HealthType getHealth();

   void setHealth(HealthType var1);

   HealthType addNewHealth();

   int getMaxCapacity();

   XmlInt xgetMaxCapacity();

   void setMaxCapacity(int var1);

   void xsetMaxCapacity(XmlInt var1);

   int getConnectionsInUse();

   XmlInt xgetConnectionsInUse();

   void setConnectionsInUse(int var1);

   void xsetConnectionsInUse(XmlInt var1);

   int getConnectionsInFreePool();

   XmlInt xgetConnectionsInFreePool();

   void setConnectionsInFreePool(int var1);

   void xsetConnectionsInFreePool(XmlInt var1);

   ManagedConnectionType[] getManagedConnectionArray();

   ManagedConnectionType getManagedConnectionArray(int var1);

   int sizeOfManagedConnectionArray();

   void setManagedConnectionArray(ManagedConnectionType[] var1);

   void setManagedConnectionArray(int var1, ManagedConnectionType var2);

   ManagedConnectionType insertNewManagedConnection(int var1);

   ManagedConnectionType addNewManagedConnection();

   void removeManagedConnection(int var1);

   public static final class Factory {
      public static OutboundAdapterType newInstance() {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().newInstance(OutboundAdapterType.type, (XmlOptions)null);
      }

      public static OutboundAdapterType newInstance(XmlOptions options) {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().newInstance(OutboundAdapterType.type, options);
      }

      public static OutboundAdapterType parse(String xmlAsString) throws XmlException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundAdapterType.type, (XmlOptions)null);
      }

      public static OutboundAdapterType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OutboundAdapterType.type, options);
      }

      public static OutboundAdapterType parse(File file) throws XmlException, IOException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(file, OutboundAdapterType.type, (XmlOptions)null);
      }

      public static OutboundAdapterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(file, OutboundAdapterType.type, options);
      }

      public static OutboundAdapterType parse(URL u) throws XmlException, IOException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(u, OutboundAdapterType.type, (XmlOptions)null);
      }

      public static OutboundAdapterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(u, OutboundAdapterType.type, options);
      }

      public static OutboundAdapterType parse(InputStream is) throws XmlException, IOException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(is, OutboundAdapterType.type, (XmlOptions)null);
      }

      public static OutboundAdapterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(is, OutboundAdapterType.type, options);
      }

      public static OutboundAdapterType parse(Reader r) throws XmlException, IOException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(r, OutboundAdapterType.type, (XmlOptions)null);
      }

      public static OutboundAdapterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(r, OutboundAdapterType.type, options);
      }

      public static OutboundAdapterType parse(XMLStreamReader sr) throws XmlException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(sr, OutboundAdapterType.type, (XmlOptions)null);
      }

      public static OutboundAdapterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(sr, OutboundAdapterType.type, options);
      }

      public static OutboundAdapterType parse(Node node) throws XmlException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(node, OutboundAdapterType.type, (XmlOptions)null);
      }

      public static OutboundAdapterType parse(Node node, XmlOptions options) throws XmlException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(node, OutboundAdapterType.type, options);
      }

      /** @deprecated */
      public static OutboundAdapterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(xis, OutboundAdapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OutboundAdapterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OutboundAdapterType)XmlBeans.getContextTypeLoader().parse(xis, OutboundAdapterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundAdapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OutboundAdapterType.type, options);
      }

      private Factory() {
      }
   }
}
