package kodo.kernel.jdoql;

import java.util.List;
import org.apache.openjpa.kernel.ExpressionStoreQuery;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.QueryContext;
import org.apache.openjpa.kernel.exps.ExpressionFactory;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.kernel.exps.QueryExpressions;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.UserException;

public class JDOQLParser implements ExpressionParser {
   private static final Localizer _loc = Localizer.forPackage(JDOQLParser.class);
   public static final String LANG_JDOQL = "javax.jdo.query.JDOQL";

   public String getLanguage() {
      return "javax.jdo.query.JDOQL";
   }

   public Object parse(String ql, ExpressionStoreQuery query) {
      SingleString sstr = new SingleString();
      sstr.fromString(ql);
      if (query.getContext().getParameterDeclaration() != null) {
         sstr.parameters = query.getContext().getParameterDeclaration();
      }

      return sstr;
   }

   public void populate(Object parsed, ExpressionStoreQuery query) {
      QueryContext ctx = query.getContext();
      SingleString sstr = (SingleString)parsed;
      if (sstr.unique != null && !ctx.isUnique()) {
         ctx.setUnique(sstr.unique);
      }

      if (sstr.parameters != null && ctx.getParameterDeclaration() == null) {
         ctx.declareParameters(sstr.parameters);
      }

      boolean candidate = sstr.candidateType != null && ctx.getCandidateType() == null;
      boolean result = sstr.resultType != null && ctx.getResultType() == null;
      if (candidate || result) {
         String[] imports = null;
         if (sstr.imports != null) {
            List decs = Filters.parseDeclaration(sstr.imports, ';', "imports");
            imports = new String[decs.size() / 2];

            for(int i = 0; i < decs.size(); i += 2) {
               imports[i / 2] = (String)decs.get(i + 1);
            }
         }

         Class cls;
         if (candidate) {
            cls = ctx.classForName(sstr.candidateType, imports);
            if (cls == null) {
               throw new UserException(_loc.get("bad-candidate-type", sstr.candidateType));
            }

            ctx.setCandidateType(cls, sstr.subclasses);
         }

         if (result) {
            cls = ctx.classForName(sstr.resultType, imports);
            if (cls == null) {
               throw new UserException(_loc.get("bad-result-type", sstr.resultType));
            }

            ctx.setResultType(cls);
         }

      }
   }

   public QueryExpressions eval(Object parsed, ExpressionStoreQuery query, ExpressionFactory factory, ClassMetaData candidate) {
      return (new JDOQLExpressionBuilder(factory, candidate, query.getContext().getCandidateType(), query.getResolver())).eval((SingleString)parsed);
   }

   public Value[] eval(String[] vals, ExpressionStoreQuery query, ExpressionFactory factory, ClassMetaData candidate) {
      return (new JDOQLExpressionBuilder(factory, candidate, query.getContext().getCandidateType(), query.getResolver())).eval(vals, query.getContext().getParameterTypes(), true);
   }
}
