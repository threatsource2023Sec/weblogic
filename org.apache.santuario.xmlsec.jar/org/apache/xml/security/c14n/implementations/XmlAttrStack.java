package org.apache.xml.security.c14n.implementations;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

class XmlAttrStack {
   private static final Logger LOG = LoggerFactory.getLogger(XmlAttrStack.class);
   private int currentLevel = 0;
   private int lastlevel = 0;
   private XmlsStackElement cur;
   private List levels = new ArrayList();
   private boolean c14n11;

   public XmlAttrStack(boolean c14n11) {
      this.c14n11 = c14n11;
   }

   void push(int level) {
      this.currentLevel = level;
      if (this.currentLevel != -1) {
         int newSize;
         for(this.cur = null; this.lastlevel >= this.currentLevel; this.lastlevel = ((XmlsStackElement)this.levels.get(newSize - 1)).level) {
            this.levels.remove(this.levels.size() - 1);
            newSize = this.levels.size();
            if (newSize == 0) {
               this.lastlevel = 0;
               return;
            }
         }

      }
   }

   void addXmlnsAttr(Attr n) {
      if (this.cur == null) {
         this.cur = new XmlsStackElement();
         this.cur.level = this.currentLevel;
         this.levels.add(this.cur);
         this.lastlevel = this.currentLevel;
      }

      this.cur.nodes.add(n);
   }

   void getXmlnsAttr(Collection col) {
      int size = this.levels.size() - 1;
      if (this.cur == null) {
         this.cur = new XmlsStackElement();
         this.cur.level = this.currentLevel;
         this.lastlevel = this.currentLevel;
         this.levels.add(this.cur);
      }

      boolean parentRendered = false;
      XmlsStackElement e = null;
      if (size == -1) {
         parentRendered = true;
      } else {
         e = (XmlsStackElement)this.levels.get(size);
         if (e.rendered && e.level + 1 == this.currentLevel) {
            parentRendered = true;
         }
      }

      if (parentRendered) {
         col.addAll(this.cur.nodes);
         this.cur.rendered = true;
      } else {
         Map loa = new HashMap();
         if (this.c14n11) {
            List baseAttrs = new ArrayList();

            Iterator it;
            label100:
            for(boolean successiveOmitted = true; size >= 0; --size) {
               e = (XmlsStackElement)this.levels.get(size);
               if (e.rendered) {
                  successiveOmitted = false;
               }

               it = e.nodes.iterator();

               while(true) {
                  while(true) {
                     if (!it.hasNext() || !successiveOmitted) {
                        continue label100;
                     }

                     Attr n = (Attr)it.next();
                     if (n.getLocalName().equals("base") && !e.rendered) {
                        baseAttrs.add(n);
                     } else if (!loa.containsKey(n.getName())) {
                        loa.put(n.getName(), n);
                     }
                  }
               }
            }

            if (!baseAttrs.isEmpty()) {
               it = col.iterator();
               String base = null;
               Attr baseAttr = null;

               Attr n;
               while(it.hasNext()) {
                  n = (Attr)it.next();
                  if (n.getLocalName().equals("base")) {
                     base = n.getValue();
                     baseAttr = n;
                     break;
                  }
               }

               it = baseAttrs.iterator();

               while(it.hasNext()) {
                  n = (Attr)it.next();
                  if (base == null) {
                     base = n.getValue();
                     baseAttr = n;
                  } else {
                     try {
                        base = joinURI(n.getValue(), base);
                     } catch (URISyntaxException var13) {
                        LOG.debug(var13.getMessage(), var13);
                     }
                  }
               }

               if (base != null && base.length() != 0) {
                  baseAttr.setValue(base);
                  col.add(baseAttr);
               }
            }
         } else {
            while(size >= 0) {
               e = (XmlsStackElement)this.levels.get(size);
               Iterator it = e.nodes.iterator();

               while(it.hasNext()) {
                  Attr n = (Attr)it.next();
                  if (!loa.containsKey(n.getName())) {
                     loa.put(n.getName(), n);
                  }
               }

               --size;
            }
         }

         this.cur.rendered = true;
         col.addAll(loa.values());
      }
   }

