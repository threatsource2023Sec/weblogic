package javax.jdo.metadata;

public interface FetchGroupMetadata extends Metadata {
   String getName();

   FetchGroupMetadata setPostLoad(boolean var1);

   Boolean getPostLoad();

   MemberMetadata[] getMembers();

   int getNumberOfMembers();

   FieldMetadata newFieldMetadata(String var1);

   PropertyMetadata newPropertyMetadata(String var1);
}
