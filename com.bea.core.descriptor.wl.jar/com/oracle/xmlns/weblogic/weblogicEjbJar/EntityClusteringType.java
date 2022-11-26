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

public interface EntityClusteringType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EntityClusteringType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("entityclusteringtype2856type");

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

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EntityClusteringType newInstance() {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().newInstance(EntityClusteringType.type, (XmlOptions)null);
      }

      public static EntityClusteringType newInstance(XmlOptions options) {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().newInstance(EntityClusteringType.type, options);
      }

      public static EntityClusteringType parse(String xmlAsString) throws XmlException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityClusteringType.type, (XmlOptions)null);
      }

      public static EntityClusteringType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EntityClusteringType.type, options);
      }

      public static EntityClusteringType parse(File file) throws XmlException, IOException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(file, EntityClusteringType.type, (XmlOptions)null);
      }

      public static EntityClusteringType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(file, EntityClusteringType.type, options);
      }

      public static EntityClusteringType parse(URL u) throws XmlException, IOException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(u, EntityClusteringType.type, (XmlOptions)null);
      }

      public static EntityClusteringType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(u, EntityClusteringType.type, options);
      }

      public static EntityClusteringType parse(InputStream is) throws XmlException, IOException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(is, EntityClusteringType.type, (XmlOptions)null);
      }

      public static EntityClusteringType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(is, EntityClusteringType.type, options);
      }

      public static EntityClusteringType parse(Reader r) throws XmlException, IOException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(r, EntityClusteringType.type, (XmlOptions)null);
      }

      public static EntityClusteringType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(r, EntityClusteringType.type, options);
      }

      public static EntityClusteringType parse(XMLStreamReader sr) throws XmlException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(sr, EntityClusteringType.type, (XmlOptions)null);
      }

      public static EntityClusteringType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(sr, EntityClusteringType.type, options);
      }

      public static EntityClusteringType parse(Node node) throws XmlException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(node, EntityClusteringType.type, (XmlOptions)null);
      }

      public static EntityClusteringType parse(Node node, XmlOptions options) throws XmlException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(node, EntityClusteringType.type, options);
      }

      /** @deprecated */
      public static EntityClusteringType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(xis, EntityClusteringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EntityClusteringType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EntityClusteringType)XmlBeans.getContextTypeLoader().parse(xis, EntityClusteringType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityClusteringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EntityClusteringType.type, options);
      }

      private Factory() {
      }
   }
}
