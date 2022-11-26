package org.python.google.common.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.python.google.common.base.Preconditions;

abstract class TypeCapture {
   final Type capture() {
      Type superclass = this.getClass().getGenericSuperclass();
      Preconditions.checkArgument(superclass instanceof ParameterizedType, "%s isn't parameterized", (Object)superclass);
      return ((ParameterizedType)superclass).getActualTypeArguments()[0];
   }
}
