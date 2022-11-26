package weblogic.nodemanager.plugin;

public abstract class DefaultProcessManagementPluginImpl implements ProcessManagementPlugin {
   private Provider provider;

   public void init(Provider provider) {
      this.provider = provider;
   }

   public Provider getProvider() {
      return this.provider;
   }

   public abstract ProcessManagementPlugin.SystemComponentManager createSystemComponentManager(String var1);

   public boolean isAlive(String pid) {
      return this.getProvider().getNMProcessFactory().isProcessAlive(pid);
   }
}
