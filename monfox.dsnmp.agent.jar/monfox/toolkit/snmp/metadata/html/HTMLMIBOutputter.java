package monfox.toolkit.snmp.metadata.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import monfox.toolkit.snmp.metadata.gen.MIBOutputter;
import monfox.toolkit.snmp.metadata.gen.MIBOutputterException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HTMLMIBOutputter implements MIBOutputter {
   private Hashtable a = new Hashtable();
   private List b = new Vector();
   private List c = new Vector();
   private String d = b("q7UOb1\"\u0015Uk16QHpq)TLtq7_Ue:;N@+6.WM");
   private String e = b("-4WQ*=)I");
   private File f = null;
   // $FF: synthetic field
   static Class g;
   public static boolean h;

   public HTMLMIBOutputter() {
      this.f = new File(".");
   }

   public void setDirectory(File var1) {
      this.f = var1;
   }

   public void preProcess() throws MIBOutputterException {
      boolean var6 = h;

      try {
         String var1 = this.d + "/" + this.e;
         File var2 = new File(this.f, this.e);
         FileOutputStream var3 = new FileOutputStream(var2);
         InputStream var4 = (g == null ? (g = a(b("35TGk&tNNk21SU*-4WQ*3?N@`?.[\u000fl*7V\u000fL\n\u0017vlM\u001c\u0015OUt+.NDv"))) : g).getResourceAsStream(var1);
         boolean var5 = true;

         while(true) {
            int var8;
            if ((var8 = var4.read()) >= 0) {
               var3.write(var8);
               if (var6) {
                  break;
               }

               if (!var6) {
                  continue;
               }
            }

            var4.close();
            var3.close();
            break;
         }

      } catch (IOException var7) {
         throw new MIBOutputterException(b("\u001d;TOk*zYNt'zyrW~<SMadz") + var7.getMessage());
      }
   }

   public void output(Document var1, String var2) throws MIBOutputterException {
      try {
         this.a(var1);
         TransformerFactory var3 = TransformerFactory.newInstance();
         InputStream var4 = (g == null ? (g = a(b("35TGk&tNNk21SU*-4WQ*3?N@`?.[\u000fl*7V\u000fL\n\u0017vlM\u001c\u0015OUt+.NDv"))) : g).getResourceAsStream(this.d + b("q)TLt\u0013?N@`?.[\u000f|-6"));
         Templates var5 = var3.newTemplates(new StreamSource(var4));
         Transformer var6 = var5.newTransformer();
         this.a(var1, var6, var2);
      } catch (MIBOutputterException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new MIBOutputterException(var8.getMessage());
      }
   }

   private void a(Document var1, Transformer var2, String var3) throws MIBOutputterException {
      try {
         File var4 = new File(this.f, var3);
         FileOutputStream var5 = new FileOutputStream(var4);
         StreamResult var6 = new StreamResult(var5);
         var2.transform(new DOMSource(var1), var6);
      } catch (Exception var7) {
         throw new MIBOutputterException(var7.getMessage());
      }
   }

   private void a(Document var1, String var2, String var3) throws MIBOutputterException {
      try {
         TransformerFactory var4 = TransformerFactory.newInstance();
         InputStream var5 = (g == null ? (g = a(b("35TGk&tNNk21SU*-4WQ*3?N@`?.[\u000fl*7V\u000fL\n\u0017vlM\u001c\u0015OUt+.NDv"))) : g).getResourceAsStream(this.d + "/" + var2);
         Templates var6 = var4.newTemplates(new StreamSource(var5));
         Transformer var7 = var6.newTransformer();
         this.a(var1, var7, var3);
      } catch (MIBOutputterException var8) {
         throw var8;
      } catch (Exception var9) {
         var9.printStackTrace();
         throw new MIBOutputterException(var9.getMessage());
      }
   }

   public void postProcess() throws MIBOutputterException {
      this.postProcessIndex();
      this.postProcessByOid();
      this.postProcessByName();
      this.postProcessByForm();
   }

   public void postProcessIndex() throws MIBOutputterException {
      boolean var10 = h;

      try {
         Vector var1 = new Vector();
         var1.addAll(this.a.keySet());
         Collections.sort(var1);
         DocumentBuilderFactory var2 = DocumentBuilderFactory.newInstance();
         var2.setValidating(true);
         DocumentBuilder var3 = var2.newDocumentBuilder();
         Document var4 = var3.newDocument();
         Element var5 = var4.createElement(b("\u00135^Th;\u0016SRp"));
         var4.appendChild(var5);
         ListIterator var6 = var1.listIterator();

         while(true) {
            if (var6.hasNext()) {
               String var7 = (String)var6.next();
               String var8 = (String)this.a.get(var7);
               if (var10) {
                  break;
               }

               if (var8 != null) {
                  Element var9 = var4.createElement(b("\u00135^Th;\b_G"));
                  var9.setAttribute(b("0;WD"), var7);
                  var5.appendChild(var9);
               }

               if (!var10) {
                  continue;
               }
            }

            this.a(var4, b("-4WQI;.[Ee*;sO`;\"\u0014Yw2"), b("74^D|p2NLh"));
            break;
         }

      } catch (Exception var11) {
         var11.printStackTrace();
         throw new MIBOutputterException(var11.getMessage());
      }
   }

   public void postProcessByOid() throws MIBOutputterException {
      Vector var1 = new Vector();
      var1.addAll(this.b);
      Collections.sort(var1, new Comparator() {
         public int compare(Object var1, Object var2) {
            OInfo var3 = (OInfo)var1;
            OInfo var4 = (OInfo)var2;
            return var3.compareToByOid(var4);
         }

         public boolean equals(Object var1) {
            return this.compare(this, var1) == 0;
         }
      });
      this.a(var1, b("-4WQI;.[Ee*;xXK7>\u0014Yw2"), b("74^D|s8C\fk7>\u0014Ip36"));
   }

   public void postProcessByName() throws MIBOutputterException {
      Vector var1 = new Vector();
      var1.addAll(this.b);
      Collections.sort(var1, new Comparator() {
         public int compare(Object var1, Object var2) {
            OInfo var3 = (OInfo)var1;
            OInfo var4 = (OInfo)var2;
            return var3.compareToByName(var4);
         }

         public boolean equals(Object var1) {
            return this.compare(this, var1) == 0;
         }
      });
      this.a(var1, b("-4WQI;.[Ee*;xXJ?7_\u000f|-6"), b("74^D|s8C\fj?7_\u000fl*7V"));
   }

   public void postProcessByForm() throws MIBOutputterException {
      Vector var1 = new Vector();
      var1.addAll(this.b);
      Collections.sort(var1, new Comparator() {
         public int compare(Object var1, Object var2) {
            OInfo var3 = (OInfo)var1;
            OInfo var4 = (OInfo)var2;
            return var3.compareToByForm(var4);
         }

         public boolean equals(Object var1) {
            return this.compare(this, var1) == 0;
         }
      });
      this.a(var1, b("-4WQI;.[Ee*;xXB1(W\u000f|-6"), b("74^D|s8C\fg14IUv+9N\u000fl*7V"));
   }

   private void a(Vector var1, String var2, String var3) throws MIBOutputterException {
      boolean var12 = h;

      try {
         DocumentBuilderFactory var4 = DocumentBuilderFactory.newInstance();
         var4.setValidating(true);
         DocumentBuilder var5 = var4.newDocumentBuilder();
         Document var6 = var5.newDocument();
         Element var7 = var6.createElement(b("\u00113^mm-."));
         var6.appendChild(var7);
         Object var8 = null;
         ListIterator var9 = var1.listIterator();

         while(true) {
            if (var9.hasNext()) {
               OInfo var10 = (OInfo)var9.next();
               if (var12 || var12) {
                  break;
               }

               if (var8 != null && var8.equals(var10) && !var12) {
                  continue;
               }

               Element var11 = var6.createElement(b("\u00113^sa8"));
               var11.setAttribute(b("0;WD"), var10.name);
               var11.setAttribute(b("35^Th;"), var10.module);
               var11.setAttribute(b("13^"), var10.oid);
               var11.setAttribute(b("85HL"), var10.form);
               if (var10.smitype != null) {
                  var11.setAttribute(b("-7SU}.?"), var10.smitype);
               }

               if (var10.access != null) {
                  var11.setAttribute(b("?9YDw-"), var10.access);
               }

               if (var10.status != null) {
                  var11.setAttribute(b("-.[Uq-"), var10.status);
               }

               var7.appendChild(var11);
               if (!var12) {
                  continue;
               }
            }

            this.a(var6, var2, var3);
            break;
         }

      } catch (Exception var13) {
         var13.printStackTrace();
         throw new MIBOutputterException(var13.getMessage());
      }
   }

   private void a(Document var1) {
      boolean var11 = h;
      Element var2 = var1.getDocumentElement();
      String var3 = var2.getAttribute(b("0;WD"));
      if (var3 != null) {
         this.a.put(var3, var3);
      }

      NodeList var4 = var2.getChildNodes();
      int var5 = 0;

      while(var5 < var4.getLength()) {
         Node var6 = var4.item(var5);
         if (var6 instanceof Element) {
            label36: {
               Element var7 = (Element)var6;
               String var8 = var7.getAttribute(b("13^"));
               String var9 = var7.getAttribute(b("0;WD"));
               if (var8 != null && var8.trim().length() > 0) {
                  String var10 = var7.getAttribute(b("85HL"));
                  if (var10 == null || var10.trim().length() == 0) {
                     var10 = var7.getNodeName().toLowerCase();
                  }

                  this.b.add(new OInfo(var8, var9, var3, var10, var7.getAttribute(b("-7SU}.?")), var7.getAttribute(b("?9YDw-")), var7.getAttribute(b("-.[Uq-"))));
                  if (!var11) {
                     break label36;
                  }
               }

               if (var7.getNodeName().equals(b("\n#JD")) && var9 != null) {
                  this.c.add(new TInfo(var9, var3, var7.getAttribute(b("-7SU}.?"))));
               }
            }
         }

         ++var5;
         if (var11) {
            break;
         }
      }

   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 94;
               break;
            case 1:
               var10003 = 90;
               break;
            case 2:
               var10003 = 58;
               break;
            case 3:
               var10003 = 33;
               break;
            default:
               var10003 = 4;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class OInfo {
      public long[] loid;
      public String name;
      public String oid;
      public String module;
      public String form;
      public String smitype;
      public String access;
      public String status;
      public boolean _isObject;

      OInfo(String var2, String var3, String var4, String var5, String var6, String var7, String var8) {
         boolean var12 = HTMLMIBOutputter.h;
         super();
         this._isObject = false;

         label38: {
            try {
               StringTokenizer var9 = new StringTokenizer(var2, a("+m"), false);
               int var10 = var9.countTokens();
               this.loid = new long[var10];
               int var11 = 0;

               while(var9.hasMoreTokens()) {
                  this.loid[var11++] = Long.parseLong(var9.nextToken());
                  if (var12) {
                     break label38;
                  }

                  if (var12) {
                     break;
                  }
               }
            } catch (Exception var13) {
            }

            this.oid = var2;
            this.name = var3;
            this.module = var4;
            this.form = var5;
            this.smitype = var6;
            this.access = var7;
            this.status = var8;
         }

         this._isObject = var5.equalsIgnoreCase(a("e\"\u0004{`")) || var5.equalsIgnoreCase(a("t-\u0012e|")) || var5.equalsIgnoreCase(a("b \u0007{dc")) || var5.equalsIgnoreCase(a("r,\nbh\u007f"));
      }

      public int compareToByOid(OInfo var1) {
         boolean var4 = HTMLMIBOutputter.h;
         int var2 = this.loid.length > var1.loid.length ? var1.loid.length : this.loid.length;
         int var3 = 0;

         int var10000;
         while(true) {
            if (var3 < var2) {
               long var5;
               var10000 = (var5 = this.loid[var3] - var1.loid[var3]) == 0L ? 0 : (var5 < 0L ? -1 : 1);
               if (var4) {
                  break;
               }

               if (var10000 > 0) {
                  return 1;
               }

               if (this.loid[var3] < var1.loid[var3]) {
                  return -1;
               }

               ++var3;
               if (!var4) {
                  continue;
               }
            }

            var10000 = this.loid.length;
            break;
         }

         if (var10000 > var1.loid.length) {
            return 1;
         } else {
            return this.name.equals(var1.name) ? this.module.compareTo(var1.module) : this.name.compareTo(var1.name);
         }
      }

      public int compareToByName(OInfo var1) {
         boolean var4 = HTMLMIBOutputter.h;
         if (!this.name.equals(var1.name)) {
            return this.name.compareTo(var1.name);
         } else if (!this.module.equals(var1.module)) {
            return this.module.compareTo(var1.module);
         } else {
            int var2 = this.loid.length > var1.loid.length ? var1.loid.length : this.loid.length;
            int var3 = 0;

            int var10000;
            while(true) {
               if (var3 < var2) {
                  long var5;
                  var10000 = (var5 = this.loid[var3] - var1.loid[var3]) == 0L ? 0 : (var5 < 0L ? -1 : 1);
                  if (var4) {
                     break;
                  }

                  if (var10000 > 0) {
                     return 1;
                  }

                  if (this.loid[var3] < var1.loid[var3]) {
                     return -1;
                  }

                  ++var3;
                  if (!var4) {
                     continue;
                  }
               }

               var10000 = this.loid.length;
               break;
            }

            return var10000 > var1.loid.length ? 1 : 0;
         }
      }

      boolean a() {
         return this._isObject;
      }

      public int compareForms(OInfo var1) {
         if (this.form.equals(var1.form)) {
            return 0;
         } else if (this.a() && var1.a()) {
            return 0;
         } else if (this.a()) {
            return a("~!\frfe").compareTo(var1.form);
         } else {
            return var1.a() ? this.form.compareTo(a("~!\frfe")) : this.form.compareTo(var1.form);
         }
      }

      public int compareToByForm(OInfo var1) {
         boolean var5 = HTMLMIBOutputter.h;
         int var2 = this.compareForms(var1);
         if (var2 != 0) {
            return var2;
         } else {
            int var3 = this.loid.length > var1.loid.length ? var1.loid.length : this.loid.length;
            int var4 = 0;

            int var10000;
            while(true) {
               if (var4 < var3) {
                  long var6;
                  var10000 = (var6 = this.loid[var4] - var1.loid[var4]) == 0L ? 0 : (var6 < 0L ? -1 : 1);
                  if (var5) {
                     break;
                  }

                  if (var10000 > 0) {
                     return 1;
                  }

                  if (this.loid[var4] < var1.loid[var4]) {
                     return -1;
                  }

                  ++var4;
                  if (!var5) {
                     continue;
                  }
               }

               var10000 = this.loid.length;
               break;
            }

            if (var10000 > var1.loid.length) {
               return 1;
            } else if (this.loid.length < var1.loid.length) {
               return -1;
            } else if (!this.module.equals(var1.module)) {
               return this.module.compareTo(var1.module);
            } else {
               return !this.name.equals(var1.name) ? this.name.compareTo(var1.name) : 0;
            }
         }
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 17;
                  break;
               case 1:
                  var10003 = 67;
                  break;
               case 2:
                  var10003 = 102;
                  break;
               case 3:
                  var10003 = 23;
                  break;
               default:
                  var10003 = 5;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class TInfo {
      public String name;
      public String module;
      public String smitype;

      TInfo(String var2, String var3, String var4) {
         this.name = var2;
         this.module = var3;
         this.smitype = var4;
      }
   }
}
