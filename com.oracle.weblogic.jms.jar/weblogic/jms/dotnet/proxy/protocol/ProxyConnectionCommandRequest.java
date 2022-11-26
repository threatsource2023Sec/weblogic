package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyConnectionCommandRequest extends ProxyRequest {
   private static final int EXTVERSION = 1;
   public static final int COMMAND_START = 1;
   public static final int COMMAND_STOP = 2;
   public static final int COMMAND_CLOSE = 3;
   private int commandCode;

   public ProxyConnectionCommandRequest(int commandCode) {
      this.commandCode = commandCode;
   }

   public ProxyConnectionCommandRequest() {
   }

   public int getCommandCode() {
      return this.commandCode;
   }

   public String getCommandCodeString() {
      switch (this.commandCode) {
         case 1:
            return "START";
         case 2:
            return "STOP";
         case 3:
            return "CLOSE";
         default:
            return "UNKNOWN";
      }
   }

   public int getMarshalTypeCode() {
      return 8;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.commandCode == 1) {
         this.versionFlags.setBit(1);
      }

      if (this.commandCode == 2) {
         this.versionFlags.setBit(2);
      }

      if (this.commandCode == 3) {
         this.versionFlags.setBit(3);
      }

      this.versionFlags.marshal(mw);
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      if (this.versionFlags.isSet(1)) {
         this.commandCode = 1;
      }

      if (this.versionFlags.isSet(2)) {
         this.commandCode = 2;
      }

      if (this.versionFlags.isSet(3)) {
         this.commandCode = 3;
      }

   }
}
