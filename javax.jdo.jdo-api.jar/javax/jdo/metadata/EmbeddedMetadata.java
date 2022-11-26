package javax.jdo.metadata;

public interface EmbeddedMetadata extends Metadata {
   EmbeddedMetadata setOwnerMember(String var1);

   String getOwnerMember();

   EmbeddedMetadata setNullIndicatorColumn(String var1);

   String getNullIndicatorColumn();

   EmbeddedMetadata setNullIndicatorValue(String var1);

   String getNullIndicatorValue();

   MemberMetadata[] getMembers();

   int getNumberOfMembers();

   FieldMetadata newFieldMetadata(String var1);

   PropertyMetadata newPropertyMetadata(String var1);

   DiscriminatorMetadata getDiscriminatorMetadata();

   DiscriminatorMetadata newDiscriminatorMetadata();
}
