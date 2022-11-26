package com.sun.faces.util;

import com.sun.faces.io.FastStringWriter;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public class DebugUtil {
   private static boolean keepWaiting = true;
   private static int curDepth = 0;

   public DebugUtil() {
      this.init();
   }

   protected void init() {
   }

   public static void setKeepWaiting(boolean keepWaiting) {
      DebugUtil.keepWaiting = keepWaiting;
   }

   public static void waitForDebugger() {
      while(keepWaiting) {
         try {
            Thread.sleep(5000L);
         } catch (InterruptedException var1) {
            System.out.println("DebugUtil.waitForDebugger(): Exception: " + var1.getMessage());
         }
      }

   }

   private static void indentPrintln(Writer out, String str) {
      try {
         for(int i = 0; i < curDepth; ++i) {
            out.write("  ");
         }

         out.write(str + "\n");
      } catch (IOException var3) {
      }

   }

   public static String printTree(UIComponent root) {
      Writer writer = new FastStringWriter(1024);
      printTree((UIComponent)root, (Writer)writer);
      return writer.toString();
   }

   public static void printTree(UIComponent root, PrintStream out) {
      PrintWriter writer = new PrintWriter(out);
      printTree((UIComponent)root, (Writer)writer);
      writer.flush();
   }

   public static void printTree(UIComponent root, Logger logger, Level level) {
      StringWriter sw = new StringWriter();
      printTree((UIComponent)root, (Writer)sw);
      logger.log(level, sw.toString());
   }

   public static void printTree(UIComponent root, Writer out) {
      if (null != root) {
         Object value = null;
         indentPrintln(out, "id:" + root.getId());
         indentPrintln(out, "type:" + root.getClass().getName());
         Iterator i$;
         if (root instanceof UISelectOne) {
            List items = null;

            try {
               items = RenderKitUtils.getSelectItems(FacesContext.getCurrentInstance(), root);
            } catch (Exception var11) {
               indentPrintln(out, " { SelectItem(s) not resolvable at this point in time }");
            }

            if (items != null) {
               indentPrintln(out, " {");
               if (!items.isEmpty()) {
                  i$ = items.iterator();

                  while(i$.hasNext()) {
                     SelectItem curItem = (SelectItem)i$.next();
                     indentPrintln(out, "\t value = " + curItem.getValue() + ", label = " + curItem.getLabel() + ", description = " + curItem.getDescription());
                  }
               }

               indentPrintln(out, " }");
            }
         } else {
            ValueExpression ve = null;
            if (root instanceof ValueHolder) {
               ve = root.getValueExpression("value");

               try {
                  value = ((ValueHolder)root).getValue();
               } catch (Exception var10) {
                  value = "UNAVAILABLE";
               }
            }

            if (ve != null) {
               indentPrintln(out, "expression/value = " + ve.getExpressionString() + " : " + value);
            } else {
               indentPrintln(out, "value = " + value);
            }

            i$ = root.getAttributes().keySet().iterator();
            if (i$ != null) {
               while(i$.hasNext()) {
                  String attrName = (String)i$.next();
                  ve = root.getValueExpression(attrName);
                  String expr = null;
                  if (ve != null) {
                     expr = ve.getExpressionString();
                  }

                  String val;
                  try {
                     val = root.getAttributes().get(attrName).toString();
                  } catch (Exception var9) {
                     val = "UNAVAILABLE";
                  }

                  if (expr != null) {
                     indentPrintln(out, "attr = " + attrName + " : [" + expr + " : " + val + " ]");
                  } else {
                     indentPrintln(out, "attr = " + attrName + " : " + val);
                  }
               }
            }
         }

         ++curDepth;
         Iterator it = root.getChildren().iterator();
         i$ = root.getFacets().values().iterator();

         while(i$.hasNext()) {
            UIComponent uiComponent = (UIComponent)i$.next();
            printTree(uiComponent, out);
         }

         while(it.hasNext()) {
            printTree((UIComponent)it.next(), out);
         }

         --curDepth;
      }
   }

   public static void simplePrintTree(UIComponent root, String duplicateId, Writer out) {
      if (null != root) {
         if (duplicateId.equals(root.getId())) {
            indentPrintln(out, "+id: " + root.getId() + "  <===============");
         } else {
            indentPrintln(out, "+id: " + root.getId());
         }

         indentPrintln(out, " type: " + root.toString());
         ++curDepth;
         Iterator i$ = root.getFacets().values().iterator();

         UIComponent uiComponent;
         while(i$.hasNext()) {
            uiComponent = (UIComponent)i$.next();
            simplePrintTree(uiComponent, duplicateId, out);
         }

         i$ = root.getChildren().iterator();

         while(i$.hasNext()) {
            uiComponent = (UIComponent)i$.next();
            simplePrintTree(uiComponent, duplicateId, out);
         }

         --curDepth;
      }
   }

   public static void printTree(Object[] root, Writer out) {
      if (null == root) {
         indentPrintln(out, "null");
      } else {
         boolean foundBottom = false;
         Object[] myState = root;

         while(!foundBottom) {
            Object state = myState[0];
            foundBottom = !state.getClass().isArray();
            if (!foundBottom) {
               myState = (Object[])((Object[])state);
            }
         }

         indentPrintln(out, "type:" + myState[8]);
         ++curDepth;
         root = (Object[])((Object[])root[1]);

         for(int i = 0; i < root.length; ++i) {
            printTree((Object[])((Object[])root[i]), out);
         }

         --curDepth;
      }
   }
}
