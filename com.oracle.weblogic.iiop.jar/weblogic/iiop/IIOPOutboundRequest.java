package weblogic.iiop;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.RequestMessage;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.FutureResultImpl;
import weblogic.rmi.spi.InboundResponse;

abstract class IIOPOutboundRequest extends OutboundRequestImpl {
   IIOPOutboundRequest(EndPoint endPoint, RequestMessage request, boolean rmiType, RuntimeMethodDescriptor md) {
      super(endPoint, request, rmiType, md);
   }

   abstract Object invoke() throws Throwable;

   static IIOPOutboundRequest createOutboundRequest(Remote stub, EndPoint endPoint, RequestMessage requestMessage, boolean isRmi, RuntimeMethodDescriptor descriptor) {
      if (descriptor.isOneway()) {
         return new OneWayIIOPOutboundRequest(endPoint, requestMessage, isRmi, descriptor);
      } else {
         return (IIOPOutboundRequest)(descriptor.hasAsyncResponse() ? new AsyncIIOPOutboundRequest(stub, endPoint, requestMessage, isRmi, descriptor) : new NormalIIOPOutboundRequest(endPoint, requestMessage, isRmi, descriptor));
      }
   }

   static class AsyncIIOPOutboundRequest extends IIOPOutboundRequest {
      private Remote stub;
      private RuntimeMethodDescriptor md;

      private AsyncIIOPOutboundRequest(Remote stub, EndPoint endPoint, RequestMessage request, boolean rmiType, RuntimeMethodDescriptor md) {
         super(endPoint, request, rmiType, md);
         this.stub = stub;
         this.md = md;
      }

      Object invoke() throws Throwable {
         FutureResultImpl futureResult = this.createFutureResult();
         this.sendAsync(futureResult);
         return futureResult;
      }

      private FutureResultImpl createFutureResult() throws RemoteException {
         return this.hasRemoteExceptionWrapperClassName() ? new FutureResultImpl(this.stub, this.md) : new FutureResultImpl(this.stub);
      }

      private boolean hasRemoteExceptionWrapperClassName() {
         return !this.isNullOrEmpty(this.md.getRemoteExceptionWrapperClassName());
      }

      private boolean isNullOrEmpty(String string) {
         return string == null || string.equals("");
      }

      // $FF: synthetic method
      AsyncIIOPOutboundRequest(Remote x0, EndPoint x1, RequestMessage x2, boolean x3, RuntimeMethodDescriptor x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }

   static class OneWayIIOPOutboundRequest extends IIOPOutboundRequest {
      OneWayIIOPOutboundRequest(EndPoint endPoint, RequestMessage request, boolean rmiType, RuntimeMethodDescriptor md) {
         super(endPoint, request, rmiType, md);
      }

      Object invoke() throws Throwable {
         this.sendOneWay();
         return null;
      }
   }

   static class NormalIIOPOutboundRequest extends IIOPOutboundRequest {
      private NormalIIOPOutboundRequest(EndPoint endPoint, RequestMessage request, boolean rmiType, RuntimeMethodDescriptor md) {
         super(endPoint, request, rmiType, md);
      }

      Object invoke() throws Throwable {
         InboundResponse response = null;

         Object var3;
         try {
            response = this.sendReceive();
            IOR ior;
            if (response instanceof InboundResponseImpl && (ior = ((InboundResponseImpl)response).needsForwarding()) != null) {
               throw new LocationForwardException(ior);
            }

            var3 = response.unmarshalReturn();
         } finally {
            if (response != null) {
               this.closeResponse(response);
            }

         }

         return var3;
      }

      private void closeResponse(InboundResponse response) throws UnmarshalException {
         try {
            response.close();
         } catch (IOException var3) {
            throw new UnmarshalException("failed to close response stream", var3);
         }
      }

      // $FF: synthetic method
      NormalIIOPOutboundRequest(EndPoint x0, RequestMessage x1, boolean x2, RuntimeMethodDescriptor x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
