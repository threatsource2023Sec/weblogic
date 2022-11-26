package weblogic.jdbc.common.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;

public final class ConnectionState implements Externalizable {
   private static final long serialVersionUID = -6110774032044504638L;
   public String sessionid = null;
   public int rowbuffsize = -1;
   public int prefetchsize = -1;
   public int streambuffsize = -1;
   public boolean cachesets = true;
   public boolean cachegets = true;
   public boolean doEvents = false;
   public String eventTopicRoot = null;
   public int blobChunkSize = 0;
   public boolean preserveSetObject = false;

   public void initialize() {
      this.sessionid = null;
      this.rowbuffsize = -1;
      this.prefetchsize = -1;
      this.streambuffsize = -1;
      this.cachesets = true;
      this.cachegets = true;
      this.doEvents = false;
      this.eventTopicRoot = null;
      this.blobChunkSize = 0;
   }

   public void destroy() {
      this.sessionid = null;
      this.rowbuffsize = -1;
      this.prefetchsize = -1;
      this.streambuffsize = -1;
      this.cachesets = true;
      this.cachegets = true;
      this.doEvents = false;
      this.eventTopicRoot = null;
      this.blobChunkSize = 0;
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      WLObjectInput sis = (WLObjectInput)in;
      this.sessionid = sis.readString();
      this.rowbuffsize = sis.readInt();
      this.prefetchsize = sis.readInt();
      this.streambuffsize = sis.readInt();
      this.cachesets = sis.readBoolean();
      this.cachegets = sis.readBoolean();
      this.doEvents = sis.readBoolean();
      this.eventTopicRoot = sis.readString();
      this.blobChunkSize = sis.readInt();
      this.preserveSetObject = sis.readBoolean();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      WLObjectOutput sos = (WLObjectOutput)out;
      sos.writeString(this.sessionid);
      sos.writeInt(this.rowbuffsize);
      sos.writeInt(this.prefetchsize);
      sos.writeInt(this.streambuffsize);
      sos.writeBoolean(this.cachesets);
      sos.writeBoolean(this.cachegets);
      sos.writeBoolean(this.doEvents);
      sos.writeString(this.eventTopicRoot);
      sos.writeInt(this.blobChunkSize);
      sos.writeBoolean(this.preserveSetObject);
   }
}
