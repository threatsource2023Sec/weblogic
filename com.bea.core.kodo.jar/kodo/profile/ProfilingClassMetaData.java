package kodo.profile;

import java.io.Serializable;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;

public class ProfilingClassMetaData implements Serializable {
   String _name;
   ProfilingFieldMetaData[] _fields;

   public ProfilingClassMetaData(ClassMetaData meta) {
      this._name = meta.getDescribedType().getName();
      FieldMetaData[] fmds = meta.getFields();
      this._fields = new ProfilingFieldMetaData[fmds.length];

      for(int i = 0; i < fmds.length; ++i) {
         this._fields[i] = new ProfilingFieldMetaData(fmds[i]);
      }

   }

   public String getName() {
      return this._name;
   }

   public String toString() {
      return this._name;
   }

   public ProfilingFieldMetaData[] getFields() {
      return this._fields;
   }
}
