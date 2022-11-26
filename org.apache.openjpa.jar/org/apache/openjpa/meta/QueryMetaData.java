package org.apache.openjpa.meta;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.xml.Commentable;

public class QueryMetaData implements MetaDataModes, SourceTracker, Commentable, Serializable {
   private static final String[] EMPTY_KEYS = new String[0];
   private static final Object[] EMPTY_VALS = new Object[0];
   private final String _name;
   private Boolean _readOnly;
   private File _file;
   private Object _scope;
   private int _srcType;
   private int _mode = 4;
   private String _language;
   private Class _class;
   private Class _candidate;
   private Class _res;
   private String _query;
   private String[] _comments;
   private List _hintKeys;
   private List _hintVals;
   private String _resultSetMappingName;

   protected QueryMetaData(String name) {
      this._name = name;
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

   public boolean isReadOnly() {
      return this._readOnly != null && this._readOnly;
   }

   public void setReadOnly(boolean readOnly) {
      this._readOnly = readOnly ? Boolean.TRUE : Boolean.FALSE;
   }

   public Class getCandidateType() {
      return this._candidate;
   }

   public void setCandidateType(Class cls) {
      this._candidate = cls;
   }

   public Class getResultType() {
      return this._res;
   }

   public void setResultType(Class cls) {
      this._res = cls;
   }

   public String getLanguage() {
      return this._language;
   }

   public void setLanguage(String language) {
      this._language = language;
   }

   public String getQueryString() {
      return this._query;
   }

   public void setQueryString(String query) {
      this._query = query;
   }

   public String[] getHintKeys() {
      return this._hintKeys == null ? EMPTY_KEYS : (String[])((String[])this._hintKeys.toArray(new String[this._hintKeys.size()]));
   }

   public Object[] getHintValues() {
      return this._hintVals == null ? EMPTY_VALS : this._hintVals.toArray();
   }

   public void addHint(String key, Object value) {
      if (this._hintKeys == null) {
         this._hintKeys = new LinkedList();
         this._hintVals = new LinkedList();
      }

      this._hintKeys.add(key);
      this._hintVals.add(value);
   }

   public String getResultSetMappingName() {
      return this._resultSetMappingName;
   }

   public void setResultSetMappingName(String setMappingName) {
      this._resultSetMappingName = setMappingName;
   }

   public void setInto(Query query) {
      if (this._candidate != null) {
         query.setCandidateType(this._candidate, true);
      }

      if (!StringUtils.isEmpty(this._query)) {
         query.setQuery(this._query);
      }

      if (this._res != null) {
         query.setResultType(this._res);
      }

      if (this._readOnly != null) {
         query.setReadOnly(this._readOnly);
      }

      if (this._resultSetMappingName != null) {
         query.setResultMapping((Class)null, this._resultSetMappingName);
      }

   }

   public void setFrom(Query query) {
      this._language = query.getLanguage();
      this._candidate = query.getCandidateType();
      this._res = query.getResultType();
      this._query = query.getQueryString();
   }

   public int getSourceMode() {
      return this._mode;
   }

   public void setSourceMode(int mode) {
      this._mode = mode;
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
}
