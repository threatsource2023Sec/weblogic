package org.apache.xmlbeans.impl.regex;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

class RegexParser {
   static final int T_CHAR = 0;
   static final int T_EOF = 1;
   static final int T_OR = 2;
   static final int T_STAR = 3;
   static final int T_PLUS = 4;
   static final int T_QUESTION = 5;
   static final int T_LPAREN = 6;
   static final int T_RPAREN = 7;
   static final int T_DOT = 8;
   static final int T_LBRACKET = 9;
   static final int T_BACKSOLIDUS = 10;
   static final int T_CARET = 11;
   static final int T_DOLLAR = 12;
   static final int T_LPAREN2 = 13;
   static final int T_LOOKAHEAD = 14;
   static final int T_NEGATIVELOOKAHEAD = 15;
   static final int T_LOOKBEHIND = 16;
   static final int T_NEGATIVELOOKBEHIND = 17;
   static final int T_INDEPENDENT = 18;
   static final int T_SET_OPERATIONS = 19;
   static final int T_POSIX_CHARCLASS_START = 20;
   static final int T_COMMENT = 21;
   static final int T_MODIFIERS = 22;
   static final int T_CONDITION = 23;
   static final int T_XMLSCHEMA_CC_SUBTRACTION = 24;
   int offset;
   String regex;
   int regexlen;
   int options;
   ResourceBundle resources;
   int chardata;
   int nexttoken;
   protected static final int S_NORMAL = 0;
   protected static final int S_INBRACKETS = 1;
   protected static final int S_INXBRACKETS = 2;
   int context = 0;
   int parennumber = 1;
   boolean hasBackReferences;
   Vector references = null;

   public RegexParser() {
      this.setLocale(Locale.getDefault());
   }

   public RegexParser(Locale locale) {
      this.setLocale(locale);
   }

   public void setLocale(Locale locale) {
      try {
         this.resources = ResourceBundle.getBundle("org.apache.xmlbeans.impl.regex.message", locale);
      } catch (MissingResourceException var3) {
         throw new RuntimeException("Installation Problem???  Couldn't load messages: " + var3.getMessage());
      }
   }

   final ParseException ex(String key, int loc) {
      return new ParseException(this.resources.getString(key), loc);
   }

   private final boolean isSet(int flag) {
      return (this.options & flag) == flag;
   }

   synchronized Token parse(String regex, int options) throws ParseException {
      this.options = options;
      this.offset = 0;
      this.setContext(0);
      this.parennumber = 1;
      this.hasBackReferences = false;
      this.regex = regex;
      if (this.isSet(16)) {
         this.regex = REUtil.stripExtendedComment(this.regex);
      }

      this.regexlen = this.regex.length();
      this.next();
      Token ret = this.parseRegex();
      if (this.offset != this.regexlen) {
         throw this.ex("parser.parse.1", this.offset);
      } else {
         if (this.references != null) {
            for(int i = 0; i < this.references.size(); ++i) {
               ReferencePosition position = (ReferencePosition)this.references.elementAt(i);
               if (this.parennumber <= position.refNumber) {
                  throw this.ex("parser.parse.2", position.position);
               }
            }

            this.references.removeAllElements();
         }

         return ret;
      }
   }

   protected final void setContext(int con) {
      this.context = con;
   }

   final int read() {
      return this.nexttoken;
   }

