package org.mozilla.javascript.tools.debugger;

import java.awt.AWTEvent;
import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.MenuComponent;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextListener;
import org.mozilla.javascript.NativeCall;
import org.mozilla.javascript.NativeFunction;
import org.mozilla.javascript.NativeGlobal;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;
import org.mozilla.javascript.debug.DebugFrame;
import org.mozilla.javascript.debug.DebuggableEngine;
import org.mozilla.javascript.debug.DebuggableScript;
import org.mozilla.javascript.debug.Debugger;

public class Main extends JFrame implements Debugger, ContextListener {
   HashSet contexts = new HashSet();
   static Thread mainThread;
   boolean breakFlag = false;
   static final int STEP_OVER = 0;
   static final int STEP_INTO = 1;
   static final int STEP_OUT = 2;
   static final int GO = 3;
   static final int BREAK = 4;
   static final int RUN_TO_CURSOR = 5;
   static final int EXIT = 6;
   private Hashtable threadState = new Hashtable();
   private Thread runToCursorThread;
   private int runToCursorLine;
   private String runToCursorFile;
   private Hashtable sourceNames = new Hashtable();
   Hashtable functionMap = new Hashtable();
   Hashtable breakpointsMap = new Hashtable();
   JDesktopPane desk;
   ContextWindow context;
   Menubar menubar;
   JToolBar toolBar;
   JSInternalConsole console;
   EvalWindow evalWindow;
   JSplitPane split1;
   JLabel statusBar;
   ScopeProvider scopeProvider;
   Runnable exitAction;
   int frameIndex = -1;
   boolean isInterrupted = false;
   boolean nonDispatcherWaiting = false;
   int dispatcherIsWaiting = 0;
   Context currentContext = null;
   JFileChooser dlg;
   Hashtable fileWindows = new Hashtable();
   FileWindow currentWindow;
   Object monitor = new Object();
   Object swingMonitor = new Object();
   int returnValue = -1;
   boolean breakOnExceptions;
   Hashtable toplevels = new Hashtable();
   // $FF: synthetic field
   static Class class$javax$swing$JSplitPane;

   public Main(String var1) {
      super(var1);
      this.init();
   }

   void Exit() {
      this.returnValue = 6;
      if (this.exitAction != null) {
         swingInvokeLater(this.exitAction);
      }

   }

