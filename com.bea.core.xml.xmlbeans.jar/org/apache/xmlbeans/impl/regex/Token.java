package org.apache.xmlbeans.impl.regex;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

class Token implements Serializable {
   static final boolean COUNTTOKENS = true;
   static int tokens = 0;
   static final int CHAR = 0;
   static final int DOT = 11;
   static final int CONCAT = 1;
   static final int UNION = 2;
   static final int CLOSURE = 3;
   static final int RANGE = 4;
   static final int NRANGE = 5;
   static final int PAREN = 6;
   static final int EMPTY = 7;
   static final int ANCHOR = 8;
   static final int NONGREEDYCLOSURE = 9;
   static final int STRING = 10;
   static final int BACKREFERENCE = 12;
   static final int LOOKAHEAD = 20;
   static final int NEGATIVELOOKAHEAD = 21;
   static final int LOOKBEHIND = 22;
   static final int NEGATIVELOOKBEHIND = 23;
   static final int INDEPENDENT = 24;
   static final int MODIFIERGROUP = 25;
   static final int CONDITION = 26;
   static final int UTF16_MAX = 1114111;
   int type;
   static Token token_dot = new Token(11);
   static Token token_0to9 = createRange();
   static Token token_wordchars;
   static Token token_not_0to9;
   static Token token_not_wordchars;
   static Token token_spaces;
   static Token token_not_spaces;
   static Token token_empty = new Token(7);
   static Token token_linebeginning = createAnchor(94);
   static Token token_linebeginning2 = createAnchor(64);
   static Token token_lineend = createAnchor(36);
   static Token token_stringbeginning = createAnchor(65);
   static Token token_stringend = createAnchor(122);
   static Token token_stringend2 = createAnchor(90);
   static Token token_wordedge = createAnchor(98);
   static Token token_not_wordedge = createAnchor(66);
   static Token token_wordbeginning = createAnchor(60);
   static Token token_wordend = createAnchor(62);
   static final int FC_CONTINUE = 0;
   static final int FC_TERMINAL = 1;
   static final int FC_ANY = 2;
   private static final Hashtable categories;
   private static final Hashtable categories2;
   private static final String[] categoryNames;
   static final int CHAR_INIT_QUOTE = 29;
   static final int CHAR_FINAL_QUOTE = 30;
   static final int CHAR_LETTER = 31;
   static final int CHAR_MARK = 32;
   static final int CHAR_NUMBER = 33;
   static final int CHAR_SEPARATOR = 34;
   static final int CHAR_OTHER = 35;
   static final int CHAR_PUNCTUATION = 36;
   static final int CHAR_SYMBOL = 37;
   private static final String[] blockNames;
   static final String blockRanges = "\u0000\u007f\u0080ÿĀſƀɏɐʯʰ˿̀ͯͰϿЀӿ\u0530֏\u0590\u05ff\u0600ۿ܀ݏހ\u07bfऀॿঀ\u09ff\u0a00\u0a7f\u0a80૿\u0b00\u0b7f\u0b80\u0bffఀ౿ಀ\u0cffഀൿ\u0d80\u0dff\u0e00\u0e7f\u0e80\u0effༀ\u0fffက႟Ⴀჿᄀᇿሀ\u137fᎠ\u13ff᐀ᙿ \u169fᚠ\u16ffក\u17ff᠀\u18afḀỿἀ\u1fff \u206f⁰\u209f₠\u20cf⃐\u20ff℀⅏⅐\u218f←⇿∀⋿⌀⏿␀\u243f⑀\u245f①⓿─╿▀▟■◿☀⛿✀➿⠀⣿⺀\u2eff⼀\u2fdf⿰\u2fff　〿\u3040ゟ゠ヿ\u3100\u312f\u3130\u318f㆐㆟ㆠ\u31bf㈀㋿㌀㏿㐀䶵一\u9fffꀀ\ua48f꒐\ua4cf가힣\ue000\uf8ff豈\ufaffﬀﭏﭐ\ufdff︠︯︰﹏﹐\ufe6fﹰ\ufefe\ufeff\ufeff\uff00\uffef";
   static final int[] nonBMPBlockRanges;
   private static final int NONBMP_BLOCK_START = 84;
   static Hashtable nonxs;
   static final String viramaString = "्্੍્୍்్್്ฺ྄";
   private static Token token_grapheme;
   private static Token token_ccs;

   static ParenToken createLook(int type, Token child) {
      ++tokens;
      return new ParenToken(type, child, 0);
   }

   static ParenToken createParen(Token child, int pnumber) {
      ++tokens;
      return new ParenToken(6, child, pnumber);
   }

   static ClosureToken createClosure(Token tok) {
      ++tokens;
      return new ClosureToken(3, tok);
   }

   static ClosureToken createNGClosure(Token tok) {
      ++tokens;
      return new ClosureToken(9, tok);
   }

   static ConcatToken createConcat(Token tok1, Token tok2) {
      ++tokens;
      return new ConcatToken(tok1, tok2);
   }

   static UnionToken createConcat() {
      ++tokens;
      return new UnionToken(1);
   }

   static UnionToken createUnion() {
      ++tokens;
      return new UnionToken(2);
   }

   static Token createEmpty() {
      return token_empty;
   }

   static RangeToken createRange() {
      ++tokens;
      return new RangeToken(4);
   }

