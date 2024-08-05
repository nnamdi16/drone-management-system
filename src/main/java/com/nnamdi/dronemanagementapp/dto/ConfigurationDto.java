package com.nnamdi.dronemanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigurationDto implements Serializable {
    private String vertexTempDirectory;
    private String vertexKeyStorePath;
    private String vertexKeyStorePassword;
    private String vertexTrustStorePath;
    private String vertexTrustStorePassword;


}
