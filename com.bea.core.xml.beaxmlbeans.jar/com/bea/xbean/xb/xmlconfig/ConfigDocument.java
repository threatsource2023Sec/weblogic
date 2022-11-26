package com.bea.xbean.xb.xmlconfig;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface ConfigDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLCONFIG").resolveHandle("config4185doctype");

   Config getConfig();

   void setConfig(Config var1);

   Config addNewConfig();

   public static final class Factory {
      public static ConfigDocument newInstance() {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().newInstance(ConfigDocument.type, (XmlOptions)null);
      }

      public static ConfigDocument newInstance(XmlOptions options) {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().newInstance(ConfigDocument.type, options);
      }

      public static ConfigDocument parse(String xmlAsString) throws XmlException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ConfigDocument.type, (XmlOptions)null);
      }

      public static ConfigDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigDocument.type, options);
      }

      public static ConfigDocument parse(File file) throws XmlException, IOException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse((File)file, ConfigDocument.type, (XmlOptions)null);
      }

      public static ConfigDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse(file, ConfigDocument.type, options);
      }

      public static ConfigDocument parse(URL u) throws XmlException, IOException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse((URL)u, ConfigDocument.type, (XmlOptions)null);
      }

      public static ConfigDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse(u, ConfigDocument.type, options);
      }

      public static ConfigDocument parse(InputStream is) throws XmlException, IOException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, ConfigDocument.type, (XmlOptions)null);
      }

      public static ConfigDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse(is, ConfigDocument.type, options);
      }

      public static ConfigDocument parse(Reader r) throws XmlException, IOException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, ConfigDocument.type, (XmlOptions)null);
      }

      public static ConfigDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse(r, ConfigDocument.type, options);
      }

      public static ConfigDocument parse(XMLStreamReader sr) throws XmlException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ConfigDocument.type, (XmlOptions)null);
      }

      public static ConfigDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse(sr, ConfigDocument.type, options);
      }

      public static ConfigDocument parse(Node node) throws XmlException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse((Node)node, ConfigDocument.type, (XmlOptions)null);
      }

      public static ConfigDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse(node, ConfigDocument.type, options);
      }

      /** @deprecated */
      public static ConfigDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ConfigDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigDocument)XmlBeans.getContextTypeLoader().parse(xis, ConfigDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Config extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Config.class.getClassLoader(), "schemacom_bea_xml.system.sXMLCONFIG").resolveHandle("configf467elemtype");

      Nsconfig[] getNamespaceArray();

      Nsconfig getNamespaceArray(int var1);

      int sizeOfNamespaceArray();

      void setNamespaceArray(Nsconfig[] var1);

      void setNamespaceArray(int var1, Nsconfig var2);

      Nsconfig insertNewNamespace(int var1);

      Nsconfig addNewNamespace();

      void removeNamespace(int var1);

      Qnameconfig[] getQnameArray();

      Qnameconfig getQnameArray(int var1);

      int sizeOfQnameArray();

      void setQnameArray(Qnameconfig[] var1);

      void setQnameArray(int var1, Qnameconfig var2);

      Qnameconfig insertNewQname(int var1);

      Qnameconfig addNewQname();

      void removeQname(int var1);

      Extensionconfig[] getExtensionArray();

      Extensionconfig getExtensionArray(int var1);

      int sizeOfExtensionArray();

      void setExtensionArray(Extensionconfig[] var1);

      void setExtensionArray(int var1, Extensionconfig var2);

      Extensionconfig insertNewExtension(int var1);

      Extensionconfig addNewExtension();

      void removeExtension(int var1);

      Usertypeconfig[] getUsertypeArray();

      Usertypeconfig getUsertypeArray(int var1);

      int sizeOfUsertypeArray();

      void setUsertypeArray(Usertypeconfig[] var1);

      void setUsertypeArray(int var1, Usertypeconfig var2);

      Usertypeconfig insertNewUsertype(int var1);

      Usertypeconfig addNewUsertype();

      void removeUsertype(int var1);

      public static final class Factory {
         public static Config newInstance() {
            return (Config)XmlBeans.getContextTypeLoader().newInstance(ConfigDocument.Config.type, (XmlOptions)null);
         }

         public static Config newInstance(XmlOptions options) {
            return (Config)XmlBeans.getContextTypeLoader().newInstance(ConfigDocument.Config.type, options);
         }

         private Factory() {
         }
      }
   }
}
