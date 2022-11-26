package org.apache.velocity.app.tools;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import org.apache.velocity.context.Context;

public class VelocityFormatter {
   Context context = null;
   NumberFormat nf = NumberFormat.getInstance();

   public VelocityFormatter(Context context) {
      this.context = context;
   }

   public String formatShortDate(Date date) {
      return DateFormat.getDateInstance(3).format(date);
   }

   public String formatLongDate(Date date) {
      return DateFormat.getDateInstance(1).format(date);
   }

   public String formatShortDateTime(Date date) {
      return DateFormat.getDateTimeInstance(3, 3).format(date);
   }

   public String formatLongDateTime(Date date) {
      return DateFormat.getDateTimeInstance(1, 1).format(date);
   }

   public String formatArray(Object array) {
      return this.formatArray(array, ", ", " and ");
   }

   public String formatArray(Object array, String delim) {
      return this.formatArray(array, delim, delim);
   }

   public String formatArray(Object array, String delim, String finaldelim) {
      StringBuffer sb = new StringBuffer();
      int arrayLen = Array.getLength(array);

      for(int i = 0; i < arrayLen; ++i) {
         sb.append(Array.get(array, i).toString());
         if (i < arrayLen - 2) {
            sb.append(delim);
         } else if (i < arrayLen - 1) {
            sb.append(finaldelim);
         }
      }

      return sb.toString();
   }

   public String formatVector(List list) {
      return this.formatVector(list, ", ", " and ");
   }

   public String formatVector(List list, String delim) {
      return this.formatVector(list, delim, delim);
   }

   public String formatVector(List list, String delim, String finaldelim) {
      StringBuffer sb = new StringBuffer();
      int size = list.size();

      for(int i = 0; i < size; ++i) {
         sb.append(list.get(i));
         if (i < size - 2) {
            sb.append(delim);
         } else if (i < size - 1) {
            sb.append(finaldelim);
         }
      }

      return sb.toString();
   }

   public String limitLen(int maxlen, String string) {
      return this.limitLen(maxlen, string, "...");
   }

   public String limitLen(int maxlen, String string, String suffix) {
      String ret = string;
      if (string.length() > maxlen) {
         ret = string.substring(0, maxlen - suffix.length()) + suffix;
      }

      return ret;
   }

   public String makeAlternator(String name, String alt1, String alt2) {
      String[] alternates = new String[]{alt1, alt2};
      this.context.put(name, new VelocityAlternator(alternates));
      return "";
   }

   public String makeAlternator(String name, String alt1, String alt2, String alt3) {
      String[] alternates = new String[]{alt1, alt2, alt3};
      this.context.put(name, new VelocityAlternator(alternates));
      return "";
   }

   public String makeAlternator(String name, String alt1, String alt2, String alt3, String alt4) {
      String[] alternates = new String[]{alt1, alt2, alt3, alt4};
      this.context.put(name, new VelocityAlternator(alternates));
      return "";
   }

   public String makeAutoAlternator(String name, String alt1, String alt2) {
      String[] alternates = new String[]{alt1, alt2};
      this.context.put(name, new VelocityAutoAlternator(alternates));
      return "";
   }

   public Object isNull(Object o, Object dflt) {
      return o == null ? dflt : o;
   }

   public class VelocityAutoAlternator extends VelocityAlternator {
      public VelocityAutoAlternator(String[] alternates) {
         super(alternates);
      }

      public final String toString() {
         String s = super.alternates[super.current];
         this.alternate();
         return s;
      }
   }

   public class VelocityAlternator {
      protected String[] alternates = null;
      protected int current = 0;

      public VelocityAlternator(String[] alternates) {
         this.alternates = alternates;
      }

      public String alternate() {
         ++this.current;
         this.current %= this.alternates.length;
         return "";
      }

      public String toString() {
         return this.alternates[this.current];
      }
   }
}
