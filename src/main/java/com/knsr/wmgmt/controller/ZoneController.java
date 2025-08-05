package com.knsr.wmgmt.controller;/*
package com.knsr.wmgmt.controller;

import com.knsr.wmgmt.dto.response.ZoneUsageResponse;
import com.knsr.wmgmt.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/api/wmgmt/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @GetMapping("/usage")
    public ResponseEntity<List<ZoneUsageResponse>> getZoneUsageTrends() {
        return ResponseEntity.ok(zoneService.getZoneUsageTrends());
    }
}*/
