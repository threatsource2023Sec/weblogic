package weblogic.store.io.file.checksum;

import weblogic.store.common.StoreDebug;

public class ChecksummerFactory {
   private static Checksummer checksummer = instantiateChecksummer();

   private static Checksummer instantiateChecksummer() {
      Checksummer checksummer = null;

      try {
         checksummer = new FaFChecksummer();
         StoreDebug.storeIOPhysical.debug("> Using weblogic.store.io.file.checksum.FaFChecksummer in ChecksummerFactory.instantiateChecksummer");
      } catch (Throwable var4) {
         try {
            checksummer = new NIOAdler32Checksummer();
            StoreDebug.storeIOPhysical.debug("> Using weblogic.store.io.file.checksum.NIOAdler32Checksummer in ChecksummerFactory.instantiateChecksummer");
         } catch (Throwable var3) {
            checksummer = new Adler32Checksummer();
            StoreDebug.storeIOPhysical.debug("> Using weblogic.store.io.file.checksum.Adler32Checksummer in ChecksummerFactory.instantiateChecksummer");
         }
      }

      return (Checksummer)checksummer;
   }

   public static Checksummer getInstance() {
      return checksummer;
   }

   public static Checksummer getNewInstance() {
      return instantiateChecksummer();
   }
}
