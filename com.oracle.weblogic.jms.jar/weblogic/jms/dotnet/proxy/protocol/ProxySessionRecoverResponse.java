package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxySessionRecoverResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private static final int _HAS_GENERATION = 1;
   private int generation = 0;

   public ProxySessionRecoverResponse(int generation) {
      this.generation = generation;
   }

   public final long getGeneration() {
      return (long)this.generation;
   }

   public ProxySessionRecoverResponse() {
   }

   public int getMarshalTypeCode() {
      return 33;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.generation != 0) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      if (this.generation != 0) {
         mw.writeInt(this.generation);
      }

   }

   public void unmarshal(MarshalReader mr) {
      MarshalBitMask versionFlags = new MarshalBitMask();
      versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(versionFlags.getVersion(), 1, 1);
      if (versionFlags.isSet(1)) {
         this.generation = mr.readInt();
      }

   }
}
