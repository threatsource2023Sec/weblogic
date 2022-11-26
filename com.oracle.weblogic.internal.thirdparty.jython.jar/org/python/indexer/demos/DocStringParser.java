package org.python.indexer.demos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.indexer.Indexer;
import org.python.indexer.NBinding;
import org.python.indexer.Ref;
import org.python.indexer.Scope;
import org.python.indexer.StyleRun;
import org.python.indexer.ast.NStr;

class DocStringParser {
   private static final int MIN_TYPE_NAME_LENGTH = 4;
   private static final String IDENT = "[a-zA-Z_][a-zA-Z0-9_]*";
   private static final Pattern TYPE_NAME = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*\\.[a-zA-Z_][a-zA-Z0-9_]*(?:\\.[a-zA-Z_][a-zA-Z0-9_]*)*\\b|\\b[A-Z][a-zA-Z0-9_]*?[a-z][a-zA-Z0-9_]*\\b|(?<![a-zA-Z0-9_])?__[a-zA-Z][a-zA-Z_]*?__");
   private boolean resolveReferences = true;
   private int docOffset;
   private String docString;
   private NStr docNode;
   private Scope scope;
   private String file;
   private Set offsets = new HashSet();
   private List styles = new ArrayList();
   private Linker linker;

   public DocStringParser(String comment, NStr node, Linker linker) {
      this.docOffset = node.start();
      this.docString = comment;
      this.docNode = node;
      this.scope = node.getEnclosingNamespace();
      this.file = node.getFile();
      this.linker = linker;
   }

   public void setResolveReferences(boolean resolve) {
      this.resolveReferences = resolve;
   }

   public boolean isResolvingReferences() {
      return this.resolveReferences;
   }

   public List highlight() {
      if (this.resolveReferences) {
         this.scanCommentForTypeNames();
      }

      return this.styles;
   }

   private void scanCommentForTypeNames() {
      Matcher m = TYPE_NAME.matcher(this.docString);

      while(m.find()) {
         String qname = m.group();
         int beg = m.start() + this.docOffset;
         if (!this.offsets.contains(beg) && qname.length() >= 4) {
            this.checkForReference(beg, qname);
         }
      }

   }

   private void checkForReference(int offset, String qname) {
      NBinding nb;
      if (qname.indexOf(46) == -1) {
         nb = this.scope.lookup(qname);
         if (nb == null) {
            nb = Indexer.idx.globaltable.lookup(qname);
         }
      } else {
         nb = Indexer.idx.lookupQname(qname);
      }

      if (nb != null) {
         this.linker.processRef(new Ref(this.file, offset, qname), nb);
      }

   }

   private void addStyle(int beg, int len, NBinding nb) {
      this.addStyle(beg, len, StyleRun.Type.TYPE_NAME);
      this.offsets.add(beg);
   }

   private void addStyle(int beg, int len, StyleRun.Type type) {
      this.styles.add(new StyleRun(type, beg, len));
      this.offsets.add(beg);
   }
}
