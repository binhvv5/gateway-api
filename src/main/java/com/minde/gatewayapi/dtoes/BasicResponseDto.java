package com.minde.gatewayapi.dtoes;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BasicResponseDto {

    private String responseCd;

    private String responseMsg;

    private String responseTs;

}
