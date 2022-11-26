package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

import com.oracle.wls.shaded.org.apache.bcel.generic.CompoundInstruction;
import com.oracle.wls.shaded.org.apache.bcel.generic.ConstantPoolGen;
import com.oracle.wls.shaded.org.apache.bcel.generic.InstructionList;
import com.oracle.wls.shaded.org.apache.bcel.generic.PUSH;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ClassGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.ErrorMsg;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.MethodGenerator;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Type;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.TypeCheckError;
import com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler.util.Util;
import com.oracle.wls.shaded.org.apache.xml.serializer.ElemDesc;
import com.oracle.wls.shaded.org.apache.xml.serializer.ToHTMLStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

final class LiteralElement extends Instruction {
   private String _name;
   private LiteralElement _literalElemParent = null;
   private Vector _attributeElements = null;
   private Hashtable _accessedPrefixes = null;
   private boolean _allAttributesUnique = false;
   private static final String XMLNS_STRING = "xmlns";

   public QName getName() {
      return this._qname;
   }

   public void display(int indent) {
      this.indent(indent);
      Util.println("LiteralElement name = " + this._name);
      this.displayContents(indent + 4);
   }

   private String accessedNamespace(String prefix) {
      if (this._literalElemParent != null) {
         String result = this._literalElemParent.accessedNamespace(prefix);
         if (result != null) {
            return result;
         }
      }

      return this._accessedPrefixes != null ? (String)this._accessedPrefixes.get(prefix) : null;
   }

   public void registerNamespace(String prefix, String uri, SymbolTable stable, boolean declared) {
      String old;
      if (this._literalElemParent != null) {
         old = this._literalElemParent.accessedNamespace(prefix);
         if (old != null && old.equals(uri)) {
            return;
         }
      }

      if (this._accessedPrefixes == null) {
         this._accessedPrefixes = new Hashtable();
      } else if (!declared) {
         old = (String)this._accessedPrefixes.get(prefix);
         if (old != null) {
            if (old.equals(uri)) {
               return;
            }

            prefix = stable.generateNamespacePrefix();
         }
      }

      if (!prefix.equals("xml")) {
         this._accessedPrefixes.put(prefix, uri);
      }

   }

   private String translateQName(QName qname, SymbolTable stable) {
      String localname = qname.getLocalPart();
      String prefix = qname.getPrefix();
      if (prefix == null) {
         prefix = "";
      } else if (prefix.equals("xmlns")) {
         return "xmlns";
      }

      String alternative = stable.lookupPrefixAlias(prefix);
      if (alternative != null) {
         stable.excludeNamespaces(prefix);
         prefix = alternative;
      }

      String uri = this.lookupNamespace(prefix);
      if (uri == null) {
         return localname;
      } else {
         this.registerNamespace(prefix, uri, stable, false);
         return prefix != "" ? prefix + ":" + localname : localname;
      }
   }

   public void addAttribute(SyntaxTreeNode attribute) {
      if (this._attributeElements == null) {
         this._attributeElements = new Vector(2);
      }

      this._attributeElements.add(attribute);
   }

   public void setFirstAttribute(SyntaxTreeNode attribute) {
      if (this._attributeElements == null) {
         this._attributeElements = new Vector(2);
      }

      this._attributeElements.insertElementAt(attribute, 0);
   }

   public Type typeCheck(SymbolTable stable) throws TypeCheckError {
      if (this._attributeElements != null) {
         int count = this._attributeElements.size();

         for(int i = 0; i < count; ++i) {
            SyntaxTreeNode node = (SyntaxTreeNode)this._attributeElements.elementAt(i);
            node.typeCheck(stable);
         }
      }

      this.typeCheckContents(stable);
      return Type.Void;
   }

   public Enumeration getNamespaceScope(SyntaxTreeNode node) {
      Hashtable all;
      for(all = new Hashtable(); node != null; node = node.getParent()) {
         Hashtable mapping = node.getPrefixMapping();
         if (mapping != null) {
            Enumeration prefixes = mapping.keys();

            while(prefixes.hasMoreElements()) {
               String prefix = (String)prefixes.nextElement();
               if (!all.containsKey(prefix)) {
                  all.put(prefix, mapping.get(prefix));
               }
            }
         }
      }

      return all.keys();
   }

