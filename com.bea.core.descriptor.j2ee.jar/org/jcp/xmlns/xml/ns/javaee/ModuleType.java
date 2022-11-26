package org.jcp.xmlns.xml.ns.javaee;

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

public interface ModuleType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ModuleType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("moduletype5b5ftype");

   PathType getConnector();

   boolean isSetConnector();

   void setConnector(PathType var1);

   PathType addNewConnector();

   void unsetConnector();

   PathType getEjb();

   boolean isSetEjb();

   void setEjb(PathType var1);

   PathType addNewEjb();

   void unsetEjb();

   PathType getJava();

   boolean isSetJava();

   void setJava(PathType var1);

   PathType addNewJava();

   void unsetJava();

   WebType getWeb();

   boolean isSetWeb();

   void setWeb(WebType var1);

   WebType addNewWeb();

   void unsetWeb();

   PathType getAltDd();

   boolean isSetAltDd();

   void setAltDd(PathType var1);

   PathType addNewAltDd();

   void unsetAltDd();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ModuleType newInstance() {
         return (ModuleType)XmlBeans.getContextTypeLoader().newInstance(ModuleType.type, (XmlOptions)null);
      }

      public static ModuleType newInstance(XmlOptions options) {
         return (ModuleType)XmlBeans.getContextTypeLoader().newInstance(ModuleType.type, options);
      }

      public static ModuleType parse(java.lang.String xmlAsString) throws XmlException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleType.type, (XmlOptions)null);
      }

      public static ModuleType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ModuleType.type, options);
      }

      public static ModuleType parse(File file) throws XmlException, IOException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(file, ModuleType.type, (XmlOptions)null);
      }

      public static ModuleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(file, ModuleType.type, options);
      }

      public static ModuleType parse(URL u) throws XmlException, IOException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(u, ModuleType.type, (XmlOptions)null);
      }

      public static ModuleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(u, ModuleType.type, options);
      }

      public static ModuleType parse(InputStream is) throws XmlException, IOException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(is, ModuleType.type, (XmlOptions)null);
      }

      public static ModuleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(is, ModuleType.type, options);
      }

      public static ModuleType parse(Reader r) throws XmlException, IOException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(r, ModuleType.type, (XmlOptions)null);
      }

      public static ModuleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(r, ModuleType.type, options);
      }

      public static ModuleType parse(XMLStreamReader sr) throws XmlException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(sr, ModuleType.type, (XmlOptions)null);
      }

      public static ModuleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(sr, ModuleType.type, options);
      }

      public static ModuleType parse(Node node) throws XmlException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(node, ModuleType.type, (XmlOptions)null);
      }

      public static ModuleType parse(Node node, XmlOptions options) throws XmlException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(node, ModuleType.type, options);
      }

      /** @deprecated */
      public static ModuleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(xis, ModuleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ModuleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ModuleType)XmlBeans.getContextTypeLoader().parse(xis, ModuleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ModuleType.type, options);
      }

      private Factory() {
      }
   }
}
