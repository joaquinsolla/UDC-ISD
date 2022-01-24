package es.udc.ws.app.thriftservice;

import es.udc.ws.app.thrift.ThriftExcursionService;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;

public class ThriftExcursionServiceServlet extends TServlet {

    public ThriftExcursionServiceServlet() {
        super(createProcessor(), createProtocolFactory());
    }

    private static TProcessor createProcessor() {

        return new ThriftExcursionService.Processor<ThriftExcursionService.Iface>(
                new ThriftExcursionServiceIpm1());

    }

    private static TProtocolFactory createProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }

}
