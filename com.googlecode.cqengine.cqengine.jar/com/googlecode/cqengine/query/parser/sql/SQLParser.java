package com.googlecode.cqengine.query.parser.sql;

import com.googlecode.cqengine.query.parser.common.InvalidQueryException;
import com.googlecode.cqengine.query.parser.common.ParseResult;
import com.googlecode.cqengine.query.parser.common.QueryParser;
import com.googlecode.cqengine.query.parser.sql.grammar.SQLGrammarLexer;
import com.googlecode.cqengine.query.parser.sql.grammar.SQLGrammarParser;
import com.googlecode.cqengine.query.parser.sql.support.FallbackValueParser;
import com.googlecode.cqengine.query.parser.sql.support.SQLAntlrListener;
import com.googlecode.cqengine.query.parser.sql.support.StringParser;
import java.util.Map;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class SQLParser extends QueryParser {
   public SQLParser(Class objectType) {
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
            SQLGrammarLexer lexer = new SQLGrammarLexer(new ANTLRInputStream(query));
            lexer.removeErrorListeners();
            lexer.addErrorListener(SYNTAX_ERROR_LISTENER);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            SQLGrammarParser parser = new SQLGrammarParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(SYNTAX_ERROR_LISTENER);
            SQLGrammarParser.StartContext queryContext = parser.start();
            ParseTreeWalker walker = new ParseTreeWalker();
            SQLAntlrListener listener = new SQLAntlrListener(this);
            walker.walk(listener, queryContext);
            return new ParseResult(listener.getParsedQuery(), listener.getQueryOptions());
         }
      } catch (InvalidQueryException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new InvalidQueryException("Failed to parse query", var9);
      }
   }

   public static SQLParser forPojo(Class pojoClass) {
      return new SQLParser(pojoClass);
   }

   public static SQLParser forPojoWithAttributes(Class pojoClass, Map attributes) {
      SQLParser parser = forPojo(pojoClass);
      parser.registerAttributes(attributes);
      return parser;
   }
}
