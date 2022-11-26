package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface GenericFileStoreMBean extends ConfigurationMBean {
   String SYNCWRITE_DISABLED = "Disabled";
   String SYNCWRITE_CACHEFLUSH = "Cache-Flush";
   String SYNCWRITE_DIRECTWRITE = "Direct-Write";
   String SYNCWRITE_DIRECTWRITEWITHCACHE = "Direct-Write-With-Cache";

   String getDirectory();

   void setDirectory(String var1) throws InvalidAttributeValueException;

   String getSynchronousWritePolicy();

   void setSynchronousWritePolicy(String var1) throws InvalidAttributeValueException, DistributedManagementException;

   String getCacheDirectory();

   void setCacheDirectory(String var1) throws InvalidAttributeValueException;

   int getMinWindowBufferSize();

   void setMinWindowBufferSize(int var1);

   int getMaxWindowBufferSize();

   void setMaxWindowBufferSize(int var1);

   int getIoBufferSize();

   void setIoBufferSize(int var1);

   long getMaxFileSize();

   void setMaxFileSize(long var1);

   int getBlockSize();

   void setBlockSize(int var1);

   long getInitialSize();

   void setInitialSize(long var1);

   boolean isFileLockingEnabled();

   void setFileLockingEnabled(boolean var1);
}
