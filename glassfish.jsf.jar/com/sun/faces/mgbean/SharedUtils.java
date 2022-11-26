package com.sun.faces.mgbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

class SharedUtils {
   static boolean isMixedExpression(String expression) {
      if (null == expression) {
         return false;
      } else {
         return (!expression.startsWith("#{") || !expression.endsWith("}")) && isExpression(expression);
      }
   }

   static boolean isExpression(String expression) {
      if (null == expression) {
         return false;
      } else {
         int start = expression.indexOf("#{");
         return start != -1 && expression.indexOf(125, start + 2) != -1;
      }
   }

   static Map evaluateExpressions(FacesContext context, Map map) {
      if (map != null && !map.isEmpty()) {
         Map ret = new HashMap(map.size());
         Iterator var3 = map.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            ret.put(entry.getKey(), evaluateExpressions(context, (List)entry.getValue()));
         }

         return ret;
      } else {
         return map;
      }
   }

   static List evaluateExpressions(FacesContext context, List values) {
      if (!values.isEmpty()) {
         List ret = new ArrayList(values.size());
         Application app = context.getApplication();
         Iterator var4 = values.iterator();

         while(var4.hasNext()) {
            String val = (String)var4.next();
            if (val != null) {
               String value = val.trim();
               if (isExpression(value)) {
                  value = (String)app.evaluateExpressionGet(context, value, String.class);
               }

               ret.add(value);
            }
         }

         return ret;
      } else {
         return values;
      }
   }
}
