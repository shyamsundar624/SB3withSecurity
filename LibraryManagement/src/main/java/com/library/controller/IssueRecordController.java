package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.IssueRecord;
import com.library.service.IssueRecordService;

@RestController
@RequestMapping("/issuerecords")
public class IssueRecordController {

	@Autowired
	private IssueRecordService issueRecordService;
	
	@PostMapping("/issuethebook/{id}")
	public ResponseEntity<IssueRecord> issueTheBook(@PathVariable("id") Long bookId){
		return ResponseEntity.ok(issueRecordService.issueTheBook(bookId));
	}

	@PostMapping("/returnthebook/{id}")
public ResponseEntity<IssueRecord> returnTheBook(@PathVariable Long id){
	return ResponseEntity.ok(issueRecordService.returnTheBook(id));
}


}
