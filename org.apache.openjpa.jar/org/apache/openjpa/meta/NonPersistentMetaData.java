package org.apache.openjpa.meta;

import java.io.File;
import java.io.Serializable;
import org.apache.openjpa.lib.meta.SourceTracker;
import org.apache.openjpa.lib.xml.Commentable;

public class NonPersistentMetaData implements Comparable, SourceTracker, Commentable, MetaDataContext, Serializable {
   public static final int TYPE_PERSISTENCE_AWARE = 1;
   public static final int TYPE_NON_MAPPED_INTERFACE = 2;
   private final MetaDataRepository _repos;
   private final Class _class;
   private final int _type;
   private File _srcFile = null;
   private int _srcType = 0;
   private String[] _comments = null;
   private int _listIndex = -1;

   protected NonPersistentMetaData(Class cls, MetaDataRepository repos, int type) {
      this._repos = repos;
      this._class = cls;
      this._type = type;
   }

   public MetaDataRepository getRepository() {
      return this._repos;
   }

   public Class getDescribedType() {
      return this._class;
   }

   public int getType() {
      return this._type;
   }

   public int getListingIndex() {
      return this._listIndex;
   }

   public void setListingIndex(int index) {
      this._listIndex = index;
   }

   public File getSourceFile() {
      return this._srcFile;
   }

   public Object getSourceScope() {
      return null;
   }

   public int getSourceType() {
      return this._srcType;
   }

   public void setSource(File file, int srcType) {
      this._srcFile = file;
      this._srcType = srcType;
   }

   public String getResourceName() {
      return this._class.getName();
   }

   public String[] getComments() {
      return this._comments == null ? ClassMetaData.EMPTY_COMMENTS : this._comments;
   }

   public void setComments(String[] comments) {
      this._comments = comments;
   }

   public int compareTo(Object o) {
      if (o == this) {
         return 0;
      } else if (!(o instanceof NonPersistentMetaData)) {
         return 1;
      } else {
         NonPersistentMetaData other = (NonPersistentMetaData)o;
         return this._type != other.getType() ? this._type - other.getType() : this._class.getName().compareTo(other.getDescribedType().getName());
      }
   }
}
