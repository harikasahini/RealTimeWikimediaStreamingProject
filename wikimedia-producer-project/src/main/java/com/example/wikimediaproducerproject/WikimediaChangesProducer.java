package com.example.wikimediaproducerproject;


import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import jdk.jfr.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

//import java.util.concurrent.TimeUnit;
import javax.xml.datatype.DatatypeConstants;
import java.net.URI;

@Service
public class WikimediaChangesProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);


    private KafkaTemplate<String,String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        String topic= "wikimedia-topic";

        //to read real time data from wikimedia
        EventHandler eventHandler=new WikimediaChangesHandler(topic,kafkaTemplate);
        String url="https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder=new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource=builder.build();
        eventSource.start();
        Thread.sleep(10 * 60 * 1000);
        //cant sleep this thread
       // DatatypeConstants TimeUnit;
        // TimeUnit.MINUTES.sleep(10);
    }
}
