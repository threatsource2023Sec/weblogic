package org.apache.xml.security.stax.ext.stax;

import javax.xml.stream.events.Characters;

public interface XMLSecCharacters extends XMLSecEvent, Characters {
   XMLSecCharacters asCharacters();

   char[] getText();
}
