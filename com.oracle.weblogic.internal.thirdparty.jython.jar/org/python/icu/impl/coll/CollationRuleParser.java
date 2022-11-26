package org.python.icu.impl.coll;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import org.python.icu.impl.IllegalIcuArgumentException;
import org.python.icu.impl.PatternProps;
import org.python.icu.lang.UCharacter;
import org.python.icu.text.Normalizer2;
import org.python.icu.text.UTF16;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.ULocale;

public final class CollationRuleParser {
   static final Position[] POSITION_VALUES = CollationRuleParser.Position.values();
   static final char POS_LEAD = '\ufffe';
   static final char POS_BASE = '⠀';
   private static final int UCOL_DEFAULT = -1;
   private static final int UCOL_OFF = 0;
   private static final int UCOL_ON = 1;
   private static final int STRENGTH_MASK = 15;
   private static final int STARRED_FLAG = 16;
   private static final int OFFSET_SHIFT = 8;
   private static final String BEFORE = "[before";
   private final StringBuilder rawBuilder = new StringBuilder();
   private static final String[] positions = new String[]{"first tertiary ignorable", "last tertiary ignorable", "first secondary ignorable", "last secondary ignorable", "first primary ignorable", "last primary ignorable", "first variable", "last variable", "first regular", "last regular", "first implicit", "last implicit", "first trailing", "last trailing"};
   private static final String[] gSpecialReorderCodes = new String[]{"space", "punct", "symbol", "currency", "digit"};
   private static final int U_PARSE_CONTEXT_LEN = 16;
   private Normalizer2 nfd = Normalizer2.getNFDInstance();
   private Normalizer2 nfc = Normalizer2.getNFCInstance();
   private String rules;
   private final CollationData baseData;
   private CollationSettings settings;
   private Sink sink;
   private Importer importer;
   private int ruleIndex;

   CollationRuleParser(CollationData base) {
      this.baseData = base;
   }

   void setSink(Sink sinkAlias) {
      this.sink = sinkAlias;
   }

   void setImporter(Importer importerAlias) {
      this.importer = importerAlias;
   }

   void parse(String ruleString, CollationSettings outSettings) throws ParseException {
      this.settings = outSettings;
      this.parse(ruleString);
   }

   private void parse(String ruleString) throws ParseException {
      this.rules = ruleString;
      this.ruleIndex = 0;

      while(this.ruleIndex < this.rules.length()) {
         char c = this.rules.charAt(this.ruleIndex);
         if (PatternProps.isWhiteSpace(c)) {
            ++this.ruleIndex;
         } else {
            switch (c) {
               case '!':
                  ++this.ruleIndex;
                  break;
               case '#':
                  this.ruleIndex = this.skipComment(this.ruleIndex + 1);
                  break;
               case '&':
                  this.parseRuleChain();
                  break;
               case '@':
                  this.settings.setFlag(2048, true);
                  ++this.ruleIndex;
                  break;
               case '[':
                  this.parseSetting();
                  break;
               default:
                  this.setParseError("expected a reset or setting or comment");
            }
         }
      }

   }

   private void parseRuleChain() throws ParseException {
      int resetStrength = this.parseResetAndPosition();
      boolean isFirstRelation = true;

      while(true) {
         while(true) {
            int result = this.parseRelationOperator();
            if (result < 0) {
               if (this.ruleIndex >= this.rules.length() || this.rules.charAt(this.ruleIndex) != '#') {
                  if (isFirstRelation) {
                     this.setParseError("reset not followed by a relation");
                  }

                  return;
               }

               this.ruleIndex = this.skipComment(this.ruleIndex + 1);
            } else {
               int strength = result & 15;
               if (resetStrength < 15) {
                  if (isFirstRelation) {
                     if (strength != resetStrength) {
                        this.setParseError("reset-before strength differs from its first relation");
                        return;
                     }
                  } else if (strength < resetStrength) {
                     this.setParseError("reset-before strength followed by a stronger relation");
                     return;
                  }
               }

               int i = this.ruleIndex + (result >> 8);
               if ((result & 16) == 0) {
                  this.parseRelationStrings(strength, i);
               } else {
                  this.parseStarredCharacters(strength, i);
               }

               isFirstRelation = false;
            }
         }
      }
   }

