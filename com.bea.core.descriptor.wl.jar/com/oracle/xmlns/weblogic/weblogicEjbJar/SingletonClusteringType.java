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

public interface SingletonClusteringType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SingletonClusteringType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("singletonclusteringtype86e0type");

   TrueFalseType getUseServersideStubs();

   boolean isSetUseServersideStubs();

   void setUseServersideStubs(TrueFalseType var1);

   TrueFalseType addNewUseServersideStubs();

   void unsetUseServersideStubs();

   TrueFalseType getSingletonBeanIsClusterable();

   boolean isSetSingletonBeanIsClusterable();

   void setSingletonBeanIsClusterable(TrueFalseType var1);

   TrueFalseType addNewSingletonBeanIsClusterable();

   void unsetSingletonBeanIsClusterable();

   LoadAlgorithmType getSingletonBeanLoadAlgorithm();

   boolean isSetSingletonBeanLoadAlgorithm();

   void setSingletonBeanLoadAlgorithm(LoadAlgorithmType var1);

   LoadAlgorithmType addNewSingletonBeanLoadAlgorithm();

   void unsetSingletonBeanLoadAlgorithm();

   CallRouterClassNameType getSingletonBeanCallRouterClassName();

   boolean isSetSingletonBeanCallRouterClassName();

   void setSingletonBeanCallRouterClassName(CallRouterClassNameType var1);

   CallRouterClassNameType addNewSingletonBeanCallRouterClassName();

   void unsetSingletonBeanCallRouterClassName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SingletonClusteringType newInstance() {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().newInstance(SingletonClusteringType.type, (XmlOptions)null);
      }

      public static SingletonClusteringType newInstance(XmlOptions options) {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().newInstance(SingletonClusteringType.type, options);
      }

      public static SingletonClusteringType parse(String xmlAsString) throws XmlException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingletonClusteringType.type, (XmlOptions)null);
      }

      public static SingletonClusteringType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SingletonClusteringType.type, options);
      }

      public static SingletonClusteringType parse(File file) throws XmlException, IOException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(file, SingletonClusteringType.type, (XmlOptions)null);
      }

      public static SingletonClusteringType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(file, SingletonClusteringType.type, options);
      }

      public static SingletonClusteringType parse(URL u) throws XmlException, IOException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(u, SingletonClusteringType.type, (XmlOptions)null);
      }

      public static SingletonClusteringType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(u, SingletonClusteringType.type, options);
      }

      public static SingletonClusteringType parse(InputStream is) throws XmlException, IOException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(is, SingletonClusteringType.type, (XmlOptions)null);
      }

      public static SingletonClusteringType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(is, SingletonClusteringType.type, options);
      }

      public static SingletonClusteringType parse(Reader r) throws XmlException, IOException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(r, SingletonClusteringType.type, (XmlOptions)null);
      }

      public static SingletonClusteringType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(r, SingletonClusteringType.type, options);
      }

      public static SingletonClusteringType parse(XMLStreamReader sr) throws XmlException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(sr, SingletonClusteringType.type, (XmlOptions)null);
      }

      public static SingletonClusteringType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(sr, SingletonClusteringType.type, options);
      }

      public static SingletonClusteringType parse(Node node) throws XmlException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(node, SingletonClusteringType.type, (XmlOptions)null);
      }

      public static SingletonClusteringType parse(Node node, XmlOptions options) throws XmlException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(node, SingletonClusteringType.type, options);
      }

      /** @deprecated */
      public static SingletonClusteringType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(xis, SingletonClusteringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SingletonClusteringType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SingletonClusteringType)XmlBeans.getContextTypeLoader().parse(xis, SingletonClusteringType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingletonClusteringType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SingletonClusteringType.type, options);
      }

      private Factory() {
      }
   }
}
