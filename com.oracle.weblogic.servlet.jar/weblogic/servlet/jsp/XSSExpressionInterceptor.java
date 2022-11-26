package weblogic.servlet.jsp;

import javax.servlet.jsp.PageContext;
import weblogic.servlet.security.Utils;

public class XSSExpressionInterceptor implements ExpressionInterceptor {
   public String intercept(String value, PageContext pageContext, ExpressionInterceptor.Type type) {
      return Utils.encodeXSS(value);
   }
}
