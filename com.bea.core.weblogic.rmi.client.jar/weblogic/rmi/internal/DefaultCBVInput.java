package weblogic.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.ColocatedStream;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkInput;
import weblogic.utils.io.DelegatingInputStream;
import weblogic.utils.io.Replacer;
import weblogic.utils.io.StringInput;

public class DefaultCBVInput extends ObjectInputStream implements CBVInput, StringInput, ChunkInput, ColocatedStream {
   private CBVInputStream cbv;
   private DelegatingInputStream delegate;
   private Replacer replacer;

   public DefaultCBVInput(CBVInputStream cbv, InputStream is) throws IOException {
      this(new DelegatingInputStream(is));
      this.cbv = cbv;
   }

   private DefaultCBVInput(DelegatingInputStream dis) throws IOException {
      super(dis);
      this.delegate = dis;

      try {
         this.enableResolveObject(true);
      } catch (SecurityException var3) {
      }

   }

   public void setDelegate(CBVInputStream cbv, InputStream in) {
      this.cbv = cbv;
      this.delegate.setDelegate(in);
   }

   public void setReplacer(Replacer replacer) {
      this.replacer = replacer;
   }

   protected Object resolveObject(Object obj) throws IOException {
      return this.replacer == null ? obj : this.replacer.resolveObject(obj);
   }

   public String readUTF() throws IOException {
      return (String)this.cbv.readObjectSpecial();
   }

   public String readASCII() throws IOException {
      return this.readUTF();
   }

   public String readUTF8() throws IOException {
      return this.readUTF();
   }

   public Chunk readChunks() throws IOException {
      return (Chunk)this.cbv.readObjectSpecial();
   }

   protected ObjectStreamClass readClassDescriptor() throws IOException {
      return (ObjectStreamClass)this.cbv.readObjectSpecial();
   }

   public Object readImmutable() throws IOException {
      return this.cbv.readObjectSpecial();
   }
}
