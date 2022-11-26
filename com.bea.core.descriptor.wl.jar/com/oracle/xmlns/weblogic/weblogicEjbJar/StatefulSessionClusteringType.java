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

public interface StatefulSessionClusteringType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StatefulSessionClusteringType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("statefulsessionclusteringtype0ca4type");

   TrueFalseType getHomeIsClusterable();

   boolean isSetHomeIsClusterable();

   void setHomeIsClusterable(TrueFalseType var1);

   TrueFalseType addNewHomeIsClusterable();

   void unsetHomeIsClusterable();

   LoadAlgorithmType getHomeLoadAlgorithm();

   boolean isSetHomeLoadAlgorithm();

   void setHomeLoadAlgorithm(LoadAlgorithmType var1);

   LoadAlgorithmType addNewHomeLoadAlgorithm();

   void unsetHomeLoadAlgorithm();

   CallRouterClassNameType getHomeCallRouterClassName();

   boolean isSetHomeCallRouterClassName();

   void setHomeCallRouterClassName(CallRouterClassNameType var1);

   CallRouterClassNameType addNewHomeCallRouterClassName();

   void unsetHomeCallRouterClassName();

   TrueFalseType getUseServersideStubs();

   boolean isSetUseServersideStubs();

   void setUseServersideStubs(TrueFalseType var1);

   TrueFalseType addNewUseServersideStubs();

   void unsetUseServersideStubs();

   ReplicationTypeType getReplicationType();

   boolean isSetReplicationType();

   void setReplicationType(ReplicationTypeType var1);

   ReplicationTypeType addNewReplicationType();

   void unsetReplicationType();

   TrueFalseType getPassivateDuringReplication();

   boolean isSetPassivateDuringReplication();

   void setPassivateDuringReplication(TrueFalseType var1);

   TrueFalseType addNewPassivateDuringReplication();

   void unsetPassivateDuringReplication();

   TrueFalseType getCalculateDeltaUsingReflection();

   boolean isSetCalculateDeltaUsingReflection();

   void setCalculateDeltaUsingReflection(TrueFalseType var1);

   TrueFalseType addNewCalculateDeltaUsingReflection();

   void unsetCalculateDeltaUsingReflection();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static StatefulSessionClusteringType newInstance() {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().newInstance(StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      public static StatefulSessionClusteringType newInstance(XmlOptions options) {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().newInstance(StatefulSessionClusteringType.type, options);
      }

      public static StatefulSessionClusteringType parse(String xmlAsString) throws XmlException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      public static StatefulSessionClusteringType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatefulSessionClusteringType.type, options);
      }

      public static StatefulSessionClusteringType parse(File file) throws XmlException, IOException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(file, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      public static StatefulSessionClusteringType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(file, StatefulSessionClusteringType.type, options);
      }

      public static StatefulSessionClusteringType parse(URL u) throws XmlException, IOException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(u, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      public static StatefulSessionClusteringType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(u, StatefulSessionClusteringType.type, options);
      }

      public static StatefulSessionClusteringType parse(InputStream is) throws XmlException, IOException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(is, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      public static StatefulSessionClusteringType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(is, StatefulSessionClusteringType.type, options);
      }

      public static StatefulSessionClusteringType parse(Reader r) throws XmlException, IOException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(r, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      public static StatefulSessionClusteringType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(r, StatefulSessionClusteringType.type, options);
      }

      public static StatefulSessionClusteringType parse(XMLStreamReader sr) throws XmlException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(sr, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      public static StatefulSessionClusteringType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(sr, StatefulSessionClusteringType.type, options);
      }

      public static StatefulSessionClusteringType parse(Node node) throws XmlException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(node, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      public static StatefulSessionClusteringType parse(Node node, XmlOptions options) throws XmlException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(node, StatefulSessionClusteringType.type, options);
      }

      /** @deprecated */
      public static StatefulSessionClusteringType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(xis, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StatefulSessionClusteringType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StatefulSessionClusteringType)XmlBeans.getContextTypeLoader().parse(xis, StatefulSessionClusteringType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatefulSessionClusteringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatefulSessionClusteringType.type, options);
      }

      private Factory() {
      }
   }
}
