package weblogic.security.spi;

public interface VersionableApplicationProvider extends SecurityProvider {
   void createApplicationVersion(String var1, String var2) throws ApplicationVersionCreationException;

   void deleteApplicationVersion(String var1) throws ApplicationVersionRemovalException;

   void deleteApplication(String var1) throws ApplicationRemovalException;
}
