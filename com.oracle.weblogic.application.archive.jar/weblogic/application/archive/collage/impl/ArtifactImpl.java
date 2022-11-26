package weblogic.application.archive.collage.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import weblogic.application.archive.collage.Artifact;
import weblogic.application.archive.collage.Directory;

public class ArtifactImpl extends NodeImpl implements Artifact {
   Streamer streamer;

   private ArtifactImpl(ContainerImpl parent, String name, long time, Streamer aStreamer, String source) {
      super(parent, name, time, source);
      this.streamer = aStreamer;
   }

   public static ArtifactImpl createFileArtifact(Directory parent, String name, File file, String source) {
      Streamer s = createFileStreamer(file);
      return new ArtifactImpl((ContainerImpl)parent, name, file.lastModified(), s, source);
   }

   protected static Streamer createFileStreamer(final File file) {
      Streamer s = new Streamer() {
         public InputStream getInputStream() throws FileNotFoundException {
            return new FileInputStream(file);
         }

         public long getSize() {
            return file.length();
         }

         public long getTime() {
            return file.lastModified();
         }
      };
      return s;
   }

   public static Artifact createJarEntryArtifact(Directory parent, String name, final JarFile jf, final String entryName, String source) {
      Streamer s = new Streamer() {
         JarEntry entry = jf.getJarEntry(entryName);

         public InputStream getInputStream() throws IOException {
            return jf.getInputStream(this.entry);
         }

         public long getSize() {
            return this.entry.getSize();
         }

         public long getTime() {
            return this.entry.getTime();
         }
      };
      return new ArtifactImpl((ContainerImpl)parent, name, s.getTime(), s, source);
   }

   public InputStream getInputStream() throws IOException {
      return this.streamer.getInputStream();
   }

   public long getSize() {
      return this.streamer.getSize();
   }

   public long getTime() {
      return this.streamer.getTime();
   }
}
