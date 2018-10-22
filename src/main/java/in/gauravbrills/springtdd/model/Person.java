/**
 * 
 */
package in.gauravbrills.springtdd.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author grawat
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	@NotBlank
	@Size(max = 4)
	private String title;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
}
