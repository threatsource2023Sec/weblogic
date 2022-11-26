package javax.faces.flow.builder;

import javax.el.ValueExpression;

public abstract class FlowCallBuilder implements NodeBuilder {
   public abstract FlowCallBuilder flowReference(String var1, String var2);

   public abstract FlowCallBuilder outboundParameter(String var1, ValueExpression var2);

   public abstract FlowCallBuilder outboundParameter(String var1, String var2);

   public abstract FlowCallBuilder markAsStartNode();
}
