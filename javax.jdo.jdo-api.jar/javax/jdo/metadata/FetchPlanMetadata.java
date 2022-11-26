package javax.jdo.metadata;

public interface FetchPlanMetadata extends Metadata {
   String getName();

   FetchPlanMetadata setMaxFetchDepth(int var1);

   int getMaxFetchDepth();

   FetchPlanMetadata setFetchSize(int var1);

   int getFetchSize();

   FetchGroupMetadata[] getFetchGroups();

   FetchGroupMetadata newFetchGroupMetadata(String var1);

   int getNumberOfFetchGroups();
}
