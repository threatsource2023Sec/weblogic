package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

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

public interface EjbQlQueryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbQlQueryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("ejbqlquerytyped335type");

   WeblogicQlType getWeblogicQl();

   boolean isSetWeblogicQl();

   void setWeblogicQl(WeblogicQlType var1);

   WeblogicQlType addNewWeblogicQl();

   void unsetWeblogicQl();

   GroupNameType getGroupName();

   boolean isSetGroupName();

   void setGroupName(GroupNameType var1);

   GroupNameType addNewGroupName();

   void unsetGroupName();

   CachingNameType getCachingName();

   boolean isSetCachingName();

   void setCachingName(CachingNameType var1);

   CachingNameType addNewCachingName();

   void unsetCachingName();

   public static final class Factory {
      public static EjbQlQueryType newInstance() {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().newInstance(EjbQlQueryType.type, (XmlOptions)null);
      }

      public static EjbQlQueryType newInstance(XmlOptions options) {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().newInstance(EjbQlQueryType.type, options);
      }

      public static EjbQlQueryType parse(String xmlAsString) throws XmlException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbQlQueryType.type, (XmlOptions)null);
      }

      public static EjbQlQueryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbQlQueryType.type, options);
      }

      public static EjbQlQueryType parse(File file) throws XmlException, IOException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(file, EjbQlQueryType.type, (XmlOptions)null);
      }

      public static EjbQlQueryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(file, EjbQlQueryType.type, options);
      }

      public static EjbQlQueryType parse(URL u) throws XmlException, IOException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(u, EjbQlQueryType.type, (XmlOptions)null);
      }

      public static EjbQlQueryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(u, EjbQlQueryType.type, options);
      }

      public static EjbQlQueryType parse(InputStream is) throws XmlException, IOException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(is, EjbQlQueryType.type, (XmlOptions)null);
      }

      public static EjbQlQueryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(is, EjbQlQueryType.type, options);
      }

      public static EjbQlQueryType parse(Reader r) throws XmlException, IOException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(r, EjbQlQueryType.type, (XmlOptions)null);
      }

      public static EjbQlQueryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(r, EjbQlQueryType.type, options);
      }

      public static EjbQlQueryType parse(XMLStreamReader sr) throws XmlException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(sr, EjbQlQueryType.type, (XmlOptions)null);
      }

      public static EjbQlQueryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(sr, EjbQlQueryType.type, options);
      }

      public static EjbQlQueryType parse(Node node) throws XmlException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(node, EjbQlQueryType.type, (XmlOptions)null);
      }

      public static EjbQlQueryType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(node, EjbQlQueryType.type, options);
      }

      /** @deprecated */
      public static EjbQlQueryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(xis, EjbQlQueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbQlQueryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbQlQueryType)XmlBeans.getContextTypeLoader().parse(xis, EjbQlQueryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbQlQueryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbQlQueryType.type, options);
      }

      private Factory() {
      }
   }
}
