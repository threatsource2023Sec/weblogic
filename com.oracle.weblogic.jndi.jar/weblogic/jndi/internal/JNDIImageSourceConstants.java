package weblogic.jndi.internal;

public interface JNDIImageSourceConstants {
   short CORBA_TYPE = 0;
   short REMOTE_TYPE = 1;
   short EXTERNALIZABLE_TYPE = 2;
   short SERIALIZABLE_TYPE = 3;
   short NON_SERIALIZABLE_TYPE = 4;
   String IMAGE_SOURCE_NAME = "JNDI_IMAGE_SOURCE";
   String CORBA = "corba";
   String REMOTE = "remote";
   String EXTERNALIZABLE = "externalizable";
   String SERIALIZABLE = "serializable";
   String NON_SERIALIZABLE = "non-serializable";
   String DOUBLE_QUOTES = "\"";
   String BINDING_OPEN_TAG = "<binding";
   String BINDING_CLOSE_TAG = "</binding>";
   String NAME = "name=\"";
   String BINDING_JNDI_NAME = "jndi-name=\"";
   String BINDING_CLASS_NAME = "class-name=\"";
   String BINDING_SIZE = "size=\"";
   String BINDING_TYPE = "type=\"";
   String BINDING_CLUSTERABLE = "clusterable=\"";
   String BINDING_STRING_REPRESENTATION = "string-representation=\"";
   String CONTEXT_OPEN_TAG = "<context";
   String CONTEXT_CLOSE_TAG = "</context>";
   String CLOSE_BRACKET = ">";
}
