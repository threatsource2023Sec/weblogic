package org.python.icu.text;

import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.python.icu.impl.IllegalIcuArgumentException;
import org.python.icu.impl.PatternProps;
import org.python.icu.impl.Utility;
import org.python.icu.lang.UCharacter;

class TransliteratorParser {
   public List dataVector;
   public List idBlockVector;
   private RuleBasedTransliterator.Data curData;
   public UnicodeSet compoundFilter;
   private int direction;
   private ParseData parseData;
   private List variablesVector;
   private Map variableNames;
   private StringBuffer segmentStandins;
   private List segmentObjects;
   private char variableNext;
   private char variableLimit;
   private String undefinedVariableName;
   private int dotStandIn = -1;
   private static final String ID_TOKEN = "::";
   private static final int ID_TOKEN_LEN = 2;
   private static final char VARIABLE_DEF_OP = '=';
   private static final char FORWARD_RULE_OP = '>';
   private static final char REVERSE_RULE_OP = '<';
   private static final char FWDREV_RULE_OP = '~';
   private static final String OPERATORS = "=><←→↔";
   private static final String HALF_ENDERS = "=><←→↔;";
   private static final char QUOTE = '\'';
   private static final char ESCAPE = '\\';
   private static final char END_OF_RULE = ';';
   private static final char RULE_COMMENT_CHAR = '#';
   private static final char CONTEXT_ANTE = '{';
   private static final char CONTEXT_POST = '}';
   private static final char CURSOR_POS = '|';
   private static final char CURSOR_OFFSET = '@';
   private static final char ANCHOR_START = '^';
   private static final char KLEENE_STAR = '*';
   private static final char ONE_OR_MORE = '+';
   private static final char ZERO_OR_ONE = '?';
   private static final char DOT = '.';
   private static final String DOT_SET = "[^[:Zp:][:Zl:]\\r\\n$]";
   private static final char SEGMENT_OPEN = '(';
   private static final char SEGMENT_CLOSE = ')';
   private static final char FUNCTION = '&';
   private static final char ALT_REVERSE_RULE_OP = '←';
   private static final char ALT_FORWARD_RULE_OP = '→';
   private static final char ALT_FWDREV_RULE_OP = '↔';
   private static final char ALT_FUNCTION = '∆';
   private static UnicodeSet ILLEGAL_TOP = new UnicodeSet("[\\)]");
   private static UnicodeSet ILLEGAL_SEG = new UnicodeSet("[\\{\\}\\|\\@]");
   private static UnicodeSet ILLEGAL_FUNC = new UnicodeSet("[\\^\\(\\.\\*\\+\\?\\{\\}\\|\\@]");

   public TransliteratorParser() {
   }

   public void parse(String rules, int dir) {
      this.parseRules(new RuleArray(new String[]{rules}), dir);
   }

