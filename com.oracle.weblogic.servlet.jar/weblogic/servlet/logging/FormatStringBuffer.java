package weblogic.servlet.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import weblogic.utils.string.SimpleCachingDateFormat;

public final class FormatStringBuffer {
   private static final TimeZone GMT = TimeZone.getTimeZone("GMT");
   private static final byte[][] monthNames = new byte[][]{"Jan".getBytes(), "Feb".getBytes(), "Mar".getBytes(), "Apr".getBytes(), "May".getBytes(), "Jun".getBytes(), "Jul".getBytes(), "Aug".getBytes(), "Sep".getBytes(), "Oct".getBytes(), "Nov".getBytes(), "Dec".getBytes()};
   private static final byte[][] twoDigits = new byte[][]{"00".getBytes(), "01".getBytes(), "02".getBytes(), "03".getBytes(), "04".getBytes(), "05".getBytes(), "06".getBytes(), "07".getBytes(), "08".getBytes(), "09".getBytes(), "10".getBytes(), "11".getBytes(), "12".getBytes(), "13".getBytes(), "14".getBytes(), "15".getBytes(), "16".getBytes(), "17".getBytes(), "18".getBytes(), "19".getBytes(), "20".getBytes(), "21".getBytes(), "22".getBytes(), "23".getBytes(), "24".getBytes(), "25".getBytes(), "26".getBytes(), "27".getBytes(), "28".getBytes(), "29".getBytes(), "30".getBytes(), "31".getBytes(), "32".getBytes(), "33".getBytes(), "34".getBytes(), "35".getBytes(), "36".getBytes(), "37".getBytes(), "38".getBytes(), "39".getBytes(), "40".getBytes(), "41".getBytes(), "42".getBytes(), "43".getBytes(), "44".getBytes(), "45".getBytes(), "46".getBytes(), "47".getBytes(), "48".getBytes(), "49".getBytes(), "50".getBytes(), "51".getBytes(), "52".getBytes(), "53".getBytes(), "54".getBytes(), "55".getBytes(), "56".getBytes(), "57".getBytes(), "58".getBytes(), "59".getBytes(), "60".getBytes(), "61".getBytes(), "62".getBytes(), "63".getBytes(), "64".getBytes(), "65".getBytes(), "66".getBytes(), "67".getBytes(), "68".getBytes(), "69".getBytes(), "70".getBytes(), "71".getBytes(), "72".getBytes(), "73".getBytes(), "74".getBytes(), "75".getBytes(), "76".getBytes(), "77".getBytes(), "78".getBytes(), "79".getBytes(), "80".getBytes(), "81".getBytes(), "82".getBytes(), "83".getBytes(), "84".getBytes(), "85".getBytes(), "86".getBytes(), "87".getBytes(), "88".getBytes(), "89".getBytes(), "90".getBytes(), "91".getBytes(), "92".getBytes(), "93".getBytes(), "94".getBytes(), "95".getBytes(), "96".getBytes(), "97".getBytes(), "98".getBytes(), "99".getBytes()};
   private static final byte[] ERROR_CODE_100 = "100".getBytes();
   private static final byte[] ERROR_CODE_200 = "200".getBytes();
   private static final byte[] ERROR_CODE_302 = "302".getBytes();
   private static final byte[] ERROR_CODE_304 = "304".getBytes();
   private static final byte[] ERROR_CODE_400 = "400".getBytes();
   private static final byte[] ERROR_CODE_401 = "401".getBytes();
   private static final byte[] ERROR_CODE_404 = "404".getBytes();
   private static final byte[] ERROR_CODE_500 = "500".getBytes();
   private static final byte[] SPACE_DASH_SPACE = " - ".getBytes();
   private static final DateOnlyDateFormat dateFormat = new DateOnlyDateFormat();
   private static final DateOnlyDateFormat gmtDateFormat;
   private static final TimeOnlyDateFormat timeFormat;
   private static final TimeOnlyDateFormat gmtTimeFormat;
   private static final TimeFormatWithMillis millisTimeFormat;
   private static final TimeFormatWithMillis gmtMillisTimeFormat;
   private byte[] value;
   private int size;
   private int capacity;
   private boolean useGMT;
   private boolean logMillis;

