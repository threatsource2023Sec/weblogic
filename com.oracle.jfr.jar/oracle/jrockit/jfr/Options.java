package oracle.jrockit.jfr;

import java.util.List;

public interface Options {
   List settingsFiles();

   String repository();

   boolean defaultRecording();

   boolean defaultRecordingToDisk();

   long defaultRecordingMaxSize();

   long defaultRecordingMaxAge();

   long maxChunkSize();

   int threadBufferSize();

   int globalBufferSize();

   int numGlobalBuffers();
}
