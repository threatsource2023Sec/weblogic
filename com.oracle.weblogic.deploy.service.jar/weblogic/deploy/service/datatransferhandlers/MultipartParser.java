package weblogic.deploy.service.datatransferhandlers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import weblogic.deploy.common.Debug;
import weblogic.deploy.common.DeploymentConstants;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.MultiDataStream;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.management.DomainDir;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.FileUtils;

public final class MultipartParser implements DeploymentConstants {
   private static final String DEFAULT_MAX_POST_SIZE = "1073741824";
   private static String MAXLEN = "weblogic.deploy.MaxPostSize";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private HttpURLConnection connection;
   private File dir;
   private int maxSize;
   private MultiDataStream streams;

   public MultipartParser(HttpURLConnection connection, String saveDirectory) throws IOException {
      this(connection, saveDirectory, Integer.parseInt(System.getProperty(MAXLEN, "1073741824")));
   }

   public MultipartParser(HttpURLConnection connection, String saveDirectory, int maxPostSize) throws IOException {
      this.streams = null;
      if (connection == null) {
         throw new IllegalArgumentException("Connection cannot be null");
      } else if (maxPostSize <= 0) {
         throw new IllegalArgumentException("maxPostSize must be positive");
      } else {
         this.connection = connection;
         this.maxSize = maxPostSize;
         String tmpLocation = DomainDir.getTempDirForServer(ManagementService.getPropertyService(kernelId).getServerName());
         this.dir = saveDirectory != null ? new File(tmpLocation, saveDirectory) : new File(tmpLocation);
         boolean isDirExists = this.dir.exists();
         if (!isDirExists) {
            isDirExists = this.dir.mkdirs();
         }

         if (!isDirExists) {
            throw new IllegalArgumentException("Could not create dir: " + this.dir);
         } else if (!this.dir.isDirectory()) {
            throw new IllegalArgumentException("Not a dir: " + this.dir);
         } else if (!this.dir.canWrite()) {
            throw new IllegalArgumentException("Not writable: " + this.dir);
         } else {
            this.parseResponse();
         }
      }
   }

   MultiDataStream getMultiDataStream() {
      return this.streams;
   }

   private void parseResponse() throws IOException {
      int length = this.connection.getContentLength();
      String type = this.connection.getContentType();
      if (type != null && type.toLowerCase(Locale.US).startsWith("multipart/mixed")) {
         String filesHeaderValue = this.connection.getHeaderField("files_header");
         if (filesHeaderValue != null && filesHeaderValue.length() != 0) {
            MultipartHelper.FileInfo[] infos = MultipartHelper.parseFilesHeader(filesHeaderValue);
            if (infos != null && infos.length != 0) {
               if (length < 0) {
                  throw new IOException("Posted content doesn't set it's Content-Length");
               } else if (length > this.maxSize) {
                  this.printFileDetails(infos, this.connection.getInputStream(), length, this.maxSize);
                  throw new IOException("Posted content exceeds max post size");
               } else {
                  this.streams = this.readFiles(infos, this.connection.getInputStream());
               }
            } else {
               throw new IllegalArgumentException("No files to be read");
            }
         } else {
            throw new IllegalArgumentException("Invalid files_header value");
         }
      } else {
         throw new IOException("Posted content type isn't multipart/x-mixed-replace");
      }
   }

   private void printFileDetails(MultipartHelper.FileInfo[] infos, InputStream in, int length, int maxSize) {
      int gbDivider = 1000000000;
      DeploymentServiceLogger.logMaxLengthExceedDetails((float)length / (float)gbDivider, (float)maxSize / (float)gbDivider);
      DeploymentServiceLogger.logMaxLengthExceedHeadMsg();
      Map map = new HashMap();
      MultipartHelper.FileInfo[] var7 = infos;
      int var8 = infos.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         MultipartHelper.FileInfo fileInfo = var7[var9];
         long contentLength = fileInfo.getSize();
         map.put(fileInfo.getName(), (float)contentLength / (float)gbDivider);
      }

