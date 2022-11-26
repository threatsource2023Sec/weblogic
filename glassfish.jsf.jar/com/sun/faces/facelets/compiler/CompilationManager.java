package com.sun.faces.facelets.compiler;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.facelets.tag.TagAttributesImpl;
import com.sun.faces.facelets.tag.TagLibrary;
import com.sun.faces.util.FacesLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagDecorator;
import javax.faces.view.facelets.TagException;

final class CompilationManager {
   private static final Logger log;
   private final Compiler compiler;
   private final TagLibrary tagLibrary;
   private final TagDecorator tagDecorator;
   private final NamespaceManager namespaceManager;
   private final Stack units;
   private int tagId;
   private boolean finished;
   private final String alias;
   private CompilationMessageHolder messageHolder = null;
   private WebConfiguration config;
   private InterfaceUnit interfaceUnit;

   public CompilationManager(String alias, Compiler compiler) {
      this.alias = alias;
      this.compiler = compiler;
      this.tagDecorator = compiler.createTagDecorator();
      this.tagLibrary = compiler.createTagLibrary(this.getCompilationMessageHolder());
      this.namespaceManager = new NamespaceManager();
      this.tagId = 0;
      this.finished = false;
      this.units = new Stack();
      this.units.push(new CompilationUnit());
      this.config = WebConfiguration.getInstance();
   }

   private InterfaceUnit getInterfaceUnit() {
      return this.interfaceUnit;
   }

   public CompilationMessageHolder getCompilationMessageHolder() {
      if (null == this.messageHolder) {
         this.messageHolder = new CompilationMessageHolderImpl();
      }

      return this.messageHolder;
   }

   public String getAlias() {
      return this.alias;
   }

   public WebConfiguration getWebConfiguration() {
      return this.config;
   }

   public void setCompilationMessageHolder(CompilationMessageHolder messageHolder) {
      this.messageHolder = messageHolder;
   }

   private void setInterfaceUnit(InterfaceUnit interfaceUnit) {
      this.interfaceUnit = interfaceUnit;
   }

   public void writeInstruction(String value) {
      if (!this.finished) {
         if (value.length() != 0) {
            TextUnit unit;
            if (this.currentUnit() instanceof TextUnit) {
               unit = (TextUnit)this.currentUnit();
            } else {
               unit = new TextUnit(this.alias, this.nextTagId());
               this.startUnit(unit);
            }

            unit.writeInstruction(value);
         }
      }
   }

   public void writeText(String value) {
      if (!this.finished) {
         if (value.length() != 0) {
            TextUnit unit;
            if (this.currentUnit() instanceof TextUnit) {
               unit = (TextUnit)this.currentUnit();
            } else {
               unit = new TextUnit(this.alias, this.nextTagId());
               this.startUnit(unit);
            }

            unit.write(value);
         }
      }
   }

   public void writeComment(String text) {
      if (!this.compiler.isTrimmingComments()) {
         if (!this.finished) {
            if (text.length() != 0) {
               TextUnit unit;
               if (this.currentUnit() instanceof TextUnit) {
                  unit = (TextUnit)this.currentUnit();
               } else {
                  unit = new TextUnit(this.alias, this.nextTagId());
                  this.startUnit(unit);
               }

               unit.writeComment(text);
            }
         }
      }
   }

   public void writeWhitespace(String text) {
      if (!this.compiler.isTrimmingWhitespace()) {
         this.writeText(text);
      }

   }

   private String nextTagId() {
      return Integer.toHexString(Math.abs(this.alias.hashCode() ^ 13 * this.tagId++));
   }

