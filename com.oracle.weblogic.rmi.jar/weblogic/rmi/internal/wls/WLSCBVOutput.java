package weblogic.rmi.internal.wls;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.rmi.extensions.server.ColocatedStream;
import weblogic.rmi.internal.CBVOutput;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkOutput;
import weblogic.utils.io.Immutable;
import weblogic.utils.io.StringOutput;

public class WLSCBVOutput extends WLObjectOutputStream implements CBVOutput, ChunkOutput, StringOutput, ColocatedStream, ObjectOutput {
   private CBVOutputStream cbvos;

   public WLSCBVOutput(CBVOutputStream cbvos, OutputStream os) throws IOException {
      super(os);
      this.cbvos = cbvos;
   }

   public void setDelegate(CBVOutputStream cbvos, OutputStream os) {
      this.cbvos = cbvos;
      super.setDelegate(os);
   }

   public void writeUTF(String s) throws IOException {
      this.cbvos.writeObjectSpecial(s);
   }

   public void writeUTF8(String s) throws IOException {
      this.cbvos.writeObjectSpecial(s);
   }

   public void writeASCII(String s) throws IOException {
      this.cbvos.writeObjectSpecial(s);
   }

   public void writeChunks(Chunk s) throws IOException {
      this.cbvos.writeObjectSpecial(s);
   }

   public final void writeImmutable(Object o) throws IOException {
      this.cbvos.writeObjectSpecial(o);
   }

   protected Object replaceObject(Object o) throws IOException {
      o = super.replaceObject(o);
      return o instanceof Immutable ? new ImmutableWrapper(o) : o;
   }

   protected void writeClassDescriptor(ObjectStreamClass descriptor) throws IOException {
      this.cbvos.writeObjectSpecial(descriptor);
   }

   public void close() throws IOException {
      this.cbvos = null;
      super.close();
   }

   private static final class ImmutableWrapper implements Immutable, Externalizable {
      private static final long serialVersionUID = -7325914689974638362L;
      private Object obj;

      public ImmutableWrapper() {
      }

      private ImmutableWrapper(Object obj) {
         this.obj = obj;
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         ((WLObjectOutput)out).writeImmutable(this.obj);
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.obj = ((WLObjectInput)in).readImmutable();
      }

      public Object readResolve() throws ObjectStreamException {
         return this.obj;
      }

      // $FF: synthetic method
      ImmutableWrapper(Object x0, Object x1) {
         this(x0);
      }
   }
}
