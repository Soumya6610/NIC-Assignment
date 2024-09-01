package com.roleapi.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AuthenticationResponse {

	private String jwt;
}
