package weblogic.apache.org.apache.velocity.anakia;

public class Escape {
   public static final String getText(String st) {
      StringBuffer buff = new StringBuffer();
      char[] block = st.toCharArray();
      String stEntity = null;
      int i = 0;

      int last;
      for(last = 0; i < block.length; ++i) {
         switch (block[i]) {
            case '"':
               stEntity = "&quot;";
               break;
            case '&':
               stEntity = "&amp;";
               break;
            case '<':
               stEntity = "&lt;";
               break;
            case '>':
               stEntity = "&gt;";
         }

         if (stEntity != null) {
            buff.append(block, last, i - last);
            buff.append(stEntity);
            stEntity = null;
            last = i + 1;
         }
      }

      if (last < block.length) {
         buff.append(block, last, i - last);
      }

      return buff.toString();
   }
}
