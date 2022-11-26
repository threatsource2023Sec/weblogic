package com.oracle.cmm.lowertier.el;

import com.oracle.cmm.lowertier.gathering.ProcVmstat;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.FunctionMapper;

public class ELFunctionMapperImpl extends FunctionMapper {
   private static final Logger LOGGER = Logger.getLogger(ProcVmstat.class.getPackage().getName());
   private HashMap supportedFunctions = null;

   public ELFunctionMapperImpl() {
      this.initializeSupportedFunctions();
   }

   public Method resolveFunction(String prefix, String localName) {
      if (localName != null && localName.length() != 0) {
         if (prefix == null || prefix.length() == 0 || "Math".equalsIgnoreCase(prefix)) {
            prefix = "java.lang.Math";
         }

         Map methods = (Map)this.supportedFunctions.get(prefix);
         return methods == null ? null : (Method)methods.get(localName);
      } else {
         return null;
      }
   }

   private void initializeSupportedFunctions() {
      this.supportedFunctions = new HashMap();
      HashMap mathMap = new HashMap();

      try {
         mathMap.put("abs", Math.class.getMethod("abs", Double.TYPE));
         mathMap.put("acos", Math.class.getMethod("acos", Double.TYPE));
         mathMap.put("asin", Math.class.getMethod("asin", Double.TYPE));
         mathMap.put("atan", Math.class.getMethod("atan", Double.TYPE));
         mathMap.put("atan2", Math.class.getMethod("atan2", Double.TYPE, Double.TYPE));
         mathMap.put("cbrt", Math.class.getMethod("cbrt", Double.TYPE));
         mathMap.put("ceil", Math.class.getMethod("ceil", Double.TYPE));
         mathMap.put("cos", Math.class.getMethod("cos", Double.TYPE));
         mathMap.put("cosh", Math.class.getMethod("cosh", Double.TYPE));
         mathMap.put("exp", Math.class.getMethod("exp", Double.TYPE));
         mathMap.put("expm1", Math.class.getMethod("expm1", Double.TYPE));
         mathMap.put("getExponent", Math.class.getMethod("getExponent", Double.TYPE));
         mathMap.put("hypot", Math.class.getMethod("hypot", Double.TYPE, Double.TYPE));
         mathMap.put("log", Math.class.getMethod("log", Double.TYPE));
         mathMap.put("log10", Math.class.getMethod("log10", Double.TYPE));
         mathMap.put("log1p", Math.class.getMethod("log1p", Double.TYPE));
         mathMap.put("max", Math.class.getMethod("max", Double.TYPE, Double.TYPE));
         mathMap.put("min", Math.class.getMethod("min", Double.TYPE, Double.TYPE));
         mathMap.put("nextAfter", Math.class.getMethod("nextAfter", Double.TYPE, Double.TYPE));
         mathMap.put("nextUp", Math.class.getMethod("nextUp", Double.TYPE));
         mathMap.put("pow", Math.class.getMethod("pow", Double.TYPE, Double.TYPE));
         mathMap.put("random", Math.class.getMethod("random"));
         mathMap.put("rint", Math.class.getMethod("rint", Double.TYPE));
         mathMap.put("round", Math.class.getMethod("round", Double.TYPE));
         mathMap.put("signum", Math.class.getMethod("signum", Double.TYPE));
         mathMap.put("sin", Math.class.getMethod("sin", Double.TYPE));
         mathMap.put("sinh", Math.class.getMethod("sinh", Double.TYPE));
         mathMap.put("sqrt", Math.class.getMethod("sqrt", Double.TYPE));
         mathMap.put("tan", Math.class.getMethod("tan", Double.TYPE));
         mathMap.put("tanh", Math.class.getMethod("tanh", Double.TYPE));
         mathMap.put("toDegrees", Math.class.getMethod("toDegrees", Double.TYPE));
         mathMap.put("toRadians", Math.class.getMethod("toRadians", Double.TYPE));
         mathMap.put("ulp", Math.class.getMethod("ulp", Double.TYPE));
      } catch (NoSuchMethodException var3) {
         if (LOGGER.isLoggable(Level.FINER)) {
            var3.printStackTrace();
         }
      }

      this.supportedFunctions.put("java.lang.Math", mathMap);
   }
}
