package weblogic.coherence.service.internal.io;

import com.tangosol.io.ObjectStreamFactory;
import com.tangosol.io.WrapperDataInputStream;
import com.tangosol.util.ExternalizableHelper;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.io.ClassLoaderResolver;

public class WLSObjectStreamFactory implements ObjectStreamFactory {
   public ObjectInput getObjectInput(DataInput in, ClassLoader loader, boolean fForceNew) throws IOException {
      if (!fForceNew && in instanceof WLSObjectInputStream) {
         return (ObjectInput)in;
      } else {
         InputStream inStream = this.getInputStream(in, fForceNew);
         loader = loader == null && in instanceof WrapperDataInputStream ? ((WrapperDataInputStream)in).getClassLoader() : loader;
         return new WLSObjectInputStream(inStream, RemoteObjectReplacer.getReplacer(), new ClassLoaderResolver(loader), loader);
      }
   }

   public ObjectOutput getObjectOutput(DataOutput out) throws IOException {
      if (out instanceof WLSObjectOutputStream) {
         return (ObjectOutput)(out instanceof ExternalizableHelper.Shielded ? (ObjectOutput)out : new ExternalizableHelper.ShieldedObjectOutputStream((ObjectOutput)out));
      } else {
         WLSObjectOutputStream stream = new WLSObjectOutputStream(this.getOutputStream(out), RemoteObjectReplacer.getReplacer());
         stream.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
         return stream;
      }
   }

   private InputStream getInputStream(DataInput in, boolean forceNew) {
      return !forceNew && in instanceof InputStream ? (InputStream)in : ExternalizableHelper.getInputStream(in);
   }

   private OutputStream getOutputStream(DataOutput out) {
      return ExternalizableHelper.getOutputStream(out);
   }
}
