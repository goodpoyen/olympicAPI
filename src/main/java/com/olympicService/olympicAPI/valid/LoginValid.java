package com.olympicService.olympicAPI.valid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginValid {
	@NotNull
	public String account;

	@NotNull
	@Size(message = "長度錯誤", min = 8, max = 16)
	public String password;
}
