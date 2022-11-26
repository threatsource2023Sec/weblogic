package javax.enterprise.deploy.spi;

import java.beans.PropertyChangeListener;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.XpathEvent;
import javax.enterprise.deploy.spi.exceptions.BeanNotFoundException;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;

public interface DConfigBean {
   DDBean getDDBean();

   String[] getXpaths();

   DConfigBean getDConfigBean(DDBean var1) throws ConfigurationException;

   void removeDConfigBean(DConfigBean var1) throws BeanNotFoundException;

   void notifyDDChange(XpathEvent var1);

   void addPropertyChangeListener(PropertyChangeListener var1);

   void removePropertyChangeListener(PropertyChangeListener var1);
}
