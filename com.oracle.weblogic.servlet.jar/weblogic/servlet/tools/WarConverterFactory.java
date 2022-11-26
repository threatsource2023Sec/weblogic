package weblogic.servlet.tools;

import java.io.File;
import java.io.IOException;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.ddconvert.Converter;
import weblogic.application.ddconvert.ConverterFactory;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.jars.VirtualJarFile;

public final class WarConverterFactory implements ConverterFactory {
   public ModuleType identifyType(VirtualJarFile vjar) throws IOException {
      File[] dir = vjar.getRootFiles();
      if (dir != null) {
         for(int i = 0; i < dir.length; ++i) {
            if (WarUtils.isPre15War(dir[i])) {
               return ModuleType.WAR;
            }
         }
      }

      return null;
   }

   public Converter newConverter(ModuleType type) {
      return type == ModuleType.WAR ? new WarConverter() : null;
   }

   public ModuleType identifyType(ApplicationArchive archive) {
      return null;
   }
}
