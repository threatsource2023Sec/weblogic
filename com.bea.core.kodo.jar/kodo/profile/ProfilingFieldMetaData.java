package kodo.profile;

import java.io.Serializable;
import org.apache.openjpa.meta.FieldMetaData;

public class ProfilingFieldMetaData implements Serializable {
   String _name;
   boolean _dfg;
   int _index;

   public ProfilingFieldMetaData(FieldMetaData meta) {
      this._name = meta.getName();
      this._dfg = meta.isInDefaultFetchGroup();
      this._index = meta.getIndex();
   }

   public String getName() {
      return this._name;
   }

   public int getIndex() {
      return this._index;
   }

   public boolean isInDefaultFetchGroup() {
      return this._dfg;
   }

   public String toString() {
      return this._name;
   }
}
