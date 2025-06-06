package com.ThongKe.ChanNuoi.TrangChu;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private int Id;
	@Column(name="User_name")
	private String username;
	private String Password;
	@Column(name="User_admin")
	private boolean useradmin;
}
