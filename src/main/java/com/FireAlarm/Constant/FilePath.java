package com.FireAlarm.Constant;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "firealarm.filepath")
@Component
@Data
public class FilePath {

    private String videoFilePath;
    private String picFilePath;
}