   void parseRules(RuleBody ruleArray, int dir) {
      boolean parsingIDs = true;
      int ruleCount = 0;
      this.dataVector = new ArrayList();
      this.idBlockVector = new ArrayList();
      this.curData = null;
      this.direction = dir;
      this.compoundFilter = null;
      this.variablesVector = new ArrayList();
      this.variableNames = new HashMap();
      this.parseData = new ParseData();
      List errors = new ArrayList();
      int errorCount = 0;
      ruleArray.reset();
      StringBuilder idBlockResult = new StringBuilder();
      this.compoundFilter = null;
      int compoundFilterOffset = -1;

      label219:
      while(true) {
         String rule = ruleArray.nextLine();
         if (rule == null) {
            break;
         }

         int pos = 0;
         int limit = rule.length();

         while(true) {
            while(true) {
               char c;
               do {
                  if (pos >= limit) {
                     continue label219;
                  }

                  c = rule.charAt(pos++);
               } while(PatternProps.isWhiteSpace(c));

               if (c == '#') {
                  pos = rule.indexOf("\n", pos) + 1;
                  if (pos == 0) {
                     continue label219;
                  }
               } else if (c != ';') {
                  try {
                     ++ruleCount;
                     --pos;
                     if (pos + 2 + 1 <= limit && rule.regionMatches(pos, "::", 0, 2)) {
                        pos += 2;

                        for(c = rule.charAt(pos); PatternProps.isWhiteSpace(c) && pos < limit; c = rule.charAt(pos)) {
                           ++pos;
                        }

                        int[] p = new int[]{pos};
                        if (!parsingIDs) {
                           if (this.curData != null) {
                              if (this.direction == 0) {
                                 this.dataVector.add(this.curData);
                              } else {
                                 this.dataVector.add(0, this.curData);
                              }

                              this.curData = null;
                           }

                           parsingIDs = true;
                        }

                        TransliteratorIDParser.SingleID id = TransliteratorIDParser.parseSingleID(rule, p, this.direction);
                        if (p[0] != pos && Utility.parseChar(rule, p, ';')) {
                           if (this.direction == 0) {
                              idBlockResult.append(id.canonID).append(';');
                           } else {
                              idBlockResult.insert(0, id.canonID + ';');
                           }
                        } else {
                           int[] withParens = new int[]{-1};
                           UnicodeSet f = TransliteratorIDParser.parseGlobalFilter(rule, p, this.direction, withParens, (StringBuffer)null);
                           if (f != null && Utility.parseChar(rule, p, ';')) {
                              if (this.direction == 0 == (withParens[0] == 0)) {
                                 if (this.compoundFilter != null) {
                                    syntaxError("Multiple global filters", rule, pos);
                                 }

                                 this.compoundFilter = f;
                                 compoundFilterOffset = ruleCount;
                              }
                           } else {
                              syntaxError("Invalid ::ID", rule, pos);
                           }
                        }

                        pos = p[0];
                     } else {
                        if (parsingIDs) {
                           if (this.direction == 0) {
                              this.idBlockVector.add(idBlockResult.toString());
                           } else {
                              this.idBlockVector.add(0, idBlockResult.toString());
                           }

                           idBlockResult.delete(0, idBlockResult.length());
                           parsingIDs = false;
                           this.curData = new RuleBasedTransliterator.Data();
                           this.setVariableRange(61440, 63743);
                        }

                        if (resemblesPragma(rule, pos, limit)) {
                           int ppp = this.parsePragma(rule, pos, limit);
                           if (ppp < 0) {
                              syntaxError("Unrecognized pragma", rule, pos);
                           }

                           pos = ppp;
                        } else {
                           pos = this.parseRule(rule, pos, limit);
                        }
                     }
                  } catch (IllegalArgumentException var18) {
                     if (errorCount == 30) {
                        IllegalIcuArgumentException icuEx = new IllegalIcuArgumentException("\nMore than 30 errors; further messages squelched");
                        icuEx.initCause(var18);
                        errors.add(icuEx);
                        break label219;
                     }

                     var18.fillInStackTrace();
                     errors.add(var18);
                     ++errorCount;
                     pos = ruleEnd(rule, pos, limit) + 1;
                  }
               }
            }
         }
      }

      if (parsingIDs && idBlockResult.length() > 0) {
         if (this.direction == 0) {
            this.idBlockVector.add(idBlockResult.toString());
         } else {
            this.idBlockVector.add(0, idBlockResult.toString());
         }
      } else if (!parsingIDs && this.curData != null) {
         if (this.direction == 0) {
            this.dataVector.add(this.curData);
         } else {
            this.dataVector.add(0, this.curData);
         }
      }

      int i;
      RuleBasedTransliterator.Data data;
      for(i = 0; i < this.dataVector.size(); ++i) {
         data = (RuleBasedTransliterator.Data)this.dataVector.get(i);
         data.variables = new Object[this.variablesVector.size()];
         this.variablesVector.toArray(data.variables);
         data.variableNames = new HashMap();
         data.variableNames.putAll(this.variableNames);
      }

      this.variablesVector = null;

      try {
         if (this.compoundFilter != null && (this.direction == 0 && compoundFilterOffset != 1 || this.direction == 1 && compoundFilterOffset != ruleCount)) {
            throw new IllegalIcuArgumentException("Compound filters misplaced");
         }

         for(i = 0; i < this.dataVector.size(); ++i) {
            data = (RuleBasedTransliterator.Data)this.dataVector.get(i);
            data.ruleSet.freeze();
         }

         if (this.idBlockVector.size() == 1 && ((String)this.idBlockVector.get(0)).length() == 0) {
            this.idBlockVector.remove(0);
         }
      } catch (IllegalArgumentException var17) {
         var17.fillInStackTrace();
         errors.add(var17);
      }

      if (errors.size() != 0) {
         for(i = errors.size() - 1; i > 0; --i) {
            RuntimeException previous;
            for(previous = (RuntimeException)errors.get(i - 1); previous.getCause() != null; previous = (RuntimeException)previous.getCause()) {
            }

            previous.initCause((Throwable)errors.get(i));
         }

         throw (RuntimeException)errors.get(0);
      }
   }

