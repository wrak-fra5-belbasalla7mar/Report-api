package org.fawry.reportapi.controller;

import org.fawry.reportapi.model.Cycle;
import org.fawry.reportapi.service.EvaluationService;
import org.fawry.reportapi.service.CycleReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final EvaluationService evaluationService;
    private final CycleReportService cycleReportService;

    public ReportController(EvaluationService evaluationService, CycleReportService cycleReportService) {
        this.evaluationService = evaluationService;
        this.cycleReportService = cycleReportService;
    }

    // company manager
    @GetMapping("/download/cycle/{id}")
    public ResponseEntity<?> downloadExcelReportCycle(@PathVariable Long id) throws IOException {

        Cycle cycle = evaluationService.getCycleData(id);


        if (cycle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        byte[] excelData = cycleReportService.generateExcel(cycle);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=cycle_report.xlsx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");


        return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
    }
}
