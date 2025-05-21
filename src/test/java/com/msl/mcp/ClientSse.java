package com.msl.mcp;

import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;


/**
 * @author Christian Tzolov
 */
public class ClientSse {

    public static void main(String[] args) {

        var transport = HttpClientSseClientTransport.builder("http://localhost:8081").build();

        new SampleClient(transport).run();
    }

}