   private int parseRule(String rule, int pos, int limit) {
      int start;
      char operator;
      RuleHalf left;
      RuleHalf right;
      label158: {
         start = pos;
         operator = 0;
         this.segmentStandins = new StringBuffer();
         this.segmentObjects = new ArrayList();
         left = new RuleHalf();
         right = new RuleHalf();
         this.undefinedVariableName = null;
         pos = left.parse(rule, pos, limit, this);
         if (pos != limit) {
            --pos;
            if ("=><←→↔".indexOf(operator = rule.charAt(pos)) >= 0) {
               break label158;
            }
         }

         syntaxError("No operator pos=" + pos, rule, start);
      }

      ++pos;
      if (operator == '<' && pos < limit && rule.charAt(pos) == '>') {
         ++pos;
         operator = '~';
      }

      switch (operator) {
         case '←':
            operator = '<';
         case '↑':
         case '↓':
         default:
            break;
         case '→':
            operator = '>';
            break;
         case '↔':
            operator = '~';
      }

      pos = right.parse(rule, pos, limit, this);
      if (pos < limit) {
         --pos;
         if (rule.charAt(pos) == ';') {
            ++pos;
         } else {
            syntaxError("Unquoted operator", rule, start);
         }
      }

      int i;
      if (operator == '=') {
         if (this.undefinedVariableName == null) {
            syntaxError("Missing '$' or duplicate definition", rule, start);
         }

         if (left.text.length() != 1 || left.text.charAt(0) != this.variableLimit) {
            syntaxError("Malformed LHS", rule, start);
         }

         if (left.anchorStart || left.anchorEnd || right.anchorStart || right.anchorEnd) {
            syntaxError("Malformed variable def", rule, start);
         }

         i = right.text.length();
         char[] value = new char[i];
         right.text.getChars(0, i, value, 0);
         this.variableNames.put(this.undefinedVariableName, value);
         ++this.variableLimit;
         return pos;
      } else {
         if (this.undefinedVariableName != null) {
            syntaxError("Undefined variable $" + this.undefinedVariableName, rule, start);
         }

         if (this.segmentStandins.length() > this.segmentObjects.size()) {
            syntaxError("Undefined segment reference", rule, start);
         }

         for(i = 0; i < this.segmentStandins.length(); ++i) {
            if (this.segmentStandins.charAt(i) == 0) {
               syntaxError("Internal error", rule, start);
            }
         }

         for(i = 0; i < this.segmentObjects.size(); ++i) {
            if (this.segmentObjects.get(i) == null) {
               syntaxError("Internal error", rule, start);
            }
         }

         if (operator != '~' && this.direction == 0 != (operator == '>')) {
            return pos;
         } else {
            if (this.direction == 1) {
               RuleHalf temp = left;
               left = right;
               right = temp;
            }

            if (operator == '~') {
               right.removeContext();
               left.cursor = -1;
               left.cursorOffset = 0;
            }

            if (left.ante < 0) {
               left.ante = 0;
            }

            if (left.post < 0) {
               left.post = left.text.length();
            }

            if (right.ante >= 0 || right.post >= 0 || left.cursor >= 0 || right.cursorOffset != 0 && right.cursor < 0 || right.anchorStart || right.anchorEnd || !left.isValidInput(this) || !right.isValidOutput(this) || left.ante > left.post) {
               syntaxError("Malformed rule", rule, start);
            }

            UnicodeMatcher[] segmentsArray = null;
            if (this.segmentObjects.size() > 0) {
               segmentsArray = new UnicodeMatcher[this.segmentObjects.size()];
               this.segmentObjects.toArray(segmentsArray);
            }

            this.curData.ruleSet.addRule(new TransliterationRule(left.text, left.ante, left.post, right.text, right.cursor, right.cursorOffset, segmentsArray, left.anchorStart, left.anchorEnd, this.curData));
            return pos;
         }
      }
   }

