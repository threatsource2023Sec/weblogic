package weblogic.management.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.InetSocketAddress;
import org.jvnet.hk2.annotations.Contract;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerIdentity;

@Contract
public interface ChannelImportExportService {
   void exportServerChannels(ServerIdentity var1, ObjectOutput var2) throws IOException;

   void importNonLocalServerChannels(ServerIdentity var1, ObjectInput var2) throws IOException;

   InetSocketAddress findServerAddress(Protocol var1);

   InetSocketAddress findServerAddress(String var1);
}
