package weblogic.application.compiler.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.internal.ModuleContextImpl;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.JavaEEModuleNameBean;

public class ContextUtils {
   public static String ORIGINAL_DESCRIPTOR_SUFFIX = ".orig";

   public static boolean isSplitDir(ToolsContext ctx) {
      return ctx != null && ctx.getApplicationFileManager() != null ? ctx.getApplicationFileManager().isSplitDirectory() : false;
   }

   public static void updateModuleContexts(Map m, Set assignedNames) {
      Map.Entry e;
      String tentativeModuleName;
      for(Iterator var2 = m.entrySet().iterator(); var2.hasNext(); ((ModuleContextImpl)e.getKey()).setName(uniqueName(assignedNames, tentativeModuleName))) {
         e = (Map.Entry)var2.next();
         tentativeModuleName = null;
         DescriptorBean[] var5 = (DescriptorBean[])e.getValue();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            DescriptorBean db = var5[var7];
            if (db instanceof JavaEEModuleNameBean) {
               tentativeModuleName = ((JavaEEModuleNameBean)db).getJavaEEModuleName();
               if (tentativeModuleName != null) {
                  break;
               }
            }
         }

         if (tentativeModuleName == null) {
            tentativeModuleName = getDefaultModuleName(((ModuleContextImpl)e.getKey()).getURI());
         }
      }

   }

   private static String getDefaultModuleName(String relativeURI) {
      while(relativeURI.length() > 1 && relativeURI.charAt(relativeURI.length() - 1) == '/') {
         relativeURI = relativeURI.substring(0, relativeURI.length() - 1);
      }

      int suffixIdx = relativeURI.lastIndexOf(46);
      if (suffixIdx > -1) {
         int separatorIdx = relativeURI.lastIndexOf(47);
         if (suffixIdx > separatorIdx) {
            return stripSuffix1(relativeURI, suffixIdx, ".*\\.[rwj]ar$");
         } else {
            return relativeURI;
         }
      } else {
         return relativeURI;
      }
   }

   private static String stripSuffix1(String relativeURI, int suffixIdx, String regex) {
      return suffixIdx == relativeURI.length() - 4 && relativeURI.matches(regex) ? relativeURI.substring(0, relativeURI.length() - 4) : relativeURI;
   }

   private static String uniqueName(Set names, String moduleName) {
      String uniqueName = moduleName;

      for(int index = 1; names.contains(uniqueName); uniqueName = moduleName + "_" + index++) {
      }

      names.add(uniqueName);
      return uniqueName;
   }
}
