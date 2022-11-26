package weblogic.common.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;
import java.io.OptionalDataException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import weblogic.common.WLObjectInput;
import weblogic.core.base.api.ClassLoaderService;
import weblogic.rmi.extensions.server.ColocatedStream;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.AssertionError;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.DelegatingInputStream;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.utils.io.Replacer;

public class WLObjectInputStream extends FilteringObjectInputStream implements WLObjectInput {
   private final DelegatingInputStream delegate;
   private Replacer replacer;
   private static ClassLoaderService classLoaderService = getClassLoaderService();

   public WLObjectInputStream(InputStream is) throws IOException {
      this(new DelegatingInputStream(is));
   }

   private WLObjectInputStream(DelegatingInputStream delegate) throws IOException {
      super(delegate);
      this.replacer = null;

      try {
         this.enableResolveObject(true);
      } catch (SecurityException var3) {
      }

      this.delegate = delegate;
   }

   public final void setDelegate(InputStream in) {
      this.delegate.setDelegate(in);
   }

   protected final Class resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
      String annotation = null;
      this.checkLegacyBlacklistIfNeeded(v.getName());

      try {
         annotation = this.readUTF();
      } catch (OptionalDataException var4) {
      }

      Class cls = classLoaderService.loadClass(v.getName(), annotation, this.useInterAppClassLoader());
      if (!(this instanceof ColocatedStream)) {
         this.validateReturnType(cls);
      }

      return cls;
   }

   private static ClassLoaderService getClassLoaderService() {
      ClassLoaderService cls = (ClassLoaderService)GlobalServiceLocator.getServiceLocator().getService(ClassLoaderService.class, "Application", new Annotation[0]);
      if (cls == null) {
         throw new RuntimeException("Implementation of weblogic.common.internal.ClassLoaderService with name of Application not found on classpath");
      } else {
         return cls;
      }
   }

   protected boolean useInterAppClassLoader() {
      return false;
   }

   protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      String annotation = null;

      try {
         annotation = this.readUTF();
      } catch (OptionalDataException var4) {
      }

      return ProxyClassResolver.resolveProxyClass(interfaces, annotation, (String)null, this.useInterAppClassLoader());
   }

   public String readUTF() throws IOException {
      return DataIO.readUTF(this);
   }

   public final void setReplacer(Replacer replacer) {
      this.replacer = replacer;
   }

   protected Object resolveObject(Object obj) throws IOException {
      return this.replacer == null ? obj : this.replacer.resolveObject(obj);
   }

   protected final void readStreamHeader() throws IOException {
   }

   public final void reset() throws IOException {
   }

   public final void close() throws IOException {
   }

   public Object readObjectWL() throws IOException, ClassNotFoundException {
      return this.readObject();
   }

   public final Object readObjectWLValidated(Class returnType) throws IOException, ClassNotFoundException {
      return this.readObjectValidated(returnType);
   }

   public final String readString() throws IOException {
      byte b = this.readByte();
      return b == 112 ? null : this.readUTF();
   }

   public final Date readDate() throws IOException {
      try {
         return (Date)this.readObjectValidated(Date.class);
      } catch (ClassNotFoundException var2) {
         throw new AssertionError("Couldn't find class", var2);
      }
   }

   public final ArrayList readArrayList() throws IOException, ClassNotFoundException {
      return (ArrayList)this.readObject();
   }

   public final Properties readProperties() throws IOException {
      try {
         return (Properties)this.readObjectValidated(Properties.class);
      } catch (ClassNotFoundException var2) {
         throw new AssertionError("Couldn't find class", var2);
      }
   }

   public final byte[] readBytes() throws IOException {
      try {
         return (byte[])((byte[])this.readObjectValidated(byte[].class));
      } catch (ClassNotFoundException var2) {
         throw new AssertionError("Couldn't find class", var2);
      }
   }

   public final Object[] readArrayOfObjects() throws IOException, ClassNotFoundException {
      return (Object[])((Object[])this.readObject());
   }

   public Object readImmutable() throws IOException, ClassNotFoundException {
      return this.readObject();
   }

   public final String readAbbrevString() throws IOException {
      return this.readString();
   }
}
