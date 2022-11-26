package com.bea.staxb.types;

import com.bea.xml.GDate;
import com.bea.xml.GDateSpecification;
import com.bea.xml.XmlCalendar;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.namespace.QName;

public class XMLGregorianCalendar extends XmlCalendar {
   public static final QName QNAME = new QName("http://www.w3.org/2001/XMLSchema", "dateTime");
   private BigDecimal fractionalSecond;
   private static final BigDecimal DECIMAL_ZERO = BigDecimal.valueOf(0L);
   private static final BigDecimal DECIMAL_ONE = BigDecimal.valueOf(1L);

   public XMLGregorianCalendar(Date date) {
      super(date);
   }

   public XMLGregorianCalendar(String xmlSchemaDateString) {
      this((GDateSpecification)(new GDate(xmlSchemaDateString)));
   }

   public XMLGregorianCalendar(GDateSpecification date) {
      super(date);
      this.fractionalSecond = date.getFraction();
   }

   public String toString() {
      String str = (new GDate(this)).toString();
      if (this.fractionalSecond != null && this.fractionalSecond.scale() > 3) {
         char[] oldChars = str.toCharArray();
         char[] newChars = new char[oldChars.length + this.fractionalSecond.scale() - 3];
         int endOfMillisecond = str.indexOf(".") + 4;
         System.arraycopy(oldChars, 0, newChars, 0, endOfMillisecond);
         String frac = this.fractionalSecond.toString();
         frac.getChars(frac.indexOf(".") + 4, frac.length(), newChars, endOfMillisecond);
         System.arraycopy(oldChars, endOfMillisecond, newChars, endOfMillisecond + this.fractionalSecond.scale() - 3, oldChars.length - endOfMillisecond);
         str = new String(newChars);
      }

      return str;
   }

   public void set(int field, int value) {
      super.set(field, value);
      if (14 == field) {
         if (value == Integer.MIN_VALUE) {
            this.fractionalSecond = null;
         } else {
            this.fractionalSecond = (new BigDecimal(value)).movePointLeft(3);
         }
      }

   }

   public int get(int field) {
      if (14 == field) {
         return this.fractionalSecond == null ? Integer.MIN_VALUE : this.fractionalSecond.movePointRight(3).intValue();
      } else {
         return super.get(field);
      }
   }

   public BigDecimal getFractionalSecond() {
      return this.fractionalSecond;
   }

   public void setFractionalSecond(BigDecimal bigdecimal) {
      if (bigdecimal == null || bigdecimal.compareTo(DECIMAL_ZERO) >= 0 && bigdecimal.compareTo(DECIMAL_ONE) <= 0) {
         super.set(14, this.fractionalSecond.movePointRight(3).intValue());
         this.fractionalSecond = bigdecimal;
      } else {
         throw new IllegalArgumentException("InvalidFractional' " + bigdecimal + "'");
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         if (!super.equals(o)) {
            return false;
         } else {
            XMLGregorianCalendar that = (XMLGregorianCalendar)o;
            if (this.fractionalSecond != null) {
               if (!this.fractionalSecond.equals(that.fractionalSecond)) {
                  return false;
               }
            } else if (that.fractionalSecond != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 29 * result + (this.fractionalSecond != null ? this.fractionalSecond.hashCode() : 0);
      return result;
   }
}
