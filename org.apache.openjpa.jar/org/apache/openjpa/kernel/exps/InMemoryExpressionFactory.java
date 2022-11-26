package org.apache.openjpa.kernel.exps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.kernel.Extent;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.UserException;

public class InMemoryExpressionFactory implements ExpressionFactory {
   private static final Value NULL = new Null();
   private static final Value CURRENT_DATE = new CurrentDate();
   private static final Object UNIQUE = new Object();
   private List _unbounds = null;

   public boolean matches(QueryExpressions exps, ClassMetaData type, boolean subs, Object candidate, StoreContext ctx, Object[] params) {
      if (candidate == null) {
         return false;
      } else if (!subs && candidate.getClass() != type.getDescribedType()) {
         return false;
      } else {
         return subs && !type.getDescribedType().isAssignableFrom(candidate.getClass()) ? false : this.matches((Exp)exps.filter, (Object)candidate, ctx, params, 0);
      }
   }

   private boolean matches(Exp exp, Object candidate, StoreContext ctx, Object[] params, int i) {
      if (this._unbounds != null && i != this._unbounds.size()) {
         UnboundVariable var = (UnboundVariable)this._unbounds.get(i);
         Iterator itr = ctx.extentIterator(var.getType(), true, (FetchConfiguration)null, false);

         boolean var8;
         try {
            if (!itr.hasNext()) {
               var.setValue((Object)null);
               var8 = this.matches(exp, candidate, ctx, params, i + 1);
               return var8;
            }

            while(itr.hasNext()) {
               var.setValue(itr.next());
               if (this.matches(exp, candidate, ctx, params, i + 1)) {
                  var8 = true;
                  return var8;
               }
            }

            var8 = false;
         } finally {
            ImplHelper.close(itr);
         }

         return var8;
      } else {
         return exp.evaluate(candidate, candidate, ctx, params);
      }
   }

   public List group(QueryExpressions exps, List matches, StoreContext ctx, Object[] params) {
      if (matches != null && !matches.isEmpty() && exps.grouping.length != 0) {
         matches = this.order(exps, exps.grouping, false, matches, ctx, params);
         Object[] prevs = new Object[exps.grouping.length];
         Arrays.fill(prevs, UNIQUE);
         Object[] curs = new Object[exps.grouping.length];
         List grouped = new ArrayList();
         List group = null;
         Iterator itr = matches.iterator();

         while(itr.hasNext()) {
            Object pc = itr.next();
            boolean eq = true;

            for(int i = 0; i < exps.grouping.length; ++i) {
               curs[i] = ((Val)exps.grouping[i]).evaluate(pc, pc, ctx, params);
               eq = eq && ObjectUtils.equals(prevs[i], curs[i]);
            }

            if (!eq) {
               if (group != null) {
                  grouped.add(group);
               }

               group = new ArrayList();
            }

            group.add(pc);
            System.arraycopy(curs, 0, prevs, 0, curs.length);
         }

         if (group != null) {
            grouped.add(group);
         }

         return grouped;
      } else {
         return matches;
      }
   }

   public boolean matches(QueryExpressions exps, Collection group, StoreContext ctx, Object[] params) {
      if (group != null && !group.isEmpty()) {
         return exps.having == null ? true : this.matches((Exp)exps.having, (Collection)group, ctx, params, 0);
      } else {
         return false;
      }
   }

   private boolean matches(Exp exp, Collection group, StoreContext ctx, Object[] params, int i) {
      if (this._unbounds != null && i != this._unbounds.size()) {
         UnboundVariable var = (UnboundVariable)this._unbounds.get(i);
         Extent extent = ctx.getBroker().newExtent(var.getType(), true);
         Iterator itr = extent.iterator();

         boolean var9;
         try {
            if (!itr.hasNext()) {
               var.setValue((Object)null);
               var9 = this.matches(exp, group, ctx, params, i + 1);
               return var9;
            }

            do {
               if (!itr.hasNext()) {
                  var9 = false;
                  return var9;
               }

               var.setValue(itr.next());
            } while(!this.matches(exp, group, ctx, params, i + 1));

            var9 = true;
         } finally {
            ImplHelper.close(itr);
         }

         return var9;
      } else {
         return exp.evaluate(group, ctx, params);
      }
   }

