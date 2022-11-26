package com.sun.faces.application;

import com.sun.faces.util.MetadataWrapperMap;
import java.util.Map;

public class ViewMemberInstanceFactoryMetadataMap extends MetadataWrapperMap {
   public ViewMemberInstanceFactoryMetadataMap(Map toWrap) {
      super(toWrap);
   }

   protected Object onPut(String key, Object value) {
      return value;
   }
}
