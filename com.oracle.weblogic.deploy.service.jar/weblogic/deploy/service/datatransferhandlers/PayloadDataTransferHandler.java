package weblogic.deploy.service.datatransferhandlers;

import java.io.IOException;
import java.io.InputStream;
import weblogic.deploy.service.DataTransferHandler;
import weblogic.deploy.service.DataTransferRequest;
import weblogic.deploy.service.MultiDataStream;

public class PayloadDataTransferHandler implements DataTransferHandler {
   public static final String PAYLOAD_HANDLER = "PAYLOAD";
   private static final PayloadDataTransferHandler HANDLER = new PayloadDataTransferHandler();

   private PayloadDataTransferHandler() {
   }

   public static synchronized DataTransferHandler getDataTransferHandler() {
      return HANDLER;
   }

   public String getType() {
      return "PAYLOAD";
   }

   public MultiDataStream getDataAsStream(DataTransferRequest request) throws IOException {
      return null;
   }

   public InputStream getDataAsStream(String path, String lockPath) throws IOException {
      return null;
   }
}
