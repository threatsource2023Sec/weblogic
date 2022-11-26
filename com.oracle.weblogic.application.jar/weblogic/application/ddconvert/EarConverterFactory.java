package weblogic.application.ddconvert;

import java.io.File;
import java.io.IOException;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.utils.EarUtils;
import weblogic.utils.jars.VirtualJarFile;

public final class EarConverterFactory implements ConverterFactory {
   public ModuleType identifyType(VirtualJarFile vjar) {
      File[] dir = vjar.getRootFiles();
      if (dir != null) {
         for(int i = 0; i < dir.length; ++i) {
            if (EarUtils.isEar(dir[i])) {
               return ModuleType.EAR;
            }
         }
      }

      return null;
   }

   public Converter newConverter(ModuleType type) throws DDConvertException, IOException {
      return type == ModuleType.EAR ? new EarConverter() : null;
   }

   public ModuleType identifyType(ApplicationArchive archive) {
      return null;
   }
}
