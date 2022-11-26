package weblogic.deploy.service.datatransferhandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import weblogic.deploy.service.DataStream;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.MultiDataStream;
import weblogic.deploy.service.TargetFileDataStream;

class DataStreamFactory {
   public static final int UNKNOWN_LENGTH = -1;
   public static final String UNKNOWN_LOCATION = "unknwown";

   static DataStream createDataStream(final String name, final InputStream is, final boolean isZip) {
      return new DataStream() {
         public InputStream getInputStream() throws IOException {
            return is;
         }

         public void close() {
         }

         public String getName() {
            return name;
         }

         public boolean isZip() {
            return isZip;
         }
      };
   }

   static FileDataStream createFileDataStream(String location, boolean isZip) {
      return createFileDataStream((String)null, (String)location, isZip);
   }

   static FileDataStream createFileDataStream(String name, String location, boolean isZip) {
      return createFileDataStream(name, new File(location), isZip);
   }

   static FileDataStream createFileDataStream(File file, boolean isZip) {
      return createFileDataStream((String)null, (File)file, isZip);
   }

   static FileDataStream createFileDataStream(String name, File file, boolean isZip) {
      return createFileDataStream(name, file, (String)null, isZip);
   }

   static FileDataStream createFileDataStream(final String name, final File file, final String target, final boolean isZip) {
      return new TargetFileDataStream() {
         public String getName() {
            return name == null ? file.getName() : name;
         }

         public File getFile() {
            return file;
         }

         public String getTarget() {
            return target;
         }

         public int getLength() throws IOException {
            this.validateFile();
            return (int)file.length();
         }

         public boolean isZip() {
            return isZip;
         }

         public InputStream getInputStream() throws IOException {
            this.validateFile();
            return new FileInputStream(file);
         }

         public void close() {
         }

         private void validateFile() throws IOException {
            if (!file.exists()) {
               throw new IOException("File '" + file.getAbsolutePath() + "' does not exist");
            }
         }
      };
   }

   static MultiDataStream createMultiDataStream() {
      return new MultiDataStreamImpl();
   }
}
