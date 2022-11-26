package com.bea.xbean.regex;

import java.util.Hashtable;
import java.util.Locale;

class ParserForXMLSchema extends RegexParser {
   private static Hashtable ranges = null;
   private static Hashtable ranges2 = null;
   private static final String SPACES = "\t\n\r\r  ";
   private static final String NAMECHARS = "-.0:AZ__az··ÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁːˑ̀͠͡ͅΆΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁ҃҆ҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆֹֻֽֿֿׁׂ֑֣֡ׄׄאתװײءغـْ٠٩ٰڷںھۀێېۓە۪ۭۨ۰۹ँःअह़्॑॔क़ॣ०९ঁঃঅঌএঐওনপরললশহ়়াৄেৈো্ৗৗড়ঢ়য়ৣ০ৱਂਂਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹ਼਼ਾੂੇੈੋ੍ਖ਼ੜਫ਼ਫ਼੦ੴઁઃઅઋઍઍએઑઓનપરલળવહ઼ૅેૉો્ૠૠ૦૯ଁଃଅଌଏଐଓନପରଲଳଶହ଼ୃେୈୋ୍ୖୗଡ଼ଢ଼ୟୡ୦୯ஂஃஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹாூெைொ்ௗௗ௧௯ఁఃఅఌఎఐఒనపళవహాౄెైొ్ౕౖౠౡ౦౯ಂಃಅಌಎಐಒನಪಳವಹಾೄೆೈೊ್ೕೖೞೞೠೡ೦೯ംഃഅഌഎഐഒനപഹാൃെൈൊ്ൗൗൠൡ൦൯กฮะฺเ๎๐๙ກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະູົຽເໄໆໆ່ໍ໐໙༘༙༠༩༹༹༵༵༷༷༾ཇཉཀྵ྄ཱ྆ྋྐྕྗྗྙྭྱྷྐྵྐྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼ⃐⃜⃡⃡ΩΩKÅ℮℮ↀↂ々々〇〇〡〯〱〵ぁゔ゙゚ゝゞァヺーヾㄅㄬ一龥가힣";
   private static final String LETTERS = "AZazÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁΆΆΈΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆאתװײءغفيٱڷںھۀێېۓەەۥۦअहऽऽक़ॡঅঌএঐওনপরললশহড়ঢ়য়ৡৰৱਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹਖ਼ੜਫ਼ਫ਼ੲੴઅઋઍઍએઑઓનપરલળવહઽઽૠૠଅଌଏଐଓନପରଲଳଶହଽଽଡ଼ଢ଼ୟୡஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹఅఌఎఐఒనపళవహౠౡಅಌಎಐಒನಪಳವಹೞೞೠೡഅഌഎഐഒനപഹൠൡกฮะะาำเๅກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະະາຳຽຽເໄཀཇཉཀྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼΩΩKÅ℮℮ↀↂ〇〇〡〩ぁゔァヺㄅㄬ一龥가힣";
   private static final String DIGITS = "09٠٩۰۹०९০৯੦੯૦૯୦୯௧௯౦౯೦೯൦൯๐๙໐໙༠༩";

   public ParserForXMLSchema() {
   }

   public ParserForXMLSchema(Locale locale) {
   }

   Token processCaret() throws ParseException {
      this.next();
      return Token.createChar(94);
   }

   Token processDollar() throws ParseException {
      this.next();
      return Token.createChar(36);
   }

   Token processLookahead() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processNegativelookahead() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processLookbehind() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processNegativelookbehind() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_A() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_Z() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_z() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_b() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_B() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_lt() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_gt() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processStar(Token tok) throws ParseException {
      this.next();
      return Token.createClosure(tok);
   }

   Token processPlus(Token tok) throws ParseException {
      this.next();
      return Token.createConcat(tok, Token.createClosure(tok));
   }

   Token processQuestion(Token tok) throws ParseException {
      this.next();
      Token par = Token.createUnion();
      par.addChild(tok);
      par.addChild(Token.createEmpty());
      return par;
   }

   boolean checkQuestion(int off) {
      return false;
   }

   Token processParen() throws ParseException {
      this.next();
      Token tok = Token.createParen(this.parseRegex(), 0);
      if (this.read() != 7) {
         throw this.ex("parser.factor.1", this.offset - 1);
      } else {
         this.next();
         return tok;
      }
   }

