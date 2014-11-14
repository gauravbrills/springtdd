/**
 * 
 */
package in.gauravbrills.springtdd.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author grawat
 * 
 */
@Data
@AllArgsConstructor
public class User {
	@NotEmpty
	@Length(max = 4)
	private String title;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
}
