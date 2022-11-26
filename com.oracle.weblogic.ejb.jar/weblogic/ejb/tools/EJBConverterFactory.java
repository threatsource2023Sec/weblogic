package weblogic.ejb.tools;

import java.io.File;
import java.io.IOException;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.ddconvert.Converter;
import weblogic.application.ddconvert.ConverterFactory;
import weblogic.j2ee.J2EEUtils;
import weblogic.utils.jars.VirtualJarFile;

public final class EJBConverterFactory implements ConverterFactory {
   public ModuleType identifyType(VirtualJarFile vjar) throws IOException {
      File[] dirs = vjar.getRootFiles();
      if (dirs != null) {
         File[] var3 = dirs;
         int var4 = dirs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File dir = var3[var5];
            if (J2EEUtils.isEJB(dir)) {
               return ModuleType.EJB;
            }
         }
      }

      return null;
   }

   public ModuleType identifyType(ApplicationArchive archive) {
      return null;
   }

   public Converter newConverter(ModuleType type) {
      return type != ModuleType.EJB && type != ModuleType.WAR ? null : new EJBConverter(type);
   }
}