   public FormatStringBuffer() {
      this(128);
   }

   public FormatStringBuffer(int capacity) {
      this.size = 0;
      this.useGMT = false;
      this.logMillis = false;
      this.value = new byte[capacity];
      this.capacity = capacity;
   }

   public void setUseGMT(boolean useGMT) {
      this.useGMT = useGMT;
   }

   public void setLogMillis(boolean logMs) {
      this.logMillis = logMs;
   }

   public FormatStringBuffer append(String s) {
      if (s != null && s.length() != 0) {
         byte[] buf = s.getBytes();
         int newSize = this.size + buf.length;
         this.ensureCapacity(newSize);
         System.arraycopy(buf, 0, this.value, this.size, buf.length);
         this.size = newSize;
         return this;
      } else {
         return this;
      }
   }

   public FormatStringBuffer append(int i) {
      this.ensureCapacity(this.size + 11);
      if (i < 0) {
         this.value[this.size++] = 45;
      }

      if (i == 0) {
         this.value[this.size++] = 48;
      }

      if (i > 0) {
         i = -i;
      }

      for(int j = 1000000000; j > 0; j /= 10) {
         int div = i / j;
         if (div != 0) {
            int mod = -(div % 10);
            this.value[this.size++] = (byte)(48 + mod);
         }
      }

      return this;
   }

   public FormatStringBuffer append(long l) {
      this.ensureCapacity(this.size + 19);
      if (l < 0L) {
         this.value[this.size++] = 45;
      }

      if (l == 0L) {
         this.value[this.size++] = 48;
      }

      if (l > 0L) {
         l = -l;
      }

      for(long j = 100000000000000000L; j > 0L; j /= 10L) {
         long div = l / j;
         if (div != 0L) {
            long mod = -(div % 10L);
            this.value[this.size++] = (byte)((int)(48L + mod));
         }
      }

      return this;
   }

   public FormatStringBuffer append(byte b) {
      int newlen = this.size + 1;
      this.ensureCapacity(newlen);
      this.value[this.size] = b;
      this.size = newlen;
      return this;
   }

   public FormatStringBuffer append(byte[] b) {
      return this.append(b, 0, b.length);
   }

   public FormatStringBuffer append(byte[] b, int off, int len) {
      int newlen = this.size + len;
      this.ensureCapacity(newlen);
      System.arraycopy(b, off, this.value, this.size, len);
      this.size = newlen;
      return this;
   }

   private void appendInLoop(byte[] b) {
      int len = b.length;
      int newlen = this.size + len;
      this.ensureCapacity(newlen);

      for(int i = 0; i < len; ++i) {
         this.value[this.size + i] = b[i];
      }

      this.size = newlen;
   }

   public FormatStringBuffer appendValueOrEmpty(String val) {
      return val != null ? this.append(val) : this;
   }

   public FormatStringBuffer appendTwoDigits(int number) {
      this.appendInLoop(twoDigits[number]);
      return this;
   }

   public FormatStringBuffer appendValueOrDash(String value) {
      return value != null ? this.append(value) : this.append('-');
   }

   public FormatStringBuffer append(char ch) {
      return this.append((byte)ch);
   }

   public FormatStringBuffer appendSpaceDashSpace() {
      this.appendInLoop(SPACE_DASH_SPACE);
      return this;
   }

   public FormatStringBuffer appendQuotedValueOrDash(String value) {
      return value != null ? this.append('"').append(value).append('"') : this.append('-');
   }

   public FormatStringBuffer appendStatusCode(int number) {
      switch (number) {
         case 100:
            this.appendInLoop(ERROR_CODE_100);
            break;
         case 200:
            this.appendInLoop(ERROR_CODE_200);
            break;
         case 302:
            this.appendInLoop(ERROR_CODE_302);
            break;
         case 304:
            this.appendInLoop(ERROR_CODE_304);
            break;
         case 400:
            this.appendInLoop(ERROR_CODE_400);
            break;
         case 401:
            this.appendInLoop(ERROR_CODE_401);
            break;
         case 404:
            this.appendInLoop(ERROR_CODE_404);
            break;
         case 500:
            this.appendInLoop(ERROR_CODE_500);
            break;
         default:
            this.append(number);
      }

      return this;
   }

