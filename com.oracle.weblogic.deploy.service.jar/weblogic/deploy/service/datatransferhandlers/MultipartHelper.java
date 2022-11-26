package weblogic.deploy.service.datatransferhandlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.deploy.common.Debug;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.MultiDataStream;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;

public class MultipartHelper {
   public static final String FILES_HEADER_NAME = "files_header";
   public static final String NO_FILES = "total_files";
   public static final String FILE_NAME = "name";
   public static final String FILE_PERMS = "perms";
   public static final String FILE_TYPE = "type";
   public static final String FILE_LENGTH = "length";
   public static final String DELIMITER = ";";
   public static final String DELIMITER1 = "=";
   public static final String EMPTY_RELATIVE_LOCATION = "NOT_SET";

   public static FileInfo[] parseFilesHeader(String value) {
      if (value == null) {
         throw new IllegalArgumentException("FilesHeader has invalid value");
      } else {
         String[] splits = StringUtils.splitCompletely(value, ";");
         if (isDebugEnabled()) {
            for(int i = 0; i < splits.length; ++i) {
               debugSay("splits[" + i + "] : " + splits[i]);
            }
         }

         FileInfo[] fileInfos = constructFileInfos(splits);
         if (isDebugEnabled()) {
            for(int i = 0; i < fileInfos.length; ++i) {
               debugSay("FileInfo[" + i + "] = " + fileInfos[i]);
            }
         }

         return fileInfos;
      }
   }

   private static int getNoOfFiles(String[] fileHeaderValues) {
      for(int i = 0; i < fileHeaderValues.length; ++i) {
         if (fileHeaderValues[i].startsWith("total_files")) {
            String[] splits = StringUtils.splitCompletely(fileHeaderValues[i], "=");
            if (splits.length != 2) {
               throw new IllegalArgumentException("Invalid number of files value");
            }

            return Integer.parseInt(splits[1]);
         }
      }

      throw new IllegalArgumentException("No numger of files sub header");
   }

   private static FileInfo[] constructFileInfos(String[] fileHeaderValues) {
      int noOfFiles = getNoOfFiles(fileHeaderValues);
      FileInfo[] fileInfos = new FileInfo[noOfFiles];
      int fileCounter = 0;
      FileInfo currentFileInfo = null;

      for(int i = 0; i < fileHeaderValues.length; ++i) {
         if (fileHeaderValues[i].indexOf("total_files") == -1) {
            if (fileHeaderValues[i].indexOf("name") != -1) {
               fileInfos[fileCounter] = new FileInfo();
               currentFileInfo = fileInfos[fileCounter++];
               currentFileInfo.setName(getValue(fileHeaderValues[i]));
            } else if (fileHeaderValues[i].indexOf("perms") != -1) {
               currentFileInfo.setPerms(getValue(fileHeaderValues[i]));
            } else if (fileHeaderValues[i].indexOf("type") != -1) {
               currentFileInfo.setType(getValue(fileHeaderValues[i]));
            } else if (fileHeaderValues[i].indexOf("length") != -1) {
               long size = Long.parseLong(getValue(fileHeaderValues[i]));
               currentFileInfo.setSize(size);
            }
         }
      }

      return fileInfos;
   }

   private static String getValue(String nameValuePair) {
      String[] splits = StringUtils.splitCompletely(nameValuePair, "=");
      if (splits.length >= 2 && splits.length <= 2) {
         return splits[1];
      } else {
         throw new IllegalArgumentException("Invalid name value pair : " + nameValuePair);
      }
   }

   static String constructFilesHeaderValue(List files) {
      if (files.isEmpty()) {
         throw new IllegalArgumentException(" files list is empty");
      } else {
         String[] fileNames = new String[files.size()];
         StringBuffer sb = new StringBuffer();
         sb.append("total_files").append("=");
         sb.append(fileNames.length).append(";");
         Iterator iter = files.iterator();

         while(iter.hasNext()) {
            File file = (File)iter.next();
            sb.append(constructEachFileHeaderValue(file));
         }

         return sb.toString();
      }
   }

