package org.glassfish.grizzly.http;

import org.glassfish.grizzly.attributes.Attribute;

public final class Note {
   final Attribute attribute;

   Note(Attribute attribute) {
      this.attribute = attribute;
   }
}
