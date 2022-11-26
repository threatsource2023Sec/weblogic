package weblogic.application.compiler;

import java.util.List;

public interface Factory {
   Comparable claim(Object var1);

   Comparable claim(Object var1, List var2);

   Object create(Object var1);
}
