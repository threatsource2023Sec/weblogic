package org.stringtemplate.v4.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.ListModel;
import javax.swing.border.Border;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreePath;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.stringtemplate.v4.InstanceScope;
import org.stringtemplate.v4.Interpreter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;
import org.stringtemplate.v4.debug.EvalExprEvent;
import org.stringtemplate.v4.debug.EvalTemplateEvent;
import org.stringtemplate.v4.debug.InterpEvent;
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.Interval;
import org.stringtemplate.v4.misc.Misc;
import org.stringtemplate.v4.misc.STMessage;
import org.stringtemplate.v4.misc.STRuntimeMessage;

public class STViz {
   protected static final String WINDOWS_LINE_ENDINGS = "WINDOWS_LINE_ENDINGS";
   public EvalTemplateEvent root;
   public InterpEvent currentEvent;
   public InstanceScope currentScope;
   public List allEvents;
   public JTreeSTModel tmodel;
   public ErrorManager errMgr;
   public Interpreter interp;
   public String output;
   public List trace;
   public List errors;
   public STViewFrame viewFrame;
   private final AtomicInteger updateDepth = new AtomicInteger();

   public STViz(ErrorManager errMgr, EvalTemplateEvent root, String output, Interpreter interp, List trace, List errors) {
      this.errMgr = errMgr;
      this.currentEvent = root;
      this.currentScope = root.scope;
      this.output = output;
      this.interp = interp;
      this.allEvents = interp.getEvents();
      this.trace = trace;
      this.errors = errors;
   }

