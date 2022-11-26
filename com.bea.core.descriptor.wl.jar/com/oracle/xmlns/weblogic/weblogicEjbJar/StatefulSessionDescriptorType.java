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

public interface StatefulSessionDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StatefulSessionDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("statefulsessiondescriptortype592btype");

   StatefulSessionCacheType getStatefulSessionCache();

   boolean isSetStatefulSessionCache();

   void setStatefulSessionCache(StatefulSessionCacheType var1);

   StatefulSessionCacheType addNewStatefulSessionCache();

   void unsetStatefulSessionCache();

   PersistentStoreDirType getPersistentStoreDir();

   boolean isSetPersistentStoreDir();

   void setPersistentStoreDir(PersistentStoreDirType var1);

   PersistentStoreDirType addNewPersistentStoreDir();

   void unsetPersistentStoreDir();

   StatefulSessionClusteringType getStatefulSessionClustering();

   boolean isSetStatefulSessionClustering();

   void setStatefulSessionClustering(StatefulSessionClusteringType var1);

   StatefulSessionClusteringType addNewStatefulSessionClustering();

   void unsetStatefulSessionClustering();

   TrueFalseType getAllowRemoveDuringTransaction();

   boolean isSetAllowRemoveDuringTransaction();

   void setAllowRemoveDuringTransaction(TrueFalseType var1);

   TrueFalseType addNewAllowRemoveDuringTransaction();

   void unsetAllowRemoveDuringTransaction();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static StatefulSessionDescriptorType newInstance() {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatefulSessionDescriptorType newInstance(XmlOptions options) {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(StatefulSessionDescriptorType.type, options);
      }

      public static StatefulSessionDescriptorType parse(String xmlAsString) throws XmlException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatefulSessionDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatefulSessionDescriptorType.type, options);
      }

      public static StatefulSessionDescriptorType parse(File file) throws XmlException, IOException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatefulSessionDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, StatefulSessionDescriptorType.type, options);
      }

      public static StatefulSessionDescriptorType parse(URL u) throws XmlException, IOException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatefulSessionDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, StatefulSessionDescriptorType.type, options);
      }

      public static StatefulSessionDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatefulSessionDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, StatefulSessionDescriptorType.type, options);
      }

      public static StatefulSessionDescriptorType parse(Reader r) throws XmlException, IOException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatefulSessionDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, StatefulSessionDescriptorType.type, options);
      }

      public static StatefulSessionDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatefulSessionDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, StatefulSessionDescriptorType.type, options);
      }

      public static StatefulSessionDescriptorType parse(Node node) throws XmlException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatefulSessionDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, StatefulSessionDescriptorType.type, options);
      }

      /** @deprecated */
      public static StatefulSessionDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StatefulSessionDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StatefulSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, StatefulSessionDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatefulSessionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatefulSessionDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
