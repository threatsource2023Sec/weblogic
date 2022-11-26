package weblogic.rmi.internal;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.rmi.extensions.server.ColocatedStream;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkOutput;
import weblogic.utils.io.DelegatingOutputStream;
import weblogic.utils.io.Replacer;
import weblogic.utils.io.StringOutput;

public class DefaultCBVOutput extends ObjectOutputStream implements CBVOutput, ChunkOutput, StringOutput, ColocatedStream, ObjectOutput {
   private CBVOutputStream cbv;
   private DelegatingOutputStream delegate;
   private Replacer replacer;

   public DefaultCBVOutput(CBVOutputStream cbv, OutputStream out) throws IOException {
      this(new DelegatingOutputStream(out));
      this.cbv = cbv;
   }

   private DefaultCBVOutput(DelegatingOutputStream dos) throws IOException {
      super(dos);
      this.delegate = dos;

      try {
         this.enableReplaceObject(true);
      } catch (SecurityException var3) {
      }

   }

   public void setDelegate(CBVOutputStream cbv, OutputStream out) {
      this.cbv = cbv;
      this.delegate.setDelegate(out);
   }

   public void setReplacer(Replacer replacer) {
      this.replacer = replacer;
   }

   protected Object replaceObject(Object obj) throws IOException {
      return this.replacer == null ? obj : this.replacer.replaceObject(obj);
   }

   public void writeUTF(String s) throws IOException {
      this.cbv.writeObjectSpecial(s);
   }

   public void writeChunks(Chunk c) throws IOException {
      this.cbv.writeObjectSpecial(c);
   }

   public void writeASCII(String s) throws IOException {
      this.cbv.writeObjectSpecial(s);
   }

   public void writeUTF8(String s) throws IOException {
      this.cbv.writeObjectSpecial(s);
   }

   protected void writeClassDescriptor(ObjectStreamClass descriptor) throws IOException {
      this.cbv.writeObjectSpecial(descriptor);
   }

   public final void writeImmutable(Object o) throws IOException {
      this.cbv.writeObjectSpecial(o);
   }

   public void close() throws IOException {
      this.cbv = null;
      super.close();
   }
}
