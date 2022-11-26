package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;

public abstract class ProxyRequest implements MarshalReadable, MarshalWritable {
   static final boolean debug = false;
   protected MarshalBitMask versionFlags;

   public abstract int getMarshalTypeCode();

   public abstract void marshal(MarshalWriter var1);

   public abstract void unmarshal(MarshalReader var1);

   protected void debug(String message) {
      System.out.println("[" + this.getClass().getName() + "]: " + message);
   }
}
