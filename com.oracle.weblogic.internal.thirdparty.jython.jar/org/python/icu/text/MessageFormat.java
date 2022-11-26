package org.python.icu.text;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.text.ChoiceFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.python.icu.impl.PatternProps;
import org.python.icu.impl.Utility;
import org.python.icu.util.ICUUncheckedIOException;
import org.python.icu.util.ULocale;

public class MessageFormat extends UFormat {
   static final long serialVersionUID = 7136212545847378652L;
   private transient ULocale ulocale;
   private transient MessagePattern msgPattern;
   private transient Map cachedFormatters;
   private transient Set customFormatArgStarts;
   private transient DateFormat stockDateFormatter;
   private transient NumberFormat stockNumberFormatter;
   private transient PluralSelectorProvider pluralProvider;
   private transient PluralSelectorProvider ordinalProvider;
   private static final String[] typeList = new String[]{"number", "date", "time", "spellout", "ordinal", "duration"};
   private static final int TYPE_NUMBER = 0;
   private static final int TYPE_DATE = 1;
   private static final int TYPE_TIME = 2;
   private static final int TYPE_SPELLOUT = 3;
   private static final int TYPE_ORDINAL = 4;
   private static final int TYPE_DURATION = 5;
   private static final String[] modifierList = new String[]{"", "currency", "percent", "integer"};
   private static final int MODIFIER_EMPTY = 0;
   private static final int MODIFIER_CURRENCY = 1;
   private static final int MODIFIER_PERCENT = 2;
   private static final int MODIFIER_INTEGER = 3;
   private static final String[] dateModifierList = new String[]{"", "short", "medium", "long", "full"};
   private static final int DATE_MODIFIER_EMPTY = 0;
   private static final int DATE_MODIFIER_SHORT = 1;
   private static final int DATE_MODIFIER_MEDIUM = 2;
   private static final int DATE_MODIFIER_LONG = 3;
   private static final int DATE_MODIFIER_FULL = 4;
   private static final Locale rootLocale = new Locale("");
   private static final char SINGLE_QUOTE = '\'';
   private static final char CURLY_BRACE_LEFT = '{';
   private static final char CURLY_BRACE_RIGHT = '}';
   private static final int STATE_INITIAL = 0;
   private static final int STATE_SINGLE_QUOTE = 1;
   private static final int STATE_IN_QUOTE = 2;
   private static final int STATE_MSG_ELEMENT = 3;

   public MessageFormat(String pattern) {
      this.ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
      this.applyPattern(pattern);
   }

   public MessageFormat(String pattern, Locale locale) {
      this(pattern, ULocale.forLocale(locale));
   }

   public MessageFormat(String pattern, ULocale locale) {
      this.ulocale = locale;
      this.applyPattern(pattern);
   }

   public void setLocale(Locale locale) {
      this.setLocale(ULocale.forLocale(locale));
   }

   public void setLocale(ULocale locale) {
      String existingPattern = this.toPattern();
      this.ulocale = locale;
      this.stockDateFormatter = null;
      this.stockNumberFormatter = null;
      this.pluralProvider = null;
      this.ordinalProvider = null;
      this.applyPattern(existingPattern);
   }

   public Locale getLocale() {
      return this.ulocale.toLocale();
   }

   public ULocale getULocale() {
      return this.ulocale;
   }

