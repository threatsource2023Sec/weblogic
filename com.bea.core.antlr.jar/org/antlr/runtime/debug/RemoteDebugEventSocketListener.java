package org.antlr.runtime.debug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.StringTokenizer;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.BaseTree;
import org.antlr.runtime.tree.Tree;

public class RemoteDebugEventSocketListener implements Runnable {
   static final int MAX_EVENT_ELEMENTS = 8;
   DebugEventListener listener;
   String machine;
   int port;
   Socket channel = null;
   PrintWriter out;
   BufferedReader in;
   String event;
   public String version;
   public String grammarFileName;
   int previousTokenIndex = -1;
   boolean tokenIndexesInvalid = false;

   public RemoteDebugEventSocketListener(DebugEventListener listener, String machine, int port) throws IOException {
      this.listener = listener;
      this.machine = machine;
      this.port = port;
      if (!this.openConnection()) {
         throw new ConnectException();
      }
   }

   protected void eventHandler() {
      try {
         this.handshake();

         for(this.event = this.in.readLine(); this.event != null; this.event = this.in.readLine()) {
            this.dispatch(this.event);
            this.ack();
         }
      } catch (Exception var5) {
         System.err.println(var5);
         var5.printStackTrace(System.err);
      } finally {
         this.closeConnection();
      }

   }

   protected boolean openConnection() {
      boolean success = false;

      try {
         this.channel = new Socket(this.machine, this.port);
         this.channel.setTcpNoDelay(true);
         OutputStream os = this.channel.getOutputStream();
         OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
         this.out = new PrintWriter(new BufferedWriter(osw));
         InputStream is = this.channel.getInputStream();
         InputStreamReader isr = new InputStreamReader(is, "UTF8");
         this.in = new BufferedReader(isr);
         success = true;
      } catch (Exception var6) {
         System.err.println(var6);
      }

      return success;
   }

   protected void closeConnection() {
      try {
         this.in.close();
         this.in = null;
         this.out.close();
         this.out = null;
         this.channel.close();
         this.channel = null;
      } catch (Exception var14) {
         System.err.println(var14);
         var14.printStackTrace(System.err);
      } finally {
         if (this.in != null) {
            try {
               this.in.close();
            } catch (IOException var13) {
               System.err.println(var13);
            }
         }

         if (this.out != null) {
            this.out.close();
         }

         if (this.channel != null) {
            try {
               this.channel.close();
            } catch (IOException var12) {
               System.err.println(var12);
            }
         }

      }

   }

   protected void handshake() throws IOException {
      String antlrLine = this.in.readLine();
      String[] antlrElements = this.getEventElements(antlrLine);
      this.version = antlrElements[1];
      String grammarLine = this.in.readLine();
      String[] grammarElements = this.getEventElements(grammarLine);
      this.grammarFileName = grammarElements[1];
      this.ack();
      this.listener.commence();
   }

   protected void ack() {
      this.out.println("ack");
      this.out.flush();
   }