   private void setVariableRange(int start, int end) {
      if (start <= end && start >= 0 && end <= 65535) {
         this.curData.variablesBase = (char)start;
         if (this.dataVector.size() == 0) {
            this.variableNext = (char)start;
            this.variableLimit = (char)(end + 1);
         }

      } else {
         throw new IllegalIcuArgumentException("Invalid variable range " + start + ", " + end);
      }
   }

   private void checkVariableRange(int ch, String rule, int start) {
      if (ch >= this.curData.variablesBase && ch < this.variableLimit) {
         syntaxError("Variable range character in rule", rule, start);
      }

   }

   private void pragmaMaximumBackup(int backup) {
      throw new IllegalIcuArgumentException("use maximum backup pragma not implemented yet");
   }

   private void pragmaNormalizeRules(Normalizer.Mode mode) {
      throw new IllegalIcuArgumentException("use normalize rules pragma not implemented yet");
   }

   static boolean resemblesPragma(String rule, int pos, int limit) {
      return Utility.parsePattern(rule, pos, limit, "use ", (int[])null) >= 0;
   }

   private int parsePragma(String rule, int pos, int limit) {
      int[] array = new int[2];
      pos += 4;
      int p = Utility.parsePattern(rule, pos, limit, "~variable range # #~;", array);
      if (p >= 0) {
         this.setVariableRange(array[0], array[1]);
         return p;
      } else {
         p = Utility.parsePattern(rule, pos, limit, "~maximum backup #~;", array);
         if (p >= 0) {
            this.pragmaMaximumBackup(array[0]);
            return p;
         } else {
            p = Utility.parsePattern(rule, pos, limit, "~nfd rules~;", (int[])null);
            if (p >= 0) {
               this.pragmaNormalizeRules(Normalizer.NFD);
               return p;
            } else {
               p = Utility.parsePattern(rule, pos, limit, "~nfc rules~;", (int[])null);
               if (p >= 0) {
                  this.pragmaNormalizeRules(Normalizer.NFC);
                  return p;
               } else {
                  return -1;
               }
            }
         }
      }
   }

   static final void syntaxError(String msg, String rule, int start) {
      int end = ruleEnd(rule, start, rule.length());
      throw new IllegalIcuArgumentException(msg + " in \"" + Utility.escape(rule.substring(start, end)) + '"');
   }

   static final int ruleEnd(String rule, int start, int limit) {
      int end = Utility.quotedIndexOf(rule, start, limit, ";");
      if (end < 0) {
         end = limit;
      }

      return end;
   }

   private final char parseSet(String rule, ParsePosition pos) {
      UnicodeSet set = new UnicodeSet(rule, pos, this.parseData);
      if (this.variableNext >= this.variableLimit) {
         throw new RuntimeException("Private use variables exhausted");
      } else {
         set.compact();
         return this.generateStandInFor(set);
      }
   }

