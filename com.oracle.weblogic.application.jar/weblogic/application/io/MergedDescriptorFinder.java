package weblogic.application.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.Map;
import weblogic.deploy.common.Debug;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.URLSource;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.SingleItemEnumeration;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public final class MergedDescriptorFinder extends AbstractClassFinder {
   private static final String PROTOCOL_NAME = "merged-descriptor";
   private final Map descriptors;

   public MergedDescriptorFinder(Map m) {
      this.descriptors = m;
   }

   public Source getSource(final String name) {
      DescriptorBean dd;
      synchronized(this.descriptors) {
         dd = (DescriptorBean)this.descriptors.get(name);
      }

      if (dd != null) {
         try {
            URL localurl = (URL)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Object run() throws MalformedURLException {
                  return new URL("merged-descriptor", (String)null, -1, name, MergedDescriptorFinder.this.new MyURLStreamHandler());
               }
            });
            return new URLSource(localurl);
         } catch (PrivilegedActionException var6) {
            if (Debug.isServiceDebugEnabled()) {
               Debug.deploymentDebug("MergedDescriptorFinder.getSource : " + var6.getCause().getMessage());
            }
         } catch (IOException var7) {
         }
      }

      return null;
   }

   public Enumeration getSources(String name) {
      Source s = this.getSource(name);
      return (Enumeration)(s == null ? new EmptyEnumerator() : new SingleItemEnumeration(s));
   }

   public Source getClassSource(String name) {
      return null;
   }

   public String getClassPath() {
      return "";
   }

   public ClassFinder getManifestFinder() {
      return NullClassFinder.NULL_FINDER;
   }

   public Enumeration entries() {
      return EmptyEnumerator.EMPTY;
   }

   public void close() {
      synchronized(this.descriptors) {
         this.descriptors.clear();
      }
   }

   static class DescriptorURLConnection extends URLConnection {
      public static final EditableDescriptorManager descMgr = new EditableDescriptorManager();
      private DescriptorBean descriptor;

      public DescriptorURLConnection(URL url, DescriptorBean bean) {
         super(url);
         this.descriptor = bean;
      }

      public void connect() throws IOException {
      }

      public InputStream getInputStream() throws IOException {
         ByteArrayOutputStream os = new ByteArrayOutputStream();
         descMgr.writeDescriptorAsXML(this.descriptor.getDescriptor(), os);
         return new UnsyncByteArrayInputStream(os.toByteArray());
      }
   }

   private final class MyURLStreamHandler extends URLStreamHandler {
      private MyURLStreamHandler() {
      }

      protected URLConnection openConnection(URL u) throws IOException {
         DescriptorBean bean;
         synchronized(MergedDescriptorFinder.this.descriptors) {
            bean = (DescriptorBean)MergedDescriptorFinder.this.descriptors.get(u.getFile());
         }

         if (bean == null) {
            throw new IOException("Merged descriptor not found " + u.getFile());
         } else {
            return new DescriptorURLConnection(u, bean);
         }
      }

      protected String toExternalForm(URL u) {
         return "merged-descriptor:/" + u.getFile();
      }

      // $FF: synthetic method
      MyURLStreamHandler(Object x1) {
         this();
      }
   }
}
