package org.hibernate.validator.internal.xml.mapping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;

class ContainerElementTypeConfigurationBuilder {
   private final List containerElementTypeStaxBuilders = new ArrayList();
   private final Set configuredPaths = new HashSet();

   public void add(ContainerElementTypeStaxBuilder containerElementTypeStaxBuilder) {
      this.containerElementTypeStaxBuilders.add(containerElementTypeStaxBuilder);
   }

   ContainerElementTypeConfiguration build(ConstraintLocation parentConstraintLocation, Type enclosingType) {
      return this.build(ContainerElementTypePath.root(), parentConstraintLocation, enclosingType);
   }

   private ContainerElementTypeConfiguration build(ContainerElementTypePath parentConstraintElementTypePath, ConstraintLocation parentConstraintLocation, Type enclosingType) {
      return (ContainerElementTypeConfiguration)this.containerElementTypeStaxBuilders.stream().map((builder) -> {
         return builder.build(this.configuredPaths, parentConstraintElementTypePath, parentConstraintLocation, enclosingType);
      }).reduce(ContainerElementTypeConfigurationBuilder.ContainerElementTypeConfiguration.EMPTY_CONFIGURATION, ContainerElementTypeConfiguration::merge);
   }

   static class ContainerElementTypeConfiguration {
      public static final ContainerElementTypeConfiguration EMPTY_CONFIGURATION = new ContainerElementTypeConfiguration(Collections.emptySet(), Collections.emptyMap());
      private final Set metaConstraints;
      private final Map containerElementTypesCascadingMetaDataBuilder;

      ContainerElementTypeConfiguration(Set metaConstraints, Map containerElementTypesCascadingMetaData) {
         this.metaConstraints = metaConstraints;
         this.containerElementTypesCascadingMetaDataBuilder = containerElementTypesCascadingMetaData;
      }

      public Set getMetaConstraints() {
         return this.metaConstraints;
      }

      public Map getTypeParametersCascadingMetaData() {
         return this.containerElementTypesCascadingMetaDataBuilder;
      }

      public static ContainerElementTypeConfiguration merge(ContainerElementTypeConfiguration l, ContainerElementTypeConfiguration r) {
         return new ContainerElementTypeConfiguration((Set)Stream.concat(l.metaConstraints.stream(), r.metaConstraints.stream()).collect(Collectors.toSet()), (Map)Stream.concat(l.containerElementTypesCascadingMetaDataBuilder.entrySet().stream(), r.containerElementTypesCascadingMetaDataBuilder.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
      }
   }
}
