package it.gssi.cs.rastapms.business.impl;

import it.gssi.cs.rastapms.configuration.RastaPMSConfigProperties;
import it.gssi.cs.rastapms.domain.Parameter;
import it.gssi.cs.rastapms.domain.ParameterValue;
import it.gssi.cs.rastapms.domain.Sensor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class InfluxDBService {

    private RastaPMSConfigProperties properties;

    public InfluxDBService(RastaPMSConfigProperties properties) {
        this.properties = properties;
    }

    public ParameterValue getParameterValue(Parameter parameter) {
        RestClient restClient = RestClient.create();
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(properties.getInfluxdbURL())
                .queryParam("orgID", properties.getOrgID())
                .build();
        Sensor sensor = parameter.getSensor();
        StringBuilder sb = new StringBuilder();
        sb.append("from(bucket: \"" + properties.getBucket() + "\")\n");
        sb.append("  |> range(start: -" + sensor.getType().getRepetition() + "m)\n");
        sb.append("  |> filter(fn: (r) => r[\"topic\"] == \"" + sensor.getTopic() + "\")\n");
        sb.append("  |> filter(fn: (r) => r[\"_field\"] == \"" + parameter.getField() + "\")");
        ResponseEntity<String> response = restClient.post()
                .uri(uriComponents.toUri())
                .accept(new MediaType("application", "csv"))
                .header("Authorization", "Token " + properties.getAuthorizationtoken())
                .contentType(new MediaType("application", "vnd.flux"))
                .body(sb.toString())
                .retrieve()
                .toEntity(String.class);
        String parameterValue = getParameterValueFromRESTResult(response.getBody());

        ParameterValue result = new ParameterValue();
        result.setValue(parameterValue);
        result.setParameter(parameter);
        return result;
    }

    private String getParameterValueFromRESTResult(String body) {
        String[] rows = body.trim().split("\n");
        if (rows.length >= 2) {
            String[] row = rows[1].split(",");
            if (row.length > 6) {
                return row[6];
            }
        }
        return "Non Disponibile";
    }
}
