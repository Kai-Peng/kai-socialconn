package com.kai.socialconn.netty.privateProtocal;

import org.jboss.marshalling.*;

import java.io.IOException;

public class MarshallingCodecFactory {
    public static Marshaller buildMarshalling() throws IOException {
        final MarshallerFactory marshallingFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        Marshaller marshaller = marshallingFactory.createMarshaller(configuration);
        return marshaller;
    }

    public static Unmarshaller buildUnMarshalling() throws IOException {
        final MarshallerFactory marshallingFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        final Unmarshaller unmarshaller = marshallingFactory.createUnmarshaller(configuration);
        return unmarshaller;
    }
}
