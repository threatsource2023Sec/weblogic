package org.apache.openjpa.meta;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.lib.meta.ClassArgParser;

public interface MetaDataFactory extends MetaDataModes {
   int STORE_DEFAULT = 0;
   int STORE_PER_CLASS = 1;
   int STORE_VERBOSE = 2;

   void setRepository(MetaDataRepository var1);

   void setStoreDirectory(File var1);

   void setStoreMode(int var1);

   void setStrict(boolean var1);

   void load(Class var1, int var2, ClassLoader var3);

   boolean store(ClassMetaData[] var1, QueryMetaData[] var2, SequenceMetaData[] var3, int var4, Map var5);

   boolean drop(Class[] var1, int var2, ClassLoader var3);

   MetaDataDefaults getDefaults();

   Set getPersistentTypeNames(boolean var1, ClassLoader var2);

   Class getQueryScope(String var1, ClassLoader var2);

   Class getResultSetMappingScope(String var1, ClassLoader var2);

   ClassArgParser newClassArgParser();

   void clear();

   void addClassExtensionKeys(Collection var1);

   void addFieldExtensionKeys(Collection var1);

   void loadXMLMetaData(FieldMetaData var1);
}
