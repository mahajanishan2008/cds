package com.govtech.cds.domains;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@CsvBindByName
	@NonNull
	private String id;
	@CsvBindByName
	@NonNull
	private String name;
	@CsvBindByName
	@NonNull
	private String salary;

}
