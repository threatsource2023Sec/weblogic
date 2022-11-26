package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.bridge.TyrusCloseListener;
import com.oracle.tyrus.fallback.bridge.TyrusWriter;
import com.oracle.tyrus.fallback.bridge.UpgradeResponseImpl;
import com.oracle.tyrus.fallback.spi.LongPollingAdapter;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.tyrus.core.RequestContext;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.core.RequestContext.Builder;
import org.glassfish.tyrus.spi.ServerContainer;
import org.glassfish.tyrus.spi.WebSocketEngine;
import org.glassfish.tyrus.spi.WebSocketEngine.UpgradeStatus;

class LongPollingConnectionMgr {
   private static final Logger LOGGER = Logger.getLogger(LongPollingConnectionMgr.class.getName());
   static final String TYRUS_CONNECTION_ID = "tyrus-connection-id";
   static final String TYRUS_ENCODING_HDR = "tyrus-encoding-required";
   static final String TYRUS_ENCODING_VALUE = "yes";
   static final String TYRUS_SUB_PROTOCOL = "tyrus-sub-protocol";
   private final Map connections = new HashMap();
   private final LongPollingAdapter adapter;

   LongPollingConnectionMgr(boolean nio) {
      LongPollingAdapter tmpAdapter = null;
      if (nio) {
         ServiceLoader sl = ServiceLoader.load(LongPollingAdapter.class);
         Iterator it = sl.iterator();
         if (it.hasNext()) {
            tmpAdapter = (LongPollingAdapter)it.next();
         } else {
            LOGGER.warning("Couldn't find non-blocking LongPollingAdapter.Using default blocking one");
         }
      }

      this.adapter = (LongPollingAdapter)(tmpAdapter == null ? new LongPollingBlockingAdapter() : tmpAdapter);
   }

   void processRequest(final HttpServletRequest req, HttpServletResponse res, String conId, FilterChain filterChain) throws ServletException, IOException {
      WebSocketEngine.UpgradeInfo upInfo = null;
      LongPollingConnection con;
      synchronized(this) {
         con = (LongPollingConnection)this.connections.get(conId);
         if (con == null) {
            String uri = req.getRequestURI();
            String encodingHdr = FallbackUtil.getHeaderValue(req, "tyrus-encoding-required");
            boolean encoding = encodingHdr != null && encodingHdr.equals("yes");
            RequestContext upRequest = Builder.create().requestURI(URI.create(req.getRequestURI())).queryString(req.getQueryString()).httpSession(req.getSession(false)).secure(req.isSecure()).userPrincipal(req.getUserPrincipal()).isUserInRoleDelegate(new RequestContext.Builder.IsUserInRoleDelegate() {
               public boolean isUserInRole(String role) {
                  return req.isUserInRole(role);
               }
            }).parameterMap(req.getParameterMap()).build();
            Enumeration headerNames = req.getHeaderNames();

            String tyrusSubProtocol;
            while(headerNames.hasMoreElements()) {
               tyrusSubProtocol = (String)headerNames.nextElement();
               List values = (List)upRequest.getHeaders().get(tyrusSubProtocol);
               if (values == null) {
                  upRequest.getHeaders().put(tyrusSubProtocol, Utils.parseHeaderValue(req.getHeader(tyrusSubProtocol).trim()));
               } else {
                  values.addAll(Utils.parseHeaderValue(req.getHeader(tyrusSubProtocol).trim()));
               }
            }

            upRequest.getHeaders().put("Upgrade", Collections.singletonList("websocket"));
            upRequest.getHeaders().put("Connection", Collections.singletonList("Upgrade"));
            upRequest.getHeaders().put("Sec-WebSocket-Key", Collections.singletonList("dGhlIHNhbXBsZSBub25jZQ=="));
            upRequest.getHeaders().put("Sec-WebSocket-Version", Collections.singletonList("13"));
            tyrusSubProtocol = FallbackUtil.getHeaderValue(req, "tyrus-sub-protocol");
            if (tyrusSubProtocol != null) {
               upRequest.getHeaders().put("Sec-WebSocket-Protocol", Utils.parseHeaderValue(tyrusSubProtocol));
            }

            UpgradeResponseImpl upResponse = new UpgradeResponseImpl(res);
            ServerContainer container = (ServerContainer)req.getServletContext().getAttribute(javax.websocket.server.ServerContainer.class.getName());
            upInfo = container.getWebSocketEngine().upgrade(upRequest, upResponse);
            switch (upInfo.getStatus()) {
               case HANDSHAKE_FAILED:
                  LOGGER.warning("Upgrade failed for emulation HTTP request with id=" + conId);
                  res.sendError(upResponse.getStatus());
                  return;
               case SUCCESS:
                  con = new LongPollingConnection(this, this.adapter, uri, conId, encoding);
                  this.connections.put(conId, con);
                  org.glassfish.tyrus.spi.Connection tyrusCon = upInfo.createConnection(new TyrusWriter(con), new TyrusCloseListener(con));
                  FallbackReadHandler rh = new FallbackReadHandler(tyrusCon.getReadHandler());
                  con.setReadHandler(rh);
                  con.setTyrusConnection(tyrusCon);
                  LOGGER.fine("New websocket connection via emulation, id=" + conId);
                  List subProtocol = (List)upResponse.getHeaders().get("Sec-WebSocket-Protocol");
                  if (subProtocol != null && !subProtocol.isEmpty()) {
                     String negotiatedSubProtocol = (String)subProtocol.get(0);
                     con.setNegotiatedSubProtocol(negotiatedSubProtocol);
                  }
               default:
                  upResponse.done();
            }
         }
      }

      if (upInfo != null && upInfo.getStatus() == UpgradeStatus.NOT_APPLICABLE) {
         LOGGER.warning("Upgrade status for HTTP request is NOT_APPLICABLE, but it is an emulation request with id=" + conId);
         filterChain.doFilter(req, res);
      } else {
         assert con != null;

         con.process(req, res);
      }
   }

   void remove(LongPollingConnection connection) {
      synchronized(this) {
         this.connections.remove(connection.getConnectionId());
         LOGGER.fine("Closing connection via emulation, id=" + connection.getConnectionId());
      }
   }
}
