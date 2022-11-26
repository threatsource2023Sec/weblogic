package weblogic.ejb.spi;

import java.io.File;
import weblogic.ejb.container.ejbc.EJBCompiler;
import weblogic.utils.Getopt2;

public final class EJBCFactory {
   public static EJBC createEJBC(Getopt2 opts, File moduleRootDir) {
      return new EJBCompiler(opts, moduleRootDir);
   }
}
