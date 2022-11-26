package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.reflect.Executable;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.validation.metadata.BeanDescriptor;
import org.hibernate.validator.internal.metadata.facets.Validatable;

public interface BeanMetaData extends Validatable {
   Class getBeanClass();

   boolean hasConstraints();

   BeanDescriptor getBeanDescriptor();

   PropertyMetaData getMetaDataFor(String var1);

   List getDefaultGroupSequence(Object var1);

   Iterator getDefaultValidationSequence(Object var1);

   boolean defaultGroupSequenceIsRedefined();

   Set getMetaConstraints();

   Set getDirectMetaConstraints();

   Optional getMetaDataFor(Executable var1) throws IllegalArgumentException;

   List getClassHierarchy();
}
