package com.bea.common.ldap.escaping;

public class XACMLEscapingFactory implements EscapingFactory {
   private static final char[] SPECIAL_CHARS = new char[]{'@', '|', '&', '!', '=', '<', '>', '~', '(', ')', '*', ':', ',', ';', ' ', '"', '\'', '\t', '\\', '+', '/', '{', '}', '#', '%', '^'};

   public Escaping getEscaping() {
      return new WLSEscaping(SPECIAL_CHARS);
   }
}
