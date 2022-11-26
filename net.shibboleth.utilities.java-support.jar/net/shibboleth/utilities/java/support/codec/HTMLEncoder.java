package net.shibboleth.utilities.java.support.codec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class HTMLEncoder {
   @Nonnull
   public static final char[] IMMUNE_HTML = new char[]{',', '.', '-', '_', ' '};
   @Nonnull
   public static final char[] IMMUNE_HTMLATTR = new char[]{',', '.', '-', '_'};
   @Nonnull
   public static final char REPLACEMENT_CHAR = '�';
   @Nonnull
   public static final String REPLACEMENT_HEX = "fffd";
   @Nonnull
   private static final Map CHARACTER_TO_ENTITY_MAP = mkCharacterToEntityMap();
   @Nonnull
   private static final String[] HEX = new String[256];

   private HTMLEncoder() {
   }

   @Nullable
   public static String encodeForHTML(@Nullable String input) {
      return input == null ? null : encode(IMMUNE_HTML, input);
   }

   @Nullable
   public static String encodeForHTMLAttribute(@Nullable String input) {
      return input == null ? null : encode(IMMUNE_HTMLATTR, input);
   }

   @Nonnull
   private static String encode(@Nonnull char[] immune, @Nonnull String input) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < input.length(); ++i) {
         char c = input.charAt(i);
         sb.append(encodeCharacter(immune, c));
      }

      return sb.toString();
   }

   @Nonnull
   private static String encodeCharacter(@Nonnull char[] immune, @Nonnull Character toEncode) {
      Character c = toEncode;
      if (containsCharacter(toEncode, immune)) {
         return "" + toEncode;
      } else {
         String hex = getHexForNonAlphanumeric(toEncode);
         if (hex == null) {
            return "" + toEncode;
         } else {
            if (toEncode <= 31 && toEncode != '\t' && toEncode != '\n' && toEncode != '\r' || toEncode >= 127 && toEncode <= 159) {
               hex = "fffd";
               c = '�';
            }

            String entityName = (String)CHARACTER_TO_ENTITY_MAP.get(c);
            return entityName != null ? "&" + entityName + ";" : "&#x" + hex + ";";
         }
      }
   }

   @Nonnull
   private static String getHexForNonAlphanumeric(@Nonnull char c) {
      return c < 255 ? HEX[c] : Integer.toHexString(c);
   }

   private static boolean containsCharacter(@Nonnull char c, @Nonnull char[] array) {
      char[] arr$ = array;
      int len$ = array.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         char ch = arr$[i$];
         if (c == ch) {
            return true;
         }
      }

      return false;
   }

   @Nonnull
   private static synchronized Map mkCharacterToEntityMap() {
      Map map = new HashMap(252);
      map.put('"', "quot");
      map.put('&', "amp");
      map.put('<', "lt");
      map.put('>', "gt");
      map.put(' ', "nbsp");
      map.put('¡', "iexcl");
      map.put('¢', "cent");
      map.put('£', "pound");
      map.put('¤', "curren");
      map.put('¥', "yen");
      map.put('¦', "brvbar");
      map.put('§', "sect");
      map.put('¨', "uml");
      map.put('©', "copy");
      map.put('ª', "ordf");
      map.put('«', "laquo");
      map.put('¬', "not");
      map.put('\u00ad', "shy");
      map.put('®', "reg");
      map.put('¯', "macr");
      map.put('°', "deg");
      map.put('±', "plusmn");
      map.put('²', "sup2");
      map.put('³', "sup3");
      map.put('´', "acute");
      map.put('µ', "micro");
      map.put('¶', "para");
      map.put('·', "middot");
      map.put('¸', "cedil");
      map.put('¹', "sup1");
      map.put('º', "ordm");
      map.put('»', "raquo");
      map.put('¼', "frac14");
      map.put('½', "frac12");
      map.put('¾', "frac34");
      map.put('¿', "iquest");
      map.put('À', "Agrave");
      map.put('Á', "Aacute");
      map.put('Â', "Acirc");
      map.put('Ã', "Atilde");
      map.put('Ä', "Auml");
      map.put('Å', "Aring");
      map.put('Æ', "AElig");
      map.put('Ç', "Ccedil");
      map.put('È', "Egrave");
      map.put('É', "Eacute");
      map.put('Ê', "Ecirc");
      map.put('Ë', "Euml");
      map.put('Ì', "Igrave");
      map.put('Í', "Iacute");
      map.put('Î', "Icirc");
      map.put('Ï', "Iuml");
      map.put('Ð', "ETH");
      map.put('Ñ', "Ntilde");
      map.put('Ò', "Ograve");
      map.put('Ó', "Oacute");
      map.put('Ô', "Ocirc");
      map.put('Õ', "Otilde");
      map.put('Ö', "Ouml");
      map.put('×', "times");
      map.put('Ø', "Oslash");
      map.put('Ù', "Ugrave");
      map.put('Ú', "Uacute");
      map.put('Û', "Ucirc");
      map.put('Ü', "Uuml");
      map.put('Ý', "Yacute");
      map.put('Þ', "THORN");
      map.put('ß', "szlig");
      map.put('à', "agrave");
      map.put('á', "aacute");
      map.put('â', "acirc");
      map.put('ã', "atilde");
      map.put('ä', "auml");
      map.put('å', "aring");
      map.put('æ', "aelig");
      map.put('ç', "ccedil");
      map.put('è', "egrave");
      map.put('é', "eacute");
      map.put('ê', "ecirc");
      map.put('ë', "euml");
      map.put('ì', "igrave");
      map.put('í', "iacute");
      map.put('î', "icirc");
      map.put('ï', "iuml");
      map.put('ð', "eth");
      map.put('ñ', "ntilde");
      map.put('ò', "ograve");
      map.put('ó', "oacute");
      map.put('ô', "ocirc");
      map.put('õ', "otilde");
      map.put('ö', "ouml");
      map.put('÷', "divide");
      map.put('ø', "oslash");
      map.put('ù', "ugrave");
      map.put('ú', "uacute");
      map.put('û', "ucirc");
      map.put('ü', "uuml");
      map.put('ý', "yacute");
      map.put('þ', "thorn");
      map.put('ÿ', "yuml");
      map.put('Œ', "OElig");
      map.put('œ', "oelig");
      map.put('Š', "Scaron");
      map.put('š', "scaron");
      map.put('Ÿ', "Yuml");
      map.put('ƒ', "fnof");
      map.put('ˆ', "circ");
      map.put('˜', "tilde");
      map.put('Α', "Alpha");
      map.put('Β', "Beta");
      map.put('Γ', "Gamma");
      map.put('Δ', "Delta");
      map.put('Ε', "Epsilon");
      map.put('Ζ', "Zeta");
      map.put('Η', "Eta");
      map.put('Θ', "Theta");
      map.put('Ι', "Iota");
      map.put('Κ', "Kappa");
      map.put('Λ', "Lambda");
      map.put('Μ', "Mu");
      map.put('Ν', "Nu");
      map.put('Ξ', "Xi");
      map.put('Ο', "Omicron");
      map.put('Π', "Pi");
      map.put('Ρ', "Rho");
      map.put('Σ', "Sigma");
      map.put('Τ', "Tau");
      map.put('Υ', "Upsilon");
      map.put('Φ', "Phi");
      map.put('Χ', "Chi");
      map.put('Ψ', "Psi");
      map.put('Ω', "Omega");
      map.put('α', "alpha");
      map.put('β', "beta");
      map.put('γ', "gamma");
      map.put('δ', "delta");
      map.put('ε', "epsilon");
      map.put('ζ', "zeta");
      map.put('η', "eta");
      map.put('θ', "theta");
      map.put('ι', "iota");
      map.put('κ', "kappa");
      map.put('λ', "lambda");
      map.put('μ', "mu");
      map.put('ν', "nu");
      map.put('ξ', "xi");
      map.put('ο', "omicron");
      map.put('π', "pi");
      map.put('ρ', "rho");
      map.put('ς', "sigmaf");
      map.put('σ', "sigma");
      map.put('τ', "tau");
      map.put('υ', "upsilon");
      map.put('φ', "phi");
      map.put('χ', "chi");
      map.put('ψ', "psi");
      map.put('ω', "omega");
      map.put('ϑ', "thetasym");
      map.put('ϒ', "upsih");
      map.put('ϖ', "piv");
      map.put(' ', "ensp");
      map.put(' ', "emsp");
      map.put(' ', "thinsp");
      map.put('\u200c', "zwnj");
      map.put('\u200d', "zwj");
      map.put('\u200e', "lrm");
      map.put('\u200f', "rlm");
      map.put('–', "ndash");
      map.put('—', "mdash");
      map.put('‘', "lsquo");
      map.put('’', "rsquo");
      map.put('‚', "sbquo");
      map.put('“', "ldquo");
      map.put('”', "rdquo");
      map.put('„', "bdquo");
      map.put('†', "dagger");
      map.put('‡', "Dagger");
      map.put('•', "bull");
      map.put('…', "hellip");
      map.put('‰', "permil");
      map.put('′', "prime");
      map.put('″', "Prime");
      map.put('‹', "lsaquo");
      map.put('›', "rsaquo");
      map.put('‾', "oline");
      map.put('⁄', "frasl");
      map.put('€', "euro");
      map.put('ℑ', "image");
      map.put('℘', "weierp");
      map.put('ℜ', "real");
      map.put('™', "trade");
      map.put('ℵ', "alefsym");
      map.put('←', "larr");
      map.put('↑', "uarr");
      map.put('→', "rarr");
      map.put('↓', "darr");
      map.put('↔', "harr");
      map.put('↵', "crarr");
      map.put('⇐', "lArr");
      map.put('⇑', "uArr");
      map.put('⇒', "rArr");
      map.put('⇓', "dArr");
      map.put('⇔', "hArr");
      map.put('∀', "forall");
      map.put('∂', "part");
      map.put('∃', "exist");
      map.put('∅', "empty");
      map.put('∇', "nabla");
      map.put('∈', "isin");
      map.put('∉', "notin");
      map.put('∋', "ni");
      map.put('∏', "prod");
      map.put('∑', "sum");
      map.put('−', "minus");
      map.put('∗', "lowast");
      map.put('√', "radic");
      map.put('∝', "prop");
      map.put('∞', "infin");
      map.put('∠', "ang");
      map.put('∧', "and");
      map.put('∨', "or");
      map.put('∩', "cap");
      map.put('∪', "cup");
      map.put('∫', "int");
      map.put('∴', "there4");
      map.put('∼', "sim");
      map.put('≅', "cong");
      map.put('≈', "asymp");
      map.put('≠', "ne");
      map.put('≡', "equiv");
      map.put('≤', "le");
      map.put('≥', "ge");
      map.put('⊂', "sub");
      map.put('⊃', "sup");
      map.put('⊄', "nsub");
      map.put('⊆', "sube");
      map.put('⊇', "supe");
      map.put('⊕', "oplus");
      map.put('⊗', "otimes");
      map.put('⊥', "perp");
      map.put('⋅', "sdot");
      map.put('⌈', "lceil");
      map.put('⌉', "rceil");
      map.put('⌊', "lfloor");
      map.put('⌋', "rfloor");
      map.put('〈', "lang");
      map.put('〉', "rang");
      map.put('◊', "loz");
      map.put('♠', "spades");
      map.put('♣', "clubs");
      map.put('♥', "hearts");
      map.put('♦', "diams");
      return Collections.unmodifiableMap(map);
   }

   static {
      for(char c = 0; c < 255; ++c) {
         if ((c < '0' || c > '9') && (c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
            HEX[c] = Integer.toHexString(c).intern();
         } else {
            HEX[c] = null;
         }
      }

   }
}