   final void next() {
      if (this.offset >= this.regexlen) {
         this.chardata = -1;
         this.nexttoken = 1;
      } else {
         int ch = this.regex.charAt(this.offset++);
         this.chardata = ch;
         byte ret;
         if (this.context == 1) {
            switch (ch) {
               case '-':
                  if (this.isSet(512) && this.offset < this.regexlen && this.regex.charAt(this.offset) == '[') {
                     ++this.offset;
                     ret = 24;
                  } else {
                     ret = 0;
                  }
                  break;
               case '[':
                  if (!this.isSet(512) && this.offset < this.regexlen && this.regex.charAt(this.offset) == ':') {
                     ++this.offset;
                     ret = 20;
                     break;
                  }
               default:
                  if (REUtil.isHighSurrogate(ch) && this.offset < this.regexlen) {
                     int low = this.regex.charAt(this.offset);
                     if (REUtil.isLowSurrogate(low)) {
                        this.chardata = REUtil.composeFromSurrogates(ch, low);
                        ++this.offset;
                     }
                  }

                  ret = 0;
                  break;
               case '\\':
                  ret = 10;
                  if (this.offset >= this.regexlen) {
                     throw this.ex("parser.next.1", this.offset - 1);
                  }

                  this.chardata = this.regex.charAt(this.offset++);
            }

            this.nexttoken = ret;
         } else {
            switch (ch) {
               case '$':
                  ret = 12;
                  break;
               case '(':
                  ret = 6;
                  if (this.offset < this.regexlen && this.regex.charAt(this.offset) == '?') {
                     if (++this.offset >= this.regexlen) {
                        throw this.ex("parser.next.2", this.offset - 1);
                     }

                     ch = this.regex.charAt(this.offset++);
                     switch (ch) {
                        case '!':
                           ret = 15;
                           break;
                        case '#':
                           while(this.offset < this.regexlen) {
                              ch = this.regex.charAt(this.offset++);
                              if (ch == ')') {
                                 break;
                              }
                           }

                           if (ch != ')') {
                              throw this.ex("parser.next.4", this.offset - 1);
                           }

                           ret = 21;
                           break;
                        case ':':
                           ret = 13;
                           break;
                        case '<':
                           if (this.offset >= this.regexlen) {
                              throw this.ex("parser.next.2", this.offset - 3);
                           }

                           ch = this.regex.charAt(this.offset++);
                           if (ch == '=') {
                              ret = 16;
                           } else {
                              if (ch != '!') {
                                 throw this.ex("parser.next.3", this.offset - 3);
                              }

                              ret = 17;
                           }
                           break;
                        case '=':
                           ret = 14;
                           break;
                        case '>':
                           ret = 18;
                           break;
                        case '[':
                           ret = 19;
                           break;
                        default:
                           if (ch == '-' || 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z') {
                              --this.offset;
                              ret = 22;
                           } else {
                              if (ch != '(') {
                                 throw this.ex("parser.next.2", this.offset - 2);
                              }

                              ret = 23;
                           }
                     }
                  }
                  break;
               case ')':
                  ret = 7;
                  break;
               case '*':
                  ret = 3;
                  break;
               case '+':
                  ret = 4;
                  break;
               case '.':
                  ret = 8;
                  break;
               case '?':
                  ret = 5;
                  break;
               case '[':
                  ret = 9;
                  break;
               case '\\':
                  ret = 10;
                  if (this.offset >= this.regexlen) {
                     throw this.ex("parser.next.1", this.offset - 1);
                  }

                  this.chardata = this.regex.charAt(this.offset++);
                  break;
               case '^':
                  ret = 11;
                  break;
               case '|':
                  ret = 2;
                  break;
               default:
                  ret = 0;
            }

            this.nexttoken = ret;
         }
      }
   }

   Token parseRegex() throws ParseException {
      Token tok = this.parseTerm();

      for(Token parent = null; this.read() == 2; ((Token)tok).addChild(this.parseTerm())) {
         this.next();
         if (parent == null) {
            parent = Token.createUnion();
            parent.addChild((Token)tok);
            tok = parent;
         }
      }

      return (Token)tok;
   }

   Token parseTerm() throws ParseException {
      int ch = this.read();
      if (ch != 2 && ch != 7 && ch != 1) {
         Token tok = this.parseFactor();

         for(Token concat = null; (ch = this.read()) != 2 && ch != 7 && ch != 1; concat.addChild(this.parseFactor())) {
            if (concat == null) {
               concat = Token.createConcat();
               concat.addChild((Token)tok);
               tok = concat;
            }
         }

         return (Token)tok;
      } else {
         return Token.createEmpty();
      }
   }

   Token processCaret() throws ParseException {
      this.next();
      return Token.token_linebeginning;
   }

