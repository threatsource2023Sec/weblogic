package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.BooleanType;
import com.bea.wls.jms.message.ByteType;
import com.bea.wls.jms.message.DoubleType;
import com.bea.wls.jms.message.FloatType;
import com.bea.wls.jms.message.IntType;
import com.bea.wls.jms.message.LongType;
import com.bea.wls.jms.message.PropertyType;
import com.bea.wls.jms.message.ShortType;
import com.bea.wls.jms.message.StringType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class PropertyTypeImpl extends XmlComplexContentImpl implements PropertyType {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTY$0 = new QName("http://www.bea.com/WLS/JMS/Message", "property");

   public PropertyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public PropertyType.Property[] getPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTY$0, targetList);
         PropertyType.Property[] result = new PropertyType.Property[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PropertyType.Property getPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType.Property target = null;
         target = (PropertyType.Property)this.get_store().find_element_user(PROPERTY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTY$0);
      }
   }

   public void setPropertyArray(PropertyType.Property[] propertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertyArray, PROPERTY$0);
   }

   public void setPropertyArray(int i, PropertyType.Property property) {
      this.generatedSetterHelperImpl(property, PROPERTY$0, i, (short)2);
   }

   public PropertyType.Property insertNewProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType.Property target = null;
         target = (PropertyType.Property)this.get_store().insert_element_user(PROPERTY$0, i);
         return target;
      }
   }

   public PropertyType.Property addNewProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertyType.Property target = null;
         target = (PropertyType.Property)this.get_store().add_element_user(PROPERTY$0);
         return target;
      }
   }

   public void removeProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTY$0, i);
      }
   }

   public static class PropertyImpl extends XmlComplexContentImpl implements PropertyType.Property {
      private static final long serialVersionUID = 1L;
      private static final QName STRING$0 = new QName("http://www.bea.com/WLS/JMS/Message", "String");
      private static final QName LONG$2 = new QName("http://www.bea.com/WLS/JMS/Message", "Long");
      private static final QName SHORT$4 = new QName("http://www.bea.com/WLS/JMS/Message", "Short");
      private static final QName INT$6 = new QName("http://www.bea.com/WLS/JMS/Message", "Int");
      private static final QName FLOAT$8 = new QName("http://www.bea.com/WLS/JMS/Message", "Float");
      private static final QName DOUBLE$10 = new QName("http://www.bea.com/WLS/JMS/Message", "Double");
      private static final QName BYTE$12 = new QName("http://www.bea.com/WLS/JMS/Message", "Byte");
      private static final QName BOOLEAN$14 = new QName("http://www.bea.com/WLS/JMS/Message", "Boolean");
      private static final QName NAME$16 = new QName("", "name");

      public PropertyImpl(SchemaType sType) {
         super(sType);
      }

      public String getString() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(STRING$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public StringType xgetString() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            StringType target = null;
            target = (StringType)this.get_store().find_element_user(STRING$0, 0);
            return target;
         }
      }

      public boolean isSetString() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(STRING$0) != 0;
         }
      }

      public void setString(String string) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(STRING$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(STRING$0);
            }

            target.setStringValue(string);
         }
      }

      public void xsetString(StringType string) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            StringType target = null;
            target = (StringType)this.get_store().find_element_user(STRING$0, 0);
            if (target == null) {
               target = (StringType)this.get_store().add_element_user(STRING$0);
            }

            target.set(string);
         }
      }

      public void unsetString() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(STRING$0, 0);
         }
      }

      public long getLong() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(LONG$2, 0);
            return target == null ? 0L : target.getLongValue();
         }
      }

      public LongType xgetLong() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LongType target = null;
            target = (LongType)this.get_store().find_element_user(LONG$2, 0);
            return target;
         }
      }

      public boolean isSetLong() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(LONG$2) != 0;
         }
      }

      public void setLong(long xlong) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(LONG$2, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(LONG$2);
            }

            target.setLongValue(xlong);
         }
      }

      public void xsetLong(LongType xlong) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            LongType target = null;
            target = (LongType)this.get_store().find_element_user(LONG$2, 0);
            if (target == null) {
               target = (LongType)this.get_store().add_element_user(LONG$2);
            }

            target.set(xlong);
         }
      }

      public void unsetLong() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(LONG$2, 0);
         }
      }

      public short getShort() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(SHORT$4, 0);
            return target == null ? 0 : target.getShortValue();
         }
      }

      public ShortType xgetShort() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ShortType target = null;
            target = (ShortType)this.get_store().find_element_user(SHORT$4, 0);
            return target;
         }
      }

      public boolean isSetShort() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(SHORT$4) != 0;
         }
      }

      public void setShort(short xshort) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(SHORT$4, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(SHORT$4);
            }

            target.setShortValue(xshort);
         }
      }

      public void xsetShort(ShortType xshort) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ShortType target = null;
            target = (ShortType)this.get_store().find_element_user(SHORT$4, 0);
            if (target == null) {
               target = (ShortType)this.get_store().add_element_user(SHORT$4);
            }

            target.set(xshort);
         }
      }

      public void unsetShort() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(SHORT$4, 0);
         }
      }

      public BigInteger getInt() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(INT$6, 0);
            return target == null ? null : target.getBigIntegerValue();
         }
      }

      public IntType xgetInt() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            IntType target = null;
            target = (IntType)this.get_store().find_element_user(INT$6, 0);
            return target;
         }
      }

      public boolean isSetInt() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(INT$6) != 0;
         }
      }

      public void setInt(BigInteger xint) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(INT$6, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(INT$6);
            }

            target.setBigIntegerValue(xint);
         }
      }

      public void xsetInt(IntType xint) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            IntType target = null;
            target = (IntType)this.get_store().find_element_user(INT$6, 0);
            if (target == null) {
               target = (IntType)this.get_store().add_element_user(INT$6);
            }

            target.set(xint);
         }
      }

      public void unsetInt() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(INT$6, 0);
         }
      }

      public float getFloat() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(FLOAT$8, 0);
            return target == null ? 0.0F : target.getFloatValue();
         }
      }

      public FloatType xgetFloat() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FloatType target = null;
            target = (FloatType)this.get_store().find_element_user(FLOAT$8, 0);
            return target;
         }
      }

      public boolean isSetFloat() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(FLOAT$8) != 0;
         }
      }

      public void setFloat(float xfloat) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(FLOAT$8, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(FLOAT$8);
            }

            target.setFloatValue(xfloat);
         }
      }

      public void xsetFloat(FloatType xfloat) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            FloatType target = null;
            target = (FloatType)this.get_store().find_element_user(FLOAT$8, 0);
            if (target == null) {
               target = (FloatType)this.get_store().add_element_user(FLOAT$8);
            }

            target.set(xfloat);
         }
      }

      public void unsetFloat() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(FLOAT$8, 0);
         }
      }

      public double getDouble() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DOUBLE$10, 0);
            return target == null ? 0.0 : target.getDoubleValue();
         }
      }

      public DoubleType xgetDouble() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DoubleType target = null;
            target = (DoubleType)this.get_store().find_element_user(DOUBLE$10, 0);
            return target;
         }
      }

      public boolean isSetDouble() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(DOUBLE$10) != 0;
         }
      }

      public void setDouble(double xdouble) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DOUBLE$10, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(DOUBLE$10);
            }

            target.setDoubleValue(xdouble);
         }
      }

      public void xsetDouble(DoubleType xdouble) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            DoubleType target = null;
            target = (DoubleType)this.get_store().find_element_user(DOUBLE$10, 0);
            if (target == null) {
               target = (DoubleType)this.get_store().add_element_user(DOUBLE$10);
            }

            target.set(xdouble);
         }
      }

      public void unsetDouble() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(DOUBLE$10, 0);
         }
      }

      public byte getByte() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(BYTE$12, 0);
            return target == null ? 0 : target.getByteValue();
         }
      }

      public ByteType xgetByte() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ByteType target = null;
            target = (ByteType)this.get_store().find_element_user(BYTE$12, 0);
            return target;
         }
      }

      public boolean isSetByte() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(BYTE$12) != 0;
         }
      }

      public void setByte(byte xbyte) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(BYTE$12, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(BYTE$12);
            }

            target.setByteValue(xbyte);
         }
      }

      public void xsetByte(ByteType xbyte) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ByteType target = null;
            target = (ByteType)this.get_store().find_element_user(BYTE$12, 0);
            if (target == null) {
               target = (ByteType)this.get_store().add_element_user(BYTE$12);
            }

            target.set(xbyte);
         }
      }

      public void unsetByte() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(BYTE$12, 0);
         }
      }

      public boolean getBoolean() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(BOOLEAN$14, 0);
            return target == null ? false : target.getBooleanValue();
         }
      }

      public BooleanType xgetBoolean() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            BooleanType target = null;
            target = (BooleanType)this.get_store().find_element_user(BOOLEAN$14, 0);
            return target;
         }
      }

      public boolean isSetBoolean() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(BOOLEAN$14) != 0;
         }
      }

      public void setBoolean(boolean xboolean) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(BOOLEAN$14, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(BOOLEAN$14);
            }

            target.setBooleanValue(xboolean);
         }
      }

      public void xsetBoolean(BooleanType xboolean) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            BooleanType target = null;
            target = (BooleanType)this.get_store().find_element_user(BOOLEAN$14, 0);
            if (target == null) {
               target = (BooleanType)this.get_store().add_element_user(BOOLEAN$14);
            }

            target.set(xboolean);
         }
      }

      public void unsetBoolean() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(BOOLEAN$14, 0);
         }
      }

      public String getName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$16);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(NAME$16);
            return target;
         }
      }

      public void setName(String name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$16);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(NAME$16);
            }

            target.setStringValue(name);
         }
      }

      public void xsetName(XmlString name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(NAME$16);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(NAME$16);
            }

            target.set(name);
         }
      }
   }
}
