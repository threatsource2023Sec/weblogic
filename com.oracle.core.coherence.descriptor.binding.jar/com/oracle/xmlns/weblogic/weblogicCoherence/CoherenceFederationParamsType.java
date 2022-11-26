package com.oracle.xmlns.weblogic.weblogicCoherence;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CoherenceFederationParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CoherenceFederationParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("coherencefederationparamstype0604type");

   String getFederationTopology();

   XmlString xgetFederationTopology();

   boolean isSetFederationTopology();

   void setFederationTopology(String var1);

   void xsetFederationTopology(XmlString var1);

   void unsetFederationTopology();

   String[] getRemoteParticipantHostArray();

   String getRemoteParticipantHostArray(int var1);

   XmlString[] xgetRemoteParticipantHostArray();

   XmlString xgetRemoteParticipantHostArray(int var1);

   boolean isNilRemoteParticipantHostArray(int var1);

   int sizeOfRemoteParticipantHostArray();

   void setRemoteParticipantHostArray(String[] var1);

   void setRemoteParticipantHostArray(int var1, String var2);

   void xsetRemoteParticipantHostArray(XmlString[] var1);

   void xsetRemoteParticipantHostArray(int var1, XmlString var2);

   void setNilRemoteParticipantHostArray(int var1);

   void insertRemoteParticipantHost(int var1, String var2);

   void addRemoteParticipantHost(String var1);

   XmlString insertNewRemoteParticipantHost(int var1);

   XmlString addNewRemoteParticipantHost();

   void removeRemoteParticipantHost(int var1);

   String getRemoteCoherenceClusterName();

   XmlString xgetRemoteCoherenceClusterName();

   boolean isNilRemoteCoherenceClusterName();

   void setRemoteCoherenceClusterName(String var1);

   void xsetRemoteCoherenceClusterName(XmlString var1);

   void setNilRemoteCoherenceClusterName();

   XsdNonNegativeIntegerType getRemoteCoherenceClusterListenPort();

   boolean isNilRemoteCoherenceClusterListenPort();

   void setRemoteCoherenceClusterListenPort(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewRemoteCoherenceClusterListenPort();

   void setNilRemoteCoherenceClusterListenPort();

   public static final class Factory {
      public static CoherenceFederationParamsType newInstance() {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().newInstance(CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      public static CoherenceFederationParamsType newInstance(XmlOptions options) {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().newInstance(CoherenceFederationParamsType.type, options);
      }

      public static CoherenceFederationParamsType parse(String xmlAsString) throws XmlException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      public static CoherenceFederationParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CoherenceFederationParamsType.type, options);
      }

      public static CoherenceFederationParamsType parse(File file) throws XmlException, IOException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(file, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      public static CoherenceFederationParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(file, CoherenceFederationParamsType.type, options);
      }

      public static CoherenceFederationParamsType parse(URL u) throws XmlException, IOException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(u, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      public static CoherenceFederationParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(u, CoherenceFederationParamsType.type, options);
      }

      public static CoherenceFederationParamsType parse(InputStream is) throws XmlException, IOException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(is, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      public static CoherenceFederationParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(is, CoherenceFederationParamsType.type, options);
      }

      public static CoherenceFederationParamsType parse(Reader r) throws XmlException, IOException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(r, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      public static CoherenceFederationParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(r, CoherenceFederationParamsType.type, options);
      }

      public static CoherenceFederationParamsType parse(XMLStreamReader sr) throws XmlException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      public static CoherenceFederationParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(sr, CoherenceFederationParamsType.type, options);
      }

      public static CoherenceFederationParamsType parse(Node node) throws XmlException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(node, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      public static CoherenceFederationParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(node, CoherenceFederationParamsType.type, options);
      }

      /** @deprecated */
      public static CoherenceFederationParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CoherenceFederationParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CoherenceFederationParamsType)XmlBeans.getContextTypeLoader().parse(xis, CoherenceFederationParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceFederationParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CoherenceFederationParamsType.type, options);
      }

      private Factory() {
      }
   }
}
