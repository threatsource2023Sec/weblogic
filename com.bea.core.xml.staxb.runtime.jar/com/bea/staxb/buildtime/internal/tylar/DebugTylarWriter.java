package com.bea.staxb.buildtime.internal.tylar;

import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.bts.BindingFileUtils;
import com.bea.staxb.buildtime.internal.joust.JavaOutputStream;
import com.bea.staxb.buildtime.internal.joust.SourceJavaOutputStream;
import com.bea.staxb.buildtime.internal.joust.WriterFactory;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Map;

public class DebugTylarWriter implements TylarWriter, WriterFactory {
   private PrintWriter mOut;
   private JavaOutputStream mJoust;
   private XmlOptions mOptions;

   public DebugTylarWriter() {
      this(new PrintWriter(System.out, true));
   }

   public DebugTylarWriter(PrintWriter out) {
      this.mJoust = new SourceJavaOutputStream(this);
      this.mOut = out;
      this.mOptions = new XmlOptions();
      this.mOptions.setSavePrettyPrint();
   }

   public void write(Tylar t) throws IOException {
      try {
         this.mOut.println("==== Dumping Type Library contents... =================");
         this.mOut.println("location = " + Arrays.toString(t.getLocations()));
         this.mOut.println("description = " + t.getDescription());
         BindingFile[] bfs = t.getBindingFiles();
         int i = 0;

         while(true) {
            if (i >= bfs.length) {
               if (!(t instanceof RuntimeTylar)) {
                  SchemaDocument[] xsds = t.getSchemas();

                  for(int i = 0; i < xsds.length; ++i) {
                     this.mOut.println("---- Schema -------------------------------------------");
                     this.writeSchema(xsds[i], (String)null, (Map)null);
                  }
               }

               this.mOut.println("==== End Type Library contents ========================");
               break;
            }

            this.mOut.println("---- Binding File -------------------------------------");
            this.writeBindingFile(bfs[i]);
            ++i;
         }
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new IOException(var5.getMessage());
      }

      this.mOut.flush();
   }

   public void writeBindingFile(BindingFile bf) throws IOException {
      BindingFileUtils.write(bf).save(this.mOut, this.mOptions);
   }

   public void writeSchema(SchemaDocument xsd, String fp, Map tns2prefix) throws IOException {
      if (tns2prefix != null) {
         this.mOptions.setSaveSuggestedPrefixes(tns2prefix);
      }

      xsd.save(this.mOut, this.mOptions);
   }

   public void writeSchemaTypeSystem(SchemaTypeSystem sts) throws IOException {
   }

   public JavaOutputStream getJavaOutputStream() {
      return this.mJoust;
   }

   public void close() {
      this.mOut.flush();
   }

   public Writer createWriter(String packageName, String className) throws IOException {
      return this.mOut;
   }

   public static void main(String[] args) {
      try {
         TylarLoader loader = DefaultTylarLoader.getInstance();
         Tylar tylar = loader.load((ClassLoader)(new URLClassLoader(new URL[]{(new File(args[0])).toURL()})));
         (new DebugTylarWriter()).write(tylar);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      System.out.flush();
   }
}
