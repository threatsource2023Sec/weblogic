package org.python.jline.console;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Stack;
import org.python.jline.DefaultTerminal2;
import org.python.jline.Terminal;
import org.python.jline.Terminal2;
import org.python.jline.TerminalFactory;
import org.python.jline.UnixTerminal;
import org.python.jline.console.completer.CandidateListCompletionHandler;
import org.python.jline.console.completer.Completer;
import org.python.jline.console.completer.CompletionHandler;
import org.python.jline.console.history.History;
import org.python.jline.console.history.MemoryHistory;
import org.python.jline.internal.Ansi;
import org.python.jline.internal.Configuration;
import org.python.jline.internal.Curses;
import org.python.jline.internal.InputStreamReader;
import org.python.jline.internal.Log;
import org.python.jline.internal.NonBlockingInputStream;
import org.python.jline.internal.Nullable;
import org.python.jline.internal.Preconditions;
import org.python.jline.internal.Urls;

public class ConsoleReader implements Closeable {
   public static final String JLINE_NOBELL = "org.python.jline.nobell";
   public static final String JLINE_ESC_TIMEOUT = "org.python.jline.esc.timeout";
   public static final String JLINE_INPUTRC = "org.python.jline.inputrc";
   public static final String INPUT_RC = ".inputrc";
   public static final String DEFAULT_INPUT_RC = "/etc/inputrc";
   public static final String JLINE_EXPAND_EVENTS = "org.python.jline.expandevents";
   public static final char BACKSPACE = '\b';
   public static final char RESET_LINE = '\r';
   public static final char KEYBOARD_BELL = '\u0007';
   public static final char NULL_MASK = '\u0000';
   public static final int TAB_WIDTH = 8;
   private static final ResourceBundle resources = ResourceBundle.getBundle(CandidateListCompletionHandler.class.getName());
   private static final int ESCAPE = 27;
   private static final int READ_EXPIRED = -2;
   private final Terminal2 terminal;
   private final Writer out;
   private final CursorBuffer buf;
   private boolean cursorOk;
   private String prompt;
   private int promptLen;
   private boolean expandEvents;
   private boolean bellEnabled;
   private boolean handleUserInterrupt;
   private boolean handleLitteralNext;
   private Character mask;
   private Character echoCharacter;
   private CursorBuffer originalBuffer;
   private StringBuffer searchTerm;
   private String previousSearchTerm;
   private int searchIndex;
   private int parenBlinkTimeout;
   private final StringBuilder opBuffer;
   private final Stack pushBackChar;
   private NonBlockingInputStream in;
   private long escapeTimeout;
   private Reader reader;
   private char charSearchChar;
   private char charSearchLastInvokeChar;
   private char charSearchFirstInvokeChar;
   private String yankBuffer;
   private KillRing killRing;
   private String encoding;
   private boolean quotedInsert;
   private boolean recording;
   private String macro;
   private String appName;
   private URL inputrcUrl;
   private ConsoleKeys consoleKeys;
   private String commentBegin;
   private boolean skipLF;
   private boolean copyPasteDetection;
   private State state;
   public static final String JLINE_COMPLETION_THRESHOLD = "org.python.jline.completion.threshold";
   private final List completers;
   private CompletionHandler completionHandler;
   private int autoprintThreshold;
   private boolean paginationEnabled;
   private History history;
   private boolean historyEnabled;
   private Thread maskThread;

   public ConsoleReader() throws IOException {
      this((String)null, new FileInputStream(FileDescriptor.in), System.out, (Terminal)null);
   }

   public ConsoleReader(InputStream in, OutputStream out) throws IOException {
      this((String)null, in, out, (Terminal)null);
   }

   public ConsoleReader(InputStream in, OutputStream out, Terminal term) throws IOException {
      this((String)null, in, out, term);
   }

   public ConsoleReader(@Nullable String appName, InputStream in, OutputStream out, @Nullable Terminal term) throws IOException {
      this(appName, in, out, term, (String)null);
   }

   public ConsoleReader(@Nullable String appName, InputStream in, OutputStream out, @Nullable Terminal term, @Nullable String encoding) throws IOException {
      this.buf = new CursorBuffer();
      this.expandEvents = Configuration.getBoolean("org.python.jline.expandevents", true);
      this.bellEnabled = !Configuration.getBoolean("org.python.jline.nobell", true);
      this.handleUserInterrupt = false;
      this.handleLitteralNext = true;
      this.originalBuffer = null;
      this.searchTerm = null;
      this.previousSearchTerm = "";
      this.searchIndex = -1;
      this.parenBlinkTimeout = 500;
      this.opBuffer = new StringBuilder();
      this.pushBackChar = new Stack();
      this.charSearchChar = 0;
      this.charSearchLastInvokeChar = 0;
      this.charSearchFirstInvokeChar = 0;
      this.yankBuffer = "";
      this.killRing = new KillRing();
      this.macro = "";
      this.commentBegin = null;
      this.skipLF = false;
      this.copyPasteDetection = false;
      this.state = ConsoleReader.State.NORMAL;
      this.completers = new LinkedList();
      this.completionHandler = new CandidateListCompletionHandler();
      this.autoprintThreshold = Configuration.getInteger("org.python.jline.completion.threshold", 100);
      this.history = new MemoryHistory();
      this.historyEnabled = true;
      this.appName = appName != null ? appName : "JLine";
      this.encoding = encoding != null ? encoding : Configuration.getEncoding();
      Terminal terminal = term != null ? term : TerminalFactory.get();
      this.terminal = (Terminal2)(terminal instanceof Terminal2 ? (Terminal2)terminal : new DefaultTerminal2(terminal));
      String outEncoding = terminal.getOutputEncoding() != null ? terminal.getOutputEncoding() : this.encoding;
      this.out = new OutputStreamWriter(terminal.wrapOutIfNeeded(out), outEncoding);
      this.setInput(in);
      this.inputrcUrl = getInputRc();
      this.consoleKeys = new ConsoleKeys(this.appName, this.inputrcUrl);
      if (terminal instanceof UnixTerminal && "/dev/tty".equals(((UnixTerminal)terminal).getSettings().getTtyDevice()) && Configuration.getBoolean("org.python.jline.sigcont", false)) {
         this.setupSigCont();
      }

   }

