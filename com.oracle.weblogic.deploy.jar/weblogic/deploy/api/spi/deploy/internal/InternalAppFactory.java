package weblogic.deploy.api.spi.deploy.internal;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface InternalAppFactory {
   List createInternalApps();
}
