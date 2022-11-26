package weblogic.application.utils;

import java.io.FileFilter;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ApplicationHashService {
   ApplicationHasher createApplicationHasher();

   ApplicationHasher createApplicationHasher(String var1);

   ApplicationHasher createApplicationHasher(String var1, FileFilter var2);
}
