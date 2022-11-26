package weblogic.elasticity.util;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import weblogic.elasticity.interceptor.ScriptAndCommandUtil;

public class DumpEnvironment {
   public static void main(String[] args) throws Exception {
      Map dataMap = new HashMap();
      Iterator var2 = System.getenv().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         String key = (String)entry.getKey();
         if (key.startsWith("WLS_SCRIPT_") && !ScriptAndCommandUtil.getReservedScriptEnvNames().contains(key)) {
            dataMap.put(key, entry.getValue());
         }
      }

      String fileName = "WLS_SCRIPT_OUTPUT_FILE";
      FileWriter fw = new FileWriter(System.getenv(fileName));
      Throwable var17 = null;

      try {
         Properties props = new Properties();
         props.putAll(dataMap);
         props.store(fw, "#Shared Data");
      } catch (Throwable var13) {
         var17 = var13;
         throw var13;
      } finally {
         if (fw != null) {
            if (var17 != null) {
               try {
                  fw.close();
               } catch (Throwable var12) {
                  var17.addSuppressed(var12);
               }
            } else {
               fw.close();
            }
         }

      }

   }
}
