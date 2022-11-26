package weblogic.deploy.service.datatransferhandlers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import weblogic.deploy.service.DataStream;
import weblogic.deploy.service.FileDataStream;
import weblogic.deploy.service.MultiDataStream;
import weblogic.deploy.service.internal.DeploymentServiceLogger;
import weblogic.logging.Loggable;

public class MultipartResponse {
   HttpServletResponse res;
   ServletOutputStream out;
   MultiDataStream multiStream = null;

   public MultipartResponse(HttpServletResponse response, MultiDataStream multiStream) throws IOException {
      this.res = response;
      this.out = this.res.getOutputStream();
      this.multiStream = multiStream;
      this.init();
   }

   private void init() throws IOException {
      this.setupMultiFileResponse();
   }

   private void setupSingleFileResponse() throws IOException {
      DataStream stream = (DataStream)this.multiStream.getDataStreams().next();
      this.res.setContentType(stream.isZip() ? "application/zip" : "text/plain");
      this.res.setContentLength(((FileDataStream)stream).getLength());
   }

   private void setupMultiFileResponse() throws IOException {
      this.res.setContentType("multipart/mixed");
      int contentLength = 0;

      for(Iterator iter = this.multiStream.getDataStreams(); iter.hasNext(); contentLength += ((FileDataStream)iter.next()).getLength()) {
      }

      this.res.setContentLength(contentLength);
      String filesHeaderValue = MultipartHelper.constructFilesHeaderValue(this.multiStream);
      this.res.setHeader("files_header", filesHeaderValue);
   }

   public void write() throws IOException {
      this.res.setStatus(200);
      Iterator iter = this.multiStream.getDataStreams();

      while(iter.hasNext()) {
         DataStream eachStream = (DataStream)iter.next();
         this.writeDataStream(eachStream);
      }

      this.out.flush();
   }

   private void writeDataStream(DataStream stream) throws IOException {
      try {
         InputStream in = null;

         try {
            in = stream.getInputStream();
            byte[] buffer = new byte[4096];

            int read;
            while((read = in.read(buffer)) != -1) {
               this.out.write(buffer, 0, read);
            }
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (IOException var11) {
               }
            }

         }

      } catch (IOException var13) {
         Loggable l = DeploymentServiceLogger.logWriteDataStreamExceptionLoggable(stream.getName());
         l.log();
         throw new IOException(l.getMessage(), var13);
      }
   }
}
