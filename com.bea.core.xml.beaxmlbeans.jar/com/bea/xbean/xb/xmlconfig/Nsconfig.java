package com.bea.xbean.xb.xmlconfig;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface Nsconfig extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Nsconfig.class.getClassLoader(), "schemacom_bea_xml.system.sXMLCONFIG").resolveHandle("nsconfigaebatype");

   String getPackage();

   XmlString xgetPackage();

   boolean isSetPackage();

   void setPackage(String var1);

   void xsetPackage(XmlString var1);

   void unsetPackage();

   String getPrefix();

   XmlString xgetPrefix();

   boolean isSetPrefix();

   void setPrefix(String var1);

   void xsetPrefix(XmlString var1);

   void unsetPrefix();

   String getSuffix();

   XmlString xgetSuffix();

   boolean isSetSuffix();

   void setSuffix(String var1);

   void xsetSuffix(XmlString var1);

   void unsetSuffix();

   Object getUri();

   NamespaceList xgetUri();

   boolean isSetUri();

   void setUri(Object var1);

   void xsetUri(NamespaceList var1);

   void unsetUri();

   List getUriprefix();

   NamespacePrefixList xgetUriprefix();

   boolean isSetUriprefix();

   void setUriprefix(List var1);

   void xsetUriprefix(NamespacePrefixList var1);

   void unsetUriprefix();

   public static final class Factory {
      public static Nsconfig newInstance() {
         return (Nsconfig)XmlBeans.getContextTypeLoader().newInstance(Nsconfig.type, (XmlOptions)null);
      }

      public static Nsconfig newInstance(XmlOptions options) {
         return (Nsconfig)XmlBeans.getContextTypeLoader().newInstance(Nsconfig.type, options);
      }

      public static Nsconfig parse(String xmlAsString) throws XmlException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Nsconfig.type, (XmlOptions)null);
      }

      public static Nsconfig parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse(xmlAsString, Nsconfig.type, options);
      }

      public static Nsconfig parse(File file) throws XmlException, IOException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse((File)file, Nsconfig.type, (XmlOptions)null);
      }

      public static Nsconfig parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse(file, Nsconfig.type, options);
      }

      public static Nsconfig parse(URL u) throws XmlException, IOException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse((URL)u, Nsconfig.type, (XmlOptions)null);
      }

      public static Nsconfig parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse(u, Nsconfig.type, options);
      }

      public static Nsconfig parse(InputStream is) throws XmlException, IOException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse((InputStream)is, Nsconfig.type, (XmlOptions)null);
      }

      public static Nsconfig parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse(is, Nsconfig.type, options);
      }

      public static Nsconfig parse(Reader r) throws XmlException, IOException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse((Reader)r, Nsconfig.type, (XmlOptions)null);
      }

      public static Nsconfig parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse(r, Nsconfig.type, options);
      }

      public static Nsconfig parse(XMLStreamReader sr) throws XmlException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Nsconfig.type, (XmlOptions)null);
      }

      public static Nsconfig parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse(sr, Nsconfig.type, options);
      }

      public static Nsconfig parse(Node node) throws XmlException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse((Node)node, Nsconfig.type, (XmlOptions)null);
      }

      public static Nsconfig parse(Node node, XmlOptions options) throws XmlException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse(node, Nsconfig.type, options);
      }

      /** @deprecated */
      public static Nsconfig parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Nsconfig.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Nsconfig parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Nsconfig)XmlBeans.getContextTypeLoader().parse(xis, Nsconfig.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Nsconfig.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Nsconfig.type, options);
      }

      private Factory() {
      }
   }
}
