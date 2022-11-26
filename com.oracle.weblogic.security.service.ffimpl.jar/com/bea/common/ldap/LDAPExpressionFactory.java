package com.bea.common.ldap;

import com.bea.common.ldap.exps.AndExpression;
import com.bea.common.ldap.exps.Any;
import com.bea.common.ldap.exps.EmptyExpression;
import com.bea.common.ldap.exps.EndsWithExpression;
import com.bea.common.ldap.exps.EqualExpression;
import com.bea.common.ldap.exps.ExpPath;
import com.bea.common.ldap.exps.GreaterThanEqualExpression;
import com.bea.common.ldap.exps.LDAPExpression;
import com.bea.common.ldap.exps.LessThanEqualExpression;
import com.bea.common.ldap.exps.Lit;
import com.bea.common.ldap.exps.MatchesExpression;
import com.bea.common.ldap.exps.NotExpression;
import com.bea.common.ldap.exps.Null;
import com.bea.common.ldap.exps.OrExpression;
import com.bea.common.ldap.exps.Param;
import com.bea.common.ldap.exps.StartsWithExpression;
import com.bea.common.ldap.exps.Val;
import com.bea.common.ldap.exps.ValueExpression;
import com.bea.common.ldap.exps.Variable;
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
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.meta.ClassMetaData;

public class LDAPExpressionFactory implements ExpressionFactory {
   private static final String HEADER = "LDAPExpressionFactory: ";
   private Log log;
   private LDAPStoreManager manager;
   private ClassMetaData type;

   public LDAPExpressionFactory(ClassMetaData type, LDAPStoreManager manager, Log log) {
      this.type = type;
      this.manager = manager;
      this.log = log;
   }

   public Expression emptyExpression() {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: emptyExpression()");
      }

