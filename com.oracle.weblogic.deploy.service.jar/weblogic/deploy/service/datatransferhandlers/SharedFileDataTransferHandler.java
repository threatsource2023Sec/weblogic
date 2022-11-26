package weblogic.deploy.service.datatransferhandlers;

import java.io.IOException;
import java.io.InputStream;
import weblogic.deploy.service.DataTransferHandler;
import weblogic.deploy.service.DataTransferRequest;
import weblogic.deploy.service.MultiDataStream;

public class SharedFileDataTransferHandler implements DataTransferHandler {
   public static final String SHARED_FILE_BASED_HANDLER = "SHARED_FILES";
   private static SharedFileDataTransferHandler HANDLER = new SharedFileDataTransferHandler();

   private SharedFileDataTransferHandler() {
   }

   public static synchronized DataTransferHandler getDataTransferHandler() {
      return HANDLER;
   }

   public String getType() {
      return "SHARED_FILES";
   }

   public MultiDataStream getDataAsStream(DataTransferRequest request) throws IOException {
      return null;
   }

   public InputStream getDataAsStream(String path, String lockPath) throws IOException {
      return null;
   }
}
