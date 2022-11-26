package com.bea.core.repackaged.springframework.jmx.export.metadata;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.Method;

public interface JmxAttributeSource {
   @Nullable
   ManagedResource getManagedResource(Class var1) throws InvalidMetadataException;

   @Nullable
   ManagedAttribute getManagedAttribute(Method var1) throws InvalidMetadataException;

   @Nullable
   ManagedMetric getManagedMetric(Method var1) throws InvalidMetadataException;

   @Nullable
   ManagedOperation getManagedOperation(Method var1) throws InvalidMetadataException;

   ManagedOperationParameter[] getManagedOperationParameters(Method var1) throws InvalidMetadataException;

   ManagedNotification[] getManagedNotifications(Class var1) throws InvalidMetadataException;
}
