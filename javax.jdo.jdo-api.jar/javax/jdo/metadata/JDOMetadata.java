package javax.jdo.metadata;

public interface JDOMetadata extends Metadata {
   JDOMetadata setCatalog(String var1);

   String getCatalog();

   JDOMetadata setSchema(String var1);

   String getSchema();

   PackageMetadata[] getPackages();

   PackageMetadata newPackageMetadata(String var1);

   PackageMetadata newPackageMetadata(Package var1);

   int getNumberOfPackages();

   ClassMetadata newClassMetadata(Class var1);

   InterfaceMetadata newInterfaceMetadata(Class var1);

   QueryMetadata[] getQueries();

   QueryMetadata newQueryMetadata(String var1);

   int getNumberOfQueries();

   FetchPlanMetadata[] getFetchPlans();

   FetchPlanMetadata newFetchPlanMetadata(String var1);

   int getNumberOfFetchPlans();
}
