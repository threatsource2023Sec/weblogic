package weblogic.i18n.tools;

import java.text.MessageFormat;
import java.util.StringTokenizer;

public class CData extends Element {
   private String cdata = "";
   private String originalCData = null;
   private Object parent = null;

   public String getCdata() {
      return this.cdata;
   }

   public void setCdata(String val) {
      this.cdata = this.cdata + val;
   }

   public String getOriginalCData() {
      String retval = this.originalCData;
      if (retval == null) {
         retval = this.cdata;
      }

      return retval;
   }

   public void normalize() {
      if (this.originalCData == null) {
         this.originalCData = this.cdata;
      }

      String tstr = this.cdata.trim();
      StringTokenizer s = new StringTokenizer(tstr, "\n\r\f");
      StringBuilder sb = new StringBuilder();

      while(s.hasMoreTokens()) {
         sb.append(s.nextToken());
         sb.append(" ");
      }

      tstr = sb.toString();
      this.cdata = "";
      int len = tstr.length();

      for(int i = 0; i < len - 2; ++i) {
         if (!tstr.substring(i, i + 2).equals("  ")) {
            this.cdata = this.cdata + tstr.charAt(i);
         }
      }

      if (len > 0) {
         this.cdata = this.cdata + tstr.substring(len - 2, len - 1);
      }

   }

   public Object getParent() {
      return this.parent;
   }

   public void setParent(Object o) {
      this.parent = o;
   }

   public void validateCdata() throws CdataException {
      try {
         new MessageFormat(this.getCdata());
      } catch (IllegalArgumentException var2) {
         throw new CdataException(var2.getMessage());
      }
   }
}
