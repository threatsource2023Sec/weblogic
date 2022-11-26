package org.python.google.common.base;

import java.nio.charset.Charset;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;

@GwtCompatible(
   emulated = true
)
public final class Charsets {
   @GwtIncompatible
   public static final Charset US_ASCII = Charset.forName("US-ASCII");
   public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
   public static final Charset UTF_8 = Charset.forName("UTF-8");
   @GwtIncompatible
   public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
   @GwtIncompatible
   public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
   @GwtIncompatible
   public static final Charset UTF_16 = Charset.forName("UTF-16");

   private Charsets() {
   }
}
