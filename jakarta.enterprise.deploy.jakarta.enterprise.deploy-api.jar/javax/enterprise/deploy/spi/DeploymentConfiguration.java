package javax.enterprise.deploy.spi;

import java.io.InputStream;
import java.io.OutputStream;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.spi.exceptions.BeanNotFoundException;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;

public interface DeploymentConfiguration {
   DeployableObject getDeployableObject();

   DConfigBeanRoot getDConfigBeanRoot(DDBeanRoot var1) throws ConfigurationException;

   void removeDConfigBean(DConfigBeanRoot var1) throws BeanNotFoundException;

   DConfigBeanRoot restoreDConfigBean(InputStream var1, DDBeanRoot var2) throws ConfigurationException;

   void saveDConfigBean(OutputStream var1, DConfigBeanRoot var2) throws ConfigurationException;

   void restore(InputStream var1) throws ConfigurationException;

   void save(OutputStream var1) throws ConfigurationException;
}
