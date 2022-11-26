package org.python.icu.text;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import org.python.icu.impl.IDNA2003;
import org.python.icu.impl.UTS46;

public abstract class IDNA {
   public static final int DEFAULT = 0;
   /** @deprecated */
   @Deprecated
   public static final int ALLOW_UNASSIGNED = 1;
   public static final int USE_STD3_RULES = 2;
   public static final int CHECK_BIDI = 4;
   public static final int CHECK_CONTEXTJ = 8;
   public static final int NONTRANSITIONAL_TO_ASCII = 16;
   public static final int NONTRANSITIONAL_TO_UNICODE = 32;
   public static final int CHECK_CONTEXTO = 64;

   public static IDNA getUTS46Instance(int options) {
      return new UTS46(options);
   }

   public abstract StringBuilder labelToASCII(CharSequence var1, StringBuilder var2, Info var3);

   public abstract StringBuilder labelToUnicode(CharSequence var1, StringBuilder var2, Info var3);

   public abstract StringBuilder nameToASCII(CharSequence var1, StringBuilder var2, Info var3);

   public abstract StringBuilder nameToUnicode(CharSequence var1, StringBuilder var2, Info var3);

   /** @deprecated */
   @Deprecated
   protected static void resetInfo(Info info) {
      info.reset();
   }

   /** @deprecated */
   @Deprecated
   protected static boolean hasCertainErrors(Info info, EnumSet errors) {
      return !info.errors.isEmpty() && !Collections.disjoint(info.errors, errors);
   }

   /** @deprecated */
   @Deprecated
   protected static boolean hasCertainLabelErrors(Info info, EnumSet errors) {
      return !info.labelErrors.isEmpty() && !Collections.disjoint(info.labelErrors, errors);
   }

   /** @deprecated */
   @Deprecated
   protected static void addLabelError(Info info, Error error) {
      info.labelErrors.add(error);
   }

   /** @deprecated */
   @Deprecated
   protected static void promoteAndResetLabelErrors(Info info) {
      if (!info.labelErrors.isEmpty()) {
         info.errors.addAll(info.labelErrors);
         info.labelErrors.clear();
      }

   }

   /** @deprecated */
   @Deprecated
   protected static void addError(Info info, Error error) {
      info.errors.add(error);
   }

   /** @deprecated */
   @Deprecated
   protected static void setTransitionalDifferent(Info info) {
      info.isTransDiff = true;
   }

   /** @deprecated */
   @Deprecated
   protected static void setBiDi(Info info) {
      info.isBiDi = true;
   }

   /** @deprecated */
   @Deprecated
   protected static boolean isBiDi(Info info) {
      return info.isBiDi;
   }

   /** @deprecated */
   @Deprecated
   protected static void setNotOkBiDi(Info info) {
      info.isOkBiDi = false;
   }

   /** @deprecated */
   @Deprecated
   protected static boolean isOkBiDi(Info info) {
      return info.isOkBiDi;
   }

   /** @deprecated */
   @Deprecated
   protected IDNA() {
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertToASCII(String src, int options) throws StringPrepParseException {
      UCharacterIterator iter = UCharacterIterator.getInstance(src);
      return convertToASCII(iter, options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertToASCII(StringBuffer src, int options) throws StringPrepParseException {
      UCharacterIterator iter = UCharacterIterator.getInstance(src);
      return convertToASCII(iter, options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertToASCII(UCharacterIterator src, int options) throws StringPrepParseException {
      return IDNA2003.convertToASCII(src, options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertIDNToASCII(UCharacterIterator src, int options) throws StringPrepParseException {
      return convertIDNToASCII(src.getText(), options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertIDNToASCII(StringBuffer src, int options) throws StringPrepParseException {
      return convertIDNToASCII(src.toString(), options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertIDNToASCII(String src, int options) throws StringPrepParseException {
      return IDNA2003.convertIDNToASCII(src, options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertToUnicode(String src, int options) throws StringPrepParseException {
      UCharacterIterator iter = UCharacterIterator.getInstance(src);
      return convertToUnicode(iter, options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertToUnicode(StringBuffer src, int options) throws StringPrepParseException {
      UCharacterIterator iter = UCharacterIterator.getInstance(src);
      return convertToUnicode(iter, options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertToUnicode(UCharacterIterator src, int options) throws StringPrepParseException {
      return IDNA2003.convertToUnicode(src, options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertIDNToUnicode(UCharacterIterator src, int options) throws StringPrepParseException {
      return convertIDNToUnicode(src.getText(), options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertIDNToUnicode(StringBuffer src, int options) throws StringPrepParseException {
      return convertIDNToUnicode(src.toString(), options);
   }

   /** @deprecated */
   @Deprecated
   public static StringBuffer convertIDNToUnicode(String src, int options) throws StringPrepParseException {
      return IDNA2003.convertIDNToUnicode(src, options);
   }

   /** @deprecated */
   @Deprecated
   public static int compare(StringBuffer s1, StringBuffer s2, int options) throws StringPrepParseException {
      if (s1 != null && s2 != null) {
         return IDNA2003.compare(s1.toString(), s2.toString(), options);
      } else {
         throw new IllegalArgumentException("One of the source buffers is null");
      }
   }

   /** @deprecated */
   @Deprecated
   public static int compare(String s1, String s2, int options) throws StringPrepParseException {
      if (s1 != null && s2 != null) {
         return IDNA2003.compare(s1, s2, options);
      } else {
         throw new IllegalArgumentException("One of the source buffers is null");
      }
   }

   /** @deprecated */
   @Deprecated
   public static int compare(UCharacterIterator s1, UCharacterIterator s2, int options) throws StringPrepParseException {
      if (s1 != null && s2 != null) {
         return IDNA2003.compare(s1.getText(), s2.getText(), options);
      } else {
         throw new IllegalArgumentException("One of the source buffers is null");
      }
   }

   public static enum Error {
      EMPTY_LABEL,
      LABEL_TOO_LONG,
      DOMAIN_NAME_TOO_LONG,
      LEADING_HYPHEN,
      TRAILING_HYPHEN,
      HYPHEN_3_4,
      LEADING_COMBINING_MARK,
      DISALLOWED,
      PUNYCODE,
      LABEL_HAS_DOT,
      INVALID_ACE_LABEL,
      BIDI,
      CONTEXTJ,
      CONTEXTO_PUNCTUATION,
      CONTEXTO_DIGITS;
   }

   public static final class Info {
      private EnumSet errors = EnumSet.noneOf(Error.class);
      private EnumSet labelErrors = EnumSet.noneOf(Error.class);
      private boolean isTransDiff = false;
      private boolean isBiDi = false;
      private boolean isOkBiDi = true;

      public boolean hasErrors() {
         return !this.errors.isEmpty();
      }

      public Set getErrors() {
         return this.errors;
      }

      public boolean isTransitionalDifferent() {
         return this.isTransDiff;
      }

      private void reset() {
         this.errors.clear();
         this.labelErrors.clear();
         this.isTransDiff = false;
         this.isBiDi = false;
         this.isOkBiDi = true;
      }
   }
}
