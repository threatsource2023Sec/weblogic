package com.bea.xbean.xb.ltgfmt;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface TestsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TestsDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLTOOLS").resolveHandle("tests5621doctype");

   Tests getTests();

   void setTests(Tests var1);

   Tests addNewTests();

   public static final class Factory {
      public static TestsDocument newInstance() {
         return (TestsDocument)XmlBeans.getContextTypeLoader().newInstance(TestsDocument.type, (XmlOptions)null);
      }

      public static TestsDocument newInstance(XmlOptions options) {
         return (TestsDocument)XmlBeans.getContextTypeLoader().newInstance(TestsDocument.type, options);
      }

      public static TestsDocument parse(String xmlAsString) throws XmlException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, TestsDocument.type, (XmlOptions)null);
      }

      public static TestsDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, TestsDocument.type, options);
      }

      public static TestsDocument parse(File file) throws XmlException, IOException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse((File)file, TestsDocument.type, (XmlOptions)null);
      }

      public static TestsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse(file, TestsDocument.type, options);
      }

      public static TestsDocument parse(URL u) throws XmlException, IOException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse((URL)u, TestsDocument.type, (XmlOptions)null);
      }

      public static TestsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse(u, TestsDocument.type, options);
      }

      public static TestsDocument parse(InputStream is) throws XmlException, IOException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, TestsDocument.type, (XmlOptions)null);
      }

      public static TestsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse(is, TestsDocument.type, options);
      }

      public static TestsDocument parse(Reader r) throws XmlException, IOException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, TestsDocument.type, (XmlOptions)null);
      }

      public static TestsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse(r, TestsDocument.type, options);
      }

      public static TestsDocument parse(XMLStreamReader sr) throws XmlException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, TestsDocument.type, (XmlOptions)null);
      }

      public static TestsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse(sr, TestsDocument.type, options);
      }

      public static TestsDocument parse(Node node) throws XmlException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse((Node)node, TestsDocument.type, (XmlOptions)null);
      }

      public static TestsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse(node, TestsDocument.type, options);
      }

      /** @deprecated */
      public static TestsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, TestsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TestsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TestsDocument)XmlBeans.getContextTypeLoader().parse(xis, TestsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TestsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TestsDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Tests extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Tests.class.getClassLoader(), "schemacom_bea_xml.system.sXMLTOOLS").resolveHandle("tests9d6eelemtype");

      TestCase[] getTestArray();

      TestCase getTestArray(int var1);

      int sizeOfTestArray();

      void setTestArray(TestCase[] var1);

      void setTestArray(int var1, TestCase var2);

      TestCase insertNewTest(int var1);

      TestCase addNewTest();

      void removeTest(int var1);

      public static final class Factory {
         public static Tests newInstance() {
            return (Tests)XmlBeans.getContextTypeLoader().newInstance(TestsDocument.Tests.type, (XmlOptions)null);
         }

         public static Tests newInstance(XmlOptions options) {
            return (Tests)XmlBeans.getContextTypeLoader().newInstance(TestsDocument.Tests.type, options);
         }

         private Factory() {
         }
      }
   }
}