   void actionPerformed(ActionEvent var1) {
      String var2 = var1.getActionCommand();
      byte var3 = -1;
      if (!var2.equals("Cut") && !var2.equals("Copy") && !var2.equals("Paste")) {
         if (var2.equals("Step Over")) {
            var3 = 0;
         } else if (var2.equals("Step Into")) {
            var3 = 1;
         } else if (var2.equals("Step Out")) {
            var3 = 2;
         } else if (var2.equals("Go")) {
            var3 = 3;
         } else if (var2.equals("Break")) {
            this.doBreak();
         } else if (var2.equals("Run to Cursor")) {
            var3 = 5;
         } else if (var2.equals("Exit")) {
            this.Exit();
         } else {
            String var5;
            Scriptable var25;
            if (var2.equals("Open")) {
               var25 = this.getScope();
               if (var25 == null) {
                  MessageDialogWrapper.showMessageDialog(this, "Can't compile scripts: no scope available", "Open", 0);
               } else {
                  var5 = this.chooseFile("Select a file to compile");
                  if (var5 != null) {
                     (new Thread(new OpenFile(this, var25, var5))).start();
                  }
               }
            } else if (var2.equals("Load")) {
               var25 = this.getScope();
               if (var25 == null) {
                  MessageDialogWrapper.showMessageDialog(this, "Can't run scripts: no scope available", "Run", 0);
               } else {
                  var5 = this.chooseFile("Select a file to execute");
                  if (var5 != null) {
                     (new Thread(new LoadFile(this, var25, var5))).start();
                  }
               }
            } else if (var2.equals("More Windows...")) {
               MoreWindows var26 = new MoreWindows(this, this.fileWindows, "Window", "Files");
               var26.showDialog(this);
            } else if (var2.equals("Console")) {
               if (this.console.isIcon()) {
                  this.desk.getDesktopManager().deiconifyFrame(this.console);
               }

               this.console.show();
               this.desk.getDesktopManager().activateFrame(this.console);
               this.console.consoleTextArea.requestFocus();
            } else if (!var2.equals("Cut") && !var2.equals("Copy") && !var2.equals("Paste")) {
               if (var2.equals("Go to function...")) {
                  FindFunction var27 = new FindFunction(this, this.functionMap, "Go to function", "Function");
                  var27.showDialog(this);
               } else {
                  int var6;
                  int var7;
                  int var9;
                  int var10;
                  int var11;
                  JInternalFrame[] var28;
                  int var29;
                  if (var2.equals("Tile")) {
                     var28 = this.desk.getAllFrames();
                     var29 = var28.length;
                     var6 = var7 = (int)Math.sqrt((double)var29);
                     if (var6 * var7 < var29) {
                        ++var7;
                        if (var6 * var7 < var29) {
                           ++var6;
                        }
                     }

                     Dimension var8 = this.desk.getSize();
                     var9 = var8.width / var7;
                     var10 = var8.height / var6;
                     var11 = 0;
                     int var12 = 0;

                     for(int var13 = 0; var13 < var6; ++var13) {
                        for(int var14 = 0; var14 < var7; ++var14) {
                           int var15 = var13 * var7 + var14;
                           if (var15 >= var28.length) {
                              break;
                           }

                           JInternalFrame var16 = var28[var15];

                           try {
                              var16.setIcon(false);
                              var16.setMaximum(false);
                           } catch (Exception var24) {
                           }

                           this.desk.getDesktopManager().setBoundsForFrame(var16, var11, var12, var9, var10);
                           var11 += var9;
                        }

                        var12 += var10;
                        var11 = 0;
                     }
                  } else if (var2.equals("Cascade")) {
                     var28 = this.desk.getAllFrames();
                     var29 = var28.length;
                     var7 = 0;
                     var6 = 0;
                     var9 = this.desk.getHeight();
                     var10 = var9 / var29;
                     if (var10 > 30) {
                        var10 = 30;
                     }

                     for(var11 = var29 - 1; var11 >= 0; var7 += var10) {
                        JInternalFrame var34 = var28[var11];

                        try {
                           var34.setIcon(false);
                           var34.setMaximum(false);
                        } catch (Exception var23) {
                        }

                        Dimension var35 = var34.getPreferredSize();
                        int var33 = var35.width;
                        var9 = var35.height;
                        this.desk.getDesktopManager().setBoundsForFrame(var34, var6, var7, var33, var9);
                        --var11;
                        var6 += var10;
                     }
                  } else {
                     FileWindow var30 = this.getFileWindow(var2);
                     if (var30 != null) {
                        FileWindow var31 = (FileWindow)var30;

                        try {
                           if (var31.isIcon()) {
                              var31.setIcon(false);
                           }

                           var31.setVisible(true);
                           var31.moveToFront();
                           var31.setSelected(true);
                        } catch (Exception var22) {
                        }
                     }
                  }
               }
            }
         }
      } else {
         JInternalFrame var4 = this.getSelectedFrame();
         if (var4 != null && var4 instanceof ActionListener) {
            ((ActionListener)var4).actionPerformed(var1);
         }
      }

      if (var3 != -1) {
         if (this.currentWindow != null) {
            this.currentWindow.setPosition(-1);
         }

         Object var32 = this.monitor;
         synchronized(var32){}

         try {
            this.returnValue = var3;
            this.monitor.notify();
         } catch (Throwable var21) {
            throw var21;
         }
      }

   }

   void addTopLevel(String var1, JFrame var2) {
      if (var2 != this) {
         this.toplevels.put(var1, var2);
      }

   }

