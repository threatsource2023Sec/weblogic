package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface PersistenceMBean extends XMLElementMBean {
   String getIsModifiedMethodName();

   void setIsModifiedMethodName(String var1);

   boolean getDelayUpdatesUntilEndOfTx();

   void setDelayUpdatesUntilEndOfTx(boolean var1);

   boolean getFindersLoadBean();

   void setFindersLoadBean(boolean var1);

   PersistenceUseMBean getPersistenceUse();

   void setPersistenceUse(PersistenceUseMBean var1);
}