   public List project(QueryExpressions exps, List matches, StoreContext ctx, Object[] params) {
      if (exps.projections.length == 0) {
         return matches;
      } else if (exps.grouping.length == 0 && exps.isAggregate()) {
         Object[] projection = this.project(matches, exps, true, ctx, params);
         return Arrays.asList((Object)projection);
      } else {
         List projected = new ArrayList(matches.size());
         Iterator itr = matches.iterator();

         while(itr.hasNext()) {
            projected.add(this.project(itr.next(), exps, exps.grouping.length > 0, ctx, params));
         }

         return projected;
      }
   }

   private Object[] project(Object candidate, QueryExpressions exps, boolean agg, StoreContext ctx, Object[] params) {
      Object[] projection = new Object[exps.projections.length + exps.ordering.length];
      Object result = null;

      for(int i = 0; i < exps.projections.length; ++i) {
         if (agg) {
            result = ((Val)exps.projections[i]).evaluate((Collection)((Collection)candidate), (Object)null, ctx, params);
         } else {
            result = ((Val)exps.projections[i]).evaluate(candidate, candidate, ctx, params);
         }

         projection[i] = result;
      }

      for(int i = 0; i < exps.ordering.length; ++i) {
         boolean repeat = false;

         for(int j = 0; !repeat && j < exps.projections.length; ++j) {
            if (exps.orderingClauses[i].equals(exps.projectionClauses[j])) {
               result = projection[j];
               repeat = true;
            }
         }

         if (!repeat) {
            if (agg) {
               result = ((Val)exps.ordering[i]).evaluate((Collection)((Collection)candidate), (Object)null, ctx, params);
            } else {
               result = ((Val)exps.ordering[i]).evaluate(candidate, candidate, ctx, params);
            }
         }

         projection[i + exps.projections.length] = result;
      }

      return projection;
   }

   public List order(QueryExpressions exps, List matches, StoreContext ctx, Object[] params) {
      return this.order(exps, exps.ordering, true, matches, ctx, params);
   }

   private List order(QueryExpressions exps, Value[] orderValues, boolean projected, List matches, StoreContext ctx, Object[] params) {
      if (matches != null && !matches.isEmpty() && orderValues != null && orderValues.length != 0) {
         int results = projected ? exps.projections.length : 0;
         boolean[] asc = projected ? exps.ascending : null;

         for(int i = orderValues.length - 1; i >= 0; --i) {
            int idx = results > 0 ? results + i : -1;
            Collections.sort(matches, new OrderValueComparator((Val)orderValues[i], asc == null || asc[i], idx, ctx, params));
         }

         return matches;
      } else {
         return matches;
      }
   }

   public List distinct(QueryExpressions exps, boolean fromExtent, List matches) {
      if (matches != null && !matches.isEmpty()) {
         int len = exps.projections.length;
         if ((exps.distinct & 4) == 0 || fromExtent && len == 0) {
            return matches;
         } else {
            Set seen = new HashSet(matches.size());
            List distinct = null;
            ListIterator li = matches.listIterator();

            while(li.hasNext()) {
               Object cur = li.next();
               Object key = len > 0 && cur != null ? new ArrayKey((Object[])((Object[])cur)) : cur;
               if (seen.add(key)) {
                  if (distinct != null) {
                     distinct.add(cur);
                  }
               } else if (distinct == null) {
                  distinct = new ArrayList(matches.size());
                  distinct.addAll(matches.subList(0, li.previousIndex()));
               }
            }

            return (List)(distinct == null ? matches : distinct);
         }
      } else {
         return matches;
      }
   }

   public Expression emptyExpression() {
      return new Exp();
   }

   public Expression asExpression(Value v) {
      return new ValExpression((Val)v);
   }

   public Expression equal(Value v1, Value v2) {
      return new EqualExpression((Val)v1, (Val)v2);
   }

   public Expression notEqual(Value v1, Value v2) {
      return new NotEqualExpression((Val)v1, (Val)v2);
   }

   public Expression lessThan(Value v1, Value v2) {
      return new LessThanExpression((Val)v1, (Val)v2);
   }

   public Expression greaterThan(Value v1, Value v2) {
      return new GreaterThanExpression((Val)v1, (Val)v2);
   }

   public Expression lessThanEqual(Value v1, Value v2) {
      return new LessThanEqualExpression((Val)v1, (Val)v2);
   }

   public Expression greaterThanEqual(Value v1, Value v2) {
      return new GreaterThanEqualExpression((Val)v1, (Val)v2);
   }

