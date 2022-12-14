package org.apache.openjpa.jdbc.kernel.exps;

import java.io.Serializable;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.Arguments;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionFactory;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.kernel.exps.Literal;
import org.apache.openjpa.kernel.exps.Parameter;
import org.apache.openjpa.kernel.exps.Path;
import org.apache.openjpa.kernel.exps.Subquery;
import org.apache.openjpa.kernel.exps.Value;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.UserException;

public class JDBCExpressionFactory implements ExpressionFactory, Serializable {
   private static final Val NULL = new Null();
   private static final Val CURRENT_DATE = new CurrentDate(14);
   private static final Val CURRENT_TIME = new CurrentDate(1010);
   private static final Val CURRENT_TIMESTAMP = new CurrentDate(1011);
   private static final Localizer _loc = Localizer.forPackage(JDBCExpressionFactory.class);
   private final ClassMapping _type;
   private final SelectConstructor _cons = new SelectConstructor();
   private int _getMapValueAlias = 0;

   public JDBCExpressionFactory(ClassMapping type) {
      this._type = type;
   }

   public SelectConstructor getSelectConstructor() {
      return this._cons;
   }

   public Expression emptyExpression() {
      return new EmptyExpression();
   }

   public Expression asExpression(Value v) {
      return this.equal(v, this.newLiteral(Boolean.TRUE, 2));
   }

   public Expression equal(Value v1, Value v2) {
      if (v1 instanceof PCPath && ((PCPath)v1).isUnaccessedVariable()) {
         return this.contains(v1, v2);
      } else {
         return (Expression)(v2 instanceof PCPath && ((PCPath)v2).isUnaccessedVariable() ? this.contains(v2, v1) : new EqualExpression((Val)v1, (Val)v2));
      }
   }

   public Expression notEqual(Value v1, Value v2) {
      return new NotEqualExpression((Val)v1, (Val)v2);
   }

   public Expression lessThan(Value v1, Value v2) {
      return new CompareExpression((Val)v1, (Val)v2, "<");
   }

   public Expression greaterThan(Value v1, Value v2) {
      return new CompareExpression((Val)v1, (Val)v2, ">");
   }

   public Expression lessThanEqual(Value v1, Value v2) {
      return new CompareExpression((Val)v1, (Val)v2, "<=");
   }

   public Expression greaterThanEqual(Value v1, Value v2) {
      return new CompareExpression((Val)v1, (Val)v2, ">=");
   }

   public Expression isEmpty(Value val) {
      return new IsEmptyExpression((Val)val);
   }

   public Expression isNotEmpty(Value val) {
      return new IsNotEmptyExpression((Val)val);
   }

   public Expression contains(Value map, Value arg) {
      if (map instanceof Const) {
         return new InExpression((Val)arg, (Const)map);
      } else {
         return (Expression)(map instanceof SubQ ? new InSubQExpression((Val)arg, (SubQ)map) : new ContainsExpression((Val)map, (Val)arg));
      }
   }

   public Expression containsKey(Value map, Value arg) {
      return (Expression)(map instanceof Const ? new InKeyExpression((Val)arg, (Const)map) : new ContainsKeyExpression((Val)map, (Val)arg));
   }

   public Expression containsValue(Value map, Value arg) {
      return (Expression)(map instanceof Const ? new InValueExpression((Val)arg, (Const)map) : new ContainsExpression((Val)map, (Val)arg));
   }

   public Expression isInstance(Value val, Class c) {
      return (Expression)(val instanceof Const ? new ConstInstanceofExpression((Const)val, c) : new InstanceofExpression((PCPath)val, c));
   }

   public Expression and(Expression exp1, Expression exp2) {
      if (exp1 instanceof BindVariableExpression) {
         return new BindVariableAndExpression((BindVariableExpression)exp1, (Exp)exp2);
      } else {
         return (Expression)(exp2 instanceof BindVariableExpression ? new BindVariableAndExpression((BindVariableExpression)exp2, (Exp)exp1) : new AndExpression((Exp)exp1, (Exp)exp2));
      }
   }

   public Expression or(Expression exp1, Expression exp2) {
      return new OrExpression((Exp)exp1, (Exp)exp2);
   }

   public Expression not(Expression exp) {
      return (Expression)(HasContainsExpressionVisitor.hasContains(exp) ? new NotContainsExpression((Exp)exp) : new NotExpression((Exp)exp));
   }

   public Expression bindVariable(Value var, Value val) {
      if (val instanceof Const) {
         PCPath path = new PCPath(this._type, (Variable)var);
         path.setMetaData(var.getMetaData());
         return new InExpression(path, (Const)val);
      } else {
         return new BindVariableExpression((Variable)var, (PCPath)val, false);
      }
   }

