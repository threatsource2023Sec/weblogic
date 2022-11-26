package com.sun.faces.util;

import com.sun.faces.io.FastStringWriter;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectOne;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public class DebugUtil {
   private static final Logger LOGGER = Logger.getLogger(DebugUtil.class.getPackage().getName());
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
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Unable to write indent", var3);
         }
      }

   }

   private static void assertSerializability(StringBuilder builder, Object toPrint) {
      DebugObjectOutputStream doos = null;

      try {
         OutputStream base = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(base);
         doos = new DebugObjectOutputStream(oos);
         doos.writeObject(toPrint);
      } catch (IOException var7) {
         List pathToBadObject = doos.getStack();
         builder.append("Path to non-Serializable Object: \n");
         Iterator var5 = pathToBadObject.iterator();

         while(var5.hasNext()) {
            Object cur = var5.next();
            builder.append(cur.toString()).append("\n");
         }
      }

   }

   private static void indentPrintln(Logger out, Object toPrint) {
      StringBuilder builder = new StringBuilder();
      String str = null == toPrint ? "null" : toPrint.toString();

      for(int i = 0; i < curDepth; ++i) {
         builder.append("  ");
      }

      builder.append(str + "\n");
      if (!(toPrint instanceof String)) {
         assertSerializability(builder, toPrint);
      }

      out.severe(builder.toString());
   }

   public static String printTree(UIComponent root) {
      Writer writer = new FastStringWriter(1024);
      printTree((UIComponent)root, (Writer)writer);
      return writer.toString();
   }

   public static void printTree(UIComponent root, PrintStream out) {
      try {
         PrintWriter writer = new PrintWriter(new PrintStream(out, true, "UTF-8"));
         printTree((UIComponent)root, (Writer)writer);
         writer.flush();
      } catch (UnsupportedEncodingException var4) {
         System.out.println(var4.getMessage());
      }

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
         Iterator it;
         if (root instanceof UISelectOne) {
            Iterator items = null;

            try {
               items = RenderKitUtils.getSelectItems(FacesContext.getCurrentInstance(), root);
            } catch (Exception var11) {
               indentPrintln(out, " { SelectItem(s) not resolvable at this point in time }");
            }

            if (items != null) {
               indentPrintln(out, " {");

               while(items.hasNext()) {
                  SelectItem curItem = (SelectItem)items.next();
                  indentPrintln(out, "\t value = " + curItem.getValue() + ", label = " + curItem.getLabel() + ", description = " + curItem.getDescription());
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

            it = root.getAttributes().keySet().iterator();
            if (it != null) {
               while(it.hasNext()) {
                  String attrName = (String)it.next();
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
         it = root.getFacets().values().iterator();

         while(it.hasNext()) {
            UIComponent uiComponent = (UIComponent)it.next();
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
         if (duplicateId.equals(root.getClientId())) {
            indentPrintln(out, "+id: " + root.getId() + "  <===============");
         } else {
            indentPrintln(out, "+id: " + root.getId());
         }

         indentPrintln(out, " type: " + root.toString());
         ++curDepth;
         Iterator var3 = root.getFacets().values().iterator();

         UIComponent uiComponent;
         while(var3.hasNext()) {
            uiComponent = (UIComponent)var3.next();
            simplePrintTree(uiComponent, duplicateId, out);
         }

         var3 = root.getChildren().iterator();

         while(var3.hasNext()) {
            uiComponent = (UIComponent)var3.next();
            simplePrintTree(uiComponent, duplicateId, out);
         }

         --curDepth;
      }
   }

   public static void printState(Map state, Logger out) {
      Set entrySet = state.entrySet();
      Iterator var7 = entrySet.iterator();

      while(var7.hasNext()) {
         Map.Entry cur = (Map.Entry)var7.next();
         Object key = cur.getKey();
         Object value = cur.getValue();
         String keyIsSerializable = key instanceof Serializable ? "true" : "+_+_+_+FALSE+_+_+_+_";
         String valueIsSerializable = value instanceof Serializable ? "true" : "+_+_+_+FALSE+_+_+_+_";
         out.severe("key: " + key.toString() + " class:" + key.getClass() + " Serializable: " + keyIsSerializable);
         out.severe("value: " + value.toString() + " class:" + key.getClass() + " Serializable: " + keyIsSerializable);
         if (value instanceof Object[]) {
            printTree((Object[])((Object[])value), out);
         }
      }

   }

   public static void printTree(Object[] root, Writer out) {
      if (null == root) {
         indentPrintln(out, "null");
      } else {
         for(int i = 0; i < root.length; ++i) {
            Object obj = root[i];
            if (null == obj) {
               indentPrintln(out, "null");
            } else if (obj.getClass().isArray()) {
               ++curDepth;
               printTree((Object[])((Object[])obj), out);
               --curDepth;
            } else {
               indentPrintln(out, obj.toString());
            }
         }

      }
   }

   public static void printTree(Object[] root, Logger out) {
      if (null == root) {
         indentPrintln((Logger)out, (Object)"null");
      } else {
         for(int i = 0; i < root.length; ++i) {
            Object obj = root[i];
            if (null == obj) {
               indentPrintln((Logger)out, (Object)"null");
            } else if (obj.getClass().isArray()) {
               ++curDepth;
               printTree((Object[])((Object[])obj), out);
               --curDepth;
            } else if (obj instanceof List) {
               printList((List)obj, out);
            } else {
               indentPrintln(out, obj);
            }
         }

      }
   }

   public static void printList(List list, Logger out) {
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         Object cur = var2.next();
         if (cur instanceof List) {
            ++curDepth;
            printList((List)cur, out);
            --curDepth;
         } else {
            indentPrintln(out, cur);
         }
      }

   }
}
