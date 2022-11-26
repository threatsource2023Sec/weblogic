package org.apache.openjpa.kernel.jpql;

import org.apache.openjpa.kernel.ExpressionStoreQuery;
import org.apache.openjpa.kernel.exps.ExpressionFactory;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;

public class JPQLParser implements ExpressionParser {
   private static final Localizer _loc = Localizer.forPackage(JPQLParser.class);
   public static final String LANG_JPQL = "javax.persistence.JPQL";

   public Object parse(String ql, ExpressionStoreQuery query) {
      if (query.getContext().getParameterDeclaration() != null) {
         throw new UserException(_loc.get("param-decs-invalid"));
      } else {
         return new JPQLExpressionBuilder.ParsedJPQL(ql);
      }
   }

   public void populate(Object parsed, ExpressionStoreQuery query) {
      if (!(parsed instanceof JPQLExpressionBuilder.ParsedJPQL)) {
         throw new ClassCastException(parsed == null ? null + "" : parsed.getClass().getName());
      } else {
         ((JPQLExpressionBuilder.ParsedJPQL)parsed).populate(query);
      }
   }

   public QueryExpressions eval(Object parsed, ExpressionStoreQuery query, ExpressionFactory factory, ClassMetaData candidate) {
      try {
         return (new JPQLExpressionBuilder(factory, query, parsed)).getQueryExpressions();
      } catch (OpenJPAException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new UserException(_loc.get("bad-jpql", parsed), var7);
      }
   }

   public Value[] eval(String[] vals, ExpressionStoreQuery query, ExpressionFactory factory, ClassMetaData candidate) {
      return null;
   }

   public String getLanguage() {
      return "javax.persistence.JPQL";
   }
}
