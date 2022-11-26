package com.googlecode.cqengine.query.parser.common;

import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.parser.common.valuetypes.BigDecimalParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.BigIntegerParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.BooleanParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.ByteParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.CharacterParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.DoubleParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.FloatParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.IntegerParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.LongParser;
import com.googlecode.cqengine.query.parser.common.valuetypes.ShortParser;
import com.googlecode.cqengine.resultset.ResultSet;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

public abstract class QueryParser {
   protected final Class objectType;
   protected final Map attributes = new HashMap();
   protected final Map valueParsers = new HashMap();
   protected volatile ValueParser fallbackValueParser = null;
   protected static final BaseErrorListener SYNTAX_ERROR_LISTENER = new BaseErrorListener() {
      public void syntaxError(Recognizer recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
         throw new InvalidQueryException("Failed to parse query at line " + line + ":" + charPositionInLine + ": " + msg);
      }
   };

   public QueryParser(Class objectType) {
      this.registerValueParser(Boolean.class, new BooleanParser());
      this.registerValueParser(Byte.class, new ByteParser());
      this.registerValueParser(Character.class, new CharacterParser());
      this.registerValueParser(Short.class, new ShortParser());
      this.registerValueParser(Integer.class, new IntegerParser());
      this.registerValueParser(Long.class, new LongParser());
      this.registerValueParser(Float.class, new FloatParser());
      this.registerValueParser(Double.class, new DoubleParser());
      this.registerValueParser(BigInteger.class, new BigIntegerParser());
      this.registerValueParser(BigDecimal.class, new BigDecimalParser());
      this.objectType = objectType;
   }

   public void registerAttribute(Attribute attribute) {
      this.attributes.put(attribute.getAttributeName(), attribute);
   }

   public void registerAttributes(Map attributes) {
      this.registerAttributes((Iterable)attributes.values());
   }

   public void registerAttributes(Iterable attributes) {
      Iterator var2 = attributes.iterator();

      while(var2.hasNext()) {
         Attribute attribute = (Attribute)var2.next();
         this.registerAttribute(attribute);
      }

   }

   public void registerValueParser(Class valueType, ValueParser valueParser) {
      this.valueParsers.put(valueType, valueParser);
   }

   public void registerFallbackValueParser(ValueParser fallbackValueParser) {
      this.fallbackValueParser = fallbackValueParser;
   }

   public Class getObjectType() {
      return this.objectType;
   }

   public Attribute getAttribute(ParseTree attributeNameContext, Class expectedSuperType) {
      String attributeName = (String)this.parseValue(String.class, attributeNameContext.getText());
      Attribute attribute = (Attribute)this.attributes.get(attributeName);
      if (attribute == null) {
         throw new IllegalStateException("No such attribute has been registered with the parser: " + attributeName);
      } else if (!expectedSuperType.isAssignableFrom(attribute.getAttributeType())) {
         throw new IllegalStateException("Non-" + expectedSuperType.getSimpleName() + " attribute used in a query which requires a " + expectedSuperType.getSimpleName() + " attribute: " + attribute.getAttributeName());
      } else {
         return attribute;
      }
   }

   public Object parseValue(Attribute attribute, ParseTree parameterContext) {
      return this.parseValue(attribute.getAttributeType(), parameterContext.getText());
   }

   public Object parseValue(Class valueType, ParseTree parameterContext) {
      return this.parseValue(valueType, parameterContext.getText());
   }

   public Object parseValue(Class valueType, String text) {
      ValueParser valueParser = (ValueParser)this.valueParsers.get(valueType);
      if (valueParser != null) {
         return valueParser.validatedParse(valueType, text);
      } else {
         ValueParser fallbackValueParser = this.fallbackValueParser;
         if (fallbackValueParser != null) {
            return valueType.cast(fallbackValueParser.parse(valueType, text));
         } else {
            throw new IllegalStateException("No value parser has been registered to parse type: " + valueType.getName());
         }
      }
   }

   public abstract ParseResult parse(String var1);

   public ResultSet retrieve(IndexedCollection collection, String query) {
      ParseResult parseResult = this.parse(query);
      return collection.retrieve(parseResult.getQuery(), parseResult.getQueryOptions());
   }

   public Query query(String query) {
      return this.parse(query).getQuery();
   }

   public QueryOptions queryOptions(String query) {
      return this.parse(query).getQueryOptions();
   }
}
