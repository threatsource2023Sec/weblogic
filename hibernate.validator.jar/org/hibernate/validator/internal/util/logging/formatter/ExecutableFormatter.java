package org.hibernate.validator.internal.util.logging.formatter;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import org.hibernate.validator.internal.util.ExecutableHelper;

public class ExecutableFormatter {
   private final String stringRepresentation;

   public ExecutableFormatter(Executable executable) {
      String name = ExecutableHelper.getSimpleName(executable);
      if (executable instanceof Method) {
         name = executable.getDeclaringClass().getSimpleName() + "#" + name;
      }

      Class[] parameterTypes = executable.getParameterTypes();
      this.stringRepresentation = ExecutableHelper.getExecutableAsString(name, parameterTypes);
   }

   public String toString() {
      return this.stringRepresentation;
   }
}
