package weblogic.diagnostics.querytree;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import weblogic.diagnostics.query.QueryExpressionLexer;
import weblogic.diagnostics.query.QueryExpressionParser;
import weblogic.diagnostics.query.QueryExpressionTreeWalker;
import weblogic.diagnostics.query.QueryParsingException;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableResolver;

public class QueryParser {
   public static QueryNode parseQuery(String query) throws QueryParsingException {
      try {
         QueryExpressionLexer lexer = new QueryExpressionLexer(query);
         QueryExpressionParser parser = new QueryExpressionParser(lexer);
         parser.booleanExpression();
         AST root = parser.getAST();
         QueryExpressionTreeWalker queryExpTreeWalker = new QueryExpressionTreeWalker((VariableResolver)null);
         QueryNode result = queryExpTreeWalker.buildTree(root);
         return result;
      } catch (RecognitionException var6) {
         throw new QueryParsingException(var6.getErrorMessage(), var6);
      } catch (TokenStreamException var7) {
         throw new QueryParsingException(var7.getMessage(), var7);
      } catch (UnknownVariableException var8) {
         throw new QueryParsingException(var8.getMessage(), var8);
      }
   }

   public static void main(String[] args) throws QueryParsingException {
      for(int i = 0; i < args.length; ++i) {
         String query = args[i];
         System.out.println("Query: " + query);
         QueryNode node = parseQuery(query);
         StringBuffer buf = new StringBuffer();
         node.displayNode(0, buf);
         System.out.println(buf.toString() + "\n");
      }

   }
}
