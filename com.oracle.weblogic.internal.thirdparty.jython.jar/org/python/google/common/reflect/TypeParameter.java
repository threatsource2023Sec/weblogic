package org.python.google.common.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.base.Preconditions;

@Beta
public abstract class TypeParameter extends TypeCapture {
   final TypeVariable typeVariable;

   protected TypeParameter() {
      Type type = this.capture();
      Preconditions.checkArgument(type instanceof TypeVariable, "%s should be a type variable.", (Object)type);
      this.typeVariable = (TypeVariable)type;
   }

   public final int hashCode() {
      return this.typeVariable.hashCode();
   }

   public final boolean equals(@Nullable Object o) {
      if (o instanceof TypeParameter) {
         TypeParameter that = (TypeParameter)o;
         return this.typeVariable.equals(that.typeVariable);
      } else {
         return false;
      }
   }

   public String toString() {
      return this.typeVariable.toString();
   }
}
