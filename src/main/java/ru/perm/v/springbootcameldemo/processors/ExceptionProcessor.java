package ru.perm.v.springbootcameldemo.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import static ru.perm.v.springbootcameldemo.utils.Utility.createResponse;

@Component
public class ExceptionProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
        String code = null;
        if(exception instanceof DuplicateKeyException) {
            code = "409";
        } else {
            code = "500";
        }
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, code);
        exchange.getOut().setBody(createResponse(exception.getMessage(), "Exception Occurred", code));
    }
}