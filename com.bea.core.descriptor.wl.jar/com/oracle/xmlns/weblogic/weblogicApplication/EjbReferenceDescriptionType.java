package com.oracle.xmlns.weblogic.weblogicApplication;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.EjbRefNameType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface EjbReferenceDescriptionType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EjbReferenceDescriptionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("ejbreferencedescriptiontypec9b3type");

   EjbRefNameType getEjbRefName();

   void setEjbRefName(EjbRefNameType var1);

   EjbRefNameType addNewEjbRefName();

   JndiNameType getJndiName();

   void setJndiName(JndiNameType var1);

   JndiNameType addNewJndiName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static EjbReferenceDescriptionType newInstance() {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static EjbReferenceDescriptionType newInstance(XmlOptions options) {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().newInstance(EjbReferenceDescriptionType.type, options);
      }

      public static EjbReferenceDescriptionType parse(String xmlAsString) throws XmlException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static EjbReferenceDescriptionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EjbReferenceDescriptionType.type, options);
      }

      public static EjbReferenceDescriptionType parse(File file) throws XmlException, IOException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static EjbReferenceDescriptionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(file, EjbReferenceDescriptionType.type, options);
      }

      public static EjbReferenceDescriptionType parse(URL u) throws XmlException, IOException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static EjbReferenceDescriptionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(u, EjbReferenceDescriptionType.type, options);
      }

      public static EjbReferenceDescriptionType parse(InputStream is) throws XmlException, IOException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static EjbReferenceDescriptionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(is, EjbReferenceDescriptionType.type, options);
      }

      public static EjbReferenceDescriptionType parse(Reader r) throws XmlException, IOException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static EjbReferenceDescriptionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(r, EjbReferenceDescriptionType.type, options);
      }

      public static EjbReferenceDescriptionType parse(XMLStreamReader sr) throws XmlException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static EjbReferenceDescriptionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(sr, EjbReferenceDescriptionType.type, options);
      }

      public static EjbReferenceDescriptionType parse(Node node) throws XmlException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      public static EjbReferenceDescriptionType parse(Node node, XmlOptions options) throws XmlException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(node, EjbReferenceDescriptionType.type, options);
      }

      /** @deprecated */
      public static EjbReferenceDescriptionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EjbReferenceDescriptionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EjbReferenceDescriptionType)XmlBeans.getContextTypeLoader().parse(xis, EjbReferenceDescriptionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbReferenceDescriptionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EjbReferenceDescriptionType.type, options);
      }

      private Factory() {
      }
   }
}
