package weblogic.application.archive.navigator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import weblogic.application.archive.ApplicationArchiveEntry;

public abstract class Node implements ApplicationArchiveEntry, weblogic.application.archive.collage.Node {
   protected Node parent;
   protected String shortName;
   protected File discLocation;
   protected File virtualLocation;
   protected URL url;

   public abstract File asFile();

   public boolean isDirectory() {
      return false;
   }

   public Certificate[] getCertificates() {
      return null;
   }

   public CodeSigner[] getCodeSigners() {
      return null;
   }

   protected Node(Node parent, String name) {
      this(new File(parent.discLocation, name), new File(parent.virtualLocation, name));
      this.parent = parent;
      this.url = parent.url;
   }

   protected Node(File discLocation, File virtualLocation) {
      this.shortName = discLocation.getName();
      this.discLocation = discLocation;
      this.virtualLocation = virtualLocation;

      try {
         this.url = discLocation.toURI().toURL();
      } catch (MalformedURLException var4) {
         throw new AssertionError(var4);
      }
   }

   public String getName() {
      return this.getShortName();
   }

   public String getShortName() {
      return this.shortName;
   }

   public File getFile(Node root) {
      return new File(root == this.parent ? new File("") : this.parent.getFile(root), this.shortName);
   }

   protected StringBuilder toString(String indent, StringBuilder sb) {
      sb.append(indent).append(this.shortName).append(" -> ").append(this.getDiscLocationURL()).append("\n");
      return sb;
   }

   protected URL getDiscLocationURL() {
      try {
         return new URL(this.url, this.discLocation.getPath());
      } catch (MalformedURLException var2) {
         throw new AssertionError(var2);
      }
   }

   public String toString() {
      return super.toString() + "\n" + this.toString("", new StringBuilder()).toString();
   }

   public abstract InputStream getInputStream() throws IOException;

   protected Node getNode(String[] path, int i, int j) {
      return i == j ? this : null;
   }

   public String getPath(DirectoryNode rootNode) {
      return this.getFile(rootNode).getPath().substring(1);
   }

   public File getDiscLocation() {
      return this.discLocation;
   }

   public File getFile() {
      return null;
   }

   public String getSource() {
      return null;
   }

   public URL getURL() throws IOException {
      return createURL("wlsaa", this.getInputStream());
   }

   public static URL createURL(String customProtocol, final InputStream is) throws IOException {
      if (is == null) {
         throw new IllegalArgumentException("Missing input stream.");
      } else if (customProtocol != null && customProtocol.trim().length() != 0 && !customProtocol.contains(":")) {
         URLStreamHandler streamHandler = new URLStreamHandler() {
            protected URLConnection openConnection(URL url) throws IOException {
               return this.openConnection(url, (Proxy)null);
            }

            protected URLConnection openConnection(URL url, Proxy proxy) throws IOException {
               return new URLConnection(url) {
                  public void connect() throws IOException {
                  }

                  public InputStream getInputStream() throws IOException {
                     return is;
                  }

                  public URL getURL() {
                     return this.url;
                  }
               };
            }

            protected void parseURL(URL arg0, String arg1, int arg2, int arg3) {
            }
         };
         return new URL((URL)null, customProtocol + ":", streamHandler);
      } else {
         throw new IllegalArgumentException("Invalid custom protocol value: " + customProtocol);
      }
   }
}
