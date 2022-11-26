package weblogic.connector.outbound;

import javax.resource.spi.ConnectionRequestInfo;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.connector.security.outbound.SecurityContext;

public class ConnectionReqInfo implements PooledResourceInfo {
   private ConnectionRequestInfo connRequestInfo;
   private SecurityContext secCtx;
   private boolean shareable;

   public ConnectionReqInfo(ConnectionRequestInfo aConnRequestInfo, SecurityContext secCtx) {
      this.connRequestInfo = aConnRequestInfo;
      this.secCtx = secCtx;
   }

   public boolean equals(PooledResourceInfo info) {
      return this == info;
   }

   public void setInfo(ConnectionRequestInfo aConnRequestInfo) {
      this.connRequestInfo = aConnRequestInfo;
   }

   void setShareable(boolean shareable) {
      this.shareable = shareable;
   }

   public ConnectionRequestInfo getInfo() {
      return this.connRequestInfo;
   }

   public SecurityContext getSecurityContext() {
      return this.secCtx;
   }

   boolean isShareable() {
      return this.shareable;
   }
}
