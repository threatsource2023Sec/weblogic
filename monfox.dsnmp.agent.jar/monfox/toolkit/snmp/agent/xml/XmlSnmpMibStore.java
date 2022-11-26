package monfox.toolkit.snmp.agent.xml;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import monfox.jdom.DocType;
import monfox.jdom.Document;
import monfox.jdom.Element;
import monfox.jdom.JDOMException;
import monfox.jdom.input.SAXBuilder;
import monfox.jdom.output.XMLOutputter;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlSnmpMibStore {
   private String a = null;
   private SnmpAgent b = null;
   private List c = new Vector();
   private List d = new Vector();
   private List e = new Vector();
   private Logger f = null;

   public XmlSnmpMibStore(SnmpAgent var1, String var2) {
      this.a = var2;
      this.b = var1;
      this.f = Logger.getInstance(a("4\t\\\u001dq\u0001\u0014}'}?\u0010_<z"));
   }

   public void add(String var1) throws SnmpValueException, SnmpMibException {
      SnmpMibNode var2 = this.b.getMib().get(var1);
      if (var2 == null) {
         throw new SnmpValueException(a("\u0002\u000b\u0010=j\u000f\f\u0010 p\b\u0001\u0010i") + var1 + "'");
      } else {
         this.add(var2);
      }
   }

   public void add(SnmpMibNode var1) throws SnmpMibException {
      boolean var2 = XmlSnmpAgentLoader.g;
      if (var1 != null) {
         if (var1 instanceof SnmpMibTable) {
            this.d.add(var1);
            if (!var2) {
               return;
            }
         }

         if (!(var1 instanceof SnmpMibLeaf)) {
            throw new SnmpMibException(a("\u0005\nF/s\u0005\u0000\u0010 p\b\u0001\u0010-s\r\u0017Ct?K") + var1.getClass());
         } else {
            this.e.add(var1);
            if (var2) {
               throw new SnmpMibException(a("\u0005\nF/s\u0005\u0000\u0010 p\b\u0001\u0010-s\r\u0017Ct?K") + var1.getClass());
            }
         }
      }
   }

   public void store() throws IOException, SnmpMibException {
      boolean var5 = XmlSnmpAgentLoader.g;

      try {
         Element var1 = new Element(a("\u0001\rR"));
         this.a(var1);
         Document var2 = new Document((Element)null);
         var2.setDocType(new DocType(a("\u0001\rR"), a("\u0004\u0010D>%CKG9hB\t_ y\u0003\u001c\u001e-p\u0001K]!q\n\u000bHak\u0003\u000b\\%v\u0018KC r\u001cKT:{C\u0017^#o-\u0003U kB\u0000D*")));
         var2.setRootElement(var1);
         XMLOutputter var3 = new XMLOutputter(a("LD\u0010"), true);
         FileOutputStream var4 = new FileOutputStream(this.a);
         var3.output((Document)var2, (OutputStream)var4);
         var4.close();
      } catch (JDOMException var6) {
         throw new SnmpMibException(a("\u0014\t\\nz\u0014\u0007U>k\u0005\u000b^t?K") + var6.getMessage() + "'");
      }

      if (SnmpException.b) {
         XmlSnmpAgentLoader.g = !var5;
      }

   }

   private void a(Element var1) throws JDOMException {
      boolean var5 = XmlSnmpAgentLoader.g;
      ListIterator var2 = this.e.listIterator();

      XmlSnmpMibStore var10000;
      while(true) {
         if (var2.hasNext()) {
            SnmpMibLeaf var3 = (SnmpMibLeaf)var2.next();
            var10000 = this;
            if (var5) {
               break;
            }

            this.a(var1, var3);
            if (!var5) {
               continue;
            }
         }

         var10000 = this;
         break;
      }

      ListIterator var6 = var10000.d.listIterator();

      while(var6.hasNext()) {
         SnmpMibTable var4 = (SnmpMibTable)var6.next();
         this.a(var1, var4);
         if (var5) {
            break;
         }
      }

   }

   private void a(Element var1, SnmpMibLeaf var2) throws JDOMException {
      SnmpValue var3 = var2.getValue();
      Element var4 = new Element(a("\u0000\u0001Q("));
      var4.addAttribute(a("\u0003\rT"), var2.getOid().toString());
      if (var3 != null) {
         var4.addAttribute(a("\u001a\u0005\\;z"), var3.toString());
      }

      var1.addContent(var4);
   }

   private void a(Element var1, SnmpMibTable var2) throws JDOMException {
      boolean var13 = XmlSnmpAgentLoader.g;
      Element var3 = new Element(a("\u0018\u0005R\"z"));
      var3.addAttribute(a("\u0003\rT"), var2.getOid().toString());
      var3.addAttribute(a("\u0002\u0011]\u0001y/\u000b\\;r\u0002\u0017"), "" + var2.getNumOfColumns());
      Iterator var4 = var2.getRows();

      label28:
      while(true) {
         if (!var4.hasNext()) {
            var1.addContent(var3);
            break;
         }

         SnmpMibTableRow var5 = (SnmpMibTableRow)var4.next();
         SnmpOid var6 = var5.getIndex();
         String var7 = var5.getIndex().toNumericString();
         Element var8 = new Element(a("\u001e\u000bG"));
         var8.addAttribute(a("\u0005\nT+g"), var7);
         if (var13) {
            break;
         }

         Iterator var9 = var5.getLeaves();

         while(var9.hasNext()) {
            SnmpMibLeaf var10 = (SnmpMibLeaf)var9.next();
            Element var11 = new Element(a("\u0000\u0001Q("));
            SnmpOid var12 = var10.getOid().getParent(var6.getLength());
            var11.addAttribute(a("\u0003\rT"), var12.toString());
            var11.addAttribute(a("\u001a\u0005\\;z"), var10.getValue().toString());
            var8.addContent(var11);
            if (var13 && var13) {
               continue label28;
            }
         }

         var3.addContent(var8);
         if (var13) {
         }
      }

   }

   public void load() throws IOException, SnmpMibException {
      boolean var9 = XmlSnmpAgentLoader.g;
      FileInputStream var1 = new FileInputStream(this.a);
      SAXBuilder var2 = new SAXBuilder(true);
      var2.setEntityResolver(new Resolver());
      Element var3 = null;

      try {
         Document var4 = var2.build((InputStream)var1);
         var3 = var4.getRootElement();
      } catch (JDOMException var10) {
         throw new SnmpMibException(a("(+}nZ\u0014\u0007U>k\u0005\u000b^t?") + var10.getMessage());
      }

      if (!var3.getName().equals(a("\u0001\rR"))) {
         throw new SnmpMibException(a("\u0005\nF/s\u0005\u0000\u0010<p\u0003\u0010\u0010 p\b\u0001\u0010:f\u001c\u0001\u0010i") + var3.getName() + a("KJ\u0010\u000bg\u001c\u0001S:z\bD\u0017#v\u000eC\u001e"));
      } else {
         List var11 = var3.getChildren(a("\u0000\u0001Q("));
         ListIterator var5 = var11.listIterator();

         Element var10000;
         while(true) {
            if (var5.hasNext()) {
               var10000 = (Element)var5.next();
               if (var9) {
                  break;
               }

               Element var6 = var10000;
               this.b(var6);
               if (!var9) {
                  continue;
               }

               SnmpException.b = !SnmpException.b;
            }

            var10000 = var3;
            break;
         }

         List var12 = var10000.getChildren(a("\u0018\u0005R\"z"));
         ListIterator var7 = var12.listIterator();

         while(var7.hasNext()) {
            Element var8 = (Element)var7.next();
            this.c(var8);
            if (var9) {
               break;
            }
         }

      }
   }

   private void b(Element var1) {
      try {
         String var2 = var1.getAttributeValue(a("\u0003\rT"));
         String var3 = var1.getAttributeValue(a("\u001a\u0005\\;z"));
         SnmpMibLeaf var4 = null;
         SnmpMibNode var5 = this.b.getMib().get(var2);
         if (var5 != null && var5 instanceof SnmpMibLeaf) {
            var4 = (SnmpMibLeaf)var5;
         } else {
            var4 = new SnmpMibLeaf(this.b.getMib().getMetadata(), var2);
            this.b.getMib().add(var4);
         }

         var4.setValue(var3);
      } catch (SnmpValueException var6) {
         this.f.error(a(".\u0005TnS\t\u0005VnG!(\nn8"), var6);
      } catch (SnmpMibException var7) {
         this.f.error(a(".\u0005TnS\t\u0005VnG!(\nn8"), var7);
      }

   }

   private void c(Element var1) {
      boolean var9 = XmlSnmpAgentLoader.g;
      String var2 = var1.getAttributeValue(a("\u0003\rT"));
      String var3 = var1.getAttributeValue(a("\u0002\u0011]\u0001y/\u000b\\;r\u0002\u0017"));

      try {
         SnmpMibTable var4;
         label51: {
            SnmpMibNode var5 = this.b.getMib().get(var2);
            if (var5 == null || !(var5 instanceof SnmpMibTable)) {
               var4 = new SnmpMibTable(this.b.getMib().getMetadata(), var2);
               this.b.getMib().add(var4);

               try {
                  var4.setInitialValuesFromDefVals();
                  break label51;
               } catch (Exception var10) {
                  if (!var9) {
                     break label51;
                  }
               }
            }

            var4 = (SnmpMibTable)var5;
         }

         List var6 = var1.getChildren(a("\u001e\u000bG"));
         Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            Element var8 = (Element)var7.next();
            this.b(var8, var4);
            if (var9 || var9) {
               break;
            }
         }
      } catch (SnmpMibException var11) {
         this.f.debug(a("8\u0005R\"zL)Y,?)\u001cS+o\u0018\r_ %LC") + var2 + "'", var11);
      } catch (SnmpValueException var12) {
         this.f.debug(a("8\u0005R\"zL(_/{L!H-z\u001c\u0010Y!qVD\u0017") + var2 + "'", var12);
      }

   }

   private void b(Element var1, SnmpMibTable var2) throws SnmpMibException, SnmpValueException {
      String var3 = var1.getAttributeValue(a("\u0005\nT+g"));
      SnmpMibTableRow var4 = var2.getRow(var3);
      if (var4 == null) {
         this.f.debug(a("- t\u0007Q+Db\u0001HVD") + var3);
         var4 = var2.addRow(var3);
      }

      List var5 = var1.getChildren(a("\u0000\u0001Q("));
      Iterator var6 = var5.iterator();

      while(var6.hasNext()) {
         Element var7 = (Element)var6.next();
         String var8 = var7.getAttributeValue(a("\u0003\rT"));
         String var9 = var7.getAttributeValue(a("\u001a\u0005\\;z"));
         var4.setLeafValue(var8, var9);
         if (XmlSnmpAgentLoader.g) {
            break;
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
               var10003 = 108;
               break;
            case 1:
               var10003 = 100;
               break;
            case 2:
               var10003 = 48;
               break;
            case 3:
               var10003 = 78;
               break;
            default:
               var10003 = 31;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class Resolver implements EntityResolver {
      public InputSource resolveEntity(String var1, String var2) throws SAXException, IOException {
         URL var3 = new URL(var2);
         if (!a("cKni){R\u007f(<:_v*").equals(var3.getHost())) {
            return null;
         } else {
            String var4 = var3.getFile();
            URL var5 = this.getClass().getResource(var4);
            InputStream var6 = var5.openStream();
            return new InputSource(var6);
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
                  var10003 = 20;
                  break;
               case 1:
                  var10003 = 60;
                  break;
               case 2:
                  var10003 = 25;
                  break;
               case 3:
                  var10003 = 71;
                  break;
               default:
                  var10003 = 68;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
