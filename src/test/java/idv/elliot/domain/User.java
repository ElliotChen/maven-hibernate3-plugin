package idv.elliot.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_USER")
public class User extends Identifiable {
	private static final long serialVersionUID = 6973853293711914826L;

	@Column(name = "NAME", length = 50)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", length = 10)
	private Gender gender;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "PROFILE_ID", nullable = true)
	private Profile profile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
