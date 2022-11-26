package javax.jdo.metadata;

public interface QueryMetadata extends Metadata {
   String getName();

   QueryMetadata setLanguage(String var1);

   String getLanguage();

   QueryMetadata setQuery(String var1);

   String getQuery();

   QueryMetadata setResultClass(String var1);

   String getResultClass();

   QueryMetadata setUnique(boolean var1);

   Boolean getUnique();

   QueryMetadata setUnmodifiable();

   boolean getUnmodifiable();

   QueryMetadata setFetchPlan(String var1);

   String getFetchPlan();
}
