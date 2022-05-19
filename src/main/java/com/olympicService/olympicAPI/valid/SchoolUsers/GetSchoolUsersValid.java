package com.olympicService.olympicAPI.valid.SchoolUsers;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetSchoolUsersValid {
	@NotNull
	public String AT;
}
