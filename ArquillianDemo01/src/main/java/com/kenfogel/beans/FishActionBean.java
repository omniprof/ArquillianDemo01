package com.kenfogel.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.sql.DataSource;

/**
 * A magic bean that returns a list of fish
 * 
 * @author Ken
 * 
 */
@Named("fishAction")
@SessionScoped
public class FishActionBean implements Serializable {

	private static final long serialVersionUID = 7498237763098271958L;

	// See the context.xml for the datasource
	@Resource(name = "jdbc/fish")
	private DataSource ds;

	public List<FishData> getAll() throws SQLException {
		if (ds == null) {
			throw new SQLException("Can't get data source");
		}

		List<FishData> fishies = new ArrayList<>();

		try (Connection conn = ds.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM FISH");
				ResultSet resultSet = stmt.executeQuery();) {
			while (resultSet.next()) {
				FishData fishData = new FishData();
				fishData.setCommonName(resultSet.getString("COMMONNAME"));
				fishData.setDiet(resultSet.getString("DIET"));
				fishData.setKh(resultSet.getString("KH"));
				fishData.setLatin(resultSet.getString("LATIN"));
				fishData.setPh(resultSet.getString("PH"));
				fishData.setFishSize(resultSet.getString("FISHSIZE"));
				fishData.setSpeciesOrigin(resultSet.getString("SPECIESORIGIN"));
				fishData.setStocking(resultSet.getString("STOCKING"));
				fishData.setTankSize(resultSet.getString("TANKSIZE"));
				fishData.setTemp(resultSet.getString("TEMP"));
				fishData.setId(resultSet.getInt("ID"));

				fishies.add(fishData);
			}
		}
		return fishies;
	}
	
}
