package com.pravna_informatika.projekat.controller;

import com.pravna_informatika.projekat.model.CaseDescription;
import com.pravna_informatika.projekat.model.ReasoningResult;
import com.pravna_informatika.projekat.service.CbrService;
import com.pravna_informatika.projekat.service.DrDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ReasoningController {

    private final DrDeviceService ruleBasedReasoningService;
    private final CbrService caseBasedReasoningService;

    @GetMapping("rules/clean")
    ResponseEntity<String> clean() {
        ruleBasedReasoningService.clean();
        return ResponseEntity.ok("Reasoning files cleaned");
    }

    @PostMapping("/start_reasoning")
    ResponseEntity<ReasoningResult> startReasoning(@RequestBody CaseDescription caseDescription) {
        ReasoningResult reasoningResult = new ReasoningResult();
        reasoningResult.setAppliedProvisions(ruleBasedReasoningService.start(caseDescription));
        reasoningResult.setSimilarCases(caseBasedReasoningService.start(caseDescription));
        ruleBasedReasoningService.clean();
        return ResponseEntity.ok(reasoningResult);
    }
}