   private int parseResetAndPosition() throws ParseException {
      int i = this.skipWhiteSpace(this.ruleIndex + 1);
      int j;
      char c;
      int resetStrength;
      if (this.rules.regionMatches(i, "[before", 0, "[before".length()) && (j = i + "[before".length()) < this.rules.length() && PatternProps.isWhiteSpace(this.rules.charAt(j)) && (j = this.skipWhiteSpace(j + 1)) + 1 < this.rules.length() && '1' <= (c = this.rules.charAt(j)) && c <= '3' && this.rules.charAt(j + 1) == ']') {
         resetStrength = 0 + (c - 49);
         i = this.skipWhiteSpace(j + 2);
      } else {
         resetStrength = 15;
      }

      if (i >= this.rules.length()) {
         this.setParseError("reset without position");
         return -1;
      } else {
         if (this.rules.charAt(i) == '[') {
            i = this.parseSpecialPosition(i, this.rawBuilder);
         } else {
            i = this.parseTailoringString(i, this.rawBuilder);
         }

         try {
            this.sink.addReset(resetStrength, this.rawBuilder);
         } catch (Exception var6) {
            this.setParseError("adding reset failed", var6);
            return -1;
         }

         this.ruleIndex = i;
         return resetStrength;
      }
   }

   private int parseRelationOperator() {
      this.ruleIndex = this.skipWhiteSpace(this.ruleIndex);
      if (this.ruleIndex >= this.rules.length()) {
         return -1;
      } else {
         int i = this.ruleIndex;
         char c = this.rules.charAt(i++);
         int strength;
         switch (c) {
            case ',':
               strength = 2;
               break;
            case ';':
               strength = 1;
               break;
            case '<':
               if (i < this.rules.length() && this.rules.charAt(i) == '<') {
                  ++i;
                  if (i < this.rules.length() && this.rules.charAt(i) == '<') {
                     ++i;
                     if (i < this.rules.length() && this.rules.charAt(i) == '<') {
                        ++i;
                        strength = 3;
                     } else {
                        strength = 2;
                     }
                  } else {
                     strength = 1;
                  }
               } else {
                  strength = 0;
               }

               if (i < this.rules.length() && this.rules.charAt(i) == '*') {
                  ++i;
                  strength |= 16;
               }
               break;
            case '=':
               strength = 15;
               if (i < this.rules.length() && this.rules.charAt(i) == '*') {
                  ++i;
                  strength |= 16;
               }
               break;
            default:
               return -1;
         }

         return i - this.ruleIndex << 8 | strength;
      }
   }

   private void parseRelationStrings(int strength, int i) throws ParseException {
      String prefix = "";
      CharSequence extension = "";
      i = this.parseTailoringString(i, this.rawBuilder);
      char next = i < this.rules.length() ? this.rules.charAt(i) : 0;
      if (next == '|') {
         prefix = this.rawBuilder.toString();
         i = this.parseTailoringString(i + 1, this.rawBuilder);
         next = i < this.rules.length() ? this.rules.charAt(i) : 0;
      }

      if (next == '/') {
         StringBuilder extBuilder = new StringBuilder();
         i = this.parseTailoringString(i + 1, extBuilder);
         extension = extBuilder;
      }

      if (prefix.length() != 0) {
         int prefix0 = prefix.codePointAt(0);
         int c = this.rawBuilder.codePointAt(0);
         if (!this.nfc.hasBoundaryBefore(prefix0) || !this.nfc.hasBoundaryBefore(c)) {
            this.setParseError("in 'prefix|str', prefix and str must each start with an NFC boundary");
            return;
         }
      }

      try {
         this.sink.addRelation(strength, prefix, this.rawBuilder, (CharSequence)extension);
      } catch (Exception var8) {
         this.setParseError("adding relation failed", var8);
         return;
      }

      this.ruleIndex = i;
   }

