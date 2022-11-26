package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlNMTOKEN;
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

public interface Wildcard extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Wildcard.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("wildcarde0b9type");

   Object getNamespace();

   NamespaceList xgetNamespace();

   boolean isSetNamespace();

   void setNamespace(Object var1);

   void xsetNamespace(NamespaceList var1);

   void unsetNamespace();

   ProcessContents.Enum getProcessContents();

   ProcessContents xgetProcessContents();

   boolean isSetProcessContents();

   void setProcessContents(ProcessContents.Enum var1);

   void xsetProcessContents(ProcessContents var1);

   void unsetProcessContents();

   public static final class Factory {
      public static Wildcard newInstance() {
         return (Wildcard)XmlBeans.getContextTypeLoader().newInstance(Wildcard.type, (XmlOptions)null);
      }

      public static Wildcard newInstance(XmlOptions options) {
         return (Wildcard)XmlBeans.getContextTypeLoader().newInstance(Wildcard.type, options);
      }

      public static Wildcard parse(String xmlAsString) throws XmlException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Wildcard.type, (XmlOptions)null);
      }

      public static Wildcard parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse(xmlAsString, Wildcard.type, options);
      }

      public static Wildcard parse(File file) throws XmlException, IOException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse((File)file, Wildcard.type, (XmlOptions)null);
      }

      public static Wildcard parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse(file, Wildcard.type, options);
      }

      public static Wildcard parse(URL u) throws XmlException, IOException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse((URL)u, Wildcard.type, (XmlOptions)null);
      }

      public static Wildcard parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse(u, Wildcard.type, options);
      }

      public static Wildcard parse(InputStream is) throws XmlException, IOException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse((InputStream)is, Wildcard.type, (XmlOptions)null);
      }

      public static Wildcard parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse(is, Wildcard.type, options);
      }

      public static Wildcard parse(Reader r) throws XmlException, IOException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse((Reader)r, Wildcard.type, (XmlOptions)null);
      }

      public static Wildcard parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse(r, Wildcard.type, options);
      }

      public static Wildcard parse(XMLStreamReader sr) throws XmlException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Wildcard.type, (XmlOptions)null);
      }

      public static Wildcard parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse(sr, Wildcard.type, options);
      }

      public static Wildcard parse(Node node) throws XmlException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse((Node)node, Wildcard.type, (XmlOptions)null);
      }

      public static Wildcard parse(Node node, XmlOptions options) throws XmlException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse(node, Wildcard.type, options);
      }

      /** @deprecated */
      public static Wildcard parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Wildcard.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Wildcard parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Wildcard)XmlBeans.getContextTypeLoader().parse(xis, Wildcard.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Wildcard.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Wildcard.type, options);
      }

      private Factory() {
      }
   }

   public interface ProcessContents extends XmlNMTOKEN {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProcessContents.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("processcontents864aattrtype");
      Enum SKIP = Wildcard.ProcessContents.Enum.forString("skip");
      Enum LAX = Wildcard.ProcessContents.Enum.forString("lax");
      Enum STRICT = Wildcard.ProcessContents.Enum.forString("strict");
      int INT_SKIP = 1;
      int INT_LAX = 2;
      int INT_STRICT = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static ProcessContents newValue(Object obj) {
            return (ProcessContents)Wildcard.ProcessContents.type.newValue(obj);
         }

         public static ProcessContents newInstance() {
            return (ProcessContents)XmlBeans.getContextTypeLoader().newInstance(Wildcard.ProcessContents.type, (XmlOptions)null);
         }

         public static ProcessContents newInstance(XmlOptions options) {
            return (ProcessContents)XmlBeans.getContextTypeLoader().newInstance(Wildcard.ProcessContents.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_SKIP = 1;
         static final int INT_LAX = 2;
         static final int INT_STRICT = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("skip", 1), new Enum("lax", 2), new Enum("strict", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }
}
