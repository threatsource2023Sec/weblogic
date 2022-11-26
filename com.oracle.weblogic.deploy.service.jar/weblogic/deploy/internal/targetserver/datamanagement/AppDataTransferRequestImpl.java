package weblogic.deploy.internal.targetserver.datamanagement;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import weblogic.deploy.service.AppDataTransferRequest;

public class AppDataTransferRequestImpl extends DataTransferRequestImpl implements AppDataTransferRequest {
   private static final long serialVersionUID = 2968297419700820168L;
   private String appName;
   private String appVersionIdentifier;
   private String partition;
   private boolean planUpdate;

   public AppDataTransferRequestImpl() {
   }

   public AppDataTransferRequestImpl(String appName, String appVersionIdentifier, long requestId, List filePaths, String lockPath, boolean isPlanUpdate, String partition) {
      super(requestId, filePaths, lockPath);
      this.appName = appName;
      this.appVersionIdentifier = appVersionIdentifier;
      this.planUpdate = isPlanUpdate;
      this.partition = partition;
   }

   public String getAppName() {
      return this.appName;
   }

   public String getAppVersionIdentifier() {
      return this.appVersionIdentifier;
   }

   public boolean isPlanUpdate() {
      return this.planUpdate;
   }

   public String getPartition() {
      return this.partition;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeObject(this.appName);
      out.writeObject(this.appVersionIdentifier);
      out.writeBoolean(this.planUpdate);
      out.writeObject(this.partition);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      this.appName = (String)in.readObject();
      this.appVersionIdentifier = (String)in.readObject();
      this.planUpdate = in.readBoolean();
      this.partition = (String)in.readObject();
   }
}
