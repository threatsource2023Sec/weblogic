package weblogic.cache.tag;

import java.util.Vector;
import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;
import weblogic.cache.KeyEnumerator;

public class CacheTagInfo extends TagExtraInfo {
   public VariableInfo[] getVariableInfo(TagData data) {
      Vector infos = new Vector();
      String flush = data.getAttributeString("flush");
      if (flush != null) {
         return new VariableInfo[0];
      } else {
         String key = data.getAttributeString("key");
         String name;
         VariableInfo info;
         if (key != null) {
            KeyEnumerator keys = new KeyEnumerator(key);

            while(keys.hasMoreKeys()) {
               name = keys.getNextKey();
               info = new VariableInfo(name, "java.lang.Object", true, 0);
               infos.addElement(info);
            }
         }

         String vars = data.getAttributeString("vars");
         if (vars != null) {
            KeyEnumerator keys = new KeyEnumerator(vars);

            while(keys.hasMoreKeys()) {
               String keyId = keys.getNextKey();
               VariableInfo info = new VariableInfo(keyId, "java.lang.Object", true, 2);
               infos.addElement(info);
            }
         }

         name = data.getAttributeString("name");
         if (name != null) {
            info = new VariableInfo(name, "weblogic.cache.CacheValue", true, 1);
            infos.addElement(info);
         }

         VariableInfo[] variableInfos = new VariableInfo[infos.size()];
         infos.toArray(variableInfos);
         return variableInfos;
      }
   }

   public boolean isValid(TagData data) {
      String key = data.getAttributeString("key");
      if (key != null) {
         KeyEnumerator keys = new KeyEnumerator(key);

         while(keys.hasMoreKeys()) {
            keys.getNextKey();
         }
      }

      String vars = data.getAttributeString("vars");
      if (vars != null) {
         KeyEnumerator keys = new KeyEnumerator(vars);

         while(keys.hasMoreKeys()) {
            keys.getNextKey();
         }
      }

      String scope = data.getAttributeString("scope");
      if (scope == null) {
         return true;
      } else {
         return scope.equals("parameter") || scope.equals("request") || scope.equals("session") || scope.equals("application") || scope.equals("file") || scope.equals("cluster");
      }
   }
}
