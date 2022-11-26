package weblogic.management.runtime;

import java.beans.PropertyChangeListener;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.WebLogicMBean;

@Contract
public interface RuntimeMBean extends WebLogicMBean {
   void preDeregister() throws Exception;

   void addPropertyChangeListener(PropertyChangeListener var1);

   void removePropertyChangeListener(PropertyChangeListener var1);
}
