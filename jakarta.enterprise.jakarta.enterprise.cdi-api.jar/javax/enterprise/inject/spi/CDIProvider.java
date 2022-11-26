package javax.enterprise.inject.spi;

public interface CDIProvider extends Prioritized {
   int DEFAULT_CDI_PROVIDER_PRIORITY = 0;

   CDI getCDI();

   default int getPriority() {
      return 0;
   }
}
