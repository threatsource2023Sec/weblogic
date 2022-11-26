package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TimerDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TimerDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("timerdescriptortypef7edtype");

   XsdStringType getPersistentStoreLogicalName();

   boolean isSetPersistentStoreLogicalName();

   void setPersistentStoreLogicalName(XsdStringType var1);

   XsdStringType addNewPersistentStoreLogicalName();

   void unsetPersistentStoreLogicalName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TimerDescriptorType newInstance() {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().newInstance(TimerDescriptorType.type, (XmlOptions)null);
      }

      public static TimerDescriptorType newInstance(XmlOptions options) {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().newInstance(TimerDescriptorType.type, options);
      }

      public static TimerDescriptorType parse(String xmlAsString) throws XmlException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimerDescriptorType.type, (XmlOptions)null);
      }

      public static TimerDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimerDescriptorType.type, options);
      }

      public static TimerDescriptorType parse(File file) throws XmlException, IOException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(file, TimerDescriptorType.type, (XmlOptions)null);
      }

      public static TimerDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(file, TimerDescriptorType.type, options);
      }

      public static TimerDescriptorType parse(URL u) throws XmlException, IOException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(u, TimerDescriptorType.type, (XmlOptions)null);
      }

      public static TimerDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(u, TimerDescriptorType.type, options);
      }

      public static TimerDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(is, TimerDescriptorType.type, (XmlOptions)null);
      }

      public static TimerDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(is, TimerDescriptorType.type, options);
      }

      public static TimerDescriptorType parse(Reader r) throws XmlException, IOException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(r, TimerDescriptorType.type, (XmlOptions)null);
      }

      public static TimerDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(r, TimerDescriptorType.type, options);
      }

      public static TimerDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, TimerDescriptorType.type, (XmlOptions)null);
      }

      public static TimerDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, TimerDescriptorType.type, options);
      }

      public static TimerDescriptorType parse(Node node) throws XmlException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(node, TimerDescriptorType.type, (XmlOptions)null);
      }

      public static TimerDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(node, TimerDescriptorType.type, options);
      }

      /** @deprecated */
      public static TimerDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, TimerDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TimerDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TimerDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, TimerDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimerDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimerDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
