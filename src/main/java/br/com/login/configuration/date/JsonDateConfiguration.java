package br.com.login.configuration.date;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JsonDateConfiguration {

    private final static String DATE = "dd/MM/yyy";
    private final static String TIME = "HH:mm:ss";
    private final static String DATE_TIME = DATE + " " + TIME;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer dateConfiguration() {
        return jackson -> jackson
                .serializers(
                        new LocalDateSerializer(date()),
                        new LocalTimeSerializer(time()),
                        new LocalDateTimeSerializer(dateTime())
                )
                .deserializers(
                        new LocalDateDeserializer(date()),
                        new LocalTimeDeserializer(time()),
                        new LocalDateTimeDeserializer(dateTime())
                );
    }

    private static DateTimeFormatter date() {
        return DateTimeFormatter.ofPattern(DATE);
    }

    private static DateTimeFormatter time() {
        return DateTimeFormatter.ofPattern(TIME);
    }

    private static DateTimeFormatter dateTime() {
        return DateTimeFormatter.ofPattern(DATE_TIME);
    }


}
