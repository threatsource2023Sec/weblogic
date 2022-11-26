package weblogic.persistence.spi;

public class AppClientPersistenceProviderResolver extends AbstractPersistenceProviderResolver {
   protected AbstractPersistenceProviderResolver.DefaultPP getDefaultPP() {
      return AbstractPersistenceProviderResolver.DefaultPP.ECLIPSELINK;
   }
}
