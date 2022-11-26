package weblogic.security.spi;

/** @deprecated */
@Deprecated
public interface AdjudicationProvider extends SecurityProvider {
   Adjudicator getAdjudicator();
}
