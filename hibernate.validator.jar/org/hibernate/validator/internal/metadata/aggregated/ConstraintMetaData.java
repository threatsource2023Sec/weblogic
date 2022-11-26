package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.reflect.Type;
import java.util.List;
import javax.validation.ElementKind;
import javax.validation.metadata.ElementDescriptor;

public interface ConstraintMetaData extends Iterable {
   String getName();

   Type getType();

   ElementKind getKind();

   boolean isCascading();

   boolean isConstrained();

   ElementDescriptor asDescriptor(boolean var1, List var2);
}
