package org.python.icu.impl.number;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.python.icu.impl.StandardPlural;
import org.python.icu.impl.TextTrieMap;
import org.python.icu.impl.number.formatters.BigDecimalMultiplier;
import org.python.icu.impl.number.formatters.CurrencyFormat;
import org.python.icu.impl.number.formatters.MagnitudeMultiplier;
import org.python.icu.impl.number.formatters.PaddingFormat;
import org.python.icu.impl.number.formatters.PositiveDecimalFormat;
import org.python.icu.impl.number.formatters.PositiveNegativeAffixFormat;
import org.python.icu.impl.number.formatters.ScientificFormat;
import org.python.icu.lang.UCharacter;
import org.python.icu.text.CurrencyPluralInfo;
import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.text.NumberFormat;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.Currency;
import org.python.icu.util.CurrencyAmount;
import org.python.icu.util.ULocale;

public class Parse {
   private static final UnicodeSet UNISET_WHITESPACE = (new UnicodeSet("[[:whitespace:][\\u2000-\\u200D]]")).freeze();
   private static final UnicodeSet UNISET_BIDI = (new UnicodeSet("[[\\u200E\\u200F\\u061C]]")).freeze();
   private static final UnicodeSet UNISET_PERIOD_LIKE = (new UnicodeSet("[.\\u2024\\u3002\\uFE12\\uFE52\\uFF0E\\uFF61]")).freeze();
   private static final UnicodeSet UNISET_STRICT_PERIOD_LIKE = (new UnicodeSet("[.\\u2024\\uFE52\\uFF0E\\uFF61]")).freeze();
   private static final UnicodeSet UNISET_COMMA_LIKE = (new UnicodeSet("[,\\u060C\\u066B\\u3001\\uFE10\\uFE11\\uFE50\\uFE51\\uFF0C\\uFF64]")).freeze();
   private static final UnicodeSet UNISET_STRICT_COMMA_LIKE = (new UnicodeSet("[,\\u066B\\uFE10\\uFE50\\uFF0C]")).freeze();
   private static final UnicodeSet UNISET_OTHER_GROUPING_SEPARATORS = (new UnicodeSet("[\\ '\\u00A0\\u066C\\u2000-\\u200A\\u2018\\u2019\\u202F\\u205F\\u3000\\uFF07]")).freeze();
   protected static final ThreadLocal threadLocalParseState = new ThreadLocal() {
      protected ParserState initialValue() {
         return new ParserState();
      }
   };
   protected static final ThreadLocal threadLocalParsePosition = new ThreadLocal() {
      protected ParsePosition initialValue() {
         return new ParsePosition(0);
      }
   };
   /** @deprecated */
   @Deprecated
   public static final UnicodeSet UNISET_PLUS = (new UnicodeSet(new int[]{43, 43, 8314, 8314, 8330, 8330, 10133, 10133, 64297, 64297, 65122, 65122, 65291, 65291})).freeze();
   /** @deprecated */
   @Deprecated
   public static final UnicodeSet UNISET_MINUS = (new UnicodeSet(new int[]{45, 45, 8315, 8315, 8331, 8331, 8722, 8722, 10134, 10134, 65123, 65123, 65293, 65293})).freeze();
   public static volatile boolean DEBUGGING = false;

   static TextTrieMap makeDigitTrie(String[] digitStrings) {
      boolean requiresTrie = false;

      for(int i = 0; i < 10; ++i) {
         String str = digitStrings[i];
         if (Character.charCount(Character.codePointAt(str, 0)) != str.length()) {
            requiresTrie = true;
            break;
         }
      }

      if (!requiresTrie) {
         return null;
      } else {
         TextTrieMap trieMap = new TextTrieMap(false);

         for(int i = 0; i < 10; ++i) {
            trieMap.put(digitStrings[i], (byte)i);
         }

         return trieMap;
      }
   }

   public static Number parse(String input, IProperties properties, DecimalFormatSymbols symbols) {
      ParsePosition ppos = (ParsePosition)threadLocalParsePosition.get();
      ppos.setIndex(0);
      return parse(input, ppos, properties, symbols);
   }

   public static Number parse(CharSequence input, ParsePosition ppos, IProperties properties, DecimalFormatSymbols symbols) {
      StateItem best = _parse(input, ppos, false, properties, symbols);
      return best == null ? null : best.toNumber(properties);
   }

   public static CurrencyAmount parseCurrency(String input, IProperties properties, DecimalFormatSymbols symbols) throws ParseException {
      return parseCurrency(input, (ParsePosition)null, properties, symbols);
   }

   public static CurrencyAmount parseCurrency(CharSequence input, ParsePosition ppos, IProperties properties, DecimalFormatSymbols symbols) throws ParseException {
      if (ppos == null) {
         ppos = (ParsePosition)threadLocalParsePosition.get();
         ppos.setIndex(0);
         ppos.setErrorIndex(-1);
      }

      StateItem best = _parse(input, ppos, true, properties, symbols);
      return best == null ? null : best.toCurrencyAmount(properties);
   }

