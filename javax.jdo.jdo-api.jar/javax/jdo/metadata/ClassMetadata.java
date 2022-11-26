package javax.jdo.metadata;

import java.lang.reflect.Field;

public interface ClassMetadata extends TypeMetadata {
   ClassMetadata setPersistenceModifier(ClassPersistenceModifier var1);

   ClassPersistenceModifier getPersistenceModifier();

   FieldMetadata newFieldMetadata(String var1);

   FieldMetadata newFieldMetadata(Field var1);
}
