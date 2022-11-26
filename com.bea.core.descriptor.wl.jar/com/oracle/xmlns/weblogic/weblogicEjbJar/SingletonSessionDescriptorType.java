package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface SingletonSessionDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SingletonSessionDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("singletonsessiondescriptortype90betype");

   TimerDescriptorType getTimerDescriptor();

   boolean isSetTimerDescriptor();

   void setTimerDescriptor(TimerDescriptorType var1);

   TimerDescriptorType addNewTimerDescriptor();

   void unsetTimerDescriptor();

   SingletonClusteringType getSingletonClustering();

   boolean isSetSingletonClustering();

   void setSingletonClustering(SingletonClusteringType var1);

   SingletonClusteringType addNewSingletonClustering();

   void unsetSingletonClustering();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SingletonSessionDescriptorType newInstance() {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      public static SingletonSessionDescriptorType newInstance(XmlOptions options) {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(SingletonSessionDescriptorType.type, options);
      }

      public static SingletonSessionDescriptorType parse(String xmlAsString) throws XmlException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      public static SingletonSessionDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingletonSessionDescriptorType.type, options);
      }

      public static SingletonSessionDescriptorType parse(File file) throws XmlException, IOException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      public static SingletonSessionDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, SingletonSessionDescriptorType.type, options);
      }

      public static SingletonSessionDescriptorType parse(URL u) throws XmlException, IOException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      public static SingletonSessionDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, SingletonSessionDescriptorType.type, options);
      }

      public static SingletonSessionDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      public static SingletonSessionDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, SingletonSessionDescriptorType.type, options);
      }

      public static SingletonSessionDescriptorType parse(Reader r) throws XmlException, IOException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      public static SingletonSessionDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, SingletonSessionDescriptorType.type, options);
      }

      public static SingletonSessionDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      public static SingletonSessionDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, SingletonSessionDescriptorType.type, options);
      }

      public static SingletonSessionDescriptorType parse(Node node) throws XmlException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      public static SingletonSessionDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, SingletonSessionDescriptorType.type, options);
      }

      /** @deprecated */
      public static SingletonSessionDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SingletonSessionDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SingletonSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, SingletonSessionDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingletonSessionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingletonSessionDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
