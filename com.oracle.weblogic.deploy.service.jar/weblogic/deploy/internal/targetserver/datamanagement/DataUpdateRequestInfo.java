package weblogic.deploy.internal.targetserver.datamanagement;

import java.util.List;

public interface DataUpdateRequestInfo {
   List getDeltaFiles();

   List getTargetFiles();

   long getRequestId();

   boolean isStatic();

   boolean isDelete();

   boolean isPlanUpdate();

   boolean isStaging();

   boolean isPlanStaging();
}