   String chooseFile(String var1) {
      this.dlg.setDialogTitle(var1);
      File var2 = null;
      String var3 = System.getProperty("user.dir");
      if (var3 != null) {
         var2 = new File(var3);
      }

      if (var2 != null) {
         this.dlg.setCurrentDirectory(var2);
      }

      int var4 = this.dlg.showOpenDialog(this);
      if (var4 == 0) {
         try {
            String var5 = this.dlg.getSelectedFile().getCanonicalPath();
            var2 = this.dlg.getSelectedFile().getParentFile();
            System.setProperty("user.dir", var2.getPath());
            return var5;
         } catch (IOException var6) {
         }
      }

      return null;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public void clearAllBreakpoints() {
      this.doClearBreakpoints();
   }

   void clearBreakPoint(String var1, int var2) {
      Vector var3 = (Vector)this.sourceNames.get(var1);
      if (var3 != null) {
         for(int var4 = 0; var4 < var3.size(); ++var4) {
            SourceEntry var5 = (SourceEntry)var3.elementAt(var4);
            var5.fnOrScript.removeBreakpoint(var2);
         }

      }
   }

   public void contextCreated(Context var1) {
      HashSet var2 = this.contexts;
      synchronized(var2){}

      try {
         DebuggableEngine var4 = var1.getDebuggableEngine();
         var4.setDebugger(this);
         var1.setGeneratingDebug(true);
         var1.setOptimizationLevel(-1);
         if (this.breakFlag || Thread.currentThread() == mainThread) {
            var4.setBreakNextLine(true);
         }
      } catch (Throwable var6) {
         throw var6;
      }

   }

   public void contextEntered(Context var1) {
      HashSet var2 = this.contexts;
      synchronized(var2){}

      try {
         if (!this.contexts.contains(var1) && var1.getDebuggableEngine().getDebugger() == this) {
            this.contexts.add(var1);
         }
      } catch (Throwable var4) {
         throw var4;
      }

   }

   public void contextExited(Context var1) {
   }

   public void contextReleased(Context var1) {
      HashSet var2 = this.contexts;
      synchronized(var2){}

      try {
         this.contexts.remove(var1);
      } catch (Throwable var4) {
         throw var4;
      }

   }

   void contextSwitch(int var1) {
      Context var2 = this.getCurrentContext();
      DebuggableEngine var3 = var2.getDebuggableEngine();
      ContextHelper var4 = new ContextHelper();
      var4.attach(var2);
      if (var2 != null) {
         int var5 = var3.getFrameCount();
         if (var1 < 0 || var1 >= var5) {
            var4.reset();
            return;
         }

         this.frameIndex = var1;
         DebugFrame var6 = var3.getFrame(var1);
         String var7 = var6.getSourceName();
         if (var7 == null || var7.equals("<stdin>")) {
            this.console.show();
            var4.reset();
            return;
         }

         if (var7 == "<eval>") {
            var4.reset();
            return;
         }

         int var8 = var6.getLineNumber();
         this.frameIndex = var1;
         FileWindow var9 = this.getFileWindow(var7);
         if (var9 != null) {
            SetFilePosition var10 = new SetFilePosition(this, var9, var8);
            var10.run();
         } else {
            Vector var13 = (Vector)this.sourceNames.get(var7);
            String var11 = ((SourceEntry)var13.elementAt(0)).source.toString();
            CreateFileWindow var12 = new CreateFileWindow(this, var7, var11, var8);
            var12.run();
         }

         var4.reset();
      }

   }

   public void doBreak() {
      this.breakFlag = true;
      HashSet var1 = this.contexts;
      synchronized(var1){}

      try {
         Iterator var3 = this.contexts.iterator();

         while(var3.hasNext()) {
            Context var4 = (Context)var3.next();
            var4.getDebuggableEngine().setBreakNextLine(true);
         }
      } catch (Throwable var6) {
         throw var6;
      }

   }

   void doClearBreakpoints() {
      Enumeration var1 = this.breakpointsMap.keys();

      while(var1.hasMoreElements()) {
         String var2 = (String)var1.nextElement();
         Hashtable var3 = (Hashtable)this.breakpointsMap.get(var2);
         Enumeration var4 = var3.keys();

         while(var4.hasMoreElements()) {
            Integer var5 = (Integer)var3.get(var4.nextElement());
            this.clearBreakPoint(var2, var5);
         }
      }

   }

   String eval(String var1) {
      Context var2 = this.getCurrentContext();
      DebuggableEngine var3 = var2.getDebuggableEngine();
      if (var2 == null) {
         return "undefined";
      } else {
         ContextHelper var4 = new ContextHelper();
         var4.attach(var2);
         if (this.frameIndex >= var3.getFrameCount()) {
            var4.reset();
            return "undefined";
         } else {
            var3.setDebugger((Debugger)null);
            var2.setGeneratingDebug(false);
            var2.setOptimizationLevel(-1);
            boolean var6 = var3.getBreakNextLine();
            var3.setBreakNextLine(false);

            String var5;
            try {
               Scriptable var7 = var3.getFrame(this.frameIndex).getVariableObject();
               Object var8;
               if (var7 instanceof NativeCall) {
                  NativeCall var9 = (NativeCall)var7;
                  var8 = NativeGlobal.evalSpecial(var2, var9, var9.getThisObj(), new Object[]{var1}, "", 1);
               } else {
                  var8 = var2.evaluateString(var7, var1, "", 0, (Object)null);
               }

               if (var8 == Undefined.instance) {
                  var8 = "";
               }

               try {
                  var5 = ScriptRuntime.toString(var8);
               } catch (Exception var10) {
                  var5 = var8.toString();
               }
            } catch (Exception var11) {
               var5 = var11.getMessage();
            }

            if (var5 == null) {
               var5 = "null";
            }

            var3.setDebugger(this);
            var2.setGeneratingDebug(true);
            var2.setOptimizationLevel(-1);
            var3.setBreakNextLine(var6);
            var4.reset();
            return var5;
         }
      }
   }

   Context getCurrentContext() {
      return this.currentContext;
   }

   public PrintStream getErr() {
      return this.console.getErr();
   }

   FileWindow getFileWindow(String var1) {
      if (var1 != null && !var1.equals("<stdin>") && !var1.equals("<eval>")) {
         String var2 = var1;
         Enumeration var3 = this.fileWindows.keys();

         while(var3.hasMoreElements()) {
            String var4 = (String)var3.nextElement();
            if (var2.equals(var4)) {
               FileWindow var5 = (FileWindow)this.fileWindows.get(var4);
               var5.setUrl(var1);
               return var5;
            }
         }

         return (FileWindow)this.fileWindows.get(var1);
      } else {
         return null;
      }
   }

   public InputStream getIn() {
      return this.console.getIn();
   }

   public PrintStream getOut() {
      return this.console.getOut();
   }

   Scriptable getScope() {
      return this.scopeProvider != null ? this.scopeProvider.getScope() : null;
   }

   JInternalFrame getSelectedFrame() {
      JInternalFrame[] var1 = this.desk.getAllFrames();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         if (var1[var2].isShowing()) {
            return var1[var2];
         }
      }

      return var1[var1.length - 1];
   }