   Token processDollar() throws ParseException {
      this.next();
      return Token.token_lineend;
   }

   Token processLookahead() throws ParseException {
      this.next();
      Token tok = Token.createLook(20, this.parseRegex());
      if (this.read() != 7) {
         throw this.ex("parser.factor.1", this.offset - 1);
      } else {
         this.next();
         return tok;
      }
   }

   Token processNegativelookahead() throws ParseException {
      this.next();
      Token tok = Token.createLook(21, this.parseRegex());
      if (this.read() != 7) {
         throw this.ex("parser.factor.1", this.offset - 1);
      } else {
         this.next();
         return tok;
      }
   }

   Token processLookbehind() throws ParseException {
      this.next();
      Token tok = Token.createLook(22, this.parseRegex());
      if (this.read() != 7) {
         throw this.ex("parser.factor.1", this.offset - 1);
      } else {
         this.next();
         return tok;
      }
   }

   Token processNegativelookbehind() throws ParseException {
      this.next();
      Token tok = Token.createLook(23, this.parseRegex());
      if (this.read() != 7) {
         throw this.ex("parser.factor.1", this.offset - 1);
      } else {
         this.next();
         return tok;
      }
   }

   Token processBacksolidus_A() throws ParseException {
      this.next();
      return Token.token_stringbeginning;
   }

   Token processBacksolidus_Z() throws ParseException {
      this.next();
      return Token.token_stringend2;
   }

   Token processBacksolidus_z() throws ParseException {
      this.next();
      return Token.token_stringend;
   }

   Token processBacksolidus_b() throws ParseException {
      this.next();
      return Token.token_wordedge;
   }

   Token processBacksolidus_B() throws ParseException {
      this.next();
      return Token.token_not_wordedge;
   }

   Token processBacksolidus_lt() throws ParseException {
      this.next();
      return Token.token_wordbeginning;
   }

   Token processBacksolidus_gt() throws ParseException {
      this.next();
      return Token.token_wordend;
   }

   Token processStar(Token tok) throws ParseException {
      this.next();
      if (this.read() == 5) {
         this.next();
         return Token.createNGClosure(tok);
      } else {
         return Token.createClosure(tok);
      }
   }

   Token processPlus(Token tok) throws ParseException {
      this.next();
      if (this.read() == 5) {
         this.next();
         return Token.createConcat(tok, Token.createNGClosure(tok));
      } else {
         return Token.createConcat(tok, Token.createClosure(tok));
      }
   }

   Token processQuestion(Token tok) throws ParseException {
      this.next();
      Token par = Token.createUnion();
      if (this.read() == 5) {
         this.next();
         par.addChild(Token.createEmpty());
         par.addChild(tok);
      } else {
         par.addChild(tok);
         par.addChild(Token.createEmpty());
      }

      return par;
   }

   boolean checkQuestion(int off) {
      return off < this.regexlen && this.regex.charAt(off) == '?';
   }

   Token processParen() throws ParseException {
      this.next();
      int p = this.parennumber++;
      Token tok = Token.createParen(this.parseRegex(), p);
      if (this.read() != 7) {
         throw this.ex("parser.factor.1", this.offset - 1);
      } else {
         this.next();
         return tok;
      }
   }

   Token processParen2() throws ParseException {
      this.next();
      Token tok = Token.createParen(this.parseRegex(), 0);
      if (this.read() != 7) {
         throw this.ex("parser.factor.1", this.offset - 1);
      } else {
         this.next();
         return tok;
      }
   }

