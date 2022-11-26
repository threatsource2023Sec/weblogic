package weblogic.diagnostics.query;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import weblogic.diagnostics.debug.DebugLogger;

class CompiledQueryGen {
   private static DebugLogger queryDebugLogger = DebugLogger.getDebugLogger("DebugDiagnosticQuery");
   private String query;
   private AST root;
   private QueryExpressionCompiler queryExpCompiler;

   private CompiledQueryGen(String q) throws QueryException {
      this(q, (VariableDeclarator)null, (VariableIndexResolver)null);
   }

   public CompiledQueryGen(String q, VariableDeclarator varDecl) throws QueryException {
      this(q, varDecl, (VariableIndexResolver)null);
   }

   public CompiledQueryGen(String q, VariableDeclarator varDecl, VariableIndexResolver varIdxRes) throws QueryException {
      this.queryExpCompiler = new QueryExpressionCompiler();
      this.query = q;
      if (queryDebugLogger.isDebugEnabled()) {
         queryDebugLogger.debug("Building query from " + this.query);
      }

      if (this.query != null && this.query.length() > 0) {
         try {
            QueryExpressionLexer lexer = new QueryExpressionLexer(this.query);
            QueryExpressionParser parser = new QueryExpressionParser(lexer);
            parser.booleanExpression();
            this.root = parser.getAST();
            if (queryDebugLogger.isDebugEnabled()) {
               queryDebugLogger.debug("Query expression in prefix notation = " + this.root.toStringTree());
            }

            this.queryExpCompiler.initialize(varDecl, varIdxRes);
            this.queryExpCompiler.compileQuery(this.root);
         } catch (RecognitionException var6) {
            throw new QueryParsingException(var6.getErrorMessage(), var6);
         } catch (TokenStreamException var7) {
            throw new QueryParsingException(var7.getMessage(), var7);
         }
      }

   }

   public Query getCompiledQuery() throws QueryException {
      return this.queryExpCompiler.getCompiledQuery();
   }

   public String toString() {
      return this.query;
   }

   public static void main(String[] args) {
      try {
         CompiledQueryGen qcg = new CompiledQueryGen(args[0]);
         Query q = qcg.getCompiledQuery();
         System.out.println("The result of executing " + q + " is " + q.executeQuery((VariableResolver)null));
      } catch (QueryException var3) {
         System.err.println(var3.getMessage());
         var3.printStackTrace();
         System.exit(1);
      }

   }
}
