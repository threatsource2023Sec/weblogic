package com.bea.core.repackaged.aspectj.bridge.context;

import com.bea.core.repackaged.aspectj.bridge.AbortException;
import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class PinpointingMessageHandler implements IMessageHandler {
   private IMessageHandler delegate;

   public PinpointingMessageHandler(IMessageHandler delegate) {
      this.delegate = delegate;
   }

   public boolean handleMessage(IMessage message) throws AbortException {
      if (!this.isIgnoring(message.getKind())) {
         MessageIssued ex = new MessageIssued();
         ex.fillInStackTrace();
         StringWriter sw = new StringWriter();
         ex.printStackTrace(new PrintWriter(sw));
         StringBuffer sb = new StringBuffer();
         sb.append(CompilationAndWeavingContext.getCurrentContext());
         sb.append(sw.toString());
         IMessage pinpointedMessage = new PinpointedMessage(message, sb.toString());
         return this.delegate.handleMessage(pinpointedMessage);
      } else {
         return this.delegate.handleMessage(message);
      }
   }

   public boolean isIgnoring(IMessage.Kind kind) {
      return this.delegate.isIgnoring(kind);
   }

   public void dontIgnore(IMessage.Kind kind) {
      this.delegate.dontIgnore(kind);
   }

   public void ignore(IMessage.Kind kind) {
      this.delegate.ignore(kind);
   }

   private static class MessageIssued extends RuntimeException {
      private static final long serialVersionUID = 1L;

      private MessageIssued() {
      }

      public String getMessage() {
         return "message issued...";
      }

      // $FF: synthetic method
      MessageIssued(Object x0) {
         this();
      }
   }

   private static class PinpointedMessage implements IMessage {
      private IMessage delegate;
      private String message;

      public PinpointedMessage(IMessage delegate, String pinpoint) {
         this.delegate = delegate;
         this.message = delegate.getMessage() + "\n" + pinpoint;
      }

      public String getMessage() {
         return this.message;
      }

      public IMessage.Kind getKind() {
         return this.delegate.getKind();
      }

      public boolean isError() {
         return this.delegate.isError();
      }

      public boolean isWarning() {
         return this.delegate.isWarning();
      }

      public boolean isDebug() {
         return this.delegate.isDebug();
      }

      public boolean isInfo() {
         return this.delegate.isInfo();
      }

      public boolean isAbort() {
         return this.delegate.isAbort();
      }

      public boolean isTaskTag() {
         return this.delegate.isTaskTag();
      }

      public boolean isFailed() {
         return this.delegate.isFailed();
      }

      public boolean getDeclared() {
         return this.delegate.getDeclared();
      }

      public int getID() {
         return this.delegate.getID();
      }

      public int getSourceStart() {
         return this.delegate.getSourceStart();
      }

      public int getSourceEnd() {
         return this.delegate.getSourceEnd();
      }

      public Throwable getThrown() {
         return this.delegate.getThrown();
      }

      public ISourceLocation getSourceLocation() {
         return this.delegate.getSourceLocation();
      }

      public String getDetails() {
         return this.delegate.getDetails();
      }

      public List getExtraSourceLocations() {
         return this.delegate.getExtraSourceLocations();
      }
   }
}
