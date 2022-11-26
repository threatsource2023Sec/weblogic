package weblogic.jms.dotnet.transport.t3client;

import weblogic.jms.dotnet.transport.Transport;

public class EchoClient {
   public static void main(String[] args) throws Exception {
      int port = 7001;
      String hostAddress = "localhost";
      if (args != null && args.length == 2) {
         hostAddress = args[0];
         port = Integer.parseInt(args[1]);
      }

      T3Connection connection1 = new T3Connection(hostAddress, port, (T3PeerInfo)null, (byte)0);
      System.out.println("Connection 1 is " + connection1);
      T3Connection connection2 = new T3Connection(hostAddress, port, (T3PeerInfo)null, (byte)0);
      System.out.println("Connection 2 is " + connection2);

      for(int i = 0; i < 1000000; ++i) {
         if (i % 1000 == 0) {
            System.out.println("SGC iter " + i);
         }

         MarshalWriterImpl mw = T3Connection.createOneWay((MarshalWriterImpl)null);
         mw.writeInt(i);
         connection1.sendOneWay(mw);
         mw = T3Connection.createOneWay((MarshalWriterImpl)null);
         mw.writeInt(i);
         connection2.sendOneWay(mw);
         MarshalReaderImpl response1 = connection1.receiveOneWay((Transport)null);
         MarshalReaderImpl response2 = connection2.receiveOneWay((Transport)null);
         if (response1.readInt() != i + 1) {
            System.out.println("SGC connection1 is failing at " + i);
            return;
         }

         if (response2.readInt() != i + 1) {
            System.out.println("SGC connection2 is failing at " + i);
            return;
         }

         response1.internalClose();
         response2.internalClose();
      }

   }
}
