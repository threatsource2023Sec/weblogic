package org.python.bouncycastle.i18n;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.TimeZone;

public class LocaleString extends LocalizedMessage {
   public LocaleString(String var1, String var2) {
      super(var1, var2);
   }

   public LocaleString(String var1, String var2, String var3) throws NullPointerException, UnsupportedEncodingException {
      super(var1, var2, var3);
   }

   public LocaleString(String var1, String var2, String var3, Object[] var4) throws NullPointerException, UnsupportedEncodingException {
      super(var1, var2, var3, var4);
   }

   public String getLocaleString(Locale var1) {
      return this.getEntry((String)null, var1, (TimeZone)null);
   }
}
