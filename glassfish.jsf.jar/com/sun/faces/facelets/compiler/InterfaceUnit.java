package com.sun.faces.facelets.compiler;

import com.sun.faces.facelets.tag.TagLibrary;
import javax.faces.view.facelets.Tag;

final class InterfaceUnit extends TagUnit {
   public InterfaceUnit(TagLibrary library, String namespace, String name, Tag tag, String id) {
      super(library, namespace, name, tag, id);
   }
}
