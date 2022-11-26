package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ApplicationServer;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Partition;
import com.oracle.weblogic.lifecycle.provisioning.core.JavaBeanMap;
import java.beans.IntrospectionException;
import java.util.Map;
import javax.inject.Inject;
import org.apache.tools.ant.PropertyHelper;
import org.apache.tools.ant.property.NullReturn;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.Rank;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.RuntimeDir;

@ContractsProvided({PropertyHelper.PropertyEvaluator.class})
@PerLookup
@Rank(2)
@Service
public final class PartitionDirPropertyEvaluator implements PropertyHelper.PropertyEvaluator {
   private static final String PREFIX = "partitionDir:";
   private final Map partitionFileSystemAsMap;

   @Inject
   public PartitionDirPropertyEvaluator(@Optional @ApplicationServer @Partition RuntimeDir temporaryPartitionFilesystem) throws IntrospectionException {
      this((Map)(temporaryPartitionFilesystem == null ? null : new JavaBeanMap(temporaryPartitionFilesystem)));
   }

   PartitionDirPropertyEvaluator(Map temporaryPartitionFilesystemAsMap) {
      this.partitionFileSystemAsMap = temporaryPartitionFilesystemAsMap;
   }

   public final Object evaluate(String propertyName, PropertyHelper ignoredCallingPropertyHelper) {
      Object returnValue = null;
      if (propertyName != null && this.partitionFileSystemAsMap != null && propertyName.startsWith("partitionDir:") && propertyName.length() > "partitionDir:".length()) {
         propertyName = propertyName.substring("partitionDir:".length());
         returnValue = this.partitionFileSystemAsMap.get(propertyName);
         if (returnValue == null && this.partitionFileSystemAsMap.containsKey(propertyName)) {
            returnValue = NullReturn.NULL;
         }
      }

      return returnValue;
   }
}
