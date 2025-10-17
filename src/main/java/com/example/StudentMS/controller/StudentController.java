package com.example.StudentMS.controller;


import com.example.StudentMS.dto.StudentRequest;
import com.example.StudentMS.dto.StudentResponse;
import com.example.StudentMS.service.StudentService;
import com.example.StudentMS.utils.ResponseWrapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/students")
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping
    public ResponseEntity<ResponseWrapper<StudentResponse>> create(@Valid @RequestBody StudentRequest request){
        StudentResponse res = service.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.success(res));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<StudentResponse>> getById(@PathVariable Long id){
        StudentResponse res = service.getStudentById(id);
        return ResponseEntity.ok(ResponseWrapper.success(res));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<StudentResponse>> update(@PathVariable Long id, @Valid @RequestBody StudentRequest request){
        StudentResponse res = service.updateStudent(id, request);
        return ResponseEntity.ok(ResponseWrapper.success(res));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<String>> delete(@PathVariable Long id){
        service.deleteStudent(id);
        return ResponseEntity.ok(ResponseWrapper.success("Delete student successfully"));
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<Page<StudentResponse>>> list(
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,desc") String sort) {

        // parse sort param: e.g. sort=name,asc
        String[] sortParts = sort.split(",");
        Sort.Direction dir = Sort.Direction.fromString(sortParts.length > 1 ? sortParts[1] : "desc");
        String prop = sortParts[0];
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, prop));

        Page<StudentResponse> result = (q == null || q.isBlank()) ? service.getAllStudents(pageable)
                : service.searchStudents(q, pageable);

        return ResponseEntity.ok(ResponseWrapper.success(result));
    }


}
