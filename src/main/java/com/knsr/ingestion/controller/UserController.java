package com.knsr.ingestion.controller;

import com.knsr.ingestion.dto.request.MeterStatusUpdateRequestDTO;
import com.knsr.ingestion.dto.request.UserRequestDTO;
import com.knsr.ingestion.dto.request.WaterUsageRequestDTO;
import com.knsr.ingestion.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/wmgmt/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addWithMeter")
    public ResponseEntity<String> addUserWithMeter(@RequestBody UserRequestDTO userDTO) {
        userService.addUserAndWaterMeter(userDTO);
        return ResponseEntity.ok("User and Water Meter added successfully.");
    }

   /* @PutMapping("/updateMeterStatus")
    public ResponseEntity<String> updateMeterStatus(@RequestBody MeterStatusUpdateRequestDTO dto) {
        userService.updateMeterStatus(dto);
        return ResponseEntity.ok("Meter status updated successfully.");
    }

    @Operation(
           summary = "Add water usage for a user",
           description = "Creates a new water usage record and generates an alert if consumption exceeds threshold."
    )
    @PostMapping("/addUsage")
    public ResponseEntity<String> addWaterUsage(@RequestBody WaterUsageRequestDTO dto) {
        userService.addWaterUsage(dto);
        return ResponseEntity.ok("Water usage recorded successfully.");
    }
*/

}
