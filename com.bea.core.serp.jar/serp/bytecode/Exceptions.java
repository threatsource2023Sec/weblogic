package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.visitor.BCVisitor;
import serp.util.Numbers;
import serp.util.Strings;

public class Exceptions extends Attribute {
   private List _indexes = new LinkedList();

   Exceptions(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   int getLength() {
      return 2 + 2 * this._indexes.size();
   }

   public BCMethod getMethod() {
      return (BCMethod)this.getOwner();
   }

   public int[] getExceptionIndexes() {
      int[] indexes = new int[this._indexes.size()];
      Iterator itr = this._indexes.iterator();

      for(int i = 0; i < indexes.length; ++i) {
         indexes[i] = (Integer)itr.next();
      }

      return indexes;
   }

   public void setExceptionIndexes(int[] exceptionIndexes) {
      this._indexes.clear();
      if (exceptionIndexes != null) {
         for(int i = 0; i < exceptionIndexes.length; ++i) {
            this._indexes.add(Numbers.valueOf(exceptionIndexes[i]));
         }
      }

   }

   public String[] getExceptionNames() {
      String[] names = new String[this._indexes.size()];
      Iterator itr = this._indexes.iterator();

      for(int i = 0; i < names.length; ++i) {
         int index = ((Number)itr.next()).intValue();
         ClassEntry entry = (ClassEntry)this.getPool().getEntry(index);
         names[i] = this.getProject().getNameCache().getExternalForm(entry.getNameEntry().getValue(), false);
      }

      return names;
   }

   public Class[] getExceptionTypes() {
      String[] names = this.getExceptionNames();
      Class[] types = new Class[names.length];

      for(int i = 0; i < names.length; ++i) {
         types[i] = Strings.toClass(names[i], this.getClassLoader());
      }

      return types;
   }

   public BCClass[] getExceptionBCs() {
      String[] names = this.getExceptionNames();
      BCClass[] types = new BCClass[names.length];

      for(int i = 0; i < names.length; ++i) {
         types[i] = this.getProject().loadClass(names[i], this.getClassLoader());
      }

      return types;
   }

   public void setExceptions(String[] exceptions) {
      int i;
      if (exceptions != null) {
         for(i = 0; i < exceptions.length; ++i) {
            if (exceptions[i] == null) {
               throw new NullPointerException("exceptions[" + i + "] = null");
            }
         }
      }

      this.clear();
      if (exceptions != null) {
         for(i = 0; i < exceptions.length; ++i) {
            this.addException(exceptions[i]);
         }
      }

   }

   public void setExceptions(Class[] exceptions) {
      String[] names = null;
      if (exceptions != null) {
         names = new String[exceptions.length];

         for(int i = 0; i < exceptions.length; ++i) {
            names[i] = exceptions[i].getName();
         }
      }

      this.setExceptions(names);
   }

   public void setExceptions(BCClass[] exceptions) {
      String[] names = null;
      if (exceptions != null) {
         names = new String[exceptions.length];

         for(int i = 0; i < exceptions.length; ++i) {
            names[i] = exceptions[i].getName();
         }
      }

      this.setExceptions(names);
   }

   public void clear() {
      this._indexes.clear();
   }

   public boolean removeException(String type) {
      String internalForm = this.getProject().getNameCache().getInternalForm(type, false);
      Iterator itr = this._indexes.iterator();

      ClassEntry entry;
      do {
         if (!itr.hasNext()) {
            return false;
         }

         entry = (ClassEntry)this.getPool().getEntry((Integer)itr.next());
      } while(!entry.getNameEntry().getValue().equals(internalForm));

      itr.remove();
      return true;
   }

   public boolean removeException(Class type) {
      return type == null ? false : this.removeException(type.getName());
   }

   public boolean removeException(BCClass type) {
      return type == null ? false : this.removeException(type.getName());
   }

   public void addException(String type) {
      int index = this.getPool().findClassEntry(this.getProject().getNameCache().getInternalForm(type, false), true);
      this._indexes.add(Numbers.valueOf(index));
   }

   public void addException(Class type) {
      this.addException(type.getName());
   }

   public void addException(BCClass type) {
      this.addException(type.getName());
   }

   public boolean throwsException(String type) {
      String[] exceptions = this.getExceptionNames();

      for(int i = 0; i < exceptions.length; ++i) {
         if (exceptions[i].equals(type)) {
            return true;
         }
      }

      return false;
   }

   public boolean throwsException(Class type) {
      return type == null ? false : this.throwsException(type.getName());
   }

   public boolean throwsException(BCClass type) {
      return type == null ? false : this.throwsException(type.getName());
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterExceptions(this);
      visit.exitExceptions(this);
   }

   void read(Attribute other) {
      this.setExceptions(((Exceptions)other).getExceptionNames());
   }

   void read(DataInput in, int length) throws IOException {
      this._indexes.clear();
      int exceptionCount = in.readUnsignedShort();

      for(int i = 0; i < exceptionCount; ++i) {
         this._indexes.add(Numbers.valueOf(in.readUnsignedShort()));
      }

   }

   void write(DataOutput out, int length) throws IOException {
      out.writeShort(this._indexes.size());
      Iterator itr = this._indexes.iterator();

      while(itr.hasNext()) {
         out.writeShort(((Number)itr.next()).shortValue());
      }

   }
}
