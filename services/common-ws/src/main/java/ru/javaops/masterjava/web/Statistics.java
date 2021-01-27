package ru.javaops.masterjava.web;

import com.sun.xml.ws.api.handler.MessageHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Statistics {
    public enum RESULT {
        SUCCESS {
            public void handleMessage(MessageHandlerContext context, long startTime, boolean isRequest) {
                count(context.getMessage().getPayloadLocalPart(), startTime, SUCCESS);
            }

            public void handleFault(MessageHandlerContext context, long startTime) {
                context.put("REQUEST_MSG", context.getMessage().getPayloadLocalPart());
                count(context.getMessage().getPayloadLocalPart(), startTime, FAIL);
            }
        },
        FAIL {
            public void handleMessage(MessageHandlerContext context, long startTime, boolean isRequest) {
                count(context.getMessage().getPayloadLocalPart(), startTime, SUCCESS);
                }

            public void handleFault(MessageHandlerContext context, long startTime) {
                context.put("REQUEST_MSG", context.getMessage().getPayloadLocalPart());
                count(context.getMessage().getPayloadLocalPart(), startTime, FAIL);
            }
        };

        public abstract void handleMessage(MessageHandlerContext mhc, long startTime, boolean isRequest);

        public abstract void handleFault(MessageHandlerContext mhc, long startTime);
    }

    public static void count(String payload, long startTime, RESULT result) {
        long now = System.currentTimeMillis();
        int ms = (int) (now - startTime);
        log.info(payload + " " + result.name() + " execution time(ms): " + ms);
        // place for statistics staff
    }
}