   public void pushTag(Tag orig) {
      if (!this.finished) {
         if (log.isLoggable(Level.FINE)) {
            log.fine("Tag Pushed: " + orig);
         }

         Tag t = this.tagDecorator.decorate(orig);
         String[] qname = this.determineQName(t);
         t = this.trimAttributes(t);
         boolean handled = false;
         NamespaceUnit nsUnit;
         if (isTrimmed(qname[0], qname[1])) {
            if (log.isLoggable(Level.FINE)) {
               log.fine("Composition Found, Popping Parent Tags");
            }

            CompilationUnit viewRootUnit = this.getViewRootUnitFromStack(this.units);
            this.units.clear();
            nsUnit = this.namespaceManager.toNamespaceUnit(this.tagLibrary);
            this.units.push(nsUnit);
            if (viewRootUnit != null) {
               viewRootUnit.removeChildren();
               this.currentUnit().addChild(viewRootUnit);
            }

            this.startUnit(new TrimmedTagUnit(this.tagLibrary, qname[0], qname[1], t, this.nextTagId()));
            if (log.isLoggable(Level.FINE)) {
               log.fine("New Namespace and [Trimmed] TagUnit pushed");
            }
         } else {
            InterfaceUnit iface;
            if (isImplementation(qname[0], qname[1])) {
               if (log.isLoggable(Level.FINE)) {
                  log.fine("Composite Component Implementation Found, Popping Parent Tags");
               }

               iface = this.getInterfaceUnit();
               if (null == iface) {
                  throw new TagException(orig, "Unable to find interface for implementation.");
               }

               this.units.clear();
               nsUnit = this.namespaceManager.toNamespaceUnit(this.tagLibrary);
               this.units.push(nsUnit);
               this.currentUnit().addChild(iface);
               this.startUnit(new ImplementationUnit(this.tagLibrary, qname[0], qname[1], t, this.nextTagId()));
               if (log.isLoggable(Level.FINE)) {
                  log.fine("New Namespace and ImplementationUnit pushed");
               }
            } else if (isRemove(qname[0], qname[1])) {
               this.units.push(new RemoveUnit());
            } else if (this.tagLibrary.containsTagHandler(qname[0], qname[1])) {
               if (isInterface(qname[0], qname[1])) {
                  iface = new InterfaceUnit(this.tagLibrary, qname[0], qname[1], t, this.nextTagId());
                  this.setInterfaceUnit(iface);
                  this.startUnit(iface);
               } else {
                  this.startUnit(new TagUnit(this.tagLibrary, qname[0], qname[1], t, this.nextTagId()));
               }
            } else {
               if (this.tagLibrary.containsNamespace(qname[0], t)) {
                  throw new TagException(orig, "Tag Library supports namespace: " + qname[0] + ", but no tag was defined for name: " + qname[1]);
               }

               TextUnit unit;
               if (this.currentUnit() instanceof TextUnit) {
                  unit = (TextUnit)this.currentUnit();
               } else {
                  unit = new TextUnit(this.alias, this.nextTagId());
                  this.startUnit(unit);
               }

               unit.startTag(t);
            }
         }

      }
   }

   public void popTag() {
      if (!this.finished) {
         CompilationUnit unit = this.currentUnit();
         if (unit instanceof TextUnit) {
            TextUnit t = (TextUnit)unit;
            if (!t.isClosed()) {
               t.endTag();
               return;
            }

            this.finishUnit();
         }

         unit = this.currentUnit();
         if (unit instanceof TagUnit) {
            TagUnit t = (TagUnit)unit;
            if (t instanceof TrimmedTagUnit) {
               this.finished = true;
               return;
            }
         }

         this.finishUnit();
      }
   }

   public void popNamespace(String ns) {
      this.namespaceManager.popNamespace(ns);
      if (this.currentUnit() instanceof NamespaceUnit) {
         this.finishUnit();
      }

   }

   public void pushNamespace(String prefix, String uri) {
      if (log.isLoggable(Level.FINE)) {
         log.fine("Namespace Pushed " + prefix + ": " + uri);
      }

      boolean alreadyPresent = this.namespaceManager.getNamespace(prefix) != null;
      this.namespaceManager.pushNamespace(prefix, uri);
      NamespaceUnit unit;
      if (this.currentUnit() instanceof NamespaceUnit && !alreadyPresent) {
         unit = (NamespaceUnit)this.currentUnit();
      } else {
         unit = new NamespaceUnit(this.tagLibrary);
         this.startUnit(unit);
      }

      unit.setNamespace(prefix, uri);
   }

   public FaceletHandler createFaceletHandler() {
      return ((CompilationUnit)this.units.get(0)).createFaceletHandler();
   }

   private CompilationUnit currentUnit() {
      return !this.units.isEmpty() ? (CompilationUnit)this.units.peek() : null;
   }

   private void finishUnit() {
      CompilationUnit unit = (CompilationUnit)this.units.pop();
      unit.finishNotify(this);
      if (log.isLoggable(Level.FINE)) {
         log.fine("Finished Unit: " + unit);
      }

   }

   private void startUnit(CompilationUnit unit) {
      if (log.isLoggable(Level.FINE)) {
         log.fine("Starting Unit: " + unit + " and adding it to parent: " + this.currentUnit());
      }

      this.currentUnit().addChild(unit);
      this.units.push(unit);
      unit.startNotify(this);
   }

   private Tag trimAttributes(Tag tag) {
      Tag t = this.trimJSFCAttribute(tag);
      t = this.trimNSAttributes(t);
      return t;
   }

