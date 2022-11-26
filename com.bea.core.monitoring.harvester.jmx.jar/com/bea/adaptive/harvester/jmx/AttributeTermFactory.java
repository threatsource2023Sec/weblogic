package com.bea.adaptive.harvester.jmx;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

class AttributeTermFactory {
   protected static HarvesterJMXTextTextFormatter mtf_base = HarvesterJMXTextTextFormatter.getInstance();

   static String parseXstr(String expr, int index, String problem) {
      return mtf_base.getExprParseErr(expr, index, problem);
   }

   static AttributeTerm parseAttributeChain(String expr) {
      AttributeTerm currTerm = null;
      boolean inEscape = false;
      char enclosureStartChar = '0';
      int enclosureStartCharIndex = -1;
      StringBuffer termData = new StringBuffer(expr.length());
      StringBuffer rawTermData = new StringBuffer(expr.length());
      boolean inNamedTerm = true;

      for(int i = 0; i < expr.length(); ++i) {
         char c = expr.charAt(i);
         if (!inEscape) {
            if (c == '\\') {
               inEscape = true;
               rawTermData.append(c);
               continue;
            }

            String termStr;
            if (enclosureStartChar == '(') {
               if (c == ')') {
                  termStr = null;
                  String rawTermString = rawTermData.toString().trim();
                  boolean isMultipleKeys = isMultipleKeys(rawTermString);
                  if (isMultipleKeys) {
                     String[] keys = splitKeys(rawTermString);
                     currTerm = new AttributeTerm.MapTerm((AttributeTerm)currTerm, keys);
                  } else {
                     termStr = termData.toString().trim();
                     if (termStr.length() == 0) {
                        throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrMissingKeyValue()));
                     }

                     currTerm = new AttributeTerm.MapTerm((AttributeTerm)currTerm, termStr, false);
                  }

                  enclosureStartChar = '0';
                  enclosureStartCharIndex = -1;
                  continue;
               }
            } else if (enclosureStartChar == '{') {
               if (c == '}') {
                  termStr = termData.toString().trim();
                  if (termStr.length() == 0) {
                     throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrMissingKeyValue()));
                  }

                  try {
                     currTerm = new AttributeTerm.MapTerm((AttributeTerm)currTerm, termStr, true);
                  } catch (PatternSyntaxException var15) {
                     throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseRegexCompilerErr(termStr, var15.getMessage())));
                  }

                  enclosureStartChar = '0';
                  enclosureStartCharIndex = -1;
                  continue;
               }
            } else if (enclosureStartChar == '[') {
               if (c == ']') {
                  termStr = termData.toString().trim();
                  if (termStr.length() == 0) {
                     throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrMissingOrderedIndexValue()));
                  }

                  try {
                     currTerm = new AttributeTerm.ArrayTerm((AttributeTerm)currTerm, termStr);
                  } catch (NumberFormatException var14) {
                     throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrOrderedIndexValueInvalid(expr.substring(enclosureStartCharIndex + 1, i))));
                  }

                  enclosureStartChar = '0';
                  enclosureStartCharIndex = -1;
                  continue;
               }
            } else {
               if (c == ')' || c == '}' || c == ']') {
                  throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrNoEnclosureStart(c)));
               }

               int remainingBufferLen;
               if (c == '.') {
                  if (inNamedTerm) {
                     if (termData.length() == 0) {
                        throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrEmptyAttr()));
                     }

                     currTerm = new AttributeTerm.SimpleTerm(termData.toString(), (AttributeTerm)currTerm);
                  } else if (enclosureStartChar != '0') {
                     throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrUnterminatedEnclosure(enclosureStartChar, enclosureStartCharIndex)));
                  }

                  remainingBufferLen = expr.length() - i - 1;
                  rawTermData = new StringBuffer(remainingBufferLen);
                  termData = new StringBuffer(remainingBufferLen);
                  inNamedTerm = true;
                  continue;
               }

               if (c == '[' || c == '(' || c == '{') {
                  if (inNamedTerm) {
                     if (termData.length() == 0) {
                        throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrEmptyAttr()));
                     }

                     currTerm = new AttributeTerm.SimpleTerm(termData.toString(), (AttributeTerm)currTerm);
                     inNamedTerm = false;
                  } else if (enclosureStartChar != '0') {
                     throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrNestedEnclosure(enclosureStartCharIndex)));
                  }

                  remainingBufferLen = expr.length() - i - 1;
                  rawTermData = new StringBuffer(remainingBufferLen);
                  termData = new StringBuffer(remainingBufferLen);
                  enclosureStartChar = c;
                  enclosureStartCharIndex = i;
                  continue;
               }
            }
         } else {
            inEscape = false;
         }

         if (!inNamedTerm && enclosureStartChar == '0') {
            throw new RuntimeException(parseXstr(expr, i, mtf_base.getExprParseErrSpuriousChars(c)));
         }

         termData.append(c);
         rawTermData.append(c);
      }

      if (inNamedTerm) {
         if (termData.length() == 0) {
            throw new RuntimeException(parseXstr(expr, expr.length(), mtf_base.getExprParseErrEmptyAttr()));
         }

         currTerm = new AttributeTerm.SimpleTerm(termData.toString(), (AttributeTerm)currTerm);
      } else if (enclosureStartChar != '0') {
         throw new RuntimeException(parseXstr(expr, expr.length(), mtf_base.getExprParseErrUnterminatedEnclosure(enclosureStartChar, enclosureStartCharIndex)));
      }

      return ((AttributeTerm)currTerm).getRoot();
   }

   private static String[] splitKeys(String s) {
      List r = new ArrayList();
      char prev = 0;
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < s.length(); ++i) {
         char current = s.charAt(i);
         if (prev != '\\' && current == ',') {
            if (sb.length() > 0) {
               r.add(sb.toString());
            }

            sb = new StringBuilder();
         } else if (current != '\\') {
            sb.append(current);
         }

         prev = current;
      }

      if (sb.length() > 0) {
         r.add(sb.toString());
      }

      return (String[])r.toArray(new String[r.size()]);
   }

   static boolean isMultipleKeys(String keyVal) {
      if (keyVal == null) {
         return false;
      } else {
         int len = keyVal.length();

         for(int i = 0; i < len; ++i) {
            char c = keyVal.charAt(i);
            if (c == ',' && (i <= 0 || keyVal.charAt(i - 1) != '\\')) {
               return true;
            }
         }

         return false;
      }
   }
}
