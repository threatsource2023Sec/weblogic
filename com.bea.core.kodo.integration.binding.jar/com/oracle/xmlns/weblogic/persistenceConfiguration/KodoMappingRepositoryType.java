package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface KodoMappingRepositoryType extends MetaDataRepositoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(KodoMappingRepositoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("kodomappingrepositorytype8345type");

   int getResolve();

   XmlInt xgetResolve();

   boolean isSetResolve();

   void setResolve(int var1);

   void xsetResolve(XmlInt var1);

   void unsetResolve();

   int getValidate();

   XmlInt xgetValidate();

   boolean isSetValidate();

   void setValidate(int var1);

   void xsetValidate(XmlInt var1);

   void unsetValidate();

   int getSourceMode();

   XmlInt xgetSourceMode();

   boolean isSetSourceMode();

   void setSourceMode(int var1);

   void xsetSourceMode(XmlInt var1);

   void unsetSourceMode();

   public static final class Factory {
      public static KodoMappingRepositoryType newInstance() {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().newInstance(KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      public static KodoMappingRepositoryType newInstance(XmlOptions options) {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().newInstance(KodoMappingRepositoryType.type, options);
      }

      public static KodoMappingRepositoryType parse(String xmlAsString) throws XmlException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      public static KodoMappingRepositoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, KodoMappingRepositoryType.type, options);
      }

      public static KodoMappingRepositoryType parse(File file) throws XmlException, IOException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(file, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      public static KodoMappingRepositoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(file, KodoMappingRepositoryType.type, options);
      }

      public static KodoMappingRepositoryType parse(URL u) throws XmlException, IOException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(u, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      public static KodoMappingRepositoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(u, KodoMappingRepositoryType.type, options);
      }

      public static KodoMappingRepositoryType parse(InputStream is) throws XmlException, IOException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(is, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      public static KodoMappingRepositoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(is, KodoMappingRepositoryType.type, options);
      }

      public static KodoMappingRepositoryType parse(Reader r) throws XmlException, IOException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(r, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      public static KodoMappingRepositoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(r, KodoMappingRepositoryType.type, options);
      }

      public static KodoMappingRepositoryType parse(XMLStreamReader sr) throws XmlException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(sr, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      public static KodoMappingRepositoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(sr, KodoMappingRepositoryType.type, options);
      }

      public static KodoMappingRepositoryType parse(Node node) throws XmlException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(node, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      public static KodoMappingRepositoryType parse(Node node, XmlOptions options) throws XmlException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(node, KodoMappingRepositoryType.type, options);
      }

      /** @deprecated */
      public static KodoMappingRepositoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(xis, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static KodoMappingRepositoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (KodoMappingRepositoryType)XmlBeans.getContextTypeLoader().parse(xis, KodoMappingRepositoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoMappingRepositoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, KodoMappingRepositoryType.type, options);
      }

      private Factory() {
      }
   }
}
