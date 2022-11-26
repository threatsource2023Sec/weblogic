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

public interface StatelessClusteringType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StatelessClusteringType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("statelessclusteringtype7521type");

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

   TrueFalseType getStatelessBeanIsClusterable();

   boolean isSetStatelessBeanIsClusterable();

   void setStatelessBeanIsClusterable(TrueFalseType var1);

   TrueFalseType addNewStatelessBeanIsClusterable();

   void unsetStatelessBeanIsClusterable();

   LoadAlgorithmType getStatelessBeanLoadAlgorithm();

   boolean isSetStatelessBeanLoadAlgorithm();

   void setStatelessBeanLoadAlgorithm(LoadAlgorithmType var1);

   LoadAlgorithmType addNewStatelessBeanLoadAlgorithm();

   void unsetStatelessBeanLoadAlgorithm();

   CallRouterClassNameType getStatelessBeanCallRouterClassName();

   boolean isSetStatelessBeanCallRouterClassName();

   void setStatelessBeanCallRouterClassName(CallRouterClassNameType var1);

   CallRouterClassNameType addNewStatelessBeanCallRouterClassName();

   void unsetStatelessBeanCallRouterClassName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static StatelessClusteringType newInstance() {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().newInstance(StatelessClusteringType.type, (XmlOptions)null);
      }

      public static StatelessClusteringType newInstance(XmlOptions options) {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().newInstance(StatelessClusteringType.type, options);
      }

      public static StatelessClusteringType parse(String xmlAsString) throws XmlException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatelessClusteringType.type, (XmlOptions)null);
      }

      public static StatelessClusteringType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatelessClusteringType.type, options);
      }

      public static StatelessClusteringType parse(File file) throws XmlException, IOException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(file, StatelessClusteringType.type, (XmlOptions)null);
      }

      public static StatelessClusteringType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(file, StatelessClusteringType.type, options);
      }

      public static StatelessClusteringType parse(URL u) throws XmlException, IOException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(u, StatelessClusteringType.type, (XmlOptions)null);
      }

      public static StatelessClusteringType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(u, StatelessClusteringType.type, options);
      }

      public static StatelessClusteringType parse(InputStream is) throws XmlException, IOException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(is, StatelessClusteringType.type, (XmlOptions)null);
      }

      public static StatelessClusteringType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(is, StatelessClusteringType.type, options);
      }

      public static StatelessClusteringType parse(Reader r) throws XmlException, IOException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(r, StatelessClusteringType.type, (XmlOptions)null);
      }

      public static StatelessClusteringType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(r, StatelessClusteringType.type, options);
      }

      public static StatelessClusteringType parse(XMLStreamReader sr) throws XmlException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(sr, StatelessClusteringType.type, (XmlOptions)null);
      }

      public static StatelessClusteringType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(sr, StatelessClusteringType.type, options);
      }

      public static StatelessClusteringType parse(Node node) throws XmlException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(node, StatelessClusteringType.type, (XmlOptions)null);
      }

      public static StatelessClusteringType parse(Node node, XmlOptions options) throws XmlException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(node, StatelessClusteringType.type, options);
      }

      /** @deprecated */
      public static StatelessClusteringType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(xis, StatelessClusteringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StatelessClusteringType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StatelessClusteringType)XmlBeans.getContextTypeLoader().parse(xis, StatelessClusteringType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatelessClusteringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatelessClusteringType.type, options);
      }

      private Factory() {
      }
   }
}
