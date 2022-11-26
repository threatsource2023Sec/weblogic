package weblogic.deploy.api.spi.deploy.internal;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InternalAppFactoryExt extends InternalAppFactory {
   boolean requiresRuntimes();
}
