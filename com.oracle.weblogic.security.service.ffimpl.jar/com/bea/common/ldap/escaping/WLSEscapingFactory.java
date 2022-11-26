package com.bea.common.ldap.escaping;

public class WLSEscapingFactory implements EscapingFactory {
   protected static final char[] SPECIAL_CHARS = new char[]{'@', '|', '&', '!', '=', '<', '>', '~', '(', ')', '*', ':', ',', ';', ' ', '"', '\'', '\t', '\\', '+', '/'};

   public Escaping getEscaping() {
      return new WLSEscaping(SPECIAL_CHARS);
   }
}
