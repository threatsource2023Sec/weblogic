package weblogic.diagnostics.debugpatch;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface FindClassLoaders {
   List findAppClassLoaders(String var1, String var2, String var3);
}
