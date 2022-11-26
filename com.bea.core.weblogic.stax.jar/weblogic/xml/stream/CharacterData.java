package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface CharacterData extends XMLEvent {
   String getContent();

   boolean hasContent();
}