   static RangeToken createNRange() {
      ++tokens;
      return new RangeToken(5);
   }

   static CharToken createChar(int ch) {
      ++tokens;
      return new CharToken(0, ch);
   }

   private static CharToken createAnchor(int ch) {
      ++tokens;
      return new CharToken(8, ch);
   }

   static StringToken createBackReference(int refno) {
      ++tokens;
      return new StringToken(12, (String)null, refno);
   }

   static StringToken createString(String str) {
      ++tokens;
      return new StringToken(10, str, 0);
   }

   static ModifierToken createModifierGroup(Token child, int add, int mask) {
      ++tokens;
      return new ModifierToken(child, add, mask);
   }

   static ConditionToken createCondition(int refno, Token condition, Token yespat, Token nopat) {
      ++tokens;
      return new ConditionToken(refno, condition, yespat, nopat);
   }

   protected Token(int type) {
      this.type = type;
   }

   int size() {
      return 0;
   }

   Token getChild(int index) {
      return null;
   }

   void addChild(Token tok) {
      throw new RuntimeException("Not supported.");
   }

   protected void addRange(int start, int end) {
      throw new RuntimeException("Not supported.");
   }

   protected void sortRanges() {
      throw new RuntimeException("Not supported.");
   }

   protected void compactRanges() {
      throw new RuntimeException("Not supported.");
   }

   protected void mergeRanges(Token tok) {
      throw new RuntimeException("Not supported.");
   }

   protected void subtractRanges(Token tok) {
      throw new RuntimeException("Not supported.");
   }

   protected void intersectRanges(Token tok) {
      throw new RuntimeException("Not supported.");
   }

   static Token complementRanges(Token tok) {
      return RangeToken.complementRanges(tok);
   }

   void setMin(int min) {
   }

   void setMax(int max) {
   }

   int getMin() {
      return -1;
   }

   int getMax() {
      return -1;
   }

   int getReferenceNumber() {
      return 0;
   }

   String getString() {
      return null;
   }

   int getParenNumber() {
      return 0;
   }

   int getChar() {
      return -1;
   }

   public String toString() {
      return this.toString(0);
   }

   public String toString(int options) {
      return this.type == 11 ? "." : "";
   }

   final int getMinLength() {
      int ret;
      switch (this.type) {
         case 0:
         case 4:
         case 5:
         case 11:
            return 1;
         case 1:
            int sum = 0;

            for(ret = 0; ret < this.size(); ++ret) {
               sum += this.getChild(ret).getMinLength();
            }

            return sum;
         case 2:
         case 26:
            if (this.size() == 0) {
               return 0;
            }

            ret = this.getChild(0).getMinLength();

            for(int i = 1; i < this.size(); ++i) {
               int min = this.getChild(i).getMinLength();
               if (min < ret) {
                  ret = min;
               }
            }

            return ret;
         case 3:
         case 9:
            if (this.getMin() >= 0) {
               return this.getMin() * this.getChild(0).getMinLength();
            }

            return 0;
         case 6:
         case 24:
         case 25:
            return this.getChild(0).getMinLength();
         case 7:
         case 8:
            return 0;
         case 10:
            return this.getString().length();
         case 12:
            return 0;
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            throw new RuntimeException("Token#getMinLength(): Invalid Type: " + this.type);
         case 20:
         case 21:
         case 22:
         case 23:
            return 0;
      }
   }

   final int getMaxLength() {
      int ret;
      int i;
      switch (this.type) {
         case 0:
            return 1;
         case 1:
            int sum = 0;

            for(ret = 0; ret < this.size(); ++ret) {
               i = this.getChild(ret).getMaxLength();
               if (i < 0) {
                  return -1;
               }

               sum += i;
            }

            return sum;
         case 2:
         case 26:
            if (this.size() == 0) {
               return 0;
            }

            ret = this.getChild(0).getMaxLength();
            i = 1;

            for(; ret >= 0 && i < this.size(); ++i) {
               int max = this.getChild(i).getMaxLength();
               if (max < 0) {
                  ret = -1;
                  break;
               }

               if (max > ret) {
                  ret = max;
               }
            }

            return ret;
         case 3:
         case 9:
            if (this.getMax() >= 0) {
               return this.getMax() * this.getChild(0).getMaxLength();
            }

            return -1;
         case 4:
         case 5:
         case 11:
            return 2;
         case 6:
         case 24:
         case 25:
            return this.getChild(0).getMaxLength();
         case 7:
         case 8:
            return 0;
         case 10:
            return this.getString().length();
         case 12:
            return -1;
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            throw new RuntimeException("Token#getMaxLength(): Invalid Type: " + this.type);
         case 20:
         case 21:
         case 22:
         case 23:
            return 0;
      }
   }

   private static final boolean isSet(int options, int flag) {
      return (options & flag) == flag;
   }

