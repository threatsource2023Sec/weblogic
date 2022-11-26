package com.sun.faces.config.initfacescontext;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

public class NoOpELContext extends ELContext {
   public ELResolver getELResolver() {
      return null;
   }

   public FunctionMapper getFunctionMapper() {
      return null;
   }

   public VariableMapper getVariableMapper() {
      return null;
   }
}
