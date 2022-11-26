package org.apache.xmlbeans.impl.xb.ltgfmt;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.StringEnumAbstractBase;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlBoolean;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface FileDesc extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FileDesc.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLTOOLS").resolveHandle("filedesc9392type");

   Code getCode();

   boolean isSetCode();

   void setCode(Code var1);

   Code addNewCode();

   void unsetCode();

   String getTsDir();

   XmlToken xgetTsDir();

   boolean isSetTsDir();

   void setTsDir(String var1);

   void xsetTsDir(XmlToken var1);

   void unsetTsDir();

   String getFolder();

   XmlToken xgetFolder();

   boolean isSetFolder();

   void setFolder(String var1);

   void xsetFolder(XmlToken var1);

   void unsetFolder();

   String getFileName();

   XmlToken xgetFileName();

   boolean isSetFileName();

   void setFileName(String var1);

   void xsetFileName(XmlToken var1);

   void unsetFileName();

   Role.Enum getRole();

   Role xgetRole();

   boolean isSetRole();

   void setRole(Role.Enum var1);

   void xsetRole(Role var1);

   void unsetRole();

   boolean getValidity();

   XmlBoolean xgetValidity();

   boolean isSetValidity();

   void setValidity(boolean var1);

   void xsetValidity(XmlBoolean var1);

   void unsetValidity();

   public static final class Factory {
      public static FileDesc newInstance() {
         return (FileDesc)XmlBeans.getContextTypeLoader().newInstance(FileDesc.type, (XmlOptions)null);
      }

      public static FileDesc newInstance(XmlOptions options) {
         return (FileDesc)XmlBeans.getContextTypeLoader().newInstance(FileDesc.type, options);
      }

      public static FileDesc parse(String xmlAsString) throws XmlException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, FileDesc.type, (XmlOptions)null);
      }

      public static FileDesc parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse(xmlAsString, FileDesc.type, options);
      }

      public static FileDesc parse(File file) throws XmlException, IOException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse((File)file, FileDesc.type, (XmlOptions)null);
      }

      public static FileDesc parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse(file, FileDesc.type, options);
      }

      public static FileDesc parse(URL u) throws XmlException, IOException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse((URL)u, FileDesc.type, (XmlOptions)null);
      }

      public static FileDesc parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse(u, FileDesc.type, options);
      }

      public static FileDesc parse(InputStream is) throws XmlException, IOException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse((InputStream)is, FileDesc.type, (XmlOptions)null);
      }

      public static FileDesc parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse(is, FileDesc.type, options);
      }

      public static FileDesc parse(Reader r) throws XmlException, IOException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse((Reader)r, FileDesc.type, (XmlOptions)null);
      }

      public static FileDesc parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse(r, FileDesc.type, options);
      }

      public static FileDesc parse(XMLStreamReader sr) throws XmlException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, FileDesc.type, (XmlOptions)null);
      }

      public static FileDesc parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse(sr, FileDesc.type, options);
      }

      public static FileDesc parse(Node node) throws XmlException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse((Node)node, FileDesc.type, (XmlOptions)null);
      }

      public static FileDesc parse(Node node, XmlOptions options) throws XmlException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse(node, FileDesc.type, options);
      }

      /** @deprecated */
      public static FileDesc parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, FileDesc.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FileDesc parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FileDesc)XmlBeans.getContextTypeLoader().parse(xis, FileDesc.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FileDesc.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FileDesc.type, options);
      }

      private Factory() {
      }
   }

   public interface Role extends XmlToken {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Role.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLTOOLS").resolveHandle("role21a8attrtype");
      Enum SCHEMA = FileDesc.Role.Enum.forString("schema");
      Enum INSTANCE = FileDesc.Role.Enum.forString("instance");
      Enum RESOURCE = FileDesc.Role.Enum.forString("resource");
      int INT_SCHEMA = 1;
      int INT_INSTANCE = 2;
      int INT_RESOURCE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Role newValue(Object obj) {
            return (Role)FileDesc.Role.type.newValue(obj);
         }

         public static Role newInstance() {
            return (Role)XmlBeans.getContextTypeLoader().newInstance(FileDesc.Role.type, (XmlOptions)null);
         }

         public static Role newInstance(XmlOptions options) {
            return (Role)XmlBeans.getContextTypeLoader().newInstance(FileDesc.Role.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_SCHEMA = 1;
         static final int INT_INSTANCE = 2;
         static final int INT_RESOURCE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("schema", 1), new Enum("instance", 2), new Enum("resource", 3)});
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
