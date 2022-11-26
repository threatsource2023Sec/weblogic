package com.bea.core.repackaged.aspectj.bridge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MessageHandler implements IMessageHolder {
   protected final ArrayList messages;
   protected final ArrayList ignoring;
   protected boolean handleMessageResult;
   protected IMessageHandler interceptor;

   public MessageHandler() {
      this(false);
   }

   public MessageHandler(boolean accumulateOnly) {
      this.messages = new ArrayList();
      this.ignoring = new ArrayList();
      this.init(accumulateOnly);
      this.ignore(IMessage.WEAVEINFO);
   }

   public void init() {
      this.init(false);
   }

   public void init(boolean accumulateOnly) {
      this.handleMessageResult = accumulateOnly;
      if (0 < this.messages.size()) {
         this.messages.clear();
      }

      if (0 < this.ignoring.size()) {
         boolean ignoringWeaveMessages = this.isIgnoring(IMessage.WEAVEINFO);
         this.ignoring.clear();
         if (ignoringWeaveMessages) {
            this.ignore(IMessage.WEAVEINFO);
         }
      }

      if (null != this.interceptor) {
         this.interceptor = null;
      }

   }

   public void clearMessages() {
      if (0 < this.messages.size()) {
         this.messages.clear();
      }

   }

   public boolean handleMessage(IMessage message) {
      if (null != this.interceptor && this.interceptor.handleMessage(message)) {
         return true;
      } else if (null == message) {
         throw new IllegalArgumentException("null message");
      } else {
         if (!this.ignoring.contains(message.getKind())) {
            this.messages.add(message);
         }

         return this.handleMessageResult;
      }
   }

   public boolean isIgnoring(IMessage.Kind kind) {
      return null != kind && this.ignoring.contains(kind);
   }

   public void ignore(IMessage.Kind kind) {
      if (null != kind && !this.ignoring.contains(kind)) {
         this.ignoring.add(kind);
      }

   }

   public void dontIgnore(IMessage.Kind kind) {
      if (null != kind) {
         this.ignoring.remove(kind);
      }

   }

   public boolean hasAnyMessage(IMessage.Kind kind, boolean orGreater) {
      if (null == kind) {
         return 0 < this.messages.size();
      } else {
         Iterator i$;
         IMessage m;
         if (!orGreater) {
            i$ = this.messages.iterator();

            while(i$.hasNext()) {
               m = (IMessage)i$.next();
               if (kind == m.getKind()) {
                  return true;
               }
            }
         } else {
            i$ = this.messages.iterator();

            while(i$.hasNext()) {
               m = (IMessage)i$.next();
               if (kind.isSameOrLessThan(m.getKind())) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public int numMessages(IMessage.Kind kind, boolean orGreater) {
      if (null == kind) {
         return this.messages.size();
      } else {
         int result = 0;
         Iterator i$;
         IMessage m;
         if (!orGreater) {
            i$ = this.messages.iterator();

            while(i$.hasNext()) {
               m = (IMessage)i$.next();
               if (kind == m.getKind()) {
                  ++result;
               }
            }
         } else {
            i$ = this.messages.iterator();

            while(i$.hasNext()) {
               m = (IMessage)i$.next();
               if (kind.isSameOrLessThan(m.getKind())) {
                  ++result;
               }
            }
         }

         return result;
      }
   }

   public List getUnmodifiableListView() {
      return Collections.unmodifiableList(this.messages);
   }

   public IMessage[] getMessages(IMessage.Kind kind, boolean orGreater) {
      if (null == kind) {
         return (IMessage[])this.messages.toArray(IMessage.RA_IMessage);
      } else {
         ArrayList result = new ArrayList();
         Iterator i$;
         IMessage m;
         if (!orGreater) {
            i$ = this.messages.iterator();

            while(i$.hasNext()) {
               m = (IMessage)i$.next();
               if (kind == m.getKind()) {
                  result.add(m);
               }
            }
         } else {
            i$ = this.messages.iterator();

            while(i$.hasNext()) {
               m = (IMessage)i$.next();
               if (kind.isSameOrLessThan(m.getKind())) {
                  result.add(m);
               }
            }
         }

         return 0 == result.size() ? IMessage.RA_IMessage : (IMessage[])result.toArray(IMessage.RA_IMessage);
      }
   }

   public IMessage[] getErrors() {
      return this.getMessages(IMessage.ERROR, false);
   }

   public IMessage[] getWarnings() {
      return this.getMessages(IMessage.WARNING, false);
   }

   public void setInterceptor(IMessageHandler interceptor) {
      this.interceptor = interceptor;
   }

   public String toString() {
      return 0 == this.messages.size() ? "MessageHandler: no messages" : "MessageHandler: " + this.messages;
   }
}
