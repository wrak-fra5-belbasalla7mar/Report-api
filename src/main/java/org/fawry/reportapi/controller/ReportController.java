package org.fawry.reportapi.controller;

import org.fawry.reportapi.model.Cycle;
import org.fawry.reportapi.service.EvaluationService;
import org.fawry.reportapi.service.ExcelReportService;
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
    private final ExcelReportService excelReportService;

    public ReportController(EvaluationService evaluationService, ExcelReportService excelReportService) {
        this.evaluationService = evaluationService;
        this.excelReportService = excelReportService;
    }

    @GetMapping("/download/cycle/{id}")
    public ResponseEntity<byte[]> downloadExcelReport(@PathVariable Long id) throws IOException {
        Cycle cycle = evaluationService.getEvaluationData(id);
        if (cycle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        byte[] excelData = excelReportService.generateExcel(cycle);
        return new ResponseEntity<>(excelData, HttpStatus.OK);
    }
}