package weblogic.persistence.spi;

public class PersistenceProviderResolverService extends AbstractPersistenceProviderResolver {
   protected AbstractPersistenceProviderResolver.DefaultPP getDefaultPP() {
      return JPAIntegrationProviderFactory.isToplinkProviderClass(JPAIntegrationProviderFactory.getDefaultJPAProviderClassName()) ? AbstractPersistenceProviderResolver.DefaultPP.ECLIPSELINK : AbstractPersistenceProviderResolver.DefaultPP.KODO;
   }
}
