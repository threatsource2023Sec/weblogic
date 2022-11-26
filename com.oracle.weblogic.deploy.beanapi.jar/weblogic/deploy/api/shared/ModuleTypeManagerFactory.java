package weblogic.deploy.api.shared;

import java.io.File;
import java.io.IOException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ModuleTypeManagerFactory {
   ModuleTypeManager create(File var1) throws IOException;
}
