package weblogic.connector.tools;

import java.io.File;
import java.io.IOException;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.ddconvert.Converter;
import weblogic.application.ddconvert.ConverterFactory;
import weblogic.j2ee.J2EEUtils;
import weblogic.utils.jars.VirtualJarFile;

public final class RarConverterFactory implements ConverterFactory {
   public ModuleType identifyType(VirtualJarFile vjar) throws IOException {
      File[] dir = vjar.getRootFiles();
      if (dir != null) {
         for(int i = 0; i < dir.length; ++i) {
            if (J2EEUtils.isRar(dir[i])) {
               return ModuleType.RAR;
            }
         }
      }

      return null;
   }

   public Converter newConverter(ModuleType type) {
      return type == ModuleType.RAR ? new RarConverter() : null;
   }

   public ModuleType identifyType(ApplicationArchive archive) {
      return null;
   }
}
