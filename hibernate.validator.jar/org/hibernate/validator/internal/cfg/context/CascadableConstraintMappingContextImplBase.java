package org.hibernate.validator.internal.cfg.context;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.validator.cfg.context.Cascadable;
import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;
import org.hibernate.validator.cfg.context.ContainerElementTarget;
import org.hibernate.validator.cfg.context.GroupConversionTargetContext;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

abstract class CascadableConstraintMappingContextImplBase extends ConstraintMappingContextImplBase implements Cascadable {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Type configuredType;
   protected boolean isCascading;
   protected final Map groupConversions = CollectionHelper.newHashMap();
   private final Map containerElementContexts = new HashMap();
   private final Set configuredPaths = new HashSet();

   CascadableConstraintMappingContextImplBase(DefaultConstraintMapping mapping, Type configuredType) {
      super(mapping);
      this.configuredType = configuredType;
   }

   protected abstract Cascadable getThis();

   public void addGroupConversion(Class from, Class to) {
      this.groupConversions.put(from, to);
   }

   public Cascadable valid() {
      this.isCascading = true;
      return this.getThis();
   }

   public GroupConversionTargetContext convertGroup(Class from) {
      return new GroupConversionTargetContextImpl(from, this.getThis(), this);
   }

   public ContainerElementConstraintMappingContext containerElement(ContainerElementTarget parent, TypeConstraintMappingContextImpl typeContext, ConstraintLocation location) {
      if (TypeHelper.isArray(this.configuredType)) {
         throw LOG.getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException(this.configuredType);
      } else {
         if (this.configuredType instanceof ParameterizedType) {
            if (((ParameterizedType)this.configuredType).getActualTypeArguments().length > 1) {
               throw LOG.getNoTypeArgumentIndexIsGivenForTypeWithMultipleTypeArgumentsException(this.configuredType);
            }
         } else if (!TypeHelper.isArray(this.configuredType)) {
            throw LOG.getTypeIsNotAParameterizedNorArrayTypeException(this.configuredType);
         }

         return this.containerElement(parent, typeContext, location, 0);
      }
   }

   public ContainerElementConstraintMappingContext containerElement(ContainerElementTarget parent, TypeConstraintMappingContextImpl typeContext, ConstraintLocation location, int index, int... nestedIndexes) {
      Contracts.assertTrue(index >= 0, "Type argument index must not be negative");
      if (TypeHelper.isArray(this.configuredType)) {
         throw LOG.getContainerElementConstraintsAndCascadedValidationNotSupportedOnArraysException(this.configuredType);
      } else if (!(this.configuredType instanceof ParameterizedType) && !TypeHelper.isArray(this.configuredType)) {
         throw LOG.getTypeIsNotAParameterizedNorArrayTypeException(this.configuredType);
      } else {
         ContainerElementPathKey key = new ContainerElementPathKey(index, nestedIndexes);
         boolean configuredBefore = !this.configuredPaths.add(key);
         if (configuredBefore) {
            throw LOG.getContainerElementTypeHasAlreadyBeenConfiguredViaProgrammaticApiException(location.getTypeForValidatorResolution());
         } else {
            ContainerElementConstraintMappingContextImpl containerElementContext = (ContainerElementConstraintMappingContextImpl)this.containerElementContexts.get(index);
            if (containerElementContext == null) {
               containerElementContext = new ContainerElementConstraintMappingContextImpl(typeContext, parent, location, index);
               this.containerElementContexts.put(index, containerElementContext);
            }

            return (ContainerElementConstraintMappingContext)(nestedIndexes.length > 0 ? containerElementContext.nestedContainerElement(nestedIndexes) : containerElementContext);
         }
      }
   }

   public boolean isCascading() {
      return this.isCascading;
   }

   protected Set getTypeArgumentConstraints(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
      return (Set)this.containerElementContexts.values().stream().map((t) -> {
         return t.build(constraintHelper, typeResolutionHelper, valueExtractorManager);
      }).flatMap(Collection::stream).collect(Collectors.toSet());
   }

   protected CascadingMetaDataBuilder getCascadingMetaDataBuilder() {
      Map typeParametersCascadingMetaData = (Map)this.containerElementContexts.values().stream().filter((c) -> {
         return c.getContainerElementCascadingMetaDataBuilder() != null;
      }).collect(Collectors.toMap((c) -> {
         return c.getContainerElementCascadingMetaDataBuilder().getTypeParameter();
      }, (c) -> {
         return c.getContainerElementCascadingMetaDataBuilder();
      }));
      Iterator var2 = this.containerElementContexts.values().iterator();

      while(var2.hasNext()) {
         ContainerElementConstraintMappingContextImpl typeArgumentContext = (ContainerElementConstraintMappingContextImpl)var2.next();
         CascadingMetaDataBuilder cascadingMetaDataBuilder = typeArgumentContext.getContainerElementCascadingMetaDataBuilder();
         if (cascadingMetaDataBuilder != null) {
            typeParametersCascadingMetaData.put(cascadingMetaDataBuilder.getTypeParameter(), cascadingMetaDataBuilder);
         }
      }

      return CascadingMetaDataBuilder.annotatedObject(this.configuredType, this.isCascading, typeParametersCascadingMetaData, this.groupConversions);
   }

   private static class ContainerElementPathKey {
      private final int index;
      private final int[] nestedIndexes;

      public ContainerElementPathKey(int index, int[] nestedIndexes) {
         this.index = index;
         this.nestedIndexes = nestedIndexes;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + this.index;
         result = 31 * result + Arrays.hashCode(this.nestedIndexes);
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            ContainerElementPathKey other = (ContainerElementPathKey)obj;
            if (this.index != other.index) {
               return false;
            } else {
               return Arrays.equals(this.nestedIndexes, other.nestedIndexes);
            }
         }
      }
   }
}
