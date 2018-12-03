package com.example.currency.aggregation.support;

import com.example.currency.aggregation.dto.CurrencyDTO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CurrencyListDeserializer extends StdDeserializer<List<CurrencyDTO>>{
    public CurrencyListDeserializer() {
        this(null);
    }

    private CurrencyListDeserializer(Class<?> vc) {        super(vc);
    }

    @Override
    public List<CurrencyDTO> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        List<CurrencyDTO> resultList = new ArrayList<>();

        JsonNode node = jp.getCodec().readTree(jp);

        if (node.isArray()) {
            Iterator<JsonNode> iterator = node.elements();
            while (iterator.hasNext()) {
                JsonNode current = iterator.next();
                if (current.isObject()) {
                    resultList.add(getDtoFromNode(current));
                }
            }
        } else if (node.isObject()){
            JsonNode root = node.get("root");
            Iterator<Map.Entry<String, JsonNode>> iterator = root.fields();
            while (iterator.hasNext()) {
                JsonNode xmlObject = iterator.next().getValue();
                if (xmlObject.isArray()){
                    Iterator<JsonNode> xmlIterator = xmlObject.elements();
                    while (xmlIterator.hasNext()) {
                        JsonNode current = xmlIterator.next();
                        if (current.isObject()) {
                            resultList.add(getDtoFromNode(current));
                        }
                    }
                } else {
                    resultList.add(getDtoFromNode(xmlObject));
                }
            }
        }
        return resultList;
    }


    private CurrencyDTO getDtoFromNode(JsonNode node){
        String value = node.get("value").asText();
        String name = node.get("name").asText();
        String action = node.get("action").asText();
        Boolean allowed = node.get("allowed").asBoolean();
        return new CurrencyDTO(name, null, action, value, allowed);
    }


    private void recursiveWrite(JsonNode node){
        System.out.println("Type: "+node.getNodeType() +" name: "+node.toString());
        System.out.println("Fields: ");
        node.fieldNames().forEachRemaining(System.out::println);
        Iterator<JsonNode> nodes = node.elements();
        System.out.println("Values: ");
        Iterator<Map.Entry<String, JsonNode>> currentValues = node.fields();
        while (currentValues.hasNext()){
            Map.Entry<String, JsonNode> currentNode = currentValues.next();
            System.out.println("key: "+currentNode.getKey()+" value: "+currentNode.getValue());
            recursiveWrite(currentNode.getValue());
        }
        while (nodes.hasNext()){
            JsonNode current = nodes.next();
            recursiveWrite(current);
        }
    }
}
