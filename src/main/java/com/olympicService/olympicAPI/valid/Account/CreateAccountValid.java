package com.olympicService.olympicAPI.valid.Account;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateAccountValid {
	@NotNull
	public String AT;
}
