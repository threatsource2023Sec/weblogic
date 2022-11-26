package weblogic.diagnostics.query;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import weblogic.diagnostics.debug.DebugLogger;

final class LateBoundQuery implements Query {
   private static DebugLogger queryDebugLogger = DebugLogger.getDebugLogger("DebugDiagnosticQuery");
   private String query;
   Object context;
   private AST root;
   private QueryExecutionTrace lastExecTrace;

   public LateBoundQuery(String q) throws QueryParsingException {
      this(q, (VariableIndexResolver)null, (Object)null);
   }

   public LateBoundQuery(String q, VariableIndexResolver indexResolver, Object context) throws QueryParsingException {
      this.query = q;
      this.context = context;
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Building query from " + this.query);
      }

      if (this.query != null && this.query.length() > 0) {
         try {
            QueryExpressionLexer lexer = new QueryExpressionLexer(this.query);
            QueryExpressionParser parser = new QueryExpressionParser(lexer);
            parser.setVariableIndexResolver(indexResolver);
            parser.booleanExpression();
            this.root = parser.getAST();
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Query expression in prefix notation = " + this.root.toStringTree());
            }
         } catch (RecognitionException var6) {
            throw new QueryParsingException(var6.getErrorMessage(), var6);
         } catch (TokenStreamException var7) {
            throw new QueryParsingException(var7.getMessage(), var7);
         } catch (UnknownVariableException var8) {
            throw new QueryParsingException(var8.getMessage(), var8);
         }
      }

   }

   public boolean executeQuery(VariableResolver vr) throws QueryExecutionException {
      if (this.query != null && this.query.length() != 0) {
         try {
            QueryExpressionTreeWalker queryExpTreeWalker = new QueryExpressionTreeWalker(vr);
            boolean result = queryExpTreeWalker.evaluateQuery(this.root);
            this.lastExecTrace = queryExpTreeWalker.getQueryExecutionTrace();
            return result;
         } catch (RecognitionException var4) {
            throw new QueryExecutionException(var4.getErrorMessage(), var4);
         }
      } else {
         return true;
      }
   }

   public String toString() {
      return this.query;
   }

   public QueryExecutionTrace getLastExecutionTrace() {
      return this.lastExecTrace;
   }

   public static void main(String[] args) {
      try {
         LateBoundQuery q = new LateBoundQuery(args[0]);
         System.out.println("The result of executing " + q + " is " + q.executeQuery((VariableResolver)null));
      } catch (QueryException var2) {
         System.err.println(var2.getMessage());
         var2.printStackTrace();
         System.exit(1);
      }

   }
}
