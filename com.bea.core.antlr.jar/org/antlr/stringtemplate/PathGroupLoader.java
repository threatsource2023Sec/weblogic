package org.antlr.stringtemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;

public class PathGroupLoader implements StringTemplateGroupLoader {
   protected List dirs = null;
   protected StringTemplateErrorListener errors = null;
   String fileCharEncoding = System.getProperty("file.encoding");

   public PathGroupLoader(StringTemplateErrorListener errors) {
      this.errors = errors;
   }

   public PathGroupLoader(String dirStr, StringTemplateErrorListener errors) {
      this.errors = errors;

      String dir;
      for(StringTokenizer tokenizer = new StringTokenizer(dirStr, ":", false); tokenizer.hasMoreElements(); this.dirs.add(dir)) {
         dir = (String)tokenizer.nextElement();
         if (this.dirs == null) {
            this.dirs = new ArrayList();
         }
      }

   }

   public StringTemplateGroup loadGroup(String groupName, Class templateLexer, StringTemplateGroup superGroup) {
      StringTemplateGroup group = null;
      BufferedReader br = null;
      Class lexer = AngleBracketTemplateLexer.class;
      if (templateLexer != null) {
         lexer = templateLexer;
      }

      Object var7;
      try {
         br = this.locate(groupName + ".stg");
         if (br != null) {
            group = new StringTemplateGroup(br, lexer, this.errors, superGroup);
            br.close();
            br = null;
            return group;
         }

         this.error("no such group file " + groupName + ".stg");
         var7 = null;
      } catch (IOException var18) {
         this.error("can't load group " + groupName, var18);
         return group;
      } finally {
         if (br != null) {
            try {
               br.close();
            } catch (IOException var17) {
               this.error("Cannot close template group file: " + groupName + ".stg", var17);
            }
         }

      }

      return (StringTemplateGroup)var7;
   }

   public StringTemplateGroup loadGroup(String groupName, StringTemplateGroup superGroup) {
      return this.loadGroup(groupName, (Class)null, superGroup);
   }

   public StringTemplateGroup loadGroup(String groupName) {
      return this.loadGroup(groupName, (StringTemplateGroup)null);
   }

   public StringTemplateGroupInterface loadInterface(String interfaceName) {
      StringTemplateGroupInterface I = null;

      try {
         BufferedReader br = this.locate(interfaceName + ".sti");
         if (br == null) {
            this.error("no such interface file " + interfaceName + ".sti");
            return null;
         }

         I = new StringTemplateGroupInterface(br, this.errors);
      } catch (IOException var4) {
         this.error("can't load interface " + interfaceName, var4);
      }

      return I;
   }

   protected BufferedReader locate(String name) throws IOException {
      for(int i = 0; i < this.dirs.size(); ++i) {
         String dir = (String)this.dirs.get(i);
         String fileName = dir + "/" + name;
         if ((new File(fileName)).exists()) {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = this.getInputStreamReader(fis);
            return new BufferedReader(isr);
         }
      }

      return null;
   }

   protected InputStreamReader getInputStreamReader(InputStream in) {
      InputStreamReader isr = null;

      try {
         isr = new InputStreamReader(in, this.fileCharEncoding);
      } catch (UnsupportedEncodingException var4) {
         this.error("Invalid file character encoding: " + this.fileCharEncoding);
      }

      return isr;
   }

   public String getFileCharEncoding() {
      return this.fileCharEncoding;
   }

   public void setFileCharEncoding(String fileCharEncoding) {
      this.fileCharEncoding = fileCharEncoding;
   }

   public void error(String msg) {
      this.error(msg, (Exception)null);
   }

   public void error(String msg, Exception e) {
      if (this.errors != null) {
         this.errors.error(msg, e);
      } else {
         System.err.println("StringTemplate: " + msg);
         if (e != null) {
            e.printStackTrace();
         }
      }

   }
}
