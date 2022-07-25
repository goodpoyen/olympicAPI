package com.olympicService.olympicAPI.valid.Personnel;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetpersonnelValid {
	@NotNull
	public String AT;
	
	@NotNull
	public Integer id;
}
