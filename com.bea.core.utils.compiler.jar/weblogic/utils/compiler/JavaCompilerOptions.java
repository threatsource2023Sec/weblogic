package weblogic.utils.compiler;

import java.util.Map;

public interface JavaCompilerOptions {
   Map getOptionsMap();

   void add(String var1, String var2);
}
