package weblogic.security.spi;

public interface BulkAdjudicationProvider extends AdjudicationProviderV2 {
   BulkAdjudicator getBulkAdjudicator();
}
