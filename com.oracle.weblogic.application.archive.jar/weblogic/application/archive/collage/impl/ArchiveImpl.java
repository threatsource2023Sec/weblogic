package weblogic.application.archive.collage.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import weblogic.application.archive.collage.Archive;

public class ArchiveImpl extends ContainerImpl implements Archive {
   Streamer streamer;

   private ArchiveImpl(ContainerImpl parent, String name, long time, Streamer aStreamer, String source) {
      super(parent, name, time, source);
      this.streamer = aStreamer;
   }

   public static ArchiveImpl createArchive(ContainerImpl parent, String name, File file, String source) {
      Streamer s = ArtifactImpl.createFileStreamer(file);
      return new ArchiveImpl(parent, name, s.getTime(), s, source);
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
