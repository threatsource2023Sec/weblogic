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

public interface StatelessSessionDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StatelessSessionDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("statelesssessiondescriptortypeadfftype");

   PoolType getPool();

   boolean isSetPool();

   void setPool(PoolType var1);

   PoolType addNewPool();

   void unsetPool();

   TimerDescriptorType getTimerDescriptor();

   boolean isSetTimerDescriptor();

   void setTimerDescriptor(TimerDescriptorType var1);

   TimerDescriptorType addNewTimerDescriptor();

   void unsetTimerDescriptor();

   StatelessClusteringType getStatelessClustering();

   boolean isSetStatelessClustering();

   void setStatelessClustering(StatelessClusteringType var1);

   StatelessClusteringType addNewStatelessClustering();

   void unsetStatelessClustering();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static StatelessSessionDescriptorType newInstance() {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatelessSessionDescriptorType newInstance(XmlOptions options) {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().newInstance(StatelessSessionDescriptorType.type, options);
      }

      public static StatelessSessionDescriptorType parse(String xmlAsString) throws XmlException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatelessSessionDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatelessSessionDescriptorType.type, options);
      }

      public static StatelessSessionDescriptorType parse(File file) throws XmlException, IOException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatelessSessionDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(file, StatelessSessionDescriptorType.type, options);
      }

      public static StatelessSessionDescriptorType parse(URL u) throws XmlException, IOException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatelessSessionDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(u, StatelessSessionDescriptorType.type, options);
      }

      public static StatelessSessionDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatelessSessionDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(is, StatelessSessionDescriptorType.type, options);
      }

      public static StatelessSessionDescriptorType parse(Reader r) throws XmlException, IOException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatelessSessionDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(r, StatelessSessionDescriptorType.type, options);
      }

      public static StatelessSessionDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatelessSessionDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, StatelessSessionDescriptorType.type, options);
      }

      public static StatelessSessionDescriptorType parse(Node node) throws XmlException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      public static StatelessSessionDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(node, StatelessSessionDescriptorType.type, options);
      }

      /** @deprecated */
      public static StatelessSessionDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StatelessSessionDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StatelessSessionDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, StatelessSessionDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatelessSessionDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatelessSessionDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
