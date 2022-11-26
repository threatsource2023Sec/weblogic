package org.apache.openjpa.lib.meta;

public class SuffixMetaDataFilter implements MetaDataFilter {
   private final String _suffix;

   public SuffixMetaDataFilter(String suffix) {
      this._suffix = suffix;
   }

   public boolean matches(MetaDataFilter.Resource rsrc) {
      String name = rsrc.getName();
      return name != null && name.endsWith(this._suffix);
   }
}