   public void applyPattern(String pttrn) {
      try {
         if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern(pttrn);
         } else {
            this.msgPattern.parse(pttrn);
         }

         this.cacheExplicitFormats();
      } catch (RuntimeException var3) {
         this.resetPattern();
         throw var3;
      }
   }

   public void applyPattern(String pattern, MessagePattern.ApostropheMode aposMode) {
      if (this.msgPattern == null) {
         this.msgPattern = new MessagePattern(aposMode);
      } else if (aposMode != this.msgPattern.getApostropheMode()) {
         this.msgPattern.clearPatternAndSetApostropheMode(aposMode);
      }

      this.applyPattern(pattern);
   }

   public MessagePattern.ApostropheMode getApostropheMode() {
      if (this.msgPattern == null) {
         this.msgPattern = new MessagePattern();
      }

      return this.msgPattern.getApostropheMode();
   }

   public String toPattern() {
      if (this.customFormatArgStarts != null) {
         throw new IllegalStateException("toPattern() is not supported after custom Format objects have been set via setFormat() or similar APIs");
      } else if (this.msgPattern == null) {
         return "";
      } else {
         String originalPattern = this.msgPattern.getPatternString();
         return originalPattern == null ? "" : originalPattern;
      }
   }

   private int nextTopLevelArgStart(int partIndex) {
      if (partIndex != 0) {
         partIndex = this.msgPattern.getLimitPartIndex(partIndex);
      }

      MessagePattern.Part.Type type;
      do {
         ++partIndex;
         type = this.msgPattern.getPartType(partIndex);
         if (type == MessagePattern.Part.Type.ARG_START) {
            return partIndex;
         }
      } while(type != MessagePattern.Part.Type.MSG_LIMIT);

      return -1;
   }

   private boolean argNameMatches(int partIndex, String argName, int argNumber) {
      MessagePattern.Part part = this.msgPattern.getPart(partIndex);
      return part.getType() == MessagePattern.Part.Type.ARG_NAME ? this.msgPattern.partSubstringMatches(part, argName) : part.getValue() == argNumber;
   }

   private String getArgName(int partIndex) {
      MessagePattern.Part part = this.msgPattern.getPart(partIndex);
      return part.getType() == MessagePattern.Part.Type.ARG_NAME ? this.msgPattern.getSubstring(part) : Integer.toString(part.getValue());
   }

   public void setFormatsByArgumentIndex(Format[] newFormats) {
      if (this.msgPattern.hasNamedArguments()) {
         throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
      } else {
         int partIndex = 0;

         while((partIndex = this.nextTopLevelArgStart(partIndex)) >= 0) {
            int argNumber = this.msgPattern.getPart(partIndex + 1).getValue();
            if (argNumber < newFormats.length) {
               this.setCustomArgStartFormat(partIndex, newFormats[argNumber]);
            }
         }

      }
   }

   public void setFormatsByArgumentName(Map newFormats) {
      int partIndex = 0;

      while((partIndex = this.nextTopLevelArgStart(partIndex)) >= 0) {
         String key = this.getArgName(partIndex + 1);
         if (newFormats.containsKey(key)) {
            this.setCustomArgStartFormat(partIndex, (Format)newFormats.get(key));
         }
      }

   }

   public void setFormats(Format[] newFormats) {
      int formatNumber = 0;

      for(int partIndex = 0; formatNumber < newFormats.length && (partIndex = this.nextTopLevelArgStart(partIndex)) >= 0; ++formatNumber) {
         this.setCustomArgStartFormat(partIndex, newFormats[formatNumber]);
      }

   }

   public void setFormatByArgumentIndex(int argumentIndex, Format newFormat) {
      if (this.msgPattern.hasNamedArguments()) {
         throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
      } else {
         int partIndex = 0;

         while((partIndex = this.nextTopLevelArgStart(partIndex)) >= 0) {
            if (this.msgPattern.getPart(partIndex + 1).getValue() == argumentIndex) {
               this.setCustomArgStartFormat(partIndex, newFormat);
            }
         }

      }
   }

   public void setFormatByArgumentName(String argumentName, Format newFormat) {
      int argNumber = MessagePattern.validateArgumentName(argumentName);
      if (argNumber >= -1) {
         int partIndex = 0;

         while((partIndex = this.nextTopLevelArgStart(partIndex)) >= 0) {
            if (this.argNameMatches(partIndex + 1, argumentName, argNumber)) {
               this.setCustomArgStartFormat(partIndex, newFormat);
            }
         }

      }
   }

   public void setFormat(int formatElementIndex, Format newFormat) {
      int formatNumber = 0;

      for(int partIndex = 0; (partIndex = this.nextTopLevelArgStart(partIndex)) >= 0; ++formatNumber) {
         if (formatNumber == formatElementIndex) {
            this.setCustomArgStartFormat(partIndex, newFormat);
            return;
         }
      }

      throw new ArrayIndexOutOfBoundsException(formatElementIndex);
   }

   public Format[] getFormatsByArgumentIndex() {
      if (this.msgPattern.hasNamedArguments()) {
         throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
      } else {
         ArrayList list = new ArrayList();
         int partIndex = 0;

         while((partIndex = this.nextTopLevelArgStart(partIndex)) >= 0) {
            int argNumber = this.msgPattern.getPart(partIndex + 1).getValue();

            while(argNumber >= list.size()) {
               list.add((Object)null);
            }

            list.set(argNumber, this.cachedFormatters == null ? null : (Format)this.cachedFormatters.get(partIndex));
         }

         return (Format[])list.toArray(new Format[list.size()]);
      }
   }

   public Format[] getFormats() {
      ArrayList list = new ArrayList();
      int partIndex = 0;

      while((partIndex = this.nextTopLevelArgStart(partIndex)) >= 0) {
         list.add(this.cachedFormatters == null ? null : (Format)this.cachedFormatters.get(partIndex));
      }

      return (Format[])list.toArray(new Format[list.size()]);
   }

   public Set getArgumentNames() {
      Set result = new HashSet();
      int partIndex = 0;

      while((partIndex = this.nextTopLevelArgStart(partIndex)) >= 0) {
         result.add(this.getArgName(partIndex + 1));
      }

      return result;
   }

   public Format getFormatByArgumentName(String argumentName) {
      if (this.cachedFormatters == null) {
         return null;
      } else {
         int argNumber = MessagePattern.validateArgumentName(argumentName);
         if (argNumber < -1) {
            return null;
         } else {
            int partIndex = 0;

            do {
               if ((partIndex = this.nextTopLevelArgStart(partIndex)) < 0) {
                  return null;
               }
            } while(!this.argNameMatches(partIndex + 1, argumentName, argNumber));

            return (Format)this.cachedFormatters.get(partIndex);
         }
      }
   }

   public final StringBuffer format(Object[] arguments, StringBuffer result, FieldPosition pos) {
      this.format(arguments, (Map)null, new AppendableWrapper(result), pos);
      return result;
   }

   public final StringBuffer format(Map arguments, StringBuffer result, FieldPosition pos) {
      this.format((Object[])null, arguments, new AppendableWrapper(result), pos);
      return result;
   }

   public static String format(String pattern, Object... arguments) {
      MessageFormat temp = new MessageFormat(pattern);
      return temp.format(arguments);
   }

   public static String format(String pattern, Map arguments) {
      MessageFormat temp = new MessageFormat(pattern);
      return temp.format(arguments);
   }

   public boolean usesNamedArguments() {
      return this.msgPattern.hasNamedArguments();
   }

   public final StringBuffer format(Object arguments, StringBuffer result, FieldPosition pos) {
      this.format(arguments, new AppendableWrapper(result), pos);
      return result;
   }

   public AttributedCharacterIterator formatToCharacterIterator(Object arguments) {
      if (arguments == null) {
         throw new NullPointerException("formatToCharacterIterator must be passed non-null object");
      } else {
         StringBuilder result = new StringBuilder();
         AppendableWrapper wrapper = new AppendableWrapper(result);
         wrapper.useAttributes();
         this.format((Object)arguments, (AppendableWrapper)wrapper, (FieldPosition)null);
         AttributedString as = new AttributedString(result.toString());
         Iterator var5 = wrapper.attributes.iterator();

         while(var5.hasNext()) {
            AttributeAndPosition a = (AttributeAndPosition)var5.next();
            as.addAttribute(a.key, a.value, a.start, a.limit);
         }

         return as.getIterator();
      }
   }

   public Object[] parse(String source, ParsePosition pos) {
      if (this.msgPattern.hasNamedArguments()) {
         throw new IllegalArgumentException("This method is not available in MessageFormat objects that use named argument.");
      } else {
         int maxArgId = -1;
         int partIndex = 0;

         int argNumber;
         while((partIndex = this.nextTopLevelArgStart(partIndex)) >= 0) {
            argNumber = this.msgPattern.getPart(partIndex + 1).getValue();
            if (argNumber > maxArgId) {
               maxArgId = argNumber;
            }
         }

         Object[] resultArray = new Object[maxArgId + 1];
         argNumber = pos.getIndex();
         this.parse(0, source, pos, resultArray, (Map)null);
         if (pos.getIndex() == argNumber) {
            return null;
         } else {
            return resultArray;
         }
      }
   }

   public Map parseToMap(String source, ParsePosition pos) {
      Map result = new HashMap();
      int backupStartPos = pos.getIndex();
      this.parse(0, source, pos, (Object[])null, result);
      return pos.getIndex() == backupStartPos ? null : result;
   }

   public Object[] parse(String source) throws ParseException {
      ParsePosition pos = new ParsePosition(0);
      Object[] result = this.parse(source, pos);
      if (pos.getIndex() == 0) {
         throw new ParseException("MessageFormat parse error!", pos.getErrorIndex());
      } else {
         return result;
      }
   }

   private void parse(int msgStart, String source, ParsePosition pos, Object[] args, Map argsMap) {
      if (source != null) {
         String msgString = this.msgPattern.getPatternString();
         int prevIndex = this.msgPattern.getPart(msgStart).getLimit();
         int sourceOffset = pos.getIndex();
         ParsePosition tempStatus = new ParsePosition(0);
         int i = msgStart + 1;

         while(true) {
            MessagePattern.Part part = this.msgPattern.getPart(i);
            MessagePattern.Part.Type type = part.getType();
            int index = part.getIndex();
            int len = index - prevIndex;
            if (len != 0 && !msgString.regionMatches(prevIndex, source, sourceOffset, len)) {
               pos.setErrorIndex(sourceOffset);
               return;
            }

            sourceOffset += len;
            int var10000 = prevIndex + len;
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
               pos.setIndex(sourceOffset);
               return;
            }

            if (type != MessagePattern.Part.Type.SKIP_SYNTAX && type != MessagePattern.Part.Type.INSERT_CHAR) {
               assert type == MessagePattern.Part.Type.ARG_START : "Unexpected Part " + part + " in parsed message.";

               int argLimit = this.msgPattern.getLimitPartIndex(i);
               MessagePattern.ArgType argType = part.getArgType();
               ++i;
               part = this.msgPattern.getPart(i);
               Object argId = null;
               int argNumber = 0;
               String key = null;
               if (args != null) {
                  argNumber = part.getValue();
                  argId = argNumber;
               } else {
                  if (part.getType() == MessagePattern.Part.Type.ARG_NAME) {
                     key = this.msgPattern.getSubstring(part);
                  } else {
                     key = Integer.toString(part.getValue());
                  }

                  argId = key;
               }

               ++i;
               Format formatter = null;
               boolean haveArgResult = false;
               Object argResult = null;
               if (this.cachedFormatters != null && (formatter = (Format)this.cachedFormatters.get(i - 2)) != null) {
                  tempStatus.setIndex(sourceOffset);
                  argResult = formatter.parseObject(source, tempStatus);
                  if (tempStatus.getIndex() == sourceOffset) {
                     pos.setErrorIndex(sourceOffset);
                     return;
                  }

                  haveArgResult = true;
                  sourceOffset = tempStatus.getIndex();
               } else if (argType == MessagePattern.ArgType.NONE || this.cachedFormatters != null && this.cachedFormatters.containsKey(i - 2)) {
                  String stringAfterArgument = this.getLiteralStringUntilNextArgument(argLimit);
                  int next;
                  if (stringAfterArgument.length() != 0) {
                     next = source.indexOf(stringAfterArgument, sourceOffset);
                  } else {
                     next = source.length();
                  }

                  if (next < 0) {
                     pos.setErrorIndex(sourceOffset);
                     return;
                  }

                  String strValue = source.substring(sourceOffset, next);
                  if (!strValue.equals("{" + argId.toString() + "}")) {
                     haveArgResult = true;
                     argResult = strValue;
                  }

                  sourceOffset = next;
               } else {
                  if (argType != MessagePattern.ArgType.CHOICE) {
                     if (!argType.hasPluralStyle() && argType != MessagePattern.ArgType.SELECT) {
                        throw new IllegalStateException("unexpected argType " + argType);
                     }

                     throw new UnsupportedOperationException("Parsing of plural/select/selectordinal argument is not supported.");
                  }

                  tempStatus.setIndex(sourceOffset);
                  double choiceResult = parseChoiceArgument(this.msgPattern, i, source, tempStatus);
                  if (tempStatus.getIndex() == sourceOffset) {
                     pos.setErrorIndex(sourceOffset);
                     return;
                  }

                  argResult = choiceResult;
                  haveArgResult = true;
                  sourceOffset = tempStatus.getIndex();
               }

               if (haveArgResult) {
                  if (args != null) {
                     args[argNumber] = argResult;
                  } else if (argsMap != null) {
                     argsMap.put(key, argResult);
                  }
               }

               prevIndex = this.msgPattern.getPart(argLimit).getLimit();
               i = argLimit;
            } else {
               prevIndex = part.getLimit();
            }

            ++i;
         }
      }
   }

   public Map parseToMap(String source) throws ParseException {
      ParsePosition pos = new ParsePosition(0);
      Map result = new HashMap();
      this.parse(0, source, pos, (Object[])null, result);
      if (pos.getIndex() == 0) {
         throw new ParseException("MessageFormat parse error!", pos.getErrorIndex());
      } else {
         return result;
      }
   }

   public Object parseObject(String source, ParsePosition pos) {
      return !this.msgPattern.hasNamedArguments() ? this.parse(source, pos) : this.parseToMap(source, pos);
   }

   public Object clone() {
      MessageFormat other = (MessageFormat)super.clone();
      Iterator it;
      if (this.customFormatArgStarts != null) {
         other.customFormatArgStarts = new HashSet();
         it = this.customFormatArgStarts.iterator();

         while(it.hasNext()) {
            Integer key = (Integer)it.next();
            other.customFormatArgStarts.add(key);
         }
      } else {
         other.customFormatArgStarts = null;
      }

      if (this.cachedFormatters != null) {
         other.cachedFormatters = new HashMap();
         it = this.cachedFormatters.entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            other.cachedFormatters.put(entry.getKey(), entry.getValue());
         }
      } else {
         other.cachedFormatters = null;
      }

      other.msgPattern = this.msgPattern == null ? null : (MessagePattern)this.msgPattern.clone();
      other.stockDateFormatter = this.stockDateFormatter == null ? null : (DateFormat)this.stockDateFormatter.clone();
      other.stockNumberFormatter = this.stockNumberFormatter == null ? null : (NumberFormat)this.stockNumberFormatter.clone();
      other.pluralProvider = null;
      other.ordinalProvider = null;
      return other;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj != null && this.getClass() == obj.getClass()) {
         MessageFormat other = (MessageFormat)obj;
         return Utility.objectEquals(this.ulocale, other.ulocale) && Utility.objectEquals(this.msgPattern, other.msgPattern) && Utility.objectEquals(this.cachedFormatters, other.cachedFormatters) && Utility.objectEquals(this.customFormatArgStarts, other.customFormatArgStarts);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.msgPattern.getPatternString().hashCode();
   }

   private DateFormat getStockDateFormatter() {
      if (this.stockDateFormatter == null) {
         this.stockDateFormatter = DateFormat.getDateTimeInstance(3, 3, (ULocale)this.ulocale);
      }

      return this.stockDateFormatter;
   }

   private NumberFormat getStockNumberFormatter() {
      if (this.stockNumberFormatter == null) {
         this.stockNumberFormatter = NumberFormat.getInstance(this.ulocale);
      }

      return this.stockNumberFormatter;
   }

   private void format(int msgStart, PluralSelectorContext pluralNumber, Object[] args, Map argsMap, AppendableWrapper dest, FieldPosition fp) {
      String msgString = this.msgPattern.getPatternString();
      int prevIndex = this.msgPattern.getPart(msgStart).getLimit();
      int i = msgStart + 1;

      while(true) {
         MessagePattern.Part part = this.msgPattern.getPart(i);
         MessagePattern.Part.Type type = part.getType();
         int index = part.getIndex();
         dest.append(msgString, prevIndex, index);
         if (type == MessagePattern.Part.Type.MSG_LIMIT) {
            return;
         }

         prevIndex = part.getLimit();
         if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
            if (pluralNumber.forReplaceNumber) {
               dest.formatAndAppend(pluralNumber.formatter, pluralNumber.number, pluralNumber.numberString);
            } else {
               dest.formatAndAppend(this.getStockNumberFormatter(), pluralNumber.number);
            }
         } else if (type == MessagePattern.Part.Type.ARG_START) {
            int argLimit = this.msgPattern.getLimitPartIndex(i);
            MessagePattern.ArgType argType = part.getArgType();
            ++i;
            part = this.msgPattern.getPart(i);
            boolean noArg = false;
            Object argId = null;
            String argName = this.msgPattern.getSubstring(part);
            int prevDestLength;
            Object arg;
            if (args != null) {
               prevDestLength = part.getValue();
               if (dest.attributes != null) {
                  argId = prevDestLength;
               }

               if (0 <= prevDestLength && prevDestLength < args.length) {
                  arg = args[prevDestLength];
               } else {
                  arg = null;
                  noArg = true;
               }
            } else {
               argId = argName;
               if (argsMap != null && argsMap.containsKey(argName)) {
                  arg = argsMap.get(argName);
               } else {
                  arg = null;
                  noArg = true;
               }
            }

            ++i;
            prevDestLength = dest.length;
            Format formatter = null;
            if (noArg) {
               dest.append((CharSequence)("{" + argName + "}"));
            } else if (arg == null) {
               dest.append((CharSequence)"null");
            } else if (pluralNumber != null && pluralNumber.numberArgIndex == i - 2) {
               if (pluralNumber.offset == 0.0) {
                  dest.formatAndAppend(pluralNumber.formatter, pluralNumber.number, pluralNumber.numberString);
               } else {
                  dest.formatAndAppend(pluralNumber.formatter, arg);
               }
            } else if (this.cachedFormatters != null && (formatter = (Format)this.cachedFormatters.get(i - 2)) != null) {
               if (!(formatter instanceof ChoiceFormat) && !(formatter instanceof PluralFormat) && !(formatter instanceof SelectFormat)) {
                  dest.formatAndAppend(formatter, arg);
               } else {
                  String subMsgString = formatter.format(arg);
                  if (subMsgString.indexOf(123) < 0 && (subMsgString.indexOf(39) < 0 || this.msgPattern.jdkAposMode())) {
                     if (dest.attributes == null) {
                        dest.append((CharSequence)subMsgString);
                     } else {
                        dest.formatAndAppend(formatter, arg);
                     }
                  } else {
                     MessageFormat subMsgFormat = new MessageFormat(subMsgString, this.ulocale);
                     subMsgFormat.format(0, (PluralSelectorContext)null, args, argsMap, dest, (FieldPosition)null);
                  }
               }
            } else if (argType == MessagePattern.ArgType.NONE || this.cachedFormatters != null && this.cachedFormatters.containsKey(i - 2)) {
               if (arg instanceof Number) {
                  dest.formatAndAppend(this.getStockNumberFormatter(), arg);
               } else if (arg instanceof Date) {
                  dest.formatAndAppend(this.getStockDateFormatter(), arg);
               } else {
                  dest.append((CharSequence)arg.toString());
               }
            } else if (argType == MessagePattern.ArgType.CHOICE) {
               if (!(arg instanceof Number)) {
                  throw new IllegalArgumentException("'" + arg + "' is not a Number");
               }

               double number = ((Number)arg).doubleValue();
               int subMsgStart = findChoiceSubMessage(this.msgPattern, i, number);
               this.formatComplexSubMessage(subMsgStart, (PluralSelectorContext)null, args, argsMap, dest);
            } else if (argType.hasPluralStyle()) {
               if (!(arg instanceof Number)) {
                  throw new IllegalArgumentException("'" + arg + "' is not a Number");
               }

               PluralSelectorProvider selector;
               if (argType == MessagePattern.ArgType.PLURAL) {
                  if (this.pluralProvider == null) {
                     this.pluralProvider = new PluralSelectorProvider(this, PluralRules.PluralType.CARDINAL);
                  }

                  selector = this.pluralProvider;
               } else {
                  if (this.ordinalProvider == null) {
                     this.ordinalProvider = new PluralSelectorProvider(this, PluralRules.PluralType.ORDINAL);
                  }

                  selector = this.ordinalProvider;
               }

               Number number = (Number)arg;
               double offset = this.msgPattern.getPluralOffset(i);
               PluralSelectorContext context = new PluralSelectorContext(i, argName, number, offset);
               int subMsgStart = PluralFormat.findSubMessage(this.msgPattern, i, selector, context, number.doubleValue());
               this.formatComplexSubMessage(subMsgStart, context, args, argsMap, dest);
            } else {
               if (argType != MessagePattern.ArgType.SELECT) {
                  throw new IllegalStateException("unexpected argType " + argType);
               }

               int subMsgStart = SelectFormat.findSubMessage(this.msgPattern, i, arg.toString());
               this.formatComplexSubMessage(subMsgStart, (PluralSelectorContext)null, args, argsMap, dest);
            }

            fp = this.updateMetaData(dest, prevDestLength, fp, argId);
            prevIndex = this.msgPattern.getPart(argLimit).getLimit();
            i = argLimit;
         }

         ++i;
      }
   }

   private void formatComplexSubMessage(int msgStart, PluralSelectorContext pluralNumber, Object[] args, Map argsMap, AppendableWrapper dest) {
      if (!this.msgPattern.jdkAposMode()) {
         this.format(msgStart, pluralNumber, args, argsMap, dest, (FieldPosition)null);
      } else {
         String msgString = this.msgPattern.getPatternString();
         StringBuilder sb = null;
         int prevIndex = this.msgPattern.getPart(msgStart).getLimit();
         int i = msgStart;

         while(true) {
            while(true) {
               ++i;
               MessagePattern.Part part = this.msgPattern.getPart(i);
               MessagePattern.Part.Type type = part.getType();
               int index = part.getIndex();
               if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                  String subMsgString;
                  if (sb == null) {
                     subMsgString = msgString.substring(prevIndex, index);
                  } else {
                     subMsgString = sb.append(msgString, prevIndex, index).toString();
                  }

                  if (subMsgString.indexOf(123) >= 0) {
                     MessageFormat subMsgFormat = new MessageFormat("", this.ulocale);
                     subMsgFormat.applyPattern(subMsgString, MessagePattern.ApostropheMode.DOUBLE_REQUIRED);
                     subMsgFormat.format(0, (PluralSelectorContext)null, args, argsMap, dest, (FieldPosition)null);
                  } else {
                     dest.append((CharSequence)subMsgString);
                  }

                  return;
               }

               if (type != MessagePattern.Part.Type.REPLACE_NUMBER && type != MessagePattern.Part.Type.SKIP_SYNTAX) {
                  if (type == MessagePattern.Part.Type.ARG_START) {
                     if (sb == null) {
                        sb = new StringBuilder();
                     }

                     sb.append(msgString, prevIndex, index);
                     prevIndex = index;
                     i = this.msgPattern.getLimitPartIndex(i);
                     index = this.msgPattern.getPart(i).getLimit();
                     MessagePattern.appendReducedApostrophes(msgString, prevIndex, index, sb);
                     prevIndex = index;
                  }
               } else {
                  if (sb == null) {
                     sb = new StringBuilder();
                  }

                  sb.append(msgString, prevIndex, index);
                  if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                     if (pluralNumber.forReplaceNumber) {
                        sb.append(pluralNumber.numberString);
                     } else {
                        sb.append(this.getStockNumberFormatter().format((Object)pluralNumber.number));
                     }
                  }

                  prevIndex = part.getLimit();
               }
            }
         }
      }
   }

   private String getLiteralStringUntilNextArgument(int from) {
      StringBuilder b = new StringBuilder();
      String msgString = this.msgPattern.getPatternString();
      int prevIndex = this.msgPattern.getPart(from).getLimit();
      int i = from + 1;

      while(true) {
         MessagePattern.Part part = this.msgPattern.getPart(i);
         MessagePattern.Part.Type type = part.getType();
         int index = part.getIndex();
         b.append(msgString, prevIndex, index);
         if (type == MessagePattern.Part.Type.ARG_START || type == MessagePattern.Part.Type.MSG_LIMIT) {
            return b.toString();
         }

         assert type == MessagePattern.Part.Type.SKIP_SYNTAX || type == MessagePattern.Part.Type.INSERT_CHAR : "Unexpected Part " + part + " in parsed message.";

         prevIndex = part.getLimit();
         ++i;
      }
   }

   private FieldPosition updateMetaData(AppendableWrapper dest, int prevLength, FieldPosition fp, Object argId) {
      if (dest.attributes != null && prevLength < dest.length) {
         dest.attributes.add(new AttributeAndPosition(argId, prevLength, dest.length));
      }

      if (fp != null && MessageFormat.Field.ARGUMENT.equals(fp.getFieldAttribute())) {
         fp.setBeginIndex(prevLength);
         fp.setEndIndex(dest.length);
         return null;
      } else {
         return fp;
      }
   }

   private static int findChoiceSubMessage(MessagePattern pattern, int partIndex, double number) {
      int count = pattern.countParts();
      partIndex += 2;

      int msgStart;
      while(true) {
         msgStart = partIndex;
         partIndex = pattern.getLimitPartIndex(partIndex);
         ++partIndex;
         if (partIndex >= count) {
            break;
         }

         MessagePattern.Part part = pattern.getPart(partIndex++);
         MessagePattern.Part.Type type = part.getType();
         if (type == MessagePattern.Part.Type.ARG_LIMIT) {
            break;
         }

         assert type.hasNumericValue();

         double boundary = pattern.getNumericValue(part);
         int selectorIndex = pattern.getPatternIndex(partIndex++);
         char boundaryChar = pattern.getPatternString().charAt(selectorIndex);
         if (boundaryChar == '<') {
            if (!(number > boundary)) {
               break;
            }
         } else if (!(number >= boundary)) {
            break;
         }
      }

      return msgStart;
   }

   private static double parseChoiceArgument(MessagePattern pattern, int partIndex, String source, ParsePosition pos) {
      int start = pos.getIndex();
      int furthest = start;
      double bestNumber = Double.NaN;

      int msgLimit;
      for(double tempNumber = 0.0; pattern.getPartType(partIndex) != MessagePattern.Part.Type.ARG_LIMIT; partIndex = msgLimit + 1) {
         tempNumber = pattern.getNumericValue(pattern.getPart(partIndex));
         partIndex += 2;
         msgLimit = pattern.getLimitPartIndex(partIndex);
         int len = matchStringUntilLimitPart(pattern, partIndex, msgLimit, source, start);
         if (len >= 0) {
            int newIndex = start + len;
            if (newIndex > furthest) {
               furthest = newIndex;
               bestNumber = tempNumber;
               if (newIndex == source.length()) {
                  break;
               }
            }
         }
      }

      if (furthest == start) {
         pos.setErrorIndex(start);
      } else {
         pos.setIndex(furthest);
      }

      return bestNumber;
   }

   private static int matchStringUntilLimitPart(MessagePattern pattern, int partIndex, int limitPartIndex, String source, int sourceOffset) {
      int matchingSourceLength = 0;
      String msgString = pattern.getPatternString();
      int prevIndex = pattern.getPart(partIndex).getLimit();

      while(true) {
         MessagePattern.Part part;
         do {
            ++partIndex;
            part = pattern.getPart(partIndex);
         } while(partIndex != limitPartIndex && part.getType() != MessagePattern.Part.Type.SKIP_SYNTAX);

         int index = part.getIndex();
         int length = index - prevIndex;
         if (length != 0 && !source.regionMatches(sourceOffset, msgString, prevIndex, length)) {
            return -1;
         }

         matchingSourceLength += length;
         if (partIndex == limitPartIndex) {
            return matchingSourceLength;
         }

         prevIndex = part.getLimit();
      }
   }

   private int findOtherSubMessage(int partIndex) {
      int count = this.msgPattern.countParts();
      MessagePattern.Part part = this.msgPattern.getPart(partIndex);
      if (part.getType().hasNumericValue()) {
         ++partIndex;
      }

      while(true) {
         part = this.msgPattern.getPart(partIndex++);
         MessagePattern.Part.Type type = part.getType();
         if (type != MessagePattern.Part.Type.ARG_LIMIT) {
            assert type == MessagePattern.Part.Type.ARG_SELECTOR;

            if (this.msgPattern.partSubstringMatches(part, "other")) {
               return partIndex;
            }

            if (this.msgPattern.getPartType(partIndex).hasNumericValue()) {
               ++partIndex;
            }

            partIndex = this.msgPattern.getLimitPartIndex(partIndex);
            ++partIndex;
            if (partIndex < count) {
               continue;
            }
         }

         return 0;
      }
   }

   private int findFirstPluralNumberArg(int msgStart, String argName) {
      int i = msgStart + 1;

      while(true) {
         MessagePattern.Part part = this.msgPattern.getPart(i);
         MessagePattern.Part.Type type = part.getType();
         if (type == MessagePattern.Part.Type.MSG_LIMIT) {
            return 0;
         }

         if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
            return -1;
         }

         if (type == MessagePattern.Part.Type.ARG_START) {
            MessagePattern.ArgType argType = part.getArgType();
            if (argName.length() != 0 && (argType == MessagePattern.ArgType.NONE || argType == MessagePattern.ArgType.SIMPLE)) {
               part = this.msgPattern.getPart(i + 1);
               if (this.msgPattern.partSubstringMatches(part, argName)) {
                  return i;
               }
            }

            i = this.msgPattern.getLimitPartIndex(i);
         }

         ++i;
      }
   }

   private void format(Object arguments, AppendableWrapper result, FieldPosition fp) {
      if (arguments != null && !(arguments instanceof Map)) {
         this.format((Object[])((Object[])arguments), (Map)null, result, fp);
      } else {
         this.format((Object[])null, (Map)arguments, result, fp);
      }

   }

   private void format(Object[] arguments, Map argsMap, AppendableWrapper dest, FieldPosition fp) {
      if (arguments != null && this.msgPattern.hasNamedArguments()) {
         throw new IllegalArgumentException("This method is not available in MessageFormat objects that use alphanumeric argument names.");
      } else {
         this.format(0, (PluralSelectorContext)null, arguments, argsMap, dest, fp);
      }
   }

   private void resetPattern() {
      if (this.msgPattern != null) {
         this.msgPattern.clear();
      }

      if (this.cachedFormatters != null) {
         this.cachedFormatters.clear();
      }

      this.customFormatArgStarts = null;
   }

   private Format createAppropriateFormat(String var1, String var2) {
      // $FF: Couldn't be decompiled
   }

   private static final int findKeyword(String s, String[] list) {
      s = PatternProps.trimWhiteSpace(s).toLowerCase(rootLocale);

      for(int i = 0; i < list.length; ++i) {
         if (s.equals(list[i])) {
            return i;
         }
      }

      return -1;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.defaultWriteObject();
      out.writeObject(this.ulocale.toLanguageTag());
      if (this.msgPattern == null) {
         this.msgPattern = new MessagePattern();
      }

      out.writeObject(this.msgPattern.getApostropheMode());
      out.writeObject(this.msgPattern.getPatternString());
      if (this.customFormatArgStarts != null && !this.customFormatArgStarts.isEmpty()) {
         out.writeInt(this.customFormatArgStarts.size());
         int formatIndex = 0;

         for(int partIndex = 0; (partIndex = this.nextTopLevelArgStart(partIndex)) >= 0; ++formatIndex) {
            if (this.customFormatArgStarts.contains(partIndex)) {
               out.writeInt(formatIndex);
               out.writeObject(this.cachedFormatters.get(partIndex));
            }
         }
      } else {
         out.writeInt(0);
      }

      out.writeInt(0);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      String languageTag = (String)in.readObject();
      this.ulocale = ULocale.forLanguageTag(languageTag);
      MessagePattern.ApostropheMode aposMode = (MessagePattern.ApostropheMode)in.readObject();
      if (this.msgPattern == null || aposMode != this.msgPattern.getApostropheMode()) {
         this.msgPattern = new MessagePattern(aposMode);
      }

      String msg = (String)in.readObject();
      if (msg != null) {
         this.applyPattern(msg);
      }

      int numPairs;
      for(numPairs = in.readInt(); numPairs > 0; --numPairs) {
         int formatIndex = in.readInt();
         Format formatter = (Format)in.readObject();
         this.setFormat(formatIndex, formatter);
      }

      for(numPairs = in.readInt(); numPairs > 0; --numPairs) {
         in.readInt();
         in.readObject();
      }

   }

   private void cacheExplicitFormats() {
      if (this.cachedFormatters != null) {
         this.cachedFormatters.clear();
      }

      this.customFormatArgStarts = null;
      int limit = this.msgPattern.countParts() - 2;

      for(int i = 1; i < limit; ++i) {
         MessagePattern.Part part = this.msgPattern.getPart(i);
         if (part.getType() == MessagePattern.Part.Type.ARG_START) {
            MessagePattern.ArgType argType = part.getArgType();
            if (argType == MessagePattern.ArgType.SIMPLE) {
               int index = i;
               i += 2;
               String explicitType = this.msgPattern.getSubstring(this.msgPattern.getPart(i++));
               String style = "";
               if ((part = this.msgPattern.getPart(i)).getType() == MessagePattern.Part.Type.ARG_STYLE) {
                  style = this.msgPattern.getSubstring(part);
                  ++i;
               }

               Format formatter = this.createAppropriateFormat(explicitType, style);
               this.setArgStartFormat(index, formatter);
            }
         }
      }

   }

   private void setArgStartFormat(int argStart, Format formatter) {
      if (this.cachedFormatters == null) {
         this.cachedFormatters = new HashMap();
      }

      this.cachedFormatters.put(argStart, formatter);
   }

   private void setCustomArgStartFormat(int argStart, Format formatter) {
      this.setArgStartFormat(argStart, formatter);
      if (this.customFormatArgStarts == null) {
         this.customFormatArgStarts = new HashSet();
      }

      this.customFormatArgStarts.add(argStart);
   }

   public static String autoQuoteApostrophe(String pattern) {
      StringBuilder buf = new StringBuilder(pattern.length() * 2);
      int state = 0;
      int braceCount = 0;
      int i = 0;

      for(int j = pattern.length(); i < j; ++i) {
         char c;
         c = pattern.charAt(i);
         label39:
         switch (state) {
            case 0:
               switch (c) {
                  case '\'':
                     state = 1;
                     break label39;
                  case '{':
                     state = 3;
                     ++braceCount;
                  default:
                     break label39;
               }
            case 1:
               switch (c) {
                  case '\'':
                     state = 0;
                     break label39;
                  case '{':
                  case '}':
                     state = 2;
                     break label39;
                  default:
                     buf.append('\'');
                     state = 0;
                     break label39;
               }
            case 2:
               switch (c) {
                  case '\'':
                     state = 0;
                  default:
                     break label39;
               }
            case 3:
               switch (c) {
                  case '{':
                     ++braceCount;
                     break;
                  case '}':
                     --braceCount;
                     if (braceCount == 0) {
                        state = 0;
                     }
               }
         }

         buf.append(c);
      }

      if (state == 1 || state == 2) {
         buf.append('\'');
      }

      return new String(buf);
   }

   private static final class AttributeAndPosition {
      private AttributedCharacterIterator.Attribute key;
      private Object value;
      private int start;
      private int limit;

      public AttributeAndPosition(Object fieldValue, int startIndex, int limitIndex) {
         this.init(MessageFormat.Field.ARGUMENT, fieldValue, startIndex, limitIndex);
      }

      public AttributeAndPosition(AttributedCharacterIterator.Attribute field, Object fieldValue, int startIndex, int limitIndex) {
         this.init(field, fieldValue, startIndex, limitIndex);
      }

      public void init(AttributedCharacterIterator.Attribute field, Object fieldValue, int startIndex, int limitIndex) {
         this.key = field;
         this.value = fieldValue;
         this.start = startIndex;
         this.limit = limitIndex;
      }
   }

   private static final class AppendableWrapper {
      private Appendable app;
      private int length;
      private List attributes;

      public AppendableWrapper(StringBuilder sb) {
         this.app = sb;
         this.length = sb.length();
         this.attributes = null;
      }

      public AppendableWrapper(StringBuffer sb) {
         this.app = sb;
         this.length = sb.length();
         this.attributes = null;
      }

      public void useAttributes() {
         this.attributes = new ArrayList();
      }

      public void append(CharSequence s) {
         try {
            this.app.append(s);
            this.length += s.length();
         } catch (IOException var3) {
            throw new ICUUncheckedIOException(var3);
         }
      }

      public void append(CharSequence s, int start, int limit) {
         try {
            this.app.append(s, start, limit);
            this.length += limit - start;
         } catch (IOException var5) {
            throw new ICUUncheckedIOException(var5);
         }
      }

      public void append(CharacterIterator iterator) {
         this.length += append(this.app, iterator);
      }

      public static int append(Appendable result, CharacterIterator iterator) {
         try {
            int start = iterator.getBeginIndex();
            int limit = iterator.getEndIndex();
            int length = limit - start;
            if (start < limit) {
               result.append(iterator.first());

               while(true) {
                  ++start;
                  if (start >= limit) {
                     break;
                  }

                  result.append(iterator.next());
               }
            }

            return length;
         } catch (IOException var5) {
            throw new ICUUncheckedIOException(var5);
         }
      }

      public void formatAndAppend(Format formatter, Object arg) {
         if (this.attributes == null) {
            this.append((CharSequence)formatter.format(arg));
         } else {
            AttributedCharacterIterator formattedArg = formatter.formatToCharacterIterator(arg);
            int prevLength = this.length;
            this.append((CharacterIterator)formattedArg);
            formattedArg.first();
            int start = formattedArg.getIndex();
            int limit = formattedArg.getEndIndex();
            int offset = prevLength - start;

            while(start < limit) {
               Map map = formattedArg.getAttributes();
               int runLimit = formattedArg.getRunLimit();
               if (map.size() != 0) {
                  Iterator var10 = map.entrySet().iterator();

                  while(var10.hasNext()) {
                     Map.Entry entry = (Map.Entry)var10.next();
                     this.attributes.add(new AttributeAndPosition((AttributedCharacterIterator.Attribute)entry.getKey(), entry.getValue(), offset + start, offset + runLimit));
                  }
               }

               start = runLimit;
               formattedArg.setIndex(runLimit);
            }
         }

      }

      public void formatAndAppend(Format formatter, Object arg, String argString) {
         if (this.attributes == null && argString != null) {
            this.append((CharSequence)argString);
         } else {
            this.formatAndAppend(formatter, arg);
         }

      }
   }

   private static final class PluralSelectorProvider implements PluralFormat.PluralSelector {
      private MessageFormat msgFormat;
      private PluralRules rules;
      private PluralRules.PluralType type;

      public PluralSelectorProvider(MessageFormat mf, PluralRules.PluralType type) {
         this.msgFormat = mf;
         this.type = type;
      }

      public String select(Object ctx, double number) {
         if (this.rules == null) {
            this.rules = PluralRules.forLocale(this.msgFormat.ulocale, this.type);
         }

         PluralSelectorContext context = (PluralSelectorContext)ctx;
         int otherIndex = this.msgFormat.findOtherSubMessage(context.startIndex);
         context.numberArgIndex = this.msgFormat.findFirstPluralNumberArg(otherIndex, context.argName);
         if (context.numberArgIndex > 0 && this.msgFormat.cachedFormatters != null) {
            context.formatter = (Format)this.msgFormat.cachedFormatters.get(context.numberArgIndex);
         }

         if (context.formatter == null) {
            context.formatter = this.msgFormat.getStockNumberFormatter();
            context.forReplaceNumber = true;
         }

         assert context.number.doubleValue() == number;

         context.numberString = context.formatter.format(context.number);
         if (context.formatter instanceof DecimalFormat) {
            PluralRules.IFixedDecimal dec = ((DecimalFormat)context.formatter).getFixedDecimal(number);
            return this.rules.select(dec);
         } else {
            return this.rules.select(number);
         }
      }
   }

   private static final class PluralSelectorContext {
      int startIndex;
      String argName;
      Number number;
      double offset;
      int numberArgIndex;
      Format formatter;
      String numberString;
      boolean forReplaceNumber;

      private PluralSelectorContext(int start, String name, Number num, double off) {
         this.startIndex = start;
         this.argName = name;
         if (off == 0.0) {
            this.number = num;
         } else {
            this.number = num.doubleValue() - off;
         }

         this.offset = off;
      }

      public String toString() {
         throw new AssertionError("PluralSelectorContext being formatted, rather than its number");
      }

      // $FF: synthetic method
      PluralSelectorContext(int x0, String x1, Number x2, double x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   public static class Field extends Format.Field {
      private static final long serialVersionUID = 7510380454602616157L;
      public static final Field ARGUMENT = new Field("message argument field");

      protected Field(String name) {
         super(name);
      }

      protected Object readResolve() throws InvalidObjectException {
         if (this.getClass() != Field.class) {
            throw new InvalidObjectException("A subclass of MessageFormat.Field must implement readResolve.");
         } else if (this.getName().equals(ARGUMENT.getName())) {
            return ARGUMENT;
         } else {
            throw new InvalidObjectException("Unknown attribute name.");
         }
      }
   }
}