   public void parseContents(Parser parser) {
      SymbolTable stable = parser.getSymbolTable();
      stable.setCurrentNode(this);
      SyntaxTreeNode parent = this.getParent();
      if (parent != null && parent instanceof LiteralElement) {
         this._literalElemParent = (LiteralElement)parent;
      }

      this._name = this.translateQName(this._qname, stable);
      int count = this._attributes.getLength();

      String uri;
      String val;
      for(int i = 0; i < count; ++i) {
         QName qname = parser.getQName(this._attributes.getQName(i));
         uri = qname.getNamespace();
         val = this._attributes.getValue(i);
         if (qname.equals(parser.getUseAttributeSets())) {
            if (!Util.isValidQNames(val)) {
               ErrorMsg err = new ErrorMsg("INVALID_QNAME_ERR", val, this);
               parser.reportError(3, err);
            }

            this.setFirstAttribute(new UseAttributeSets(val, parser));
         } else if (qname.equals(parser.getExtensionElementPrefixes())) {
            stable.excludeNamespaces(val);
         } else if (qname.equals(parser.getExcludeResultPrefixes())) {
            stable.excludeNamespaces(val);
         } else {
            String prefix = qname.getPrefix();
            if ((prefix == null || !prefix.equals("xmlns")) && (prefix != null || !qname.getLocalPart().equals("xmlns")) && (uri == null || !uri.equals("http://www.w3.org/1999/XSL/Transform"))) {
               String name = this.translateQName(qname, stable);
               LiteralAttribute attr = new LiteralAttribute(name, val, parser, this);
               this.addAttribute(attr);
               attr.setParent(this);
               attr.parseContents(parser);
            }
         }
      }

      Enumeration include = this.getNamespaceScope(this);

      while(include.hasMoreElements()) {
         String prefix = (String)include.nextElement();
         if (!prefix.equals("xml")) {
            uri = this.lookupNamespace(prefix);
            if (uri != null && !stable.isExcludedNamespace(uri)) {
               this.registerNamespace(prefix, uri, stable, true);
            }
         }
      }

      this.parseChildren(parser);

      for(int i = 0; i < count; ++i) {
         QName qname = parser.getQName(this._attributes.getQName(i));
         val = this._attributes.getValue(i);
         if (qname.equals(parser.getExtensionElementPrefixes())) {
            stable.unExcludeNamespaces(val);
         } else if (qname.equals(parser.getExcludeResultPrefixes())) {
            stable.unExcludeNamespaces(val);
         }
      }

   }

   protected boolean contextDependent() {
      return this.dependentContents();
   }

   public void translate(ClassGenerator classGen, MethodGenerator methodGen) {
      ConstantPoolGen cpg = classGen.getConstantPool();
      InstructionList il = methodGen.getInstructionList();
      this._allAttributesUnique = this.checkAttributesUnique();
      il.append(methodGen.loadHandler());
      il.append((CompoundInstruction)(new PUSH(cpg, this._name)));
      il.append((com.oracle.wls.shaded.org.apache.bcel.generic.Instruction)DUP2);
      il.append(methodGen.startElement());

      for(int j = 0; j < this.elementCount(); ++j) {
         SyntaxTreeNode item = (SyntaxTreeNode)this.elementAt(j);
         if (item instanceof Variable) {
            item.translate(classGen, methodGen);
         }
      }

      if (this._accessedPrefixes != null) {
         boolean declaresDefaultNS = false;
         Enumeration e = this._accessedPrefixes.keys();

         label54:
         while(true) {
            String prefix;
            String uri;
            do {
               if (!e.hasMoreElements()) {
                  if (!declaresDefaultNS && this._parent instanceof XslElement && ((XslElement)this._parent).declaresDefaultNS()) {
                     il.append(methodGen.loadHandler());
                     il.append((CompoundInstruction)(new PUSH(cpg, "")));
                     il.append((CompoundInstruction)(new PUSH(cpg, "")));
                     il.append(methodGen.namespace());
                  }
                  break label54;
               }

               prefix = (String)e.nextElement();
               uri = (String)this._accessedPrefixes.get(prefix);
            } while(uri == "" && prefix == "");

            if (prefix == "") {
               declaresDefaultNS = true;
            }

            il.append(methodGen.loadHandler());
            il.append((CompoundInstruction)(new PUSH(cpg, prefix)));
            il.append((CompoundInstruction)(new PUSH(cpg, uri)));
            il.append(methodGen.namespace());
         }
      }

      if (this._attributeElements != null) {
         int count = this._attributeElements.size();

         for(int i = 0; i < count; ++i) {
            SyntaxTreeNode node = (SyntaxTreeNode)this._attributeElements.elementAt(i);
            if (!(node instanceof XslAttribute)) {
               node.translate(classGen, methodGen);
            }
         }
      }

      this.translateContents(classGen, methodGen);
      il.append(methodGen.endElement());
   }