   protected static boolean isRemove(String ns, String name) {
      return ("http://java.sun.com/jsf/facelets".equals(ns) || "http://xmlns.jcp.org/jsf/facelets".equals(ns)) && "remove".equals(name);
   }

   protected static boolean isTrimmed(String ns, String name) {
      boolean matchInUILibrary = ("http://java.sun.com/jsf/facelets".equals(ns) || "http://xmlns.jcp.org/jsf/facelets".equals(ns)) && ("composition".equals(name) || "component".equals(name));
      return matchInUILibrary;
   }

   protected static boolean isImplementation(String ns, String name) {
      boolean matchInCompositeLibrary = ("http://java.sun.com/jsf/composite".equals(ns) || "http://xmlns.jcp.org/jsf/composite".equals(ns)) && "implementation".equals(name);
      return matchInCompositeLibrary;
   }

   protected static boolean isInterface(String ns, String name) {
      boolean matchInCompositeLibrary = ("http://java.sun.com/jsf/composite".equals(ns) || "http://xmlns.jcp.org/jsf/composite".equals(ns)) && "interface".equals(name);
      return matchInCompositeLibrary;
   }

   private String[] determineQName(Tag tag) {
      TagAttribute attr = tag.getAttributes().get("jsfc");
      if (attr != null) {
         if (log.isLoggable(Level.FINE)) {
            log.fine(attr + " JSF Facelet Compile Directive Found");
         }

         String value = attr.getValue();
         int c = value.indexOf(58);
         String namespace;
         String localName;
         if (c == -1) {
            namespace = this.namespaceManager.getNamespace("");
            localName = value;
         } else {
            String prefix = value.substring(0, c);
            namespace = this.namespaceManager.getNamespace(prefix);
            if (namespace == null) {
               throw new TagAttributeException(tag, attr, "No Namespace matched for: " + prefix);
            }

            localName = value.substring(c + 1);
         }

         return new String[]{namespace, localName};
      } else {
         return new String[]{tag.getNamespace(), tag.getLocalName()};
      }
   }

   private Tag trimJSFCAttribute(Tag tag) {
      TagAttribute attr = tag.getAttributes().get("jsfc");
      if (attr != null) {
         TagAttribute[] oa = tag.getAttributes().getAll();
         TagAttribute[] na = new TagAttribute[oa.length - 1];
         int p = 0;

         for(int i = 0; i < oa.length; ++i) {
            if (!"jsfc".equals(oa[i].getLocalName())) {
               na[p++] = oa[i];
            }
         }

         return new Tag(tag, new TagAttributesImpl(na));
      } else {
         return tag;
      }
   }

   private Tag trimNSAttributes(Tag tag) {
      TagAttribute[] attr = tag.getAttributes().getAll();
      int remove = 0;

      for(int i = 0; i < attr.length; ++i) {
         if (attr[i].getQName().startsWith("xmlns") && this.tagLibrary.containsNamespace(attr[i].getValue(), (Tag)null)) {
            remove |= 1 << i;
            if (log.isLoggable(Level.FINE)) {
               log.fine(attr[i] + " Namespace Bound to TagLibrary");
            }
         }
      }

      if (remove == 0) {
         return tag;
      } else {
         List attrList = new ArrayList(attr.length);
         int p = false;

         for(int i = 0; i < attr.length; ++i) {
            int p = 1 << i;
            if ((p & remove) != p) {
               attrList.add(attr[i]);
            }
         }

         attr = (TagAttribute[])((TagAttribute[])attrList.toArray(new TagAttribute[attrList.size()]));
         return new Tag(tag.getLocation(), tag.getNamespace(), tag.getLocalName(), tag.getQName(), new TagAttributesImpl(attr));
      }
   }

   private CompilationUnit getViewRootUnitFromStack(Stack units) {
      CompilationUnit result = null;
      Iterator iterator = units.iterator();

      while(iterator.hasNext()) {
         CompilationUnit compilationUnit = (CompilationUnit)iterator.next();
         if (compilationUnit instanceof TagUnit) {
            TagUnit tagUnit = (TagUnit)compilationUnit;
            String ns = tagUnit.getTag().getNamespace();
            if ((ns.equals("http://java.sun.com/jsf/core") || ns.equals("http://xmlns.jcp.org/jsf/core")) && tagUnit.getTag().getLocalName().equals("view")) {
               result = tagUnit;
               break;
            }
         }
      }

      return result;
   }

   static {
      log = FacesLogger.FACELETS_COMPILER.getLogger();
   }
}
