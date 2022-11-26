package weblogicx.jsp.tags;

import java.util.Enumeration;
import javax.servlet.ServletRequest;
import weblogic.utils.UnsyncHashtable;

public class XParams {
   private boolean tracing;
   UnsyncHashtable params;
   private String beanid;
   private String idNoDot;
   int len;

   static void p(String s) {
      System.err.println("[XParams]: " + s);
   }

   public void setTracing(boolean t) {
      this.tracing = t;
   }

   private String matchID(String n) {
      if (n.startsWith(this.beanid)) {
         return n.substring(this.len);
      } else if (!n.startsWith(this.idNoDot)) {
         return null;
      } else {
         int idlen = this.beanid.length();
         if (n.length() > idlen && n.charAt(idlen - 1) == '[') {
            int ind = n.indexOf(46);
            return ind >= 0 && ind != n.length() - 1 ? n.substring(ind + 1) : null;
         } else {
            return null;
         }
      }
   }

   public XParams(String beanid, UnsyncHashtable uh) {
      this.tracing = true;
      this.params = new UnsyncHashtable();
      this.beanid = beanid;
      this.params = (UnsyncHashtable)((UnsyncHashtable)uh.clone());
   }

   private XParams(String beanid, _ParamHelper p, Enumeration e) {
      this.tracing = true;
      this.params = new UnsyncHashtable();
      if (beanid.length() == 0) {
         throw new IllegalArgumentException("beanid cannot be empty string");
      } else {
         if (!beanid.endsWith(".")) {
            beanid = beanid + ".";
         }

         this.beanid = beanid;
         this.len = beanid.length();
         this.idNoDot = beanid.substring(0, this.len - 1);

         while(e.hasMoreElements()) {
            String name = (String)e.nextElement();
            String subname = this.matchID(name);
            if (subname == null) {
               if (this.tracing) {
                  p("discarding param '" + name + "' for beanid='" + beanid + "'");
               }
            } else {
               subname = subname.toLowerCase();
               String val = p.getParameter(name);
               this.params.put(subname, val);
            }
         }

      }
   }

   public String getBeanId() {
      return this.beanid;
   }

   public XParams(String beanid, ServletRequest rq) {
      this(beanid, new _ParamHelper(rq, (XParams)null), rq.getParameterNames());
   }

   public XParams(String beanid, XParams parent) {
      this(beanid, new _ParamHelper((ServletRequest)null, parent), parent.getNames());
   }

   public String get(String s) {
      return (String)this.params.get(s);
   }

   public Enumeration getNames() {
      return this.params.keys();
   }

   public String toString() {
      return "[XParams: beanid=" + this.getBeanId() + " params=" + this.params + "]";
   }
}