   char generateStandInFor(Object obj) {
      for(int i = 0; i < this.variablesVector.size(); ++i) {
         if (this.variablesVector.get(i) == obj) {
            return (char)(this.curData.variablesBase + i);
         }
      }

      if (this.variableNext >= this.variableLimit) {
         throw new RuntimeException("Variable range exhausted");
      } else {
         this.variablesVector.add(obj);
         char var10002 = this.variableNext;
         this.variableNext = (char)(var10002 + 1);
         return var10002;
      }
   }

   public char getSegmentStandin(int seg) {
      if (this.segmentStandins.length() < seg) {
         this.segmentStandins.setLength(seg);
      }

      char c = this.segmentStandins.charAt(seg - 1);
      if (c == 0) {
         if (this.variableNext >= this.variableLimit) {
            throw new RuntimeException("Variable range exhausted");
         }

         char var10002 = this.variableNext;
         this.variableNext = (char)(var10002 + 1);
         c = var10002;
         this.variablesVector.add((Object)null);
         this.segmentStandins.setCharAt(seg - 1, c);
      }

      return c;
   }

   public void setSegmentObject(int seg, StringMatcher obj) {
      while(this.segmentObjects.size() < seg) {
         this.segmentObjects.add((Object)null);
      }

      int index = this.getSegmentStandin(seg) - this.curData.variablesBase;
      if (this.segmentObjects.get(seg - 1) == null && this.variablesVector.get(index) == null) {
         this.segmentObjects.set(seg - 1, obj);
         this.variablesVector.set(index, obj);
      } else {
         throw new RuntimeException();
      }
   }

   char getDotStandIn() {
      if (this.dotStandIn == -1) {
         this.dotStandIn = this.generateStandInFor(new UnicodeSet("[^[:Zp:][:Zl:]\\r\\n$]"));
      }

      return (char)this.dotStandIn;
   }

   private void appendVariableDef(String name, StringBuffer buf) {
      char[] ch = (char[])this.variableNames.get(name);
      if (ch == null) {
         if (this.undefinedVariableName != null) {
            throw new IllegalIcuArgumentException("Undefined variable $" + name);
         }

         this.undefinedVariableName = name;
         if (this.variableNext >= this.variableLimit) {
            throw new RuntimeException("Private use variables exhausted");
         }

         buf.append(--this.variableLimit);
      } else {
         buf.append(ch);
      }

   }

   private static class RuleHalf {
      public String text;
      public int cursor;
      public int ante;
      public int post;
      public int cursorOffset;
      private int cursorOffsetPos;
      public boolean anchorStart;
      public boolean anchorEnd;
      private int nextSegmentNumber;

      private RuleHalf() {
         this.cursor = -1;
         this.ante = -1;
         this.post = -1;
         this.cursorOffset = 0;
         this.cursorOffsetPos = 0;
         this.anchorStart = false;
         this.anchorEnd = false;
         this.nextSegmentNumber = 1;
      }

      public int parse(String rule, int pos, int limit, TransliteratorParser parser) {
         int start = pos;
         StringBuffer buf = new StringBuffer();
         pos = this.parseSection(rule, pos, limit, parser, buf, TransliteratorParser.ILLEGAL_TOP, false);
         this.text = buf.toString();
         if (this.cursorOffset > 0 && this.cursor != this.cursorOffsetPos) {
            TransliteratorParser.syntaxError("Misplaced |", rule, start);
         }

         return pos;
      }