   Token processCondition() throws ParseException {
      if (this.offset + 1 >= this.regexlen) {
         throw this.ex("parser.factor.4", this.offset);
      } else {
         int refno = -1;
         Token condition = null;
         int ch = this.regex.charAt(this.offset);
         if ('1' <= ch && ch <= '9') {
            refno = ch - 48;
            this.hasBackReferences = true;
            if (this.references == null) {
               this.references = new Vector();
            }

            this.references.addElement(new ReferencePosition(refno, this.offset));
            ++this.offset;
            if (this.regex.charAt(this.offset) != ')') {
               throw this.ex("parser.factor.1", this.offset);
            }

            ++this.offset;
         } else {
            if (ch == '?') {
               --this.offset;
            }

            this.next();
            condition = this.parseFactor();
            switch (condition.type) {
               case 8:
                  if (this.read() != 7) {
                     throw this.ex("parser.factor.1", this.offset - 1);
                  }
               case 20:
               case 21:
               case 22:
               case 23:
                  break;
               default:
                  throw this.ex("parser.factor.5", this.offset);
            }
         }

         this.next();
         Token yesPattern = this.parseRegex();
         Token noPattern = null;
         if (yesPattern.type == 2) {
            if (yesPattern.size() != 2) {
               throw this.ex("parser.factor.6", this.offset);
            }

            noPattern = yesPattern.getChild(1);
            yesPattern = yesPattern.getChild(0);
         }

         if (this.read() != 7) {
            throw this.ex("parser.factor.1", this.offset - 1);
         } else {
            this.next();
            return Token.createCondition(refno, condition, yesPattern, noPattern);
         }
      }
   }

   Token processModifiers() throws ParseException {
      int add = 0;
      int mask = 0;

      int ch;
      int v;
      for(ch = -1; this.offset < this.regexlen; ++this.offset) {
         ch = this.regex.charAt(this.offset);
         v = REUtil.getOptionValue(ch);
         if (v == 0) {
            break;
         }

         add |= v;
      }

      if (this.offset >= this.regexlen) {
         throw this.ex("parser.factor.2", this.offset - 1);
      } else {
         if (ch == 45) {
            ++this.offset;

            while(this.offset < this.regexlen) {
               ch = this.regex.charAt(this.offset);
               v = REUtil.getOptionValue(ch);
               if (v == 0) {
                  break;
               }

               mask |= v;
               ++this.offset;
            }

            if (this.offset >= this.regexlen) {
               throw this.ex("parser.factor.2", this.offset - 1);
            }
         }

         Token.ModifierToken tok;
         if (ch == 58) {
            ++this.offset;
            this.next();
            tok = Token.createModifierGroup(this.parseRegex(), add, mask);
            if (this.read() != 7) {
               throw this.ex("parser.factor.1", this.offset - 1);
            }

            this.next();
         } else {
            if (ch != 41) {
               throw this.ex("parser.factor.3", this.offset);
            }

            ++this.offset;
            this.next();
            tok = Token.createModifierGroup(this.parseRegex(), add, mask);
         }

         return tok;
      }
   }

   Token processIndependent() throws ParseException {
      this.next();
      Token tok = Token.createLook(24, this.parseRegex());
      if (this.read() != 7) {
         throw this.ex("parser.factor.1", this.offset - 1);
      } else {
         this.next();
         return tok;
      }
   }

   Token processBacksolidus_c() throws ParseException {
      char ch2;
      if (this.offset < this.regexlen && ((ch2 = this.regex.charAt(this.offset++)) & '￠') == 64) {
         this.next();
         return Token.createChar(ch2 - 64);
      } else {
         throw this.ex("parser.atom.1", this.offset - 1);
      }
   }

   Token processBacksolidus_C() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_i() throws ParseException {
      Token tok = Token.createChar(105);
      this.next();
      return tok;
   }

   Token processBacksolidus_I() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_g() throws ParseException {
      this.next();
      return Token.getGraphemePattern();
   }

   Token processBacksolidus_X() throws ParseException {
      this.next();
      return Token.getCombiningCharacterSequence();
   }

   Token processBackreference() throws ParseException {
      int refnum = this.chardata - 48;
      Token tok = Token.createBackReference(refnum);
      this.hasBackReferences = true;
      if (this.references == null) {
         this.references = new Vector();
      }

      this.references.addElement(new ReferencePosition(refnum, this.offset - 2));
      this.next();
      return tok;
   }

