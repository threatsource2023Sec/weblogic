package org.glassfish.grizzly.http.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

public class FastDateFormat extends DateFormat {
   private static final long serialVersionUID = -1L;
   final DateFormat df;
   long lastSec = -1L;
   final StringBuffer sb = new StringBuffer();
   final transient FieldPosition fp = new FieldPosition(8);

   public FastDateFormat(DateFormat df) {
      this.df = df;
   }

   public Date parse(String text, ParsePosition pos) {
      return this.df.parse(text, pos);
   }

   public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
      long dt = date.getTime();
      long ds = dt / 1000L;
      if (ds != this.lastSec) {
         this.sb.setLength(0);
         this.df.format(date, this.sb, this.fp);
         this.lastSec = ds;
      } else {
         int ms = (int)(dt % 1000L);
         int pos = this.fp.getEndIndex();
         int begin = this.fp.getBeginIndex();
         if (pos > 0) {
            if (pos > begin) {
               --pos;
               this.sb.setCharAt(pos, Character.forDigit(ms % 10, 10));
            }

            ms /= 10;
            if (pos > begin) {
               --pos;
               this.sb.setCharAt(pos, Character.forDigit(ms % 10, 10));
            }

            ms /= 10;
            if (pos > begin) {
               --pos;
               this.sb.setCharAt(pos, Character.forDigit(ms % 10, 10));
            }
         }
      }

      toAppendTo.append(this.sb.toString());
      return toAppendTo;
   }

   public StringBuilder format(Date date, StringBuilder toAppendTo, FieldPosition fieldPosition) {
      long dt = date.getTime();
      long ds = dt / 1000L;
      if (ds != this.lastSec) {
         this.sb.setLength(0);
         this.df.format(date, this.sb, this.fp);
         this.lastSec = ds;
      } else {
         int ms = (int)(dt % 1000L);
         int pos = this.fp.getEndIndex();
         int begin = this.fp.getBeginIndex();
         if (pos > 0) {
            if (pos > begin) {
               --pos;
               this.sb.setCharAt(pos, Character.forDigit(ms % 10, 10));
            }

            ms /= 10;
            if (pos > begin) {
               --pos;
               this.sb.setCharAt(pos, Character.forDigit(ms % 10, 10));
            }

            ms /= 10;
            if (pos > begin) {
               --pos;
               this.sb.setCharAt(pos, Character.forDigit(ms % 10, 10));
            }
         }
      }

      toAppendTo.append(this.sb.toString());
      return toAppendTo;
   }
}
