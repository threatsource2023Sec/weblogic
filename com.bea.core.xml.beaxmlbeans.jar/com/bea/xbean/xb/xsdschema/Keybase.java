package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNCName;
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

public interface Keybase extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Keybase.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("keybase3955type");

   SelectorDocument.Selector getSelector();

   void setSelector(SelectorDocument.Selector var1);

   SelectorDocument.Selector addNewSelector();

   FieldDocument.Field[] getFieldArray();

   FieldDocument.Field getFieldArray(int var1);

   int sizeOfFieldArray();

   void setFieldArray(FieldDocument.Field[] var1);

   void setFieldArray(int var1, FieldDocument.Field var2);

   FieldDocument.Field insertNewField(int var1);

   FieldDocument.Field addNewField();

   void removeField(int var1);

   String getName();

   XmlNCName xgetName();

   void setName(String var1);

   void xsetName(XmlNCName var1);

   public static final class Factory {
      public static Keybase newInstance() {
         return (Keybase)XmlBeans.getContextTypeLoader().newInstance(Keybase.type, (XmlOptions)null);
      }

      public static Keybase newInstance(XmlOptions options) {
         return (Keybase)XmlBeans.getContextTypeLoader().newInstance(Keybase.type, options);
      }

      public static Keybase parse(String xmlAsString) throws XmlException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Keybase.type, (XmlOptions)null);
      }

      public static Keybase parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse(xmlAsString, Keybase.type, options);
      }

      public static Keybase parse(File file) throws XmlException, IOException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse((File)file, Keybase.type, (XmlOptions)null);
      }

      public static Keybase parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse(file, Keybase.type, options);
      }

      public static Keybase parse(URL u) throws XmlException, IOException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse((URL)u, Keybase.type, (XmlOptions)null);
      }

      public static Keybase parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse(u, Keybase.type, options);
      }

      public static Keybase parse(InputStream is) throws XmlException, IOException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse((InputStream)is, Keybase.type, (XmlOptions)null);
      }

      public static Keybase parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse(is, Keybase.type, options);
      }

      public static Keybase parse(Reader r) throws XmlException, IOException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse((Reader)r, Keybase.type, (XmlOptions)null);
      }

      public static Keybase parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse(r, Keybase.type, options);
      }

      public static Keybase parse(XMLStreamReader sr) throws XmlException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Keybase.type, (XmlOptions)null);
      }

      public static Keybase parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse(sr, Keybase.type, options);
      }

      public static Keybase parse(Node node) throws XmlException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse((Node)node, Keybase.type, (XmlOptions)null);
      }

      public static Keybase parse(Node node, XmlOptions options) throws XmlException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse(node, Keybase.type, options);
      }

      /** @deprecated */
      public static Keybase parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Keybase.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Keybase parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Keybase)XmlBeans.getContextTypeLoader().parse(xis, Keybase.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Keybase.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Keybase.type, options);
      }

      private Factory() {
      }
   }
}