   Token parseFactor() throws ParseException {
      int ch;
      ch = this.read();
      label67:
      switch (ch) {
         case 10:
            switch (this.chardata) {
               case 60:
                  return this.processBacksolidus_lt();
               case 62:
                  return this.processBacksolidus_gt();
               case 65:
                  return this.processBacksolidus_A();
               case 66:
                  return this.processBacksolidus_B();
               case 90:
                  return this.processBacksolidus_Z();
               case 98:
                  return this.processBacksolidus_b();
               case 122:
                  return this.processBacksolidus_z();
               default:
                  break label67;
            }
         case 11:
            return this.processCaret();
         case 12:
            return this.processDollar();
         case 13:
         case 18:
         case 19:
         case 20:
         default:
            break;
         case 14:
            return this.processLookahead();
         case 15:
            return this.processNegativelookahead();
         case 16:
            return this.processLookbehind();
         case 17:
            return this.processNegativelookbehind();
         case 21:
            this.next();
            return Token.createEmpty();
      }

      Token tok = this.parseAtom();
      ch = this.read();
      switch (ch) {
         case 0:
            if (this.chardata == 123 && this.offset < this.regexlen) {
               int off = this.offset;
               int min = false;
               int max = true;
               char ch;
               if ((ch = this.regex.charAt(off++)) < '0' || ch > '9') {
                  throw this.ex("parser.quantifier.1", this.offset);
               }

               int min = ch - 48;

               while(off < this.regexlen && (ch = this.regex.charAt(off++)) >= '0' && ch <= '9') {
                  min = min * 10 + ch - 48;
                  if (min < 0) {
                     throw this.ex("parser.quantifier.5", this.offset);
                  }
               }

               int max = min;
               if (ch == ',') {
                  if (off >= this.regexlen) {
                     throw this.ex("parser.quantifier.3", this.offset);
                  }

                  if ((ch = this.regex.charAt(off++)) >= '0' && ch <= '9') {
                     max = ch - 48;

                     while(off < this.regexlen && (ch = this.regex.charAt(off++)) >= '0' && ch <= '9') {
                        max = max * 10 + ch - 48;
                        if (max < 0) {
                           throw this.ex("parser.quantifier.5", this.offset);
                        }
                     }

                     if (min > max) {
                        throw this.ex("parser.quantifier.4", this.offset);
                     }
                  } else {
                     max = -1;
                  }
               }

               if (ch != '}') {
                  throw this.ex("parser.quantifier.2", this.offset);
               }

               if (this.checkQuestion(off)) {
                  tok = Token.createNGClosure((Token)tok);
                  this.offset = off + 1;
               } else {
                  tok = Token.createClosure((Token)tok);
                  this.offset = off;
               }

               ((Token)tok).setMin(min);
               ((Token)tok).setMax(max);
               this.next();
            }
         case 1:
         case 2:
         default:
            break;
         case 3:
            return this.processStar((Token)tok);
         case 4:
            return this.processPlus((Token)tok);
         case 5:
            return this.processQuestion((Token)tok);
      }

      return (Token)tok;
   }

