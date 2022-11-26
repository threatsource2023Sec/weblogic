package com.bea.security.providers.xacml.entitlement.p13n;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.JavaObjectAttribute;
import com.bea.security.providers.xacml.ContextConverter;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.entitlement.ContextUnifier;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import weblogic.security.service.ContextHandler;

public class ContextConverterFactory implements com.bea.security.providers.xacml.ContextConverterFactory {
   private static final String P13N_CONTEXTUNIFIER_WRAPPER_CLASS = "com.bea.p13n.entitlements.rules.internal.ContextUnifierWrapper";

   private ContextUnifier getUnifier() {
      ContextUnifier contextUnifier = null;

      try {
         PrivilegedAction getThreadCCLAction = new PrivilegedAction() {
            public ClassLoader run() {
               return Thread.currentThread().getContextClassLoader();
            }
         };
         ClassLoader threadCCL = (ClassLoader)AccessController.doPrivileged(getThreadCCLAction);
         contextUnifier = (ContextUnifier)Class.forName("com.bea.p13n.entitlements.rules.internal.ContextUnifierWrapper", true, threadCCL).newInstance();
      } catch (ClassNotFoundException var4) {
      } catch (InstantiationException var5) {
      } catch (IllegalAccessException var6) {
      } catch (NoClassDefFoundError var7) {
      }

      return contextUnifier;
   }

   public boolean isPortal() {
      return this.getUnifier() != null;
   }

   public ContextConverter getConverter(final ContextHandler context) {
      return context != null ? new ContextConverter() {
         private Map portalUnification;

         private JavaObjectAttribute getPortalUnification(String key) {
            if (this.portalUnification == null) {
               this.portalUnification = new HashMap();
               ContextUnifier contextUnifier = ContextConverterFactory.this.getUnifier();
               if (contextUnifier != null) {
                  contextUnifier.unifyContext(context, this.portalUnification);
               }
            }

            return (JavaObjectAttribute)this.portalUnification.get(key);
         }

         public JavaObjectAttribute getContextValue(String key) throws IndeterminateEvaluationException {
            try {
               String eKey = URLDecoder.decode(key, "UTF-8");
               if (eKey.startsWith("portal:")) {
                  return this.getPortalUnification(eKey.substring("portal:".length()));
               } else {
                  Object o = context.getValue(eKey);
                  return o != null ? new JavaObjectAttribute(o) : null;
               }
            } catch (UnsupportedEncodingException var4) {
               throw new IndeterminateEvaluationException(var4);
            }
         }

         public AttributeEvaluator getEvaluator(URI type, URI id, String issuer) {
            return null;
         }
      } : new ContextConverter() {
         public JavaObjectAttribute getContextValue(String key) {
            return null;
         }

         public AttributeEvaluator getEvaluator(URI type, URI id, String issuer) {
            return null;
         }
      };
   }
}
