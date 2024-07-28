package com.example.marketmailapp.mappers;

import com.example.marketmailapp.dto.CampaignDTO;
import com.example.marketmailapp.model.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CampaignMapper {

    @Mapping(source = "client.email", target = "client_eMail")
    @Mapping(source = "client.id", target = "client_id")
    CampaignDTO campaignToCampaignDTO(Campaign campaign);
    @Mapping(source = "client_id", target = "client.id")
    //@InheritInverseConfiguration
    Campaign campaignDTOToCampaign(CampaignDTO campaignDTO);
}