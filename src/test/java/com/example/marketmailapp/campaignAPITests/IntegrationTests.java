package com.example.marketmailapp.campaignAPITests;

import com.example.marketmailapp.dto.CampaignDTO;
import com.example.marketmailapp.enums.MailStatus;
import com.example.marketmailapp.repository.CampaignRepository;
import com.example.marketmailapp.service.CampaignService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateCampaign() throws Exception {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setName("test Campaign");
        campaignDTO.setSubject("test subject");
        campaignDTO.setEmailBody("This is the test email body.");
        campaignDTO.setClient_id(1L);
        campaignDTO.setSubscribers(Arrays.asList("test1@example.com", "test2@example.com", "test3@example.com"));

        MvcResult result =  mockMvc.perform(post("/api/campaigns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(campaignDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();

        Long campaignId = objectMapper.readTree(jsonResponse).get("id").asLong();

        assertThat(campaignId).isNotNull();
        Long clientId = objectMapper.readTree(jsonResponse).get("client_id").asLong();
        testGetCampaign(clientId);
        testSendMail(campaignId);
        campaignService.deleteById(campaignId);
    }

    private void testGetCampaign(Long clientId) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/campaigns/client/" + clientId.toString()))
                .andExpect(status().isOk()).andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();

        JsonNode campaignNodes = objectMapper.readTree(jsonResponse);
        assertThat(campaignNodes).isNotNull();
        assertThat(campaignNodes.isArray()).isTrue();
        assertThat(campaignNodes.size()).isGreaterThan(0);
    }

    private void testSendMail(Long campaignId) throws Exception {
        CampaignDTO campaignDTO = new CampaignDTO();
        campaignDTO.setId(campaignId);

        MvcResult mvcResult = mockMvc.perform(post("/api/campaigns/send/" + campaignId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(campaignDTO)))
                .andExpect(status().isOk()).andReturn();

        CampaignDTO updatedCampaign = campaignService.getById(campaignId);
        assertThat(updatedCampaign).isNotNull();
        assertThat(updatedCampaign.getStatus()).isEqualTo(MailStatus.SENT);
    }
}