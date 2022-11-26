package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ReplicationTypeType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ReplicationTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("replicationtypetype1291type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ReplicationTypeType newInstance() {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().newInstance(ReplicationTypeType.type, (XmlOptions)null);
      }

      public static ReplicationTypeType newInstance(XmlOptions options) {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().newInstance(ReplicationTypeType.type, options);
      }

      public static ReplicationTypeType parse(String xmlAsString) throws XmlException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReplicationTypeType.type, (XmlOptions)null);
      }

      public static ReplicationTypeType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReplicationTypeType.type, options);
      }

      public static ReplicationTypeType parse(File file) throws XmlException, IOException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(file, ReplicationTypeType.type, (XmlOptions)null);
      }

      public static ReplicationTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(file, ReplicationTypeType.type, options);
      }

      public static ReplicationTypeType parse(URL u) throws XmlException, IOException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(u, ReplicationTypeType.type, (XmlOptions)null);
      }

      public static ReplicationTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(u, ReplicationTypeType.type, options);
      }

      public static ReplicationTypeType parse(InputStream is) throws XmlException, IOException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(is, ReplicationTypeType.type, (XmlOptions)null);
      }

      public static ReplicationTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(is, ReplicationTypeType.type, options);
      }

      public static ReplicationTypeType parse(Reader r) throws XmlException, IOException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(r, ReplicationTypeType.type, (XmlOptions)null);
      }

      public static ReplicationTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(r, ReplicationTypeType.type, options);
      }

      public static ReplicationTypeType parse(XMLStreamReader sr) throws XmlException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(sr, ReplicationTypeType.type, (XmlOptions)null);
      }

      public static ReplicationTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(sr, ReplicationTypeType.type, options);
      }

      public static ReplicationTypeType parse(Node node) throws XmlException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(node, ReplicationTypeType.type, (XmlOptions)null);
      }

      public static ReplicationTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(node, ReplicationTypeType.type, options);
      }

      /** @deprecated */
      public static ReplicationTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(xis, ReplicationTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ReplicationTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ReplicationTypeType)XmlBeans.getContextTypeLoader().parse(xis, ReplicationTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReplicationTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReplicationTypeType.type, options);
      }

      private Factory() {
      }
   }
}
