package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface LoadOnStartupType extends XmlAnySimpleType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LoadOnStartupType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("loadonstartuptype3427type");

   Object getObjectValue();

   void setObjectValue(Object var1);

   /** @deprecated */
   Object objectValue();

   /** @deprecated */
   void objectSet(Object var1);

   SchemaType instanceType();

   public static final class Factory {
      public static LoadOnStartupType newValue(Object obj) {
         return (LoadOnStartupType)LoadOnStartupType.type.newValue(obj);
      }

      public static LoadOnStartupType newInstance() {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().newInstance(LoadOnStartupType.type, (XmlOptions)null);
      }

      public static LoadOnStartupType newInstance(XmlOptions options) {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().newInstance(LoadOnStartupType.type, options);
      }

      public static LoadOnStartupType parse(java.lang.String xmlAsString) throws XmlException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoadOnStartupType.type, (XmlOptions)null);
      }

      public static LoadOnStartupType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LoadOnStartupType.type, options);
      }

      public static LoadOnStartupType parse(File file) throws XmlException, IOException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(file, LoadOnStartupType.type, (XmlOptions)null);
      }

      public static LoadOnStartupType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(file, LoadOnStartupType.type, options);
      }

      public static LoadOnStartupType parse(URL u) throws XmlException, IOException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(u, LoadOnStartupType.type, (XmlOptions)null);
      }

      public static LoadOnStartupType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(u, LoadOnStartupType.type, options);
      }

      public static LoadOnStartupType parse(InputStream is) throws XmlException, IOException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(is, LoadOnStartupType.type, (XmlOptions)null);
      }

      public static LoadOnStartupType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(is, LoadOnStartupType.type, options);
      }

      public static LoadOnStartupType parse(Reader r) throws XmlException, IOException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(r, LoadOnStartupType.type, (XmlOptions)null);
      }

      public static LoadOnStartupType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(r, LoadOnStartupType.type, options);
      }

      public static LoadOnStartupType parse(XMLStreamReader sr) throws XmlException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(sr, LoadOnStartupType.type, (XmlOptions)null);
      }

      public static LoadOnStartupType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(sr, LoadOnStartupType.type, options);
      }

      public static LoadOnStartupType parse(Node node) throws XmlException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(node, LoadOnStartupType.type, (XmlOptions)null);
      }

      public static LoadOnStartupType parse(Node node, XmlOptions options) throws XmlException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(node, LoadOnStartupType.type, options);
      }

      /** @deprecated */
      public static LoadOnStartupType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(xis, LoadOnStartupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LoadOnStartupType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LoadOnStartupType)XmlBeans.getContextTypeLoader().parse(xis, LoadOnStartupType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoadOnStartupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LoadOnStartupType.type, options);
      }

      private Factory() {
      }
   }
}
