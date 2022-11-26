package weblogic.rjvm.http;

class UtilsBase {
   static final String TUNNEL_SEND = "/bea_wls_internal/HTTPClntSend";
   static final String TUNNEL_RECV = "/bea_wls_internal/HTTPClntRecv";
   static final String TUNNEL_LOGIN = "/bea_wls_internal/HTTPClntLogin";
   static final String TUNNEL_CLOSE = "/bea_wls_internal/HTTPClntClose";
   static final String TUNNEL_OK = "OK";
   static final String TUNNEL_DEAD = "DEAD";
   static final String TUNNEL_RETRY = "RETRY";
   static final String TUNNEL_UNAVAIL = "UNAVAIL";
   static final String RESULT_HEADER = "WL-Result";
   static final String VERSION_HEADER = "WL-Version";
   static final String ID_HEADER = "Conn-Id";
}
