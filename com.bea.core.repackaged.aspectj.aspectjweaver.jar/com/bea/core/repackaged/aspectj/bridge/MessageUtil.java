package com.bea.core.repackaged.aspectj.bridge;

import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MessageUtil {
   public static final IMessage ABORT_NOTHING_TO_RUN;
   public static final IMessage FAIL_INCOMPLETE;
   public static final IMessage ABORT_NOMESSAGE;
   public static final IMessage FAIL_NOMESSAGE;
   public static final IMessage ERROR_NOMESSAGE;
   public static final IMessage WARNING_NOMESSAGE;
   public static final IMessageHandler PICK_ALL;
   public static final IMessageHandler PICK_ABORT;
   public static final IMessageHandler PICK_DEBUG;
   public static final IMessageHandler PICK_ERROR;
   public static final IMessageHandler PICK_FAIL;
   public static final IMessageHandler PICK_INFO;
   public static final IMessageHandler PICK_WARNING;
   public static final IMessageHandler PICK_ABORT_PLUS;
   public static final IMessageHandler PICK_DEBUG_PLUS;
   public static final IMessageHandler PICK_ERROR_PLUS;
   public static final IMessageHandler PICK_FAIL_PLUS;
   public static final IMessageHandler PICK_INFO_PLUS;
   public static final IMessageHandler PICK_WARNING_PLUS;
   public static final IMessageRenderer MESSAGE_SCALED;
   public static final IMessageRenderer MESSAGE_LABEL;
   public static final IMessageRenderer MESSAGE_LABEL_NOLOC;
   public static final IMessageRenderer MESSAGE_LINE;
   public static final IMessageRenderer MESSAGE_LINE_FORCE_LOC;
   public static final IMessageRenderer MESSAGE_ALL;
   public static final IMessageRenderer MESSAGE_MOST;
   public static final IMessageRenderer MESSAGE_WIDELINE;
   public static final IMessageRenderer MESSAGE_TOSTRING;
   public static final IMessageRenderer MESSAGE_SHORT;

   public static boolean abort(IMessageHandler handler, String message) {
      return null != handler && handler.handleMessage(abort(message));
   }

   public static boolean abort(IMessageHandler handler, String message, Throwable t) {
      return handler != null ? handler.handleMessage(abort(message, t)) : false;
   }

   public static boolean fail(IMessageHandler handler, String message) {
      return null != handler && handler.handleMessage(fail(message));
   }

   public static boolean fail(IMessageHandler handler, String message, Throwable thrown) {
      return null != handler && handler.handleMessage(fail(message, thrown));
   }

   public static boolean error(IMessageHandler handler, String message) {
      return null != handler && handler.handleMessage(error(message));
   }

   public static boolean warn(IMessageHandler handler, String message) {
      return null != handler && handler.handleMessage(warn(message));
   }

   public static boolean debug(IMessageHandler handler, String message) {
      return null != handler && handler.handleMessage(debug(message));
   }

   public static boolean info(IMessageHandler handler, String message) {
      return null != handler && handler.handleMessage(info(message));
   }

   public static IMessage abort(String message) {
      return (IMessage)(LangUtil.isEmpty(message) ? ABORT_NOMESSAGE : new Message(message, IMessage.ABORT, (Throwable)null, (ISourceLocation)null));
   }

   public static IMessage abort(String message, Throwable thrown) {
      if (!LangUtil.isEmpty(message)) {
         return new Message(message, IMessage.ABORT, thrown, (ISourceLocation)null);
      } else {
         return (IMessage)(null == thrown ? ABORT_NOMESSAGE : new Message(thrown.getMessage(), IMessage.ABORT, thrown, (ISourceLocation)null));
      }
   }

   public static IMessage fail(String message) {
      return (IMessage)(LangUtil.isEmpty(message) ? FAIL_NOMESSAGE : new Message(message, IMessage.FAIL, (Throwable)null, ISourceLocation.EMPTY));
   }

   public static IMessage fail(String message, Throwable thrown) {
      if (LangUtil.isEmpty(message)) {
         return (IMessage)(null == thrown ? FAIL_NOMESSAGE : new Message(thrown.getMessage(), IMessage.FAIL, thrown, (ISourceLocation)null));
      } else {
         return new Message(message, IMessage.FAIL, thrown, (ISourceLocation)null);
      }
   }

   public static IMessage error(String message, ISourceLocation location) {
      return (IMessage)(LangUtil.isEmpty(message) ? ERROR_NOMESSAGE : new Message(message, IMessage.ERROR, (Throwable)null, location));
   }

   public static IMessage warn(String message, ISourceLocation location) {
      return (IMessage)(LangUtil.isEmpty(message) ? WARNING_NOMESSAGE : new Message(message, IMessage.WARNING, (Throwable)null, location));
   }

   public static IMessage error(String message) {
      return (IMessage)(LangUtil.isEmpty(message) ? ERROR_NOMESSAGE : new Message(message, IMessage.ERROR, (Throwable)null, (ISourceLocation)null));
   }

   public static IMessage warn(String message) {
      return (IMessage)(LangUtil.isEmpty(message) ? WARNING_NOMESSAGE : new Message(message, IMessage.WARNING, (Throwable)null, (ISourceLocation)null));
   }

   public static IMessage debug(String message) {
      return new Message(message, IMessage.DEBUG, (Throwable)null, (ISourceLocation)null);
   }

   public static IMessage info(String message) {
      return new Message(message, IMessage.INFO, (Throwable)null, (ISourceLocation)null);
   }

   public static void printMessageCounts(PrintStream out, IMessageHolder messageHolder) {
      if (null != out && null != messageHolder) {
         printMessageCounts(out, messageHolder, "");
      }
   }

   public static void printMessageCounts(PrintStream out, IMessageHolder holder, String prefix) {
      out.println(prefix + "MessageHolder: " + renderCounts(holder));
   }

   public static void print(PrintStream out, IMessageHolder messageHolder) {
      print(out, messageHolder, (String)null, (IMessageRenderer)null, (IMessageHandler)null);
   }

   public static void print(PrintStream out, IMessageHolder holder, String prefix) {
      print(out, holder, prefix, (IMessageRenderer)null, (IMessageHandler)null);
   }

   public static void print(PrintStream out, IMessageHolder holder, String prefix, IMessageRenderer renderer) {
      print(out, holder, prefix, renderer, (IMessageHandler)null);
   }

   public static void print(PrintStream out, IMessageHolder holder, String prefix, IMessageRenderer renderer, IMessageHandler selector) {
      print(out, holder, prefix, renderer, selector, true);
   }

   public static void print(PrintStream out, IMessageHolder holder, String prefix, IMessageRenderer renderer, IMessageHandler selector, boolean printSummary) {
      if (null != out && null != holder) {
         if (null == renderer) {
            renderer = MESSAGE_ALL;
         }

         if (null == selector) {
            selector = PICK_ALL;
         }

         if (printSummary) {
            out.println(prefix + "MessageHolder: " + renderCounts(holder));
         }

         Iterator i$ = IMessage.KINDS.iterator();

         while(true) {
            IMessage.Kind kind;
            do {
               if (!i$.hasNext()) {
                  return;
               }

               kind = (IMessage.Kind)i$.next();
            } while(selector.isIgnoring(kind));

            IMessage[] messages = holder.getMessages(kind, false);

            for(int i = 0; i < messages.length; ++i) {
               if (selector.handleMessage(messages[i])) {
                  String label = null == prefix ? "" : prefix + "[" + kind + " " + LangUtil.toSizedString((long)i, 3) + "]: ";
                  out.println(label + renderer.renderToString(messages[i]));
               }
            }
         }
      }
   }

   public static String toShortString(IMessage message) {
      if (null == message) {
         return "null";
      } else {
         String m = message.getMessage();
         Throwable t = message.getThrown();
         return message.getKind() + (null == m ? "" : ": " + m) + (null == t ? "" : ": " + LangUtil.unqualifiedClassName((Object)t));
      }
   }

   public static int numMessages(List messages, IMessage.Kind kind, boolean orGreater) {
      if (LangUtil.isEmpty((Collection)messages)) {
         return 0;
      } else {
         IMessageHandler selector = makeSelector(kind, orGreater, (String)null);
         IMessage[] result = visitMessages((Collection)messages, selector, true, false);
         return result.length;
      }
   }

   public static IMessage[] getMessagesExcept(IMessageHolder holder, final IMessage.Kind kind, final boolean orGreater) {
      if (null != holder && null != kind) {
         IMessageHandler selector = new IMessageHandler() {
            public boolean handleMessage(IMessage message) {
               boolean var10000;
               label23: {
                  IMessage.Kind test = message.getKind();
                  if (orGreater) {
                     if (!kind.isSameOrLessThan(test)) {
                        break label23;
                     }
                  } else if (kind != test) {
                     break label23;
                  }

                  var10000 = false;
                  return var10000;
               }

               var10000 = true;
               return var10000;
            }

            public boolean isIgnoring(IMessage.Kind kindx) {
               return false;
            }

            public void dontIgnore(IMessage.Kind kindx) {
            }

            public void ignore(IMessage.Kind kindx) {
            }
         };
         return visitMessages(holder, selector, true, false);
      } else {
         return new IMessage[0];
      }
   }

   public static List getMessages(IMessageHolder holder, IMessage.Kind kind, boolean orGreater, String infix) {
      if (null == holder) {
         return Collections.emptyList();
      } else if (null == kind && LangUtil.isEmpty(infix)) {
         return holder.getUnmodifiableListView();
      } else {
         IMessageHandler selector = makeSelector(kind, orGreater, infix);
         IMessage[] messages = visitMessages(holder, selector, true, false);
         return LangUtil.isEmpty((Object[])messages) ? Collections.emptyList() : Collections.unmodifiableList(Arrays.asList(messages));
      }
   }

   public static List getMessages(List messages, IMessage.Kind kind) {
      if (null == messages) {
         return Collections.emptyList();
      } else if (null == kind) {
         return messages;
      } else {
         ArrayList result = new ArrayList();
         Iterator i$ = messages.iterator();

         while(i$.hasNext()) {
            IMessage message = (IMessage)i$.next();
            if (kind == message.getKind()) {
               result.add(message);
            }
         }

         if (0 == result.size()) {
            return Collections.emptyList();
         } else {
            return result;
         }
      }
   }

   public static IMessage.Kind getKind(String kind) {
      if (null != kind) {
         kind = kind.toLowerCase();
         Iterator i$ = IMessage.KINDS.iterator();

         while(i$.hasNext()) {
            IMessage.Kind k = (IMessage.Kind)i$.next();
            if (kind.equals(k.toString())) {
               return k;
            }
         }
      }

      return null;
   }

   public static IMessage[] visitMessages(IMessageHolder holder, IMessageHandler visitor, boolean accumulate, boolean abortOnFail) {
      return null == holder ? IMessage.RA_IMessage : visitMessages((Collection)holder.getUnmodifiableListView(), visitor, accumulate, abortOnFail);
   }

   public static IMessage[] visitMessages(IMessage[] messages, IMessageHandler visitor, boolean accumulate, boolean abortOnFail) {
      return LangUtil.isEmpty((Object[])messages) ? IMessage.RA_IMessage : visitMessages((Collection)Arrays.asList(messages), visitor, accumulate, abortOnFail);
   }

   public static IMessage[] visitMessages(Collection messages, IMessageHandler visitor, boolean accumulate, boolean abortOnFail) {
      if (LangUtil.isEmpty(messages)) {
         return IMessage.RA_IMessage;
      } else {
         LangUtil.throwIaxIfNull(visitor, "visitor");
         ArrayList result = accumulate ? new ArrayList() : null;
         Iterator i$ = messages.iterator();

         while(i$.hasNext()) {
            IMessage m = (IMessage)i$.next();
            if (visitor.handleMessage(m)) {
               if (accumulate) {
                  result.add(m);
               }
            } else if (abortOnFail) {
               break;
            }
         }

         return accumulate && 0 != result.size() ? (IMessage[])result.toArray(IMessage.RA_IMessage) : IMessage.RA_IMessage;
      }
   }

   public static IMessageHandler makeSelector(IMessage.Kind kind, boolean orGreater, String infix) {
      if (!orGreater && LangUtil.isEmpty(infix)) {
         if (kind == IMessage.ABORT) {
            return PICK_ABORT;
         }

         if (kind == IMessage.DEBUG) {
            return PICK_DEBUG;
         }

         if (kind == IMessage.DEBUG) {
            return PICK_DEBUG;
         }

         if (kind == IMessage.ERROR) {
            return PICK_ERROR;
         }

         if (kind == IMessage.FAIL) {
            return PICK_FAIL;
         }

         if (kind == IMessage.INFO) {
            return PICK_INFO;
         }

         if (kind == IMessage.WARNING) {
            return PICK_WARNING;
         }
      }

      return new KindSelector(kind, orGreater, infix);
   }

   public static String renderMessage(IMessage message) {
      return renderMessage(message, true);
   }

   public static String renderMessage(IMessage message, boolean elide) {
      if (null == message) {
         return "((IMessage) null)";
      } else {
         ISourceLocation loc = message.getSourceLocation();
         String locString = null == loc ? "" : " at " + loc;
         String result = message.getKind() + locString + " " + message.getMessage();
         Throwable thrown = message.getThrown();
         if (thrown != null) {
            result = result + " -- " + LangUtil.renderExceptionShort(thrown);
            result = result + "\n" + LangUtil.renderException(thrown, elide);
         }

         return message.getExtraSourceLocations().isEmpty() ? result : addExtraSourceLocations(message, result);
      }
   }

   public static String addExtraSourceLocations(IMessage message, String baseMessage) {
      StringWriter buf = new StringWriter();
      PrintWriter writer = new PrintWriter(buf);
      writer.println(baseMessage);
      Iterator iter = message.getExtraSourceLocations().iterator();

      while(iter.hasNext()) {
         ISourceLocation element = (ISourceLocation)iter.next();
         if (element != null) {
            writer.print("\tsee also: " + element.toString());
            if (iter.hasNext()) {
               writer.println();
            }
         }
      }

      try {
         buf.close();
      } catch (IOException var6) {
      }

      return buf.getBuffer().toString();
   }

   public static String renderSourceLocation(ISourceLocation loc) {
      if (null == loc) {
         return "((ISourceLocation) null)";
      } else {
         StringBuffer sb = new StringBuffer();
         File sourceFile = loc.getSourceFile();
         if (sourceFile != ISourceLocation.NO_FILE) {
            sb.append(sourceFile.getPath());
            sb.append(":");
         }

         int line = loc.getLine();
         sb.append("" + line);
         int column = loc.getColumn();
         if (column != -2147483647) {
            sb.append(":" + column);
         }

         return sb.toString();
      }
   }

   public static String renderMessageLine(IMessage message, int textScale, int locScale, int max) {
      if (null == message) {
         return "((IMessage) null)";
      } else {
         if (max < 32) {
            max = 32;
         } else if (max > 10000) {
            max = 10000;
         }

         if (0 > textScale) {
            textScale = -textScale;
         }

         if (0 > locScale) {
            locScale = -locScale;
         }

         String text = message.getMessage();
         Throwable thrown = message.getThrown();
         ISourceLocation sl = message.getSourceLocation();
         IMessage.Kind kind = message.getKind();
         StringBuffer result = new StringBuffer();
         result.append(kind.toString());
         result.append(": ");
         if (null != thrown) {
            result.append(LangUtil.unqualifiedClassName((Object)thrown) + " ");
            if (null == text || "".equals(text)) {
               text = thrown.getMessage();
            }
         }

         String loc;
         if (0 == textScale) {
            text = "";
         } else if (null != text && null != thrown) {
            loc = thrown.getMessage();
            if (null != loc && 0 < loc.length()) {
               text = text + " - " + loc;
            }
         }

         loc = "";
         int textSize;
         int locSize;
         if (0 != locScale && null != sl) {
            File f = sl.getSourceFile();
            if (f == ISourceLocation.NO_FILE) {
               f = null;
            }

            if (null != f) {
               loc = f.getName();
            }

            int line = sl.getLine();
            textSize = sl.getColumn();
            locSize = sl.getEndLine();
            if (0 != line || 0 != textSize || 0 != locSize) {
               loc = loc + ":" + line + (textSize == 0 ? "" : ":" + textSize);
               if (line != locSize) {
                  loc = loc + ":" + locSize;
               }
            }

            if (!LangUtil.isEmpty(loc)) {
               loc = "@[" + loc;
            }
         }

         float totalScale = (float)(locScale + textScale);
         float remainder = (float)(max - result.length() - 4);
         if (remainder > 0.0F && 0.0F < totalScale) {
            textSize = (int)(remainder * (float)textScale / totalScale);
            locSize = (int)(remainder * (float)locScale / totalScale);
            int extra = locSize - loc.length();
            if (0 < extra) {
               locSize = loc.length();
               textSize += extra;
            }

            extra = textSize - text.length();
            if (0 < extra) {
               textSize = text.length();
               if (locSize < loc.length()) {
                  locSize += extra;
               }
            }

            if (locSize > loc.length()) {
               locSize = loc.length();
            }

            if (textSize > text.length()) {
               textSize = text.length();
            }

            if (0 < textSize) {
               result.append(text.substring(0, textSize));
            }

            if (0 < locSize) {
               if (0 < textSize) {
                  result.append(" ");
               }

               result.append(loc.substring(0, locSize) + "]");
            }
         }

         return result.toString();
      }
   }

   public static String renderCounts(IMessageHolder holder) {
      if (0 == holder.numMessages((IMessage.Kind)null, false)) {
         return "(0 messages)";
      } else {
         StringBuffer sb = new StringBuffer();
         Iterator i$ = IMessage.KINDS.iterator();

         while(i$.hasNext()) {
            IMessage.Kind kind = (IMessage.Kind)i$.next();
            int num = holder.numMessages(kind, false);
            if (0 < num) {
               sb.append(" (" + num + " " + kind + ") ");
            }
         }

         return sb.toString();
      }
   }

   public static PrintStream handlerPrintStream(final IMessageHandler handler, final IMessage.Kind kind, final OutputStream overage, final String prefix) {
      LangUtil.throwIaxIfNull(handler, "handler");
      LangUtil.throwIaxIfNull(kind, "kind");

      class HandlerPrintStream extends PrintStream {
         HandlerPrintStream() {
            super((OutputStream)(null == overage ? System.out : overage));
         }

         public void println() {
            this.println("");
         }

         public void println(Object o) {
            this.println(null == o ? "null" : o.toString());
         }

         public void println(String input) {
            String textMessage = null == prefix ? input : prefix + input;
            IMessage m = new Message(textMessage, kind, (Throwable)null, (ISourceLocation)null);
            handler.handleMessage(m);
         }
      }

      return new HandlerPrintStream();
   }

   private MessageUtil() {
   }

   public static boolean handleAll(IMessageHandler sink, IMessageHolder source, boolean fastFail) {
      return handleAll(sink, source, (IMessage.Kind)null, true, fastFail);
   }

   public static boolean handleAll(IMessageHandler sink, IMessageHolder source, IMessage.Kind kind, boolean orGreater, boolean fastFail) {
      LangUtil.throwIaxIfNull(sink, "sink");
      LangUtil.throwIaxIfNull(source, "source");
      return handleAll(sink, source.getMessages(kind, orGreater), fastFail);
   }

   public static boolean handleAllExcept(IMessageHandler sink, IMessageHolder source, IMessage.Kind kind, boolean orGreater, boolean fastFail) {
      LangUtil.throwIaxIfNull(sink, "sink");
      LangUtil.throwIaxIfNull(source, "source");
      if (null == kind) {
         return true;
      } else {
         IMessage[] messages = getMessagesExcept(source, kind, orGreater);
         return handleAll(sink, messages, fastFail);
      }
   }

   public static boolean handleAll(IMessageHandler sink, IMessage[] sources, boolean fastFail) {
      LangUtil.throwIaxIfNull(sink, "sink");
      if (LangUtil.isEmpty((Object[])sources)) {
         return true;
      } else {
         boolean result = true;

         for(int i = 0; i < sources.length; ++i) {
            if (!sink.handleMessage(sources[i])) {
               if (fastFail) {
                  return false;
               }

               if (result) {
                  result = false;
               }
            }
         }

         return result;
      }
   }

   static {
      ABORT_NOTHING_TO_RUN = new Message("aborting - nothing to run", IMessage.ABORT, (Throwable)null, (ISourceLocation)null);
      FAIL_INCOMPLETE = new Message("run not completed", IMessage.FAIL, (Throwable)null, (ISourceLocation)null);
      ABORT_NOMESSAGE = new Message("", IMessage.ABORT, (Throwable)null, (ISourceLocation)null);
      FAIL_NOMESSAGE = new Message("", IMessage.FAIL, (Throwable)null, (ISourceLocation)null);
      ERROR_NOMESSAGE = new Message("", IMessage.ERROR, (Throwable)null, (ISourceLocation)null);
      WARNING_NOMESSAGE = new Message("", IMessage.WARNING, (Throwable)null, (ISourceLocation)null);
      PICK_ALL = new KindSelector((IMessage.Kind)null);
      PICK_ABORT = new KindSelector(IMessage.ABORT);
      PICK_DEBUG = new KindSelector(IMessage.DEBUG);
      PICK_ERROR = new KindSelector(IMessage.ERROR);
      PICK_FAIL = new KindSelector(IMessage.FAIL);
      PICK_INFO = new KindSelector(IMessage.INFO);
      PICK_WARNING = new KindSelector(IMessage.WARNING);
      PICK_ABORT_PLUS = new KindSelector(IMessage.ABORT, true);
      PICK_DEBUG_PLUS = new KindSelector(IMessage.DEBUG, true);
      PICK_ERROR_PLUS = new KindSelector(IMessage.ERROR, true);
      PICK_FAIL_PLUS = new KindSelector(IMessage.FAIL, true);
      PICK_INFO_PLUS = new KindSelector(IMessage.INFO, true);
      PICK_WARNING_PLUS = new KindSelector(IMessage.WARNING, true);
      MESSAGE_SCALED = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_SCALED";
         }

         public String renderToString(IMessage message) {
            if (null == message) {
               return "((IMessage) null)";
            } else {
               IMessage.Kind kind = message.getKind();
               int levelx = true;
               byte level;
               if (kind != IMessage.ABORT && kind != IMessage.FAIL) {
                  if (kind != IMessage.ERROR && kind != IMessage.WARNING) {
                     level = 3;
                  } else {
                     level = 2;
                  }
               } else {
                  level = 1;
               }

               String result = null;
               switch (level) {
                  case 1:
                     result = MessageUtil.MESSAGE_TOSTRING.renderToString(message);
                     break;
                  case 2:
                     result = MessageUtil.MESSAGE_LINE.renderToString(message);
                     break;
                  case 3:
                     result = MessageUtil.MESSAGE_SHORT.renderToString(message);
               }

               Throwable thrown = message.getThrown();
               if (null != thrown) {
                  if (level == 3) {
                     result = result + "Thrown: \n" + LangUtil.renderExceptionShort(thrown);
                  } else {
                     result = result + "Thrown: \n" + LangUtil.renderException(thrown);
                  }
               }

               return result;
            }
         }
      };
      MESSAGE_LABEL = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_LABEL";
         }

         public String renderToString(IMessage message) {
            return null == message ? "((IMessage) null)" : MessageUtil.renderMessageLine(message, 5, 5, 32);
         }
      };
      MESSAGE_LABEL_NOLOC = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_LABEL_NOLOC";
         }

         public String renderToString(IMessage message) {
            return null == message ? "((IMessage) null)" : MessageUtil.renderMessageLine(message, 10, 0, 32);
         }
      };
      MESSAGE_LINE = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_LINE";
         }

         public String renderToString(IMessage message) {
            return null == message ? "((IMessage) null)" : MessageUtil.renderMessageLine(message, 8, 2, 74);
         }
      };
      MESSAGE_LINE_FORCE_LOC = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_LINE_FORCE_LOC";
         }

         public String renderToString(IMessage message) {
            return null == message ? "((IMessage) null)" : MessageUtil.renderMessageLine(message, 2, 40, 74);
         }
      };
      MESSAGE_ALL = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_ALL";
         }

         public String renderToString(IMessage message) {
            return MessageUtil.renderMessage(message);
         }
      };
      MESSAGE_MOST = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_MOST";
         }

         public String renderToString(IMessage message) {
            return null == message ? "((IMessage) null)" : MessageUtil.renderMessageLine(message, 1, 1, 10000);
         }
      };
      MESSAGE_WIDELINE = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_WIDELINE";
         }

         public String renderToString(IMessage message) {
            return null == message ? "((IMessage) null)" : MessageUtil.renderMessageLine(message, 8, 2, 255);
         }
      };
      MESSAGE_TOSTRING = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_TOSTRING";
         }

         public String renderToString(IMessage message) {
            return null == message ? "((IMessage) null)" : message.toString();
         }
      };
      MESSAGE_SHORT = new IMessageRenderer() {
         public String toString() {
            return "MESSAGE_SHORT";
         }

         public String renderToString(IMessage message) {
            return MessageUtil.toShortString(message);
         }
      };
   }

   public interface IMessageRenderer {
      String renderToString(IMessage var1);
   }

   private static class KindSelector implements IMessageHandler {
      final IMessage.Kind sought;
      final boolean floor;
      final String infix;

      KindSelector(IMessage.Kind sought) {
         this(sought, false);
      }

      KindSelector(IMessage.Kind sought, boolean floor) {
         this(sought, floor, (String)null);
      }

      KindSelector(IMessage.Kind sought, boolean floor, String infix) {
         this.sought = sought;
         this.floor = floor;
         this.infix = LangUtil.isEmpty(infix) ? null : infix;
      }

      public boolean handleMessage(IMessage message) {
         return null != message && !this.isIgnoring(message.getKind()) && this.textIn(message);
      }

      public boolean isIgnoring(IMessage.Kind kind) {
         if (this.floor) {
            if (null == this.sought) {
               return false;
            } else {
               return 0 < IMessage.Kind.COMPARATOR.compare(this.sought, kind);
            }
         } else {
            return null != this.sought && this.sought != kind;
         }
      }

      public void dontIgnore(IMessage.Kind kind) {
      }

      private boolean textIn(IMessage message) {
         if (null == this.infix) {
            return true;
         } else {
            String text = message.getMessage();
            return text.indexOf(this.infix) != -1;
         }
      }

      public void ignore(IMessage.Kind kind) {
      }
   }
}
