package com.oracle.weblogic.diagnostics.expressions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.el.FunctionMapper;
import javax.inject.Inject;
import weblogic.diagnostics.debug.DebugLogger;

public class DiagnosticsFunctionMapper extends FunctionMapper {
   @Inject
   private ExpressionExtensionsManager extensionsManager;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionFunctionMapper");
   private Map functionCache = new ConcurrentHashMap();
   private Annotation[] qualifiers;

   DiagnosticsFunctionMapper(Annotation... qualifiers) {
      this.qualifiers = qualifiers;
   }

   public Method resolveFunction(String prefix, String localName) {
      Method method = null;
      String namespace = ExpressionExtensionsManager.normalizeNamespace(prefix);
      if (namespace == null) {
         return method;
      } else {
         Map fnNamespace = (Map)this.functionCache.get(namespace);
         if (fnNamespace == null) {
            fnNamespace = new ConcurrentHashMap();
            this.functionCache.put(namespace, fnNamespace);
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Checking the method cache for " + prefix + ":" + localName);
         }

         method = (Method)((Map)fnNamespace).get(localName);
         if (method == null) {
            method = this.extensionsManager.lookupFunction(prefix, localName, this.qualifiers);
            if (method == null) {
               throw new FunctionExtensionNotFoundException(prefix, localName);
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Caching method for '" + prefix + ":" + localName + "': " + method.toString());
            }

            ((Map)fnNamespace).put(localName, method);
         }

         return method;
      }
   }
}
