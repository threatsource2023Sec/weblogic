package org.apache.xmlbeans.impl.xb.ltgfmt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlBoolean;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface TestCase extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TestCase.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLTOOLS").resolveHandle("testcase939btype");

   String getDescription();

   XmlString xgetDescription();

   boolean isSetDescription();

   void setDescription(String var1);

   void xsetDescription(XmlString var1);

   void unsetDescription();

   Files getFiles();

   void setFiles(Files var1);

   Files addNewFiles();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   String getOrigin();

   XmlToken xgetOrigin();

   boolean isSetOrigin();

   void setOrigin(String var1);

   void xsetOrigin(XmlToken var1);

   void unsetOrigin();

   boolean getModified();

   XmlBoolean xgetModified();

   boolean isSetModified();

   void setModified(boolean var1);

   void xsetModified(XmlBoolean var1);

   void unsetModified();

   public static final class Factory {
      public static TestCase newInstance() {
         return (TestCase)XmlBeans.getContextTypeLoader().newInstance(TestCase.type, (XmlOptions)null);
      }

      public static TestCase newInstance(XmlOptions options) {
         return (TestCase)XmlBeans.getContextTypeLoader().newInstance(TestCase.type, options);
      }

      public static TestCase parse(String xmlAsString) throws XmlException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, TestCase.type, (XmlOptions)null);
      }

      public static TestCase parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse(xmlAsString, TestCase.type, options);
      }

      public static TestCase parse(File file) throws XmlException, IOException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse((File)file, TestCase.type, (XmlOptions)null);
      }

      public static TestCase parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse(file, TestCase.type, options);
      }

      public static TestCase parse(URL u) throws XmlException, IOException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse((URL)u, TestCase.type, (XmlOptions)null);
      }

      public static TestCase parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse(u, TestCase.type, options);
      }

      public static TestCase parse(InputStream is) throws XmlException, IOException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse((InputStream)is, TestCase.type, (XmlOptions)null);
      }

      public static TestCase parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse(is, TestCase.type, options);
      }

      public static TestCase parse(Reader r) throws XmlException, IOException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse((Reader)r, TestCase.type, (XmlOptions)null);
      }

      public static TestCase parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse(r, TestCase.type, options);
      }

      public static TestCase parse(XMLStreamReader sr) throws XmlException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, TestCase.type, (XmlOptions)null);
      }

      public static TestCase parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse(sr, TestCase.type, options);
      }

      public static TestCase parse(Node node) throws XmlException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse((Node)node, TestCase.type, (XmlOptions)null);
      }

      public static TestCase parse(Node node, XmlOptions options) throws XmlException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse(node, TestCase.type, options);
      }

      /** @deprecated */
      public static TestCase parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, TestCase.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TestCase parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TestCase)XmlBeans.getContextTypeLoader().parse(xis, TestCase.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TestCase.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TestCase.type, options);
      }

      private Factory() {
      }
   }

   public interface Files extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Files.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLTOOLS").resolveHandle("files7c3eelemtype");

      FileDesc[] getFileArray();

      FileDesc getFileArray(int var1);

      int sizeOfFileArray();

      void setFileArray(FileDesc[] var1);

      void setFileArray(int var1, FileDesc var2);

      FileDesc insertNewFile(int var1);

      FileDesc addNewFile();

      void removeFile(int var1);

      public static final class Factory {
         public static Files newInstance() {
            return (Files)XmlBeans.getContextTypeLoader().newInstance(TestCase.Files.type, (XmlOptions)null);
         }

         public static Files newInstance(XmlOptions options) {
            return (Files)XmlBeans.getContextTypeLoader().newInstance(TestCase.Files.type, options);
         }

         private Factory() {
         }
      }
   }
}