   JMenu getWindowMenu() {
      return this.menubar.getMenu(3);
   }

   public void handleBreakpointHit(Context var1) {
      this.breakFlag = false;
      this.interrupted(var1);
   }

   public void handleCompilationDone(Context var1, DebuggableScript var2, StringBuffer var3) {
      String var4 = var2.getSourceName();
      if (var4 == null) {
         var4 = "<stdin>";
      }

      Vector var5 = (Vector)this.sourceNames.get(var4);
      if (var5 == null) {
         var5 = new Vector();
         this.sourceNames.put(var4, var5);
      }

      SourceEntry var6 = new SourceEntry(var3, var2);
      var5.addElement(var6);
      if (var2.getScriptable() instanceof NativeFunction) {
         NativeFunction var7 = (NativeFunction)var2.getScriptable();
         String var8 = var7.getFunctionName();
         if (var8.length() > 0 && !var8.equals("anonymous")) {
            this.functionMap.put(var8, var6);
         }
      }

      this.loadedFile(var4, var3.toString());
   }

   public void handleExceptionThrown(Context var1, Object var2) {
      if (this.breakOnExceptions) {
         DebuggableEngine var3 = var1.getDebuggableEngine();
         DebugFrame var4 = var3.getFrame(0);
         String var5 = var4.getSourceName();
         int var6 = var4.getLineNumber();
         FileWindow var7 = null;
         if (var5 == null) {
            if (var6 == -1) {
               var5 = "<eval>";
            } else {
               var5 = "<stdin>";
            }
         } else {
            var7 = this.getFileWindow(var5);
         }

         if (var2 instanceof NativeJavaObject) {
            var2 = ((NativeJavaObject)var2).unwrap();
         }

         String var8 = var2.toString();
         if (var8 == null || var8.length() == 0) {
            var8 = var2.getClass().toString();
         }

         var8 = var8 + " (" + var5 + ", line " + var6 + ")";
         if (var7 != null) {
            swingInvoke(new SetFilePosition(this, var7, var6));
         }

         MessageDialogWrapper.showMessageDialog(this, var8, "Exception in Script", 0);
         this.interrupted(var1);
      }

   }

