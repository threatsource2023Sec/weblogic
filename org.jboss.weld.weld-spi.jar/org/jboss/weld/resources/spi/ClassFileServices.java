package org.jboss.weld.resources.spi;

import org.jboss.weld.bootstrap.api.BootstrapService;

public interface ClassFileServices extends BootstrapService {
   ClassFileInfo getClassFileInfo(String var1);
}