   final int analyzeFirstCharacter(RangeToken result, int options) {
      int ret2;
      int ret3;
      switch (this.type) {
         case 0:
            int ch = this.getChar();
            result.addRange(ch, ch);
            if (ch < 65536 && isSet(options, 2)) {
               int ch = Character.toUpperCase((char)ch);
               result.addRange(ch, ch);
               ch = Character.toLowerCase((char)ch);
               result.addRange(ch, ch);
            }

            return 1;
         case 1:
            int ret = 0;

            for(ret2 = 0; ret2 < this.size() && (ret = this.getChild(ret2).analyzeFirstCharacter(result, options)) == 0; ++ret2) {
            }

            return ret;
         case 2:
            if (this.size() == 0) {
               return 0;
            }

            ret2 = 0;
            boolean hasEmpty = false;

            for(ret3 = 0; ret3 < this.size(); ++ret3) {
               ret2 = this.getChild(ret3).analyzeFirstCharacter(result, options);
               if (ret2 == 2) {
                  break;
               }

               if (ret2 == 0) {
                  hasEmpty = true;
               }
            }

            return hasEmpty ? 0 : ret2;
         case 3:
         case 9:
            this.getChild(0).analyzeFirstCharacter(result, options);
            return 0;
         case 4:
            if (isSet(options, 2)) {
               result.mergeRanges(((RangeToken)this).getCaseInsensitiveToken());
            } else {
               result.mergeRanges(this);
            }

            return 1;
         case 5:
            if (isSet(options, 2)) {
               result.mergeRanges(complementRanges(((RangeToken)this).getCaseInsensitiveToken()));
            } else {
               result.mergeRanges(complementRanges(this));
            }

            return 1;
         case 6:
         case 24:
            return this.getChild(0).analyzeFirstCharacter(result, options);
         case 7:
         case 8:
            return 0;
         case 10:
            int cha = this.getString().charAt(0);
            char ch2;
            if (REUtil.isHighSurrogate(cha) && this.getString().length() >= 2 && REUtil.isLowSurrogate(ch2 = this.getString().charAt(1))) {
               cha = REUtil.composeFromSurrogates(cha, ch2);
            }

            result.addRange(cha, cha);
            if (cha < 65536 && isSet(options, 2)) {
               int cha = Character.toUpperCase((char)cha);
               result.addRange(cha, cha);
               cha = Character.toLowerCase((char)cha);
               result.addRange(cha, cha);
            }

            return 1;
         case 11:
            if (isSet(options, 4)) {
               return 0;
            }

            return 0;
         case 12:
            result.addRange(0, 1114111);
            return 2;
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            throw new RuntimeException("Token#analyzeHeadCharacter(): Invalid Type: " + this.type);
         case 20:
         case 21:
         case 22:
         case 23:
            return 0;
         case 25:
            options |= ((ModifierToken)this).getOptions();
            options &= ~((ModifierToken)this).getOptionsMask();
            return this.getChild(0).analyzeFirstCharacter(result, options);
         case 26:
            ret3 = this.getChild(0).analyzeFirstCharacter(result, options);
            if (this.size() == 1) {
               return 0;
            } else if (ret3 == 2) {
               return ret3;
            } else {
               int ret4 = this.getChild(1).analyzeFirstCharacter(result, options);
               if (ret4 == 2) {
                  return ret4;
               } else {
                  return ret3 != 0 && ret4 != 0 ? 1 : 0;
               }
            }
      }
   }

   private final boolean isShorterThan(Token tok) {
      if (tok == null) {
         return false;
      } else if (this.type == 10) {
         int mylength = this.getString().length();
         if (tok.type == 10) {
            int otherlength = tok.getString().length();
            return mylength < otherlength;
         } else {
            throw new RuntimeException("Internal Error: Illegal type: " + tok.type);
         }
      } else {
         throw new RuntimeException("Internal Error: Illegal type: " + this.type);
      }
   }

   final void findFixedString(FixedStringContainer container, int options) {
      switch (this.type) {
         case 0:
            container.token = null;
            return;
         case 1:
            Token prevToken = null;
            int prevOptions = 0;
            int i = 0;

            for(; i < this.size(); ++i) {
               this.getChild(i).findFixedString(container, options);
               if (prevToken == null || prevToken.isShorterThan(container.token)) {
                  prevToken = container.token;
                  prevOptions = container.options;
               }
            }

            container.token = prevToken;
            container.options = prevOptions;
            return;
         case 2:
         case 3:
         case 4:
         case 5:
         case 7:
         case 8:
         case 9:
         case 11:
         case 12:
         case 20:
         case 21:
         case 22:
         case 23:
         case 26:
            container.token = null;
            return;
         case 6:
         case 24:
            this.getChild(0).findFixedString(container, options);
            return;
         case 10:
            container.token = this;
            container.options = options;
            return;
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            throw new RuntimeException("Token#findFixedString(): Invalid Type: " + this.type);
         case 25:
            options |= ((ModifierToken)this).getOptions();
            options &= ~((ModifierToken)this).getOptionsMask();
            this.getChild(0).findFixedString(container, options);
      }
   }

   boolean match(int ch) {
      throw new RuntimeException("NFAArrow#match(): Internal error: " + this.type);
   }