   private void parseStarredCharacters(int strength, int i) throws ParseException {
      String empty = "";
      i = this.parseString(this.skipWhiteSpace(i), this.rawBuilder);
      if (this.rawBuilder.length() == 0) {
         this.setParseError("missing starred-relation string");
      } else {
         int prev = -1;
         int j = 0;

         while(true) {
            int c;
            while(j < this.rawBuilder.length()) {
               c = this.rawBuilder.codePointAt(j);
               if (!this.nfd.isInert(c)) {
                  this.setParseError("starred-relation string is not all NFD-inert");
                  return;
               }

               try {
                  this.sink.addRelation(strength, empty, UTF16.valueOf(c), empty);
               } catch (Exception var9) {
                  this.setParseError("adding relation failed", var9);
                  return;
               }

               j += Character.charCount(c);
               prev = c;
            }

            if (i >= this.rules.length() || this.rules.charAt(i) != '-') {
               this.ruleIndex = this.skipWhiteSpace(i);
               return;
            }

            if (prev < 0) {
               this.setParseError("range without start in starred-relation string");
               return;
            }

            i = this.parseString(i + 1, this.rawBuilder);
            if (this.rawBuilder.length() == 0) {
               this.setParseError("range without end in starred-relation string");
               return;
            }

            c = this.rawBuilder.codePointAt(0);
            if (c < prev) {
               this.setParseError("range start greater than end in starred-relation string");
               return;
            }

            while(true) {
               ++prev;
               if (prev > c) {
                  prev = -1;
                  j = Character.charCount(c);
                  break;
               }

               if (!this.nfd.isInert(prev)) {
                  this.setParseError("starred-relation string range is not all NFD-inert");
                  return;
               }

               if (isSurrogate(prev)) {
                  this.setParseError("starred-relation string range contains a surrogate");
                  return;
               }

               if (65533 <= prev && prev <= 65535) {
                  this.setParseError("starred-relation string range contains U+FFFD, U+FFFE or U+FFFF");
                  return;
               }

               try {
                  this.sink.addRelation(strength, empty, UTF16.valueOf(prev), empty);
               } catch (Exception var8) {
                  this.setParseError("adding relation failed", var8);
                  return;
               }
            }
         }
      }
   }

   private int parseTailoringString(int i, StringBuilder raw) throws ParseException {
      i = this.parseString(this.skipWhiteSpace(i), raw);
      if (raw.length() == 0) {
         this.setParseError("missing relation string");
      }

      return this.skipWhiteSpace(i);
   }

   private int parseString(int i, StringBuilder raw) throws ParseException {
      raw.setLength(0);

      int c;
      int cp;
      label70:
      while(i < this.rules.length()) {
         c = this.rules.charAt(i++);
         if (isSyntaxChar(c)) {
            if (c == 39) {
               if (i < this.rules.length() && this.rules.charAt(i) == '\'') {
                  raw.append('\'');
                  ++i;
               } else {
                  for(; i != this.rules.length(); raw.append((char)c)) {
                     c = this.rules.charAt(i++);
                     if (c == 39) {
                        if (i >= this.rules.length() || this.rules.charAt(i) != '\'') {
                           continue label70;
                        }

                        ++i;
                     }
                  }

                  this.setParseError("quoted literal text missing terminating apostrophe");
                  return i;
               }
            } else {
               if (c != 92) {
                  --i;
                  break;
               }

               if (i == this.rules.length()) {
                  this.setParseError("backslash escape at the end of the rule string");
                  return i;
               }

               cp = this.rules.codePointAt(i);
               raw.appendCodePoint(cp);
               i += Character.charCount(cp);
            }
         } else {
            if (PatternProps.isWhiteSpace(c)) {
               --i;
               break;
            }

            raw.append((char)c);
         }
      }

      for(c = 0; c < raw.length(); c += Character.charCount(cp)) {
         cp = raw.codePointAt(c);
         if (isSurrogate(cp)) {
            this.setParseError("string contains an unpaired surrogate");
            return i;
         }

         if (65533 <= cp && cp <= 65535) {
            this.setParseError("string contains U+FFFD, U+FFFE or U+FFFF");
            return i;
         }
      }

      return i;
   }

   private static final boolean isSurrogate(int c) {
      return (c & -2048) == 55296;
   }

