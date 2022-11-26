package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
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

public interface RealGroup extends Group {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RealGroup.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("realgroup1f64type");

   All[] getAllArray();

   All getAllArray(int var1);

   int sizeOfAllArray();

   void setAllArray(All[] var1);

   void setAllArray(int var1, All var2);

   All insertNewAll(int var1);

   All addNewAll();

   void removeAll(int var1);

   ExplicitGroup[] getChoiceArray();

   ExplicitGroup getChoiceArray(int var1);

   int sizeOfChoiceArray();

   void setChoiceArray(ExplicitGroup[] var1);

   void setChoiceArray(int var1, ExplicitGroup var2);

   ExplicitGroup insertNewChoice(int var1);

   ExplicitGroup addNewChoice();

   void removeChoice(int var1);

   ExplicitGroup[] getSequenceArray();

   ExplicitGroup getSequenceArray(int var1);

   int sizeOfSequenceArray();

   void setSequenceArray(ExplicitGroup[] var1);

   void setSequenceArray(int var1, ExplicitGroup var2);

   ExplicitGroup insertNewSequence(int var1);

   ExplicitGroup addNewSequence();

   void removeSequence(int var1);

   public static final class Factory {
      public static RealGroup newInstance() {
         return (RealGroup)XmlBeans.getContextTypeLoader().newInstance(RealGroup.type, (XmlOptions)null);
      }

      public static RealGroup newInstance(XmlOptions options) {
         return (RealGroup)XmlBeans.getContextTypeLoader().newInstance(RealGroup.type, options);
      }

      public static RealGroup parse(String xmlAsString) throws XmlException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, RealGroup.type, (XmlOptions)null);
      }

      public static RealGroup parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse(xmlAsString, RealGroup.type, options);
      }

      public static RealGroup parse(File file) throws XmlException, IOException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse((File)file, RealGroup.type, (XmlOptions)null);
      }

      public static RealGroup parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse(file, RealGroup.type, options);
      }

      public static RealGroup parse(URL u) throws XmlException, IOException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse((URL)u, RealGroup.type, (XmlOptions)null);
      }

      public static RealGroup parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse(u, RealGroup.type, options);
      }

      public static RealGroup parse(InputStream is) throws XmlException, IOException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse((InputStream)is, RealGroup.type, (XmlOptions)null);
      }

      public static RealGroup parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse(is, RealGroup.type, options);
      }

      public static RealGroup parse(Reader r) throws XmlException, IOException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse((Reader)r, RealGroup.type, (XmlOptions)null);
      }

      public static RealGroup parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse(r, RealGroup.type, options);
      }

      public static RealGroup parse(XMLStreamReader sr) throws XmlException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, RealGroup.type, (XmlOptions)null);
      }

      public static RealGroup parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse(sr, RealGroup.type, options);
      }

      public static RealGroup parse(Node node) throws XmlException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse((Node)node, RealGroup.type, (XmlOptions)null);
      }

      public static RealGroup parse(Node node, XmlOptions options) throws XmlException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse(node, RealGroup.type, options);
      }

      /** @deprecated */
      public static RealGroup parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, RealGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RealGroup parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RealGroup)XmlBeans.getContextTypeLoader().parse(xis, RealGroup.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RealGroup.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RealGroup.type, options);
      }

      private Factory() {
      }
   }
}
