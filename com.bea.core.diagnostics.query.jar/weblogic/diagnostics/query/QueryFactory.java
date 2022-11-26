package weblogic.diagnostics.query;

public final class QueryFactory {
   public static Query createQuery(VariableDeclarator varDecl, VariableIndexResolver varIndexResolver, String queryExp) throws QueryException {
      CompiledQueryGen cqgen = new CompiledQueryGen(queryExp, varDecl, varIndexResolver);
      return cqgen.getCompiledQuery();
   }

   public static Query createQuery(VariableDeclarator varDecl, String queryExp) throws QueryException {
      CompiledQueryGen cqgen = new CompiledQueryGen(queryExp, varDecl);
      return cqgen.getCompiledQuery();
   }

   public static Query createQuery(VariableIndexResolver vir, String queryExp) throws QueryParsingException {
      return new LateBoundQuery(queryExp, vir, (Object)null);
   }

   public static Query createQuery(VariableIndexResolver vir, String queryExp, Object context) throws QueryParsingException {
      return new LateBoundQuery(queryExp, vir, context);
   }

   public static Query createQuery(String queryExp) throws QueryParsingException {
      return new LateBoundQuery(queryExp);
   }
}