   private int parseSpecialPosition(int i, StringBuilder str) throws ParseException {
      int j = this.readWords(i + 1, this.rawBuilder);
      if (j > i && this.rules.charAt(j) == ']' && this.rawBuilder.length() != 0) {
         ++j;
         String raw = this.rawBuilder.toString();
         str.setLength(0);

         for(int pos = 0; pos < positions.length; ++pos) {
            if (raw.equals(positions[pos])) {
               str.append('\ufffe').append((char)(10240 + pos));
               return j;
            }
         }

         if (raw.equals("top")) {
            str.append('\ufffe').append((char)(10240 + CollationRuleParser.Position.LAST_REGULAR.ordinal()));
            return j;
         }

         if (raw.equals("variable top")) {
            str.append('\ufffe').append((char)(10240 + CollationRuleParser.Position.LAST_VARIABLE.ordinal()));
            return j;
         }
      }

      this.setParseError("not a valid special reset position");
      return i;
   }

   private void parseSetting() throws ParseException {
      int i = this.ruleIndex + 1;
      int j = this.readWords(i, this.rawBuilder);
      if (j <= i || this.rawBuilder.length() == 0) {
         this.setParseError("expected a setting/option at '['");
      }

      String raw = this.rawBuilder.toString();
      if (this.rules.charAt(j) != ']') {
         if (this.rules.charAt(j) == '[') {
            UnicodeSet set = new UnicodeSet();
            j = this.parseUnicodeSet(j, set);
            if (raw.equals("optimize")) {
               try {
                  this.sink.optimize(set);
               } catch (Exception var16) {
                  this.setParseError("[optimize set] failed", var16);
               }

               this.ruleIndex = j;
               return;
            }

            if (raw.equals("suppressContractions")) {
               try {
                  this.sink.suppressContractions(set);
               } catch (Exception var17) {
                  this.setParseError("[suppressContractions set] failed", var17);
               }

               this.ruleIndex = j;
               return;
            }
         }
      } else {
         ++j;
         if (raw.startsWith("reorder") && (raw.length() == 7 || raw.charAt(7) == ' ')) {
            this.parseReordering(raw);
            this.ruleIndex = j;
            return;
         }

         if (raw.equals("backwards 2")) {
            this.settings.setFlag(2048, true);
            this.ruleIndex = j;
            return;
         }

         int valueIndex = raw.lastIndexOf(32);
         String v;
         if (valueIndex >= 0) {
            v = raw.substring(valueIndex + 1);
            raw = raw.substring(0, valueIndex);
         } else {
            v = "";
         }

         int value;
         if (raw.equals("strength") && v.length() == 1) {
            value = -1;
            char c = v.charAt(0);
            if ('1' <= c && c <= '4') {
               value = 0 + (c - 49);
            } else if (c == 'I') {
               value = 15;
            }

            if (value != -1) {
               this.settings.setStrength(value);
               this.ruleIndex = j;
               return;
            }
         } else if (raw.equals("alternate")) {
            value = -1;
            if (v.equals("non-ignorable")) {
               value = 0;
            } else if (v.equals("shifted")) {
               value = 1;
            }

            if (value != -1) {
               this.settings.setAlternateHandlingShifted(value > 0);
               this.ruleIndex = j;
               return;
            }
         } else if (raw.equals("maxVariable")) {
            value = -1;
            if (v.equals("space")) {
               value = 0;
            } else if (v.equals("punct")) {
               value = 1;
            } else if (v.equals("symbol")) {
               value = 2;
            } else if (v.equals("currency")) {
               value = 3;
            }

            if (value != -1) {
               this.settings.setMaxVariable(value, 0);
               this.settings.variableTop = this.baseData.getLastPrimaryForGroup(4096 + value);

               assert this.settings.variableTop != 0L;

               this.ruleIndex = j;
               return;
            }
         } else if (raw.equals("caseFirst")) {
            value = -1;
            if (v.equals("off")) {
               value = 0;
            } else if (v.equals("lower")) {
               value = 512;
            } else if (v.equals("upper")) {
               value = 768;
            }

            if (value != -1) {
               this.settings.setCaseFirst(value);
               this.ruleIndex = j;
               return;
            }
         } else if (raw.equals("caseLevel")) {
            value = getOnOffValue(v);
            if (value != -1) {
               this.settings.setFlag(1024, value > 0);
               this.ruleIndex = j;
               return;
            }
         } else if (raw.equals("normalization")) {
            value = getOnOffValue(v);
            if (value != -1) {
               this.settings.setFlag(1, value > 0);
               this.ruleIndex = j;
               return;
            }
         } else if (raw.equals("numericOrdering")) {
            value = getOnOffValue(v);
            if (value != -1) {
               this.settings.setFlag(2, value > 0);
               this.ruleIndex = j;
               return;
            }
         } else if (raw.equals("hiraganaQ")) {
            value = getOnOffValue(v);
            if (value != -1) {
               if (value == 1) {
                  this.setParseError("[hiraganaQ on] is not supported");
               }

               this.ruleIndex = j;
               return;
            }
         } else if (raw.equals("import")) {
            ULocale localeID;
            try {
               localeID = (new ULocale.Builder()).setLanguageTag(v).build();
            } catch (Exception var15) {
               this.setParseError("expected language tag in [import langTag]", var15);
               return;
            }

            String baseID = localeID.getBaseName();
            String collationType = localeID.getKeywordValue("collation");
            if (this.importer == null) {
               this.setParseError("[import langTag] is not supported");
            } else {
               String importedRules;
               try {
                  importedRules = this.importer.getRules(baseID, collationType != null ? collationType : "standard");
               } catch (Exception var14) {
                  this.setParseError("[import langTag] failed", var14);
                  return;
               }

               String outerRules = this.rules;
               int outerRuleIndex = this.ruleIndex;

               try {
                  this.parse(importedRules);
               } catch (Exception var13) {
                  this.ruleIndex = outerRuleIndex;
                  this.setParseError("parsing imported rules failed", var13);
               }

               this.rules = outerRules;
               this.ruleIndex = j;
            }

            return;
         }
      }

      this.setParseError("not a valid setting/option");
   }

