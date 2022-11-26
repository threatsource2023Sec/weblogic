package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import serp.bytecode.visitor.BCVisitor;

public class Annotations extends Attribute {
   private final List _annotations = new ArrayList();

   Annotations(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   public boolean isRuntime() {
      return this.getName().equals("RuntimeVisibleAnnotations");
   }

   public Annotation[] getAnnotations() {
      return (Annotation[])((Annotation[])this._annotations.toArray(new Annotation[this._annotations.size()]));
   }

   public void setAnnotations(Annotation[] annos) {
      this.clear();
      if (annos != null) {
         for(int i = 0; i < annos.length; ++i) {
            this.addAnnotation(annos[i]);
         }
      }

   }

   public Annotation getAnnotation(Class type) {
      return type == null ? null : this.getAnnotation(type.getName());
   }

   public Annotation getAnnotation(BCClass type) {
      return type == null ? null : this.getAnnotation(type.getName());
   }

   public Annotation getAnnotation(String type) {
      for(int i = 0; i < this._annotations.size(); ++i) {
         Annotation anno = (Annotation)this._annotations.get(i);
         if (anno.getTypeName().equals(type)) {
            return anno;
         }
      }

      return null;
   }

   public Annotation addAnnotation(Annotation an) {
      Annotation anno = this.addAnnotation(an.getTypeName());
      anno.setProperties(an.getProperties());
      return anno;
   }

   public Annotation addAnnotation(Class type) {
      return this.addAnnotation(type.getName());
   }

   public Annotation addAnnotation(BCClass type) {
      return this.addAnnotation(type.getName());
   }

   public Annotation addAnnotation(String type) {
      Annotation anno = new Annotation(this);
      anno.setType(type);
      this._annotations.add(anno);
      return anno;
   }

   public void clear() {
      for(int i = 0; i < this._annotations.size(); ++i) {
         ((Annotation)this._annotations.get(i)).invalidate();
      }

      this._annotations.clear();
   }

   public boolean removeAnnotation(Annotation anno) {
      return anno != null && this.removeAnnotation(anno.getTypeName());
   }

   public boolean removeAnnotation(Class type) {
      return type != null && this.removeAnnotation(type.getName());
   }

   public boolean removeAnnotation(BCClass type) {
      return type != null && this.removeAnnotation(type.getName());
   }

   public boolean removeAnnotation(String type) {
      if (type == null) {
         return false;
      } else {
         for(int i = 0; i < this._annotations.size(); ++i) {
            Annotation anno = (Annotation)this._annotations.get(i);
            if (anno.getTypeName().equals(type)) {
               anno.invalidate();
               this._annotations.remove(i);
               return true;
            }
         }

         return false;
      }
   }

   int getLength() {
      int len = 2;

      for(int i = 0; i < this._annotations.size(); ++i) {
         len += ((Annotation)this._annotations.get(i)).getLength();
      }

      return len;
   }

   void read(Attribute other) {
      this.setAnnotations(((Annotations)other).getAnnotations());
   }

   void read(DataInput in, int length) throws IOException {
      this._annotations.clear();
      int annos = in.readUnsignedShort();

      for(int i = 0; i < annos; ++i) {
         Annotation anno = new Annotation(this);
         anno.read(in);
         this._annotations.add(anno);
      }

   }

   void write(DataOutput out, int length) throws IOException {
      out.writeShort(this._annotations.size());

      for(int i = 0; i < this._annotations.size(); ++i) {
         ((Annotation)this._annotations.get(i)).write(out);
      }

   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterAnnotations(this);

      for(int i = 0; i < this._annotations.size(); ++i) {
         ((Annotation)this._annotations.get(i)).acceptVisit(visit);
      }

      visit.exitAnnotations(this);
   }
}