   protected static RangeToken getRange(String name, boolean positive) {
      if (categories.size() == 0) {
         synchronized(categories) {
            Token[] ranges = new Token[categoryNames.length];

            int type;
            for(type = 0; type < ranges.length; ++type) {
               ranges[type] = createRange();
            }

            int i;
            for(i = 0; i < 65536; ++i) {
               type = Character.getType((char)i);
               if (type == 21 || type == 22) {
                  if (i == 171 || i == 8216 || i == 8219 || i == 8220 || i == 8223 || i == 8249) {
                     type = 29;
                  }

                  if (i == 187 || i == 8217 || i == 8221 || i == 8250) {
                     type = 30;
                  }
               }

               ranges[type].addRange(i, i);
               byte type;
               switch (type) {
                  case 0:
                  case 15:
                  case 16:
                  case 18:
                  case 19:
                     type = 35;
                     break;
                  case 1:
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                     type = 31;
                     break;
                  case 6:
                  case 7:
                  case 8:
                     type = 32;
                     break;
                  case 9:
                  case 10:
                  case 11:
                     type = 33;
                     break;
                  case 12:
                  case 13:
                  case 14:
                     type = 34;
                     break;
                  case 17:
                  default:
                     throw new RuntimeException("org.apache.xerces.utils.regex.Token#getRange(): Unknown Unicode category: " + type);
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 24:
                  case 29:
                  case 30:
                     type = 36;
                     break;
                  case 25:
                  case 26:
                  case 27:
                  case 28:
                     type = 37;
               }

               ranges[type].addRange(i, i);
            }

            ranges[0].addRange(65536, 1114111);

            for(i = 0; i < ranges.length; ++i) {
               if (categoryNames[i] != null) {
                  if (i == 0) {
                     ranges[i].addRange(65536, 1114111);
                  }

                  categories.put(categoryNames[i], ranges[i]);
                  categories2.put(categoryNames[i], complementRanges(ranges[i]));
               }
            }

            StringBuffer buffer = new StringBuffer(50);

            RangeToken r1;
            for(int i = 0; i < blockNames.length; ++i) {
               r1 = createRange();
               int location;
               int ci;
               if (i < 84) {
                  location = i * 2;
                  int rstart = "\u0000\u007f\u0080ÿĀſƀɏɐʯʰ˿̀ͯͰϿЀӿ\u0530֏\u0590\u05ff\u0600ۿ܀ݏހ\u07bfऀॿঀ\u09ff\u0a00\u0a7f\u0a80૿\u0b00\u0b7f\u0b80\u0bffఀ౿ಀ\u0cffഀൿ\u0d80\u0dff\u0e00\u0e7f\u0e80\u0effༀ\u0fffက႟Ⴀჿᄀᇿሀ\u137fᎠ\u13ff᐀ᙿ \u169fᚠ\u16ffក\u17ff᠀\u18afḀỿἀ\u1fff \u206f⁰\u209f₠\u20cf⃐\u20ff℀⅏⅐\u218f←⇿∀⋿⌀⏿␀\u243f⑀\u245f①⓿─╿▀▟■◿☀⛿✀➿⠀⣿⺀\u2eff⼀\u2fdf⿰\u2fff　〿\u3040ゟ゠ヿ\u3100\u312f\u3130\u318f㆐㆟ㆠ\u31bf㈀㋿㌀㏿㐀䶵一\u9fffꀀ\ua48f꒐\ua4cf가힣\ue000\uf8ff豈\ufaffﬀﭏﭐ\ufdff︠︯︰﹏﹐\ufe6fﹰ\ufefe\ufeff\ufeff\uff00\uffef".charAt(location);
                  ci = "\u0000\u007f\u0080ÿĀſƀɏɐʯʰ˿̀ͯͰϿЀӿ\u0530֏\u0590\u05ff\u0600ۿ܀ݏހ\u07bfऀॿঀ\u09ff\u0a00\u0a7f\u0a80૿\u0b00\u0b7f\u0b80\u0bffఀ౿ಀ\u0cffഀൿ\u0d80\u0dff\u0e00\u0e7f\u0e80\u0effༀ\u0fffက႟Ⴀჿᄀᇿሀ\u137fᎠ\u13ff᐀ᙿ \u169fᚠ\u16ffក\u17ff᠀\u18afḀỿἀ\u1fff \u206f⁰\u209f₠\u20cf⃐\u20ff℀⅏⅐\u218f←⇿∀⋿⌀⏿␀\u243f⑀\u245f①⓿─╿▀▟■◿☀⛿✀➿⠀⣿⺀\u2eff⼀\u2fdf⿰\u2fff　〿\u3040ゟ゠ヿ\u3100\u312f\u3130\u318f㆐㆟ㆠ\u31bf㈀㋿㌀㏿㐀䶵一\u9fffꀀ\ua48f꒐\ua4cf가힣\ue000\uf8ff豈\ufaffﬀﭏﭐ\ufdff︠︯︰﹏﹐\ufe6fﹰ\ufefe\ufeff\ufeff\uff00\uffef".charAt(location + 1);
                  r1.addRange(rstart, ci);
               } else {
                  location = (i - 84) * 2;
                  r1.addRange(nonBMPBlockRanges[location], nonBMPBlockRanges[location + 1]);
               }

               String n = blockNames[i];
               if (n.equals("Specials")) {
                  r1.addRange(65520, 65533);
               }

               if (n.equals("Private Use")) {
                  r1.addRange(983040, 1048573);
                  r1.addRange(1048576, 1114109);
               }

               categories.put(n, r1);
               categories2.put(n, complementRanges(r1));
               buffer.setLength(0);
               buffer.append("Is");
               if (n.indexOf(32) >= 0) {
                  for(ci = 0; ci < n.length(); ++ci) {
                     if (n.charAt(ci) != ' ') {
                        buffer.append(n.charAt(ci));
                     }
                  }
               } else {
                  buffer.append(n);
               }

               setAlias(buffer.toString(), n, true);
            }

            setAlias("ASSIGNED", "Cn", false);
            setAlias("UNASSIGNED", "Cn", true);
            Token all = createRange();
            all.addRange(0, 1114111);
            categories.put("ALL", all);
            categories2.put("ALL", complementRanges(all));
            registerNonXS("ASSIGNED");
            registerNonXS("UNASSIGNED");
            registerNonXS("ALL");
            r1 = createRange();
            r1.mergeRanges(ranges[1]);
            r1.mergeRanges(ranges[2]);
            r1.mergeRanges(ranges[5]);
            categories.put("IsAlpha", r1);
            categories2.put("IsAlpha", complementRanges(r1));
            registerNonXS("IsAlpha");
            Token isalnum = createRange();
            isalnum.mergeRanges(r1);
            isalnum.mergeRanges(ranges[9]);
            categories.put("IsAlnum", isalnum);
            categories2.put("IsAlnum", complementRanges(isalnum));
            registerNonXS("IsAlnum");
            Token isspace = createRange();
            isspace.mergeRanges(token_spaces);
            isspace.mergeRanges(ranges[34]);
            categories.put("IsSpace", isspace);
            categories2.put("IsSpace", complementRanges(isspace));
            registerNonXS("IsSpace");
            Token isword = createRange();
            isword.mergeRanges(isalnum);
            isword.addRange(95, 95);
            categories.put("IsWord", isword);
            categories2.put("IsWord", complementRanges(isword));
            registerNonXS("IsWord");
            Token isascii = createRange();
            isascii.addRange(0, 127);
            categories.put("IsASCII", isascii);
            categories2.put("IsASCII", complementRanges(isascii));
            registerNonXS("IsASCII");
            Token isnotgraph = createRange();
            isnotgraph.mergeRanges(ranges[35]);
            isnotgraph.addRange(32, 32);
            categories.put("IsGraph", complementRanges(isnotgraph));
            categories2.put("IsGraph", isnotgraph);
            registerNonXS("IsGraph");
            Token isxdigit = createRange();
            isxdigit.addRange(48, 57);
            isxdigit.addRange(65, 70);
            isxdigit.addRange(97, 102);
            categories.put("IsXDigit", complementRanges(isxdigit));
            categories2.put("IsXDigit", isxdigit);
            registerNonXS("IsXDigit");
            setAlias("IsDigit", "Nd", true);
            setAlias("IsUpper", "Lu", true);
            setAlias("IsLower", "Ll", true);
            setAlias("IsCntrl", "C", true);
            setAlias("IsPrint", "C", false);
            setAlias("IsPunct", "P", true);
            registerNonXS("IsDigit");
            registerNonXS("IsUpper");
            registerNonXS("IsLower");
            registerNonXS("IsCntrl");
            registerNonXS("IsPrint");
            registerNonXS("IsPunct");
            setAlias("alpha", "IsAlpha", true);
            setAlias("alnum", "IsAlnum", true);
            setAlias("ascii", "IsASCII", true);
            setAlias("cntrl", "IsCntrl", true);
            setAlias("digit", "IsDigit", true);
            setAlias("graph", "IsGraph", true);
            setAlias("lower", "IsLower", true);
            setAlias("print", "IsPrint", true);
            setAlias("punct", "IsPunct", true);
            setAlias("space", "IsSpace", true);
            setAlias("upper", "IsUpper", true);
            setAlias("word", "IsWord", true);
            setAlias("xdigit", "IsXDigit", true);
            registerNonXS("alpha");
            registerNonXS("alnum");
            registerNonXS("ascii");
            registerNonXS("cntrl");
            registerNonXS("digit");
            registerNonXS("graph");
            registerNonXS("lower");
            registerNonXS("print");
            registerNonXS("punct");
            registerNonXS("space");
            registerNonXS("upper");
            registerNonXS("word");
            registerNonXS("xdigit");
         }
      }

      RangeToken tok = positive ? (RangeToken)categories.get(name) : (RangeToken)categories2.get(name);
      return tok;
   }