   Token parseAtom() throws ParseException {
      int ch = this.read();
      Token tok = null;
      int ch2;
      switch (ch) {
         case 0:
            if (this.chardata != 93 && this.chardata != 123 && this.chardata != 125) {
               tok = Token.createChar(this.chardata);
               ch2 = this.chardata;
               this.next();
               if (REUtil.isHighSurrogate(ch2) && this.read() == 0 && REUtil.isLowSurrogate(this.chardata)) {
                  char[] sur = new char[]{(char)ch2, (char)this.chardata};
                  tok = Token.createParen(Token.createString(new String(sur)), 0);
                  this.next();
               }
               break;
            }

            throw this.ex("parser.atom.4", this.offset - 1);
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 7:
         case 11:
         case 12:
         case 14:
         case 15:
         case 16:
         case 17:
         case 20:
         case 21:
         default:
            throw this.ex("parser.atom.4", this.offset - 1);
         case 6:
            return this.processParen();
         case 8:
            this.next();
            tok = Token.token_dot;
            break;
         case 9:
            return this.parseCharacterClass(true);
         case 10:
            switch (this.chardata) {
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
                  return this.processBackreference();
               case 58:
               case 59:
               case 60:
               case 61:
               case 62:
               case 63:
               case 64:
               case 65:
               case 66:
               case 69:
               case 70:
               case 71:
               case 72:
               case 74:
               case 75:
               case 76:
               case 77:
               case 78:
               case 79:
               case 81:
               case 82:
               case 84:
               case 85:
               case 86:
               case 89:
               case 90:
               case 91:
               case 92:
               case 93:
               case 94:
               case 95:
               case 96:
               case 97:
               case 98:
               case 104:
               case 106:
               case 107:
               case 108:
               case 109:
               case 111:
               case 113:
               default:
                  tok = Token.createChar(this.chardata);
                  break;
               case 67:
                  return this.processBacksolidus_C();
               case 68:
               case 83:
               case 87:
               case 100:
               case 115:
               case 119:
                  Token tok = this.getTokenForShorthand(this.chardata);
                  this.next();
                  return tok;
               case 73:
                  return this.processBacksolidus_I();
               case 80:
               case 112:
                  ch2 = this.offset;
                  tok = this.processBacksolidus_pP(this.chardata);
                  if (tok == null) {
                     throw this.ex("parser.atom.5", ch2);
                  }
                  break;
               case 88:
                  return this.processBacksolidus_X();
               case 99:
                  return this.processBacksolidus_c();
               case 101:
               case 102:
               case 110:
               case 114:
               case 116:
               case 117:
               case 118:
               case 120:
                  ch2 = this.decodeEscaped();
                  if (ch2 < 65536) {
                     tok = Token.createChar(ch2);
                  } else {
                     tok = Token.createString(REUtil.decomposeToSurrogates(ch2));
                  }
                  break;
               case 103:
                  return this.processBacksolidus_g();
               case 105:
                  return this.processBacksolidus_i();
            }

            this.next();
            break;
         case 13:
            return this.processParen2();
         case 18:
            return this.processIndependent();
         case 19:
            return this.parseSetOperations();
         case 22:
            return this.processModifiers();
         case 23:
            return this.processCondition();
      }

      return (Token)tok;
   }

   protected RangeToken processBacksolidus_pP(int c) throws ParseException {
      this.next();
      if (this.read() == 0 && this.chardata == 123) {
         boolean positive = c == 112;
         int namestart = this.offset;
         int nameend = this.regex.indexOf(125, namestart);
         if (nameend < 0) {
            throw this.ex("parser.atom.3", this.offset);
         } else {
            String pname = this.regex.substring(namestart, nameend);
            this.offset = nameend + 1;
            return Token.getRange(pname, positive, this.isSet(512));
         }
      } else {
         throw this.ex("parser.atom.2", this.offset - 1);
      }
   }

   int processCIinCharacterClass(RangeToken tok, int c) {
      return this.decodeEscaped();
   }