   public Expression bindKeyVariable(Value var, Value val) {
      if (val instanceof Const) {
         PCPath path = new PCPath(this._type, (Variable)var);
         path.setMetaData(var.getMetaData());
         return new InKeyExpression(path, (Const)val);
      } else {
         return new BindVariableExpression((Variable)var, (PCPath)val, true);
      }
   }

   public Expression bindValueVariable(Value var, Value val) {
      return this.bindVariable(var, val);
   }

   public Expression startsWith(Value v1, Value v2) {
      return new StartsWithExpression((Val)v1, (Val)v2);
   }

   public Expression endsWith(Value v1, Value v2) {
      return new EndsWithExpression((Val)v1, (Val)v2);
   }

   public Expression notMatches(Value v1, Value v2, String single, String multi, String esc) {
      return this.not(this.matches(v1, v2, single, multi, esc));
   }

   public Expression matches(Value v1, Value v2, String single, String multi, String esc) {
      if (!(v2 instanceof Const)) {
         throw new UserException(_loc.get("const-only", (Object)"matches"));
      } else {
         return new MatchesExpression((Val)v1, (Const)v2, single, multi, esc != null ? esc : this._type.getMappingRepository().getDBDictionary().searchStringEscape);
      }
   }

   public Subquery newSubquery(ClassMetaData candidate, boolean subs, String alias) {
      DBDictionary dict = this._type.getMappingRepository().getDBDictionary();
      dict.assertSupport(dict.supportsSubselect, "SupportsSubselect");
      return new SubQ((ClassMapping)candidate, subs, alias);
   }

   public Path newPath() {
      return new PCPath(this._type);
   }

   public Path newPath(Value val) {
      if (val instanceof Const) {
         return new ConstPath((Const)val);
      } else {
         return val instanceof SubQ ? new PCPath((SubQ)val) : new PCPath(this._type, (Variable)val);
      }
   }

   public Literal newLiteral(Object val, int ptype) {
      return new Lit(val, ptype);
   }

   public Value getThis() {
      return new PCPath(this._type);
   }

   public Value getNull() {
      return NULL;
   }

   public Value getCurrentDate() {
      return CURRENT_DATE;
   }

   public Value getCurrentTime() {
      return CURRENT_TIME;
   }

   public Value getCurrentTimestamp() {
      return CURRENT_TIMESTAMP;
   }

   public Parameter newParameter(String name, Class type) {
      return new Param(name, type);
   }

   public Value newExtension(FilterListener listener, Value target, Value arg) {
      return new Extension((JDBCFilterListener)listener, (Val)target, (Val)arg, this._type);
   }

   public Value newAggregate(AggregateListener listener, Value arg) {
      return new Aggregate((JDBCAggregateListener)listener, (Val)arg, this._type);
   }

   public Arguments newArgumentList(Value v1, Value v2) {
      return new Args((Val)v1, (Val)v2);
   }

   public Value newUnboundVariable(String name, Class type) {
      return new Variable(name, type);
   }

   public Value newBoundVariable(String name, Class type) {
      return this.newUnboundVariable(name, type);
   }

   public Value cast(Value val, Class cls) {
      val.setImplicitType(cls);
      return val;
   }

   public Value add(Value v1, Value v2) {
      return new Math((Val)v1, (Val)v2, "+");
   }

   public Value subtract(Value v1, Value v2) {
      return new Math((Val)v1, (Val)v2, "-");
   }

   public Value multiply(Value v1, Value v2) {
      return new Math((Val)v1, (Val)v2, "*");
   }

   public Value divide(Value v1, Value v2) {
      return new Math((Val)v1, (Val)v2, "/");
   }

   public Value mod(Value v1, Value v2) {
      return new Math((Val)v1, (Val)v2, "MOD");
   }

   public Value abs(Value val) {
      return new Abs((Val)val);
   }

   public Value indexOf(Value v1, Value v2) {
      return new IndexOf((Val)v1, (Val)v2);
   }

   public Value concat(Value v1, Value v2) {
      return new Concat((Val)v1, (Val)v2);
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

   public Value substring(Value v1, Value v2) {
      return new Substring((Val)v1, (Val)v2);
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
      return (Value)(val instanceof Const ? new ConstGetObjectId((Const)val) : new GetObjectId((PCPath)val));
   }

   public Value getMapValue(Value map, Value arg) {
      return new GetMapValue((Val)map, (Val)arg, "gmv" + this._getMapValueAlias++);
   }
}
