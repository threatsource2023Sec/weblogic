package javax.faces.flow;

import javax.el.ValueExpression;

public abstract class Parameter {
   public abstract String getName();

   public abstract ValueExpression getValue();
}
