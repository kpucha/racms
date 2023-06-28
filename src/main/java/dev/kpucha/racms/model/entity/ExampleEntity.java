package dev.kpucha.racms.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "EXAMPLES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -752918708356010982L;
	@Id
	@Column(name = "ID")
	private Long id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "DATE")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;
	@Column(name = "AMOUNT")
	@NumberFormat(pattern = "#0.00")
	private BigDecimal amount;
	@Column(name = "ACTIVE")
	private Boolean active;
}