   private static StateItem _parse(CharSequence input, ParsePosition ppos, boolean parseCurrency, IProperties properties, DecimalFormatSymbols symbols) {
      if (input != null && ppos != null && properties != null && symbols != null) {
         ParseMode mode = properties.getParseMode();
         if (mode == null) {
            mode = Parse.ParseMode.LENIENT;
         }

         boolean integerOnly = properties.getParseIntegerOnly();
         boolean ignoreExponent = properties.getParseNoExponent();
         ParserState state = ((ParserState)threadLocalParseState.get()).clear();
         state.properties = properties;
         state.symbols = symbols;
         state.mode = mode;
         state.parseCurrency = parseCurrency;
         state.groupingMode = properties.getParseGroupingMode();
         if (state.groupingMode == null) {
            state.groupingMode = Parse.GroupingMode.DEFAULT;
         }

         state.caseSensitive = properties.getParseCaseSensitive();
         state.decimalCp1 = Character.codePointAt(symbols.getDecimalSeparatorString(), 0);
         state.decimalCp2 = Character.codePointAt(symbols.getMonetaryDecimalSeparatorString(), 0);
         state.groupingCp1 = Character.codePointAt(symbols.getGroupingSeparatorString(), 0);
         state.groupingCp2 = Character.codePointAt(symbols.getMonetaryGroupingSeparatorString(), 0);
         state.decimalType1 = Parse.SeparatorType.fromCp(state.decimalCp1, mode);
         state.decimalType2 = Parse.SeparatorType.fromCp(state.decimalCp2, mode);
         state.groupingType1 = Parse.SeparatorType.fromCp(state.groupingCp1, mode);
         state.groupingType2 = Parse.SeparatorType.fromCp(state.groupingCp2, mode);
         StateItem initialStateItem = state.getNext().clear();
         initialStateItem.name = Parse.StateName.BEFORE_PREFIX;
         if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.STRICT) {
            state.digitTrie = makeDigitTrie(symbols.getDigitStringsLocal());
            Parse.AffixHolder.addToState(state, properties);
            if (parseCurrency) {
               Parse.CurrencyAffixPatterns.addToState(symbols.getULocale(), state);
            }
         }

         if (DEBUGGING) {
            System.out.println("Parsing: " + input);
            System.out.println(properties);
            System.out.println(state.affixHolders);
         }

         int offset;
         int cp;
         int i;
         StateItem item;
         for(offset = ppos.getIndex(); offset < input.length(); offset += Character.charCount(cp)) {
            cp = Character.codePointAt(input, offset);
            state.swap();

            for(i = 0; i < state.prevLength; ++i) {
               item = state.prevItems[i];
               if (DEBUGGING) {
                  System.out.println(":" + offset + item.id + " " + item);
               }

               switch (item.name) {
                  case BEFORE_PREFIX:
                     if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) {
                        acceptMinusOrPlusSign(cp, Parse.StateName.BEFORE_PREFIX, state, item, false);
                        if (state.length > 0 && mode == Parse.ParseMode.FAST) {
                           break;
                        }
                     }

                     acceptIntegerDigit(cp, Parse.StateName.AFTER_INTEGER_DIGIT, state, item);
                     if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                        acceptBidi(cp, Parse.StateName.BEFORE_PREFIX, state, item);
                        if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                           acceptWhitespace(cp, Parse.StateName.BEFORE_PREFIX, state, item);
                           if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                              acceptPadding(cp, Parse.StateName.BEFORE_PREFIX, state, item);
                              if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                                 acceptNan(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                                 if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                                    acceptInfinity(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                                    if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                                       if (!integerOnly) {
                                          acceptDecimalPoint(cp, Parse.StateName.AFTER_FRACTION_DIGIT, state, item);
                                          if (state.length > 0 && mode == Parse.ParseMode.FAST) {
                                             break;
                                          }
                                       }

                                       if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.STRICT) {
                                          acceptPrefix(cp, Parse.StateName.AFTER_PREFIX, state, item);
                                       }

                                       if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) {
                                          acceptGrouping(cp, Parse.StateName.AFTER_INTEGER_DIGIT, state, item);
                                          if ((state.length <= 0 || mode != Parse.ParseMode.FAST) && parseCurrency) {
                                             acceptCurrency(cp, Parse.StateName.BEFORE_PREFIX, state, item);
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                     break;
                  case AFTER_PREFIX:
                     acceptBidi(cp, Parse.StateName.AFTER_PREFIX, state, item);
                     acceptPadding(cp, Parse.StateName.AFTER_PREFIX, state, item);
                     acceptNan(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                     acceptInfinity(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                     acceptIntegerDigit(cp, Parse.StateName.AFTER_INTEGER_DIGIT, state, item);
                     if (!integerOnly) {
                        acceptDecimalPoint(cp, Parse.StateName.AFTER_FRACTION_DIGIT, state, item);
                     }

                     if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) {
                        acceptWhitespace(cp, Parse.StateName.AFTER_PREFIX, state, item);
                        acceptGrouping(cp, Parse.StateName.AFTER_INTEGER_DIGIT, state, item);
                        if (parseCurrency) {
                           acceptCurrency(cp, Parse.StateName.AFTER_PREFIX, state, item);
                        }
                     }
                     break;
                  case AFTER_INTEGER_DIGIT:
                     acceptIntegerDigit(cp, Parse.StateName.AFTER_INTEGER_DIGIT, state, item);
                     if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                        if (!integerOnly) {
                           acceptDecimalPoint(cp, Parse.StateName.AFTER_FRACTION_DIGIT, state, item);
                           if (state.length > 0 && mode == Parse.ParseMode.FAST) {
                              continue;
                           }
                        }

                        acceptGrouping(cp, Parse.StateName.AFTER_INTEGER_DIGIT, state, item);
                        if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                           acceptBidi(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                           if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                              acceptPadding(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                              if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                                 if (!ignoreExponent) {
                                    acceptExponentSeparator(cp, Parse.StateName.AFTER_EXPONENT_SEPARATOR, state, item);
                                    if (state.length > 0 && mode == Parse.ParseMode.FAST) {
                                       continue;
                                    }
                                 }

                                 if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.STRICT) {
                                    acceptSuffix(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                                 }

                                 if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) {
                                    acceptWhitespace(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                                    if ((state.length <= 0 || mode != Parse.ParseMode.FAST) && (state.length <= 0 || mode != Parse.ParseMode.FAST) && parseCurrency) {
                                       acceptCurrency(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                                    }
                                 }
                              }
                           }
                        }
                     }
                     break;
                  case AFTER_FRACTION_DIGIT:
                     acceptFractionDigit(cp, Parse.StateName.AFTER_FRACTION_DIGIT, state, item);
                     if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                        acceptBidi(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                        if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                           acceptPadding(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                           if (state.length <= 0 || mode != Parse.ParseMode.FAST) {
                              if (!ignoreExponent) {
                                 acceptExponentSeparator(cp, Parse.StateName.AFTER_EXPONENT_SEPARATOR, state, item);
                                 if (state.length > 0 && mode == Parse.ParseMode.FAST) {
                                    continue;
                                 }
                              }

                              if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.STRICT) {
                                 acceptSuffix(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                              }

                              if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) {
                                 acceptWhitespace(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                                 if ((state.length <= 0 || mode != Parse.ParseMode.FAST) && (state.length <= 0 || mode != Parse.ParseMode.FAST) && parseCurrency) {
                                    acceptCurrency(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                                 }
                              }
                           }
                        }
                     }
                     break;
                  case AFTER_EXPONENT_SEPARATOR:
                     acceptBidi(cp, Parse.StateName.AFTER_EXPONENT_SEPARATOR, state, item);
                     acceptMinusOrPlusSign(cp, Parse.StateName.AFTER_EXPONENT_SEPARATOR, state, item, true);
                     acceptExponentDigit(cp, Parse.StateName.AFTER_EXPONENT_DIGIT, state, item);
                     break;
                  case AFTER_EXPONENT_DIGIT:
                     acceptBidi(cp, Parse.StateName.BEFORE_SUFFIX_SEEN_EXPONENT, state, item);
                     acceptPadding(cp, Parse.StateName.BEFORE_SUFFIX_SEEN_EXPONENT, state, item);
                     acceptExponentDigit(cp, Parse.StateName.AFTER_EXPONENT_DIGIT, state, item);
                     if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.STRICT) {
                        acceptSuffix(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                     }

                     if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) {
                        acceptWhitespace(cp, Parse.StateName.BEFORE_SUFFIX_SEEN_EXPONENT, state, item);
                        if (parseCurrency) {
                           acceptCurrency(cp, Parse.StateName.BEFORE_SUFFIX_SEEN_EXPONENT, state, item);
                        }
                     }
                     break;
                  case BEFORE_SUFFIX:
                     acceptBidi(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                     acceptPadding(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                     if (!ignoreExponent) {
                        acceptExponentSeparator(cp, Parse.StateName.AFTER_EXPONENT_SEPARATOR, state, item);
                     }

                     if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.STRICT) {
                        acceptSuffix(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                     }

                     if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) {
                        acceptWhitespace(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                        if (parseCurrency) {
                           acceptCurrency(cp, Parse.StateName.BEFORE_SUFFIX, state, item);
                        }
                     }
                     break;
                  case BEFORE_SUFFIX_SEEN_EXPONENT:
                     acceptBidi(cp, Parse.StateName.BEFORE_SUFFIX_SEEN_EXPONENT, state, item);
                     acceptPadding(cp, Parse.StateName.BEFORE_SUFFIX_SEEN_EXPONENT, state, item);
                     if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.STRICT) {
                        acceptSuffix(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                     }

                     if (mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) {
                        acceptWhitespace(cp, Parse.StateName.BEFORE_SUFFIX_SEEN_EXPONENT, state, item);
                        if (parseCurrency) {
                           acceptCurrency(cp, Parse.StateName.BEFORE_SUFFIX_SEEN_EXPONENT, state, item);
                        }
                     }
                     break;
                  case AFTER_SUFFIX:
                     if ((mode == Parse.ParseMode.LENIENT || mode == Parse.ParseMode.FAST) && parseCurrency) {
                        acceptBidi(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                        acceptPadding(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                        acceptWhitespace(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                        if (parseCurrency) {
                           acceptCurrency(cp, Parse.StateName.AFTER_SUFFIX, state, item);
                        }
                     }
                     break;
                  case INSIDE_CURRENCY:
                     acceptCurrencyOffset(cp, state, item);
                     break;
                  case INSIDE_DIGIT:
                     acceptDigitTrieOffset(cp, state, item);
                     break;
                  case INSIDE_STRING:
                     acceptStringOffset(cp, state, item);
                     break;
                  case INSIDE_AFFIX_PATTERN:
                     acceptAffixPatternOffset(cp, state, item);
               }
            }

            if (state.length == 0) {
               state.swapBack();
               break;
            }
         }

         if (state.length == 0) {
            if (DEBUGGING) {
               System.out.println("No matches found");
               System.out.println("- - - - - - - - - -");
            }

            return null;
         } else {
            StateItem best = null;

            label374:
            for(i = 0; i < state.length; ++i) {
               item = state.items[i];
               if (DEBUGGING) {
                  System.out.println(":end " + item);
               }

               if (!item.hasNumber()) {
                  if (DEBUGGING) {
                     System.out.println("-> rejected due to no number value");
                  }
               } else {
                  if (mode == Parse.ParseMode.STRICT) {
                     boolean sawPrefix = item.sawPrefix || item.affix != null && item.affix.p.isEmpty();
                     boolean sawSuffix = item.sawSuffix || item.affix != null && item.affix.s.isEmpty();
                     boolean hasEmptyAffix = state.affixHolders.contains(Parse.AffixHolder.EMPTY_POSITIVE) || state.affixHolders.contains(Parse.AffixHolder.EMPTY_NEGATIVE);
                     if ((!sawPrefix || !sawSuffix) && (sawPrefix || sawSuffix || !hasEmptyAffix)) {
                        if (DEBUGGING) {
                           System.out.println("-> rejected due to mismatched prefix/suffix");
                        }
                        continue;
                     }

                     if (properties.getMinimumExponentDigits() > 0 && !item.sawExponentDigit) {
                        if (DEBUGGING) {
                           System.out.println("-> reject due to lack of exponent");
                        }
                        continue;
                     }

                     int grouping1 = properties.getGroupingSize();
                     int grouping2 = properties.getSecondaryGroupingSize();
                     grouping1 = grouping1 > 0 ? grouping1 : grouping2;
                     grouping2 = grouping2 > 0 ? grouping2 : grouping1;
                     long groupingWidths = item.groupingWidths;

                     int numGroupingRegions;
                     for(numGroupingRegions = 16 - Long.numberOfLeadingZeros(groupingWidths) / 4; numGroupingRegions > 1 && (groupingWidths & 15L) == 0L; --numGroupingRegions) {
                        if (item.sawDecimalPoint) {
                           if (DEBUGGING) {
                              System.out.println("-> rejected due to decimal point after grouping");
                           }
                           continue label374;
                        }

                        groupingWidths >>>= 4;
                     }

                     if (grouping1 >= 0 && numGroupingRegions > 1) {
                        if ((groupingWidths & 15L) != (long)grouping1) {
                           if (DEBUGGING) {
                              System.out.println("-> rejected due to first grouping violation");
                           }
                           continue;
                        }

                        if ((groupingWidths >>> (numGroupingRegions - 1) * 4 & 15L) > (long)grouping2) {
                           if (DEBUGGING) {
                              System.out.println("-> rejected due to final grouping violation");
                           }
                           continue;
                        }

                        for(int j = 1; j < numGroupingRegions - 1; ++j) {
                           if ((groupingWidths >>> j * 4 & 15L) != (long)grouping2) {
                              if (DEBUGGING) {
                                 System.out.println("-> rejected due to inner grouping violation");
                              }
                              continue label374;
                           }
                        }
                     }
                  }

                  if (properties.getDecimalPatternMatchRequired() && item.sawDecimalPoint != PositiveDecimalFormat.allowsDecimalPoint(properties)) {
                     if (DEBUGGING) {
                        System.out.println("-> rejected due to decimal point violation");
                     }
                  } else if (parseCurrency && !item.sawCurrency) {
                     if (DEBUGGING) {
                        System.out.println("-> rejected due to lack of currency");
                     }
                  } else if (best == null) {
                     best = item;
                  } else if (item.score > best.score) {
                     best = item;
                  } else if (item.trailingCount < best.trailingCount) {
                     best = item;
                  }
               }
            }

            if (DEBUGGING) {
               System.out.println("- - - - - - - - - -");
            }

            if (best != null) {
               ppos.setIndex(offset - best.trailingCount);
               return best;
            } else {
               ppos.setErrorIndex(offset);
               return null;
            }
         }
      } else {
         throw new IllegalArgumentException("All arguments are required for parse.");
      }
   }

   private static void acceptWhitespace(int cp, StateName nextName, ParserState state, StateItem item) {
      if (UNISET_WHITESPACE.contains(cp)) {
         state.getNext().copyFrom(item, nextName, cp);
      }

   }

   private static void acceptBidi(int cp, StateName nextName, ParserState state, StateItem item) {
      if (UNISET_BIDI.contains(cp)) {
         state.getNext().copyFrom(item, nextName, cp);
      }

   }

   private static void acceptPadding(int cp, StateName nextName, ParserState state, StateItem item) {
      CharSequence padding = state.properties.getPadString();
      if (padding != null && padding.length() != 0) {
         int referenceCp = Character.codePointAt(padding, 0);
         if (cp == referenceCp) {
            state.getNext().copyFrom(item, nextName, cp);
         }

      }
   }

   private static void acceptIntegerDigit(int cp, StateName nextName, ParserState state, StateItem item) {
      acceptDigitHelper(cp, nextName, state, item, Parse.DigitType.INTEGER);
   }

   private static void acceptFractionDigit(int cp, StateName nextName, ParserState state, StateItem item) {
      acceptDigitHelper(cp, nextName, state, item, Parse.DigitType.FRACTION);
   }

   private static void acceptExponentDigit(int cp, StateName nextName, ParserState state, StateItem item) {
      acceptDigitHelper(cp, nextName, state, item, Parse.DigitType.EXPONENT);
   }

   private static void acceptDigitHelper(int cp, StateName nextName, ParserState state, StateItem item, DigitType type) {
      byte digit = (byte)UCharacter.digit(cp, 10);
      StateItem next = null;
      if (digit >= 0) {
         next = state.getNext().copyFrom(item, nextName, -1);
      }

      if (digit < 0 && (state.mode == Parse.ParseMode.LENIENT || state.mode == Parse.ParseMode.STRICT)) {
         if (state.digitTrie == null) {
            for(byte d = 0; d < 10; ++d) {
               int referenceCp = Character.codePointAt(state.symbols.getDigitStringsLocal()[d], 0);
               if (cp == referenceCp) {
                  digit = d;
                  next = state.getNext().copyFrom(item, nextName, -1);
               }
            }
         } else {
            acceptDigitTrie(cp, nextName, state, item, type);
         }
      }

      recordDigit(next, digit, type);
   }

   private static void recordDigit(StateItem next, byte digit, DigitType type) {
      if (next != null) {
         next.appendDigit(digit, type);
         if (type == Parse.DigitType.INTEGER && (next.groupingWidths & 15L) < 15L) {
            ++next.groupingWidths;
         }

      }
   }

   private static void acceptMinusOrPlusSign(int cp, StateName nextName, ParserState state, StateItem item, boolean exponent) {
      acceptMinusSign(cp, nextName, (StateName)null, state, item, exponent);
      acceptPlusSign(cp, nextName, (StateName)null, state, item, exponent);
   }

   private static long acceptMinusSign(int cp, StateName returnTo1, StateName returnTo2, ParserState state, StateItem item, boolean exponent) {
      if (UNISET_MINUS.contains(cp)) {
         StateItem next = state.getNext().copyFrom(item, returnTo1, -1);
         next.returnTo1 = returnTo2;
         if (exponent) {
            next.sawNegativeExponent = true;
         } else {
            next.sawNegative = true;
         }

         return 1L << state.lastInsertedIndex();
      } else {
         return 0L;
      }
   }

   private static long acceptPlusSign(int cp, StateName returnTo1, StateName returnTo2, ParserState state, StateItem item, boolean exponent) {
      if (UNISET_PLUS.contains(cp)) {
         StateItem next = state.getNext().copyFrom(item, returnTo1, -1);
         next.returnTo1 = returnTo2;
         return 1L << state.lastInsertedIndex();
      } else {
         return 0L;
      }
   }

   private static void acceptGrouping(int cp, StateName nextName, ParserState state, StateItem item) {
      if (item.groupingCp != -1) {
         if (cp == item.groupingCp) {
            StateItem next = state.getNext().copyFrom(item, nextName, cp);
            next.groupingWidths <<= 4;
         }
      } else {
         SeparatorType cpType = Parse.SeparatorType.fromCp(cp, state.mode);
         if (cp != state.groupingCp1 && cp != state.groupingCp2) {
            if (cpType == Parse.SeparatorType.UNKNOWN) {
               return;
            }

            if (state.groupingMode == Parse.GroupingMode.RESTRICTED) {
               if (cpType != state.groupingType1 || cpType != state.groupingType2) {
                  return;
               }
            } else {
               if (cpType == Parse.SeparatorType.COMMA_LIKE && (state.decimalType1 == Parse.SeparatorType.COMMA_LIKE || state.decimalType2 == Parse.SeparatorType.COMMA_LIKE)) {
                  return;
               }

               if (cpType == Parse.SeparatorType.PERIOD_LIKE && (state.decimalType1 == Parse.SeparatorType.PERIOD_LIKE || state.decimalType2 == Parse.SeparatorType.PERIOD_LIKE)) {
                  return;
               }
            }
         }

         StateItem next = state.getNext().copyFrom(item, nextName, cp);
         next.groupingCp = cp;
         next.groupingWidths <<= 4;
      }

   }

   private static void acceptDecimalPoint(int cp, StateName nextName, ParserState state, StateItem item) {
      if (cp != item.groupingCp) {
         SeparatorType cpType = Parse.SeparatorType.fromCp(cp, state.mode);
         if (cpType == state.decimalType1 || cpType == state.decimalType2) {
            if (cpType != Parse.SeparatorType.OTHER_GROUPING && cpType != Parse.SeparatorType.UNKNOWN || cp == state.decimalCp1 || cp == state.decimalCp2) {
               StateItem next = state.getNext().copyFrom(item, nextName, -1);
               next.sawDecimalPoint = true;
            }
         }
      }
   }

   private static void acceptNan(int cp, StateName nextName, ParserState state, StateItem item) {
      CharSequence nan = state.symbols.getNaN();
      long added = acceptString(cp, nextName, (StateName)null, state, item, nan, 0, false);

      for(int i = Long.numberOfTrailingZeros(added); 1L << i <= added; ++i) {
         if ((1L << i & added) != 0L) {
            state.getItem(i).sawNaN = true;
         }
      }

   }

   private static void acceptInfinity(int cp, StateName nextName, ParserState state, StateItem item) {
      CharSequence inf = state.symbols.getInfinity();
      long added = acceptString(cp, nextName, (StateName)null, state, item, inf, 0, false);

      for(int i = Long.numberOfTrailingZeros(added); 1L << i <= added; ++i) {
         if ((1L << i & added) != 0L) {
            state.getItem(i).sawInfinity = true;
         }
      }

   }

   private static void acceptExponentSeparator(int cp, StateName nextName, ParserState state, StateItem item) {
      CharSequence exp = state.symbols.getExponentSeparator();
      acceptString(cp, nextName, (StateName)null, state, item, exp, 0, true);
   }

   private static void acceptPrefix(int cp, StateName nextName, ParserState state, StateItem item) {
      Iterator var4 = state.affixHolders.iterator();

      while(var4.hasNext()) {
         AffixHolder holder = (AffixHolder)var4.next();
         acceptAffixHolder(cp, nextName, state, item, holder, true);
      }

   }

   private static void acceptSuffix(int cp, StateName nextName, ParserState state, StateItem item) {
      if (item.affix != null) {
         acceptAffixHolder(cp, nextName, state, item, item.affix, false);
      } else {
         Iterator var4 = state.affixHolders.iterator();

         while(var4.hasNext()) {
            AffixHolder holder = (AffixHolder)var4.next();
            acceptAffixHolder(cp, nextName, state, item, holder, false);
         }
      }

   }

   private static void acceptAffixHolder(int cp, StateName nextName, ParserState state, StateItem item, AffixHolder holder, boolean prefix) {
      if (holder != null) {
         String str = prefix ? holder.p : holder.s;
         long added;
         if (holder.strings) {
            added = acceptString(cp, nextName, (StateName)null, state, item, str, 0, false);
         } else {
            added = acceptAffixPattern(cp, nextName, state, item, str, AffixPatternUtils.nextToken(0L, str));
         }

         for(int i = Long.numberOfTrailingZeros(added); 1L << i <= added; ++i) {
            if ((1L << i & added) != 0L) {
               StateItem next = state.getItem(i);
               next.affix = holder;
               if (prefix) {
                  next.sawPrefix = true;
               }

               if (!prefix) {
                  next.sawSuffix = true;
               }

               if (holder.negative) {
                  next.sawNegative = true;
               }

               next.score += 10;
               if (!holder.negative) {
                  ++next.score;
               }

               if (!next.sawPrefix && holder.p.isEmpty()) {
                  next.score += 5;
               }

               if (!next.sawSuffix && holder.s.isEmpty()) {
                  next.score += 5;
               }
            }
         }

      }
   }

   private static long acceptStringOffset(int cp, ParserState state, StateItem item) {
      return acceptString(cp, item.returnTo1, item.returnTo2, state, item, item.currentString, item.currentOffset, item.currentTrailing);
   }

   private static long acceptString(int cp, StateName ret1, StateName ret2, ParserState state, StateItem item, CharSequence str, int offset, boolean trailing) {
      return str != null && str.length() != 0 ? acceptStringOrAffixPatternWithIgnorables(cp, ret1, ret2, state, item, str, (long)offset, trailing, true) : 0L;
   }

   private static long acceptStringNonIgnorable(int cp, StateName ret1, StateName ret2, ParserState state, StateItem item, CharSequence str, boolean trailing, int referenceCp, long firstOffsetOrTag, long nextOffsetOrTag) {
      long added = 0L;
      int firstOffset = (int)firstOffsetOrTag;
      int nextOffset = (int)nextOffsetOrTag;
      if (codePointEquals(referenceCp, cp, state)) {
         if (firstOffset < str.length()) {
            added |= acceptStringHelper(cp, ret1, ret2, state, item, str, firstOffset, trailing);
         }

         if (nextOffset >= str.length()) {
            added |= acceptStringHelper(cp, ret1, ret2, state, item, str, nextOffset, trailing);
         }

         return added;
      } else {
         return 0L;
      }
   }

   private static long acceptStringHelper(int cp, StateName returnTo1, StateName returnTo2, ParserState state, StateItem item, CharSequence str, int newOffset, boolean trailing) {
      StateItem next = state.getNext().copyFrom(item, (StateName)null, cp);
      ++next.score;
      if (newOffset < str.length()) {
         next.name = Parse.StateName.INSIDE_STRING;
         next.returnTo1 = returnTo1;
         next.returnTo2 = returnTo2;
         next.currentString = str;
         next.currentOffset = newOffset;
         next.currentTrailing = trailing;
      } else {
         next.name = returnTo1;
         if (!trailing) {
            next.trailingCount = 0;
         }

         next.returnTo1 = returnTo2;
         next.returnTo2 = null;
      }

      return 1L << state.lastInsertedIndex();
   }

   private static long acceptAffixPatternOffset(int cp, ParserState state, StateItem item) {
      return acceptAffixPattern(cp, item.returnTo1, state, item, item.currentAffixPattern, item.currentStepwiseParserTag);
   }

   private static long acceptAffixPattern(int cp, StateName ret1, ParserState state, StateItem item, CharSequence str, long tag) {
      return str != null && str.length() != 0 ? acceptStringOrAffixPatternWithIgnorables(cp, ret1, (StateName)null, state, item, str, tag, false, false) : 0L;
   }

   private static long acceptAffixPatternNonIgnorable(int cp, StateName returnTo, ParserState state, StateItem item, CharSequence str, int typeOrCp, long firstTag, long nextTag) {
      int resolvedCp = -1;
      CharSequence resolvedStr = null;
      boolean resolvedMinusSign = false;
      boolean resolvedPlusSign = false;
      boolean resolvedCurrency = false;
      if (typeOrCp < 0) {
         switch (typeOrCp) {
            case -7:
            case -6:
            case -5:
               resolvedCurrency = true;
               break;
            case -4:
               resolvedStr = state.symbols.getPerMillString();
               if (resolvedStr.length() != 1 || resolvedStr.charAt(0) != 8240) {
                  resolvedCp = 8240;
               }
               break;
            case -3:
               resolvedStr = state.symbols.getPercentString();
               if (resolvedStr.length() != 1 || resolvedStr.charAt(0) != '%') {
                  resolvedCp = 37;
               }
               break;
            case -2:
               resolvedPlusSign = true;
               break;
            case -1:
               resolvedMinusSign = true;
               break;
            default:
               throw new AssertionError();
         }
      } else {
         resolvedCp = typeOrCp;
      }

      long added = 0L;
      if (resolvedCp >= 0 && codePointEquals(cp, resolvedCp, state)) {
         if (firstTag >= 0L) {
            added |= acceptAffixPatternHelper(cp, returnTo, state, item, str, firstTag);
         }

         if (nextTag < 0L) {
            added |= acceptAffixPatternHelper(cp, returnTo, state, item, str, nextTag);
         }
      }

      String pss;
      int pssCp;
      if (resolvedMinusSign) {
         if (firstTag >= 0L) {
            added |= acceptMinusSign(cp, Parse.StateName.INSIDE_AFFIX_PATTERN, returnTo, state, item, false);
         }

         if (nextTag < 0L) {
            added |= acceptMinusSign(cp, returnTo, (StateName)null, state, item, false);
         }

         if (added == 0L) {
            pss = state.symbols.getMinusSignString();
            pssCp = Character.codePointAt(pss, 0);
            if (pss.length() != Character.charCount(pssCp) || !UNISET_MINUS.contains(pssCp)) {
               resolvedStr = pss;
            }
         }
      }

      if (resolvedPlusSign) {
         if (firstTag >= 0L) {
            added |= acceptPlusSign(cp, Parse.StateName.INSIDE_AFFIX_PATTERN, returnTo, state, item, false);
         }

         if (nextTag < 0L) {
            added |= acceptPlusSign(cp, returnTo, (StateName)null, state, item, false);
         }

         if (added == 0L) {
            pss = state.symbols.getPlusSignString();
            pssCp = Character.codePointAt(pss, 0);
            if (pss.length() != Character.charCount(pssCp) || !UNISET_MINUS.contains(pssCp)) {
               resolvedStr = pss;
            }
         }
      }

      if (resolvedStr != null) {
         if (firstTag >= 0L) {
            added |= acceptString(cp, Parse.StateName.INSIDE_AFFIX_PATTERN, returnTo, state, item, resolvedStr, 0, false);
         }

         if (nextTag < 0L) {
            added |= acceptString(cp, returnTo, (StateName)null, state, item, resolvedStr, 0, false);
         }
      }

      if (resolvedCurrency) {
         if (firstTag >= 0L) {
            added |= acceptCurrency(cp, Parse.StateName.INSIDE_AFFIX_PATTERN, returnTo, state, item);
         }

         if (nextTag < 0L) {
            added |= acceptCurrency(cp, returnTo, (StateName)null, state, item);
         }
      }

      for(int i = Long.numberOfTrailingZeros(added); 1L << i <= added; ++i) {
         if ((1L << i & added) != 0L) {
            state.getItem(i).currentAffixPattern = str;
            state.getItem(i).currentStepwiseParserTag = firstTag;
         }
      }

      return added;
   }

   private static long acceptAffixPatternHelper(int cp, StateName returnTo, ParserState state, StateItem item, CharSequence str, long newTag) {
      StateItem next = state.getNext().copyFrom(item, (StateName)null, cp);
      ++next.score;
      if (newTag >= 0L) {
         next.name = Parse.StateName.INSIDE_AFFIX_PATTERN;
         next.returnTo1 = returnTo;
         next.currentAffixPattern = str;
         next.currentStepwiseParserTag = newTag;
      } else {
         next.name = returnTo;
         next.trailingCount = 0;
         next.returnTo1 = null;
      }

      return 1L << state.lastInsertedIndex();
   }

   private static long acceptStringOrAffixPatternWithIgnorables(int cp, StateName ret1, StateName ret2, ParserState state, StateItem item, CharSequence str, long offsetOrTag, boolean trailing, boolean isString) {
      int typeOrCp = isString ? Character.codePointAt(str, (int)offsetOrTag) : AffixPatternUtils.getTypeOrCp(offsetOrTag);
      int nextTypeOrCp;
      long nextOffsetOrTag;
      long prevOffsetOrTag;
      if (isIgnorable(typeOrCp, state)) {
         nextTypeOrCp = typeOrCp;
         nextOffsetOrTag = offsetOrTag;
         long firstOffsetOrTag = 0L;

         label126: {
            while(true) {
               prevOffsetOrTag = nextOffsetOrTag;
               nextOffsetOrTag = isString ? nextOffsetOrTag + (long)Character.charCount(nextTypeOrCp) : AffixPatternUtils.nextToken(nextOffsetOrTag, str);
               if (firstOffsetOrTag == 0L) {
                  firstOffsetOrTag = nextOffsetOrTag;
               }

               if (isString) {
                  if (nextOffsetOrTag >= (long)str.length()) {
                     break;
                  }
               } else if (nextOffsetOrTag < 0L) {
                  break;
               }

               nextTypeOrCp = isString ? Character.codePointAt(str, (int)nextOffsetOrTag) : AffixPatternUtils.getTypeOrCp(nextOffsetOrTag);
               if (!isIgnorable(nextTypeOrCp, state)) {
                  break label126;
               }
            }

            nextTypeOrCp = Integer.MIN_VALUE;
         }

         if (nextTypeOrCp == Integer.MIN_VALUE) {
            if (codePointEquals(cp, typeOrCp, state)) {
               long added = 0L;
               added |= isString ? acceptStringHelper(cp, ret1, ret2, state, item, str, (int)firstOffsetOrTag, trailing) : acceptAffixPatternHelper(cp, ret1, state, item, str, firstOffsetOrTag);
               if (firstOffsetOrTag != nextOffsetOrTag) {
                  added |= isString ? acceptStringHelper(cp, ret1, ret2, state, item, str, (int)nextOffsetOrTag, trailing) : acceptAffixPatternHelper(cp, ret1, state, item, str, nextOffsetOrTag);
               }

               return added;
            }

            return 0L;
         }

         if (offsetOrTag != 0L && isIgnorable(cp, state)) {
            return isString ? acceptStringHelper(cp, ret1, ret2, state, item, str, (int)prevOffsetOrTag, trailing) : acceptAffixPatternHelper(cp, ret1, state, item, str, prevOffsetOrTag);
         }

         assert nextTypeOrCp != Integer.MIN_VALUE;

         typeOrCp = nextTypeOrCp;
         offsetOrTag = nextOffsetOrTag;
      }

      assert !isIgnorable(typeOrCp, state);

      nextTypeOrCp = typeOrCp;
      prevOffsetOrTag = offsetOrTag;
      nextOffsetOrTag = 0L;

      do {
         prevOffsetOrTag = isString ? prevOffsetOrTag + (long)Character.charCount(nextTypeOrCp) : AffixPatternUtils.nextToken(prevOffsetOrTag, str);
         if (nextOffsetOrTag == 0L) {
            nextOffsetOrTag = prevOffsetOrTag;
         }

         label110: {
            if (isString) {
               if (prevOffsetOrTag < (long)str.length()) {
                  break label110;
               }
            } else if (prevOffsetOrTag >= 0L) {
               break label110;
            }

            int nextTypeOrCp = true;
            break;
         }

         nextTypeOrCp = isString ? Character.codePointAt(str, (int)prevOffsetOrTag) : AffixPatternUtils.getTypeOrCp(prevOffsetOrTag);
      } while(isIgnorable(nextTypeOrCp, state));

      return isString ? acceptStringNonIgnorable(cp, ret1, ret2, state, item, str, trailing, typeOrCp, nextOffsetOrTag, prevOffsetOrTag) : acceptAffixPatternNonIgnorable(cp, ret1, state, item, str, typeOrCp, nextOffsetOrTag, prevOffsetOrTag);
   }

   private static void acceptCurrency(int cp, StateName nextName, ParserState state, StateItem item) {
      acceptCurrency(cp, nextName, (StateName)null, state, item);
   }

   private static long acceptCurrency(int cp, StateName returnTo1, StateName returnTo2, ParserState state, StateItem item) {
      if (item.sawCurrency) {
         return 0L;
      } else {
         long added = 0L;
         Currency currency = state.properties.getCurrency();
         String str1;
         String str2;
         if (currency != null) {
            str1 = currency.getName((ULocale)state.symbols.getULocale(), 0, (boolean[])null);
            str2 = currency.getCurrencyCode();
         } else {
            currency = state.symbols.getCurrency();
            str1 = state.symbols.getCurrencySymbol();
            str2 = state.symbols.getInternationalCurrencySymbol();
         }

         added |= acceptString(cp, returnTo1, returnTo2, state, item, str1, 0, false);
         added |= acceptString(cp, returnTo1, returnTo2, state, item, str2, 0, false);

         for(int i = Long.numberOfTrailingZeros(added); 1L << i <= added; ++i) {
            if ((1L << i & added) != 0L) {
               state.getItem(i).sawCurrency = true;
               state.getItem(i).isoCode = str2;
            }
         }

         if (state.parseCurrency) {
            ULocale uloc = state.symbols.getULocale();
            TextTrieMap.ParseState trie1 = Currency.openParseState(uloc, cp, 1);
            TextTrieMap.ParseState trie2 = Currency.openParseState(uloc, cp, 0);
            added |= acceptCurrencyHelper(cp, returnTo1, returnTo2, state, item, trie1);
            added |= acceptCurrencyHelper(cp, returnTo1, returnTo2, state, item, trie2);
         }

         return added;
      }
   }

   private static void acceptCurrencyOffset(int cp, ParserState state, StateItem item) {
      acceptCurrencyHelper(cp, item.returnTo1, item.returnTo2, state, item, item.currentCurrencyTrieState);
   }

   private static long acceptCurrencyHelper(int cp, StateName returnTo1, StateName returnTo2, ParserState state, StateItem item, TextTrieMap.ParseState trieState) {
      if (trieState == null) {
         return 0L;
      } else {
         trieState.accept(cp);
         long added = 0L;
         Iterator currentMatches = trieState.getCurrentMatches();
         StateItem next;
         if (currentMatches != null) {
            next = state.getNext().copyFrom(item, returnTo1, -1);
            next.returnTo1 = returnTo2;
            next.returnTo2 = null;
            next.sawCurrency = true;
            next.isoCode = ((Currency.CurrencyStringInfo)currentMatches.next()).getISOCode();
            added |= 1L << state.lastInsertedIndex();
         }

         if (!trieState.atEnd()) {
            next = state.getNext().copyFrom(item, Parse.StateName.INSIDE_CURRENCY, -1);
            next.returnTo1 = returnTo1;
            next.returnTo2 = returnTo2;
            next.currentCurrencyTrieState = trieState;
            added |= 1L << state.lastInsertedIndex();
         }

         return added;
      }
   }

   private static long acceptDigitTrie(int cp, StateName nextName, ParserState state, StateItem item, DigitType type) {
      assert state.digitTrie != null;

      TextTrieMap.ParseState trieState = state.digitTrie.openParseState(cp);
      return trieState == null ? 0L : acceptDigitTrieHelper(cp, nextName, state, item, type, trieState);
   }

   private static void acceptDigitTrieOffset(int cp, ParserState state, StateItem item) {
      acceptDigitTrieHelper(cp, item.returnTo1, state, item, item.currentDigitType, item.currentDigitTrieState);
   }

   private static long acceptDigitTrieHelper(int cp, StateName returnTo1, ParserState state, StateItem item, DigitType type, TextTrieMap.ParseState trieState) {
      if (trieState == null) {
         return 0L;
      } else {
         trieState.accept(cp);
         long added = 0L;
         Iterator currentMatches = trieState.getCurrentMatches();
         if (currentMatches != null) {
            byte digit = (Byte)currentMatches.next();
            StateItem next = state.getNext().copyFrom(item, returnTo1, -1);
            next.returnTo1 = null;
            recordDigit(next, digit, type);
            added |= 1L << state.lastInsertedIndex();
         }

         if (!trieState.atEnd()) {
            StateItem next = state.getNext().copyFrom(item, Parse.StateName.INSIDE_DIGIT, -1);
            next.returnTo1 = returnTo1;
            next.currentDigitTrieState = trieState;
            next.currentDigitType = type;
            added |= 1L << state.lastInsertedIndex();
         }

         return added;
      }
   }

   private static boolean codePointEquals(int cp1, int cp2, ParserState state) {
      if (!state.caseSensitive) {
         cp1 = UCharacter.foldCase(cp1, true);
         cp2 = UCharacter.foldCase(cp2, true);
      }

      return cp1 == cp2;
   }

   private static boolean isIgnorable(int cp, ParserState state) {
      if (cp < 0) {
         return false;
      } else if (UNISET_BIDI.contains(cp)) {
         return true;
      } else {
         return state.mode == Parse.ParseMode.LENIENT && UNISET_WHITESPACE.contains(cp);
      }
   }

   private static class CurrencyAffixPatterns {
      private final Set set = new HashSet();
      private static final ConcurrentHashMap currencyAffixPatterns = new ConcurrentHashMap();
      private static final ThreadLocal threadLocalProperties = new ThreadLocal() {
         protected Properties initialValue() {
            return new Properties();
         }
      };

      static void addToState(ULocale uloc, ParserState state) {
         CurrencyAffixPatterns value = (CurrencyAffixPatterns)currencyAffixPatterns.get(uloc);
         if (value == null) {
            CurrencyAffixPatterns newValue = new CurrencyAffixPatterns(uloc);
            currencyAffixPatterns.putIfAbsent(uloc, newValue);
            value = (CurrencyAffixPatterns)currencyAffixPatterns.get(uloc);
         }

         state.affixHolders.addAll(value.set);
      }

      private CurrencyAffixPatterns(ULocale uloc) {
         String pattern = NumberFormat.getPatternForStyle(uloc, 1);
         this.addPattern(pattern);
         CurrencyPluralInfo pluralInfo = CurrencyPluralInfo.getInstance(uloc);
         Iterator var4 = StandardPlural.VALUES.iterator();

         while(var4.hasNext()) {
            StandardPlural plural = (StandardPlural)var4.next();
            pattern = pluralInfo.getCurrencyPluralPattern(plural.getKeyword());
            this.addPattern(pattern);
         }

      }

      private void addPattern(String pattern) {
         Properties properties = (Properties)threadLocalProperties.get();

         try {
            PatternString.parseToExistingProperties(pattern, properties);
         } catch (IllegalArgumentException var4) {
         }

         this.set.add(Parse.AffixHolder.fromPropertiesPositivePattern(properties));
         this.set.add(Parse.AffixHolder.fromPropertiesNegativePattern(properties));
      }
   }

   private static class AffixHolder {
      final String p;
      final String s;
      final boolean strings;
      final boolean negative;
      static final AffixHolder EMPTY_POSITIVE = new AffixHolder("", "", true, false);
      static final AffixHolder EMPTY_NEGATIVE = new AffixHolder("", "", true, true);

      static void addToState(ParserState state, IProperties properties) {
         AffixHolder pp = fromPropertiesPositivePattern(properties);
         AffixHolder np = fromPropertiesNegativePattern(properties);
         AffixHolder ps = fromPropertiesPositiveString(properties);
         AffixHolder ns = fromPropertiesNegativeString(properties);
         if (pp != null) {
            state.affixHolders.add(pp);
         }

         if (ps != null) {
            state.affixHolders.add(ps);
         }

         if (np != null) {
            state.affixHolders.add(np);
         }

         if (ns != null) {
            state.affixHolders.add(ns);
         }

      }

      static AffixHolder fromPropertiesPositivePattern(IProperties properties) {
         String ppp = properties.getPositivePrefixPattern();
         String psp = properties.getPositiveSuffixPattern();
         if (properties.getSignAlwaysShown()) {
            boolean foundSign = false;
            String npp = properties.getNegativePrefixPattern();
            String nsp = properties.getNegativeSuffixPattern();
            if (AffixPatternUtils.containsType(npp, -1)) {
               foundSign = true;
               ppp = AffixPatternUtils.replaceType(npp, -1, '+');
            }

            if (AffixPatternUtils.containsType(nsp, -1)) {
               foundSign = true;
               psp = AffixPatternUtils.replaceType(nsp, -1, '+');
            }

            if (!foundSign) {
               ppp = "+" + ppp;
            }
         }

         return getInstance(ppp, psp, false, false);
      }

      static AffixHolder fromPropertiesNegativePattern(IProperties properties) {
         String npp = properties.getNegativePrefixPattern();
         String nsp = properties.getNegativeSuffixPattern();
         if (npp == null && nsp == null) {
            npp = properties.getPositivePrefixPattern();
            nsp = properties.getPositiveSuffixPattern();
            if (npp == null) {
               npp = "-";
            } else {
               npp = "-" + npp;
            }
         }

         return getInstance(npp, nsp, false, true);
      }

      static AffixHolder fromPropertiesPositiveString(IProperties properties) {
         String pp = properties.getPositivePrefix();
         String ps = properties.getPositiveSuffix();
         return pp == null && ps == null ? null : getInstance(pp, ps, true, false);
      }

      static AffixHolder fromPropertiesNegativeString(IProperties properties) {
         String np = properties.getNegativePrefix();
         String ns = properties.getNegativeSuffix();
         return np == null && ns == null ? null : getInstance(np, ns, true, true);
      }

      static AffixHolder getInstance(String p, String s, boolean strings, boolean negative) {
         if (p == null && s == null) {
            return negative ? EMPTY_NEGATIVE : EMPTY_POSITIVE;
         } else {
            if (p == null) {
               p = "";
            }

            if (s == null) {
               s = "";
            }

            if (p.length() == 0 && s.length() == 0) {
               return negative ? EMPTY_NEGATIVE : EMPTY_POSITIVE;
            } else {
               return new AffixHolder(p, s, strings, negative);
            }
         }
      }

      AffixHolder(String pp, String sp, boolean strings, boolean negative) {
         this.p = pp;
         this.s = sp;
         this.strings = strings;
         this.negative = negative;
      }

      public boolean equals(Object other) {
         if (other == null) {
            return false;
         } else if (this == other) {
            return true;
         } else if (!(other instanceof AffixHolder)) {
            return false;
         } else {
            AffixHolder _other = (AffixHolder)other;
            if (!this.p.equals(_other.p)) {
               return false;
            } else if (!this.s.equals(_other.s)) {
               return false;
            } else if (this.strings != _other.strings) {
               return false;
            } else {
               return this.negative == _other.negative;
            }
         }
      }

      public int hashCode() {
         return this.p.hashCode() ^ this.s.hashCode();
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("{");
         sb.append(this.p);
         sb.append("|");
         sb.append(this.s);
         sb.append("|");
         sb.append((char)(this.strings ? 'S' : 'P'));
         sb.append("}");
         return sb.toString();
      }
   }

   private static class ParserState {
      StateItem[] items = new StateItem[16];
      StateItem[] prevItems = new StateItem[16];
      int length;
      int prevLength;
      IProperties properties;
      DecimalFormatSymbols symbols;
      ParseMode mode;
      boolean caseSensitive;
      boolean parseCurrency;
      GroupingMode groupingMode;
      int decimalCp1;
      int decimalCp2;
      int groupingCp1;
      int groupingCp2;
      SeparatorType decimalType1;
      SeparatorType decimalType2;
      SeparatorType groupingType1;
      SeparatorType groupingType2;
      TextTrieMap digitTrie;
      Set affixHolders = new HashSet();

      ParserState() {
         for(int i = 0; i < this.items.length; ++i) {
            this.items[i] = new StateItem((char)(65 + i));
            this.prevItems[i] = new StateItem((char)(65 + i));
         }

      }

      ParserState clear() {
         this.length = 0;
         this.prevLength = 0;
         this.digitTrie = null;
         this.affixHolders.clear();
         return this;
      }

      void swap() {
         StateItem[] temp = this.prevItems;
         this.prevItems = this.items;
         this.items = temp;
         this.prevLength = this.length;
         this.length = 0;
      }

      void swapBack() {
         StateItem[] temp = this.prevItems;
         this.prevItems = this.items;
         this.items = temp;
         this.length = this.prevLength;
         this.prevLength = 0;
      }

      StateItem getNext() {
         if (this.length >= this.items.length) {
            this.length = this.items.length - 1;
         }

         StateItem item = this.items[this.length];
         ++this.length;
         return item;
      }

      public int lastInsertedIndex() {
         assert this.length > 0;

         return this.length - 1;
      }

      public StateItem getItem(int i) {
         assert i >= 0 && i < this.length;

         return this.items[i];
      }
   }

   private static class StateItem {
      StateName name;
      int trailingCount;
      int score;
      FormatQuantity4 fq = new FormatQuantity4();
      int numDigits;
      int trailingZeros;
      int exponent;
      int groupingCp;
      long groupingWidths;
      String isoCode;
      boolean sawNegative;
      boolean sawNegativeExponent;
      boolean sawCurrency;
      boolean sawNaN;
      boolean sawInfinity;
      AffixHolder affix;
      boolean sawPrefix;
      boolean sawSuffix;
      boolean sawDecimalPoint;
      boolean sawExponentDigit;
      StateName returnTo1;
      StateName returnTo2;
      CharSequence currentString;
      int currentOffset;
      boolean currentTrailing;
      CharSequence currentAffixPattern;
      long currentStepwiseParserTag;
      TextTrieMap.ParseState currentCurrencyTrieState;
      TextTrieMap.ParseState currentDigitTrieState;
      DigitType currentDigitType;
      final char id;
      String path;

      StateItem(char _id) {
         this.id = _id;
      }

      StateItem clear() {
         this.name = Parse.StateName.BEFORE_PREFIX;
         this.trailingCount = 0;
         this.score = 0;
         this.fq.clear();
         this.numDigits = 0;
         this.trailingZeros = 0;
         this.exponent = 0;
         this.groupingCp = -1;
         this.groupingWidths = 0L;
         this.isoCode = null;
         this.sawNegative = false;
         this.sawNegativeExponent = false;
         this.sawCurrency = false;
         this.sawNaN = false;
         this.sawInfinity = false;
         this.affix = null;
         this.sawPrefix = false;
         this.sawSuffix = false;
         this.sawDecimalPoint = false;
         this.sawExponentDigit = false;
         this.returnTo1 = null;
         this.returnTo2 = null;
         this.currentString = null;
         this.currentOffset = 0;
         this.currentTrailing = false;
         this.currentAffixPattern = null;
         this.currentStepwiseParserTag = 0L;
         this.currentCurrencyTrieState = null;
         this.currentDigitTrieState = null;
         this.currentDigitType = null;
         this.path = "";
         return this;
      }

      StateItem copyFrom(StateItem other, StateName newName, int trailing) {
         this.name = newName;
         this.score = other.score;
         this.trailingCount = trailing < 0 ? 0 : other.trailingCount + Character.charCount(trailing);
         this.fq.copyFrom(other.fq);
         this.numDigits = other.numDigits;
         this.trailingZeros = other.trailingZeros;
         this.exponent = other.exponent;
         this.groupingCp = other.groupingCp;
         this.groupingWidths = other.groupingWidths;
         this.isoCode = other.isoCode;
         this.sawNegative = other.sawNegative;
         this.sawNegativeExponent = other.sawNegativeExponent;
         this.sawCurrency = other.sawCurrency;
         this.sawNaN = other.sawNaN;
         this.sawInfinity = other.sawInfinity;
         this.affix = other.affix;
         this.sawPrefix = other.sawPrefix;
         this.sawSuffix = other.sawSuffix;
         this.sawDecimalPoint = other.sawDecimalPoint;
         this.sawExponentDigit = other.sawExponentDigit;
         this.returnTo1 = other.returnTo1;
         this.returnTo2 = other.returnTo2;
         this.currentString = other.currentString;
         this.currentOffset = other.currentOffset;
         this.currentTrailing = other.currentTrailing;
         this.currentAffixPattern = other.currentAffixPattern;
         this.currentStepwiseParserTag = other.currentStepwiseParserTag;
         this.currentCurrencyTrieState = other.currentCurrencyTrieState;
         this.currentDigitTrieState = other.currentDigitTrieState;
         this.currentDigitType = other.currentDigitType;
         if (Parse.DEBUGGING) {
            this.path = other.path + other.id;
         }

         return this;
      }

      void appendDigit(byte digit, DigitType type) {
         if (type == Parse.DigitType.EXPONENT) {
            this.sawExponentDigit = true;
            int newExponent = this.exponent * 10 + digit;
            if (newExponent < this.exponent) {
               this.exponent = Integer.MAX_VALUE;
            } else {
               this.exponent = newExponent;
            }
         } else {
            ++this.numDigits;
            if (type == Parse.DigitType.FRACTION && digit == 0) {
               ++this.trailingZeros;
            } else if (type == Parse.DigitType.FRACTION) {
               this.fq.appendDigit(digit, this.trailingZeros, false);
               this.trailingZeros = 0;
            } else {
               this.fq.appendDigit(digit, 0, true);
            }
         }

      }

      public boolean hasNumber() {
         return this.numDigits > 0 || this.sawNaN || this.sawInfinity;
      }

      Number toNumber(IProperties properties) {
         if (this.sawNaN) {
            return Double.NaN;
         } else if (this.sawInfinity) {
            return this.sawNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
         } else if (this.fq.isZero() && this.sawNegative) {
            return -0.0;
         } else {
            boolean forceBigDecimal = properties.getParseToBigDecimal();
            if (this.exponent == Integer.MAX_VALUE) {
               if (this.sawNegativeExponent && this.sawNegative) {
                  return -0.0;
               } else if (this.sawNegativeExponent) {
                  return 0.0;
               } else {
                  return this.sawNegative ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
               }
            } else {
               if (this.exponent > 1000) {
                  forceBigDecimal = true;
               }

               BigDecimal multiplier = properties.getMultiplier();
               if (properties.getMagnitudeMultiplier() != 0) {
                  if (multiplier == null) {
                     multiplier = BigDecimal.ONE;
                  }

                  multiplier = multiplier.scaleByPowerOfTen(properties.getMagnitudeMultiplier());
               }

               int delta = (this.sawNegativeExponent ? -1 : 1) * this.exponent;
               MathContext mc = RoundingUtils.getMathContextOr16Digits(properties);
               BigDecimal result = this.fq.toBigDecimal();
               if (this.sawNegative) {
                  result = result.negate();
               }

               result = result.scaleByPowerOfTen(delta);
               if (multiplier != null) {
                  result = result.divide(multiplier, mc);
               }

               result = result.stripTrailingZeros();
               if (!forceBigDecimal && result.scale() <= 0) {
                  return (Number)(-result.scale() + result.precision() <= 18 ? result.longValueExact() : result.toBigIntegerExact());
               } else {
                  return result;
               }
            }
         }
      }

      public CurrencyAmount toCurrencyAmount(IProperties properties) {
         assert this.isoCode != null;

         Number number = this.toNumber(properties);
         Currency currency = Currency.getInstance(this.isoCode);
         return new CurrencyAmount(number, currency);
      }

      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("[");
         sb.append(this.path);
         sb.append("] ");
         sb.append(this.name.name());
         if (this.name == Parse.StateName.INSIDE_STRING) {
            sb.append("{");
            sb.append(this.currentString);
            sb.append(":");
            sb.append(this.currentOffset);
            sb.append("}");
         }

         if (this.name == Parse.StateName.INSIDE_AFFIX_PATTERN) {
            sb.append("{");
            sb.append(this.currentAffixPattern);
            sb.append(":");
            sb.append(AffixPatternUtils.getOffset(this.currentStepwiseParserTag) - 1);
            sb.append("}");
         }

         sb.append(" ");
         sb.append(this.fq.toBigDecimal());
         sb.append(" grouping:");
         sb.append(this.groupingCp == -1 ? new char[]{'?'} : Character.toChars(this.groupingCp));
         sb.append(" widths:");
         sb.append(Long.toHexString(this.groupingWidths));
         sb.append(" seen:");
         sb.append(this.sawNegative ? 1 : 0);
         sb.append(this.sawNegativeExponent ? 1 : 0);
         sb.append(this.sawNaN ? 1 : 0);
         sb.append(this.sawInfinity ? 1 : 0);
         sb.append(this.sawPrefix ? 1 : 0);
         sb.append(this.sawSuffix ? 1 : 0);
         sb.append(this.sawDecimalPoint ? 1 : 0);
         sb.append(" trailing:");
         sb.append(this.trailingCount);
         sb.append(" score:");
         sb.append(this.score);
         sb.append(" affix:");
         sb.append(this.affix);
         sb.append(" currency:");
         sb.append(this.isoCode);
         return sb.toString();
      }
   }

   private static enum DigitType {
      INTEGER,
      FRACTION,
      EXPONENT;
   }

   private static enum SeparatorType {
      COMMA_LIKE,
      PERIOD_LIKE,
      OTHER_GROUPING,
      UNKNOWN;

      static SeparatorType fromCp(int cp, ParseMode mode) {
         if (mode == Parse.ParseMode.FAST) {
            return UNKNOWN;
         } else if (mode == Parse.ParseMode.STRICT) {
            if (Parse.UNISET_STRICT_COMMA_LIKE.contains(cp)) {
               return COMMA_LIKE;
            } else if (Parse.UNISET_STRICT_PERIOD_LIKE.contains(cp)) {
               return PERIOD_LIKE;
            } else {
               return Parse.UNISET_OTHER_GROUPING_SEPARATORS.contains(cp) ? OTHER_GROUPING : UNKNOWN;
            }
         } else if (Parse.UNISET_COMMA_LIKE.contains(cp)) {
            return COMMA_LIKE;
         } else if (Parse.UNISET_PERIOD_LIKE.contains(cp)) {
            return PERIOD_LIKE;
         } else {
            return Parse.UNISET_OTHER_GROUPING_SEPARATORS.contains(cp) ? OTHER_GROUPING : UNKNOWN;
         }
      }
   }

   private static enum StateName {
      BEFORE_PREFIX,
      AFTER_PREFIX,
      AFTER_INTEGER_DIGIT,
      AFTER_FRACTION_DIGIT,
      AFTER_EXPONENT_SEPARATOR,
      AFTER_EXPONENT_DIGIT,
      BEFORE_SUFFIX,
      BEFORE_SUFFIX_SEEN_EXPONENT,
      AFTER_SUFFIX,
      INSIDE_CURRENCY,
      INSIDE_DIGIT,
      INSIDE_STRING,
      INSIDE_AFFIX_PATTERN;
   }

   public static enum GroupingMode {
      DEFAULT,
      RESTRICTED;
   }

   public interface IProperties extends PositiveNegativeAffixFormat.IProperties, PaddingFormat.IProperties, CurrencyFormat.ICurrencyProperties, BigDecimalMultiplier.IProperties, MagnitudeMultiplier.IProperties, PositiveDecimalFormat.IProperties, ScientificFormat.IProperties {
      boolean DEFAULT_PARSE_INTEGER_ONLY = false;
      boolean DEFAULT_PARSE_NO_EXPONENT = false;
      boolean DEFAULT_DECIMAL_PATTERN_MATCH_REQUIRED = false;
      ParseMode DEFAULT_PARSE_MODE = null;
      boolean DEFAULT_PARSE_TO_BIG_DECIMAL = false;
      boolean DEFAULT_PARSE_CASE_SENSITIVE = false;
      GroupingMode DEFAULT_PARSE_GROUPING_MODE = null;

      boolean getParseIntegerOnly();

      IProperties setParseIntegerOnly(boolean var1);

      boolean getParseNoExponent();

      IProperties setParseNoExponent(boolean var1);

      boolean getDecimalPatternMatchRequired();

      IProperties setDecimalPatternMatchRequired(boolean var1);

      ParseMode getParseMode();

      IProperties setParseMode(ParseMode var1);

      boolean getParseToBigDecimal();

      IProperties setParseToBigDecimal(boolean var1);

      boolean getParseCaseSensitive();

      IProperties setParseCaseSensitive(boolean var1);

      GroupingMode getParseGroupingMode();

      IProperties setParseGroupingMode(GroupingMode var1);
   }

   public static enum ParseMode {
      LENIENT,
      STRICT,
      FAST;
   }
}