   static String constructFilesHeaderValue(MultiDataStream stream) throws IOException {
      Iterator iter = stream.getDataStreams();
      boolean hasStreams = iter.hasNext();
      if (!hasStreams) {
         throw new IllegalArgumentException("files list is empty");
      } else {
         String[] fileNames = new String[stream.getSize()];
         StringBuffer sb = new StringBuffer();
         sb.append("total_files").append("=");
         sb.append(fileNames.length).append(";");

         while(iter.hasNext()) {
            FileDataStream eachStream = (FileDataStream)iter.next();
            sb.append(constructEachHeaderValue(eachStream));
         }

         return sb.toString();
      }
   }

   static String constructFilesHeaderValue(String[] files) {
      ArrayList fileObjs = new ArrayList();

      for(int i = 0; i < files.length; ++i) {
         fileObjs.add(new File(files[i]));
      }

      return constructFilesHeaderValue((List)fileObjs);
   }

   public static String constructEachHeaderValue(FileDataStream stream) throws IOException {
      StringBuffer sb = new StringBuffer();
      String fileName = stream.getName();
      String filePerms = FileUtils.getPosixFilePermissions(stream.getFile());
      String fileType = stream.isZip() ? "application/zip" : "text/plain";
      String fileSize = Long.toString((long)stream.getLength());
      sb.append("name").append("=");
      sb.append(fileName).append(";");
      if (filePerms != null) {
         sb.append("perms").append("=");
         sb.append(filePerms).append(";");
      }

      sb.append("type").append("=");
      sb.append(fileType).append(";");
      sb.append("length").append("=");
      sb.append(fileSize).append(";");
      return sb.toString();
   }

   private static String constructEachFileHeaderValue(File file) {
      if (!file.exists()) {
         throw new IllegalArgumentException(" File with name " + file.getName() + " does not exists");
      } else if (!file.isFile()) {
         throw new IllegalArgumentException(" File with name " + file.getName() + " is not a file");
      } else {
         StringBuffer sb = new StringBuffer();
         String fileName = file.getName();
         String filePerms = FileUtils.getPosixFilePermissions(file);
         String fileType = fileName.endsWith(".jar") ? "application/zip" : "text/plain";
         String fileSize = Long.toString(file.length());
         sb.append("name").append("=");
         sb.append(fileName).append(";");
         if (filePerms != null) {
            sb.append("perms").append("=");
            sb.append(filePerms).append(";");
         }

         sb.append("type").append("=");
         sb.append(fileType).append(";");
         sb.append("length").append("=");
         sb.append(fileSize).append(";");
         return sb.toString();
      }
   }

   public static void main(String[] a) {
      String filesHeaderValue = constructFilesHeaderValue(a);
      debugSay("filesHeaderValue : " + filesHeaderValue);
      parseFilesHeader(filesHeaderValue);
   }

   private static boolean isDebugEnabled() {
      return Debug.isServiceHttpDebugEnabled();
   }

   private static void debugSay(String s) {
      Debug.serviceHttpDebug(" +++ " + s);
   }

   static class FileInfo {
      private String name;
      private String perms;
      private String type;
      private long length;

      public String getType() {
         return this.type;
      }

      public String getName() {
         return this.name;
      }

      public String getPerms() {
         return this.perms;
      }

      public long getSize() {
         return this.length;
      }

      public void setType(String type) {
         this.type = type;
      }

      public boolean isZip() {
         return this.type.equals("application/zip");
      }

      public void setName(String name) {
         this.name = name;
      }

      public void setPerms(String perms) {
         this.perms = perms;
      }

      public void setSize(long length) {
         this.length = length;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("FileInfo(");
         sb.append("name=").append(this.name).append(",");
         sb.append("perms=").append(this.perms).append(",");
         sb.append("type=").append(this.type).append(",");
         sb.append("length=").append(this.length);
         sb.append(")");
         return sb.toString();
      }
   }
}