   protected RangeToken parseCharacterClass(boolean useNrange) throws ParseException {
      this.setContext(1);
      this.next();
      boolean nrange = false;
      RangeToken base = null;
      RangeToken tok;
      if (this.read() == 0 && this.chardata == 94) {
         nrange = true;
         this.next();
         if (useNrange) {
            tok = Token.createNRange();
         } else {
            base = Token.createRange();
            base.addRange(0, 1114111);
            tok = Token.createRange();
         }
      } else {
         tok = Token.createRange();
      }

      boolean firstloop = true;

      int type;
      while((type = this.read()) != 1 && (type != 0 || this.chardata != 93 || firstloop)) {
         firstloop = false;
         int c = this.chardata;
         boolean end = false;
         int rangeend;
         if (type == 10) {
            switch (c) {
               case 67:
               case 73:
               case 99:
               case 105:
                  c = this.processCIinCharacterClass(tok, c);
                  if (c < 0) {
                     end = true;
                  }
                  break;
               case 68:
               case 83:
               case 87:
               case 100:
               case 115:
               case 119:
                  tok.mergeRanges(this.getTokenForShorthand(c));
                  end = true;
                  break;
               case 80:
               case 112:
                  rangeend = this.offset;
                  RangeToken tok2 = this.processBacksolidus_pP(c);
                  if (tok2 == null) {
                     throw this.ex("parser.atom.5", rangeend);
                  }

                  tok.mergeRanges(tok2);
                  end = true;
                  break;
               default:
                  c = this.decodeEscaped();
            }
         } else if (type == 20) {
            rangeend = this.regex.indexOf(58, this.offset);
            if (rangeend < 0) {
               throw this.ex("parser.cc.1", this.offset);
            }

            boolean positive = true;
            if (this.regex.charAt(this.offset) == '^') {
               ++this.offset;
               positive = false;
            }

            String name = this.regex.substring(this.offset, rangeend);
            RangeToken range = Token.getRange(name, positive, this.isSet(512));
            if (range == null) {
               throw this.ex("parser.cc.3", this.offset);
            }

            tok.mergeRanges(range);
            end = true;
            if (rangeend + 1 >= this.regexlen || this.regex.charAt(rangeend + 1) != ']') {
               throw this.ex("parser.cc.1", rangeend);
            }

            this.offset = rangeend + 2;
         }

         this.next();
         if (!end) {
            if (this.read() == 0 && this.chardata == 45) {
               this.next();
               if ((type = this.read()) == 1) {
                  throw this.ex("parser.cc.2", this.offset);
               }

               if (type == 0 && this.chardata == 93) {
                  tok.addRange(c, c);
                  tok.addRange(45, 45);
               } else {
                  rangeend = this.chardata;
                  if (type == 10) {
                     rangeend = this.decodeEscaped();
                  }

                  this.next();
                  tok.addRange(c, rangeend);
               }
            } else {
               tok.addRange(c, c);
            }
         }

         if (this.isSet(1024) && this.read() == 0 && this.chardata == 44) {
            this.next();
         }
      }

      if (this.read() == 1) {
         throw this.ex("parser.cc.2", this.offset);
      } else {
         if (!useNrange && nrange) {
            base.subtractRanges(tok);
            tok = base;
         }

         tok.sortRanges();
         tok.compactRanges();
         this.setContext(0);
         this.next();
         return tok;
      }
   }

   protected RangeToken parseSetOperations() throws ParseException {
      RangeToken tok = this.parseCharacterClass(false);

      int type;
      while((type = this.read()) != 7) {
         int ch = this.chardata;
         if ((type != 0 || ch != 45 && ch != 38) && type != 4) {
            throw this.ex("parser.ope.2", this.offset - 1);
         }

         this.next();
         if (this.read() != 9) {
            throw this.ex("parser.ope.1", this.offset - 1);
         }

         RangeToken t2 = this.parseCharacterClass(false);
         if (type == 4) {
            tok.mergeRanges(t2);
         } else if (ch == 45) {
            tok.subtractRanges(t2);
         } else {
            if (ch != 38) {
               throw new RuntimeException("ASSERT");
            }

            tok.intersectRanges(t2);
         }
      }

      this.next();
      return tok;
   }

   Token getTokenForShorthand(int ch) {
      Object tok;
      switch (ch) {
         case 68:
            tok = this.isSet(32) ? Token.getRange("Nd", false) : Token.token_not_0to9;
            break;
         case 83:
            tok = this.isSet(32) ? Token.getRange("IsSpace", false) : Token.token_not_spaces;
            break;
         case 87:
            tok = this.isSet(32) ? Token.getRange("IsWord", false) : Token.token_not_wordchars;
            break;
         case 100:
            tok = this.isSet(32) ? Token.getRange("Nd", true) : Token.token_0to9;
            break;
         case 115:
            tok = this.isSet(32) ? Token.getRange("IsSpace", true) : Token.token_spaces;
            break;
         case 119:
            tok = this.isSet(32) ? Token.getRange("IsWord", true) : Token.token_wordchars;
            break;
         default:
            throw new RuntimeException("Internal Error: shorthands: \\u" + Integer.toString(ch, 16));
      }

      return (Token)tok;
   }