      Set set = map.entrySet();
      List list = new ArrayList(set);
      Collections.sort(list, new Comparator() {
         public int compare(Map.Entry o1, Map.Entry o2) {
            return ((Float)o2.getValue()).compareTo((Float)o1.getValue());
         }
      });
      Iterator var16 = list.iterator();

      while(var16.hasNext()) {
         Map.Entry entry = (Map.Entry)var16.next();
         DeploymentServiceLogger.logMaxLengthExceedFileList((String)entry.getKey(), (Float)entry.getValue());
      }

   }

   private MultiDataStream readFiles(MultipartHelper.FileInfo[] infos, InputStream in) throws IOException {
      MultiDataStream streams = DataStreamFactory.createMultiDataStream();

      for(int i = 0; i < infos.length; ++i) {
         streams.addDataStream(this.readFile(infos[i], in));
      }

      return streams;
   }

   private FileDataStream readFile(MultipartHelper.FileInfo fileInfo, InputStream in) throws IOException {
      OutputStream out = null;
      int contentLength = (int)fileInfo.getSize();
      if (Debug.isServiceHttpDebugEnabled()) {
         Debug.serviceDebug(" Reading file '" + fileInfo.getName() + "' with length : " + contentLength);
      }

      String suffix = fileInfo.isZip() ? ".jar" : ".txt";
      File f = File.createTempFile("wl_comp", suffix, this.dir);
      File parentDir = f.getParentFile();
      if (parentDir != null) {
         boolean parentExists = parentDir.exists();
         if (!parentExists) {
            parentExists = parentDir.mkdirs();
         }

         if (!parentExists) {
            throw new IOException("Cannot create parent dir for '" + f.getAbsolutePath());
         }
      }

      try {
         out = new BufferedOutputStream(new FileOutputStream(f), 8192);
         byte[] bbuf = new byte[8192];
         int toBeRead = contentLength < bbuf.length ? contentLength : bbuf.length;

         int remaining;
         for(int readBytes = 0; readBytes != contentLength; toBeRead = remaining < bbuf.length ? remaining : bbuf.length) {
            int result = in.read(bbuf, 0, toBeRead);
            if (result == -1) {
               throw new IOException("Reached EOF for file: " + fileInfo);
            }

            readBytes += result;
            out.write(bbuf, 0, result);
            remaining = contentLength - readBytes;
         }

         out.flush();
      } finally {
         if (out != null) {
            try {
               out.close();
            } catch (IOException var18) {
            }
         }

      }

      FileUtils.setPosixFilePermissions(f, fileInfo.getPerms());
      return createDataStream(fileInfo, f);
   }

   private static FileDataStream createDataStream(final MultipartHelper.FileInfo info, final File file) {
      return new FileDataStream() {
         public String getName() {
            String infoName = info.getName();
            return infoName != null && infoName.length() != 0 ? infoName : file.getName();
         }

         public File getFile() {
            return file;
         }

         public int getLength() throws IOException {
            this.validateFile();
            return (int)file.length();
         }

         public boolean isZip() {
            return info.isZip();
         }

         public InputStream getInputStream() throws IOException {
            this.validateFile();
            return new FileInputStream(file);
         }

         public void close() {
            boolean deleted = file.delete();
            if (Debug.isServiceHttpDebugEnabled()) {
               if (deleted) {
                  Debug.serviceDebug("Successfully deleted temp file : " + file.getAbsolutePath());
               } else {
                  Debug.serviceDebug("Could not delete temp file : " + file.getAbsolutePath());
               }
            }

         }

         private void validateFile() throws IOException {
            if (!file.exists()) {
               throw new IOException("File '" + file.getAbsolutePath() + "' does not exist");
            }
         }
      };
   }
}
