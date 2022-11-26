package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyContextCloseRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   private static final int _IS_CLOSE_ALL = 1;
   private boolean closeAll;

   public ProxyContextCloseRequest(boolean closeAll) {
      this.closeAll = closeAll;
   }

   public boolean isCloseAll() {
      return this.closeAll;
   }

   public ProxyContextCloseRequest() {
   }

   public int getMarshalTypeCode() {
      return 7;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.closeAll) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         this.closeAll = true;
      }

   }
}
