package org.antlr.runtime.debug;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.TreeAdaptor;

public class DebugEventSocketProxy extends BlankDebugEventListener {
   public static final int DEFAULT_DEBUGGER_PORT = 49100;
   protected int port;
   protected ServerSocket serverSocket;
   protected Socket socket;
   protected String grammarFileName;
   protected PrintWriter out;
   protected BufferedReader in;
   protected BaseRecognizer recognizer;
   protected TreeAdaptor adaptor;

   public DebugEventSocketProxy(BaseRecognizer recognizer, TreeAdaptor adaptor) {
      this(recognizer, 49100, adaptor);
   }

   public DebugEventSocketProxy(BaseRecognizer recognizer, int port, TreeAdaptor adaptor) {
      this.port = 49100;
      this.grammarFileName = recognizer.getGrammarFileName();
      this.adaptor = adaptor;
      this.port = port;
   }

   public void handshake() throws IOException {
      if (this.serverSocket == null) {
         this.serverSocket = new ServerSocket(this.port);
         this.socket = this.serverSocket.accept();
         this.socket.setTcpNoDelay(true);
         OutputStream os = this.socket.getOutputStream();
         OutputStreamWriter osw = new OutputStreamWriter(os, "UTF8");
         this.out = new PrintWriter(new BufferedWriter(osw));
         InputStream is = this.socket.getInputStream();
         InputStreamReader isr = new InputStreamReader(is, "UTF8");
         this.in = new BufferedReader(isr);
         this.out.println("ANTLR 2");
         this.out.println("grammar \"" + this.grammarFileName);
         this.out.flush();
         this.ack();
      }

   }

   public void commence() {
   }

   public void terminate() {
      this.transmit("terminate");
      this.out.close();

      try {
         this.socket.close();
      } catch (IOException var2) {
         var2.printStackTrace(System.err);
      }

   }

   protected void ack() {
      try {
         this.in.readLine();
      } catch (IOException var2) {
         var2.printStackTrace(System.err);
      }

   }

   protected void transmit(String event) {
      this.out.println(event);
      this.out.flush();
      this.ack();
   }

   public void enterRule(String grammarFileName, String ruleName) {
      this.transmit("enterRule\t" + grammarFileName + "\t" + ruleName);
   }

   public void enterAlt(int alt) {
      this.transmit("enterAlt\t" + alt);
   }

   public void exitRule(String grammarFileName, String ruleName) {
      this.transmit("exitRule\t" + grammarFileName + "\t" + ruleName);
   }

   public void enterSubRule(int decisionNumber) {
      this.transmit("enterSubRule\t" + decisionNumber);
   }

   public void exitSubRule(int decisionNumber) {
      this.transmit("exitSubRule\t" + decisionNumber);
   }

   public void enterDecision(int decisionNumber, boolean couldBacktrack) {
      this.transmit("enterDecision\t" + decisionNumber + "\t" + couldBacktrack);
   }

   public void exitDecision(int decisionNumber) {
      this.transmit("exitDecision\t" + decisionNumber);
   }

   public void consumeToken(Token t) {
      String buf = this.serializeToken(t);
      this.transmit("consumeToken\t" + buf);
   }

   public void consumeHiddenToken(Token t) {
      String buf = this.serializeToken(t);
      this.transmit("consumeHiddenToken\t" + buf);
   }

   public void LT(int i, Token t) {
      if (t != null) {
         this.transmit("LT\t" + i + "\t" + this.serializeToken(t));
      }

   }

   public void mark(int i) {
      this.transmit("mark\t" + i);
   }

   public void rewind(int i) {
      this.transmit("rewind\t" + i);
   }

   public void rewind() {
      this.transmit("rewind");
   }

   public void beginBacktrack(int level) {
      this.transmit("beginBacktrack\t" + level);
   }

   public void endBacktrack(int level, boolean successful) {
      this.transmit("endBacktrack\t" + level + "\t" + (successful ? 1 : 0));
   }

   public void location(int line, int pos) {
      this.transmit("location\t" + line + "\t" + pos);
   }

   public void recognitionException(RecognitionException e) {
      StringBuilder buf = new StringBuilder(50);
      buf.append("exception\t");
      buf.append(e.getClass().getName());
      buf.append("\t");
      buf.append(e.index);
      buf.append("\t");
      buf.append(e.line);
      buf.append("\t");
      buf.append(e.charPositionInLine);
      this.transmit(buf.toString());
   }

   public void beginResync() {
      this.transmit("beginResync");
   }

   public void endResync() {
      this.transmit("endResync");
   }

