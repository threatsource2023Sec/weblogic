package weblogic.deploy.api.spi;

import java.util.List;
import javax.enterprise.deploy.model.XpathEvent;
import javax.enterprise.deploy.spi.DConfigBean;
import weblogic.descriptor.DescriptorBean;

public interface WebLogicDConfigBean extends DConfigBean {
   boolean isValid();

   void notifyDDChange(XpathEvent var1);

   List getChildBeans();

   DescriptorBean getDescriptorBean();

   boolean isModified();

   String applyNamespace(String var1);
}
