package weblogic.rmi.internal.wls;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.ColocatedStream;
import weblogic.rmi.internal.CBVInput;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkInput;
import weblogic.utils.io.StringInput;

public class WLSCBVInput extends WLObjectInputStream implements CBVInput, StringInput, ChunkInput, ColocatedStream {
   CBVInputStream cbvis;

   public WLSCBVInput(CBVInputStream cbvis, InputStream is) throws IOException {
      super(is);
      this.cbvis = cbvis;
   }

   public void setDelegate(CBVInputStream cbvis, InputStream is) {
      this.cbvis = cbvis;
      super.setDelegate(is);
   }

   protected boolean useInterAppClassLoader() {
      return true;
   }

   public String readUTF() throws IOException {
      return (String)this.cbvis.readObjectSpecial();
   }

   public String readUTF8() throws IOException {
      return this.readUTF();
   }

   public String readASCII() throws IOException {
      return this.readUTF();
   }

   public final Object readImmutable() throws IOException {
      return this.cbvis.readObjectSpecial();
   }

   public Chunk readChunks() throws IOException {
      return (Chunk)this.cbvis.readObjectSpecial();
   }

   protected ObjectStreamClass readClassDescriptor() throws IOException {
      return (ObjectStreamClass)this.cbvis.readObjectSpecial();
   }
}
