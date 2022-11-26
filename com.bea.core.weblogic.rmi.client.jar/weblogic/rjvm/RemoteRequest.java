package weblogic.rjvm;

import java.io.IOException;
import weblogic.common.WLObjectInput;
import weblogic.common.internal.PeerInfo;
import weblogic.security.subject.AbstractSubject;

public interface RemoteRequest extends WLObjectInput {
   RJVM getOrigin();

   AbstractSubject getSubject();

   Object getTxContext();

   ReplyStream getResponseStream() throws IOException;

   PeerInfo getPeerInfo();

   String getPartitionName();
}
