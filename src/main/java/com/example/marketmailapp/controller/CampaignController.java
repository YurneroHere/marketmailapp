package com.example.marketmailapp.controller;

import com.example.marketmailapp.dto.CampaignDTO;
import com.example.marketmailapp.service.CampaignService;
import com.example.marketmailapp.service.RequestCacheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    private final CampaignService campaignService;
    private final RequestCacheService requestCacheService;

    public CampaignController(CampaignService campaignService, RequestCacheService requestCacheService) {
        this.campaignService = campaignService;
        this.requestCacheService = requestCacheService;
    }

    @PostMapping
    public ResponseEntity<?> createCampaign(@RequestBody CampaignDTO campaignDTO) {
        try {
            return ResponseEntity.ok(campaignService.createCampaign(campaignDTO));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CampaignDTO>> getCampaignsByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(campaignService.getCampaignsByClientId(clientId));
    }

    @PostMapping("/send/{campaignId}")
    public ResponseEntity<?> sendCampaign(@PathVariable Long campaignId) {
        if(requestCacheService.isDuplicate(campaignId)){
            return ResponseEntity.badRequest().body(requestCacheService.getResponse(campaignId));
        }
        try{
        requestCacheService.store(campaignId, "Already in Process");
            return ResponseEntity.ok(campaignService.sendCampaignMail(campaignId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        finally {
            requestCacheService.removeResponse(campaignId);
        }
    }
}