   public Expression isEmpty(Value v1) {
      return new IsEmptyExpression((Val)v1);
   }

   public Expression isNotEmpty(Value v1) {
      return this.not(this.isEmpty(v1));
   }

   public Expression contains(Value v1, Value v2) {
      return new ContainsExpression((Val)v1, (Val)v2);
   }

   public Expression containsKey(Value v1, Value v2) {
      return new ContainsKeyExpression((Val)v1, (Val)v2);
   }

   public Expression containsValue(Value v1, Value v2) {
      return new ContainsValueExpression((Val)v1, (Val)v2);
   }

   public Value getMapValue(Value map, Value arg) {
      return new GetMapValue((Val)map, (Val)arg);
   }

   public Expression isInstance(Value v1, Class c) {
      return new InstanceofExpression((Val)v1, c);
   }

   public Expression and(Expression exp1, Expression exp2) {
      return (Expression)(exp1 instanceof BindVariableExpression ? new BindVariableAndExpression((BindVariableExpression)exp1, (Exp)exp2) : new AndExpression((Exp)exp1, (Exp)exp2));
   }

   public Expression or(Expression exp1, Expression exp2) {
      return new OrExpression((Exp)exp1, (Exp)exp2);
   }

   public Expression not(Expression exp) {
      return new NotExpression((Exp)exp);
   }

   public Expression bindVariable(Value var, Value val) {
      return new BindVariableExpression((BoundVariable)var, (Val)val);
   }

   public Expression bindKeyVariable(Value var, Value val) {
      return new BindKeyVariableExpression((BoundVariable)var, (Val)val);
   }

   public Expression bindValueVariable(Value var, Value val) {
      return new BindValueVariableExpression((BoundVariable)var, (Val)val);
   }

   public Expression endsWith(Value v1, Value v2) {
      return new EndsWithExpression((Val)v1, (Val)v2);
   }

   public Expression matches(Value v1, Value v2, String single, String multi, String esc) {
      return new MatchesExpression((Val)v1, (Val)v2, single, multi, esc, true);
   }

   public Expression notMatches(Value v1, Value v2, String single, String multi, String esc) {
      return new MatchesExpression((Val)v1, (Val)v2, single, multi, esc, false);
   }

   public Expression startsWith(Value v1, Value v2) {
      return new StartsWithExpression((Val)v1, (Val)v2);
   }

   public Subquery newSubquery(ClassMetaData candidate, boolean subs, String alias) {
      return new SubQ(alias);
   }

   public Path newPath() {
      return new CandidatePath();
   }

   public Path newPath(Value val) {
      return new ValuePath((Val)val);
   }

   public Literal newLiteral(Object val, int parseType) {
      return new Lit(val, parseType);
   }

   public Value getThis() {
      return new This();
   }

   public Value getNull() {
      return NULL;
   }

   public Value getCurrentDate() {
      return CURRENT_DATE;
   }

   public Value getCurrentTime() {
      return CURRENT_DATE;
   }

   public Value getCurrentTimestamp() {
      return CURRENT_DATE;
   }

   public Parameter newParameter(String name, Class type) {
      return new Param(name, type);
   }

   public Value newExtension(FilterListener listener, Value target, Value arg) {
      return new Extension(listener, (Val)target, (Val)arg);
   }

   public Value newAggregate(AggregateListener listener, Value arg) {
      return new Aggregate(listener, (Val)arg);
   }

   public Arguments newArgumentList(Value val1, Value val2) {
      return new Args(val1, val2);
   }

   public Value newUnboundVariable(String name, Class type) {
      UnboundVariable var = new UnboundVariable(type);
      if (this._unbounds == null) {
         this._unbounds = new ArrayList(3);
      }

      this._unbounds.add(var);
      return var;
   }

   public Value newBoundVariable(String name, Class type) {
      return new BoundVariable(type);
   }

   public Value cast(Value val, Class cls) {
      if (val instanceof CandidatePath) {
         ((CandidatePath)val).castTo(cls);
      } else if (val instanceof BoundVariable) {
         ((BoundVariable)val).castTo(cls);
      } else {
         val = new Cast((Val)val, cls);
      }

      return (Value)val;
   }

   public Value add(Value val1, Value val2) {
      return new Add((Val)val1, (Val)val2);
   }

   public Value subtract(Value val1, Value val2) {
      return new Subtract((Val)val1, (Val)val2);
   }

