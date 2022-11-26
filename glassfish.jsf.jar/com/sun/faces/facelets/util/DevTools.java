package com.sun.faces.facelets.util;

import com.sun.faces.util.Util;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.Expression;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

public final class DevTools {
   public static final String Namespace = "http://java.sun.com/mojarra/private/functions";
   public static final String NewNamespace = "http://xmlns.jcp.org/mojarra/private/functions";
   private static final Logger LOGGER = Logger.getLogger(DevTools.class.getPackage().getName());
   private static final String TS = "&lt;";
   private static final String ERROR_TEMPLATE = "META-INF/facelet-dev-error.xml";
   private static String[] ERROR_PARTS;
   private static final String DEBUG_TEMPLATE = "META-INF/facelet-dev-debug.xml";
   private static String[] DEBUG_PARTS;
   private static final String[] IGNORE = new String[]{"parent", "rendererType"};

   private DevTools() {
      throw new IllegalStateException();
   }

   public static void debugHtml(Writer writer, FacesContext faces, Throwable e) throws IOException {
      init();
      Date now = new Date();
      String[] var4 = ERROR_PARTS;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String ERROR_PART = var4[var6];
         if (null != ERROR_PART) {
            switch (ERROR_PART) {
               case "message":
                  writeMessage(writer, e);
                  break;
               case "trace":
                  writeException(writer, e);
                  break;
               case "now":
                  writer.write(DateFormat.getDateTimeInstance().format(now));
                  break;
               case "tree":
                  writeComponent(writer, faces.getViewRoot());
                  break;
               case "vars":
                  writeVariables(writer, faces);
                  break;
               default:
                  writer.write(ERROR_PART);
            }
         }
      }

   }

   public static void writeMessage(Writer writer, Throwable e) throws IOException {
      if (e != null) {
         String msg = e.getMessage();
         if (msg != null) {
            writer.write(msg.replaceAll("<", "&lt;"));
         } else {
            writer.write(e.getClass().getName());
         }
      }

   }

   public static void writeException(Writer writer, Throwable e) throws IOException {
      if (e != null) {
         StringWriter str = new StringWriter(256);
         PrintWriter pstr = new PrintWriter(str);
         e.printStackTrace(pstr);
         pstr.close();
         writer.write(str.toString().replaceAll("<", "&lt;"));
      }

   }

   public static void debugHtml(Writer writer, FacesContext faces) throws IOException {
      init();
      Date now = new Date();
      String[] var3 = DEBUG_PARTS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String DEBUG_PART = var3[var5];
         if (null != DEBUG_PART) {
            switch (DEBUG_PART) {
               case "message":
                  writer.write(faces.getViewRoot().getViewId());
                  break;
               case "now":
                  writer.write(DateFormat.getDateTimeInstance().format(now));
                  break;
               case "tree":
                  writeComponent(writer, faces.getViewRoot());
                  break;
               case "vars":
                  writeVariables(writer, faces);
                  break;
               default:
                  writer.write(DEBUG_PART);
            }
         }
      }

   }

   public static void writeVariables(Writer writer, FacesContext faces) throws IOException {
      ExternalContext ctx = faces.getExternalContext();
      writeVariables(writer, ctx.getRequestParameterMap(), "Request Parameters");
      if (faces.getViewRoot() != null) {
         Map viewMap = faces.getViewRoot().getViewMap(false);
         if (viewMap != null) {
            writeVariables(writer, viewMap, "View Attributes");
         } else {
            writeVariables(writer, Collections.emptyMap(), "View Attributes");
         }
      } else {
         writeVariables(writer, Collections.emptyMap(), "View Attributes");
      }

      writeVariables(writer, ctx.getRequestMap(), "Request Attributes");
      Flash flash = ctx.getFlash();

      try {
         flash = ctx.getFlash();
      } catch (UnsupportedOperationException var5) {
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Flash not supported", var5);
         }
      }

      if (flash != null) {
         writeVariables(writer, flash, "Flash Attributes");
      } else {
         writeVariables(writer, Collections.emptyMap(), "Flash Attributes");
      }

      if (ctx.getSession(false) != null) {
         writeVariables(writer, ctx.getSessionMap(), "Session Attributes");
      } else {
         writeVariables(writer, Collections.emptyMap(), "Session Attributes");
      }

      writeVariables(writer, ctx.getApplicationMap(), "Application Attributes");
   }

   public static void writeComponent(Writer writer, UIComponent c) throws IOException {
      writer.write("<dl style=\"color: #006;\"><dt style=\"border: 1px solid #DDD; padding: 4px; border-left: 2px solid #666; font-family: 'Courier New', Courier, mono; font-size: small;");
      if (c != null && isText(c)) {
         writer.write("color: #999;");
      }

      writer.write("\">");
      if (c != null) {
         boolean hasChildren = c.getChildCount() > 0 || c.getFacets().size() > 0;
         writeStart(writer, c, hasChildren);
         writer.write("</dt>");
         if (hasChildren) {
            Iterator var3;
            if (c.getFacets().size() > 0) {
               var3 = c.getFacets().entrySet().iterator();

               while(var3.hasNext()) {
                  Map.Entry entry = (Map.Entry)var3.next();
                  writer.write("<dd style=\"margin-top: 2px; margin-bottom: 2px;\">");
                  writer.write("<span style=\"font-family: 'Trebuchet MS', Verdana, Arial, Sans-Serif; font-size: small;\">");
                  writer.write((String)entry.getKey());
                  writer.write("</span>");
                  writeComponent(writer, (UIComponent)entry.getValue());
                  writer.write("</dd>");
               }
            }

            if (c.getChildCount() > 0) {
               var3 = c.getChildren().iterator();

               while(var3.hasNext()) {
                  UIComponent child = (UIComponent)var3.next();
                  writer.write("<dd style=\"margin-top: 2px; margin-bottom: 2px;\">");
                  writeComponent(writer, child);
                  writer.write("</dd>");
               }
            }

            writer.write("<dt style=\"border: 1px solid #DDD; padding: 4px; border-left: 2px solid #666; font-family: 'Courier New', Courier, mono; font-size: small;\">");
            writeEnd(writer, c);
            writer.write("</dt>");
         }

         writer.write("</dl>");
      }
   }

   private static void init() throws IOException {
      if (ERROR_PARTS == null) {
         ERROR_PARTS = splitTemplate("META-INF/facelet-dev-error.xml");
      }

      if (DEBUG_PARTS == null) {
         DEBUG_PARTS = splitTemplate("META-INF/facelet-dev-debug.xml");
      }

   }

   private static String[] splitTemplate(String rsc) throws IOException {
      ClassLoader loader = Util.getCurrentLoader(DevTools.class);
      String str = "";
      InputStream is = null;

      try {
         is = loader.getResourceAsStream(rsc);
         if (is == null) {
            loader = DevTools.class.getClassLoader();
            is = loader.getResourceAsStream(rsc);
            if (is == null) {
               throw new FileNotFoundException(rsc);
            }
         }

         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] buff = new byte[512];

         int read;
         while((read = is.read(buff)) != -1) {
            baos.write(buff, 0, read);
         }

         str = baos.toString("UTF-8");
         return str.split("@@");
      } finally {
         if (null != is) {
            is.close();
         }

      }
   }

   private static void writeVariables(Writer writer, Map vars, String caption) throws IOException {
      writer.write("<table style=\"border: 1px solid #CCC; border-collapse: collapse; border-spacing: 0px; width: 100%; text-align: left;\"><caption style=\"text-align: left; padding: 10px 0; font-size: large;\">");
      writer.write(caption);
      writer.write("</caption><thead stype=\"padding: 2px; color: #030; background-color: #F9F9F9;\"><tr style=\"padding: 2px; color: #030; background-color: #F9F9F9;\"><th style=\"padding: 2px; color: #030; background-color: #F9F9F9;width: 10%; \">Name</th><th style=\"padding: 2px; color: #030; background-color: #F9F9F9;width: 90%; \">Value</th></tr></thead><tbody style=\"padding: 10px 6px;\">");
      boolean written = false;
      if (!vars.isEmpty()) {
         SortedMap map = new TreeMap(vars);
         Iterator var5 = map.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            String key = (String)entry.getKey();
            if (key.indexOf(46) == -1) {
               writer.write("<tr style=\"padding: 10px 6px;\"><td style=\"padding: 10px 6px;\">");
               writer.write(key.replaceAll("<", "&lt;"));
               writer.write("</td><td>");
               writer.write(entry.getValue() == null ? "null" : entry.getValue().toString().replaceAll("<", "&lt;"));
               writer.write("</td></tr>");
               written = true;
            }
         }
      }

      if (!written) {
         writer.write("<tr style=\"padding: 10px 6px;\"><td colspan=\"2\" style=\"padding: 10px 6px;\"><em>None</em></td></tr>");
      }

      writer.write("</tbody></table>");
   }

   private static void writeEnd(Writer writer, UIComponent c) throws IOException {
      if (!isText(c)) {
         writer.write("&lt;");
         writer.write(47);
         writer.write(getName(c));
         writer.write(62);
      }

   }

   private static void writeAttributes(Writer writer, UIComponent c) {
      try {
         BeanInfo info = Introspector.getBeanInfo(c.getClass());
         PropertyDescriptor[] pd = info.getPropertyDescriptors();
         PropertyDescriptor[] var4 = pd;
         int var5 = pd.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor aPd = var4[var6];
            if (aPd.getWriteMethod() != null && Arrays.binarySearch(IGNORE, aPd.getName()) < 0) {
               Method m = aPd.getReadMethod();

               try {
                  Object v = m.invoke(c);
                  if (v != null && !(v instanceof Collection) && !(v instanceof Map) && !(v instanceof Iterator)) {
                     writer.write(" ");
                     writer.write(aPd.getName());
                     writer.write("=\"");
                     String str;
                     if (v instanceof Expression) {
                        str = ((Expression)v).getExpressionString();
                     } else if (v instanceof ValueBinding) {
                        str = ((ValueBinding)v).getExpressionString();
                     } else if (v instanceof MethodBinding) {
                        str = ((MethodBinding)v).getExpressionString();
                     } else {
                        str = v.toString();
                     }

                     writer.write(str.replaceAll("<", "&lt;"));
                     writer.write("\"");
                  }
               } catch (IllegalArgumentException | InvocationTargetException | IOException | IllegalAccessException var11) {
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, "Error writing out attribute", var11);
                  }
               } catch (Exception var12) {
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.log(Level.FINEST, "Error writing out attribute", var12);
                  }
               }
            }
         }

         ValueBinding binding = c.getValueBinding("binding");
         if (binding != null) {
            writer.write(" binding=\"");
            writer.write(binding.getExpressionString().replaceAll("<", "&lt;"));
            writer.write("\"");
         }
      } catch (IOException | IntrospectionException var13) {
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Error writing out attributes", var13);
         }
      }

   }

   private static void writeStart(Writer writer, UIComponent c, boolean children) throws IOException {
      if (isText(c)) {
         String str = c.toString().trim();
         writer.write(str.replaceAll("<", "&lt;"));
      } else {
         writer.write("&lt;");
         writer.write(getName(c));
         writeAttributes(writer, c);
         if (children) {
            writer.write(62);
         } else {
            writer.write("/>");
         }
      }

   }

   private static String getName(UIComponent c) {
      String nm = c.getClass().getName();
      return nm.substring(nm.lastIndexOf(46) + 1);
   }

   private static boolean isText(UIComponent c) {
      return c.getClass().getName().startsWith("com.sun.faces.facelets.compiler");
   }
}
