package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.BooleanType;
import com.bea.wls.jms.message.ByteType;
import com.bea.wls.jms.message.BytesType;
import com.bea.wls.jms.message.CharType;
import com.bea.wls.jms.message.DoubleType;
import com.bea.wls.jms.message.FloatType;
import com.bea.wls.jms.message.IntType;
import com.bea.wls.jms.message.LongType;
import com.bea.wls.jms.message.ShortType;
import com.bea.wls.jms.message.StreamBodyType;
import com.bea.wls.jms.message.StringType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class StreamBodyTypeImpl extends XmlComplexContentImpl implements StreamBodyType {
   private static final long serialVersionUID = 1L;
   private static final QName STRING$0 = new QName("http://www.bea.com/WLS/JMS/Message", "String");
   private static final QName LONG$2 = new QName("http://www.bea.com/WLS/JMS/Message", "Long");
   private static final QName SHORT$4 = new QName("http://www.bea.com/WLS/JMS/Message", "Short");
   private static final QName INT$6 = new QName("http://www.bea.com/WLS/JMS/Message", "Int");
   private static final QName FLOAT$8 = new QName("http://www.bea.com/WLS/JMS/Message", "Float");
   private static final QName DOUBLE$10 = new QName("http://www.bea.com/WLS/JMS/Message", "Double");
   private static final QName BYTE$12 = new QName("http://www.bea.com/WLS/JMS/Message", "Byte");
   private static final QName BOOLEAN$14 = new QName("http://www.bea.com/WLS/JMS/Message", "Boolean");
   private static final QName BYTES$16 = new QName("http://www.bea.com/WLS/JMS/Message", "Bytes");
   private static final QName CHAR$18 = new QName("http://www.bea.com/WLS/JMS/Message", "Char");

   public StreamBodyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getStringArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STRING$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getStringArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRING$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public StringType[] xgetStringArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STRING$0, targetList);
         StringType[] result = new StringType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public StringType xgetStringArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StringType target = null;
         target = (StringType)this.get_store().find_element_user(STRING$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfStringArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STRING$0);
      }
   }

   public void setStringArray(String[] stringArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(stringArray, STRING$0);
      }
   }

   public void setStringArray(int i, String string) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STRING$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(string);
         }
      }
   }

   public void xsetStringArray(StringType[] stringArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(stringArray, STRING$0);
      }
   }

   public void xsetStringArray(int i, StringType string) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StringType target = null;
         target = (StringType)this.get_store().find_element_user(STRING$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(string);
         }
      }
   }

   public void insertString(int i, String string) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(STRING$0, i);
         target.setStringValue(string);
      }
   }

   public void addString(String string) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(STRING$0);
         target.setStringValue(string);
      }
   }

   public StringType insertNewString(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StringType target = null;
         target = (StringType)this.get_store().insert_element_user(STRING$0, i);
         return target;
      }
   }

   public StringType addNewString() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StringType target = null;
         target = (StringType)this.get_store().add_element_user(STRING$0);
         return target;
      }
   }

   public void removeString(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STRING$0, i);
      }
   }

   public long[] getLongArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LONG$2, targetList);
         long[] result = new long[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getLongValue();
         }

         return result;
      }
   }

   public long getLongArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LONG$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getLongValue();
         }
      }
   }

   public LongType[] xgetLongArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(LONG$2, targetList);
         LongType[] result = new LongType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public LongType xgetLongArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LongType target = null;
         target = (LongType)this.get_store().find_element_user(LONG$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfLongArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LONG$2);
      }
   }

   public void setLongArray(long[] xlongArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xlongArray, LONG$2);
      }
   }

   public void setLongArray(int i, long xlong) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LONG$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setLongValue(xlong);
         }
      }
   }

   public void xsetLongArray(LongType[] xlongArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xlongArray, LONG$2);
      }
   }

   public void xsetLongArray(int i, LongType xlong) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LongType target = null;
         target = (LongType)this.get_store().find_element_user(LONG$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xlong);
         }
      }
   }

   public void insertLong(int i, long xlong) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(LONG$2, i);
         target.setLongValue(xlong);
      }
   }

   public void addLong(long xlong) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(LONG$2);
         target.setLongValue(xlong);
      }
   }

   public LongType insertNewLong(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LongType target = null;
         target = (LongType)this.get_store().insert_element_user(LONG$2, i);
         return target;
      }
   }

   public LongType addNewLong() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LongType target = null;
         target = (LongType)this.get_store().add_element_user(LONG$2);
         return target;
      }
   }

   public void removeLong(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LONG$2, i);
      }
   }

   public short[] getShortArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SHORT$4, targetList);
         short[] result = new short[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getShortValue();
         }

         return result;
      }
   }

   public short getShortArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHORT$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getShortValue();
         }
      }
   }

   public ShortType[] xgetShortArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SHORT$4, targetList);
         ShortType[] result = new ShortType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ShortType xgetShortArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShortType target = null;
         target = (ShortType)this.get_store().find_element_user(SHORT$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfShortArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHORT$4);
      }
   }

   public void setShortArray(short[] xshortArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xshortArray, SHORT$4);
      }
   }

   public void setShortArray(int i, short xshort) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHORT$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setShortValue(xshort);
         }
      }
   }

   public void xsetShortArray(ShortType[] xshortArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xshortArray, SHORT$4);
      }
   }

   public void xsetShortArray(int i, ShortType xshort) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShortType target = null;
         target = (ShortType)this.get_store().find_element_user(SHORT$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xshort);
         }
      }
   }

   public void insertShort(int i, short xshort) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(SHORT$4, i);
         target.setShortValue(xshort);
      }
   }

   public void addShort(short xshort) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(SHORT$4);
         target.setShortValue(xshort);
      }
   }

   public ShortType insertNewShort(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShortType target = null;
         target = (ShortType)this.get_store().insert_element_user(SHORT$4, i);
         return target;
      }
   }

   public ShortType addNewShort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ShortType target = null;
         target = (ShortType)this.get_store().add_element_user(SHORT$4);
         return target;
      }
   }

   public void removeShort(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHORT$4, i);
      }
   }

   public BigInteger[] getIntArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INT$6, targetList);
         BigInteger[] result = new BigInteger[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getBigIntegerValue();
         }

         return result;
      }
   }

   public BigInteger getIntArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INT$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getBigIntegerValue();
         }
      }
   }

   public IntType[] xgetIntArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INT$6, targetList);
         IntType[] result = new IntType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IntType xgetIntArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IntType target = null;
         target = (IntType)this.get_store().find_element_user(INT$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIntArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INT$6);
      }
   }

   public void setIntArray(BigInteger[] xintArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xintArray, INT$6);
      }
   }

   public void setIntArray(int i, BigInteger xint) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INT$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setBigIntegerValue(xint);
         }
      }
   }

   public void xsetIntArray(IntType[] xintArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xintArray, INT$6);
      }
   }

   public void xsetIntArray(int i, IntType xint) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IntType target = null;
         target = (IntType)this.get_store().find_element_user(INT$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xint);
         }
      }
   }

   public void insertInt(int i, BigInteger xint) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(INT$6, i);
         target.setBigIntegerValue(xint);
      }
   }

   public void addInt(BigInteger xint) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(INT$6);
         target.setBigIntegerValue(xint);
      }
   }

   public IntType insertNewInt(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IntType target = null;
         target = (IntType)this.get_store().insert_element_user(INT$6, i);
         return target;
      }
   }

   public IntType addNewInt() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IntType target = null;
         target = (IntType)this.get_store().add_element_user(INT$6);
         return target;
      }
   }

   public void removeInt(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INT$6, i);
      }
   }

   public float[] getFloatArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FLOAT$8, targetList);
         float[] result = new float[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getFloatValue();
         }

         return result;
      }
   }

   public float getFloatArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOAT$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getFloatValue();
         }
      }
   }

   public FloatType[] xgetFloatArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FLOAT$8, targetList);
         FloatType[] result = new FloatType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public FloatType xgetFloatArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FloatType target = null;
         target = (FloatType)this.get_store().find_element_user(FLOAT$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFloatArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOAT$8);
      }
   }

   public void setFloatArray(float[] xfloatArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xfloatArray, FLOAT$8);
      }
   }

   public void setFloatArray(int i, float xfloat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLOAT$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setFloatValue(xfloat);
         }
      }
   }

   public void xsetFloatArray(FloatType[] xfloatArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xfloatArray, FLOAT$8);
      }
   }

   public void xsetFloatArray(int i, FloatType xfloat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FloatType target = null;
         target = (FloatType)this.get_store().find_element_user(FLOAT$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xfloat);
         }
      }
   }

   public void insertFloat(int i, float xfloat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(FLOAT$8, i);
         target.setFloatValue(xfloat);
      }
   }

   public void addFloat(float xfloat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(FLOAT$8);
         target.setFloatValue(xfloat);
      }
   }

   public FloatType insertNewFloat(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FloatType target = null;
         target = (FloatType)this.get_store().insert_element_user(FLOAT$8, i);
         return target;
      }
   }

   public FloatType addNewFloat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FloatType target = null;
         target = (FloatType)this.get_store().add_element_user(FLOAT$8);
         return target;
      }
   }

   public void removeFloat(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOAT$8, i);
      }
   }

   public double[] getDoubleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DOUBLE$10, targetList);
         double[] result = new double[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getDoubleValue();
         }

         return result;
      }
   }

   public double getDoubleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DOUBLE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getDoubleValue();
         }
      }
   }

   public DoubleType[] xgetDoubleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DOUBLE$10, targetList);
         DoubleType[] result = new DoubleType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DoubleType xgetDoubleArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DoubleType target = null;
         target = (DoubleType)this.get_store().find_element_user(DOUBLE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDoubleArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DOUBLE$10);
      }
   }

   public void setDoubleArray(double[] xdoubleArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xdoubleArray, DOUBLE$10);
      }
   }

   public void setDoubleArray(int i, double xdouble) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DOUBLE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setDoubleValue(xdouble);
         }
      }
   }

   public void xsetDoubleArray(DoubleType[] xdoubleArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xdoubleArray, DOUBLE$10);
      }
   }

   public void xsetDoubleArray(int i, DoubleType xdouble) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DoubleType target = null;
         target = (DoubleType)this.get_store().find_element_user(DOUBLE$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xdouble);
         }
      }
   }

   public void insertDouble(int i, double xdouble) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DOUBLE$10, i);
         target.setDoubleValue(xdouble);
      }
   }

   public void addDouble(double xdouble) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(DOUBLE$10);
         target.setDoubleValue(xdouble);
      }
   }

   public DoubleType insertNewDouble(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DoubleType target = null;
         target = (DoubleType)this.get_store().insert_element_user(DOUBLE$10, i);
         return target;
      }
   }

   public DoubleType addNewDouble() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DoubleType target = null;
         target = (DoubleType)this.get_store().add_element_user(DOUBLE$10);
         return target;
      }
   }

   public void removeDouble(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DOUBLE$10, i);
      }
   }

   public byte[] getByteArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BYTE$12, targetList);
         byte[] result = new byte[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getByteValue();
         }

         return result;
      }
   }

   public byte getByteArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTE$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getByteValue();
         }
      }
   }

   public ByteType[] xgetByteArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BYTE$12, targetList);
         ByteType[] result = new ByteType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ByteType xgetByteArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ByteType target = null;
         target = (ByteType)this.get_store().find_element_user(BYTE$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfByteArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BYTE$12);
      }
   }

   public void setByteArray(byte[] xbyteArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xbyteArray, BYTE$12);
      }
   }

   public void setByteArray(int i, byte xbyte) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTE$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setByteValue(xbyte);
         }
      }
   }

   public void xsetByteArray(ByteType[] xbyteArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xbyteArray, BYTE$12);
      }
   }

   public void xsetByteArray(int i, ByteType xbyte) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ByteType target = null;
         target = (ByteType)this.get_store().find_element_user(BYTE$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xbyte);
         }
      }
   }

   public void insertByte(int i, byte xbyte) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(BYTE$12, i);
         target.setByteValue(xbyte);
      }
   }

   public void addByte(byte xbyte) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(BYTE$12);
         target.setByteValue(xbyte);
      }
   }

   public ByteType insertNewByte(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ByteType target = null;
         target = (ByteType)this.get_store().insert_element_user(BYTE$12, i);
         return target;
      }
   }

   public ByteType addNewByte() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ByteType target = null;
         target = (ByteType)this.get_store().add_element_user(BYTE$12);
         return target;
      }
   }

   public void removeByte(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BYTE$12, i);
      }
   }

   public boolean[] getBooleanArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BOOLEAN$14, targetList);
         boolean[] result = new boolean[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getBooleanValue();
         }

         return result;
      }
   }

   public boolean getBooleanArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BOOLEAN$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getBooleanValue();
         }
      }
   }

   public BooleanType[] xgetBooleanArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BOOLEAN$14, targetList);
         BooleanType[] result = new BooleanType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public BooleanType xgetBooleanArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BooleanType target = null;
         target = (BooleanType)this.get_store().find_element_user(BOOLEAN$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfBooleanArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BOOLEAN$14);
      }
   }

   public void setBooleanArray(boolean[] xbooleanArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xbooleanArray, BOOLEAN$14);
      }
   }

   public void setBooleanArray(int i, boolean xboolean) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BOOLEAN$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setBooleanValue(xboolean);
         }
      }
   }

   public void xsetBooleanArray(BooleanType[] xbooleanArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xbooleanArray, BOOLEAN$14);
      }
   }

   public void xsetBooleanArray(int i, BooleanType xboolean) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BooleanType target = null;
         target = (BooleanType)this.get_store().find_element_user(BOOLEAN$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xboolean);
         }
      }
   }

   public void insertBoolean(int i, boolean xboolean) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(BOOLEAN$14, i);
         target.setBooleanValue(xboolean);
      }
   }

   public void addBoolean(boolean xboolean) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(BOOLEAN$14);
         target.setBooleanValue(xboolean);
      }
   }

   public BooleanType insertNewBoolean(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BooleanType target = null;
         target = (BooleanType)this.get_store().insert_element_user(BOOLEAN$14, i);
         return target;
      }
   }

   public BooleanType addNewBoolean() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BooleanType target = null;
         target = (BooleanType)this.get_store().add_element_user(BOOLEAN$14);
         return target;
      }
   }

   public void removeBoolean(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BOOLEAN$14, i);
      }
   }

   public byte[][] getBytesArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BYTES$16, targetList);
         byte[][] result = new byte[targetList.size()][];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getByteArrayValue();
         }

         return result;
      }
   }

   public byte[] getBytesArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTES$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getByteArrayValue();
         }
      }
   }

   public BytesType[] xgetBytesArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BYTES$16, targetList);
         BytesType[] result = new BytesType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public BytesType xgetBytesArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BytesType target = null;
         target = (BytesType)this.get_store().find_element_user(BYTES$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfBytesArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BYTES$16);
      }
   }

   public void setBytesArray(byte[][] bytesArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(bytesArray, BYTES$16);
      }
   }

   public void setBytesArray(int i, byte[] bytes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTES$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setByteArrayValue(bytes);
         }
      }
   }

   public void xsetBytesArray(BytesType[] bytesArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(bytesArray, BYTES$16);
      }
   }

   public void xsetBytesArray(int i, BytesType bytes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BytesType target = null;
         target = (BytesType)this.get_store().find_element_user(BYTES$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(bytes);
         }
      }
   }

   public void insertBytes(int i, byte[] bytes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(BYTES$16, i);
         target.setByteArrayValue(bytes);
      }
   }

   public void addBytes(byte[] bytes) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(BYTES$16);
         target.setByteArrayValue(bytes);
      }
   }

   public BytesType insertNewBytes(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BytesType target = null;
         target = (BytesType)this.get_store().insert_element_user(BYTES$16, i);
         return target;
      }
   }

   public BytesType addNewBytes() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BytesType target = null;
         target = (BytesType)this.get_store().add_element_user(BYTES$16);
         return target;
      }
   }

   public void removeBytes(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BYTES$16, i);
      }
   }

   public String[] getCharArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CHAR$18, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getCharArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CHAR$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public CharType[] xgetCharArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CHAR$18, targetList);
         CharType[] result = new CharType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CharType xgetCharArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharType target = null;
         target = (CharType)this.get_store().find_element_user(CHAR$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCharArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHAR$18);
      }
   }

   public void setCharArray(String[] xcharArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xcharArray, CHAR$18);
      }
   }

   public void setCharArray(int i, String xchar) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CHAR$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(xchar);
         }
      }
   }

   public void xsetCharArray(CharType[] xcharArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(xcharArray, CHAR$18);
      }
   }

   public void xsetCharArray(int i, CharType xchar) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharType target = null;
         target = (CharType)this.get_store().find_element_user(CHAR$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(xchar);
         }
      }
   }

   public void insertChar(int i, String xchar) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(CHAR$18, i);
         target.setStringValue(xchar);
      }
   }

   public void addChar(String xchar) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(CHAR$18);
         target.setStringValue(xchar);
      }
   }

   public CharType insertNewChar(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharType target = null;
         target = (CharType)this.get_store().insert_element_user(CHAR$18, i);
         return target;
      }
   }

   public CharType addNewChar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CharType target = null;
         target = (CharType)this.get_store().add_element_user(CHAR$18);
         return target;
      }
   }

   public void removeChar(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHAR$18, i);
      }
   }
}
