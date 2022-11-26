package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdIntegerType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MinThreadsConstraintType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MinThreadsConstraintType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("minthreadsconstrainttypec034type");

   XsdStringType getName();

   void setName(XsdStringType var1);

   XsdStringType addNewName();

   XsdIntegerType getCount();

   void setCount(XsdIntegerType var1);

   XsdIntegerType addNewCount();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MinThreadsConstraintType newInstance() {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().newInstance(MinThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MinThreadsConstraintType newInstance(XmlOptions options) {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().newInstance(MinThreadsConstraintType.type, options);
      }

      public static MinThreadsConstraintType parse(String xmlAsString) throws XmlException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MinThreadsConstraintType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MinThreadsConstraintType.type, options);
      }

      public static MinThreadsConstraintType parse(File file) throws XmlException, IOException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(file, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MinThreadsConstraintType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(file, MinThreadsConstraintType.type, options);
      }

      public static MinThreadsConstraintType parse(URL u) throws XmlException, IOException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(u, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MinThreadsConstraintType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(u, MinThreadsConstraintType.type, options);
      }

      public static MinThreadsConstraintType parse(InputStream is) throws XmlException, IOException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(is, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MinThreadsConstraintType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(is, MinThreadsConstraintType.type, options);
      }

      public static MinThreadsConstraintType parse(Reader r) throws XmlException, IOException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(r, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MinThreadsConstraintType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(r, MinThreadsConstraintType.type, options);
      }

      public static MinThreadsConstraintType parse(XMLStreamReader sr) throws XmlException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(sr, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MinThreadsConstraintType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(sr, MinThreadsConstraintType.type, options);
      }

      public static MinThreadsConstraintType parse(Node node) throws XmlException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(node, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MinThreadsConstraintType parse(Node node, XmlOptions options) throws XmlException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(node, MinThreadsConstraintType.type, options);
      }

      /** @deprecated */
      public static MinThreadsConstraintType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(xis, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MinThreadsConstraintType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MinThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(xis, MinThreadsConstraintType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MinThreadsConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MinThreadsConstraintType.type, options);
      }

      private Factory() {
      }
   }
}
