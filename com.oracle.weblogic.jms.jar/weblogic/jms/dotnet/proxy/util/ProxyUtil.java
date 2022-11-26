package weblogic.jms.dotnet.proxy.util;

import java.io.Externalizable;
import java.io.IOException;
import weblogic.jms.dotnet.transport.MarshalRuntimeException;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedObjectOutputStream;

public final class ProxyUtil {
   public static void checkVersion(int version, int minExpectedVersion, int maxExpectedVersion) throws MarshalRuntimeException {
      if (version < minExpectedVersion || version > maxExpectedVersion) {
         throw new MarshalRuntimeException("Unsupported class version {" + version + "}.  Expected a value between {" + minExpectedVersion + "} and {" + maxExpectedVersion + "} inclusive.");
      }
   }

   public static byte[] marshalExternalizable(Externalizable obj) {
      ChunkedObjectOutputStream stream = null;

      try {
         stream = new ChunkedObjectOutputStream();
         obj.writeExternal(stream);
      } catch (IOException var6) {
         var6.printStackTrace(System.err);
      }

      byte[] buf = stream.getBuffer();
      int size = stream.getSize();
      byte[] marshaledObj = new byte[size];
      System.arraycopy(buf, 0, marshaledObj, 0, size);
      Chunk head = stream.getChunks();
      if (head != null) {
         Chunk.releaseChunks(head);
      }

      return marshaledObj;
   }
}
