package org.python.antlr.runtime.tree;

import org.python.antlr.runtime.CommonToken;
import org.python.antlr.runtime.Token;

public class TreePatternParser {
   protected TreePatternLexer tokenizer;
   protected int ttype;
   protected TreeWizard wizard;
   protected TreeAdaptor adaptor;

   public TreePatternParser(TreePatternLexer tokenizer, TreeWizard wizard, TreeAdaptor adaptor) {
      this.tokenizer = tokenizer;
      this.wizard = wizard;
      this.adaptor = adaptor;
      this.ttype = tokenizer.nextToken();
   }

   public Object pattern() {
      if (this.ttype == 1) {
         return this.parseTree();
      } else if (this.ttype == 3) {
         Object node = this.parseNode();
         return this.ttype == -1 ? node : null;
      } else {
         return null;
      }
   }

   public Object parseTree() {
      if (this.ttype != 1) {
         System.out.println("no BEGIN");
         return null;
      } else {
         this.ttype = this.tokenizer.nextToken();
         Object root = this.parseNode();
         if (root == null) {
            return null;
         } else {
            while(this.ttype == 1 || this.ttype == 3 || this.ttype == 5 || this.ttype == 7) {
               Object child;
               if (this.ttype == 1) {
                  child = this.parseTree();
                  this.adaptor.addChild(root, child);
               } else {
                  child = this.parseNode();
                  if (child == null) {
                     return null;
                  }

                  this.adaptor.addChild(root, child);
               }
            }

            if (this.ttype != 2) {
               System.out.println("no END");
               return null;
            } else {
               this.ttype = this.tokenizer.nextToken();
               return root;
            }
         }
      }
   }

   public Object parseNode() {
      String label = null;
      if (this.ttype == 5) {
         this.ttype = this.tokenizer.nextToken();
         if (this.ttype != 3) {
            return null;
         }

         label = this.tokenizer.sval.toString();
         this.ttype = this.tokenizer.nextToken();
         if (this.ttype != 6) {
            return null;
         }

         this.ttype = this.tokenizer.nextToken();
      }

      if (this.ttype == 7) {
         this.ttype = this.tokenizer.nextToken();
         Token wildcardPayload = new CommonToken(0, ".");
         TreeWizard.TreePattern node = new TreeWizard.WildcardTreePattern(wildcardPayload);
         if (label != null) {
            node.label = label;
         }

         return node;
      } else if (this.ttype != 3) {
         return null;
      } else {
         String tokenName = this.tokenizer.sval.toString();
         this.ttype = this.tokenizer.nextToken();
         if (tokenName.equals("nil")) {
            return this.adaptor.nil();
         } else {
            String text = tokenName;
            String arg = null;
            if (this.ttype == 4) {
               arg = this.tokenizer.sval.toString();
               text = arg;
               this.ttype = this.tokenizer.nextToken();
            }

            int treeNodeType = this.wizard.getTokenType(tokenName);
            if (treeNodeType == 0) {
               return null;
            } else {
               Object node = this.adaptor.create(treeNodeType, text);
               if (label != null && node.getClass() == TreeWizard.TreePattern.class) {
                  ((TreeWizard.TreePattern)node).label = label;
               }

               if (arg != null && node.getClass() == TreeWizard.TreePattern.class) {
                  ((TreeWizard.TreePattern)node).hasTextArg = true;
               }

               return node;
            }
         }
      }
   }
}