   int decodeEscaped() throws ParseException {
      if (this.read() != 10) {
         throw this.ex("parser.next.1", this.offset - 1);
      } else {
         int c = this.chardata;
         boolean v1;
         int uv;
         int v1;
         switch (c) {
            case 65:
            case 90:
            case 122:
               throw this.ex("parser.descape.5", this.offset - 2);
            case 101:
               c = 27;
               break;
            case 102:
               c = 12;
               break;
            case 110:
               c = 10;
               break;
            case 114:
               c = 13;
               break;
            case 116:
               c = 9;
               break;
            case 117:
               v1 = false;
               this.next();
               if (this.read() != 0 || (v1 = hexChar(this.chardata)) < 0) {
                  throw this.ex("parser.descape.1", this.offset - 1);
               }

               uv = v1;
               this.next();
               if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                  uv = uv * 16 + v1;
                  this.next();
                  if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                     uv = uv * 16 + v1;
                     this.next();
                     if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                        uv = uv * 16 + v1;
                        c = uv;
                        break;
                     }

                     throw this.ex("parser.descape.1", this.offset - 1);
                  }

                  throw this.ex("parser.descape.1", this.offset - 1);
               }

               throw this.ex("parser.descape.1", this.offset - 1);
            case 118:
               this.next();
               if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                  uv = v1;
                  this.next();
                  if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                     uv = uv * 16 + v1;
                     this.next();
                     if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                        uv = uv * 16 + v1;
                        this.next();
                        if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                           uv = uv * 16 + v1;
                           this.next();
                           if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                              uv = uv * 16 + v1;
                              this.next();
                              if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                                 uv = uv * 16 + v1;
                                 if (uv > 1114111) {
                                    throw this.ex("parser.descappe.4", this.offset - 1);
                                 }

                                 c = uv;
                                 break;
                              }

                              throw this.ex("parser.descape.1", this.offset - 1);
                           }

                           throw this.ex("parser.descape.1", this.offset - 1);
                        }

                        throw this.ex("parser.descape.1", this.offset - 1);
                     }

                     throw this.ex("parser.descape.1", this.offset - 1);
                  }

                  throw this.ex("parser.descape.1", this.offset - 1);
               }

               throw this.ex("parser.descape.1", this.offset - 1);
            case 120:
               this.next();
               if (this.read() != 0) {
                  throw this.ex("parser.descape.1", this.offset - 1);
               }

               if (this.chardata == 123) {
                  v1 = false;
                  uv = 0;

                  while(true) {
                     this.next();
                     if (this.read() != 0) {
                        throw this.ex("parser.descape.1", this.offset - 1);
                     }

                     if ((v1 = hexChar(this.chardata)) < 0) {
                        if (this.chardata != 125) {
                           throw this.ex("parser.descape.3", this.offset - 1);
                        }

                        if (uv > 1114111) {
                           throw this.ex("parser.descape.4", this.offset - 1);
                        }

                        c = uv;
                        break;
                     }

                     if (uv > uv * 16) {
                        throw this.ex("parser.descape.2", this.offset - 1);
                     }

                     uv = uv * 16 + v1;
                  }
               } else {
                  v1 = false;
                  if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                     uv = v1;
                     this.next();
                     if (this.read() == 0 && (v1 = hexChar(this.chardata)) >= 0) {
                        uv = uv * 16 + v1;
                        c = uv;
                        return c;
                     }

                     throw this.ex("parser.descape.1", this.offset - 1);
                  }

                  throw this.ex("parser.descape.1", this.offset - 1);
               }
         }

         return c;
      }
   }

   private static final int hexChar(int ch) {
      if (ch < 48) {
         return -1;
      } else if (ch > 102) {
         return -1;
      } else if (ch <= 57) {
         return ch - 48;
      } else if (ch < 65) {
         return -1;
      } else if (ch <= 70) {
         return ch - 65 + 10;
      } else {
         return ch < 97 ? -1 : ch - 97 + 10;
      }
   }

   static class ReferencePosition {
      int refNumber;
      int position;

      ReferencePosition(int n, int pos) {
         this.refNumber = n;
         this.position = pos;
      }
   }
}
