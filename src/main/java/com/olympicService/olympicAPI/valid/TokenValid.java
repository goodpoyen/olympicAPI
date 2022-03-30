package com.olympicService.olympicAPI.valid;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenValid {
	@NotNull
	public String T;

}
