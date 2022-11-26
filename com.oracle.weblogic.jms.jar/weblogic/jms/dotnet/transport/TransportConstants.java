package weblogic.jms.dotnet.transport;

public abstract class TransportConstants {
   public static final int UNUSED = -1;
   public static final int SERVICE_NEW_TRANSPORT = 10000;
   public static final int SERVICE_HEARTBEAT = 10001;
   public static final int SERVICE_PING = 10002;
   public static final int SERVICE_BOOTSTRAP = 10003;
   public static final int SERVICE_PROXY = 10004;
   public static final int TYPE_CODE_BOOTSTRAP = 20000;
   public static final int TYPE_CODE_TRANSPORT_ERROR = 20001;
   public static final int TYPE_CODE_PING = 20002;
   public static final int TYPE_CODE_HEARTBEAT = 20003;

   private TransportConstants() {
   }
}
