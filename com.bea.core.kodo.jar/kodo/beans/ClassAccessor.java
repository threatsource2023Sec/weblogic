package kodo.beans;

public interface ClassAccessor {
   Class getType();

   FieldAccessor[] getFieldAccessors();

   Object newInstance();

   boolean equals(Object var1);

   int hashCode();
}
