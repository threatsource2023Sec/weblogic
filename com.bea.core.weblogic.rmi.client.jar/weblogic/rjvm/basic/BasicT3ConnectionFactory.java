package weblogic.rjvm.basic;

import java.io.IOException;
import java.net.InetAddress;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVMConnectionFactory;
import weblogic.work.WorkManagerFactory;

public class BasicT3ConnectionFactory implements RJVMConnectionFactory {
   public MsgAbbrevJVMConnection createConnection(String host, InetAddress addr, int port, ServerChannel channel, JVMID jvmId, int timeout) throws IOException {
      return this.createConnection(host, addr, port, channel, jvmId, timeout, "t3://" + addr.getHostName() + ':' + port);
   }

   public MsgAbbrevJVMConnection createConnection(String host, InetAddress addr, int port, ServerChannel channel, JVMID destinationJVMID, int timeout, String partitionUrl) throws IOException {
      BasicT3Connection transport = new BasicT3Connection(channel, partitionUrl);
      transport.connect(host, addr, port, timeout);
      WorkManagerFactory.getInstance().getDefault().schedule(transport);
      return transport;
   }
}