   Token processParen2() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processCondition() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processModifiers() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processIndependent() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token processBacksolidus_c() throws ParseException {
      this.next();
      return this.getTokenForShorthand(99);
   }

   Token processBacksolidus_C() throws ParseException {
      this.next();
      return this.getTokenForShorthand(67);
   }

   Token processBacksolidus_i() throws ParseException {
      this.next();
      return this.getTokenForShorthand(105);
   }

   Token processBacksolidus_I() throws ParseException {
      this.next();
      return this.getTokenForShorthand(73);
   }

   Token processBacksolidus_g() throws ParseException {
      throw this.ex("parser.process.1", this.offset - 2);
   }

   Token processBacksolidus_X() throws ParseException {
      throw this.ex("parser.process.1", this.offset - 2);
   }

   Token processBackreference() throws ParseException {
      throw this.ex("parser.process.1", this.offset - 4);
   }

   int processCIinCharacterClass(RangeToken tok, int c) {
      tok.mergeRanges(this.getTokenForShorthand(c));
      return -1;
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
         base = Token.createRange();
         base.addRange(0, 1114111);
         tok = Token.createRange();
      } else {
         tok = Token.createRange();
      }

      boolean firstloop = true;

      while(true) {
         int type;
         int c;
         boolean end;
         int rangeend;
         label156: {
            if ((type = this.read()) != 1) {
               if (type == 0 && this.chardata == 93 && !firstloop) {
                  if (nrange) {
                     base.subtractRanges(tok);
                     tok = base;
                  }
               } else {
                  c = this.chardata;
                  end = false;
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
                           break label156;
                        case 68:
                        case 83:
                        case 87:
                        case 100:
                        case 115:
                        case 119:
                           tok.mergeRanges(this.getTokenForShorthand(c));
                           end = true;
                           break label156;
                        case 80:
                        case 112:
                           rangeend = this.offset;
                           RangeToken tok2 = this.processBacksolidus_pP(c);
                           if (tok2 == null) {
                              throw this.ex("parser.atom.5", rangeend);
                           }

                           tok.mergeRanges(tok2);
                           end = true;
                           break label156;
                        default:
                           c = this.decodeEscaped();
                           break label156;
                     }
                  }

                  if (type != 24 || firstloop) {
                     break label156;
                  }

                  if (nrange) {
                     base.subtractRanges(tok);
                     tok = base;
                  }

                  RangeToken range2 = this.parseCharacterClass(false);
                  tok.subtractRanges(range2);
                  if (this.read() != 0 || this.chardata != 93) {
                     throw this.ex("parser.cc.5", this.offset);
                  }
               }
            }

            if (this.read() == 1) {
               throw this.ex("parser.cc.2", this.offset);
            }

