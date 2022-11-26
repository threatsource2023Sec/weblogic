package utils.applet.archiver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;

public class Parser implements Runnable {
   public static final String DONE = "done";
   int c;
   ActionListener listener;
   Vector v = new Vector();
   InputStream in;
   Hashtable atts = new Hashtable();
   boolean inAppletTag = false;
   URL u;
   Exception failure;
   boolean canceled = false;

   void p(String s) {
      System.out.println(s);
   }

   public Exception getException() {
      return this.failure;
   }

   public Vector getTags() {
      return this.v;
   }

   public Parser(URL u, ActionListener l) {
      this.listener = l;
      this.u = u;
   }

   public Parser(InputStream i, ActionListener l) {
      this.listener = l;
      this.in = i;
   }

   public void cancel() {
      this.canceled = true;
   }

   public void run() {
      String arg = "done";
      String auth = AppletArchiver.getInstance().getAuthenticationInfo(false);

      try {
         while(this.in == null) {
            HttpURLConnection conn = null;

            try {
               conn = (HttpURLConnection)this.u.openConnection();
               conn.setRequestProperty("User-Agent", "Mozilla/404");
               if (auth != null) {
                  conn.setRequestProperty("Authorization", "Basic " + auth);
               }

               this.in = conn.getInputStream();
            } catch (Exception var9) {
               if (conn == null || conn.getResponseCode() != 401) {
                  throw var9;
               }

               AppletArchiver.getInstance().setAuthenticationInfo((String)null);
               auth = AppletArchiver.getInstance().getAuthenticationInfo(true);
               if (auth == null) {
                  throw new ProtocolException("HTTP Authorization failed");
               }
            }
         }

         this.parse();
         if (this.v.size() == 0) {
            throw new IOException("No applet tags found in that page");
         }
      } catch (Exception var10) {
         this.failure = var10;
         arg = this.failure.toString();
      } finally {
         if (!this.canceled && this.listener != null) {
            this.listener.actionPerformed(new ActionEvent(this, 0, arg));
         }

      }

   }

   public void parse() throws IOException {
      while((this.c = this.in.read()) != -1) {
         if (this.c == 60) {
            this.c = this.in.read();
            this.skipIfWS();
            if (this.inAppletTag) {
               this.scanParamTag();
            } else if (this.isAppletTag()) {
               this.inAppletTag = true;
               this.c = this.in.read();
               this.scanAppletTag();
            }
         }
      }

   }

   boolean isWS() {
      return this.c == 32 || this.c == 9 || this.c == 10 || this.c == 13;
   }

   void skipIfWS() throws IOException {
      if (this.isWS()) {
         this.skipWS();
      }

   }

   public void skipWS() throws IOException {
      do {
         this.c = this.in.read();
      } while(this.isWS() && this.c != -1);

   }

   boolean isAppletTag() throws IOException {
      if (this.c != 65 && this.c != 97) {
         return false;
      } else {
         this.c = this.in.read();
         if (this.c != 80 && this.c != 112) {
            return false;
         } else {
            this.c = this.in.read();
            if (this.c != 80 && this.c != 112) {
               return false;
            } else {
               this.c = this.in.read();
               if (this.c != 76 && this.c != 108) {
                  return false;
               } else {
                  this.c = this.in.read();
                  if (this.c != 69 && this.c != 101) {
                     return false;
                  } else {
                     this.c = this.in.read();
                     return this.c == 84 || this.c == 116;
                  }
               }
            }
         }
      }
   }

   void scanAppletTag() throws IOException {
      while(true) {
         this.skipIfWS();
         if (this.c != 62) {
            String[] pair = null;
            pair = this.scanParam();
            if (pair != null) {
               this.atts.put(pair[0].toUpperCase(), pair[1]);
            }

            if (this.c != 62) {
               continue;
            }
         }

         return;
      }
   }

   void scanParamTag() throws IOException {
      this.skipIfWS();
      String s = this.scanString().toUpperCase();
      if (s.equals("/APPLET")) {
         this.inAppletTag = false;
         this.v.addElement(this.atts);
         this.atts = new Hashtable();
      } else if (s.equals("PARAM")) {
         this.skipIfWS();
         String[] p = this.scanParam();
         if (p != null && p[0].toUpperCase().equals("NAME") && this.c != 62) {
            String k = p[1];
            this.skipIfWS();
            p = this.scanParam();
            if (p != null && p[0].toUpperCase().equals("VALUE")) {
               this.atts.put(k, p[1]);
            }
         }
      }
   }

   String[] scanParam() throws IOException {
      StringBuffer sb = new StringBuffer();
      String[] ret = new String[]{null, ""};

      do {
         sb.append((char)this.c);
      } while((this.c = this.in.read()) != -1 && this.c != 62 && !this.isWS() && this.c != 61);

      ret[0] = sb.toString();
      this.skipIfWS();
      if (this.c == 62) {
         return sb.length() > 0 ? ret : null;
      } else if (this.c != 61) {
         return ret;
      } else {
         sb.setLength(0);
         this.c = this.in.read();
         this.skipIfWS();
         if (this.c != 62 && this.c != -1) {
            this.skipIfWS();
            if (this.c != 39 && this.c != 34) {
               ret[1] = this.scanString();
            } else {
               int delim = this.c;
               this.c = this.in.read();
               ret[1] = this.scanStringDelim(delim);
            }

            return ret;
         } else {
            return ret;
         }
      }
   }

   String scanString() throws IOException {
      StringBuffer sb = new StringBuffer();

      do {
         sb.append((char)this.c);
      } while((this.c = this.in.read()) != -1 && !this.isWS() && this.c != 62);

      return sb.toString();
   }

   String scanStringDelim(int delim) throws IOException {
      StringBuffer sb = new StringBuffer();
      if (this.c == delim) {
         this.c = this.in.read();
      } else {
         while(true) {
            sb.append((char)this.c);
            this.c = this.in.read();
            if (this.c == -1 || this.c == 62 || this.c == delim) {
               if (this.c == delim) {
                  this.c = this.in.read();
               }
               break;
            }
         }
      }

      String r = sb.toString();
      return r;
   }

   public static void main(String[] a) throws Exception {
      if (a.length == 0) {
         System.err.println("Parser: <file | URL>");
      } else {
         Parser p;
         if (a[0].indexOf("://") == -1) {
            p = new Parser(new FileInputStream(a[0]), (ActionListener)null);
         } else {
            p = new Parser(new URL(a[0]), (ActionListener)null);
         }

         p.run();
         System.out.println("got tags:");

         for(int i = 0; i < p.v.size(); ++i) {
            System.out.println(i + "::" + p.v.elementAt(i));
         }

      }
   }
}
