package weblogic.corba.idl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.cert.X509Certificate;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import weblogic.iiop.EndPoint;
import weblogic.iiop.IIOPOutputStream;
import weblogic.iiop.server.InboundRequest;
import weblogic.iiop.server.OutboundResponse;
import weblogic.iiop.spi.Message;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.extensions.server.InvokableServerReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.spi.InvokeHandler;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.MsgOutput;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.AbstractSubject;

public class CollocatedRequest implements InboundRequest, OutboundResponse, ResponseHandler, Message {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private InvokableServerReference serverReference;
   private String method;
   private Object txContext;
   private Object activationID;
   private IIOPOutputStream output;
   private InputStream input;
   private boolean responseExpected;
   private boolean exception = false;

   public CollocatedRequest(ServerReference ref, String method, boolean responseExpected, Object activationID) {
      ref = ref.getDelegate();
      if (ref instanceof InvokeHandler && ref instanceof InvokableServerReference) {
         this.serverReference = (InvokableServerReference)ref;
         this.method = method;
         this.responseExpected = responseExpected;
         this.activationID = activationID;
      } else {
         throw new IllegalArgumentException("ServerReference must be invokable: " + ref);
      }
   }

   public InputStream getInputStream() {
      if (this.input == null) {
         if (this.output == null) {
            throw new IllegalStateException("No OutputStream");
         }

         this.input = this.output.create_input_stream();
         this.output = null;
      }

      return this.input;
   }

   public OutputStream getOutputStream() {
      if (this.output == null) {
         this.output = new IIOPOutputStream(false, (EndPoint)null, this);
      }

      return this.output;
   }

   public ResponseHandler createResponseHandler(InboundRequest request) {
      if (request != this) {
         throw new IllegalArgumentException("request not valid: " + request);
      } else {
         return this;
      }
   }

   public OutputStream createExceptionReply() {
      this.closeInput();
      this.exception = true;
      return this.getOutputStream();
   }

   public OutputStream createReply() {
      this.closeInput();
      return this.getOutputStream();
   }

   private void closeInput() {
      if (this.input != null) {
         try {
            this.input.close();
         } catch (IOException var2) {
         }

         this.input = null;
      }

   }

   public String getMethod() {
      return this.method;
   }

   public boolean responseExpected() {
      return this.responseExpected;
   }

   public boolean isCollocated() {
      return true;
   }

   public void setTxContext(Object txContext) throws RemoteException {
      this.txContext = txContext;
   }

   public Object getTxContext() {
      return this.txContext;
   }

   public void invoke() throws Exception {
      Thread currentThread = null;
      ClassLoader savedCCL = null;
      ClassLoader newCCL = this.serverReference.getApplicationClassLoader();
      if (newCCL != null) {
         currentThread = Thread.currentThread();
         savedCCL = currentThread.getContextClassLoader();
         currentThread.setContextClassLoader(newCCL);
      }

      try {
         ManagedInvocationContext ignored = RmiInvocationFacade.setInvocationContext(KERNEL_ID, this.serverReference.getInvocationContext());
         Throwable var5 = null;

         try {
            ((InvokeHandler)this.serverReference).invoke((RuntimeMethodDescriptor)null, this, this.responseExpected ? this : null);
            if (this.exception) {
               InputStream is = this.getInputStream();
               is.mark(0);
               String id = is.read_string();
               is.reset();
               throw new ApplicationException(id, is);
            }
         } catch (Throwable var22) {
            var5 = var22;
            throw var22;
         } finally {
            if (ignored != null) {
               if (var5 != null) {
                  try {
                     ignored.close();
                  } catch (Throwable var21) {
                     var5.addSuppressed(var21);
                  }
               } else {
                  ignored.close();
               }
            }

         }
      } finally {
         if (savedCCL != null) {
            currentThread.setContextClassLoader(savedCCL);
         }

      }

   }

   public void close() throws IOException {
      this.closeInput();
      if (this.output != null) {
         this.output.close();
      }

   }

   public Object getActivationID() throws IOException {
      return this.activationID;
   }

   public int getMinorVersion() {
      return 2;
   }

   public byte getMaxStreamFormatVersion() {
      return 2;
   }

   public void transferThreadLocalContext(weblogic.rmi.spi.InboundRequest request) {
   }

   public void retrieveThreadLocalContext() throws IOException {
   }

   public void setContext(int contextid, Object data) throws IOException {
   }

   public Object getContext(int contextid) throws IOException {
      return null;
   }

   public X509Certificate[] getCertificateChain() {
      return null;
   }

   public ContextHandler getContextHandler() {
      return null;
   }

   public String getRequestUrl() {
      return null;
   }

   public Object getReplicaInfo() throws IOException {
      return null;
   }

   public void setPiggybackResponse(PiggybackResponse piggybackResponse) throws IOException {
   }

   public void sendThrowable(Throwable problem) {
      throw new UnsupportedOperationException("sendThrowable()");
   }

   public RuntimeMethodDescriptor getRuntimeMethodDescriptor(RuntimeDescriptor rd) {
      throw new UnsupportedOperationException("getRuntimeMethodDescriptor()");
   }

   public weblogic.rmi.spi.OutboundResponse getOutboundResponse() {
      throw new UnsupportedOperationException("getOutboundResponse()");
   }

   public weblogic.rmi.spi.EndPoint getEndPoint() {
      throw new UnsupportedOperationException("getEndPoint()");
   }

   public ServerChannel getServerChannel() {
      throw new UnsupportedOperationException("getServerChannel()");
   }

   public MsgInput getMsgInput() {
      throw new UnsupportedOperationException("getMsgInput()");
   }

   public MsgOutput getMsgOutput() {
      throw new UnsupportedOperationException("getMsgOutput()");
   }

   public AbstractSubject getSubject() {
      throw new UnsupportedOperationException("getSubject()");
   }

   public void send() throws RemoteException {
      throw new UnsupportedOperationException("send()");
   }
}
