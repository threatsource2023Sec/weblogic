package weblogic.messaging.dispatcher;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;
import weblogic.jms.common.JMSDebug;
import weblogic.messaging.ID;

public abstract class DispatcherObjectHandler {
   protected int MASK;

   protected DispatcherObjectHandler(int mask) {
      this.MASK = mask;
   }

   public void writeRequest(ObjectOutput out, Request r) throws IOException {
      out.writeInt(r.getMethodId());
      r.writeShortened(out);
   }

   public Request readRequest(int methodID, ObjectInput in, ID invocableID) throws ClassNotFoundException, IOException {
      Request request = this.instantiate(methodID & this.MASK);

      try {
         request.readExternal(in);
      } catch (IOException var6) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            debugWireOperation("RecvReq  ", (byte)15, request, -1, invocableID, (Response)null, var6);
         }

         throw var6;
      } catch (ClassNotFoundException var7) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            debugWireOperation("RecvReq  ", (byte)15, request, -1, invocableID, (Response)null, var7);
         }

         throw var7;
      }

      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         debugWireOperation("RecvReq  ", (byte)15, request, -1, invocableID, (Response)null, (Throwable)null);
      }

      request.setMethodId(methodID);
      request.setInvocableId(invocableID);
      return request;
   }

   static void debugWireOperation(String sendRecv, byte requestType, Request request, int workId, ID invocableID, Response response, Throwable thrown) {
      String dbgWork;
      if (workId != 0 && workId != -1) {
         dbgWork = "workId " + Integer.toString(workId) + " ";
      } else {
         dbgWork = "";
      }

      String dbgType;
      if (15 == requestType) {
         dbgType = "";
      } else {
         dbgType = ", requestType x" + Integer.toHexString(requestType).toUpperCase();
      }

      String dbgResponse;
      if (response == null) {
         dbgResponse = "";
      } else {
         dbgResponse = ", Response " + response.getClass().getName();
      }

      String msg = sendRecv + dbgWork + dbgType + ", ID " + invocableID + ", " + request.getClass().getName() + dbgResponse;
      if (thrown == null) {
         JMSDebug.JMSDispatcher.debug(msg);
      } else {
         JMSDebug.JMSDispatcher.debug(msg, thrown);
      }

   }

   public void writeResponse(ObjectOutput out, Request request, Response response) throws IOException {
      try {
         out.writeInt(request.getMethodId() & this.MASK);
         response.writeExternal(out);
      } catch (IOException var5) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            debugWireOperation("SendResp ", (byte)15, request, -1, request.getInvocableId(), response, var5);
         }

         throw var5;
      }

      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         debugWireOperation("SendResp ", (byte)15, request, -1, request.getInvocableId(), response, (Throwable)null);
      }

   }

   public Response readResponse(ObjectInput in, Request request) throws ClassNotFoundException, IOException {
      int tc = in.readInt();
      if (tc != (request.getMethodId() & this.MASK)) {
         StreamCorruptedException sce = new StreamCorruptedException("Unexpected response for " + request.getClass().getName() + " : " + tc);
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            debugWireOperation("RecvResp ", (byte)15, request, -1, request.getInvocableId(), (Response)null, sce);
         }

         throw sce;
      } else {
         Response response = request.createResponse();

         try {
            response.readExternal(in);
         } catch (IOException var6) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               debugWireOperation("RecvResp ", (byte)15, request, -1, request.getInvocableId(), response, var6);
            }

            throw var6;
         } catch (ClassNotFoundException var7) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               debugWireOperation("RecvResp ", (byte)15, request, -1, request.getInvocableId(), response, var7);
            }

            throw var7;
         }

         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            debugWireOperation("RecvResp ", (byte)15, request, -1, request.getInvocableId(), response, (Throwable)null);
         }

         return response;
      }
   }

   protected Request instantiate(int tc) throws IOException {
      throw new StreamCorruptedException("Unknown typecode: " + tc);
   }

   static DispatcherObjectHandler load(String className) {
      try {
         return (DispatcherObjectHandler)Class.forName(className).newInstance();
      } catch (ClassNotFoundException var2) {
         throw new AssertionError(var2);
      } catch (InstantiationException var3) {
         throw new AssertionError(var3);
      } catch (IllegalAccessException var4) {
         throw new AssertionError(var4);
      }
   }
}