   protected static RangeToken getRange(String name, boolean positive, boolean xs) {
      RangeToken range = getRange(name, positive);
      if (xs && range != null && isRegisterNonXS(name)) {
         range = null;
      }

      return range;
   }

   protected static void registerNonXS(String name) {
      if (nonxs == null) {
         nonxs = new Hashtable();
      }

      nonxs.put(name, name);
   }

   protected static boolean isRegisterNonXS(String name) {
      return nonxs == null ? false : nonxs.containsKey(name);
   }

   private static void setAlias(String newName, String name, boolean positive) {
      Token t1 = (Token)categories.get(name);
      Token t2 = (Token)categories2.get(name);
      if (positive) {
         categories.put(newName, t1);
         categories2.put(newName, t2);
      } else {
         categories2.put(newName, t1);
         categories.put(newName, t2);
      }

   }

   static synchronized Token getGraphemePattern() {
      if (token_grapheme != null) {
         return token_grapheme;
      } else {
         Token base_char = createRange();
         base_char.mergeRanges(getRange("ASSIGNED", true));
         base_char.subtractRanges(getRange("M", true));
         base_char.subtractRanges(getRange("C", true));
         Token virama = createRange();

         for(int i = 0; i < "्্੍્୍்్್്ฺ྄".length(); ++i) {
            int ch = "्্੍્୍்్್്ฺ྄".charAt(i);
            virama.addRange(i, i);
         }

         Token combiner_wo_virama = createRange();
         combiner_wo_virama.mergeRanges(getRange("M", true));
         combiner_wo_virama.addRange(4448, 4607);
         combiner_wo_virama.addRange(65438, 65439);
         Token left = createUnion();
         left.addChild(base_char);
         left.addChild(token_empty);
         Token foo = createUnion();
         foo.addChild(createConcat(virama, getRange("L", true)));
         foo.addChild(combiner_wo_virama);
         Token foo = createClosure(foo);
         Token foo = createConcat(left, foo);
         token_grapheme = foo;
         return token_grapheme;
      }
   }

