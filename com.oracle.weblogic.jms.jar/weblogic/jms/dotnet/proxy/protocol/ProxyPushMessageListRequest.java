package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class ProxyPushMessageListRequest extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private ProxyPushMessageRequest first;
   private ProxyPushMessageRequest last;
   private int pipelineSize;
   private int size;

   public ProxyPushMessageListRequest() {
   }

   public ProxyPushMessageListRequest(int pipelineSize) {
      this.pipelineSize = pipelineSize;
   }

   public synchronized int size() {
      return this.size;
   }

   public synchronized void add(ProxyPushMessageRequest push) {
      ++this.size;
      push.setNext((ProxyPushMessageRequest)null);
      if (this.first == null) {
         this.first = push;
         this.last = push;
      } else {
         this.last.setNext(push);
         this.last = push;
      }
   }

   public int getMarshalTypeCode() {
      return 51;
   }

   public synchronized void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      this.versionFlags.marshal(mw);
      mw.writeInt(this.pipelineSize);
      mw.writeInt(this.size);

      for(ProxyPushMessageRequest cur = this.first; cur != null; cur = cur.getNext()) {
         cur.marshal(mw);
      }

   }

   public synchronized void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.pipelineSize = mr.readInt();
      int count = mr.readInt();

      while(count-- > 0) {
         ProxyPushMessageRequest req = new ProxyPushMessageRequest();
         req.unmarshal(mr);
         this.add(req);
      }

   }
}
