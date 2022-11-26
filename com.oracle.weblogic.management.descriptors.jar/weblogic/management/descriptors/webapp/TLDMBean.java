package weblogic.management.descriptors.webapp;

import weblogic.management.descriptors.WebElementMBean;

public interface TLDMBean extends WebElementMBean {
   String getTaglibVersion();

   void setTaglibVersion(String var1);

   String getJspVersion();

   void setJspVersion(String var1);

   String getShortName();

   void setShortName(String var1);

   String getURI();

   void setURI(String var1);

   String getDisplayName();

   void setDisplayName(String var1);

   String getSmallIcon();

   void setSmallIcon(String var1);

   String getLargeIcon();

   void setLargeIcon(String var1);

   String getDescription();

   void setDescription(String var1);

   ValidatorMBean getValidator();

   void setValidator(ValidatorMBean var1);

   TagMBean[] getTags();

   void setTags(TagMBean[] var1);

   ListenerMBean[] getListeners();

   void setListeners(ListenerMBean[] var1);
}
