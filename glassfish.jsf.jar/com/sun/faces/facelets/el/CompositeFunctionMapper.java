package com.sun.faces.facelets.el;

import java.lang.reflect.Method;
import javax.el.FunctionMapper;

public final class CompositeFunctionMapper extends FunctionMapper {
   private final FunctionMapper fn0;
   private final FunctionMapper fn1;

   public CompositeFunctionMapper(FunctionMapper fn0, FunctionMapper fn1) {
      this.fn0 = fn0;
      this.fn1 = fn1;
   }

   public Method resolveFunction(String prefix, String name) {
      Method m = this.fn0.resolveFunction(prefix, name);
      return m == null ? this.fn1.resolveFunction(prefix, name) : m;
   }
}
