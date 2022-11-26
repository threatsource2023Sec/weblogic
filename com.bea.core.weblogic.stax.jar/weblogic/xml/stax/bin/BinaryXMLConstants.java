package weblogic.xml.stax.bin;

public interface BinaryXMLConstants {
   String HEADERV1 = "<?binxml version=\"1.0\"?>\n";
   byte[] HEADERV1_BYTES = "<?binxml version=\"1.0\"?>\n".getBytes();
   String HEADERV2 = "<?binxml version=\"2.0\"?>\n";
   byte[] HEADERV2_BYTES = "<?binxml version=\"2.0\"?>\n".getBytes();
   short BEGIN_ELEMENT = 1;
   short BEGIN_ATTRIBUTE = 2;
   short BEGIN_DOCUMENT = 3;
   short END_ELEMENT = 4;
   short END_ATTRIBUTE = 5;
   short END_DOCUMENT = 6;
   short COMMENT = 7;
   short NAMESPACE = 8;
   short PROC_INST = 9;
   short TEXT = 10;
   short PCDATA = 19;
   short DOCUMENT_TYPE = 50;
   short OP_ADD_ENTRY = 60;
   short OP_FLUSH_ENTRIES = 61;
   short OP_END_OF_STREAM = 62;
   short DICTIONARY_PCDATA = 83;
   int V1_OP_ADD_ENTRY = 1000;
   int V1_OP_FLUSH_ENTRIES = 1001;
   int V1_OP_END_OF_STREAM = 1002;
}
