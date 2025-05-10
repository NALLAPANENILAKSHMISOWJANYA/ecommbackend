package Ecom.Exception;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MyErrorClass {

	private String message;
	private LocalDateTime localDateTimes;
	private String desc;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LocalDateTime getLocalDateTimes() {
		return localDateTimes;
	}
	public void setLocalDateTimes(LocalDateTime localDateTimes) {
		this.localDateTimes = localDateTimes;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
