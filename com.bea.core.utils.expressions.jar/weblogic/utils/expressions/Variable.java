package weblogic.utils.expressions;

public interface Variable {
   Object get(Object var1) throws ExpressionEvaluationException;

   Expression.Type getType();
}
