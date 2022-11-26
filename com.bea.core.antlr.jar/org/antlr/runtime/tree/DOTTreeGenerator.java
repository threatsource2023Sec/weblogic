package org.antlr.runtime.tree;

import java.util.HashMap;
import org.antlr.stringtemplate.StringTemplate;

public class DOTTreeGenerator {
   public static StringTemplate _treeST = new StringTemplate("digraph {\n\n\tordering=out;\n\tranksep=.4;\n\tbgcolor=\"lightgrey\"; node [shape=box, fixedsize=false, fontsize=12, fontname=\"Helvetica-bold\", fontcolor=\"blue\"\n\t\twidth=.25, height=.25, color=\"black\", fillcolor=\"white\", style=\"filled, solid, bold\"];\n\tedge [arrowsize=.5, color=\"black\", style=\"bold\"]\n\n  $nodes$\n  $edges$\n}\n");
   public static StringTemplate _nodeST = new StringTemplate("$name$ [label=\"$text$\"];\n");
   public static StringTemplate _edgeST = new StringTemplate("$parent$ -> $child$ // \"$parentText$\" -> \"$childText$\"\n");
   HashMap nodeToNumberMap = new HashMap();
   int nodeNumber = 0;

   public StringTemplate toDOT(Object tree, TreeAdaptor adaptor, StringTemplate _treeST, StringTemplate _edgeST) {
      StringTemplate treeST = _treeST.getInstanceOf();
      this.nodeNumber = 0;
      this.toDOTDefineNodes(tree, adaptor, treeST);
      this.nodeNumber = 0;
      this.toDOTDefineEdges(tree, adaptor, treeST);
      return treeST;
   }

   public StringTemplate toDOT(Object tree, TreeAdaptor adaptor) {
      return this.toDOT(tree, adaptor, _treeST, _edgeST);
   }

   public StringTemplate toDOT(Tree tree) {
      return this.toDOT(tree, new CommonTreeAdaptor());
   }

   protected void toDOTDefineNodes(Object tree, TreeAdaptor adaptor, StringTemplate treeST) {
      if (tree != null) {
         int n = adaptor.getChildCount(tree);
         if (n != 0) {
            StringTemplate parentNodeST = this.getNodeST(adaptor, tree);
            treeST.setAttribute("nodes", (Object)parentNodeST);

            for(int i = 0; i < n; ++i) {
               Object child = adaptor.getChild(tree, i);
               StringTemplate nodeST = this.getNodeST(adaptor, child);
               treeST.setAttribute("nodes", (Object)nodeST);
               this.toDOTDefineNodes(child, adaptor, treeST);
            }

         }
      }
   }

   protected void toDOTDefineEdges(Object tree, TreeAdaptor adaptor, StringTemplate treeST) {
      if (tree != null) {
         int n = adaptor.getChildCount(tree);
         if (n != 0) {
            String parentName = "n" + this.getNodeNumber(tree);
            String parentText = adaptor.getText(tree);

            for(int i = 0; i < n; ++i) {
               Object child = adaptor.getChild(tree, i);
               String childText = adaptor.getText(child);
               String childName = "n" + this.getNodeNumber(child);
               StringTemplate edgeST = _edgeST.getInstanceOf();
               edgeST.setAttribute("parent", (Object)parentName);
               edgeST.setAttribute("child", (Object)childName);
               edgeST.setAttribute("parentText", (Object)this.fixString(parentText));
               edgeST.setAttribute("childText", (Object)this.fixString(childText));
               treeST.setAttribute("edges", (Object)edgeST);
               this.toDOTDefineEdges(child, adaptor, treeST);
            }

         }
      }
   }

   protected StringTemplate getNodeST(TreeAdaptor adaptor, Object t) {
      String text = adaptor.getText(t);
      StringTemplate nodeST = _nodeST.getInstanceOf();
      String uniqueName = "n" + this.getNodeNumber(t);
      nodeST.setAttribute("name", (Object)uniqueName);
      nodeST.setAttribute("text", (Object)this.fixString(text));
      return nodeST;
   }

   protected int getNodeNumber(Object t) {
      Integer nI = (Integer)this.nodeToNumberMap.get(t);
      if (nI != null) {
         return nI;
      } else {
         this.nodeToNumberMap.put(t, this.nodeNumber);
         ++this.nodeNumber;
         return this.nodeNumber - 1;
      }
   }

   protected String fixString(String in) {
      String text = in;
      if (in != null) {
         text = in.replaceAll("\"", "\\\\\"");
         text = text.replaceAll("\\t", "    ");
         text = text.replaceAll("\\n", "\\\\n");
         text = text.replaceAll("\\r", "\\\\r");
         if (text.length() > 20) {
            text = text.substring(0, 8) + "..." + text.substring(text.length() - 8);
         }
      }

      return text;
   }
}
