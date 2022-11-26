package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UnsupportedException;

class SubQ extends Val implements Subquery {
   private static final Localizer _loc = Localizer.forPackage(Subquery.class);
   private final String _alias;
   private Class _type = null;

   public SubQ(String alias) {
      this._alias = alias;
   }

   public String getCandidateAlias() {
      return this._alias;
   }

   public void setQueryExpressions(QueryExpressions q) {
   }

   public Class getType() {
      return this._type;
   }

   public void setImplicitType(Class type) {
      this._type = type;
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      throw new UnsupportedException(_loc.get("in-mem-subquery"));
   }
}
