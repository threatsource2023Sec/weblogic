package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface WLDFImageRuntimeMBean extends WLDFPartitionImageRuntimeMBean {
   WLDFImageCreationTaskRuntimeMBean captureImage(String var1) throws ManagementException;

   WLDFImageCreationTaskRuntimeMBean captureImage(String var1, int var2) throws ManagementException;
}
