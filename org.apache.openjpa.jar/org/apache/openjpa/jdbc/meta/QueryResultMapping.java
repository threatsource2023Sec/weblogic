package org.apache.openjpa.jdbc.meta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.xml.Commentable;
import org.apache.openjpa.meta.MetaDataModes;
import org.apache.openjpa.util.MetaDataException;
import serp.util.Strings;

public class QueryResultMapping implements MetaDataModes, SourceTracker, Commentable {
   private static final Localizer _loc = Localizer.forPackage(QueryResultMapping.class);
   private final String _name;
   private final MappingRepository _repos;
   private File _file = null;
   private Object _scope = null;
   private int _srcType = 0;
   private int _mode = 4;
   private Class _class = null;
   private int _idx = 0;
   private String[] _comments = null;
   private List _colList = null;
   private List _pcList = null;
   private PCResult[] _pcs = null;
   private Object[] _cols = null;

   QueryResultMapping(String name, MappingRepository repos) {
      this._name = name;
      this._repos = repos;
   }

   public String getName() {
      return this._name;
   }

   public Class getDefiningType() {
      return this._class;
   }

   public void setDefiningType(Class cls) {
      this._class = cls;
   }

   public Object[] getColumnResults() {
      if (this._cols == null) {
         Object[] cols;
         if (this._colList == null) {
            cols = new Object[0];
         } else {
            cols = this._colList.toArray();
         }

         this._cols = cols;
      }

      return this._cols;
   }

   public void addColumnResult(Object id) {
      this._cols = null;
      if (this._colList == null) {
         this._colList = new ArrayList();
      }

      this._colList.add(id);
   }

   public PCResult[] getPCResults() {
      if (this._pcs == null) {
         PCResult[] pcs;
         if (this._pcList == null) {
            pcs = new PCResult[0];
         } else {
            pcs = (PCResult[])((PCResult[])this._pcList.toArray(new PCResult[this._pcList.size()]));
         }

         this._pcs = pcs;
      }

      return this._pcs;
   }

   public PCResult addPCResult(Class candidate) {
      this._pcs = null;
      PCResult pc = new PCResult(candidate);
      if (this._pcList == null) {
         this._pcList = new ArrayList();
      }

      this._pcList.add(pc);
      return pc;
   }

   public int getSourceMode() {
      return this._mode;
   }

   public void setSourceMode(int mode) {
      this._mode = mode;
   }

   public int getListingIndex() {
      return this._idx;
   }

   public void setListingIndex(int idx) {
      this._idx = idx;
   }

   public String toString() {
      return this._name;
   }

   public String[] getComments() {
      return this._comments == null ? EMPTY_COMMENTS : this._comments;
   }

   public void setComments(String[] comments) {
      this._comments = comments;
   }

   public File getSourceFile() {
      return this._file;
   }

   public Object getSourceScope() {
      return this._scope;
   }

   public int getSourceType() {
      return this._srcType;
   }

   public void setSource(File file, Object scope, int srcType) {
      this._file = file;
      this._scope = scope;
      this._srcType = srcType;
   }

   public String getResourceName() {
      return this._class == null ? this._name : this._class.getName() + ":" + this._name;
   }

   private static class MultiColumnMap implements ColumnMap {
      private final List _cols;
      private final Object[] _ids;

      public MultiColumnMap(Column[] cols) {
         this._cols = Arrays.asList(cols);
         this._ids = new Object[cols.length];
      }

      public Object map(Column col) {
         int idx = this._cols.indexOf(col);
         return idx == -1 ? col : this._ids[idx];
      }

      public void set(Column col, Object id) {
         int idx = this._cols.indexOf(col);
         if (idx != -1) {
            this._ids[idx] = id;
         }

      }

      public String toString() {
         return this._cols + "=" + Arrays.asList(this._ids);
      }
   }

   private static class SingleColumnMap implements ColumnMap {
      private final Object _id;

      public SingleColumnMap(Object id) {
         this._id = id;
      }

      public Object map(Column col) {
         return this._id;
      }

      public String toString() {
         return this._id.toString();
      }
   }

