package org.ohnlp.backbone.instrumentation.io;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

public class MongoDBJavaAgent {
    public static void premain(String arg, Instrumentation inst) {
        new AgentBuilder.Default()
                // Redefine AggregateIterableImpl to run sorts on disk by default
                .type(ElementMatchers.named("com.mongodb.client.internal.AggregateIterableImpl"))
                .transform((builder, type, ignored, ignored2) -> builder.field(ElementMatchers.named("allowDiskUse")).value(true))
                .type(ElementMatchers.named("com.mongodb.operation.AggregateOperationImpl"))
                .transform((builder, type, ignored, ignored2) -> builder.field(ElementMatchers.named("allowDiskUse")).value(true))
                .installOn(inst);
    }
}