   private void parseReordering(CharSequence raw) throws ParseException {
      int i = 7;
      if (i == raw.length()) {
         this.settings.resetReordering();
      } else {
         ArrayList reorderCodes;
         int limit;
         for(reorderCodes = new ArrayList(); i < raw.length(); i = limit) {
            ++i;

            for(limit = i; limit < raw.length() && raw.charAt(limit) != ' '; ++limit) {
            }

            String word = raw.subSequence(i, limit).toString();
            int code = getReorderCode(word);
            if (code < 0) {
               this.setParseError("unknown script or reorder code");
               return;
            }

            reorderCodes.add(code);
         }

         if (reorderCodes.isEmpty()) {
            this.settings.resetReordering();
         } else {
            int[] codes = new int[reorderCodes.size()];
            int j = 0;

            Integer code;
            for(Iterator var10 = reorderCodes.iterator(); var10.hasNext(); codes[j++] = code) {
               code = (Integer)var10.next();
            }

            this.settings.setReordering(this.baseData, codes);
         }

      }
   }

   public static int getReorderCode(String word) {
      int script;
      for(script = 0; script < gSpecialReorderCodes.length; ++script) {
         if (word.equalsIgnoreCase(gSpecialReorderCodes[script])) {
            return 4096 + script;
         }
      }

      try {
         script = UCharacter.getPropertyValueEnum(4106, word);
         if (script >= 0) {
            return script;
         }
      } catch (IllegalIcuArgumentException var2) {
      }

      if (word.equalsIgnoreCase("others")) {
         return 103;
      } else {
         return -1;
      }
   }

   private static int getOnOffValue(String s) {
      if (s.equals("on")) {
         return 1;
      } else {
         return s.equals("off") ? 0 : -1;
      }
   }

