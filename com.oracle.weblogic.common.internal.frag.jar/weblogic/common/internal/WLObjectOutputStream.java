package weblogic.common.internal;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import weblogic.common.WLObjectOutput;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelStream;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.DelegatingOutputStream;
import weblogic.utils.io.Replacer;

public class WLObjectOutputStream extends ObjectOutputStream implements WLObjectOutput, ServerChannelStream {
   private final DelegatingOutputStream delegate;
   private Replacer replacer;
   private ServerChannel channel;

   public WLObjectOutputStream(OutputStream os) throws IOException {
      this(new DelegatingOutputStream(os));
   }

   private WLObjectOutputStream(DelegatingOutputStream delegate) throws IOException {
      super(delegate);
      this.replacer = null;
      this.channel = null;

      try {
         this.enableReplaceObject(true);
      } catch (SecurityException var3) {
      }

      this.delegate = delegate;
   }

   public final void setDelegate(OutputStream os) {
      this.delegate.setDelegate(os);
   }

   protected final void annotateClass(Class c) throws IOException {
      ClassLoader cl = c.getClassLoader();
      if (cl instanceof GenericClassLoader) {
         GenericClassLoader gcl = (GenericClassLoader)cl;
         String appName = gcl.getAnnotation().getAnnotationString();
         if (appName != null) {
            this.writeUTF(appName);
         } else {
            this.writeUTF("");
         }
      } else {
         this.writeUTF("");
      }

   }

   protected void annotateProxyClass(Class c) throws IOException {
      this.annotateClass(c);
   }

   public void writeUTF(String s) throws IOException {
      DataIO.writeUTF(this, s);
   }

   public final void setReplacer(Replacer replacer) {
      this.replacer = replacer;
   }

   public final void setServerChannel(ServerChannel sc) {
      this.channel = sc;
   }

   protected Object replaceObject(Object obj) throws IOException {
      return this.replacer == null ? obj : this.replacer.replaceObject(obj);
   }

   protected final void writeStreamHeader() throws IOException {
   }

   public void writeObjectWL(Object o) throws IOException {
      this.writeObject(o);
   }

   public final void writeString(String s) throws IOException {
      this.flush();
      if (s == null) {
         this.writeByte(112);
      } else {
         this.writeByte(116);
         this.writeUTF(s);
      }

   }

   public final void writeDate(Date dateval) throws IOException {
      this.writeObject(dateval);
   }

   public final void writeArrayList(ArrayList lst) throws IOException {
      this.writeObject(lst);
   }

   public final void writeProperties(Properties propval) throws IOException {
      this.writeObject(propval);
   }

   public final void writeBytes(byte[] val) throws IOException {
      this.writeObject(val);
   }

   public final void writeBytes(byte[] val, int off, int len) throws IOException {
      byte[] b = new byte[len];
      System.arraycopy(val, off, b, 0, len);
      this.writeObject(b);
   }

   public final void writeArrayOfObjects(Object[] aoo) throws IOException {
      this.writeObject(aoo);
   }

   public void writeImmutable(Object o) throws IOException {
      this.writeObject(o);
   }

   public final void writeAbbrevString(String s) throws IOException {
      this.writeString(s);
   }

   public ServerChannel getServerChannel() {
      return this.channel;
   }
}
