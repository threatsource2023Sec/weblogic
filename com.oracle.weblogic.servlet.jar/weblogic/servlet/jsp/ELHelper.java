package weblogic.servlet.jsp;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

public class ELHelper {
   public static Object evaluate(String expression, Class expectedType, PageContext pageContext, FunctionMapper functionMapper) {
      ValueExpression valueExpr = createValueExpression(expression, expectedType, pageContext, functionMapper);
      return valueExpr.getValue(pageContext.getELContext());
   }

   public static ValueExpression createValueExpression(String expression, Class expectedType, PageContext pageContext, FunctionMapper functionMapper) {
      ExpressionFactory exprFactory = getExpressionFactory(pageContext);
      if (expression.length() == 0) {
         return exprFactory.createValueExpression(expression, expectedType);
      } else {
         ELContextImpl elContext = (ELContextImpl)pageContext.getELContext();
         elContext.setFunctionMapper(functionMapper);
         return exprFactory.createValueExpression(elContext, expression, expectedType);
      }
   }

   public static MethodExpression createMethodExpression(String expression, Class expectedType, Class[] paramTypes, PageContext pageContext, FunctionMapper functionMapper) {
      ExpressionFactory exprFactory = getExpressionFactory(pageContext);
      ELContextImpl elContext = (ELContextImpl)pageContext.getELContext();
      elContext.setFunctionMapper(functionMapper);
      return exprFactory.createMethodExpression(elContext, expression, expectedType, paramTypes);
   }

   public static void mapValueExpression(PageContext pageContext, String variable, ValueExpression expression) {
      ELContext elContext = pageContext.getELContext();
      elContext.getVariableMapper().setVariable(variable, expression);
   }

   public static void mapMethodExpression(PageContext pageContext, String variable, MethodExpression expression) {
      ExpressionFactory exprFactory = getExpressionFactory(pageContext);
      ValueExpression valExpression = exprFactory.createValueExpression(expression, Object.class);
      mapValueExpression(pageContext, variable, valExpression);
   }

   private static ExpressionFactory getExpressionFactory(PageContext pageContext) {
      JspApplicationContext jaFactory = ELHelper.Holder.jspFactory.getJspApplicationContext(pageContext.getServletContext());
      return jaFactory.getExpressionFactory();
   }

   private static class Holder {
      static JspFactory jspFactory = JspFactory.getDefaultFactory();
   }
}
