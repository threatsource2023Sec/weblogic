package com.googlecode.cqengine.query.parser.cqn;

import com.googlecode.cqengine.query.parser.common.InvalidQueryException;
import com.googlecode.cqengine.query.parser.common.ParseResult;
import com.googlecode.cqengine.query.parser.common.QueryParser;
import com.googlecode.cqengine.query.parser.cqn.grammar.CQNGrammarLexer;
import com.googlecode.cqengine.query.parser.cqn.grammar.CQNGrammarParser;
import com.googlecode.cqengine.query.parser.cqn.support.CQNAntlrListener;
import com.googlecode.cqengine.query.parser.cqn.support.FallbackValueParser;
import com.googlecode.cqengine.query.parser.cqn.support.StringParser;
import java.util.Map;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class CQNParser extends QueryParser {
   public CQNParser(Class objectType) {
      super(objectType);
      StringParser stringParser = new StringParser();
      super.registerValueParser(String.class, stringParser);
      super.registerFallbackValueParser(new FallbackValueParser(stringParser));
   }

   public ParseResult parse(String query) {
      try {
         if (query == null) {
            throw new IllegalArgumentException("Query was null");
         } else {
            CQNGrammarLexer lexer = new CQNGrammarLexer(new ANTLRInputStream(query));
            lexer.removeErrorListeners();
            lexer.addErrorListener(SYNTAX_ERROR_LISTENER);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            CQNGrammarParser parser = new CQNGrammarParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(SYNTAX_ERROR_LISTENER);
            CQNGrammarParser.StartContext queryContext = parser.start();
            ParseTreeWalker walker = new ParseTreeWalker();
            CQNAntlrListener listener = new CQNAntlrListener(this);
            walker.walk(listener, queryContext);
            return new ParseResult(listener.getParsedQuery(), listener.getQueryOptions());
         }
      } catch (InvalidQueryException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new InvalidQueryException("Failed to parse query", var9);
      }
   }

   public static CQNParser forPojo(Class pojoClass) {
      return new CQNParser(pojoClass);
   }

   public static CQNParser forPojoWithAttributes(Class pojoClass, Map attributes) {
      CQNParser parser = forPojo(pojoClass);
      parser.registerAttributes(attributes);
      return parser;
   }
}
