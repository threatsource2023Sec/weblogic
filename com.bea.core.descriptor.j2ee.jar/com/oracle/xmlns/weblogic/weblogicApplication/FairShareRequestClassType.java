package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface FairShareRequestClassType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FairShareRequestClassType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("fairsharerequestclasstypedbbetype");

   XsdStringType getName();

   void setName(XsdStringType var1);

   XsdStringType addNewName();

   XsdIntegerType getFairShare();

   void setFairShare(XsdIntegerType var1);

   XsdIntegerType addNewFairShare();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FairShareRequestClassType newInstance() {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().newInstance(FairShareRequestClassType.type, (XmlOptions)null);
      }

      public static FairShareRequestClassType newInstance(XmlOptions options) {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().newInstance(FairShareRequestClassType.type, options);
      }

      public static FairShareRequestClassType parse(String xmlAsString) throws XmlException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FairShareRequestClassType.type, (XmlOptions)null);
      }

      public static FairShareRequestClassType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FairShareRequestClassType.type, options);
      }

      public static FairShareRequestClassType parse(File file) throws XmlException, IOException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(file, FairShareRequestClassType.type, (XmlOptions)null);
      }

      public static FairShareRequestClassType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(file, FairShareRequestClassType.type, options);
      }

      public static FairShareRequestClassType parse(URL u) throws XmlException, IOException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(u, FairShareRequestClassType.type, (XmlOptions)null);
      }

      public static FairShareRequestClassType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(u, FairShareRequestClassType.type, options);
      }

      public static FairShareRequestClassType parse(InputStream is) throws XmlException, IOException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(is, FairShareRequestClassType.type, (XmlOptions)null);
      }

      public static FairShareRequestClassType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(is, FairShareRequestClassType.type, options);
      }

      public static FairShareRequestClassType parse(Reader r) throws XmlException, IOException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(r, FairShareRequestClassType.type, (XmlOptions)null);
      }

      public static FairShareRequestClassType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(r, FairShareRequestClassType.type, options);
      }

      public static FairShareRequestClassType parse(XMLStreamReader sr) throws XmlException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(sr, FairShareRequestClassType.type, (XmlOptions)null);
      }

      public static FairShareRequestClassType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(sr, FairShareRequestClassType.type, options);
      }

      public static FairShareRequestClassType parse(Node node) throws XmlException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(node, FairShareRequestClassType.type, (XmlOptions)null);
      }

      public static FairShareRequestClassType parse(Node node, XmlOptions options) throws XmlException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(node, FairShareRequestClassType.type, options);
      }

      /** @deprecated */
      public static FairShareRequestClassType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(xis, FairShareRequestClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FairShareRequestClassType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FairShareRequestClassType)XmlBeans.getContextTypeLoader().parse(xis, FairShareRequestClassType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FairShareRequestClassType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FairShareRequestClassType.type, options);
      }

      private Factory() {
      }
   }
}
