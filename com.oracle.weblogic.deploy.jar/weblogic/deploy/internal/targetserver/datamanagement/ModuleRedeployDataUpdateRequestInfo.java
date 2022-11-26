package weblogic.deploy.internal.targetserver.datamanagement;

import java.util.ArrayList;
import java.util.List;

public class ModuleRedeployDataUpdateRequestInfo implements DataUpdateRequestInfo {
   private final List moduleIds;
   private final long requestId;

   public ModuleRedeployDataUpdateRequestInfo(List givenModuleIds, long givenRequestId) {
      this.moduleIds = givenModuleIds;
      this.requestId = givenRequestId;
   }

   public List getDeltaFiles() {
      return this.moduleIds;
   }

   public List getTargetFiles() {
      return new ArrayList();
   }

   public long getRequestId() {
      return this.requestId;
   }

   public boolean isStatic() {
      return false;
   }

   public boolean isDelete() {
      return false;
   }

   public boolean isPlanUpdate() {
      return false;
   }

   public boolean isStaging() {
      return false;
   }

   public boolean isPlanStaging() {
      return false;
   }
}
