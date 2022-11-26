package javax.enterprise.deploy.spi;

import javax.enterprise.deploy.model.DDBeanRoot;

public interface DConfigBeanRoot extends DConfigBean {
   DConfigBean getDConfigBean(DDBeanRoot var1);
}
