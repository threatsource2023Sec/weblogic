package weblogic.management.provider.internal;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface BeanInfoEnabler {
   Map getEnabledProperties();
}
