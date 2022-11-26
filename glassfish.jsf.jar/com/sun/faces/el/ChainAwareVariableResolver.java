package com.sun.faces.el;

import com.sun.faces.util.RequestStateManager;
import javax.el.ELResolver;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;

public class ChainAwareVariableResolver extends VariableResolver {
   public Object resolveVariable(FacesContext context, String name) throws EvaluationException {
      Object result = null;
      FacesCompositeELResolver.ELResolverChainType type = FacesCompositeELResolver.ELResolverChainType.Faces;
      Object valueObject = RequestStateManager.get(context, "com.sun.faces.ELResolverChainType");
      if (null != valueObject && valueObject instanceof FacesCompositeELResolver.ELResolverChainType) {
         type = (FacesCompositeELResolver.ELResolverChainType)valueObject;
      }

      if (FacesCompositeELResolver.ELResolverChainType.JSP == type) {
         ValueExpression ve = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), "#{" + name + "}", Object.class);
         result = ve.getValue(context.getELContext());
      } else if (FacesCompositeELResolver.ELResolverChainType.Faces == type) {
         ELResolver elr = context.getApplication().getELResolver();
         result = elr.getValue(context.getELContext(), (Object)null, name);
      }

      return result;
   }
}