   private interface ColumnMap {
      Object map(Column var1);
   }

   private static class FetchInfo {
      public final BitSet excludes;
      public final BitSet eager;

      public FetchInfo(ClassMapping type) {
         FieldMapping[] fms = type.getFieldMappings();
         this.eager = new BitSet(fms.length);
         this.excludes = new BitSet(fms.length);

         for(int i = 0; i < fms.length; ++i) {
            if (!fms[i].isPrimaryKey()) {
               this.excludes.set(i);
            }
         }

      }
   }

   public class PCResult {
      public static final String DISCRIMINATOR = "<discriminator>";
      private final Class _candidate;
      private ClassMapping _candidateMap;
      private Map _rawMappings;
      private Map _mappings;
      private Map _eager;
      private FetchInfo _fetchInfo;

      private PCResult(Class candidate) {
         this._candidateMap = null;
         this._rawMappings = null;
         this._mappings = null;
         this._eager = null;
         this._fetchInfo = null;
         this._candidate = candidate;
      }

      public Class getCandidateType() {
         return this._candidate;
      }

      public ClassMapping getCandidateTypeMapping() {
         if (this._candidateMap == null) {
            this._candidateMap = QueryResultMapping.this._repos.getMapping((Class)this._candidate, (ClassLoader)null, true);
         }

         return this._candidateMap;
      }

      public String[] getMappingPaths() {
         if (this._rawMappings == null) {
            return new String[0];
         } else {
            Collection keys = this._rawMappings.keySet();
            return (String[])((String[])keys.toArray(new String[keys.size()]));
         }
      }

      public Object getMapping(String path) {
         return this._rawMappings == null ? null : this._rawMappings.get(path);
      }

      public void addMapping(String path, Object id) {
         if (StringUtils.isEmpty(path)) {
            throw new MetaDataException(QueryResultMapping._loc.get("null-path", QueryResultMapping.this, this._candidate));
         } else {
            this._mappings = null;
            this._eager = null;
            this._fetchInfo = null;
            if (this._rawMappings == null) {
               this._rawMappings = new HashMap();
            }

            this._rawMappings.put(path, id);
         }
      }

      public Object map(List path, Object id, Joins joins) {
         if (this._rawMappings != null && id instanceof Column) {
            this.resolve();
            ColumnMap cm = (ColumnMap)this._mappings.get(path);
            return cm == null ? id : cm.map((Column)id);
         } else {
            return id;
         }
      }

      public boolean hasEager(List path, FieldMapping field) {
         if (this._rawMappings == null) {
            return false;
         } else {
            this.resolve();
            if (path.isEmpty()) {
               return this._fetchInfo.eager.get(field.getIndex());
            } else if (this._eager == null) {
               return false;
            } else {
               FetchInfo info = (FetchInfo)this._eager.get(path);
               return info != null && info.eager.get(field.getIndex());
            }
         }
      }

      public BitSet getExcludes(List path) {
         if (this._rawMappings == null) {
            return null;
         } else {
            this.resolve();
            if (path.isEmpty()) {
               return this._fetchInfo.excludes;
            } else if (this._eager == null) {
               return null;
            } else {
               FetchInfo info = (FetchInfo)this._eager.get(path);
               return info == null ? null : info.excludes;
            }
         }
      }

      private synchronized void resolve() {
         if (this._rawMappings != null && this._mappings == null) {
            this._mappings = new HashMap();
            this._fetchInfo = new FetchInfo(this.getCandidateTypeMapping());
            Iterator itr = this._rawMappings.entrySet().iterator();

            while(itr.hasNext()) {
               Map.Entry entry = (Map.Entry)itr.next();
               this.resolveMapping((String)entry.getKey(), entry.getValue());
            }

         }
      }

