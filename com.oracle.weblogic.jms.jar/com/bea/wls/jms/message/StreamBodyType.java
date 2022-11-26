package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
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

public interface StreamBodyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StreamBodyType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("streambodytype98ebtype");

   String[] getStringArray();

   String getStringArray(int var1);

   StringType[] xgetStringArray();

   StringType xgetStringArray(int var1);

   int sizeOfStringArray();

   void setStringArray(String[] var1);

   void setStringArray(int var1, String var2);

   void xsetStringArray(StringType[] var1);

   void xsetStringArray(int var1, StringType var2);

   void insertString(int var1, String var2);

   void addString(String var1);

   StringType insertNewString(int var1);

   StringType addNewString();

   void removeString(int var1);

   long[] getLongArray();

   long getLongArray(int var1);

   LongType[] xgetLongArray();

   LongType xgetLongArray(int var1);

   int sizeOfLongArray();

   void setLongArray(long[] var1);

   void setLongArray(int var1, long var2);

   void xsetLongArray(LongType[] var1);

   void xsetLongArray(int var1, LongType var2);

   void insertLong(int var1, long var2);

   void addLong(long var1);

   LongType insertNewLong(int var1);

   LongType addNewLong();

   void removeLong(int var1);

   short[] getShortArray();

   short getShortArray(int var1);

   ShortType[] xgetShortArray();

   ShortType xgetShortArray(int var1);

   int sizeOfShortArray();

   void setShortArray(short[] var1);

   void setShortArray(int var1, short var2);

   void xsetShortArray(ShortType[] var1);

   void xsetShortArray(int var1, ShortType var2);

   void insertShort(int var1, short var2);

   void addShort(short var1);

   ShortType insertNewShort(int var1);

   ShortType addNewShort();

   void removeShort(int var1);

   BigInteger[] getIntArray();

   BigInteger getIntArray(int var1);

   IntType[] xgetIntArray();

   IntType xgetIntArray(int var1);

   int sizeOfIntArray();

   void setIntArray(BigInteger[] var1);

   void setIntArray(int var1, BigInteger var2);

   void xsetIntArray(IntType[] var1);

   void xsetIntArray(int var1, IntType var2);

   void insertInt(int var1, BigInteger var2);

   void addInt(BigInteger var1);

   IntType insertNewInt(int var1);

   IntType addNewInt();

   void removeInt(int var1);

   float[] getFloatArray();

   float getFloatArray(int var1);

   FloatType[] xgetFloatArray();

   FloatType xgetFloatArray(int var1);

   int sizeOfFloatArray();

   void setFloatArray(float[] var1);

   void setFloatArray(int var1, float var2);

   void xsetFloatArray(FloatType[] var1);

   void xsetFloatArray(int var1, FloatType var2);

   void insertFloat(int var1, float var2);

   void addFloat(float var1);

   FloatType insertNewFloat(int var1);

   FloatType addNewFloat();

   void removeFloat(int var1);

   double[] getDoubleArray();

   double getDoubleArray(int var1);

   DoubleType[] xgetDoubleArray();

   DoubleType xgetDoubleArray(int var1);

   int sizeOfDoubleArray();

   void setDoubleArray(double[] var1);

   void setDoubleArray(int var1, double var2);

   void xsetDoubleArray(DoubleType[] var1);

   void xsetDoubleArray(int var1, DoubleType var2);

   void insertDouble(int var1, double var2);

   void addDouble(double var1);

   DoubleType insertNewDouble(int var1);

   DoubleType addNewDouble();

   void removeDouble(int var1);

   byte[] getByteArray();

   byte getByteArray(int var1);

   ByteType[] xgetByteArray();

   ByteType xgetByteArray(int var1);

   int sizeOfByteArray();

   void setByteArray(byte[] var1);

   void setByteArray(int var1, byte var2);

   void xsetByteArray(ByteType[] var1);

   void xsetByteArray(int var1, ByteType var2);

   void insertByte(int var1, byte var2);

   void addByte(byte var1);

   ByteType insertNewByte(int var1);

   ByteType addNewByte();

   void removeByte(int var1);

   boolean[] getBooleanArray();

   boolean getBooleanArray(int var1);

   BooleanType[] xgetBooleanArray();

   BooleanType xgetBooleanArray(int var1);

   int sizeOfBooleanArray();

   void setBooleanArray(boolean[] var1);

   void setBooleanArray(int var1, boolean var2);

   void xsetBooleanArray(BooleanType[] var1);

   void xsetBooleanArray(int var1, BooleanType var2);

   void insertBoolean(int var1, boolean var2);

   void addBoolean(boolean var1);

   BooleanType insertNewBoolean(int var1);

   BooleanType addNewBoolean();

   void removeBoolean(int var1);

   byte[][] getBytesArray();

   byte[] getBytesArray(int var1);

   BytesType[] xgetBytesArray();

   BytesType xgetBytesArray(int var1);

   int sizeOfBytesArray();

   void setBytesArray(byte[][] var1);

   void setBytesArray(int var1, byte[] var2);

   void xsetBytesArray(BytesType[] var1);

   void xsetBytesArray(int var1, BytesType var2);

   void insertBytes(int var1, byte[] var2);

   void addBytes(byte[] var1);

   BytesType insertNewBytes(int var1);

   BytesType addNewBytes();

   void removeBytes(int var1);

   String[] getCharArray();

   String getCharArray(int var1);

   CharType[] xgetCharArray();

   CharType xgetCharArray(int var1);

   int sizeOfCharArray();

   void setCharArray(String[] var1);

   void setCharArray(int var1, String var2);

   void xsetCharArray(CharType[] var1);

   void xsetCharArray(int var1, CharType var2);

   void insertChar(int var1, String var2);

   void addChar(String var1);

   CharType insertNewChar(int var1);

   CharType addNewChar();

   void removeChar(int var1);

   public static final class Factory {
      public static StreamBodyType newInstance() {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().newInstance(StreamBodyType.type, (XmlOptions)null);
      }

      public static StreamBodyType newInstance(XmlOptions options) {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().newInstance(StreamBodyType.type, options);
      }

      public static StreamBodyType parse(String xmlAsString) throws XmlException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StreamBodyType.type, (XmlOptions)null);
      }

      public static StreamBodyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StreamBodyType.type, options);
      }

      public static StreamBodyType parse(File file) throws XmlException, IOException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(file, StreamBodyType.type, (XmlOptions)null);
      }

      public static StreamBodyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(file, StreamBodyType.type, options);
      }

      public static StreamBodyType parse(URL u) throws XmlException, IOException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(u, StreamBodyType.type, (XmlOptions)null);
      }

      public static StreamBodyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(u, StreamBodyType.type, options);
      }

      public static StreamBodyType parse(InputStream is) throws XmlException, IOException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(is, StreamBodyType.type, (XmlOptions)null);
      }

      public static StreamBodyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(is, StreamBodyType.type, options);
      }

      public static StreamBodyType parse(Reader r) throws XmlException, IOException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(r, StreamBodyType.type, (XmlOptions)null);
      }

      public static StreamBodyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(r, StreamBodyType.type, options);
      }

      public static StreamBodyType parse(XMLStreamReader sr) throws XmlException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(sr, StreamBodyType.type, (XmlOptions)null);
      }

      public static StreamBodyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(sr, StreamBodyType.type, options);
      }

      public static StreamBodyType parse(Node node) throws XmlException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(node, StreamBodyType.type, (XmlOptions)null);
      }

      public static StreamBodyType parse(Node node, XmlOptions options) throws XmlException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(node, StreamBodyType.type, options);
      }

      /** @deprecated */
      public static StreamBodyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(xis, StreamBodyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StreamBodyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StreamBodyType)XmlBeans.getContextTypeLoader().parse(xis, StreamBodyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StreamBodyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StreamBodyType.type, options);
      }

      private Factory() {
      }
   }
}
