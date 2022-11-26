package com.bea.xbean.xb.substwsdl;

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

public interface DefinitionsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefinitionsDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLTOOLS").resolveHandle("definitionsc7f1doctype");

   Definitions getDefinitions();

   void setDefinitions(Definitions var1);

   Definitions addNewDefinitions();

   public static final class Factory {
      public static DefinitionsDocument newInstance() {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().newInstance(DefinitionsDocument.type, (XmlOptions)null);
      }

      public static DefinitionsDocument newInstance(XmlOptions options) {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().newInstance(DefinitionsDocument.type, options);
      }

      public static DefinitionsDocument parse(String xmlAsString) throws XmlException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, DefinitionsDocument.type, (XmlOptions)null);
      }

      public static DefinitionsDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefinitionsDocument.type, options);
      }

      public static DefinitionsDocument parse(File file) throws XmlException, IOException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse((File)file, DefinitionsDocument.type, (XmlOptions)null);
      }

      public static DefinitionsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse(file, DefinitionsDocument.type, options);
      }

      public static DefinitionsDocument parse(URL u) throws XmlException, IOException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse((URL)u, DefinitionsDocument.type, (XmlOptions)null);
      }

      public static DefinitionsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse(u, DefinitionsDocument.type, options);
      }

      public static DefinitionsDocument parse(InputStream is) throws XmlException, IOException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, DefinitionsDocument.type, (XmlOptions)null);
      }

      public static DefinitionsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse(is, DefinitionsDocument.type, options);
      }

      public static DefinitionsDocument parse(Reader r) throws XmlException, IOException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, DefinitionsDocument.type, (XmlOptions)null);
      }

      public static DefinitionsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse(r, DefinitionsDocument.type, options);
      }

      public static DefinitionsDocument parse(XMLStreamReader sr) throws XmlException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, DefinitionsDocument.type, (XmlOptions)null);
      }

      public static DefinitionsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse(sr, DefinitionsDocument.type, options);
      }

      public static DefinitionsDocument parse(Node node) throws XmlException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse((Node)node, DefinitionsDocument.type, (XmlOptions)null);
      }

      public static DefinitionsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse(node, DefinitionsDocument.type, options);
      }

      /** @deprecated */
      public static DefinitionsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, DefinitionsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefinitionsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefinitionsDocument)XmlBeans.getContextTypeLoader().parse(xis, DefinitionsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefinitionsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefinitionsDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Definitions extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Definitions.class.getClassLoader(), "schemacom_bea_xml.system.sXMLTOOLS").resolveHandle("definitions05ddelemtype");

      TImport[] getImportArray();

      TImport getImportArray(int var1);

      int sizeOfImportArray();

      void setImportArray(TImport[] var1);

      void setImportArray(int var1, TImport var2);

      TImport insertNewImport(int var1);

      TImport addNewImport();

      void removeImport(int var1);

      XmlObject[] getTypesArray();

      XmlObject getTypesArray(int var1);

      int sizeOfTypesArray();

      void setTypesArray(XmlObject[] var1);

      void setTypesArray(int var1, XmlObject var2);

      XmlObject insertNewTypes(int var1);

      XmlObject addNewTypes();

      void removeTypes(int var1);

      XmlObject[] getMessageArray();

      XmlObject getMessageArray(int var1);

      int sizeOfMessageArray();

      void setMessageArray(XmlObject[] var1);

      void setMessageArray(int var1, XmlObject var2);

      XmlObject insertNewMessage(int var1);

      XmlObject addNewMessage();

      void removeMessage(int var1);

      XmlObject[] getBindingArray();

      XmlObject getBindingArray(int var1);

      int sizeOfBindingArray();

      void setBindingArray(XmlObject[] var1);

      void setBindingArray(int var1, XmlObject var2);

      XmlObject insertNewBinding(int var1);

      XmlObject addNewBinding();

      void removeBinding(int var1);

      XmlObject[] getPortTypeArray();

      XmlObject getPortTypeArray(int var1);

      int sizeOfPortTypeArray();

      void setPortTypeArray(XmlObject[] var1);

      void setPortTypeArray(int var1, XmlObject var2);

      XmlObject insertNewPortType(int var1);

      XmlObject addNewPortType();

      void removePortType(int var1);

      XmlObject[] getServiceArray();

      XmlObject getServiceArray(int var1);

      int sizeOfServiceArray();

      void setServiceArray(XmlObject[] var1);

      void setServiceArray(int var1, XmlObject var2);

      XmlObject insertNewService(int var1);

      XmlObject addNewService();

      void removeService(int var1);

      public static final class Factory {
         public static Definitions newInstance() {
            return (Definitions)XmlBeans.getContextTypeLoader().newInstance(DefinitionsDocument.Definitions.type, (XmlOptions)null);
         }

         public static Definitions newInstance(XmlOptions options) {
            return (Definitions)XmlBeans.getContextTypeLoader().newInstance(DefinitionsDocument.Definitions.type, options);
         }

         private Factory() {
         }
      }
   }
}
