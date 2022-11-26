package com.sun.faces.el;

import javax.el.CompositeELResolver;
import javax.el.ELResolver;

public abstract class FacesCompositeELResolver extends CompositeELResolver {
   public abstract ELResolverChainType getChainType();

   public abstract void addRootELResolver(ELResolver var1);

   public abstract void addPropertyELResolver(ELResolver var1);

   public static enum ELResolverChainType {
      JSP,
      Faces;
   }
}
