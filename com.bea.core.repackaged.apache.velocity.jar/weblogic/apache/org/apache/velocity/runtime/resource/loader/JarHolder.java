package weblogic.apache.org.apache.velocity.runtime.resource.loader;

import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;

public class JarHolder {
   private String urlpath = null;
   private JarFile theJar = null;
   private JarURLConnection conn = null;
   private RuntimeServices rsvc = null;

   public JarHolder(RuntimeServices rs, String urlpath) {
      this.rsvc = rs;
      this.urlpath = urlpath;
      this.init();
      this.rsvc.info("  JarHolder : initialized JAR: " + urlpath);
   }

   public void init() {
      try {
         this.rsvc.info("  JarHolder : attempting to connect to " + this.urlpath);
         URL url = new URL(this.urlpath);
         this.conn = (JarURLConnection)url.openConnection();
         this.conn.setAllowUserInteraction(false);
         this.conn.setDoInput(true);
         this.conn.setDoOutput(false);
         this.conn.connect();
         this.theJar = this.conn.getJarFile();
      } catch (Exception var2) {
         this.rsvc.error("  JarHolder : error establishing connection to JAR " + var2);
      }

   }

   public void close() {
      try {
         this.theJar.close();
      } catch (Exception var2) {
         this.rsvc.error("  JarHolder : error Closing JAR the file " + var2);
      }

      this.theJar = null;
      this.conn = null;
      this.rsvc.info("  JarHolder : JAR file closed");
   }

   public InputStream getResource(String theentry) throws ResourceNotFoundException {
      InputStream data = null;

      try {
         JarEntry entry = this.theJar.getJarEntry(theentry);
         if (entry != null) {
            data = this.theJar.getInputStream(entry);
         }

         return data;
      } catch (Exception var4) {
         this.rsvc.error("  JarHolder : getResource() error : exception : " + var4);
         throw new ResourceNotFoundException(var4.getMessage());
      }
   }

   public Hashtable getEntries() {
      Hashtable allEntries = new Hashtable(559);
      Enumeration all = this.theJar.entries();

      while(all.hasMoreElements()) {
         JarEntry je = (JarEntry)all.nextElement();
         if (!je.isDirectory()) {
            allEntries.put(je.getName(), this.urlpath);
         }
      }

      return allEntries;
   }

   public String getUrlPath() {
      return this.urlpath;
   }
}
