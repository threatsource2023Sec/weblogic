package org.stringtemplate.v4;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.compiler.CompiledST;
import org.stringtemplate.v4.compiler.STException;
import org.stringtemplate.v4.misc.ErrorType;
import org.stringtemplate.v4.misc.Misc;

public class STGroupDir extends STGroup {
   public String groupDirName;
   public URL root;

   public STGroupDir(String dirName) {
      this(dirName, '<', '>');
   }

   public STGroupDir(String dirName, char delimiterStartChar, char delimiterStopChar) {
      super(delimiterStartChar, delimiterStopChar);
      this.groupDirName = dirName;
      File dir = new File(dirName);
      if (dir.exists() && dir.isDirectory()) {
         try {
            this.root = dir.toURI().toURL();
         } catch (MalformedURLException var6) {
            throw new STException("can't load dir " + dirName, var6);
         }

         if (verbose) {
            System.out.println("STGroupDir(" + dirName + ") found at " + this.root);
         }
      } else {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         this.root = cl.getResource(dirName);
         if (this.root == null) {
            cl = this.getClass().getClassLoader();
            this.root = cl.getResource(dirName);
         }

         if (verbose) {
            System.out.println("STGroupDir(" + dirName + ") found via CLASSPATH at " + this.root);
         }

         if (this.root == null) {
            throw new IllegalArgumentException("No such directory: " + dirName);
         }
      }

   }

   public STGroupDir(String dirName, String encoding) {
      this(dirName, encoding, '<', '>');
   }

   public STGroupDir(String dirName, String encoding, char delimiterStartChar, char delimiterStopChar) {
      this(dirName, delimiterStartChar, delimiterStopChar);
      this.encoding = encoding;
   }

   public STGroupDir(URL root, String encoding, char delimiterStartChar, char delimiterStopChar) {
      super(delimiterStartChar, delimiterStopChar);
      this.groupDirName = (new File(root.getFile())).getName();
      this.root = root;
      this.encoding = encoding;
   }

   public void importTemplates(Token fileNameToken) {
      String msg = "import illegal in group files embedded in STGroupDirs; import " + fileNameToken.getText() + " in STGroupDir " + this.getName();
      throw new UnsupportedOperationException(msg);
   }

   protected CompiledST load(String name) {
      if (verbose) {
         System.out.println("STGroupDir.load(" + name + ")");
      }

      String parent = Misc.getParent(name);
      String prefix = Misc.getPrefix(name);
      URL groupFileURL = null;

      try {
         groupFileURL = new URL(this.root + parent + GROUP_FILE_EXTENSION);
      } catch (MalformedURLException var19) {
         this.errMgr.internalError((ST)null, "bad URL: " + this.root + parent + GROUP_FILE_EXTENSION, var19);
         return null;
      }

      InputStream is = null;

      label91: {
         CompiledST var8;
         try {
            is = groupFileURL.openStream();
            break label91;
         } catch (IOException var20) {
            String unqualifiedName = Misc.getFileName(name);
            var8 = this.loadTemplateFile(prefix, unqualifiedName + TEMPLATE_FILE_EXTENSION);
         } finally {
            try {
               if (is != null) {
                  is.close();
               }
            } catch (IOException var18) {
               this.errMgr.internalError((ST)null, "can't close template file stream " + name, var18);
            }

         }

         return var8;
      }

      this.loadGroupFile(prefix, this.root + parent + GROUP_FILE_EXTENSION);
      return this.rawGetTemplate(name);
   }

   public CompiledST loadTemplateFile(String prefix, String unqualifiedFileName) {
      if (verbose) {
         System.out.println("loadTemplateFile(" + unqualifiedFileName + ") in groupdir " + "from " + this.root + " prefix=" + prefix);
      }

      URL f = null;

      try {
         f = new URL(this.root + prefix + unqualifiedFileName);
      } catch (MalformedURLException var6) {
         this.errMgr.runTimeError((Interpreter)null, (InstanceScope)null, ErrorType.INVALID_TEMPLATE_NAME, (Throwable)var6, this.root + unqualifiedFileName);
         return null;
      }

      ANTLRInputStream fs;
      try {
         fs = new ANTLRInputStream(f.openStream(), this.encoding);
         fs.name = unqualifiedFileName;
      } catch (IOException var7) {
         if (verbose) {
            System.out.println(this.root + "/" + unqualifiedFileName + " doesn't exist");
         }

         return null;
      }

      return this.loadTemplateFile(prefix, unqualifiedFileName, fs);
   }

   public String getName() {
      return this.groupDirName;
   }

   public String getFileName() {
      return this.root.getFile();
   }

   public URL getRootDirURL() {
      return this.root;
   }
}