            tok.sortRanges();
            tok.compactRanges();
            this.setContext(0);
            this.next();
            return tok;
         }

         this.next();
         if (!end) {
            if (type == 0) {
               if (c == 91) {
                  throw this.ex("parser.cc.6", this.offset - 2);
               }

               if (c == 93) {
                  throw this.ex("parser.cc.7", this.offset - 2);
               }

               if (c == 45 && !firstloop && this.chardata != 93) {
                  throw this.ex("parser.cc.8", this.offset - 2);
               }
            }

            if (this.read() == 0 && this.chardata == 45) {
               this.next();
               if ((type = this.read()) == 1) {
                  throw this.ex("parser.cc.2", this.offset);
               }

               if (type == 24) {
                  throw this.ex("parser.cc.8", this.offset - 1);
               }

               if (type == 0 && this.chardata == 93) {
                  tok.addRange(c, c);
                  tok.addRange(45, 45);
               } else {
                  rangeend = this.chardata;
                  if (type == 0) {
                     if (rangeend == 91) {
                        throw this.ex("parser.cc.6", this.offset - 1);
                     }

                     if (rangeend == 93) {
                        throw this.ex("parser.cc.7", this.offset - 1);
                     }

                     if (rangeend == 45) {
                        this.next();
                        if (this.chardata != 93) {
                           throw this.ex("parser.cc.8", this.offset - 2);
                        }
                     }
                  } else if (type == 10) {
                     rangeend = this.decodeEscaped();
                  }

                  if (rangeend != 45 || this.chardata != 93) {
                     this.next();
                  }

                  if (c > rangeend) {
                     throw this.ex("parser.ope.3", this.offset - 1);
                  }

                  tok.addRange(c, rangeend);
               }
            } else {
               tok.addRange(c, c);
            }
         }

         firstloop = false;
      }
   }

   protected RangeToken parseSetOperations() throws ParseException {
      throw this.ex("parser.process.1", this.offset);
   }

   Token getTokenForShorthand(int ch) {
      switch (ch) {
         case 67:
            return getRange("xml:isNameChar", false);
         case 68:
            return getRange("xml:isDigit", false);
         case 73:
            return getRange("xml:isInitialNameChar", false);
         case 83:
            return getRange("xml:isSpace", false);
         case 87:
            return getRange("xml:isWord", false);
         case 99:
            return getRange("xml:isNameChar", true);
         case 100:
            return getRange("xml:isDigit", true);
         case 105:
            return getRange("xml:isInitialNameChar", true);
         case 115:
            return getRange("xml:isSpace", true);
         case 119:
            return getRange("xml:isWord", true);
         default:
            throw new RuntimeException("Internal Error: shorthands: \\u" + Integer.toString(ch, 16));
      }
   }

   int decodeEscaped() throws ParseException {
      if (this.read() != 10) {
         throw this.ex("parser.next.1", this.offset - 1);
      } else {
         int c = this.chardata;
         switch (c) {
            case 40:
            case 41:
            case 42:
            case 43:
            case 45:
            case 46:
            case 63:
            case 91:
            case 92:
            case 93:
            case 94:
            case 123:
            case 124:
            case 125:
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
            default:
               throw this.ex("parser.process.1", this.offset - 2);
         }

         return c;
      }
   }

   protected static synchronized RangeToken getRange(String name, boolean positive) {
      RangeToken tok;
      if (ranges == null) {
         ranges = new Hashtable();
         ranges2 = new Hashtable();
         tok = Token.createRange();
         setupRange(tok, "\t\n\r\r  ");
         ranges.put("xml:isSpace", tok);
         ranges2.put("xml:isSpace", Token.complementRanges(tok));
         tok = Token.createRange();
         setupRange(tok, "09٠٩۰۹०९০৯੦੯૦૯୦୯௧௯౦౯೦೯൦൯๐๙໐໙༠༩");
         ranges.put("xml:isDigit", tok);
         ranges2.put("xml:isDigit", Token.complementRanges(tok));
         tok = Token.createRange();
         setupRange(tok, "09٠٩۰۹०९০৯੦੯૦૯୦୯௧௯౦౯೦೯൦൯๐๙໐໙༠༩");
         ranges.put("xml:isDigit", tok);
         ranges2.put("xml:isDigit", Token.complementRanges(tok));
         tok = Token.createRange();
         setupRange(tok, "AZazÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁΆΆΈΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆאתװײءغفيٱڷںھۀێېۓەەۥۦअहऽऽक़ॡঅঌএঐওনপরললশহড়ঢ়য়ৡৰৱਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹਖ਼ੜਫ਼ਫ਼ੲੴઅઋઍઍએઑઓનપરલળવહઽઽૠૠଅଌଏଐଓନପରଲଳଶହଽଽଡ଼ଢ଼ୟୡஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹఅఌఎఐఒనపళవహౠౡಅಌಎಐಒನಪಳವಹೞೞೠೡഅഌഎഐഒനപഹൠൡกฮะะาำเๅກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະະາຳຽຽເໄཀཇཉཀྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼΩΩKÅ℮℮ↀↂ〇〇〡〩ぁゔァヺㄅㄬ一龥가힣");
         tok.mergeRanges((Token)ranges.get("xml:isDigit"));
         ranges.put("xml:isWord", tok);
         ranges2.put("xml:isWord", Token.complementRanges(tok));
         tok = Token.createRange();
         setupRange(tok, "-.0:AZ__az··ÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁːˑ̀͠͡ͅΆΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁ҃҆ҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆֹֻֽֿֿׁׂ֑֣֡ׄׄאתװײءغـْ٠٩ٰڷںھۀێېۓە۪ۭۨ۰۹ँःअह़्॑॔क़ॣ०९ঁঃঅঌএঐওনপরললশহ়়াৄেৈো্ৗৗড়ঢ়য়ৣ০ৱਂਂਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹ਼਼ਾੂੇੈੋ੍ਖ਼ੜਫ਼ਫ਼੦ੴઁઃઅઋઍઍએઑઓનપરલળવહ઼ૅેૉો્ૠૠ૦૯ଁଃଅଌଏଐଓନପରଲଳଶହ଼ୃେୈୋ୍ୖୗଡ଼ଢ଼ୟୡ୦୯ஂஃஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹாூெைொ்ௗௗ௧௯ఁఃఅఌఎఐఒనపళవహాౄెైొ్ౕౖౠౡ౦౯ಂಃಅಌಎಐಒನಪಳವಹಾೄೆೈೊ್ೕೖೞೞೠೡ೦೯ംഃഅഌഎഐഒനപഹാൃെൈൊ്ൗൗൠൡ൦൯กฮะฺเ๎๐๙ກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະູົຽເໄໆໆ່ໍ໐໙༘༙༠༩༹༹༵༵༷༷༾ཇཉཀྵ྄ཱ྆ྋྐྕྗྗྙྭྱྷྐྵྐྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼ⃐⃜⃡⃡ΩΩKÅ℮℮ↀↂ々々〇〇〡〯〱〵ぁゔ゙゚ゝゞァヺーヾㄅㄬ一龥가힣");
         ranges.put("xml:isNameChar", tok);
         ranges2.put("xml:isNameChar", Token.complementRanges(tok));
         tok = Token.createRange();
         setupRange(tok, "AZazÀÖØöøıĴľŁňŊžƀǃǍǰǴǵǺȗɐʨʻˁΆΆΈΊΌΌΎΡΣώϐϖϚϚϜϜϞϞϠϠϢϳЁЌЎяёќўҁҐӄӇӈӋӌӐӫӮӵӸӹԱՖՙՙաֆאתװײءغفيٱڷںھۀێېۓەەۥۦअहऽऽक़ॡঅঌএঐওনপরললশহড়ঢ়য়ৡৰৱਅਊਏਐਓਨਪਰਲਲ਼ਵਸ਼ਸਹਖ਼ੜਫ਼ਫ਼ੲੴઅઋઍઍએઑઓનપરલળવહઽઽૠૠଅଌଏଐଓନପରଲଳଶହଽଽଡ଼ଢ଼ୟୡஅஊஎஐஒகஙசஜஜஞடணதநபமவஷஹఅఌఎఐఒనపళవహౠౡಅಌಎಐಒನಪಳವಹೞೞೠೡഅഌഎഐഒനപഹൠൡกฮะะาำเๅກຂຄຄງຈຊຊຍຍດທນຟມຣລລວວສຫອຮະະາຳຽຽເໄཀཇཉཀྵႠჅაჶᄀᄀᄂᄃᄅᄇᄉᄉᄋᄌᄎᄒᄼᄼᄾᄾᅀᅀᅌᅌᅎᅎᅐᅐᅔᅕᅙᅙᅟᅡᅣᅣᅥᅥᅧᅧᅩᅩᅭᅮᅲᅳᅵᅵᆞᆞᆨᆨᆫᆫᆮᆯᆷᆸᆺᆺᆼᇂᇫᇫᇰᇰᇹᇹḀẛẠỹἀἕἘἝἠὅὈὍὐὗὙὙὛὛὝὝὟώᾀᾴᾶᾼιιῂῄῆῌῐΐῖΊῠῬῲῴῶῼΩΩKÅ℮℮ↀↂ〇〇〡〩ぁゔァヺㄅㄬ一龥가힣");
         tok.addRange(95, 95);
         tok.addRange(58, 58);
         ranges.put("xml:isInitialNameChar", tok);
         ranges2.put("xml:isInitialNameChar", Token.complementRanges(tok));
      }

      tok = positive ? (RangeToken)ranges.get(name) : (RangeToken)ranges2.get(name);
      return tok;
   }

   static void setupRange(Token range, String src) {
      int len = src.length();

      for(int i = 0; i < len; i += 2) {
         range.addRange(src.charAt(i), src.charAt(i + 1));
      }

   }
}