   protected void dispatch(String line) {
      String[] elements = this.getEventElements(line);
      if (elements != null && elements[0] != null) {
         if (elements[0].equals("enterRule")) {
            this.listener.enterRule(elements[1], elements[2]);
         } else if (elements[0].equals("exitRule")) {
            this.listener.exitRule(elements[1], elements[2]);
         } else if (elements[0].equals("enterAlt")) {
            this.listener.enterAlt(Integer.parseInt(elements[1]));
         } else if (elements[0].equals("enterSubRule")) {
            this.listener.enterSubRule(Integer.parseInt(elements[1]));
         } else if (elements[0].equals("exitSubRule")) {
            this.listener.exitSubRule(Integer.parseInt(elements[1]));
         } else if (elements[0].equals("enterDecision")) {
            this.listener.enterDecision(Integer.parseInt(elements[1]), elements[2].equals("true"));
         } else if (elements[0].equals("exitDecision")) {
            this.listener.exitDecision(Integer.parseInt(elements[1]));
         } else if (elements[0].equals("location")) {
            this.listener.location(Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
         } else {
            ProxyToken t;
            if (elements[0].equals("consumeToken")) {
               t = this.deserializeToken(elements, 1);
               if (t.getTokenIndex() == this.previousTokenIndex) {
                  this.tokenIndexesInvalid = true;
               }

               this.previousTokenIndex = t.getTokenIndex();
               this.listener.consumeToken(t);
            } else if (elements[0].equals("consumeHiddenToken")) {
               t = this.deserializeToken(elements, 1);
               if (t.getTokenIndex() == this.previousTokenIndex) {
                  this.tokenIndexesInvalid = true;
               }

               this.previousTokenIndex = t.getTokenIndex();
               this.listener.consumeHiddenToken(t);
            } else if (elements[0].equals("LT")) {
               t = this.deserializeToken(elements, 2);
               this.listener.LT(Integer.parseInt(elements[1]), (Token)t);
            } else if (elements[0].equals("mark")) {
               this.listener.mark(Integer.parseInt(elements[1]));
            } else if (elements[0].equals("rewind")) {
               if (elements[1] != null) {
                  this.listener.rewind(Integer.parseInt(elements[1]));
               } else {
                  this.listener.rewind();
               }
            } else if (elements[0].equals("beginBacktrack")) {
               this.listener.beginBacktrack(Integer.parseInt(elements[1]));
            } else {
               int childID;
               int ID;
               if (elements[0].equals("endBacktrack")) {
                  ID = Integer.parseInt(elements[1]);
                  childID = Integer.parseInt(elements[2]);
                  this.listener.endBacktrack(ID, childID == 1);
               } else {
                  String text;
                  String predicateText;
                  if (elements[0].equals("exception")) {
                     String excName = elements[1];
                     predicateText = elements[2];
                     text = elements[3];
                     String posS = elements[4];

                     try {
                        Class excClass = Class.forName(excName).asSubclass(RecognitionException.class);
                        RecognitionException e = (RecognitionException)excClass.newInstance();
                        e.index = Integer.parseInt(predicateText);
                        e.line = Integer.parseInt(text);
                        e.charPositionInLine = Integer.parseInt(posS);
                        this.listener.recognitionException(e);
                     } catch (ClassNotFoundException var9) {
                        System.err.println("can't find class " + var9);
                        var9.printStackTrace(System.err);
                     } catch (InstantiationException var10) {
                        System.err.println("can't instantiate class " + var10);
                        var10.printStackTrace(System.err);
                     } catch (IllegalAccessException var11) {
                        System.err.println("can't access class " + var11);
                        var11.printStackTrace(System.err);
                     }
                  } else if (elements[0].equals("beginResync")) {
                     this.listener.beginResync();
                  } else if (elements[0].equals("endResync")) {
                     this.listener.endResync();
                  } else if (elements[0].equals("terminate")) {
                     this.listener.terminate();
                  } else if (elements[0].equals("semanticPredicate")) {
                     Boolean result = Boolean.valueOf(elements[1]);
                     predicateText = elements[2];
                     predicateText = this.unEscapeNewlines(predicateText);
                     this.listener.semanticPredicate(result, predicateText);
                  } else if (elements[0].equals("consumeNode")) {
                     ProxyTree node = this.deserializeNode(elements, 1);
                     this.listener.consumeNode(node);
                  } else {
                     ProxyTree node;
                     if (elements[0].equals("LN")) {
                        ID = Integer.parseInt(elements[1]);
                        node = this.deserializeNode(elements, 2);
                        this.listener.LT(ID, (Object)node);
                     } else {
                        ProxyTree child;
                        if (elements[0].equals("createNodeFromTokenElements")) {
                           ID = Integer.parseInt(elements[1]);
                           childID = Integer.parseInt(elements[2]);
                           text = elements[3];
                           text = this.unEscapeNewlines(text);
                           child = new ProxyTree(ID, childID, -1, -1, -1, text);
                           this.listener.createNode(child);
                        } else {
                           ProxyTree root;
                           if (elements[0].equals("createNode")) {
                              ID = Integer.parseInt(elements[1]);
                              childID = Integer.parseInt(elements[2]);
                              root = new ProxyTree(ID);
                              ProxyToken token = new ProxyToken(childID);
                              this.listener.createNode(root, token);
                           } else if (elements[0].equals("nilNode")) {
                              ID = Integer.parseInt(elements[1]);
                              node = new ProxyTree(ID);
                              this.listener.nilNode(node);
                           } else if (elements[0].equals("errorNode")) {
                              ID = Integer.parseInt(elements[1]);
                              childID = Integer.parseInt(elements[2]);
                              text = elements[3];
                              text = this.unEscapeNewlines(text);
                              child = new ProxyTree(ID, childID, -1, -1, -1, text);
                              this.listener.errorNode(child);
                           } else if (elements[0].equals("becomeRoot")) {
                              ID = Integer.parseInt(elements[1]);
                              childID = Integer.parseInt(elements[2]);
                              root = new ProxyTree(ID);
                              child = new ProxyTree(childID);
                              this.listener.becomeRoot(root, child);
                           } else if (elements[0].equals("addChild")) {
                              ID = Integer.parseInt(elements[1]);
                              childID = Integer.parseInt(elements[2]);
                              root = new ProxyTree(ID);
                              child = new ProxyTree(childID);
                              this.listener.addChild(root, child);
                           } else if (elements[0].equals("setTokenBoundaries")) {
                              ID = Integer.parseInt(elements[1]);
                              node = new ProxyTree(ID);
                              this.listener.setTokenBoundaries(node, Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
                           } else {
                              System.err.println("unknown debug event: " + line);
                           }
                        }
                     }
                  }
               }
            }
         }

      } else {
         System.err.println("unknown debug event: " + line);
      }
   }

   protected ProxyTree deserializeNode(String[] elements, int offset) {
      int ID = Integer.parseInt(elements[offset + 0]);
      int type = Integer.parseInt(elements[offset + 1]);
      int tokenLine = Integer.parseInt(elements[offset + 2]);
      int charPositionInLine = Integer.parseInt(elements[offset + 3]);
      int tokenIndex = Integer.parseInt(elements[offset + 4]);
      String text = elements[offset + 5];
      text = this.unEscapeNewlines(text);
      return new ProxyTree(ID, type, tokenLine, charPositionInLine, tokenIndex, text);
   }

   protected ProxyToken deserializeToken(String[] elements, int offset) {
      String indexS = elements[offset + 0];
      String typeS = elements[offset + 1];
      String channelS = elements[offset + 2];
      String lineS = elements[offset + 3];
      String posS = elements[offset + 4];
      String text = elements[offset + 5];
      text = this.unEscapeNewlines(text);
      int index = Integer.parseInt(indexS);
      ProxyToken t = new ProxyToken(index, Integer.parseInt(typeS), Integer.parseInt(channelS), Integer.parseInt(lineS), Integer.parseInt(posS), text);
      return t;
   }

   public void start() {
      Thread t = new Thread(this);
      t.start();
   }

   public void run() {
      this.eventHandler();
   }

   public String[] getEventElements(String event) {
      if (event == null) {
         return null;
      } else {
         String[] elements = new String[8];
         String str = null;

         try {
            int firstQuoteIndex = event.indexOf(34);
            if (firstQuoteIndex >= 0) {
               String eventWithoutString = event.substring(0, firstQuoteIndex);
               str = event.substring(firstQuoteIndex + 1, event.length());
               event = eventWithoutString;
            }

            StringTokenizer st = new StringTokenizer(event, "\t", false);

            int i;
            for(i = 0; st.hasMoreTokens(); ++i) {
               if (i >= 8) {
                  return elements;
               }

               elements[i] = st.nextToken();
            }

            if (str != null) {
               elements[i] = str;
            }
         } catch (Exception var7) {
            var7.printStackTrace(System.err);
         }

         return elements;
      }
   }

   protected String unEscapeNewlines(String txt) {
      txt = txt.replaceAll("%0A", "\n");
      txt = txt.replaceAll("%0D", "\r");
      txt = txt.replaceAll("%25", "%");
      return txt;
   }

   public boolean tokenIndexesAreInvalid() {
      return false;
   }

   public static class ProxyTree extends BaseTree {
      public int ID;
      public int type;
      public int line = 0;
      public int charPos = -1;
      public int tokenIndex = -1;
      public String text;

      public ProxyTree(int ID, int type, int line, int charPos, int tokenIndex, String text) {
         this.ID = ID;
         this.type = type;
         this.line = line;
         this.charPos = charPos;
         this.tokenIndex = tokenIndex;
         this.text = text;
      }

      public ProxyTree(int ID) {
         this.ID = ID;
      }

      public int getTokenStartIndex() {
         return this.tokenIndex;
      }

      public void setTokenStartIndex(int index) {
      }

      public int getTokenStopIndex() {
         return 0;
      }

      public void setTokenStopIndex(int index) {
      }

      public Tree dupNode() {
         return null;
      }

      public int getType() {
         return this.type;
      }

      public String getText() {
         return this.text;
      }

      public String toString() {
         return "fix this";
      }
   }

   public static class ProxyToken implements Token {
      int index;
      int type;
      int channel;
      int line;
      int charPos;
      String text;

      public ProxyToken(int index) {
         this.index = index;
      }

      public ProxyToken(int index, int type, int channel, int line, int charPos, String text) {
         this.index = index;
         this.type = type;
         this.channel = channel;
         this.line = line;
         this.charPos = charPos;
         this.text = text;
      }

      public String getText() {
         return this.text;
      }

      public void setText(String text) {
         this.text = text;
      }

      public int getType() {
         return this.type;
      }

      public void setType(int ttype) {
         this.type = ttype;
      }

      public int getLine() {
         return this.line;
      }

      public void setLine(int line) {
         this.line = line;
      }

      public int getCharPositionInLine() {
         return this.charPos;
      }

      public void setCharPositionInLine(int pos) {
         this.charPos = pos;
      }

      public int getChannel() {
         return this.channel;
      }

      public void setChannel(int channel) {
         this.channel = channel;
      }

      public int getTokenIndex() {
         return this.index;
      }

      public void setTokenIndex(int index) {
         this.index = index;
      }

      public CharStream getInputStream() {
         return null;
      }

      public void setInputStream(CharStream input) {
      }

      public String toString() {
         String channelStr = "";
         if (this.channel != 0) {
            channelStr = ",channel=" + this.channel;
         }

         return "[" + this.getText() + "/<" + this.type + ">" + channelStr + "," + this.line + ":" + this.getCharPositionInLine() + ",@" + this.index + "]";
      }
   }
}
