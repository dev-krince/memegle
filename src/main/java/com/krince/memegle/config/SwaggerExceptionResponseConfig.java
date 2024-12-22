package com.krince.memegle.config;

import com.krince.memegle.global.response.ExceptionResponseCode;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerExceptionResponseConfig {

    public Components getExceptionResponseComponents() {
        Components components = new Components();

        generateExceptionResponseComponents(components);

        return components;
    }

    private void generateExceptionResponseComponents(Components components) {
        for (ExceptionResponseCode responseCode : ExceptionResponseCode.values()) {
            components.addResponses(
                    String.valueOf(responseCode.getCode()),
                    createApiResponse(responseCode.getMessage(), generateExampleJson(responseCode))
            );
        }
    }

    private ApiResponse createApiResponse(String description, String example) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>()).example(example)));
    }

    private String generateExampleJson(ExceptionResponseCode responseCode) {
        return String.format(
                "{\n" +
                        "  \"success\": %s,\n" +
                        "  \"status\": \"%s\",\n" +
                        "  \"code\": %d,\n" +
                        "  \"message\": \"%s\"\n" +
                        "}",
                responseCode.getIsSuccess(),
                responseCode.getHttpStatus(),
                responseCode.getCode(),
                responseCode.getMessage()
        );
    }
}
