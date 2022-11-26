package weblogic.application.archive.collage.parser;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;
import org.xml.sax.Attributes;

public class Mapping extends PatternSet {
   String uri;
   String path;
   boolean isAntStyle;

   public Mapping(Collage parentCollage, Attributes attributes) {
      super(parentCollage, attributes);
      this.isAntStyle = Collage.getStyle(attributes.getValue("style"), parentCollage.isAntStyle);
   }

   public FileFilter getFileFilter() {
      return this.isAntStyle ? new PatternFilter(this.patterns) {
         public boolean accept(File f) {
            if (super.accept(f)) {
               return true;
            } else {
               boolean isIncluded = false;
               String path = f.getPath();
               Iterator var4 = this.patterns.iterator();

               while(var4.hasNext()) {
                  Pattern p = (Pattern)var4.next();
                  if (AntMatcher.match(p.pattern, path, false)) {
                     if (!p.isInclude) {
                        return false;
                     }

                     isIncluded = true;
                  }
               }

               return isIncluded;
            }
         }
      } : new PatternFilter(this.patterns) {
         public boolean accept(File f) {
            if (super.accept(f)) {
               return true;
            } else {
               String path = f.getPath();
               Iterator var3 = this.patterns.iterator();

               Pattern p;
               do {
                  if (!var3.hasNext()) {
                     return true;
                  }

                  p = (Pattern)var3.next();
               } while(!AntMatcher.match(p.pattern, path, false));

               return p.isInclude;
            }
         }
      };
   }

   public String getPath() {
      return this.path;
   }

   public String getUri() {
      return this.uri;
   }

   protected void setPath(String path) {
      this.path = path;
   }

   protected void setUri(String uri) {
      this.uri = uri;
   }

   abstract class PatternFilter implements FileFilter {
      protected LinkedList patterns;

      public PatternFilter(LinkedList patterns) {
         this.patterns = patterns;
      }

      public boolean accept(File f) {
         return this.patterns == null || this.patterns.size() == 0;
      }
   }
}
