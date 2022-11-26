package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface MaxThreadsConstraintType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MaxThreadsConstraintType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("maxthreadsconstrainttype1f80type");

   XsdStringType getName();

   void setName(XsdStringType var1);

   XsdStringType addNewName();

   XsdIntegerType getCount();

   boolean isSetCount();

   void setCount(XsdIntegerType var1);

   XsdIntegerType addNewCount();

   void unsetCount();

   XsdStringType getPoolName();

   boolean isSetPoolName();

   void setPoolName(XsdStringType var1);

   XsdStringType addNewPoolName();

   void unsetPoolName();

   XsdIntegerType getQueueSize();

   boolean isSetQueueSize();

   void setQueueSize(XsdIntegerType var1);

   XsdIntegerType addNewQueueSize();

   void unsetQueueSize();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MaxThreadsConstraintType newInstance() {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().newInstance(MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MaxThreadsConstraintType newInstance(XmlOptions options) {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().newInstance(MaxThreadsConstraintType.type, options);
      }

      public static MaxThreadsConstraintType parse(String xmlAsString) throws XmlException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MaxThreadsConstraintType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MaxThreadsConstraintType.type, options);
      }

      public static MaxThreadsConstraintType parse(File file) throws XmlException, IOException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(file, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MaxThreadsConstraintType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(file, MaxThreadsConstraintType.type, options);
      }

      public static MaxThreadsConstraintType parse(URL u) throws XmlException, IOException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(u, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MaxThreadsConstraintType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(u, MaxThreadsConstraintType.type, options);
      }

      public static MaxThreadsConstraintType parse(InputStream is) throws XmlException, IOException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(is, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MaxThreadsConstraintType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(is, MaxThreadsConstraintType.type, options);
      }

      public static MaxThreadsConstraintType parse(Reader r) throws XmlException, IOException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(r, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MaxThreadsConstraintType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(r, MaxThreadsConstraintType.type, options);
      }

      public static MaxThreadsConstraintType parse(XMLStreamReader sr) throws XmlException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(sr, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MaxThreadsConstraintType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(sr, MaxThreadsConstraintType.type, options);
      }

      public static MaxThreadsConstraintType parse(Node node) throws XmlException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(node, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      public static MaxThreadsConstraintType parse(Node node, XmlOptions options) throws XmlException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(node, MaxThreadsConstraintType.type, options);
      }

      /** @deprecated */
      public static MaxThreadsConstraintType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(xis, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MaxThreadsConstraintType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MaxThreadsConstraintType)XmlBeans.getContextTypeLoader().parse(xis, MaxThreadsConstraintType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MaxThreadsConstraintType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MaxThreadsConstraintType.type, options);
      }

      private Factory() {
      }
   }
}
