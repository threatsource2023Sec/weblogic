package com.oracle.weblogic.lifecycle.config;

import java.io.File;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface PropagationManager {
   String MAGIC_FILE_APPEND = ".changed";

   boolean isEnabled();

   void propagate(File var1);
}
