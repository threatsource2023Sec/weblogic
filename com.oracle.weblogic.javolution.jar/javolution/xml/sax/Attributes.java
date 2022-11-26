package javolution.xml.sax;

public interface Attributes {
   int getLength();

   CharSequence getURI(int var1);

   CharSequence getLocalName(int var1);

   CharSequence getQName(int var1);

   String getType(int var1);

   CharSequence getValue(int var1);

   int getIndex(String var1, String var2);

   int getIndex(String var1);

   String getType(String var1, String var2);

   String getType(String var1);

   CharSequence getValue(String var1, String var2);

   CharSequence getValue(String var1);
}
