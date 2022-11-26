package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import java.util.Objects;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.StandardELContext;
import org.apache.tools.ant.PropertyHelper;

public abstract class AbstractJavaxElPropertyEvaluator implements PropertyHelper.PropertyEvaluator {
   protected final ELContext elContext;
   protected final ExpressionFactory expressionFactory;

   protected AbstractJavaxElPropertyEvaluator(ExpressionFactory expressionFactory, ELContext elContext) {
      Objects.requireNonNull(expressionFactory, "expressionFactory == null");
      Objects.requireNonNull(elContext, "elContext == null");
      this.expressionFactory = expressionFactory;
      this.elContext = elContext;
   }

   protected AbstractJavaxElPropertyEvaluator() {
      this.expressionFactory = ExpressionFactory.newInstance();

      assert this.expressionFactory != null;

      this.elContext = new StandardELContext(this.expressionFactory);
   }
}
