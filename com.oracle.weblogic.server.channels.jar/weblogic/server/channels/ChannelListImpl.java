package weblogic.server.channels;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import weblogic.protocol.ChannelList;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;

class ChannelListImpl implements ChannelList {
   private ServerIdentity id;
   private RemoteChannelService service;

   public ChannelListImpl() {
      this.id = LocalServerIdentity.getIdentity();
      this.service = (RemoteChannelService)RemoteChannelServiceImpl.getInstance();
   }

   public ChannelListImpl(ServerIdentity id) {
      this.id = id;
   }

   public ServerIdentity getIdentity() {
      return this.id;
   }

   public RemoteChannelService getChannelService() {
      return this.service;
   }

   protected Object writeReplace() throws ObjectStreamException {
      return !ChannelService.hasChannels(this.id) ? null : this;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.id);
      out.writeObject(this.service);
      ChannelService.exportServerChannels(this.id, out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.id = (ServerIdentity)in.readObject();
      this.service = (RemoteChannelService)in.readObject();
      ChannelService.importServerChannels(this.id, in);
   }
}
