package org.glassfish.tyrus.core.uri.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UriTemplateParser {
   static final int[] EMPTY_INT_ARRAY = new int[0];
   private static final Set RESERVED_REGEX_CHARACTERS = initReserved();
   private static final Pattern TEMPLATE_VALUE_PATTERN = Pattern.compile("[^/]+?");
   private final String template;
   private final StringBuffer regex = new StringBuffer();
   private final StringBuffer normalizedTemplate = new StringBuffer();
   private final StringBuffer literalCharactersBuffer = new StringBuffer();
   private final Pattern pattern;
   private final List names = new ArrayList();
   private final List groupCounts = new ArrayList();
   private final Map nameToPattern = new HashMap();
   private int numOfExplicitRegexes;
   private int skipGroup;
   private int literalCharacters;
   private static final String[] HEX_TO_UPPERCASE_REGEX = initHexToUpperCaseRegex();

   private static Set initReserved() {
      char[] reserved = new char[]{'.', '^', '&', '!', '?', '-', ':', '<', '(', '[', '$', '=', ')', ']', ',', '>', '*', '+', '|'};
      Set s = new HashSet(reserved.length);
      char[] var2 = reserved;
      int var3 = reserved.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char c = var2[var4];
         s.add(c);
      }

      return s;
   }

   public UriTemplateParser(String template) throws IllegalArgumentException {
      if (template != null && !template.isEmpty()) {
         this.template = template;
         this.parse(new CharacterIterator(template));

         try {
            this.pattern = Pattern.compile(this.regex.toString());
         } catch (PatternSyntaxException var3) {
            throw new IllegalArgumentException("Invalid syntax for the template expression '" + this.regex + "'", var3);
         }
      } else {
         throw new IllegalArgumentException("Template is null or has zero length");
      }
   }

   public final String getTemplate() {
      return this.template;
   }

   public final Pattern getPattern() {
      return this.pattern;
   }

   public final String getNormalizedTemplate() {
      return this.normalizedTemplate.toString();
   }

   public final Map getNameToPattern() {
      return this.nameToPattern;
   }

   public final List getNames() {
      return this.names;
   }

   public final List getGroupCounts() {
      return this.groupCounts;
   }

   public final int[] getGroupIndexes() {
      if (this.names.isEmpty()) {
         return EMPTY_INT_ARRAY;
      } else {
         int[] indexes = new int[this.names.size()];
         indexes[0] = 0 + (Integer)this.groupCounts.get(0);

         for(int i = 1; i < indexes.length; ++i) {
            indexes[i] = indexes[i - 1] + (Integer)this.groupCounts.get(i);
         }

         return indexes;
      }
   }

   public final int getNumberOfExplicitRegexes() {
      return this.numOfExplicitRegexes;
   }

   public final int getNumberOfRegexGroups() {
      if (this.groupCounts.isEmpty()) {
         return 0;
      } else {
         int[] groupIndex = this.getGroupIndexes();
         return groupIndex[groupIndex.length - 1] + this.skipGroup;
      }
   }

   public final int getNumberOfLiteralCharacters() {
      return this.literalCharacters;
   }

   protected String encodeLiteralCharacters(String characters) {
      return characters;
   }

   private void parse(CharacterIterator ci) {
      try {
         while(ci.hasNext()) {
            char c = ci.next();
            if (c == '{') {
               this.processLiteralCharacters();
               this.skipGroup = this.parseName(ci, this.skipGroup);
            } else {
               this.literalCharactersBuffer.append(c);
            }
         }

         this.processLiteralCharacters();
      } catch (NoSuchElementException var3) {
         throw new IllegalArgumentException("Invalid syntax for the template, \"" + this.template + "\". Check if a path parameter is terminated with a '}'.", var3);
      }
   }

   private void processLiteralCharacters() {
      if (this.literalCharactersBuffer.length() > 0) {
         this.literalCharacters += this.literalCharactersBuffer.length();
         String s = this.encodeLiteralCharacters(this.literalCharactersBuffer.toString());
         this.normalizedTemplate.append(s);

         for(int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (RESERVED_REGEX_CHARACTERS.contains(c)) {
               this.regex.append("\\");
               this.regex.append(c);
            } else if (c == '%') {
               char c1 = s.charAt(i + 1);
               char c2 = s.charAt(i + 2);
               if (UriComponent.isHexCharacter(c1) && UriComponent.isHexCharacter(c2)) {
                  this.regex.append("%").append(HEX_TO_UPPERCASE_REGEX[c1]).append(HEX_TO_UPPERCASE_REGEX[c2]);
                  i += 2;
               }
            } else {
               this.regex.append(c);
            }
         }

         this.literalCharactersBuffer.setLength(0);
      }

   }

   private static String[] initHexToUpperCaseRegex() {
      String[] table = new String[128];

      for(int i = 0; i < table.length; ++i) {
         table[i] = String.valueOf((char)i);
      }

      char c;
      for(c = 'a'; c <= 'f'; ++c) {
         table[c] = "[" + c + (char)(c - 97 + 65) + "]";
      }

      for(c = 'A'; c <= 'F'; ++c) {
         table[c] = "[" + (char)(c - 65 + 97) + c + "]";
      }

      return table;
   }

   private int parseName(CharacterIterator ci, int skipGroup) {
      char c = this.consumeWhiteSpace(ci);
      char paramType = 'p';
      StringBuilder nameBuffer = new StringBuilder();
      if (c == '?' || c == ';') {
         paramType = c;
         c = ci.next();
      }

      if (!Character.isLetterOrDigit(c) && c != '_') {
         throw new IllegalArgumentException("Illegal character '" + c + "' at position " + ci.pos() + " is not as the start of a name");
      } else {
         nameBuffer.append(c);
         String nameRegexString = "";

         while(true) {
            while(true) {
               c = ci.next();
               if (!Character.isLetterOrDigit(c) && c != '_' && c != '-' && c != '.') {
                  if (c != ',' || paramType == 'p') {
                     if (c == ':' && paramType == 'p') {
                        nameRegexString = this.parseRegex(ci);
                     } else if (c != '}') {
                        if (c != ' ') {
                           throw new IllegalArgumentException("Illegal character '" + c + "' at position " + ci.pos() + " is not allowed as part of a name");
                        }

                        c = this.consumeWhiteSpace(ci);
                        if (c == ':') {
                           nameRegexString = this.parseRegex(ci);
                        } else if (c != '}') {
                           throw new IllegalArgumentException("Illegal character '" + c + "' at position " + ci.pos() + " is not allowed after a name");
                        }
                     }

                     String name = nameBuffer.toString();

                     try {
                        Pattern namePattern;
                        if (paramType != '?' && paramType != ';') {
                           this.names.add(name);
                           if (!nameRegexString.isEmpty()) {
                              ++this.numOfExplicitRegexes;
                           }

                           namePattern = nameRegexString.isEmpty() ? TEMPLATE_VALUE_PATTERN : Pattern.compile(nameRegexString);
                           if (this.nameToPattern.containsKey(name)) {
                              if (!((Pattern)this.nameToPattern.get(name)).equals(namePattern)) {
                                 throw new IllegalArgumentException("The name '" + name + "' is declared more than once with different regular expressions");
                              }
                           } else {
                              this.nameToPattern.put(name, namePattern);
                           }

                           Matcher m = namePattern.matcher("");
                           int g = m.groupCount();
                           this.groupCounts.add(1 + skipGroup);
                           skipGroup = g;
                        } else {
                           String[] subNames = name.split(",\\s?");
                           StringBuilder regexBuilder = new StringBuilder(paramType == '?' ? "\\?" : ";");
                           String separator = paramType == '?' ? "\\&" : ";/\\?";
                           boolean first = true;
                           regexBuilder.append("(");
                           String[] var13 = subNames;
                           int var14 = subNames.length;

                           for(int var15 = 0; var15 < var14; ++var15) {
                              String subName = var13[var15];
                              regexBuilder.append("(&?");
                              regexBuilder.append(subName);
                              regexBuilder.append("(=([^");
                              regexBuilder.append(separator);
                              regexBuilder.append("]*))?");
                              regexBuilder.append(")");
                              if (!first) {
                                 regexBuilder.append("|");
                              }

                              this.names.add(subName);
                              this.groupCounts.add(first ? 5 : 3);
                              first = false;
                           }

                           skipGroup = 1;
                           regexBuilder.append(")*");
                           namePattern = Pattern.compile(regexBuilder.toString());
                           name = paramType + name;
                        }

                        this.regex.append('(').append(namePattern).append(')');
                        this.normalizedTemplate.append('{').append(name).append('}');
                        return skipGroup;
                     } catch (PatternSyntaxException var17) {
                        throw new IllegalArgumentException("Invalid syntax for the expression '" + nameRegexString + "' associated with the name '" + name + "'", var17);
                     }
                  }

                  nameBuffer.append(c);
               } else {
                  nameBuffer.append(c);
               }
            }
         }
      }
   }

   private String parseRegex(CharacterIterator ci) {
      StringBuilder regexBuffer = new StringBuilder();
      int braceCount = 1;

      while(true) {
         char c = ci.next();
         if (c == '{') {
            ++braceCount;
         } else if (c == '}') {
            --braceCount;
            if (braceCount == 0) {
               return regexBuffer.toString().trim();
            }
         }

         regexBuffer.append(c);
      }
   }

   private char consumeWhiteSpace(CharacterIterator ci) {
      char c;
      do {
         c = ci.next();
      } while(Character.isWhitespace(c));

      return c;
   }
}
