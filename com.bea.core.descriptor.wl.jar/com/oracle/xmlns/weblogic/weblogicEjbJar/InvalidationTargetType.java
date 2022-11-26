package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.EjbNameType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface InvalidationTargetType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InvalidationTargetType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("invalidationtargettype4324type");

   EjbNameType getEjbName();

   void setEjbName(EjbNameType var1);

   EjbNameType addNewEjbName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InvalidationTargetType newInstance() {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().newInstance(InvalidationTargetType.type, (XmlOptions)null);
      }

      public static InvalidationTargetType newInstance(XmlOptions options) {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().newInstance(InvalidationTargetType.type, options);
      }

      public static InvalidationTargetType parse(String xmlAsString) throws XmlException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InvalidationTargetType.type, (XmlOptions)null);
      }

      public static InvalidationTargetType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InvalidationTargetType.type, options);
      }

      public static InvalidationTargetType parse(File file) throws XmlException, IOException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(file, InvalidationTargetType.type, (XmlOptions)null);
      }

      public static InvalidationTargetType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(file, InvalidationTargetType.type, options);
      }

      public static InvalidationTargetType parse(URL u) throws XmlException, IOException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(u, InvalidationTargetType.type, (XmlOptions)null);
      }

      public static InvalidationTargetType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(u, InvalidationTargetType.type, options);
      }

      public static InvalidationTargetType parse(InputStream is) throws XmlException, IOException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(is, InvalidationTargetType.type, (XmlOptions)null);
      }

      public static InvalidationTargetType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(is, InvalidationTargetType.type, options);
      }

      public static InvalidationTargetType parse(Reader r) throws XmlException, IOException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(r, InvalidationTargetType.type, (XmlOptions)null);
      }

      public static InvalidationTargetType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(r, InvalidationTargetType.type, options);
      }

      public static InvalidationTargetType parse(XMLStreamReader sr) throws XmlException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(sr, InvalidationTargetType.type, (XmlOptions)null);
      }

      public static InvalidationTargetType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(sr, InvalidationTargetType.type, options);
      }

      public static InvalidationTargetType parse(Node node) throws XmlException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(node, InvalidationTargetType.type, (XmlOptions)null);
      }

      public static InvalidationTargetType parse(Node node, XmlOptions options) throws XmlException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(node, InvalidationTargetType.type, options);
      }

      /** @deprecated */
      public static InvalidationTargetType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(xis, InvalidationTargetType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InvalidationTargetType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InvalidationTargetType)XmlBeans.getContextTypeLoader().parse(xis, InvalidationTargetType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InvalidationTargetType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InvalidationTargetType.type, options);
      }

      private Factory() {
      }
   }
}
