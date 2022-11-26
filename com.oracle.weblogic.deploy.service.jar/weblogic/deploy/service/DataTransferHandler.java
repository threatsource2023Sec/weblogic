package weblogic.deploy.service;

import java.io.IOException;

public interface DataTransferHandler {
   String getType();

   MultiDataStream getDataAsStream(DataTransferRequest var1) throws IOException;
}
