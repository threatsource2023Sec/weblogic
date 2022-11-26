package kodo.beans;

public interface FieldAccessor {
   String getName();

   ClassAccessor getClassAccessor();

   Class getType();

   Object getValue(Object var1);

   void setValue(Object var1, Object var2);

   boolean isEditable(Object var1);

   boolean equals(Object var1);

   int hashCode();
}
