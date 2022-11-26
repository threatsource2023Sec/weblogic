package weblogic.deploy.api.model;

import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.XpathListener;

public interface WebLogicDDBean extends DDBean {
   void addXpathListener(String var1, XpathListener var2);

   void removeXpathListener(String var1, XpathListener var2);

   boolean isModified();
}
