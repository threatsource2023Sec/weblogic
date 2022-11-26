package com.bea.connector.diagnostic;

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

public interface AdapterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdapterType.class.getClassLoader(), "schemacom_bea_xml.system.conn_diag").resolveHandle("adaptertypefdd0type");

   String getPartitionName();

   XmlString xgetPartitionName();

   void setPartitionName(String var1);

   void xsetPartitionName(XmlString var1);

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

   OutboundAdapterType[] getOutboundAdapterArray();

   OutboundAdapterType getOutboundAdapterArray(int var1);

   int sizeOfOutboundAdapterArray();

   void setOutboundAdapterArray(OutboundAdapterType[] var1);

   void setOutboundAdapterArray(int var1, OutboundAdapterType var2);

   OutboundAdapterType insertNewOutboundAdapter(int var1);

   OutboundAdapterType addNewOutboundAdapter();

   void removeOutboundAdapter(int var1);

   InboundAdapterType[] getInboundAdapterArray();

   InboundAdapterType getInboundAdapterArray(int var1);

   int sizeOfInboundAdapterArray();

   void setInboundAdapterArray(InboundAdapterType[] var1);

   void setInboundAdapterArray(int var1, InboundAdapterType var2);

   InboundAdapterType insertNewInboundAdapter(int var1);

   InboundAdapterType addNewInboundAdapter();

   void removeInboundAdapter(int var1);

   WorkManagerType getWorkManager();

   boolean isSetWorkManager();

   void setWorkManager(WorkManagerType var1);

   WorkManagerType addNewWorkManager();

   void unsetWorkManager();

   public static final class Factory {
      public static AdapterType newInstance() {
         return (AdapterType)XmlBeans.getContextTypeLoader().newInstance(AdapterType.type, (XmlOptions)null);
      }

      public static AdapterType newInstance(XmlOptions options) {
         return (AdapterType)XmlBeans.getContextTypeLoader().newInstance(AdapterType.type, options);
      }

      public static AdapterType parse(String xmlAsString) throws XmlException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdapterType.type, (XmlOptions)null);
      }

      public static AdapterType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdapterType.type, options);
      }

      public static AdapterType parse(File file) throws XmlException, IOException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(file, AdapterType.type, (XmlOptions)null);
      }

      public static AdapterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(file, AdapterType.type, options);
      }

      public static AdapterType parse(URL u) throws XmlException, IOException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(u, AdapterType.type, (XmlOptions)null);
      }

      public static AdapterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(u, AdapterType.type, options);
      }

      public static AdapterType parse(InputStream is) throws XmlException, IOException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(is, AdapterType.type, (XmlOptions)null);
      }

      public static AdapterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(is, AdapterType.type, options);
      }

      public static AdapterType parse(Reader r) throws XmlException, IOException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(r, AdapterType.type, (XmlOptions)null);
      }

      public static AdapterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(r, AdapterType.type, options);
      }

      public static AdapterType parse(XMLStreamReader sr) throws XmlException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(sr, AdapterType.type, (XmlOptions)null);
      }

      public static AdapterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(sr, AdapterType.type, options);
      }

      public static AdapterType parse(Node node) throws XmlException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(node, AdapterType.type, (XmlOptions)null);
      }

      public static AdapterType parse(Node node, XmlOptions options) throws XmlException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(node, AdapterType.type, options);
      }

      /** @deprecated */
      public static AdapterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(xis, AdapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdapterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdapterType)XmlBeans.getContextTypeLoader().parse(xis, AdapterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdapterType.type, options);
      }

      private Factory() {
      }
   }
}
