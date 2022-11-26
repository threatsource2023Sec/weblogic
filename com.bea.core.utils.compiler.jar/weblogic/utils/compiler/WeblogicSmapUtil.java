package weblogic.utils.compiler;

public class WeblogicSmapUtil {
   public static void installSmap(byte[] classBuffer, String smap, String outFileName) {
      try {
         SourceDebugExtensionInstaller.installSmap(classBuffer, smap == null ? null : smap.getBytes(), outFileName);
      } catch (Exception var4) {
      }

   }
}
