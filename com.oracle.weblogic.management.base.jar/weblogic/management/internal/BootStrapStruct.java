package weblogic.management.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.Debug;

public class BootStrapStruct implements Externalizable {
   private static final long serialVersionUID = -737557156369970506L;
   private String adminServerName = null;
   private ServerIdentity adminIdentity;
   private final ChannelImportExportService importExportService = (ChannelImportExportService)GlobalServiceLocator.getServiceLocator().getService(ChannelImportExportService.class, new Annotation[0]);

   public BootStrapStruct(String adminServerName) {
      this.adminServerName = adminServerName;
   }

   public BootStrapStruct() {
   }

   public ServerIdentity getAdminIdentity() {
      return this.adminIdentity;
   }

   public String getAdminServerName() {
      return this.adminServerName;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.adminServerName);
      Debug.assertion(LocalServerIdentity.getIdentity().getServerName().equals(this.adminServerName));
      out.writeObject(LocalServerIdentity.getIdentity());
      this.importExportService.exportServerChannels(LocalServerIdentity.getIdentity(), out);
      ProductionModeHelper.exportProductionMode(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.adminServerName = in.readUTF();
      this.adminIdentity = (ServerIdentity)in.readObject();
      AbstractAdminServerIdentity.setBootstrapIdentity(this.adminIdentity);
      this.importExportService.importNonLocalServerChannels(this.adminIdentity, in);
      ProductionModeHelper.importProductionMode(in);
   }
}
