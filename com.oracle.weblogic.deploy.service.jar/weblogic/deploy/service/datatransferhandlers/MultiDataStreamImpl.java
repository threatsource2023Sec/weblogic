package weblogic.deploy.service.datatransferhandlers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.deploy.service.DataStream;
import weblogic.deploy.service.MultiDataStream;

final class MultiDataStreamImpl implements MultiDataStream {
   private List dataStreams = new ArrayList();

   public int getSize() {
      return this.dataStreams.size();
   }

   public Iterator getDataStreams() {
      return this.dataStreams.iterator();
   }

   public Iterator getInputStreams() throws IOException {
      List inputStreams = new ArrayList();
      Iterator iter = this.getDataStreams();

      while(iter.hasNext()) {
         inputStreams.add(((DataStream)iter.next()).getInputStream());
      }

      return inputStreams.iterator();
   }

   public void close() {
      Iterator streams = this.getDataStreams();

      while(streams.hasNext()) {
         DataStream stream = (DataStream)streams.next();
         stream.close();
      }

   }

   void addDataStream(String name, InputStream in, boolean isZip) {
      this.dataStreams.add(DataStreamFactory.createDataStream(name, in, isZip));
   }

   void addFileDataStream(String name, String location, boolean isZip) {
      this.addFileDataStream(name, new File(location), isZip);
   }

   void addFileDataStream(File file, boolean isZip) {
      this.addFileDataStream((String)null, (File)file, isZip);
   }

   public void addFileDataStream(String location, boolean isZip) {
      this.addFileDataStream(new File(location), isZip);
   }

   public void addFileDataStream(String name, File file, boolean isZip) {
      this.addFileDataStream(name, file, (String)null, isZip);
   }

   public void addFileDataStream(String name, File file, String targetPath, boolean isZip) {
      this.dataStreams.add(DataStreamFactory.createFileDataStream(name, file, targetPath, isZip));
   }

   public void addDataStream(DataStream stream) {
      this.dataStreams.add(stream);
   }

   public void removeDataStream(DataStream stream) {
      this.dataStreams.remove(stream);
   }
}
