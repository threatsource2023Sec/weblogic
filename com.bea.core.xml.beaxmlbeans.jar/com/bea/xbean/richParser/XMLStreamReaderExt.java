package com.bea.xbean.richParser;

import com.bea.xml.GDate;
import com.bea.xml.GDuration;
import com.bea.xml.XmlCalendar;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public interface XMLStreamReaderExt extends XMLStreamReader {
   int WS_PRESERVE = 1;
   int WS_REPLACE = 2;
   int WS_COLLAPSE = 3;

   String getStringValue() throws XMLStreamException;

   String getStringValue(int var1) throws XMLStreamException;

   boolean getBooleanValue() throws XMLStreamException;

   byte getByteValue() throws XMLStreamException;

   short getShortValue() throws XMLStreamException;

   int getIntValue() throws XMLStreamException;

   long getLongValue() throws XMLStreamException;

   BigInteger getBigIntegerValue() throws XMLStreamException;

   BigDecimal getBigDecimalValue() throws XMLStreamException;

   float getFloatValue() throws XMLStreamException;

   double getDoubleValue() throws XMLStreamException;

   InputStream getHexBinaryValue() throws XMLStreamException;

   InputStream getBase64Value() throws XMLStreamException;

   XmlCalendar getCalendarValue() throws XMLStreamException;

   Date getDateValue() throws XMLStreamException;

   GDate getGDateValue() throws XMLStreamException;

   GDuration getGDurationValue() throws XMLStreamException;

   QName getQNameValue() throws XMLStreamException;

   String getAttributeStringValue(int var1) throws XMLStreamException;

   String getAttributeStringValue(int var1, int var2) throws XMLStreamException;

   boolean getAttributeBooleanValue(int var1) throws XMLStreamException;

   byte getAttributeByteValue(int var1) throws XMLStreamException;

   short getAttributeShortValue(int var1) throws XMLStreamException;

   int getAttributeIntValue(int var1) throws XMLStreamException;

   long getAttributeLongValue(int var1) throws XMLStreamException;

   BigInteger getAttributeBigIntegerValue(int var1) throws XMLStreamException;

   BigDecimal getAttributeBigDecimalValue(int var1) throws XMLStreamException;

   float getAttributeFloatValue(int var1) throws XMLStreamException;

   double getAttributeDoubleValue(int var1) throws XMLStreamException;

   InputStream getAttributeHexBinaryValue(int var1) throws XMLStreamException;

   InputStream getAttributeBase64Value(int var1) throws XMLStreamException;

   XmlCalendar getAttributeCalendarValue(int var1) throws XMLStreamException;

   Date getAttributeDateValue(int var1) throws XMLStreamException;

   GDate getAttributeGDateValue(int var1) throws XMLStreamException;

   GDuration getAttributeGDurationValue(int var1) throws XMLStreamException;

   QName getAttributeQNameValue(int var1) throws XMLStreamException;

   String getAttributeStringValue(String var1, String var2) throws XMLStreamException;

   String getAttributeStringValue(String var1, String var2, int var3) throws XMLStreamException;

   boolean getAttributeBooleanValue(String var1, String var2) throws XMLStreamException;

   byte getAttributeByteValue(String var1, String var2) throws XMLStreamException;

   short getAttributeShortValue(String var1, String var2) throws XMLStreamException;

   int getAttributeIntValue(String var1, String var2) throws XMLStreamException;

   long getAttributeLongValue(String var1, String var2) throws XMLStreamException;

   BigInteger getAttributeBigIntegerValue(String var1, String var2) throws XMLStreamException;

   BigDecimal getAttributeBigDecimalValue(String var1, String var2) throws XMLStreamException;

   float getAttributeFloatValue(String var1, String var2) throws XMLStreamException;

   double getAttributeDoubleValue(String var1, String var2) throws XMLStreamException;

   InputStream getAttributeHexBinaryValue(String var1, String var2) throws XMLStreamException;

   InputStream getAttributeBase64Value(String var1, String var2) throws XMLStreamException;

   XmlCalendar getAttributeCalendarValue(String var1, String var2) throws XMLStreamException;

   Date getAttributeDateValue(String var1, String var2) throws XMLStreamException;

   GDate getAttributeGDateValue(String var1, String var2) throws XMLStreamException;

   GDuration getAttributeGDurationValue(String var1, String var2) throws XMLStreamException;

   QName getAttributeQNameValue(String var1, String var2) throws XMLStreamException;

   void setDefaultValue(String var1) throws XMLStreamException;
}
