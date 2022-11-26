package org.apache.xmlbeans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;

public interface SimpleValue extends XmlObject {
   SchemaType instanceType();

   String getStringValue();

   boolean getBooleanValue();

   byte getByteValue();

   short getShortValue();

   int getIntValue();

   long getLongValue();

   BigInteger getBigIntegerValue();

   BigDecimal getBigDecimalValue();

   float getFloatValue();

   double getDoubleValue();

   byte[] getByteArrayValue();

   StringEnumAbstractBase getEnumValue();

   Calendar getCalendarValue();

   Date getDateValue();

   GDate getGDateValue();

   GDuration getGDurationValue();

   QName getQNameValue();

   List getListValue();

   List xgetListValue();

   Object getObjectValue();

   void setStringValue(String var1);

   void setBooleanValue(boolean var1);

   void setByteValue(byte var1);

   void setShortValue(short var1);

   void setIntValue(int var1);

   void setLongValue(long var1);

   void setBigIntegerValue(BigInteger var1);

   void setBigDecimalValue(BigDecimal var1);

   void setFloatValue(float var1);

   void setDoubleValue(double var1);

   void setByteArrayValue(byte[] var1);

   void setEnumValue(StringEnumAbstractBase var1);

   void setCalendarValue(Calendar var1);

   void setDateValue(Date var1);

   void setGDateValue(GDate var1);

   void setGDurationValue(GDuration var1);

   void setQNameValue(QName var1);

   void setListValue(List var1);

   void setObjectValue(Object var1);

   /** @deprecated */
   String stringValue();

   /** @deprecated */
   boolean booleanValue();

   /** @deprecated */
   byte byteValue();

   /** @deprecated */
   short shortValue();

   /** @deprecated */
   int intValue();

   /** @deprecated */
   long longValue();

   /** @deprecated */
   BigInteger bigIntegerValue();

   /** @deprecated */
   BigDecimal bigDecimalValue();

   /** @deprecated */
   float floatValue();

   /** @deprecated */
   double doubleValue();

   /** @deprecated */
   byte[] byteArrayValue();

   /** @deprecated */
   StringEnumAbstractBase enumValue();

   /** @deprecated */
   Calendar calendarValue();

   /** @deprecated */
   Date dateValue();

   /** @deprecated */
   GDate gDateValue();

   /** @deprecated */
   GDuration gDurationValue();

   /** @deprecated */
   QName qNameValue();

   /** @deprecated */
   List listValue();

   /** @deprecated */
   List xlistValue();

   /** @deprecated */
   Object objectValue();

   /** @deprecated */
   void set(String var1);

   /** @deprecated */
   void set(boolean var1);

   /** @deprecated */
   void set(byte var1);

   /** @deprecated */
   void set(short var1);

   /** @deprecated */
   void set(int var1);

   /** @deprecated */
   void set(long var1);

   /** @deprecated */
   void set(BigInteger var1);

   /** @deprecated */
   void set(BigDecimal var1);

   /** @deprecated */
   void set(float var1);

   /** @deprecated */
   void set(double var1);

   /** @deprecated */
   void set(byte[] var1);

   /** @deprecated */
   void set(StringEnumAbstractBase var1);

   /** @deprecated */
   void set(Calendar var1);

   /** @deprecated */
   void set(Date var1);

   /** @deprecated */
   void set(GDateSpecification var1);

   /** @deprecated */
   void set(GDurationSpecification var1);

   /** @deprecated */
   void set(QName var1);

   /** @deprecated */
   void set(List var1);

   /** @deprecated */
   void objectSet(Object var1);
}