   private static String joinURI(String baseURI, String relativeURI) throws URISyntaxException {
      String bscheme = null;
      String bauthority = null;
      String bpath = "";
      String bquery = null;
      URI base;
      if (baseURI != null) {
         if (baseURI.endsWith("..")) {
            baseURI = baseURI + "/";
         }

         base = new URI(baseURI);
         bscheme = base.getScheme();
         bauthority = base.getAuthority();
         bpath = base.getPath();
         bquery = base.getQuery();
      }

      base = new URI(relativeURI);
      String rscheme = base.getScheme();
      String rauthority = base.getAuthority();
      String rpath = base.getPath();
      String rquery = base.getQuery();
      if (rscheme != null && rscheme.equals(bscheme)) {
         rscheme = null;
      }

      String tscheme;
      String tauthority;
      String tpath;
      String tquery;
      if (rscheme != null) {
         tscheme = rscheme;
         tauthority = rauthority;
         tpath = removeDotSegments(rpath);
         tquery = rquery;
      } else {
         if (rauthority != null) {
            tauthority = rauthority;
            tpath = removeDotSegments(rpath);
            tquery = rquery;
         } else {
            if (rpath.length() == 0) {
               tpath = bpath;
               if (rquery != null) {
                  tquery = rquery;
               } else {
                  tquery = bquery;
               }
            } else {
               if (rpath.startsWith("/")) {
                  tpath = removeDotSegments(rpath);
               } else {
                  if (bauthority != null && bpath.length() == 0) {
                     tpath = "/" + rpath;
                  } else {
                     int last = bpath.lastIndexOf(47);
                     if (last == -1) {
                        tpath = rpath;
                     } else {
                        tpath = bpath.substring(0, last + 1) + rpath;
                     }
                  }

                  tpath = removeDotSegments(tpath);
               }

               tquery = rquery;
            }

            tauthority = bauthority;
         }

         tscheme = bscheme;
      }

      return (new URI(tscheme, tauthority, tpath, tquery, (String)null)).toString();
   }

   private static String removeDotSegments(String path) {
      LOG.debug("STEP OUTPUT BUFFER\t\tINPUT BUFFER");

      String input;
      for(input = path; input.indexOf("//") > -1; input = input.replaceAll("//", "/")) {
      }

      StringBuilder output = new StringBuilder();
      if (input.charAt(0) == '/') {
         output.append("/");
         input = input.substring(1);
      }

      printStep("1 ", output.toString(), input);

      while(input.length() != 0) {
         if (input.startsWith("./")) {
            input = input.substring(2);
            printStep("2A", output.toString(), input);
         } else if (input.startsWith("../")) {
            input = input.substring(3);
            if (!output.toString().equals("/")) {
               output.append("../");
            }

            printStep("2A", output.toString(), input);
         } else if (input.startsWith("/./")) {
            input = input.substring(2);
            printStep("2B", output.toString(), input);
         } else if (input.equals("/.")) {
            input = input.replaceFirst("/.", "/");
            printStep("2B", output.toString(), input);
         } else {
            int end;
            if (input.startsWith("/../")) {
               input = input.substring(3);
               if (output.length() == 0) {
                  output.append("/");
               } else if (output.toString().endsWith("../")) {
                  output.append("..");
               } else if (output.toString().endsWith("..")) {
                  output.append("/..");
               } else {
                  end = output.lastIndexOf("/");
                  if (end == -1) {
                     output = new StringBuilder();
                     if (input.charAt(0) == '/') {
                        input = input.substring(1);
                     }
                  } else {
                     output = output.delete(end, output.length());
                  }
               }

               printStep("2C", output.toString(), input);
            } else if (input.equals("/..")) {
               input = input.replaceFirst("/..", "/");
               if (output.length() == 0) {
                  output.append("/");
               } else if (output.toString().endsWith("../")) {
                  output.append("..");
               } else if (output.toString().endsWith("..")) {
                  output.append("/..");
               } else {
                  end = output.lastIndexOf("/");
                  if (end == -1) {
                     output = new StringBuilder();
                     if (input.charAt(0) == '/') {
                        input = input.substring(1);
                     }
                  } else {
                     output = output.delete(end, output.length());
                  }
               }

               printStep("2C", output.toString(), input);
            } else if (input.equals(".")) {
               input = "";
               printStep("2D", output.toString(), input);
            } else if (input.equals("..")) {
               if (!output.toString().equals("/")) {
                  output.append("..");
               }

               input = "";
               printStep("2D", output.toString(), input);
            } else {
               int end = true;
               int begin = input.indexOf(47);
               if (begin == 0) {
                  end = input.indexOf(47, 1);
               } else {
                  end = begin;
                  begin = 0;
               }

               String segment;
               if (end == -1) {
                  segment = input.substring(begin);
                  input = "";
               } else {
                  segment = input.substring(begin, end);
                  input = input.substring(end);
               }

               output.append(segment);
               printStep("2E", output.toString(), input);
            }
         }
      }

      if (output.toString().endsWith("..")) {
         output.append("/");
         printStep("3 ", output.toString(), input);
      }

      return output.toString();
   }

   private static void printStep(String step, String output, String input) {
      if (LOG.isDebugEnabled()) {
         LOG.debug(" " + step + ":   " + output);
         if (output.length() == 0) {
            LOG.debug("\t\t\t\t" + input);
         } else {
            LOG.debug("\t\t\t" + input);
         }
      }

   }

   static class XmlsStackElement {
      int level;
      boolean rendered = false;
      List nodes = new ArrayList();
   }
}
