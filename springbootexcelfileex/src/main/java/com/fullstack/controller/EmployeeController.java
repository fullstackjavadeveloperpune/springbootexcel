package com.fullstack.controller;

import com.fullstack.model.Employee;
import com.fullstack.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> saveExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        employeeService.saveExcelFileData(file.getInputStream());

        return ResponseEntity.ok("File Data Saved Successfully");
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList());
    }

    @GetMapping("/searchbyname")
    public ResponseEntity<List<Employee>> searchByName(@RequestParam(required = false, defaultValue = "POOJA") String empName) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> emp.getEmpName().equals(empName)).toList());
    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {
        employeeService.deleteById(empId);
        return ResponseEntity.ok("Data Deleted Successfully");
    }
}