   private void setupSigCont() {
      try {
         Class signalClass = Class.forName("sun.misc.Signal");
         Class signalHandlerClass = Class.forName("sun.misc.SignalHandler");
         Object signalHandler = Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{signalHandlerClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               ConsoleReader.this.terminal.init();

               try {
                  ConsoleReader.this.drawLine();
                  ConsoleReader.this.flush();
               } catch (IOException var5) {
                  var5.printStackTrace();
               }

               return null;
            }
         });
         signalClass.getMethod("handle", signalClass, signalHandlerClass).invoke((Object)null, signalClass.getConstructor(String.class).newInstance("CONT"), signalHandler);
      } catch (ClassNotFoundException var4) {
      } catch (Exception var5) {
      }

   }

   private static URL getInputRc() throws IOException {
      String path = Configuration.getString("org.python.jline.inputrc");
      if (path == null) {
         File f = new File(Configuration.getUserHome(), ".inputrc");
         if (!f.exists()) {
            f = new File("/etc/inputrc");
         }

         return f.toURI().toURL();
      } else {
         return Urls.create(path);
      }
   }

   public KeyMap getKeys() {
      return this.consoleKeys.getKeys();
   }

   void setInput(InputStream in) throws IOException {
      this.escapeTimeout = Configuration.getLong("org.python.jline.esc.timeout", 100L);
      boolean nonBlockingEnabled = this.escapeTimeout > 0L && this.terminal.isSupported() && in != null;
      if (this.in != null) {
         this.in.shutdown();
      }

      InputStream wrapped = this.terminal.wrapInIfNeeded(in);
      this.in = new NonBlockingInputStream(wrapped, nonBlockingEnabled);
      this.reader = new InputStreamReader(this.in, this.encoding);
   }

   public void close() {
      if (this.in != null) {
         this.in.shutdown();
      }

   }

   /** @deprecated */
   @Deprecated
   public void shutdown() {
      this.close();
   }

   protected void finalize() throws Throwable {
      try {
         this.close();
      } finally {
         super.finalize();
      }

   }

   public InputStream getInput() {
      return this.in;
   }

   public Writer getOutput() {
      return this.out;
   }

   public Terminal getTerminal() {
      return this.terminal;
   }

   public CursorBuffer getCursorBuffer() {
      return this.buf;
   }

   public void setExpandEvents(boolean expand) {
      this.expandEvents = expand;
   }

   public boolean getExpandEvents() {
      return this.expandEvents;
   }

   public void setCopyPasteDetection(boolean onoff) {
      this.copyPasteDetection = onoff;
   }

   public boolean isCopyPasteDetectionEnabled() {
      return this.copyPasteDetection;
   }

   public void setBellEnabled(boolean enabled) {
      this.bellEnabled = enabled;
   }

   public boolean getBellEnabled() {
      return this.bellEnabled;
   }

   public void setHandleUserInterrupt(boolean enabled) {
      this.handleUserInterrupt = enabled;
   }

   public boolean getHandleUserInterrupt() {
      return this.handleUserInterrupt;
   }

   public void setHandleLitteralNext(boolean handleLitteralNext) {
      this.handleLitteralNext = handleLitteralNext;
   }

   public boolean getHandleLitteralNext() {
      return this.handleLitteralNext;
   }

   public void setCommentBegin(String commentBegin) {
      this.commentBegin = commentBegin;
   }

   public String getCommentBegin() {
      String str = this.commentBegin;
      if (str == null) {
         str = this.consoleKeys.getVariable("comment-begin");
         if (str == null) {
            str = "#";
         }
      }

      return str;
   }

   public void setPrompt(String prompt) {
      this.prompt = prompt;
      this.promptLen = prompt == null ? 0 : this.wcwidth(Ansi.stripAnsi(lastLine(prompt)), 0);
   }

   public String getPrompt() {
      return this.prompt;
   }

   public void setEchoCharacter(Character c) {
      this.echoCharacter = c;
   }

   public Character getEchoCharacter() {
      return this.echoCharacter;
   }

   protected final boolean resetLine() throws IOException {
      if (this.buf.cursor == 0) {
         return false;
      } else {
         StringBuilder killed = new StringBuilder();

         while(this.buf.cursor > 0) {
            char c = this.buf.current();
            if (c == 0) {
               break;
            }

            killed.append(c);
            this.backspace();
         }

         String copy = killed.reverse().toString();
         this.killRing.addBackwards(copy);
         return true;
      }
   }

   int wcwidth(CharSequence str, int pos) {
      return this.wcwidth(str, 0, str.length(), pos);
   }

   int wcwidth(CharSequence str, int start, int end, int pos) {
      int cur = pos;

      int ucs;
      for(int i = start; i < end; cur += this.wcwidth(ucs, cur)) {
         char c1 = str.charAt(i++);
         if (Character.isHighSurrogate(c1) && i < end) {
            char c2 = str.charAt(i);
            if (Character.isLowSurrogate(c2)) {
               ++i;
               ucs = Character.toCodePoint(c1, c2);
            } else {
               ucs = c1;
            }
         } else {
            ucs = c1;
         }
      }

      return cur - pos;
   }

   int wcwidth(int ucs, int pos) {
      if (ucs == 9) {
         return this.nextTabStop(pos);
      } else if (ucs < 32) {
         return 2;
      } else {
         int w = WCWidth.wcwidth(ucs);
         return w > 0 ? w : 0;
      }
   }

   int nextTabStop(int pos) {
      int tabWidth = 8;
      int width = this.getTerminal().getWidth();
      int mod = (pos + tabWidth - 1) % tabWidth;
      int npos = pos + tabWidth - mod;
      return npos < width ? npos - pos : width - pos;
   }

   int getCursorPosition() {
      return this.promptLen + this.wcwidth(this.buf.buffer, 0, this.buf.cursor, this.promptLen);
   }

   private static String lastLine(String str) {
      if (str == null) {
         return "";
      } else {
         int last = str.lastIndexOf("\n");
         return last >= 0 ? str.substring(last + 1, str.length()) : str;
      }
   }

   public boolean setCursorPosition(int position) throws IOException {
      if (position == this.buf.cursor) {
         return true;
      } else {
         return this.moveCursor(position - this.buf.cursor) != 0;
      }
   }

   private void setBuffer(String buffer) throws IOException {
      if (!buffer.equals(this.buf.buffer.toString())) {
         int sameIndex = 0;
         int diff = 0;
         int l1 = buffer.length();

         for(int l2 = this.buf.buffer.length(); diff < l1 && diff < l2 && buffer.charAt(diff) == this.buf.buffer.charAt(diff); ++diff) {
            ++sameIndex;
         }

         diff = this.buf.cursor - sameIndex;
         if (diff < 0) {
            this.moveToEnd();
            diff = this.buf.buffer.length() - sameIndex;
         }

         this.backspace(diff);
         this.killLine();
         this.buf.buffer.setLength(sameIndex);
         this.putString(buffer.substring(sameIndex));
      }
   }

   private void setBuffer(CharSequence buffer) throws IOException {
      this.setBuffer(String.valueOf(buffer));
   }

   private void setBufferKeepPos(String buffer) throws IOException {
      int pos = this.buf.cursor;
      this.setBuffer(buffer);
      this.setCursorPosition(pos);
   }

   private void setBufferKeepPos(CharSequence buffer) throws IOException {
      this.setBufferKeepPos(String.valueOf(buffer));
   }

   public void drawLine() throws IOException {
      String prompt = this.getPrompt();
      if (prompt != null) {
         this.rawPrint(prompt);
      }

      this.fmtPrint(this.buf.buffer, 0, this.buf.cursor, this.promptLen);
      this.drawBuffer();
   }

   public void redrawLine() throws IOException {
      this.tputs("carriage_return");
      this.drawLine();
   }

   final String finishBuffer() throws IOException {
      String str = this.buf.buffer.toString();
      String historyLine = str;
      if (this.expandEvents) {
         try {
            str = this.expandEvents(str);
            historyLine = str.replace("!", "\\!");
            historyLine = historyLine.replaceAll("^\\^", "\\\\^");
         } catch (IllegalArgumentException var4) {
            Log.error("Could not expand event", var4);
            this.beep();
            this.buf.clear();
            str = "";
         }
      }

      if (str.length() > 0) {
         if (this.mask == null && this.isHistoryEnabled()) {
            this.history.add(historyLine);
         } else {
            this.mask = null;
         }
      }

      this.history.moveToEnd();
      this.buf.buffer.setLength(0);
      this.buf.cursor = 0;
      return str;
   }

   protected String expandEvents(String str) throws IOException {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < str.length(); ++i) {
         char c = str.charAt(i);
         String sc;
         switch (c) {
            case '!':
               if (i + 1 >= str.length()) {
                  sb.append(c);
               } else {
                  ++i;
                  c = str.charAt(i);
                  boolean neg = false;
                  String rep = null;
                  int i1;
                  int idx;
                  switch (c) {
                     case '\t':
                     case ' ':
                        sb.append('!');
                        sb.append(c);
                        break;
                     case '\n':
                     case '\u000b':
                     case '\f':
                     case '\r':
                     case '\u000e':
                     case '\u000f':
                     case '\u0010':
                     case '\u0011':
                     case '\u0012':
                     case '\u0013':
                     case '\u0014':
                     case '\u0015':
                     case '\u0016':
                     case '\u0017':
                     case '\u0018':
                     case '\u0019':
                     case '\u001a':
                     case '\u001b':
                     case '\u001c':
                     case '\u001d':
                     case '\u001e':
                     case '\u001f':
                     case '"':
                     case '%':
                     case '&':
                     case '\'':
                     case '(':
                     case ')':
                     case '*':
                     case '+':
                     case ',':
                     case '.':
                     case '/':
                     case ':':
                     case ';':
                     case '<':
                     case '=':
                     case '>':
                     default:
                        String ss = str.substring(i);
                        i = str.length();
                        idx = this.searchBackwards(ss, this.history.index(), true);
                        if (idx < 0) {
                           throw new IllegalArgumentException("!" + ss + ": event not found");
                        }

                        rep = this.history.get(idx).toString();
                        break;
                     case '!':
                        if (this.history.size() == 0) {
                           throw new IllegalArgumentException("!!: event not found");
                        }

                        rep = this.history.get(this.history.index() - 1).toString();
                        break;
                     case '#':
                        sb.append(sb.toString());
                        break;
                     case '$':
                        if (this.history.size() == 0) {
                           throw new IllegalArgumentException("!$: event not found");
                        }

                        String previous = this.history.get(this.history.index() - 1).toString().trim();
                        int lastSpace = previous.lastIndexOf(32);
                        if (lastSpace != -1) {
                           rep = previous.substring(lastSpace + 1);
                        } else {
                           rep = previous;
                        }
                        break;
                     case '-':
                        neg = true;
                        ++i;
                     case '0':
                     case '1':
                     case '2':
                     case '3':
                     case '4':
                     case '5':
                     case '6':
                     case '7':
                     case '8':
                     case '9':
                        for(i1 = i; i < str.length(); ++i) {
                           c = str.charAt(i);
                           if (c < '0' || c > '9') {
                              break;
                           }
                        }

                        int idx = false;

                        try {
                           idx = Integer.parseInt(str.substring(i1, i));
                        } catch (NumberFormatException var13) {
                           throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
                        }

                        if (neg) {
                           if (idx <= 0 || idx > this.history.size()) {
                              throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
                           }

                           rep = this.history.get(this.history.index() - idx).toString();
                        } else {
                           if (idx <= this.history.index() - this.history.size() || idx > this.history.index()) {
                              throw new IllegalArgumentException((neg ? "!-" : "!") + str.substring(i1, i) + ": event not found");
                           }

                           rep = this.history.get(idx - 1).toString();
                        }
                        break;
                     case '?':
                        i1 = str.indexOf(63, i + 1);
                        if (i1 < 0) {
                           i1 = str.length();
                        }

                        sc = str.substring(i + 1, i1);
                        i = i1;
                        idx = this.searchBackwards(sc);
                        if (idx < 0) {
                           throw new IllegalArgumentException("!?" + sc + ": event not found");
                        }

                        rep = this.history.get(idx).toString();
                  }

                  if (rep != null) {
                     sb.append(rep);
                  }
               }
               break;
            case '\\':
               if (i + 1 < str.length()) {
                  char nextChar = str.charAt(i + 1);
                  if (nextChar == '!' || nextChar == '^' && i == 0) {
                     c = nextChar;
                     ++i;
                  }
               }

               sb.append(c);
               break;
            case '^':
               if (i == 0) {
                  int i1 = str.indexOf(94, i + 1);
                  int i2 = str.indexOf(94, i1 + 1);
                  if (i2 < 0) {
                     i2 = str.length();
                  }

                  if (i1 > 0 && i2 > 0) {
                     String s1 = str.substring(i + 1, i1);
                     String s2 = str.substring(i1 + 1, i2);
                     sc = this.history.get(this.history.index() - 1).toString().replace(s1, s2);
                     sb.append(sc);
                     i = i2 + 1;
                     break;
                  }
               }

               sb.append(c);
               break;
            default:
               sb.append(c);
         }
      }

      String result = sb.toString();
      if (!str.equals(result)) {
         this.fmtPrint(result, this.getCursorPosition());
         this.println();
         this.flush();
      }

      return result;
   }

   public void putString(CharSequence str) throws IOException {
      int pos = this.getCursorPosition();
      this.buf.write(str);
      if (this.mask == null) {
         this.fmtPrint(str, pos);
      } else if (this.mask != 0) {
         this.rawPrint(this.mask, str.length());
      }

      this.drawBuffer();
   }

   private void drawBuffer(int clear) throws IOException {
      int nbChars = this.buf.length() - this.buf.cursor;
      if (this.buf.cursor != this.buf.length() || clear != 0) {
         if (this.mask != null) {
            if (this.mask != 0) {
               this.rawPrint(this.mask, nbChars);
            } else {
               nbChars = 0;
            }
         } else {
            this.fmtPrint(this.buf.buffer, this.buf.cursor, this.buf.length());
         }
      }

      int cursorPos = this.promptLen + this.wcwidth(this.buf.buffer, 0, this.buf.length(), this.promptLen);
      if (this.terminal.hasWeirdWrap() && !this.cursorOk) {
         int width = this.terminal.getWidth();
         if (cursorPos > 0 && cursorPos % width == 0) {
            this.rawPrint(32);
            this.tputs("carriage_return");
         }

         this.cursorOk = true;
      }

      this.clearAhead(clear, cursorPos);
      this.back(nbChars);
   }

   private void drawBuffer() throws IOException {
      this.drawBuffer(0);
   }

   private void clearAhead(int num, int pos) throws IOException {
      if (num != 0) {
         int width = this.terminal.getWidth();
         int cur;
         int c0;
         int nb;
         if (this.terminal.getStringCapability("clr_eol") != null) {
            cur = pos;
            c0 = pos % width;
            nb = Math.min(num, width - c0);
            this.tputs("clr_eol");

            for(num -= nb; num > 0; num -= nb) {
               int prev = cur;
               cur = cur - cur % width + width;
               this.moveCursorFromTo(prev, cur);
               nb = Math.min(num, width);
               this.tputs("clr_eol");
            }

            this.moveCursorFromTo(cur, pos);
         } else if (!this.terminal.getBooleanCapability("auto_right_margin")) {
            c0 = pos % width;
            nb = Math.min(num, width - c0);
            this.rawPrint(' ', nb);
            num -= nb;

            for(cur = pos + nb; num > 0; cur += nb) {
               this.moveCursorFromTo(cur++, cur);
               nb = Math.min(num, width);
               this.rawPrint(' ', nb);
               num -= nb;
            }

            this.moveCursorFromTo(cur, pos);
         } else {
            this.rawPrint(' ', num);
            this.moveCursorFromTo(pos + num, pos);
         }

      }
   }

   protected void back(int num) throws IOException {
      if (num != 0) {
         int i0 = this.promptLen + this.wcwidth(this.buf.buffer, 0, this.buf.cursor, this.promptLen);
         int i1 = i0 + (this.mask != null ? num : this.wcwidth(this.buf.buffer, this.buf.cursor, this.buf.cursor + num, i0));
         this.moveCursorFromTo(i1, i0);
      }
   }

   public void flush() throws IOException {
      this.out.flush();
   }

   private int backspaceAll() throws IOException {
      return this.backspace(Integer.MAX_VALUE);
   }

   private int backspace(int num) throws IOException {
      if (this.buf.cursor == 0) {
         return 0;
      } else {
         int count = -this.moveCursor(-num);
         int clear = this.wcwidth(this.buf.buffer, this.buf.cursor, this.buf.cursor + count, this.getCursorPosition());
         this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + count);
         this.drawBuffer(clear);
         return count;
      }
   }

   public boolean backspace() throws IOException {
      return this.backspace(1) == 1;
   }

   protected boolean moveToEnd() throws IOException {
      if (this.buf.cursor == this.buf.length()) {
         return true;
      } else {
         return this.moveCursor(this.buf.length() - this.buf.cursor) > 0;
      }
   }

   private boolean deleteCurrentCharacter() throws IOException {
      if (this.buf.length() != 0 && this.buf.cursor != this.buf.length()) {
         this.buf.buffer.deleteCharAt(this.buf.cursor);
         this.drawBuffer(1);
         return true;
      } else {
         return false;
      }
   }

   private Operation viDeleteChangeYankToRemap(Operation op) {
      switch (op) {
         case VI_EOF_MAYBE:
         case ABORT:
         case BACKWARD_CHAR:
         case FORWARD_CHAR:
         case END_OF_LINE:
         case VI_MATCH:
         case VI_BEGINNING_OF_LINE_OR_ARG_DIGIT:
         case VI_ARG_DIGIT:
         case VI_PREV_WORD:
         case VI_END_WORD:
         case VI_CHAR_SEARCH:
         case VI_NEXT_WORD:
         case VI_FIRST_PRINT:
         case VI_GOTO_MARK:
         case VI_COLUMN:
         case VI_DELETE_TO:
         case VI_YANK_TO:
         case VI_CHANGE_TO:
            return op;
         default:
            return Operation.VI_MOVEMENT_MODE;
      }
   }

   private boolean viRubout(int count) throws IOException {
      boolean ok = true;

      for(int i = 0; ok && i < count; ++i) {
         ok = this.backspace();
      }

      return ok;
   }

   private boolean viDelete(int count) throws IOException {
      boolean ok = true;

      for(int i = 0; ok && i < count; ++i) {
         ok = this.deleteCurrentCharacter();
      }

      return ok;
   }

   private boolean viChangeCase(int count) throws IOException {
      boolean ok = true;

      for(int i = 0; ok && i < count; ++i) {
         ok = this.buf.cursor < this.buf.buffer.length();
         if (ok) {
            char ch = this.buf.buffer.charAt(this.buf.cursor);
            if (Character.isUpperCase(ch)) {
               ch = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
               ch = Character.toUpperCase(ch);
            }

            this.buf.buffer.setCharAt(this.buf.cursor, ch);
            this.drawBuffer(1);
            this.moveCursor(1);
         }
      }

      return ok;
   }

   private boolean viChangeChar(int count, int c) throws IOException {
      if (c >= 0 && c != 27 && c != 3) {
         boolean ok = true;

         for(int i = 0; ok && i < count; ++i) {
            ok = this.buf.cursor < this.buf.buffer.length();
            if (ok) {
               this.buf.buffer.setCharAt(this.buf.cursor, (char)c);
               this.drawBuffer(1);
               if (i < count - 1) {
                  this.moveCursor(1);
               }
            }
         }

         return ok;
      } else {
         return true;
      }
   }

   private boolean viPreviousWord(int count) throws IOException {
      boolean ok = true;
      if (this.buf.cursor == 0) {
         return false;
      } else {
         int pos = this.buf.cursor - 1;

         for(int i = 0; pos > 0 && i < count; ++i) {
            while(pos > 0 && isWhitespace(this.buf.buffer.charAt(pos))) {
               --pos;
            }

            while(pos > 0 && !isDelimiter(this.buf.buffer.charAt(pos - 1))) {
               --pos;
            }

            if (pos > 0 && i < count - 1) {
               --pos;
            }
         }

         this.setCursorPosition(pos);
         return ok;
      }
   }

   private boolean viDeleteTo(int startPos, int endPos, boolean isChange) throws IOException {
      if (startPos == endPos) {
         return true;
      } else {
         if (endPos < startPos) {
            int tmp = endPos;
            endPos = startPos;
            startPos = tmp;
         }

         this.setCursorPosition(startPos);
         this.buf.cursor = startPos;
         this.buf.buffer.delete(startPos, endPos);
         this.drawBuffer(endPos - startPos);
         if (!isChange && startPos > 0 && startPos == this.buf.length()) {
            this.moveCursor(-1);
         }

         return true;
      }
   }

   private boolean viYankTo(int startPos, int endPos) throws IOException {
      int cursorPos = startPos;
      if (endPos < startPos) {
         int tmp = endPos;
         endPos = startPos;
         startPos = tmp;
      }

      if (startPos == endPos) {
         this.yankBuffer = "";
         return true;
      } else {
         this.yankBuffer = this.buf.buffer.substring(startPos, endPos);
         this.setCursorPosition(cursorPos);
         return true;
      }
   }

   private boolean viPut(int count) throws IOException {
      if (this.yankBuffer.length() == 0) {
         return true;
      } else {
         if (this.buf.cursor < this.buf.buffer.length()) {
            this.moveCursor(1);
         }

         for(int i = 0; i < count; ++i) {
            this.putString(this.yankBuffer);
         }

         this.moveCursor(-1);
         return true;
      }
   }

   private boolean viCharSearch(int count, int invokeChar, int ch) throws IOException {
      if (ch >= 0 && invokeChar >= 0) {
         char searchChar = (char)ch;
         if (invokeChar != 59 && invokeChar != 44) {
            this.charSearchChar = searchChar;
            this.charSearchFirstInvokeChar = (char)invokeChar;
         } else {
            if (this.charSearchChar == 0) {
               return false;
            }

            if (this.charSearchLastInvokeChar != ';' && this.charSearchLastInvokeChar != ',') {
               if (invokeChar == 44) {
                  this.charSearchFirstInvokeChar = switchCase(this.charSearchFirstInvokeChar);
               }
            } else if (this.charSearchLastInvokeChar != invokeChar) {
               this.charSearchFirstInvokeChar = switchCase(this.charSearchFirstInvokeChar);
            }

            searchChar = this.charSearchChar;
         }

         this.charSearchLastInvokeChar = (char)invokeChar;
         boolean isForward = Character.isLowerCase(this.charSearchFirstInvokeChar);
         boolean stopBefore = Character.toLowerCase(this.charSearchFirstInvokeChar) == 't';
         boolean ok = false;
         int pos;
         if (isForward) {
            while(true) {
               while(count-- > 0) {
                  for(pos = this.buf.cursor + 1; pos < this.buf.buffer.length(); ++pos) {
                     if (this.buf.buffer.charAt(pos) == searchChar) {
                        this.setCursorPosition(pos);
                        ok = true;
                        break;
                     }
                  }
               }

               if (ok) {
                  if (stopBefore) {
                     this.moveCursor(-1);
                  }

                  if (this.isInViMoveOperationState()) {
                     this.moveCursor(1);
                  }
               }
               break;
            }
         } else {
            while(true) {
               while(count-- > 0) {
                  for(pos = this.buf.cursor - 1; pos >= 0; --pos) {
                     if (this.buf.buffer.charAt(pos) == searchChar) {
                        this.setCursorPosition(pos);
                        ok = true;
                        break;
                     }
                  }
               }

               if (ok && stopBefore) {
                  this.moveCursor(1);
               }
               break;
            }
         }

         return ok;
      } else {
         return false;
      }
   }

   private static char switchCase(char ch) {
      return Character.isUpperCase(ch) ? Character.toLowerCase(ch) : Character.toUpperCase(ch);
   }

   private final boolean isInViMoveOperationState() {
      return this.state == ConsoleReader.State.VI_CHANGE_TO || this.state == ConsoleReader.State.VI_DELETE_TO || this.state == ConsoleReader.State.VI_YANK_TO;
   }

   private boolean viNextWord(int count) throws IOException {
      int pos = this.buf.cursor;
      int end = this.buf.buffer.length();

      for(int i = 0; pos < end && i < count; ++i) {
         while(pos < end && !isDelimiter(this.buf.buffer.charAt(pos))) {
            ++pos;
         }

         if (i < count - 1 || this.state != ConsoleReader.State.VI_CHANGE_TO) {
            while(pos < end && isDelimiter(this.buf.buffer.charAt(pos))) {
               ++pos;
            }
         }
      }

      this.setCursorPosition(pos);
      return true;
   }

   private boolean viEndWord(int count) throws IOException {
      int pos = this.buf.cursor;
      int end = this.buf.buffer.length();

      for(int i = 0; pos < end && i < count; ++i) {
         if (pos < end - 1 && !isDelimiter(this.buf.buffer.charAt(pos)) && isDelimiter(this.buf.buffer.charAt(pos + 1))) {
            ++pos;
         }

         while(pos < end && isDelimiter(this.buf.buffer.charAt(pos))) {
            ++pos;
         }

         while(pos < end - 1 && !isDelimiter(this.buf.buffer.charAt(pos + 1))) {
            ++pos;
         }
      }

      this.setCursorPosition(pos);
      return true;
   }

   private boolean previousWord() throws IOException {
      while(isDelimiter(this.buf.current()) && this.moveCursor(-1) != 0) {
      }

      while(!isDelimiter(this.buf.current()) && this.moveCursor(-1) != 0) {
      }

      return true;
   }

   private boolean nextWord() throws IOException {
      while(isDelimiter(this.buf.nextChar()) && this.moveCursor(1) != 0) {
      }

      while(!isDelimiter(this.buf.nextChar()) && this.moveCursor(1) != 0) {
      }

      return true;
   }

   private boolean unixWordRubout(int count) throws IOException {
      boolean success = true;

      StringBuilder killed;
      for(killed = new StringBuilder(); count > 0; --count) {
         if (this.buf.cursor == 0) {
            success = false;
            break;
         }

         char c;
         while(isWhitespace(this.buf.current())) {
            c = this.buf.current();
            if (c == 0) {
               break;
            }

            killed.append(c);
            this.backspace();
         }

         while(!isWhitespace(this.buf.current())) {
            c = this.buf.current();
            if (c == 0) {
               break;
            }

            killed.append(c);
            this.backspace();
         }
      }

      String copy = killed.reverse().toString();
      this.killRing.addBackwards(copy);
      return success;
   }

   private String insertComment(boolean isViMode) throws IOException {
      String comment = this.getCommentBegin();
      this.setCursorPosition(0);
      this.putString(comment);
      if (isViMode) {
         this.consoleKeys.setKeyMap("vi-insert");
      }

      return this.accept();
   }

   private int viSearch(char searchChar) throws IOException {
      boolean isForward = searchChar == '/';
      CursorBuffer origBuffer = this.buf.copy();
      this.setCursorPosition(0);
      this.killLine();
      this.putString(Character.toString(searchChar));
      this.flush();
      boolean isAborted = false;
      boolean isComplete = false;

      int ch;
      for(ch = -1; !isAborted && !isComplete && (ch = this.readCharacter()) != -1; this.flush()) {
         switch (ch) {
            case 8:
            case 127:
               this.backspace();
               if (this.buf.cursor == 0) {
                  isAborted = true;
               }
               break;
            case 10:
            case 13:
               isComplete = true;
               break;
            case 27:
               isAborted = true;
               break;
            default:
               this.putString(Character.toString((char)ch));
         }
      }

      if (ch != -1 && !isAborted) {
         String searchTerm = this.buf.buffer.substring(1);
         int idx = -1;
         int end = this.history.index();
         int start = end <= this.history.size() ? 0 : end - this.history.size();
         int i;
         if (isForward) {
            for(i = start; i < end; ++i) {
               if (this.history.get(i).toString().contains(searchTerm)) {
                  idx = i;
                  break;
               }
            }
         } else {
            for(i = end - 1; i >= start; --i) {
               if (this.history.get(i).toString().contains(searchTerm)) {
                  idx = i;
                  break;
               }
            }
         }

         if (idx == -1) {
            this.setCursorPosition(0);
            this.killLine();
            this.putString(origBuffer.buffer);
            this.setCursorPosition(0);
            return -1;
         } else {
            this.setCursorPosition(0);
            this.killLine();
            this.putString(this.history.get(idx));
            this.setCursorPosition(0);
            this.flush();

            for(isComplete = false; !isComplete && (ch = this.readCharacter()) != -1; this.flush()) {
               boolean forward = isForward;
               switch (ch) {
                  case 80:
                  case 112:
                     forward = !isForward;
                  case 78:
                  case 110:
                     boolean isMatch = false;
                     int i;
                     if (forward) {
                        for(i = idx + 1; !isMatch && i < end; ++i) {
                           if (this.history.get(i).toString().contains(searchTerm)) {
                              idx = i;
                              isMatch = true;
                           }
                        }
                     } else {
                        for(i = idx - 1; !isMatch && i >= start; --i) {
                           if (this.history.get(i).toString().contains(searchTerm)) {
                              idx = i;
                              isMatch = true;
                           }
                        }
                     }

                     if (isMatch) {
                        this.setCursorPosition(0);
                        this.killLine();
                        this.putString(this.history.get(idx));
                        this.setCursorPosition(0);
                     }
                     break;
                  default:
                     isComplete = true;
               }
            }

            return ch;
         }
      } else {
         this.setCursorPosition(0);
         this.killLine();
         this.putString(origBuffer.buffer);
         this.setCursorPosition(origBuffer.cursor);
         return -1;
      }
   }

   public void setParenBlinkTimeout(int timeout) {
      this.parenBlinkTimeout = timeout;
   }

   private void insertClose(String s) throws IOException {
      this.putString(s);
      int closePosition = this.buf.cursor;
      this.moveCursor(-1);
      this.viMatch();
      if (this.in.isNonBlockingEnabled()) {
         this.in.peek((long)this.parenBlinkTimeout);
      }

      this.setCursorPosition(closePosition);
      this.flush();
   }

   private boolean viMatch() throws IOException {
      int pos = this.buf.cursor;
      if (pos == this.buf.length()) {
         return false;
      } else {
         int type = getBracketType(this.buf.buffer.charAt(pos));
         int move = type < 0 ? -1 : 1;
         int count = 1;
         if (type == 0) {
            return false;
         } else {
            while(count > 0) {
               pos += move;
               if (pos < 0 || pos >= this.buf.buffer.length()) {
                  return false;
               }

               int curType = getBracketType(this.buf.buffer.charAt(pos));
               if (curType == type) {
                  ++count;
               } else if (curType == -type) {
                  --count;
               }
            }

            if (move > 0 && this.isInViMoveOperationState()) {
               ++pos;
            }

            this.setCursorPosition(pos);
            this.flush();
            return true;
         }
      }
   }

   private static int getBracketType(char ch) {
      switch (ch) {
         case '(':
            return 3;
         case ')':
            return -3;
         case '[':
            return 1;
         case ']':
            return -1;
         case '{':
            return 2;
         case '}':
            return -2;
         default:
            return 0;
      }
   }

   private boolean deletePreviousWord() throws IOException {
      StringBuilder killed = new StringBuilder();

      char c;
      while(isDelimiter(c = this.buf.current()) && c != 0) {
         killed.append(c);
         this.backspace();
      }

      while(!isDelimiter(c = this.buf.current()) && c != 0) {
         killed.append(c);
         this.backspace();
      }

      String copy = killed.reverse().toString();
      this.killRing.addBackwards(copy);
      return true;
   }

   private boolean deleteNextWord() throws IOException {
      StringBuilder killed = new StringBuilder();

      char c;
      while(isDelimiter(c = this.buf.nextChar()) && c != 0) {
         killed.append(c);
         this.delete();
      }

      while(!isDelimiter(c = this.buf.nextChar()) && c != 0) {
         killed.append(c);
         this.delete();
      }

      String copy = killed.toString();
      this.killRing.add(copy);
      return true;
   }

   private boolean capitalizeWord() throws IOException {
      boolean first = true;

      int i;
      char c;
      for(i = 1; this.buf.cursor + i - 1 < this.buf.length() && !isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)); ++i) {
         this.buf.buffer.setCharAt(this.buf.cursor + i - 1, first ? Character.toUpperCase(c) : Character.toLowerCase(c));
         first = false;
      }

      this.drawBuffer();
      this.moveCursor(i - 1);
      return true;
   }

   private boolean upCaseWord() throws IOException {
      int i;
      char c;
      for(i = 1; this.buf.cursor + i - 1 < this.buf.length() && !isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)); ++i) {
         this.buf.buffer.setCharAt(this.buf.cursor + i - 1, Character.toUpperCase(c));
      }

      this.drawBuffer();
      this.moveCursor(i - 1);
      return true;
   }

   private boolean downCaseWord() throws IOException {
      int i;
      char c;
      for(i = 1; this.buf.cursor + i - 1 < this.buf.length() && !isDelimiter(c = this.buf.buffer.charAt(this.buf.cursor + i - 1)); ++i) {
         this.buf.buffer.setCharAt(this.buf.cursor + i - 1, Character.toLowerCase(c));
      }

      this.drawBuffer();
      this.moveCursor(i - 1);
      return true;
   }

   private boolean transposeChars(int count) throws IOException {
      while(true) {
         if (count > 0) {
            if (this.buf.cursor != 0 && this.buf.cursor != this.buf.buffer.length()) {
               int first = this.buf.cursor - 1;
               int second = this.buf.cursor;
               char tmp = this.buf.buffer.charAt(first);
               this.buf.buffer.setCharAt(first, this.buf.buffer.charAt(second));
               this.buf.buffer.setCharAt(second, tmp);
               this.moveInternal(-1);
               this.drawBuffer();
               this.moveInternal(2);
               --count;
               continue;
            }

            return false;
         }

         return true;
      }
   }

   public boolean isKeyMap(String name) {
      KeyMap map = this.consoleKeys.getKeys();
      KeyMap mapByName = (KeyMap)this.consoleKeys.getKeyMaps().get(name);
      if (mapByName == null) {
         return false;
      } else {
         return map == mapByName;
      }
   }

   public String accept() throws IOException {
      this.moveToEnd();
      this.println();
      this.flush();
      return this.finishBuffer();
   }

   private void abort() throws IOException {
      this.beep();
      this.buf.clear();
      this.println();
      this.redrawLine();
   }

   public int moveCursor(int num) throws IOException {
      int where = num;
      if (this.buf.cursor == 0 && num <= 0) {
         return 0;
      } else if (this.buf.cursor == this.buf.buffer.length() && num >= 0) {
         return 0;
      } else {
         if (this.buf.cursor + num < 0) {
            where = -this.buf.cursor;
         } else if (this.buf.cursor + num > this.buf.buffer.length()) {
            where = this.buf.buffer.length() - this.buf.cursor;
         }

         this.moveInternal(where);
         return where;
      }
   }

   private void moveInternal(int where) throws IOException {
      CursorBuffer var10000 = this.buf;
      var10000.cursor += where;
      int i1;
      int i0;
      if (this.mask == null) {
         if (where < 0) {
            i1 = this.promptLen + this.wcwidth(this.buf.buffer, 0, this.buf.cursor, this.promptLen);
            i0 = i1 + this.wcwidth(this.buf.buffer, this.buf.cursor, this.buf.cursor - where, i1);
         } else {
            i0 = this.promptLen + this.wcwidth(this.buf.buffer, 0, this.buf.cursor - where, this.promptLen);
            i1 = i0 + this.wcwidth(this.buf.buffer, this.buf.cursor - where, this.buf.cursor, i0);
         }
      } else {
         if (this.mask == 0) {
            return;
         }

         i1 = this.promptLen + this.buf.cursor;
         i0 = i1 - where;
      }

      this.moveCursorFromTo(i0, i1);
   }

   private void moveCursorFromTo(int i0, int i1) throws IOException {
      if (i0 != i1) {
         int width = this.getTerminal().getWidth();
         int l0 = i0 / width;
         int c0 = i0 % width;
         int l1 = i1 / width;
         int c1 = i1 % width;
         int i;
         if (l0 == l1 + 1) {
            if (!this.tputs("cursor_up")) {
               this.tputs("parm_up_cursor", 1);
            }
         } else if (l0 > l1) {
            if (!this.tputs("parm_up_cursor", l0 - l1)) {
               for(i = l1; i < l0; ++i) {
                  this.tputs("cursor_up");
               }
            }
         } else if (l0 < l1) {
            this.tputs("carriage_return");
            this.rawPrint('\n', l1 - l0);
            c0 = 0;
         }

         if (c0 == c1 - 1) {
            this.tputs("cursor_right");
         } else if (c0 == c1 + 1) {
            this.tputs("cursor_left");
         } else if (c0 < c1) {
            if (!this.tputs("parm_right_cursor", c1 - c0)) {
               for(i = c0; i < c1; ++i) {
                  this.tputs("cursor_right");
               }
            }
         } else if (c0 > c1 && !this.tputs("parm_left_cursor", c0 - c1)) {
            for(i = c1; i < c0; ++i) {
               this.tputs("cursor_left");
            }
         }

         this.cursorOk = true;
      }
   }

   public int readCharacter() throws IOException {
      return this.readCharacter(false);
   }

   public int readCharacter(boolean checkForAltKeyCombo) throws IOException {
      int c = this.reader.read();
      if (c >= 0) {
         Log.trace("Keystroke: ", c);
         if (this.terminal.isSupported()) {
            this.clearEcho(c);
         }

         if (c == 27 && checkForAltKeyCombo && this.in.peek(this.escapeTimeout) >= 32) {
            int next = this.reader.read();
            next += 1000;
            return next;
         }
      }

      return c;
   }

   private int clearEcho(int c) throws IOException {
      if (!this.terminal.isEchoEnabled()) {
         return 0;
      } else {
         int pos = this.getCursorPosition();
         int num = this.wcwidth(c, pos);
         this.moveCursorFromTo(pos + num, pos);
         this.drawBuffer(num);
         return num;
      }
   }

   public int readCharacter(char... allowed) throws IOException {
      return this.readCharacter(false, allowed);
   }

   public int readCharacter(boolean checkForAltKeyCombo, char... allowed) throws IOException {
      Arrays.sort(allowed);

      char c;
      while(Arrays.binarySearch(allowed, c = (char)this.readCharacter(checkForAltKeyCombo)) < 0) {
      }

      return c;
   }

   public Object readBinding(KeyMap keys) throws IOException {
      this.opBuffer.setLength(0);

      Object o;
      do {
         int c = this.pushBackChar.isEmpty() ? this.readCharacter() : (Character)this.pushBackChar.pop();
         if (c == -1) {
            return null;
         }

         this.opBuffer.appendCodePoint(c);
         if (this.recording) {
            this.macro = this.macro + new String(Character.toChars(c));
         }

         if (this.quotedInsert) {
            o = Operation.SELF_INSERT;
            this.quotedInsert = false;
         } else {
            o = keys.getBound(this.opBuffer);
         }

         if (!this.recording && !(o instanceof KeyMap)) {
            if (o != Operation.YANK_POP && o != Operation.YANK) {
               this.killRing.resetLastYank();
            }

            if (o != Operation.KILL_LINE && o != Operation.KILL_WHOLE_LINE && o != Operation.BACKWARD_KILL_WORD && o != Operation.KILL_WORD && o != Operation.UNIX_LINE_DISCARD && o != Operation.UNIX_WORD_RUBOUT) {
               this.killRing.resetLastKill();
            }
         }

         if (o == Operation.DO_LOWERCASE_VERSION) {
            this.opBuffer.setLength(this.opBuffer.length() - 1);
            this.opBuffer.append(Character.toLowerCase((char)c));
            o = keys.getBound(this.opBuffer);
         }

         if (o instanceof KeyMap) {
            if (c != 27 || !this.pushBackChar.isEmpty() || !this.in.isNonBlockingEnabled() || this.in.peek(this.escapeTimeout) != -2) {
               continue;
            }

            o = ((KeyMap)o).getAnotherKey();
            if (o == null || o instanceof KeyMap) {
               continue;
            }

            this.opBuffer.setLength(0);
         }

         while(o == null && this.opBuffer.length() > 0) {
            int c = this.opBuffer.charAt(this.opBuffer.length() - 1);
            this.opBuffer.setLength(this.opBuffer.length() - 1);
            Object o2 = keys.getBound(this.opBuffer);
            if (o2 instanceof KeyMap) {
               o = ((KeyMap)o2).getAnotherKey();
               if (o != null) {
                  this.pushBackChar.push((char)c);
               }
            }
         }
      } while(o == null || o instanceof KeyMap);

      return o;
   }

   public String getLastBinding() {
      return this.opBuffer.toString();
   }

   public String readLine() throws IOException {
      return this.readLine((String)null);
   }

   public String readLine(Character mask) throws IOException {
      return this.readLine((String)null, mask);
   }

   public String readLine(String prompt) throws IOException {
      return this.readLine(prompt, (Character)null);
   }

   public String readLine(String prompt, Character mask) throws IOException {
      return this.readLine(prompt, mask, (String)null);
   }

   public boolean setKeyMap(String name) {
      return this.consoleKeys.setKeyMap(name);
   }

   public String getKeyMap() {
      return this.consoleKeys.getKeys().getName();
   }

   public String readLine(String prompt, Character mask, String buffer) throws IOException {
      int repeatCount = 0;
      this.mask = mask != null ? mask : this.echoCharacter;
      if (prompt != null) {
         this.setPrompt(prompt);
      } else {
         prompt = this.getPrompt();
      }

      try {
         if (buffer != null) {
            this.buf.write(buffer);
         }

         if (!this.terminal.isSupported()) {
            this.beforeReadLine(prompt, mask);
         }

         if (buffer != null && buffer.length() > 0 || prompt != null && prompt.length() > 0) {
            this.drawLine();
            this.out.flush();
         }

         String originalPrompt;
         if (!this.terminal.isSupported()) {
            originalPrompt = this.readLineSimple();
            return originalPrompt;
         } else {
            if (this.handleUserInterrupt) {
               this.terminal.disableInterruptCharacter();
            }

            if (this.handleLitteralNext && this.terminal instanceof UnixTerminal) {
               ((UnixTerminal)this.terminal).disableLitteralNextCharacter();
            }

            originalPrompt = this.prompt;
            this.state = ConsoleReader.State.NORMAL;
            boolean success = true;
            this.pushBackChar.clear();

            while(true) {
               Object o = this.readBinding(this.getKeys());
               if (o == null) {
                  Object var23 = null;
                  return (String)var23;
               }

               int c = 0;
               if (this.opBuffer.length() > 0) {
                  c = this.opBuffer.codePointBefore(this.opBuffer.length());
               }

               Log.trace("Binding: ", o);
               int cursorDest;
               if (o instanceof String) {
                  String macro = (String)o;

                  for(cursorDest = 0; cursorDest < macro.length(); ++cursorDest) {
                     this.pushBackChar.push(macro.charAt(macro.length() - 1 - cursorDest));
                  }

                  this.opBuffer.setLength(0);
               } else if (o instanceof ActionListener) {
                  ((ActionListener)o).actionPerformed((ActionEvent)null);
                  this.opBuffer.setLength(0);
               } else {
                  CursorBuffer oldBuf = new CursorBuffer();
                  oldBuf.buffer.append(this.buf.buffer);
                  oldBuf.cursor = this.buf.cursor;
                  if (this.state == ConsoleReader.State.SEARCH || this.state == ConsoleReader.State.FORWARD_SEARCH) {
                     cursorDest = -1;
                     switch ((Operation)o) {
                        case ABORT:
                           this.state = ConsoleReader.State.NORMAL;
                           this.buf.clear();
                           this.buf.write(this.originalBuffer.buffer);
                           this.buf.cursor = this.originalBuffer.cursor;
                           break;
                        case REVERSE_SEARCH_HISTORY:
                           this.state = ConsoleReader.State.SEARCH;
                           if (this.searchTerm.length() == 0) {
                              this.searchTerm.append(this.previousSearchTerm);
                           }

                           if (this.searchIndex > 0) {
                              this.searchIndex = this.searchBackwards(this.searchTerm.toString(), this.searchIndex);
                           }
                           break;
                        case FORWARD_SEARCH_HISTORY:
                           this.state = ConsoleReader.State.FORWARD_SEARCH;
                           if (this.searchTerm.length() == 0) {
                              this.searchTerm.append(this.previousSearchTerm);
                           }

                           if (this.searchIndex > -1 && this.searchIndex < this.history.size() - 1) {
                              this.searchIndex = this.searchForwards(this.searchTerm.toString(), this.searchIndex);
                           }
                           break;
                        case BACKWARD_DELETE_CHAR:
                           if (this.searchTerm.length() > 0) {
                              this.searchTerm.deleteCharAt(this.searchTerm.length() - 1);
                              if (this.state == ConsoleReader.State.SEARCH) {
                                 this.searchIndex = this.searchBackwards(this.searchTerm.toString());
                              } else {
                                 this.searchIndex = this.searchForwards(this.searchTerm.toString());
                              }
                           }
                           break;
                        case SELF_INSERT:
                           this.searchTerm.appendCodePoint(c);
                           if (this.state == ConsoleReader.State.SEARCH) {
                              this.searchIndex = this.searchBackwards(this.searchTerm.toString());
                           } else {
                              this.searchIndex = this.searchForwards(this.searchTerm.toString());
                           }
                           break;
                        default:
                           if (this.searchIndex != -1) {
                              this.history.moveTo(this.searchIndex);
                              cursorDest = this.history.current().toString().indexOf(this.searchTerm.toString());
                           }

                           if (o != Operation.ACCEPT_LINE) {
                              o = null;
                           }

                           this.state = ConsoleReader.State.NORMAL;
                     }

                     if (this.state != ConsoleReader.State.SEARCH && this.state != ConsoleReader.State.FORWARD_SEARCH) {
                        this.restoreLine(originalPrompt, cursorDest);
                     } else if (this.searchTerm.length() == 0) {
                        if (this.state == ConsoleReader.State.SEARCH) {
                           this.printSearchStatus("", "");
                        } else {
                           this.printForwardSearchStatus("", "");
                        }

                        this.searchIndex = -1;
                     } else if (this.searchIndex == -1) {
                        this.beep();
                        this.printSearchStatus(this.searchTerm.toString(), "");
                     } else if (this.state == ConsoleReader.State.SEARCH) {
                        this.printSearchStatus(this.searchTerm.toString(), this.history.get(this.searchIndex).toString());
                     } else {
                        this.printForwardSearchStatus(this.searchTerm.toString(), this.history.get(this.searchIndex).toString());
                     }
                  }

                  if (this.state != ConsoleReader.State.SEARCH && this.state != ConsoleReader.State.FORWARD_SEARCH) {
                     boolean isArgDigit = false;
                     int count = repeatCount == 0 ? 1 : repeatCount;
                     success = true;
                     if (o instanceof Operation) {
                        Operation op = (Operation)o;
                        int cursorStart = this.buf.cursor;
                        State origState = this.state;
                        if (this.state == ConsoleReader.State.VI_CHANGE_TO || this.state == ConsoleReader.State.VI_YANK_TO || this.state == ConsoleReader.State.VI_DELETE_TO) {
                           op = this.viDeleteChangeYankToRemap(op);
                        }

                        int lastChar;
                        String partialLine;
                        String var26;
                        switch (op) {
                           case VI_EOF_MAYBE:
                              if (this.buf.buffer.length() == 0) {
                                 var26 = null;
                                 return var26;
                              }

                              var26 = this.accept();
                              return var26;
                           case ABORT:
                              if (this.searchTerm == null) {
                                 this.abort();
                              }
                              break;
                           case BACKWARD_CHAR:
                              success = this.moveCursor(-count) != 0;
                              break;
                           case FORWARD_CHAR:
                              success = this.moveCursor(count) != 0;
                              break;
                           case END_OF_LINE:
                              success = this.moveToEnd();
                              break;
                           case VI_MATCH:
                              success = this.viMatch();
                              break;
                           case VI_BEGINNING_OF_LINE_OR_ARG_DIGIT:
                              if (repeatCount > 0) {
                                 repeatCount = repeatCount * 10 + this.opBuffer.charAt(0) - 48;
                                 isArgDigit = true;
                              } else {
                                 success = this.setCursorPosition(0);
                              }
                              break;
                           case VI_ARG_DIGIT:
                              repeatCount = repeatCount * 10 + this.opBuffer.charAt(0) - 48;
                              isArgDigit = true;
                              break;
                           case VI_PREV_WORD:
                              success = this.viPreviousWord(count);
                              break;
                           case VI_END_WORD:
                              success = this.viEndWord(count);
                              break;
                           case VI_CHAR_SEARCH:
                              int searchChar = c != 59 && c != 44 ? (this.pushBackChar.isEmpty() ? this.readCharacter() : (Character)this.pushBackChar.pop()) : 0;
                              success = this.viCharSearch(count, c, searchChar);
                              break;
                           case VI_NEXT_WORD:
                              success = this.viNextWord(count);
                              break;
                           case VI_FIRST_PRINT:
                              success = this.setCursorPosition(0) && this.viNextWord(1);
                           case VI_GOTO_MARK:
                           case VI_COLUMN:
                           default:
                              break;
                           case VI_DELETE_TO:
                              if (this.state == ConsoleReader.State.VI_DELETE_TO) {
                                 success = this.setCursorPosition(0) && this.killLine();
                                 this.state = origState = ConsoleReader.State.NORMAL;
                              } else {
                                 this.state = ConsoleReader.State.VI_DELETE_TO;
                              }
                              break;
                           case VI_YANK_TO:
                              if (this.state == ConsoleReader.State.VI_YANK_TO) {
                                 this.yankBuffer = this.buf.buffer.toString();
                                 this.state = origState = ConsoleReader.State.NORMAL;
                              } else {
                                 this.state = ConsoleReader.State.VI_YANK_TO;
                              }
                              break;
                           case VI_CHANGE_TO:
                              if (this.state == ConsoleReader.State.VI_CHANGE_TO) {
                                 success = this.setCursorPosition(0) && this.killLine();
                                 this.state = origState = ConsoleReader.State.NORMAL;
                                 this.consoleKeys.setKeyMap("vi-insert");
                              } else {
                                 this.state = ConsoleReader.State.VI_CHANGE_TO;
                              }
                              break;
                           case REVERSE_SEARCH_HISTORY:
                              this.originalBuffer = new CursorBuffer();
                              this.originalBuffer.write(this.buf.buffer);
                              this.originalBuffer.cursor = this.buf.cursor;
                              if (this.searchTerm != null) {
                                 this.previousSearchTerm = this.searchTerm.toString();
                              }

                              this.searchTerm = new StringBuffer(this.buf.buffer);
                              this.state = ConsoleReader.State.SEARCH;
                              if (this.searchTerm.length() > 0) {
                                 this.searchIndex = this.searchBackwards(this.searchTerm.toString());
                                 if (this.searchIndex == -1) {
                                    this.beep();
                                 }

                                 this.printSearchStatus(this.searchTerm.toString(), this.searchIndex > -1 ? this.history.get(this.searchIndex).toString() : "");
                              } else {
                                 this.searchIndex = -1;
                                 this.printSearchStatus("", "");
                              }
                              break;
                           case FORWARD_SEARCH_HISTORY:
                              this.originalBuffer = new CursorBuffer();
                              this.originalBuffer.write(this.buf.buffer);
                              this.originalBuffer.cursor = this.buf.cursor;
                              if (this.searchTerm != null) {
                                 this.previousSearchTerm = this.searchTerm.toString();
                              }

                              this.searchTerm = new StringBuffer(this.buf.buffer);
                              this.state = ConsoleReader.State.FORWARD_SEARCH;
                              if (this.searchTerm.length() > 0) {
                                 this.searchIndex = this.searchForwards(this.searchTerm.toString());
                                 if (this.searchIndex == -1) {
                                    this.beep();
                                 }

                                 this.printForwardSearchStatus(this.searchTerm.toString(), this.searchIndex > -1 ? this.history.get(this.searchIndex).toString() : "");
                              } else {
                                 this.searchIndex = -1;
                                 this.printForwardSearchStatus("", "");
                              }
                              break;
                           case BACKWARD_DELETE_CHAR:
                              success = this.backspace();
                              break;
                           case SELF_INSERT:
                              this.putString(this.opBuffer);
                              break;
                           case COMPLETE:
                              boolean isTabLiteral = false;
                              if (this.copyPasteDetection && c == 9 && (!this.pushBackChar.isEmpty() || this.in.isNonBlockingEnabled() && this.in.peek(this.escapeTimeout) != -2)) {
                                 isTabLiteral = true;
                              }

                              if (!isTabLiteral) {
                                 success = this.complete();
                              } else {
                                 this.putString(this.opBuffer);
                              }
                              break;
                           case POSSIBLE_COMPLETIONS:
                              this.printCompletionCandidates();
                              break;
                           case BEGINNING_OF_LINE:
                              success = this.setCursorPosition(0);
                              break;
                           case YANK:
                              success = this.yank();
                              break;
                           case YANK_POP:
                              success = this.yankPop();
                              break;
                           case KILL_LINE:
                              success = this.killLine();
                              break;
                           case KILL_WHOLE_LINE:
                              success = this.setCursorPosition(0) && this.killLine();
                              break;
                           case CLEAR_SCREEN:
                              success = this.clearScreen();
                              this.redrawLine();
                              break;
                           case OVERWRITE_MODE:
                              this.buf.setOverTyping(!this.buf.isOverTyping());
                              break;
                           case ACCEPT_LINE:
                              partialLine = this.accept();
                              return partialLine;
                           case INTERRUPT:
                              if (this.handleUserInterrupt) {
                                 this.println();
                                 this.flush();
                                 partialLine = this.buf.buffer.toString();
                                 this.buf.clear();
                                 this.history.moveToEnd();
                                 throw new UserInterruptException(partialLine);
                              }
                              break;
                           case VI_MOVE_ACCEPT_LINE:
                              this.consoleKeys.setKeyMap("vi-insert");
                              partialLine = this.accept();
                              return partialLine;
                           case BACKWARD_WORD:
                              success = this.previousWord();
                              break;
                           case FORWARD_WORD:
                              success = this.nextWord();
                              break;
                           case PREVIOUS_HISTORY:
                              success = this.moveHistory(false);
                              break;
                           case VI_PREVIOUS_HISTORY:
                              success = this.moveHistory(false, count) && this.setCursorPosition(0);
                              break;
                           case NEXT_HISTORY:
                              success = this.moveHistory(true);
                              break;
                           case VI_NEXT_HISTORY:
                              success = this.moveHistory(true, count) && this.setCursorPosition(0);
                              break;
                           case EXIT_OR_DELETE_CHAR:
                              if (this.buf.buffer.length() == 0) {
                                 partialLine = null;
                                 return partialLine;
                              }

                              success = this.deleteCurrentCharacter();
                              break;
                           case DELETE_CHAR:
                              success = this.deleteCurrentCharacter();
                              break;
                           case UNIX_LINE_DISCARD:
                              success = this.resetLine();
                              break;
                           case UNIX_WORD_RUBOUT:
                              success = this.unixWordRubout(count);
                              break;
                           case BACKWARD_KILL_WORD:
                              success = this.deletePreviousWord();
                              break;
                           case KILL_WORD:
                              success = this.deleteNextWord();
                              break;
                           case BEGINNING_OF_HISTORY:
                              success = this.history.moveToFirst();
                              if (success) {
                                 this.setBuffer(this.history.current());
                              }
                              break;
                           case END_OF_HISTORY:
                              success = this.history.moveToLast();
                              if (success) {
                                 this.setBuffer(this.history.current());
                              }
                              break;
                           case HISTORY_SEARCH_BACKWARD:
                              this.searchTerm = new StringBuffer(this.buf.upToCursor());
                              this.searchIndex = this.searchBackwards(this.searchTerm.toString(), this.history.index(), true);
                              if (this.searchIndex == -1) {
                                 this.beep();
                              } else {
                                 success = this.history.moveTo(this.searchIndex);
                                 if (success) {
                                    this.setBufferKeepPos(this.history.current());
                                 }
                              }
                              break;
                           case HISTORY_SEARCH_FORWARD:
                              this.searchTerm = new StringBuffer(this.buf.upToCursor());
                              int index = this.history.index() + 1;
                              if (index == this.history.size()) {
                                 this.history.moveToEnd();
                                 this.setBufferKeepPos(this.searchTerm.toString());
                              } else if (index < this.history.size()) {
                                 this.searchIndex = this.searchForwards(this.searchTerm.toString(), index, true);
                                 if (this.searchIndex == -1) {
                                    this.beep();
                                 } else {
                                    success = this.history.moveTo(this.searchIndex);
                                    if (success) {
                                       this.setBufferKeepPos(this.history.current());
                                    }
                                 }
                              }
                              break;
                           case CAPITALIZE_WORD:
                              success = this.capitalizeWord();
                              break;
                           case UPCASE_WORD:
                              success = this.upCaseWord();
                              break;
                           case DOWNCASE_WORD:
                              success = this.downCaseWord();
                              break;
                           case TAB_INSERT:
                              this.putString("\t");
                              break;
                           case RE_READ_INIT_FILE:
                              this.consoleKeys.loadKeys(this.appName, this.inputrcUrl);
                              break;
                           case START_KBD_MACRO:
                              this.recording = true;
                              break;
                           case END_KBD_MACRO:
                              this.recording = false;
                              this.macro = this.macro.substring(0, this.macro.length() - this.opBuffer.length());
                              break;
                           case CALL_LAST_KBD_MACRO:
                              for(lastChar = 0; lastChar < this.macro.length(); ++lastChar) {
                                 this.pushBackChar.push(this.macro.charAt(this.macro.length() - 1 - lastChar));
                              }

                              this.opBuffer.setLength(0);
                              break;
                           case VI_EDITING_MODE:
                              this.consoleKeys.setKeyMap("vi-insert");
                              break;
                           case VI_MOVEMENT_MODE:
                              if (this.state == ConsoleReader.State.NORMAL) {
                                 this.moveCursor(-1);
                              }

                              this.consoleKeys.setKeyMap("vi-move");
                              break;
                           case VI_INSERTION_MODE:
                              this.consoleKeys.setKeyMap("vi-insert");
                              break;
                           case VI_APPEND_MODE:
                              this.moveCursor(1);
                              this.consoleKeys.setKeyMap("vi-insert");
                              break;
                           case VI_APPEND_EOL:
                              success = this.moveToEnd();
                              this.consoleKeys.setKeyMap("vi-insert");
                              break;
                           case TRANSPOSE_CHARS:
                              success = this.transposeChars(count);
                              break;
                           case INSERT_COMMENT:
                              var26 = this.insertComment(false);
                              return var26;
                           case INSERT_CLOSE_CURLY:
                              this.insertClose("}");
                              break;
                           case INSERT_CLOSE_PAREN:
                              this.insertClose(")");
                              break;
                           case INSERT_CLOSE_SQUARE:
                              this.insertClose("]");
                              break;
                           case VI_INSERT_COMMENT:
                              var26 = this.insertComment(true);
                              return var26;
                           case VI_SEARCH:
                              lastChar = this.viSearch(this.opBuffer.charAt(0));
                              if (lastChar != -1) {
                                 this.pushBackChar.push((char)lastChar);
                              }
                              break;
                           case VI_INSERT_BEG:
                              success = this.setCursorPosition(0);
                              this.consoleKeys.setKeyMap("vi-insert");
                              break;
                           case VI_RUBOUT:
                              success = this.viRubout(count);
                              break;
                           case VI_DELETE:
                              success = this.viDelete(count);
                              break;
                           case VI_KILL_WHOLE_LINE:
                              success = this.setCursorPosition(0) && this.killLine();
                              this.consoleKeys.setKeyMap("vi-insert");
                              break;
                           case VI_PUT:
                              success = this.viPut(count);
                              break;
                           case VI_CHANGE_CASE:
                              success = this.viChangeCase(count);
                              break;
                           case VI_CHANGE_CHAR:
                              success = this.viChangeChar(count, this.pushBackChar.isEmpty() ? this.readCharacter() : (Character)this.pushBackChar.pop());
                              break;
                           case VI_DELETE_TO_EOL:
                              success = this.viDeleteTo(this.buf.cursor, this.buf.buffer.length(), false);
                              break;
                           case VI_CHANGE_TO_EOL:
                              success = this.viDeleteTo(this.buf.cursor, this.buf.buffer.length(), true);
                              this.consoleKeys.setKeyMap("vi-insert");
                              break;
                           case EMACS_EDITING_MODE:
                              this.consoleKeys.setKeyMap("emacs");
                              break;
                           case QUIT:
                              this.getCursorBuffer().clear();
                              String var18 = this.accept();
                              return var18;
                           case QUOTED_INSERT:
                              this.quotedInsert = true;
                              break;
                           case PASTE_FROM_CLIPBOARD:
                              this.paste();
                        }

                        if (origState != ConsoleReader.State.NORMAL) {
                           if (origState == ConsoleReader.State.VI_DELETE_TO) {
                              success = this.viDeleteTo(cursorStart, this.buf.cursor, false);
                           } else if (origState == ConsoleReader.State.VI_CHANGE_TO) {
                              success = this.viDeleteTo(cursorStart, this.buf.cursor, true);
                              this.consoleKeys.setKeyMap("vi-insert");
                           } else if (origState == ConsoleReader.State.VI_YANK_TO) {
                              success = this.viYankTo(cursorStart, this.buf.cursor);
                           }

                           this.state = ConsoleReader.State.NORMAL;
                        }

                        if (this.state == ConsoleReader.State.NORMAL && !isArgDigit) {
                           repeatCount = 0;
                        }

                        if (this.state != ConsoleReader.State.SEARCH && this.state != ConsoleReader.State.FORWARD_SEARCH) {
                           this.originalBuffer = null;
                           this.previousSearchTerm = "";
                           this.searchTerm = null;
                           this.searchIndex = -1;
                        }
                     }
                  }

                  if (!success) {
                     this.beep();
                  }

                  this.opBuffer.setLength(0);
                  this.flush();
               }
            }
         }
      } finally {
         if (!this.terminal.isSupported()) {
            this.afterReadLine();
         }

         if (this.handleUserInterrupt) {
            this.terminal.enableInterruptCharacter();
         }

      }
   }

   private String readLineSimple() throws IOException {
      StringBuilder buff = new StringBuilder();
      int i;
      if (this.skipLF) {
         this.skipLF = false;
         i = this.readCharacter();
         if (i == -1 || i == 13) {
            return buff.toString();
         }

         if (i != 10) {
            buff.append((char)i);
         }
      }

      while(true) {
         i = this.readCharacter();
         if (i == -1 && buff.length() == 0) {
            return null;
         }

         if (i == -1 || i == 10) {
            return buff.toString();
         }

         if (i == 13) {
            this.skipLF = true;
            return buff.toString();
         }

         buff.append((char)i);
      }
   }

   public boolean addCompleter(Completer completer) {
      return this.completers.add(completer);
   }

   public boolean removeCompleter(Completer completer) {
      return this.completers.remove(completer);
   }

   public Collection getCompleters() {
      return Collections.unmodifiableList(this.completers);
   }

   public void setCompletionHandler(CompletionHandler handler) {
      this.completionHandler = (CompletionHandler)Preconditions.checkNotNull(handler);
   }

   public CompletionHandler getCompletionHandler() {
      return this.completionHandler;
   }

   protected boolean complete() throws IOException {
      if (this.completers.size() == 0) {
         return false;
      } else {
         List candidates = new LinkedList();
         String bufstr = this.buf.buffer.toString();
         int cursor = this.buf.cursor;
         int position = -1;
         Iterator var5 = this.completers.iterator();

         while(var5.hasNext()) {
            Completer comp = (Completer)var5.next();
            if ((position = comp.complete(bufstr, cursor, candidates)) != -1) {
               break;
            }
         }

         return candidates.size() != 0 && this.getCompletionHandler().complete(this, candidates, position);
      }
   }

   protected void printCompletionCandidates() throws IOException {
      if (this.completers.size() != 0) {
         List candidates = new LinkedList();
         String bufstr = this.buf.buffer.toString();
         int cursor = this.buf.cursor;
         Iterator var4 = this.completers.iterator();

         while(var4.hasNext()) {
            Completer comp = (Completer)var4.next();
            if (comp.complete(bufstr, cursor, candidates) != -1) {
               break;
            }
         }

         CandidateListCompletionHandler.printCandidates(this, candidates);
         this.drawLine();
      }
   }

   public void setAutoprintThreshold(int threshold) {
      this.autoprintThreshold = threshold;
   }

   public int getAutoprintThreshold() {
      return this.autoprintThreshold;
   }

   public void setPaginationEnabled(boolean enabled) {
      this.paginationEnabled = enabled;
   }

   public boolean isPaginationEnabled() {
      return this.paginationEnabled;
   }

   public void setHistory(History history) {
      this.history = history;
   }

   public History getHistory() {
      return this.history;
   }

   public void setHistoryEnabled(boolean enabled) {
      this.historyEnabled = enabled;
   }

   public boolean isHistoryEnabled() {
      return this.historyEnabled;
   }

   private boolean moveHistory(boolean next, int count) throws IOException {
      boolean ok = true;

      for(int i = 0; i < count && (ok = this.moveHistory(next)); ++i) {
      }

      return ok;
   }

   private boolean moveHistory(boolean next) throws IOException {
      if (next && !this.history.next()) {
         return false;
      } else if (!next && !this.history.previous()) {
         return false;
      } else {
         this.setBuffer(this.history.current());
         return true;
      }
   }

   private int fmtPrint(CharSequence buff, int cursorPos) throws IOException {
      return this.fmtPrint(buff, 0, buff.length(), cursorPos);
   }

   private int fmtPrint(CharSequence buff, int start, int end) throws IOException {
      return this.fmtPrint(buff, start, end, this.getCursorPosition());
   }

   private int fmtPrint(CharSequence buff, int start, int end, int cursorPos) throws IOException {
      Preconditions.checkNotNull(buff);

      for(int i = start; i < end; ++i) {
         char c = buff.charAt(i);
         int w;
         if (c == '\t') {
            w = this.nextTabStop(cursorPos);
            cursorPos += w;

            while(w-- > 0) {
               this.out.write(32);
            }
         } else if (c < ' ') {
            this.out.write(94);
            this.out.write((char)(c + 64));
            cursorPos += 2;
         } else {
            w = WCWidth.wcwidth(c);
            if (w > 0) {
               this.out.write(c);
               cursorPos += w;
            }
         }
      }

      this.cursorOk = false;
      return cursorPos;
   }

   public void print(CharSequence s) throws IOException {
      this.rawPrint(s.toString());
   }

   public void println(CharSequence s) throws IOException {
      this.print(s);
      this.println();
   }

   public void println() throws IOException {
      this.tputs("carriage_return");
      this.rawPrint(10);
   }

   final void rawPrint(int c) throws IOException {
      this.out.write(c);
      this.cursorOk = false;
   }

   final void rawPrint(String str) throws IOException {
      this.out.write(str);
      this.cursorOk = false;
   }

   private void rawPrint(char c, int num) throws IOException {
      for(int i = 0; i < num; ++i) {
         this.rawPrint(c);
      }

   }

   private void rawPrintln(String s) throws IOException {
      this.rawPrint(s);
      this.println();
   }

   public boolean delete() throws IOException {
      if (this.buf.cursor == this.buf.buffer.length()) {
         return false;
      } else {
         this.buf.buffer.delete(this.buf.cursor, this.buf.cursor + 1);
         this.drawBuffer(1);
         return true;
      }
   }

   public boolean killLine() throws IOException {
      int cp = this.buf.cursor;
      int len = this.buf.buffer.length();
      if (cp >= len) {
         return false;
      } else {
         int num = len - cp;
         int pos = this.getCursorPosition();
         int width = this.wcwidth(this.buf.buffer, cp, len, pos);
         this.clearAhead(width, pos);
         char[] killed = new char[num];
         this.buf.buffer.getChars(cp, cp + num, killed, 0);
         this.buf.buffer.delete(cp, cp + num);
         String copy = new String(killed);
         this.killRing.add(copy);
         return true;
      }
   }

   public boolean yank() throws IOException {
      String yanked = this.killRing.yank();
      if (yanked == null) {
         return false;
      } else {
         this.putString(yanked);
         return true;
      }
   }

   public boolean yankPop() throws IOException {
      if (!this.killRing.lastYank()) {
         return false;
      } else {
         String current = this.killRing.yank();
         if (current == null) {
            return false;
         } else {
            this.backspace(current.length());
            String yanked = this.killRing.yankPop();
            if (yanked == null) {
               return false;
            } else {
               this.putString(yanked);
               return true;
            }
         }
      }
   }

   public boolean clearScreen() throws IOException {
      if (!this.tputs("clear_screen")) {
         this.println();
      }

      return true;
   }

   public void beep() throws IOException {
      if (this.bellEnabled && this.tputs("bell")) {
         this.flush();
      }

   }

   public boolean paste() throws IOException {
      Clipboard clipboard;
      try {
         clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      } catch (Exception var8) {
         return false;
      }

      if (clipboard == null) {
         return false;
      } else {
         Transferable transferable = clipboard.getContents((Object)null);
         if (transferable == null) {
            return false;
         } else {
            try {
               Object content = transferable.getTransferData(DataFlavor.plainTextFlavor);
               if (content == null) {
                  try {
                     content = (new DataFlavor()).getReaderForText(transferable);
                  } catch (Exception var7) {
                  }
               }

               if (content == null) {
                  return false;
               } else {
                  String value;
                  if (content instanceof Reader) {
                     value = "";

                     String line;
                     for(BufferedReader read = new BufferedReader((Reader)content); (line = read.readLine()) != null; value = value + line) {
                        if (value.length() > 0) {
                           value = value + "\n";
                        }
                     }
                  } else {
                     value = content.toString();
                  }

                  if (value == null) {
                     return true;
                  } else {
                     this.putString(value);
                     return true;
                  }
               }
            } catch (UnsupportedFlavorException var9) {
               Log.error("Paste failed: ", var9);
               return false;
            }
         }
      }
   }

   public void addTriggeredAction(char c, ActionListener listener) {
      this.getKeys().bind(Character.toString(c), listener);
   }

   public void printColumns(Collection items) throws IOException {
      if (items != null && !items.isEmpty()) {
         int width = this.getTerminal().getWidth();
         int height = this.getTerminal().getHeight();
         int maxWidth = 0;

         int realLength;
         for(Iterator var5 = items.iterator(); var5.hasNext(); maxWidth = Math.max(maxWidth, realLength)) {
            CharSequence item = (CharSequence)var5.next();
            realLength = this.wcwidth(Ansi.stripAnsi(item.toString()), 0);
         }

         maxWidth += 3;
         Log.debug("Max width: ", maxWidth);
         int showLines;
         if (this.isPaginationEnabled()) {
            showLines = height - 1;
         } else {
            showLines = Integer.MAX_VALUE;
         }

         StringBuilder buff = new StringBuilder();
         realLength = 0;

         for(Iterator var8 = items.iterator(); var8.hasNext(); realLength += maxWidth) {
            CharSequence item = (CharSequence)var8.next();
            int c;
            if (realLength + maxWidth > width) {
               this.rawPrintln(buff.toString());
               buff.setLength(0);
               realLength = 0;
               --showLines;
               if (showLines == 0) {
                  this.print(resources.getString("DISPLAY_MORE"));
                  this.flush();
                  c = this.readCharacter();
                  if (c != 13 && c != 10) {
                     if (c != 113) {
                        showLines = height - 1;
                     }
                  } else {
                     showLines = 1;
                  }

                  this.tputs("carriage_return");
                  if (c == 113) {
                     break;
                  }
               }
            }

            buff.append(item.toString());
            c = this.wcwidth(Ansi.stripAnsi(item.toString()), 0);

            for(int i = 0; i < maxWidth - c; ++i) {
               buff.append(' ');
            }
         }

         if (buff.length() > 0) {
            this.rawPrintln(buff.toString());
         }

      }
   }

   private void beforeReadLine(String prompt, Character mask) {
      if (mask != null && this.maskThread == null) {
         final String fullPrompt = "\r" + prompt + "                                                   \r" + prompt;
         this.maskThread = new Thread() {
            public void run() {
               while(!interrupted()) {
                  try {
                     Writer out = ConsoleReader.this.getOutput();
                     out.write(fullPrompt);
                     out.flush();
                     sleep(3L);
                  } catch (IOException var2) {
                     return;
                  } catch (InterruptedException var3) {
                     return;
                  }
               }

            }
         };
         this.maskThread.setPriority(10);
         this.maskThread.setDaemon(true);
         this.maskThread.start();
      }

   }

   private void afterReadLine() {
      if (this.maskThread != null && this.maskThread.isAlive()) {
         this.maskThread.interrupt();
      }

      this.maskThread = null;
   }

   public void resetPromptLine(String prompt, String buffer, int cursorDest) throws IOException {
      this.moveToEnd();
      this.buf.buffer.append(this.prompt);
      int promptLength = 0;
      if (this.prompt != null) {
         promptLength = this.prompt.length();
      }

      CursorBuffer var10000 = this.buf;
      var10000.cursor += promptLength;
      this.setPrompt("");
      this.backspaceAll();
      this.setPrompt(prompt);
      this.redrawLine();
      this.setBuffer(buffer);
      if (cursorDest < 0) {
         cursorDest = buffer.length();
      }

      this.setCursorPosition(cursorDest);
      this.flush();
   }

   public void printSearchStatus(String searchTerm, String match) throws IOException {
      this.printSearchStatus(searchTerm, match, "(reverse-i-search)`");
   }

   public void printForwardSearchStatus(String searchTerm, String match) throws IOException {
      this.printSearchStatus(searchTerm, match, "(i-search)`");
   }

   private void printSearchStatus(String searchTerm, String match, String searchLabel) throws IOException {
      String prompt = searchLabel + searchTerm + "': ";
      int cursorDest = match.indexOf(searchTerm);
      this.resetPromptLine(prompt, match, cursorDest);
   }

   public void restoreLine(String originalPrompt, int cursorDest) throws IOException {
      String prompt = lastLine(originalPrompt);
      String buffer = this.buf.buffer.toString();
      this.resetPromptLine(prompt, buffer, cursorDest);
   }

   public int searchBackwards(String searchTerm, int startIndex) {
      return this.searchBackwards(searchTerm, startIndex, false);
   }

   public int searchBackwards(String searchTerm) {
      return this.searchBackwards(searchTerm, this.history.index());
   }

   public int searchBackwards(String searchTerm, int startIndex, boolean startsWith) {
      ListIterator it = this.history.entries(startIndex);

      while(it.hasPrevious()) {
         History.Entry e = (History.Entry)it.previous();
         if (startsWith) {
            if (e.value().toString().startsWith(searchTerm)) {
               return e.index();
            }
         } else if (e.value().toString().contains(searchTerm)) {
            return e.index();
         }
      }

      return -1;
   }

   public int searchForwards(String searchTerm, int startIndex) {
      return this.searchForwards(searchTerm, startIndex, false);
   }

   public int searchForwards(String searchTerm) {
      return this.searchForwards(searchTerm, this.history.index());
   }

   public int searchForwards(String searchTerm, int startIndex, boolean startsWith) {
      if (startIndex >= this.history.size()) {
         startIndex = this.history.size() - 1;
      }

      ListIterator it = this.history.entries(startIndex);
      if (this.searchIndex != -1 && it.hasNext()) {
         it.next();
      }

      while(it.hasNext()) {
         History.Entry e = (History.Entry)it.next();
         if (startsWith) {
            if (e.value().toString().startsWith(searchTerm)) {
               return e.index();
            }
         } else if (e.value().toString().contains(searchTerm)) {
            return e.index();
         }
      }

      return -1;
   }

   private static boolean isDelimiter(char c) {
      return !Character.isLetterOrDigit(c);
   }

   private static boolean isWhitespace(char c) {
      return Character.isWhitespace(c);
   }

   private boolean tputs(String cap, Object... params) throws IOException {
      String str = this.terminal.getStringCapability(cap);
      if (str == null) {
         return false;
      } else {
         Curses.tputs(this.out, str, params);
         return true;
      }
   }

   private static enum State {
      NORMAL,
      SEARCH,
      FORWARD_SEARCH,
      VI_YANK_TO,
      VI_DELETE_TO,
      VI_CHANGE_TO;
   }
}
