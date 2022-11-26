package javax.jdo.metadata;

import javax.jdo.annotations.SequenceStrategy;

public interface PackageMetadata extends Metadata {
   String getName();

   PackageMetadata setCatalog(String var1);

   String getCatalog();

   PackageMetadata setSchema(String var1);

   String getSchema();

   ClassMetadata[] getClasses();

   ClassMetadata newClassMetadata(String var1);

   ClassMetadata newClassMetadata(Class var1);

   int getNumberOfClasses();

   InterfaceMetadata[] getInterfaces();

   InterfaceMetadata newInterfaceMetadata(String var1);

   InterfaceMetadata newInterfaceMetadata(Class var1);

   int getNumberOfInterfaces();

   SequenceMetadata[] getSequences();

   SequenceMetadata newSequenceMetadata(String var1, SequenceStrategy var2);

   int getNumberOfSequences();
}
