package weblogic.management.rest.lib.bean.utils;

public interface PropertyType extends AttributeType {
   boolean isIdentity();

   boolean isEncrypted();

   boolean isSensitive();

   boolean isDerivedDefault();

   boolean hasDefaultValue();

   Object getDefaultValue();

   boolean hasProductionModeDefaultValue();

   Object getProductionModeDefaultValue();

   boolean hasSecureValue();

   Object getSecureValue();

   boolean isSecureValueDocOnly();

   PropertyMarshaller getMarshaller();

   PropertyUnmarshaller getUnmarshaller();

   Number getMin();

   Number getMax();

   Object[] getLegalValues();

   boolean isNullable();

   boolean isUnharvestable();
}
