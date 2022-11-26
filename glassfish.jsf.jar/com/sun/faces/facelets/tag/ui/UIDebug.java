package com.sun.faces.facelets.tag.ui;

import com.sun.faces.facelets.util.DevTools;
import com.sun.faces.facelets.util.FastWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletResponse;

public final class UIDebug extends UIComponentBase {
   public static final String COMPONENT_TYPE = "facelets.ui.Debug";
   public static final String COMPONENT_FAMILY = "facelets";
   private static long nextId = System.currentTimeMillis();
   private static final String KEY = "facelets.ui.DebugOutput";
   public static final String DEFAULT_HOTKEY = "D";
   private String hotkey = "D";

   public UIDebug() {
      this.setTransient(true);
      this.setRendererType((String)null);
   }

   public String getFamily() {
      return "facelets";
   }

   public List getChildren() {
      return new ArrayList() {
         private static final long serialVersionUID = 2156130539926052013L;

         public boolean add(Object o) {
            throw new IllegalStateException("<ui:debug> does not support children");
         }

         public void add(int index, Object o) {
            throw new IllegalStateException("<ui:debug> does not support children");
         }
      };
   }

   public void encodeBegin(FacesContext facesContext) throws IOException {
      if (this.isRendered()) {
         this.pushComponentToEL(facesContext, this);
         String actionId = facesContext.getApplication().getViewHandler().getActionURL(facesContext, facesContext.getViewRoot().getViewId());
         StringBuffer sb = new StringBuffer(512);
         sb.append("//<![CDATA[\n");
         sb.append("function faceletsDebug(URL) {");
         sb.append("day = new Date();");
         sb.append("id = day.getTime();");
         sb.append("eval(\"page\" + id + \" = window.open(URL, '\" + id + \"', 'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=800,height=600,left = 240,top = 212');\"); };");
         sb.append("(function() { if (typeof jsfFaceletsDebug === 'undefined') { var jsfFaceletsDebug = false; } if (!jsfFaceletsDebug) {");
         sb.append("var faceletsOrigKeyup = document.onkeyup;");
         sb.append("document.onkeyup = function(e) { if (window.event) e = window.event; if (String.fromCharCode(e.keyCode) == '" + this.getHotkey() + "' & e.shiftKey & e.ctrlKey) faceletsDebug('");
         sb.append(actionId);
         sb.append((char)(actionId.indexOf(63) == -1 ? '?' : '&'));
         sb.append("facelets.ui.DebugOutput");
         sb.append('=');
         sb.append(writeDebugOutput(facesContext));
         sb.append("'); jsfFaceletsDebug = true; if (faceletsOrigKeyup) faceletsOrigKeyup(e); };\n");
         sb.append("}})();");
         sb.append("//]]>\n");
         ResponseWriter writer = facesContext.getResponseWriter();
         writer.startElement("span", this);
         writer.writeAttribute("id", this.getClientId(facesContext), "id");
         writer.startElement("script", this);
         writer.writeAttribute("language", "javascript", "language");
         writer.writeAttribute("type", "text/javascript", "type");
         writer.writeText(sb.toString(), this, (String)null);
         writer.endElement("script");
         writer.endElement("span");
      }

   }

   private static String writeDebugOutput(FacesContext faces) throws IOException {
      FastWriter fw = new FastWriter();
      DevTools.debugHtml(fw, faces);
      Map session = faces.getExternalContext().getSessionMap();
      Map debugs = (Map)session.get("facelets.ui.DebugOutput");
      if (debugs == null) {
         debugs = new LinkedHashMap() {
            private static final long serialVersionUID = 2541609242499547693L;

            protected boolean removeEldestEntry(Map.Entry eldest) {
               return this.size() > 5;
            }
         };
      }

      session.put("facelets.ui.DebugOutput", debugs);
      String id = "" + nextId++;
      ((Map)debugs).put(id, fw.toString());
      return id;
   }

   private static String fetchDebugOutput(FacesContext faces, String id) {
      Map session = faces.getExternalContext().getSessionMap();
      Map debugs = (Map)session.get("facelets.ui.DebugOutput");
      return debugs != null ? (String)debugs.get(id) : null;
   }

   public static boolean debugRequest(FacesContext faces) {
      String id = (String)faces.getExternalContext().getRequestParameterMap().get("facelets.ui.DebugOutput");
      if (id != null) {
         Object resp = faces.getExternalContext().getResponse();
         if (!faces.getResponseComplete() && resp instanceof HttpServletResponse) {
            try {
               HttpServletResponse httpResp = (HttpServletResponse)resp;
               String page = fetchDebugOutput(faces, id);
               if (page != null) {
                  httpResp.setContentType("text/html");
                  httpResp.getWriter().write(page);
               } else {
                  httpResp.setContentType("text/plain");
                  httpResp.getWriter().write("No Debug Output Available");
               }

               httpResp.flushBuffer();
               faces.responseComplete();
               return true;
            } catch (IOException var5) {
               return false;
            }
         }
      }

      return false;
   }

   public String getHotkey() {
      return this.hotkey;
   }

   public void setHotkey(String hotkey) {
      this.hotkey = hotkey != null ? hotkey.toUpperCase() : "";
   }
}
