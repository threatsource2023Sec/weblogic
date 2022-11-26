package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnyURI;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNCName;
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

public interface NotationDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NotationDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("notation3381doctype");

   Notation getNotation();

   void setNotation(Notation var1);

   Notation addNewNotation();

   public static final class Factory {
      public static NotationDocument newInstance() {
         return (NotationDocument)XmlBeans.getContextTypeLoader().newInstance(NotationDocument.type, (XmlOptions)null);
      }

      public static NotationDocument newInstance(XmlOptions options) {
         return (NotationDocument)XmlBeans.getContextTypeLoader().newInstance(NotationDocument.type, options);
      }

      public static NotationDocument parse(String xmlAsString) throws XmlException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, NotationDocument.type, (XmlOptions)null);
      }

      public static NotationDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, NotationDocument.type, options);
      }

      public static NotationDocument parse(File file) throws XmlException, IOException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse((File)file, NotationDocument.type, (XmlOptions)null);
      }

      public static NotationDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse(file, NotationDocument.type, options);
      }

      public static NotationDocument parse(URL u) throws XmlException, IOException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse((URL)u, NotationDocument.type, (XmlOptions)null);
      }

      public static NotationDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse(u, NotationDocument.type, options);
      }

      public static NotationDocument parse(InputStream is) throws XmlException, IOException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, NotationDocument.type, (XmlOptions)null);
      }

      public static NotationDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse(is, NotationDocument.type, options);
      }

      public static NotationDocument parse(Reader r) throws XmlException, IOException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, NotationDocument.type, (XmlOptions)null);
      }

      public static NotationDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse(r, NotationDocument.type, options);
      }

      public static NotationDocument parse(XMLStreamReader sr) throws XmlException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, NotationDocument.type, (XmlOptions)null);
      }

      public static NotationDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse(sr, NotationDocument.type, options);
      }

      public static NotationDocument parse(Node node) throws XmlException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse((Node)node, NotationDocument.type, (XmlOptions)null);
      }

      public static NotationDocument parse(Node node, XmlOptions options) throws XmlException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse(node, NotationDocument.type, options);
      }

      /** @deprecated */
      public static NotationDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, NotationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NotationDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NotationDocument)XmlBeans.getContextTypeLoader().parse(xis, NotationDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NotationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NotationDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Notation extends Annotated {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Notation.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("notation8b1felemtype");

      String getName();

      XmlNCName xgetName();

      void setName(String var1);

      void xsetName(XmlNCName var1);

      String getPublic();

      Public xgetPublic();

      boolean isSetPublic();

      void setPublic(String var1);

      void xsetPublic(Public var1);

      void unsetPublic();

      String getSystem();

      XmlAnyURI xgetSystem();

      boolean isSetSystem();

      void setSystem(String var1);

      void xsetSystem(XmlAnyURI var1);

      void unsetSystem();

      public static final class Factory {
         public static Notation newInstance() {
            return (Notation)XmlBeans.getContextTypeLoader().newInstance(NotationDocument.Notation.type, (XmlOptions)null);
         }

         public static Notation newInstance(XmlOptions options) {
            return (Notation)XmlBeans.getContextTypeLoader().newInstance(NotationDocument.Notation.type, options);
         }

         private Factory() {
         }
      }
   }
}