   private boolean isHTMLOutput() {
      return this.getStylesheet().getOutputMethod() == 2;
   }

   public ElemDesc getElemDesc() {
      return this.isHTMLOutput() ? ToHTMLStream.getElemDesc(this._name) : null;
   }

   public boolean allAttributesUnique() {
      return this._allAttributesUnique;
   }

   private boolean checkAttributesUnique() {
      boolean hasHiddenXslAttribute = this.canProduceAttributeNodes(this, true);
      if (hasHiddenXslAttribute) {
         return false;
      } else {
         if (this._attributeElements != null) {
            int numAttrs = this._attributeElements.size();
            Hashtable attrsTable = null;

            for(int i = 0; i < numAttrs; ++i) {
               SyntaxTreeNode node = (SyntaxTreeNode)this._attributeElements.elementAt(i);
               if (node instanceof UseAttributeSets) {
                  return false;
               }

               if (node instanceof XslAttribute) {
                  if (attrsTable == null) {
                     attrsTable = new Hashtable();

                     for(int k = 0; k < i; ++k) {
                        SyntaxTreeNode n = (SyntaxTreeNode)this._attributeElements.elementAt(k);
                        if (n instanceof LiteralAttribute) {
                           LiteralAttribute literalAttr = (LiteralAttribute)n;
                           attrsTable.put(literalAttr.getName(), literalAttr);
                        }
                     }
                  }

                  XslAttribute xslAttr = (XslAttribute)node;
                  AttributeValue attrName = xslAttr.getName();
                  if (attrName instanceof AttributeValueTemplate) {
                     return false;
                  }

                  if (attrName instanceof SimpleAttributeValue) {
                     SimpleAttributeValue simpleAttr = (SimpleAttributeValue)attrName;
                     String name = simpleAttr.toString();
                     if (name != null && attrsTable.get(name) != null) {
                        return false;
                     }

                     if (name != null) {
                        attrsTable.put(name, xslAttr);
                     }
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean canProduceAttributeNodes(SyntaxTreeNode node, boolean ignoreXslAttribute) {
      Vector contents = node.getContents();
      int size = contents.size();

      for(int i = 0; i < size; ++i) {
         SyntaxTreeNode child = (SyntaxTreeNode)contents.elementAt(i);
         if (child instanceof Text) {
            Text text = (Text)child;
            if (!text.isIgnore()) {
               return false;
            }
         } else {
            if (!(child instanceof LiteralElement) && !(child instanceof ValueOf) && !(child instanceof XslElement) && !(child instanceof Comment) && !(child instanceof Number) && !(child instanceof ProcessingInstruction)) {
               if (child instanceof XslAttribute) {
                  if (!ignoreXslAttribute) {
                     return true;
                  }
                  continue;
               }

               if (!(child instanceof CallTemplate) && !(child instanceof ApplyTemplates) && !(child instanceof Copy) && !(child instanceof CopyOf)) {
                  if ((child instanceof If || child instanceof ForEach) && this.canProduceAttributeNodes(child, false)) {
                     return true;
                  }

                  if (child instanceof Choose) {
                     Vector chooseContents = child.getContents();
                     int num = chooseContents.size();

                     for(int k = 0; k < num; ++k) {
                        SyntaxTreeNode chooseChild = (SyntaxTreeNode)chooseContents.elementAt(k);
                        if ((chooseChild instanceof When || chooseChild instanceof Otherwise) && this.canProduceAttributeNodes(chooseChild, false)) {
                           return true;
                        }
                     }
                  }
                  continue;
               }

               return true;
            }

            return false;
         }
      }

      return false;
   }
}
