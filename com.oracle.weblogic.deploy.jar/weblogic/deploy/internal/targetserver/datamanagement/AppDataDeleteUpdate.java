package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import weblogic.management.DeploymentException;
import weblogic.utils.FileUtils;

public class AppDataDeleteUpdate extends DataUpdate {
   public AppDataDeleteUpdate(Data localData, DataUpdateRequestInfo reqInfo) {
      super(localData, reqInfo);
   }

   protected final void doDownload(String handlerType) throws DeploymentException {
   }

   protected final void doUpdate() throws DeploymentException {
      try {
         Iterator toBeDeletedFiles = this.getRequestedFiles().iterator();
         if (toBeDeletedFiles != null && toBeDeletedFiles.hasNext()) {
            File eachFile;
            boolean removed;
            do {
               if (!toBeDeletedFiles.hasNext()) {
                  return;
               }

               String eachRelativePath = (String)toBeDeletedFiles.next();
               eachFile = new File(this.getLocalAppData().getSourceFile(), eachRelativePath);
               removed = FileUtils.remove(eachFile);
            } while(removed);

            throw new IOException("Could not remove file or dir : " + eachFile.getAbsolutePath());
         }
      } catch (IOException var5) {
         var5.printStackTrace();
         throw new DeploymentException("Exception occured while copying files", var5);
      }
   }

   protected void doCancel() {
   }

   protected void doClose(boolean success) {
   }

   protected void deleteFile(String targetURI) {
   }

   protected File getFileFor(String targetPath) {
      return null;
   }

   private AppData getLocalAppData() {
      return (AppData)this.getLocalData();
   }
}
