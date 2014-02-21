package com.kenfogel.beans;

import java.io.Serializable;

/**
 * Its a Fish!
 * @author Ken
 *
 */
public class FishData implements Serializable{
	
	private static final long serialVersionUID = -6748810783693516976L;

	private int id;
	private String commonName;
	private String latin;
	private String ph;
	private String kh;
	private String temp;
	private String fishSize;
	private String speciesOrigin;
	private String tankSize;
	private String stocking;
	private String diet;
	
	public FishData() {
		super();
		this.id = -1;
		this.commonName = "";
		this.latin = "";
		this.ph = "";
		this.kh = "";
		this.temp = "";
		this.fishSize = "";
		this.speciesOrigin = "";
		this.tankSize = "";
		this.stocking = "";
		this.diet = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getLatin() {
		return latin;
	}

	public void setLatin(String latin) {
		this.latin = latin;
	}

	public String getPh() {
		return ph;
	}

	public void setPh(String ph) {
		this.ph = ph;
	}

	public String getKh() {
		return kh;
	}

	public void setKh(String kh) {
		this.kh = kh;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getFishSize() {
		return fishSize;
	}

	public void setFishSize(String fishSize) {
		this.fishSize = fishSize;
	}

	public String getSpeciesOrigin() {
		return speciesOrigin;
	}

	public void setSpeciesOrigin(String speciesOrigin) {
		this.speciesOrigin = speciesOrigin;
	}

	public String getTankSize() {
		return tankSize;
	}

	public void setTankSize(String tankSize) {
		this.tankSize = tankSize;
	}

	public String getStocking() {
		return stocking;
	}

	public void setStocking(String stocking) {
		this.stocking = stocking;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FishData other = (FishData) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FishData [id=" + id + ", commonName=" + commonName + ", latin="
				+ latin + ", ph=" + ph + ", kh=" + kh + ", temp=" + temp
				+ ", fishSize=" + fishSize + ", speciesOrigin=" + speciesOrigin
				+ ", tankSize=" + tankSize + ", stocking=" + stocking
				+ ", diet=" + diet + "]";
	}

	
}
