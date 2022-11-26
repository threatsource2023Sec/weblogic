package org.python.modules._csv;

public enum QuoteStyle {
   QUOTE_MINIMAL,
   QUOTE_ALL,
   QUOTE_NONNUMERIC,
   QUOTE_NONE;

   public static QuoteStyle fromOrdinal(int ordinal) {
      return ordinal >= 0 && ordinal < values().length ? values()[ordinal] : null;
   }
}