   private int parseUnicodeSet(int i, UnicodeSet set) throws ParseException {
      int level = 0;
      int j = i;

      while(j != this.rules.length()) {
         char c = this.rules.charAt(j++);
         if (c == '[') {
            ++level;
         } else if (c == ']') {
            --level;
            if (level == 0) {
               try {
                  set.applyPattern(this.rules.substring(i, j));
               } catch (Exception var6) {
                  this.setParseError("not a valid UnicodeSet pattern: " + var6.getMessage());
               }

               j = this.skipWhiteSpace(j);
               if (j != this.rules.length() && this.rules.charAt(j) == ']') {
                  ++j;
                  return j;
               }

               this.setParseError("missing option-terminating ']' after UnicodeSet pattern");
               return j;
            }
         }
      }

      this.setParseError("unbalanced UnicodeSet pattern brackets");
      return j;
   }

   private int readWords(int i, StringBuilder raw) {
      raw.setLength(0);
      i = this.skipWhiteSpace(i);

      while(i < this.rules.length()) {
         char c = this.rules.charAt(i);
         if (isSyntaxChar(c) && c != '-' && c != '_') {
            if (raw.length() == 0) {
               return i;
            }

            int lastIndex = raw.length() - 1;
            if (raw.charAt(lastIndex) == ' ') {
               raw.setLength(lastIndex);
            }

            return i;
         }

         if (PatternProps.isWhiteSpace(c)) {
            raw.append(' ');
            i = this.skipWhiteSpace(i + 1);
         } else {
            raw.append(c);
            ++i;
         }
      }

      return 0;
   }

   private int skipComment(int i) {
      while(true) {
         if (i < this.rules.length()) {
            char c = this.rules.charAt(i++);
            if (c != '\n' && c != '\f' && c != '\r' && c != 133 && c != 8232 && c != 8233) {
               continue;
            }
         }

         return i;
      }
   }

   private void setParseError(String reason) throws ParseException {
      throw this.makeParseException(reason);
   }

   private void setParseError(String reason, Exception e) throws ParseException {
      ParseException newExc = this.makeParseException(reason + ": " + e.getMessage());
      newExc.initCause(e);
      throw newExc;
   }

   private ParseException makeParseException(String reason) {
      return new ParseException(this.appendErrorContext(reason), this.ruleIndex);
   }

   private String appendErrorContext(String reason) {
      StringBuilder msg = new StringBuilder(reason);
      msg.append(" at index ").append(this.ruleIndex);
      msg.append(" near \"");
      int start = this.ruleIndex - 15;
      if (start < 0) {
         start = 0;
      } else if (start > 0 && Character.isLowSurrogate(this.rules.charAt(start))) {
         ++start;
      }

      msg.append(this.rules, start, this.ruleIndex);
      msg.append('!');
      int length = this.rules.length() - this.ruleIndex;
      if (length >= 16) {
         length = 15;
         if (Character.isHighSurrogate(this.rules.charAt(this.ruleIndex + length - 1))) {
            --length;
         }
      }

      msg.append(this.rules, this.ruleIndex, this.ruleIndex + length);
      return msg.append('"').toString();
   }

   private static boolean isSyntaxChar(int c) {
      return 33 <= c && c <= 126 && (c <= 47 || 58 <= c && c <= 64 || 91 <= c && c <= 96 || 123 <= c);
   }

   private int skipWhiteSpace(int i) {
      while(i < this.rules.length() && PatternProps.isWhiteSpace(this.rules.charAt(i))) {
         ++i;
      }

      return i;
   }

   interface Importer {
      String getRules(String var1, String var2);
   }

   abstract static class Sink {
      abstract void addReset(int var1, CharSequence var2);

      abstract void addRelation(int var1, CharSequence var2, CharSequence var3, CharSequence var4);

      void suppressContractions(UnicodeSet set) {
      }

      void optimize(UnicodeSet set) {
      }
   }

   static enum Position {
      FIRST_TERTIARY_IGNORABLE,
      LAST_TERTIARY_IGNORABLE,
      FIRST_SECONDARY_IGNORABLE,
      LAST_SECONDARY_IGNORABLE,
      FIRST_PRIMARY_IGNORABLE,
      LAST_PRIMARY_IGNORABLE,
      FIRST_VARIABLE,
      LAST_VARIABLE,
      FIRST_REGULAR,
      LAST_REGULAR,
      FIRST_IMPLICIT,
      LAST_IMPLICIT,
      FIRST_TRAILING,
      LAST_TRAILING;
   }
}