   static synchronized Token getCombiningCharacterSequence() {
      if (token_ccs != null) {
         return token_ccs;
      } else {
         Token foo = createClosure(getRange("M", true));
         Token foo = createConcat(getRange("M", false), foo);
         token_ccs = foo;
         return token_ccs;
      }
   }

   static {
      token_0to9.addRange(48, 57);
      token_wordchars = createRange();
      token_wordchars.addRange(48, 57);
      token_wordchars.addRange(65, 90);
      token_wordchars.addRange(95, 95);
      token_wordchars.addRange(97, 122);
      token_spaces = createRange();
      token_spaces.addRange(9, 9);
      token_spaces.addRange(10, 10);
      token_spaces.addRange(12, 12);
      token_spaces.addRange(13, 13);
      token_spaces.addRange(32, 32);
      token_not_0to9 = complementRanges(token_0to9);
      token_not_wordchars = complementRanges(token_wordchars);
      token_not_spaces = complementRanges(token_spaces);
      categories = new Hashtable();
      categories2 = new Hashtable();
      categoryNames = new String[]{"Cn", "Lu", "Ll", "Lt", "Lm", "Lo", "Mn", "Me", "Mc", "Nd", "Nl", "No", "Zs", "Zl", "Zp", "Cc", "Cf", null, "Co", "Cs", "Pd", "Ps", "Pe", "Pc", "Po", "Sm", "Sc", "Sk", "So", "Pi", "Pf", "L", "M", "N", "Z", "C", "P", "S"};
      blockNames = new String[]{"Basic Latin", "Latin-1 Supplement", "Latin Extended-A", "Latin Extended-B", "IPA Extensions", "Spacing Modifier Letters", "Combining Diacritical Marks", "Greek", "Cyrillic", "Armenian", "Hebrew", "Arabic", "Syriac", "Thaana", "Devanagari", "Bengali", "Gurmukhi", "Gujarati", "Oriya", "Tamil", "Telugu", "Kannada", "Malayalam", "Sinhala", "Thai", "Lao", "Tibetan", "Myanmar", "Georgian", "Hangul Jamo", "Ethiopic", "Cherokee", "Unified Canadian Aboriginal Syllabics", "Ogham", "Runic", "Khmer", "Mongolian", "Latin Extended Additional", "Greek Extended", "General Punctuation", "Superscripts and Subscripts", "Currency Symbols", "Combining Marks for Symbols", "Letterlike Symbols", "Number Forms", "Arrows", "Mathematical Operators", "Miscellaneous Technical", "Control Pictures", "Optical Character Recognition", "Enclosed Alphanumerics", "Box Drawing", "Block Elements", "Geometric Shapes", "Miscellaneous Symbols", "Dingbats", "Braille Patterns", "CJK Radicals Supplement", "Kangxi Radicals", "Ideographic Description Characters", "CJK Symbols and Punctuation", "Hiragana", "Katakana", "Bopomofo", "Hangul Compatibility Jamo", "Kanbun", "Bopomofo Extended", "Enclosed CJK Letters and Months", "CJK Compatibility", "CJK Unified Ideographs Extension A", "CJK Unified Ideographs", "Yi Syllables", "Yi Radicals", "Hangul Syllables", "Private Use", "CJK Compatibility Ideographs", "Alphabetic Presentation Forms", "Arabic Presentation Forms-A", "Combining Half Marks", "CJK Compatibility Forms", "Small Form Variants", "Arabic Presentation Forms-B", "Specials", "Halfwidth and Fullwidth Forms", "Old Italic", "Gothic", "Deseret", "Byzantine Musical Symbols", "Musical Symbols", "Mathematical Alphanumeric Symbols", "CJK Unified Ideographs Extension B", "CJK Compatibility Ideographs Supplement", "Tags"};
      nonBMPBlockRanges = new int[]{66304, 66351, 66352, 66383, 66560, 66639, 118784, 119039, 119040, 119295, 119808, 120831, 131072, 173782, 194560, 195103, 917504, 917631};
      nonxs = null;
      token_grapheme = null;
      token_ccs = null;
   }

   static class UnionToken extends Token implements Serializable {
      Vector children;

      UnionToken(int type) {
         super(type);
      }

