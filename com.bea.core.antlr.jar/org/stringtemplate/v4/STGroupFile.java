package org.stringtemplate.v4;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.stringtemplate.v4.compiler.CompiledST;
import org.stringtemplate.v4.compiler.STException;
import org.stringtemplate.v4.misc.ErrorType;
import org.stringtemplate.v4.misc.Misc;

public class STGroupFile extends STGroup {
   public String fileName;
   public URL url;
   protected boolean alreadyLoaded;

   public STGroupFile(String fileName) {
      this(fileName, '<', '>');
   }

   public STGroupFile(String fileName, char delimiterStartChar, char delimiterStopChar) {
      super(delimiterStartChar, delimiterStopChar);
      this.alreadyLoaded = false;
      if (!fileName.endsWith(GROUP_FILE_EXTENSION)) {
         throw new IllegalArgumentException("Group file names must end in .stg: " + fileName);
      } else {
         File f = new File(fileName);
         if (f.exists()) {
            try {
               this.url = f.toURI().toURL();
            } catch (MalformedURLException var6) {
               throw new STException("can't load group file " + fileName, var6);
            }

            if (verbose) {
               System.out.println("STGroupFile(" + fileName + ") == file " + f.getAbsolutePath());
            }
         } else {
            this.url = this.getURL(fileName);
            if (this.url == null) {
               throw new IllegalArgumentException("No such group file: " + fileName);
            }

            if (verbose) {
               System.out.println("STGroupFile(" + fileName + ") == url " + this.url);
            }
         }

         this.fileName = fileName;
      }
   }

   public STGroupFile(String fullyQualifiedFileName, String encoding) {
      this(fullyQualifiedFileName, encoding, '<', '>');
   }

   public STGroupFile(String fullyQualifiedFileName, String encoding, char delimiterStartChar, char delimiterStopChar) {
      this(fullyQualifiedFileName, delimiterStartChar, delimiterStopChar);
      this.encoding = encoding;
   }

   public STGroupFile(URL url, String encoding, char delimiterStartChar, char delimiterStopChar) {
      super(delimiterStartChar, delimiterStopChar);
      this.alreadyLoaded = false;
      this.url = url;
      this.encoding = encoding;

      try {
         String urlString = url.toString();
         if (urlString.startsWith("jar:file:")) {
            urlString = urlString.substring(4);
         }

         this.fileName = (new File(new URI(urlString))).getAbsolutePath();
      } catch (Exception var6) {
      }

   }

   public boolean isDictionary(String name) {
      if (!this.alreadyLoaded) {
         this.load();
      }

      return super.isDictionary(name);
   }

   public boolean isDefined(String name) {
      if (!this.alreadyLoaded) {
         this.load();
      }

      return super.isDefined(name);
   }

   public synchronized void unload() {
      super.unload();
      this.alreadyLoaded = false;
   }

   protected CompiledST load(String name) {
      if (!this.alreadyLoaded) {
         this.load();
      }

      return this.rawGetTemplate(name);
   }

   public void load() {
      if (!this.alreadyLoaded) {
         this.alreadyLoaded = true;
         if (verbose) {
            System.out.println("loading group file " + this.url.toString());
         }

         this.loadGroupFile("/", this.url.toString());
         if (verbose) {
            System.out.println("found " + this.templates.size() + " templates in " + this.url.toString() + " = " + this.templates.keySet());
         }

      }
   }

   public String show() {
      if (!this.alreadyLoaded) {
         this.load();
      }

      return super.show();
   }

   public String getName() {
      return Misc.getFileNameNoSuffix(this.fileName);
   }

   public String getFileName() {
      return this.fileName;
   }

   public URL getRootDirURL() {
      String parent = Misc.stripLastPathElement(this.url.toString());

      try {
         return new URL(parent);
      } catch (MalformedURLException var3) {
         this.errMgr.runTimeError((Interpreter)null, (InstanceScope)null, ErrorType.INVALID_TEMPLATE_NAME, (Throwable)var3, parent);
         return null;
      }
   }
}
