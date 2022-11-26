package com.bea.core.repackaged.springframework.cglib.transform;

import com.bea.core.repackaged.springframework.asm.Attribute;
import com.bea.core.repackaged.springframework.asm.ClassReader;
import com.bea.core.repackaged.springframework.cglib.core.ClassNameReader;
import com.bea.core.repackaged.springframework.cglib.core.DebuggingClassWriter;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public abstract class AbstractTransformTask extends AbstractProcessTask {
   private static final int ZIP_MAGIC = 1347093252;
   private static final int CLASS_MAGIC = -889275714;
   private boolean verbose;

   public void setVerbose(boolean verbose) {
      this.verbose = verbose;
   }

   protected abstract ClassTransformer getClassTransformer(String[] var1);

   protected Attribute[] attributes() {
      return null;
   }

   protected void processFile(File file) throws Exception {
      if (this.isClassFile(file)) {
         this.processClassFile(file);
      } else if (this.isJarFile(file)) {
         this.processJarFile(file);
      } else {
         this.log("ignoring " + file.toURI(), 1);
      }

   }

   private void processClassFile(File file) throws Exception, FileNotFoundException, IOException, MalformedURLException {
      ClassReader reader = getClassReader(file);
      String[] name = ClassNameReader.getClassInfo(reader);
      DebuggingClassWriter w = new DebuggingClassWriter(2);
      ClassTransformer t = this.getClassTransformer(name);
      if (t != null) {
         if (this.verbose) {
            this.log("processing " + file.toURI());
         }

         (new TransformingClassGenerator(new ClassReaderGenerator(getClassReader(file), this.attributes(), this.getFlags()), t)).generateClass(w);
         FileOutputStream fos = new FileOutputStream(file);

         try {
            fos.write(w.toByteArray());
         } finally {
            fos.close();
         }
      }

   }

   protected int getFlags() {
      return 0;
   }

   private static ClassReader getClassReader(File file) throws Exception {
      InputStream in = new BufferedInputStream(new FileInputStream(file));

      ClassReader var3;
      try {
         ClassReader r = new ClassReader(in);
         var3 = r;
      } finally {
         in.close();
      }

      return var3;
   }

   protected boolean isClassFile(File file) throws IOException {
      return this.checkMagic(file, -889275714L);
   }

   protected void processJarFile(File file) throws Exception {
      if (this.verbose) {
         this.log("processing " + file.toURI());
      }

      File tempFile = File.createTempFile(file.getName(), (String)null, new File(file.getAbsoluteFile().getParent()));

      try {
         ZipInputStream zip = new ZipInputStream(new FileInputStream(file));

         try {
            FileOutputStream fout = new FileOutputStream(tempFile);

            try {
               ZipOutputStream out = new ZipOutputStream(fout);

               ZipEntry entry;
               while((entry = zip.getNextEntry()) != null) {
                  byte[] bytes = this.getBytes(zip);
                  if (!entry.isDirectory()) {
                     DataInputStream din = new DataInputStream(new ByteArrayInputStream(bytes));
                     if (din.readInt() == -889275714) {
                        bytes = this.process(bytes);
                     } else if (this.verbose) {
                        this.log("ignoring " + entry.toString());
                     }
                  }

                  ZipEntry outEntry = new ZipEntry(entry.getName());
                  outEntry.setMethod(entry.getMethod());
                  outEntry.setComment(entry.getComment());
                  outEntry.setSize((long)bytes.length);
                  if (outEntry.getMethod() == 0) {
                     CRC32 crc = new CRC32();
                     crc.update(bytes);
                     outEntry.setCrc(crc.getValue());
                     outEntry.setCompressedSize((long)bytes.length);
                  }

                  out.putNextEntry(outEntry);
                  out.write(bytes);
                  out.closeEntry();
                  zip.closeEntry();
               }

               out.close();
            } finally {
               fout.close();
            }
         } finally {
            zip.close();
         }

         if (!file.delete()) {
            throw new IOException("can not delete " + file);
         }

         File newFile = new File(tempFile.getAbsolutePath());
         if (!newFile.renameTo(file)) {
            throw new IOException("can not rename " + tempFile + " to " + file);
         }
      } finally {
         tempFile.delete();
      }

   }

   private byte[] process(byte[] bytes) throws Exception {
      ClassReader reader = new ClassReader(new ByteArrayInputStream(bytes));
      String[] name = ClassNameReader.getClassInfo(reader);
      DebuggingClassWriter w = new DebuggingClassWriter(2);
      ClassTransformer t = this.getClassTransformer(name);
      if (t != null) {
         if (this.verbose) {
            this.log("processing " + name[0]);
         }

         (new TransformingClassGenerator(new ClassReaderGenerator(new ClassReader(new ByteArrayInputStream(bytes)), this.attributes(), this.getFlags()), t)).generateClass(w);
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         out.write(w.toByteArray());
         return out.toByteArray();
      } else {
         return bytes;
      }
   }

   private byte[] getBytes(ZipInputStream zip) throws IOException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      InputStream in = new BufferedInputStream(zip);

      int b;
      while((b = in.read()) != -1) {
         bout.write(b);
      }

      return bout.toByteArray();
   }

   private boolean checkMagic(File file, long magic) throws IOException {
      DataInputStream in = new DataInputStream(new FileInputStream(file));

      boolean var6;
      try {
         int m = in.readInt();
         var6 = magic == (long)m;
      } finally {
         in.close();
      }

      return var6;
   }

   protected boolean isJarFile(File file) throws IOException {
      return this.checkMagic(file, 1347093252L);
   }
}