      private int parseSection(String rule, int pos, int limit, TransliteratorParser parser, StringBuffer buf, UnicodeSet illegal, boolean isSegment) {
         int start = pos;
         ParsePosition pp = null;
         int quoteStart = -1;
         int quoteLimit = -1;
         int varStart = -1;
         int varLimit = -1;
         int[] iref = new int[1];
         int bufStart = buf.length();

         while(pos < limit) {
            char c = rule.charAt(pos++);
            if (!PatternProps.isWhiteSpace(c)) {
               if ("=><←→↔;".indexOf(c) >= 0) {
                  if (isSegment) {
                     TransliteratorParser.syntaxError("Unclosed segment", rule, start);
                  }
                  break;
               }

               if (this.anchorEnd) {
                  TransliteratorParser.syntaxError("Malformed variable reference", rule, start);
               }

               if (UnicodeSet.resemblesPattern(rule, pos - 1)) {
                  if (pp == null) {
                     pp = new ParsePosition(0);
                  }

                  pp.setIndex(pos - 1);
                  buf.append(parser.parseSet(rule, pp));
                  pos = pp.getIndex();
               } else {
                  int qstart;
                  if (c == '\\') {
                     if (pos == limit) {
                        TransliteratorParser.syntaxError("Trailing backslash", rule, start);
                     }

                     iref[0] = pos;
                     qstart = Utility.unescapeAt(rule, iref);
                     pos = iref[0];
                     if (qstart == -1) {
                        TransliteratorParser.syntaxError("Malformed escape", rule, start);
                     }

                     parser.checkVariableRange(qstart, rule, start);
                     UTF16.append(buf, qstart);
                  } else if (c == '\'') {
                     qstart = rule.indexOf(39, pos);
                     if (qstart == pos) {
                        buf.append(c);
                        ++pos;
                     } else {
                        quoteStart = buf.length();

                        while(true) {
                           if (qstart < 0) {
                              TransliteratorParser.syntaxError("Unterminated quote", rule, start);
                           }

                           buf.append(rule.substring(pos, qstart));
                           pos = qstart + 1;
                           if (pos >= limit || rule.charAt(pos) != '\'') {
                              quoteLimit = buf.length();

                              for(qstart = quoteStart; qstart < quoteLimit; ++qstart) {
                                 parser.checkVariableRange(buf.charAt(qstart), rule, start);
                              }
                              break;
                           }

                           qstart = rule.indexOf(39, pos + 1);
                        }
                     }
                  } else {
                     parser.checkVariableRange(c, rule, start);
                     if (illegal.contains(c)) {
                        TransliteratorParser.syntaxError("Illegal character '" + c + '\'', rule, start);
                     }

                     int qlimit;
                     StringMatcher m;
                     switch (c) {
                        case '$':
                           if (pos == limit) {
                              this.anchorEnd = true;
                           } else {
                              c = rule.charAt(pos);
                              qstart = UCharacter.digit(c, 10);
                              if (qstart >= 1 && qstart <= 9) {
                                 iref[0] = pos;
                                 qstart = Utility.parseNumber(rule, iref, 10);
                                 if (qstart < 0) {
                                    TransliteratorParser.syntaxError("Undefined segment reference", rule, start);
                                 }

                                 pos = iref[0];
                                 buf.append(parser.getSegmentStandin(qstart));
                              } else {
                                 if (pp == null) {
                                    pp = new ParsePosition(0);
                                 }

                                 pp.setIndex(pos);
                                 String name = parser.parseData.parseReference(rule, pp, limit);
                                 if (name == null) {
                                    this.anchorEnd = true;
                                 } else {
                                    pos = pp.getIndex();
                                    varStart = buf.length();
                                    parser.appendVariableDef(name, buf);
                                    varLimit = buf.length();
                                 }
                              }
                           }
                           break;
                        case '&':
                        case '∆':
                           iref[0] = pos;
                           TransliteratorIDParser.SingleID single = TransliteratorIDParser.parseFilterID(rule, iref);
                           if (single == null || !Utility.parseChar(rule, iref, '(')) {
                              TransliteratorParser.syntaxError("Invalid function", rule, start);
                           }

                           Transliterator t = single.getInstance();
                           if (t == null) {
                              TransliteratorParser.syntaxError("Invalid function ID", rule, start);
                           }

                           int bufSegStart = buf.length();
                           pos = this.parseSection(rule, iref[0], limit, parser, buf, TransliteratorParser.ILLEGAL_FUNC, true);
                           FunctionReplacer r = new FunctionReplacer(t, new StringReplacer(buf.substring(bufSegStart), parser.curData));
                           buf.setLength(bufSegStart);
                           buf.append(parser.generateStandInFor(r));
                           break;
                        case '(':
                           qstart = buf.length();
                           qlimit = this.nextSegmentNumber++;
                           pos = this.parseSection(rule, pos, limit, parser, buf, TransliteratorParser.ILLEGAL_SEG, true);
                           m = new StringMatcher(buf.substring(qstart), qlimit, parser.curData);
                           parser.setSegmentObject(qlimit, m);
                           buf.setLength(qstart);
                           buf.append(parser.getSegmentStandin(qlimit));
                           break;
                        case ')':
                           return pos;
                        case '*':
                        case '+':
                        case '?':
                           if (isSegment && buf.length() == bufStart) {
                              TransliteratorParser.syntaxError("Misplaced quantifier", rule, start);
                              break;
                           }

                           if (buf.length() == quoteLimit) {
                              qstart = quoteStart;
                              qlimit = quoteLimit;
                           } else if (buf.length() == varLimit) {
                              qstart = varStart;
                              qlimit = varLimit;
                           } else {
                              qstart = buf.length() - 1;
                              qlimit = qstart + 1;
                           }

                           try {
                              m = new StringMatcher(buf.toString(), qstart, qlimit, 0, parser.curData);
                           } catch (RuntimeException var23) {
                              String precontext = pos < 50 ? rule.substring(0, pos) : "..." + rule.substring(pos - 50, pos);
                              String postContext = limit - pos <= 50 ? rule.substring(pos, limit) : rule.substring(pos, pos + 50) + "...";
                              throw (new IllegalIcuArgumentException("Failure in rule: " + precontext + "$$$" + postContext)).initCause(var23);
                           }

                           int min = 0;
                           int max = Integer.MAX_VALUE;
                           switch (c) {
                              case '+':
                                 min = 1;
                                 break;
                              case '?':
                                 min = 0;
                                 max = 1;
                           }

                           UnicodeMatcher m = new Quantifier(m, min, max);
                           buf.setLength(qstart);
                           buf.append(parser.generateStandInFor(m));
                           break;
                        case '.':
                           buf.append(parser.getDotStandIn());
                           break;
                        case '@':
                           if (this.cursorOffset < 0) {
                              if (buf.length() > 0) {
                                 TransliteratorParser.syntaxError("Misplaced " + c, rule, start);
                              }

                              --this.cursorOffset;
                           } else {
                              if (this.cursorOffset <= 0) {
                                 if (this.cursor == 0 && buf.length() == 0) {
                                    this.cursorOffset = -1;
                                    continue;
                                 }

                                 if (this.cursor < 0) {
                                    this.cursorOffsetPos = buf.length();
                                    this.cursorOffset = 1;
                                 } else {
                                    TransliteratorParser.syntaxError("Misplaced " + c, rule, start);
                                 }
                                 continue;
                              }

                              if (buf.length() != this.cursorOffsetPos || this.cursor >= 0) {
                                 TransliteratorParser.syntaxError("Misplaced " + c, rule, start);
                              }

                              ++this.cursorOffset;
                           }
                           break;
                        case '^':
                           if (buf.length() == 0 && !this.anchorStart) {
                              this.anchorStart = true;
                              break;
                           }

                           TransliteratorParser.syntaxError("Misplaced anchor start", rule, start);
                           break;
                        case '{':
                           if (this.ante >= 0) {
                              TransliteratorParser.syntaxError("Multiple ante contexts", rule, start);
                           }

                           this.ante = buf.length();
                           break;
                        case '|':
                           if (this.cursor >= 0) {
                              TransliteratorParser.syntaxError("Multiple cursors", rule, start);
                           }

                           this.cursor = buf.length();
                           break;
                        case '}':
                           if (this.post >= 0) {
                              TransliteratorParser.syntaxError("Multiple post contexts", rule, start);
                           }

                           this.post = buf.length();
                           break;
                        default:
                           if (c >= '!' && c <= '~' && (c < '0' || c > '9') && (c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
                              TransliteratorParser.syntaxError("Unquoted " + c, rule, start);
                           }

                           buf.append(c);
                     }
                  }
               }
            }
         }

         return pos;
      }

      void removeContext() {
         this.text = this.text.substring(this.ante < 0 ? 0 : this.ante, this.post < 0 ? this.text.length() : this.post);
         this.ante = this.post = -1;
         this.anchorStart = this.anchorEnd = false;
      }

      public boolean isValidOutput(TransliteratorParser parser) {
         int i = 0;

         int c;
         do {
            if (i >= this.text.length()) {
               return true;
            }

            c = UTF16.charAt(this.text, i);
            i += UTF16.getCharCount(c);
         } while(parser.parseData.isReplacer(c));

         return false;
      }

      public boolean isValidInput(TransliteratorParser parser) {
         int i = 0;

         int c;
         do {
            if (i >= this.text.length()) {
               return true;
            }

            c = UTF16.charAt(this.text, i);
            i += UTF16.getCharCount(c);
         } while(parser.parseData.isMatcher(c));

         return false;
      }

      // $FF: synthetic method
      RuleHalf(Object x0) {
         this();
      }
   }

