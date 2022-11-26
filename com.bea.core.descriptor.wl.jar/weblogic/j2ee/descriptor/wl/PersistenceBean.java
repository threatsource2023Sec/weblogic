package weblogic.j2ee.descriptor.wl;

public interface PersistenceBean {
   String getIsModifiedMethodName();

   void setIsModifiedMethodName(String var1);

   boolean isDelayUpdatesUntilEndOfTx();

   void setDelayUpdatesUntilEndOfTx(boolean var1);

   boolean isFindersLoadBean();

   void setFindersLoadBean(boolean var1);

   PersistenceUseBean getPersistenceUse();

   boolean isPersistenceUseSet();

   String getId();

   void setId(String var1);
}
