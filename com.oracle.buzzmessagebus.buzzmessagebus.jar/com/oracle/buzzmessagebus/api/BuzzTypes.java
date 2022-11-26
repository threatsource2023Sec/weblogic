package com.oracle.buzzmessagebus.api;

public interface BuzzTypes {
   byte BUZZ_REQUEST_DATA_FRAME = 0;
   byte BUZZ_RESPONSE_DATA_FRAME = -3;
   byte BUZZ_START_MESSAGE_FRAME = -1;
   int BUZZ_COMMON_FRAME_SIZE = 12;
   short BUZZ_NO_FLAGS_SET = 0;
   short BUZZ_END_MESSAGE_FLAG = 1;
   short BUZZ_ONE_WAY_MESSAGE_FLAG = 16384;
   short BUZZ_CONTAINS_SUBPROTOCOL_HEADER_LENGTH_FLAG = Short.MIN_VALUE;

   public static enum BuzzMessageState {
      OPEN,
      HALF_CLOSED,
      CLOSED;
   }
}
