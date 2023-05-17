package com.notification.rest;

import com.notification.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@Api
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@ApiOperation(value = "Send a notification email")
	@PostMapping("/send-notification")
	public ResponseEntity<Map<String, Object>> sendEmail(
			@ApiParam(value = "Message details", required = true)
			@RequestBody Map<String, Object> messageDetails) {
		boolean status = notificationService.sendEmailFromDetails(messageDetails);
		Map<String, Object> response = new HashMap<>();
		System.out.println(messageDetails);
		if (status) {
			response.put("status", HttpStatus.OK.value());
			response.put("message", "Email sent successfully.");
			return ResponseEntity.ok(response);
		} else {
			response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.put("message", "Failed to send email.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@RabbitListener(queues = "${rabbitmq.queue.receive}")
	public void receiveMessage(Map<String, Object> messageDetails) {
		boolean status = notificationService.sendEmailFromDetails(messageDetails);
		System.out.println(messageDetails);
		if (status) {
			// Handle the success case, e.g., log a success message or update a database record
		} else {
			// Handle the failure case, e.g., log an error message or retry the operation
		}
	}
}