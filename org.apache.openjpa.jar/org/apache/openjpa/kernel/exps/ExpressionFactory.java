package org.apache.openjpa.kernel.exps;

import org.apache.openjpa.meta.ClassMetaData;

public interface ExpressionFactory {
   Expression emptyExpression();

   Expression asExpression(Value var1);

   Expression equal(Value var1, Value var2);

   Expression notEqual(Value var1, Value var2);

   Expression lessThan(Value var1, Value var2);

   Expression greaterThan(Value var1, Value var2);

   Expression lessThanEqual(Value var1, Value var2);

   Expression greaterThanEqual(Value var1, Value var2);

   Expression isEmpty(Value var1);

   Expression isNotEmpty(Value var1);

   Expression contains(Value var1, Value var2);

   Expression containsKey(Value var1, Value var2);

   Expression containsValue(Value var1, Value var2);

   Value getMapValue(Value var1, Value var2);

   Expression isInstance(Value var1, Class var2);

   Expression and(Expression var1, Expression var2);

   Expression or(Expression var1, Expression var2);

   Expression not(Expression var1);

   Expression bindVariable(Value var1, Value var2);

   Expression bindKeyVariable(Value var1, Value var2);

   Expression bindValueVariable(Value var1, Value var2);

   Expression endsWith(Value var1, Value var2);

   Expression matches(Value var1, Value var2, String var3, String var4, String var5);

   Expression notMatches(Value var1, Value var2, String var3, String var4, String var5);

   Expression startsWith(Value var1, Value var2);

   Value stringLength(Value var1);

   Value trim(Value var1, Value var2, Boolean var3);

   Subquery newSubquery(ClassMetaData var1, boolean var2, String var3);

   Path newPath();

   Path newPath(Value var1);

   Literal newLiteral(Object var1, int var2);

   Value getThis();

   Value getNull();

   Value getCurrentDate();

   Value getCurrentTime();

   Value getCurrentTimestamp();

   Parameter newParameter(String var1, Class var2);

   Value newExtension(FilterListener var1, Value var2, Value var3);

   Value newAggregate(AggregateListener var1, Value var2);

   Arguments newArgumentList(Value var1, Value var2);

   Value newUnboundVariable(String var1, Class var2);

   Value newBoundVariable(String var1, Class var2);

   Value cast(Value var1, Class var2);

   Value add(Value var1, Value var2);

   Value subtract(Value var1, Value var2);

   Value multiply(Value var1, Value var2);

   Value divide(Value var1, Value var2);

   Value mod(Value var1, Value var2);

   Value abs(Value var1);

   Value indexOf(Value var1, Value var2);

   Value concat(Value var1, Value var2);

   Value sqrt(Value var1);

   Value substring(Value var1, Value var2);

   Value toUpperCase(Value var1);

   Value toLowerCase(Value var1);

   Value avg(Value var1);

   Value count(Value var1);

   Value max(Value var1);

   Value min(Value var1);

   Value sum(Value var1);

   Value any(Value var1);

   Value all(Value var1);

   Value size(Value var1);

   Value distinct(Value var1);

   Value getObjectId(Value var1);
}
