package weblogic.application.internal.library.util;

import java.io.Serializable;

public class DeweyDecimal implements Comparable, Serializable {
   private static final long serialVersionUID = -1547973439851595348L;
   private static final String FORMAT = "long(.long)*, long >=0";
   private static final String FORMAT_ERROR = "DeweyDecimal must be of format: long(.long)*, long >=0";
   private final long[] decimals;
   private final String stringRepr;

   public DeweyDecimal(Float f) {
      this(String.valueOf(f));
   }

   public DeweyDecimal(String s) {
      this.validate(s);
      this.decimals = this.init(s);
      this.stringRepr = this.decimalsToString();
   }

   private void validate(String s) {
      if (s == null) {
         throw new IllegalArgumentException("DeweyDecimal cannot be constructed from null String");
      } else if (s.startsWith(".") || s.endsWith(".")) {
         throw new NumberFormatException("DeweyDecimal must be of format: long(.long)*, long >=0");
      }
   }

   private long[] init(String in) {
      String s = in;
      int di = in.lastIndexOf(".");
      if (di == -1) {
         return new long[]{this.parseLong(in)};
      } else {
         int ai = 0;
         int last = in.length();

         long[] decimals;
         for(decimals = null; di > -1; --ai) {
            long dec = this.parseLong(s.substring(di + 1, last));
            if (dec != 0L || decimals != null) {
               if (decimals == null) {
                  ai = this.countNumDots(s);
                  decimals = new long[ai + 1];
               }

               decimals[ai] = dec;
            }

            s = s.substring(0, di);
            last = di;
            di = s.lastIndexOf(".");
         }

         if (decimals == null) {
            decimals = new long[1];
         }

         decimals[0] = this.parseLong(s);
         return decimals;
      }
   }

   private long parseLong(String s) {
      long i = Long.parseLong(s);
      if (i < 0L) {
         throw new NumberFormatException(i + " " + "DeweyDecimal must be of format: long(.long)*, long >=0");
      } else {
         return i;
      }
   }

   private int countNumDots(String s) {
      int rtn = 0;

      for(int i = 0; i < s.length(); ++i) {
         if (s.charAt(i) == '.') {
            ++rtn;
         }
      }

      return rtn;
   }

   private String decimalsToString() {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.decimals.length; ++i) {
         sb.append(this.decimals[i]);
         if (i < this.decimals.length - 1) {
            sb.append(".");
         }
      }

      return sb.toString();
   }

   public int hashCode() {
      return this.stringRepr.hashCode();
   }

   public boolean equals(Object o) {
      return !(o instanceof DeweyDecimal) ? false : this.equalsInternal(o);
   }

   public String toString() {
      return this.stringRepr;
   }

   private long[] getDecimals() {
      return this.decimals;
   }

   private boolean equalsInternal(Object o) {
      return this.hashCode() == o.hashCode();
   }

   public int compareTo(DeweyDecimal o) {
      if (this.equalsInternal(o)) {
         return 0;
      } else {
         for(int i = 0; i != o.getDecimals().length; ++i) {
            if (i == this.getDecimals().length) {
               return -1;
            }

            if (this.getDecimals()[i] > o.getDecimals()[i]) {
               return 1;
            }

            if (this.getDecimals()[i] < o.getDecimals()[i]) {
               return -1;
            }
         }

         return 1;
      }
   }
}
