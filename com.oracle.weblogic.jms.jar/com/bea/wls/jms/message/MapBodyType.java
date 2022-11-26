package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface MapBodyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MapBodyType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("mapbodytype1fc1type");

   NameValue[] getNameValueArray();

   NameValue getNameValueArray(int var1);

   int sizeOfNameValueArray();

   void setNameValueArray(NameValue[] var1);

   void setNameValueArray(int var1, NameValue var2);

   NameValue insertNewNameValue(int var1);

   NameValue addNewNameValue();

   void removeNameValue(int var1);

   public static final class Factory {
      public static MapBodyType newInstance() {
         return (MapBodyType)XmlBeans.getContextTypeLoader().newInstance(MapBodyType.type, (XmlOptions)null);
      }

      public static MapBodyType newInstance(XmlOptions options) {
         return (MapBodyType)XmlBeans.getContextTypeLoader().newInstance(MapBodyType.type, options);
      }

      public static MapBodyType parse(String xmlAsString) throws XmlException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MapBodyType.type, (XmlOptions)null);
      }

      public static MapBodyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MapBodyType.type, options);
      }

      public static MapBodyType parse(File file) throws XmlException, IOException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(file, MapBodyType.type, (XmlOptions)null);
      }

      public static MapBodyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(file, MapBodyType.type, options);
      }

      public static MapBodyType parse(URL u) throws XmlException, IOException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(u, MapBodyType.type, (XmlOptions)null);
      }

      public static MapBodyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(u, MapBodyType.type, options);
      }

      public static MapBodyType parse(InputStream is) throws XmlException, IOException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(is, MapBodyType.type, (XmlOptions)null);
      }

      public static MapBodyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(is, MapBodyType.type, options);
      }

      public static MapBodyType parse(Reader r) throws XmlException, IOException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(r, MapBodyType.type, (XmlOptions)null);
      }

      public static MapBodyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(r, MapBodyType.type, options);
      }

      public static MapBodyType parse(XMLStreamReader sr) throws XmlException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(sr, MapBodyType.type, (XmlOptions)null);
      }

      public static MapBodyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(sr, MapBodyType.type, options);
      }

      public static MapBodyType parse(Node node) throws XmlException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(node, MapBodyType.type, (XmlOptions)null);
      }

      public static MapBodyType parse(Node node, XmlOptions options) throws XmlException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(node, MapBodyType.type, options);
      }

      /** @deprecated */
      public static MapBodyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(xis, MapBodyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MapBodyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MapBodyType)XmlBeans.getContextTypeLoader().parse(xis, MapBodyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MapBodyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MapBodyType.type, options);
      }

      private Factory() {
      }
   }

   public interface NameValue extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NameValue.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("namevalue94d6elemtype");

      String getString();

      StringType xgetString();

      boolean isSetString();

      void setString(String var1);

      void xsetString(StringType var1);

      void unsetString();

      long getLong();

      LongType xgetLong();

      boolean isSetLong();

      void setLong(long var1);

      void xsetLong(LongType var1);

      void unsetLong();

      short getShort();

      ShortType xgetShort();

      boolean isSetShort();

      void setShort(short var1);

      void xsetShort(ShortType var1);

      void unsetShort();

      BigInteger getInt();

      IntType xgetInt();

      boolean isSetInt();

      void setInt(BigInteger var1);

      void xsetInt(IntType var1);

      void unsetInt();

      float getFloat();

      FloatType xgetFloat();

      boolean isSetFloat();

      void setFloat(float var1);

      void xsetFloat(FloatType var1);

      void unsetFloat();

      double getDouble();

      DoubleType xgetDouble();

      boolean isSetDouble();

      void setDouble(double var1);

      void xsetDouble(DoubleType var1);

      void unsetDouble();

      byte getByte();

      ByteType xgetByte();

      boolean isSetByte();

      void setByte(byte var1);

      void xsetByte(ByteType var1);

      void unsetByte();

      boolean getBoolean();

      BooleanType xgetBoolean();

      boolean isSetBoolean();

      void setBoolean(boolean var1);

      void xsetBoolean(BooleanType var1);

      void unsetBoolean();

      byte[] getBytes();

      BytesType xgetBytes();

      boolean isSetBytes();

      void setBytes(byte[] var1);

      void xsetBytes(BytesType var1);

      void unsetBytes();

      String getChar();

      CharType xgetChar();

      boolean isSetChar();

      void setChar(String var1);

      void xsetChar(CharType var1);

      void unsetChar();

      String getName();

      XmlString xgetName();

      void setName(String var1);

      void xsetName(XmlString var1);

      public static final class Factory {
         public static NameValue newInstance() {
            return (NameValue)XmlBeans.getContextTypeLoader().newInstance(MapBodyType.NameValue.type, (XmlOptions)null);
         }

         public static NameValue newInstance(XmlOptions options) {
            return (NameValue)XmlBeans.getContextTypeLoader().newInstance(MapBodyType.NameValue.type, options);
         }

         private Factory() {
         }
      }
   }
}