      void addChild(Token tok) {
         if (tok != null) {
            if (this.children == null) {
               this.children = new Vector();
            }

            if (this.type == 2) {
               this.children.addElement(tok);
            } else {
               int size;
               if (tok.type == 1) {
                  for(size = 0; size < tok.size(); ++size) {
                     this.addChild(tok.getChild(size));
                  }

               } else {
                  size = this.children.size();
                  if (size == 0) {
                     this.children.addElement(tok);
                  } else {
                     Token previous = (Token)this.children.elementAt(size - 1);
                     if ((((Token)previous).type == 0 || ((Token)previous).type == 10) && (tok.type == 0 || tok.type == 10)) {
                        int nextMaxLength = tok.type == 0 ? 2 : tok.getString().length();
                        StringBuffer buffer;
                        int ch;
                        if (((Token)previous).type == 0) {
                           buffer = new StringBuffer(2 + nextMaxLength);
                           ch = ((Token)previous).getChar();
                           if (ch >= 65536) {
                              buffer.append(REUtil.decomposeToSurrogates(ch));
                           } else {
                              buffer.append((char)ch);
                           }

                           previous = Token.createString((String)null);
                           this.children.setElementAt(previous, size - 1);
                        } else {
                           buffer = new StringBuffer(((Token)previous).getString().length() + nextMaxLength);
                           buffer.append(((Token)previous).getString());
                        }

                        if (tok.type == 0) {
                           ch = tok.getChar();
                           if (ch >= 65536) {
                              buffer.append(REUtil.decomposeToSurrogates(ch));
                           } else {
                              buffer.append((char)ch);
                           }
                        } else {
                           buffer.append(tok.getString());
                        }

                        ((StringToken)previous).string = new String(buffer);
                     } else {
                        this.children.addElement(tok);
                     }
                  }
               }
            }
         }
      }

      int size() {
         return this.children == null ? 0 : this.children.size();
      }

      Token getChild(int index) {
         return (Token)this.children.elementAt(index);
      }

      public String toString(int options) {
         String ret;
         StringBuffer sb;
         int i;
         if (this.type == 1) {
            if (this.children.size() == 2) {
               Token ch = this.getChild(0);
               Token ch2 = this.getChild(1);
               if (ch2.type == 3 && ch2.getChild(0) == ch) {
                  ret = ch.toString(options) + "+";
               } else if (ch2.type == 9 && ch2.getChild(0) == ch) {
                  ret = ch.toString(options) + "+?";
               } else {
                  ret = ch.toString(options) + ch2.toString(options);
               }
            } else {
               sb = new StringBuffer();

               for(i = 0; i < this.children.size(); ++i) {
                  sb.append(((Token)this.children.elementAt(i)).toString(options));
               }

               ret = new String(sb);
            }

            return ret;
         } else {
            if (this.children.size() == 2 && this.getChild(1).type == 7) {
               ret = this.getChild(0).toString(options) + "?";
            } else if (this.children.size() == 2 && this.getChild(0).type == 7) {
               ret = this.getChild(1).toString(options) + "??";
            } else {
               sb = new StringBuffer();
               sb.append(((Token)this.children.elementAt(0)).toString(options));

               for(i = 1; i < this.children.size(); ++i) {
                  sb.append('|');
                  sb.append(((Token)this.children.elementAt(i)).toString(options));
               }

               ret = new String(sb);
            }

            return ret;
         }
      }
   }

   static class ModifierToken extends Token implements Serializable {
      Token child;
      int add;
      int mask;

      ModifierToken(Token tok, int add, int mask) {
         super(25);
         this.child = tok;
         this.add = add;
         this.mask = mask;
      }

      int size() {
         return 1;
      }

      Token getChild(int index) {
         return this.child;
      }

      int getOptions() {
         return this.add;
      }

      int getOptionsMask() {
         return this.mask;
      }

      public String toString(int options) {
         return "(?" + (this.add == 0 ? "" : REUtil.createOptionString(this.add)) + (this.mask == 0 ? "" : REUtil.createOptionString(this.mask)) + ":" + this.child.toString(options) + ")";
      }
   }

   static class ConditionToken extends Token implements Serializable {
      int refNumber;
      Token condition;
      Token yes;
      Token no;

      ConditionToken(int refno, Token cond, Token yespat, Token nopat) {
         super(26);
         this.refNumber = refno;
         this.condition = cond;
         this.yes = yespat;
         this.no = nopat;
      }

      int size() {
         return this.no == null ? 1 : 2;
      }

      Token getChild(int index) {
         if (index == 0) {
            return this.yes;
         } else if (index == 1) {
            return this.no;
         } else {
            throw new RuntimeException("Internal Error: " + index);
         }
      }

      public String toString(int options) {
         String ret;
         if (this.refNumber > 0) {
            ret = "(?(" + this.refNumber + ")";
         } else if (this.condition.type == 8) {
            ret = "(?(" + this.condition + ")";
         } else {
            ret = "(?" + this.condition;
         }

         if (this.no == null) {
            ret = ret + this.yes + ")";
         } else {
            ret = ret + this.yes + "|" + this.no + ")";
         }

         return ret;
      }
   }

   static class ParenToken extends Token implements Serializable {
      Token child;
      int parennumber;

      ParenToken(int type, Token tok, int paren) {
         super(type);
         this.child = tok;
         this.parennumber = paren;
      }

      int size() {
         return 1;
      }

      Token getChild(int index) {
         return this.child;
      }

      int getParenNumber() {
         return this.parennumber;
      }

