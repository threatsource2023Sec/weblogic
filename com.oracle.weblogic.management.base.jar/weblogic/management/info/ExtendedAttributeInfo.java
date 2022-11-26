package weblogic.management.info;

public interface ExtendedAttributeInfo {
   Object getDefaultValue();

   Class getTypeClass();

   Class getCollectionTypeClass();

   boolean isDynamic();

   String getLegalCheck();

   Object[] getLegalValues();

   Long getLegalMax();

   Long getLegalMin();

   boolean getLegalNull();

   Object getClientDefault();

   boolean isConfigurable();

   String getOldProp();

   boolean isExcluded();

   boolean isEncrypted();

   String getUnits();

   String[] getLegalChecks();

   String[] getLegalResponses();

   String getLegalResponse();

   Integer getProtectionLevel();

   boolean isOverrideDynamic();

   boolean isDeploymentDescriptor();

   boolean isLegalValuesExtensible();

   String getName();

   String getType();

   String getDescription();

   boolean isWritable();

   boolean isReadable();

   boolean isIs();

   boolean isContained();
}
