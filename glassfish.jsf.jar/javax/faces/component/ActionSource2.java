package javax.faces.component;

import javax.el.MethodExpression;

public interface ActionSource2 extends ActionSource {
   MethodExpression getActionExpression();

   void setActionExpression(MethodExpression var1);
}
