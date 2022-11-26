package javax.faces.el;

/** @deprecated */
public abstract class PropertyResolver {
   public abstract Object getValue(Object var1, Object var2) throws EvaluationException, PropertyNotFoundException;

   public abstract Object getValue(Object var1, int var2) throws EvaluationException, PropertyNotFoundException;

   public abstract void setValue(Object var1, Object var2, Object var3) throws EvaluationException, PropertyNotFoundException;

   public abstract void setValue(Object var1, int var2, Object var3) throws EvaluationException, PropertyNotFoundException;

   public abstract boolean isReadOnly(Object var1, Object var2) throws EvaluationException, PropertyNotFoundException;

   public abstract boolean isReadOnly(Object var1, int var2) throws EvaluationException, PropertyNotFoundException;

   public abstract Class getType(Object var1, Object var2) throws EvaluationException, PropertyNotFoundException;

   public abstract Class getType(Object var1, int var2) throws EvaluationException, PropertyNotFoundException;
}
