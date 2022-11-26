package weblogic.servlet.internal;

import java.io.IOException;
import java.util.List;

public interface WarExtensionProcessor {
   List getExtensions() throws IOException;

   boolean isSupport();
}