   public Value multiply(Value val1, Value val2) {
      return new Multiply((Val)val1, (Val)val2);
   }

   public Value divide(Value val1, Value val2) {
      return new Divide((Val)val1, (Val)val2);
   }

   public Value mod(Value val1, Value val2) {
      return new Mod((Val)val1, (Val)val2);
   }

   public Value abs(Value val) {
      return new Abs((Val)val);
   }

   public Value indexOf(Value val1, Value val2) {
      return new IndexOf((Val)val1, (Val)val2);
   }

   public Value concat(Value val1, Value val2) {
      return new Concat((Val)val1, (Val)val2);
   }

   public Value stringLength(Value str) {
      return new StringLength((Val)str);
   }

   public Value trim(Value str, Value trimChar, Boolean where) {
      return new Trim((Val)str, (Val)trimChar, where);
   }

   public Value sqrt(Value val) {
      return new Sqrt((Val)val);
   }

   public Value substring(Value val1, Value val2) {
      return new Substring((Val)val1, (Val)val2);
   }

   public Value toUpperCase(Value val) {
      return new ToUpperCase((Val)val);
   }

   public Value toLowerCase(Value val) {
      return new ToLowerCase((Val)val);
   }

   public Value avg(Value val) {
      return new Avg((Val)val);
   }

   public Value count(Value val) {
      return new Count((Val)val);
   }

   public Value distinct(Value val) {
      return new Distinct((Val)val);
   }

   public Value max(Value val) {
      return new Max((Val)val);
   }

   public Value min(Value val) {
      return new Min((Val)val);
   }

   public Value sum(Value val) {
      return new Sum((Val)val);
   }

   public Value any(Value val) {
      return new Any((Val)val);
   }

   public Value all(Value val) {
      return new All((Val)val);
   }

   public Value size(Value val) {
      return new Size((Val)val);
   }

   public Value getObjectId(Value val) {
      return new GetObjectId((Val)val);
   }

   private static class OrderValueComparator implements Comparator {
      private final StoreContext _ctx;
      private final Val _val;
      private final boolean _asc;
      private final int _idx;
      private final Object[] _params;

      private OrderValueComparator(Val val, boolean asc, int idx, StoreContext ctx, Object[] params) {
         this._ctx = ctx;
         this._val = val;
         this._asc = asc;
         this._idx = idx;
         this._params = params;
      }

      public int compare(Object o1, Object o2) {
         if (this._idx != -1) {
            o1 = ((Object[])((Object[])o1))[this._idx];
            o2 = ((Object[])((Object[])o2))[this._idx];
         } else {
            o1 = this._val.evaluate(o1, o1, this._ctx, this._params);
            o2 = this._val.evaluate(o2, o2, this._ctx, this._params);
         }

         if (o1 == null && o2 == null) {
            return 0;
         } else if (o1 == null) {
            return this._asc ? 1 : -1;
         } else if (o2 == null) {
            return this._asc ? -1 : 1;
         } else if (o1 instanceof Boolean && o2 instanceof Boolean) {
            int i1 = (Boolean)o1 ? 1 : 0;
            int i2 = (Boolean)o2 ? 1 : 0;
            return i1 - i2;
         } else {
            try {
               return this._asc ? ((Comparable)o1).compareTo(o2) : ((Comparable)o2).compareTo(o1);
            } catch (ClassCastException var5) {
               Localizer loc = Localizer.forPackage(InMemoryExpressionFactory.class);
               throw new UserException(loc.get("not-comp", o1, o2));
            }
         }
      }

      // $FF: synthetic method
      OrderValueComparator(Val x0, boolean x1, int x2, StoreContext x3, Object[] x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }

   private static class ArrayKey {
      private final Object[] _arr;

      public ArrayKey(Object[] arr) {
         this._arr = arr;
      }

      public int hashCode() {
         int rs = 17;

         for(int i = 0; i < this._arr.length; ++i) {
            rs = 37 * rs + (this._arr[i] == null ? 0 : this._arr[i].hashCode());
         }

         return rs;
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else if (other == null) {
            return false;
         } else {
            Object[] arr = ((ArrayKey)other)._arr;
            if (this._arr.length != arr.length) {
               return false;
            } else {
               for(int i = 0; i < this._arr.length; ++i) {
                  if (!ObjectUtils.equals(this._arr[i], arr[i])) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }
}
