package com.notification.rest.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NotificationDto {
	private String sender;
	private List<String> receivers;
	private String object;
	private String message;
	private List<FileDto> files;
}
