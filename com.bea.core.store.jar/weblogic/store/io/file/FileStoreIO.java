package weblogic.store.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.store.PersistentStoreException;
import weblogic.store.StoreWritePolicy;
import weblogic.store.common.StoreDebug;
import weblogic.store.common.StoreRCMUtils;
import weblogic.store.io.IOListener;
import weblogic.store.io.file.direct.DirectIOManager;

public class FileStoreIO extends BaseStoreIO {
   public FileStoreIO(String filePrefix, String dirName) throws PersistentStoreException {
      this(filePrefix, dirName, true);
   }

   public FileStoreIO(String filePrefix, String dirName, boolean autoCreateDir) throws PersistentStoreException {
      super(DirectIOManager.getOpenFileManager(), filePrefix, dirName, autoCreateDir, false);
   }

   /** @deprecated */
   @Deprecated
   public int open(StoreWritePolicy wp, int ignored) throws PersistentStoreException {
      HashMap config = new HashMap();
      config.put("SynchronousWritePolicy", wp);
      return this.open(config);
   }

   HashMap adjustConfig(HashMap config) {
      return config;
   }

   public int open(HashMap config) throws PersistentStoreException {
      if (StoreDebug.storeIOPhysicalVerbose.isDebugEnabled()) {
         StoreDebug.storeIOPhysicalVerbose.debug("Opening store: " + this.heap.getName());
      }

      return this.openInternal(config);
   }

   FileChannel fileChannelFactory(Map config, File file, String mode, boolean exclusive) throws IOException {
      return staticOpen(this.heap, file, mode, exclusive);
   }

   static FileChannel staticOpen(Heap optional, File file, String mode, boolean exclusive) throws IOException {
      if (optional == null) {
         boolean var10000 = true;
      } else {
         System.identityHashCode(optional);
      }

      return DirectIOManager.getFileManager().openBasic(file, mode, exclusive);
   }

   public void flush(IOListener listener) throws PersistentStoreException {
      throw new UnsupportedOperationException("the asynchrounous flush should not be called on FileStoreIO");
   }

   public void dump(XMLStreamWriter xsw) throws XMLStreamException {
      xsw.writeStartElement("FileStore");
      this.dumpInternal(xsw);
   }

   File[] listRegionsOrFiles(Heap heap, final File configuredDirectory, final FilenameFilter filenameFilter) throws IOException {
      File[] files = new File[0];

      try {
         class ListRegionsOrFileCallable implements Callable {
            public File[] call() throws IOException {
               return configuredDirectory.listFiles(filenameFilter);
            }
         }

         files = (File[])StoreRCMUtils.accountAsGlobal((Callable)(new ListRegionsOrFileCallable()));
      } catch (Exception var6) {
         StoreRCMUtils.throwIOorRuntimeException(var6);
      }

      return files;
   }
}
