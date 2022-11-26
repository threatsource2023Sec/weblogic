package weblogic.i18n.tools;

import java.util.Vector;

public final class MessageFinder {
   private static final boolean debug = false;
   private static int lastFoundLogIndex = -1;

   public static LogMessage findLogMessage(MessageCatalog msgCat, boolean findFirst, String msgId, String method, String msgText) {
      int i = false;
      boolean found = false;
      LogMessage msg = null;
      Vector logMsgV = msgCat.getLogMessages();
      LogMessage[] logMsgs = new LogMessage[logMsgV.size()];
      logMsgs = (LogMessage[])((LogMessage[])logMsgV.toArray(logMsgs));
      int startIndex;
      if (findFirst) {
         startIndex = 0;
      } else {
         startIndex = lastFoundLogIndex + 1;
      }

      int i;
      for(i = startIndex; i < logMsgs.length; ++i) {
         if (checkIdMatch(logMsgs[i], msgId) && checkMethodMatch(logMsgs[i], method) && checkLogTextMatch(logMsgs[i], msgText)) {
            found = true;
            lastFoundLogIndex = i;
            break;
         }
      }

      if (found) {
         msg = logMsgs[i];
      }

      return msg;
   }

   private static boolean checkIdMatch(BasicMessage msg, String msgId) {
      boolean matches = false;
      if (msgId != null && !msgId.equals("")) {
         if (msgId.equals(msg.getMessageId())) {
            matches = true;
         }
      } else {
         matches = true;
      }

      return matches;
   }

   private static boolean checkMethodMatch(LogMessage logMsg, String method) {
      boolean matches = false;
      if (method != null && !method.equals("")) {
         if (method.equals(logMsg.getMethodName())) {
            matches = true;
         }
      } else {
         matches = true;
      }

      return matches;
   }

   private static boolean checkLogTextMatch(LogMessage logMsg, String msgText) {
      boolean matches = false;
      if (msgText != null && !msgText.equals("")) {
         if (logMsg.getMessageBody().getCdata().toUpperCase().indexOf(msgText.toUpperCase()) != -1) {
            matches = true;
         } else if (logMsg.getMessageDetail().getCdata().toUpperCase().indexOf(msgText.toUpperCase()) != -1) {
            matches = true;
         } else if (logMsg.getAction().getCdata().toUpperCase().indexOf(msgText.toUpperCase()) != -1) {
            matches = true;
         } else if (logMsg.getCause().getCdata().toUpperCase().indexOf(msgText.toUpperCase()) != -1) {
            matches = true;
         }
      } else {
         matches = true;
      }

      return matches;
   }

   private static boolean checkSimpleTextMatch(Message msg, String msgText) {
      boolean matches = false;
      if (msgText != null && !msgText.equals("")) {
         if (msg.getMessageBody().getCdata().toUpperCase().indexOf(msgText.toUpperCase()) != -1) {
            matches = true;
         }
      } else {
         matches = true;
      }

      return matches;
   }

   public static Message findSimpleMessage(MessageCatalog msgCat, boolean findFirst, String msgId, String msgText) {
      int i = false;
      boolean found = false;
      Message msg = null;
      Vector simpleMsgV = msgCat.getMessages();
      Message[] simpleMsgs = new Message[simpleMsgV.size()];
      simpleMsgs = (Message[])((Message[])simpleMsgV.toArray(simpleMsgs));
      int startIndex;
      if (findFirst) {
         startIndex = 0;
      } else {
         startIndex = lastFoundLogIndex + 1;
      }

      int i;
      for(i = startIndex; i < simpleMsgs.length; ++i) {
         if (checkIdMatch(simpleMsgs[i], msgId) && checkSimpleTextMatch(simpleMsgs[i], msgText)) {
            found = true;
            lastFoundLogIndex = i;
            break;
         }
      }

      if (found) {
         msg = simpleMsgs[i];
      }

      return msg;
   }
}