      private void resolveMapping(String path, Object id) {
         String[] tokens = Strings.split(path, ".", 0);
         List rpath = new ArrayList(tokens.length);
         ClassMapping candidate = this.getCandidateTypeMapping();
         FieldMapping fm = null;

         for(int i = 0; i < tokens.length - 1; ++i) {
            fm = candidate.getFieldMapping(tokens[i]);
            if (fm == null) {
               throw new MetaDataException(QueryResultMapping._loc.get("bad-path", QueryResultMapping.this, this._candidate, path));
            }

            if (fm.getEmbeddedMapping() != null) {
               this.recordIncluded(candidate, rpath, fm);
               candidate = fm.getEmbeddedMapping();
            } else {
               candidate = fm.getTypeMapping();
            }

            if (candidate == null) {
               throw new MetaDataException(QueryResultMapping._loc.get("untraversable-path", QueryResultMapping.this, this._candidate, path));
            }

            rpath.add(fm);
         }

         String lastToken = tokens[tokens.length - 1];
         if ("<discriminator>".equals(lastToken)) {
            Discriminator discrim = candidate.getDiscriminator();
            rpath.add(discrim);
            this.assertSingleColumn(discrim.getColumns(), path);
            this._mappings.put(rpath, new SingleColumnMap(id));
         } else {
            FieldMapping last = candidate.getFieldMapping(lastToken);
            if (last == null) {
               throw new MetaDataException(QueryResultMapping._loc.get("untraversable-path", QueryResultMapping.this, this._candidate, path));
            }

            Column[] cols = last.getColumns();
            this.assertSingleColumn(cols, path);
            Column col = cols[0];
            if (fm != null && fm.getDeclaredTypeCode() == 29) {
               this.addComplexColumnMapping(fm, rpath, col, id);
               return;
            }

            if (fm != null && fm.getForeignKey() != null) {
               Column fkCol = fm.getForeignKey().getColumn(col);
               if (fkCol != null) {
                  this.addComplexColumnMapping(fm, new ArrayList(rpath), fkCol, id);
               } else {
                  this.recordEager(candidate, rpath, fm);
                  this.recordIncluded(candidate, rpath, last);
               }
            } else {
               this.recordIncluded(candidate, rpath, last);
            }

            rpath.add(last);
            this._mappings.put(rpath, new SingleColumnMap(id));
         }

      }

      private void addComplexColumnMapping(FieldMapping fm, List rpath, Column col, Object id) {
         if (fm.getColumns().length == 1) {
            this._mappings.put(rpath, new SingleColumnMap(id));
         } else {
            MultiColumnMap mcm = (MultiColumnMap)this._mappings.get(rpath);
            if (mcm == null) {
               mcm = new MultiColumnMap(fm.getColumns());
               this._mappings.put(rpath, mcm);
            }

            mcm.set(col, id);
         }

      }

      private void assertSingleColumn(Column[] cols, String path) {
         if (cols.length != 1) {
            throw new MetaDataException(QueryResultMapping._loc.get("num-cols-path", QueryResultMapping.this, this._candidate, path));
         }
      }

      private void recordEager(ClassMapping candidate, List path, FieldMapping fm) {
         if (path.size() == 1) {
            this._fetchInfo.eager.set(fm.getIndex());
            this._fetchInfo.excludes.clear(fm.getIndex());
         } else {
            List copy = new ArrayList(path.size() - 1);

            for(int i = 0; i < copy.size(); ++i) {
               copy.add(path.get(i));
            }

            if (this._eager == null) {
               this._eager = new HashMap();
            }

            FetchInfo info = (FetchInfo)this._eager.get(copy);
            if (info == null) {
               info = new FetchInfo(candidate);
               this._eager.put(copy, info);
            }

            info.eager.set(fm.getIndex());
            info.excludes.clear(fm.getIndex());
         }

      }

      private void recordIncluded(ClassMapping candidate, List path, FieldMapping fm) {
         if (path.isEmpty()) {
            this._fetchInfo.excludes.clear(fm.getIndex());
         } else {
            if (this._eager == null) {
               this._eager = new HashMap();
            }

            FetchInfo info = (FetchInfo)this._eager.get(path);
            if (info == null) {
               info = new FetchInfo(candidate);
               this._eager.put(new ArrayList(path), info);
            }

            info.excludes.clear(fm.getIndex());
         }

      }

      // $FF: synthetic method
      PCResult(Class x1, Object x2) {
         this(x1);
      }
   }
}
