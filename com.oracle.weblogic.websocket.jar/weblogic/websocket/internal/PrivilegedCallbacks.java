package weblogic.websocket.internal;

import java.io.IOException;
import java.security.PrivilegedAction;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.websocket.ClosingMessage;
import weblogic.websocket.WebSocketConnection;
import weblogic.websocket.WebSocketListener;

class PrivilegedCallbacks {
   public static void onOpen(final WebSocketListener listener, final WebSocketConnection connection) {
      SubjectHandle subject = getAuthenticatedUser(connection);
      subject.run(new PrivilegedAction() {
         public Throwable run() {
            try {
               listener.onOpen(connection);
               return null;
            } catch (Throwable var2) {
               PrivilegedCallbacks.handleException(listener, connection, var2);
               return var2;
            }
         }
      });
   }

   public static void onMessageProcess(final WebSocketListener listener, final WebSocketConnection connection, final AbstractWebSocketMessage message) {
      SubjectHandle subject = getAuthenticatedUser(connection);
      subject.run(new PrivilegedAction() {
         public Throwable run() {
            try {
               message.process(connection, listener);
               return null;
            } catch (Throwable var2) {
               PrivilegedCallbacks.handleException(listener, connection, var2);
               return var2;
            }
         }
      });
   }

   public static void onClose(final WebSocketListener listener, final WebSocketConnection connection, final ClosingMessage message) {
      SubjectHandle subject = getAuthenticatedUser(connection);
      subject.run(new PrivilegedAction() {
         public Throwable run() {
            try {
               listener.onClose(connection, message);
               return null;
            } catch (Throwable var2) {
               return var2;
            }
         }
      });
   }

   public static void onTimeout(final WebSocketListener listener, final WebSocketConnection connection) {
      SubjectHandle subject = getAuthenticatedUser(connection);
      subject.run(new PrivilegedAction() {
         public Throwable run() {
            try {
               listener.onTimeout(connection);
               return null;
            } catch (Throwable var2) {
               return var2;
            }
         }
      });
   }

   public static void onError(final WebSocketListener listener, final WebSocketConnection connection, final Throwable cause) {
      SubjectHandle subject = getAuthenticatedUser(connection);
      subject.run(new PrivilegedAction() {
         public Throwable run() {
            try {
               listener.onError(connection, cause);
               return null;
            } catch (Throwable var2) {
               return var2;
            }
         }
      });
   }

   private static SubjectHandle getAuthenticatedUser(WebSocketConnection connection) {
      SubjectHandle subject = ((MuxableWebSocket)connection).getAuthenticatedUser();
      return subject == null ? WebAppSecurity.getProvider().getAnonymousSubject() : subject;
   }

   private static void handleException(WebSocketListener listener, WebSocketConnection connection, Throwable cause) {
      try {
         listener.onError(connection, cause);
      } catch (Throwable var6) {
         try {
            connection.close(1011);
         } catch (IOException var5) {
         }
      }

   }
}
