package weblogic.store;

public interface RuntimeUpdater {
   void setHealthFailed(PersistentStoreException var1);

   void setHealthWarn(String var1);
}