   public void semanticPredicate(boolean result, String predicate) {
      StringBuffer buf = new StringBuffer(50);
      buf.append("semanticPredicate\t");
      buf.append(result);
      this.serializeText(buf, predicate);
      this.transmit(buf.toString());
   }

   public void consumeNode(Object t) {
      StringBuffer buf = new StringBuffer(50);
      buf.append("consumeNode");
      this.serializeNode(buf, t);
      this.transmit(buf.toString());
   }

   public void LT(int i, Object t) {
      this.adaptor.getUniqueID(t);
      this.adaptor.getText(t);
      this.adaptor.getType(t);
      StringBuffer buf = new StringBuffer(50);
      buf.append("LN\t");
      buf.append(i);
      this.serializeNode(buf, t);
      this.transmit(buf.toString());
   }

   protected void serializeNode(StringBuffer buf, Object t) {
      int ID = this.adaptor.getUniqueID(t);
      String text = this.adaptor.getText(t);
      int type = this.adaptor.getType(t);
      buf.append("\t");
      buf.append(ID);
      buf.append("\t");
      buf.append(type);
      Token token = this.adaptor.getToken(t);
      int line = -1;
      int pos = -1;
      if (token != null) {
         line = token.getLine();
         pos = token.getCharPositionInLine();
      }

      buf.append("\t");
      buf.append(line);
      buf.append("\t");
      buf.append(pos);
      int tokenIndex = this.adaptor.getTokenStartIndex(t);
      buf.append("\t");
      buf.append(tokenIndex);
      this.serializeText(buf, text);
   }

   public void nilNode(Object t) {
      int ID = this.adaptor.getUniqueID(t);
      this.transmit("nilNode\t" + ID);
   }

   public void errorNode(Object t) {
      int ID = this.adaptor.getUniqueID(t);
      String text = t.toString();
      StringBuffer buf = new StringBuffer(50);
      buf.append("errorNode\t");
      buf.append(ID);
      buf.append("\t");
      buf.append(0);
      this.serializeText(buf, text);
      this.transmit(buf.toString());
   }

   public void createNode(Object t) {
      int ID = this.adaptor.getUniqueID(t);
      String text = this.adaptor.getText(t);
      int type = this.adaptor.getType(t);
      StringBuffer buf = new StringBuffer(50);
      buf.append("createNodeFromTokenElements\t");
      buf.append(ID);
      buf.append("\t");
      buf.append(type);
      this.serializeText(buf, text);
      this.transmit(buf.toString());
   }

   public void createNode(Object node, Token token) {
      int ID = this.adaptor.getUniqueID(node);
      int tokenIndex = token.getTokenIndex();
      this.transmit("createNode\t" + ID + "\t" + tokenIndex);
   }

   public void becomeRoot(Object newRoot, Object oldRoot) {
      int newRootID = this.adaptor.getUniqueID(newRoot);
      int oldRootID = this.adaptor.getUniqueID(oldRoot);
      this.transmit("becomeRoot\t" + newRootID + "\t" + oldRootID);
   }

   public void addChild(Object root, Object child) {
      int rootID = this.adaptor.getUniqueID(root);
      int childID = this.adaptor.getUniqueID(child);
      this.transmit("addChild\t" + rootID + "\t" + childID);
   }

   public void setTokenBoundaries(Object t, int tokenStartIndex, int tokenStopIndex) {
      int ID = this.adaptor.getUniqueID(t);
      this.transmit("setTokenBoundaries\t" + ID + "\t" + tokenStartIndex + "\t" + tokenStopIndex);
   }

   public void setTreeAdaptor(TreeAdaptor adaptor) {
      this.adaptor = adaptor;
   }

   public TreeAdaptor getTreeAdaptor() {
      return this.adaptor;
   }

   protected String serializeToken(Token t) {
      StringBuffer buf = new StringBuffer(50);
      buf.append(t.getTokenIndex());
      buf.append('\t');
      buf.append(t.getType());
      buf.append('\t');
      buf.append(t.getChannel());
      buf.append('\t');
      buf.append(t.getLine());
      buf.append('\t');
      buf.append(t.getCharPositionInLine());
      this.serializeText(buf, t.getText());
      return buf.toString();
   }

   protected void serializeText(StringBuffer buf, String text) {
      buf.append("\t\"");
      if (text == null) {
         text = "";
      }

      text = this.escapeNewlines(text);
      buf.append(text);
   }

   protected String escapeNewlines(String txt) {
      txt = txt.replaceAll("%", "%25");
      txt = txt.replaceAll("\n", "%0A");
      txt = txt.replaceAll("\r", "%0D");
      return txt;
   }
}