   public void open() {
      this.viewFrame = new STViewFrame();
      this.updateStack(this.currentScope, this.viewFrame);
      this.updateAttributes(this.currentScope, this.viewFrame);
      List events = this.currentScope.events;
      this.tmodel = new JTreeSTModel(this.interp, (EvalTemplateEvent)events.get(events.size() - 1));
      this.viewFrame.tree.setModel(this.tmodel);
      this.viewFrame.tree.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
            int depth = STViz.this.updateDepth.incrementAndGet();

            try {
               if (depth == 1) {
                  STViz.this.currentEvent = ((JTreeSTModel.Wrapper)STViz.this.viewFrame.tree.getLastSelectedPathComponent()).event;
                  STViz.this.currentScope = STViz.this.currentEvent.scope;
                  STViz.this.updateCurrentST(STViz.this.viewFrame);
                  return;
               }
            } finally {
               STViz.this.updateDepth.decrementAndGet();
            }

         }
      });
      JTreeASTModel astModel = new JTreeASTModel(new CommonTreeAdaptor(), this.currentScope.st.impl.ast);
      this.viewFrame.ast.setModel(astModel);
      this.viewFrame.ast.addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
            int depth = STViz.this.updateDepth.incrementAndGet();

            try {
               if (depth != 1) {
                  return;
               }

               TreePath path = treeSelectionEvent.getNewLeadSelectionPath();
               if (path != null) {
                  CommonTree node = (CommonTree)treeSelectionEvent.getNewLeadSelectionPath().getLastPathComponent();
                  CommonToken a = (CommonToken)STViz.this.currentScope.st.impl.tokens.get(node.getTokenStartIndex());
                  CommonToken b = (CommonToken)STViz.this.currentScope.st.impl.tokens.get(node.getTokenStopIndex());
                  STViz.this.highlight(STViz.this.viewFrame.template, a.getStartIndex(), b.getStopIndex());
                  return;
               }
            } finally {
               STViz.this.updateDepth.decrementAndGet();
            }

         }
      });
      CaretListener caretListenerLabel = new CaretListener() {
         public void caretUpdate(CaretEvent e) {
            int depth = STViz.this.updateDepth.incrementAndGet();

            try {
               if (depth == 1) {
                  int dot = STViz.this.toEventPosition((JTextComponent)e.getSource(), e.getDot());
                  STViz.this.currentEvent = STViz.this.findEventAtOutputLocation(STViz.this.allEvents, dot);
                  if (STViz.this.currentEvent == null) {
                     STViz.this.currentScope = STViz.this.tmodel.root.event.scope;
                  } else {
                     STViz.this.currentScope = STViz.this.currentEvent.scope;
                  }

                  List stack = Interpreter.getEvalTemplateEventStack(STViz.this.currentScope, true);
                  Object[] path = new Object[stack.size()];
                  int j = 0;

                  EvalTemplateEvent s;
                  for(Iterator var7 = stack.iterator(); var7.hasNext(); path[j++] = new JTreeSTModel.Wrapper(s)) {
                     s = (EvalTemplateEvent)var7.next();
                  }

                  TreePath p = new TreePath(path);
                  STViz.this.viewFrame.tree.setSelectionPath(p);
                  STViz.this.viewFrame.tree.scrollPathToVisible(p);
                  STViz.this.updateCurrentST(STViz.this.viewFrame);
                  return;
               }
            } finally {
               STViz.this.updateDepth.decrementAndGet();
            }

         }
      };
      this.viewFrame.output.addCaretListener(caretListenerLabel);
      if (this.errors != null && this.errors.size() != 0) {
         DefaultListModel errorListModel = new DefaultListModel();
         Iterator var5 = this.errors.iterator();

         while(var5.hasNext()) {
            STMessage msg = (STMessage)var5.next();
            errorListModel.addElement(msg);
         }

         this.viewFrame.errorList.setModel(errorListModel);
      } else {
         this.viewFrame.errorScrollPane.setVisible(false);
      }

      this.viewFrame.errorList.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent e) {
            int depth = STViz.this.updateDepth.incrementAndGet();

            try {
               if (depth != 1) {
                  return;
               }

               int minIndex = STViz.this.viewFrame.errorList.getMinSelectionIndex();
               int maxIndex = STViz.this.viewFrame.errorList.getMaxSelectionIndex();

               int i;
               for(i = minIndex; i <= maxIndex && !STViz.this.viewFrame.errorList.isSelectedIndex(i); ++i) {
               }

               ListModel model = STViz.this.viewFrame.errorList.getModel();
               STMessage msg = (STMessage)model.getElementAt(i);
               if (msg instanceof STRuntimeMessage) {
                  STRuntimeMessage rmsg = (STRuntimeMessage)msg;
                  Interval I = rmsg.self.impl.sourceMap[rmsg.ip];
                  STViz.this.currentEvent = null;
                  STViz.this.currentScope = ((STRuntimeMessage)msg).scope;
                  STViz.this.updateCurrentST(STViz.this.viewFrame);
                  if (I != null) {
                     STViz.this.highlight(STViz.this.viewFrame.template, I.a, I.b);
                  }
               }
            } finally {
               STViz.this.updateDepth.decrementAndGet();
            }

         }
      });
      Border empty = BorderFactory.createEmptyBorder();
      this.viewFrame.treeContentSplitPane.setBorder(empty);
      this.viewFrame.outputTemplateSplitPane.setBorder(empty);
      this.viewFrame.templateBytecodeTraceTabPanel.setBorder(empty);
      this.viewFrame.treeAttributesSplitPane.setBorder(empty);
      this.viewFrame.treeContentSplitPane.setOneTouchExpandable(true);
      this.viewFrame.outputTemplateSplitPane.setOneTouchExpandable(true);
      this.viewFrame.treeContentSplitPane.setDividerSize(10);
      this.viewFrame.outputTemplateSplitPane.setDividerSize(8);
      this.viewFrame.treeContentSplitPane.setContinuousLayout(true);
      this.viewFrame.treeAttributesSplitPane.setContinuousLayout(true);
      this.viewFrame.outputTemplateSplitPane.setContinuousLayout(true);
      this.viewFrame.setDefaultCloseOperation(2);
      this.viewFrame.pack();
      this.viewFrame.setSize(900, 700);
      this.setText(this.viewFrame.output, this.output);
      this.setText(this.viewFrame.template, this.currentScope.st.impl.template);
      this.setText(this.viewFrame.bytecode, this.currentScope.st.impl.disasm());
      this.setText(this.viewFrame.trace, Misc.join(this.trace.iterator(), "\n"));
      this.viewFrame.setVisible(true);
   }

   public void waitForClose() throws InterruptedException {
      final Object lock = new Object();
      Thread t = new Thread() {
         public void run() {
            synchronized(lock) {
               while(STViz.this.viewFrame.isVisible()) {
                  try {
                     lock.wait();
                  } catch (InterruptedException var4) {
                  }
               }

            }
         }
      };
      t.start();
      this.viewFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent arg0) {
            synchronized(lock) {
               STViz.this.viewFrame.setVisible(false);
               lock.notify();
            }
         }
      });
      t.join();
   }

   private void updateCurrentST(STViewFrame m) {
      this.updateStack(this.currentScope, m);
      this.updateAttributes(this.currentScope, m);
      this.setText(m.bytecode, this.currentScope.st.impl.disasm());
      this.setText(m.template, this.currentScope.st.impl.template);
      JTreeASTModel astModel = new JTreeASTModel(new CommonTreeAdaptor(), this.currentScope.st.impl.ast);
      this.viewFrame.ast.setModel(astModel);
      if (this.currentEvent instanceof EvalExprEvent) {
         EvalExprEvent exprEvent = (EvalExprEvent)this.currentEvent;
         this.highlight(m.output, exprEvent.outputStartChar, exprEvent.outputStopChar);
         this.highlight(m.template, exprEvent.exprStartChar, exprEvent.exprStopChar);
      } else {
         EvalTemplateEvent templateEvent;
         if (this.currentEvent instanceof EvalTemplateEvent) {
            templateEvent = (EvalTemplateEvent)this.currentEvent;
         } else {
            List events = this.currentScope.events;
            templateEvent = (EvalTemplateEvent)events.get(events.size() - 1);
         }

         if (templateEvent != null) {
            this.highlight(m.output, templateEvent.outputStartChar, templateEvent.outputStopChar);
         }

         if (this.currentScope.st.isAnonSubtemplate()) {
            Interval r = this.currentScope.st.impl.getTemplateRange();
            this.highlight(m.template, r.a, r.b);
         }
      }

   }

   protected void setText(JEditorPane component, String text) {
      List windowsLineEndingsList = new ArrayList();

      for(int i = 0; i < text.length(); i += 2) {
         i = text.indexOf("\r\n", i);
         if (i < 0) {
            break;
         }

         windowsLineEndingsList.add(i);
      }

      int[] windowsLineEndings = new int[windowsLineEndingsList.size()];

      for(int i = 0; i < windowsLineEndingsList.size(); ++i) {
         windowsLineEndings[i] = (Integer)windowsLineEndingsList.get(i);
      }

      component.setText(text);
      component.getDocument().putProperty("WINDOWS_LINE_ENDINGS", windowsLineEndings);
   }

   protected int toComponentPosition(JTextComponent component, int position) {
      int[] windowsLineEndings = (int[])((int[])component.getDocument().getProperty("WINDOWS_LINE_ENDINGS"));
      if (windowsLineEndings != null && windowsLineEndings.length != 0) {
         int index = Arrays.binarySearch(windowsLineEndings, position);
         return index >= 0 ? position - index : position - (-index - 1);
      } else {
         return position;
      }
   }

   protected int toEventPosition(JTextComponent component, int position) {
      int result;
      for(result = position; this.toComponentPosition(component, result) < position; ++result) {
      }

      return result;
   }

   protected final void highlight(JTextComponent comp, int i, int j) {
      this.highlight(comp, i, j, true);
   }

   protected void highlight(JTextComponent comp, int i, int j, boolean scroll) {
      Highlighter highlighter = comp.getHighlighter();
      highlighter.removeAllHighlights();

      try {
         i = this.toComponentPosition(comp, i);
         j = this.toComponentPosition(comp, j);
         highlighter.addHighlight(i, j + 1, DefaultHighlighter.DefaultPainter);
         if (scroll && (comp.getCaretPosition() < i || comp.getCaretPosition() > j)) {
            comp.moveCaretPosition(i);
            comp.scrollRectToVisible(comp.modelToView(i));
         }
      } catch (BadLocationException var7) {
         this.errMgr.internalError(this.tmodel.root.event.scope.st, "bad highlight location", var7);
      }

   }

   protected void updateAttributes(InstanceScope scope, STViewFrame m) {
      m.attributes.setModel(new JTreeScopeStackModel(scope));
      m.attributes.setRootVisible(false);
      m.attributes.setShowsRootHandles(true);
   }

   protected void updateStack(InstanceScope scope, STViewFrame m) {
      List stack = Interpreter.getEnclosingInstanceStack(scope, true);
      m.setTitle("STViz - [" + Misc.join(stack.iterator(), " ") + "]");
   }

   public InterpEvent findEventAtOutputLocation(List events, int charIndex) {
      Iterator var3 = events.iterator();

      InterpEvent e;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         e = (InterpEvent)var3.next();
      } while(e.scope.earlyEval || charIndex < e.outputStartChar || charIndex > e.outputStopChar);

      return e;
   }

   public static void main(String[] args) throws IOException {
      if (args.length > 0 && args[0].equals("1")) {
         test1();
      } else if (args.length > 0 && args[0].equals("2")) {
         test2();
      } else if (args.length > 0 && args[0].equals("3")) {
         test3();
      } else if (args.length > 0 && args[0].equals("4")) {
         test4();
      }

   }

   public static void test1() throws IOException {
      String templates = "method(type,name,locals,args,stats) ::= <<\npublic <type> <name>(<args:{a| int <a>}; separator=\", \">) {\n    <if(locals)>int locals[<locals>];<endif>\n    <stats;separator=\"\\n\">\n}\n>>\nassign(a,b) ::= \"<a> = <b>;\"\nreturn(x) ::= <<return <x>;>>\nparen(x) ::= \"(<x>)\"\n";
      String tmpdir = System.getProperty("java.io.tmpdir");
      writeFile(tmpdir, "t.stg", templates);
      STGroup group = new STGroupFile(tmpdir + "/" + "t.stg");
      ST st = group.getInstanceOf("method");
      st.impl.dump();
      st.add("type", "float");
      st.add("name", "foo");
      st.add("locals", 3);
      st.add("args", new String[]{"x", "y", "z"});
      ST s1 = group.getInstanceOf("assign");
      ST paren = group.getInstanceOf("paren");
      paren.add("x", "x");
      s1.add("a", paren);
      s1.add("b", "y");
      ST s2 = group.getInstanceOf("assign");
      s2.add("a", "y");
      s2.add("b", "z");
      ST s3 = group.getInstanceOf("return");
      s3.add("x", "3.14159");
      st.add("stats", s1);
      st.add("stats", s2);
      st.add("stats", s3);
      STViz viz = st.inspect();
      System.out.println(st.render());
   }

   public static void test2() throws IOException {
      String templates = "t1(q1=\"Some\\nText\") ::= <<\n<q1>\n>>\n\nt2(p1) ::= <<\n<p1>\n>>\n\nmain() ::= <<\nSTART-<t1()>-END\n\nSTART-<t2(p1=\"Some\\nText\")>-END\n>>\n";
      String tmpdir = System.getProperty("java.io.tmpdir");
      writeFile(tmpdir, "t.stg", templates);
      STGroup group = new STGroupFile(tmpdir + "/" + "t.stg");
      ST st = group.getInstanceOf("main");
      STViz viz = st.inspect();
   }

   public static void test3() throws IOException {
      String templates = "main() ::= <<\nFoo: <{bar};format=\"lower\">\n>>\n";
      String tmpdir = System.getProperty("java.io.tmpdir");
      writeFile(tmpdir, "t.stg", templates);
      STGroup group = new STGroupFile(tmpdir + "/" + "t.stg");
      ST st = group.getInstanceOf("main");
      st.inspect();
   }

   public static void test4() throws IOException {
      String templates = "main(t) ::= <<\nhi: <t>\n>>\nfoo(x,y={hi}) ::= \"<bar(x,y)>\"\nbar(x,y) ::= << <y> >>\nignore(m) ::= \"<m>\"\n";
      STGroup group = new STGroupString(templates);
      ST st = group.getInstanceOf("main");
      ST foo = group.getInstanceOf("foo");
      st.add("t", foo);
      ST ignore = group.getInstanceOf("ignore");
      ignore.add("m", foo);
      st.inspect();
      st.render();
   }

   public static void writeFile(String dir, String fileName, String content) {
      try {
         File f = new File(dir, fileName);
         if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
         }

         FileWriter w = new FileWriter(f);
         BufferedWriter bw = new BufferedWriter(w);
         bw.write(content);
         bw.close();
         w.close();
      } catch (IOException var6) {
         System.err.println("can't write file");
         var6.printStackTrace(System.err);
      }

   }
}
