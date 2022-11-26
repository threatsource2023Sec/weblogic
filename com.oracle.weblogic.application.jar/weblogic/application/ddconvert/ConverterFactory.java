package weblogic.application.ddconvert;

import java.io.IOException;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.archive.ApplicationArchive;
import weblogic.utils.jars.VirtualJarFile;

public interface ConverterFactory {
   ModuleType identifyType(VirtualJarFile var1) throws IOException;

   /** @deprecated */
   @Deprecated
   ModuleType identifyType(ApplicationArchive var1);

   Converter newConverter(ModuleType var1) throws DDConvertException, IOException;
}
