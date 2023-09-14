package net.javaguides.springboot;

import net.javaguides.springboot.entity.WikimediaData;
import net.javaguides.springboot.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDatabaseConsumer.class);

    private WikimediaDataRepository dataRepository;

    public KafkaDatabaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String eventMessage){

        LOGGER.info(String.format("Event message received -> %s", eventMessage));

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);

        dataRepository.save(wikimediaData);
    }

//    @KafkaListener(topics = "stock-feed", groupId = "my-group")
//    public void consume(ConsumerRecord<String, StockData> record) {
//        StockData stockData = record.value();
//        String stockId = stockData.getStockID();
//
//        // Check if there are subscribers for this stockID and send the data accordingly
//        // You can use a map or a database to store subscriber information
//
//        // Example: Send data to subscribers
//        // sendToSubscribers(stockData, stockId);
//    }
//
//    // Implement the logic to send data to subscribers based on stockID
//    // private void sendToSubscribers(StockData stockData, String stockId) {
//    //     ...
//    // }

}