      return new EmptyExpression();
   }

   public Expression asExpression(Value bool) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: asExpression(" + bool + ")");
      }

      return new ValueExpression((Val)bool);
   }

   public Expression equal(Value v1, Value v2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: equal(" + v1 + "," + v2 + ")");
      }

      return (Expression)(v2 instanceof Null ? this.notEqual(v1, new Any(this.manager)) : new EqualExpression((Val)v1, (Val)v2));
   }

   public Expression notEqual(Value v1, Value v2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: notEqual(" + v1 + "," + v2 + ")");
      }

      return (Expression)(v2 instanceof Null ? this.equal(v1, new Any(this.manager)) : new NotExpression(new EqualExpression((Val)v1, (Val)v2)));
   }

   public Expression lessThan(Value v1, Value v2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: lessThan(" + v1 + "," + v2 + ")");
      }

      return new AndExpression(new EqualExpression((Val)v1, new Any(this.manager)), new NotExpression(new GreaterThanEqualExpression((Val)v1, (Val)v2)));
   }

   public Expression greaterThan(Value v1, Value v2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: greaterThan(" + v1 + "," + v2 + ")");
      }

      return new AndExpression(new EqualExpression((Val)v1, new Any(this.manager)), new NotExpression(new LessThanEqualExpression((Val)v1, (Val)v2)));
   }

   public Expression lessThanEqual(Value v1, Value v2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: lessThanEqual(" + v1 + "," + v2 + ")");
      }

      return new LessThanEqualExpression((Val)v1, (Val)v2);
   }

   public Expression greaterThanEqual(Value v1, Value v2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: greaterThanEqual(" + v1 + "," + v2 + ")");
      }

      return new GreaterThanEqualExpression((Val)v1, (Val)v2);
   }

   public Expression isEmpty(Value target) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: isEmpty(" + target + ")");
      }

      return new NotExpression(new EqualExpression((Val)target, (Val)null));
   }

   public Expression isNotEmpty(Value target) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: isNotEmpty(" + target + ")");
      }

      return new EqualExpression((Val)target, (Val)null);
   }

   public Expression contains(Value coll, Value arg) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: contains(" + coll + "," + arg + ")");
      }

      return new EqualExpression((Val)coll, (Val)arg);
   }

   public Expression containsKey(Value map, Value arg) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: containsKey(" + map + "," + arg + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Expression containsValue(Value map, Value arg) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: containsValue(" + map + "," + arg + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Expression isInstance(Value obj, Class c) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: isInstance(" + obj + "," + c + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Expression and(Expression exp1, Expression exp2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: and(" + exp1 + "," + exp2 + ")");
      }

      return new AndExpression((LDAPExpression)exp1, (LDAPExpression)exp2);
   }

   public Expression or(Expression exp1, Expression exp2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: or(" + exp1 + "," + exp2 + ")");
      }

      return new OrExpression((LDAPExpression)exp1, (LDAPExpression)exp2);
   }

   public Expression not(Expression exp) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: not(" + exp + ")");
      }

      return new NotExpression((LDAPExpression)exp);
   }

   public Expression bindVariable(Value var, Value coll) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: bindVariable(" + var + "," + coll + ")");
      }

      Variable v = (Variable)var;
      v.setBound((ExpPath)coll);
      return new ValueExpression(v);
   }

   public Expression bindKeyVariable(Value var, Value map) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: bindKeyVariable(" + var + "," + map + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Expression bindValueVariable(Value var, Value map) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: bindValueVariable(" + var + "," + map + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Expression endsWith(Value str1, Value str2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: endsWith(" + str1 + "," + str2 + ")");
      }

      return new EndsWithExpression((Val)str1, (Val)str2);
   }

   public Expression matches(Value str, Value regexp, String single, String multi, String escape) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: matches(" + str + "," + regexp + "," + single + "," + multi + "," + escape + ")");
      }

      return new MatchesExpression((Val)(str instanceof Variable ? ((Variable)str).getBound() : (Val)str), (Val)regexp, single, multi, escape);
   }

   public Expression notMatches(Value str, Value regexp, String single, String multi, String escape) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: notMatches(" + str + "," + regexp + "," + single + "," + multi + "," + escape + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Expression startsWith(Value str1, Value str2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: startsWith(" + str1 + "," + str2 + ")");
      }

      return new StartsWithExpression((Val)str1, (Val)str2);
   }

   public Value stringLength(Value str) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: stringLength(" + str + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value trim(Value str, Value trimChar, Boolean where) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: trim(" + str + "," + trimChar + "," + where + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Subquery newSubquery(ClassMetaData candidate, boolean subs, String alias) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newSubquery(" + candidate + "," + subs + "," + alias + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Path newPath() {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newPath()");
      }

      return new ExpPath(this.type, this.log);
   }

   public Path newPath(Value val) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newPath(" + val + ")");
      }

      if (val instanceof Variable) {
         return (Variable)val;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public Literal newLiteral(Object val, int parseType) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newLiteral(" + val + "," + parseType + ")");
      }

      return new Lit(val, this.manager, parseType);
   }

   public Value getThis() {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: getThis()");
      }

      throw new UnsupportedOperationException();
   }

   public Value getNull() {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: getNull()");
      }

      return new Null(this.manager);
   }

   public Value getCurrentDate() {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: getCurrentDate()");
      }

      throw new UnsupportedOperationException();
   }

   public Value getCurrentTime() {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: getCurrentTime()");
      }

      throw new UnsupportedOperationException();
   }

   public Value getCurrentTimestamp() {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: getCurrentTimestamp()");
      }

      throw new UnsupportedOperationException();
   }

   public Parameter newParameter(String name, Class type) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newParameter(" + name + "," + type + ")");
      }

      return new Param(name, type, this.manager);
   }

   public Value newExtension(FilterListener listener, Value target, Value args) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newExtension(" + listener + "," + target + "," + args + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value newAggregate(AggregateListener listener, Value args) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newAggregate(" + listener + "," + args + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Arguments newArgumentList(Value arg1, Value arg2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newArgumentList(" + arg1 + "," + arg2 + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value newUnboundVariable(String name, Class type) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newUnboundVariable(" + name + "," + type + ")");
      }

      return new Variable(name, type, false);
   }

   public Value newBoundVariable(String name, Class type) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: newBoundVariable(" + name + "," + type + ")");
      }

      return new Variable(name, type, true);
   }

   public Value cast(Value obj, Class cls) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: cast(" + obj + "," + cls + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value add(Value num1, Value num2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: add(" + num1 + "," + num2 + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value subtract(Value num1, Value num2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: subtract(" + num1 + "," + num2 + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value multiply(Value num1, Value num2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: multiply(" + num1 + "," + num2 + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value divide(Value num1, Value num2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: divide(" + num1 + "," + num2 + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value mod(Value num1, Value num2) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: mod(" + num1 + "," + num2 + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value abs(Value num) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: abs(" + num + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value indexOf(Value str, Value args) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: indexOf(" + str + "," + args + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value concat(Value str, Value args) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: concat(" + str + "," + args + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value sqrt(Value num) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: add(" + num + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value substring(Value str, Value args) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: substring(" + str + "," + args + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value toUpperCase(Value str) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: toUpperCase(" + str + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value toLowerCase(Value str) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: toLowerCase(" + str + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value avg(Value num) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: avg(" + num + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value count(Value obj) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: count(" + obj + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value max(Value num) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: max(" + num + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value min(Value num) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: min(" + num + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value sum(Value num) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: sum(" + num + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value any(Value target) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: any(" + target + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value all(Value target) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: all(" + target + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value size(Value target) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: size(" + target + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value distinct(Value arg0) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: distinct(" + arg0 + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value getObjectId(Value val) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: getObjectId(" + val + ")");
      }

      throw new UnsupportedOperationException();
   }

   public Value getMapValue(Value map, Value arg) {
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPExpressionFactory: getMapValue(" + map + ", " + arg + ")");
      }

      throw new UnsupportedOperationException();
   }
}