   void init() {
      this.setJMenuBar(this.menubar = new Menubar(this));
      this.toolBar = new JToolBar();
      String[] var7 = new String[]{"Break (Pause)", "Go (F5)", "Step Into (F11)", "Step Over (F7)", "Step Out (F8)"};
      int var8 = 0;
      JButton var2;
      JButton var1 = var2 = new JButton("Break");
      var1.setToolTipText("Break");
      var1.setActionCommand("Break");
      var1.addActionListener(this.menubar);
      var1.setEnabled(true);
      var1.setToolTipText(var7[var8++]);
      JButton var3;
      var1 = var3 = new JButton("Go");
      var1.setToolTipText("Go");
      var1.setActionCommand("Go");
      var1.addActionListener(this.menubar);
      var1.setEnabled(false);
      var1.setToolTipText(var7[var8++]);
      JButton var4;
      var1 = var4 = new JButton("Step Into");
      var1.setToolTipText("Step Into");
      var1.setActionCommand("Step Into");
      var1.addActionListener(this.menubar);
      var1.setEnabled(false);
      var1.setToolTipText(var7[var8++]);
      JButton var5;
      var1 = var5 = new JButton("Step Over");
      var1.setToolTipText("Step Over");
      var1.setActionCommand("Step Over");
      var1.setEnabled(false);
      var1.addActionListener(this.menubar);
      var1.setToolTipText(var7[var8++]);
      JButton var6;
      var1 = var6 = new JButton("Step Out");
      var1.setToolTipText("Step Out");
      var1.setActionCommand("Step Out");
      var1.setEnabled(false);
      var1.addActionListener(this.menubar);
      var1.setToolTipText(var7[var8++]);
      Dimension var10 = var5.getPreferredSize();
      var2.setPreferredSize(var10);
      var2.setMinimumSize(var10);
      var2.setMaximumSize(var10);
      var2.setSize(var10);
      var3.setPreferredSize(var10);
      var3.setMinimumSize(var10);
      var3.setMaximumSize(var10);
      var4.setPreferredSize(var10);
      var4.setMinimumSize(var10);
      var4.setMaximumSize(var10);
      var5.setPreferredSize(var10);
      var5.setMinimumSize(var10);
      var5.setMaximumSize(var10);
      var6.setPreferredSize(var10);
      var6.setMinimumSize(var10);
      var6.setMaximumSize(var10);
      this.toolBar.add(var2);
      this.toolBar.add(var3);
      this.toolBar.add(var4);
      this.toolBar.add(var5);
      this.toolBar.add(var6);
      JPanel var11 = new JPanel();
      var11.setLayout(new BorderLayout());
      this.getContentPane().add(this.toolBar, "North");
      this.getContentPane().add(var11, "Center");
      this.desk = new JDesktopPane();
      this.desk.setPreferredSize(new Dimension(600, 300));
      this.desk.setMinimumSize(new Dimension(150, 50));
      this.desk.add(this.console = new JSInternalConsole("JavaScript Console"));
      this.context = new ContextWindow(this);
      this.context.setPreferredSize(new Dimension(600, 120));
      this.context.setMinimumSize(new Dimension(50, 50));
      this.split1 = new JSplitPane(0, this.desk, this.context);
      this.split1.setOneTouchExpandable(true);
      setResizeWeight(this.split1, 0.66);
      var11.add(this.split1, "Center");
      this.statusBar = new JLabel();
      this.statusBar.setText("Thread: ");
      var11.add(this.statusBar, "South");
      this.dlg = new JFileChooser();
      FileFilter var12 = new FileFilter() {
         public boolean accept(File var1) {
            if (var1.isDirectory()) {
               return true;
            } else {
               String var2 = var1.getName();
               int var3 = var2.lastIndexOf(46);
               if (var3 > 0 && var3 < var2.length() - 1) {
                  String var4 = var2.substring(var3 + 1).toLowerCase();
                  if (var4.equals("js")) {
                     return true;
                  }
               }

               return false;
            }
         }

         public String getDescription() {
            return "JavaScript Files (*.js)";
         }
      };
      this.dlg.addChoosableFileFilter(var12);
      this.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent var1) {
            Main.this.Exit();
         }
      });
   }

   void interrupted(Context var1) {
      Object var2 = this.swingMonitor;
      synchronized(var2){}

      try {
         if (EventQueue.isDispatchThread()) {
            ++this.dispatcherIsWaiting;
            if (this.nonDispatcherWaiting) {
               EventQueue var4 = Toolkit.getDefaultToolkit().getSystemEventQueue();

               while(this.nonDispatcherWaiting) {
                  try {
                     AWTEvent var5 = var4.getNextEvent();
                     if (var5 instanceof ActiveEvent) {
                        ((ActiveEvent)var5).dispatch();
                     } else {
                        Object var6 = var5.getSource();
                        if (var6 instanceof Component) {
                           Component var7 = (Component)var6;
                           var7.dispatchEvent(var5);
                        } else if (var6 instanceof MenuComponent) {
                           ((MenuComponent)var6).dispatchEvent(var5);
                        }
                     }

                     if (this.returnValue == 6) {
                        return;
                     }

                     this.swingMonitor.wait(1L);
                  } catch (InterruptedException var43) {
                     return;
                  }
               }
            }
         } else {
            while(true) {
               if (!this.isInterrupted && this.dispatcherIsWaiting <= 0) {
                  this.nonDispatcherWaiting = true;
                  break;
               }

               try {
                  this.swingMonitor.wait();
               } catch (InterruptedException var42) {
                  return;
               }
            }
         }

         this.isInterrupted = true;
      } catch (Throwable var44) {
         throw var44;
      }

      this.currentContext = var1;
      DebuggableEngine var45 = var1.getDebuggableEngine();
      Thread var3 = Thread.currentThread();
      this.statusBar.setText("Thread: " + var3.toString());
      ThreadState var46 = (ThreadState)this.threadState.get(var3);
      int var47 = -1;
      if (var46 != null) {
         var47 = var46.stopAtFrameDepth;
      }

      int var48;
      if (this.runToCursorFile != null && var3 == this.runToCursorThread) {
         var48 = var45.getFrameCount();
         if (var48 > 0) {
            DebugFrame var49 = var45.getFrame(0);
            String var8 = var49.getSourceName();
            if (var8 != null && var8.equals(this.runToCursorFile)) {
               int var9 = var49.getLineNumber();
               if (var9 == this.runToCursorLine) {
                  var47 = -1;
                  this.runToCursorFile = null;
               } else {
                  FileWindow var10 = this.getFileWindow(var8);
                  if (var10 == null || !var10.isBreakPoint(var9)) {
                     return;
                  }

                  this.runToCursorFile = null;
               }
            }
         }
      }

      if (var47 <= 0 || var45.getFrameCount() <= var47) {
         if (var46 != null) {
            var46.stopAtFrameDepth = -1;
         }

         this.threadState.remove(var3);
         var48 = var45.getFrameCount();
         this.frameIndex = var48 - 1;
         boolean var50 = false;
         if (var48 != 0) {
            label998: {
               DebugFrame var52 = var45.getFrame(0);
               String var53 = var52.getSourceName();
               var45.setBreakNextLine(false);
               int var51 = var52.getLineNumber();
               int var54 = 0;
               boolean var11 = EventQueue.isDispatchThread();
               if (!var11) {
                  Context.exit();

                  while(Context.getCurrentContext() != null) {
                     Context.exit();
                     ++var54;
                  }
               }

               if (var53 != null && !var53.equals("<stdin>")) {
                  FileWindow var55 = this.getFileWindow(var53);
                  if (var55 != null) {
                     SetFilePosition var13 = new SetFilePosition(this, var55, var51);
                     swingInvoke(var13);
                  } else {
                     Vector var57 = (Vector)this.sourceNames.get(var53);
                     String var14 = ((SourceEntry)var57.elementAt(0)).source.toString();
                     CreateFileWindow var15 = new CreateFileWindow(this, var53, var14, var51);
                     swingInvoke(var15);
                  }
               } else if (this.console.isVisible()) {
                  final JSInternalConsole var12 = this.console;
                  swingInvoke(new Runnable() {
                     public void run() {
                        var12.show();
                     }
                  });
               }

               swingInvoke(new EnterInterrupt(this, var1));
               swingInvoke(new UpdateContext(this, var1));
               int var56;
               if (!var11) {
                  Object var59 = this.monitor;
                  synchronized(var59){}

                  try {
                     this.returnValue = -1;

                     try {
                        while(true) {
                           if (this.returnValue != -1) {
                              var56 = this.returnValue;
                              break;
                           }

                           this.monitor.wait();
                        }
                     } catch (InterruptedException var40) {
                        break label998;
                     }
                  } catch (Throwable var41) {
                     throw var41;
                  }
               } else {
                  EventQueue var58 = Toolkit.getDefaultToolkit().getSystemEventQueue();
                  this.returnValue = -1;

                  while(this.returnValue == -1) {
                     try {
                        AWTEvent var60 = var58.getNextEvent();
                        if (var60 instanceof ActiveEvent) {
                           ((ActiveEvent)var60).dispatch();
                        } else {
                           Object var62 = var60.getSource();
                           if (var62 instanceof Component) {
                              Component var16 = (Component)var62;
                              var16.dispatchEvent(var60);
                           } else if (var62 instanceof MenuComponent) {
                              ((MenuComponent)var62).dispatchEvent(var60);
                           }
                        }
                     } catch (InterruptedException var39) {
                     }
                  }

                  var56 = this.returnValue;
               }

               swingInvoke(new ExitInterrupt(this));
               if (!var11) {
                  Context var61;
                  if ((var61 = Context.enter(var1)) != var1) {
                     System.out.println("debugger error: cx = " + var1 + " current = " + var61);
                  }

                  while(var54 > 0) {
                     Context.enter();
                     --var54;
                  }
               }

               switch (var56) {
                  case 0:
                     var45.setBreakNextLine(true);
                     var47 = var45.getFrameCount();
                     if (var46 == null) {
                        var46 = new ThreadState();
                     }

                     var46.stopAtFrameDepth = var47;
                     this.threadState.put(var3, var46);
                     break;
                  case 1:
                     var45.setBreakNextLine(true);
                     if (var46 != null) {
                        var46.stopAtFrameDepth = -1;
                     }
                     break;
                  case 2:
                     var47 = var45.getFrameCount() - 1;
                     if (var47 > 0) {
                        var45.setBreakNextLine(true);
                        if (var46 == null) {
                           var46 = new ThreadState();
                        }

                        var46.stopAtFrameDepth = var47;
                        this.threadState.put(var3, var46);
                     }
                  case 3:
                  case 4:
                  default:
                     break;
                  case 5:
                     var45.setBreakNextLine(true);
                     if (var46 != null) {
                        var46.stopAtFrameDepth = -1;
                     }
               }
            }
         }
      }

      var2 = this.swingMonitor;
      synchronized(var2){}

      try {
         this.isInterrupted = false;
         if (EventQueue.isDispatchThread()) {
            --this.dispatcherIsWaiting;
         } else {
            this.nonDispatcherWaiting = false;
         }

         this.swingMonitor.notifyAll();
      } catch (Throwable var38) {
         throw var38;
      }

   }

   void loadedFile(String var1, String var2) {
      FileWindow var3 = this.getFileWindow(var1);
      if (var3 != null) {
         swingInvoke(new SetFileText(var3, var2));
         var3.show();
      } else if (!var1.equals("<stdin>")) {
         swingInvoke(new CreateFileWindow(this, var1, var2, -1));
      }

   }

   public static void main(String[] var0) {
      try {
         mainThread = Thread.currentThread();
         final Main var1 = new Main("Rhino JavaScript Debugger");
         swingInvoke(new Runnable() {
            public void run() {
               var1.pack();
               var1.setSize(600, 460);
               var1.setVisible(true);
            }
         });
         var1.setExitAction(new Runnable() {
            public void run() {
               System.exit(0);
            }
         });
         System.setIn(var1.getIn());
         System.setOut(var1.getOut());
         System.setErr(var1.getErr());
         Context.addContextListener(var1);
         var1.setScopeProvider(new ScopeProvider() {
            public Scriptable getScope() {
               return org.mozilla.javascript.tools.shell.Main.getScope();
            }
         });
         org.mozilla.javascript.tools.shell.Main.exec(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   void removeWindow(FileWindow var1) {
      this.fileWindows.remove(var1.getUrl());
      JMenu var2 = this.getWindowMenu();
      int var3 = var2.getItemCount();
      JMenuItem var4 = var2.getItem(var3 - 1);
      String var5 = (new File(var1.getUrl())).getName();

      for(int var6 = 5; var6 < var3; ++var6) {
         JMenuItem var7 = var2.getItem(var6);
         if (var7 != null) {
            String var8 = var7.getText();
            int var9 = var8.indexOf(32);
            if (var8.substring(var9 + 1).equals(var5)) {
               var2.remove(var7);
               if (var3 == 6) {
                  var2.remove(4);
               } else {
                  for(int var10 = var6 - 4; var6 < var3 - 1; ++var6) {
                     JMenuItem var11 = var2.getItem(var6);
                     if (var11 != null) {
                        var8 = var11.getText();
                        if (var8.equals("More Windows...")) {
                           break;
                        }

                        var9 = var8.indexOf(32);
                        var11.setText((char)(48 + var10) + " " + var8.substring(var9 + 1));
                        var11.setMnemonic(48 + var10);
                        ++var10;
                     }
                  }

                  if (var3 - 6 == 0 && var4 != var7 && var4.getText().equals("More Windows...")) {
                     var2.remove(var4);
                  }
               }
               break;
            }
         }
      }

      var2.revalidate();
   }

   void runToCursor(String var1, int var2, ActionEvent var3) {
      Vector var4 = (Vector)this.sourceNames.get(var1);
      if (var4 == null) {
         System.out.println("debugger error: Couldn't find source: " + var1);
      }

      SourceEntry var6 = null;

      int var5;
      for(var5 = var4.size() - 1; var5 >= 0; --var5) {
         var6 = (SourceEntry)var4.elementAt(var5);
         if (var6.fnOrScript.removeBreakpoint(var2)) {
            var6.fnOrScript.placeBreakpoint(var2);
            break;
         }

         if (var6.fnOrScript.placeBreakpoint(var2)) {
            var6.fnOrScript.removeBreakpoint(var2);
            break;
         }
      }

      if (var5 >= 0) {
         this.runToCursorFile = var1;
         this.runToCursorLine = var2;
         this.actionPerformed(var3);
      }

   }

   void setBreakOnExceptions(boolean var1) {
      this.breakOnExceptions = var1;
   }

   int setBreakPoint(String var1, int var2) {
      Vector var3 = (Vector)this.sourceNames.get(var1);
      if (var3 == null) {
         return -1;
      } else {
         boolean var4 = false;
         int var5 = -1;

         for(int var7 = var3.size() - 1; var7 >= 0; --var7) {
            SourceEntry var6 = (SourceEntry)var3.elementAt(var7);
            if (var6.fnOrScript.placeBreakpoint(var2)) {
               var5 = var2;
            }
         }

         return var5;
      }
   }

   public void setExitAction(Runnable var1) {
      this.exitAction = var1;
   }

   static void setResizeWeight(JSplitPane var0, double var1) {
      try {
         Method var3 = (class$javax$swing$JSplitPane != null ? class$javax$swing$JSplitPane : (class$javax$swing$JSplitPane = class$("javax.swing.JSplitPane"))).getMethod("setResizeWeight", Double.TYPE);
         var3.invoke(var0, new Double(var1));
      } catch (NoSuchMethodException var4) {
      } catch (IllegalAccessException var5) {
      } catch (InvocationTargetException var6) {
      }

   }

   public void setScopeProvider(ScopeProvider var1) {
      this.scopeProvider = var1;
   }

   public void setVisible(boolean var1) {
      super.setVisible(var1);
      if (var1) {
         this.console.consoleTextArea.requestFocus();
         this.context.split.setDividerLocation(0.5);

         try {
            this.console.setMaximum(true);
            this.console.setSelected(true);
            this.console.show();
            this.console.consoleTextArea.requestFocus();
         } catch (Exception var2) {
         }
      }

   }

   boolean shouldDispatchTo(Component var1) {
      Component var2 = SwingUtilities.getRoot(var1);
      if (var2 == this) {
         return true;
      } else {
         Enumeration var3 = this.toplevels.keys();

         while(var3.hasMoreElements()) {
            Object var4 = var3.nextElement();
            JFrame var5 = (JFrame)this.toplevels.get(var4);
            if (var2 == var5) {
               return true;
            }
         }

         return false;
      }
   }

   boolean stringIsCompilableUnit(String var1) {
      Context var2 = Context.enter();
      boolean var3 = var2.stringIsCompilableUnit(var1);
      Context.exit();
      return var3;
   }

   static void swingInvoke(Runnable var0) {
      if (EventQueue.isDispatchThread()) {
         var0.run();
      } else {
         try {
            SwingUtilities.invokeAndWait(var0);
         } catch (Exception var2) {
            var2.printStackTrace();
         }

      }
   }

   static void swingInvokeLater(Runnable var0) {
      try {
         SwingUtilities.invokeLater(var0);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   class ThreadState {
      private int stopAtFrameDepth = -1;
   }

   class SourceEntry {
      StringBuffer source;
      DebuggableScript fnOrScript;

      SourceEntry(StringBuffer var2, DebuggableScript var3) {
         this.source = var2;
         this.fnOrScript = var3;
      }
   }
}
