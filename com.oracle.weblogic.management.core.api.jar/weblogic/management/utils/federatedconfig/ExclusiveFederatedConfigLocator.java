package weblogic.management.utils.federatedconfig;

import java.util.Set;

public interface ExclusiveFederatedConfigLocator extends FederatedConfigLocator {
   Set excludes();
}
