package antlr.debug;

import antlr.collections.impl.BitSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ParserEventSupport {
   private Object source;
   private Hashtable doneListeners;
   private Vector matchListeners;
   private Vector messageListeners;
   private Vector tokenListeners;
   private Vector traceListeners;
   private Vector semPredListeners;
   private Vector synPredListeners;
   private Vector newLineListeners;
   private ParserMatchEvent matchEvent;
   private MessageEvent messageEvent;
   private ParserTokenEvent tokenEvent;
   private SemanticPredicateEvent semPredEvent;
   private SyntacticPredicateEvent synPredEvent;
   private TraceEvent traceEvent;
   private NewLineEvent newLineEvent;
   private ParserController controller;
   protected static final int CONSUME = 0;
   protected static final int ENTER_RULE = 1;
   protected static final int EXIT_RULE = 2;
   protected static final int LA = 3;
   protected static final int MATCH = 4;
   protected static final int MATCH_NOT = 5;
   protected static final int MISMATCH = 6;
   protected static final int MISMATCH_NOT = 7;
   protected static final int REPORT_ERROR = 8;
   protected static final int REPORT_WARNING = 9;
   protected static final int SEMPRED = 10;
   protected static final int SYNPRED_FAILED = 11;
   protected static final int SYNPRED_STARTED = 12;
   protected static final int SYNPRED_SUCCEEDED = 13;
   protected static final int NEW_LINE = 14;
   protected static final int DONE_PARSING = 15;
   private int ruleDepth = 0;

   public ParserEventSupport(Object var1) {
      this.matchEvent = new ParserMatchEvent(var1);
      this.messageEvent = new MessageEvent(var1);
      this.tokenEvent = new ParserTokenEvent(var1);
      this.traceEvent = new TraceEvent(var1);
      this.semPredEvent = new SemanticPredicateEvent(var1);
      this.synPredEvent = new SyntacticPredicateEvent(var1);
      this.newLineEvent = new NewLineEvent(var1);
      this.source = var1;
   }

   public void addDoneListener(ListenerBase var1) {
      if (this.doneListeners == null) {
         this.doneListeners = new Hashtable();
      }

      Integer var2 = (Integer)this.doneListeners.get(var1);
      int var3;
      if (var2 != null) {
         var3 = var2 + 1;
      } else {
         var3 = 1;
      }

      this.doneListeners.put(var1, new Integer(var3));
   }

   public void addMessageListener(MessageListener var1) {
      if (this.messageListeners == null) {
         this.messageListeners = new Vector();
      }

      this.messageListeners.addElement(var1);
      this.addDoneListener(var1);
   }

   public void addNewLineListener(NewLineListener var1) {
      if (this.newLineListeners == null) {
         this.newLineListeners = new Vector();
      }

      this.newLineListeners.addElement(var1);
      this.addDoneListener(var1);
   }

   public void addParserListener(ParserListener var1) {
      if (var1 instanceof ParserController) {
         ((ParserController)var1).setParserEventSupport(this);
         this.controller = (ParserController)var1;
      }

      this.addParserMatchListener(var1);
      this.addParserTokenListener(var1);
      this.addMessageListener(var1);
      this.addTraceListener(var1);
      this.addSemanticPredicateListener(var1);
      this.addSyntacticPredicateListener(var1);
   }

   public void addParserMatchListener(ParserMatchListener var1) {
      if (this.matchListeners == null) {
         this.matchListeners = new Vector();
      }

      this.matchListeners.addElement(var1);
      this.addDoneListener(var1);
   }

   public void addParserTokenListener(ParserTokenListener var1) {
      if (this.tokenListeners == null) {
         this.tokenListeners = new Vector();
      }

      this.tokenListeners.addElement(var1);
      this.addDoneListener(var1);
   }

   public void addSemanticPredicateListener(SemanticPredicateListener var1) {
      if (this.semPredListeners == null) {
         this.semPredListeners = new Vector();
      }

      this.semPredListeners.addElement(var1);
      this.addDoneListener(var1);
   }

   public void addSyntacticPredicateListener(SyntacticPredicateListener var1) {
      if (this.synPredListeners == null) {
         this.synPredListeners = new Vector();
      }

      this.synPredListeners.addElement(var1);
      this.addDoneListener(var1);
   }

   public void addTraceListener(TraceListener var1) {
      if (this.traceListeners == null) {
         this.traceListeners = new Vector();
      }

      this.traceListeners.addElement(var1);
      this.addDoneListener(var1);
   }

   public void fireConsume(int var1) {
      this.tokenEvent.setValues(ParserTokenEvent.CONSUME, 1, var1);
      this.fireEvents(0, this.tokenListeners);
   }

   public void fireDoneParsing() {
      this.traceEvent.setValues(TraceEvent.DONE_PARSING, 0, 0, 0);
      Hashtable var1 = null;
      ListenerBase var2 = null;
      synchronized(this) {
         if (this.doneListeners == null) {
            return;
         }

         var1 = (Hashtable)this.doneListeners.clone();
      }

      if (var1 != null) {
         Enumeration var3 = var1.keys();

         while(var3.hasMoreElements()) {
            var2 = (ListenerBase)var3.nextElement();
            this.fireEvent(15, var2);
         }
      }

      if (this.controller != null) {
         this.controller.checkBreak();
      }

   }

   public void fireEnterRule(int var1, int var2, int var3) {
      ++this.ruleDepth;
      this.traceEvent.setValues(TraceEvent.ENTER, var1, var2, var3);
      this.fireEvents(1, this.traceListeners);
   }

   public void fireEvent(int var1, ListenerBase var2) {
      switch (var1) {
         case 0:
            ((ParserTokenListener)var2).parserConsume(this.tokenEvent);
            break;
         case 1:
            ((TraceListener)var2).enterRule(this.traceEvent);
            break;
         case 2:
            ((TraceListener)var2).exitRule(this.traceEvent);
            break;
         case 3:
            ((ParserTokenListener)var2).parserLA(this.tokenEvent);
            break;
         case 4:
            ((ParserMatchListener)var2).parserMatch(this.matchEvent);
            break;
         case 5:
            ((ParserMatchListener)var2).parserMatchNot(this.matchEvent);
            break;
         case 6:
            ((ParserMatchListener)var2).parserMismatch(this.matchEvent);
            break;
         case 7:
            ((ParserMatchListener)var2).parserMismatchNot(this.matchEvent);
            break;
         case 8:
            ((MessageListener)var2).reportError(this.messageEvent);
            break;
         case 9:
            ((MessageListener)var2).reportWarning(this.messageEvent);
            break;
         case 10:
            ((SemanticPredicateListener)var2).semanticPredicateEvaluated(this.semPredEvent);
            break;
         case 11:
            ((SyntacticPredicateListener)var2).syntacticPredicateFailed(this.synPredEvent);
            break;
         case 12:
            ((SyntacticPredicateListener)var2).syntacticPredicateStarted(this.synPredEvent);
            break;
         case 13:
            ((SyntacticPredicateListener)var2).syntacticPredicateSucceeded(this.synPredEvent);
            break;
         case 14:
            ((NewLineListener)var2).hitNewLine(this.newLineEvent);
            break;
         case 15:
            var2.doneParsing(this.traceEvent);
            break;
         default:
            throw new IllegalArgumentException("bad type " + var1 + " for fireEvent()");
      }

   }

   public void fireEvents(int var1, Vector var2) {
      ListenerBase var3 = null;
      if (var2 != null) {
         for(int var4 = 0; var4 < var2.size(); ++var4) {
            var3 = (ListenerBase)var2.elementAt(var4);
            this.fireEvent(var1, var3);
         }
      }

      if (this.controller != null) {
         this.controller.checkBreak();
      }

   }

   public void fireExitRule(int var1, int var2, int var3) {
      this.traceEvent.setValues(TraceEvent.EXIT, var1, var2, var3);
      this.fireEvents(2, this.traceListeners);
      --this.ruleDepth;
      if (this.ruleDepth == 0) {
         this.fireDoneParsing();
      }

   }

   public void fireLA(int var1, int var2) {
      this.tokenEvent.setValues(ParserTokenEvent.LA, var1, var2);
      this.fireEvents(3, this.tokenListeners);
   }

   public void fireMatch(char var1, int var2) {
      this.matchEvent.setValues(ParserMatchEvent.CHAR, var1, new Character(var1), (String)null, var2, false, true);
      this.fireEvents(4, this.matchListeners);
   }

   public void fireMatch(char var1, BitSet var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.CHAR_BITSET, var1, var2, (String)null, var3, false, true);
      this.fireEvents(4, this.matchListeners);
   }

   public void fireMatch(char var1, String var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.CHAR_RANGE, var1, var2, (String)null, var3, false, true);
      this.fireEvents(4, this.matchListeners);
   }

   public void fireMatch(int var1, BitSet var2, String var3, int var4) {
      this.matchEvent.setValues(ParserMatchEvent.BITSET, var1, var2, var3, var4, false, true);
      this.fireEvents(4, this.matchListeners);
   }

   public void fireMatch(int var1, String var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.TOKEN, var1, new Integer(var1), var2, var3, false, true);
      this.fireEvents(4, this.matchListeners);
   }

   public void fireMatch(String var1, int var2) {
      this.matchEvent.setValues(ParserMatchEvent.STRING, 0, var1, (String)null, var2, false, true);
      this.fireEvents(4, this.matchListeners);
   }

   public void fireMatchNot(char var1, char var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.CHAR, var1, new Character(var2), (String)null, var3, true, true);
      this.fireEvents(5, this.matchListeners);
   }

   public void fireMatchNot(int var1, int var2, String var3, int var4) {
      this.matchEvent.setValues(ParserMatchEvent.TOKEN, var1, new Integer(var2), var3, var4, true, true);
      this.fireEvents(5, this.matchListeners);
   }

   public void fireMismatch(char var1, char var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.CHAR, var1, new Character(var2), (String)null, var3, false, false);
      this.fireEvents(6, this.matchListeners);
   }

   public void fireMismatch(char var1, BitSet var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.CHAR_BITSET, var1, var2, (String)null, var3, false, true);
      this.fireEvents(6, this.matchListeners);
   }

   public void fireMismatch(char var1, String var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.CHAR_RANGE, var1, var2, (String)null, var3, false, true);
      this.fireEvents(6, this.matchListeners);
   }

   public void fireMismatch(int var1, int var2, String var3, int var4) {
      this.matchEvent.setValues(ParserMatchEvent.TOKEN, var1, new Integer(var2), var3, var4, false, false);
      this.fireEvents(6, this.matchListeners);
   }

   public void fireMismatch(int var1, BitSet var2, String var3, int var4) {
      this.matchEvent.setValues(ParserMatchEvent.BITSET, var1, var2, var3, var4, false, true);
      this.fireEvents(6, this.matchListeners);
   }

   public void fireMismatch(String var1, String var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.STRING, 0, var2, var1, var3, false, true);
      this.fireEvents(6, this.matchListeners);
   }

   public void fireMismatchNot(char var1, char var2, int var3) {
      this.matchEvent.setValues(ParserMatchEvent.CHAR, var1, new Character(var2), (String)null, var3, true, true);
      this.fireEvents(7, this.matchListeners);
   }

   public void fireMismatchNot(int var1, int var2, String var3, int var4) {
      this.matchEvent.setValues(ParserMatchEvent.TOKEN, var1, new Integer(var2), var3, var4, true, true);
      this.fireEvents(7, this.matchListeners);
   }

   public void fireNewLine(int var1) {
      this.newLineEvent.setValues(var1);
      this.fireEvents(14, this.newLineListeners);
   }

   public void fireReportError(Exception var1) {
      this.messageEvent.setValues(MessageEvent.ERROR, var1.toString());
      this.fireEvents(8, this.messageListeners);
   }

   public void fireReportError(String var1) {
      this.messageEvent.setValues(MessageEvent.ERROR, var1);
      this.fireEvents(8, this.messageListeners);
   }

   public void fireReportWarning(String var1) {
      this.messageEvent.setValues(MessageEvent.WARNING, var1);
      this.fireEvents(9, this.messageListeners);
   }

   public boolean fireSemanticPredicateEvaluated(int var1, int var2, boolean var3, int var4) {
      this.semPredEvent.setValues(var1, var2, var3, var4);
      this.fireEvents(10, this.semPredListeners);
      return var3;
   }

   public void fireSyntacticPredicateFailed(int var1) {
      this.synPredEvent.setValues(0, var1);
      this.fireEvents(11, this.synPredListeners);
   }

   public void fireSyntacticPredicateStarted(int var1) {
      this.synPredEvent.setValues(0, var1);
      this.fireEvents(12, this.synPredListeners);
   }

   public void fireSyntacticPredicateSucceeded(int var1) {
      this.synPredEvent.setValues(0, var1);
      this.fireEvents(13, this.synPredListeners);
   }

   protected void refresh(Vector var1) {
      Vector var2;
      synchronized(var1) {
         var2 = (Vector)var1.clone();
      }

      if (var2 != null) {
         for(int var3 = 0; var3 < var2.size(); ++var3) {
            ((ListenerBase)var2.elementAt(var3)).refresh();
         }
      }

   }

   public void refreshListeners() {
      this.refresh(this.matchListeners);
      this.refresh(this.messageListeners);
      this.refresh(this.tokenListeners);
      this.refresh(this.traceListeners);
      this.refresh(this.semPredListeners);
      this.refresh(this.synPredListeners);
   }

   public void removeDoneListener(ListenerBase var1) {
      if (this.doneListeners != null) {
         Integer var2 = (Integer)this.doneListeners.get(var1);
         int var3 = 0;
         if (var2 != null) {
            var3 = var2 - 1;
         }

         if (var3 == 0) {
            this.doneListeners.remove(var1);
         } else {
            this.doneListeners.put(var1, new Integer(var3));
         }

      }
   }

   public void removeMessageListener(MessageListener var1) {
      if (this.messageListeners != null) {
         this.messageListeners.removeElement(var1);
      }

      this.removeDoneListener(var1);
   }

   public void removeNewLineListener(NewLineListener var1) {
      if (this.newLineListeners != null) {
         this.newLineListeners.removeElement(var1);
      }

      this.removeDoneListener(var1);
   }

   public void removeParserListener(ParserListener var1) {
      this.removeParserMatchListener(var1);
      this.removeMessageListener(var1);
      this.removeParserTokenListener(var1);
      this.removeTraceListener(var1);
      this.removeSemanticPredicateListener(var1);
      this.removeSyntacticPredicateListener(var1);
   }

   public void removeParserMatchListener(ParserMatchListener var1) {
      if (this.matchListeners != null) {
         this.matchListeners.removeElement(var1);
      }

      this.removeDoneListener(var1);
   }

   public void removeParserTokenListener(ParserTokenListener var1) {
      if (this.tokenListeners != null) {
         this.tokenListeners.removeElement(var1);
      }

      this.removeDoneListener(var1);
   }

   public void removeSemanticPredicateListener(SemanticPredicateListener var1) {
      if (this.semPredListeners != null) {
         this.semPredListeners.removeElement(var1);
      }

      this.removeDoneListener(var1);
   }

   public void removeSyntacticPredicateListener(SyntacticPredicateListener var1) {
      if (this.synPredListeners != null) {
         this.synPredListeners.removeElement(var1);
      }

      this.removeDoneListener(var1);
   }

   public void removeTraceListener(TraceListener var1) {
      if (this.traceListeners != null) {
         this.traceListeners.removeElement(var1);
      }

      this.removeDoneListener(var1);
   }
}
