package weblogic.servlet.jsp;

import javax.servlet.jsp.PageContext;

public interface ExpressionInterceptor {
   String intercept(String var1, PageContext var2, Type var3);

   public static enum Type {
      Scripting,
      EL;
   }
}