      public String toString(int options) {
         String ret = null;
         switch (this.type) {
            case 6:
               if (this.parennumber == 0) {
                  ret = "(?:" + this.child.toString(options) + ")";
               } else {
                  ret = "(" + this.child.toString(options) + ")";
               }
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               break;
            case 20:
               ret = "(?=" + this.child.toString(options) + ")";
               break;
            case 21:
               ret = "(?!" + this.child.toString(options) + ")";
               break;
            case 22:
               ret = "(?<=" + this.child.toString(options) + ")";
               break;
            case 23:
               ret = "(?<!" + this.child.toString(options) + ")";
               break;
            case 24:
               ret = "(?>" + this.child.toString(options) + ")";
         }

         return ret;
      }
   }

   static class ClosureToken extends Token implements Serializable {
      int min;
      int max;
      Token child;

      ClosureToken(int type, Token tok) {
         super(type);
         this.child = tok;
         this.setMin(-1);
         this.setMax(-1);
      }

      int size() {
         return 1;
      }

      Token getChild(int index) {
         return this.child;
      }

      final void setMin(int min) {
         this.min = min;
      }

      final void setMax(int max) {
         this.max = max;
      }

      final int getMin() {
         return this.min;
      }

      final int getMax() {
         return this.max;
      }

      public String toString(int options) {
         String ret;
         if (this.type == 3) {
            if (this.getMin() < 0 && this.getMax() < 0) {
               ret = this.child.toString(options) + "*";
            } else if (this.getMin() == this.getMax()) {
               ret = this.child.toString(options) + "{" + this.getMin() + "}";
            } else if (this.getMin() >= 0 && this.getMax() >= 0) {
               ret = this.child.toString(options) + "{" + this.getMin() + "," + this.getMax() + "}";
            } else {
               if (this.getMin() < 0 || this.getMax() >= 0) {
                  throw new RuntimeException("Token#toString(): CLOSURE " + this.getMin() + ", " + this.getMax());
               }

               ret = this.child.toString(options) + "{" + this.getMin() + ",}";
            }
         } else if (this.getMin() < 0 && this.getMax() < 0) {
            ret = this.child.toString(options) + "*?";
         } else if (this.getMin() == this.getMax()) {
            ret = this.child.toString(options) + "{" + this.getMin() + "}?";
         } else if (this.getMin() >= 0 && this.getMax() >= 0) {
            ret = this.child.toString(options) + "{" + this.getMin() + "," + this.getMax() + "}?";
         } else {
            if (this.getMin() < 0 || this.getMax() >= 0) {
               throw new RuntimeException("Token#toString(): NONGREEDYCLOSURE " + this.getMin() + ", " + this.getMax());
            }

            ret = this.child.toString(options) + "{" + this.getMin() + ",}?";
         }

         return ret;
      }
   }

   static class CharToken extends Token implements Serializable {
      int chardata;

      CharToken(int type, int ch) {
         super(type);
         this.chardata = ch;
      }

      int getChar() {
         return this.chardata;
      }

      public String toString(int options) {
         String ret;
         switch (this.type) {
            case 0:
               switch (this.chardata) {
                  case 9:
                     ret = "\\t";
                     return ret;
                  case 10:
                     ret = "\\n";
                     return ret;
                  case 12:
                     ret = "\\f";
                     return ret;
                  case 13:
                     ret = "\\r";
                     return ret;
                  case 27:
                     ret = "\\e";
                     return ret;
                  case 40:
                  case 41:
                  case 42:
                  case 43:
                  case 46:
                  case 63:
                  case 91:
                  case 92:
                  case 123:
                  case 124:
                     ret = "\\" + (char)this.chardata;
                     return ret;
                  default:
                     if (this.chardata >= 65536) {
                        String pre = "0" + Integer.toHexString(this.chardata);
                        ret = "\\v" + pre.substring(pre.length() - 6, pre.length());
                     } else {
                        ret = "" + (char)this.chardata;
                     }

                     return ret;
               }
            case 8:
               if (this != Token.token_linebeginning && this != Token.token_lineend) {
                  ret = "\\" + (char)this.chardata;
               } else {
                  ret = "" + (char)this.chardata;
               }
               break;
            default:
               ret = null;
         }

         return ret;
      }

      boolean match(int ch) {
         if (this.type == 0) {
            return ch == this.chardata;
         } else {
            throw new RuntimeException("NFAArrow#match(): Internal error: " + this.type);
         }
      }
   }

   static class ConcatToken extends Token implements Serializable {
      Token child;
      Token child2;

      ConcatToken(Token t1, Token t2) {
         super(1);
         this.child = t1;
         this.child2 = t2;
      }

      int size() {
         return 2;
      }

      Token getChild(int index) {
         return index == 0 ? this.child : this.child2;
      }

      public String toString(int options) {
         String ret;
         if (this.child2.type == 3 && this.child2.getChild(0) == this.child) {
            ret = this.child.toString(options) + "+";
         } else if (this.child2.type == 9 && this.child2.getChild(0) == this.child) {
            ret = this.child.toString(options) + "+?";
         } else {
            ret = this.child.toString(options) + this.child2.toString(options);
         }

         return ret;
      }
   }

   static class StringToken extends Token implements Serializable {
      String string;
      int refNumber;

      StringToken(int type, String str, int n) {
         super(type);
         this.string = str;
         this.refNumber = n;
      }

      int getReferenceNumber() {
         return this.refNumber;
      }

      String getString() {
         return this.string;
      }

      public String toString(int options) {
         return this.type == 12 ? "\\" + this.refNumber : REUtil.quoteMeta(this.string);
      }
   }

   static class FixedStringContainer {
      Token token = null;
      int options = 0;
   }
}