   private static class RuleArray extends RuleBody {
      String[] array;
      int i;

      public RuleArray(String[] array) {
         super(null);
         this.array = array;
         this.i = 0;
      }

      public String handleNextLine() {
         return this.i < this.array.length ? this.array[this.i++] : null;
      }

      public void reset() {
         this.i = 0;
      }
   }

   private abstract static class RuleBody {
      private RuleBody() {
      }

      String nextLine() {
         String s = this.handleNextLine();
         if (s != null && s.length() > 0 && s.charAt(s.length() - 1) == '\\') {
            StringBuilder b = new StringBuilder(s);

            do {
               b.deleteCharAt(b.length() - 1);
               s = this.handleNextLine();
               if (s == null) {
                  break;
               }

               b.append(s);
            } while(s.length() > 0 && s.charAt(s.length() - 1) == '\\');

            s = b.toString();
         }

         return s;
      }

      abstract void reset();

      abstract String handleNextLine();

      // $FF: synthetic method
      RuleBody(Object x0) {
         this();
      }
   }

   private class ParseData implements SymbolTable {
      private ParseData() {
      }

      public char[] lookup(String name) {
         return (char[])TransliteratorParser.this.variableNames.get(name);
      }

      public UnicodeMatcher lookupMatcher(int ch) {
         int i = ch - TransliteratorParser.this.curData.variablesBase;
         return i >= 0 && i < TransliteratorParser.this.variablesVector.size() ? (UnicodeMatcher)TransliteratorParser.this.variablesVector.get(i) : null;
      }

      public String parseReference(String text, ParsePosition pos, int limit) {
         int start = pos.getIndex();

         int i;
         for(i = start; i < limit; ++i) {
            char c = text.charAt(i);
            if (i == start && !UCharacter.isUnicodeIdentifierStart(c) || !UCharacter.isUnicodeIdentifierPart(c)) {
               break;
            }
         }

         if (i == start) {
            return null;
         } else {
            pos.setIndex(i);
            return text.substring(start, i);
         }
      }

      public boolean isMatcher(int ch) {
         int i = ch - TransliteratorParser.this.curData.variablesBase;
         return i >= 0 && i < TransliteratorParser.this.variablesVector.size() ? TransliteratorParser.this.variablesVector.get(i) instanceof UnicodeMatcher : true;
      }

      public boolean isReplacer(int ch) {
         int i = ch - TransliteratorParser.this.curData.variablesBase;
         return i >= 0 && i < TransliteratorParser.this.variablesVector.size() ? TransliteratorParser.this.variablesVector.get(i) instanceof UnicodeReplacer : true;
      }

      // $FF: synthetic method
      ParseData(Object x1) {
         this();
      }
   }
}