   public byte[] getBytes() {
      return this.value;
   }

   public int size() {
      return this.size;
   }

   public FormatStringBuffer appendMonth(int number) {
      this.appendInLoop(monthNames[number]);
      return this;
   }

   public FormatStringBuffer appendYear(int yearMinus1900) {
      if (yearMinus1900 >= 100) {
         this.appendInLoop(twoDigits[20]);
         this.appendInLoop(twoDigits[yearMinus1900 % 100]);
      } else {
         this.appendInLoop(twoDigits[19]);
         this.appendInLoop(twoDigits[yearMinus1900]);
      }

      return this;
   }

   public FormatStringBuffer appendDate() {
      if (this.useGMT) {
         this.appendInLoop(gmtDateFormat.getDateAsBytes(System.currentTimeMillis()));
      } else {
         this.appendInLoop(dateFormat.getDateAsBytes(System.currentTimeMillis()));
      }

      return this;
   }

   public FormatStringBuffer appendTime() {
      if (this.useGMT) {
         if (this.logMillis) {
            this.appendInLoop(gmtMillisTimeFormat.getDateAsBytes(System.currentTimeMillis()));
         } else {
            this.appendInLoop(gmtTimeFormat.getDateAsBytes(System.currentTimeMillis()));
         }
      } else if (this.logMillis) {
         this.appendInLoop(millisTimeFormat.getDateAsBytes(System.currentTimeMillis()));
      } else {
         this.appendInLoop(timeFormat.getDateAsBytes(System.currentTimeMillis()));
      }

      return this;
   }

   private void ensureCapacity(int minimumCapacity) {
      if (this.capacity < minimumCapacity) {
         int newCapacity = this.capacity * 2;
         if (minimumCapacity > newCapacity) {
            newCapacity = minimumCapacity;
         }

         byte[] newValue = new byte[newCapacity];
         System.arraycopy(this.value, 0, newValue, 0, this.size);
         this.value = newValue;
         this.capacity = newCapacity;
      }
   }

   static {
      gmtDateFormat = new DateOnlyDateFormat(GMT);
      timeFormat = new TimeOnlyDateFormat();
      gmtTimeFormat = new TimeOnlyDateFormat(GMT);
      millisTimeFormat = new TimeFormatWithMillis();
      gmtMillisTimeFormat = new TimeFormatWithMillis(GMT);
   }

   private static class TimeFormatWithMillis {
      private Date date;
      private SimpleDateFormat format;

      TimeFormatWithMillis() {
         this(TimeZone.getDefault());
      }

      TimeFormatWithMillis(TimeZone tz) {
         this.date = new Date();
         this.format = new SimpleDateFormat("HH:mm:ss.SSS");
         this.format.setTimeZone(tz);
      }

      byte[] getDateAsBytes(long utc) {
         this.date.setTime(utc);
         return this.format.format(this.date).getBytes();
      }
   }

   private static class TimeOnlyDateFormat extends SimpleCachingDateFormat {
      private final byte[] cachedBytes = new byte[8];
      private String cachedString;

      TimeOnlyDateFormat() {
         super("HH:mm:ss");
      }

      TimeOnlyDateFormat(TimeZone tz) {
         super("HH:mm:ss", tz);
      }

      byte[] getDateAsBytes(long utc) {
         String s = super.getDate(utc);
         if (s != this.cachedString) {
            this.cachedString = s;
            s.getBytes(0, 8, this.cachedBytes, 0);
         }

         return this.cachedBytes;
      }
   }

   private static class DateOnlyDateFormat extends SimpleCachingDateFormat {
      private final byte[] cachedBytes = new byte[10];
      private String cachedString;

      DateOnlyDateFormat() {
         super("yyyy-MM-dd");
      }

      DateOnlyDateFormat(TimeZone tz) {
         super("yyyy-MM-dd", tz);
      }

      byte[] getDateAsBytes(long utc) {
         String s = super.getDate(utc);
         if (s != this.cachedString) {
            this.cachedString = s;
            s.getBytes(0, 10, this.cachedBytes, 0);
         }

         return this.cachedBytes;
      }
   }
}
