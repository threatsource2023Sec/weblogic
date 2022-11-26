package com.bea.security.providers.xacml.entitlement.p13n;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Expression;
import com.bea.security.xacml.entitlement.ExpressionConverter;
import com.bea.security.xacml.entitlement.SimplificationException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Set;

public class P13NExpressionConverter implements ExpressionConverter {
   private static final String P13N_EXPRESSION_WRAPPER_CLASS = "com.bea.p13n.entitlements.rules.internal.ExpressionWrapper";

   private ExpressionConverter getConverter() {
      ExpressionConverter converter = null;

      try {
         PrivilegedAction getThreadCCLAction = new PrivilegedAction() {
            public ClassLoader run() {
               return Thread.currentThread().getContextClassLoader();
            }
         };
         ClassLoader threadCCL = (ClassLoader)AccessController.doPrivileged(getThreadCCLAction);
         converter = (ExpressionConverter)Class.forName("com.bea.p13n.entitlements.rules.internal.ExpressionWrapper", true, threadCCL).newInstance();
      } catch (ClassNotFoundException var4) {
      } catch (InstantiationException var5) {
      } catch (IllegalAccessException var6) {
      } catch (NoClassDefFoundError var7) {
      }

      return converter;
   }

   public Expression convert(String expression, Set vdefs) throws DocumentParseException, SimplificationException, URISyntaxException {
      ExpressionConverter converter = this.getConverter();
      return converter != null ? converter.convert(expression, vdefs) : null;
   }

   public boolean isPortal() {
      return this.getConverter() != null;
   }
}
